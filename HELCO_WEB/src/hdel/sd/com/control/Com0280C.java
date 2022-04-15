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
import hdel.sd.com.domain.Com0280;
import hdel.sd.com.service.Com0280S;

@Controller
@RequestMapping("com0280")
public class Com0280C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0280S service;
	
	@RequestMapping(value="find")
	public View Com0280FindView(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");  
		Dataset dsList = paramVO.getDataset("ds_list");  
		try {
 
			Com0280 param = new Com0280();
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setSpras(new Spras(paramVO.getVariable("G_LANG")).getValue());
			DatasetUtil.moveDsRowToObj(dsCond, 0, param);

			List<Com0280> list = service.find(param);
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