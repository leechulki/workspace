package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0034D;
import hdel.sd.ses.domain.Ses0034;
import hdel.sd.ses.domain.Ses0034ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0034S extends SrmService {

	private Ses0034D Ses0034Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0034Dao = sqlSession.getMapper(Ses0034D.class);
	}

	public List<Ses0034> searchDetail(Ses0034ParamVO param) {
		return Ses0034Dao.selectZipcode(param);
	}
}
