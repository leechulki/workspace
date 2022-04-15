package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0250D;
import hdel.sd.com.domain.Com0250;
import hdel.sd.com.domain.Com0250ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0250S extends SrmService {

	private Com0250D Com0250Dao;
	
	public void createDao(SqlSession sqlSession) {
		Com0250Dao = sqlSession.getMapper(Com0250D.class);
	}
	
	public List<Com0250> find(Com0250ParamVO param) {	
		if (param.getGubun().equals("")) {
			return Com0250Dao.selectListQtnum(param);
		} else {
			return Com0250Dao.selectListQtnumD(param);
	
		}  
	}
}
