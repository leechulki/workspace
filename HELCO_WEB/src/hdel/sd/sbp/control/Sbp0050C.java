package hdel.sd.sbp.control;


import hdel.lib.control.SrmView; 
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.lib.exception.BizException;

import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.sbp.service.Sbp0050S; 
import hdel.sd.sbp.domain.Sbp0050;
import hdel.sd.sbp.domain.Sbp0050ParamVO;    
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoBpNumberS;  

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
      
import com.helco.xf.comm.CallSAPHdel;  
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;


import org.apache.log4j.Logger; 


/**
 * �����ȹ����ä�ǰ���(���⺸��)(Sbp0050C) Control
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
@RequestMapping("sbp0050")
public class Sbp0050C {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0050S service;   

	@Autowired
	private AutoBpNumberS 	Autoservice;	// �����ȹ��ȣä�� ���� 

	/*-----------------------------------------------------
	 *  ������� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_gtype")
	public View Sbp0050FindGTypeView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0050FindGTypeView START!!!");
		
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
			
			logger.debug("@@@ Sbp0050FindGTypeView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0050FindGTypeView Exception ERROR!!!");
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

		logger.debug("@@@ Sbp0050FindGTypeView addAttribute end" + "");
 
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  �����ȹ�⵵�� �ش��ϴ� �����Ͻ� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_opendtm")
	public View Sbp0050FindOpenDtmView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0050FindOpenDtmView START!!!" + "\n");
		
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
 
			Sbp0050ParamVO param = new Sbp0050ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			//param.setLang	(paramVO.getVariable("G_LANG"));	 			// ���
			param.setZPYEAR	(DatasetUtility.getString(ds_cond_opendtm,"ZPYEAR"	));	// ���⵵ 
			
			// ����CALL (�����ȹ�⵵�� �ش��ϴ� �������� ��ȸ)
			List<Sbp0050> list = service.selectOpenDtm(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����  
			CallSAPHdel.moveListToDs(ds_list_opendtm, Sbp0050.class, list);  

			logger.debug("@@@ Sbp0050FindOpenDtmView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0050FindOpenDtmView Exception ERROR!!!");
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

		logger.debug("@@@ Sbp0050FindOpenDtmView addAttribute end" + "");
		
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  �����ȹ���ָ�� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find")
	public View Sbp0050FindView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ Sbp0050FindView START!!!");
		
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
 
			Sbp0050ParamVO param = new Sbp0050ParamVO();
			
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
			List<Sbp0050> list = service.selectList(param);  
			
			logger.debug("@@@ list.size() ===>" + list.size());
		
			// ȣ����(list)�� ����Ÿ��(dsList)�� ���� 
			CallSAPHdel.moveListToDs(ds_list, Sbp0050.class, list);  

			logger.debug("@@@ Sbp0050FindView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0050FindView Exception ERROR!!!" + "\n");
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

		logger.debug("@@@ Sbp0050FindView addAttribute end" + "");
		
		return view;
	}
	

	/*-----------------------------------------------------
	 *  �����ȹ��ȣ�� �ش��ϴ� ����/û��/���� ��ȸ
	 ------------------------------------------------------*/ 
	@RequestMapping(value="find_detail")
	public View Sbp0050FindDetailView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Sbp0050FindDetailView START!!!" + "\n");
		
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
		logger.debug("@@@ ds_cond_detail.PSPIDSV	===>"+DatasetUtility.getString(ds_cond_detail,"PSPIDSV")	);
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
 
			Sbp0050ParamVO param = new Sbp0050ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// ����������Ʈ��ȣ  
			String pspidsv = DatasetUtility.getString(ds_cond_detail,"PSPIDSV");
			
			// �Ķ���� SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT 
			param.setPSPIDSV	(pspidsv);									// ����������Ʈ��ȣ
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
			List<Sbp0050> list = service.selectListDetail(param);  
			
			logger.debug("@@@ list.size ===>" + list.size());
		
			// ȣ����(list)�� ����Ÿ��(ds_list_bill)�� ���� 
			CallSAPHdel.moveListToDs(ds_list_bill, Sbp0050.class, list); 
			 
			logger.debug("@@@ Sbp0050FindDetailView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0050FindDetailView Exception ERROR!!!");
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

		logger.debug("@@@ Sbp0050FindDetailView addAttribute end" + "");
		
		return view;
	}
 
	/*------------------------------------------------------------
	 *  ��ȹ����� �ش��ϴ� �����ȹ ���/����/���� (����/û��/���� �����ĵ��)
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Sbp0050SaveView(MipParameterVO paramVO, Model model) { 
  
		logger.debug("@@@ Sbp0050SaveView START!!!" + "\n");
		
		// INPUT DATASET GET 
		Dataset ds_detail 	= paramVO.getDataset("ds_detail");  					// ���� ���,����,���� �������
		String 	pspidsv 	= DatasetUtility.getString(ds_detail, 0, "PSPIDSV");  	// ����������Ʈ��ȣ(���� ���� �� ȭ������ ����)
		String 	auart		= DatasetUtility.getString(ds_detail, 0, "AUART");		// �ǸŹ�������(��������)
		String 	flag 		= DatasetUtility.getString(ds_detail, 0, "FLAG");		// ���屸��(I,U,D) 
		String 	mandt 		= paramVO.getVariable("G_MANDT");						// SAP CLIENT 
				
		// �������� DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// �������  DATASET GET
		Dataset ds_result 	= paramVO.getDataset("ds_result");   

		// �����ȹ�� �����Ѵ�.
		try { 
		
			// 1.Session GET
			logger.debug("@@@ 1.Session GET"); 
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")); 
			  
			// 3.���� ȣ��
			logger.debug("@@@ 3.���� ȣ��"); 
			service.save(paramVO, model, pspidsv);
			
			// 4.����������Ʈ��ȣ ��ȯ
			logger.debug("@@@ 4.����������Ʈ��ȣ ��ȯ"); 
			ds_result.appendRow();
			ds_result.setColumn(0, "PSPIDSV", pspidsv);
			
			logger.debug("@@@ Sbp0050SaveView SUCCESS!!!");
			 
		} catch (BizException e) {  
		 	// ����������Ǿ� �����ڵ尡 ���ϵ� ��� (e.getMessage()�� �����ڵ常 ����� ��)
			logger.info("@@@ Sbp0050SaveView BizException ERROR!!! : " + e.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
		} catch (Exception e) { 
			// ����ġ���� ������������ ���		 (e.getMessage()�� �����޼��� ��ü�� ����� ��)
			e.printStackTrace();
			logger.error("@@@ Sbp0050SaveView Exception ERROR!!! : " + e.getMessage());
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

		logger.debug("@@@ Sbp0050SaveView addAttribute end" + "");

		return view;

	}
	 
	// �����ȹ��ȣ ä��
	public String getNewZbpnnr(String sysid, String mandt 
								, String gbn		// ä������(0:�����ȹ, 1:���ְ�ȹ , 2:����)
								, String auart		// �ǸŹ������� (��������)
								)
	{   

		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr START!!!");
		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr.mandt	===> " 	+ mandt);
		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr.gbn		===> " 	+ gbn);
		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr.auart	===> " 	+ auart);
		 
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
		List<AutoNumberVO> listVO = Autoservice.AutoBpNumber(param);   

		logger.debug("@@@ Sbp0050SaveView.getNewZbpnnr SUCCESS !!!");
		return listVO.get(0).getNumber().toString();  // ä���� �����ȹ��ȣ RETURN
		
	}
	
}
