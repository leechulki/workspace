package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0053D;
import hdel.sd.ses.domain.Ses0053;
import hdel.sd.ses.domain.Ses0053ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0053S extends SrmService {

	private Ses0053D Ses0053Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0053Dao = sqlSession.getMapper(Ses0053D.class);
	}

	public List<Ses0053> searchDetail(Ses0053ParamVO param) {
		return Ses0053Dao.selectListDetail(param);
	}
}
