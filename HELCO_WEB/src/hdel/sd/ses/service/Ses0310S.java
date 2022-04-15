package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0310D;
import hdel.sd.ses.domain.Ses0310;
import hdel.sd.ses.domain.Ses0310ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0310S extends SrmService {

	private Ses0310D Ses0310Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0310Dao = sqlSession.getMapper(Ses0310D.class);
	}
	
	public List<Ses0310> find(Ses0310ParamVO param) {
		return Ses0310Dao.selectListSpec(param);
	}  
	
}
