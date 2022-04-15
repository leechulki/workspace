package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0150D;
import hdel.sd.com.domain.Com0150;
import hdel.sd.com.domain.Com0150ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0150S extends SrmService {

	private Com0150D Com0150Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0150Dao = sqlSession.getMapper(Com0150D.class);
	}
	
	public List<Com0150> find(Com0150ParamVO param) {
		return Com0150Dao.selectList(param);
	}  
	
}
