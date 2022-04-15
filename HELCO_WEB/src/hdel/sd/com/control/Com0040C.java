package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;
import hdel.sd.com.service.Com0040S;

import java.io.IOException;
import java.util.List;
	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility; 

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger; 


/**
 * �ŷ������ ��ȸ (Com0040C) Control
 * @Comment 
 *     	1. View find ( �ŷ������ ��ȸ )
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("com0040")
public class Com0040C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0040S service;
	
	/* �ŷ��� ��� ��ȸ */
	@RequestMapping(value="find")
	public View Com0040FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond_buyr");  
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list		= paramVO.getDataset("ds_list_buyr");			// UI�� return�� out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset  
					
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		logger.debug("@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT")); 	 
		logger.debug("@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG"));  
		logger.debug("@@@ ds_cond.BUYR_CD	===>"+DatasetUtility.getString(ds_cond,"BUYR_CD")); 
		logger.debug("@@@ ds_cond.BUYR_NM	===>"+DatasetUtility.getString(ds_cond,"BUYR_NM"));
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Com0040ParamVO param = new Com0040ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
			
			// �Ķ����SET 
			param.setMandt	(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT 
			param.setBuyr_cd(DatasetUtility.getString(ds_cond,"BUYR_CD"));	// �ŷ�ó�ڵ�
			param.setBuyr_nm(DatasetUtility.getString(ds_cond,"BUYR_NM"));	// �ŷ�ó�� 

			// �� ���� Dataǥ��
			// 2012.11.14 �ŷ���üũ ���� ������ ���� �ּ�ó�� Start SMJ
			if ("KO".equals( paramVO.getVariable("G_LANG").toUpperCase() ) )	{
				param.setLang("3");
			}else	{
				param.setLang("E");
			}

			// ����CALL
			List<Com0040> list = service.find(param);  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ���� 
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				ds_list.appendRow();   
	    		// �÷�SET 
				ds_list.setColumn(iRow, "BUYR_CD"		, list.get(iRow).getBuyr_cd());			// �ŷ�ó�ڵ� (6�ڸ�)
				ds_list.setColumn(iRow, "BUYR_NM"		, list.get(iRow).getBuyr_nm());			// �ŷ�ó�� 
				ds_list.setColumn(iRow, "BUYR_NM_SORT"	, list.get(iRow).getBuyr_nm_sort());	// �ŷ�ó�� ���� 
				ds_list.setColumn(iRow, "BUYR_ADDR"		, list.get(iRow).getBuyr_addr());		// �ŷ�ó�ּ�  
				ds_list.setColumn(iRow, "BUYR_CD_DB"	, list.get(iRow).getBuyr_cd_db());		// �ŷ�ó�ڵ� (10�ڸ�)
				ds_list.setColumn(iRow, "LAND1"			, list.get(iRow).getLand1());			// �����ڵ�  
				ds_list.setColumn(iRow, "LAND1_NM"		, list.get(iRow).getLand1_nm());		// ������
				ds_list.setColumn(iRow, "ZAREA"			, list.get(iRow).getZarea());			// �ǿ��ڵ�  
				ds_list.setColumn(iRow, "ZAREA_NM"		, list.get(iRow).getZarea_nm());		// �ǿ���
				ds_list.setColumn(iRow, "OPENDT"		, list.get(iRow).getOpendt());		// E-GIS���¿���
			} 
		
			logger.debug("@@@ Com0040FindView SUCCESS!!!"); 
			 
		} catch (Exception e) { 
			logger.debug("@@@ Com0040FindView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		
		// RFC ��� dataset�� return 
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount()); 
		logger.debug("@@@ ds_error.getRowCount ===>" + ds_error.getRowCount());
		MipResultVO resultVO = new MipResultVO(); 
		ds_list.setId	("ds_list_buyr");  
		resultVO.addDataset(ds_list); 
		ds_error.setId	("ds_error_buyr");  
		resultVO.addDataset(ds_error); 
		model.addAttribute("resultVO", resultVO);   
		
		return view;
		
	}
	 
}
