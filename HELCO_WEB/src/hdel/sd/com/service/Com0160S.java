package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0160D;
import hdel.sd.com.domain.Com0160;
import hdel.sd.com.domain.Com0160ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0160S extends SrmService {

	private Com0160D Com0160Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0160Dao = sqlSession.getMapper(Com0160D.class);
	}
	
	public List<Com0160> find(Com0160ParamVO param) {
		return Com0160Dao.selectList(param);
	}  
	
	public List<Com0160> find_l(Com0160ParamVO param) {
		return Com0160Dao.selectList_l(param);
	}  
	
}
