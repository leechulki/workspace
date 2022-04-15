package hdel.sd.set.control;

import java.util.ArrayList;
import java.util.List;

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
import hdel.sd.set.domain.Set0010;
import hdel.sd.set.service.Set0010S;

@Controller
@RequestMapping("set0010")
public class Set0010C {
	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Set0010S set0010S;

	@RequestMapping(value = "searchCostSection")
	public View searchCostSection(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try {
			set0010S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Dataset dsCrtsc = set0010S.searchCostSection(paramVO);
			resultVO.addDataset(dsCrtsc);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value = "searchMatrixMap")
	public View searchMatrixMap(MipParameterVO paramVO, Model model) {
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsSummary = paramVO.getDataset("ds_summary");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try {
			set0010S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			set0010S.searchMatrixMap(paramVO);

			resultVO.addDataset(paramVO.getDataset("ds_crtsc"));
			resultVO.addDataset(dsList);
			resultVO.addDataset(dsSummary);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}
}