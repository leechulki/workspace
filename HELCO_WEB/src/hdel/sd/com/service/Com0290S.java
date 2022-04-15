package hdel.sd.com.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0290D;
import hdel.sd.com.domain.Com0290;
import hdel.sd.com.domain.Com0290ParamVO;

@Service
public class Com0290S extends SrmService {
	
	private Com0290D com0290d;
	
	public void createDao(SqlSession sqlSession) {
		com0290d = sqlSession.getMapper(Com0290D.class);
	}
	
	public List<Com0290> find(Com0290ParamVO param) {
		return com0290d.find(param);
	}

}
