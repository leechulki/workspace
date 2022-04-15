package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0032D;
import hdel.sd.ses.domain.Ses0032;
import hdel.sd.ses.domain.Ses0032ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0032S extends SrmService {

	private Ses0032D Ses0032Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0032Dao = sqlSession.getMapper(Ses0032D.class);
	}
	
	public List<Ses0032> search(Ses0032ParamVO param) {
		return Ses0032Dao.selectList(param);
	}
}
