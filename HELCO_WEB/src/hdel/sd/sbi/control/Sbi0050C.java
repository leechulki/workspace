package hdel.sd.sbi.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;
import com.sap.domain.Spras;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDTDUTYEXCPTD;
import hdel.lib.service.ZSDTDUTYEXCPTDS;
import hdel.sd.sbi.service.Sbi0050S;

@Controller
@RequestMapping("sbi0050")
public class Sbi0050C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Sbi0050S sbi0050S;
	@Autowired
	private ZSDTDUTYEXCPTDS dutyExcptDService;

	/**
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "searchDutyExcption")
	public View searchAgentList(MipParameterVO paramVO, Model model) {
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try {
			dutyExcptDService.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Dataset dsCond = paramVO.getDataset("ds_cond");
			List<ZSDTDUTYEXCPTD> lstDutyExcptD = new ArrayList<ZSDTDUTYEXCPTD>();
			Integer ordseq = dsCond.getColumnAsInteger(0, "ordseq") == null ? 0 : dsCond.getColumnAsInteger(0, "ordseq");
			lstDutyExcptD = dutyExcptDService.selectDutyExcptionAll(paramVO.getVariable("G_MANDT"), 
					new OrderNo(dsCond.getColumnAsString(0, "ordno")), ordseq, new OrderItem(dsCond.getColumnAsString(0, "orditem")), new Spras(paramVO.getVariable("G_LANG")));

			DatasetUtil.moveListToDs(lstDutyExcptD, dsList);
			
			resultVO.addDataset(dsList);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="modifyDutyException")
	public View modifyDutyException(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();

		try { 
			sbi0050S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			sbi0050S.modifyDutyException(paramVO);
			
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="searchOrderItem")
	public View searchOrderItem(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		OrderItem orditem = new OrderItem();

		MipResultVO resultVO = new MipResultVO();

		try { 
			sbi0050S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			orditem = sbi0050S.searchOrderItem(paramVO);
			
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		resultVO.addVariable("orditem", orditem.toString());
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	@RequestMapping(value="searchBlockName")
	public View searchBlockName(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		String blocknm="";

		MipResultVO resultVO = new MipResultVO();

		try { 
			sbi0050S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			blocknm = sbi0050S.searchBlockName(paramVO);
			
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		resultVO.addVariable("blocknm", blocknm);
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}
}