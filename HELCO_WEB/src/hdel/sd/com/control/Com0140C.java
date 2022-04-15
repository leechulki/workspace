package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0140;
import hdel.sd.com.domain.Com0140ParamVO;
import hdel.sd.com.service.Com0140S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0140")
public class Com0140C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0140S service;
	
	@RequestMapping(value="find")
	public View Com0140FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list 		= paramVO.getDataset("ds_list");	// UI로 return할 out dataset  
		 
		try { 
 
			Com0140ParamVO param = new Com0140ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	        +"\n");  
			System.out.print("\n@@@ dsCond.LANG	===>"+DatasetUtility.getString(dsCond,"LANG")	+"\n");
			System.out.print("\n@@@ dsCond.WHERE	===>"+DatasetUtility.getString(dsCond,"WHERE")	+"\n");
			
			// 파라메터SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  			
			param.setLang(DatasetUtility.getString(dsCond,"LANG"));		
			param.setWhere(DatasetUtility.getString(dsCond,"WHERE"));

			// 서비스CALL
			List<Com0140> list = service.find(param);  
			
			ds_list.deleteAll();
			
			// 호출결과(list)를 데이타셋(ds_list)에 복사
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				ds_list.appendRow(); 
	    		
	    		// 컬럼SET
				for (int iCol=0;iCol<ds_list.getColumnCount();iCol++)
				{    
					if (ds_list.getColumnID(iCol).equals("CODE")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getCode());			// 코드 
					} else if (ds_list.getColumnID(iCol).equals("CODE_NAME")) {
						ds_list.setColumn(iRow, iCol	, list.get(iRow).getCodeName());			// 명 
					}
				} 
			} 
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_list); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	 
}
