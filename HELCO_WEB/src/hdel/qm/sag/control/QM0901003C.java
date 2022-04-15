package hdel.qm.sag.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.qm.sag.domain.QM0901003;
import hdel.qm.sag.domain.QM0901003ParamVO;
import hdel.qm.sag.service.QM0901003S;

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

@Controller
@RequestMapping("qm0901003")
public class QM0901003C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private QM0901003S service;
	
	@RequestMapping(value="find")
	public View QM0901003FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_sman");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list_sman");	// UI로 return할 out dataset  
		 
		try { 
 
			QM0901003ParamVO param = new QM0901003ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setSman_cd(DatasetUtility.getString(dsCond,"SMAN_CD"));		// 담당자코드
			param.setSman_nm(DatasetUtility.getString(dsCond,"SMAN_NM"));		// 담당자명  
			
			// 서비스CALL
			List<QM0901003> list = service.find(param);  
			
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// 행추가
				dsList.appendRow();  
	    		// 컬럼SET 
				dsList.setColumn(iRow, "SMAN_CD"	, list.get(iRow).getSman_cd());			// 담당자코드  
				dsList.setColumn(iRow, "SMAN_NM"	, list.get(iRow).getSman_nm());			// 담당자명   
				dsList.setColumn(iRow, "SMAN_MAIL"	, list.get(iRow).getSman_mail());			// 담당자메일
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
