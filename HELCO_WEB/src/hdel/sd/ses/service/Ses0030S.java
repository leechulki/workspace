package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0030D;
import hdel.sd.ses.domain.Ses0030;
import hdel.sd.ses.domain.Ses0030ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0030S extends SrmService {

	private Ses0030D Ses0030Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0030Dao = sqlSession.getMapper(Ses0030D.class);
	}
	
	public List<Ses0030> searchHeader(Ses0030ParamVO param) {
		return Ses0030Dao.selectListHeader(param);
	}

	public List<Ses0030> getMaxQtnumInfo(String sManst, String sQtnum, String sQtser) {
		return Ses0030Dao.selectMaxQtnumInfo(sManst, sQtnum, sQtser);
	}

	public List<Ses0030> searchDetail(Ses0030ParamVO param) {
		return Ses0030Dao.selectListDetail(param);
	}
}
