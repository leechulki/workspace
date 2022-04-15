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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger; 


/**
 * ����ä�� �� �� ä����Ȳ(û����)(Ssa0020C) Control
 * @Comment 
 * 		1. View find_zclose ( ���س���� �ش��ϴ� ������� ��ȸ )     
 *     	2. View find 		( ����ä�� �� �� ä����Ȳ(û����) ��� ��ȸ )     
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	(��ȸ) 					http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FBE0081D32E0110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *   	 	- RFC OPERATION NAME : 	(�̼�, �ε� ��ȸ)			ZWEB_SD_CHN_152 		(ZSDS0034)
 *   								(���ݸ���Ʈ ��ȸ)			ZWEB_SD_CHN_152 		(ZSDS0036)
 *   	 	- RFC OBJ NAME 		 : 	(�̼�, �ε� ��ȸ)			ZSDS0034
 *   								(���ݸ���Ʈ ��ȸ)			ZSDS0036
 *     	3. View update_plan ( ���س���� �ش��ϴ� �̼�, �ε��� ���ݰ�ȹ���� )     
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	(�̼�, �ε� ���ݰ�ȹ ����) 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FC4981489370110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910									
 *   	 	- RFC OPERATION NAME : 	(�̼�, �ε� ���ݰ�ȹ ����)	ZWEB_SD_CHN_152_PLAN	(ZSDS0039) 
 *   	 	- RFC OBJ NAME 		 : 	(�̼�, �ε� ���ݰ�ȹ ����)	ZSDS0039 
 *     	4. View update_reason ( ���س���� �ش��ϴ� �̼�, �ε��� ä�ǻ������� )     
 *        	- T-CODE             : 	ZSDR152 
 *   	 	- RFC URL 			 : 	(�̼�, �ε� ä�ǻ��� ����) 	http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FC22D3D89930018E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910									
 *   	 	- RFC OPERATION NAME : 	(�̼�, �ε� ä�ǻ��� ����) 	ZWEB_SD_CHN_152_RES   	(ZSDT0053) 
 *   	 	- RFC OBJ NAME 		 : 	(�̼�, �ε� ä�ǻ��� ����) 	ZSDT0053 
 *   
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("ssa0020")
public class Ssa0020C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ssa0020S service;
	
	/*-----------------------------------------------------
	 *  ���س���� �ش��ϴ� ������� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_zclose")
	public View Ssa0020FindZcloseView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ssa0020FindZcloseView START" + "");  
		
		// INPUT DATASET GET
		Dataset ds_cond_zclose 	= paramVO.getDataset("ds_cond_zclose");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_zclose 	= paramVO.getDataset("ds_list_zclose");	// UI�� return�� out dataset   
		
		// �������� DATASET GET
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
						
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 		===>"+paramVO.getVariable("G_MANDT") 			+"");
		//logger.debug("@@@ paramVO.G_LANG		===>"+paramVO.getVariable("G_LANG") 			+""); 
		logger.debug("@@@ ds_cond_zclose.ZYEAR	===>"+DatasetUtility.getString(ds_cond_zclose,"ZYEAR")+"");
		logger.debug("@@@ ds_cond_zclose.ZMONTH	===>"+DatasetUtility.getString(ds_cond_zclose,"ZMONTH")+"");   
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Ssa0020ParamVO param = new Ssa0020ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMandt	(paramVO.getVariable("G_MANDT"));  						// SAP CLIENT
			//param.setLang	(paramVO.getVariable("G_LANG"));	 					// ���
			param.setZyear	(DatasetUtility.getString(ds_cond_zclose,"ZYEAR"));		// ���س⵵
			param.setZmonth	(DatasetUtility.getString(ds_cond_zclose,"ZMONTH"));	// ���ؿ��� 
			
			// ����CALL
			List<Ssa0020> list = service.find_zclose(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{    
				ds_list_zclose.appendRow();  
				ds_list_zclose.setColumn(iRow, "ZCLOSE", list.get(iRow).getZclose());		// ��������
			}    
			
			logger.debug("@@@ Ssa0020FindZcloseView SUCCESS!!!" + ""); 
			 
		} catch (Exception e) { 
			logger.error("@@@ Ssa0020FindZcloseView Exception ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y"); 
		}	     

		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list.getRowCount 	===>" + ds_list_zclose.getRowCount() + "");
		logger.debug("@@@ ds_error.getRowCount 	===>" + ds_error.getRowCount() + ""); 
		MipResultVO resultVO = new MipResultVO(); 
		ds_list_zclose.setId	("ds_list_zclose");
		ds_error.setId			("ds_error");  
		resultVO.addDataset		(ds_list_zclose);
		resultVO.addDataset		(ds_error); 
		model.addAttribute		("resultVO", resultVO);     
		 
		logger.debug("@@@ Ssa0020FindZcloseView addAttribute end" + "");
		
		return view;
		
	}
	
	/*-----------------------------------------------------
	 *  ���س���� �ش��ϴ� �̼�, �ε�, ���ݰ�ȹ����Ʈ ��� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find") 
	public View Ssa0020FindView(MipParameterVO paramVO, Model model) { 

		logger.debug("@@@ Ssa0020FindView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_cond 			= paramVO.getDataset("ds_cond");  
		Dataset ds_cond_prjt_list 	= paramVO.getDataset("ds_cond_prjt_list");	// ������Ʈ��ȣ��� ����
		Dataset ds_cond_buyr_list 	= paramVO.getDataset("ds_cond_buyr_list");	// �ŷ������ ���� 
		
		// OUTPUT DATASET DECLARE
		Dataset ds_itab = null;				// UI�� return�� out dataset (�̼�(��������:1)/�ε�(��������:2) )
		Dataset ds_itab2= null;				// UI�� return�� out dataset (���ݰ�ȹ(��������:3))  
		Dataset ds_error= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				);  
		logger.debug("@@@ ds_cond.FR_AUART 	===>"+DatasetUtility.getString(ds_cond,"FR_AUART"	)); // ���������ڵ�_FR
		logger.debug("@@@ ds_cond.FR_EMPL  	===>"+DatasetUtility.getString(ds_cond,"FR_EMPL"	)); // ������ڵ�_FR
		logger.debug("@@@ ds_cond.FR_PAYER 	===>"+DatasetUtility.getString(ds_cond,"FR_PAYER"	)); // �ŷ����ڵ�_FR
		logger.debug("@@@ ds_cond.FR_PJT   	===>"+DatasetUtility.getString(ds_cond,"FR_PJT"		));	// ������Ʈ_FR
		logger.debug("@@@ ds_cond.FR_VB    	===>"+DatasetUtility.getString(ds_cond,"FR_VB"		));	// �μ��ڵ�_FR
		logger.debug("@@@ ds_cond.FR_VG    	===>"+DatasetUtility.getString(ds_cond,"FR_VG"		));	// ���ڵ�_FR
		logger.debug("@@@ ds_cond.JOB_GBN  	===>"+DatasetUtility.getString(ds_cond,"JOB_GBN"	)); // �������� (1:�̼�, 2:�ε�, 3:���ݰ�ȹ)
		logger.debug("@@@ ds_cond.PRT_GBN  	===>"+DatasetUtility.getString(ds_cond,"PRT_GBN"	)); // ����Ʈ��ı��� (1:����/�ؿܿ���, 2:��������, 3:�ε�)
		logger.debug("@@@ ds_cond.TO_AUART 	===>"+DatasetUtility.getString(ds_cond,"TO_AUART"	)); // ���������ڵ�_TO
		logger.debug("@@@ ds_cond.TO_EMPL  	===>"+DatasetUtility.getString(ds_cond,"TO_EMPL"	)); // ������ڵ�_TO
		logger.debug("@@@ ds_cond.TO_PAYER 	===>"+DatasetUtility.getString(ds_cond,"TO_PAYER"	)); // �ŷ����ڵ�_TO
		logger.debug("@@@ ds_cond.TO_PJT   	===>"+DatasetUtility.getString(ds_cond,"TO_PJT"		));	// ������Ʈ_TO
		logger.debug("@@@ ds_cond.TO_VB    	===>"+DatasetUtility.getString(ds_cond,"TO_VB"		));	// �μ��ڵ�_TO
		logger.debug("@@@ ds_cond.TO_VG    	===>"+DatasetUtility.getString(ds_cond,"TO_VG"		));	// ���ڵ�_TO
		logger.debug("@@@ ds_cond.YEARM    	===>"+DatasetUtility.getString(ds_cond,"YEARM"		));	// ���س�� 
		//--------------------------------------------------------------------------------------------
		  
		try {  
			
			// RFC INPUT PARAM DECLARE 
			ZSDS0034[] 	list_m 		= new ZSDS0034[]{};  // �̼�(��������:1)/�ε�(��������:2) RFC output list
			ZSDS0036[] 	list_h 		= new ZSDS0036[]{};  // ���ݰ�ȹ����Ʈ(��������:3) RFC output list
			ZQMSEMSG[] 	listMsg 	= new ZQMSEMSG[]{};  // �����޼���
			RANGE_C30[] list_prjt 	= (RANGE_C30[])CallSAPHdel.moveDs2Obj2(ds_cond_prjt_list, RANGE_C30.class, "");	// ������Ʈ���Ǹ��
			RANGE_C10[] list_buyr 	= (RANGE_C10[])CallSAPHdel.moveDs2Obj2(ds_cond_buyr_list, RANGE_C10.class, "");	// �ŷ������Ǹ��
			 
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{ 
					  DatasetUtility.getString(ds_cond, "FR_AUART")  	// ���������ڵ�_FR
					, DatasetUtility.getString(ds_cond, "FR_EMPL")   	// ������ڵ�_FR
					, DatasetUtility.getString(ds_cond, "FR_PAYER")  	// �ŷ����ڵ�_FR
					, DatasetUtility.getString(ds_cond, "FR_PJT")  		// ������Ʈ_FR
					, DatasetUtility.getString(ds_cond, "FR_VB")  		// �μ��ڵ�_FR
					, DatasetUtility.getString(ds_cond, "FR_VG")  		// ���ڵ�_FR
					, DatasetUtility.getString(ds_cond, "JOB_GBN")  	// �������� (1:�̼�, 2:�ε�, 3:���ݰ�ȹ)
					, DatasetUtility.getString(ds_cond, "PRT_GBN")  	// ����Ʈ��ı��� (1:����/�ؿܿ���, 2:��������, 3:�ε�)
					, DatasetUtility.getString(ds_cond, "TO_AUART")  	// ���������ڵ�_TO
					, DatasetUtility.getString(ds_cond, "TO_EMPL")  	// ������ڵ�_TO
					, DatasetUtility.getString(ds_cond, "TO_PAYER")  	// �ŷ����ڵ�_TO
					, DatasetUtility.getString(ds_cond, "TO_PJT") 		// ������Ʈ_TO
					, DatasetUtility.getString(ds_cond, "TO_VB")  		// �μ��ڵ�_TO
					, DatasetUtility.getString(ds_cond, "TO_VG")  		// ���ڵ�_TO
					, DatasetUtility.getString(ds_cond, "YEARM")  		// ���س��
					, listMsg 											// O_ERROR INFO
					, list_prjt											// ������Ʈ���Ǹ��
					, list_buyr											// �ŷ������Ǹ��
					, list_h											// O_��� : ZSDS0034 : �̼�(��������:1)/����(��������:2)
					, list_m											// O_��� : ZSDS0036 : ���ݰ�ȹ(��������:3)
			}; 
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID")
								,paramVO.getVariable("G_MANDT")
								, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152"
								, obj
								,false); 
			 
			// RFC ��±���ü�� out dataset(ds) ����
			ds_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0034.getDataset(); // �̼�(��������:1)/����(��������:2)   
			ds_itab2 = CallSAP.isJCO() ? new Dataset() : ZSDS0036.getDataset();	// ���ݰ�ȹ(��������:3) 
			
			// RFC ȣ����(T_ITAB,T_ITAB2)�� out dataset(ds)�� �ű��  
			CallSAP.moveObj2Ds(ds_itab, stub.getOutput("T_ITAB")); 		// �̼�(��������:1)/����(��������:2)
			CallSAP.moveObj2Ds(ds_itab2, stub.getOutput("T_ITAB2"));	// ���ݰ�ȹ(��������:3)   

			// ���  dataset�� �÷� �߰� 
			CallSAP.makeDsCol(ds_itab , "SAVE_YN"	, 1);	// �����󿩺�
			CallSAP.makeDsCol(ds_itab , "CHK"		, 1);	// ���ÿ���
			CallSAP.makeDsCol(ds_itab , "FLAG"		, 1);	// ���濩��    
			for (int i=0;i<ds_itab.getRowCount();i++)
			{ 
				ds_itab.setColumn(i, "SAVE_YN"		, "N");
				ds_itab.setColumn(i, "CHK"			, "0");
				ds_itab.setColumn(i, "FLAG"			, ""); 
				// ȸ����ȹ�� 8�ڸ��� �缳��
				ds_itab.setColumn(i, "PLDAT", CallSAPHdel.dateReplace(ds_itab.getColumnAsString(i, "PLDAT")));  
			}  
			
			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0020FindView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0020FindView SUCCESS!!!" + "");
			}  
			
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0020FindView CommRfcException ERROR!!!" + "");
			e.printStackTrace();   
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0020FindView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	 
		
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list_itab.getRowCount  ===>" + ds_itab.getRowCount() 	);
		logger.debug("@@@ ds_list_itab2.getRowCount	===>" + ds_itab2.getRowCount() 	);
		logger.debug("@@@ ds_error.getRowCount		===>" + ds_error.getRowCount()	); 
		MipResultVO resultVO = new MipResultVO();
		ds_itab.setId		("ds_list_itab");  
		ds_itab2.setId		("ds_list_itab2");
		ds_error.setId		("ds_error");  
		resultVO.addDataset	(ds_itab);  	// �̼�(��������:1)/����(��������:2)
		resultVO.addDataset	(ds_itab2); 	// ���ݰ�ȹ(��������:3)\
		resultVO.addDataset	(ds_error); 	// ��������
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0020FindView addAttribute end" + "");
		
		return view;
	}
	
	

	/*-----------------------------------------------------
	 *  ���س���� �ش��ϴ� �̼�, �ε��� ���ݰ�ȹ����
	 ------------------------------------------------------*/
	@RequestMapping(value="update_plan")
	//public View Ssa0020UpdatePlanView(MipParameterVO paramVO, Model model) {
	public View Ssa0020UpdatePlanView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ssa0020UpdatePlanView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_update_yearm 		= paramVO.getDataset("ds_update_yearm");        // ���س��  
		Dataset ds_list_update_plan 	= paramVO.getDataset("ds_list_update_plan");  	// ���ݰ�ȹ��������Ʈ 
		 
		// OUTPUT DATASET DECLARE 
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 					===>"+paramVO.getVariable("G_MANDT") 			+"");  
		logger.debug("@@@ ds_update_yearm.YEARM 			===>"+DatasetUtility.getString(ds_update_yearm,"YEARM")); // ���س��
		logger.debug("@@@ ds_list_update_plan.getRowCount 	===>"+ds_list_update_plan.getRowCount() + "");
		for( int irow = 0; irow < ds_list_update_plan.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list_update_plan.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list_update_plan["+irow+"Record : "+ds_list_update_plan.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list_update_plan, irow, ds_list_update_plan.getColumnID(icol)) );
			}  
		}
		//--------------------------------------------------------------------------------------------

		
		try { 

			// RFC INPUT PARAM DECLARE   
			// input dataset�� CLASS(ZSDS0039) �� �ű��    
			// �̼�(��������:1)/�ε�(��������:2) ���ݰ�ȹ RFC input list
			ZSDS0039[] 	list 	= (ZSDS0039[])CallSAPHdel.moveDs2Obj2(ds_list_update_plan, ZSDS0039.class, "");    
			// �����޼���
			ZQMSEMSG[]	listMsg = new ZQMSEMSG[]{};		  
			
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{  
										  DatasetUtility.getString(ds_update_yearm, "YEARM")  	// ���س��
										, listMsg 												// O_ERROR INFO
										, list													// ������ : ZSDS0039 : �̼�(��������:1)/����(��������:2) 
										};   
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
												, "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_PLAN"
												, obj, false);  
			 
			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);  
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0020UpdatePlanView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0020UpdatePlanView SUCCESS!!!" + "");
			}  

		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0020UpdatePlanView CommRfcException ERROR!!!" + "");
			e.printStackTrace();     
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0020UpdatePlanView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	   
		  
		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO(); 
		ds_error.setId		("ds_error");  
		resultVO.addDataset	(ds_error);  	// ��������
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0020UpdatePlanView addAttribute end" + "");
		
		return view;
		
	}
	

	/*-----------------------------------------------------
	 *  ���س���� �ش��ϴ� ä�ǻ��� ����
	 ------------------------------------------------------*/
	@RequestMapping(value="update_reason")
	public View Ssa0020UpdateReasonView(MipParameterVO paramVO, Model model)  {

		logger.debug("@@@ Ssa0020UpdateReasonView START" + ""); 
		
		// INPUT DATASET GET
		Dataset ds_list_update_reason 	= paramVO.getDataset("ds_list_update_reason");  	// ä�ǻ�����������Ʈ
		
		// OUTPUT DATASET DECLARE 
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 					===>"+paramVO.getVariable("G_MANDT") 		+"");  
		logger.debug("@@@ ds_list_update_reason.getRowCount	===>"+ds_list_update_reason.getRowCount() 	+ "");
		for( int irow = 0; irow < ds_list_update_reason.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_list_update_reason.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_list_update_plan["+irow+"Record : "
								+ ds_list_update_reason.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_list_update_reason, irow, ds_list_update_reason.getColumnID(icol)));
			}  
		}
		//--------------------------------------------------------------------------------------------
 
		try { 

			// RFC INPUT PARAM DECLARE   
			// input dataset�� CLASS(ZSDS0039) �� �ű��    
			// �̼�(��������:1)/�ε�(��������:2) ä�ǻ��� RFC input list
			ZSDT0053[] 	list 	= (ZSDT0053[])CallSAPHdel.moveDs2Obj2(ds_list_update_reason, ZSDT0053.class, "");  
			// �����޼���
			ZQMSEMSG[]	listMsg = new ZQMSEMSG[]{};		  
			
			// RFC INPUT SET (�Ķ���� ��������)
			Object obj[] = new Object[]{   
										  listMsg 		// O_ERROR INFO
										, list			// ������ : ZSDT0053 : �̼�(��������:1)/����(��������:2) 
										};   
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_152_RES", obj, false);	
			logger.debug("@@@ Ssa0020UpdateReasonView RFC CALL SUCCESS " + ""); 

			// RFC ȣ���� ��������
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg); 
			if (ds_error.getRowCount() > 0)
			{
				logger.debug("@@@ Ssa0020UpdateReasonView FAILED!!!" + "");
				logger.info("@@@ ERROR : " + ds_error.getColumnAsString(0, "ERRMSG"));
			}
			else
			{
				logger.debug("@@@ Ssa0020UpdateReasonView SUCCESS!!!" + "");
			}  
			 
		} catch (CommRfcException e) { 
			logger.error("@@@ Ssa0020UpdateReasonView CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (Exception e) { 
			logger.error("@@@ Ssa0020UpdateReasonView Exception ERROR!!!" + "");
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	
		  
		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO();  
		ds_error.setId		("ds_error"); 
		resultVO.addDataset	(ds_error);  	// �������� 
		model.addAttribute	("resultVO", resultVO);   

		logger.debug("@@@ Ssa0020UpdateReasonView addAttribute end" + "");
		
		return view;
		
	} 
}
