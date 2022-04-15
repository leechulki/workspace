package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0081D;
import hdel.sd.ses.domain.Ses0081;
import hdel.sd.ses.domain.Ses0081ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0081S extends SrmService {

	private Ses0081D Ses0081Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0081Dao = sqlSession.getMapper(Ses0081D.class);
	}

	public List<Ses0081> searchDetail(Ses0081ParamVO param) {
		return Ses0081Dao.selectListDetail(param);
	}
}
