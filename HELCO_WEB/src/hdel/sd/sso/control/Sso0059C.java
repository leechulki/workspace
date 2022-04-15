package hdel.sd.sso.control;

import org.apache.log4j.Logger;
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
import hdel.sd.sso.service.Sso0059S;

@Controller
@RequestMapping("sso0059")
public class Sso0059C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Sso0059S service;

	@RequestMapping(value = "searchItemList")
	public View searchItemList(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try { 
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Dataset dsList = service.searchItemList(paramVO);

			resultVO.addDataset(dsList);
		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	@RequestMapping(value = "searchItemChar")
	public View searchItemChar(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try { 
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Dataset dsList = service.searchItemChar(paramVO);

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