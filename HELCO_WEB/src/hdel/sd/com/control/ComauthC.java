package hdel.sd.com.control;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.service.ComauthS;

@Controller
@RequestMapping("comauth")
public class ComauthC {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private ComauthS service;
	
	@RequestMapping(value = "find")
	public View SpecialAuth(MipParameterVO paramVO, Model model) {
		
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Dataset dsList = service.searchspcialAuth(paramVO);
			resultVO.addDataset(dsList);
		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");			
		}
		
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}
}
