package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0240;
import hdel.sd.com.domain.Com0240ParamVO;
import hdel.sd.com.service.Com0240S;

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

@Controller
@RequestMapping("com0240")
public class Com0240C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0240S service;
	
	@RequestMapping(value="find")
	public View Com0240FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");  
		
		// OUTPUT DATASET DECLARE
		Dataset dsList 		= paramVO.getDataset("ds_list");	// UI로 return할 out dataset  
		//Dataset 	dsError = paramVO.getDataset("ds_error_buyr");	// UI로 return할 오류메세지 dataset  

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 			+"\n");
		System.out.print("\n@@@ paramVO.G_LANG	===>"+paramVO.getVariable("G_LANG") 			+"\n"); 
		System.out.print("\n@@@ dsCond.CODE	===>"+DatasetUtility.getString(dsCond,"CODE")+"\n");
		System.out.print("\n@@@ dsCond.CODE_NAME	===>"+DatasetUtility.getString(dsCond,"CODE_NAME")+"\n");   
		System.out.print("\n@@@ dsCond.CODE_FLAG	===>"+DatasetUtility.getString(dsCond,"CODE_FLAG")+"\n");
		//--------------------------------------------------------------------------------------------
		 
		try { 
 
			Com0240ParamVO param = new Com0240ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			String codeName = DatasetUtility.getString(dsCond,"CODE_NAME");
			String codeFlag = DatasetUtility.getString(dsCond,"CODE_FLAG");

			if (codeName == null) codeName = "";
			if (codeFlag == null) codeFlag   = "";

			// 파라메터SET
			param.setMandt	(paramVO.getVariable("G_MANDT"));  			   		// SAP CLIENT
			param.setCode(DatasetUtility.getString(dsCond,"CODE"));   			// 코드
			param.setCode_name(codeName); 	// 명
			param.setCode_flag(codeFlag); 	// 코드필터조건
			param.setLang(paramVO.getVariable("G_LANG"));

			// 해외부서의 경우 en으로 변경
			if ("B500".equals(paramVO.getVariable("G_SAP_USER_VBCD")) ||
				"B600".equals(paramVO.getVariable("G_SAP_USER_VBCD")) )		{
				param.setLang("en");
			}
			
			// 서비스CALL
			List<Com0240> list = service.find(param);  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				dsList.appendRow(); 
	    		
	    		// 컬럼SET
				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)
				{     
					dsList.setColumn(iRow, "CODE"		, list.get(iRow).getCode());		// 코드  
					dsList.setColumn(iRow, "CODE_NAME"	, list.get(iRow).getCode_name());	// 명  
					dsList.setColumn(iRow, "REF_NAME1"	, list.get(iRow).getRef_name1());	// 참조1
					dsList.setColumn(iRow, "REF_NAME2"	, list.get(iRow).getRef_name2());	// 참조2
					dsList.setColumn(iRow, "REF_NAME3"	, list.get(iRow).getRef_name3());	// 참조3
					dsList.setColumn(iRow, "REF_NAME4"	, list.get(iRow).getRef_name4());	// 참조4
					dsList.setColumn(iRow, "REF_NAME5"	, list.get(iRow).getRef_name5());	// 참조5
				} 
			} 
			// 데이터건수 INFO
			System.out.print("\n@@@ ds_list.getRowCount ===>" + dsList.getRowCount() + "\n"); 
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			model.addAttribute("resultVO", resultVO);     

			System.out.print("\n@@@ Com0240FindView SUCCESS!!!" + "\n"); 
			 
		} catch (Exception e) { 
			System.out.print("\n@@@ Com0240FindView Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	 
}
