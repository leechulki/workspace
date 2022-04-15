package hdel.sd.scl.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.sd.scl.domain.ZSDS0067;  		// �ŷ��� �繫���� 
import hdel.sd.scl.domain.ZSDS0068;  		// �ŷ��� ä����Ȳ 

import java.io.IOException;
	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
// import com.helco.xf.cs96.ws.ZCSS_TEST;   // �׽�Ʈ�� ����
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.log4j.Logger;  


/**
 * �ŷ��� ä����Ȳ (Scl0060C) Control
 * @Comment 
 *     	1. View find ( �ŷ��� ä����Ȳ )     
 *        	- T-CODE             : ZSDR506 
 *   	 	- RFC URL 			 : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_500E1055856301DDE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : ZWEB_SD_CHN_506
 *   	 	- RFC OBJ NAME 		 : ZSDS0067, ZSDS0068 
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  �輱ȣ  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("scl0060")
public class Scl0060C {
	 
	Logger logger = Logger.getLogger(this.getClass()); 

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession; 
	
	@RequestMapping(value="find")
	public View Scl0060FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond 			= paramVO.getDataset("ds_cond");			// �⺻ �˻� ����
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0067[] 	list 		= new ZSDS0067[]{};  // �ŷ��� ���
		ZSDS0068[] 	list_d 		= new ZSDS0068[]{};  // �ŷ��� ä����Ȳ  
		ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};	
		 
		// OUTPUT DATASET DECLARE
		Dataset 	ds_list		= null;				// UI�� return�� out dataset
		Dataset 	ds_list_dtl	= null;				// UI�� return�� out dataset  
		Dataset 	ds_error	= null;				// UI�� return�� error out dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 			+"");  
		logger.debug("@@@ ds_cond.BUYR	===>"+DatasetUtility.getString(ds_cond,"BUYR")+"");
		logger.debug("@@@ ds_cond.RANK	===>"+DatasetUtility.getString(ds_cond,"RANK")+"");
		logger.debug("@@@ ds_cond.STADA	===>"+DatasetUtility.getString(ds_cond,"STADA")	+""); 
		
		//--------------------------------------------------------------------------------------------
		
		// RFC INPUT SET (�Ķ���� ��������)
		Object obj[] = new Object[]{
				  DatasetUtility.getString(ds_cond, "BUYR")  	// IN_�ŷ����ڵ�
				 ,DatasetUtility.getString(ds_cond, "RANK")  	// IN_�ð��ɷ¼���
				 ,DatasetUtility.getString(ds_cond, "STADA")  	// IN_������
				 ,listMsg										// O_ERROR INFO 
				 ,list_d                                        // O_ZSDS0068 : �ŷ��� ä����Ȳ
				 ,list											// O_��� : ZSDS0067 : �ŷ��� �繫����
		}; 
		 
		try { 

			logger.debug("@@@ ZWEB_SD_CHN_506 start" + "");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.scl.domain.ZWEB_SD_CHN_506"
												, obj, false);	
			
			// RFC ��±���ü�� out dataset(ds) ����
			ds_list     = CallSAP.isJCO() ? new Dataset() : ZSDS0067.getDataset();
			ds_list_dtl = CallSAP.isJCO() ? new Dataset() : ZSDS0068.getDataset();
			
			// RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds_list, stub.getOutput("T_ITAB"));  
			CallSAP.moveObj2Ds(ds_list_dtl, stub.getOutput("P_ITAB"));  

			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);
			 
			logger.debug("@@@ ZWEB_SD_CHN_506 SUCCESS!!!" + "");
			
		} catch (CommRfcException e) { 
			logger.debug("@@@ ZWEB_SD_CHN_506 CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
		} catch (Exception e) { 
			logger.debug("@@@ ZWEB_SD_CHN_506 Exception ERROR!!!" + "");
			e.printStackTrace(); 
		}	 
		 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount() + "");
		logger.debug("@@@ ds_list_dtl.getRowCount ===>" + ds_list_dtl.getRowCount() + "");
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId	("ds_list");
		ds_list_dtl.setId	("ds_list_dtl");
		ds_error.setId	("ds_error"); 
		resultVO.addDataset(ds_list); 		    // �ŷ��� �繫����
		resultVO.addDataset(ds_list_dtl); 		// �ŷ��� ä����Ȳ
		resultVO.addDataset(ds_error);  	    // ��������
		model.addAttribute("resultVO", resultVO);   

		logger.debug("@@@ addAttribute end" + "");
		
		return view;
	}
		
} 