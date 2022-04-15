package hdel.lib.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0095;
import hdel.lib.service.ZSDT0095S;

@Controller
@RequestMapping("zsdt0095")
public class ZSDT0095C {
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private ZSDT0095S zsdt0095s;

	@RequestMapping(value="checkLock")
	public View checkLock(MipParameterVO paramVO, Model model) {
		Dataset dsError = paramVO.getDataset("ds_error");
		Dataset dsLock = paramVO.getDataset("ds_lock");
		MipResultVO resultVO = new MipResultVO();
		zsdt0095s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		try {
			dsLock.deleteAll();
			dsError.deleteAll();

			ZSDT0095 zsdt0095 = zsdt0095s.select(paramVO.getVariable("G_MANDT"), paramVO.getVariable("p_vbeln"));
			if(zsdt0095.getLock())
				throw new IllegalAccessException(zsdt0095.getTstmp().getAenam() + zsdt0095.getUsername());
		} catch(IllegalAccessException iae) {
			dsLock.appendRow();
			dsLock.setColumn(dsLock.getRowCount()-1, "locker", iae.getMessage());
		} catch(Exception e) {
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			resultVO.addDataset(dsError);
			resultVO.addDataset(dsLock);
			model.addAttribute("resultVO", resultVO);
		}

		return view;
	}

	@RequestMapping(value="lock")
	public View lock(MipParameterVO paramVO, Model model) {
		setLock(paramVO, model, true);

		return view;
	}

	@RequestMapping(value="unlock")
	public View unlock(MipParameterVO paramVO, Model model) {
		setLock(paramVO, model, false);

		return view;
	}

	private void setLock(MipParameterVO paramVO, Model model, Boolean lock) {
		Dataset dsError = paramVO.getDataset("ds_error");
		MipResultVO resultVO = new MipResultVO();
		zsdt0095s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		try {
			zsdt0095s.lock(paramVO.getVariable("G_MANDT"), paramVO.getVariable("p_vbeln"), lock, paramVO.getVariable("G_USER_ID"));
		} catch(Exception e) {
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			resultVO.addDataset(dsError);
			model.addAttribute("resultVO", resultVO);
		}
	}
}