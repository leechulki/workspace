package hdel.sd.sbp.service;


import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.sbp.dao.Sbp0162D;
import hdel.sd.sbp.domain.Sbp0162ParamVO;
import hdel.sd.sso.domain.ZCOBS001;
import hdel.sd.sbp.domain.ZSDS0072;
import hdel.sd.sbp.domain.ZSDT0014;
import hdel.sd.sbp.domain.ZSDT0014S;
import hdel.sd.sbp.domain.ZSDT1001;
import hdel.sd.sbp.domain.ZSDT1005;
import hdel.sd.sbp.domain.ZSDT1012;
import hdel.sd.sbp.domain.ZSDT1023;
import hdel.sd.ses.domain.Ses0180;
import hdel.sd.smp.control.SmpComC;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Service
public class Sbp0162S_N extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private final String SORLT_NEW = "10";

//	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private AutoSoNumberS autoSoService;

	private Sbp0162D sbp0162D;

	@Override
	public void createDao(SqlSession sqlSession) {
		this.sqlSession = (SrmSqlSession) sqlSession;
		sbp0162D = sqlSession.getMapper(Sbp0162D.class);
	}

	/**
	 *
	 * ����ó���� �������(71),�����ߴ�(72),������(73)�� ���ְ��(sorlt)�� ����
	 *
	 * @param paramVO
	 */
	public void updateSORLT(MipParameterVO paramVO) throws Exception{

		Dataset dsInput = paramVO.getDatasetList().getDataset("ds_input");

		logger.info("@@@@@@@@@ ����ó�� update in @@@@@@@@@@@@@@");
		logger.info("@@@@@@@@@ ����ó�� update in dsCond : "+dsInput.toString() );

		try {

			Sbp0162ParamVO param = new Sbp0162ParamVO();
			param.setMANDT(paramVO.getVariable("G_MANDT"));			// Ŭ���̾�Ʈ
			param.setSORLT(dsInput.getColumnAsString(0, "SORLT"));	// ���ְ��
			param.setSONNR(dsInput.getColumnAsString(0, "SONNR"));	// ���ְ�ȹ��ȣ
			param.setZPYM(dsInput.getColumnAsString(0, "ZPYM"));		// ��ȹ���
			param.setDATE(DateTime.getDateString());
			param.setTIME(DateTime.getShortTimeString());
			param.setUSER(paramVO.getVariable("G_USER_ID"));

			sbp0162D.updateSORLT(param);

			if ("73".equals(dsInput.getColumnAsString(0, "SORLT")))		{	// ������
				if ("Y".equals(dsInput.getColumnAsString(0, "CHKFLG")))		{	// ���������� ��� ��
					Ses0180 ses0180 = new Ses0180();
					// ���� Mapping
					ses0180.setMANDT(paramVO.getVariable("G_MANDT"));
					ses0180.setQTNUM(dsInput.getColumnAsString(0, "SONNR"));	// ������ȣ�ʵ忡 ���ְ�ȹ��ȣ �Է�
					ses0180.setQTSER(0);
					ses0180.setBDDAT(dsInput.getColumnAsString(0, "BDDAT"));	// ����������
					ses0180.setSBDCM(dsInput.getColumnAsString(0, "ZSEC"));		// ������
					ses0180.setSBDAM(new BigDecimal(DatasetUtility.getDouble(dsInput, 0, "SBDAM")).setScale(2, RoundingMode.DOWN));	// ������
					ses0180.setZPRGFLG("31");	// ��������
					ses0180.setCDATE(DateTime.getDateString());
					ses0180.setCTIME(DateTime.getShortTimeString());
					ses0180.setCUSER(paramVO.getVariable("G_USER_ID"));
					//ses0180.setCTDAT(" ");
					//ses0180.setZCOST(new BigDecimal("0").setScale(2, RoundingMode.DOWN));	// ����
					//ses0180.setPBDAM(new BigDecimal("0").setScale(2, RoundingMode.DOWN));	// ������
					//ses0180.setZFRSN(" ");
					//ses0180.setZSEC("");		// 2��������
					//ses0180.setZSECAM(new BigDecimal("0").setScale(2, RoundingMode.DOWN));	// 2��������
					//ses0180.setZFOREC(new BigDecimal("0").setScale(2, RoundingMode.DOWN));	// 2��������
					//ses0180.setZSFLG(" ");	// ��������
					//ses0180.setZRMK(" ");		// ���

					sbp0162D.insertZSDT1055(ses0180);
				}
			}

		} catch (Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 *
	 * ����ó���� �̿�(70)�ϰ�� copyó��
	 *
	 * @param paramVO
	 */
	public void insertSORLT(MipParameterVO paramVO) throws Exception{
		Dataset dsInput  = paramVO.getDatasetList().getDataset("ds_input");

		// ���ְ�ȹ��� RFCȣ�� �� ó�� 2012.11.16
		Dataset dsInput2 = paramVO.getDatasetList().getDataset("ds_input2");

		Object obj[] = null;

		ZQMSEMSG[] listMsg = new ZQMSEMSG[] {}; // WEB I/F ó�� ���� ���

		ZSDT0014[] listZSDT0014 = new ZSDT0014[] {}; // �� ���ְ�ȹ
		ZSDT1001[] listZSDT1001 = new ZSDT1001[] {}; // ���ְ�ȹ
		ZCOBS001[] listZCOBS001 = new ZCOBS001[] {}; // ��������
		ZSDT1005[] listZSDT1005 = new ZSDT1005[] {}; // ���ְ�ȹ(����)
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[] {}; // �����ȹ DATA (WEB���� ����)
		ZSDT1012[] listZSDT1012 = new ZSDT1012[] {}; // �����ȹ-����
		ZSDT1023[] listZSDT1023 = new ZSDT1023[] {}; // �����ȹ(����)-����
		ZSDS0072[] listZSDS0072 = new ZSDS0072[] {}; // [SD] ����/��� ��ȹ �ڵ� ���

		Dataset dsZSDT0014 = ZSDT0014.getDataset(); // sap dataset������ �ʱ�ȭ
		Dataset dsZSDT1001 = ZSDT1001.getDataset(); // sap dataset������ �ʱ�ȭ
		Dataset dsZCOBS001 = ZCOBS001.getDataset(); // sap dataset������ �ʱ�ȭ

		logger.info("@@@@@@@@@ ����ó�� insert in @@@@@@@@@@@@@@");
		logger.info("@@@@@@@@@ ����ó�� insert in dsCond : "+dsInput.toString() );

		try {

			// �ڵ�ä��(���ְ�ȹ��ȣ)--------------------------------------------------
			// DAO����
			autoSoService.createDao( sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")) );

			AutoNumberParamVO sonnrParam = new AutoNumberParamVO();
			sonnrParam.setDeptFlag(DatasetUtility.getString(dsInput, "AUART"));	// �ǸŹ�������
			sonnrParam.setSoQtFlag("1");// ä������(0:�����ȹ, 1:���ְ�ȹ, 2:����)
			sonnrParam.setMandt(paramVO.getVariable("G_MANDT")); // SAP CLIENT

			List<AutoNumberVO> sonnrList = autoSoService.AutoSoNumber(sonnrParam);
			// ----------------------------------------------------------------------

			Sbp0162ParamVO param = new Sbp0162ParamVO();
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setSORLT(dsInput.getColumnAsString(0, "SORLT"));		// ����ó�� ����
			param.setSONNR(dsInput.getColumnAsString(0, "SONNR"));		// ������ ���ְ�ȹ��ȣ
			param.setZPYM(dsInput.getColumnAsString(0, "ZPYM"));
			param.setNEWSONNR(sonnrList.get(0).getNumber());// ä�� �� ���ְ�ȹ��ȣ
			param.setNEWZPYM(dsInput.getColumnAsString(0, "NEWZPYM"));
			param.setDELDAT(dsInput.getColumnAsString(0, "DELDAT"));	// ��ǰ������
			param.setDATE(DateTime.getDateString());
			param.setTIME(DateTime.getShortTimeString());
			param.setUSER(paramVO.getVariable("G_USER_ID"));

			// ����ó�� ���� ����
			param.setPRESORLT(dsInput.getColumnAsString(0, "PRESORLT"));
			param.setNEWSORLT(SORLT_NEW);	// ��ȹ

			logger.info("MANDT    ==> " + param.getMANDT()    + "\n");
			logger.info("SORLT    ==> " + param.getSORLT()    + "\n");
			logger.info("SONNR    ==> " + param.getSONNR()    + "\n");
			logger.info("ZPYM     ==> " + param.getZPYM()     + "\n");
			logger.info("NEWSONNR ==> " + param.getNEWSONNR() + "\n");
			logger.info("NEWZPYM  ==> " + param.getNEWZPYM()  + "\n");
			logger.info("DATE     ==> " + param.getDATE()     + "\n");
			logger.info("TIME     ==> " + param.getTIME()     + "\n");
			logger.info("USER     ==> " + param.getUSER()     + "\n");

			// ���ְ�ȹ��� RFCȣ��  2012.11.16
			dsZSDT0014.deleteAll(); // �� ���ְ�ȹ
			dsZSDT1001.deleteAll(); // ���ְ�ȹ
			dsZCOBS001.deleteAll(); // ��������

			// input����Ʈ��
			String status = "insert"; // �̿��� ���´� ������ isert
			int nRow = 0; // �� ����Ÿ��(ZSDT1001, ZSDT0014, ZCOBS001)�� ����� row number

			// �̿� ó���� ������ ���ϰ��̳� ���� �� ���ְ�ȹ�� ������ ���� ������ ���Ͽ� �� ���
			// �� Dutyó���� ���ʿ�.
			for (int i = 0; i < dsInput2.getRowCount(); i++) {

				String matnr = dsInput2.getColumn(i, "MATNR").toString(); // �����ȣ

				String gtype = dsInput2.getColumn(i, "GTYPE").toString(); // ��ǥ����

				dsZSDT0014.appendRow(); // �����ȹ �������̺�
				dsZSDT1001.appendRow(); // �����ȹ

				// ��ȹ��� ��¥����(-)����� -> ��ȹ����� ����
				String biddat = DatasetUtility.getString(dsInput, 0, "NEWZPYM").substring(0, 4) + "-" +
							    DatasetUtility.getString(dsInput, 0, "NEWZPYM").substring(4) + "-01";

				// deldat ��¥����(-)����� -> ����� ��ǰ������
				String deldat = DatasetUtility.getString(dsInput, 0, "DELDAT");
				deldat = deldat.substring(0, 4) + "-" +
						 deldat.substring(4).substring(0, 2) + "-" + deldat.substring(6);

				// ���糯¥ ����(-)�����
				String date = DateTime.getDateString();
				date = date.substring(0, 4) + "-" +
					   date.substring(4).substring(0, 2) + "-" + date.substring(6);

				// ����ð� ����(:)�����
				String time = DateTime.getShortTimeString();
				time = time.substring(0, 2) + ":" +
					   time.substring(2).substring(0, 2) + ":" + time.substring(4);

				// ���ְ�ȹ��ȣ -> �̿� �� �űԷ� ä���� ���ְ�ȹ��ȣ
				String sonnr = sonnrList.get(0).getNumber();

				dsZSDT1001.setColumn(nRow, "CDATE", date); // ��������
				dsZSDT1001.setColumn(nRow, "CTIME", time); // �����ð�
				dsZSDT1001.setColumn(nRow, "CUSER", paramVO.getVariable("G_USER_ID")); // ������

				dsZSDT0014.setColumn(nRow, "CDATE", dsZSDT1001.getColumn(nRow, "CDATE")); // ��������
				dsZSDT0014.setColumn(nRow, "CTIME", dsZSDT1001.getColumn(nRow, "CTIME")); // �����ð�
				dsZSDT0014.setColumn(nRow, "CUSER", dsZSDT1001.getColumn(nRow, "CUSER")); // ������

				String zmpflg = " ";
				dsZSDT1001.setColumn(nRow, "ZMPFLG", zmpflg);	// �̵���ȹ����
				dsZSDT1001.setColumn(nRow, "ZDELI",  dsInput2.getColumnAsString(i, "ZDELI"));	// �ܳ��ⱸ��
				dsZSDT1001.setColumn(nRow, "ZINTER", dsInput2.getColumnAsString(i, "ZINTER"));	// �߰蹫������

				// 0014�� ���� �ʵ���
				// dsZSDT0014.setColumn(nRow, "ZMPFLG",
				// dsZSDT1001.getColumn(nRow, "ZMPFLG") ); // �̵���ȹ����
				dsZSDT0014.setColumn(nRow, "ZDELI",  dsZSDT1001.getColumn(nRow, "ZDELI")); // �ܳ��ⱸ��
				dsZSDT0014.setColumn(nRow, "ZINTER", dsZSDT1001.getColumn(nRow, "ZINTER")); // �߰蹫������(Y)

				dsZSDT1001.setColumn(nRow, "SONNR", sonnr); // ���ְ�ȹ��ȣ
				dsZSDT0014.setColumn(nRow, "SONNR", dsZSDT1001.getColumn(nRow, "SONNR")); // ���ְ�ȹ��ȣ
				dsZSDT0014.setColumn(nRow, "PSPID", dsZSDT1001.getColumn(nRow, "SONNR")); // ������Ʈ��ȣ

				dsZSDT1001.setColumn(nRow, "MANDT", paramVO.getVariable("G_MANDT")); // Ŭ���̾�Ʈ
				dsZSDT0014.setColumn(nRow, "MANDT", dsZSDT1001.getColumn(nRow, "MANDT")); // Ŭ���̾�Ʈ

				dsZSDT1001.setColumn(nRow, "ZPYM",  dsInput.getColumnAsString(0, "NEWZPYM")); // ��ȹ���
				dsZSDT0014.setColumn(nRow, "BIDYM", dsZSDT1001.getColumn(nRow, "ZPYM")); // ��ȹ���

				dsZSDT1001.setColumn(nRow, "SPART", dsInput2.getColumnAsString(i, "SPART")); // ��ǰ��
				dsZSDT0014.setColumn(nRow, "SPART", dsZSDT1001.getColumn(nRow, "SPART")); // ��ǰ��

				dsZSDT1001.setColumn(nRow, "AUART", dsInput2.getColumnAsString(i, "AUART")); // �ǸŹ�������
				dsZSDT0014.setColumn(nRow, "AUART", dsZSDT1001.getColumn(nRow, "AUART")); // �ǸŹ�������

				dsZSDT1001.setColumn(nRow, "MATNR", matnr); // �����ȣ
				dsZSDT0014.setColumn(nRow, "MATNR", dsZSDT1001.getColumn(nRow, "MATNR")); // �����ȣ

				dsZSDT1001.setColumn(nRow, "VKBUR", dsInput2.getColumnAsString(i, "VKBUR")); // �μ�
				dsZSDT0014.setColumn(nRow, "VKBUR", dsZSDT1001.getColumn(nRow, "VKBUR")); // �μ�

				dsZSDT1001.setColumn(nRow, "VKGRP", dsInput2.getColumnAsString(i, "VKGRP")); // ��
				dsZSDT0014.setColumn(nRow, "VKGRP", dsZSDT1001.getColumn(nRow, "VKGRP")); // ��

				dsZSDT1001.setColumn(nRow, "ZKUNNR", dsInput2.getColumnAsString(i, "ZKUNNR")); // ���������
				dsZSDT0014.setColumn(nRow, "ZKUNNR", dsZSDT1001.getColumn(nRow, "ZKUNNR")); // ���������

				dsZSDT1001.setColumn(nRow, "GTYPE", gtype); // ��ǥ����
				dsZSDT0014.setColumn(nRow, "GTYPE", dsZSDT1001.getColumn(nRow, "GTYPE")); // ��ǥ����

				dsZSDT1001.setColumn(nRow, "RTYPE",     dsInput2.getColumnAsString(i, "RTYPE")); // �Ǳ�
				dsZSDT0014.setColumn(nRow, "GTYPE_OLD", dsZSDT1001.getColumn(nRow, "RTYPE")); // �Ǳ��� GTYPE_OLD->RTYPE

				dsZSDT1001.setColumn(nRow, "TYPE1",  dsInput2.getColumnAsString(i, "TYPE1")); // Ÿ��1
				dsZSDT0014.setColumn(nRow, "GSPEC1", dsZSDT1001.getColumn(nRow, "TYPE1")); // ����1

				dsZSDT1001.setColumn(nRow, "TYPE2",  dsInput2.getColumnAsString(i, "TYPE2")); // Ÿ��2
				dsZSDT0014.setColumn(nRow, "GSPEC2", dsZSDT1001.getColumn(nRow, "TYPE2")); // ����2

				dsZSDT1001.setColumn(nRow, "TYPE3",  dsInput2.getColumnAsString(i, "TYPE3")); // Ÿ��3
				dsZSDT0014.setColumn(nRow, "GSPEC3", dsZSDT1001.getColumn(nRow, "TYPE3")); // ����3

				dsZSDT1001.setColumn(nRow, "ZNUMBER", dsInput2.getColumnAsDouble(i, "ZNUMBER")); // ���
				dsZSDT0014.setColumn(nRow, "ZNUMBER", dsZSDT1001.getColumn(nRow, "ZNUMBER")); // ���

				dsZSDT1001.setColumn(nRow, "KUNNR", dsInput2.getColumnAsString(i, "KUNNR")); // ��
				dsZSDT0014.setColumn(nRow, "KUNNR", dsZSDT1001.getColumn(nRow, "KUNNR")); // ��

				dsZSDT1001.setColumn(nRow, "ZAGNT", dsInput2.getColumnAsString(i, "ZAGNT")); // �븮��
				dsZSDT0014.setColumn(nRow, "ZAGNT", dsZSDT1001.getColumn(nRow, "ZAGNT")); // �븮��

				dsZSDT1001.setColumn(nRow, "LAND1", dsInput2.getColumnAsString(i, "LAND1")); // ����
				dsZSDT0014.setColumn(nRow, "LAND1", dsZSDT1001.getColumn(nRow, "LAND1")); // ����

				dsZSDT1001.setColumn(nRow, "GSNAM", dsInput2.getColumnAsString(i, "GSNAM")); // �����
				dsZSDT0014.setColumn(nRow, "GSNAM", dsZSDT1001.getColumn(nRow, "GSNAM")); // �����

				dsZSDT1001.setColumn(nRow, "REGION", dsInput2.getColumnAsString(i, "REGION")); // ��ġ����
				dsZSDT0014.setColumn(nRow, "REGION", dsZSDT1001.getColumn(nRow, "REGION")); // ��ġ����

				dsZSDT1001.setColumn(nRow, "SHANGH", dsInput2.getColumnAsString(i, "SHANGH")); // ����/���ر���
				dsZSDT0014.setColumn(nRow, "SHANGH", dsZSDT1001.getColumn(nRow, "SHANGH")); // ����/���ر���

				dsZSDT1001.setColumn(nRow, "SOFOCA", dsInput2.getColumnAsString(i, "SOFOCA"));// ���ֿ����

				// ���ֿ����
				double tempDbl = 0;
				if (DatasetUtility.getDouble(dsInput2, i, "SOFOCA") == 0) {
					dsZSDT0014.setColumn(nRow, "SOFOCA", 0);
				} else {
					// ��ȭ(WAERK)������ ���� ȯ��ݾ� ���
					if (   "KRW".equals(DatasetUtility.getString(dsInput2, i, "WAERK"))
						|| "JPY".equals(DatasetUtility.getString(dsInput2, i, "WAERK"))) {

						tempDbl = DatasetUtility.getDouble(dsInput2, i, "SOFOCA") / 100.0;

					} else {
						tempDbl = DatasetUtility.getDouble(dsInput2, i, "SOFOCA");
					}

					dsZSDT0014.setColumn(nRow, "SOFOCA", tempDbl);
				}

				dsZSDT1001.setColumn(nRow, "ZFORE", DatasetUtility.getString(dsInput2, i, "ZFORE")); // ���������
				dsZSDT0014.setColumn(nRow, "ZFORE", dsZSDT1001.getColumn(nRow, "ZFORE")); // ���������

				dsZSDT1001.setColumn(nRow, "WAERK", DatasetUtility.getString(dsInput2, i, "WAERK")); // ��ȭ
				dsZSDT0014.setColumn(nRow, "WAERK", dsZSDT1001.getColumn(nRow, "WAERK")); // ��ȭ

				dsZSDT1001.setColumn(nRow, "DELDAT", deldat); // ���⿹����
				dsZSDT0014.setColumn(nRow, "DELDAT", dsZSDT1001.getColumn(nRow, "DELDAT")); // ���⿹����

				dsZSDT1001.setColumn(nRow, "ZRMK", DatasetUtility.getString(dsInput2, i, "ZRMK")); // ���

				dsZSDT1001.setColumn(nRow, "SOABLE", DatasetUtility.getString(dsInput2, i, "SOABLE")); // ���ְ��ɼ�
				dsZSDT0014.setColumn(nRow, "SOABLE", dsZSDT1001.getColumn(nRow, "SOABLE")); // ���ְ��ɼ�

				dsZSDT1001.setColumn(nRow, "SORLT", DatasetUtility.getString(dsInput, 0, "PRESORLT")); // ���ְ��(�̿�������)
				dsZSDT0014.setColumn(nRow, "SORLT", dsZSDT1001.getColumn(nRow, "SORLT")); // ���ְ��

				// �������ְ�ȹ ��ȣ mapping
				dsZSDT1001.setColumn(nRow, "SONNRB", DatasetUtility.getString(dsInput, 0, "SONNR")); // �������ְ�ȹ��ȣ

				// �̵���ȹ��������
				String zclflg = "�̿�";
				dsZSDT1001.setColumn(nRow, "PGUBN", zclflg); // ��ȹ����
				// 0014�� ���� �ʵ�
				// dsZSDT0014.setColumn(nRow, "PGUBN", zclflg ); // ��ȹ����

				dsZSDT1001.setColumn(nRow, "SOPRICE", DatasetUtility.getDouble(dsInput2, i, "SOPRICE"));// ���ֱݾ�

				// ���ֱݾ�
				tempDbl = 0;
				if (DatasetUtility.getDouble(dsInput2, i, "SOPRICE") == 0) {
					dsZSDT0014.setColumn(nRow, "SOPRICE", 0);
				} else {
					// ��ȭ(WAERK)������ ���� ȯ��ݾ� ���
					if (    "KRW".equals(DatasetUtility.getString(dsInput2, i, "WAERK"))
						 || "JPY".equals(DatasetUtility.getString(dsInput2, i, "WAERK"))) {
						tempDbl = DatasetUtility.getDouble(dsInput, i, "SOPRICE") / 100.0;
					} else {
						tempDbl = DatasetUtility.getDouble(dsInput, i, "SOPRICE");
					}
					dsZSDT0014.setColumn(nRow, "SOPRICE", tempDbl + "");
				}

				dsZSDT1001.setColumn(nRow, "SHANG", DatasetUtility.getString(dsInput2, i, "SHANG")); // ��������
				dsZSDT0014.setColumn(nRow, "SHANG", dsZSDT1001.getColumn(nRow, "SHANG")); // ��������

				dsZSDT1001.setColumn(nRow, "VBELN", DatasetUtility.getString(dsInput2, i, "VBELN")); // ���ֹ�ȣ
				dsZSDT0014.setColumn(nRow, "VBELN", dsZSDT1001.getColumn(nRow, "VBELN")); // ���ֹ�ȣ

				dsZSDT0014.setColumn(nRow, "POSID", f_posid_make(sonnr, matnr)); // ȣ���ȣ
				dsZSDT0014.setColumn(nRow, "BIDDAT", biddat); // ����������
				dsZSDT0014.setColumn(nRow, "NAME1", DatasetUtility.getString(dsInput2, i, "KUNNR_NM"));// ����

				dsZSDT1001.setColumn(nRow, "ZBPNNR", DatasetUtility.getString(dsInput2, i, "ZBPNNR")); // �����ȹ��ȣ
				dsZSDT0014.setColumn(nRow, "ZBPNNR", dsZSDT1001.getColumn(nRow, "ZBPNNR")); // �����ȹ��ȣ

				dsZSDT1001.setColumn(nRow, "ZCOST", DatasetUtility.getString(dsInput2, i, "ZCOST")); // ����
				dsZSDT0014.setColumn(nRow, "ZCOST", dsZSDT1001.getColumn(nRow, "ZCOST")); // ����

				dsZSDT0014.setColumn(nRow, "ENTP3", "");

				nRow++;
			}// end for

			// ����/û��/���� ���� RFC
			listZSDT0014 = (ZSDT0014[]) SmpComC.moveDs2Obj(dsZSDT0014, ZSDT0014.class, "");
			listZSDT1001 = (ZSDT1001[]) SmpComC.moveDs2Obj(dsZSDT1001, ZSDT1001.class, "");

			// �������� RFC
			// ������ �¿��� ����. - ���δ� ����(2012.10.16)
			// listZCOBS001 = (ZCOBS001[])SmpComC.moveDs2Obj(dsZCOBS001,
			// ZCOBS001.class, "");
			// logger.info(" @@@@@@ save 2 : go ");
			// save2( paramVO, listZCOBS001, model );

			// ����Ʈ�� ���� ���¿��� addrow�� �����
			// ����/û��/���ݿ� ���� �����Ͱ� �������� �����Ƿ� �׿����� ó���� �Ѵ�.
			// RFC FUCNTION ȣ��
			obj = new Object[] {
					  "S" // ����/���� ����
					, "" // delete����(���ְ�ȹ���� ������)
					, "M" // ���/���� ����
					, "" // Ư���ʵ�(TYPE1,TYPE2,TYPE3,RTYPE,SOFOCA) ���汸��(���ְ�ȹ���� ������� ����)
					, listMsg
					, listZSDT0014
					, listZSDT1001 // ���ְ�ȹ
					, listZSDT1005
					, listZSDT0014S
					, listZSDT1012
					, listZSDT1023
					, listZSDS0072
			};

			// logger.info(" @@@@@@ SapFunction stub" );
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);

			listMsg = (ZQMSEMSG[]) stub.getOutput("O_ETAB");

			// �����޽����� return�� ���
			if (listMsg.length != 0)
			{
				if ("4".equals(listMsg[0].getIDX().toString()))
				{
					throw new BizException("CE00001");
				}
			}

			sbp0162D.updateSORLT(param);

			// 2012.11.13 ����ó�� �������¿� ���� ���������� ���ְ�ȹ��ȣ ����
			if ( param.getPRESORLT() != null &&
				 ( "20".equals(param.getPRESORLT()) || "50".equals(param.getPRESORLT()) )  )	{	// �����ۼ�, ��������
				param.setNEWSORLT(param.getPRESORLT());	// �̿�����
				sbp0162D.updateZSDRT1047Sonnr(param);
			}

			// 2012.11.16 RFCȣ�� �������� ����
			/*
			sbp0162D.insertSORLT1(param);
			sbp0162D.insertSORLT2(param);
			sbp0162D.insertSORLT3(param);
			sbp0162D.insertSORLT4(param);
			 */
		}catch (BizException be) {
			be.printStackTrace();
			throw be;
		} catch (Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * �����ȹ��ȣ, �����ȣ�� ȣ���ȣ make�Ͽ� return
	 *
	 * @param sonnr
	 * @param matnr
	 * @return
	 */
	private String f_posid_make(String sonnr, String matnr) {
		// ȣ���ȣ
		String posid = "";
		if ("NS-100".equals(matnr)) {
			posid = sonnr + "NS";
		} else {
			posid = sonnr + StringUtils.substring(matnr, 0, 1) + "01";
		}
		return posid;
	}
}
