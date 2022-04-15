package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0440D;
import hdel.sd.ses.domain.Ses0440;
import hdel.sd.ses.domain.Ses0440ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0440S extends SrmService {

	private Ses0440D Ses0440Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0440Dao = sqlSession.getMapper(Ses0440D.class);
	}
	
	public List<Ses0440> getListHeader(Ses0440ParamVO param) {
		// 기술검토 요청 의뢰 조회 야브
		
		return Ses0440Dao.selectListHeaderProject(param);
	
	}

	public List<Ses0440> getListDetail(Ses0440ParamVO param) {
		return Ses0440Dao.selectListDetail(param);
	}

}
