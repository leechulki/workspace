package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0341D;
import hdel.sd.ses.domain.Ses0341;
import hdel.sd.ses.domain.Ses0341ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


@Service
public class Ses0341S extends SrmService {

	private Ses0341D Ses0341Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0341Dao = sqlSession.getMapper(Ses0341D.class);
	}
	
	public List<Ses0341> selectList(Ses0341ParamVO param) throws Exception {
		return Ses0341Dao.selectList(param);
	}

	public List<Ses0341> selectListFile(Ses0341ParamVO param) throws Exception {
		return Ses0341Dao.selectListFile(param);
	}

}
