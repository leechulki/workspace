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
import hdel.sd.sso.service.Sso0058S;

@Controller
@RequestMapping("sso0058")
public class Sso0058C {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Sso0058S sso0058S;

	@RequestMapping(value="getHogiStatus")
	public View getHogiStatus(MipParameterVO paramVO, Model model) {
		Dataset dsError = paramVO.getDataset("ds_error");
		Dataset dsLock = paramVO.getDataset("ds_lock");

		MipResultVO resultVO = new MipResultVO();
		sso0058S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			sso0058S.getHogiStatus(paramVO, resultVO);
			//대금청구여부 고려!!
//		} catch(IllegalAccessException iae) {
//			dsLock.appendRow();
//			dsLock.setColumn(dsLock.getRowCount()-1, "locker", iae.getMessage());
		} catch(Exception e) {
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			resultVO.addDataset(dsError);
			resultVO.addDataset(dsLock);
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}

	@RequestMapping(value="saveChangedHogi")
	public View saveChangedHogi(MipParameterVO paramVO, Model model) {
		Dataset ds_error = paramVO.getDataset("ds_error");

		MipResultVO resultVO = new MipResultVO();
		sso0058S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			sso0058S.saveChangedHogi(paramVO, resultVO);
		} catch(Exception e) {
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			resultVO.addDataset(ds_error);
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}
}