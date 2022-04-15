package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0200D;
import hdel.sd.com.domain.Com0200;
import hdel.sd.com.domain.Com0200ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0200S extends SrmService {

	private Com0200D Com0200Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0200Dao = sqlSession.getMapper(Com0200D.class);
	}
	
	public List<Com0200> find(Com0200ParamVO param) {
		return Com0200Dao.selectList(param);
	}  
	
}
