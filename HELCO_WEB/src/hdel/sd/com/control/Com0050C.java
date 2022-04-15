package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;  
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.service.ComboS;

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
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

import org.apache.log4j.Logger; 
 
/**
 * 공통코드 목록 조회 (Com0050C) Control
 * @Comment 
 *     	1. View find ( 공통코드목록 조회 )
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Controller
@RequestMapping("com0050")
public class Com0050C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private ComboS service;
	
	// 2012.05.30 : GRY : 추가 : 공통코드정보
	@RequestMapping(value="find") 
	public View Com0050FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_cond_dd07t = paramVO.getDataset("ds_cond_dd07t");
		
		// INPUT INFO
		System.out.print("\n@@@ ds_cond_dd07t.DOMNAME	===>"+DatasetUtility.getString(ds_cond_dd07t,"DOMNAME"		)+"\n");
		System.out.print("\n@@@ ds_cond_dd07t.DOMVALUE_L===>"+DatasetUtility.getString(ds_cond_dd07t,"DOMVALUE_L"	)+"\n");
		
		// OUTPUT DATASET GET
		Dataset ds_list_dd07t = paramVO.getDataset("ds_list_dd07t");
		ds_list_dd07t.deleteAll();
		
		try { 
			 
			// Connection
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
			
			// PARAM SET
			ComboParamVO param = new ComboParamVO();
			//param.setMandt(paramVO.getVariable("G_MANDT"));							// 채널
			param.setDomname(DatasetUtility.getString(ds_cond_dd07t,"DOMNAME"));   		// 도메인명
			param.setDomvalue_l(DatasetUtility.getString(ds_cond_dd07t,"DOMVALUE_L")); 	// 코드 
	
			List<ComboVO> listCombo = null; 
			 
			listCombo = service.selectListDD07T(param); 
	
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<listCombo.size();iRow++)
			{   
				// 행추가
				ds_list_dd07t.appendRow(); 
	    		
	    		// 컬럼SET 
				ds_list_dd07t.setColumn(iRow, "CODE"	 , listCombo.get(iRow).getCode());		// 코드  
				ds_list_dd07t.setColumn(iRow, "CODE_NAME", listCombo.get(iRow).getCodeName());	// 코드명  
			}  
	
			logger.debug("\n@@@ Com0050FindView SUCCESS!!!" + "\n"); 
			
			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			ds_list_dd07t.setId	("ds_list_dd07t");  
			resultVO.addDataset(ds_list_dd07t);
			model.addAttribute("resultVO", resultVO);  
			
		} catch (Exception e) { 
			logger.debug("\n@@@ Com0050FindView Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	
		
		return view;
	}
	 
}
