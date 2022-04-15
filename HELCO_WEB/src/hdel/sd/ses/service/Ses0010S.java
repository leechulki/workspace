package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0010D;
import hdel.sd.ses.domain.Ses0010;
import hdel.sd.ses.domain.Ses0010ParamVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0010S extends SrmService {

	private Ses0010D Ses0010Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0010Dao = sqlSession.getMapper(Ses0010D.class);
	}
	
	public List<Ses0010> find(Ses0010ParamVO param) {
		return Ses0010Dao.selectListSpec(param);
	}
	
	public List<Map> selectListAbrand(Ses0010ParamVO param) {
		return Ses0010Dao.selectListAbrand(param);
	}

}
