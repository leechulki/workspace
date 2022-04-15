package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0080D;
import hdel.sd.com.domain.Com0080;
import hdel.sd.com.domain.Com0080ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0080S extends SrmService {

	private Com0080D Com0080Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0080Dao = sqlSession.getMapper(Com0080D.class);
	}
	
	public List<Com0080> find(Com0080ParamVO param) {
		return Com0080Dao.selectList(param);
	}  
	
}
