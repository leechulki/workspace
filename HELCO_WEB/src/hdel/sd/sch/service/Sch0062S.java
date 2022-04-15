package hdel.sd.sch.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.sch.dao.Sch0062D;
import hdel.sd.sch.domain.Sch0062;
import hdel.sd.sch.domain.Sch0062ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tobesoft.platform.data.Dataset;

@Service
public class Sch0062S extends SrmService {

	private Sch0062D sch0062D;

	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		sch0062D = sqlSession.getMapper(Sch0062D.class);
	}

	public List<Sch0062> selectZSDT0045(Sch0062ParamVO param) {
		return sch0062D.selectZSDT0045(param);
	}

	public void mergeZSDT0045 (MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsListSave = paramVO.getDatasetList().getDataset("ds_list_save");

		try {
			// DAO持失
			createDao(session);

			Sch0062 param = new Sch0062();

			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setPSPID(dsListSave.getColumnAsString(0,"PSPID"));
			param.setMEDAT(dsListSave.getColumnAsString(0,"MEDAT"));
			param.setMEKIND(dsListSave.getColumnAsString(0,"MEKIND"));
			param.setDEBTR(dsListSave.getColumnAsString(0,"DEBTR"));
			param.setDEBTRT(dsListSave.getColumnAsString(0,"DEBTRT"));
			param.setERDAT(dsListSave.getColumnAsString(0,"ERDAT"));
			param.setERZZT(dsListSave.getColumnAsString(0,"ERZZT"));
			param.setERNAM(dsListSave.getColumnAsString(0,"ERNAM"));
			param.setAEDAT(dsListSave.getColumnAsString(0,"AEDAT"));
			param.setAEZZT(dsListSave.getColumnAsString(0,"AEZZT"));
			param.setAENAM(dsListSave.getColumnAsString(0,"AENAM"));

			sch0062D.mergeZSDT0045(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteZSDT0045 (MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsListSave = paramVO.getDatasetList().getDataset("ds_list_save");

		try {
			// DAO持失
			createDao(session);

			Sch0062 param = new Sch0062();

			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setPSPID(dsListSave.getColumnAsString(0,"PSPID"));
			param.setERDAT(dsListSave.getColumnAsString(0,"ERDAT"));
			param.setERZZT(dsListSave.getColumnAsString(0,"ERZZT"));

			sch0062D.deleteZSDT0045(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
