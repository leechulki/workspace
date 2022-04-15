package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.sd.com.domain.RANGE_C10;
import hdel.sd.com.domain.RANGE_C30;
import hdel.sd.ssa.domain.ZSDS0032;  		// [SD]����ä�� AGING SCHEDULE 

import java.io.IOException;
	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.log4j.Logger; 


/**
 * [SD]����ä�� AGING SCHEDULE (Ssa0030C) Control
 * @Comment      
 *     	1. View find 		( ����ä�� �� �� ä����Ȳ(û����) ��� ��ȸ )     
 *        	- T-CODE             : 	ZSDR183 
 *   	 	- RFC URL 			 : 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FB22EEE04670046E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910				
 *   	 	- RFC OPERATION NAME : 	ZWEB_SD_CHN_183
 *   	 	- RFC OBJ NAME 		 : 	ZSDS0032 
 *   
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0030")
public class Ssa0030C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession; 
	
	@RequestMapping(value="find")
	public View Ssa0030CFindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");
		Dataset ds_cond_prjt_list 	= paramVO.getDataset("ds_cond_prjt_list");	// ������Ʈ��ȣ��� ����
		Dataset ds_cond_buyr_list 	= paramVO.getDataset("ds_cond_buyr_list");	// �ŷ������ ���� 
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0032[] 	list 		= new ZSDS0032[]{};  // [SD]����ä�� AGING SCHEDULE RFC output list
		ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};
		RANGE_C30[] list_prjt 	= (RANGE_C30[])CallSAPHdel.moveDs2Obj2(ds_cond_prjt_list, RANGE_C30.class, "");	// ������Ʈ���Ǹ��
		RANGE_C10[] list_buyr 	= (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_buyr_list, RANGE_C10.class, "");	// �ŷ������Ǹ��
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI�� return�� out dataset 
		Dataset 	ds_error 	= null;			// UI�� return�� out dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				+"");  
		logger.debug("@@@ ds_cond.BS_YMD	===>"+DatasetUtility.getString(ds_cond,"BS_YMD")	+""); 	// ��������
		logger.debug("@@@ ds_cond.FR_PAYER	===>"+DatasetUtility.getString(ds_cond,"FR_PAYER")	+"");	// ���ڵ�_FR
		logger.debug("@@@ ds_cond.FR_PJT	===>"+DatasetUtility.getString(ds_cond,"FR_PJT")  	+""); 	// ������Ʈ_FR
		logger.debug("@@@ ds_cond.FR_VB		===>"+DatasetUtility.getString(ds_cond,"FR_VB")   	+""); 	// �μ��ڵ�_FR
		logger.debug("@@@ ds_cond.FR_VG		===>"+DatasetUtility.getString(ds_cond,"FR_VG")   	+""); 	// ���ڵ�_FR
		logger.debug("@@@ ds_cond.MG_YMD	===>"+DatasetUtility.getString(ds_cond,"MG_YMD")  	+"");	// ��������
		logger.debug("@@@ ds_cond.PRT_GBN	===>"+DatasetUtility.getString(ds_cond,"PRT_GBN") 	+""); 	// ��±���(O,C)
		logger.debug("@@@ ds_cond.TO_PAYER	===>"+DatasetUtility.getString(ds_cond,"TO_PAYER")	+""); 	// ���ڵ�_TO
		logger.debug("@@@ ds_cond.TO_PJT	===>"+DatasetUtility.getString(ds_cond,"TO_PJT")  	+""); 	// ������Ʈ_TO
		logger.debug("@@@ ds_cond.TO_VB		===>"+DatasetUtility.getString(ds_cond,"TO_VB")   	+""); 	// �μ��ڵ�_TO
		logger.debug("@@@ ds_cond.TO_VG		===>"+DatasetUtility.getString(ds_cond,"TO_VG")   	+"");	// ���ڵ�_TO
		logger.debug("@@@ ds_cond.YMD_GBN	===>"+DatasetUtility.getString(ds_cond,"YMD_GBN") 	+""); 	// ���ڱ���(D/M)
		//--------------------------------------------------------------------------------------------
		
		// RFC INPUT SET (�Ķ���� ��������)
		Object obj[] = new Object[]{
				  DatasetUtility.getString(ds_cond, "BS_YMD") 	// ��������     
				 ,DatasetUtility.getString(ds_cond, "FR_PAYER")	// ���ڵ�_FR  
				 ,DatasetUtility.getString(ds_cond, "FR_PJT")  	// ������Ʈ_FR  
				 ,DatasetUtility.getString(ds_cond, "FR_VB")   	// �μ��ڵ�_FR  
				 ,DatasetUtility.getString(ds_cond, "FR_VG")   	// ���ڵ�_FR    
				 ,DatasetUtility.getString(ds_cond, "MG_YMD")  	// ��������     
				 ,DatasetUtility.getString(ds_cond, "PRT_GBN") 	// ��±���(O,C)
				 ,DatasetUtility.getString(ds_cond, "TO_PAYER")	// ���ڵ�_TO  
				 ,DatasetUtility.getString(ds_cond, "TO_PJT")  	// ������Ʈ_TO  
				 ,DatasetUtility.getString(ds_cond, "TO_VB")   	// �μ��ڵ�_TO  
				 ,DatasetUtility.getString(ds_cond, "TO_VG")   	// ���ڵ�_TO    
				 ,DatasetUtility.getString(ds_cond, "YMD_GBN") 	// ���ڱ���(D/M) 
				, listMsg										// �����޼���
				, list_prjt										// ������Ʈ���Ǹ��
				, list_buyr										// �ŷ������Ǹ��
				, list											// O_��� : ZSDS0032
		}; 
		 
		try { 

			logger.debug("@@@ ZWEB_SD_CHN_183 start" + "");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_183"
												, obj, false);	 
			
			logger.debug("@@@ 11111111111111111111111" + "");
			
			// RFC ��±���ü�� out dataset(ds) ����
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0032.getDataset(); 
			
			// RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));  
			
			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			
			logger.debug("@@@ ZWEB_SD_CHN_183 SUCCESS!!!" + "");
			
		} catch (CommRfcException e) { 
			logger.debug("@@@ ZWEB_SD_CHN_183 CommRfcException ERROR!!!" + "");
			e.printStackTrace();  
		} catch (Exception e) { 
			logger.debug("@@@ ZWEB_SD_CHN_183 Exception ERROR!!!" + "");
			e.printStackTrace(); 
		}	 
		 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list.getRowCount 	===>" + ds.getRowCount() + "");
		logger.debug("@@@ ds_error.getRowCount 	===>" + ds_error.getRowCount() + "");
		MipResultVO resultVO = new MipResultVO();
		ds.setId("ds_list");  
		ds_error.setId("ds_error");
		resultVO.addDataset(ds); 			// ����ä�� AGING SCHEDULE RFC output list
		resultVO.addDataset(ds_error);  	// ��������
		model.addAttribute("resultVO", resultVO);   
		 
		logger.debug("@@@ addAttribute end" + "");
		
		return view;
	}

}
	