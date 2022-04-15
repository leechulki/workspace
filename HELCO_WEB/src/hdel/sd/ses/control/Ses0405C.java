package hdel.sd.ses.control;

import java.math.BigDecimal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.DateUtil;
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0167;
import hdel.sd.ses.domain.Ses0405;
import hdel.sd.ses.service.Ses0405S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0405")
public class Ses0405C {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0405S service;
	
	@RequestMapping(value="searchAnnualUnitCost")
	public View searchAnnualUnitCost(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try { 
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Dataset dsList = service.searchAnnualUnitCost(paramVO);
			Dataset dsReport = paramVO.getDataset("ds_report");
			service.mergeIntoReport(dsReport, dsList);

			resultVO.addDataset(dsList);
			resultVO.addDataset(dsReport);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	@RequestMapping(value="searchAUCKunnr")
	public View searchAUCKunnr(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try { 
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Dataset dsList = service.searchAUCKunnr(paramVO);

			resultVO.addDataset(dsList);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	@RequestMapping(value="updateAUCKunnr")
	public View updateAUCKunnr(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try { 
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			service.updateAUCKunnr(paramVO);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
}
