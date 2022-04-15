package hdel.sd.com.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0280D;
import hdel.sd.com.domain.Com0280;

@Service
public class Com0280S extends SrmService {
	private Com0280D com0280d;

	public void createDao(SqlSession sqlSession) {
		com0280d = sqlSession.getMapper(Com0280D.class);
	}

	public List<Com0280> find(Com0280 param) {
		return com0280d.find(param);
	}  
}