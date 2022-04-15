package hdel.sd.sbp.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.sbp.domain.Sbp0160;
import hdel.sd.sbp.domain.Sbp0160ParamVO;
import hdel.sd.sbp.domain.Sbp0161;
import hdel.sd.sbp.domain.Sbp0161ParamVO;
import hdel.sd.sso.domain.ZCOBS001;
import hdel.sd.sso.domain.ZCOBS002;
import hdel.sd.sso.domain.ZCOBT200;
import hdel.sd.sso.domain.ZCOBT203;
import hdel.sd.sso.domain.ZCOBT300;
//import hdel.sd.sso.domain.ZCOBT303;
import hdel.sd.sbp.domain.ZSDS0072;
import hdel.sd.sbp.domain.ZSDT0014;
import hdel.sd.sbp.domain.ZSDT0014S;
import hdel.sd.sbp.domain.ZSDT1001;
import hdel.sd.sbp.domain.ZSDT1005;
import hdel.sd.sbp.domain.ZSDT1012;
import hdel.sd.sbp.domain.ZSDT1023;
import hdel.sd.sbp.service.Sbp0161S;

import hdel.sd.sbp.service.Sbp0160S;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sbp0161")
public class Sbp0161C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0161S service;
	
	@Autowired
	private Sbp0160S service0160;
	
	@Autowired
	private AutoSoNumberS autoSoService;	
	
	@RequestMapping(value="find_detail")
	public View Sbp0161FindDetailView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCondDetail = paramVO.getDatasetList().getDataset("ds_cond_detail");
		Dataset dsYearm = paramVO.getDatasetList().getDataset("ds_yearm");

		// OUTPUT DATASET DECLARE
		Dataset dsListDetail = paramVO.getDatasetList().getDataset("ds_list_detail");
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			Sbp0161ParamVO param = new Sbp0161ParamVO();

			// ���ְ�ȹ��ȣ
			String sonnr = DatasetUtility.getString(dsCondDetail,"SONNR");

			// �Ķ���� SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT 
			param.setSONNR		(sonnr);									// �����ȹ��ȣ
			param.setM00		(dsYearm.getColumnAsString( 0, "YEARM"));	// ��ȹ��� + 00��
			param.setM01		(dsYearm.getColumnAsString( 1, "YEARM"));	// ��ȹ��� + 01��
			param.setM02		(dsYearm.getColumnAsString( 2, "YEARM"));	// ��ȹ��� + 02��
			param.setM03		(dsYearm.getColumnAsString( 3, "YEARM"));	// ��ȹ��� + 03��
			param.setM04		(dsYearm.getColumnAsString( 4, "YEARM"));	// ��ȹ��� + 04��
			param.setM05		(dsYearm.getColumnAsString( 5, "YEARM"));	// ��ȹ��� + 05��
			param.setM06		(dsYearm.getColumnAsString( 6, "YEARM"));	// ��ȹ��� + 06��
			param.setM07		(dsYearm.getColumnAsString( 7, "YEARM"));	// ��ȹ��� + 07��
			param.setM08		(dsYearm.getColumnAsString( 8, "YEARM"));	// ��ȹ��� + 08��
			param.setM09		(dsYearm.getColumnAsString( 9, "YEARM"));	// ��ȹ��� + 09��
			param.setM10		(dsYearm.getColumnAsString(10, "YEARM"));	// ��ȹ��� + 10��
			param.setM11		(dsYearm.getColumnAsString(11, "YEARM"));	// ��ȹ��� + 11��
			param.setM12		(dsYearm.getColumnAsString(12, "YEARM"));	// ��ȹ��� + 12��
			param.setM13		(dsYearm.getColumnAsString(13, "YEARM"));	// ��ȹ��� + 13��
			param.setM14		(dsYearm.getColumnAsString(14, "YEARM"));	// ��ȹ��� + 14��
			param.setM15		(dsYearm.getColumnAsString(15, "YEARM"));	// ��ȹ��� + 15��
			param.setM16		(dsYearm.getColumnAsString(16, "YEARM"));	// ��ȹ��� + 16��
			param.setM17		(dsYearm.getColumnAsString(17, "YEARM"));	// ��ȹ��� + 17��
			param.setM18		(dsYearm.getColumnAsString(18, "YEARM"));	// ��ȹ��� + 18��
			param.setM19		(dsYearm.getColumnAsString(19, "YEARM"));	// ��ȹ��� + 19��
			param.setM20		(dsYearm.getColumnAsString(20, "YEARM"));	// ��ȹ��� + 20��
			param.setM21		(dsYearm.getColumnAsString(21, "YEARM"));	// ��ȹ��� + 21��
			param.setM22		(dsYearm.getColumnAsString(22, "YEARM"));	// ��ȹ��� + 22��
			param.setM23		(dsYearm.getColumnAsString(23, "YEARM"));	// ��ȹ��� + 23��
			param.setM24		(dsYearm.getColumnAsString(24, "YEARM"));	// ��ȹ��� + 24��
			param.setM25		(dsYearm.getColumnAsString(25, "YEARM"));	// ��ȹ��� + 25��
			param.setM26		(dsYearm.getColumnAsString(26, "YEARM"));	// ��ȹ��� + 26��
			param.setM27		(dsYearm.getColumnAsString(27, "YEARM"));	// ��ȹ��� + 27��
			param.setM28		(dsYearm.getColumnAsString(28, "YEARM"));	// ��ȹ��� + 28��
			param.setM29		(dsYearm.getColumnAsString(29, "YEARM"));	// ��ȹ��� + 29��
			
			List<Sbp0161> listSbp0161 = service.selectListDetail(param);

			CallSAPHdel.moveListToDs(dsListDetail, Sbp0161.class, listSbp0161);
			dsListDetail.setId("ds_list_detail");
			
			// dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsListDetail);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}

	@RequestMapping(value="save")
	public View Sbp0010SaveView(MipParameterVO paramVO, Model model) {
		
		// ��� 
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");		
		
		// INPUT DATASET GET
		Dataset dsCondDetail = paramVO.getDatasetList().getDataset("ds_cond_detail");
		Dataset dsYearm = paramVO.getDatasetList().getDataset("ds_yearm");
		Dataset dsListDetail = paramVO.getDatasetList().getDataset("ds_list_detail");

		// �������  DATASET GET
		Dataset ds_result = paramVO.getDataset("ds_result");
		//Dataset ds_error = null;
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};			// WEB I/F ó�� ���� ���
		ZSDT0014[] listZSDT0014 = new ZSDT0014[]{};		// �� ���ְ�ȹ
		ZSDT1001[] listZSDT1001 = new ZSDT1001[]{};		// ���ְ�ȹ
		ZSDT1005[] listZSDT1005 = new ZSDT1005[]{};		// ���ְ�ȹ(����)
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[]{};	// �����ȹ DATA (WEB���� ����)
		ZSDT1012[] listZSDT1012 = new ZSDT1012[]{};		// �����ȹ-����
		ZSDT1023[] listZSDT1023 = new ZSDT1023[]{};		// �����ȹ(����)-����
		ZSDS0072[] listZSDS0072 = new ZSDS0072[]{};		// [SD] ����/��� ��ȹ �ڵ� ��� EXPORT
		
		// ���� 
		ZCOBS001[] listZCOBS001 = new ZCOBS001[]{};
		ZCOBS002[] listZCOBS002 = new ZCOBS002[]{};
		ZCOBT200[] listZCOBT200 = new ZCOBT200[]{};
		ZCOBT203[] listZCOBT203 = new ZCOBT203[]{};
		ZCOBT300[] listZCOBT300 = new ZCOBT300[]{};
//		ZCOBT303[] listZCOBT303 = new ZCOBT303[]{};		
		
		// ��� 
		Dataset dsZSDT0014 = ZSDT0014.getDataset();
		Dataset dsZSDT1001 = ZSDT1001.getDataset();
		// ���� 
		Dataset dsZCOBS001 = ZCOBS001.getDataset();
//		Dataset dsZCOBT303 = ZCOBT303.getDataset();

		Dataset dsZSDT0072 = ZSDS0072.getDataset();

		String sonnr = DatasetUtility.getString(dsCondDetail,"SONNR");
		String waerk = DatasetUtility.getString(dsCondDetail,"WAERK");
		Object obj[] = null;

		// ZSDT1001, ZSDT0014, ZCOBS001 Input Parameter Setting
		try {
			//��� 
			dsZSDT0014.deleteAll();
			dsZSDT1001.deleteAll();
			dsZCOBS001.deleteAll();			
			
			dsZSDT0072.deleteAll();

			rfcParamSet(paramVO, session, dsList, dsZSDT0014, dsZSDT1001, dsZCOBS001
					, dsListDetail, dsYearm, dsZSDT0072, sonnr, waerk);
		} catch (Exception e ) {
			e.printStackTrace();
		}

		// ����/û��/���� ���� RFC
		try {
			// ��� 
			listZSDT0014 = (ZSDT0014[])moveDs2Obj2(dsZSDT0014, ZSDT0014.class, null);
			listZSDT1001 = (ZSDT1001[])moveDs2Obj2(dsZSDT1001, ZSDT1001.class, null);
			
			listZSDS0072 = (ZSDS0072[])moveDs2Obj2(dsZSDT0072, ZSDS0072.class, null);

			// RFC FUCNTION ȣ��
			obj = new Object[]{ 
					  "S"
					, "U"
					, "M"
					, " "
					, listMsg
					, listZSDT0014
					, listZSDT1001
					, listZSDT1005
					, listZSDT0014S
					, listZSDT1012
					, listZSDT1023
					, listZSDS0072
			};
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
							, "hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);

			if ( "4".equals(stub.getOutput("E_TYPE")) ) {
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
						, listMsg[0].getIDX().toString()
						, listMsg[0].getERRMSG().toString()
						, "Y"
						, "Y");
			}
		} catch (BizException e) {
			e.printStackTrace();
		 	// ����������Ǿ� �����ڵ尡 ���ϵ� ��� (e.getMessage()�� �����ڵ常 ����� ��)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
			System.out.print("BizException����");
		} catch (Exception e ) {
			e.printStackTrace();
			// ����ġ���� ������������ ���		 (e.getMessage()�� �����޼��� ��ü�� ����� ��)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			System.out.print("Exception����2");
		}
/*
		try {
			// ���� ȣ��
			service.save(paramVO, model, session);
		} catch (Exception e ) {
			e.printStackTrace();
		}
*/
		
		// ó����� ����
		MipResultVO resultVO = new MipResultVO(); 
		ds_result.setId		("ds_result");   
		resultVO.addDataset	(ds_result);  	// ���INFO(�����ȹ��ȣ) 
		ds_error.setId		("ds_error");   
		resultVO.addDataset	(ds_error);  	// ����INFO  
		model.addAttribute	("resultVO", resultVO);    


		return view;		
		

//		MipResultVO resultVO = new MipResultVO();
//		dsResult.setId("ds_result");
//		resultVO.addDataset(dsResult);
//		resultVO.addDataset(dsError);
//		model.addAttribute("resultVO", resultVO);



	}

	private Object moveDs2Obj2(Dataset ds, Class cls, Ds2ObjHelper helper) {
		// �ش� Type���� �迭 ���� 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set �޼ҵ常 ��������
			if ( mArr[i].getName().startsWith("set")) {
				mData.put(mArr[i].getName().substring(3), mArr[i]);
			}
		}
		Object tmpObj = null;
		Method m = null;
		Class setClass = null;
		BigDecimal b = null;
		
		for( int i = 0; i < ds.getRowCount(); i++ ) {
			try {
				tmpObj = cls.newInstance();
				
				for( int c = 0; c < ds.getColumnCount(); c++) {
					m = (Method) mData.get(ds.getColumnID(c));
					if ( m != null ) {
						setClass = m.getParameterTypes()[0];
						if ( setClass.getName().equals("int")){
							m.invoke(tmpObj, DatasetUtility.getInt(ds,i, ds.getColumnID(c)));
						} else if ( setClass.getName().equals("java.math.BigDecimal")){
							b = new BigDecimal(DatasetUtility.getDbl(ds, i, ds.getColumnID(c), 0));
							if ("ZNUMBER".equals(ds.getColumnID(c))) {
								b = b.setScale(0, RoundingMode.DOWN);  // �Ҽ� 0�ڸ�
							} else if ("ZFORE".equals(ds.getColumnID(c)) || "SHANG".equals(ds.getColumnID(c))) {
								b = b.setScale(1, RoundingMode.DOWN);  // �Ҽ� 1�ڸ�
							} else if ("MRATE".equals(ds.getColumnID(c))) {
								b = b.setScale(4, RoundingMode.DOWN);  // �Ҽ� 4�ڸ�
							} else {
								b = b.setScale(2, RoundingMode.DOWN);	// �Ҽ� 2�ڸ�
							}
							m.invoke(tmpObj, b);
						} else { 
							m.invoke(tmpObj, DatasetUtility.getString(ds, i, ds.getColumnID(c)));
						}
					}
				}
				
				// ó�� ���� �˸�. 
				if ( helper != null ) {
					helper.endMoveRow(ds, i, tmpObj);
				}
			} catch( Exception e){
				e.printStackTrace();
			}
			
			// Array ���� ���� 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}
	
	// �����ȹ��ȣ, �����ȣ�� ȣ���ȣ   make�Ͽ� return
	private String f_posid_make(String sonnr, String matnr)
	{
		String posid = "";		// ȣ��		
		if ("NS-100".equals(matnr))
		{
			posid = sonnr + "NS";
		}
		else
		{
			posid = sonnr + StringUtils.substring(matnr, 0, 1) + "01";
		}  
		return posid;
	}	

	private void rfcParamSet(MipParameterVO paramVO, SqlSession session, Dataset dsList
			, Dataset dsZSDT0014, Dataset dsZSDT1001, Dataset dsZCOBS001
			, Dataset dsListDetail,
			Dataset dsYearm, Dataset dsZSDT0072, String SONNR, String WAERK) {
// ��� �߰� 
		String sonnr = "";
		String matnr = "";
		String biddat = "";
		String deldat = "";
		String date = "";
		String time = "";
		String cdate = "";
		String ctime = "";
		String ztplno = "1001";  // �ӽ�
		int nRow = 0;
		double tempDbl = 0;
		List<Sbp0160> listTemplte = null;
		Sbp0160ParamVO param = new Sbp0160ParamVO();
		AutoNumberParamVO sonnrParam = new AutoNumberParamVO();

		param.setMANDT(paramVO.getVariable("G_MANDT"));
		param.setZTPLNO(ztplno);	// ���ø���ȣ

		service.createDao(session);
		autoSoService.createDao(session);

		for (int i = 0; i < dsList.getRowCount(); i++) {
			if (DatasetUtility.getString(dsList, i, "FLAG") != null
					&& (DatasetUtility.getString(dsList, i, "FLAG").equals("U")
					|| DatasetUtility.getString(dsList, i, "FLAG").equals("I"))) {

				dsZSDT0014.appendRow();
				dsZSDT1001.appendRow();
				dsZCOBS001.appendRow();

				matnr = DatasetUtility.getString(dsList, i, "MATNR");
				biddat = DatasetUtility.getString(dsList, i, "ZPYM").substring(0, 4) + "-" 
						+ DatasetUtility.getString(dsList, i, "ZPYM").substring(4) + "-01";
				deldat = DatasetUtility.getString(dsList, i, "DELDAT");
				deldat = deldat.substring(0, 4) + "-" + deldat.substring(4).substring(0, 2) + "-" 
						+ deldat.substring(6);
				date = DateTime.getDateString();
				date = date.substring(0, 4) + "-" + date.substring(4).substring(0, 2) + "-" + date.substring(6);
				time = DateTime.getShortTimeString();
				time = time.substring(0, 2) + ":" + time.substring(2).substring(0, 2) + ":" + time.substring(4);

				if (DatasetUtility.getInt(dsList, i, "ZMPFLG") == 1) {
					dsZSDT1001.setColumn(nRow, "ZMPFLG", "X");
				} else {
					if (DatasetUtility.getString(dsList, i, "ZMPFLG").equals("X")) {
						dsZSDT1001.setColumn(nRow, "ZMPFLG", "X");
					} else {
						dsZSDT1001.setColumn(nRow, "ZMPFLG", " ");
					}
				}
				if (DatasetUtility.getString(dsList, i, "SONNR").equals("�ڵ��Է�")) {
					sonnrParam.setDeptFlag(DatasetUtility.getString(dsList, i, "AUART"));
					sonnrParam.setSoQtFlag("1");
					sonnrParam.setMandt(paramVO.getVariable("G_MANDT"));
					List<AutoNumberVO> sonnrList = autoSoService.AutoSoNumber(sonnrParam);
					sonnr = sonnrList.get(0).getNumber();

					dsZSDT0014.setColumn(nRow, "CDATE", date);
					dsZSDT0014.setColumn(nRow, "CTIME", time);
					dsZSDT0014.setColumn(nRow, "CUSER", paramVO.getVariable("G_USER_ID"));
					dsZSDT0014.setColumn(nRow, "UDATE", "0000-00-00");
					dsZSDT0014.setColumn(nRow, "UTIME", "00:00:00");
					dsZSDT0014.setColumn(nRow, "UUSER", " ");
					dsZSDT0014.setColumn(nRow, "DDATE", "0000-00-00");
					dsZSDT0014.setColumn(nRow, "DTIME", "00:00:00");
					dsZSDT0014.setColumn(nRow, "DUSER", " ");

					dsZSDT1001.setColumn(nRow, "CDATE", date);
					dsZSDT1001.setColumn(nRow, "CTIME", time);
					dsZSDT1001.setColumn(nRow, "CUSER", paramVO.getVariable("G_USER_ID"));
					dsZSDT1001.setColumn(nRow, "UDATE", "0000-00-00");
					dsZSDT1001.setColumn(nRow, "UTIME", "00:00:00");
					dsZSDT1001.setColumn(nRow, "UUSER", " ");
					dsZSDT1001.setColumn(nRow, "DDATE", "0000-00-00");
					dsZSDT1001.setColumn(nRow, "DTIME", "00:00:00");
					dsZSDT1001.setColumn(nRow, "DUSER", " ");
				} else {
					cdate = DatasetUtility.getString(dsList, i, "CDATE");
					cdate = cdate.substring(0, 4) + "-" + cdate.substring(4).substring(0, 2) + "-" + cdate.substring(6);
					ctime = DatasetUtility.getString(dsList, i, "CTIME");
					ctime = ctime.substring(0, 2) + ":" + ctime.substring(2).substring(0, 2) + ":" + ctime.substring(4);

					sonnr = DatasetUtility.getString(dsList, i, "SONNR");

					dsZSDT0014.setColumn(nRow, "CDATE", cdate);
					dsZSDT0014.setColumn(nRow, "CTIME", ctime);
					dsZSDT0014.setColumn(nRow, "CUSER", DatasetUtility.getString(dsList, i, "CUSER"));
					dsZSDT0014.setColumn(nRow, "UDATE", date);
					dsZSDT0014.setColumn(nRow, "UTIME", time);
					dsZSDT0014.setColumn(nRow, "UUSER", paramVO.getVariable("G_USER_ID"));

					dsZSDT1001.setColumn(nRow, "CDATE", cdate);
					dsZSDT1001.setColumn(nRow, "CTIME", ctime);
					dsZSDT1001.setColumn(nRow, "CUSER", DatasetUtility.getString(dsList, i, "CUSER"));
					dsZSDT1001.setColumn(nRow, "UDATE", date);
					dsZSDT1001.setColumn(nRow, "UTIME", time);
					dsZSDT1001.setColumn(nRow, "UUSER", paramVO.getVariable("G_USER_ID"));
				}
				if (DatasetUtility.getInt(dsList, i, "ZDELI") == 1) {
					dsZSDT0014.setColumn(nRow, "ZDELI", "X");
					dsZSDT1001.setColumn(nRow, "ZDELI", "X");
				} else {
					dsZSDT0014.setColumn(nRow, "ZDELI", " ");
					dsZSDT1001.setColumn(nRow, "ZDELI", " ");
				}
				if (DatasetUtility.getInt(dsList, i, "ZINTER") == 1) {
					dsZSDT0014.setColumn(nRow, "ZINTER", "X");
					dsZSDT1001.setColumn(nRow, "ZINTER", "X");
				} else {
					dsZSDT0014.setColumn(nRow, "ZINTER", " ");
					dsZSDT1001.setColumn(nRow, "ZINTER", " ");
				}

				dsZSDT0014.setColumn(nRow, "SONNR", sonnr);
				dsZSDT0014.setColumn(nRow, "PSPID", sonnr);
				dsZSDT0014.setColumn(nRow, "MANDT", paramVO.getVariable("G_MANDT"));
				dsZSDT0014.setColumn(nRow, "BIDYM", DatasetUtility.getInt(dsList, i, "ZPYM"));
				dsZSDT0014.setColumn(nRow, "SPART", DatasetUtility.getString(dsList, i, "SPART"));
				dsZSDT0014.setColumn(nRow, "AUART", DatasetUtility.getString(dsList, i, "AUART"));
				dsZSDT0014.setColumn(nRow, "MATNR", matnr);
				dsZSDT0014.setColumn(nRow, "VKBUR", DatasetUtility.getString(dsList, i, "VKBUR"));
				dsZSDT0014.setColumn(nRow, "VKGRP", DatasetUtility.getString(dsList, i, "VKGRP"));
				dsZSDT0014.setColumn(nRow, "ZKUNNR", DatasetUtility.getString(dsList, i, "ZKUNNR"));
				
				
				//dsZSDT0014.setColumn(nRow, "GTYPE", DatasetUtility.getString(dsList, i, "GTYPE"));   // ��ǥ����
				dsZSDT0014.setColumn(nRow, "GTYPE_OLD", DatasetUtility.getString(dsList, i, "RTYPE")); // �Ǳ��� 
				
				
				dsZSDT0014.setColumn(nRow, "GSPEC1", DatasetUtility.getString(dsList, i, "TYPE1"));
				dsZSDT0014.setColumn(nRow, "GSPEC2", DatasetUtility.getString(dsList, i, "TYPE2"));
				dsZSDT0014.setColumn(nRow, "GSPEC3", DatasetUtility.getString(dsList, i, "TYPE3"));
				dsZSDT0014.setColumn(nRow, "ZNUMBER", DatasetUtility.getInt(dsList, i, "ZNUMBER"));
				dsZSDT0014.setColumn(nRow, "KUNNR", DatasetUtility.getString(dsList, i, "KUNNR"));
				dsZSDT0014.setColumn(nRow, "GSNAM", DatasetUtility.getString(dsList, i, "GSNAM"));
				dsZSDT0014.setColumn(nRow, "REGION", DatasetUtility.getString(dsList, i, "REGION"));
				dsZSDT0014.setColumn(nRow, "SHANGH", DatasetUtility.getString(dsList, i, "SHANGH"));
				dsZSDT0014.setColumn(nRow, "WAERK", DatasetUtility.getString(dsList, i, "WAERK"));
				dsZSDT0014.setColumn(nRow, "DELDAT", deldat);
				dsZSDT0014.setColumn(nRow, "SOABLE", DatasetUtility.getString(dsList, i, "SOABLE"));
				dsZSDT0014.setColumn(nRow, "SORLT", DatasetUtility.getString(dsList, i, "SORLT"));
				dsZSDT0014.setColumn(nRow, "VBELN", DatasetUtility.getString(dsList, i, "VBELN"));
				dsZSDT0014.setColumn(nRow, "POSID", f_posid_make(sonnr, matnr));
				dsZSDT0014.setColumn(nRow, "BIDDAT", biddat);
				dsZSDT0014.setColumn(nRow, "NAME1", DatasetUtility.getString(dsList, i, "KUNNR_NM"));
				dsZSDT0014.setColumn(nRow, "ZFORE", DatasetUtility.getDouble(dsList, i, "ZFORE"));
				dsZSDT0014.setColumn(nRow, "SHANG", DatasetUtility.getDouble(dsList, i, "SHANG"));
				if (DatasetUtility.getDouble(dsList, i, "SOFOCA") == 0) {
					dsZSDT0014.setColumn(nRow, "SOFOCA", 0);
				} else {			
					if ("KRW".equals(DatasetUtility.getString(dsList, i, "WAERK")) 
							||"JPY".equals(DatasetUtility.getString(dsList, i, "WAERK"))) {
						tempDbl = DatasetUtility.getDouble(dsList, i, "SOFOCA") / 100.0;
					} else {
						tempDbl = DatasetUtility.getDouble(dsList, i, "SOFOCA");
					}
					dsZSDT0014.setColumn(nRow, "SOFOCA", tempDbl);
				}
				if (DatasetUtility.getDouble(dsList, i, "SOPRICE") == 0) {
					dsZSDT0014.setColumn(nRow, "SOPRICE", 0);
				} else {
					if ("KRW".equals(DatasetUtility.getString(dsList, i, "WAERK")) 
							||"JPY".equals(DatasetUtility.getString(dsList, i, "WAERK"))) {
						tempDbl = DatasetUtility.getDouble(dsList, i, "SOPRICE") / 100.0;
					} else {
						tempDbl = DatasetUtility.getDouble(dsList, i, "SOPRICE");
					}
					dsZSDT0014.setColumn(nRow, "SOPRICE", tempDbl);
				}
				
				dsZSDT1001.setColumn(nRow, "SONNR", sonnr);
				dsZSDT1001.setColumn(nRow, "MANDT", paramVO.getVariable("G_MANDT"));
				dsZSDT1001.setColumn(nRow, "ZPYM", DatasetUtility.getString(dsList, i, "ZPYM"));
				dsZSDT1001.setColumn(nRow, "SPART", DatasetUtility.getString(dsList, i, "SPART"));
				dsZSDT1001.setColumn(nRow, "AUART", DatasetUtility.getString(dsList, i, "AUART"));
				dsZSDT1001.setColumn(nRow, "MATNR", matnr);
				dsZSDT1001.setColumn(nRow, "VKBUR", DatasetUtility.getString(dsList, i, "VKBUR"));
				dsZSDT1001.setColumn(nRow, "VKGRP", DatasetUtility.getString(dsList, i, "VKGRP"));
				dsZSDT1001.setColumn(nRow, "ZKUNNR", DatasetUtility.getString(dsList, i, "ZKUNNR"));
				
			//	dsZSDT1001.setColumn(nRow, "GTYPE", DatasetUtility.getString(dsList, i, "GTYPE"));       // ��ǥ����
				dsZSDT1001.setColumn(nRow, "GTYPE_OLD", DatasetUtility.getString(dsList, i, "RTYPE"));   // �Ǳ��� 
				
				dsZSDT1001.setColumn(nRow, "TYPE1", DatasetUtility.getString(dsList, i, "TYPE1"));
				dsZSDT1001.setColumn(nRow, "TYPE2", DatasetUtility.getString(dsList, i, "TYPE2"));
				dsZSDT1001.setColumn(nRow, "TYPE3", DatasetUtility.getString(dsList, i, "TYPE3"));
				dsZSDT1001.setColumn(nRow, "ZNUMBER", DatasetUtility.getDouble(dsList, i, "ZNUMBER"));
				dsZSDT1001.setColumn(nRow, "KUNNR", DatasetUtility.getString(dsList, i, "KUNNR"));
				dsZSDT1001.setColumn(nRow, "GSNAM", DatasetUtility.getString(dsList, i, "GSNAM"));
				dsZSDT1001.setColumn(nRow, "REGION", DatasetUtility.getString(dsList, i, "REGION"));
				dsZSDT1001.setColumn(nRow, "SHANGH", DatasetUtility.getString(dsList, i, "SHANGH"));
				dsZSDT1001.setColumn(nRow, "SOFOCA", DatasetUtility.getDouble(dsList, i, "SOFOCA"));
				dsZSDT1001.setColumn(nRow, "ZFORE", DatasetUtility.getDouble(dsList, i, "ZFORE"));
				dsZSDT1001.setColumn(nRow, "WAERK", DatasetUtility.getString(dsList, i, "WAERK"));
				dsZSDT1001.setColumn(nRow, "DELDAT", deldat);
				dsZSDT1001.setColumn(nRow, "ZRMK", DatasetUtility.getString(dsList, i, "ZRMK"));
				dsZSDT1001.setColumn(nRow, "SOABLE", DatasetUtility.getString(dsList, i, "SOABLE"));
				dsZSDT1001.setColumn(nRow, "SORLT", DatasetUtility.getString(dsList, i, "SORLT"));
				dsZSDT1001.setColumn(nRow, "PGUBN", DatasetUtility.getString(dsList, i, "PGUBN"));
				dsZSDT1001.setColumn(nRow, "SOPRICE", DatasetUtility.getDouble(dsList, i, "SOPRICE"));
				dsZSDT1001.setColumn(nRow, "SHANG", DatasetUtility.getDouble(dsList, i, "SHANG"));
				dsZSDT1001.setColumn(nRow, "VBELN", DatasetUtility.getString(dsList, i, "VBELN"));
				dsZSDT1001.setColumn(nRow, "ZBPNNR", DatasetUtility.getString(dsList, i, "ZBPNNR"));
				dsZSDT1001.setColumn(nRow, "ZAGNT", DatasetUtility.getString(dsList, i, "ZAGNT"));
				dsZSDT1001.setColumn(nRow, "LAND1", DatasetUtility.getString(dsList, i, "LAND1"));
				dsZSDT1001.setColumn(nRow, "ZCOST", 0);

			//	listTemplte = service0160.selectListTempletInfo(param);

				// ���� ZCOBS001
//				dsZCOBS001.setColumn(nRow, "QTNUM", sonnr);
//				dsZCOBS001.setColumn(nRow, "GJAHR", DatasetUtility.getString(dsList, i, "ZPYM").substring(0, 4));
//				dsZCOBS001.setColumn(nRow, "POPER", "0" + DatasetUtility.getString(dsList, i, "ZPYM").substring(4));
//				dsZCOBS001.setColumn(nRow, "MATNR", matnr);
//				dsZCOBS001.setColumn(nRow, "VKBUR", DatasetUtility.getString(dsList, i, "VKBUR"));
//				dsZCOBS001.setColumn(nRow, "VKGRP", DatasetUtility.getString(dsList, i, "VKGRP"));
//				dsZCOBS001.setColumn(nRow, "AUART", DatasetUtility.getString(dsList, i, "AUART"));
//				dsZCOBS001.setColumn(nRow, "REGIO", "");
//				dsZCOBS001.setColumn(nRow, "LZONE", "");
//				dsZCOBS001.setColumn(nRow, "ATNAM", listTemplte.get(0).getATNAM());
//				dsZCOBS001.setColumn(nRow, "VALUE", listTemplte.get(0).getVALUE());

				nRow++;
			}
		}		
		// ��� ����		
		
		
		// �ϴ� ����
		String gbn = "";
		String colId = "";
		String yearm = "";
		int nDetailRow = 0;
		BigDecimal amt = null;
		BigDecimal zero = new BigDecimal("0.00");

		for (int row = 0; row < dsListDetail.getRowCount(); row++) {
			gbn = DatasetUtility.getString(dsListDetail, row, "GBN");

			for (int col = 0; col < dsListDetail.getColumnCount(); col++) {
				colId = dsListDetail.getColumnID(col);

				if (!("MANDT".equals(colId)) && !("GBN".equals(colId)) && !("GBN_NM".equals(colId))) {
					// �ݾ��׸� ���� �ִ� ��쿡�� ����
					if (StringUtils.isNotBlank(dsListDetail.getColumnAsString(row, col))) {
						dsZSDT0072.appendRow();

						// ����ݾ�
						amt = new BigDecimal(dsListDetail.getColumnAsDouble(row, col));

						// �ݾ� > 0 �� ��츸 ���
						if( amt.compareTo(zero) == 1 ) {	// compareTo :::: -1�� �۴�, 0�� ����, 1�� ũ��
							if ( colId.endsWith("00")) yearm = dsYearm.getColumnAsString( 0, "YEARM");
							if ( colId.endsWith("01")) yearm = dsYearm.getColumnAsString( 1, "YEARM");
							if ( colId.endsWith("02")) yearm = dsYearm.getColumnAsString( 2, "YEARM");
							if ( colId.endsWith("03")) yearm = dsYearm.getColumnAsString( 3, "YEARM");
							if ( colId.endsWith("04")) yearm = dsYearm.getColumnAsString( 4, "YEARM");
							if ( colId.endsWith("05")) yearm = dsYearm.getColumnAsString( 5, "YEARM");
							if ( colId.endsWith("06")) yearm = dsYearm.getColumnAsString( 6, "YEARM");
							if ( colId.endsWith("07")) yearm = dsYearm.getColumnAsString( 7, "YEARM");
							if ( colId.endsWith("08")) yearm = dsYearm.getColumnAsString( 8, "YEARM");
							if ( colId.endsWith("09")) yearm = dsYearm.getColumnAsString( 9, "YEARM");
							if ( colId.endsWith("10")) yearm = dsYearm.getColumnAsString(10, "YEARM");
							if ( colId.endsWith("11")) yearm = dsYearm.getColumnAsString(11, "YEARM");
							if ( colId.endsWith("12")) yearm = dsYearm.getColumnAsString(12, "YEARM");
							if ( colId.endsWith("13")) yearm = dsYearm.getColumnAsString(13, "YEARM");
							if ( colId.endsWith("14")) yearm = dsYearm.getColumnAsString(14, "YEARM");
							if ( colId.endsWith("15")) yearm = dsYearm.getColumnAsString(15, "YEARM");
							if ( colId.endsWith("16")) yearm = dsYearm.getColumnAsString(16, "YEARM");
							if ( colId.endsWith("17")) yearm = dsYearm.getColumnAsString(17, "YEARM");
							if ( colId.endsWith("18")) yearm = dsYearm.getColumnAsString(18, "YEARM");
							if ( colId.endsWith("19")) yearm = dsYearm.getColumnAsString(19, "YEARM");
							if ( colId.endsWith("20")) yearm = dsYearm.getColumnAsString(20, "YEARM");
							if ( colId.endsWith("21")) yearm = dsYearm.getColumnAsString(21, "YEARM");
							if ( colId.endsWith("22")) yearm = dsYearm.getColumnAsString(22, "YEARM");
							if ( colId.endsWith("23")) yearm = dsYearm.getColumnAsString(23, "YEARM");
							if ( colId.endsWith("24")) yearm = dsYearm.getColumnAsString(24, "YEARM");
							if ( colId.endsWith("25")) yearm = dsYearm.getColumnAsString(25, "YEARM");
							if ( colId.endsWith("26")) yearm = dsYearm.getColumnAsString(26, "YEARM");
							if ( colId.endsWith("27")) yearm = dsYearm.getColumnAsString(27, "YEARM");
							if ( colId.endsWith("28")) yearm = dsYearm.getColumnAsString(28, "YEARM");
							if ( colId.endsWith("29")) yearm = dsYearm.getColumnAsString(29, "YEARM");
						}
						dsZSDT0072.setColumn(nDetailRow, "SONNR", SONNR);
						dsZSDT0072.setColumn(nDetailRow, "WAERK", WAERK);
						dsZSDT0072.setColumn(nDetailRow, "PLANYM", yearm);
						dsZSDT0072.setColumn(nDetailRow, "PLANAMT", amt.doubleValue());

						// ��ȹ���� (SJ : ����, MC : ����, CH : û��, SG : ����)
						if ("1".equals(gbn)) {
							dsZSDT0072.setColumn(nDetailRow, "PLANTP", "MC");
						} else if ("2".equals(gbn)) {
							dsZSDT0072.setColumn(nDetailRow, "PLANTP", "CH");
						} else if ("3".equals(gbn)) {
							dsZSDT0072.setColumn(nDetailRow, "PLANTP", "SG");
						}

						nDetailRow++;
					}
				}
			}
		}
	}
	
}
