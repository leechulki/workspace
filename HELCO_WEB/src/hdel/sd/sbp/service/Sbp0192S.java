package hdel.sd.sbp.service;


import java.util.List;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.sbp.dao.Sbp0192D;
import hdel.sd.sbp.domain.Sbp0192ParamVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Service
public class Sbp0192S extends SrmService {

	@Autowired
	private AutoSoNumberS autoSoService;
	 
	private Sbp0192D sbp0192D;

	@Override
	public void createDao(SqlSession sqlSession) {
		sbp0192D = sqlSession.getMapper(Sbp0192D.class);
	}

	public void updateSORLT(MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		try {
			// DAO持失
			createDao(session);

			Sbp0192ParamVO param = new Sbp0192ParamVO();
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setSORLT(dsCond.getColumnAsString(0, "SORLT"));
			param.setSONNR(dsCond.getColumnAsString(0, "SONNR"));
			param.setZPYM(dsCond.getColumnAsString(0, "ZPYM"));

			sbp0192D.updateSORLT(param);
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}

	public void insertSORLT(MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsCond = paramVO.getDatasetList().getDataset("ds_cond");

		try {
			// DAO持失
			createDao(session);
			autoSoService.createDao(session);

			AutoNumberParamVO sonnrParam = new AutoNumberParamVO();
			sonnrParam.setDeptFlag(DatasetUtility.getString(dsCond, "AUART"));
			sonnrParam.setSoQtFlag("1");
			sonnrParam.setMandt(paramVO.getVariable("G_MANDT"));
			List<AutoNumberVO> sonnrList = autoSoService.AutoSoNumber(sonnrParam);

			Sbp0192ParamVO param = new Sbp0192ParamVO();
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setSORLT(dsCond.getColumnAsString(0, "SORLT"));
			param.setSONNR(dsCond.getColumnAsString(0, "SONNR"));
			param.setZPYM(dsCond.getColumnAsString(0, "ZPYM"));
			param.setNEWSONNR(sonnrList.get(0).getNumber());
			param.setNEWZPYM(dsCond.getColumnAsString(0, "NEWZPYM"));
			param.setDATE(DateTime.getDateString());
			param.setTIME(DateTime.getShortTimeString());
			param.setUSER(paramVO.getVariable("G_USER_ID"));

			sbp0192D.updateSORLT(param);
			sbp0192D.insertSORLT1(param);
			sbp0192D.insertSORLT2(param);
			sbp0192D.insertSORLT3(param);
			sbp0192D.insertSORLT4(param);
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
	
}
