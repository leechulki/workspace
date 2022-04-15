package hdel.sd.sso.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0214D;
import hdel.sd.sso.domain.Sso0214;
import hdel.sd.sso.domain.Sso0214ParamVO;

@Service
public class Sso0214S extends SrmService {
	
	private Sso0214D sso0214d;
	
	public void createDao(SqlSession sqlSession) {
		sso0214d = sqlSession.getMapper(Sso0214D.class);
	}
	
	public List<Sso0214> find(Sso0214ParamVO param) {
		return sso0214d.find(param);
	}
	
	public void merge(List<Sso0214> param) throws Exception {
		sso0214d.merge(param);
	}

}
