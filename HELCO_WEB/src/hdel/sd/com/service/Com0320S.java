package hdel.sd.com.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0320D;
import hdel.sd.com.domain.Com0320;
import hdel.sd.com.domain.Com0320ParamVO;

@Service
public class Com0320S extends SrmService {
	
	private Com0320D com0320d;
	
	public void createDao(SqlSession sqlSession) {
		com0320d = sqlSession.getMapper(Com0320D.class);
	}
	
	public List<Com0320> find(Com0320ParamVO param) {
		return com0320d.find(param);
	}
}
