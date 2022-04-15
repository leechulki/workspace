package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0350D;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.domain.Ses0350;
import hdel.sd.ses.domain.Ses0350ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0350S extends SrmService {

	private Ses0350D Ses0350Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0350Dao = sqlSession.getMapper(Ses0350D.class);
	}
	
	public List<Ses0350> searchHeader(Ses0350ParamVO param) {
		return Ses0350Dao.selectListHeader(param);
	}
	
	public Ses0350 getZuam(Ses0350ParamVO param) {
		return Ses0350Dao.selectZuam(param.getMandt(), param.getQtnum(), param.getQtser());
	}
	public Ses0350 getWavwr(Ses0350ParamVO param) {
		return Ses0350Dao.selectWavwr(param.getMandt(), param.getPspid(), param.getSeq());
	}
	
	
}
