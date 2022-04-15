package hdel.sd.sso.service;

import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0210D;
import hdel.sd.sso.domain.Sso0210;
import hdel.sd.sso.domain.Sso0210ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Sso0210S extends SrmService {

	private Sso0210D Sso0210Dao;
	
	public void createDao(SqlSession sqlSession) {
		Sso0210Dao = sqlSession.getMapper(Sso0210D.class);
	}

	public List<Sso0210> getListHeader(Sso0210ParamVO param) {
		
		return Sso0210Dao.selectList(param);
		
	}

	public List<Sso0210> getListDetail(Sso0210ParamVO param) {

		return Sso0210Dao.selectDetail(param);
		
	}

}
