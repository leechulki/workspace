package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0330D;
import hdel.sd.com.domain.Com0330;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.domain.Com0330ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0330S extends SrmService {

	private Com0330D Com0330Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0330Dao = sqlSession.getMapper(Com0330D.class);
	}
	public List<Com0330> search(Com0330ParamVO param) {
		return Com0330Dao.selectList(param);
	}
	public List<ComboVO> searchAra(Com0330ParamVO param) {
		return Com0330Dao.searchAra(param);
	}
	public List<ComboVO> searchDmstat(Com0330ParamVO param) {
		return Com0330Dao.searchDmstat(param);
	}
	
	
}
