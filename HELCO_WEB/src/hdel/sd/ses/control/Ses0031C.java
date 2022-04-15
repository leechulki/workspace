package hdel.sd.ses.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.com.service.DutyS;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.service.Ses0020S;
import hdel.sd.ses.service.Ses0031S;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.sso.domain.ZCOBS001;
import hdel.sd.sso.domain.ZCOBT200;
import hdel.sd.sso.domain.ZCOBT202;
import hdel.sd.sso.domain.ZCOBT204;
import hdel.sd.sso.domain.ZCOBT206;
import hdel.sd.sso.domain.ZCOBT300;
import hdel.sd.sso.domain.ZCOBT302;
import hdel.sd.sso.domain.ZCOBT304;
import hdel.sd.sso.domain.ZCOBT306;
import hdel.sd.sso.domain.ZCOBT309;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0031")
public class Ses0031C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0031S service;

	@Autowired
	private Ses0020S service2;
	
	@Autowired
	private DutyS dutyService;

	@Autowired
	private ExchangeS ExchangeS;
	
	@RequestMapping(value="searchGtype")
	public View Ses0031FindGTypeView(MipParameterVO paramVO, Model model) {

		Dataset ds_gtype = paramVO.getDataset("ds_gtype");	// UI로 return할 out dataset  
		Dataset ds_error = paramVO.getDataset("ds_error");	// UI로 return할 오류메세지 dataset

		try {
			List<ZGType> list = ZGTypes.find();	// 기종목록 조회
			
			for (int i=0; i<list.size(); i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사

				ds_gtype.appendRow();  				// 행추가
				ds_gtype.setColumn(i, "gtype"    , list.get(i).getZgtype	 ()); // 기종
				ds_gtype.setColumn(i, "auart"    , list.get(i).getAuart	 ()); // 판매문서유형(오더유형)
				ds_gtype.setColumn(i, "matnr"   , list.get(i).getMatnr	 ()); // 자재번호
				ds_gtype.setColumn(i, "spart"    , list.get(i).getSpart	 ()); // 제품군
				ds_gtype.setColumn(i, "zprdgbn", list.get(i).getZprdgbn()); // 템플릿구분
			}
			MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
			ds_gtype.setId("ds_gtype");
			resultVO.addDataset(ds_gtype);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;  
	} 

	@RequestMapping(value="searchZGtype")
	public View Ses0031ZsdtGTypeView(MipParameterVO paramVO, Model model) {

		Dataset dsZGtype = paramVO.getDataset("ds_zgtype");	// UI로 return할 out dataset
		Dataset ds_error = paramVO.getDataset("ds_error");	// UI로 return할 오류메세지 dataset

		dsZGtype = gTypeToDataset(dsZGtype, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsZGtype);

			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;  
	}

	@RequestMapping(value="searchSo")
	public View Ses0032HeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsSo	      = paramVO.getDataset("ds_so");	// UI로 return할 out dataset

		dsSo = soToDataset(dsSo, dsHeader, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsSo);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value="searchSpec")
	public View Ses0020FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_temp");	// UI로 return할 out dataset  

		dsList = specToDataset(dsList, dsCond, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
			resultVO.addDataset(dsList); 
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}

		return view;
	}

	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="searchCost")
	public View Ses0031CostView(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsCost     = paramVO.getDataset("ds_cost");
		Dataset dsChar     = paramVO.getDataset("ds_char");		// 화면상의 ds_template
		Dataset dsT202     = paramVO.getDataset("ds_t202");
		Dataset dsT302     = paramVO.getDataset("ds_t302");
		Dataset dsCheck    = paramVO.getDataset("ds_check");
		Dataset dsHeader   = paramVO.getDataset("ds_header");
		//Dataset dsExchange = paramVO.getDataset("ds_exchange");		// 환율기준정보 변경으로 제거 2013.02.20 &&&&&
		Dataset dsDetail   = paramVO.getDataset("ds_detail");	// QTSEQ별 사양서 확인 용
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		Dataset ds_log = new Dataset("ds_log");                  // ds_log 추가 20150423 김선호 
		ds_log.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);  
		ds_log.addColumn("LOGMSG", ColumnInfo.COLTYPE_STRING, 256);
		
		// RFC INPUT PARAM DECLARE
		ZCOBS001[] list001 = (ZCOBS001[])SmpComC.moveDs2Obj(dsChar , ZCOBS001.class, ""); // RFC input dataset을 CLASS(ZCOBS001) 에 옮기기
		ZCOBT309[] list309 = new ZCOBT309[]{}; // I_CHEK
		ZCOBT200[] list200 = new ZCOBT200[]{};
		ZCOBT202[] list202 = new ZCOBT202[]{};
		ZCOBT204[] list204 = new ZCOBT204[]{};
		ZCOBT206[] list206 = new ZCOBT206[]{};
		ZCOBT300[] list300 = new ZCOBT300[]{};
		ZCOBT302[] list302 = new ZCOBT302[]{};
		ZCOBT304[] list304 = new ZCOBT304[]{};
		ZCOBT306[] list306 = new ZCOBT306[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};	// sap 에러메시지 return용

		// OUTPUT DATASET DECLARE
		Dataset ds_300    = null; // UI로 return할 out dataset
		Dataset ds_202    = null; // UI로 return할 out dataset
		Dataset ds_302    = null; // UI로 return할 out dataset
		Dataset ds_204    = null; // UI로 return할 out dataset
		Dataset ds_304    = null; // UI로 return할 out dataset
		Dataset ds_check = null;

		MipResultVO resultVO = new MipResultVO();		// RFC 출력 dataset을 return

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		String item  = "";
		try {
			// 2013.01.02 원가산출의뢰전 사양 필수사양 확인
			dutyService.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			String mandt = paramVO.getVariable("G_MANDT");				// MANDT
			String lang  = paramVO.getVariable("G_LANG");				// LANG
			String flag  = "Q"; 										// Q-견적, P-계약

			for (int i = 0 ; i < dsDetail.getRowCount(); i++)	{
				item = dsDetail.getColumnAsString(i, "QTSEQ"); 	// QTSEQ번호

				// Exception이 발생하지 않는 경우에는 계속 진행
				boolean bBool = dutyService.genSpecCheck(mandt, flag, item, dsChar,ds_log,lang);
			}
			if ( ds_log.getRowCount() > 0 ) {   // 종속성 체크후 log 있으면 return
				resultVO.addDataset(ds_log); 	// log 내역 
				model.addAttribute("resultVO", resultVO);
				return view;
			}
		} catch(Exception e) {
			e.printStackTrace();

			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "MO [" + item + "] " + e.getMessage(), "Y", "Y");
			ds_error.setId("ds_error");
			resultVO.addDataset(ds_error); 	// 오류결과내역
			model.addAttribute("resultVO", resultVO);

			return view;
		}

		try
		{
			Object obj[] = new Object[]{		// RFC INPUT SET
					  DatasetUtility.getString(dsCost, "DIV"   )
					, DatasetUtility.getString(dsCost, "GBN"  )
					, DatasetUtility.getString(dsCost, "GJAHR")
					, DatasetUtility.getString(dsCost, "POPER")
					, 0
					, ""
					, list001
					, list309 // list002-> list309
					, list200
					, list202
					, list204 // list202 -> list204
					, list206
					, list300
					, list302
					, list304 // list302 -> list304
					, list306
			};

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_CO4_FIND_COST", obj, false);	// RFC CALL

			ds_300 = CallSAP.isJCO() ? new Dataset() : ZCOBT300.getDataset();
			ds_300.setId("ds_t300");

			ds_202 = CallSAP.isJCO() ? new Dataset() : ZCOBT202.getDataset();	// RFC 출력구조체로 out dataset(ds) 생성
			ds_202.setId("ds_t202");
			ds_302 = CallSAP.isJCO() ? new Dataset() : ZCOBT302.getDataset();
			ds_302.setId("ds_t302");
			ds_204 = CallSAP.isJCO() ? new Dataset() : ZCOBT204.getDataset();	// RFC 출력구조체로 out dataset(ds) 생성
			ds_204.setId("ds_t204");
			ds_304 = CallSAP.isJCO() ? new Dataset() : ZCOBT304.getDataset();
			ds_304.setId("ds_t304");

			ds_check = CallSAP.isJCO() ? new Dataset() : ZCOBT309.getDataset(); // ZCOBS002->ZCOBT309
			ds_check.setId("ds_check");

			if( "4".equals(stub.getOutput("E_TYPE")) ) {

				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				ds_error = CallSAP.makeErrorInfo(listMsg);
				//ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
			}
			
			CallSAP.moveObj2Ds(ds_202,   stub.getOutput("T_T202"));		// RFC 호출결과(T_T202,T_T302)를 out dataset(ds)에 옮기기
			CallSAP.moveObj2Ds(ds_302,   stub.getOutput("T_T302"));
			CallSAP.moveObj2Ds(ds_check, stub.getOutput("T_CHEK"));

			// 견적의 경우에만 처리 2012.12.18
			if ( DatasetUtility.getString(dsCost, "DIV").equals("3") )	{
				// service.saveCostUpdate(dsHeader, dsExchange, ds_302, paramVO, session);	// 환율기준정보 변경으로 제거 2013.02.20 &&&&&
				// 2013.03.13 원가산출(재산출) 시 부분원가 동일 여부 확인
//				boolean bCheck = false;
//				for (int i = 0; i < ds_check.getRowCount(); i++)		{
//					if ( !"X".equals(DatasetUtility.getString(ds_check, i, "STATE")) )	{
//						bCheck = true;	// 부분원가가 존재하는 경우
//						break;
//					}
//				}

				//2013.03.20 부분원가 처리 여부 확인, 부분원가 처리대상만 존재하도록 처리
				boolean bCheck   = false;
				String  keyQtseq = "";
				String  tmpQtseq = "";
				String  tmpState = "";
				HashMap tmpMap = new HashMap();
				
				int iRow = 0;

				Dataset tmpCheck = new Dataset("tmpCheck");
				tmpCheck.addColumn("KEY",   ColumnInfo.COLTYPE_STRING, 10);

				for (int i = 0; i < ds_check.getRowCount(); i++)	{
					keyQtseq = DatasetUtility.getString(ds_check, i, "QTSEQ");
					if ( i == 0)	{
						tmpMap.put(keyQtseq, keyQtseq);
						iRow = tmpCheck.appendRow();
						tmpCheck.setColumn(iRow, "KEY", keyQtseq);
					}else	{
						tmpQtseq = (String) tmpMap.get(keyQtseq);
						if (tmpQtseq == null || "".equals(tmpQtseq) )	{
							tmpMap.put(keyQtseq, keyQtseq);
							iRow = tmpCheck.appendRow();
							tmpCheck.setColumn(iRow, "KEY", keyQtseq);
						}
					}
				}

				for (int ii = 0; ii < tmpCheck.getRowCount(); ii++ )	{
					tmpQtseq = DatasetUtility.getString(tmpCheck, ii, "KEY");
					bCheck = false;

					for (int i = 0; i < ds_check.getRowCount(); i++)	{
						if (tmpQtseq.equals(DatasetUtility.getString(ds_check, i, "QTSEQ")) )	{
							if ( !"X".equals(DatasetUtility.getString(ds_check, i, "STATE")) )		{
								bCheck = true;	// 부분원가가 존재하는 경우
								break;
							}
						}
					}

					if (!bCheck)	{	// 부분원가 재 처리 불필요건
						for (int i = 0; i < ds_check.getRowCount(); i++)	{
							if (tmpQtseq.equals(DatasetUtility.getString(ds_check, i, "QTSEQ")) )	{
								ds_check.deleteRow(i);
							}
						}
					}
				}
				
				service.saveCostUpdate(dsHeader, ds_302, paramVO, session);
			}

			resultVO.addDataset(ds_202);
			resultVO.addDataset(ds_302);
			resultVO.addDataset(ds_check);
			resultVO.addDataset(ds_log);

		} catch (CommRfcException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (RuntimeException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), e.getMessage(), "Y", "Y"); // 환율의 경우에는 메시지 코드 임
		}catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		//System.out.print("\n@@@ ds_202:" + ds_202.getRowCount() + ",  ds_302:" + ds_302.getRowCount() + ",  ds_check:" + ds_check.getRowCount());

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	
	@RequestMapping(value="searchTemplate")
	public View Ses0031TemplateView(MipParameterVO paramVO, Model model) {

		Dataset dsDetail     = paramVO.getDataset("ds_detail");
		Dataset dsTemplate = paramVO.getDataset("ds_template"); // UI로 return할 out dataset

		dsTemplate = templateToDataset(dsDetail, dsTemplate, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			if (dsTemplate != null) {
				resultVO.addDataset(dsTemplate);
			}
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value="searchZ302")
	public View Ses0031Z302View(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsT302    = paramVO.getDataset("ds_t302");

		dsT302 = t302ToDataset(dsHeader, dsT302, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsT302);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value="searchZ309")
	public View Ses0031Z309View(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsT309 = paramVO.getDataset("ds_t309");

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return

		try {
			dsT309 = t309ToDataset(dsCond, dsT309, paramVO);

		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		resultVO.addDataset(dsT309);
		model.addAttribute("resultVO", resultVO);
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		return view;
	}
	
	@RequestMapping(value="searchBlock")
	public View Ses0031BlockNameView(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsBlock   = paramVO.getDataset("ds_block");

		dsBlock = blockToDataset(dsHeader, dsBlock, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsBlock);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value="searchClass")
	public View Ses0031ClassCdView(MipParameterVO paramVO, Model model) {

		Dataset dsClass = paramVO.getDataset("ds_class");

		dsClass = classToDataset(dsClass, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsClass);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value="searchAcapa")
	public View Ses0031AcapaView(MipParameterVO paramVO, Model model) {

		Dataset dsAcapa = paramVO.getDataset("ds_acapa");

		dsAcapa = acapaToDataset(dsAcapa, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsAcapa);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}

/*	2018.03.30  환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start
	@RequestMapping(value="searchExchange")
	public View Ses0031ExchangeView(MipParameterVO paramVO, Model model) {

		Dataset dsExchange = paramVO.getDataset("ds_exchange");

		dsExchange = exchangeToDataset(dsExchange, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsExchange);

			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}

	// 환율 기준 변경 2013.02.20
	@RequestMapping(value="searchNewExchange")
	public View NewExchangeView(MipParameterVO paramVO, Model model) {

		Dataset dsExchange = paramVO.getDataset("ds_exchange");
		Dataset ds_error   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return

		try {
			
			dsExchange = newExchangeToDataset(dsExchange, paramVO);

			resultVO.addDataset(dsExchange);
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	2018.03.30  환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End 	*/
	
	// -----<  견적, 수주 환율 정보 UI로 전송 - 부분원가 관리 (Ses0350) by mj.Shin 		Start
	@RequestMapping(value = "searchExchangeRate")
	public View NewExchangeView(MipParameterVO paramVO, Model model) {

		Dataset dsExchange = paramVO.getDataset("ds_exchange");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
		
		try {
			
			dsExchange = ExchangeRateToDataset(dsExchange, paramVO);
	
			resultVO.addDataset(dsExchange);
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	// ----->  견적, 수주 환율 정보 UI로 전송 - 부분원가 관리 (Ses0350) by mj.Shin 		End
	
	@RequestMapping(value="saveHeader")
	public View Ses0031HeaderView(MipParameterVO paramVO, Model model) {
		
		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return

		try {
			dsHeader = service.saveHeader(paramVO, model, session);

			resultVO.addDataset(dsHeader);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value="saveQtser")
	public View Ses0031SaveQtser(MipParameterVO paramVO, Model model) {
		
		Dataset dsHeader   = paramVO.getDataset("ds_header");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
		
		try {
			dsHeader = service.saveQtser(paramVO, model, session);

			resultVO.addDataset(dsHeader);

		}catch ( RuntimeException re )	{
			re.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, re.getMessage(), re.getMessage(), "Y", "Y");
		}catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}


	@RequestMapping(value="saveDetail")
	public View Ses0031DetailView(MipParameterVO paramVO, Model model) {

		Dataset    dsDetail = paramVO.getDataset("ds_detail");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		
		try { 
//			dsDetail = service.saveDetail(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
			resultVO.addDataset(dsDetail);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value="saveTemplate")
	public View Ses0031SaveTemplate(MipParameterVO paramVO, Model model) {

		Dataset    dsTemplate = paramVO.getDataset("ds_template");
		SqlSession session     = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
//			dsTemplate = service.saveTemplate(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
			resultVO.addDataset(dsTemplate);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value="saveZsdt1054")
	public View Ses0031SaveZsdt1054H(MipParameterVO paramVO, Model model) {

		Dataset    dsZsdt1054 = paramVO.getDataset("ds_check");
		SqlSession session    = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		Dataset    ds_error   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
		try {
			dsZsdt1054 = service.saveZsdt1054(paramVO, model, session);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		resultVO.addDataset(dsZsdt1054);

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="saveZuam")
	public View Ses0031SaveZuam(MipParameterVO paramVO, Model model) {

		Dataset    dsZsdt1054 = paramVO.getDataset("ds_check");		// 부분원가
		Dataset    ds_apply   = paramVO.getDataset("ds_apply");		// 반영원가
		SqlSession session    = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		Dataset    ds_error   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return

		Ses0031ParamVO param = new Ses0031ParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setQtnum(dsZsdt1054.getColumnAsString(0,"QTNUM"));
		param.setQtser(dsZsdt1054.getColumnAsString(0,"QTSER"));
		
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Ses0031ParamVO vo = service.selectEgisFlag(param);
			if("X".equals(vo.getEgisFlag())) {
				dsZsdt1054 = service.saveZuamEgis(paramVO, model, session);
			} else {
				dsZsdt1054 = service.saveZuam(paramVO, model, session);
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), e.getMessage(), "Y", "Y"); // 환율의 경우에는 메시지 코드 임
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		resultVO.addDataset(dsZsdt1054);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value="updateHeaderFlag")
	public View Ses0031UpdateHeaderFlag(MipParameterVO paramVO, Model model) {

		Dataset    dsHeader = paramVO.getDataset("ds_header");
		SqlSession session   = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			dsHeader = service.updateHeaderFlag(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
			resultVO.addDataset(dsHeader);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}	
	
	private Dataset gTypeToDataset(Dataset dsZGtype, MipParameterVO paramVO) {
		Ses0031ParamVO param = new Ses0031ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT

		List<Ses0031> list = service.searchGtype(param);	// 서비스CALL
		dsZGtype.deleteAll();

		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsSo)에 복사
			dsZGtype.appendRow(); 	// 행추가
			dsZGtype.setColumn(i, "MANDT", list.get(i).getMandt());
			dsZGtype.setColumn(i, "GTYPE"  , list.get(i).getGtype());
			dsZGtype.setColumn(i, "GTYPEO", list.get(i).getGtypeo());
		}
		return dsZGtype;
	}

	private Dataset soToDataset(Dataset dsSo, Dataset dsHeader, MipParameterVO paramVO) {
		Ses0031ParamVO param = new Ses0031ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		param.setMandt(DatasetUtility.getString(dsHeader,"MANDT"));	// SAP CLIENT
		param.setSonnr(DatasetUtility.getString(dsHeader,"SONNR"));

/*		// INPUT PARAM INFO --------------------------------------------------------------------------------------------
		System.out.print("\n@@@ dsheader.MANDT ===>"+DatasetUtility.getString(dsHeader,"MANDT")+"\n");
		System.out.print("\n@@@ dsheader.SONNR ===>"+DatasetUtility.getString(dsHeader,"SONNR") +"\n");*/
		
		List<Ses0031> list = service.searchSO(param);	// 서비스CALL
		dsSo.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsSo)에 복사
			dsSo.appendRow(); 	// 행추가
			dsSo.setColumn(i, "MANDT"    , list.get(i).getMandt());
			dsSo.setColumn(i, "SONNR"     , list.get(i).getSonnr());
			dsSo.setColumn(i, "ZPYM"       , list.get(i).getZpym());
			dsSo.setColumn(i, "SPART"      , list.get(i).getSpart());
			dsSo.setColumn(i, "AUART"     , list.get(i).getAuart());
			dsSo.setColumn(i, "MATNR"    , list.get(i).getMatnr());
			dsSo.setColumn(i, "VKBUR"     , list.get(i).getVkbur());
			dsSo.setColumn(i, "VKGRP"     , list.get(i).getVkgrp());
			dsSo.setColumn(i, "ZKUNNR"   , list.get(i).getZkunnr());
//			dsSo.setColumn(i, "GTYPE"      , list.get(i).getGtype());
			dsSo.setColumn(i, "GTYPE"      , list.get(i).getRtype());
			dsSo.setColumn(i, "TYPE1"      , list.get(i).getType1());
			dsSo.setColumn(i, "TYPE2"      , list.get(i).getType2());
			dsSo.setColumn(i, "TYPE3"      , list.get(i).getType3());
			dsSo.setColumn(i, "ZNUMBER", list.get(i).getZnumber());
			dsSo.setColumn(i, "KUNNR"    , list.get(i).getKunnr());
			dsSo.setColumn(i, "GSNAM"    , list.get(i).getGsnam());
			dsSo.setColumn(i, "REGION"    , list.get(i).getRegion());
			dsSo.setColumn(i, "ZDELI"       , list.get(i).getZdeli());
			dsSo.setColumn(i, "SHANGH"   , list.get(i).getShangh());
			dsSo.setColumn(i, "ZINTER"     , list.get(i).getZinter());
			dsSo.setColumn(i, "SOFOCA"    , list.get(i).getSofoca());
			dsSo.setColumn(i, "ZFORE"      , list.get(i).getZfore());
			dsSo.setColumn(i, "WAERK"     , list.get(i).getWaerk());
			dsSo.setColumn(i, "DELDAT"    , list.get(i).getDeldat());
			dsSo.setColumn(i, "ZRMK"      , list.get(i).getZrmk());
			dsSo.setColumn(i, "SOABLE"    , list.get(i).getSoable());
			dsSo.setColumn(i, "SORLT"      , list.get(i).getSorlt());
			dsSo.setColumn(i, "SOPRICE"   , list.get(i).getSoprice());
			dsSo.setColumn(i, "SHANG"     , list.get(i).getShang());
			dsSo.setColumn(i, "ZMPFLG"    , list.get(i).getZmpflg());
			dsSo.setColumn(i, "VBELN"      , list.get(i).getVbeln());
			dsSo.setColumn(i, "ZBPNNR"   , list.get(i).getZbpnnr());
			dsSo.setColumn(i, "CDATE"     , list.get(i).getCdate());
			dsSo.setColumn(i, "CTIME"     , list.get(i).getCtime());
			dsSo.setColumn(i, "CUSER"     , list.get(i).getCuser());
			dsSo.setColumn(i, "UDATE"     , list.get(i).getUdate());
			dsSo.setColumn(i, "UTIME"     , list.get(i).getUtime());
			dsSo.setColumn(i, "UUSER"     , list.get(i).getUuser());
			dsSo.setColumn(i, "DDATE"     , list.get(i).getDdate());
			dsSo.setColumn(i, "DTIME"     , list.get(i).getDtime());
			dsSo.setColumn(i, "DUSER"     , list.get(i).getDuser());
		}
		return dsSo;
	}
	
	private Dataset specToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 

		String mandt   = DatasetUtility.getString(dsCond,"MANDT" );
		String ztplno   = DatasetUtility.getString(dsCond,"ZTPLNO" );
		String mclass   = DatasetUtility.getString(dsCond,"MCLASS");
		String ztplgbn  = DatasetUtility.getString(dsCond,"ZTPLGBN");
		String zrmk     = DatasetUtility.getString(dsCond,"ZRMK");
		String zprdgbn = DatasetUtility.getString(dsCond,"ZPRDGBN");
		String brndcd  = DatasetUtility.getString(dsCond,"BRNDCD");
		String brndseq  = DatasetUtility.getString(dsCond,"BRNDSEQ");
		String qtnum    = DatasetUtility.getString(dsCond,"QTNUM");

		if( brndcd == null) {
			brndcd = "NOBRND";
		} else {
			if( "".equals(brndcd)) {
				brndcd = "NOBRND";
			}
		}

		if( brndseq == null) {
			brndseq = "000";
		} else {
			if( "".equals(brndseq)) {
				brndseq = "000";
			}
		}

		String lang = paramVO.getVariable("G_LANG");
		String spras = "3";

		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";

		if (mandt   == null) mandt    = "";
		if (ztplgbn  == null) ztplgbn   = "";
		if (zrmk     == null) zrmk      = "";
		if (mclass   == null) mclass    = "";
		if (zprdgbn == null) zprdgbn = "";

		param.setMandt(mandt);
		param.setMclass(mclass);
		param.setZtplgbn(ztplgbn);
		param.setZrmk(zrmk);
		param.setZprdgbn(zprdgbn);
		param.setSpras(spras);
		param.setLang(lang);
        
		// 2020 브랜드 입력
		param.setBrndcd(brndcd);
		param.setBrndseq(brndseq);
		param.setQtnum(qtnum);

		if (ztplno == null) {
//			List<Ses0031> minList = service2.searchMinTemplate(param);
//			ztplno = minList.get(0).getZtplno().toString();
		}

		param.setZtplno(ztplno);

		List<Ses0031> list = service.searchSpec(param); // 서비스CALL
		List<Map<String, Object>> list1072 = service.searchZsdt1072(param);
		List<Map<String, Object>> list1073 = service.searchZsdt1073(param);
		dsList.deleteAll();

		String sAtfor = "";
		String sPrd   = "";
		String sPrh   = "";
		String sClassGbn = "";
		
		try	{
			for (int i = 0; i < list.size(); i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "FLAG"      , list.get(i).getFlag());
				dsList.setColumn(i, "MANDT"  , list.get(i).getMandt());
				dsList.setColumn(i, "MCLASS"  , list.get(i).getMclass());
				dsList.setColumn(i, "ATKLA"    , list.get(i).getAtkla());
				
				sAtfor		= list.get(i).getAtfor();
				sPrh		= list.get(i).getPrh();
				sPrd   		= list==null?"":list.get(i).getPrd()==null?"":list.get(i).getPrd();
				sClassGbn	= list==null?"":list.get(i).getClassgbn()==null?"":list.get(i).getClassgbn();
				
				for(int j=0; j < list1072.size(); j++) {
					if(sPrh.equals(list1072.get(j).get("ATNAM")) && sPrd.equals(list1072.get(j).get("ATWRT"))) {
						sPrd = "";
					}
				}
				
				for(int k=0; k < list1073.size(); k++) {
					if(sPrh.equals(list1073.get(k).get("ATNAM")) && sClassGbn.equals(list1073.get(k).get("CLASS")) ) {
						sPrd = "";
					}
				}
				
				if ("NUM".equals(sAtfor))	{
					if (sPrd != null && sPrd.indexOf(",") >= 0 )
					sPrd = sPrd.replaceAll(",", "");
				}
				
				dsList.setColumn(i, "PRH"       , sPrh);
				dsList.setColumn(i, "PRD"       , sPrd);
				dsList.setColumn(i, "ATFOR"     , sAtfor);
				dsList.setColumn(i, "PRHNAME", list.get(i).getPrhname());
				dsList.setColumn(i, "ZTPLSEQ"  , list.get(i).getZtplseq());
				dsList.setColumn(i, "CNT"       , list.get(i).getCnt());
			}
		}catch (Exception e)	{
			e.printStackTrace();
		}

		return dsList;
	}

	private Dataset templateToDataset(Dataset dsDetail, Dataset dsTemplate, MipParameterVO paramVO) {
		Ses0031ParamVO param  = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		String mandt   = "";
		String qtnum   = "";
		String qtser     = "";
		String qtseq    = "";
		String mclass   = "";
		String prh       = "";
		String spras     = "";
		String zprdgbn = "";
		String lang      = paramVO.getVariable("G_LANG");
		int j = 0;

		if (lang == null) lang = "";

		if (lang.equals("en")) spras = "E";
		else                     spras = "3";

		if (dsTemplate != null) dsTemplate.deleteAll();

		for (int a=0; a<dsDetail.getRowCount(); a++) {
			mandt   = DatasetUtility.getString(dsDetail, a, "MANDT" );
			qtnum   = DatasetUtility.getString(dsDetail, a, "QTNUM" );
			qtser     = DatasetUtility.getString(dsDetail, a, "QTSER"  );
			qtseq    = DatasetUtility.getString(dsDetail, a, "QTSEQ"  );
			mclass   = DatasetUtility.getString(dsDetail, a, "MCLASS");
			prh       = DatasetUtility.getString(dsDetail, a, "ATNAM" );
			zprdgbn = DatasetUtility.getString(dsDetail, a, "ZPRDGBN");

			if (mandt == null) mandt = "";
			if (qtnum == null) qtnum = "";
			if (qtser   == null) qtser  = "0";
			if (qtseq  == null) qtseq  = "0";
			if (mclass == null) mclass = "";
			if (prh    == null) prh     = "";
			if (zprdgbn != null) mclass = zprdgbn;

			if (mclass.equals("SHN_01") || mclass.equals("SHN_02")) spras = "3";

			param.setMandt  (mandt);	// SAP CLIENT
			param.setQtnum  (qtnum);
			param.setQtser    (qtser  );
			param.setQtseq   (qtseq  );
			param.setZprdgbn(mclass);
			param.setPrh       (prh   );
			param.setSpras    (spras );
			param.setLang     (lang  );

			List<Ses0031> list = service.searchTemplate(param);	// 서비스CALL

			if ( list.size() == 0 || qtnum.length()==0 ) {
				list = service.searchKsml(param);	// 서비스CALL
				for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsSo)에 복사
					dsTemplate.appendRow(); 	// 행추가
					dsTemplate.setColumn(j, "FLAG"       , list.get(i).getFlag());
					dsTemplate.setColumn(j, "MANDT"   , mandt);
					dsTemplate.setColumn(j, "QTNUM"   , qtnum);
					dsTemplate.setColumn(j, "QTSER"     , qtser);
					dsTemplate.setColumn(j, "QTSEQ"     , qtseq);
					dsTemplate.setColumn(j, "PRSEQ"     , list.get(i).getPosnr());
					dsTemplate.setColumn(j, "MCLASS"   , list.get(i).getMclass());
					dsTemplate.setColumn(j, "ATKLA"     , list.get(i).getAtkla());
					dsTemplate.setColumn(j, "PRH"        , list.get(i).getPrh());
					dsTemplate.setColumn(j, "PRD"        , list.get(i).getPrd());
					dsTemplate.setColumn(j, "ATFOR"     , list.get(i).getAtfor());
					dsTemplate.setColumn(j, "PRHNAME", list.get(i).getPrhname());
					dsTemplate.setColumn(j, "CNT"       , list.get(i).getCnt());
					dsTemplate.setColumn(j, "PCNCD"    , list.get(i).getPcncd());
					j++;
				}
			} else if ( list.size() > 0) {

				int cnt = 0;
				
				for (int iCol=0; iCol<dsTemplate.getColumnCount(); iCol++) {    
					if (dsTemplate.getColumnID(iCol).equals("CNT")) cnt++;
				}
					
				if (cnt == 0) dsTemplate.addColumn("CNT", (short)1, 256);

				for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsSo)에 복사
					dsTemplate.appendRow(); 	// 행추가
					dsTemplate.setColumn(j, "FLAG"      , list.get(i).getFlag());
					dsTemplate.setColumn(j, "MANDT"  , list.get(i).getMandt());
					dsTemplate.setColumn(j, "QTNUM"  , list.get(i).getQtnum());
					dsTemplate.setColumn(j, "QTSER"    , list.get(i).getQtser());
					dsTemplate.setColumn(j, "QTSEQ"    , list.get(i).getQtseq());
					dsTemplate.setColumn(j, "PRSEQ"    , list.get(i).getPrseq());
					dsTemplate.setColumn(j, "ATKLA"    , list.get(i).getAtkla());
					dsTemplate.setColumn(j, "PRH"       , list.get(i).getPrh());
					dsTemplate.setColumn(j, "PRD"       , list.get(i).getPrd());
					dsTemplate.setColumn(j, "PRHNAME", list.get(i).getPrhname());
					dsTemplate.setColumn(j, "ETCH"      , list.get(i).getEtch());
					dsTemplate.setColumn(j, "ETCM"     , list.get(i).getEtcm());
					dsTemplate.setColumn(j, "ZRMK"    , list.get(i).getZrmk());
					dsTemplate.setColumn(j, "CNT"      , list.get(i).getCnt());
					dsTemplate.setColumn(j, "ATFOR"     , list.get(i).getAtfor());
					dsTemplate.setColumn(j, "ATWTB"       , list.get(i).getAtwtb());
					dsTemplate.setColumn(j, "PCNCD"    , list.get(i).getPcncd());
					j++;
				}
			}
		}
		
		return dsTemplate;
	}
	
	private Dataset blockToDataset(Dataset dsHeader, Dataset dsBlock, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 

		String mandt = "";
		String qtnum = "";
		String qtser   = "";
		String qtseq  = "";

		dsBlock.deleteAll();
		
		mandt = DatasetUtility.getString(dsHeader, "MANDT");
		qtnum = DatasetUtility.getString(dsHeader, "QTNUM");
		qtser = DatasetUtility.getString(dsHeader, "QTSER" );

		if (mandt == null) mandt = "";
		if (qtnum == null) qtnum = "";
		if (qtser   == null) qtser  = "0";


		param.setMandt(mandt);
		param.setQtnum(qtnum);
		param.setQtser  (qtser );
			
		List<Ses0031> list = service.searchBlockName(param); // 서비스CALL
			
		for (int i = 0; i < list.size(); i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
			dsBlock.appendRow(); 	// 행추가
			dsBlock.setColumn(i, "NO"            , i+1);
			dsBlock.setColumn(i, "MANDT"      , list.get(i).getMandt());
			dsBlock.setColumn(i, "QTNUM"      , list.get(i).getQtnum());
			dsBlock.setColumn(i, "QTSER"        , list.get(i).getQtser());
			dsBlock.setColumn(i, "QTSEQ"       , list.get(i).getQtseq());
			dsBlock.setColumn(i, "BLSEQ"        , list.get(i).getBlseq());
			dsBlock.setColumn(i, "BDMNG"      , list.get(i).getBdmng());
			dsBlock.setColumn(i, "ZCTOTAL"     , list.get(i).getZctotal());
			dsBlock.setColumn(i, "SALES"        , list.get(i).getSales());
			dsBlock.setColumn(i, "MESSG"       , list.get(i).getMessg());
			dsBlock.setColumn(i, "BLOCKNAME", list.get(i).getBlockname());
			dsBlock.setColumn(i, "MAKTX"       , list.get(i).getMaktx());
		}
		return dsBlock;
	}

	private Dataset classToDataset(Dataset dsClass, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 

		String mandt = paramVO.getVariable("G_MANDT");

		if (mandt == null) mandt = "";

		param.setMandt(mandt);

		List<Ses0031> list = service.searchClass(param); // 서비스CALL

		dsClass.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
			dsClass.appendRow(); 	// 행추가
			dsClass.setColumn(i, "MANDT"   , list.get(i).getMandt());
			dsClass.setColumn(i, "MATNR"   , list.get(i).getMatnr());
			dsClass.setColumn(i, "CLASS_CD", list.get(i).getClass_cd());
		}
		return dsClass;
	}	
	
	private Dataset t302ToDataset(Dataset dsHeader, Dataset dsT302, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 

		String mandt = "";
		String qtnum = "";
		String qtser   = "";

		dsT302.deleteAll();
		
		for (int a=0; a<dsHeader.getRowCount(); a++) {

			mandt = DatasetUtility.getString(dsHeader, a, "MANDT");
			qtnum = DatasetUtility.getString(dsHeader, a, "QTNUM");
			qtser   = DatasetUtility.getString(dsHeader, a, "QTSER" );

			if (mandt == null) mandt = "";
			if (qtnum == null) qtnum = "";
			if (qtser   == null) qtser  = "0";


			param.setMandt(mandt);
			param.setQtnum(qtnum);
			param.setQtser  (qtser );
			
			List<Ses0031> list = service.searchZcobt302(param); // 서비스CALL
			
			for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
				dsT302.appendRow(); 	// 행추가
				dsT302.setColumn(i, "MANDT"  , list.get(i).getMandt());
				dsT302.setColumn(i, "QTNUM"  , list.get(i).getQtnum());
				dsT302.setColumn(i, "QTSER"    , list.get(i).getQtser());
				dsT302.setColumn(i, "QTSEQ"    , list.get(i).getQtseq());
				dsT302.setColumn(i, "MEIN_MH", list.get(i).getMeinh_mh());
				dsT302.setColumn(i, "PRDM01" , list.get(i).getPrdm01());
				dsT302.setColumn(i, "PRDM02" , list.get(i).getPrdm02());
				dsT302.setColumn(i, "PRDM03" , list.get(i).getPrdm03());
				dsT302.setColumn(i, "PRDM04" , list.get(i).getPrdm04());
				dsT302.setColumn(i, "PRDM05" , list.get(i).getPrdm05());
				dsT302.setColumn(i, "PRDA01" , list.get(i).getPrda01());
				dsT302.setColumn(i, "PRDL01" , list.get(i).getPrdl01());
				dsT302.setColumn(i, "PRDL02" , list.get(i).getPrdl02());
				dsT302.setColumn(i, "PRDL03" , list.get(i).getPrdl03());
				dsT302.setColumn(i, "PRDL04" , list.get(i).getPrdl04());
				dsT302.setColumn(i, "PRDL05" , list.get(i).getPrdl05());
				dsT302.setColumn(i, "PRDE01" , list.get(i).getPrde01());
				dsT302.setColumn(i, "PRDE02" , list.get(i).getPrde02());
				dsT302.setColumn(i, "PRDE03" , list.get(i).getPrde03());
				dsT302.setColumn(i, "PRDE04" , list.get(i).getPrde04());
				dsT302.setColumn(i, "PRDE05" , list.get(i).getPrde05());
				dsT302.setColumn(i, "PRDE06" , list.get(i).getPrde06());
				dsT302.setColumn(i, "PRDE07" , list.get(i).getPrde07());
				dsT302.setColumn(i, "PRDE08" , list.get(i).getPrde08());
				dsT302.setColumn(i, "PRDE09" , list.get(i).getPrde09());
				dsT302.setColumn(i, "PRDE10" , list.get(i).getPrde10());
				dsT302.setColumn(i, "PRDE11" , list.get(i).getPrde11());
				dsT302.setColumn(i, "PRDE12" , list.get(i).getPrde12());
				dsT302.setColumn(i, "PRDE13" , list.get(i).getPrde13());
				dsT302.setColumn(i, "PRDE14" , list.get(i).getPrde14());
				dsT302.setColumn(i, "PRDE15" , list.get(i).getPrde15());
				dsT302.setColumn(i, "PRDI01" , list.get(i).getPrdi01());
				dsT302.setColumn(i, "PRDI02" , list.get(i).getPrdi02());
				dsT302.setColumn(i, "PRDI03" , list.get(i).getPrdi03());
				dsT302.setColumn(i, "PRDI04" , list.get(i).getPrdi04());
				dsT302.setColumn(i, "EQMM01" , list.get(i).getEqmm01());
				dsT302.setColumn(i, "EQMM02" , list.get(i).getEqmm02());
				dsT302.setColumn(i, "EQMM03" , list.get(i).getEqmm03());
				dsT302.setColumn(i, "EQMM04" , list.get(i).getEqmm04());
				dsT302.setColumn(i, "EQMM05" , list.get(i).getEqmm05());
				dsT302.setColumn(i, "EQMA01" , list.get(i).getEqma01());
				dsT302.setColumn(i, "EQMA02" , list.get(i).getEqma02());
				dsT302.setColumn(i, "EQML01" , list.get(i).getEqml01());
				dsT302.setColumn(i, "EQML02" , list.get(i).getEqml02());
				dsT302.setColumn(i, "EQML03" , list.get(i).getEqml03());
				dsT302.setColumn(i, "EQML04" , list.get(i).getEqml04());
				dsT302.setColumn(i, "EQML05" , list.get(i).getEqml05());
				dsT302.setColumn(i, "EQME01" , list.get(i).getEqme01());
				dsT302.setColumn(i, "EQME02" , list.get(i).getEqme02());
				dsT302.setColumn(i, "EQME03" , list.get(i).getEqme03());
				dsT302.setColumn(i, "EQME04" , list.get(i).getEqme04());
				dsT302.setColumn(i, "EQME05" , list.get(i).getEqme05());
				dsT302.setColumn(i, "EQME06" , list.get(i).getEqme06());
				dsT302.setColumn(i, "EQME07" , list.get(i).getEqme07());
				dsT302.setColumn(i, "EQME08" , list.get(i).getEqme08());
				dsT302.setColumn(i, "EQME09" , list.get(i).getEqme09());
				dsT302.setColumn(i, "EQME10" , list.get(i).getEqme10());
				dsT302.setColumn(i, "EQME11" , list.get(i).getEqme11());
				dsT302.setColumn(i, "EQME12" , list.get(i).getEqme12());
				dsT302.setColumn(i, "EQME13" , list.get(i).getEqme13());
				dsT302.setColumn(i, "EQME14" , list.get(i).getEqme14());
				dsT302.setColumn(i, "EQME15" , list.get(i).getEqme15());
				dsT302.setColumn(i, "EQME51" , list.get(i).getEqme51());
				dsT302.setColumn(i, "EQME52" , list.get(i).getEqme52());
				dsT302.setColumn(i, "EQME53" , list.get(i).getEqme53());
				dsT302.setColumn(i, "EQME54" , list.get(i).getEqme54());
				dsT302.setColumn(i, "EQME55" , list.get(i).getEqme55());
				dsT302.setColumn(i, "EQME56" , list.get(i).getEqme56());
				dsT302.setColumn(i, "EQME57" , list.get(i).getEqme57());
				dsT302.setColumn(i, "EQME58" , list.get(i).getEqme58());
				dsT302.setColumn(i, "EQME59" , list.get(i).getEqme59());
				dsT302.setColumn(i, "EQME60" , list.get(i).getEqme60());
				dsT302.setColumn(i, "EQME61" , list.get(i).getEqme61());
				dsT302.setColumn(i, "EQME62" , list.get(i).getEqme62());
				dsT302.setColumn(i, "EQME63" , list.get(i).getEqme63());
				dsT302.setColumn(i, "EQME64" , list.get(i).getEqme64());
				dsT302.setColumn(i, "EQME65" , list.get(i).getEqme65());
				dsT302.setColumn(i, "EQME66" , list.get(i).getEqme66());
				dsT302.setColumn(i, "EQME67" , list.get(i).getEqme67());
				dsT302.setColumn(i, "EQME68" , list.get(i).getEqme68());
				dsT302.setColumn(i, "EQME69" , list.get(i).getEqme69());
				dsT302.setColumn(i, "EQME70" , list.get(i).getEqme70());
				dsT302.setColumn(i, "EQME71" , list.get(i).getEqme71());
				dsT302.setColumn(i, "EQME72" , list.get(i).getEqme72());
				dsT302.setColumn(i, "EQME73" , list.get(i).getEqme73());
				dsT302.setColumn(i, "EQME74" , list.get(i).getEqme74());
				dsT302.setColumn(i, "EQME75" , list.get(i).getEqme75());
				dsT302.setColumn(i, "EQME76" , list.get(i).getEqme76());
				dsT302.setColumn(i, "EQME77" , list.get(i).getEqme77());
				dsT302.setColumn(i, "EQME78" , list.get(i).getEqme78());
				dsT302.setColumn(i, "EQME79" , list.get(i).getEqme79());
				dsT302.setColumn(i, "EQME80" , list.get(i).getEqme80());
				dsT302.setColumn(i, "EQME81" , list.get(i).getEqme81());
				dsT302.setColumn(i, "EQME82" , list.get(i).getEqme82());
				dsT302.setColumn(i, "EQME83" , list.get(i).getEqme83());
				dsT302.setColumn(i, "EQME84" , list.get(i).getEqme84());
				dsT302.setColumn(i, "EQME85" , list.get(i).getEqme85());
				dsT302.setColumn(i, "EQME86" , list.get(i).getEqme86());
				dsT302.setColumn(i, "EQME87" , list.get(i).getEqme87());
				dsT302.setColumn(i, "EQME88" , list.get(i).getEqme88());
				dsT302.setColumn(i, "EQME89" , list.get(i).getEqme89());
				dsT302.setColumn(i, "EQME90" , list.get(i).getEqme90());
				dsT302.setColumn(i, "EQME91" , list.get(i).getEqme91());
				dsT302.setColumn(i, "EQME92" , list.get(i).getEqme92());
				dsT302.setColumn(i, "EQME93" , list.get(i).getEqme93());
				dsT302.setColumn(i, "EQME94" , list.get(i).getEqme94());
				dsT302.setColumn(i, "EQME95" , list.get(i).getEqme95());
				dsT302.setColumn(i, "EQME96" , list.get(i).getEqme96());
				dsT302.setColumn(i, "EQME97" , list.get(i).getEqme97());
				dsT302.setColumn(i, "EQME98" , list.get(i).getEqme98());
				dsT302.setColumn(i, "EQME99" , list.get(i).getEqme99());
				dsT302.setColumn(i, "EQME100" , list.get(i).getEqme100());
				dsT302.setColumn(i, "EQME101" , list.get(i).getEqme101());
				dsT302.setColumn(i, "EQME102" , list.get(i).getEqme102());
				dsT302.setColumn(i, "EQME103" , list.get(i).getEqme103());
				dsT302.setColumn(i, "EQME104" , list.get(i).getEqme104());
				dsT302.setColumn(i, "EQME105" , list.get(i).getEqme105());
				dsT302.setColumn(i, "EQME106" , list.get(i).getEqme106());
				dsT302.setColumn(i, "EQME107" , list.get(i).getEqme107());
				dsT302.setColumn(i, "EQME108" , list.get(i).getEqme108());
				dsT302.setColumn(i, "EQME109" , list.get(i).getEqme109());
				dsT302.setColumn(i, "EQME110" , list.get(i).getEqme110());
				dsT302.setColumn(i, "EQME111" , list.get(i).getEqme111());
				dsT302.setColumn(i, "EQME112" , list.get(i).getEqme112());
				dsT302.setColumn(i, "EQME113" , list.get(i).getEqme113());
				dsT302.setColumn(i, "EQME114" , list.get(i).getEqme114());
				dsT302.setColumn(i, "EQME115" , list.get(i).getEqme115());
				dsT302.setColumn(i, "EQME116" , list.get(i).getEqme116());
				dsT302.setColumn(i, "EQME117" , list.get(i).getEqme117());
				dsT302.setColumn(i, "EQME118" , list.get(i).getEqme118());
				dsT302.setColumn(i, "EQME119" , list.get(i).getEqme119());
				dsT302.setColumn(i, "EQME120" , list.get(i).getEqme120());
				dsT302.setColumn(i, "EQMI01" , list.get(i).getEqmi01());
				dsT302.setColumn(i, "EQMI02" , list.get(i).getEqmi02());
				dsT302.setColumn(i, "EQMI03" , list.get(i).getEqmi03());
				dsT302.setColumn(i, "EQMO01" , list.get(i).getEqmo01());
				dsT302.setColumn(i, "EQMO02" , list.get(i).getEqmo02());
				dsT302.setColumn(i, "EQMO03" , list.get(i).getEqmo03());
				dsT302.setColumn(i, "EQMO04" , list.get(i).getEqmo04());
				dsT302.setColumn(i, "EQMO05" , list.get(i).getEqmo05());
				dsT302.setColumn(i, "EQMO06" , list.get(i).getEqmo06());
				dsT302.setColumn(i, "EQMO07" , list.get(i).getEqmo07());
				dsT302.setColumn(i, "EQMO08" , list.get(i).getEqmo08());
				dsT302.setColumn(i, "EQMO09" , list.get(i).getEqmo09());
				dsT302.setColumn(i, "EQMO10" , list.get(i).getEqmo10());
				dsT302.setColumn(i, "EQMO11" , list.get(i).getEqmo11());
				dsT302.setColumn(i, "EQMO12" , list.get(i).getEqmo12());
				dsT302.setColumn(i, "EQMO13" , list.get(i).getEqmo13());
				dsT302.setColumn(i, "EQMO14" , list.get(i).getEqmo14());
				dsT302.setColumn(i, "EQMO15" , list.get(i).getEqmo15());
				dsT302.setColumn(i, "EQMO16" , list.get(i).getEqmo16());
				dsT302.setColumn(i, "EQMO17" , list.get(i).getEqmo17());
				dsT302.setColumn(i, "EQMO18" , list.get(i).getEqmo18());
				dsT302.setColumn(i, "EQMO19" , list.get(i).getEqmo19());
				dsT302.setColumn(i, "EQMO20" , list.get(i).getEqmo20());
				dsT302.setColumn(i, "EQMO21" , list.get(i).getEqmo21());
				dsT302.setColumn(i, "EQMO22" , list.get(i).getEqmo22());
				dsT302.setColumn(i, "EQMO23" , list.get(i).getEqmo23());
				dsT302.setColumn(i, "EQMO24" , list.get(i).getEqmo24());
				dsT302.setColumn(i, "EQMO25" , list.get(i).getEqmo25());
				dsT302.setColumn(i, "EQMO26" , list.get(i).getEqmo26());
				dsT302.setColumn(i, "EQMO27" , list.get(i).getEqmo27());
				dsT302.setColumn(i, "EQMO28" , list.get(i).getEqmo28());
				dsT302.setColumn(i, "EQMO29" , list.get(i).getEqmo29());
				dsT302.setColumn(i, "EQMO30" , list.get(i).getEqmo30());
				dsT302.setColumn(i, "EQMO31" , list.get(i).getEqmo31());
				dsT302.setColumn(i, "EQMO32" , list.get(i).getEqmo32());
				dsT302.setColumn(i, "EQMO33" , list.get(i).getEqmo33());
				dsT302.setColumn(i, "EQMO34" , list.get(i).getEqmo34());
				dsT302.setColumn(i, "EQMO35" , list.get(i).getEqmo35());
				dsT302.setColumn(i, "EQMO36" , list.get(i).getEqmo36());
				dsT302.setColumn(i, "EQMO37" , list.get(i).getEqmo37());
				dsT302.setColumn(i, "EQMO38" , list.get(i).getEqmo38());
				dsT302.setColumn(i, "EQMO39" , list.get(i).getEqmo39());
				dsT302.setColumn(i, "EQMO40" , list.get(i).getEqmo40());
				dsT302.setColumn(i, "CTSM01" , list.get(i).getCtsm01());
				dsT302.setColumn(i, "CTSM02" , list.get(i).getCtsm02());
				dsT302.setColumn(i, "CTSM03" , list.get(i).getCtsm03());
				dsT302.setColumn(i, "CTSM04" , list.get(i).getCtsm04());
				dsT302.setColumn(i, "CTSM05" , list.get(i).getCtsm05());
				dsT302.setColumn(i, "CTSM06" , list.get(i).getCtsm06());
				dsT302.setColumn(i, "CTSM07" , list.get(i).getCtsm07());
				dsT302.setColumn(i, "CTSM08" , list.get(i).getCtsm08());
				dsT302.setColumn(i, "CTSM09" , list.get(i).getCtsm09());
				dsT302.setColumn(i, "CTSM10" , list.get(i).getCtsm10());
				dsT302.setColumn(i, "CTSM11" , list.get(i).getCtsm11());
				dsT302.setColumn(i, "CTSM12" , list.get(i).getCtsm12());
				dsT302.setColumn(i, "CTSM13" , list.get(i).getCtsm13());
				dsT302.setColumn(i, "CTSM14" , list.get(i).getCtsm14());
				dsT302.setColumn(i, "CTSM15" , list.get(i).getCtsm15());
				dsT302.setColumn(i, "CTSA01" , list.get(i).getCtsa01());
				dsT302.setColumn(i, "CTSL01" , list.get(i).getCtsl01());
				dsT302.setColumn(i, "CTSL02" , list.get(i).getCtsl02());
				dsT302.setColumn(i, "CTSL03" , list.get(i).getCtsl03());
				dsT302.setColumn(i, "CTSL04" , list.get(i).getCtsl04());
				dsT302.setColumn(i, "CTSL05" , list.get(i).getCtsl05());
				dsT302.setColumn(i, "CTSE01" , list.get(i).getCtse01());
				dsT302.setColumn(i, "CTSE02" , list.get(i).getCtse02());
				dsT302.setColumn(i, "CTSE03" , list.get(i).getCtse03());
				dsT302.setColumn(i, "CTSE04" , list.get(i).getCtse04());
				dsT302.setColumn(i, "CTSE05" , list.get(i).getCtse05());
				dsT302.setColumn(i, "CTSI01" , list.get(i).getCtsi01());
				dsT302.setColumn(i, "CTSI02" , list.get(i).getCtsi02());
				dsT302.setColumn(i, "CTSI03" , list.get(i).getCtsi03());
				dsT302.setColumn(i, "FDSP01" , list.get(i).getFdsp01());
				dsT302.setColumn(i, "FDSP02" , list.get(i).getFdsp02());
				dsT302.setColumn(i, "FDSP03" , list.get(i).getFdsp03());
				dsT302.setColumn(i, "FDSP04" , list.get(i).getFdsp04());
				dsT302.setColumn(i, "FDSP05" , list.get(i).getFdsp05());
				dsT302.setColumn(i, "FDSP06" , list.get(i).getFdsp06());
				dsT302.setColumn(i, "FDSP07" , list.get(i).getFdsp07());
				dsT302.setColumn(i, "FDSP08" , list.get(i).getFdsp08());
				dsT302.setColumn(i, "FDSP09" , list.get(i).getFdsp09());
				dsT302.setColumn(i, "FDSP10" , list.get(i).getFdsp10());
				dsT302.setColumn(i, "FDSP11" , list.get(i).getFdsp11());
				dsT302.setColumn(i, "FDS01"  , list.get(i).getFds01());
				dsT302.setColumn(i, "FDS02"  , list.get(i).getFds02());
				dsT302.setColumn(i, "FDS03"  , list.get(i).getFds03());
				dsT302.setColumn(i, "FDS04"  , list.get(i).getFds04());
				dsT302.setColumn(i, "FDS05"  , list.get(i).getFds05());
				dsT302.setColumn(i, "FDS06"  , list.get(i).getFds06());
				dsT302.setColumn(i, "FDS07"  , list.get(i).getFds07());
				dsT302.setColumn(i, "FDS08"  , list.get(i).getFds08());
				dsT302.setColumn(i, "FDS09"  , list.get(i).getFds09());
				dsT302.setColumn(i, "FDS10"  , list.get(i).getFds10());
				dsT302.setColumn(i, "ADDT01" , list.get(i).getAddt01());
			}
		}
		return dsT302;
	}

	private Dataset t309ToDataset(Dataset dsCond, Dataset dsT309, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 

		String mandt = DatasetUtility.getString(dsCond, 0, "MANDT");
		String qtnum = DatasetUtility.getString(dsCond, 0, "QTNUM");
		String qtser = DatasetUtility.getString(dsCond, 0, "QTSER");
		String qtseq = DatasetUtility.getString(dsCond, 0, "QTSEQ");
		String pspid = DatasetUtility.getString(dsCond, 0, "PSPID");
		String posid = DatasetUtility.getString(dsCond, 0, "POSID");

		if (mandt == null) mandt = paramVO.getVariable("G_MANDT");
		if (qtnum == null) qtnum = "";
		if (qtser == null) qtser = "";
		if (qtseq == null) qtseq = "";							// 예상원가산출에서 호출시에만 setting 
		if (pspid == null || "".equals(pspid)) pspid   = " ";	// 견적 부분원가 조회 시 필요 ' '
		if (posid == null) posid   = "";

		param.setMandt(mandt);
		param.setQtnum(qtnum);
		param.setQtser(qtser);
		param.setQtseq(qtseq);
		param.setPspid(pspid);
		param.setPosid(posid);

		dsT309.deleteAll();
			
		List<Ses0031> list = service.searchZcobt309(param); // 서비스CALL

		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
			dsT309.appendRow(); 	// 행추가
			dsT309.setColumn(i, "FLAG"   , list.get(i).getFlag());
			dsT309.setColumn(i, "MANDT", list.get(i).getMandt());
			dsT309.setColumn(i, "QTNUM", list.get(i).getQtnum());
			dsT309.setColumn(i, "QTSER"  , list.get(i).getQtser());
			dsT309.setColumn(i, "QTSEQ" , list.get(i).getQtseq());
			dsT309.setColumn(i, "PSPID"   , list.get(i).getPspid());
			dsT309.setColumn(i, "POSID"  , list.get(i).getPosid());
			dsT309.setColumn(i, "ATYPE"  , list.get(i).getAtype());
			dsT309.setColumn(i, "GUBUN", list.get(i).getGubun());
			dsT309.setColumn(i, "BLSEQ" , list.get(i).getBlseq());
			dsT309.setColumn(i, "BLNUM", list.get(i).getBlnum());
			dsT309.setColumn(i, "BLNAM", list.get(i).getBlnam());
			dsT309.setColumn(i, "MESSG", list.get(i).getMessg());
			dsT309.setColumn(i, "STATE" , list.get(i).getState());
			dsT309.setColumn(i, "SERIA" , list.get(i).getSeria());
			dsT309.setColumn(i, "ZITEM" , list.get(i).getZitem());
			dsT309.setColumn(i, "ZCHAR" , list.get(i).getZchar());
			dsT309.setColumn(i, "ZQUTY" , list.get(i).getZquty());
			dsT309.setColumn(i, "ZAMNT", list.get(i).getZamnt());
			dsT309.setColumn(i, "ZTOTL" , list.get(i).getZtotl());
			dsT309.setColumn(i, "BIGO"  , list.get(i).getBigo());
			dsT309.setColumn(i, "TEX1"  , list.get(i).getTex1());
			dsT309.setColumn(i, "TEX2"  , list.get(i).getTex2());
			dsT309.setColumn(i, "ADDT01", list.get(i).getAddt01());
			// dsT309.setColumn(i, "VKBUR", list.get(i).getVkbur());
			// dsT309.setColumn(i, "VKGRP", list.get(i).getVkgrp());
			// dsT309.setColumn(i, "ZKUNNR", list.get(i).getZkunnr());
			// dsT309.setColumn(i, "SHANGH", list.get(i).getShangh());

			// 2020 브랜드 입력화
			dsT309.setColumn(i, "BRNDNM", list.get(i).getBrndnm());
		}
		return dsT309;
	}

	private Dataset acapaToDataset(Dataset dsAcapa, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 

		String mandt = DatasetUtility.getString(dsAcapa, 0, "MANDT");
		String atinn  = DatasetUtility.getString(dsAcapa, 0, "ATINN");
		String spras  = DatasetUtility.getString(dsAcapa, 0, "SPRAS");

		if (mandt == null) mandt = paramVO.getVariable("G_MANDT");
		if (atinn  == null)  atinn  = "";
		if (spras  == null)  spras = "";

		param.setMandt(mandt);
		param.setAtinn(atinn);
		param.setSpras(spras);

		List<Ses0031> list = service.searchAcapa(param); // 서비스CALL

		dsAcapa.deleteAll();
		 
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
			dsAcapa.appendRow(); 	// 행추가
			dsAcapa.setColumn(i, "MANDT" , list.get(i).getMandt());
			dsAcapa.setColumn(i, "ATINN"  , list.get(i).getAtinn());
			dsAcapa.setColumn(i, "ATZHL"  , list.get(i).getAtzhl());
			dsAcapa.setColumn(i, "ATWRT"  , list.get(i).getAtwrt());
			dsAcapa.setColumn(i, "ZACAPA", list.get(i).getZacapa());
			dsAcapa.setColumn(i, "SPRAS"  , list.get(i).getSpras());
		}
		return dsAcapa;
	}

/* -----<	2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start
	private Dataset exchangeToDataset(Dataset dsExchange, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 

		String mandt  = DatasetUtility.getString(dsExchange, 0, "MANDT");
		String zyear    = DatasetUtility.getString(dsExchange, 0, "ZYEAR");
		String zmonth = DatasetUtility.getString(dsExchange, 0, "ZMONTH");

		if (mandt  == null) mandt  = paramVO.getVariable("G_MANDT");
		if (zyear   == null)  zyear   = "";
		if (zmonth == null) zmonth = "";

		param.setMandt(mandt);
		param.setZyear(zyear);
		param.setZmonth(zmonth);

		List<Ses0031> list = service.searchExchange(param); // 서비스CALL

		dsExchange.deleteAll();

		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
			dsExchange.appendRow(); 	// 행추가
			dsExchange.setColumn(i, "MANDT" , list.get(i).getMandt());
			dsExchange.setColumn(i, "ZYEAR"   , list.get(i).getZyear() );
			dsExchange.setColumn(i, "ZMONTH", list.get(i).getZmonth());
			dsExchange.setColumn(i, "KRWUSD", list.get(i).getKrwusd());
			dsExchange.setColumn(i, "KRWJPY" , list.get(i).getKrwjpy());
			dsExchange.setColumn(i, "KRWEUR" , list.get(i).getKrweur());
		}
		return dsExchange;
	}

	private Dataset newExchangeToDataset(Dataset dsExchange, MipParameterVO paramVO)	throws Exception{

		try	{
			Ses0031ParamVO param = new Ses0031ParamVO();

			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); // Session GET
			//service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 
	
			String mandt  = DatasetUtility.getString(dsExchange, 0, "MANDT");
			String sdate  = DatasetUtility.getString(dsExchange, 0, "SDATE");
			String f_flag = DatasetUtility.getString(dsExchange, 0, "F_FLAG");
	
			if (mandt  == null) mandt  = paramVO.getVariable("G_MANDT");
			if (sdate  == null) sdate  = "";
			if (f_flag == null) f_flag = "";
	
			param.setMandt(mandt);
			param.setSdate(sdate);
			param.setF_flag(f_flag);
	
			Ses0031 exchangeVO = service.searchNewExchange(param, session, paramVO.getVariable("G_LANG")); // 서비스CALL
	
			dsExchange.deleteAll();

			if (exchangeVO != null )	{
				dsExchange.appendRow(); 	// 행추가
				dsExchange.setColumn(0, "MANDT" , exchangeVO.getMandt());
				dsExchange.setColumn(0, "KRWUSD", exchangeVO.getKrwusd());
				dsExchange.setColumn(0, "KRWJPY", exchangeVO.getKrwjpy());
				dsExchange.setColumn(0, "KRWEUR", exchangeVO.getKrweur());
			}
			return dsExchange;
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End 	*/
	
	private Dataset ExchangeRateToDataset(Dataset dsExchange, MipParameterVO paramVO) throws Exception {
		try {
			
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); // Session GET

			ExchangeVO exchangeRate = null;
			ExchangeVO paramExchangeVO = new ExchangeVO();			
			paramExchangeVO.setMandt(DatasetUtility.getString(dsExchange, 0, "MANDT"));
			paramExchangeVO.setKurst(DatasetUtility.getString(dsExchange, 0, "KURST"));
			paramExchangeVO.setBasedt(DatasetUtility.getString(dsExchange, 0, "BASEDT"));
			paramExchangeVO.setFcurr(DatasetUtility.getString(dsExchange, 0, "FCURR"));
			paramExchangeVO.setTcurr(DatasetUtility.getString(dsExchange, 0, "TCURR"));
			
			exchangeRate = ExchangeS.searchExchangeRate(paramExchangeVO);

			dsExchange.deleteAll();

			if (exchangeRate != null) {
				dsExchange.appendRow();
				dsExchange.setColumn(0, "MANDT", exchangeRate.getMandt());				
				dsExchange.setColumn(0, "KURST", exchangeRate.getKurst());
				dsExchange.setColumn(0, "FCURR", exchangeRate.getFcurr());
				dsExchange.setColumn(0, "TCURR", exchangeRate.getTcurr());
				dsExchange.setColumn(0, "FRDAT", exchangeRate.getFrdat());
				dsExchange.setColumn(0, "TODAT", exchangeRate.getTodat());
				dsExchange.setColumn(0, "UKURS", exchangeRate.getUkurs());
			}
			return dsExchange;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/*------------------------------------------------------------
	 *  메일전송(sendmail)
	 ------------------------------------------------------------*/
	@RequestMapping(value="sendmail")
	public View Ses0243sendMail(MipParameterVO paramVO, Model model) { 
  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");	                // 오류정보 DATASET GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); // Session GET

		service.createDao(session);	 // DAO Create

		try {
			service.sendMailheader(paramVO, model, session);	// 저장 호출

		} catch (Exception e ) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");	// 호출결과  
		}
		
		MipResultVO resultVO = new MipResultVO(); 

		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}

	@RequestMapping(value="searchKunnr")
	public View FindSearchKunnr(MipParameterVO paramVO, Model model) {

		Dataset ds_kunnr = paramVO.getDataset("ds_kunnr");

		MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return

		try {
			
			Ses0031ParamVO param = new Ses0031ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성 

			String mandt      = paramVO.getVariable("G_MANDT");
			String paramKunnr = paramVO.getVariable("paramKunnr");

			param.setMandt(mandt);
			param.setKunnr(paramKunnr);

			Ses0031 kunnrVO = service.searchKunnr(param); // 서비스CALL

			ds_kunnr.deleteAll();

			if (kunnrVO != null) 	{
				ds_kunnr.appendRow(); 	// 행추가

				ds_kunnr.setColumn(0, "MANDT" ,   kunnrVO.getMandt());
				ds_kunnr.setColumn(0, "KUNNR" ,   kunnrVO.getKunnr());
				ds_kunnr.setColumn(0, "LAND1_NM", kunnrVO.getLand1_nm());
				ds_kunnr.setColumn(0, "LAND1",    kunnrVO.getLand1());
				ds_kunnr.setColumn(0, "NAME1",    kunnrVO.getName1());
				ds_kunnr.setColumn(0, "TELF1",    kunnrVO.getTelf1());
				ds_kunnr.setColumn(0, "TELFX",    kunnrVO.getTelfx());
			}

			resultVO.addDataset(ds_kunnr);

			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value="saveZuamReject")
	public View Ses0350SaveZuamReject(MipParameterVO paramVO, Model model) {

		Dataset    ds_reject = paramVO.getDataset("ds_reject");
		SqlSession session   = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));	// Session GET
		Dataset    ds_error  = CallSAPHdel.makeErrorInfoCreateDs("ds_error");				// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return

		try {
			service.saveZuamReject(paramVO, model, session);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		resultVO.addDataset(ds_reject);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	/*------------------------------------------------------------
	 *  도면생성 flag
	 ------------------------------------------------------------*/
	@RequestMapping(value="updateDwgFlag")

	public View Ses0031UpdateDwgFlag(MipParameterVO paramVO, Model model) {

		Dataset    dsHeader = paramVO.getDataset("ds_header");
		SqlSession session   = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		
        int dwg;
		String mandt = "";
		String qtnum = "";
		String qtser   = "";
		
		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(session); // DAO생성
		
		try {

			for (int i=0; i<dsHeader.getRowCount(); i++) {

				mandt = DatasetUtility.getString(dsHeader, i, "MANDT" );
				qtnum = DatasetUtility.getString(dsHeader, i, "QTNUM");
				qtser   = DatasetUtility.getString(dsHeader, i, "QTSER"  );

				if (mandt == null) mandt  = paramVO.getVariable("G_MANDT");
				if (qtnum == null) qtnum  = "";
				if (qtser   == null) qtser    = "0";


				param.setMandt(mandt);	 // SAP CLIENT
				param.setQtnum(qtnum);
				param.setQtser(qtser);

				dwg = service.updateDwgFlag(param);
			}
			MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
			resultVO.addDataset(dsHeader);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}	


}