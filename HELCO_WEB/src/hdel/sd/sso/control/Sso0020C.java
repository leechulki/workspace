package hdel.sd.sso.control;

/**
 * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ - ï¿½ï¿½ï¿½Ö°ï¿½ï¿½ï¿½
 * 
 * ï¿½Û¼ï¿½  ï¿½ï¿½ï¿½ï¿½   : 2012.06
 * HISTORY  : ï¿½Ï´Ü¿ï¿½ Ç¥ï¿½ï¿½
 */
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.sso.domain.Sso0020ParamVO;
import hdel.sd.sso.domain.Sso0020VO;
import hdel.sd.sso.domain.ZSDS0050;
import hdel.sd.sso.domain.ZSDS0052;
import hdel.sd.sso.service.Sso0020S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("sso0020")
public class Sso0020C extends CallSAP{
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Sso0020S service;
	
	/**
	 * 
	 * HISTORY  : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FE2972AB54D0205E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View find(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsInput = paramVO.getDataset("ds_input");
		logger.info("@@@@@@@@@ ¿µ¾÷»ç¾ç Àü¼Û´ë»ó Á¶È¸ control in @@@@@@@@@@@@@@");
		logger.info(dsInput.toString());
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0050[] list 	= new ZSDS0050[]{};  // sap output list
		ZQMSEMSG[] listMsg  = new ZQMSEMSG[]{};  // sap ¿¡·¯¸Þ½ÃÁö return¿ë
		logger.info("@@@@@@@@ ZSDS0050 list : "+ list.toString());
		logger.info("@@@@@@@@ ZQMSEMSG list : "+ listMsg.toString());
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsInput, "I_FR_SO")  //FR_SO
				, listMsg
				, list
		}; 

		// OUTPUT DATASET DECLARE
		Dataset ds_output	= null;
		Dataset dsError 	= null;
		
		MipResultVO resultVO = new MipResultVO();
		 
		try { 

			//CW00002=ÇÊ¼ö ÀÔ·ÂÇ×¸ñÀÔ´Ï´Ù.\nÈ®ÀÎÇÏ¿© ÁÖ½Ê½Ã¿À.
			if ( "".equals( dsInput.getColumn(0, "I_FR_SO").toString() ) 
					|| dsInput.getColumn(0, "I_FR_SO").toString() == null )
			{
				throw new BizException("CW00002,SOï¿½ï¿½È£");
			}
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_LANG")
								, "hdel.sd.sso.domain.ZWEB_SD_CHN_100"			
								, obj, false);	

			ds_output = CallSAP.isJCO() ? new Dataset() : ZSDS0050.getDataset();
			ds_output.setId("ds_output");  
			
			CallSAP.moveObj2Ds(ds_output, stub.getOutput("T_ITAB"));
			
			logger.info("@@@@@@@ O_ETAB : "+stub.getOutput("O_ETAB"));
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			if( dsError.getRowCount() > 0 )
			{
				if ( "4".equals( dsError.getColumn(0, "IDX") ) )
				{
					logger.info("@@@@@@@ dsError : "+dsError.toString());
					resultVO.addDataset(dsError);
				}
			}
			
			
		} catch (CommRfcException e) { 
			// RfC Exc
			logger.info("@@@@@@@ RfC Exception ERROR!!!");
			e.printStackTrace();
		} catch (BizException e) {
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
		} catch (Exception e) {
			// java Exc
			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();
		}	 
		
		logger.info("@@@@@@@ ds_output.toString ===>" + ds_output.toString());
				
		resultVO.addDataset(ds_output);
		model.addAttribute("resultVO", resultVO);  
		return view;
	}

	@RequestMapping(value="checkTransportable")
	public View checkTransportable(MipParameterVO paramVO, Model model) {
		Dataset dsInput = paramVO.getDataset("ds_input");
		Dataset dsTransCheck = paramVO.getDataset("ds_transCheck");
		Dataset dsError = paramVO.getDataset("ds_error");

		Sso0020ParamVO param = new Sso0020ParamVO();
		try {

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMANDT(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVBELN(DatasetUtility.getString(dsInput, "I_FR_SO")); 
			
			dsTransCheck = service.checkTransportable(paramVO.getVariable("G_MANDT"), dsInput, dsError);

			MipResultVO resultVO = new MipResultVO();
			dsTransCheck.setId("ds_transCheck");
			resultVO.addDataset(dsTransCheck);
			resultVO.addDataset(dsError);

			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value="checkChnsoFinl")
	public View checkChnsoFinl(MipParameterVO paramVO, Model model) {
		Dataset dsInput = paramVO.getDataset("ds_input");
		Dataset dsFinlCheck = paramVO.getDataset("ds_finlCheck");
		Dataset dsError = paramVO.getDataset("ds_error");
		
		Sso0020ParamVO param = new Sso0020ParamVO();
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			param.setMANDT(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVBELN(DatasetUtility.getString(dsInput, "I_FR_SO")); 
			
			dsFinlCheck = service.checkChnsoFinl(paramVO.getVariable("G_MANDT"), dsInput, dsError);
			
			MipResultVO resultVO = new MipResultVO();
			dsFinlCheck.setId("ds_finlCheck");
			resultVO.addDataset(dsFinlCheck);
			resultVO.addDataset(dsError);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	/**
	 * history : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FE24164B54D0205E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
	 * 
	 */
	@RequestMapping(value="detail")
	public View detail(MipParameterVO paramVO, Model model) {
		Dataset dsOutput = paramVO.getDataset("ds_output");
		String flag 	 = paramVO.getVariable("popFlag");
		
		ZSDS0050[] input 	= (ZSDS0050[])moveDs2Obj(dsOutput, ZSDS0050.class, "");
		ZSDS0052[] list 	= new ZSDS0052[]{};  // sap output list
		ZQMSEMSG[] listMsg	= new ZQMSEMSG[]{};
		
		Object obj[] = new Object[]{
				flag//DatasetUtility.getString(dsInput, "I_FR_SO")  //FR_SO
				, listMsg
				, list
				, input
		}; 
		
		logger.info("@@@@ obj ===>" + listMsg + ", " + list + ", " + input);

		Dataset ds_output	 = null;
		Dataset dsError 	 = null;
		Integer last90Seq 	 = 0;
		Integer gspCnt		 = 0;
		MipResultVO resultVO = new MipResultVO();
		
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			last90Seq = service.getLast90Seq(paramVO.getVariable("G_MANDT"), dsOutput.getColumnAsString(0, "VBELN"));
			gspCnt   = service.getGspCnt(paramVO.getVariable("G_MANDT"), dsOutput.getColumnAsString(0, "VBELN"));
			
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
						, "hdel.sd.sso.domain.ZWEB_SD_CHN_100_CMD"
						, obj, false);	

			ds_output = CallSAP.isJCO() ? new Dataset() : ZSDS0052.getDataset();
			ds_output.setId("ds_output");  

			CallSAP.moveObj2Ds(ds_output, stub.getOutput("P_ITAB"));

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			if( dsError.getRowCount() > 0 ) {
				if( "4".equals( dsError.getColumn(0, "IDX") ) ) {
					resultVO.addDataset(dsError);
				}
			}
		} catch (CommRfcException e) { 
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	 
		
		resultVO.addVariable("last90Seq", last90Seq.toString());
		resultVO.addVariable("gspCnt", gspCnt.toString());
		resultVO.addDataset(ds_output); 
		model.addAttribute("resultVO", resultVO);  
		return view;
	}
	@RequestMapping(value="configEmailInfo")
	public View configEmailInfo(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			service.configEmailInfo(paramVO, resultVO);
		} catch(Exception e) {
			e.printStackTrace();
//			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
//			resultVO.addDataset(dsError);
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}
	@RequestMapping(value="findAutoLP")
	public View Sso0020findAutoLP(MipParameterVO paramVO, Model model) {

		Dataset dsInput = paramVO.getDataset("ds_input");
		Dataset dsautoLP = paramVO.getDataset("ds_autoLP");
		Dataset ds_error = paramVO.getDataset("ds_error");

		Sso0020ParamVO param = new Sso0020ParamVO();

		logger.info(dsInput.toString());
		
		try {

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMANDT(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVBELN(DatasetUtility.getString(dsInput, "I_FR_SO")); 
			
			List<Sso0020VO> list = service.selectAutoLP(param);
			List<Sso0020VO> list2 = service.selectSpecE(param);

			// AUTOLP 
			for (int i=0;i<list.size();i++) {
				dsautoLP.appendRow();
				dsautoLP.setColumn(i, "AUTOLP"   , list.get(i).getAUTOLP());
				dsautoLP.setColumn(i, "AUTOLP_P" , list.get(i).getAUTOLP_P());
				dsautoLP.setColumn(i, "QTNUM" , list.get(i).getQTNUM());
				dsautoLP.setColumn(i, "KUNNR_Z3" , list.get(i).getKUNNR_Z3());
				dsautoLP.setColumn(i, "LP_CHN" , list.get(i).getLP_CHN());
			}
			for (int i=0;i<list2.size();i++) {
				dsautoLP.setColumn(i, "SPEC_E" ,"Y");
			}
			
			MipResultVO resultVO = new MipResultVO();
			dsautoLP.setId("ds_autoLP");
			resultVO.addDataset(dsautoLP);

			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;  
	}

}
