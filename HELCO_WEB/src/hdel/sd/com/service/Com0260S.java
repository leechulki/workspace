package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0260D;
import hdel.sd.com.domain.Com0260;
import hdel.sd.com.domain.Com0260ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0260S extends SrmService {

	private Com0260D Com0260Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0260Dao = sqlSession.getMapper(Com0260D.class);
	}
	
	public List<Com0260> find(Com0260ParamVO param) {
		return Com0260Dao.selectListQtnum(param);
	}  
	
}
