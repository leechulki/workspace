package hdel.sd.sch.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.sch.dao.Sch0063D;
import hdel.sd.sch.domain.Sch0063;
import hdel.sd.sch.domain.Sch0063ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tobesoft.platform.data.Dataset;

@Service
public class Sch0063S extends SrmService {

	private Sch0063D sch0063D;

	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		sch0063D = sqlSession.getMapper(Sch0063D.class);
	}

	public List<Sch0063> selectZFIT1015(Sch0063ParamVO param) {
		return sch0063D.selectZFIT1015(param);
	}

	public void mergeZFIT1015 (MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsListSave = paramVO.getDatasetList().getDataset("ds_list_save");

		try {
			// DAO持失
			createDao(session);

			Sch0063 param = new Sch0063();

			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setKUNNR(dsListSave.getColumnAsString(0,"KUNNR"));
			param.setKIDNO(dsListSave.getColumnAsString(0,"KIDNO"));
			param.setSERNO(dsListSave.getColumnAsString(0,"SERNO"));
			param.setINGTEXT(dsListSave.getColumnAsString(0,"INGTEXT"));
			param.setUNAME(dsListSave.getColumnAsString(0,"UNAME"));
			param.setDATUM(dsListSave.getColumnAsString(0,"DATUM"));
			param.setCHAN_UNAME(dsListSave.getColumnAsString(0,"CHAN_UNAME"));
			param.setCHAN_DATUM(dsListSave.getColumnAsString(0,"CHAN_DATUM"));
			param.setDISGBN(dsListSave.getColumnAsString(0,"DISGBN"));
			
			sch0063D.mergeZFIT1015(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteZFIT1015 (MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsListSave = paramVO.getDatasetList().getDataset("ds_list_save");

		try {
			// DAO持失
			createDao(session);

			Sch0063 param = new Sch0063();

			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setKIDNO(dsListSave.getColumnAsString(0,"KIDNO"));
			param.setKUNNR(dsListSave.getColumnAsString(0,"KUNNR"));
			param.setSERNO(dsListSave.getColumnAsString(0,"SERNO"));
			
			sch0063D.deleteZFIT1015(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
