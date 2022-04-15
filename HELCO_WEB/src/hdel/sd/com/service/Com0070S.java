package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0070D;
import hdel.sd.com.domain.Com0070;
import hdel.sd.com.domain.Com0070ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0070S extends SrmService {

	private Com0070D Com0070Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0070Dao = sqlSession.getMapper(Com0070D.class);
	}
	
	public List<Com0070> find(Com0070ParamVO param) {
		return Com0070Dao.selectList(param);
	}  
	
}
