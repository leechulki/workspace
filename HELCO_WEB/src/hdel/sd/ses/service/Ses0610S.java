package hdel.sd.ses.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0610D;
import hdel.sd.ses.domain.Ses0610;
import hdel.sd.ses.domain.Ses0610ParamVO;

@Service
public class Ses0610S extends SrmService {
	private Ses0610D ses0610d;
	
	public void createDao(SqlSession sqlSession) {
		ses0610d = sqlSession.getMapper(Ses0610D.class);
	}
	
	public List<Ses0610> find(Ses0610ParamVO param) {
		return ses0610d.find(param);
	}
	
	public void merge(List<Ses0610> param) throws Exception {
		ses0610d.merge(param);
	}
	
	public List<Ses0610> findzsdt0222(Ses0610ParamVO param) {
		return ses0610d.findzsdt0222(param);
	}
}
