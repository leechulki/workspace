package hdel.sd.ses.control;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.sap.domain.Spras;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.service.Ses0031NS;
import tit.util.DatasetUtility;
import webservice.stub.sap.SAPStub;
import webservice.stub.sap.ZWEB_SD_IS_SPECIAL_USERStub;

@Controller
@RequestMapping("ses0031n")
public class Ses0031NC {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Ses0031NS service;

	@Autowired
	private ExchangeS ExchangeS;
	
//	@Autowired
//	private Ses0020S service2;


	/*-----------------------------------------------------
	 *  견적상세 초기화 작업
	 ------------------------------------------------------*/
	@RequestMapping(value = "init")
	public View Ses0031NInit(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		//ZWEB_SD_IS_SPECIAL_USERStub stub;
		//ZWEB_SD_IS_SPECIAL_USERStub.ZWEB_SD_IS_SPECIAL_USER param;
		//ZWEB_SD_IS_SPECIAL_USERStub.ZWEB_SD_IS_SPECIAL_USERResponse response;

		//stub = (ZWEB_SD_IS_SPECIAL_USERStub) CallSAP.createStub(ZWEB_SD_IS_SPECIAL_USERStub.class,
		//		paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"));

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		try {
			service.createDao(session);
			resultVO = service.searchInit(paramVO, session);

			//param = new ZWEB_SD_IS_SPECIAL_USERStub.ZWEB_SD_IS_SPECIAL_USER();
			//param.setIV_CLSNO(functions.rfc.sap.document.sap_com.ZWEB_SD_IS_SPECIAL_USERStub.Numeric5.Factory.fromString("00001", ""));
			//param.setIV_SLGID(paramVO.getVariable("G_USER_ID"));

			//response = stub.zWEB_SD_IS_SPECIAL_USER(param);

			//resultVO.addVariable("permitted", response.getEV_RIGHT().toString());

		} catch (Exception e) {
			logger.error("@@@ Ses0031NInit Exception ERROR!!!");
			e.printStackTrace();
		}
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	/*-----------------------------------------------------
	 *  견적상세 사양 조회
	 ------------------------------------------------------*/
	@RequestMapping(value = "searchTemplate")
	public View Ses0031NTemplateView(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		try {
			// DAO생성
			service.createDao(session);

			// 서비스호출
			resultVO = service.searchTemplate(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ Ses0031NTemplateView Exception ERROR!!!");
			e.printStackTrace();
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	/*-----------------------------------------------------
	 *  견적상세 사양 추가 조회
	 ------------------------------------------------------*/
	@RequestMapping(value = "searchAddTemplate")
	public View Ses0031NAddTemplateView(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();

		try {
			// 서비스호출
			resultVO = service.searchAddTemplate(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0031NAddTemplateView Exception ERROR!!!");
			e.printStackTrace();
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	/*-----------------------------------------------------
	 *  견적사양 조회(팝업)
	 ------------------------------------------------------*/
	@RequestMapping(value = "search31NP")
	public View Ses0031NTemplatePopView(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();

		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 서비스호출
			resultVO = service.searchTemplatePop(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0031NTemplatePopView Exception ERROR!!!");
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "searchCost")
	public View Ses0031CostView(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.searchCost(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ Ses0031N searchCost Exception ERROR!!!");
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value = "restrictionCondi")
	public View Ses0031RestrictionCondi(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.restrictionCondi(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ Ses0031N restrictionCondi Exception ERROR!!!");
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value = "searchGtype")
	public View Ses0031FindGTypeView(MipParameterVO paramVO, Model model) {

		Dataset ds_gtype = paramVO.getDataset("ds_gtype"); // UI로 return할 out dataset
		Dataset ds_error = paramVO.getDataset("ds_error"); // UI로 return할 오류메세지 dataset

		try {
			List<ZGType> list = ZGTypes.find(); // 기종목록 조회

			for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사

				ds_gtype.appendRow(); // 행추가
				ds_gtype.setColumn(i, "gtype", list.get(i).getZgtype()); // 기종
				ds_gtype.setColumn(i, "auart", list.get(i).getAuart()); // 판매문서유형(오더유형)
				ds_gtype.setColumn(i, "matnr", list.get(i).getMatnr()); // 자재번호
				ds_gtype.setColumn(i, "spart", list.get(i).getSpart()); // 제품군
				ds_gtype.setColumn(i, "zprdgbn", list.get(i).getZprdgbn()); // 템플릿구분
			}
			MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
			ds_gtype.setId("ds_gtype");
			resultVO.addDataset(ds_gtype);
			// System.out.println("\n@@@ ds_gtype.getRowCount ===>" + ds_gtype.getRowCount()
			// + "\n");
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "searchZGtype")
	public View Ses0031ZsdtGTypeView(MipParameterVO paramVO, Model model) {

		Dataset dsZGtype = paramVO.getDataset("ds_zgtype"); // UI로 return할 out dataset
		Dataset ds_error = paramVO.getDataset("ds_error"); // UI로 return할 오류메세지 dataset

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

	@RequestMapping(value = "searchSo")
	public View Ses0032HeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsSo = paramVO.getDataset("ds_so"); // UI로 return할 out dataset

		dsSo = soToDataset(dsSo, dsHeader, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsSo);
			// System.out.println("============== ds_so.getRowCount() " +
			// dsSo.getRowCount());
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "searchSpec")
	public View Ses0020FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_temp"); // UI로 return할 out dataset

		dsList = specToDataset(dsList, dsCond, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	@RequestMapping(value = "searchZ302")
	public View Ses0031Z302View(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsT302 = paramVO.getDataset("ds_t302");

		dsT302 = t302ToDataset(dsHeader, dsT302, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsT302);
			// System.out.println("============== dsT302.getRowCount() " +
			// dsT302.getRowCount());
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "searchZ309")
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

	@RequestMapping(value = "searchBlock")
	public View Ses0031BlockNameView(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsBlock = paramVO.getDataset("ds_block");

		dsBlock = blockToDataset(dsHeader, dsBlock, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsBlock);
			// System.out.println("============== dsBlock.getRowCount() " +
			// dsBlock.getRowCount());
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

/*	2018.03.06 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start
	@RequestMapping(value = "searchExchange")
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
	@RequestMapping(value = "searchNewExchange")
	public View NewExchangeView(MipParameterVO paramVO, Model model) {

		Dataset dsExchange = paramVO.getDataset("ds_exchange");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

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

	2018.03.06 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End 	*/
	
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
	
	@RequestMapping(value = "saveHeader")
	public View Ses0031HeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return

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
	
	@RequestMapping(value = "saveQtser")
	public View Ses0031SaveQtser(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return

		try {
			dsHeader = service.saveQtser(paramVO, model, session);

			resultVO.addDataset(dsHeader);

		} catch (RuntimeException re) {
			re.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, re.getMessage(), re.getMessage(), "Y", "Y");
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value = "saveDetail")
	public View Ses0031DetailView(MipParameterVO paramVO, Model model) {

		Dataset dsDetail = paramVO.getDataset("ds_detail");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			// dsDetail = service.saveDetail(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
			// System.out.println("============== dsDetail.getRowCount() " +
			// dsDetail.getRowCount());
			resultVO.addDataset(dsDetail);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "saveTemplate")
	public View Ses0031SaveTemplate(MipParameterVO paramVO, Model model) {

		Dataset dsTemplate = paramVO.getDataset("ds_template");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			// dsTemplate = service.saveTemplate(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
			// System.out.println("============== dsTemplate.getRowCount() " +
			// dsTemplate.getRowCount());
			resultVO.addDataset(dsTemplate);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "saveZsdt1054")
	public View Ses0031SaveZsdt1054H(MipParameterVO paramVO, Model model) {

		Dataset dsZsdt1054 = paramVO.getDataset("ds_check");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
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

	@RequestMapping(value = "saveZuam")
	public View Ses0031SaveZuam(MipParameterVO paramVO, Model model) {

		Dataset dsZsdt1054 = paramVO.getDataset("ds_check"); // 부분원가
		Dataset ds_apply = paramVO.getDataset("ds_apply"); // 반영원가
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return

		try {
			dsZsdt1054 = service.saveZuam(paramVO, model, session);
		} catch (RuntimeException e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), e.getMessage(), "Y", "Y"); // 환율의 경우에는
																											// 메시지 코드 임
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

	@RequestMapping(value = "updateHeaderFlag")
	public View Ses0031UpdateHeaderFlag(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			dsHeader = service.updateHeaderFlag(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return

			resultVO.addDataset(dsHeader);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "saveLog")
	public View Ses0031BSaveCallLog(MipParameterVO paramVO, Model model) {

		Dataset dsCallLog = paramVO.getDataset("ds_call_log");	
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return

		try {
			dsCallLog = service.saveLog(paramVO, model, session);

			resultVO.addDataset(dsCallLog);
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}	
	
	private Dataset gTypeToDataset(Dataset dsZGtype, MipParameterVO paramVO) {
		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		param.setMandt(paramVO.getVariable("G_MANDT")); // SAP CLIENT

		List<Ses0031> list = service.searchGtype(param); // 서비스CALL
		dsZGtype.deleteAll();

		for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsSo)에 복사
			dsZGtype.appendRow(); // 행추가
			dsZGtype.setColumn(i, "MANDT", list.get(i).getMandt());
			dsZGtype.setColumn(i, "GTYPE", list.get(i).getGtype());
			dsZGtype.setColumn(i, "GTYPEO", list.get(i).getGtypeo());
		}
		return dsZGtype;
	}

	private Dataset soToDataset(Dataset dsSo, Dataset dsHeader, MipParameterVO paramVO) {
		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		param.setMandt(DatasetUtility.getString(dsHeader, "MANDT")); // SAP CLIENT
		param.setSonnr(DatasetUtility.getString(dsHeader, "SONNR"));

		/*
		 * // INPUT PARAM INFO
		 * -----------------------------------------------------------------------------
		 * ---------------
		 * System.out.print("\n@@@ dsheader.MANDT ===>"+DatasetUtility.getString(
		 * dsHeader,"MANDT")+"\n");
		 * System.out.print("\n@@@ dsheader.SONNR ===>"+DatasetUtility.getString(
		 * dsHeader,"SONNR") +"\n");
		 */

		List<Ses0031> list = service.searchSO(param); // 서비스CALL
		dsSo.deleteAll();

		for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsSo)에 복사
			dsSo.appendRow(); // 행추가
			dsSo.setColumn(i, "MANDT", list.get(i).getMandt());
			dsSo.setColumn(i, "SONNR", list.get(i).getSonnr());
			dsSo.setColumn(i, "ZPYM", list.get(i).getZpym());
			dsSo.setColumn(i, "SPART", list.get(i).getSpart());
			dsSo.setColumn(i, "AUART", list.get(i).getAuart());
			dsSo.setColumn(i, "MATNR", list.get(i).getMatnr());
			dsSo.setColumn(i, "VKBUR", list.get(i).getVkbur());
			dsSo.setColumn(i, "VKGRP", list.get(i).getVkgrp());
			dsSo.setColumn(i, "ZKUNNR", list.get(i).getZkunnr());
			// dsSo.setColumn(i, "GTYPE" , list.get(i).getGtype());
			dsSo.setColumn(i, "GTYPE", list.get(i).getRtype());
			dsSo.setColumn(i, "TYPE1", list.get(i).getType1());
			dsSo.setColumn(i, "TYPE2", list.get(i).getType2());
			dsSo.setColumn(i, "TYPE3", list.get(i).getType3());
			dsSo.setColumn(i, "ZNUMBER", list.get(i).getZnumber());
			dsSo.setColumn(i, "KUNNR", list.get(i).getKunnr());
			dsSo.setColumn(i, "GSNAM", list.get(i).getGsnam());
			dsSo.setColumn(i, "REGION", list.get(i).getRegion());
			dsSo.setColumn(i, "ZDELI", list.get(i).getZdeli());
			dsSo.setColumn(i, "SHANGH", list.get(i).getShangh());
			dsSo.setColumn(i, "ZINTER", list.get(i).getZinter());
			dsSo.setColumn(i, "SOFOCA", list.get(i).getSofoca());
			dsSo.setColumn(i, "ZFORE", list.get(i).getZfore());
			dsSo.setColumn(i, "WAERK", list.get(i).getWaerk());
			dsSo.setColumn(i, "DELDAT", list.get(i).getDeldat());
			dsSo.setColumn(i, "ZRMK", list.get(i).getZrmk());
			dsSo.setColumn(i, "SOABLE", list.get(i).getSoable());
			dsSo.setColumn(i, "SORLT", list.get(i).getSorlt());
			dsSo.setColumn(i, "SOPRICE", list.get(i).getSoprice());
			dsSo.setColumn(i, "SHANG", list.get(i).getShang());
			dsSo.setColumn(i, "ZMPFLG", list.get(i).getZmpflg());
			dsSo.setColumn(i, "VBELN", list.get(i).getVbeln());
			dsSo.setColumn(i, "ZBPNNR", list.get(i).getZbpnnr());
			dsSo.setColumn(i, "CDATE", list.get(i).getCdate());
			dsSo.setColumn(i, "CTIME", list.get(i).getCtime());
			dsSo.setColumn(i, "CUSER", list.get(i).getCuser());
			dsSo.setColumn(i, "UDATE", list.get(i).getUdate());
			dsSo.setColumn(i, "UTIME", list.get(i).getUtime());
			dsSo.setColumn(i, "UUSER", list.get(i).getUuser());
			dsSo.setColumn(i, "DDATE", list.get(i).getDdate());
			dsSo.setColumn(i, "DTIME", list.get(i).getDtime());
			dsSo.setColumn(i, "DUSER", list.get(i).getDuser());
		}
		return dsSo;
	}

	private Dataset specToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		String mandt = DatasetUtility.getString(dsCond, "MANDT");
		String ztplno = DatasetUtility.getString(dsCond, "ZTPLNO");
		String mclass = DatasetUtility.getString(dsCond, "MCLASS");
		String ztplgbn = DatasetUtility.getString(dsCond, "ZTPLGBN");
		String zrmk = DatasetUtility.getString(dsCond, "ZRMK");
		String zprdgbn = DatasetUtility.getString(dsCond, "ZPRDGBN");

		String lang = paramVO.getVariable("G_LANG");
		String spras = "3";

		if (lang.equals("ko"))
			spras = "3";
		else
			spras = "E";

		if (mandt == null)
			mandt = "";
		if (ztplgbn == null)
			ztplgbn = "";
		if (zrmk == null)
			zrmk = "";
		if (mclass == null)
			mclass = "";
		if (zprdgbn == null)
			zprdgbn = "";

		param.setMandt(mandt);
		param.setMclass(mclass);
		param.setZtplgbn(ztplgbn);
		param.setZrmk(zrmk);
		param.setZprdgbn(zprdgbn);
		param.setSpras(spras);
		param.setLang(lang);

		if (ztplno == null) {
			// List<Ses0031> minList = service2.searchMinTemplate(param);
			// ztplno = minList.get(0).getZtplno().toString();
		}

		param.setZtplno(ztplno);

		List<Ses0031> list = service.searchSpec(param); // 서비스CALL
		dsList.deleteAll();

		String sAtfor = "";
		String sPrd = "";

		try {
			for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
				dsList.appendRow(); // 행추가
				dsList.setColumn(i, "FLAG", list.get(i).getFlag());
				dsList.setColumn(i, "MANDT", list.get(i).getMandt());
				dsList.setColumn(i, "MCLASS", list.get(i).getMclass());
				dsList.setColumn(i, "ATKLA", list.get(i).getAtkla());
				dsList.setColumn(i, "PRH", list.get(i).getPrh());
				sAtfor = list.get(i).getAtfor();
				sPrd = list.get(i).getPrd();
				if ("NUM".equals(sAtfor)) {
					if (sPrd != null && sPrd.indexOf(",") >= 0)
						sPrd = sPrd.replaceAll(",", "");
				}
				dsList.setColumn(i, "PRD", sPrd);
				dsList.setColumn(i, "ATFOR", sAtfor);
				dsList.setColumn(i, "PRHNAME", list.get(i).getPrhname());
				dsList.setColumn(i, "ZTPLSEQ", list.get(i).getZtplseq());
				dsList.setColumn(i, "CNT", list.get(i).getCnt());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dsList;
	}

	private Dataset blockToDataset(Dataset dsHeader, Dataset dsBlock, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		String mandt = "";
		String qtnum = "";
		String qtser = "";
		String qtseq = "";

		dsBlock.deleteAll();

		mandt = DatasetUtility.getString(dsHeader, "MANDT");
		qtnum = DatasetUtility.getString(dsHeader, "QTNUM");
		qtser = DatasetUtility.getString(dsHeader, "QTSER");

		if (mandt == null)
			mandt = "";
		if (qtnum == null)
			qtnum = "";
		if (qtser == null)
			qtser = "0";

		/*
		 * System.out.println("=========== mandt = " + mandt);
		 * System.out.println("=========== qtnum = " + qtnum);
		 * System.out.println("=========== qtser  = " + qtser);
		 */

		param.setMandt(mandt);
		param.setQtnum(qtnum);
		param.setQtser(qtser);

		List<Ses0031> list = service.searchBlockName(param); // 서비스CALL

		for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
			dsBlock.appendRow(); // 행추가
			dsBlock.setColumn(i, "NO", i + 1);
			dsBlock.setColumn(i, "MANDT", list.get(i).getMandt());
			dsBlock.setColumn(i, "QTNUM", list.get(i).getQtnum());
			dsBlock.setColumn(i, "QTSER", list.get(i).getQtser());
			dsBlock.setColumn(i, "QTSEQ", list.get(i).getQtseq());
			dsBlock.setColumn(i, "BLSEQ", list.get(i).getBlseq());
			dsBlock.setColumn(i, "BDMNG", list.get(i).getBdmng());
			dsBlock.setColumn(i, "ZCTOTAL", list.get(i).getZctotal());
			dsBlock.setColumn(i, "SALES", list.get(i).getSales());
			dsBlock.setColumn(i, "MESSG", list.get(i).getMessg());
			dsBlock.setColumn(i, "BLOCKNAME", list.get(i).getBlockname());
			dsBlock.setColumn(i, "MAKTX", list.get(i).getMaktx());
		}
		return dsBlock;
	}

	private Dataset t302ToDataset(Dataset dsHeader, Dataset dsT302, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		String mandt = "";
		String qtnum = "";
		String qtser = "";

		dsT302.deleteAll();

		for (int a = 0; a < dsHeader.getRowCount(); a++) {

			mandt = DatasetUtility.getString(dsHeader, a, "MANDT");
			qtnum = DatasetUtility.getString(dsHeader, a, "QTNUM");
			qtser = DatasetUtility.getString(dsHeader, a, "QTSER");

			if (mandt == null)
				mandt = "";
			if (qtnum == null)
				qtnum = "";
			if (qtser == null)
				qtser = "0";

			/*
			 * System.out.println("=========== mandt = " + mandt);
			 * System.out.println("=========== qtnum = " + qtnum);
			 * System.out.println("=========== qtser  = " + qtser);
			 */

			param.setMandt(mandt);
			param.setQtnum(qtnum);
			param.setQtser(qtser);

			List<Ses0031> list = service.searchZcobt302(param); // 서비스CALL

			for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
				dsT302.appendRow(); // 행추가
				dsT302.setColumn(i, "MANDT", list.get(i).getMandt());
				dsT302.setColumn(i, "QTNUM", list.get(i).getQtnum());
				dsT302.setColumn(i, "QTSER", list.get(i).getQtser());
				dsT302.setColumn(i, "QTSEQ", list.get(i).getQtseq());
				dsT302.setColumn(i, "MEIN_MH", list.get(i).getMeinh_mh());
				dsT302.setColumn(i, "PRDM01", list.get(i).getPrdm01());
				dsT302.setColumn(i, "PRDM02", list.get(i).getPrdm02());
				dsT302.setColumn(i, "PRDM03", list.get(i).getPrdm03());
				dsT302.setColumn(i, "PRDM04", list.get(i).getPrdm04());
				dsT302.setColumn(i, "PRDM05", list.get(i).getPrdm05());
				dsT302.setColumn(i, "PRDA01", list.get(i).getPrda01());
				dsT302.setColumn(i, "PRDL01", list.get(i).getPrdl01());
				dsT302.setColumn(i, "PRDL02", list.get(i).getPrdl02());
				dsT302.setColumn(i, "PRDL03", list.get(i).getPrdl03());
				dsT302.setColumn(i, "PRDL04", list.get(i).getPrdl04());
				dsT302.setColumn(i, "PRDL05", list.get(i).getPrdl05());
				dsT302.setColumn(i, "PRDE01", list.get(i).getPrde01());
				dsT302.setColumn(i, "PRDE02", list.get(i).getPrde02());
				dsT302.setColumn(i, "PRDE03", list.get(i).getPrde03());
				dsT302.setColumn(i, "PRDE04", list.get(i).getPrde04());
				dsT302.setColumn(i, "PRDE05", list.get(i).getPrde05());
				dsT302.setColumn(i, "PRDE06", list.get(i).getPrde06());
				dsT302.setColumn(i, "PRDE07", list.get(i).getPrde07());
				dsT302.setColumn(i, "PRDE08", list.get(i).getPrde08());
				dsT302.setColumn(i, "PRDE09", list.get(i).getPrde09());
				dsT302.setColumn(i, "PRDE10", list.get(i).getPrde10());
				dsT302.setColumn(i, "PRDE11", list.get(i).getPrde11());
				dsT302.setColumn(i, "PRDE12", list.get(i).getPrde12());
				dsT302.setColumn(i, "PRDE13", list.get(i).getPrde13());
				dsT302.setColumn(i, "PRDE14", list.get(i).getPrde14());
				dsT302.setColumn(i, "PRDE15", list.get(i).getPrde15());
				dsT302.setColumn(i, "PRDI01", list.get(i).getPrdi01());
				dsT302.setColumn(i, "PRDI02", list.get(i).getPrdi02());
				dsT302.setColumn(i, "PRDI03", list.get(i).getPrdi03());
				dsT302.setColumn(i, "PRDI04", list.get(i).getPrdi04());
				dsT302.setColumn(i, "EQMM01", list.get(i).getEqmm01());
				dsT302.setColumn(i, "EQMM02", list.get(i).getEqmm02());
				dsT302.setColumn(i, "EQMM03", list.get(i).getEqmm03());
				dsT302.setColumn(i, "EQMM04", list.get(i).getEqmm04());
				dsT302.setColumn(i, "EQMM05", list.get(i).getEqmm05());
				dsT302.setColumn(i, "EQMA01", list.get(i).getEqma01());
				dsT302.setColumn(i, "EQMA02", list.get(i).getEqma02());
				dsT302.setColumn(i, "EQML01", list.get(i).getEqml01());
				dsT302.setColumn(i, "EQML02", list.get(i).getEqml02());
				dsT302.setColumn(i, "EQML03", list.get(i).getEqml03());
				dsT302.setColumn(i, "EQML04", list.get(i).getEqml04());
				dsT302.setColumn(i, "EQML05", list.get(i).getEqml05());
				dsT302.setColumn(i, "EQME01", list.get(i).getEqme01());
				dsT302.setColumn(i, "EQME02", list.get(i).getEqme02());
				dsT302.setColumn(i, "EQME03", list.get(i).getEqme03());
				dsT302.setColumn(i, "EQME04", list.get(i).getEqme04());
				dsT302.setColumn(i, "EQME05", list.get(i).getEqme05());
				dsT302.setColumn(i, "EQME06", list.get(i).getEqme06());
				dsT302.setColumn(i, "EQME07", list.get(i).getEqme07());
				dsT302.setColumn(i, "EQME08", list.get(i).getEqme08());
				dsT302.setColumn(i, "EQME09", list.get(i).getEqme09());
				dsT302.setColumn(i, "EQME10", list.get(i).getEqme10());
				dsT302.setColumn(i, "EQME11", list.get(i).getEqme11());
				dsT302.setColumn(i, "EQME12", list.get(i).getEqme12());
				dsT302.setColumn(i, "EQME13", list.get(i).getEqme13());
				dsT302.setColumn(i, "EQME14", list.get(i).getEqme14());
				dsT302.setColumn(i, "EQME15", list.get(i).getEqme15());
				dsT302.setColumn(i, "EQME51", list.get(i).getEqme51());
				dsT302.setColumn(i, "EQME52", list.get(i).getEqme52());
				dsT302.setColumn(i, "EQME53", list.get(i).getEqme53());
				dsT302.setColumn(i, "EQME54", list.get(i).getEqme54());
				dsT302.setColumn(i, "EQME55", list.get(i).getEqme55());
				dsT302.setColumn(i, "EQME56", list.get(i).getEqme56());
				dsT302.setColumn(i, "EQME57", list.get(i).getEqme57());
				dsT302.setColumn(i, "EQME58", list.get(i).getEqme58());
				dsT302.setColumn(i, "EQME59", list.get(i).getEqme59());
				dsT302.setColumn(i, "EQME60", list.get(i).getEqme60());
				dsT302.setColumn(i, "EQME61", list.get(i).getEqme61());
				dsT302.setColumn(i, "EQME62", list.get(i).getEqme62());
				dsT302.setColumn(i, "EQME63", list.get(i).getEqme63());
				dsT302.setColumn(i, "EQME64", list.get(i).getEqme64());
				dsT302.setColumn(i, "EQME65", list.get(i).getEqme65());
				dsT302.setColumn(i, "EQME66", list.get(i).getEqme66());
				dsT302.setColumn(i, "EQME67", list.get(i).getEqme67());
				dsT302.setColumn(i, "EQME68", list.get(i).getEqme68());
				dsT302.setColumn(i, "EQME69", list.get(i).getEqme69());
				dsT302.setColumn(i, "EQME70", list.get(i).getEqme70());
				dsT302.setColumn(i, "EQME71", list.get(i).getEqme71());
				dsT302.setColumn(i, "EQME72", list.get(i).getEqme72());
				dsT302.setColumn(i, "EQME73", list.get(i).getEqme73());
				dsT302.setColumn(i, "EQME74", list.get(i).getEqme74());
				dsT302.setColumn(i, "EQME75", list.get(i).getEqme75());
				dsT302.setColumn(i, "EQME76", list.get(i).getEqme76());
				dsT302.setColumn(i, "EQME77", list.get(i).getEqme77());
				dsT302.setColumn(i, "EQME78", list.get(i).getEqme78());
				dsT302.setColumn(i, "EQME79", list.get(i).getEqme79());
				dsT302.setColumn(i, "EQME80", list.get(i).getEqme80());
				dsT302.setColumn(i, "EQME81", list.get(i).getEqme81());
				dsT302.setColumn(i, "EQME82", list.get(i).getEqme82());
				dsT302.setColumn(i, "EQME83", list.get(i).getEqme83());
				dsT302.setColumn(i, "EQME84", list.get(i).getEqme84());
				dsT302.setColumn(i, "EQME85", list.get(i).getEqme85());
				dsT302.setColumn(i, "EQME86", list.get(i).getEqme86());
				dsT302.setColumn(i, "EQME87", list.get(i).getEqme87());
				dsT302.setColumn(i, "EQME88", list.get(i).getEqme88());
				dsT302.setColumn(i, "EQME89", list.get(i).getEqme89());
				dsT302.setColumn(i, "EQME90", list.get(i).getEqme90());
				dsT302.setColumn(i, "EQME91", list.get(i).getEqme91());
				dsT302.setColumn(i, "EQME92", list.get(i).getEqme92());
				dsT302.setColumn(i, "EQME93", list.get(i).getEqme93());
				dsT302.setColumn(i, "EQME94", list.get(i).getEqme94());
				dsT302.setColumn(i, "EQME95", list.get(i).getEqme95());
				dsT302.setColumn(i, "EQME96", list.get(i).getEqme96());
				dsT302.setColumn(i, "EQME97", list.get(i).getEqme97());
				dsT302.setColumn(i, "EQME98", list.get(i).getEqme98());
				dsT302.setColumn(i, "EQME99", list.get(i).getEqme99());
				dsT302.setColumn(i, "EQME100", list.get(i).getEqme100());
				dsT302.setColumn(i, "EQME101", list.get(i).getEqme101());
				dsT302.setColumn(i, "EQME102", list.get(i).getEqme102());
				dsT302.setColumn(i, "EQME103", list.get(i).getEqme103());
				dsT302.setColumn(i, "EQME104", list.get(i).getEqme104());
				dsT302.setColumn(i, "EQME105", list.get(i).getEqme105());
				dsT302.setColumn(i, "EQME106", list.get(i).getEqme106());
				dsT302.setColumn(i, "EQME107", list.get(i).getEqme107());
				dsT302.setColumn(i, "EQME108", list.get(i).getEqme108());
				dsT302.setColumn(i, "EQME109", list.get(i).getEqme109());
				dsT302.setColumn(i, "EQME110", list.get(i).getEqme110());
				dsT302.setColumn(i, "EQME111", list.get(i).getEqme111());
				dsT302.setColumn(i, "EQME112", list.get(i).getEqme112());
				dsT302.setColumn(i, "EQME113", list.get(i).getEqme113());
				dsT302.setColumn(i, "EQME114", list.get(i).getEqme114());
				dsT302.setColumn(i, "EQME115", list.get(i).getEqme115());
				dsT302.setColumn(i, "EQME116", list.get(i).getEqme116());
				dsT302.setColumn(i, "EQME117", list.get(i).getEqme117());
				dsT302.setColumn(i, "EQME118", list.get(i).getEqme118());
				dsT302.setColumn(i, "EQME119", list.get(i).getEqme119());
				dsT302.setColumn(i, "EQME120", list.get(i).getEqme120());
				dsT302.setColumn(i, "EQMI01", list.get(i).getEqmi01());
				dsT302.setColumn(i, "EQMI02", list.get(i).getEqmi02());
				dsT302.setColumn(i, "EQMI03", list.get(i).getEqmi03());
				dsT302.setColumn(i, "EQMO01", list.get(i).getEqmo01());
				dsT302.setColumn(i, "EQMO02", list.get(i).getEqmo02());
				dsT302.setColumn(i, "EQMO03", list.get(i).getEqmo03());
				dsT302.setColumn(i, "EQMO04", list.get(i).getEqmo04());
				dsT302.setColumn(i, "EQMO05", list.get(i).getEqmo05());
				dsT302.setColumn(i, "EQMO06", list.get(i).getEqmo06());
				dsT302.setColumn(i, "EQMO07", list.get(i).getEqmo07());
				dsT302.setColumn(i, "EQMO08", list.get(i).getEqmo08());
				dsT302.setColumn(i, "EQMO09", list.get(i).getEqmo09());
				dsT302.setColumn(i, "EQMO10", list.get(i).getEqmo10());
				dsT302.setColumn(i, "EQMO11", list.get(i).getEqmo11());
				dsT302.setColumn(i, "EQMO12", list.get(i).getEqmo12());
				dsT302.setColumn(i, "EQMO13", list.get(i).getEqmo13());
				dsT302.setColumn(i, "EQMO14", list.get(i).getEqmo14());
				dsT302.setColumn(i, "EQMO15", list.get(i).getEqmo15());
				dsT302.setColumn(i, "EQMO16", list.get(i).getEqmo16());
				dsT302.setColumn(i, "EQMO17", list.get(i).getEqmo17());
				dsT302.setColumn(i, "EQMO18", list.get(i).getEqmo18());
				dsT302.setColumn(i, "EQMO19", list.get(i).getEqmo19());
				dsT302.setColumn(i, "EQMO20", list.get(i).getEqmo20());
				dsT302.setColumn(i, "EQMO21", list.get(i).getEqmo21());
				dsT302.setColumn(i, "EQMO22", list.get(i).getEqmo22());
				dsT302.setColumn(i, "EQMO23", list.get(i).getEqmo23());
				dsT302.setColumn(i, "EQMO24", list.get(i).getEqmo24());
				dsT302.setColumn(i, "EQMO25", list.get(i).getEqmo25());
				dsT302.setColumn(i, "EQMO26", list.get(i).getEqmo26());
				dsT302.setColumn(i, "EQMO27", list.get(i).getEqmo27());
				dsT302.setColumn(i, "EQMO28", list.get(i).getEqmo28());
				dsT302.setColumn(i, "EQMO29", list.get(i).getEqmo29());
				dsT302.setColumn(i, "EQMO30", list.get(i).getEqmo30());
				dsT302.setColumn(i, "EQMO31", list.get(i).getEqmo31());
				dsT302.setColumn(i, "EQMO32", list.get(i).getEqmo32());
				dsT302.setColumn(i, "EQMO33", list.get(i).getEqmo33());
				dsT302.setColumn(i, "EQMO34", list.get(i).getEqmo34());
				dsT302.setColumn(i, "EQMO35", list.get(i).getEqmo35());
				dsT302.setColumn(i, "EQMO36", list.get(i).getEqmo36());
				dsT302.setColumn(i, "EQMO37", list.get(i).getEqmo37());
				dsT302.setColumn(i, "EQMO38", list.get(i).getEqmo38());
				dsT302.setColumn(i, "EQMO39", list.get(i).getEqmo39());
				dsT302.setColumn(i, "EQMO40", list.get(i).getEqmo40());
				dsT302.setColumn(i, "CTSM01", list.get(i).getCtsm01());
				dsT302.setColumn(i, "CTSM02", list.get(i).getCtsm02());
				dsT302.setColumn(i, "CTSM03", list.get(i).getCtsm03());
				dsT302.setColumn(i, "CTSM04", list.get(i).getCtsm04());
				dsT302.setColumn(i, "CTSM05", list.get(i).getCtsm05());
				dsT302.setColumn(i, "CTSM06", list.get(i).getCtsm06());
				dsT302.setColumn(i, "CTSM07", list.get(i).getCtsm07());
				dsT302.setColumn(i, "CTSM08", list.get(i).getCtsm08());
				dsT302.setColumn(i, "CTSM09", list.get(i).getCtsm09());
				dsT302.setColumn(i, "CTSM10", list.get(i).getCtsm10());
				dsT302.setColumn(i, "CTSM11", list.get(i).getCtsm11());
				dsT302.setColumn(i, "CTSM12", list.get(i).getCtsm12());
				dsT302.setColumn(i, "CTSM13", list.get(i).getCtsm13());
				dsT302.setColumn(i, "CTSM14", list.get(i).getCtsm14());
				dsT302.setColumn(i, "CTSM15", list.get(i).getCtsm15());
				dsT302.setColumn(i, "CTSA01", list.get(i).getCtsa01());
				dsT302.setColumn(i, "CTSL01", list.get(i).getCtsl01());
				dsT302.setColumn(i, "CTSL02", list.get(i).getCtsl02());
				dsT302.setColumn(i, "CTSL03", list.get(i).getCtsl03());
				dsT302.setColumn(i, "CTSL04", list.get(i).getCtsl04());
				dsT302.setColumn(i, "CTSL05", list.get(i).getCtsl05());
				dsT302.setColumn(i, "CTSE01", list.get(i).getCtse01());
				dsT302.setColumn(i, "CTSE02", list.get(i).getCtse02());
				dsT302.setColumn(i, "CTSE03", list.get(i).getCtse03());
				dsT302.setColumn(i, "CTSE04", list.get(i).getCtse04());
				dsT302.setColumn(i, "CTSE05", list.get(i).getCtse05());
				dsT302.setColumn(i, "CTSI01", list.get(i).getCtsi01());
				dsT302.setColumn(i, "CTSI02", list.get(i).getCtsi02());
				dsT302.setColumn(i, "CTSI03", list.get(i).getCtsi03());
				dsT302.setColumn(i, "FDSP01", list.get(i).getFdsp01());
				dsT302.setColumn(i, "FDSP02", list.get(i).getFdsp02());
				dsT302.setColumn(i, "FDSP03", list.get(i).getFdsp03());
				dsT302.setColumn(i, "FDSP04", list.get(i).getFdsp04());
				dsT302.setColumn(i, "FDSP05", list.get(i).getFdsp05());
				dsT302.setColumn(i, "FDSP06", list.get(i).getFdsp06());
				dsT302.setColumn(i, "FDSP07", list.get(i).getFdsp07());
				dsT302.setColumn(i, "FDSP08", list.get(i).getFdsp08());
				dsT302.setColumn(i, "FDSP09", list.get(i).getFdsp09());
				dsT302.setColumn(i, "FDSP10", list.get(i).getFdsp10());
				dsT302.setColumn(i, "FDSP11", list.get(i).getFdsp11());
				dsT302.setColumn(i, "FDS01", list.get(i).getFds01());
				dsT302.setColumn(i, "FDS02", list.get(i).getFds02());
				dsT302.setColumn(i, "FDS03", list.get(i).getFds03());
				dsT302.setColumn(i, "FDS04", list.get(i).getFds04());
				dsT302.setColumn(i, "FDS05", list.get(i).getFds05());
				dsT302.setColumn(i, "FDS06", list.get(i).getFds06());
				dsT302.setColumn(i, "FDS07", list.get(i).getFds07());
				dsT302.setColumn(i, "FDS08", list.get(i).getFds08());
				dsT302.setColumn(i, "FDS09", list.get(i).getFds09());
				dsT302.setColumn(i, "FDS10", list.get(i).getFds10());
				dsT302.setColumn(i, "ADDT01", list.get(i).getAddt01());
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

		if (mandt == null)
			mandt = paramVO.getVariable("G_MANDT");
		if (qtnum == null)
			qtnum = "";
		if (qtser == null)
			qtser = "";
		if (qtseq == null)
			qtseq = ""; // 예상원가산출에서 호출시에만 setting
		if (pspid == null || "".equals(pspid))
			pspid = " "; // 견적 부분원가 조회 시 필요 ' '
		if (posid == null)
			posid = "";

		param.setMandt(mandt);
		param.setQtnum(qtnum);
		param.setQtser(qtser);
		param.setQtseq(qtseq);
		param.setPspid(pspid);
		param.setPosid(posid);

		dsT309.deleteAll();

		List<Ses0031> list = service.searchZcobt309(param); // 서비스CALL

		for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
			dsT309.appendRow(); // 행추가
			dsT309.setColumn(i, "FLAG", list.get(i).getFlag());
			dsT309.setColumn(i, "MANDT", list.get(i).getMandt());
			dsT309.setColumn(i, "QTNUM", list.get(i).getQtnum());
			dsT309.setColumn(i, "QTSER", list.get(i).getQtser());
			dsT309.setColumn(i, "QTSEQ", list.get(i).getQtseq());
			dsT309.setColumn(i, "PSPID", list.get(i).getPspid());
			dsT309.setColumn(i, "POSID", list.get(i).getPosid());
			dsT309.setColumn(i, "ATYPE", list.get(i).getAtype());
			dsT309.setColumn(i, "GUBUN", list.get(i).getGubun());
			dsT309.setColumn(i, "BLSEQ", list.get(i).getBlseq());
			dsT309.setColumn(i, "BLNUM", list.get(i).getBlnum());
			dsT309.setColumn(i, "BLNAM", list.get(i).getBlnam());
			dsT309.setColumn(i, "MESSG", list.get(i).getMessg());
			dsT309.setColumn(i, "STATE", list.get(i).getState());
			dsT309.setColumn(i, "SERIA", list.get(i).getSeria());
			dsT309.setColumn(i, "ZITEM", list.get(i).getZitem());
			dsT309.setColumn(i, "ZCHAR", list.get(i).getZchar());
			dsT309.setColumn(i, "ZQUTY", list.get(i).getZquty());
			dsT309.setColumn(i, "ZAMNT", list.get(i).getZamnt());
			dsT309.setColumn(i, "ZTOTL", list.get(i).getZtotl());
			dsT309.setColumn(i, "BIGO", list.get(i).getBigo());
			dsT309.setColumn(i, "TEX1", list.get(i).getTex1());
			dsT309.setColumn(i, "TEX2", list.get(i).getTex2());
			dsT309.setColumn(i, "ADDT01", list.get(i).getAddt01());
			// dsT309.setColumn(i, "VKBUR", list.get(i).getVkbur());
			// dsT309.setColumn(i, "VKGRP", list.get(i).getVkgrp());
			// dsT309.setColumn(i, "ZKUNNR", list.get(i).getZkunnr());
			// dsT309.setColumn(i, "SHANGH", list.get(i).getShangh());
		}
		return dsT309;
	}

/*	2018.03.06 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start 
	private Dataset exchangeToDataset(Dataset dsExchange, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		String mandt = DatasetUtility.getString(dsExchange, 0, "MANDT");
		String zyear = DatasetUtility.getString(dsExchange, 0, "ZYEAR");
		String zmonth = DatasetUtility.getString(dsExchange, 0, "ZMONTH");

		if (mandt == null)
			mandt = paramVO.getVariable("G_MANDT");
		if (zyear == null)
			zyear = "";
		if (zmonth == null)
			zmonth = "";

		param.setMandt(mandt);
		param.setZyear(zyear);
		param.setZmonth(zmonth);

		List<Ses0031> list = service.searchExchange(param); // 서비스CALL

		dsExchange.deleteAll();

		for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
			dsExchange.appendRow(); // 행추가
			dsExchange.setColumn(i, "MANDT", list.get(i).getMandt());
			dsExchange.setColumn(i, "ZYEAR", list.get(i).getZyear());
			dsExchange.setColumn(i, "ZMONTH", list.get(i).getZmonth());
			dsExchange.setColumn(i, "KRWUSD", list.get(i).getKrwusd());
			dsExchange.setColumn(i, "KRWJPY", list.get(i).getKrwjpy());
			dsExchange.setColumn(i, "KRWEUR", list.get(i).getKrweur());
		}
		return dsExchange;
	}

	private Dataset newExchangeToDataset(Dataset dsExchange, MipParameterVO paramVO) throws Exception {

		try {
			Ses0031ParamVO param = new Ses0031ParamVO();

			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); // Session GET
			// service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			// // DAO생성

			String mandt = DatasetUtility.getString(dsExchange, 0, "MANDT");
			String sdate = DatasetUtility.getString(dsExchange, 0, "SDATE");
			String f_flag = DatasetUtility.getString(dsExchange, 0, "F_FLAG");

			if (mandt == null)
				mandt = paramVO.getVariable("G_MANDT");
			if (sdate == null)
				sdate = "";
			if (f_flag == null)
				f_flag = "";

			param.setMandt(mandt);
			param.setSdate(sdate);
			param.setF_flag(f_flag);

			Ses0031 exchangeVO = service.searchNewExchange(param, session, paramVO.getVariable("G_LANG")); // 서비스CALL

			dsExchange.deleteAll();

			if (exchangeVO != null) {
				dsExchange.appendRow(); // 행추가
				dsExchange.setColumn(0, "MANDT", exchangeVO.getMandt());
				dsExchange.setColumn(0, "KRWUSD", exchangeVO.getKrwusd());
				dsExchange.setColumn(0, "KRWJPY", exchangeVO.getKrwjpy());
				dsExchange.setColumn(0, "KRWEUR", exchangeVO.getKrweur());
			}
			return dsExchange;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	2018.03.06 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End  */
	
	private Dataset ExchangeRateToDataset(Dataset dsExchange, MipParameterVO paramVO) throws Exception {
		try {
			
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); // Session GET
			ExchangeS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
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
	@RequestMapping(value = "sendmail")
	public View Ses0243sendMail(MipParameterVO paramVO, Model model) {

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error"); // 오류정보 DATASET GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); // Session GET

		service.createDao(session); // DAO Create

		try {
			service.sendMailheader(paramVO, model, session); // 저장 호출

		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); // 호출결과
		}

		MipResultVO resultVO = new MipResultVO();

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error); // 오류INFO
		model.addAttribute("resultVO", resultVO);

		return view;

	}

	@RequestMapping(value = "searchKunnr")
	public View FindSearchKunnr(MipParameterVO paramVO, Model model) {

		Dataset ds_kunnr = paramVO.getDataset("ds_kunnr");

		MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return

		try {

			Ses0031ParamVO param = new Ses0031ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String mandt = paramVO.getVariable("G_MANDT");
			String paramKunnr = paramVO.getVariable("paramKunnr");

			param.setMandt(mandt);
			param.setKunnr(paramKunnr);

			Ses0031 kunnrVO = service.searchKunnr(param); // 서비스CALL

			ds_kunnr.deleteAll();

			if (kunnrVO != null) {
				ds_kunnr.appendRow(); // 행추가

				ds_kunnr.setColumn(0, "MANDT", kunnrVO.getMandt());
				ds_kunnr.setColumn(0, "KUNNR", kunnrVO.getKunnr());
				ds_kunnr.setColumn(0, "LAND1_NM", kunnrVO.getLand1_nm());
				ds_kunnr.setColumn(0, "LAND1", kunnrVO.getLand1());
				ds_kunnr.setColumn(0, "NAME1", kunnrVO.getName1());
				ds_kunnr.setColumn(0, "TELF1", kunnrVO.getTelf1());
				ds_kunnr.setColumn(0, "TELFX", kunnrVO.getTelfx());
			}

			resultVO.addDataset(ds_kunnr);

			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "saveZuamReject")
	public View Ses0350SaveZuamReject(MipParameterVO paramVO, Model model) {

		Dataset ds_reject = paramVO.getDataset("ds_reject");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); // Session GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error"); // UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return

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
	@RequestMapping(value = "updateDwgFlag")

	public View Ses0031UpdateDwgFlag(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		int dwg;
		String mandt = "";
		String qtnum = "";
		String qtser = "";

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(session); // DAO생성

		try {

			for (int i = 0; i < dsHeader.getRowCount(); i++) {

				mandt = DatasetUtility.getString(dsHeader, i, "MANDT");
				qtnum = DatasetUtility.getString(dsHeader, i, "QTNUM");
				qtser = DatasetUtility.getString(dsHeader, i, "QTSER");

				if (mandt == null)
					mandt = paramVO.getVariable("G_MANDT");
				if (qtnum == null)
					qtnum = "";
				if (qtser == null)
					qtser = "0";

				param.setMandt(mandt); // SAP CLIENT
				param.setQtnum(qtnum);
				param.setQtser(qtser);

				dwg = service.updateDwgFlag(param);
			}
			MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
			resultVO.addDataset(dsHeader);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "searchClass")
	public View Ses0031ClassCdView(MipParameterVO paramVO, Model model) {

		Dataset dsClass = paramVO.getDataset("ds_class");

		dsClass = classToDataset(dsClass, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
			resultVO.addDataset(dsClass);
			// System.out.println("============== dsClass.getRowCount() " +
			// dsClass.getRowCount());
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value = "searchAcapa")
	public View Ses0031AcapaView(MipParameterVO paramVO, Model model) {

		MipResultVO resultVO = new MipResultVO();
		Dataset dsAcapa = paramVO.getDataset("ds_acapa");
		

		ZWEB_SD_IS_SPECIAL_USERStub stub;
		ZWEB_SD_IS_SPECIAL_USERStub.ZWEB_SD_IS_SPECIAL_USER param;
		ZWEB_SD_IS_SPECIAL_USERStub.ZWEB_SD_IS_SPECIAL_USERResponse response;

//		stub = (ZWEB_SD_IS_SPECIAL_USERStub) CallSAP.createStub(ZWEB_SD_IS_SPECIAL_USERStub.class,
//				paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"));
		stub = (ZWEB_SD_IS_SPECIAL_USERStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), new Spras(paramVO.getVariable("G_LANG")).toString()).create(ZWEB_SD_IS_SPECIAL_USERStub.class);
		dsAcapa = acapaToDataset(dsAcapa, paramVO);
		try {
			resultVO.addDataset(dsAcapa);
			
			param = new ZWEB_SD_IS_SPECIAL_USERStub.ZWEB_SD_IS_SPECIAL_USER();

			//param.setIV_CLSNO(functions.rfc.sap.document.sap_com.ZWEB_SD_IS_SPECIAL_USERStub.Numeric5.Factory.fromString("00001", ""));
			param.setIV_SLGID(paramVO.getVariable("G_USER_ID"));
			//response = stub.zWEB_SD_IS_SPECIAL_USER(param);
			//resultVO.addVariable("permitted", response.getEV_RIGHT().toString());

			param.setIV_CLSNO(webservice.stub.sap.ZWEB_SD_IS_SPECIAL_USERStub.Numeric5.Factory.fromString("00002", ""));
			response = stub.zWEB_SD_IS_SPECIAL_USER(param);
			resultVO.addVariable("changeSpec", response.getEV_RIGHT().toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	private Dataset classToDataset(Dataset dsClass, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		String mandt = paramVO.getVariable("G_MANDT");

		if (mandt == null)
			mandt = "";

		param.setMandt(mandt);

		List<Ses0031> list = service.searchClass(param); // 서비스CALL

		dsClass.deleteAll();

		for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
			dsClass.appendRow(); // 행추가
			dsClass.setColumn(i, "MANDT", list.get(i).getMandt());
			dsClass.setColumn(i, "MATNR", list.get(i).getMatnr());
			dsClass.setColumn(i, "CLASS_CD", list.get(i).getClass_cd());
		}
		return dsClass;
	}

	private Dataset acapaToDataset(Dataset dsAcapa, MipParameterVO paramVO) {

		Ses0031ParamVO param = new Ses0031ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		// DAO생성

		String mandt = DatasetUtility.getString(dsAcapa, 0, "MANDT");
		String atinn = DatasetUtility.getString(dsAcapa, 0, "ATINN");
		String spras = DatasetUtility.getString(dsAcapa, 0, "SPRAS");

		if (mandt == null)
			mandt = paramVO.getVariable("G_MANDT");
		if (atinn == null)
			atinn = "";
		if (spras == null)
			spras = "";

		param.setMandt(mandt);
		param.setAtinn(atinn);
		param.setSpras(spras);

		List<Ses0031> list = service.searchAcapa(param); // 서비스CALL

		dsAcapa.deleteAll();

		for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
			dsAcapa.appendRow(); // 행추가
			dsAcapa.setColumn(i, "MANDT", list.get(i).getMandt());
			dsAcapa.setColumn(i, "ATINN", list.get(i).getAtinn());
			dsAcapa.setColumn(i, "ATZHL", list.get(i).getAtzhl());
			dsAcapa.setColumn(i, "ATWRT", list.get(i).getAtwrt());
			dsAcapa.setColumn(i, "ZACAPA", list.get(i).getZacapa());
			dsAcapa.setColumn(i, "SPRAS", list.get(i).getSpras());
		}
		return dsAcapa;
	}

	@RequestMapping(value = "oneRestrictionCondi")
	// 2020 브랜드 입력화 박수근 수정
	public View oneRestrictionCondi(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.oneRestrictionCondi(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ Ses0031N restrictionCondi Exception ERROR!!!");
		}
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	//=========================================================================================
	//  함수기능 	: 제한조건 제외 원가산출
	//  개선내역 	: 제한조건 제외하고 원가산출 수행
	//  HISTORY : 2020.07.09 박수근
	//=========================================================================================
	@RequestMapping(value = "restrictionCondisearchCost")
	public View restrictionCondisearchCost(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.restrictionCondisearchCost(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ Ses0031N restrictionCondisearchCost Exception ERROR!!!");
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	
	@RequestMapping(value = "saveQtVadt")
	public View Ses0031SaveQtVadt(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return

		try {
			dsHeader = service.saveQtVadt(paramVO, model, session);
			resultVO.addDataset(dsHeader);

		} catch (RuntimeException re) {
			re.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, re.getMessage(), re.getMessage(), "Y", "Y");
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}	
	
	
}
