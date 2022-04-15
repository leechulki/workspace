package hdel.sd.ses.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0250S;

@Controller
@RequestMapping("ses0250")
public class Ses0250C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0250S service;

	@RequestMapping(value="searchOrder")
	public View searchOrder(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");  

		MipResultVO resultVO = new MipResultVO();
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			dsList = service.searchOrder(paramVO, dsCond, dsList);
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}	    

		return view;
	}

	@RequestMapping(value="searchCharacteristics")
	public View searchCharacteristics(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");  

		MipResultVO resultVO = new MipResultVO();
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			dsList = service.searchCharacteristics(paramVO, dsCond, dsList);
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}	    

		return view;
	}
}