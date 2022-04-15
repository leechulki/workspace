package hdel.sd.sso.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDT0168D;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0212D;
import hdel.sd.sso.domain.Sso0212;

@Service
public class Sso0212S extends SrmService {

	private Sso0212D sso0212Dao;

	public void createDao(SqlSession sqlSession) {
		sso0212Dao = sqlSession.getMapper(Sso0212D.class);
	}

	public Dataset searchAgentCommission(MipParameterVO paramVO) throws Exception {
		Sso0212 param = new Sso0212();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<Sso0212> commissionList = sso0212Dao.searchAgentCommission(param);
		Dataset dsList = paramVO.getDataset("ds_output");
		for(Sso0212 commission:commissionList) {
			if(commission.getStake() != null)
				commission.setExists128(true);
		}
		DatasetUtil.moveListToDs(commissionList, dsList);

		return dsList;
	}
	public void deleteAgentCommission(MipParameterVO paramVO) throws Exception {
		Sso0212 param = new Sso0212();
		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));
		sso0212Dao.deleteAgentCommission(param);
	}
	public Dataset merge(MipParameterVO paramVO) throws Exception {
		Sso0212 param = new Sso0212();

		List<Sso0212> lstSso0212 = new ArrayList<Sso0212>();
		Dataset dsOutput = paramVO.getDataset("ds_output");

		for(int i=0; i < dsOutput.getRowCount(); i++) {
			Sso0212 sso0212 = new Sso0212();
			DatasetUtil.moveDsRowToObj(dsOutput, i, sso0212);
			sso0212.setMandt(paramVO.getVariable("G_MANDT"));
			sso0212.getTstmp().setTimeStamp(paramVO.getVariable("G_USER_ID"));
			lstSso0212.add(sso0212);
		}
		sso0212Dao.merge(lstSso0212);

		return dsOutput;
	}
}