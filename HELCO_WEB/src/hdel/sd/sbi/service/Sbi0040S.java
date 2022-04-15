package hdel.sd.sbi.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDT0168D;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.ZSDT0168;
import hdel.lib.service.SrmService;
import hdel.sd.sbi.dao.Sbi0040D;
import hdel.sd.sbi.domain.Sbi0040;

@Service
public class Sbi0040S extends SrmService {

	private Sbi0040D sbi0040Dao;
	private ZSDT0168D zsdt0168Dao;

	public void createDao(SqlSession sqlSession) {
		sbi0040Dao = sqlSession.getMapper(Sbi0040D.class);
		zsdt0168Dao = sqlSession.getMapper(hdel.lib.dao.ZSDT0168D.class);
	}

	public Dataset searchAgentList(MipParameterVO paramVO) {
		Sbi0040 param = new Sbi0040();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<Sbi0040> agentList = sbi0040Dao.searchAgentList(param);
		for(Sbi0040 agent : agentList) {
			if(agent.getFirstErdat().equals("00000000"))
				agent.setEditable(true);
			else
				agent.setEditable(false);
		}
		Dataset dsList = paramVO.getDataset("ds_list");
		// if(agentList != null) {
		// for (Sbi0040 sbi0040 : agentList) {
		// }
		// }

		DatasetUtil.moveListToDs(agentList, dsList);

		return dsList;
	}

	public void mergeAgentEntryDate(MipParameterVO paramVO) throws Exception {
		ZSDT0168 param = new ZSDT0168();
//		List<Sbi0040> entryList = new ArrayList<Sbi0040>();
//		param.setMandt(paramVO.getVariable("G_MANDT"));
		Dataset dsList = paramVO.getDataset("ds_list");
		for(int dsRowIdx=0; dsRowIdx < dsList.getRowCount(); dsRowIdx++) {
			DatasetUtil.moveDsRowToObj(dsList, dsRowIdx, param);
//			entryList.add(param);
			zsdt0168Dao.merge(param);
		}

	}
}