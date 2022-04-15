package hdel.sd.sbp.service;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.com.service.Com0040S;
import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;
import hdel.sd.sbp.dao.Sbp0040D;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0040ParamVO;
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0040ParamVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.service.resource.TransactionManager;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * �����ȹ����(����)(Sbp0040S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List selectOpenDtm  	( �����ȹ(����) �����Ͻ�(14�ڸ�) ��ȸ )
 *		3.  List selectList 		( �����ȹ(����) ��� ��ȸ )
 *		4.  List selectListDetail	( �����ȹ��ȣ �� �ǿ� ��ϵ� �����ȹ(����) ����/û��/���ݸ�� ��ȸ )
 *		5.  void save 				( ���� ���� )
 *		6.  void bill_save 		( ����/û��/���� ���� )
 *
 * @since 1.0
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version
 * @version 1.0
 */


@Service
public class Sbp0040S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Com0040S 		BuyrService ;	// �ŷ�����ȸ ����

	private Sbp0040D sbp0040Dao;

	public void createDao(SqlSession sqlSession) {
		sbp0040Dao = sqlSession.getMapper(Sbp0040D.class);
	}

	// �����ȹ �����Ͻ�(14�ڸ�) ��ȸ
	public List<Sbp0040> selectOpenDtm(Sbp0040ParamVO param) {
		return sbp0040Dao.selectOpenDtm(param);
	}

	// �����ȹ ��� ��ȸ
	public List<Sbp0040> selectList(Sbp0040ParamVO param) {
		return sbp0040Dao.selectList(param);
	}

	// ����������Ʈ��ȣ�� ���ֿ����SUM
	public Double selectSumSofoca(Sbp0040ParamVO param) {
		return sbp0040Dao.selectSumSofoca(param);
	}

	// �����ȹ��ȣ�� �� �ǿ� ��ϵ� �����ȹ ����/û��/���ݸ�� ��ȸ
	public List<Sbp0040> selectListDetail(Sbp0040ParamVO param) {
		return sbp0040Dao.selectListDetail(param);
	}

	// �����ȹ ���ֿ����, ��ȭ ��ȸ : ����/û��/���� �ڵ����� ��� ��������
	public List<Sbp0040> selectListComputeItem(Sbp0040ParamVO param) {
		return sbp0040Dao.selectListComputeItem(param);
	}

	// �����ȹ_���� ����
	public void save( Dataset 	ds_detail  		// �����ȹ��
					, Dataset 	ds_list_bill  // ����/û��/���� ���� �������(����ݾ�)
					, Dataset 	ds_yearm  		// ����/û��/���� ���� �������(30����ġ ������)
					, String 	flag			// ���屸��(I,U,D)
					, String	sysid
					, String 	mandt			// CLIENT
					, String 	user_id			// �����ID
					, String 	zbpnnr			// �����ȹ��ȣ
					, String	pspidsv			// ����������Ʈ��ȣ
					, String 	call_sap_yn		// ����/û��/���� ���� SAP�Լ� ȣ�⿩��(SAP��)
					, String    auto_compute_yn	// ����/û��/���� �ڵ����� ��󿩺�(WEB��)
					, String 	posid			// ȣ��
					)
	{

		logger.debug("@@@ Sbp0040S.save START!!!" + "");

		String ErrCode = "CE00001";  // "ó���� �����߽��ϴ�.Ȯ�� �� �ٽ� ó���� �ֽʽÿ�."

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (���� : 310 or 910)
		logger.debug("@@@ mandt 	: " + mandt);
		logger.debug("@@@ user_id 	: " + user_id);
		logger.debug("@@@ ds_detail.getRowCount   ===>"+ds_detail.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_detail.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_detail.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_detail["+irow+"Record : "+ds_detail.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_detail, irow, ds_detail.getColumnID(icol)));
			}
		}
		logger.debug("@@@ ds_yearm.getRowCount ===>"+ds_yearm.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_yearm.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_yearm.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_yearm["+irow+"Record : "+ds_yearm.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_yearm, irow, ds_yearm.getColumnID(icol)));
			}
		}
		logger.debug("@@@ ds_list_bill.getRowCount ===>"+ds_list_bill.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_list_bill.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_list_bill.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_list_bill["+irow+"Record : "+ds_list_bill.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_list_bill, irow, ds_list_bill.getColumnID(icol)));
			}
		}
		//--------------------------------------------------------------------------------------------

		logger.debug("@@@ Sbp0040SaveView start" + "");

		//------------------------------------------------------------------------
		// ���� ����
		//------------------------------------------------------------------------
		Sbp0040[] 	param 		= null;						// ���� ���� �Ķ����
		Sbp0040 	paramDetail = null;						// ����/û��/���� ���� �Ķ����
		String 		auart		= "";						// �ǸŹ�������(��������)
		String		zkunnr		= "";						// ������ڵ�
		String		kunnr		= "";						// �ŷ����ڵ�
		String		zpyear		= "";						// �����Ͻ�
		String 		gbn 		= "";						// ����/û��/���� ����	(1:����, 2:û��, 3:����)
		BigDecimal 	amt 		= null;						// ����/û��/���� �ݾ�
		BigDecimal 	zero 		= new BigDecimal("0.00");	// 0.00
		String 		bill_save_yn= "Y"; 						// ����/û��/���� �ڷ� ����SKIP����

		//------------------------------------------------------------------------
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(sysid);
		//------------------------------------------------------------------------

		//------------------------------------------------------------------------
		// DAO ����
		//------------------------------------------------------------------------
		createDao(session);

		//------------------------------------------------------------------------
		// �����Ͻ� ���翩�� üũ (�����Ͻ� ������� ����Ұ�ó��)
		//------------------------------------------------------------------------
		zpyear	= DatasetUtility.getString(ds_detail, 0, "ZPYEAR");	// ���⵵
		if (chkOpenDtm(session, mandt, zpyear)==false)
		{
			// "CE10002", "���µ��� ���� ���⵵�� �����ȹ�Դϴ�.\n�ٽ� �ѹ� Ȯ�� �ٶ��ϴ�."
			throw new BizException("CE10002");
		}

		//------------------------------------------------------------------------
		// �����ȹ��ȣ�� ó�� START
		//------------------------------------------------------------------------
		for( int irow = 0; irow < ds_detail.getRowCount(); irow++ )
		{

			ds_detail.setColumn(irow, "USER_ID"	,user_id); 	// WEB USER ID
			for( int icol = 0; icol < ds_detail.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_detail["+irow+"Record : "
									+ ds_detail.getColumnID(icol)+"] ===>"
									+ DatasetUtility.getString(ds_detail, irow, ds_detail.getColumnID(icol))
									);
			}

			auart		= DatasetUtility.getString(ds_detail, irow, "AUART");		// �ǸŹ�������(��������)
			kunnr		= DatasetUtility.getString(ds_detail, irow, "KUNNR");		// �ŷ����ڵ�
			bill_save_yn= DatasetUtility.getString(ds_detail, irow, "BILL_SAVE_YN");// ����/û��/���� �ڷ� ���忩��

			logger.debug("@@@ bill_save_yn : " + bill_save_yn);

			//------------------------------------------------------------------------
			// �ű� �����ȹ�� ���
			//------------------------------------------------------------------------
			if ("I".equals(flag))
			{

				// 0.�ŷ����ڵ� ��ȿ�� üũ
				if (chkBuyrCd(session, mandt, kunnr)==false)
				{
					// "CE10001", "�ŷ����ڵ尡 ��ȿ���� �ʽ��ϴ�.\n�ٽ� �ѹ� Ȯ�� �ٶ��ϴ�."
					throw new BizException("CE10001");
				}

				// 1.�����ȹ��ȣ GET (��ȹ���к�(�����ȹ:0), �ǸŹ��������� ä��)
				logger.debug("@@@ 1.�����ȹ��ȣ GET ");
				logger.debug("@@@ getNewZbpnnr.zbpnnr : " + zbpnnr + "");

				// 2.�����ȹ��ȣ SET
				logger.debug("@@@ 2.�����ȹ��ȣ SET ");
				ds_detail.setColumn(irow, "ZBPNNR"	, zbpnnr);

				// 3.���� Input Dataset(ds_detail) --> class(Sbp0040)�� copy
				logger.debug("@@@ 3.���� Input Dataset(ds_detail) --> class(Sbp0040)�� copy  ");
				param = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);

				// 4.���ֵ�� ����CALL
				logger.debug("@@@ 4.���ֵ�� ����CALL  ");
				sbp0040Dao.insertZSDT1023(param[0]);

				// 5. SAP�� ���ְ�ȹ ���(ZSDT1012==>ZSDT0014S )
				logger.debug("@@@ 5. SAP�� ���ְ�ȹ ���(ZSDT1012==>ZSDT0014S )");
				save_0014S( session
					        , flag				// ���屸��
							, ds_detail			// ��������
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, zbpnnr 			// �����ȹ��ȣ
							, posid				// ȣ��
							);

				// 6.����/û��/���ݵ�� ����CALL
				logger.debug("@@@  6.����/û��/���ݵ�� ����CALL   ");
				bill_save(    ds_detail			// �����ȹ����
							, ds_list_bill	// ����/û��/���� ����ݾ�
							, ds_yearm			// ����/û��/���� ������
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, auto_compute_yn	// �ڵ����� ��󿩺� ��ȸ
							);

				// 7. �ű� �����ȹ�� ��� �����ȹ������´�  SAP FUNCTION ȣ�� �� ������(SAVE_STAT=Z)
				logger.debug("@@@ 7. �ű� �����ȹ�� ��� �����ȹ������´�  SAP FUNCTION ȣ�� �� ������(SAVE_STAT=Z)  ");

			}

			//------------------------------------------------------------------------
			// ������ ���
			//------------------------------------------------------------------------
			else if ("U".equals(DatasetUtility.getString(ds_detail, irow, "FLAG")))
			{

				// 0.�ŷ����ڵ� ��ȿ�� üũ
				if (chkBuyrCd(session, mandt, kunnr)==false)
				{
					// "CE10001", "�ŷ����ڵ尡 ��ȿ���� �ʽ��ϴ�.\n�ٽ� �ѹ� Ȯ�� �ٶ��ϴ�."
					throw new BizException("CE10001");
				}

				// 1.�����ȹ��ȣ GET
				logger.debug("@@@ 1.�����ȹ��ȣ GET ");
				zbpnnr = DatasetUtility.getString(ds_detail, irow, "ZBPNNR");

				// 2.���� Input Dataset(ds_detail) --> class(Sbp0040)�� copy
				logger.debug("@@@ 2.���� Input Dataset(ds_detail) --> class(Sbp0040)�� copy ");
				param  = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);

				// 3.���ֺ��� ����CALL
				logger.debug("@@@ 3.���ֺ��� ����CALL ");
				sbp0040Dao.updateZSDT1023(param[0]);

				// 4. SAP�� ���ְ�ȹ ����(ZSDT1023==>ZSDT0014S )
				logger.debug("@@@ 4. SAP�� ���ְ�ȹ ����(ZSDT1023==>ZSDT0014S )");
				save_0014S(    session
					        , flag				// ���屸��
							, ds_detail			// ��������
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, zbpnnr 			// �����ȹ��ȣ
							, posid				// ȣ��
							);

				// 5. ����/û��/���ݵ�� ����CALL
				logger.debug("@@@ 5.4 ����/û��/���ݵ�� ����CALL ");
				bill_save(    ds_detail			// �����ȹ����
							, ds_list_bill		// ����/û��/���� ����ݾ�
							, ds_yearm			// ����/û��/���� ������
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, auto_compute_yn	// �ڵ����� ��󿩺� ��ȸ
							);

				// 6. ����/û��/���� ���� ����� �ƴ� ��� �����ȹ���� ���� ó��(SAVE_STAT=Z)
				if ("N".equals(call_sap_yn))
				{
					logger.debug("@@@ 6. ����/û��/���� ���� ����� �ƴ� ��� �����ȹ���� ���� ó��(SAVE_STAT=Z)");
					updateSaveStat(mandt,zbpnnr,user_id, "Z");   // ��������ó��
				}

			}

			//------------------------------------------------------------------------
			// ������ ���
			//------------------------------------------------------------------------
			else if ("D".equals(DatasetUtility.getString(ds_detail, irow, "FLAG")))
			{

				// 1.�����ȹ��ȣ GET
				logger.debug("@@@ 1.�����ȹ��ȣ GET ");
				zbpnnr = DatasetUtility.getString(ds_detail, irow, "ZBPNNR");

				// 2.���� Input Dataset(ds_detail) --> class(Sbp0040)�� copy
				logger.debug("@@@ 2.���� Input Dataset(ds_detail) --> class(Sbp0040)�� copy ");
				param = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);

				// 3.���ֻ��� ����CALL : Update ��������
				logger.debug("@@@ 3.���ֻ��� ����CALL : Update �������� ");
				sbp0040Dao.deleteZSDT1023(param[0]);

				// 4. SAP�� ���ֻ��� ����CALL : Update ��������
				logger.debug("@@@ 4. SAP�� ���ֻ��� ����CALL : Update �������� ");
				sbp0040Dao.deleteZSDT0014S(param[0]);

				// 5. �����ȹ���� ���� ó��(SAVE_STAT=Z)
				logger.debug("@@@ 5. �����ȹ���� ���� ó��(SAVE_STAT=Z)");
				updateSaveStat(mandt,zbpnnr,user_id, "Z");   // ��������ó��

			}  // end of else if ("D".equals(DatasetUtility.getString(ds_detail, irow, "FLAG")))


		} // end of for

		logger.debug("@@@ Sbp0040S.save SUCCESS!!!" + "");

	}

	// ����/û��/���� ���
	public void bill_save(   Dataset ds_detail			// �����ȹ����
							,Dataset ds_list_bill		// ȭ�鿡�� �Է¹��� ����/û��/����  ����
							,Dataset ds_yearm			// ������30����
							,String  mandt				// CLIENT
							,String  user_id 			// WEB USER_ID
							,String  auto_compute_yn	// �ڵ����� ��󿩺� ��ȸ
							)
	{

		logger.debug("@@@ Sbp0040SaveView.bill_save START!!!" + "");

		// 1.���� Input Dataset(ds_detail) --> class(Sbp0040)�� copy
		logger.debug("@@@ 1. ���� Input Dataset(ds_detail) --> class(Sbp0040)�� copy ");
		Sbp0040[] param = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);

		// 2.������� ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
		logger.debug("@@@ 2. ������� ����CALL  (�����ȹ��ȣ �ѰǴ� ����) ");
		sbp0040Dao.deleteZSDT1024(param[0]);

		// 3.û������ ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
		logger.debug("@@@ 3. û������ ����CALL  (�����ȹ��ȣ �ѰǴ� ����) ");
		sbp0040Dao.deleteZSDT1025(param[0]);

		// 4.���ݻ��� ����CALL  (�����ȹ��ȣ �ѰǴ� ����)
		logger.debug("@@@ 4. ���ݻ��� ����CALL  (�����ȹ��ȣ �ѰǴ� ����) ");
		sbp0040Dao.deleteZSDT1026(param[0]);

		// 5.����/û��/���� ���
		logger.debug("@@@ 5.����/û��/���� ��� ");
		String  flag	= ds_detail.getColumnAsString(0, "FLAG");		// FLAG
		String  zbpnnr 	= ds_detail.getColumnAsString(0, "ZBPNNR");		// �����ȹ��ȣ
		String  pspidsv	= ds_detail.getColumnAsString(0, "PSPIDSV");	// ����������Ʈ��ȣ
		String  auart	= ds_detail.getColumnAsString(0, "AUART");		// ��������(�ǸŹ�������)
		String  zsvcgbn	= ds_detail.getColumnAsString(0, "ZSVCGBN");	// ��������
		Double  sofoca  = ds_detail.getColumnAsDouble(0, "SOFOCA");		// ���ֿ����

		logger.debug("@@@ Sbp0040SaveView.bill_save.auto_compute_yn	[" + auto_compute_yn +  "] ");

		// �ڵ����� ����� ���
		if ("Y".equals(auto_compute_yn))
		{

			logger.debug("@@@ Sbp0040SaveView.bill_save.�ڵ����� ����� ��� ");

			// ���⺸��(��������=="ZR11)�� ���
			if ("ZR11".equals(auart))
			{
				logger.debug("@@@ Sbp0040SaveView.bill_save.���⺸��(��������==ZR11)�� ��� ");

				// ����������Ʈ��ȣ�� �ִ� ��� (�������� IN (20:����, 30:ȸ��))
				if ("20".equals(zsvcgbn) || "30".equals(zsvcgbn))
				{
					logger.debug("@@@ Sbp0040SaveView.bill_save.����������Ʈ��ȣ�� �ִ� ��� (�������� IN (20:����, 20:ȸ��)) ");
					// ����������Ʈ��ȣ�� ��ϵ� SUM(���ֿ����)�� ��ȸ�Ͽ� SUM�� ���� 1/12 �����Ͽ�
					// ����������Ʈ������ ����/û��/������ ����Ѵ�.
					Sbp0040ParamVO	 paramSum  = new Sbp0040ParamVO();
					paramSum.setMANDT	(mandt);  					// SAP CLIENT
					paramSum.setPSPIDSV (pspidsv);					// ����������Ʈ��ȣ
					sofoca = sbp0040Dao.selectSumSofoca(paramSum);  // ���ֿ����SUM ��ȸ
					zbpnnr = pspidsv;								// �����ȹ��ȣ = ����������Ʈ��ȣ�� SETTING
				}

				// ���ֿ���ݾ��� ��ȹ���~��ȹ���+11�� ���� 1/12 �Ͽ� ����/û��/������ ����Ѵ�
				logger.debug("@@@ Sbp0040SaveView.bill_save.���ֿ���ݾ��� ��ȹ���~��ȹ���+11�� ���� 1/12 �Ͽ� ����/û��/������ ����Ѵ� ");
				bill_save_12month(    ds_yearm			// ������30����
									, mandt				// CLIENT
									, user_id 			// WEB USER_ID
									, zbpnnr			// �����ȹ��ȣ
									, sofoca			// ���ֿ����
									);
			}
			else
			{
				// �����ȹ��ȣ��  ���ֿ���ݾ��� ��ȹ���~��ȹ���+2�� ���� 30%,30%,40%�� �����Ͽ� ����/û��/������ ����Ѵ�.
				bill_save_3month(    ds_yearm			// ������30����
									, mandt				// CLIENT
									, user_id 			// WEB USER_ID
									, zbpnnr			// �����ȹ��ȣ
									, sofoca			// ���ֿ����
									);
			}
		}
		else
		{
			// ȭ�鿡�� �Է¹��� ������ ����/û��/���� ��� ���� ȣ��
			bill_save_input(  ds_list_bill		// ����/��������
							, ds_yearm			// ������30����
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, zbpnnr			// �����ȹ��ȣ
							);
		}


		logger.debug("@@@ Sbp0040SaveView.bill_save SUCCESS !!!" + "");

		/*  �Ʒ� �ҽ� ���� ó���� : ���� ��� ����
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
		Calendar date = Calendar.getInstance();
		date.set(2013, 11, 02);
		date.add(Calendar.MONTH, +2);
		logger.debug("@@@ sdf.format(date.getTime()) : " + sdf.format(date.getTime()) );
		*/

	}

	// ����/û��/������ 3������ �����Ͽ� ���
	private void bill_save_3month(   Dataset ds_yearm			// ������30����
									,String  mandt				// CLIENT
									,String  user_id 			// WEB USER_ID
									,String  zbpnnr				// �����ȹ��ȣ
									,Double  sofoca				// ���ֿ����
									)
	{

		logger.debug("@@@ bill_save_3month START ");

		// 3 ������ ����(30%, 30%, 40%) �ϰ� ���������� �����Ѵ�.
		Sbp0040	 	paramDetail  = new Sbp0040();
		BigDecimal 	sofoca_div1  = new BigDecimal((sofoca*3)/10);	// ���� �� �ݾ�(��ȹ��� + 0����)
		BigDecimal 	sofoca_div2  = new BigDecimal((sofoca*3)/10);	// ���� �� �ݾ�(��ȹ��� + 1����)
		BigDecimal 	sofoca_div3  = new BigDecimal((sofoca*4)/10);	// ���� �� �ݾ�(��ȹ��� + 2����)

		sofoca_div1	= sofoca_div1.setScale(0, RoundingMode.DOWN);	// �ݾ��� ���������� ����
		sofoca_div2	= sofoca_div2.setScale(0, RoundingMode.DOWN);	// �ݾ��� ���������� ����
		sofoca_div3	= sofoca_div3.setScale(0, RoundingMode.DOWN);	// �ݾ��� ���������� ����

		// ���� = ���ֿ���ݾ�-(�����ݾ�*12)
		BigDecimal diff = new BigDecimal(0);
		diff = new BigDecimal(sofoca - (sofoca_div1.doubleValue()
										+ sofoca_div2.doubleValue()
										+ sofoca_div3.doubleValue()));

		logger.debug("@@@ paramDetail.sofoca_div1 [" + sofoca_div1 +  "] ");
		logger.debug("@@@ paramDetail.sofoca_div2 [" + sofoca_div2 +  "] ");
		logger.debug("@@@ paramDetail.sofoca_div3 [" + sofoca_div3 +  "] ");
		logger.debug("@@@ paramDetail.diff 	      [" + diff       +  "] ");

		for( int i = 0; i <= 2; i++ )
		{
			paramDetail.setMANDT	(mandt);  									// SAP CLIENT
			paramDetail.setUSER_ID	(user_id);  								// WEB USER_ID
			paramDetail.setZBPNNR	(zbpnnr);									// �����ȹ��ȣ
			paramDetail.setZSAYM	(ds_yearm.getColumnAsString(i, "YEARM"));	// ������
			// ����ݾ�
			if (i==0) {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div1.doubleValue()));
			} else if (i==1) {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div2.doubleValue()));
			} else if (i==2) {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div3.doubleValue() + diff.doubleValue()));
			}

			logger.debug("@@@ paramDetail.i 	[" + i 		+  "] ");
			logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
			logger.debug("@@@ paramDetail.user_id 	[" + user_id 	+  "] ");
			logger.debug("@@@ paramDetail.zbpnnr 	[" + zbpnnr 	+  "] ");
			logger.debug("@@@ paramDetail.yearm 	[" + paramDetail.getZSAYM()+  "] ");
			logger.debug("@@@ paramDetail.amt 		[" + paramDetail.getNETWR_SA()	+  "] ");

			// ��ϼ��� CALL
			sbp0040Dao.insertZSDT1024(paramDetail);  // ������ ����CALL
			sbp0040Dao.insertZSDT1025(paramDetail);  // û����� ����CALL
			sbp0040Dao.insertZSDT1026(paramDetail);  // ���ݵ�� ����CALL
		}

		logger.debug("@@@ bill_save_3month SUCCESS ");

	}

	// ����/û��/������ 12������ �����Ͽ� ���
	private void bill_save_12month(  Dataset ds_yearm			// ������30����
									,String  mandt				// CLIENT
									,String  user_id 			// WEB USER_ID
									,String  zbpnnr				// �����ȹ��ȣ
									,Double  sofoca				// ���ֿ����
									)
	{

		logger.debug("@@@ bill_save_12month START ");

		// 12 ������ ����(1/n)�ϰ� ���������� �����Ѵ�.
		Sbp0040	 	paramDetail  = new Sbp0040();
		BigDecimal 	sofoca_div  = new BigDecimal(sofoca/12);		// ���� �� �ݾ�
		sofoca_div	= sofoca_div.setScale(0, RoundingMode.DOWN);	// �ݾ��� ���������� ����

		// ���� = ���ֿ���ݾ�-(�����ݾ�*12)
		BigDecimal diff = new BigDecimal(0);
		diff = new BigDecimal(sofoca - (sofoca_div.doubleValue() * 12));
		int row = 0;

		for( int i = 0; i <= 11; i++ )
		{

			logger.debug("@@@ paramDetail.sofoca_div 	[" + sofoca_div +  "] ");
			logger.debug("@@@ paramDetail.diff 	[" + diff +  "] ");

		    paramDetail.setMANDT	(mandt);  									// SAP CLIENT
			paramDetail.setUSER_ID	(user_id);  								// WEB USER_ID
			paramDetail.setZBPNNR	(zbpnnr);									// �����ȹ��ȣ
			paramDetail.setZSAYM	(ds_yearm.getColumnAsString(i, "YEARM"));	// ������
			// ����ݾ�
			if (i == 11) {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div.doubleValue() + diff.doubleValue()));
			} else {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div.doubleValue()));
			}

			logger.debug("@@@ paramDetail.i 	[" + i 		+  "] ");
			logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
			logger.debug("@@@ paramDetail.user_id 	[" + user_id 	+  "] ");
			logger.debug("@@@ paramDetail.zbpnnr 	[" + zbpnnr 	+  "] ");
			logger.debug("@@@ paramDetail.yearm 	[" + ds_yearm.getColumnAsString(i, "YEARM") 		+  "] ");
			logger.debug("@@@ paramDetail.amt 		[" + sofoca_div.doubleValue() + diff.doubleValue() 		+  "] ");

			// ��ϼ��� CALL
			sbp0040Dao.insertZSDT1024(paramDetail);  // ������ ����CALL
			sbp0040Dao.insertZSDT1025(paramDetail);  // û����� ����CALL
			sbp0040Dao.insertZSDT1026(paramDetail);  // ���ݵ�� ����CALL
    	}

		logger.debug("@@@ bill_save_12month SUCCESS ");

	}

	// ����/û��/������ ȭ�鿡�� �Է¹��� ������ ���
	private void bill_save_input(  Dataset ds_list_bill		// ����/��������
									, Dataset ds_yearm		// ������30����
									,String  mandt			// CLIENT
									,String  user_id 		// WEB USER_ID
									,String  zbpnnr			// �����ȹ��ȣ
									)
	{

		logger.debug("@@@ bill_save_input START ");

		Sbp0040 	paramDetail = paramDetail = new Sbp0040(); 	// ����/û��/����
		String 		gbn 		= null;							// ����/û��/���� ����	(1:����, 2:û��, 3:����)
		BigDecimal 	amt 		= null;							// ����/û��/���� �ݾ�
		BigDecimal 	zero 		= new BigDecimal("0.00");		// 0.00
		String      colId       = null;
		String      yearm 		= null;
		int 		row 		= 0;

		for (int iRow1=0;iRow1<ds_list_bill.getRowCount(); iRow1++ )
		{
			gbn = ds_list_bill.getColumnAsString(iRow1, "GBN"); 	// ����/û��/���� ����

			for (int iCol1=0;iCol1<ds_list_bill.getColumnCount(); iCol1++ )
			{

				colId = ds_list_bill.getColumnID(iCol1);

				if (!("MANDT".equals(colId)) && !("GBN".equals(colId)) && !("GBN_NM".equals(colId)))
				{

					// �ݾ��׸� ���� �ִ� ��쿡�� ����
					if (StringUtils.isNotBlank(ds_list_bill.getColumnAsString(iRow1, iCol1)))
					{
						// ����ݾ�
						amt = new BigDecimal(ds_list_bill.getColumnAsDouble(iRow1, iCol1));

						// �ݾ� > 0 �� ��츸 ���
						if( amt.compareTo(zero) == 1 )  // compareTo :::: -1�� �۴�, 0�� ����, 1�� ũ��
						{
							row = Integer.parseInt(colId.substring(3,5));
							yearm = ds_yearm.getColumnAsString(row, "YEARM");
							logger.debug("@@@ row   : " + row);
							logger.debug("@@@ yearm : " + yearm);

							paramDetail.setMANDT	(mandt);  		// SAP CLIENT
							paramDetail.setUSER_ID	(user_id);  	// WEB USER_ID
							paramDetail.setZBPNNR	(zbpnnr);		// �����ȹ��ȣ
							paramDetail.setZSAYM	(yearm);		// ������
							paramDetail.setNETWR_SA (amt);			// ����ݾ�

							logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
							logger.debug("@@@ paramDetail.user_id   [" + user_id 	+  "] ");
							logger.debug("@@@ paramDetail.zbpnnr 	[" + zbpnnr 	+  "] ");
							logger.debug("@@@ paramDetail.yearm 	[" + yearm 		+  "] ");
							logger.debug("@@@ paramDetail.amt 	    [" + amt 		+  "] ");

							// ��ϼ��� CALL (����,û���� ���� ������ ����Ѵ�.)
							if 		 ("1".equals(gbn)) {
								sbp0040Dao.insertZSDT1024(paramDetail);  // ������ ����CALL
								sbp0040Dao.insertZSDT1025(paramDetail);  // û����� ����CALL
							}else if ("3".equals(gbn)) {
								sbp0040Dao.insertZSDT1026(paramDetail);  // ���ݵ�� ����CALL
							}
						}  	// end of if( amt.compareTo(zero) == 1 )
					}		// end of if (StringUtils.isNotBlank(ds_list_bill.getColumnAsString(iRow1, iCol1)))
				}			//
			}  				// end of for (int iCol1=2;iCol1<=ds_list_bill.getColumnCount(); iCol1++ )
		}					// end of for (int iRow1=0;iRow1<=ds_list_bill.getRowCount(); iRow1++ )
		logger.debug("@@@ bill_save_input SUCCESS ");

	}


	// �����Ͻ� ���翩�� üũ
	public Boolean chkOpenDtm(SqlSession session
					            , String mandt
								, String zpyear		// ���⵵
								)
	{


		logger.debug("@@@ Sbp0040SaveView.chkOpenDtm START!!!" 	+ "");
		logger.debug("@@@ Sbp0040SaveView.chkOpenDtm.mandt" 		+ mandt + "");
		logger.debug("@@@ Sbp0040SaveView.chkOpenDtm.zpyear" 		+ zpyear + "");

		Sbp0040ParamVO param = new Sbp0040ParamVO();

		// �Ķ����SET
		param.setMANDT	(mandt);  	// SAP CLIENT
		param.setZPYEAR	(zpyear);	// ���⵵

		// ����CALL (�����ȹ�⵵�� �ش��ϴ� �������� ��ȸ)
		List<Sbp0040> list = selectOpenDtm(param);

		// ��ȸ�� �����Ͻ� ������ ���� ��� ������ �����Ѵ�.(����Ұ�ó��)
		if (list.size()==0) return false;

		return true;
	}

	// �ŷ����ڵ� ��ȿ�� üũ
	public Boolean chkBuyrCd(SqlSession session
			                    , String mandt
								, String kunnr		// �ŷ����ڵ�
								)
	{

		logger.debug("@@@ sbp0040SaveView.chkBuyrCd START!!!" 	+ "");
		logger.debug("@@@ sbp0040SaveView.chkBuyrCd.mandt" 		+ mandt + "");
		logger.debug("@@@ sbp0040SaveView.chkBuyrCd.kunnr" 		+ kunnr + "");

		Com0040ParamVO param = new Com0040ParamVO();

		// DAO����
		BuyrService.createDao(session);

		// Parameter SET
		param.setMandt		(mandt);  	// SAP CLIENT
		param.setBuyr_cd	(kunnr);	// �ŷ����ڵ�

		// �ŷ����� ��ȸ
		List<Com0040> list = BuyrService.find(param);

		logger.debug("@@@ sbp0040SaveView.chkBuyrCd SUCCESS !!!" + "");

		// ��ȸ�� �ŷ��������� ���� ��� ������ �����Ѵ�.(����Ұ�ó��)
		if (list.size()==0) return false;

		return true;
	}



	// SAP�� �����ȹ(ZSDT0014S) ���̺� �����ȹ ���(I) �� ����(U)
	public void save_0014S(SqlSession session
			                    ,String flag		// ���屸�� (I:�ű�, U:����)
								,Dataset ds_detail	// ����/û��/���� �ڵ�������
								,String mandt		// CLIENT
								,String user_id 	// WEB USER_ID
								,String zbpnnr 		// �����ȹ��ȣ
								,String posid		// ȣ��
								)
	{

		logger.debug("@@@ sbp0040SaveView.save_0014S START!!!" + "");

		// ���� ���� �Ķ����
		Sbp0040 	param 	= new Sbp0040();

		// ���⺯��
		String 		waerk	= DatasetUtility.getString(ds_detail, 0, "WAERK");				// ��ȭ
		String 		kunnr	= DatasetUtility.getString(ds_detail, 0, "KUNNR");				// �ŷ����ڵ�
		String 		matnr   = DatasetUtility.getString(ds_detail, 0, "MATNR");				// �����ȣ
		String		biddat  = DatasetUtility.getString(ds_detail, 0, "ZPYM") + "01";
		BigDecimal 	zfore	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "ZFORE"));	// ���������
		BigDecimal 	sofoca  = new BigDecimal(0);

		// ��ȭ�� ��ȭ�� ��� 100���� ������ ���ֿ���ݾ��� �����Ѵ�.
		if ("KRW".equals(waerk) ||"JPY".equals(waerk ))
		{
			sofoca	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "SOFOCA")/100);// ���ֿ���ݾ�
		}
		else
		{
			sofoca	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "SOFOCA"));// ���ֿ���ݾ�
		}

		sofoca = sofoca.setScale(2, RoundingMode.DOWN);  // �Ҽ� ��°�ڸ����� �ݿø�

		// �ŷ����� ��ȸ
		Com0040ParamVO com_param = new Com0040ParamVO();
		BuyrService.createDao(session);
		com_param.setMandt	(mandt);  	// SAP CLIENT
		com_param.setBuyr_cd(kunnr);	// �ŷ����ڵ�
		List<Com0040> list = BuyrService.find(com_param);
		String kunnr_nm = list.get(0).getBuyr_nm();	// �ŷ�����

		logger.debug("@@@ sbp0040SaveView.save_0014S.flag : " + flag);
		logger.debug("@@@ sbp0040SaveView.save_0014S.biddat : " + biddat);
		logger.debug("@@@ sbp0040SaveView.save_0014S.sofoca : " + sofoca);
		logger.debug("@@@ sbp0040SaveView.save_0014S.zfore : " + zfore);
		logger.debug("@@@ sbp0040SaveView.save_0014S.matnr : " + matnr);
		logger.debug("@@@ sbp0040SaveView.save_0014S.posid : " + posid);
		logger.debug("@@@ sbp0040SaveView.save_0014S.kunnr_nm : " + kunnr_nm);

		// ����� �Ķ���� SETTING
		param.setMANDT    	( DatasetUtility.getString(ds_detail, 0, "MANDT"	)); // CLIENT
		param.setZBPNNR   	( zbpnnr											) ;	// �����ȹ��ȣ
		param.setPSPID    	( zbpnnr											);  // ������Ʈ
		param.setPOSID    	( posid												) ; // ȣ��
		param.setZPYM    	( DatasetUtility.getString(ds_detail, 0, "ZPYM"		)); // ��ȹ���(�������)
		param.setAUART    	( DatasetUtility.getString(ds_detail, 0, "AUART"	)); // ��������
		param.setSPART    	( DatasetUtility.getString(ds_detail, 0, "SPART"	)); // ��ǰ��
		param.setMATNR    	( matnr												) ; // �����ȣ
		param.setVKBUR    	( DatasetUtility.getString(ds_detail, 0, "VKBUR"	)); // �μ��ڵ�
		param.setVKGRP    	( DatasetUtility.getString(ds_detail, 0, "VKGRP"	)); // ���ڵ�
		param.setZKUNNR   	( DatasetUtility.getString(ds_detail, 0, "ZKUNNR"	)); // ������ڵ�
		param.setBIDDAT   	( biddat											) ; // ��������
		param.setKUNNR    	( DatasetUtility.getString(ds_detail, 0, "KUNNR"	)); // ����ȣ
		param.setKUNNR_NM	( kunnr_nm											) ; // ����
		param.setGSNAM    	( DatasetUtility.getString(ds_detail, 0, "GSNAM"	)); // �����
		param.setREGION   	( DatasetUtility.getString(ds_detail, 0, "REGION"	)); // ��ġ����
		param.setGTYPE    	( DatasetUtility.getString(ds_detail, 0, "GTYPE"	)); // ����
		param.setTYPE1    	( ""												);  // TYPE1
		param.setTYPE2    	( ""												);  // TYPE2
		param.setTYPE3    	( ""												);  // TYPE3
		param.setZNUMBER  	( DatasetUtility.getInt	  (ds_detail, 0, "ZNUMBER"	)); // ���
		param.setZDELI    	( DatasetUtility.getString(ds_detail, 0, "ZDELI"	)); // �ܳ��ⱸ��
		param.setSHANGH   	( "����"												);  // ����/���ر���
		param.setZINTER   	( ""												);  // �߰蹫������
		param.setSOFOCA   	( sofoca											);	// ���ֿ����
		param.setZFORE    	( zfore												);	// ���������
		param.setWAERK    	( DatasetUtility.getString(ds_detail, 0, "WAERK"	)); // ��ȭ
		param.setDELDAT   	( DatasetUtility.getString(ds_detail, 0, "DELDAT"	)); // ���⿹����
		//param.setVBELN    ( DatasetUtility.getString(ds_detail, 0, "VBELN"	)); // �ǸŹ���
		param.setUSER_ID   	( DatasetUtility.getString(ds_detail, 0, "USER_ID"	)); // ó����ID

		// �ű� ���
		if ("I".equals(flag))
		{
			// SAP�� �����ȹ ���
			sbp0040Dao.insertZSDT0014S(param);
		}
		// ����
		else if ("U".equals(flag))
		{
			// SAP�� �����ȹ ����
			sbp0040Dao.updateZSDT0014S(param);
		}

		logger.debug("@@@ bill_save_auto.save_0014S SUCCESS !!!" + "");

	}

	// ����/û��/���� �ڵ������� ���
	public void bill_save_auto_sap(Dataset ds_compute_out	// ����/û��/���� �ڵ�������
									,String mandt			// CLIENT
									,String user_id 		// WEB USER_ID
									,String zbpnnr 			// �����ȹ��ȣ
									)
	{

		logger.debug("@@@ sbp0040SaveView.bill_save_auto_sap START!!!" + "");

		// �����ȹ ������� ���� (SAVE_STAT : Z)
		logger.debug("@@@ Sbp0040SaveView.bill_save_auto_sap.updateSaveStat  ");
		updateSaveStat(mandt, zbpnnr, user_id, "Z");

		logger.debug("@@@ bill_save_auto.bill_save_auto_sap SUCCESS !!!" + "");

		/*
		Sbp0040 	paramDetail = null;						// ����/û��/����
		String 		gbn 		= null;						// ����/û��/���� ����	(1:����, 2:û��, 3:����)
		BigDecimal 	amt 		= null;						// ����/û��/���� �ݾ�
		BigDecimal 	zero 		= new BigDecimal("0.00");	// 0.00
		String      yearm 		= null;

		for (int i=0;i<ds_compute_out.getRowCount(); i++ )
		{
			//��ȹ���� (MC : ����, CH : û��, SG : ����)
			gbn = ds_compute_out.getColumnAsString(i, "PLANTP");

			// �ݾ��׸� ���� �ִ� ��쿡�� ����
			if (StringUtils.isNotBlank(ds_compute_out.getColumnAsString(i, "PLANAMT")))
			{
				// ����ݾ�
				amt = new BigDecimal(ds_compute_out.getColumnAsDouble(i, "PLANAMT"));

				// �ݾ� > 0 �� ��츸 ���
				if( amt.compareTo(zero) == 1 )  // compareTo :::: -1�� �۴�, 0�� ����, 1�� ũ��
				{
					// ��ȹ���
					yearm = ds_compute_out.getColumnAsString(i, "PLANYM");

					paramDetail = new Sbp0040();
				    paramDetail.setMANDT	(mandt);  		// SAP CLIENT
					paramDetail.setUSER_ID	(user_id);  	// WEB USER_ID
					paramDetail.setZBPNNR	(zbpnnr);		// �����ȹ��ȣ
					paramDetail.setZSAYM	(yearm);		// ������
					paramDetail.setNETWR_SA (amt);			// ����ݾ�

					logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
					logger.debug("@@@ paramDetail.user_id 	[" + user_id 	+  "] ");
					logger.debug("@@@ paramDetail.zbpnnr 	[" + zbpnnr 	+  "] ");
					logger.debug("@@@ paramDetail.yearm 	[" + yearm 		+  "] ");
					logger.debug("@@@ paramDetail.amt 		[" + amt 		+  "] ");

					// ��ϼ��� CALL
					if 		 ("MC".equals(gbn)) {
						sbp0040Dao.insertZSDT1024(paramDetail);  // ������ ����CALL
					}else if ("CH".equals(gbn)) {
						sbp0040Dao.insertZSDT1025(paramDetail);  // û����� ����CALL
					}else if ("SG".equals(gbn)) {
						sbp0040Dao.insertZSDT1026(paramDetail);  // ���ݵ�� ����CALL
					}
				}  	// end of if( amt.compareTo(zero) == 1 )
			}		// end of if (StringUtils.isNotBlank(ds_list_bill.getColumnAsString(i, iCol1)))
		}			// end of for (int i=0;i<=ds_list_bill.getRowCount(); i++ )

		*/

	}

	// �����ȹ ������� ����
	public void updateSaveStat(String mandt			// CLIENT
								,String zbpnnr		// �����ȹ��ȣ
								,String user_id 	// �����ID
								,String save_stat	// �������
			                  )
	{
		Sbp0040 param = new Sbp0040();

		param.setMANDT(mandt);
		param.setZBPNNR(zbpnnr);
		param.setUSER_ID(user_id);
		param.setSAVE_STAT(save_stat);

		sbp0040Dao.updateSaveStat(param);
	}

	// ���¾�ü���� ��ȸ
	public List<Sbp0040> selectListLifnr(Sbp0040ParamVO paramV) {
		return sbp0040Dao.selectListLifnr(paramV);
	}




}
