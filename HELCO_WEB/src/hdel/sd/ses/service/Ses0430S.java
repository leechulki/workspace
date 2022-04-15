package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0430D;
import hdel.sd.ses.domain.Ses0430;
import hdel.sd.ses.domain.Ses0430ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0430S extends SrmService {

	private Ses0430D Ses0430Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0430Dao = sqlSession.getMapper(Ses0430D.class);
	}
	
	public List<Ses0430> getListHeader(Ses0430ParamVO param) {
		
		return Ses0430Dao.selectList(param);
		
	}

}
