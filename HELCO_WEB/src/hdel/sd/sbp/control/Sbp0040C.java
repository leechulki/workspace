package hdel.sd.sbp.control;


import hdel.lib.control.SrmView; 
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.lib.exception.BizException;

import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.sbp.service.Sbp0040S;   
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0040ParamVO;    
import hdel.sd.sbp.domain.ZSDS0071;
import hdel.sd.sbp.domain.ZSDS0072;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoSoNumberS;  

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;  

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.commons.lang.StringUtils; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.biz.BusinessException;
import tit.util.DatasetUtility;
      
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;  
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;


import org.apache.log4j.Logger; 


/**
 * �����ȹ����(����)(Sbp0040C) Control
 * @Comment
 *     	1. View   find_gtype   	( ���� ��� ��ȸ )
 *     	2. View   find_opendtm 	( ���⵵�� �ش��ϴ� ���µ� �����ȹ������ ����Ͻ� ��ȸ )
 *     	3. View   find 			( ��ȹ����� �ش��ϴ� �����ȹ���� ��� ��ȸ )
 *     	4. View   find_detail 	( �����ȹ��ȣ�� �ش��ϴ� ����/û��/���� ��� ��ȸ )
 *     	5. View   save 			( ��ȹ����� �ش��ϴ� �����ȹ ���/����/���� �� ����/û��/���� ������ ��� �� �����ȹ��ȣ�� ȭ������ ����  )
 *     	6. String getNewZbpnnr 	( �����ȹ��ȣ ä��  )
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("sbp0040")
public class Sbp0040C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0040S service;   

	@Autowired
	private AutoSoNumberS 	Autoservice;	// �����ȹ��ȣä�� ���� 

	/*-----------------------------------------------------
	 *  ������ü�ڵ�� ���¾�ü�� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_lifnr")
	public View Sbp0040FindLifnrView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0040FindLifnrView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_lifnr = paramVO.getDataset("ds_lifnr");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT"));
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG")); 
		logger.debug("@@@ ds_lifnr.ZAGNT	===>"+DatasetUtility.getString(ds_lifnr,"ZAGNT")); 
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0040ParamVO param = new Sbp0040ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				   	// SAP CLIENT 
			param.setZAGNT	(DatasetUtility.getString(ds_lifnr,"ZAGNT"	));		// �븮���ڵ� 
			
			// ����CALL (���¾�ü����ȸ)
			List<Sbp0040> list = service.selectListLifnr(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����  
			ds_lifnr.deleteAll();
			CallSAPHdel.moveListToDs(ds_lifnr, Sbp0040.class, list); 
			
			logger.debug("@@@ ds_lifnr.lifnr : " + ds_lifnr.getColumnAsString(0, "ZAGNT"));
			logger.debug("@@@ ds_lifnr.lifnr_nm : " + ds_lifnr.getColumnAsString(0, "ZAGNT_NM"));
			
			logger.debug("@@@ Sbp0040FindLifnrView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindLifnrView Exception ERROR!!!");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		
		// ��� dataset�� return
		logger.debug("@@@ ds_lifnr.getRowCount ===>" + ds_lifnr.getRowCount());
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_lifnr.setId		("ds_lifnr");  
		resultVO.addDataset	(ds_error);  		// ��������
		resultVO.addDataset	(ds_lifnr); 
		model.addAttribute("resultVO", resultVO);     

		logger.debug("@@@ Sbp0040FindLifnrView addAttribute end" + "");
		
		return view;  
	} 
	
	/*-----------------------------------------------------
	 *  ������� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_gtype")
	public View Sbp0040FindGTypeView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0040FindGTypeView START!!!");
		
		// INPUT DATASET GET 
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_gtype 	= paramVO.getDataset("ds_list_gtype");			// UI�� return�� out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT"));
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG"));
		//--------------------------------------------------------------------------------------------
		 
		try {  
			
			// ������� ��ȸ
			List<ZGType> list = ZGTypes.find();   
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����   
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				ds_list_gtype.appendRow();  
	    		// �÷�SET 
				ds_list_gtype.setColumn(iRow, "gtype"	, list.get(iRow).getZgtype	());	// ����
				ds_list_gtype.setColumn(iRow, "auart"	, list.get(iRow).getAuart	());	// �ǸŹ�������(��������)
				ds_list_gtype.setColumn(iRow, "matnr"	, list.get(iRow).getMatnr	());	// �����ȣ
				ds_list_gtype.setColumn(iRow, "spart"	, list.get(iRow).getSpart	());	// ��ǰ�� 
			}   
			
			logger.debug("@@@ Sbp0040FindGTypeView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindGTypeView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		 
		// ��� dataset�� return
		logger.debug("@@@ ds_list_gtype.getRowCount ===>" + ds_list_gtype.getRowCount());
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");  
		ds_list_gtype.setId	("ds_list_gtype");  
		resultVO.addDataset	(ds_error);  		// ��������
		resultVO.addDataset	(ds_list_gtype); 	 
		model.addAttribute	("resultVO", resultVO);     

		logger.debug("@@@ Sbp0040FindGTypeView addAttribute end" + "");
 
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  �����ȹ�⵵�� �ش��ϴ� �����Ͻ� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_opendtm")
	public View Sbp0040FindOpenDtmView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0040FindOpenDtmView START!!!" + "\n");
		
		// INPUT DATASET GET
		Dataset ds_cond_opendtm = paramVO.getDataset("ds_cond_opendtm");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_opendtm = paramVO.getDataset("ds_list_opendtm");		// UI�� return�� out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 			===>"+paramVO.getVariable("G_MANDT"));
		logger.debug("@@@ paramVO.G_LANG			===>"+paramVO.getVariable("G_LANG") );
		logger.debug("@@@ ds_cond_opendtm.ZPYEAR	===>"+DatasetUtility.getString(ds_cond_opendtm,"ZPYEAR")); 
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0040ParamVO param = new Sbp0040ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			//param.setLang	(paramVO.getVariable("G_LANG"));	 			// ���
			param.setZPYEAR	(DatasetUtility.getString(ds_cond_opendtm,"ZPYEAR"	));	// ���⵵ 
			
			// ����CALL (�����ȹ�⵵�� �ش��ϴ� �������� ��ȸ)
			List<Sbp0040> list = service.selectOpenDtm(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����  
			CallSAPHdel.moveListToDs(ds_list_opendtm, Sbp0040.class, list);  

			logger.debug("@@@ Sbp0040FindOpenDtmView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindOpenDtmView Exception ERROR!!!");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		// ��� dataset�� return
		logger.debug("@@@ ds_list_opendtm.getRowCount ===>" + ds_list_opendtm.getRowCount());
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId			("ds_error");   
		ds_list_opendtm.setId	("ds_list_opendtm");  
		resultVO.addDataset		(ds_list_opendtm); 
		resultVO.addDataset		(ds_error);  		// ��������
		model.addAttribute		("resultVO", resultVO);   

		logger.debug("@@@ Sbp0040FindOpenDtmView addAttribute end" + "");
		
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  �����ȹ���ָ�� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find")
	public View Sbp0040FindView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ Sbp0040FindView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 	= paramVO.getDataset("ds_list");				// UI�� return�� out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT") 				);
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG") 				); 
		logger.debug("@@@ ds_cond.ZPYEAR	===>"+DatasetUtility.getString(ds_cond,"ZPYEAR")	);
		logger.debug("@@@ ds_cond.ZPYM		===>"+DatasetUtility.getString(ds_cond,"ZPYM")		);
		logger.debug("@@@ ds_cond.SPART		===>"+DatasetUtility.getString(ds_cond,"SPART")		);
		logger.debug("@@@ ds_cond.VKBUR_FR	===>"+DatasetUtility.getString(ds_cond,"VKBUR_FR")	);   
		logger.debug("@@@ ds_cond.VKBUR_TO	===>"+DatasetUtility.getString(ds_cond,"VKBUR_TO")	);
		logger.debug("@@@ ds_cond.VKGRP_FR	===>"+DatasetUtility.getString(ds_cond,"VKGRP_FR")	);   
		logger.debug("@@@ ds_cond.VKGRP_TO	===>"+DatasetUtility.getString(ds_cond,"VKGRP_TO")	);
		logger.debug("@@@ ds_cond.ZKUNNR	===>"+DatasetUtility.getString(ds_cond,"ZKUNNR")	);
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0040ParamVO param = new Sbp0040ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setLANG		(paramVO.getVariable("G_LANG"));	 			// ���
			param.setZPYEAR		(DatasetUtility.getString(ds_cond,"ZPYEAR"));	// ���⵵
			param.setZPYM		(DatasetUtility.getString(ds_cond,"ZPYM"));		// ��ȹ���
			param.setSPART		(DatasetUtility.getString(ds_cond,"SPART"));	// ��ǰ���ڵ�
			param.setVKBUR_FR	(DatasetUtility.getString(ds_cond,"VKBUR_FR"));	// �μ��ڵ�_FR
			param.setVKBUR_TO	(DatasetUtility.getString(ds_cond,"VKBUR_TO"));	// �μ��ڵ�_TO
			param.setVKGRP_FR	(DatasetUtility.getString(ds_cond,"VKGRP_FR"));	// ���ڵ�_FR
			param.setVKGRP_TO	(DatasetUtility.getString(ds_cond,"VKGRP_TO"));	// ���ڵ�_TO
			param.setZKUNNR		(DatasetUtility.getString(ds_cond,"ZKUNNR"));	// ������ڵ� 
				 
			// ����CALL (�����ȹ��� ��ȸ)
			List<Sbp0040> list = service.selectList(param);  
			
			logger.debug("@@@ list.size() ===>" + list.size());
		
			// ȣ����(list)�� ����Ÿ��(dsList)�� ���� 
			CallSAPHdel.moveListToDs(ds_list, Sbp0040.class, list);  

			logger.debug("@@@ Sbp0040FindView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindView Exception ERROR!!!" + "\n");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount()); 
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_list.setId		("ds_list");  
		resultVO.addDataset	(ds_error);  		// ��������
		resultVO.addDataset (ds_list);  
		model.addAttribute	("resultVO", resultVO);     

		logger.debug("@@@ Sbp0040FindView addAttribute end" + "");
		
		return view;
	}
	

	/*-----------------------------------------------------
	 *  �����ȹ��ȣ�� �ش��ϴ� ����/û��/���� ��ȸ
	 ------------------------------------------------------*/ 
	@RequestMapping(value="find_detail")
	public View Sbp0040FindDetailView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Sbp0040FindDetailView START!!!" + "\n");
		
		// INPUT DATASET GET
		Dataset ds_cond_detail 	= paramVO.getDataset("ds_cond_detail");
		Dataset ds_yearm 		= paramVO.getDataset("ds_yearm");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_bill 	= paramVO.getDataset("ds_list_bill");			// UI�� return�� out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 		===>"+paramVO.getVariable("G_MANDT") 					);
		logger.debug("@@@ paramVO.G_LANG		===>"+paramVO.getVariable("G_LANG") 					); 
		logger.debug("@@@ ds_cond_detail.ZBPNNR	===>"+DatasetUtility.getString(ds_cond_detail,"ZBPNNR")	);
		logger.debug("@@@ ds_yearm.getRowCount 	===>"+ds_yearm.getRowCount() 						);
		for( int irow = 0; irow < ds_yearm.getRowCount(); irow++ ) 
		{ 
			for( int icol = 0; icol < ds_yearm.getColumnCount(); icol++) 
			{
				logger.debug("@@@ ds_yearm["+irow+"Record : "+ds_yearm.getColumnID(icol)+"] ===>"  
								+ DatasetUtility.getString(ds_yearm, irow, ds_yearm.getColumnID(icol)));
			}  
		}
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0040ParamVO param = new Sbp0040ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �����ȹ��ȣ  
			String zbpnnr = DatasetUtility.getString(ds_cond_detail,"ZBPNNR");
			
			// �Ķ���� SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT 
			param.setZBPNNR		(zbpnnr);									// �����ȹ��ȣ
			param.setM00		(ds_yearm.getColumnAsString( 0, "YEARM"));	// ��ȹ��� + 00��
			param.setM01		(ds_yearm.getColumnAsString( 1, "YEARM"));	// ��ȹ��� + 01��
			param.setM02		(ds_yearm.getColumnAsString( 2, "YEARM"));	// ��ȹ��� + 02��
			param.setM03		(ds_yearm.getColumnAsString( 3, "YEARM"));	// ��ȹ��� + 03��
			param.setM04		(ds_yearm.getColumnAsString( 4, "YEARM"));	// ��ȹ��� + 04��
			param.setM05		(ds_yearm.getColumnAsString( 5, "YEARM"));	// ��ȹ��� + 05��
			param.setM06		(ds_yearm.getColumnAsString( 6, "YEARM"));	// ��ȹ��� + 06��
			param.setM07		(ds_yearm.getColumnAsString( 7, "YEARM"));	// ��ȹ��� + 07��
			param.setM08		(ds_yearm.getColumnAsString( 8, "YEARM"));	// ��ȹ��� + 08��
			param.setM09		(ds_yearm.getColumnAsString( 9, "YEARM"));	// ��ȹ��� + 09��
			param.setM10		(ds_yearm.getColumnAsString(10, "YEARM"));	// ��ȹ��� + 10��
			param.setM11		(ds_yearm.getColumnAsString(11, "YEARM"));	// ��ȹ��� + 11��
			param.setM12		(ds_yearm.getColumnAsString(12, "YEARM"));	// ��ȹ��� + 12��
			param.setM13		(ds_yearm.getColumnAsString(13, "YEARM"));	// ��ȹ��� + 13��
			param.setM14		(ds_yearm.getColumnAsString(14, "YEARM"));	// ��ȹ��� + 14��
			param.setM15		(ds_yearm.getColumnAsString(15, "YEARM"));	// ��ȹ��� + 15��
			param.setM16		(ds_yearm.getColumnAsString(16, "YEARM"));	// ��ȹ��� + 16��
			param.setM17		(ds_yearm.getColumnAsString(17, "YEARM"));	// ��ȹ��� + 17��
			param.setM18		(ds_yearm.getColumnAsString(18, "YEARM"));	// ��ȹ��� + 18��
			param.setM19		(ds_yearm.getColumnAsString(19, "YEARM"));	// ��ȹ��� + 19�� 
			param.setM20		(ds_yearm.getColumnAsString(20, "YEARM"));	// ��ȹ��� + 20��
			param.setM21		(ds_yearm.getColumnAsString(21, "YEARM"));	// ��ȹ��� + 21��
			param.setM22		(ds_yearm.getColumnAsString(22, "YEARM"));	// ��ȹ��� + 22��
			param.setM23		(ds_yearm.getColumnAsString(23, "YEARM"));	// ��ȹ��� + 23��
			param.setM24		(ds_yearm.getColumnAsString(24, "YEARM"));	// ��ȹ��� + 24��
			param.setM25		(ds_yearm.getColumnAsString(25, "YEARM"));	// ��ȹ��� + 25��
			param.setM26		(ds_yearm.getColumnAsString(26, "YEARM"));	// ��ȹ��� + 26��
			param.setM27		(ds_yearm.getColumnAsString(27, "YEARM"));	// ��ȹ��� + 27��
			param.setM28		(ds_yearm.getColumnAsString(28, "YEARM"));	// ��ȹ��� + 28��
			param.setM29		(ds_yearm.getColumnAsString(29, "YEARM"));	// ��ȹ��� + 29�� 

			// ����CALL (�����ȹ ����/û��/���� ��ȸ)
			List<Sbp0040> list = service.selectListDetail(param);  
			
			logger.debug("@@@ list.toString ===>" + list.toString());
		
			// ȣ����(list)�� ����Ÿ��(dsList)�� ���� 
			CallSAPHdel.moveListToDs(ds_list_bill, Sbp0040.class, list); 
			 
			logger.debug("@@@ Sbp0040FindDetailView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0040FindDetailView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list_bill.getRowCount ===>" + ds_list_bill.getRowCount()); 
		MipResultVO resultVO = new MipResultVO();
		ds_list_bill.setId("ds_list_bill");  
		ds_error.setId		("ds_error");   
		resultVO.addDataset (ds_list_bill);  
		resultVO.addDataset	(ds_error);  		// ��������
		model.addAttribute	("resultVO", resultVO);    

		logger.debug("@@@ Sbp0040FindDetailView addAttribute end" + "");
		
		return view;
	}
 
	/*------------------------------------------------------------
	 *  ��ȹ����� �ش��ϴ� �����ȹ ���/����/���� (����/û��/���� �����ĵ��)
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Sbp0040SaveView(MipParameterVO paramVO, Model model) { 
  
		logger.debug("@@@ Sbp0040SaveView START!!!" + "\n");
		
		// INPUT DATASET GET  
		Dataset ds_detail 		= paramVO.getDataset("ds_detail");  		// ���� ���,����,���� �������
		Dataset ds_compute_cond	= paramVO.getDataset("ds_compute_cond");  	// ����/û��/���� �ڵ������ �����׸�
		Dataset ds_list_bill 	= paramVO.getDataset("ds_list_bill"); 	// ����/û��/���� ���� �������(����ݾ�)
		Dataset ds_yearm 		= paramVO.getDataset("ds_yearm"); 			// ����/û��/���� ���� �������(18����ġ ������)
				
		// INPUT ITEM 
		String 	mandt 			= paramVO.getVariable("G_MANDT");						// SAP CLIENT 
		String 	user_id 		= paramVO.getVariable("G_USER_ID");						// WEB USER ID 
		String 	flag 			= DatasetUtility.getString(ds_detail, 0, "FLAG");		// ���屸��(I,U,D) 
		String 	zbpnnr 			= DatasetUtility.getString(ds_detail, 0, "ZBPNNR");  	// �����ȹ��ȣ(���� ���� �� ȭ������ ����)
		String 	auart			= DatasetUtility.getString(ds_detail, 0, "AUART");		// �ǸŹ�������(��������)
		String 	matnr			= DatasetUtility.getString(ds_detail, 0, "MATNR");		// �����ȣ
		String 	posid 			= "";													// ȣ���ȣ
		String  pspidsv			= DatasetUtility.getString(ds_detail, 0, "PSPIDSV");  	// ����������Ʈ��ȣ
		 	
		// �������� DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// �������  DATASET GET
		Dataset ds_result 	= paramVO.getDataset("ds_result");   

		String call_sap_yn 		= "";	// ����/û��/���� ���� SAP�Լ� ȣ�⿩�� (SAP��)
		String auto_compute_yn  = "";	// ����/û��/���� �ڵ����� �ݿ����� 	 (WEB��)
		
		// �����ȹ�� �����Ѵ�.
		try { 
		
			// 1.Session GET
			logger.debug("@@@ 1.Session GET"); 
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); 
			
			// 2.�ű� �����ȹ�� ��� �����ȹ��ȣ GET (��ȹ���к�(�����ȹ:0), �ǸŹ��������� ä��)   
			logger.debug("@@@ 2.�ű� �����ȹ�� ��� �����ȹ��ȣ GET (��ȹ���к�(�����ȹ:0), �ǸŹ��������� ä��)");  
			if ("I".equals(flag))
			{
				logger.debug("@@@ 2. �����ȹ �űԻ�����û�� ��� ");
				 
				logger.debug("@@@ 2.1 �űԻ����ȹ��ȣ ä��"); 
				zbpnnr = getNewZbpnnr(paramVO.getVariable("G_SYSID"), mandt, "1", auart); 
				logger.debug("@@@ 2.1 �űԻ����ȹ��ȣ ä�� ��� : " + zbpnnr);  

				logger.debug("@@@ 2.2  ����/û��/���� ���� SAP�Լ� ȣ�⿩�� SET"); 
				call_sap_yn = "Y"; 
				logger.debug("@@@ 2.2 call_sap_yn : " + call_sap_yn); 
				 
				// ����/û��/���� �ڵ����� ��󿩺�
				auto_compute_yn = "Y"; 
				logger.debug("@@@ 2.3 auto_compute_yn : " + auto_compute_yn);
				
				logger.debug("@@@ 2.4 ȣ���ȣ �缳��"); 
				posid 			= f_posid_make(zbpnnr, matnr);	
				logger.debug("@@@ 2.4 posid : " + posid);    
				
			}
			
			// 2. �����ȹ �����û�� ���
			else if ("U".equals(flag))
			{  
				logger.debug("@@@ 2. �����ȹ �����û�� ���"); 

				logger.debug("@@@ 2.1  ����/û��/���� ���� SAP�Լ� ȣ�⿩�� SET");  
				call_sap_yn = chkAutoComputetSapYN(session ,ds_detail ,mandt, zbpnnr); 
				logger.debug("@@@ 2.1 call_sap_yn : " + call_sap_yn);
				
				// ����/û��/���� �ڵ����� ��󿩺�
				auto_compute_yn =  chkAutoComputetWebYN(  session
						                                 , mandt 		// CLIENT  
														 , ds_detail	// �����ȹ����
														 ); 
				logger.debug("@@@ 2.2 auto_compute_yn : " + auto_compute_yn);
				
				logger.debug("@@@ 2.3 ȣ���ȣ �缳��"); 
				posid 			= f_posid_make(zbpnnr, matnr);	
				logger.debug("@@@ 2.3 posid : " + posid);  
 
				
			}	// end of else if ("U".equals(flag))
			

			// 2. �����ȹ ������û�� ���    
			else if ("D".equals(flag))
			{ 
				logger.debug("@@@ 2. �����ȹ ������û�� ���    "); 

				logger.debug("@@@ 2.1  ����/û��/���� ���� SAP�Լ� ȣ�⿩�� SET");  
				call_sap_yn = "";   // ������ ��� ����/û��/���� ���̺��� �������� �ʴ´�.
				logger.debug("@@@ 2.1 call_sap_yn : " + call_sap_yn); 
				
				// ����/û��/���� �ڵ����� ��󿩺�
				auto_compute_yn = ""; 
				logger.debug("@@@ 2.2 auto_compute_yn : " + auto_compute_yn);
				
			}	// end of else if ("D".equals(flag))  

			// 3.���� ȣ��
			logger.debug("@@@ 3.���� ȣ��");     
			service.save( 	  ds_detail  		// �����ȹ��
							, ds_list_bill  	// ����/û��/���� ���� �������(����ݾ�)
							, ds_yearm  		// ����/û��/���� ���� �������(30����ġ ������)
							, flag				// ���屸��(I,U,D)
							, paramVO.getVariable("G_SYSID")
							, mandt				// CLIENT
							, user_id			// �����ID
							, zbpnnr			// �����ȹ��ȣ 
							, pspidsv			// ����������Ʈ��ȣ
							, call_sap_yn		// ����/û��/���� �ڵ����� ��󿩺�(SAP��)
							, auto_compute_yn	// ����/û��/���� �ڵ����� ��󿩺�(WEB��)
							, posid				// ȣ��
							) ;
			
			// 4. ����/û��/���� ���� SAP�Լ� ȣ�⿩��=='Y'�� ��� ����/û��/���� �ڵ�����  RFC FUCNTION ȣ��
			if ("Y".equals(call_sap_yn))
			{ 
				logger.debug("@@@ 4. ����/û��/���� ���� SAP�Լ� ȣ�⿩��=='Y'�� ��� ����/û��/���� �ڵ�����  RFC FUCNTION ȣ�� ");
				
				 
				// 4.1 ���� Input Dataset(ds_detail) --> class(Sbp0010)�� copy
				logger.debug("@@@ 4.1 ���� Input Dataset(ds_detail) --> class(Sbp0010)�� copy "); 
				Sbp0040[] param = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);    
				 
				// 4.2  �Ϻ� �׸� �缳��
				logger.debug("@@@ 4.2  �Ϻ� �׸� �缳�� "); 
				ds_compute_cond.setColumn(0, "SONNR", zbpnnr);  // �����ȹ��ȣ
				ds_compute_cond.setColumn(0, "POSID", posid); 	// ȣ���ȣ
				
				// 4.3 RFC FUCNTION ȣ��
				logger.debug("@@@ 4.3 RFC FUCNTION ȣ�� ");
				ZQMSEMSG[] 	list_msg		= new ZQMSEMSG[]{};	 	// �����޽���  
				ZSDS0072[] 	list_out		= new ZSDS0072[]{};		// ����/û��/���� �ڵ����� ���
				ZSDS0071[]	list_in 		= (ZSDS0071[])CallSAPHdel.moveDs2Obj2(ds_compute_cond, ZSDS0071.class, ""); 
				Object 		obj[] 			= new Object[]{"Y", list_msg , list_in , list_out};   
				SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), mandt , "hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);
				Dataset   	ds_compute_out 	= CallSAP.isJCO() ? new Dataset() : ZSDS0072.getDataset(); 
				CallSAP.moveObj2Ds(ds_compute_out, stub.getOutput("T_ITAB2")); 
				list_msg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				
				// �������� ����� ���� ���̺� SETTING
				if (list_msg.length > 0)
				{  
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
																, list_msg[0].getIDX().toString()
																, list_msg[0].getERRMSG().toString()
																, "Y"
																, "Y"); 
				} 
				
				// 5.2 ȣ�� ��� �������� ���� ��
				if ( ds_error.getRowCount() > 0 )
				{  
					logger.error("@@@ 5.2 ȣ�� ��� �������� ���� ��");
					logger.error("@@@ 5.2 ZWEB_SD_PLAN_COMPUTE FAILED!!!" + "");
					logger.error("@@@ 5.2 ERRCODE : " + ds_error.getColumnAsString(0, "ERRCODE"));  
					logger.error("@@@ 5.2 ERRMSG : " + ds_error.getColumnAsString(0, "ERRMSG")); 
					
					String save_stat    = ds_error.getColumnAsString(0, "ERRCODE"); 
					if ("1".equals(save_stat) 	|| "2".equals(save_stat) 
												|| "3".equals(save_stat))	
					{  
						// �����ȹ ������� ���� (SAVE_STAT : E/F/G)
						logger.debug("@@@ 5.2 �����ȹ ������� ����  "); 
						service.updateSaveStat(mandt, zbpnnr, user_id, save_stat);   
					}   
				} 	// end of else if ( ds_error.getRowCount() > 0 )
				
				// 5.2 �ڵ������� ������ �� ���� ó��  
				else if ( ds_compute_out.getRowCount() == 0 )
				{
					logger.debug("@@@ 5.2 �ڵ������� ������ �� ���� ó��   ");
					// "CE10003", "�����ȹ ���� �� ����/û��/���� �ڵ����� ����� �������� �ʽ��ϴ�. Ȯ�� �ٶ��ϴ�.");   
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE10003", "", "Y", "Y");
				}	// end of if ( ds_compute_out.getRowCount() == 0 ) 
				
				// 5.3 ȣ�� ��� ���� �� ���
				else
				{
					 
					// 5.4 �����ȹ������º��� (SAVE_STAT=Z)
					logger.debug("@@@ 5.3 �����ȹ������º��� (SAVE_STAT=Z)");   
					service.bill_save_auto_sap(   ds_compute_out	// ����/û��/���� �ڵ������� 
												, mandt				// CLIENT
												, user_id 			// WEB USER_ID
												, zbpnnr 			// �����ȹ��ȣ
												);   
					 
					
				} 
				 
			}
			
			// 4.�����ȹ��ȣ ��ȯ
			logger.debug("@@@ 4.�����ȹ��ȣ ��ȯ"); 
			ds_result.appendRow();
			ds_result.setColumn(0, "ZBPNNR", zbpnnr);
			
			logger.debug("@@@ Sbp0040SaveView SUCCESS!!!");
			 
		} catch (BizException e) {  
		 	// ����������Ǿ� �����ڵ尡 ���ϵ� ��� (e.getMessage()�� �����ڵ常 ����� ��)
			logger.info("@@@ Sbp0040SaveView BizException ERROR!!! : " + e.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
		} catch (Exception e) { 
			// ����ġ���� ������������ ���		 (e.getMessage()�� �����޼��� ��ü�� ����� ��)
			e.printStackTrace();
			logger.error("@@@ Sbp0040SaveView Exception ERROR!!! : " + e.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO(); 
		logger.debug("@@@ ds_result.getRowCount ===>"+ds_result.getRowCount());
		logger.debug("@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount());
		ds_result.setId		("ds_result");   
		ds_error.setId		("ds_error");   
		resultVO.addDataset	(ds_result);  	// ���INFO(�����ȹ��ȣ) 
		resultVO.addDataset	(ds_error);  	// ����INFO 
		model.addAttribute(	"resultVO", resultVO);   

		logger.debug("@@@ Sbp0040SaveView addAttribute end" + "");

		return view;

	}
	 
	// �����ȹ��ȣ ä��
	public String getNewZbpnnr(String sysid, String mandt 
								, String gbn		// ä������(1:�����ȹ, 1:���ְ�ȹ , 2:����)
								, String auart		// �ǸŹ������� (��������)
								)
	{   

		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr START!!!");
		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr.mandt	===> " 	+ mandt);
		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr.gbn		===> " 	+ gbn);
		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr.auart	===> " 	+ auart);
		 
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(sysid); 
		
		AutoNumberParamVO param = new AutoNumberParamVO();
		
		// DAO���� 
		Autoservice.createDao(session); 

		// Parameter SET
		param.setMandt		(mandt);  	// SAP CLIENT
		param.setDeptFlag	(auart);	// ����
		param.setSoQtFlag	(gbn);		// ä������(0:�����ȹ, 1:���ְ�ȹ , 2:����)

		// �����ȹ��ȣ ä�� ���� ȣ��
		List<AutoNumberVO> listVO = Autoservice.AutoSoNumber(param);   

		logger.debug("@@@ Sbp0040SaveView.getNewZbpnnr SUCCESS !!!");
		return listVO.get(0).getNumber().toString();  // ä���� �����ȹ��ȣ RETURN
		
	}
	
	// �����ȹ ���� ��û�� ��� (flag=="U") ����/û��/���� ���� SAP�Լ� ȣ�⿩�� ��󿩺� üũ 
	// Ư���׸��� ���� �� ����  ��ȸ �� ����Ǿ��� ��� �ڵ����⿩�� = Y�� SET
	// Ư���׸� : ��������, ��ǰ��, ����, �����ȣ, ���ֿ����
	public String chkAutoComputetSapYN(  SqlSession session
				                    , Dataset 	 ds_detail
				                    , String 	 mandt 		// CLIENT
									, String 	 zbpnnr		// �����ȹ��ȣ  
									)
	{    
		// DAO����
		service.createDao(session);

		logger.debug("@@@ Sbp0040C.chkAutoComputetYN START!!!" 	+ "");
		logger.debug("@@@ Sbp0040C.chkAutoComputetYN.mandt : " 	+ mandt + "");
		logger.debug("@@@ Sbp0040C.chkAutoComputetYN.zbpnnr : " + zbpnnr + ""); 

		String call_sap_yn = "N";	// ����/û��/���� ���� SAP�Լ� ȣ�⿩��
		
		// ���� DB�� ����Ǿ� �ִ� �� ��ȸ
		Sbp0040ParamVO 	param_item 		= new Sbp0040ParamVO(); 
		
		param_item.setMANDT(mandt);		// CLIENT
		param_item.setZBPNNR(zbpnnr);  	// �����ȹ��ȣ
		
		List<Sbp0040> list_item = service.selectListComputeItem(param_item);
		 
		Double list_sofoca  = list_item.get(0).getSOFOCA().doubleValue();	// T/B_���ֿ���ݾ�
		
		String auart 		= ds_detail.getColumnAsString(0, "AUART");	// IN_��������
		String spart 		= ds_detail.getColumnAsString(0, "SPART");	// IN_��ǰ��
		String gtype 		= ds_detail.getColumnAsString(0, "GTYPE");	// IN_����
		String mantr 		= ds_detail.getColumnAsString(0, "MATNR");	// IN_�����ڵ� 
		Double detail_sofoca = ds_detail.getColumnAsDouble(0,"SOFOCA"); // IN_���ֿ���ݾ�
		if (StringUtils.isBlank(auart)) auart = "";
		if (StringUtils.isBlank(spart)) spart = "";
		if (StringUtils.isBlank(gtype)) gtype = "";
		if (StringUtils.isBlank(mantr)) mantr = ""; 
		
		logger.debug("@@@ list.AUART() :[" + list_item.get(0).getAUART() + "");
		logger.debug("@@@ list.SPART() :[" + list_item.get(0).getSPART() + "");
		logger.debug("@@@ list.GTYPE() :[" + list_item.get(0).getGTYPE() + "");
		logger.debug("@@@ list.MATNR() :[" + list_item.get(0).getMATNR() + ""); 
		logger.debug("@@@ list.getSOFOCA() :[" + list_item.get(0).getSOFOCA().doubleValue()+ "");
		logger.debug("@@@ ds_detail.AUART() :[" + auart + "");
		logger.debug("@@@ ds_detail.SPART() :[" + spart + "");
		logger.debug("@@@ ds_detail.GTYPE() :[" + gtype + "");
		logger.debug("@@@ ds_detail.MATNR() :[" + mantr + "");  	
		logger.debug("@@@ ds_detail.SOFOCA() :[" + detail_sofoca+ ""); 
				
				
		if ( 
			   !auart.equals(list_item.get(0).getAUART().toString()) 	// ��������(�ǸŹ�������)
			|| !spart.equals(list_item.get(0).getSPART().toString())  	// ��ǰ��
			|| !gtype.equals(list_item.get(0).getGTYPE().toString())  	// ����
			|| !mantr.equals(list_item.get(0).getMATNR().toString()) 	// �����ȣ  
			|| (list_sofoca.compareTo(detail_sofoca) != 0)				// ���ֿ���� 
			)
		{
			// ���� ���� �İ� ������ ��� �ٽ� ����/û��/���� ���� SAP�Լ� ȣ��  �ؾ� �� 
			call_sap_yn	= "Y";		
		} 
		  
		
		return call_sap_yn;
	}
	
	// �����ȹ ���� ��û�� ��� (flag=="U") ���� �����ȹ ���� ��ȸ�Ͽ�
	// ��������, ���ֿ���ݾ��� ����� ��� ����/û��/���� �ڵ����� �� ����
	public String chkAutoComputetWebYN(  SqlSession session
										, String mandt 		// CLIENT  
										, Dataset ds_detail
										)
	{     
   
		// DAO����
		service.createDao(session);
		
		String  zbpnnr 	= ds_detail.getColumnAsString(0, "ZBPNNR");		// �����ȹ��ȣ 
		Double  sofoca  = ds_detail.getColumnAsDouble(0, "SOFOCA");		// ���ֿ����   
		String 	auart 	= ds_detail.getColumnAsString(0, "AUART");		// IN_��������
		
		logger.debug("@@@ Sbp0040C.selectListComputeItem START!!!" 	+ "");
		logger.debug("@@@ Sbp0040C.selectListComputeItem.mandt : " 	+ mandt + "");
		logger.debug("@@@ Sbp0040C.selectListComputeItem.zbpnnr : " + zbpnnr + "");
		logger.debug("@@@ Sbp0040C.selectListComputeItem.sofoca : " + sofoca + "");
		logger.debug("@@@ Sbp0040C.selectListComputeItem.auart : " + auart + "");  
		
		// ���� DB�� ����Ǿ� �ִ� �� ��ȸ
		Sbp0040ParamVO 	param_item 		= new Sbp0040ParamVO(); 
		
		param_item.setMANDT(mandt);		// CLIENT
		param_item.setZBPNNR(zbpnnr);  	// �����ȹ��ȣ
		
		List<Sbp0040> list_item = service.selectListComputeItem(param_item);
		 
		Double old_sofoca   = list_item.get(0).getSOFOCA().doubleValue();	// T/B_���ֿ���ݾ�    
		 
		logger.debug("@@@ old_sofoca :[" + list_item.get(0).getSOFOCA().doubleValue()+ ""); 
		logger.debug("@@@ new_sofoca :[" + sofoca + "");  
				
		if (
			 !auart.equals(list_item.get(0).getAUART().toString()) 	// ��������(�ǸŹ�������)�� ������ ���
			 || old_sofoca.compareTo(sofoca) != 0					// ���ֿ������ ������ ��� 
			)	
		{
			//  ����/û��/���� ������ �ٽ�  �ؾ� �� 
			return "Y";		
		}  
		
		// �ڵ����� �ٽ� ���� ����
		return "N";
	}
	
	// �����ȹ��ȣ, �����ȣ�� ȣ���ȣ   make�Ͽ� return
	private String f_posid_make(String zbpnnr, String matnr)
	{
		String 		posid   = "";		// ȣ��		
		if ("NS-100".equals(matnr))
		{
			posid = zbpnnr + "NS";
		}
		else
		{
			posid = zbpnnr + StringUtils.substring(matnr, 0, 1) + "01";
		}  
		return posid;
	}
	
}
