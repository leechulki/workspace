package hdel.sd.sbp.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.com.service.ComboS;
import hdel.sd.com.service.DutyS;
import hdel.sd.sbp.domain.ComboParamVO;
import hdel.sd.sbp.domain.ComboVO;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;
import hdel.sd.sbp.domain.Sbp0160;
import hdel.sd.sbp.domain.Sbp0160ParamVO;
import hdel.sd.sso.domain.ZCOBS001;
import hdel.sd.sso.domain.ZCOBS002;
import hdel.sd.sso.domain.ZCOBT200;
import hdel.sd.sso.domain.ZCOBT202;
import hdel.sd.sso.domain.ZCOBT203;
import hdel.sd.sso.domain.ZCOBT300;
import hdel.sd.sso.domain.ZCOBT302;
//import hdel.sd.sso.domain.ZCOBT303;
import hdel.sd.sbp.domain.ZSDS0072;
import hdel.sd.sbp.domain.ZSDT0014;
import hdel.sd.sbp.domain.ZSDT0014S;
import hdel.sd.sbp.domain.ZSDT1001;
import hdel.sd.sbp.domain.ZSDT1005;
import hdel.sd.sbp.domain.ZSDT1012;
import hdel.sd.sbp.domain.ZSDT1023;
import hdel.sd.sbp.service.Sbp0010S;
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
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sbp0160")
public class Sbp0160C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0160S service;

	@Autowired
	private AutoSoNumberS autoSoService;
	
	@Autowired
	private ComboS comboService;

	@Autowired
	private DutyS dutyService;

	@Autowired
	private Sbp0010S serviceSbp0010;
	
	@RequestMapping(value="combo")
	public View combo(MipParameterVO paramVO, Model model) {
		Dataset dsComboGubun = paramVO.getDatasetList().getDataset("ds_combo_gubun");
		String strComboGubun = dsComboGubun.getColumnAsString(0, "GUBUN");
		Dataset dsCond = paramVO.getDatasetList().getDataset(strComboGubun);
		Dataset dsCombo = paramVO.getDatasetList().getDataset("ds_combo");
		Dataset dsGtypes = paramVO.getDataset("ds_gtypes");
		Dataset dsGtype = paramVO.getDatasetList().getDataset("ds_gtype");

		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			if (strComboGubun.equals("ds_cond_soable")) {					// 수주가능성
				ComboParamVO param = new ComboParamVO();
				param.setMANDT(paramVO.getVariable("G_MANDT"));
				param.setDOMNAME(dsCond.getColumnAsString(0, "DOMNAME"));

				List<ComboVO> listCombo = service.selectDD07T(param);

				CallSAPHdel.moveListToDs(dsCombo, ComboVO.class, listCombo);
			} else if (strComboGubun.equals("ds_cond_gtype")) {				// 기종
				Sbp0010ParamVO paramSbp0010 = new Sbp0010ParamVO();

				String mandt = paramVO.getVariable("G_MANDT");
				String vkbur = dsCond.getColumnAsString(0, "VKBUR");
				String vkgrp = dsCond.getColumnAsString(0, "VKGRP");
				String zkunnr = dsCond.getColumnAsString(0, "ZKUNNR");

				paramSbp0010.setMANDT(mandt);
				paramSbp0010.setVKBUR(vkbur);
				paramSbp0010.setVKGRP(vkgrp);
				paramSbp0010.setZKUNNR(zkunnr);

				serviceSbp0010.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
				List<Sbp0010> list = serviceSbp0010.selectListRGtype(paramSbp0010);  // 실기종목록 조회

				dsGtype.deleteAll();
				dsGtype.setId("ds_gtype");

				for (int iRow=0;iRow<list.size();iRow++) {	// 호출결과(list)를 데이타셋(dsList)에 복사   

					dsGtype.appendRow(); 			// 행추가
					dsGtype.setColumn(iRow, "CODE"	 , list.get(iRow).getCODE	());	 // 실기종 코드 
					dsGtype.setColumn(iRow, "CODE_NAME" , list.get(iRow).getCODENAME	()); // 실기종 코드 이름
					dsGtype.setColumn(iRow, "SPART"	 , list.get(iRow).getSPART	());	 // 제품군
					dsGtype.setColumn(iRow, "AUART"	 , list.get(iRow).getAUART	());	 // 판매문서유형 
					dsGtype.setColumn(iRow, "MATNR"	 , list.get(iRow).getMATNR	());	 // 자재번호
				}
//				List<hdel.sd.com.domain.ComboVO> listCombo = comboService.selectRtype(param); // comboService.selectGtype(param);
//				CallSAPHdel.moveComboListToDs(dsCombo, hdel.sd.com.domain.ComboVO.class, listCombo);
				dsGtypes.deleteAll();

				ZGTypes zgtype = new ZGTypes();
				CallSAPHdel.moveListToDs(dsGtypes, ZGType.class, zgtype.find());
				dsGtypes.setId("ds_gtypes");
			} else if (strComboGubun.equals("ds_cond_region")) {			// 설치
				ComboParamVO param = new ComboParamVO();
				param.setMANDT(paramVO.getVariable("G_MANDT"));
				param.setLANG(paramVO.getVariable("G_LANG"));

				List<ComboVO> listCombo = service.selectREGION(param);
				CallSAPHdel.moveListToDs(dsCombo, ComboVO.class, listCombo);
			}

			dsCombo.setId("ds_combo");

			MipResultVO resultVO = new MipResultVO();			// dataset을 return
			resultVO.addDataset(dsCombo);

			if (strComboGubun.equals("ds_cond_gtype")) {
				resultVO.addDataset(dsGtype);
				resultVO.addDataset(dsGtypes);
			}
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	@RequestMapping(value="deldat")
	public View deldat(MipParameterVO paramVO, Model model) {
		// INPUT DATASET GET
		Dataset dsCondDeldat = paramVO.getDatasetList().getDataset("ds_cond_deldat");
		Dataset dsDeldat = paramVO.getDatasetList().getDataset("ds_deldat");
		Dataset dsError = null;

		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};

		// RFC INPUT SET (파라메터 순서엄수)
		Object obj[] = new Object[]{ 
				  ""
				, DatasetUtility.getString(dsCondDeldat, "I_DATE_FROM")
				, DatasetUtility.getString(dsCondDeldat, "I_DAYS")
				, DatasetUtility.getString(dsCondDeldat, "I_WERKS")
				, listMsg
		};

		try {
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
							, "hdel.sd.sbp.domain.ZWEB_SD_GET_WORKINGDAY"
							, obj, false);

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);

			if (dsError.getRowCount() == 0) {
				dsDeldat.deleteAll();
				dsDeldat.appendRow();
				dsDeldat.setColumn(0, 0, stub.getOutput("C_WORKINGDAY").toString().replace("-", ""));
			}
		} catch (CommRfcException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsDeldat);
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
	@RequestMapping(value="find")
	public View Sbp0160FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		dsList = listToDataset(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsList);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="save")
	public View Sbp0160Merge(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		// 결과정보  DATASET GET
		Dataset dsResult = paramVO.getDataset("ds_result");
		Dataset dsError = null;

		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};			// WEB I/F 처리 오류 결과
		ZSDT0014[] listZSDT0014 = new ZSDT0014[]{};		// 월 수주계획
		ZSDT1001[] listZSDT1001 = new ZSDT1001[]{};		// 수주계획
		ZSDT1005[] listZSDT1005 = new ZSDT1005[]{};		// 수주계획(보수)
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[]{};	// 사업계획 DATA (WEB에서 전송)
		ZSDT1012[] listZSDT1012 = new ZSDT1012[]{};		// 사업계획-수주
		ZSDT1023[] listZSDT1023 = new ZSDT1023[]{};		// 사업계획(보수)-수주
		ZSDS0072[] listZSDS0072 = new ZSDS0072[]{};		// [SD] 수주/사업 계획 자동 계산 EXPORT

		ZCOBS001[] listZCOBS001 = new ZCOBS001[]{};
		ZCOBS002[] listZCOBS002 = new ZCOBS002[]{};
		ZCOBT200[] listZCOBT200 = new ZCOBT200[]{};
		ZCOBT202[] listZCOBT202 = new ZCOBT202[]{};
		ZCOBT203[] listZCOBT203 = new ZCOBT203[]{};
		ZCOBT300[] listZCOBT300 = new ZCOBT300[]{};
		ZCOBT302[] listZCOBT302 = new ZCOBT302[]{};
//		ZCOBT303[] listZCOBT303 = new ZCOBT303[]{}; //new ZCOBT303[]{};

		Dataset dsZSDT0014 = ZSDT0014.getDataset();
		Dataset dsZSDT1001 = ZSDT1001.getDataset();
		Dataset dsZCOBS001 = ZCOBS001.getDataset();
		Dataset dsZCOBT303 = null;//ZCOBT303.getDataset();

		Object obj[] = null;

		// ZSDT1001, ZSDT0014, ZCOBS001 Input Parameter Setting
		try {
			dsZSDT0014.deleteAll();
			dsZSDT1001.deleteAll();
			dsZCOBS001.deleteAll();

			rfcParamSet(paramVO, session, dsList, dsZSDT0014, dsZSDT1001, dsZCOBS001);
		} catch (Exception e ) {
			e.printStackTrace();
		}

		// 매출/청구/수금 산출 RFC
		try {
			listZSDT0014 = (ZSDT0014[])moveDs2Obj2(dsZSDT0014, ZSDT0014.class, null);
			listZSDT1001 = (ZSDT1001[])moveDs2Obj2(dsZSDT1001, ZSDT1001.class, null);

			// RFC FUCNTION 호출
			obj = new Object[]{ 
					  "S"
					, ""
					, "M"
					, DatasetUtility.getString(dsList, 0, "UFLAG")
					, listMsg
					, listZSDT0014
					, listZSDT1001
					, listZSDT1005
					, listZSDT0014S
					, listZSDT1012
					, listZSDT1023
					, listZSDS0072
			};
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);

			if ( "4".equals(stub.getOutput("E_TYPE")) ) {
				dsError = CallSAPHdel.makeErrorInfoColSet(dsError
						, listMsg[0].getIDX().toString()
						, listMsg[0].getERRMSG().toString()
						, "Y"
						, "Y");
				System.out.print(listMsg[0].getIDX().toString() + "\n");
				System.out.print(listMsg[0].getERRMSG().toString() + "\n");

				// dataset을 return
				MipResultVO resultVO = new MipResultVO();
				resultVO.addDataset(dsError);
				model.addAttribute("resultVO", resultVO);

				return view;
			} else {
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			}
		} catch (BizException e) {
			e.printStackTrace();
		 	// 비정상종료되어 오류코드가 리턴된 경우 (e.getMessage()에 오류코드만 담겨져 옴)
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, e.getMessage(), "", "Y", "Y"); 
		} catch (Exception e ) {
			e.printStackTrace();
			// 예기치못한 비정상종료인 경우		 (e.getMessage()에 오류메세지 전체가 담겨져 옴)
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		// 원가산출 RFC
		try {
			listZCOBS001 = (ZCOBS001[])moveDs2Obj2(dsZCOBS001, ZCOBS001.class, null);

			// RFC INPUT SET
			obj = new Object[] {
					  "2"  				// I_DIV   분류( 1:사업계획,2:수주계획,3:견적,4:수주,9:예상 )
					, "3"  				// I_GBN   처리구분 (1: BOM, 2: 원가, 3: Web )
					, ""  				// I_GJAHR 견적년도(=사업계획년도)
					, ""  				// I_POPER 기준년월
					, listZCOBS001
					, listZCOBS002
					, listZCOBT200
					, listZCOBT202
					, listZCOBT203
					, listZCOBT300
					, listZCOBT302
					, null //listZCOBT303
			};

			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_CO4_FIND_COST", obj, false);

			if ( "4".equals(stub.getOutput("E_TYPE")) ) {
				dsError = CallSAPHdel.makeErrorInfoColSet(dsError
							, "CE00001"
							, ""
							, "Y"
							, "Y");
			} else {
				CallSAP.moveObj2Ds(dsZCOBT303, stub.getOutput("T_T303"));	// 원가마스터

				costUpdate(dsZSDT1001, dsZCOBT303, paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_USER_ID"), session);
			}
		} catch (BizException e) {
			e.printStackTrace();
		 	// 비정상종료되어 오류코드가 리턴된 경우 (e.getMessage()에 오류코드만 담겨져 옴)
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, e.getMessage(), "", "Y", "Y");
		} catch (Exception e ) {
			e.printStackTrace();
			// 예기치못한 비정상종료인 경우		 (e.getMessage()에 오류메세지 전체가 담겨져 옴)
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		try {
			dsList = listToDataset(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
			
			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e ) {
			e.printStackTrace();
		}

		return view;
	}
	
	private Dataset listToDataset(String sysid, String mandt, Dataset dsList, Dataset dsCond) {
		service.createDao(sqlSession.getSqlSessionBySysid(sysid));

		Sbp0160ParamVO param = new Sbp0160ParamVO();
		param.setMANDT(mandt);
		param.setFR_ZPYM(DatasetUtility.getString(dsCond, "FR_ZPYM"));
		param.setTO_ZPYM(DatasetUtility.getString(dsCond, "TO_ZPYM"));
		param.setZPYM(DatasetUtility.getString(dsCond, "ZPYM"));
		param.setSPART(DatasetUtility.getString(dsCond, "SPART"));
		param.setZKUNNR(DatasetUtility.getString(dsCond, "ZKUNNR"));
		param.setVKBUR(DatasetUtility.getString(dsCond, "VKBUR"));
		param.setVKGRP(DatasetUtility.getString(dsCond, "VKGRP"));
		param.setZMPFLG(DatasetUtility.getString(dsCond, "ZMPFLG"));
		param.setSORLT(DatasetUtility.getString(dsCond, "SORLT"));
		param.setZAGNT(DatasetUtility.getString(dsCond, "ZAGNT"));
		
		List<Sbp0160> listSbp0160 = null;

		if (DatasetUtility.getString(dsCond, "WGBN").equals("E")) {
			listSbp0160 = service.selectZSDT1001E(param);
		} else {
			listSbp0160 = service.selectZSDT1001(param);
		}

		dsList.deleteAll();

		CallSAPHdel.moveListToDs(dsList, Sbp0160.class, listSbp0160);
		dsList.setId("ds_list");
		
		return dsList;
	}

	// 사업계획번호, 자재번호로 호기번호   make하여 return
	private String f_posid_make(String sonnr, String matnr)
	{
		String posid = "";		// 호기		
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
							) {
		String sonnr = "";
		String matnr = "";
		String biddat = "";
		String deldat = "";
		String date = "";
		String time = "";
		String cdate = "";
		String ctime = "";
		String ztplno = "";
		String flag   = "";
		String mandt = paramVO.getVariable("G_MANDT");
		String vkbur    = "";
		String rtype     = "";
		String type1     = "";
		String type2     = "";
		String type3     = "";
		String shangh   = "";
		String zinter     = "";
		String dutyGbn = "";
		String gtype     = "";

		int nRow = 0;
		double tempDbl = 0;
		List<Sbp0160> listTemplte = null;
		Sbp0160ParamVO param = new Sbp0160ParamVO();
		AutoNumberParamVO sonnrParam = new AutoNumberParamVO();

		service.createDao(session);
		autoSoService.createDao(session);

		for (int i = 0; i < dsList.getRowCount(); i++) {
			flag = DatasetUtility.getString(dsList, i, "FLAG");

			if (flag != null	&& (flag.equals("U") || flag.equals("I"))) {

				dutyService.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

				HashMap mapInput  = new HashMap();
				HashMap mapOutput = new HashMap();

				vkbur = dsList.getColumn(i, "VKBUR").toString();
				rtype = dsList.getColumn(i, "RTYPE").toString();
				type1 = dsList.getColumn(i, "TYPE1").toString();
				type2 = dsList.getColumn(i, "TYPE2").toString();
				type3 = dsList.getColumn(i, "TYPE3").toString();
				matnr = dsList.getColumn(i, "MATNR").toString();
				shangh = dsList.getColumn(i, "SHANGH").toString();
				zinter   = dsList.getColumn(i, "ZINTER").toString();
				
				mapInput.put("VKBUR", vkbur); // 부서
				mapInput.put("RTYPE", rtype); // 기종
				mapInput.put("TYPE1", type1); // 인승
				mapInput.put("TYPE2", type2); // 속도
				mapInput.put("TYPE3", type3); // 층수
				mapInput.put("MATNR", matnr);
				mapInput.put("SHANGH", shangh);
				mapInput.put("ZINTER", zinter);
				
				if       (matnr.equals("L-1000") || matnr.equals("L-2000"))  dutyGbn = "PCELV";
				else if (matnr.equals("S-1000") || matnr.equals("W-1000")) dutyGbn = "PCESMW";
				else    dutyGbn = "PCELV";

				mapOutput = dutyService.genSpec(mandt, dutyGbn, "00", mapInput, paramVO.getVariable("G_LANG"));

				ztplno = mapOutput.get("ZTPLNO").toString();
				gtype = mapOutput.get("GTYPE").toString();
				

				if(ztplno != null && !ztplno.equals("") ) {
					param.setMANDT(mandt);
					param.setZTPLNO(ztplno);	// 템플릿번호
				
					dsZSDT0014.appendRow();
					dsZSDT1001.appendRow();
					dsZCOBS001.appendRow();

					matnr = DatasetUtility.getString(dsList, i, "MATNR");
					biddat = DatasetUtility.getString(dsList, i, "ZPYM").substring(0, 4) + "-" + DatasetUtility.getString(dsList, i, "ZPYM").substring(4) + "-01";
					deldat = DatasetUtility.getString(dsList, i, "DELDAT");
					deldat = deldat.substring(0, 4) + "-" + deldat.substring(4).substring(0, 2) + "-" + deldat.substring(6);
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
					if (DatasetUtility.getString(dsList, i, "SONNR").equals("자동입력")) {
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
				
					//dsZSDT0014.setColumn(nRow, "GTYPE", DatasetUtility.getString(dsList, i, "GTYPE"));  // 대표기종
					dsZSDT0014.setColumn(nRow, "GTYPE", gtype);  // 대표기종
					dsZSDT0014.setColumn(nRow, "GTYPE_OLD", DatasetUtility.getString(dsList, i, "RTYPE")); // 실기종
				
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
				
					//dsZSDT1001.setColumn(nRow, "GTYPE", DatasetUtility.getString(dsList, i, "GTYPE"));      // 대표기종
					dsZSDT1001.setColumn(nRow, "GTYPE", gtype);      // 대표기종
					dsZSDT1001.setColumn(nRow, "RTYPE", DatasetUtility.getString(dsList, i, "RTYPE"));  // 실기종 GTYPE_OLD->RTYPE
				
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

					listTemplte = service.selectListTempletInfo(param);

					dsZCOBS001.deleteAll(); // 원가 ZCOBS001
				
					for(int j=0; j< listTemplte.size(); j++) {
						dsZCOBS001.appendRow(); 	// 행추가
						dsZCOBS001.setColumn(j, "QTNUM", sonnr);
						dsZCOBS001.setColumn(j, "GJAHR", DatasetUtility.getString(dsList, 0, "ZPYM").substring(0, 4));
						dsZCOBS001.setColumn(j, "POPER", "0" + DatasetUtility.getString(dsList, 0, "ZPYM").substring(4));
						dsZCOBS001.setColumn(j, "MATNR", matnr);
						dsZCOBS001.setColumn(j, "VKBUR", DatasetUtility.getString(dsList, 0, "VKBUR"));
						dsZCOBS001.setColumn(j, "VKGRP", DatasetUtility.getString(dsList, 0, "VKGRP"));
						dsZCOBS001.setColumn(j, "AUART", DatasetUtility.getString(dsList, 0, "AUART"));
						dsZCOBS001.setColumn(j, "REGIO", "");
						dsZCOBS001.setColumn(j, "LZONE", "");
						dsZCOBS001.setColumn(j, "ATNAM", listTemplte.get(j).getATNAM());
						dsZCOBS001.setColumn(j, "VALUE", listTemplte.get(j).getVALUE());
					}

					nRow++;
				}
			}
		}
	}

	public static Object moveDs2Obj2(Dataset ds, Class cls, Ds2ObjHelper helper) {
		// 해당 Type으로 배열 생성 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set 메소드만 가져오기
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
								b = b.setScale(0, RoundingMode.DOWN);  // 소수 0자리
							} else if ("ZFORE".equals(ds.getColumnID(c)) || "SHANG".equals(ds.getColumnID(c))) {
								b = b.setScale(1, RoundingMode.DOWN);  // 소수 1자리
							} else if ("MRATE".equals(ds.getColumnID(c))) {
								b = b.setScale(4, RoundingMode.DOWN);  // 소수 4자리
							} else {
								b = b.setScale(2, RoundingMode.DOWN);	// 소수 2자리
							}
							m.invoke(tmpObj, b);
						} else { 
							m.invoke(tmpObj, DatasetUtility.getString(ds, i, ds.getColumnID(c)));
						}
					}
				}
				
				// 처리 종료 알림. 
				if ( helper != null ) {
					helper.endMoveRow(ds, i, tmpObj);
				}
			} catch( Exception e){
				e.printStackTrace();
			}
			
			// Array 정보 설정 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}

	private void costUpdate(Dataset dsZSDT1001, Dataset dsZCOBT303, String MANDT, String UUSER, SqlSession session) {
		Method[] mArr = null; //ZCOBT303.class.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set 메소드만 가져오기
			if ( mArr[i].getName().startsWith("set")) {
				mData.put(mArr[i].getName().substring(3), mArr[i]);
			}
		}

		Method m = null;
		Class setClass = null;
		double sum = 0;
		BigDecimal b = null;
		BigDecimal ZCOST = new BigDecimal(0);
		Sbp0160 param = new Sbp0160();

		for( int i = 0; i < dsZCOBT303.getRowCount(); i++ ) {
			try {
				ZCOST = new BigDecimal(0);
				sum = 0;

				for( int c = 0; c < dsZCOBT303.getColumnCount(); c++) {
					m = (Method) mData.get(dsZCOBT303.getColumnID(c));

					if ( m != null ) {
						setClass = m.getParameterTypes()[0];

						if ( setClass.getName().equals("java.math.BigDecimal")){
							if ("EQMA01".equals(dsZCOBT303.getColumnID(c)) 
								|| "EQMA02".equals(dsZCOBT303.getColumnID(c)) 
								|| "PRDA01".equals(dsZCOBT303.getColumnID(c)) 
								|| "CTSA01".equals(dsZCOBT303.getColumnID(c))) {
								continue;
							} else {
								b = new BigDecimal(DatasetUtility.getDbl(dsZCOBT303, i, dsZCOBT303.getColumnID(c), 0));
								sum += b.doubleValue();
							}
						}
					}
				}

				if ("KRW".equals(DatasetUtility.getString(dsZSDT1001, i, "WAERK")) 
						||"JPY".equals(DatasetUtility.getString(dsZSDT1001, i, "WAERK"))) {
					sum *= 100;
				}
				ZCOST = new BigDecimal(sum);
				ZCOST = ZCOST.setScale(2, RoundingMode.DOWN);
				param.setMANDT(MANDT);
				param.setUUSER(UUSER);
				param.setSONNR(DatasetUtility.getString(dsZCOBT303, i, "SONNR"));
				param.setZCOST(ZCOST);

				service.createDao(session);
				service.saveCost(param);
			} catch( Exception e){
				e.printStackTrace();
			}
		}
	}

}
