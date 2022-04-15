package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;   
import hdel.sd.ssa.domain.ZSDS0042;				// ������� ��� ����Ʈ 
import hdel.sd.ssa.domain.ZWEB_SD_CHN_152_INGT;	// ������� ���� ����Ʈ  
 
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
import com.sap.mw.jco.JCO;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.log4j.Logger; 


/**
 * ����ä�� �� �� ä�� ��Ȳ(û��) _������װ��� (Ssa0021C) Control
 * @Comment 
 * 		1. View find ( ������׸�� ��ȸ )       
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FDFC4BCD68E0205E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910						
 *   	 	- RFC OPERATION NAME : 	ZWEB_SD_CHN_152_INGT 
 *   	 	- RFC OBJ NAME 		 : 	ZSDS0042
 *     	2. View save ( ������׸�� ���� )     
 *        	- ��
 *     	 
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0021")
public class Ssa0021C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	 
	/*-----------------------------------------------------
	 * ������� ��� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find") 
	public View Ssa0021FindView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Ssa0021FindView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 	= paramVO.getDataset("ds_cond");
		
		// ȣ���� (E_TYPE) DATASET GET
		Dataset ds_result 	= paramVO.getDataset("ds_result");
		ds_result.appendRow();  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 	= null;											// UI�� return�� out dataset
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") );  
		logger.debug("@@@ ds_cond.BELNR 	===>"+DatasetUtility.getString(ds_cond,"BELNR")); 	// û����ȣ(��ǥ��ȣ) 
		logger.debug("@@@ ds_cond.GUBUN  	===>"+DatasetUtility.getString(ds_cond,"GUBUN"));  	// ����(Q:��ȸ, S:����)
		logger.debug("@@@ ds_cond.KIDNO 	===>"+DatasetUtility.getString(ds_cond,"KIDNO")); 	// ������Ʈ��ȣ
		logger.debug("@@@ ds_cond.KUNNR   	===>"+DatasetUtility.getString(ds_cond,"KUNNR"));  	// �ŷ�����ȣ 
		//--------------------------------------------------------------------------------------------
		 
		try {  
			
			// RFC INPUT PARAM DECLARE 
			ZSDS0042[] 	list 	= new ZSDS0042[]{};  // RFC output list 
			ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};  // �����޼���
			
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{ 
					  ""											// IO_������� ���
					, DatasetUtility.getString(ds_cond, "BELNR")  	// I_û����ȣ(��ǥ��ȣ)      
					, DatasetUtility.getString(ds_cond, "GUBUN")  	// I_����(Q:��ȸ, S:����)  
					, DatasetUtility.getString(ds_cond, "KIDNO")  	// I_������Ʈ��ȣ          
					, DatasetUtility.getString(ds_cond, "KUNNR")  	// I_�ŷ�����ȣ         	
					, listMsg 										// IO_O_ERROR INFO
					, list											// IO_O_��� : ZSDS0042 : ������׸��  
			}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_INGT"
												, obj, false);  
			
			// RFC ��±���ü�� out dataset(ds) ����
			ds_list = CallSAP.isJCO() ? new Dataset() : ZSDS0042.getDataset(); 	// ������׸��
   
			// RFC ȣ����(T_ITAB,T_ITAB2)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds_list, stub.getOutput("T_ITAB")); 				// ������׸��   
			
			// ȣ����   : ������׹��ڿ�
			ds_result.setColumn(0, "R_INGTEXT", (String)stub.getOutput("C_INGTEXT"));	// ������׹��ڿ�
						
			// ���  dataset�� �÷� �߰�
			CallSAP.makeDsCol(ds_list , "SAVE_YN"	, 1);	// �����󿩺�
			CallSAP.makeDsCol(ds_list , "CHK"		, 1);	// ���ÿ���
			CallSAP.makeDsCol(ds_list , "FLAG"		, 1);	// ���濩��
			CallSAP.makeDsCol(ds_list , "NEW_YN"	, 1);	// �űԿ���
			CallSAP.makeDsCol(ds_list , "DISGBN_CHK", 1);	// ǥ�ÿ���(1:ǥ��,0:��ǥ��)   
			for (int i=0;i<ds_list.getRowCount();i++)
			{ 
				ds_list.setColumn(i, "SAVE_YN"		, "N");
				ds_list.setColumn(i, "CHK"			, "0");
				ds_list.setColumn(i, "FLAG"			, "");
				ds_list.setColumn(i, "NEW_YN"		, "N"); 
				if ("X".equals(ds_list.getColumn(i, "DISGBN").toString()))  // ǥ�⿩��
				{ 
					ds_list.setColumn(i, "DISGBN_CHK"	, "1");	// ǥ��
				}
				else
				{ 
					ds_list.setColumn(i, "DISGBN_CHK"	, "0");	// ��ǥ��
				} 
			} 
			
			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0021FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0021FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0021FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");  
		} catch (Exception e) { 
			logger.error("@@@ Ssa0021FindView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y"); 
		}	    
		 
		
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list.getRowCount  	===>" + ds_list.getRowCount()  	+ "");
		logger.debug("@@@ ds_result.getRowCount ===>" + ds_result.getRowCount() + "");  
		MipResultVO resultVO = new MipResultVO(); 
		ds_list.setId		("ds_list");  
		ds_result.setId		("ds_result");
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_list);  		// ������׸��
		resultVO.addDataset	(ds_result);  		// �������� (������׹��ڿ�)
		resultVO.addDataset	(ds_error);  		// �������� 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0021FindView addAttribute end" + "");
		
		return view;
	} 

	/*-----------------------------------------------------
	 *  ������� ����
	 ------------------------------------------------------*/
	@RequestMapping(value="save") 
	public View Ssa0021SaveProgressView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ Ssa0021SaveProgressView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 		= paramVO.getDataset("ds_cond");  		// ������� ���� �׸�
		Dataset ds_list_save 	= paramVO.getDataset("ds_list_save");  	// ������� ���� ����Ʈ 

		// OUTPUT DATASET DECLARE 
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
		 
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT	===>"+paramVO.getVariable("G_MANDT"));   
		logger.debug("@@@ ds_cond.BELNR 	===>"+DatasetUtility.getString(ds_cond,"BELNR")); 	// û����ȣ(��ǥ��ȣ) 
		logger.debug("@@@ ds_cond.GUBUN  	===>"+DatasetUtility.getString(ds_cond,"GUBUN"));  	// ����(Q:��ȸ, S:����)
		logger.debug("@@@ ds_cond.KIDNO 	===>"+DatasetUtility.getString(ds_cond,"KIDNO")); 	// ������Ʈ��ȣ
		logger.debug("@@@ ds_cond.KUNNR   	===>"+DatasetUtility.getString(ds_cond,"KUNNR"));  	// �ŷ�����ȣ 
		logger.debug("@@@ ds_list_save.getRowCount	===>"+ds_list_save.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_list_save.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list_save.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list_save["+irow+"Record : "+ds_list_save.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list_save, irow, ds_list_save.getColumnID(icol)));
			}  
		}
		//--------------------------------------------------------------------------------------------
  
					 		
		try { 

			// RFC INPUT PARAM DECLARE   
			// input dataset�� CLASS(ZSDS0042) �� �ű��    
			// ������� RFC input list
			ZSDS0042[] 	list 	= (ZSDS0042[])CallSAPHdel.moveDs2Obj2(ds_list_save, ZSDS0042.class, "");    
			// �����޼���
			ZQMSEMSG[]	listMsg = new ZQMSEMSG[]{};		 
			
			logger.debug("@@@ CLASS(ZSDS0042) �� �ű�� success" + "");  
			logger.debug("@@@ list.length() : " + list.length + ""); 
			
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{   
										""												// IO_������� ���
									  	, DatasetUtility.getString(ds_cond, "BELNR")  	// I_û����ȣ(��ǥ��ȣ)      
										, DatasetUtility.getString(ds_cond, "GUBUN")  	// I_����(Q:��ȸ, S:����)  
										, DatasetUtility.getString(ds_cond, "KIDNO")  	// I_������Ʈ��ȣ          
										, DatasetUtility.getString(ds_cond, "KUNNR")  	// I_�ŷ�����ȣ         	
										, listMsg 										// IO_O_ERROR INFO
										, list											// IO_������������� : ZSDS0039  
										};   
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
											, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_INGT"
											, obj, false); 

			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0021SaveProgressView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0021SaveProgressView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0021SaveProgressView CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0021SaveProgressView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	   
		 	 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_error.getRowCount  	===>" + ds_error.getRowCount() + "");  
		MipResultVO resultVO = new MipResultVO();  
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_error);  	// �������� 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0021SaveProgressView addAttribute end" + "");
		
		return view;
		
	} 
	
}
