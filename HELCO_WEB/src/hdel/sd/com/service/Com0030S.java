package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0030D;
import hdel.sd.com.domain.Com0020;
import hdel.sd.com.domain.Com0020ParamVO;
import hdel.sd.com.domain.Com0030;
import hdel.sd.com.domain.Com0030ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0030S extends SrmService {

	private Com0030D com0030Dao;
	
	public void createDao(SqlSession sqlSession) {
		com0030Dao = sqlSession.getMapper(Com0030D.class);
	}
	
	public List<Com0030> find(Com0030ParamVO param) {
		return com0030Dao.selectListSman(param);
	}  
	
	public List<Com0030> loginCheck(Com0030ParamVO param) {
		return com0030Dao.selectLangLogin(param);
	} 
}
