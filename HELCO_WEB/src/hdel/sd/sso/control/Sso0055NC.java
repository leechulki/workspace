package hdel.sd.sso.control;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.sap.domain.Spras;
import com.tobesoft.platform.data.Dataset;

/**
 * 수주변경
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 하단에 표기
 */
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.com.service.DutyNS;
import hdel.sd.com.service.DutyS;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.sso.domain.Sso0055ParamVO;
import hdel.sd.sso.domain.Sso0055VO;
import hdel.sd.sso.service.Sso0055NS;
import tit.util.DatasetUtility;
import webservice.stub.sap.SAPStub;
import webservice.stub.sap.ZWEB_PP_CHECK_CODENROUTINGStub;
import webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub;
import webservice.stub.sap.ZWEB_PP_CHECK_CODENROUTINGStub.Char20;
import webservice.stub.sap.ZWEB_PP_CHECK_CODENROUTINGStub.TABLE_OF_ZPPT124;
import webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub.Char24;
import webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub.Char9;
import webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub.TABLE_OF_ZPPS015;

/**
 * public View find(MipParameterVO paramVO, Model model)
 * public View save(MipParameterVO paramVO, Model model)
 * public View cost(MipParameterVO paramVO, Model model)
 * public View specChange(MipParameterVO paramVO, Model model)
 * public View findSayang(MipParameterVO paramVO, Model model)
 * public View findSayangForPrint(MipParameterVO paramVO, Model model)
 * public View Sso0055SetCostState(MipParameterVO paramVO, Model model)
 * public View searchZcobt304(MipParameterVO paramVO, Model model)
 * public View Sso0055SearchExchangeView(MipParameterVO paramVO, Model model)
 */


@Controller
@RequestMapping("sso0055n")
public class Sso0055NC extends CallSAP{
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sso0055NS service;
	
	@Autowired
	private DutyS dutyService;
	
	@Autowired
	private DutyNS dutyNService;
	
	@Autowired
	private ExchangeS ExchangeS;
	
	/**
	 * 
	 * HISTORY  : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50350D0CE184009AE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View find(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO 	= new MipResultVO();
		
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		try {
			// DAO생성
			service.createDao(session);
			
			// 서비스호출
			resultVO = service.find(paramVO, session, model);
		} catch (Exception e) {
			logger.error("@@@ Sso0055N find Exception ERROR!!!");
			e.printStackTrace();
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/**
	 * 단가 합계
	 * HISTORY  : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50612AF4FA9C010FE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
	 *            http://elsprd.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50A3651B524E410DE1008000CBF22514/wsdl11/allinone/ws_policy/document?sap-client=183
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="cost")
	public View cost(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO 	= new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		
		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.cost(paramVO, session, model);
		} catch (Exception e) {
			logger.error("@@@ Sso0055Ncost Exception ERROR!!!");
		}
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}


	/**
	 * 
	 * HISTORY  : 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	  
	@RequestMapping(value="save")
	public View save(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO 	= new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		
		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.save(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ Sso0055Ncost Exception ERROR!!!");
		}
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	/**
	 * 사양
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sayang")
	public View findSayang(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		String i_pspId  = paramVO.getVariable("pspId"); // 프로젝트번호
		String i_posnr  = paramVO.getVariable("posnr"); // 판매품목번호
		String i_matnr  = paramVO.getVariable("matnr"); // 자재번호 
		String i_class  = paramVO.getVariable("class"); // class번호
		String i_brndcd = paramVO.getVariable("brndcd"); // 브랜드코드
		String i_brndseq= paramVO.getVariable("brndseq");// 브랜드시퀀스
		String i_hogi   = paramVO.getVariable("hogi");   // 호기

		try { 

			//CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
			//CW00002=필수 입력항목입니다.\n확인하여 주십시오.
			if ( "".equals( i_pspId ) || i_pspId == null )
			{
				throw new BizException("CW00001,프로젝트번호");
			}
			if ( "".equals( i_posnr ) || i_posnr == null )
			{
				throw new BizException("CW00001,판매품목번호");
			}
			if ( "".equals( i_matnr ) || i_matnr == null )
			{
				throw new BizException("CW00001,자재번호");
			}
			if ( "".equals( i_class ) || i_class == null )
			{
				throw new BizException("CW00001,class번호");
			}

			// 2020 브랜드 초기값 정의
			if(i_brndcd == null) {
				i_brndcd = "NOBRND";
			} else {
				if("".equals(i_brndcd)) {
					i_brndcd = "NOBRND";
				}
			}
			if(i_brndseq == null) {
				i_brndseq = "000";
			} else {
				if("".equals(i_brndseq)) {
					i_brndseq = "000";
				}
			}

			if(i_hogi == null) {
				i_hogi = "";
			}
			
			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			Sso0055ParamVO paramV = new Sso0055ParamVO();					// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 				// 세션권한
			paramV.setPSPID(i_pspId); 										// 프로젝트번호
			paramV.setPOSNR(i_posnr); 										// 판매품목번호
			paramV.setMATNR(i_matnr); 										// 자재번호
			paramV.setCLASS(i_class); 										// class번호
			
			// 2020 브랜드
			paramV.setBRNDCD(i_brndcd);     // 브랜드코드
			paramV.setBRNDSEQ(i_brndseq);    // 브랜드시퀀스
			paramV.setHOGI(i_hogi);

			List<Sso0055VO> list = service.find(paramV);
			 
			Dataset dsOutput = new Dataset();
			// list결과를 dataset으로 담기
			SmpComC.moveDs2List(dsOutput, Sso0055VO.class, list );
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);     
		}  catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1)
			{
				SmpComC.moveDs2Msg(message[0], message[0], model);
			}
			else
			{
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		}  catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	
	
	/**
	 * 사양
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sayangForPrint")
	public View findSayangForPrint(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		String i_pspId	= paramVO.getVariable("pspId");	// 프로젝트번호
		String i_seq	= paramVO.getVariable("seq"); 	// 차수
		String i_seqpre = "";

		try { 
			
			//CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
			//CW00002=필수 입력항목입니다.\n확인하여 주십시오.
			if ( "".equals( i_pspId ) || i_pspId == null )
			{
				throw new BizException("CW00001,프로젝트번호");
			}
			if ( "".equals( i_seq ) || i_seq == null )
			{
				throw new BizException("CW00001,차수");
			}
			if ( i_seq == "1" ) {
				i_seqpre = "1";
			} else {
				i_seqpre = String.valueOf(Integer.parseInt(i_seq) - 1);
			}
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			Sso0055ParamVO paramV = new Sso0055ParamVO();					// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 				// 세션권한
			paramV.setPSPID(i_pspId); 										// 프로젝트번호
			paramV.setSEQ(i_seq);											// 차수
			paramV.setSEQPRE(i_seqpre);
			
			List<Sso0055VO> list = service.findSayangForPrint(paramV);
			
			Dataset dsOutput = new Dataset();
			// list결과를 dataset으로 담기
			SmpComC.moveDs2List(dsOutput, Sso0055VO.class, list );
			
			dsOutput.setId("ds_print_sayang");
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);
		}  catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");
			
			if ( message.length <= 1)
			{
				SmpComC.moveDs2Msg(message[0], message[0], model);
			}
			else
			{
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		}  catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	
	@RequestMapping(value="setCostState")
	public View Sso0055SetCostState(MipParameterVO paramVO, Model model) {
		
		Dataset ds_error   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");		// 오류정보 DATASET GET
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		try {
			service.setCostState(paramVO);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	
	/**
	 * 2012.01.11 계약변경에서 원가내역서 출력을 위한 자료 조회
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="queryZcobt304")
	public View searchZcobt304(MipParameterVO paramVO, Model model) {
		
		Dataset dsT304   = paramVO.getDataset("ds_t304");
		Dataset dsBlock  = paramVO.getDataset("ds_block");
		Dataset dsAbrand = paramVO.getDataset("ds_abrand");
		Dataset dsError  = paramVO.getDataset("ds_error");
		
		Sso0055ParamVO paramVo = new Sso0055ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		paramVo.setMANDT(paramVO.getVariable("G_MANDT"));
		paramVo.setPSPID(paramVO.getVariable("PSPID"));
		paramVo.setPOSID(paramVO.getVariable("POSID"));
		paramVo.setSEQ(paramVO.getVariable("SEQ"));
		
		MipResultVO resultVO = new MipResultVO();
		
		try {
			dsT304.deleteAll();
			
			List<Sso0055VO> list = service.searchZcobt304(paramVo);
			
			for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
				dsT304.appendRow(); 	// 행추가
				dsT304.setColumn(i, "MANDT"  , list.get(i).getMANDT());
				dsT304.setColumn(i, "PSPID"  , list.get(i).getPSPID());
				dsT304.setColumn(i, "SEQ"    , list.get(i).getSEQ());
				dsT304.setColumn(i, "POSID"  , list.get(i).getPOSID());
				dsT304.setColumn(i, "MEIN_MH", list.get(i).getMEINH_MH());
				dsT304.setColumn(i, "PRDM01" , list.get(i).getPRDM01());
				dsT304.setColumn(i, "PRDM02" , list.get(i).getPRDM02());
				dsT304.setColumn(i, "PRDM03" , list.get(i).getPRDM03());
				dsT304.setColumn(i, "PRDM04" , list.get(i).getPRDM04());
				dsT304.setColumn(i, "PRDM05" , list.get(i).getPRDM05());
				dsT304.setColumn(i, "PRDA01" , list.get(i).getPRDA01());
				dsT304.setColumn(i, "PRDL01" , list.get(i).getPRDL01());
				dsT304.setColumn(i, "PRDL02" , list.get(i).getPRDL02());
				dsT304.setColumn(i, "PRDL03" , list.get(i).getPRDL03());
				dsT304.setColumn(i, "PRDL04" , list.get(i).getPRDL04());
				dsT304.setColumn(i, "PRDL05" , list.get(i).getPRDL05());
				dsT304.setColumn(i, "PRDE01" , list.get(i).getPRDE01());
				dsT304.setColumn(i, "PRDE02" , list.get(i).getPRDE02());
				dsT304.setColumn(i, "PRDE03" , list.get(i).getPRDE03());
				dsT304.setColumn(i, "PRDE04" , list.get(i).getPRDE04());
				dsT304.setColumn(i, "PRDE05" , list.get(i).getPRDE05());
				dsT304.setColumn(i, "PRDE06" , list.get(i).getPRDE06());
				dsT304.setColumn(i, "PRDE07" , list.get(i).getPRDE07());
				dsT304.setColumn(i, "PRDE08" , list.get(i).getPRDE08());
				dsT304.setColumn(i, "PRDE09" , list.get(i).getPRDE09());
				dsT304.setColumn(i, "PRDE10" , list.get(i).getPRDE10());
				dsT304.setColumn(i, "PRDE11" , list.get(i).getPRDE11());
				dsT304.setColumn(i, "PRDE12" , list.get(i).getPRDE12());
				dsT304.setColumn(i, "PRDE13" , list.get(i).getPRDE13());
				dsT304.setColumn(i, "PRDE14" , list.get(i).getPRDE14());
				dsT304.setColumn(i, "PRDE15" , list.get(i).getPRDE15());
				dsT304.setColumn(i, "PRDI01" , list.get(i).getPRDI01());
				dsT304.setColumn(i, "PRDI02" , list.get(i).getPRDI02());
				dsT304.setColumn(i, "PRDI03" , list.get(i).getPRDI03());
				dsT304.setColumn(i, "PRDI04" , list.get(i).getPRDI04());
				dsT304.setColumn(i, "EQMM01" , list.get(i).getEQMM01());
				dsT304.setColumn(i, "EQMM02" , list.get(i).getEQMM02());
				dsT304.setColumn(i, "EQMM03" , list.get(i).getEQMM03());
				dsT304.setColumn(i, "EQMM04" , list.get(i).getEQMM04());
				dsT304.setColumn(i, "EQMM05" , list.get(i).getEQMM05());
				dsT304.setColumn(i, "EQMA01" , list.get(i).getEQMA01());
				dsT304.setColumn(i, "EQMA02" , list.get(i).getEQMA02());
				dsT304.setColumn(i, "EQML01" , list.get(i).getEQML01());
				dsT304.setColumn(i, "EQML02" , list.get(i).getEQML02());
				dsT304.setColumn(i, "EQML03" , list.get(i).getEQML03());
				dsT304.setColumn(i, "EQML04" , list.get(i).getEQML04());
				dsT304.setColumn(i, "EQML05" , list.get(i).getEQML05());
				dsT304.setColumn(i, "EQME01" , list.get(i).getEQME01());
				dsT304.setColumn(i, "EQME02" , list.get(i).getEQME02());
				dsT304.setColumn(i, "EQME03" , list.get(i).getEQME03());
				dsT304.setColumn(i, "EQME04" , list.get(i).getEQME04());
				dsT304.setColumn(i, "EQME05" , list.get(i).getEQME05());
				dsT304.setColumn(i, "EQME06" , list.get(i).getEQME06());
				dsT304.setColumn(i, "EQME07" , list.get(i).getEQME07());
				dsT304.setColumn(i, "EQME08" , list.get(i).getEQME08());
				dsT304.setColumn(i, "EQME09" , list.get(i).getEQME09());
				dsT304.setColumn(i, "EQME10" , list.get(i).getEQME10());
				dsT304.setColumn(i, "EQME11" , list.get(i).getEQME11());
				dsT304.setColumn(i, "EQME12" , list.get(i).getEQME12());
				dsT304.setColumn(i, "EQME13" , list.get(i).getEQME13());
				dsT304.setColumn(i, "EQME14" , list.get(i).getEQME14());
				dsT304.setColumn(i, "EQME15" , list.get(i).getEQME15());
				dsT304.setColumn(i, "EQME51" , list.get(i).getEQME51());
				dsT304.setColumn(i, "EQME52" , list.get(i).getEQME52());
				dsT304.setColumn(i, "EQME53" , list.get(i).getEQME53());
				dsT304.setColumn(i, "EQME54" , list.get(i).getEQME54());
				dsT304.setColumn(i, "EQME55" , list.get(i).getEQME55());
				dsT304.setColumn(i, "EQME56" , list.get(i).getEQME56());
				dsT304.setColumn(i, "EQME57" , list.get(i).getEQME57());
				dsT304.setColumn(i, "EQME58" , list.get(i).getEQME58());
				dsT304.setColumn(i, "EQME59" , list.get(i).getEQME59());
				dsT304.setColumn(i, "EQME60" , list.get(i).getEQME60());
				dsT304.setColumn(i, "EQME61" , list.get(i).getEQME61());
				dsT304.setColumn(i, "EQME62" , list.get(i).getEQME62());
				dsT304.setColumn(i, "EQME63" , list.get(i).getEQME63());
				dsT304.setColumn(i, "EQME64" , list.get(i).getEQME64());
				dsT304.setColumn(i, "EQME65" , list.get(i).getEQME65());
				dsT304.setColumn(i, "EQME66" , list.get(i).getEQME66());
				dsT304.setColumn(i, "EQME67" , list.get(i).getEQME67());
				dsT304.setColumn(i, "EQME68" , list.get(i).getEQME68());
				dsT304.setColumn(i, "EQME69" , list.get(i).getEQME69());
				dsT304.setColumn(i, "EQME70" , list.get(i).getEQME70());
				dsT304.setColumn(i, "EQME71" , list.get(i).getEQME71());
				dsT304.setColumn(i, "EQME72" , list.get(i).getEQME72());
				dsT304.setColumn(i, "EQME73" , list.get(i).getEQME73());
				dsT304.setColumn(i, "EQME74" , list.get(i).getEQME74());
				dsT304.setColumn(i, "EQME75" , list.get(i).getEQME75());
				dsT304.setColumn(i, "EQME76" , list.get(i).getEQME76());
				dsT304.setColumn(i, "EQME77" , list.get(i).getEQME77());
				dsT304.setColumn(i, "EQME78" , list.get(i).getEQME78());
				dsT304.setColumn(i, "EQME79" , list.get(i).getEQME79());
				dsT304.setColumn(i, "EQME80" , list.get(i).getEQME80());
				dsT304.setColumn(i, "EQME81" , list.get(i).getEQME81());
				dsT304.setColumn(i, "EQME82" , list.get(i).getEQME82());
				dsT304.setColumn(i, "EQME83" , list.get(i).getEQME83());
				dsT304.setColumn(i, "EQME84" , list.get(i).getEQME84());
				dsT304.setColumn(i, "EQME85" , list.get(i).getEQME85());
				dsT304.setColumn(i, "EQME86" , list.get(i).getEQME86());
				dsT304.setColumn(i, "EQME87" , list.get(i).getEQME87());
				dsT304.setColumn(i, "EQME88" , list.get(i).getEQME88());
				dsT304.setColumn(i, "EQME89" , list.get(i).getEQME89());
				dsT304.setColumn(i, "EQME90" , list.get(i).getEQME90());
				dsT304.setColumn(i, "EQME91" , list.get(i).getEQME91());
				dsT304.setColumn(i, "EQME92" , list.get(i).getEQME92());
				dsT304.setColumn(i, "EQME93" , list.get(i).getEQME93());
				dsT304.setColumn(i, "EQME94" , list.get(i).getEQME94());
				dsT304.setColumn(i, "EQME95" , list.get(i).getEQME95());
				dsT304.setColumn(i, "EQME96" , list.get(i).getEQME96());
				dsT304.setColumn(i, "EQME97" , list.get(i).getEQME97());
				dsT304.setColumn(i, "EQME98" , list.get(i).getEQME98());
				dsT304.setColumn(i, "EQME99" , list.get(i).getEQME99());
				dsT304.setColumn(i, "EQME100" , list.get(i).getEQME100());
				dsT304.setColumn(i, "EQME101" , list.get(i).getEQME101());
				dsT304.setColumn(i, "EQME102" , list.get(i).getEQME102());
				dsT304.setColumn(i, "EQME103" , list.get(i).getEQME103());
				dsT304.setColumn(i, "EQME104" , list.get(i).getEQME104());
				dsT304.setColumn(i, "EQME105" , list.get(i).getEQME105());
				dsT304.setColumn(i, "EQME106" , list.get(i).getEQME106());
				dsT304.setColumn(i, "EQME107" , list.get(i).getEQME107());
				dsT304.setColumn(i, "EQME108" , list.get(i).getEQME108());
				dsT304.setColumn(i, "EQME109" , list.get(i).getEQME109());
				dsT304.setColumn(i, "EQME110" , list.get(i).getEQME110());
				dsT304.setColumn(i, "EQME111" , list.get(i).getEQME111());
				dsT304.setColumn(i, "EQME112" , list.get(i).getEQME112());
				dsT304.setColumn(i, "EQME113" , list.get(i).getEQME113());
				dsT304.setColumn(i, "EQME114" , list.get(i).getEQME114());
				dsT304.setColumn(i, "EQME115" , list.get(i).getEQME115());
				dsT304.setColumn(i, "EQME116" , list.get(i).getEQME116());
				dsT304.setColumn(i, "EQME117" , list.get(i).getEQME117());
				dsT304.setColumn(i, "EQME118" , list.get(i).getEQME118());
				dsT304.setColumn(i, "EQME119" , list.get(i).getEQME119());
				dsT304.setColumn(i, "EQME120" , list.get(i).getEQME120());				
				dsT304.setColumn(i, "EQMI01" , list.get(i).getEQMI01());
				dsT304.setColumn(i, "EQMI02" , list.get(i).getEQMI02());
				dsT304.setColumn(i, "EQMI03" , list.get(i).getEQMI03());
				dsT304.setColumn(i, "EQMO01" , list.get(i).getEQMO01());
				dsT304.setColumn(i, "EQMO02" , list.get(i).getEQMO02());
				dsT304.setColumn(i, "EQMO03" , list.get(i).getEQMO03());
				dsT304.setColumn(i, "EQMO04" , list.get(i).getEQMO04());
				dsT304.setColumn(i, "EQMO05" , list.get(i).getEQMO05());
				dsT304.setColumn(i, "EQMO06" , list.get(i).getEQMO06());
				dsT304.setColumn(i, "EQMO07" , list.get(i).getEQMO07());
				dsT304.setColumn(i, "EQMO08" , list.get(i).getEQMO08());
				dsT304.setColumn(i, "EQMO09" , list.get(i).getEQMO09());
				dsT304.setColumn(i, "EQMO10" , list.get(i).getEQMO10());
				dsT304.setColumn(i, "EQMO11" , list.get(i).getEQMO11());
				dsT304.setColumn(i, "EQMO12" , list.get(i).getEQMO12());
				dsT304.setColumn(i, "EQMO13" , list.get(i).getEQMO13());
				dsT304.setColumn(i, "EQMO14" , list.get(i).getEQMO14());
				dsT304.setColumn(i, "EQMO15" , list.get(i).getEQMO15());
				dsT304.setColumn(i, "EQMO16" , list.get(i).getEQMO16());
				dsT304.setColumn(i, "EQMO17" , list.get(i).getEQMO17());
				dsT304.setColumn(i, "EQMO18" , list.get(i).getEQMO18());
				dsT304.setColumn(i, "EQMO19" , list.get(i).getEQMO19());
				dsT304.setColumn(i, "EQMO20" , list.get(i).getEQMO20());
				dsT304.setColumn(i, "EQMO21" , list.get(i).getEQMO21());
				dsT304.setColumn(i, "EQMO22" , list.get(i).getEQMO22());
				dsT304.setColumn(i, "EQMO23" , list.get(i).getEQMO23());
				dsT304.setColumn(i, "EQMO24" , list.get(i).getEQMO24());
				dsT304.setColumn(i, "EQMO25" , list.get(i).getEQMO25());
				dsT304.setColumn(i, "EQMO26" , list.get(i).getEQMO26());
				dsT304.setColumn(i, "EQMO27" , list.get(i).getEQMO27());
				dsT304.setColumn(i, "EQMO28" , list.get(i).getEQMO28());
				dsT304.setColumn(i, "EQMO29" , list.get(i).getEQMO29());
				dsT304.setColumn(i, "EQMO30" , list.get(i).getEQMO30());
				dsT304.setColumn(i, "EQMO31" , list.get(i).getEQMO31());
				dsT304.setColumn(i, "EQMO32" , list.get(i).getEQMO32());
				dsT304.setColumn(i, "EQMO33" , list.get(i).getEQMO33());
				dsT304.setColumn(i, "EQMO34" , list.get(i).getEQMO34());
				dsT304.setColumn(i, "EQMO35" , list.get(i).getEQMO35());
				dsT304.setColumn(i, "EQMO36" , list.get(i).getEQMO36());
				dsT304.setColumn(i, "EQMO37" , list.get(i).getEQMO37());
				dsT304.setColumn(i, "EQMO38" , list.get(i).getEQMO38());
				dsT304.setColumn(i, "EQMO39" , list.get(i).getEQMO39());
				dsT304.setColumn(i, "EQMO40" , list.get(i).getEQMO40());
				dsT304.setColumn(i, "CTSM01" , list.get(i).getCTSM01());
				dsT304.setColumn(i, "CTSM02" , list.get(i).getCTSM02());
				dsT304.setColumn(i, "CTSM03" , list.get(i).getCTSM03());
				dsT304.setColumn(i, "CTSM04" , list.get(i).getCTSM04());
				dsT304.setColumn(i, "CTSM05" , list.get(i).getCTSM05());
				dsT304.setColumn(i, "CTSM06" , list.get(i).getCTSM06());
				dsT304.setColumn(i, "CTSM07" , list.get(i).getCTSM07());
				dsT304.setColumn(i, "CTSM08" , list.get(i).getCTSM08());
				dsT304.setColumn(i, "CTSM09" , list.get(i).getCTSM09());
				dsT304.setColumn(i, "CTSM10" , list.get(i).getCTSM10());
				dsT304.setColumn(i, "CTSM11" , list.get(i).getCTSM11());
				dsT304.setColumn(i, "CTSM12" , list.get(i).getCTSM12());
				dsT304.setColumn(i, "CTSM13" , list.get(i).getCTSM13());
				dsT304.setColumn(i, "CTSM14" , list.get(i).getCTSM14());
				dsT304.setColumn(i, "CTSM15" , list.get(i).getCTSM15());
				dsT304.setColumn(i, "CTSM17" , list.get(i).getCTSM17());
				dsT304.setColumn(i, "CTSM18" , list.get(i).getCTSM18());
				dsT304.setColumn(i, "CTSA01" , list.get(i).getCTSA01());
				dsT304.setColumn(i, "CTSL01" , list.get(i).getCTSL01());
				dsT304.setColumn(i, "CTSL02" , list.get(i).getCTSL02());
				dsT304.setColumn(i, "CTSL03" , list.get(i).getCTSL03());
				dsT304.setColumn(i, "CTSL04" , list.get(i).getCTSL04());
				dsT304.setColumn(i, "CTSL05" , list.get(i).getCTSL05());
				dsT304.setColumn(i, "CTSE01" , list.get(i).getCTSE01());
				dsT304.setColumn(i, "CTSE02" , list.get(i).getCTSE02());
				dsT304.setColumn(i, "CTSE03" , list.get(i).getCTSE03());
				dsT304.setColumn(i, "CTSE04" , list.get(i).getCTSE04());
				dsT304.setColumn(i, "CTSE05" , list.get(i).getCTSE05());
				dsT304.setColumn(i, "CTSI01" , list.get(i).getCTSI01());
				dsT304.setColumn(i, "CTSI02" , list.get(i).getCTSI02());
				dsT304.setColumn(i, "CTSI03" , list.get(i).getCTSI03());
				dsT304.setColumn(i, "FDSP01" , list.get(i).getFDSP01());
				dsT304.setColumn(i, "FDSP02" , list.get(i).getFDSP02());
				dsT304.setColumn(i, "FDSP03" , list.get(i).getFDSP03());
				dsT304.setColumn(i, "FDSP04" , list.get(i).getFDSP04());
				dsT304.setColumn(i, "FDSP05" , list.get(i).getFDSP05());
				dsT304.setColumn(i, "FDSP06" , list.get(i).getFDSP06());
				dsT304.setColumn(i, "FDSP07" , list.get(i).getFDSP07());
				dsT304.setColumn(i, "FDSP08" , list.get(i).getFDSP08());
				dsT304.setColumn(i, "FDSP09" , list.get(i).getFDSP09());
				dsT304.setColumn(i, "FDSP10" , list.get(i).getFDSP10());
				dsT304.setColumn(i, "FDSP11" , list.get(i).getFDSP11());
				dsT304.setColumn(i, "FDS01"  , list.get(i).getFDS01());
				dsT304.setColumn(i, "FDS02"  , list.get(i).getFDS02());
				dsT304.setColumn(i, "FDS03"  , list.get(i).getFDS03());
				dsT304.setColumn(i, "FDS04"  , list.get(i).getFDS04());
				dsT304.setColumn(i, "FDS05"  , list.get(i).getFDS05());
				dsT304.setColumn(i, "FDS06"  , list.get(i).getFDS06());
				dsT304.setColumn(i, "FDS07"  , list.get(i).getFDS07());
				dsT304.setColumn(i, "FDS08"  , list.get(i).getFDS08());
				dsT304.setColumn(i, "FDS09"  , list.get(i).getFDS09());
				dsT304.setColumn(i, "FDS10"  , list.get(i).getFDS10());
				dsT304.setColumn(i, "ADDT01" , list.get(i).getADDT01());
				
				dsT304.setColumn(i, "VKGRP"    , list.get(i).getVKGRP()    );
				dsT304.setColumn(i, "VKGRP_NM" , list.get(i).getVKGRP_NM() );
				dsT304.setColumn(i, "ZKUNNR_NM", list.get(i).getZKUNNR_NM());
				dsT304.setColumn(i, "GSNAM"    , list.get(i).getGSNAM()    );
				dsT304.setColumn(i, "BUYR_NM"  , list.get(i).getBUYR_NM()  );
				dsT304.setColumn(i, "SPART"    , list.get(i).getSPART()    );
				dsT304.setColumn(i, "LAND1"    , list.get(i).getLAND1()    );
			}
			
			resultVO.addDataset(dsT304);
			
			String brand = DatasetUtility.getString(dsAbrand, 0, "ATWTB");
			
			if("GSP_MR".equals(brand) || "GSP_MRL".equals(brand)) {
				paramVo.setPLM_YN("Y");
			} else {
				paramVo.setPLM_YN("");
			}
			
			// block
			List<Sso0055VO> listBlock = service.searchBlockName(paramVo);
				
			for (int i = 0; i < listBlock.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
				dsBlock.appendRow(); // 행추가
				dsBlock.setColumn(i, "NO", i + 1);
				dsBlock.setColumn(i, "MANDT", listBlock.get(i).getMANDT());
				dsBlock.setColumn(i, "POSID", listBlock.get(i).getPOSID());
				dsBlock.setColumn(i, "SEQ", listBlock.get(i).getSEQ());
				dsBlock.setColumn(i, "BLSEQ", listBlock.get(i).getBLSEQ());
				dsBlock.setColumn(i, "BDMNG", listBlock.get(i).getBDMNG());
				dsBlock.setColumn(i, "ZCTOTAL", listBlock.get(i).getZCTOTAL());
				dsBlock.setColumn(i, "SALES", listBlock.get(i).getSALES());
				dsBlock.setColumn(i, "BLOCKNAME", listBlock.get(i).getBLOCKNAME());
				dsBlock.setColumn(i, "MAKTX", listBlock.get(i).getMAKTX());

				if ("X".equals(dsBlock.getColumnAsString(i, "SALES"))) {

					String zctotal = StringUtils.defaultString(dsBlock.getColumnAsString(i, "ZCTOTAL"));
					String bdmng = StringUtils.defaultString(dsBlock.getColumnAsString(i, "BDMNG"));
					if (NumberUtils.isNumber(zctotal) && NumberUtils.isNumber(bdmng)) {
						Float bdmng2 = Float.parseFloat(bdmng);
						if (bdmng2 > 1) {
							Float messg = Float.parseFloat(zctotal) / Float.parseFloat(bdmng) * 100;
							dsBlock.setColumn(i, "MESSG", messg);
						}
					}

				}

			}

			resultVO.addDataset(dsBlock);
			
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			resultVO.addDataset(dsError);
		}
		
		return view;
	}
	
/*	2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start
	@RequestMapping(value="searchExchange")
	public View Sso0055SearchExchangeView(MipParameterVO paramVO, Model model) {
		
		Dataset dsExchange = paramVO.getDataset("ds_exchange");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
		
		Sso0055ParamVO param = new Sso0055ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		try {
			
			String mandt     = DatasetUtility.getString(dsExchange, 0, "MANDT"    );
			String tcurrdate = DatasetUtility.getString(dsExchange, 0, "TCURRDATE");
			
			if (mandt     == null) mandt     = paramVO.getVariable("G_MANDT");
			if (tcurrdate == null) tcurrdate = "";
			
			param.setMANDT(mandt);
			param.setTCURRDATE(tcurrdate);
			
			dsExchange.deleteAll();
			
			List<Sso0055VO> list = service.searchExchange(param); // 서비스CALL
			
			for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
				dsExchange.appendRow(); 	// 행추가
				dsExchange.setColumn(i, "KRWUSD", list.get(i).getKRWUSD());
				dsExchange.setColumn(i, "KRWJPY", list.get(i).getKRWJPY());
				dsExchange.setColumn(i, "KRWEUR", list.get(i).getKRWEUR());
			}
			
			resultVO.addDataset(dsExchange);
			
			model.addAttribute("resultVO", resultVO);
			
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError); 	// 오류결과내역
		
		return view;
		
	}
	2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End */

	@RequestMapping(value="searchExchange")
	public View Sso0055SearchExchangeView(MipParameterVO paramVO, Model model) {
		
		Dataset dsExchange = paramVO.getDataset("ds_exchange");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		MipResultVO resultVO = new MipResultVO(); // RFC 출력 dataset을 return
				
		ExchangeS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		try {
			
			ExchangeVO exchangeRate = null;
			ExchangeVO paramExchangeVO = new ExchangeVO();
			paramExchangeVO.setMandt(DatasetUtility.getString(dsExchange, 0, "MANDT"));
			paramExchangeVO.setKurst(DatasetUtility.getString(dsExchange, 0, "KURST"));
			paramExchangeVO.setBasedt(DatasetUtility.getString(dsExchange, 0, "GDATU"));
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
			
		} catch (Exception e) { 
			e.printStackTrace();	
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsExchange);
		resultVO.addDataset(dsError); 	// 오류결과내역
		model.addAttribute("resultVO", resultVO);
		
		return view;
		
	}
	
	@RequestMapping(value = "checkRouting")
	public View checkRouting(MipParameterVO paramVO, Model model) {
		Dataset ds_updatedChar	= paramVO.getDataset("ds_updated_char");
		Dataset ds_routing		= paramVO.getDataset("ds_routing");
		Dataset ds_routStatus	= paramVO.getDataset("ds_rout_status");

		MipResultVO resultVO = new MipResultVO();

		ds_routing.deleteAll();
		ds_routStatus.deleteAll();

		ZWEB_PP_CHECK_CODENROUTINGStub stub;
		ZWEB_PP_CHECK_CODENROUTINGStub.ZWEB_PP_CHECK_CODENROUTING param;
		ZWEB_PP_CHECK_CODENROUTINGStub.ZWEB_PP_CHECK_CODENROUTINGResponse response;

//		stub = (ZWEB_PP_CHECK_CODENROUTINGStub) CallSAP.createStub(
//				ZWEB_PP_CHECK_CODENROUTINGStub.class, 
//				paramVO.getVariable("G_SYSID"), 
//				paramVO.getVariable("G_MANDT")
//			);
		stub = (ZWEB_PP_CHECK_CODENROUTINGStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), new Spras(paramVO.getVariable("G_LANG")).toString()).create(ZWEB_PP_CHECK_CODENROUTINGStub.class);
		try {
			for(int i=0; i < ds_updatedChar.getRowCount(); i++) {
				param = new ZWEB_PP_CHECK_CODENROUTINGStub.ZWEB_PP_CHECK_CODENROUTING();
	
				param.setI_POSID(ZWEB_PP_CHECK_CODENROUTINGStub.Char24.Factory.fromString(ds_updatedChar.getColumnAsString(i, "POSID"), ""));
				param.setI_CODE(ZWEB_PP_CHECK_CODENROUTINGStub.Char20.Factory.fromString(ds_updatedChar.getColumnAsString(i, "ATNAM"), ""));
				param.setRTN_TBL(new TABLE_OF_ZPPT124());
	
				response = stub.zWEB_PP_CHECK_CODENROUTING(param);
				String rtn_status = response.getRTN_STATUS().toString(); 
				if(rtn_status.equals("2") || rtn_status.equals("5")) {	//공정중이 아닐때도 rtn_status = '', 승인(2)제외 미진행(5)제외
					continue;
				}

				ZWEB_PP_CHECK_CODENROUTINGStub.ZPPT124[] arrZppt124 = response.getRTN_TBL().getItem();
				for(ZWEB_PP_CHECK_CODENROUTINGStub.ZPPT124 zppt124: arrZppt124) {
					ds_routing.appendRow();
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "POSID", zppt124.getPOSID().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "ITEM_NO", zppt124.getITEM_NO().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "MATNR", zppt124.getMATNR().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "CODE", zppt124.getCODE().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "SEQ", zppt124.getSEQ().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "JTYPE", zppt124.getJTYPE().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "STATUS", zppt124.getSTATUS().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "ILDAT", zppt124.getILDAT().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "VALUE_B", zppt124.getVALUE_B().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "VALUE", zppt124.getVALUE().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "S_RQ_USER", zppt124.getS_RQ_USER().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "S_RQ_DAT", zppt124.getS_RQ_DAT().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "CNG_USEDAT", zppt124.getCNG_USEDAT().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "CRDAT", zppt124.getCRDAT().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "CRTIM", zppt124.getCRTIM().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "CRNAM", zppt124.getCRNAM().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "UPDAT", zppt124.getUPDAT().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "UPNAM", zppt124.getUPNAM().toString());
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "UPTIM", zppt124.getUPTIM().toString());

					ds_routing.setColumn(ds_routing.getRowCount() - 1, "VALUE_B", ds_updatedChar.getColumnAsString(i, "ATWRTB"));
					ds_routing.setColumn(ds_routing.getRowCount() - 1, "VALUE", ds_updatedChar.getColumnAsString(i, "ATWRT"));

					ds_routStatus.appendRow();
					ds_routStatus.setColumn(ds_routing.getRowCount() - 1, "STATUS", rtn_status);
				}
			}

			resultVO.addDataset(ds_routing);
			resultVO.addDataset(ds_routStatus);
			model.addAttribute("resultVO", resultVO);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return view;
	}

	@RequestMapping(value = "requestChange")
	public View requestChange(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();

		Dataset ds_routing	= paramVO.getDataset("ds_routing");
		Dataset ds_receiver	= paramVO.getDataset("ds_receiver");
		Dataset dsError 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		String partnerZ3 = "";
		
		try {
			for (int i = 0; i < ds_routing.getRowCount(); i++) {
				if(i== 0)
					partnerZ3 = service.getPartnerZ3(paramVO.getVariable("G_MANDT"), ds_routing.getColumnAsString(0, "POSID"));
					if (partnerZ3 != null ) {
						partnerZ3 = partnerZ3.replace("H", "");
					}

				ds_routing.setColumn(i, "S_RQ_DAT", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
				ds_routing.setColumn(i, "S_RQ_USER", paramVO.getVariable("G_USER_ID"));

				SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"),
						paramVO.getVariable("G_MANDT"),
						"hdel.sd.sso.domain.ZWEB_PP_MOD_APP1_CODENROUTING",
						new Object[] {
							"",	//CNG_USEDAT
							ds_routing.getColumnAsString(i, "CODE"),              
							ds_routing.getColumnAsString(i, "ILDAT"),             
							ds_routing.getColumnAsString(i, "ITEM_NO"),           
							ds_routing.getColumnAsString(i, "JTYPE"),             
							ds_routing.getColumnAsString(i, "MATNR"),             
							ds_routing.getColumnAsString(i, "POSID"),             
							ds_routing.getColumnAsString(i, "SEQ"),               
							ds_routing.getColumnAsString(i, "STATUS"),            
							ds_routing.getColumnAsString(i, "S_RQ_DAT"),          
							ds_routing.getColumnAsString(i, "S_RQ_USER"),         
							ds_routing.getColumnAsString(i, "VALUE"),
							ds_routing.getColumnAsString(i, "VALUE_B")
						},
						false);

				webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub stubManager;
				webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub.ZWEB_PP_GET_PRODMANAGER param;
				ZWEB_PP_GET_PRODMANAGERStub.ZWEB_PP_GET_PRODMANAGERResponse respManager;

//				stubManager = (ZWEB_PP_GET_PRODMANAGERStub) CallSAP.createStub(
//								functions.rfc.sap.document.sap_com.ZWEB_PP_GET_PRODMANAGERStub.class, 
//								paramVO.getVariable("G_SYSID"), 
//								paramVO.getVariable("G_MANDT")
//							);
				stubManager = (ZWEB_PP_GET_PRODMANAGERStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), new Spras(paramVO.getVariable("G_LANG")).toString()).create(ZWEB_PP_GET_PRODMANAGERStub.class);
				param = new webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub.ZWEB_PP_GET_PRODMANAGER();

				param.setI_POSID(Char24.Factory.fromString(ds_routing.getColumnAsString(i, "POSID"), ""));
				param.setI_ITEM_NO(Char9.Factory.fromString(ds_routing.getColumnAsString(i, "ITEM_NO"), ""));
				param.setRTN_TBL(new TABLE_OF_ZPPS015());

				respManager = stubManager.zWEB_PP_GET_PRODMANAGER(param);
				ZWEB_PP_GET_PRODMANAGERStub.ZPPS015[] arrZpps015 = respManager.getRTN_TBL().getItem();
				for(ZWEB_PP_GET_PRODMANAGERStub.ZPPS015 zpps015 : arrZpps015) {
					ds_receiver.appendRow();
					ds_receiver.setColumn(ds_receiver.getRowCount() - 1, "POSID", ds_routing.getColumnAsString(i, "POSID"));
					ds_receiver.setColumn(ds_receiver.getRowCount() - 1, "PSNO", zpps015.getPSNO().toString());
					
					if(partnerZ3 != null) {
						ds_receiver.appendRow();
						ds_receiver.setColumn(ds_receiver.getRowCount() - 1, "POSID", ds_routing.getColumnAsString(i, "POSID"));
						ds_receiver.setColumn(ds_receiver.getRowCount() - 1, "PSNO", partnerZ3);
					}	
				}
			}

			resultVO.addDataset(ds_routing);
			resultVO.addDataset(ds_receiver);
			model.addAttribute("resultVO", resultVO);
			return view;
		} catch (CommRfcException e) {
			// RfC Exc
			logger.info("@@@@@@@ RfC Exception ERROR!!!");
			e.printStackTrace();

			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError); // 오류결과내역
		} catch (Exception e) {
			// java Exc
			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();

			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError); // 오류결과내역
		}

//		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value = "getAprManager")
	public View getAprManager(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();

		String posid 		= paramVO.getVariable("fa_posid");
		Dataset ds_receiver = paramVO.getDataset("ds_receiver");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub stub;
		webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub.ZWEB_PP_GET_PRODMANAGER param;
		ZWEB_PP_GET_PRODMANAGERStub.ZWEB_PP_GET_PRODMANAGERResponse response;
		try {
//			stub = (ZWEB_PP_GET_PRODMANAGERStub) CallSAP.createStub(
//						functions.rfc.sap.document.sap_com.ZWEB_PP_GET_PRODMANAGERStub.class, 
//						paramVO.getVariable("G_SYSID"), 
//						paramVO.getVariable("G_MANDT")
//					);
			stub = (ZWEB_PP_GET_PRODMANAGERStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), new Spras(paramVO.getVariable("G_LANG")).toString()).create(ZWEB_PP_GET_PRODMANAGERStub.class);
			param = new webservice.stub.sap.ZWEB_PP_GET_PRODMANAGERStub.ZWEB_PP_GET_PRODMANAGER();

			param.setI_POSID(Char24.Factory.fromString(posid, ""));
			param.setRTN_TBL(new TABLE_OF_ZPPS015());

			response = stub.zWEB_PP_GET_PRODMANAGER(param);
			ZWEB_PP_GET_PRODMANAGERStub.ZPPS015[] arrZpps015 = response.getRTN_TBL().getItem();
			for(ZWEB_PP_GET_PRODMANAGERStub.ZPPS015 zpps015 : arrZpps015) {
				ds_receiver.appendRow();
				ds_receiver.setColumn(ds_receiver.getRowCount() - 1, "PSNO", zpps015.getPSNO().toString());
			}

			resultVO.addDataset(ds_receiver);
			model.addAttribute("resultVO", resultVO);
//			return view;

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

//		resultVO.addDataset(ds_receiver);
//		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value = "requestChange2")
	public View requestChange2(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();

		Dataset ds_receiver = paramVO.getDataset("ds_routing");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		webservice.stub.sap.ZWEB_PP_CHECK_CODENROUTINGStub stub;
		webservice.stub.sap.ZWEB_PP_CHECK_CODENROUTINGStub.ZWEB_PP_CHECK_CODENROUTING param;
		ZWEB_PP_CHECK_CODENROUTINGStub.ZWEB_PP_CHECK_CODENROUTINGResponse response;
		try {
//			stub = (ZWEB_PP_CHECK_CODENROUTINGStub) CallSAP.createStub(functions.rfc.sap.document.sap_com.ZWEB_PP_CHECK_CODENROUTINGStub.class, paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"));
			stub = (ZWEB_PP_CHECK_CODENROUTINGStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), new Spras(paramVO.getVariable("G_LANG")).toString()).create(ZWEB_PP_CHECK_CODENROUTINGStub.class);
			param = new webservice.stub.sap.ZWEB_PP_CHECK_CODENROUTINGStub.ZWEB_PP_CHECK_CODENROUTING();

			param.setI_POSID(webservice.stub.sap.ZWEB_PP_CHECK_CODENROUTINGStub.Char24.Factory.fromString("152340L01", ""));
			param.setI_CODE(Char20.Factory.fromString("EL_ACDF", ""));
			param.setRTN_TBL(new TABLE_OF_ZPPT124());

			response = stub.zWEB_PP_CHECK_CODENROUTING(param);
			ZWEB_PP_CHECK_CODENROUTINGStub.ZPPT124[] zppt124;
			zppt124 = response.getRTN_TBL().getItem();

			resultVO.addDataset(ds_receiver);
			model.addAttribute("resultVO", resultVO);
			return view;

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value = "completeChange")
	public View completeChange(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();

		Dataset ds_routing	= paramVO.getDataset("ds_routing");
		Dataset dsError 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		try {

			for (int i = 0; i < ds_routing.getRowCount(); i++) {
//				ds_routing.setColumn(i, "S_RQ_DAT", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
//				ds_routing.setColumn(i, "S_RQ_USER", paramVO.getVariable("G_USER_ID"));
				ds_routing.setColumn(i, "CNG_USEDAT", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));

				SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"),
						paramVO.getVariable("G_MANDT"),
						"hdel.sd.sso.domain.ZWEB_PP_MOD_APP1_CODENROUTING",
						new Object[] { 
							ds_routing.getColumnAsString(i, "CNG_USEDAT"),       
							ds_routing.getColumnAsString(i, "CODE"),              
							ds_routing.getColumnAsString(i, "ILDAT"),             
							ds_routing.getColumnAsString(i, "ITEM_NO"),           
							ds_routing.getColumnAsString(i, "JTYPE"),             
							ds_routing.getColumnAsString(i, "MATNR"),             
							ds_routing.getColumnAsString(i, "POSID"),
							ds_routing.getColumnAsString(i, "SEQ"),               
							ds_routing.getColumnAsString(i, "STATUS"),            
							"",	//S_RQ_DAT 
							"",	//S_RQ_USER
							ds_routing.getColumnAsString(i, "VALUE"),
							ds_routing.getColumnAsString(i, "VALUE_B")
						},
						false);
			}

			resultVO.addDataset(ds_routing);
			model.addAttribute("resultVO", resultVO);
			return view;
			// dsError = CallSAP.makeErrorInfo(listMsg);
			// if( listMsg.length != 0 )
			// {
			// if ( "4".equals( listMsg[0].getIDX().toString() ) )
			// {
			// resultVO.addDataset(dsError); // 오류결과내역
			// model.addAttribute("resultVO", resultVO);
			// return view;
			// }
			// }

		} catch (CommRfcException e) {
			// RfC Exc
			logger.info("@@@@@@@ RfC Exception ERROR!!!");
			e.printStackTrace();

			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError); // 오류결과내역
		} catch (Exception e) {
			// java Exc
			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();

			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError); // 오류결과내역
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/**
	 * 단가 합계 전 Duty 체크 실행
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="singleGenCostDuty")
	public View singleGenCostDuty(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO 	= new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		
		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.singleGenCostDuty(paramVO, session, model);
		} catch (Exception e) {
			logger.error("@@@ singleGenCostDuty Exception ERROR!!!");
		}
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	/**
	 * Duty 체크 분리 단가 합계
	 * HISTORY  : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50612AF4FA9C010FE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
	 *            http://elsprd.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50A3651B524E410DE1008000CBF22514/wsdl11/allinone/ws_policy/document?sap-client=183
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="singleCost")
	public View singleCost(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO 	= new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		
		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.singleCost(paramVO, session, model);
		} catch (Exception e) {
			logger.error("@@@ singleCost Exception ERROR!!!");
		}
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
}
