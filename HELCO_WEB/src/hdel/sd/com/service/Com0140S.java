package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0140D;
import hdel.sd.com.domain.Com0140;
import hdel.sd.com.domain.Com0140ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0140S extends SrmService {

	private Com0140D Com0140Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0140Dao = sqlSession.getMapper(Com0140D.class);
	}
	
	public List<Com0140> find(Com0140ParamVO param) {
		return Com0140Dao.selectList(param);
	}  
	
}
