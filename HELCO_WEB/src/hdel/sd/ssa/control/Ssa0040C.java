package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0020;
import hdel.sd.com.domain.Com0020ParamVO;
import hdel.sd.ssa.domain.Ssa0020ParamVO;
import hdel.sd.ssa.domain.Ssa0040;
import hdel.sd.ssa.domain.Ssa0040ParamVO;
import hdel.sd.ssa.domain.ZSDS0034;
import hdel.sd.ssa.domain.ZSDS0042;
import hdel.sd.ssa.domain.ZSDS0073;
import hdel.sd.ssa.service.Ssa0040S;
 
import java.io.IOException; 
import java.util.List; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.biz.BusinessContext;
import tit.biz.BusinessException;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.mm03.ws.ZMMS011;
// import com.helco.xf.cs96.ws.ZCSS_TEST;   // �׽�Ʈ�� ����
import com.sap.mw.jco.JCO;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger; 


/**
 * ���ݰ�꼭Cû����Ȳ(Ssa0040C) Control
 * @Comment  		
 *     	1. View find 		( ���ݰ�꼭Cû����Ȳ�� ��ȸ )     
 *        	- T-CODE             : 	ZSDR236
 *   	 	- RFC URL 			 : 	(��ȸ) 					http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50A008CD74B5010BE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : 	(��ȸ)			ZWEB_SD_CHN_235 		(ZSDR236)
 *   	 	- RFC OBJ NAME 		 : 	(��ȸ)			ZSDR236   
 *   	2. View save ( ���ݰ�꼭Cû����Ȳ�� ���� )     
 *        	- T-CODE             : 	ZSDR235
 *   	 	- RFC URL 			 : 	(����) 					http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_50A29ECE55B30084E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : 	(����)			ZWEB_SD_CHN_235_SAVE 		(ZSDR235)
 *   	 	- RFC OBJ NAME 		 : 	(����)			ZSDR235
 * @since 1.0 
 * 		History
 * 		1.0  2012.11.13  khs  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0040")
public class Ssa0040C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
		
	@Autowired
	private Ssa0040S service;
	
	/*-----------------------------------------------------
	 *  �������� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_auart")
	public View Ssa0040FindAuartView(MipParameterVO paramVO, Model model) {
		
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_auart_cd");	// UI�� return�� out dataset  
		 
		try { 
			
			Ssa0040ParamVO param = new Ssa0040ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMandt	(paramVO.getVariable("G_MANDT"));  						// SAP CLIENT
						
			// ����CALL
			List<Ssa0040> list = service.find_auart(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				dsList.appendRow();  
	    		// �÷�SET 
				dsList.setColumn(iRow, "CODE"			,list.get(iRow).getAcode());				// ���������ڵ�  
				dsList.setColumn(iRow, "CODE_NAME"	,list.get(iRow).getAcode_name());		// ���������ڵ��   
			} 
			
			//dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			dsList.setId	("ds_auart_cd");
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	
	/*-----------------------------------------------------
	 *  �����ڵ� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_reason")
	public View Ssa0040FindReasonView(MipParameterVO paramVO, Model model) {
		
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_reason");	// UI�� return�� out dataset  
		 
		try { 
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// ����CALL
			List<Ssa0040> list = service.find_reason();  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				dsList.appendRow();  
	    		// �÷�SET 
				dsList.setColumn(iRow, "CODE"			,list.get(iRow).getScode());			// �����ڵ�  
				dsList.setColumn(iRow, "CODE_NAME"	,list.get(iRow).getScode_t());		// �ڵ峻��   
			} 
			
			//dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			dsList.setId	("ds_reason");
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	
	/*-----------------------------------------------------
	 *  �����Ͽ� �ش��ϴ� ���ݰ�꼭Cû����Ȳ ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find") 
	public View Ssa0040FindView(MipParameterVO paramVO, Model model) { 

		logger.debug("@@@ Ssa0040FindView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 			= paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_itab = null;				// UI�� return�� out dataset
		Dataset ds_error= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)						
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				);  
		logger.debug("@@@ ds_cond.I_YMD 			===>"+DatasetUtility.getString(ds_cond,"I_YMD"			)); // ���س����
		logger.debug("@@@ ds_cond.I_VKBUR  		===>"+DatasetUtility.getString(ds_cond,"I_VKBUR"		)); // �μ��ڵ�
		logger.debug("@@@ ds_cond.I_VKGRP 		===>"+DatasetUtility.getString(ds_cond,"I_VKGRP"		)); // ���ڵ�
		logger.debug("@@@ ds_cond.I_ZKUNNR   	===>"+DatasetUtility.getString(ds_cond,"I_ZKUNNR"		)); // �����
		logger.debug("@@@ ds_cond.I_AUART    		===>"+DatasetUtility.getString(ds_cond,"I_AUART"		)); // ��������_FR
		logger.debug("@@@ ds_cond.I_RAD1    		===>"+DatasetUtility.getString(ds_cond,"I_RAD1"			)); // �Ѱ�ǥ���(��ü)
		logger.debug("@@@ ds_cond.I_RAD2  		===>"+DatasetUtility.getString(ds_cond,"I_RAD2"			)); // �Ѱ�ǥ���(����)
		logger.debug("@@@ ds_cond.I_RAD3  		===>"+DatasetUtility.getString(ds_cond,"I_RAD3"			)); // ������Ʈ����Ȳ
		logger.debug("@@@ ds_cond.I_CHK1 			===>"+DatasetUtility.getString(ds_cond,"I_CHK1"			)); // ����������Ʈ
		logger.debug("@@@ ds_cond.I_GBN  			===>"+DatasetUtility.getString(ds_cond,"I_GBN"			)); // ������
		//--------------------------------------------------------------------------------------------
		  
		try {  
			
			// RFC INPUT PARAM DECLARE 
			ZSDS0073[] 	list 		= new ZSDS0073[]{}; 			 	//  RFC output list
			ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};  	 	// �����޼���
			
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{ 
					 DatasetUtility.getString(ds_cond, "I_AUART")  		// ��������_FR
					, DatasetUtility.getString(ds_cond, "I_CHK1")  		// ����������Ʈ
					, DatasetUtility.getString(ds_cond, "I_GBN")  		// ������ INFO
					, DatasetUtility.getString(ds_cond, "I_RAD1")  		// �Ѱ�ǥ���(��ü)
					, DatasetUtility.getString(ds_cond, "I_RAD2")  		// �Ѱ�ǥ���(����)
					, DatasetUtility.getString(ds_cond, "I_RAD3")  		// ������Ʈ����Ȳ
					, DatasetUtility.getString(ds_cond, "I_VKBUR")   	// �μ��ڵ�
					, DatasetUtility.getString(ds_cond, "I_VKGRP")  		// ���ڵ�
					, DatasetUtility.getString(ds_cond, "I_YMD")  		// ���س����
					, DatasetUtility.getString(ds_cond, "I_ZKUNNR")  	// �����
					, listMsg 													// O_ERROR INFO
					, list														// O_��� : ZSDR236
			};
						
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_235"
												, obj, false); 
			 
			// RFC ��±���ü�� out dataset(ds) ����
			ds_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0073.getDataset(); 
			
			// RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds_itab, stub.getOutput("T_ITAB1")); 		//��ȸ�������Ʈ

			// ���  dataset�� �÷� �߰� 
			/*for (int i=0;i<ds_itab.getRowCount();i++)
			{ 
				ds_itab.setColumn(i, "I_YMD"			, DatasetUtility.getString(ds_cond, "I_YMD") );
			}  */
			
			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0040FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0040FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0040FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0040FindView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	 
		
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list_itab.getRowCount  ===>" + ds_itab.getRowCount() 	);
		logger.debug("@@@ ds_error.getRowCount	  ===>" + ds_error.getRowCount()	); 
		
		MipResultVO resultVO = new MipResultVO();
		ds_itab.setId		("ds_list_itab");  
		ds_error.setId		("ds_error");  
		resultVO.addDataset	(ds_itab);  	// ����Ʈ
		resultVO.addDataset	(ds_error); 	// ��������
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0040FindView addAttribute end" + "");
		
		return view;
	}
	
	/*-----------------------------------------------------
	 *  ����Cû����Ȳ ����
	 ------------------------------------------------------*/
	@RequestMapping(value="save") 
	public View Ssa0040SaveView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ssa0021SaveProgressView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_save_cond 		= paramVO.getDataset("ds_save_cond");  	//���� �׸�	
		Dataset ds_list_save 	= paramVO.getDataset("ds_list_itab");  	// ���� ����Ʈ 
		
		// OUTPUT DATASET DECLARE 
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
				
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT	===>"+paramVO.getVariable("G_MANDT"));   
		logger.debug("@@@ ds_cond.I_YMD   	===>"+DatasetUtility.getString(ds_save_cond,"I_YMD")); 			// ������
		logger.debug("@@@ ds_cond.I_UNAME  	===>"+DatasetUtility.getString(ds_save_cond,"I_UNAME"));  	// �����
		logger.debug("@@@ ds_list_save.getRowCount	===>"+ds_save_cond.getRowCount() 		+ "");
		
		for( int irow = 0; irow < ds_list_save.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list_save.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list_save["+irow+"Record : "+ds_list_save.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list_save, irow, ds_list_save.getColumnID(icol)));
			}  
		}
		
		try { 

			// RFC INPUT PARAM DECLARE   
			// input dataset�� CLASS(ZSDS0073) �� �ű��    
			// RFC input list 
			ZSDS0073[] 	list 	= (ZSDS0073[])CallSAPHdel.moveDs2Obj2(ds_list_save, ZSDS0073.class, "");    
			// �����޼���
			ZQMSEMSG[]	listMsg = new ZQMSEMSG[]{};		 
			
			logger.debug("@@@ CLASS(ZSDS0073) �� �ű�� success" + "");  
			logger.debug("@@@ list.length() : " + list.length + ""); 
			
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{   
									  	  DatasetUtility.getString(ds_save_cond, "I_UNAME")  		// �����      
										, DatasetUtility.getString(ds_save_cond, "I_YMD")  			// ������  	
										, listMsg 															// IO_O_ERROR INFO
										, list																// IO_������ : ZSDS0073  
										};   

			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
											, "hdel.sd.ssa.domain.ZWEB_SD_CHN_235_SAVE"
											, obj, false); 
			System.out.println("@@@ RFC CALL  success" + "");  
			
			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0040SaveView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0040SaveView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0040SaveView CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0040SaveView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	   
		 	 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_error.getRowCount  	===>" + ds_error.getRowCount() + "");  
		MipResultVO resultVO = new MipResultVO();  
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_error);  	// �������� 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0040SaveView addAttribute end" + "");
		
		return view;
	}
}
