package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0250ParamVO;
import hdel.sd.com.domain.Com0250;
import hdel.sd.com.service.Com0250S;

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
@RequestMapping("com0250")
public class Com0250C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0250S service;
	
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
 
			Com0250ParamVO param = new Com0250ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			String qtnum = DatasetUtility.getString(dsCond,"QTNUM");
			String qtser = DatasetUtility.getString(dsCond,"QTSER");
			String fr_qtdat = DatasetUtility.getString(dsCond,"FR_QTDAT");
			String to_qtdat = DatasetUtility.getString(dsCond,"TO_QTDAT");
			String kunnr = DatasetUtility.getString(dsCond,"KUNNR");
			String gsnam    = DatasetUtility.getString(dsCond,"GSNAM");
			String gubun    = DatasetUtility.getString(dsCond,"GUBUN");

			if (qtnum == null) qtnum = "";
			if (qtser   == null) qtser   = "";
			if (fr_qtdat   == null) fr_qtdat   = "";
			if (to_qtdat   == null) to_qtdat   = "";
			if (kunnr   == null) kunnr   = "";
			if (gsnam   == null) gsnam   = "";
			if (gubun   == null) gubun   = "";

			// 파라메터SET
			param.setMandt	(paramVO.getVariable("G_MANDT"));  			   		// SAP CLIENT
			param.setQtnum(qtnum);   			// 견적번호
			param.setQtser(qtser); 					// 견적차수
			param.setFr_qtdat(fr_qtdat); 					// 견적일자
			param.setTo_qtdat(to_qtdat);				// 견적일자
			param.setKunnr(kunnr);				// 고객번호
			param.setGsnam(gsnam); 				// 공사명
			param.setGubun(gubun);

			// 서비스CALL			
			List<Com0250> list = service.find(param);  
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				dsList.appendRow(); 
	    		
	    		// 컬럼SET
				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)
				{     
					dsList.setColumn(iRow, "QTNUM"	, list.get(iRow).getQtnum());
					dsList.setColumn(iRow, "QTSER"	, list.get(iRow).getQtser());
					//dsList.setColumn(iRow, "QTDAT"	, list.get(iRow).getQtdat());
					dsList.setColumn(iRow, "KUNNR"	, list.get(iRow).getKunnr());
					dsList.setColumn(iRow, "GSNAM"	, list.get(iRow).getGsnam());
				} 
			} 
			
			// 데이터건수 INFO
			System.out.print("\n@@@ ds_list.getRowCount ===>" + dsList.getRowCount() + "\n"); 
			
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
