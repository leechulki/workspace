package hdel.sd.com.control;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.Spras;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Coms019;
import hdel.sd.com.service.Coms019S;

@Controller
@RequestMapping("coms019")
public class Coms019C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Coms019S service;
	
	@RequestMapping(value="search")
	public View search(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");  
		Dataset dsList = paramVO.getDataset("ds_list");  

		try {
			Coms019 coms019 = new Coms019();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			DatasetUtil.moveDsRowToObj(dsCond, 0, coms019);
			coms019.setMandt(paramVO.getVariable("G_MANDT"));
			coms019.setSpras(new Spras(paramVO.getVariable("G_LANG")));
			List<Coms019> list = service.search(coms019);
			DatasetUtil.moveListToDs(list, dsList);

			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}	    

		return view;
	}
}