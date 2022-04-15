package hdel.sd.sco.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;   
import hdel.sd.sco.domain.ZSDS0031;  		// ����� ���� ���ݰ�ȹ/����/������Ȳ ���VO 
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
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.log4j.Logger; 


/**
 * ���� ���ݰ�ȹ/����/������Ȳ(Sco0100C) Control
 * @Comment 
 *     1. View 	find ( ���� ���ݰ�ȹ/����/������Ȳ ��� ��ȸ )     
 *        	- T-CODE             : ZSDR176
 *   	 	- RFC URL 			 : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FB9A3DA4F120110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : ZWEB_SD_CHN_176
 *   	 	- RFC OBJ NAME 		 : ZSDS0031
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("sco0100")
public class Sco0100C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession; 
	
	@RequestMapping(value="find")
	public View Sco0100FindView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sco0100FindView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond 		= paramVO.getDataset("ds_cond");
		Dataset ds_cond_so_list = paramVO.getDataset("ds_cond_so_list");	// SO��ȣ��� ����
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list	= null;											// UI�� return�� out dataset  
		Dataset ds_error= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 			+"");  
		logger.debug("@@@ ds_cond.FR_SO	===>"+DatasetUtility.getString(ds_cond,"FR_SO")	+""); // IN_SO_FR  
		logger.debug("@@@ ds_cond.FR_VB	===>"+DatasetUtility.getString(ds_cond,"FR_VB")	+""); // IN_�μ��ڵ�   
		logger.debug("@@@ ds_cond.FR_VG	===>"+DatasetUtility.getString(ds_cond,"FR_VG")	+""); // IN_���ڵ�_FR 
		logger.debug("@@@ ds_cond.FR_YM	===>"+DatasetUtility.getString(ds_cond,"FR_YM")	+""); // IN_���ֳ��    
		logger.debug("@@@ ds_cond.SMAN	===>"+DatasetUtility.getString(ds_cond,"SMAN")	+""); // IN_������ڵ�  
		logger.debug("@@@ ds_cond.TO_SO	===>"+DatasetUtility.getString(ds_cond,"TO_SO")	+""); // IN_SO_TO  
		logger.debug("@@@ ds_cond.TO_VG	===>"+DatasetUtility.getString(ds_cond,"TO_VG")	+""); // IN_���ڵ�_TO
		for( int irow = 0; irow < ds_cond_so_list.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_cond_so_list.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_cond_so_list["+irow+"Record : "
								+ ds_cond_so_list.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_cond_so_list, irow, ds_cond_so_list.getColumnID(icol)) );
			}  
		}
		//--------------------------------------------------------------------------------------------
		 
		 
		try { 

			// RFC INPUT PARAM DECLARE 
			ZSDS0031[] 	list 	= new ZSDS0031[]{};  // ����� ���� ���ݰ�ȹ/����/������ RFC output list
			ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
			RANGE_C10[] list_so = (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_so_list	, RANGE_C10.class, "");	// SO���Ǹ��
			 
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{
					  DatasetUtility.getString(ds_cond, "FR_SO")  	// IN_SO_FR
					 ,DatasetUtility.getString(ds_cond, "FR_VB")  	// IN_�μ��ڵ�
					 ,DatasetUtility.getString(ds_cond, "FR_VG")  	// IN_���ڵ�_FR
					 ,DatasetUtility.getString(ds_cond, "FR_YM")  	// IN_���ֳ�� 
					 ,DatasetUtility.getString(ds_cond, "SMAN")  	// IN_������ڵ�
					 ,DatasetUtility.getString(ds_cond, "TO_SO")  	// IN_SO_TO
					 ,DatasetUtility.getString(ds_cond, "TO_VG")  	// IN_���ڵ�_TO 
					 , listMsg										// O_ERROR INFO
					 , list_so										// SO���Ǹ��
					 , list											// O_��� : ZSDS0031 : ����� ���� ���ݰ�ȹ/����/������
			}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sco.domain.ZWEB_SD_CHN_176", obj, false);	
			
			// RFC ��±���ü�� out dataset(ds) ����
			ds_list = CallSAP.isJCO() ? new Dataset() : ZSDS0031.getDataset();
			
			// RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds_list, stub.getOutput("T_ITAB")); 

			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Sco0100FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Sco0100FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Sco0100FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y"); 
		} catch (Exception e) { 
			logger.error("@@@ Sco0100FindView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	 
		 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list.getRowCount 	===>" + ds_list.getRowCount() + "");
		logger.debug("@@@ ds_error.getRowCount 	===>" + ds_error.getRowCount() + "");
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		("ds_list");
		ds_error.setId		("ds_error");  
		resultVO.addDataset	(ds_list); 			// ����� ���� ���ݰ�ȹ/����/������
		resultVO.addDataset	(ds_error);  		// ��������
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Sco0100FindView addAttribute end" + "");
		
		return view;
	}
	
 
}
