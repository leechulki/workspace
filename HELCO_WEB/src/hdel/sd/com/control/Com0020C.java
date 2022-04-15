package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0020;
import hdel.sd.com.domain.Com0020ParamVO;
import hdel.sd.com.service.Com0020S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0020")
public class Com0020C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0020S service;
	
	@RequestMapping(value="find")
	public View Com0020FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_lifnr");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_list_lifnr");	// UI로 return할 out dataset  
		 
		try { 
 
			Com0020ParamVO param = new Com0020ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setLifnr(DatasetUtility.getString(dsCond,"LIFNR"));		// 협력업체코드
			param.setName1(DatasetUtility.getString(dsCond,"NAME1"));		// 협력업체명  
			
			// 서비스CALL
			List<Com0020> list = service.find(param);  
			
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				dsList.appendRow();  
	    		// 컬럼SET 
				dsList.setColumn(iRow, "LIFNR"	, list.get(iRow).getLifnr());			// 거래처코드  
				dsList.setColumn(iRow, "NAME1"	, list.get(iRow).getName1());			// 거래처명   
			} 
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
}
