package hdel.sd.scl.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.sd.scl.domain.ZSDS_PJT;  		// ������Ʈ�� ���ݰ������� (Scl0040)
import hdel.sd.scl.domain.ZSDS0014;  		// ���ݰ������� ���������� (Scl00401-�˾�)
import hdel.sd.scl.domain.ZSDS0015;  		// ���ݰ������� ������    (Scl00401-�˾�)
import hdel.sd.ssa.domain.ZSDT0053;
import hdel.sd.com.domain.RANGE_C10;		// SO, �ŷ����� �ڵ�������� DATASET

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
 * ������Ʈ�� ���ݴ���(Scl0040C) Control
 * @Comment 
 *     	1. View find ( ������Ʈ�� ���ݸ�� ��ȸ )     
 *        	- T-CODE             : ZSDR117 
 *   	 	- RFC URL 			 : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FA8CB6DB8C3013FE1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : ZWEB_SD_PROJECT
 *   	 	- RFC OBJ NAME 		 : ZSDS_PJT 
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("scl0040")
public class Scl0040C {
	 
	Logger logger = Logger.getLogger(this.getClass()); 

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession; 
	
	@RequestMapping(value="find")
	public View Scl0040FindView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Scl0040FindView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond 			= paramVO.getDataset("ds_cond");			// �⺻ �˻� ����
		Dataset ds_cond_so_list 	= paramVO.getDataset("ds_cond_so_list");	// SO��ȣ��� ����
		Dataset ds_cond_buyr_list 	= paramVO.getDataset("ds_cond_buyr_list");	// �ŷ������ ���� 
		  
		// OUTPUT DATASET DECLARE
		Dataset ds_list	= null;											// UI�� return�� out dataset  
		Dataset ds_error= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 			+"");  
		logger.debug("@@@ ds_cond.FR_YMD	===>"+DatasetUtility.getString(ds_cond,"FR_YMD")+"");
		logger.debug("@@@ ds_cond.TO_YMD	===>"+DatasetUtility.getString(ds_cond,"TO_YMD")+"");
		logger.debug("@@@ ds_cond.FR_SO		===>"+DatasetUtility.getString(ds_cond,"FR_SO")	+"");
		logger.debug("@@@ ds_cond.TO_SO		===>"+DatasetUtility.getString(ds_cond,"TO_SO")	+"");
		logger.debug("@@@ ds_cond.FR_VB		===>"+DatasetUtility.getString(ds_cond,"FR_VB")	+"");
		logger.debug("@@@ ds_cond.TO_VB		===>"+DatasetUtility.getString(ds_cond,"TO_VB")	+"");
		logger.debug("@@@ ds_cond.FR_VG		===>"+DatasetUtility.getString(ds_cond,"FR_VG")	+"");
		logger.debug("@@@ ds_cond.TO_VG		===>"+DatasetUtility.getString(ds_cond,"TO_VG")	+"");
		logger.debug("@@@ ds_cond.SMAN		===>"+DatasetUtility.getString(ds_cond,"SMAN")	+"");
		logger.debug("@@@ ds_cond.BUYR		===>"+DatasetUtility.getString(ds_cond,"BUYR")	+"");
		logger.debug("@@@ ds_cond.AUART		===>"+DatasetUtility.getString(ds_cond,"AUART")	+"");
		logger.debug("@@@ ds_cond.SPART		===>"+DatasetUtility.getString(ds_cond,"SPART")	+"");
		logger.debug("@@@ ds_cond.STADA		===>"+DatasetUtility.getString(ds_cond,"STADA")	+"");
		logger.debug("@@@ ds_cond.BSTNK		===>"+DatasetUtility.getString(ds_cond,"BSTNK")	+"");  
		logger.debug("@@@ ds_cond_so_list.getRowCount	===>"+ds_cond_so_list.getRowCount() + "");
		for( int irow = 0; irow < ds_cond_so_list.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_cond_so_list.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_cond_so_list["+irow+"Record : "
								+ ds_cond_so_list.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_cond_so_list, irow, ds_cond_so_list.getColumnID(icol)));
			}  
		}
		logger.debug("@@@ ds_cond_buyr_list.getRowCount	===>"+ds_cond_buyr_list.getRowCount() 	+ "");
		for( int irow = 0; irow < ds_cond_buyr_list.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_cond_buyr_list.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_cond_buyr_list["+irow+"Record : "
								+ ds_cond_buyr_list.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_cond_buyr_list, irow, ds_cond_buyr_list.getColumnID(icol)));
			}  
		}
		//--------------------------------------------------------------------------------------------
		  
		try {  

			// RFC INPUT PARAM DECLARE 
			ZSDS_PJT[] 	list 		= new ZSDS_PJT[]{};  // ������Ʈ�� ���ݰ������ RFC output list
			ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};	
			RANGE_C10[] list_so 	= (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_so_list	, RANGE_C10.class, "");	// SO���Ǹ��
			RANGE_C10[] list_buyr 	= (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_buyr_list, RANGE_C10.class, "");	// �ŷ������Ǹ��
			 
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{
					  DatasetUtility.getString(ds_cond, "AUART")  	// IN_��������
					 ,DatasetUtility.getString(ds_cond, "BSTNK")  	// IN_�����
					 ,DatasetUtility.getString(ds_cond, "BUYR")  	// IN_����ó
					 ,DatasetUtility.getString(ds_cond, "FR_SO")  	// IN_S/O _FR
					 ,DatasetUtility.getString(ds_cond, "FR_VB")  	// IN_�μ��ڵ� _FR
					 ,DatasetUtility.getString(ds_cond, "FR_VG")  	// IN_���ڵ� _FR
					 ,DatasetUtility.getString(ds_cond, "FR_YMD")  	// IN_������ _FR
					 ,DatasetUtility.getString(ds_cond, "SMAN")  	// IN_�����
					 ,DatasetUtility.getString(ds_cond, "SPART")  	// IN_��ǰ��
					 ,DatasetUtility.getString(ds_cond, "STADA")  	// IN_������
					 ,DatasetUtility.getString(ds_cond, "TO_SO")  	// IN_S/O _TO
					 ,DatasetUtility.getString(ds_cond, "TO_VB") 	// IN_�μ��ڵ� _TO
					 ,DatasetUtility.getString(ds_cond, "TO_VG")  	// IN_���ڵ� _TO
					 ,DatasetUtility.getString(ds_cond, "TO_YMD")  	// IN_������ _TO
					, listMsg										// O_ERROR INFO
					, list_buyr										// �ŷ������Ǹ��
					, list_so										// SO���Ǹ��
					, list											// O_��� : ZSDS_PJT : ������Ʈ���
			}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.scl.domain.ZWEB_SD_PROJECT"
												, obj, false);	
			
			// RFC ��±���ü�� out dataset(ds) ����
			ds_list = CallSAP.isJCO() ? new Dataset() : ZSDS_PJT.getDataset();
			
			// RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds_list, stub.getOutput("T_ITAB"));  

			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Scl0040FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Scl0040FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Scl0040FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Scl0040FindView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	 
		 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount() + "");
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		("ds_list");
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_list); 		// ������Ʈ���
		resultVO.addDataset	(ds_error);  	// ��������
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Scl0040FindView addAttribute end" + "");
		
		return view;
	}
	 
	
} 