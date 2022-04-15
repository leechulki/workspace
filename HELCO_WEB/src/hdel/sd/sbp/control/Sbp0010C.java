package hdel.sd.sbp.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;
import hdel.sd.sbp.domain.Sbp0160;
import hdel.sd.sbp.domain.Sbp0160ParamVO;
import hdel.sd.sso.domain.ZCOBS001;
import hdel.sd.sso.domain.ZCOBS002;
import hdel.sd.sso.domain.ZCOBT200;
import hdel.sd.sso.domain.ZCOBT202;
import hdel.sd.sso.domain.ZCOBT203;
import hdel.sd.sso.domain.ZCOBT300;
import hdel.sd.sso.domain.ZCOBT302;
//import hdel.sd.sso.domain.ZCOBT303;
import hdel.sd.sbp.domain.ZSDS0072;
import hdel.sd.sbp.domain.ZSDT0014;
import hdel.sd.sbp.domain.ZSDT0014S;
import hdel.sd.sbp.domain.ZSDT1001;
import hdel.sd.sbp.domain.ZSDT1005;
import hdel.sd.sbp.domain.ZSDT1012;
import hdel.sd.sbp.domain.ZSDT1023;
import hdel.sd.sbp.service.Sbp0010S;
import hdel.sd.sbp.service.Sbp0160S;
import hdel.sd.com.service.DutyS;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;


/**
 * �����ȹ����(Sbp0010C) Control
 * @Comment
 *     	1. View   find_gtype   	( ���� ��� ��ȸ )
 *     	2. Vie 	  find_opendtm 	( ���⵵�� �ش��ϴ� ���µ� �����ȹ������ ����Ͻ� ��ȸ )
 *     	3. View	  find 			( ��ȹ����� �ش��ϴ� �����ȹ���� ��� ��ȸ )
 *     	4. View   find_detail 	( �����ȹ��ȣ�� �ش��ϴ� ����/û��/���� ��� ��ȸ )
 *     	5. View   save 			( ��ȹ����� �ش��ϴ� �����ȹ ���/����/���� �� ����/û��/���� ������ ��� �� �����ȹ��ȣ�� ȭ������ ���� )
 *     	6. String getNewZbpnnr 	( �����ȹ��ȣ ä�� ) 
 *      7. Dataset plan_compute ( ����/û��/���� �ڵ��������� ��ȸ)
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 * 
 * �� �����ȹ������� 
 *         A : ZSDT1012, ZSDT0014 ���� �Ϸ� 
 *     ==> W : ZSDT0066, ZSDT0067 ���� �Ϸ�
 *     ==> 1,2,3 : ����/û��/���� �ڵ����� ����
 *     ==> Z : �����ȹ���� ��������
 */


@Controller
@RequestMapping("sbp0010")
public class Sbp0010C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0010S service;
	
	@Autowired
	private Sbp0160S sbp0160Service;

	@Autowired
	private AutoSoNumberS 	Autoservice;	// �����ȹ��ȣä�� ����

	@Autowired
	private DutyS dutyService;
	
	/*-----------------------------------------------------
	 *  �븮���ڵ�� �븮���� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_zagnt")
	public View Sbp0010FindZagntView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0010FindZagntView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_zagnt = paramVO.getDataset("ds_zagnt");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT"));
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG")); 
		logger.debug("@@@ ds_zagnt.ZAGNT	===>"+DatasetUtility.getString(ds_zagnt,"ZAGNT")); 
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0010ParamVO param = new Sbp0010ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				   	// SAP CLIENT 
			param.setZAGNT	(DatasetUtility.getString(ds_zagnt,"ZAGNT"	));		// �븮���ڵ� 
			
			// ����CALL (�븮������ȸ)
			List<Sbp0010> list = service.selectListZagnt(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����  
			ds_zagnt.deleteAll();
			CallSAPHdel.moveListToDs(ds_zagnt, Sbp0010.class, list); 
			
			logger.debug("@@@ Sbp0010FindZagntView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0010FindZagntView Exception ERROR!!!");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		
		// ��� dataset�� return
		logger.debug("@@@ ds_zagnt.getRowCount ===>" + ds_zagnt.getRowCount());
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_zagnt.setId		("ds_zagnt");  
		resultVO.addDataset	(ds_error);  		// ��������
		resultVO.addDataset	(ds_zagnt); 
		model.addAttribute("resultVO", resultVO);     

		logger.debug("@@@ Sbp0010FindZagntView addAttribute end" + "");
		
		return view;  
	} 
	
	
	/*-----------------------------------------------------
	 *  ������� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_gtype")
	public View Sbp0010FindGTypeView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0010FindGTypeView START!!!");
		
		// INPUT DATASET GET 
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_gtype 	= paramVO.getDataset("ds_list_gtype");			// UI�� return�� out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT	===>"+paramVO.getVariable("G_MANDT")); 
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
			
			logger.debug("@@@ Sbp0010FindGTypeView SUCCESS!!!"); 
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0010FindGTypeView Exception ERROR!!!");
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

		logger.debug("@@@ Sbp0010FindGTypeView addAttribute end" + "");
		
		return view;  
	} 
	
	/*-----------------------------------------------------
	 *  �� ������� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_rgtype")
	public View Sbp0010FindRGTypeView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0010FindRGTypeView START!!!");
		
		// INPUT DATASET GET 
		
		// INPUT DATASET GET
		Dataset ds_rgtype = paramVO.getDataset("ds_cond_gtype");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_gtype 	= paramVO.getDataset("ds_list_gtype");			// UI�� return�� out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT	===>"+paramVO.getVariable("G_MANDT")); 
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG"));
		//--------------------------------------------------------------------------------------------
		 
		try {  
			
			Sbp0010ParamVO param = new Sbp0010ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// �Ķ����SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				   	// SAP CLIENT 
			param.setVKBUR	(DatasetUtility.getString(ds_rgtype, "VKBUR"	));	// �μ� 
			param.setVKGRP	(DatasetUtility.getString(ds_rgtype, "VKGRP"	)); // ���ڵ�		
			param.setZKUNNR	(DatasetUtility.getString(ds_rgtype, "ZKUNNR"	));	// ������ڵ� 	
			
			// �Ǳ������ ��ȸ
			List<Sbp0010> list = service.selectListRGtype(param);  
			//List<ZGType> list = ZGTypes.find();   
			 
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����   
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				ds_list_gtype.appendRow();  
	    		// �÷�SET 
				ds_list_gtype.setColumn(iRow, "CODE"	 , list.get(iRow).getCODE	());	 // �Ǳ��� �ڵ� 
				ds_list_gtype.setColumn(iRow, "CODENAME" , list.get(iRow).getCODENAME	()); // �Ǳ��� �ڵ� �̸�
				ds_list_gtype.setColumn(iRow, "gtype"	 , list.get(iRow).getCODE	());	 // �Ǳ��� �ڵ� 
				ds_list_gtype.setColumn(iRow, "spart"	 , list.get(iRow).getSPART	());	 // ��ǰ��
				ds_list_gtype.setColumn(iRow, "auart"	 , list.get(iRow).getAUART	());	 // �ǸŹ������� 
				ds_list_gtype.setColumn(iRow, "matnr"	 , list.get(iRow).getMATNR	());	 // �����ȣ
			}   
			
			logger.debug("@@@ Sbp0010FindRGTypeView SUCCESS!!!"); 
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0010FindRGTypeView Exception ERROR!!!");
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

		logger.debug("@@@ Sbp0010FindRGTypeView addAttribute end" + "");
		
		return view;  
	} 	
	

	/*-----------------------------------------------------
	 *  �����ȹ�⵵�� �ش��ϴ� �����Ͻ� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find_opendtm")
	public View Sbp0010FindOpenDtmView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbp0010FindOpenDtmView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond_opendtm = paramVO.getDataset("ds_cond_opendtm");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_opendtm = paramVO.getDataset("ds_list_opendtm");		// UI�� return�� out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 			===>"+paramVO.getVariable("G_MANDT"));
		logger.debug("@@@ paramVO.G_LANG			===>"+paramVO.getVariable("G_LANG")); 
		logger.debug("@@@ ds_cond_opendtm.ZPYEAR	===>"+DatasetUtility.getString(ds_cond_opendtm,"ZPYEAR")); 
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0010ParamVO param = new Sbp0010ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// �Ķ����SET
			param.setMANDT	(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			//param.setLang	(paramVO.getVariable("G_LANG"));	 			// ���
			param.setZPYEAR	(DatasetUtility.getString(ds_cond_opendtm,"ZPYEAR"	));	// ���⵵ 
			
			// ����CALL (�����ȹ�⵵�� �ش��ϴ� �������� ��ȸ)
			List<Sbp0010> list = service.selectOpenDtm(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����  
			CallSAPHdel.moveListToDs(ds_list_opendtm, Sbp0010.class, list); 
			
			logger.debug("@@@ Sbp0010FindOpenDtmView SUCCESS!!!");
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0010FindOpenDtmView Exception ERROR!!!");
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	     
		
		// ��� dataset�� return
		logger.debug("@@@ ds_list_opendtm.getRowCount ===>" + ds_list_opendtm.getRowCount());
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId			("ds_error");   
		ds_list_opendtm.setId	("ds_list_opendtm");  
		resultVO.addDataset		(ds_error);  		// ��������
		resultVO.addDataset		(ds_list_opendtm); 
		model.addAttribute("resultVO", resultVO);     

		logger.debug("@@@ Sbp0010FindOpenDtmView addAttribute end" + "");
		
		return view;  
	} 
	

	/*-----------------------------------------------------
	 *  �����ȹ���ָ�� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="find")
	public View Sbp0010FindView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ Sbp0010FindView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 	= paramVO.getDataset("ds_list");				// UI�� return�� out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug( "@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT")); 
		logger.debug( "@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG")); 
		logger.debug( "@@@ ds_cond.ZPYEAR	===>"+DatasetUtility.getString(ds_cond,"ZPYEAR")); 
		logger.debug( "@@@ ds_cond.ZPYM		===>"+DatasetUtility.getString(ds_cond,"ZPYM") );
		logger.debug( "@@@ ds_cond.SPART	===>"+DatasetUtility.getString(ds_cond,"SPART")); 
		logger.debug( "@@@ ds_cond.VKBUR_FR	===>"+DatasetUtility.getString(ds_cond,"VKBUR_FR")); 
		logger.debug( "@@@ ds_cond.VKBUR_TO	===>"+DatasetUtility.getString(ds_cond,"VKBUR_TO") );
		logger.debug( "@@@ ds_cond.VKGRP_FR	===>"+DatasetUtility.getString(ds_cond,"VKGRP_FR") );
		logger.debug( "@@@ ds_cond.VKGRP_TO	===>"+DatasetUtility.getString(ds_cond,"VKGRP_TO") );
		logger.debug( "@@@ ds_cond.ZKUNNR	===>"+DatasetUtility.getString(ds_cond,"ZKUNNR"));
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Sbp0010ParamVO param = new Sbp0010ParamVO();
			
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
			List<Sbp0010> list = service.selectList(param);  
			
			logger.debug("@@@ list.size() ===>" + list.size()); 
		
			// ȣ����(list)�� ����Ÿ��(dsList)�� ���� 
			CallSAPHdel.moveListToDs(ds_list, Sbp0010.class, list);  
			
			logger.debug("@@@ Sbp0010FindView SUCCESS!!!"); 
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0010FindView Exception ERROR!!!");
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
		model.addAttribute("resultVO", resultVO);     

		logger.debug("@@@ Sbp0010FindView addAttribute end" + "");
		
		return view;
	}
	

	/*-----------------------------------------------------
	 *  �����ȹ��ȣ�� �ش��ϴ� ����/û��/���� ��ȸ
	 ------------------------------------------------------*/ 
	@RequestMapping(value="find_detail")
	public View Sbp0010FindDetailView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Sbp0010FindDetailView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond_detail 	= paramVO.getDataset("ds_cond_detail");
		Dataset ds_yearm 		= paramVO.getDataset("ds_yearm");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_detail 	= paramVO.getDataset("ds_list_detail");			// UI�� return�� out dataset  
		Dataset ds_error 		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug( "@@@ paramVO.G_MANDT 		===>"+paramVO.getVariable("G_MANDT"));
		logger.debug( "@@@ paramVO.G_LANG		===>"+paramVO.getVariable("G_LANG")); 
		logger.debug( "@@@ ds_cond_detail.ZBPNNR===>"+DatasetUtility.getString(ds_cond_detail,"ZBPNNR")); 
		logger.debug( "@@@ ds_yearm.getRowCount ===>"+ds_yearm.getRowCount());
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
 
			Sbp0010ParamVO param = new Sbp0010ParamVO();
			
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
			List<Sbp0010> list = service.selectListDetail(param);  
			
			logger.debug("@@@ list.toString ===>" + list.toString()); 
		
			// ȣ����(list)�� ����Ÿ��(dsList)�� ���� 
			CallSAPHdel.moveListToDs(ds_list_detail, Sbp0010.class, list);  

			logger.debug("@@@ Sbp0010FindDetailView SUCCESS!!!"); 
			 
		} catch (Exception e) { 
			logger.error("@@@ Sbp0010FindDetailView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		 
		// RFC ��� dataset�� return
		logger.debug("@@@ ds_list_detail.getRowCount ===>" + ds_list_detail.getRowCount()); 
		MipResultVO resultVO = new MipResultVO();
		ds_list_detail.setId("ds_list_detail");  
		ds_error.setId		("ds_error");   
		resultVO.addDataset (ds_list_detail);  
		resultVO.addDataset	(ds_error);  		// ��������
		model.addAttribute	("resultVO", resultVO);       

		logger.debug("@@@ Sbp0010FindDetailView addAttribute end" + "");
		
		return view;
	}
 
	/*------------------------------------------------------------
	 *  ��ȹ����� �ش��ϴ� �����ȹ ���/����/���� (����/û��/���� �����ĵ��)
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Sbp0010SaveView(MipParameterVO paramVO, Model model) {
   
		// 0. Sbp0010SaveView START
		logger.debug("@@@ 0. Sbp0010SaveView START!!!");
		
		// INPUT DATASET GET 
		Dataset ds_detail 		= paramVO.getDataset("ds_detail");  		// ���� ���,����,���� �������
//		Dataset ds_compute_cond	= paramVO.getDataset("ds_compute_cond");  	// ����/û��/���� �ڵ������ �����׸�
		Dataset ds_list_detail 	= paramVO.getDataset("ds_list_detail"); 	// ����/û��/���� ���� �������(����ݾ�)
		// kdh 
		Dataset ds_yearm 		= paramVO.getDataset("ds_yearm"); 			// ����/û��/���� ���� �������(18����ġ ������)
		
		// INPUT ITEM
		String mandt  = paramVO.getVariable("G_MANDT");						// SAP CLIENT 
		String flag 	  = DatasetUtility.getString(ds_detail, 0, "FLAG"  );		// ���屸��(I,U,D) 
		String zbpnnr = DatasetUtility.getString(ds_detail, 0, "ZBPNNR");		// �����ȹ��ȣ(���� ���� �� ȭ������ ����)
		String vkbur	  = DatasetUtility.getString(ds_detail, 0, "VKBUR" );
		String auart	  = DatasetUtility.getString(ds_detail, 0, "AUART" );
		String rtype    = DatasetUtility.getString(ds_detail, 0, "RTYPE" );
		String type1	  = DatasetUtility.getString(ds_detail, 0, "TYPE1" );
		String type2	  = DatasetUtility.getString(ds_detail, 0, "TYPE2" );
		String type3    = DatasetUtility.getString(ds_detail, 0, "TYPE3" );
		String matnr	  = DatasetUtility.getString(ds_detail, 0, "MATNR");
		String shangh = DatasetUtility.getString(ds_detail, 0, "SHANGH");
		String zinter   = DatasetUtility.getString(ds_detail, 0, "ZINTER");
		String zbdtyp   = DatasetUtility.getString(ds_detail, 0, "ZBDTYP"); //�ǹ��뵵  jss
		String zagnt   = DatasetUtility.getString(ds_detail, 0, "ZAGNT");  //�븮���ڵ� jss
		String zdeli   = DatasetUtility.getString(ds_detail, 0, "ZDELI");  //�ܳ����� jss
		
		//ZSDTDUTYD.SPEC8 ���üũ jss
		if (zagnt.trim().length()>0) zagnt="Y";
		else zagnt="";
		
		//ZSDTDUTYD.SPEC9 ���üũ jss
		if ("X".equals(zdeli)) zdeli="Y";
		else zdeli="";
		
		String zpyear  = null;													// �����Ͻ� 
		String deleteGbn = "";
		String dutyGbn   = "";
		String ztplno     = "";
		Object obj[]	= null;

		if (flag.equals("I") || flag.equals("U")) {
			dutyService.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			HashMap mapInput  = new HashMap();
			HashMap mapOutput = new HashMap();

			mapInput.put("VKBUR", vkbur); // �μ�
			mapInput.put("RTYPE", rtype); // ����
			mapInput.put("TYPE1", type1); // �ν�
			mapInput.put("TYPE2", type2); // �ӵ�
			mapInput.put("TYPE3", type3); // ����
			mapInput.put("MATNR", matnr);
			mapInput.put("SHANGH", shangh);
			mapInput.put("ZINTER", zinter);
			mapInput.put("ZBDTYP", zbdtyp); //�ǹ��뵵 jss
			mapInput.put("ZAGNT", zagnt);   //�븮���ڵ� jss
			mapInput.put("ZDELI", zdeli);   //�ܳ����� jss 

			if       (matnr.equals("L-1000") || matnr.equals("L-2000"))  dutyGbn = "PCELV";
			else if (matnr.equals("S-1000") || matnr.equals("W-1000")) dutyGbn = "PCESMW";
			else    dutyGbn = "PCELV";

			mapOutput = dutyService.genSpec(mandt, dutyGbn, "00", mapInput, paramVO.getVariable("G_LANG"));
			

					ztplno = mapOutput.get("ZTPLNO").toString();
			String gtype = mapOutput.get("GTYPE").toString();
			
/*			String spart = mapOutput.get("SPART").toString();
			String auart = mapOutput.get("AUART").toString();
			String	matnr = mapOutput.get("MATNR").toString();*/
			
			ds_detail.setColumn(0, "GTYPE", gtype);
			auart = mapOutput.get("AUART").toString();  //�������� jss
			ds_detail.setColumn(0, "AUART", auart);     //�������� jss
		}

		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};			// WEB I/F ó�� ���� ���
		ZSDT0014[] listZSDT0014 = new ZSDT0014[]{};		// �� ���ְ�ȹ
		ZSDT1001[] listZSDT1001 = new ZSDT1001[]{};		// ���ְ�ȹ  *
		ZSDT1005[] listZSDT1005 = new ZSDT1005[]{};		// ���ְ�ȹ(����) *
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[]{};	// �����ȹ DATA (WEB���� ����)  
		ZSDT1012[] listZSDT1012 = new ZSDT1012[]{};		// �����ȹ-���� *
		ZSDT1023[] listZSDT1023 = new ZSDT1023[]{};		// �����ȹ(����)-���� *
		ZSDS0072[] listZSDS0072 = new ZSDS0072[]{};		// [SD] ����/��� ��ȹ �ڵ� ��� EXPORT

		ZCOBS001[] listZCOBS001 = new ZCOBS001[]{};
		ZCOBS002[] listZCOBS002 = new ZCOBS002[]{};
		ZCOBT200[] listZCOBT200 = new ZCOBT200[]{};
		ZCOBT202[] listZCOBT202 = new ZCOBT202[]{};
		ZCOBT203[] listZCOBT203 = new ZCOBT203[]{};
		ZCOBT300[] listZCOBT300 = new ZCOBT300[]{};
		ZCOBT302[] listZCOBT302 = new ZCOBT302[]{};

		Dataset dsZSDT0014S = ZSDT0014S.getDataset();
		Dataset dsZSDT1012 = ZSDT1012.getDataset();
		Dataset dsZCOBS001 = ZCOBS001.getDataset();
		Dataset dsZSDS0072 = ZSDS0072.getDataset();  // �߰�  kdh
		Dataset dsZCOBT303 = null;
		 
		// �������� DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// �������  DATASET GET
		Dataset ds_result 	= paramVO.getDataset("ds_result");   

		// 1.Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// �����ȹ�� �����Ѵ�.
		try {
			// 2. �����ȹ �űԻ�����û�� ��� 
			if ("I".equals(flag)) {
				zbpnnr = getNewZbpnnr(paramVO.getVariable("G_SYSID"), mandt, "1", auart); 
			}	// end of if ("I".equals(flag))
			
			service.createDao(session);
			//------------------------------------------------------------------------
			// �����Ͻ� ���翩�� üũ (�����Ͻ� ������� ����Ұ�ó��)
			//------------------------------------------------------------------------
			zpyear	= DatasetUtility.getString(ds_detail, 0, "ZPYEAR");	// ���⵵ 
		/*	if (service.chkOpenDtm(session, mandt, zpyear)==false)	{ 
				throw new BizException("CE10002"); 				// "CE10002", "���µ��� ���� ���⵵�� �����ȹ�Դϴ�.\n�ٽ� �ѹ� Ȯ�� �ٶ��ϴ�."
			} */    // �����ȹ ������ �ӽ� �ּ� ó�� 2013011

			try {
				dsZSDT0014S.deleteAll();
				dsZSDT1012.deleteAll();
	
				rfcParamSet(paramVO, session, ds_list_detail,  dsZSDS0072, ds_detail, ds_yearm, dsZSDT0014S
						, dsZSDT1012, dsZCOBS001, flag, mandt, zbpnnr, zpyear, ztplno);
			} catch (Exception e ) {
				e.printStackTrace();
			}
		} catch (Exception e) { 
			// ����ġ���� ������������ ���		 (e.getMessage()�� �����޼��� ��ü�� ����� ��)
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		// ����/û��/���� ���� RFC
		try {
			listZSDT0014S = (ZSDT0014S[])moveDs2Obj2(dsZSDT0014S, ZSDT0014S.class, null);
			listZSDT1012 = (ZSDT1012[])moveDs2Obj2(dsZSDT1012, ZSDT1012.class, null);
			
			// kdh  �ڵ� ���� 
			listZSDS0072 = (ZSDS0072[])moveDs2Obj2(dsZSDS0072, ZSDS0072.class, null);
			
			if ("D".equals(flag)) {
				deleteGbn = "D";
			} else if ("U".equals(flag)){
				
				deleteGbn = "U";
			}
			
			obj = new Object[]{ 
					  "S"
					, deleteGbn
					, "Y"
					, DatasetUtility.getString(ds_detail, 0, "UFLAG")
					, listMsg
					, listZSDT0014
					, listZSDT1001  //
					, listZSDT1005  //
					, listZSDT0014S
					, listZSDT1012  //
					, listZSDT1023  //
					, listZSDS0072
			};
			
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
							, "hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);

			if ( "4".equals(stub.getOutput("E_TYPE")) ) {
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
						, listMsg[0].getIDX().toString()
						, listMsg[0].getERRMSG().toString()
						, "Y"
						, "Y");
			} else {
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			}
			
		} catch (BizException e) {
			e.printStackTrace();
		 	// ����������Ǿ� �����ڵ尡 ���ϵ� ��� (e.getMessage()�� �����ڵ常 ����� ��)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
		} catch (Exception e ) {
			e.printStackTrace();
			// ����ġ���� ������������ ���		 (e.getMessage()�� �����޼��� ��ü�� ����� ��)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		// ��ǰ�� 10�ϰ��  �°���  �������� 
		if("10".equals(DatasetUtility.getString(ds_detail, 0, "SPART"))) {

			// �������� RFC
			try {
				if ("I".equals(flag) || "U".equals(flag)) {
					listZCOBS001 = (ZCOBS001[])moveDs2Obj2(dsZCOBS001, ZCOBS001.class, null);
	
					// RFC INPUT SET
					obj = new Object[] {
							  "1"  				// I_DIV   �з�( 1:�����ȹ,2:���ְ�ȹ,3:����,4:����,9:���� )
							, "3"  				// I_GBN   ó������ (1: BOM, 2: ����, 3: Web )
							, ""  				// I_GJAHR �����⵵(=�����ȹ�⵵)
							, ""  				// I_POPER ���س��
							, listZCOBS001
							, listZCOBS002
							, listZCOBT200
							, listZCOBT202
							, listZCOBT203
							, listZCOBT300
							, listZCOBT302
//							, listZCOBT303
					};

				}
			} catch (BizException e) {
				e.printStackTrace();
			 	// ����������Ǿ� �����ڵ尡 ���ϵ� ��� (e.getMessage()�� �����ڵ常 ����� ��)
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			} catch (Exception e ) {
				e.printStackTrace();
				// ����ġ���� ������������ ���		 (e.getMessage()�� �����޼��� ��ü�� ����� ��)
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			}

		}
		// 9.�����ȹ��ȣ ��ȯ
		logger.debug("@@@ 9.�����ȹ��ȣ ��ȯ"); 
		ds_result.appendRow();
		ds_result.setColumn(0, "ZBPNNR"		, zbpnnr);		// �����ȹ��ȣ

		// ó����� ����
		MipResultVO resultVO = new MipResultVO(); 
		logger.debug("@@@ ds_result.getRowCount ===>"+ds_result.getRowCount());
		logger.debug("@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount());
		ds_result.setId		("ds_result");   
		resultVO.addDataset	(ds_result);  	// ���INFO(�����ȹ��ȣ) 
		ds_error.setId		("ds_error");   
		resultVO.addDataset	(ds_error);  	// ����INFO  
		model.addAttribute	("resultVO", resultVO);    

		logger.debug("@@@ Sbp0010SaveView addAttribute end" + "");

		return view;
	} 


	// �����ȹ��ȣ ä��
	public String getNewZbpnnr(String sysid, String mandt 
								, String gbn		// ä������(0:�����ȹ, 1:���ְ�ȹ , 2:����)
								, String auart		// �ǸŹ������� (��������)
								)
	{   

		logger.debug( "@@@ Sbp0010SaveView.getNewZbpnnr START!!!"  );
		logger.debug( "@@@ Sbp0010SaveView.getNewZbpnnr.mandt	===> " 	+ mandt); 
		logger.debug( "@@@ Sbp0010SaveView.getNewZbpnnr.gbn 	===> "	+ gbn); 
		logger.debug( "@@@ Sbp0010SaveView.getNewZbpnnr.auart	===> " 	+ auart );
		 
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

		logger.debug("@@@ Sbp0010SaveView.getNewZbpnnr SUCCESS !!!");
		return listVO.get(0).getNumber().toString();  // ä���� �����ȹ��ȣ RETURN
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

	public static Object moveDs2Obj2(Dataset ds, Class cls, Ds2ObjHelper helper) {
		// �ش� Type���� �迭 ���� 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set �޼ҵ常 �������� 
			if ( mArr[i].getName().startsWith("set")) {
				mData.put(mArr[i].getName().substring(3), mArr[i]);
			}
		}
		Object tmpObj = null;
		Method m = null;
		Class setClass = null;
		BigDecimal b = null; 
		
		for( int i = 0; i < ds.getRowCount(); i++ ) {
			try {
				tmpObj = cls.newInstance();
				
				for( int c = 0; c < ds.getColumnCount(); c++) {
					m = (Method) mData.get(ds.getColumnID(c));
					if ( m != null ) {
						setClass = m.getParameterTypes()[0];
						if ( setClass.getName().equals("int")){
							m.invoke(tmpObj, DatasetUtility.getInt(ds,i, ds.getColumnID(c)));
						} else if ( setClass.getName().equals("java.math.BigDecimal")){
							b = new BigDecimal(DatasetUtility.getDbl(ds, i, ds.getColumnID(c), 0));
							if ("ZNUMBER".equals(ds.getColumnID(c))) {
								b = b.setScale(0, RoundingMode.DOWN);  // �Ҽ� 0�ڸ�
							} else if ("ZFORE".equals(ds.getColumnID(c)) || "SHANG".equals(ds.getColumnID(c))) {							
								b = b.setScale(1, RoundingMode.HALF_UP);  // �Ҽ� 1�ڸ�
							} else if ("MRATE".equals(ds.getColumnID(c))) {
								b = b.setScale(4, RoundingMode.DOWN);  // �Ҽ� 4�ڸ�
							} else {
								b = b.setScale(2, RoundingMode.DOWN);	// �Ҽ� 2�ڸ�
							}
							m.invoke(tmpObj, b);
						} else { 
							m.invoke(tmpObj, DatasetUtility.getString(ds, i, ds.getColumnID(c)));
						}
					}
				}
				
				// ó�� ���� �˸�. 
				if ( helper != null ) {
					helper.endMoveRow(ds, i, tmpObj);
				}
			} catch( Exception e){
				e.printStackTrace();
			}
			
			// Array ���� ���� 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}

//�߰� Dataset ds_list_detail, Dataset dsZSDS0072,     KDH 
	private void rfcParamSet(MipParameterVO paramVO, SqlSession session, Dataset ds_list_detail, Dataset dsZSDS0072,  Dataset ds_detail, Dataset ds_yearm
							, Dataset dsZSDT0014S, Dataset dsZSDT1012, Dataset dsZCOBS001
							, String FLAG, String MANDT, String ZBPNNR, String ZPYEAR, String ztplno) {
		List<Sbp0160> listTemplte = null;
		Sbp0160ParamVO param = new Sbp0160ParamVO();
		//------------------------------------------------------------------------
		// ���� ����
		//------------------------------------------------------------------------ 
		String biddat = "";
		String deldat = "";
		String date = "";
		String time = "";
		String cdate = "";
		String ctime = "";
		String matnr = DatasetUtility.getString(ds_detail, 0, "MATNR");		// �����ȣ
		String posid = f_posid_make(ZBPNNR, matnr);							// ȣ���ȣ
		String kunnr = DatasetUtility.getString(ds_detail, 0, "KUNNR");		// �ŷ����ڵ�
		String waerk = DatasetUtility.getString(ds_detail, 0, "WAERK");		// ��ȭ
		BigDecimal zfore = new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "ZFORE"));	// ���������
		BigDecimal sofoca12 = null;
		BigDecimal sofoca14 = null;

		// ��ȭ�� ��ȭ�� ��� 100���� ������ ���ֿ���ݾ��� �����Ѵ�.
		if ("KRW".equals(waerk) ||"JPY".equals(waerk))
		{
			sofoca12	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "SOFOCA") + "");// ���ֿ���ݾ�
			sofoca14	= new BigDecimal((DatasetUtility.getDbl(ds_detail, 0, "SOFOCA")/100.0) + "");// ���ֿ���ݾ�
		}
		else
		{
			sofoca12	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "SOFOCA") + "");// ���ֿ���ݾ�
			sofoca14	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "SOFOCA") + "");// ���ֿ���ݾ�
		}

		sofoca12 = sofoca12.setScale(2, RoundingMode.DOWN);
		sofoca14 = sofoca14.setScale(2, RoundingMode.DOWN);
		
		//------------------------------------------------------------------------
		// DAO ����
		//------------------------------------------------------------------------
		service.createDao(session);
		sbp0160Service.createDao(session);

		param.setMANDT(MANDT);
		param.setZTPLNO(ztplno);	// ���ø���ȣ

		if ("I".equals(FLAG) || "U".equals(FLAG)) {
			dsZSDT0014S.appendRow();
			dsZSDT1012.appendRow();
			dsZCOBS001.appendRow();

			biddat = DatasetUtility.getString(ds_detail, 0, "ZPYM").substring(0, 4) + "-" 
					+ DatasetUtility.getString(ds_detail, 0, "ZPYM").substring(4) + "-01";
			deldat = DatasetUtility.getString(ds_detail, 0, "DELDAT");
			deldat = deldat.substring(0, 4) + "-" + deldat.substring(4).substring(0, 2) + "-" 
					+ deldat.substring(6);
			date = DateTime.getDateString();
			date = date.substring(0, 4) + "-" + date.substring(4).substring(0, 2) + "-" + date.substring(6);
			time = DateTime.getShortTimeString();
			time = time.substring(0, 2) + ":" + time.substring(2).substring(0, 2) + ":" + time.substring(4);

			// 0.�ŷ����ڵ� ��ȿ�� üũ
			if (service.chkBuyrCd(session, MANDT, kunnr)==false)
			{
				// "CE10001", "�ŷ����ڵ尡 ��ȿ���� �ʽ��ϴ�.\n�ٽ� �ѹ� Ȯ�� �ٶ��ϴ�."
				throw new BizException("CE10001"); 
			}

			if ("I".equals(FLAG)) {
				dsZSDT0014S.setColumn(0, "CDATE", date);
				dsZSDT0014S.setColumn(0, "CTIME", time);
				dsZSDT0014S.setColumn(0, "CUSER", paramVO.getVariable("G_USER_ID"));
				dsZSDT0014S.setColumn(0, "UDATE", "0000-00-00");
				dsZSDT0014S.setColumn(0, "UTIME", "00:00:00");
				dsZSDT0014S.setColumn(0, "UUSER", " ");
				dsZSDT0014S.setColumn(0, "DDATE", "0000-00-00");
				dsZSDT0014S.setColumn(0, "DTIME", "00:00:00");
				dsZSDT0014S.setColumn(0, "DUSER", " ");

				dsZSDT1012.setColumn(0, "CDATE", date);
				dsZSDT1012.setColumn(0, "CTIME", time);
				dsZSDT1012.setColumn(0, "CUSER", paramVO.getVariable("G_USER_ID"));
				dsZSDT1012.setColumn(0, "UDATE", "0000-00-00");
				dsZSDT1012.setColumn(0, "UTIME", "00:00:00");
				dsZSDT1012.setColumn(0, "UUSER", " ");
				dsZSDT1012.setColumn(0, "DDATE", "0000-00-00");
				dsZSDT1012.setColumn(0, "DTIME", "00:00:00");
				dsZSDT1012.setColumn(0, "DUSER", " ");
			} else if ("U".equals(FLAG)) {
				cdate = DatasetUtility.getString(ds_detail, 0, "CDATE");
				cdate = cdate.substring(0, 4) + "-" + cdate.substring(4).substring(0, 2) + "-" + cdate.substring(6);
				ctime = DatasetUtility.getString(ds_detail, 0, "CTIME");
				ctime = ctime.substring(0, 2) + ":" + ctime.substring(2).substring(0, 2) + ":" + ctime.substring(4);

				dsZSDT0014S.setColumn(0, "CDATE", cdate);
				dsZSDT0014S.setColumn(0, "CTIME", ctime);
				dsZSDT0014S.setColumn(0, "CUSER", DatasetUtility.getString(ds_detail, 0, "CUSER"));
				dsZSDT0014S.setColumn(0, "UDATE", date);
				dsZSDT0014S.setColumn(0, "UTIME", time);
				dsZSDT0014S.setColumn(0, "UUSER", paramVO.getVariable("G_USER_ID"));

				dsZSDT1012.setColumn(0, "CDATE", cdate);
				dsZSDT1012.setColumn(0, "CTIME", ctime);
				dsZSDT1012.setColumn(0, "CUSER", DatasetUtility.getString(ds_detail, 0, "CUSER"));
				dsZSDT1012.setColumn(0, "UDATE", date);
				dsZSDT1012.setColumn(0, "UTIME", time);
				dsZSDT1012.setColumn(0, "UUSER", paramVO.getVariable("G_USER_ID"));
				
				String gbn = null;							// ����/û��/���� ����	(1:����, 2:û��, 3:����)
				BigDecimal amt = null;						// ����/û��/���� �ݾ�
				BigDecimal zero = new BigDecimal("0.00");	// 0.00
				String colId = null;
				String yearm = null;
				
				for (int iRow1=0;iRow1<ds_list_detail.getRowCount(); iRow1++ ) 
				{
					gbn = ds_list_detail.getColumnAsString(iRow1, "GBN"); 	// ����/û��/���� ����
					
					for (int iCol1=0;iCol1<ds_list_detail.getColumnCount(); iCol1++ ) 
					{
						colId = ds_list_detail.getColumnID(iCol1);
						
						//if (!("MANDT".equals(colId)) && !("GBN".equals(colId)) && !("GBN_NM".equals(colId)))
						if ( colId.startsWith(("AMT")))
						{
							// �ݾ��׸� ���� �ִ� ��쿡�� ����
							if (StringUtils.isNotBlank(ds_list_detail.getColumnAsString(iRow1, iCol1)))
							{ 
								// ����ݾ�
								amt = new BigDecimal(ds_list_detail.getColumnAsDouble(iRow1, iCol1));  
								
								// �ݾ� > 0 �� ��츸 ���
								//if( amt.compareTo(zero) == 1 )  // compareTo :::: -1�� �۴�, 0�� ����, 1�� ũ�� 
								//{  	
									if ( colId.endsWith("00")) yearm = ds_yearm.getColumnAsString( 0, "YEARM");
									if ( colId.endsWith("01")) yearm = ds_yearm.getColumnAsString( 1, "YEARM");
									if ( colId.endsWith("02")) yearm = ds_yearm.getColumnAsString( 2, "YEARM");
									if ( colId.endsWith("03")) yearm = ds_yearm.getColumnAsString( 3, "YEARM");
									if ( colId.endsWith("04")) yearm = ds_yearm.getColumnAsString( 4, "YEARM");
									if ( colId.endsWith("05")) yearm = ds_yearm.getColumnAsString( 5, "YEARM");
									if ( colId.endsWith("06")) yearm = ds_yearm.getColumnAsString( 6, "YEARM");
									if ( colId.endsWith("07")) yearm = ds_yearm.getColumnAsString( 7, "YEARM");
									if ( colId.endsWith("08")) yearm = ds_yearm.getColumnAsString( 8, "YEARM");
									if ( colId.endsWith("09")) yearm = ds_yearm.getColumnAsString( 9, "YEARM");
									if ( colId.endsWith("10")) yearm = ds_yearm.getColumnAsString(10, "YEARM");
									if ( colId.endsWith("11")) yearm = ds_yearm.getColumnAsString(11, "YEARM");
									if ( colId.endsWith("12")) yearm = ds_yearm.getColumnAsString(12, "YEARM");
									if ( colId.endsWith("13")) yearm = ds_yearm.getColumnAsString(13, "YEARM");
									if ( colId.endsWith("14")) yearm = ds_yearm.getColumnAsString(14, "YEARM");
									if ( colId.endsWith("15")) yearm = ds_yearm.getColumnAsString(15, "YEARM");
									if ( colId.endsWith("16")) yearm = ds_yearm.getColumnAsString(16, "YEARM");
									if ( colId.endsWith("17")) yearm = ds_yearm.getColumnAsString(17, "YEARM");
									if ( colId.endsWith("18")) yearm = ds_yearm.getColumnAsString(18, "YEARM");
									if ( colId.endsWith("19")) yearm = ds_yearm.getColumnAsString(19, "YEARM");
									if ( colId.endsWith("20")) yearm = ds_yearm.getColumnAsString(20, "YEARM");
									if ( colId.endsWith("21")) yearm = ds_yearm.getColumnAsString(21, "YEARM");
									if ( colId.endsWith("22")) yearm = ds_yearm.getColumnAsString(22, "YEARM");
									if ( colId.endsWith("23")) yearm = ds_yearm.getColumnAsString(23, "YEARM");
									if ( colId.endsWith("24")) yearm = ds_yearm.getColumnAsString(24, "YEARM");
									if ( colId.endsWith("25")) yearm = ds_yearm.getColumnAsString(25, "YEARM");
									if ( colId.endsWith("26")) yearm = ds_yearm.getColumnAsString(26, "YEARM");
									if ( colId.endsWith("27")) yearm = ds_yearm.getColumnAsString(27, "YEARM");
									if ( colId.endsWith("28")) yearm = ds_yearm.getColumnAsString(28, "YEARM");
									if ( colId.endsWith("29")) yearm = ds_yearm.getColumnAsString(29, "YEARM");
									
									int nRow = dsZSDS0072.appendRow();
									// ���ְ�ȹ ��ȣ 
						
									dsZSDS0072.setColumn(nRow, "SONNR", ZBPNNR);
									if("1".equals(gbn)) {
										// ���� 
										dsZSDS0072.setColumn(nRow, "PLANTP", "MC" );
									} else if("2".equals(gbn)) {
										// û�� 
										dsZSDS0072.setColumn(nRow, "PLANTP", "CH" );
									} else if("3".equals(gbn)) {
										// ���� 
										dsZSDS0072.setColumn(nRow, "PLANTP", "SG" );
									} 
									//��ȹ ��� 
									dsZSDS0072.setColumn(nRow, "PLANYM", yearm);
									
							       //��ȹ �ݾ� 
									dsZSDS0072.setColumn(nRow, "PLANAMT", ds_list_detail.getColumnAsDouble(iRow1, iCol1));
									
									//������ȭ
									dsZSDS0072.setColumn(nRow, "WAERK", waerk);

								//}  	// end of if( amt.compareTo(zero) == 1 ) 
							}		// end of if (StringUtils.isNotBlank(ds_list_detail.getColumnAsString(iRow1, iCol1)))
						}			// 
					}  				// end of for (int iCol1=2;iCol1<=ds_list_detail.getColumnCount(); iCol1++ ) 
				}					// end of for (int iRow1=0;iRow1<=ds_list_detail.getRowCount(); iRow1++ ) 
			}
			dsZSDT0014S.setColumn(0, "MANDT", MANDT);
			dsZSDT0014S.setColumn(0, "SONNR", ZBPNNR);
			dsZSDT0014S.setColumn(0, "PSPID", ZBPNNR);
			dsZSDT0014S.setColumn(0, "POSID", posid);
			dsZSDT0014S.setColumn(0, "BIDYM", DatasetUtility.getString(ds_detail, 0, "ZPYM"));
			dsZSDT0014S.setColumn(0, "AUART", DatasetUtility.getString(ds_detail, 0, "AUART"));
			dsZSDT0014S.setColumn(0, "SPART", DatasetUtility.getString(ds_detail, 0, "SPART"));
			dsZSDT0014S.setColumn(0, "MATNR", DatasetUtility.getString(ds_detail, 0, "MATNR"));
			dsZSDT0014S.setColumn(0, "VKBUR", DatasetUtility.getString(ds_detail, 0, "VKBUR"));
			dsZSDT0014S.setColumn(0, "VKGRP", DatasetUtility.getString(ds_detail, 0, "VKGRP"));
			dsZSDT0014S.setColumn(0, "ZKUNNR", DatasetUtility.getString(ds_detail, 0, "ZKUNNR"));
			dsZSDT0014S.setColumn(0, "BIDDAT", biddat);
			dsZSDT0014S.setColumn(0, "KUNNR", kunnr);
			dsZSDT0014S.setColumn(0, "NAME1", DatasetUtility.getString(ds_detail, 0, "KUNNR_NM"));
			dsZSDT0014S.setColumn(0, "GSNAM", DatasetUtility.getString(ds_detail, 0, "GSNAM"));
			dsZSDT0014S.setColumn(0, "REGION", DatasetUtility.getString(ds_detail, 0, "REGION"));
			dsZSDT0014S.setColumn(0, "GTYPE", DatasetUtility.getString(ds_detail, 0, "GTYPE"));   // ��ǥ����

			dsZSDT0014S.setColumn(0, "GTYPE_OLD", DatasetUtility.getString(ds_detail, 0, "RTYPE")); // �Ǳ���
			dsZSDT0014S.setColumn(0, "ZBDTYP", DatasetUtility.getString(ds_detail, 0, "ZBDTYP")); // �ǹ��뵵 jss
			dsZSDT0014S.setColumn(0, "ZPYEAR", ZPYEAR); //jss
			dsZSDT0014S.setColumn(0, "GSPEC1", DatasetUtility.getString(ds_detail, 0, "TYPE1"));
			dsZSDT0014S.setColumn(0, "GSPEC2", DatasetUtility.getString(ds_detail, 0, "TYPE2"));
			dsZSDT0014S.setColumn(0, "GSPEC3", DatasetUtility.getString(ds_detail, 0, "TYPE3"));
			dsZSDT0014S.setColumn(0, "ZNUMBER", DatasetUtility.getString(ds_detail, 0, "ZNUMBER"));
			dsZSDT0014S.setColumn(0, "ZDELI", DatasetUtility.getString(ds_detail, 0, "ZDELI"));
			dsZSDT0014S.setColumn(0, "SHANGH", DatasetUtility.getString(ds_detail, 0, "SHANGH"));
			dsZSDT0014S.setColumn(0, "ZINTER", DatasetUtility.getString(ds_detail, 0, "ZINTER"));
			dsZSDT0014S.setColumn(0, "SOFOCA", sofoca14.doubleValue());
			dsZSDT0014S.setColumn(0, "ZFORE", zfore.doubleValue());
			dsZSDT0014S.setColumn(0, "WAERK", waerk);
			dsZSDT0014S.setColumn(0, "DELDAT", deldat);

			dsZSDT1012.setColumn(0, "MANDT", MANDT);
			dsZSDT1012.setColumn(0, "ZBPNNR", ZBPNNR);
			dsZSDT1012.setColumn(0, "ZPYEAR", ZPYEAR);
			dsZSDT1012.setColumn(0, "ZPYM", DatasetUtility.getString(ds_detail, 0, "ZPYM"));
			dsZSDT1012.setColumn(0, "SPART", DatasetUtility.getString(ds_detail, 0, "SPART"));
			dsZSDT1012.setColumn(0, "AUART", DatasetUtility.getString(ds_detail, 0, "AUART"));
			dsZSDT1012.setColumn(0, "MATNR", DatasetUtility.getString(ds_detail, 0, "MATNR"));
			dsZSDT1012.setColumn(0, "VKBUR", DatasetUtility.getString(ds_detail, 0, "VKBUR"));
			dsZSDT1012.setColumn(0, "VKGRP", DatasetUtility.getString(ds_detail, 0, "VKGRP"));
			dsZSDT1012.setColumn(0, "ZKUNNR", DatasetUtility.getString(ds_detail, 0, "ZKUNNR"));
			dsZSDT1012.setColumn(0, "GTYPE", DatasetUtility.getString(ds_detail, 0, "GTYPE"));
			dsZSDT1012.setColumn(0, "RTYPE", DatasetUtility.getString(ds_detail, 0, "RTYPE"));
			dsZSDT1012.setColumn(0, "ZBDTYP", DatasetUtility.getString(ds_detail, 0, "ZBDTYP")); //�ǹ��뵵jss
			dsZSDT1012.setColumn(0, "TYPE1", DatasetUtility.getString(ds_detail, 0, "TYPE1"));
			dsZSDT1012.setColumn(0, "TYPE2", DatasetUtility.getString(ds_detail, 0, "TYPE2"));
			dsZSDT1012.setColumn(0, "TYPE3", DatasetUtility.getString(ds_detail, 0, "TYPE3"));
			dsZSDT1012.setColumn(0, "ZNUMBER", DatasetUtility.getDouble(ds_detail, 0, "ZNUMBER"));
			dsZSDT1012.setColumn(0, "KUNNR", kunnr);
			dsZSDT1012.setColumn(0, "GSNAM", DatasetUtility.getString(ds_detail, 0, "GSNAM"));
			dsZSDT1012.setColumn(0, "REGION", DatasetUtility.getString(ds_detail, 0, "REGION"));
			dsZSDT1012.setColumn(0, "ZDELI", DatasetUtility.getString(ds_detail, 0, "ZDELI"));
			dsZSDT1012.setColumn(0, "SHANGH", DatasetUtility.getString(ds_detail, 0, "SHANGH"));
			dsZSDT1012.setColumn(0, "ZINTER", DatasetUtility.getString(ds_detail, 0, "ZINTER"));
			dsZSDT1012.setColumn(0, "SOFOCA", sofoca12.doubleValue());
			dsZSDT1012.setColumn(0, "ZFORE", zfore.doubleValue());
			dsZSDT1012.setColumn(0, "WAERK", waerk);
			dsZSDT1012.setColumn(0, "DELDAT", deldat);
			dsZSDT1012.setColumn(0, "ZRMK", DatasetUtility.getString(ds_detail, 0, "ZRMK"));
			dsZSDT1012.setColumn(0, "ZAGNT", DatasetUtility.getString(ds_detail, 0, "ZAGNT"));
			
			String spart = DatasetUtility.getString(ds_detail, 0, "SPART");

			if("10".equals(spart) && ztplno != null && !ztplno.equals("") ) {	// spart ��ǰ��  10 �°���  �ϰ��  ���� ����

				listTemplte = sbp0160Service.selectListTempletInfo(param);

				dsZCOBS001.deleteAll(); // ���� ZCOBS001
				
				for(int i=0; i< listTemplte.size(); i++) {
					dsZCOBS001.appendRow(); 	// ���߰�
					dsZCOBS001.setColumn(i, "QTNUM", ZBPNNR);
					dsZCOBS001.setColumn(i, "GJAHR", DatasetUtility.getString(ds_detail, 0, "ZPYM").substring(0, 4));
					dsZCOBS001.setColumn(i, "POPER", "0" + DatasetUtility.getString(ds_detail, 0, "ZPYM").substring(4));
					dsZCOBS001.setColumn(i, "MATNR", matnr);
					dsZCOBS001.setColumn(i, "VKBUR", DatasetUtility.getString(ds_detail, 0, "VKBUR"));
					dsZCOBS001.setColumn(i, "VKGRP", DatasetUtility.getString(ds_detail, 0, "VKGRP"));
					dsZCOBS001.setColumn(i, "AUART", DatasetUtility.getString(ds_detail, 0, "AUART"));
					dsZCOBS001.setColumn(i, "REGIO", "");
					dsZCOBS001.setColumn(i, "LZONE", "");
					dsZCOBS001.setColumn(i, "ATNAM", listTemplte.get(i).getATNAM());
					dsZCOBS001.setColumn(i, "VALUE", listTemplte.get(i).getVALUE());
				}
			}
		} else if ("D".equals(FLAG)) {
			dsZSDT0014S.appendRow();
			dsZSDT1012.appendRow();

			dsZSDT0014S.setColumn(0, "MANDT", MANDT);
			dsZSDT0014S.setColumn(0, "SONNR", ZBPNNR);
			dsZSDT0014S.setColumn(0, "PSPID", ZBPNNR);

			dsZSDT1012.setColumn(0, "MANDT", paramVO.getVariable("G_MANDT"));
			dsZSDT1012.setColumn(0, "ZBPNNR", ZBPNNR);
		}
	}


	private void costUpdate(Dataset dsZSDT1012, Dataset dsZCOBT303, String MANDT, String UUSER, SqlSession session) {
//		Method[] mArr = ZCOBT303.class.getMethods();
//		HashMap mData = new HashMap();
//		for ( int i = 0; i < mArr.length; i++) {
//			// Set �޼ҵ常 ��������
//			if ( mArr[i].getName().startsWith("set")) {
//				mData.put(mArr[i].getName().substring(3), mArr[i]);
//			}
//		}
//
//		Method m = null;
//		Class setClass = null;
//		double sum = 0;
//		BigDecimal b = null;
//		BigDecimal ZCOST = new BigDecimal(0);
//		Sbp0010 param = new Sbp0010();
//
//		for( int i = 0; i < dsZCOBT303.getRowCount(); i++ ) {
//			try {
//				ZCOST = new BigDecimal(0);
//
//				for( int c = 0; c < dsZCOBT303.getColumnCount(); c++) {
//					m = (Method) mData.get(dsZCOBT303.getColumnID(c));
//
//					if ( m != null ) {
//						setClass = m.getParameterTypes()[0];
//
//						if ( setClass.getName().equals("java.math.BigDecimal")){
//							if ("EQMA01".equals(dsZCOBT303.getColumnID(c)) 
//								|| "EQMA02".equals(dsZCOBT303.getColumnID(c))
//								|| "PRDA01".equals(dsZCOBT303.getColumnID(c)) 
//								|| "CTSA01".equals(dsZCOBT303.getColumnID(c))) {
//								continue;
//							} else {
//								b = new BigDecimal(DatasetUtility.getDbl(dsZCOBT303, i, dsZCOBT303.getColumnID(c), 0));
//								sum += b.doubleValue();
//							}
//						}
//					}
//				}
//
//				if ("KRW".equals(DatasetUtility.getString(dsZSDT1012, i, "WAERK")) 
//						||"JPY".equals(DatasetUtility.getString(dsZSDT1012, i, "WAERK"))) {
//					sum *= 100;
//				}
//				ZCOST = new BigDecimal(sum);
//				ZCOST = ZCOST.setScale(2, RoundingMode.DOWN);
//				param.setMANDT(MANDT);
//				param.setUUSER(UUSER);
//				param.setZBPNNR(DatasetUtility.getString(dsZCOBT303, i, "SONNR"));
//				param.setZCOST(ZCOST);
//				
//				service.createDao(session);
//				service.saveCost(param);
//			} catch( Exception e){
//				e.printStackTrace();
//			}
//		}
	}
	  
}
