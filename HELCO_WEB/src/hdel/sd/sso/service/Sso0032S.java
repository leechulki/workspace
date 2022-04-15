package hdel.sd.sso.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.ZBCS_TIMESTAMP;
import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0032D;
import hdel.sd.sso.domain.Sso0032;
import hdel.sd.sso.domain.Sso0032ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

@Service
public class Sso0032S extends SrmService {
	
	private Sso0032D sso0032Dao;

	public void createDao(SqlSession sqlSession) {
		sso0032Dao = sqlSession.getMapper(Sso0032D.class);
	}

	public Dataset find(MipParameterVO paramVO) {
		Sso0032ParamVO param = new Sso0032ParamVO();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMANDT(paramVO.getVariable("G_MANDT"));

		List<Sso0032> spdrftList = sso0032Dao.find(param);
		Dataset dsList = paramVO.getDataset("ds_list");

		DatasetUtil.moveListToDs(spdrftList, dsList);

		return dsList;
	}

	public void save(MipParameterVO paramVO) throws Exception {

		Sso0032 param = new Sso0032();
		ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
		tstmp.setErnam(paramVO.getVariable("G_USER_ID"));
		tstmp.setAenam(paramVO.getVariable("G_USER_ID"));
		param.setTstmp(tstmp);
		param.setMANDT(paramVO.getVariable("G_MANDT"));
		
		Dataset dsList = paramVO.getDataset("ds_list");
		DatasetUtil.moveDsRowToObj(dsList, 0, param);
		
		if("SAVE".equals(paramVO.getVariable("CMDCD")) ) {
			sso0032Dao.saveZsdt0219(param);
		} else {
			sso0032Dao.deleteZsdt0219(param);			
		}

	}	
}
