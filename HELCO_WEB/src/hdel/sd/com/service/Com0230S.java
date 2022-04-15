package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0230D;
import hdel.sd.com.domain.Com0230;
import hdel.sd.com.domain.Com0230ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0230S extends SrmService {

	private Com0230D Com0230Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0230Dao = sqlSession.getMapper(Com0230D.class);
	}
	
	public List<Com0230> find(Com0230ParamVO param) {
		return Com0230Dao.selectListSman(param);
	}  
	
}
