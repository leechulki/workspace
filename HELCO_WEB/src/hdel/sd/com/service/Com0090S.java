package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0090D;
import hdel.sd.com.domain.Com0090;
import hdel.sd.com.domain.Com0090ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0090S extends SrmService {

	private Com0090D Com0090Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0090Dao = sqlSession.getMapper(Com0090D.class);
	}
	
	public List<Com0090> find(Com0090ParamVO param) {
		return Com0090Dao.selectList(param);
	}  
	
}
