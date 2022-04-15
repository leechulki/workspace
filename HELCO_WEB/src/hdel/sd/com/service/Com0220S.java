package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0220D;
import hdel.sd.com.domain.Com0220;
import hdel.sd.com.domain.Com0220ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0220S extends SrmService {

	private Com0220D Com0220Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0220Dao = sqlSession.getMapper(Com0220D.class);
	}
	
	public List<Com0220> find(Com0220ParamVO param) {
		return Com0220Dao.selectList(param);
	}  
	
}
