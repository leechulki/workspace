package hdel.sd.ses.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.Spras;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0250D;
import hdel.sd.ses.domain.Ses0250;
import hdel.sd.ses.domain.Ses0251;

@Service
public class Ses0250S extends SrmService {

	private Ses0250D ses0250Dao;
	
	public void createDao(SqlSession sqlSession) {
		ses0250Dao = sqlSession.getMapper(Ses0250D.class);
	}

	public Dataset searchOrder(MipParameterVO paramVO, Dataset dsCond, Dataset dsList) {
		Ses0250 param = new Ses0250();
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<Ses0250> lstOrder = ses0250Dao.searchOrder(param);
		DatasetUtil.moveListToDs(lstOrder, dsList);

		return dsList;
	}
	public Dataset searchCharacteristics(MipParameterVO paramVO, Dataset dsCond, Dataset dsList) {
		List<Ses0251> lstSes0251 = new ArrayList<Ses0251>();
		for(int i=0; i < dsCond.getRowCount(); i++) {
			Ses0251 ses0251 = new Ses0251();
			DatasetUtil.moveDsRowToObj(dsCond, i, ses0251);
			ses0251.setMandt(paramVO.getVariable("G_MANDT"));
			ses0251.setSpras(new Spras(paramVO.getVariable("G_LANG")));
			lstSes0251.add(ses0251);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lstCond", lstSes0251);
		map.put("entity", lstSes0251.get(0));
		List<Ses0251> lstOrder = ses0250Dao.searchCharacteristics(map);
		DatasetUtil.moveListToDs(lstOrder, dsList);

		return dsList;
	}
}