package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0240D;
import hdel.sd.com.domain.Com0240;
import hdel.sd.com.domain.Com0240ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0240S extends SrmService {

	private Com0240D Com0240Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0240Dao = sqlSession.getMapper(Com0240D.class);
	}
	
	public List<Com0240> find(Com0240ParamVO param) {
		return Com0240Dao.selectListSman(param);
	}  
	
}
