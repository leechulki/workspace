package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.sbp.domain.ZSDS0038;
import hdel.sd.ssa.domain.Ssa0020;
import hdel.sd.ssa.domain.Ssa0020ParamVO;
import hdel.sd.ssa.service.Ssa0020S;
import hdel.sd.ssa.domain.ZSDS0034;	// �̼�(1), �ε�(2) ��¸���Ʈ
import hdel.sd.ssa.domain.ZSDS0036;	// ���ݰ�ȹ����Ʈ(3) ��¸���Ʈ
import hdel.sd.ssa.domain.ZSDS0039;	// �̼�(1), �ε�(2) ���ݰ�ȹ ���� �Է¸���Ʈ
import hdel.sd.ssa.domain.ZSDT0053;	// �̼�(1), �ε�(2) ä�ǻ��� ���� �Է¸���Ʈ 
import hdel.sd.com.domain.RANGE_C10;// SO, �ŷ����� �ڵ�������� DATASET 
import hdel.sd.com.domain.RANGE_C30;// ������Ʈ�� �ڵ�������� DATASET
 
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

import org.apache.log4j.Logger; 


/**
 * ����ä�� �� �� ä����Ȳ(û����)_�߻���������Ʈ ��� ��� ��ȸ (Ssa0023C) Control
 * @Comment 
 * 		1. View find 		( ����ä�� �� �� ä����Ȳ(û����)_�߻���������Ʈ ���  )     
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	(��ȸ) 					http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FBE0081D32E0110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : 	(�̼�, �ε� ��ȸ)			ZWEB_SD_CHN_152 		(ZSDS0034)
 *   								(���ݸ���Ʈ ��ȸ)			ZWEB_SD_CHN_152 		(ZSDS0036)
 *   	 	- RFC OBJ NAME 		 : 	(�̼�, �ε� ��ȸ)			ZSDS0034
 *   								(���ݸ���Ʈ ��ȸ)			ZSDS0036 
 *   
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0023")
public class Ssa0023C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession; 
	 
	/*-----------------------------------------------------
	 * �߻���������Ʈ ��� ��� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find") 
	public View Ssa0020FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_list_filter");	// ����ä�� �� �� ä����Ȳ���
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0034[] 	list_out_h	= new ZSDS0034[]{};  // �߻���������Ʈ RFC output list (HEADER)
		ZSDS0034[] 	list_out_l	= new ZSDS0034[]{};  // �߻���������Ʈ RFC output list (LIST)
		ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};  // �����޼��� 
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds_itab = null;				// UI�� return�� out dataset (�߻���������Ʈ) 
		Dataset 	ds_error= null;				// UI�� return�� error out dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT	===>"		+ paramVO.getVariable("G_MANDT") + "");   
		logger.debug("@@@ ds_list.getRowCount	===>"	+ ds_list.getRowCount() 		 + "");
		for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list["+irow+"Record : "+ds_list.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list, irow, ds_list.getColumnID(icol)) );
			}  
		} 
		//--------------------------------------------------------------------------------------------
		  
		try {  
			 
			// RFC INPUT PARAM DECLARE  
			ZSDS0034[] 	list_in 	= (ZSDS0034[])CallSAPHdel.moveDs2Obj2(ds_list, ZSDS0034.class, ""); // ����ä�� �� �� ä����Ȳ���    
			
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{  list_in		// IN_��� : ZSDS0034 : ����ä�� �� �� ä����Ȳ���    
										, listMsg		// OUT_ERROR INFO 
										, list_out_h	// OUT_HEADER ��� : ZSDS0034 : �߻���������Ʈ HEADER
										, list_out_l	// OUT_LIST ���     : ZSDS0034 : �߻���������Ʈ LIST
										}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152"
												, obj, false); 
			 
			// RFC ��±���ü�� out dataset(ds) ����
			ds_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0034.getDataset(); 	// �߻���������Ʈ
			
			// RFC ȣ����(T_ITAB,T_ITAB2)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds_itab, stub.getOutput("T_ITAB")); 				// �߻���������Ʈ
			  
			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);
			 
			logger.debug("@@@ ZWEB_SD_CHN_152 SUCCESS!!!" + "");
			
		} catch (CommRfcException e) { 
			logger.debug("@@@ ZWEB_SD_CHN_152 CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
		} catch (Exception e) { 
			logger.debug("@@@ ZWEB_SD_CHN_152 Exception ERROR!!!" + "");
			e.printStackTrace();  
		}	 
		 
		
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list_itab.getRowCount  ===>" + ds_itab.getRowCount() 	); 
		logger.debug("@@@ ds_error.getRowCount		===>" + ds_error.getRowCount()	); 
		MipResultVO resultVO = new MipResultVO();
		ds_itab.setId		("ds_list_itab");   
		ds_error.setId		("ds_error");  
		resultVO.addDataset	(ds_itab);  	// �߻���������Ʈ
		resultVO.addDataset	(ds_error); 	// ��������
		model.addAttribute("resultVO", resultVO);   

		logger.debug("@@@ addAttribute end" + "");
		
		return view;
	}
	
	 
}
