package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0170D;
import hdel.sd.com.domain.Com0170;
import hdel.sd.com.domain.Com0170ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0170S extends SrmService {

	private Com0170D Com0170Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0170Dao = sqlSession.getMapper(Com0170D.class);
	}
	
	public List<Com0170> find(Com0170ParamVO param) {
		return Com0170Dao.selectList(param);
	}  
	
}
