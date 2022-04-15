package hdel.sd.ssa.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ssa.service.Ssa0070S;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ssa0070")
public class Ssa0070C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ssa0070S service;
	
	@RequestMapping(value="find")
	public View Ssa0070FindView(MipParameterVO paramVO, Model model) {
		
		Dataset ds_list  = paramVO.getDataset("ds_list");	// UI로 return할 out dataset  
		Dataset ds_error = paramVO.getDataset("ds_error");	// UI로 return할 오류메세지 dataset
		
		if ( ds_error == null ) {
			ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		}
		ds_error.deleteAll();
		
		MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
		
		try {
			
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
			service.createDao(session); // DAO생성
			
			ds_list = service.selectList(paramVO, model, session);
			
			resultVO.addDataset(ds_list);
			model.addAttribute("resultVO", resultVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		
		model.addAttribute("resultVO", resultVO);
		
		return view;
		
	}
	
	@RequestMapping(value="save")
	public View Ssa0070SaveView(MipParameterVO paramVO, Model model) {
		
		Dataset ds_error = paramVO.getDataset("ds_error");	// UI로 return할 오류메세지 dataset
		
		if ( ds_error == null ) {
			ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		}
		ds_error.deleteAll();
		
		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
		
		try {
			SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
			service.createDao(session); // DAO생성
			
			service.saveList(paramVO, model, session);
			
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		
		model.addAttribute("resultVO", resultVO);
		
		return view;
		
	}

}