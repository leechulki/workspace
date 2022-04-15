package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0200D;
import hdel.sd.ses.domain.Ses0200;
import hdel.sd.ses.domain.Ses0200ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0200S extends SrmService {

	private Ses0200D Ses0200Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0200Dao = sqlSession.getMapper(Ses0200D.class);
	}

	public List<Ses0200> searchDetail(Ses0200ParamVO param) {
		return Ses0200Dao.selectListDetail(param);
	}
}
