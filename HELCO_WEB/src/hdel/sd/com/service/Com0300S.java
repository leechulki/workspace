package hdel.sd.com.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0300D;
import hdel.sd.com.domain.Com0300;
import hdel.sd.com.domain.Com0300ParamVO;

@Service
public class Com0300S extends SrmService {
	
	private Com0300D com0300d;
	
	public void createDao(SqlSession sqlSession) {
		com0300d = sqlSession.getMapper(Com0300D.class);
	}
	
	public List<Com0300> find(Com0300ParamVO param) {
		return com0300d.find(param);
	}
	

}
