package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0181;
import hdel.sd.ses.domain.Ses0181ParamVO;
import hdel.sd.ses.service.Ses0181S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0181")
public class Ses0181C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0181S service;
	
	@RequestMapping(value="find")
	public View Ses0181FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		dsList = listToDataset(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), dsList, dsCond);
		
		// dataset return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsList);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	private Dataset listToDataset(String sysid, String mandt, Dataset dsList, Dataset dsCond) {

		service.createDao(sqlSession.getSqlSessionBySysid(sysid));
		
		Ses0181ParamVO param = new Ses0181ParamVO();

		param.setMANDT(mandt);
		param.setVKBUR(DatasetUtility.getString(dsCond, "VKBUR"));
		param.setVKGRP(DatasetUtility.getString(dsCond, "VKGRP"));
		param.setZKUNNR(DatasetUtility.getString(dsCond, "ZKUNNR"));
		param.setQTNUM(DatasetUtility.getString(dsCond, "QTNUM"));
		List<Ses0181> listSes0181 = service.selectZSDT1046(param);

		dsList.deleteAll();

		CallSAPHdel.moveListToDs(dsList, Ses0181.class, listSes0181);
		dsList.setId("ds_list");
		
		return dsList;
	}

}
