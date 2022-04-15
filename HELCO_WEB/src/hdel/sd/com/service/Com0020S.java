package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0020D;
import hdel.sd.com.domain.Com0020;
import hdel.sd.com.domain.Com0020ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0020S extends SrmService {

	private Com0020D Com0020Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0020Dao = sqlSession.getMapper(Com0020D.class);
	}
	
	public List<Com0020> find(Com0020ParamVO param) {
		return Com0020Dao.selectListLifnr(param);
	} 
}
