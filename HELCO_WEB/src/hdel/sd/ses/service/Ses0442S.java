package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0442D;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.domain.Ses0442;
import hdel.sd.ses.domain.Ses0442ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0442S extends SrmService {

	private Ses0442D Ses0442Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0442Dao = sqlSession.getMapper(Ses0442D.class);
	}
	
	public List<Ses0442> getListHeader(Ses0442ParamVO param) {
		// 기술검토 요청 의뢰 조회 야브
		
		return Ses0442Dao.selectListHeaderProject(param);
	
	}
	
	public float selectinexrate(Ses0442ParamVO param){
		return Ses0442Dao.selectinexrate(param.getMandt(), param.getZgubun(), param.getZuse());
	}	
	/*public List<Ses0442> getListDetail(Ses0442ParamVO param) {
		return Ses0442Dao.selectListDetail(param);
	}*/

}
