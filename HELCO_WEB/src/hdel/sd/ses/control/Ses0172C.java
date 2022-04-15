package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0172;
import hdel.sd.ses.service.Ses0172S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0172")
public class Ses0172C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0172S service;
	
	/*------------------------------------------------------------
	 *  기술검토결과등록
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0172SaveView(MipParameterVO paramVO, Model model) { 
 
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));


		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.save(paramVO, model, session);

			} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}

}
