package hdel.sd.scl.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.scl.domain.ZSDS_PJT;  		// ������Ʈ�� ���ݰ������� (Scl0040)
import hdel.sd.scl.domain.ZSDS0014;  		// ���ݰ������� ���������� (Scl00401-�˾�)
import hdel.sd.scl.domain.ZSDS0015;  		// ���ݰ������� ������    (Scl00401-�˾�)
import hdel.sd.scl.domain.ZSDS0014E;  		// ���ݰ������� ���������� (Scl00403-�˾�)
import hdel.sd.scl.domain.ZSDS0015E;  		// ���ݰ������� ������    (Scl00403-�˾�)
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
 * �ؿܼ��ݻ����� ��ȸ(Scl0043C) Control
 * @Comment 
 *     1. View 	find ( ���ݻ����� ��ȸ )     
 *        	- T-CODE             : ZSDR117E 
 *   	 	- RFC URL 			 : http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FE26175B54D0205E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : ZWEB_SD_CHN_117E
 *   	 	- RFC OBJ NAME 		 : ZSDS0014E(���� ����������), ZSDS0015E(���� ������)
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  �輱ȣ  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("scl0043")
public class Scl0043C {
	 
	Logger logger = Logger.getLogger(this.getClass()); 

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	   
	@RequestMapping(value="find")
	public View Scl0043FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");
		 
		// RFC INPUT PARAM DECLARE 
		ZSDS0014E[] 	list_m 	= new ZSDS0014E[]{};  // ���ݰ������� ���������� RFC output list
		ZSDS0015E[] 	list_h 	= new ZSDS0015E[]{};  // ���ݰ������� ������ RFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};  // �����޼���
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds_m 	= null;				// UI�� return�� out dataset (���ݰ������� ���������� )
		Dataset 	ds_h 	= null;				// UI�� return�� out dataset (���ݰ������� ������) 
		Dataset 	ds_error= null;				// UI�� return�� error out dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 			);  
		logger.debug("@@@ ds_cond.PROJ	===>"+DatasetUtility.getString(ds_cond,"PROJ")	);;  
		//--------------------------------------------------------------------------------------------
		
		// RFC INPUT SET (�Ķ���� ��������)
		Object obj[] = new Object[]{
				""   // C_ISVAT
				, DatasetUtility.getString(ds_cond, "PROJ")  // IN_������ƮID (6�ڸ�)
				, listMsg 									// O_ERROR INFO
				, list_h									// O_��� : ZSDS0015 : ���ݰ������� �󼼸������
				, list_m									// O_��� : ZSDS0014 : ���ݰ������� ����������
		}; 

		String c_isvat = "";	// �ΰ��� ��󿩺�
		MipResultVO resultVO = new MipResultVO();

		try { 

			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.scl.domain.ZWEB_SD_CHN_117E", obj, false);	

			// RFC ��±���ü�� out dataset(ds) ����
			ds_m = CallSAP.isJCO() ? new Dataset() : ZSDS0014E.getDataset(); // ���������� 
			ds_h = CallSAP.isJCO() ? new Dataset() : ZSDS0015E.getDataset();	// �󼼸������
			
			// RFC ȣ����(T_ITAB,P_ITAB)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds_m, stub.getOutput("T_ITAB")); // ����������
			CallSAP.moveObj2Ds(ds_h, stub.getOutput("P_ITAB"));	// �󼼸������

			c_isvat = (String)stub.getOutput("C_ISVAT");		// �ΰ��� ��󿩺�
			
			// ȣ����   
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);
			
			logger.debug("@@@ ZWEB_SD_CHN_117E SUCCESS!!!");

			// �ΰ������Կ��θ� �� �࿡ �����Ѵ�.
			CallSAP.makeDsCol(ds_h, "C_ISVAT", 1);	
			for (int i=0;i<ds_h.getRowCount();i++)
			{
				ds_h.setColumn(i, "C_ISVAT", c_isvat); 
			}
			
			// RFC ��� dataset�� return
			logger.debug("@@@ ds_list_m.getRowCount 		===>" + ds_m.getRowCount());
			logger.debug("@@@ ds_list_h_temp.getRowCount 	===>" + ds_h.getRowCount()); 
			logger.debug("@@@ ds_error.getRowCount 		===>" + ds_error.getRowCount());
			
			ds_m.setId		("ds_list_m");   
			ds_h.setId		("ds_list_h");   
			resultVO.addDataset(ds_m);  		// ������ ����
			resultVO.addDataset(ds_h);  		// �󼼸�� ���� 

			logger.debug("@@@ addAttribute end");
			
		} catch (CommRfcException e) {
			ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
			logger.debug("@@@ Scl0043FindView CommRfcException ERROR!!!");
			e.printStackTrace();  
		} catch (Exception e) {
			ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
			logger.debug("@@@ Scl0043FindView Exception ERROR!!!");
			e.printStackTrace(); 
		}	  

		ds_error.setId	("ds_error");   
		resultVO.addDataset(ds_error);  	// ��������

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
} 