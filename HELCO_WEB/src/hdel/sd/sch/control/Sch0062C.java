package hdel.sd.sch.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sch.domain.Sch0062;
import hdel.sd.sch.domain.Sch0062ParamVO;
import hdel.sd.sch.service.Sch0062S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sch0062")
public class Sch0062C {

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Sch0062S service;

	@RequestMapping(value="find")
	public View main(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		List<Sch0062> listSch0062 = null;

		Sch0062ParamVO param = new Sch0062ParamVO();
		try {
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setPSPID(dsCond.getColumnAsString(0,"PSPID"));
			
			listSch0062 = service.selectZSDT0045(param);

			dsList.deleteAll();

			CallSAPHdel.moveListToDs(dsList, Sch0062.class, listSch0062);
			dsList.setId("ds_list");

			// dataset¿ª return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	@RequestMapping(value="save")
	public View save(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsListSave = paramVO.getDatasetList().getDataset("ds_list_save");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		List<Sch0062> listSch0062 = null;

		Sch0062ParamVO param = new Sch0062ParamVO();
		try {
			service.mergeZSDT0045(paramVO, model, session);

			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setPSPID(dsCond.getColumnAsString(0,"PSPID"));
			
			listSch0062 = service.selectZSDT0045(param);

			dsList.deleteAll();

			CallSAPHdel.moveListToDs(dsList, Sch0062.class, listSch0062);
			dsList.setId("ds_list");

			// dataset¿ª return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	@RequestMapping(value="delete")
	public View delete(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsListSave = paramVO.getDatasetList().getDataset("ds_list_save");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		List<Sch0062> listSch0062 = null;

		Sch0062ParamVO param = new Sch0062ParamVO();
		try {
			service.deleteZSDT0045(paramVO, model, session);

			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setPSPID(dsCond.getColumnAsString(0,"PSPID"));
			
			listSch0062 = service.selectZSDT0045(param);

			dsList.deleteAll();

			CallSAPHdel.moveListToDs(dsList, Sch0062.class, listSch0062);
			dsList.setId("ds_list");

			// dataset¿ª return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

}
