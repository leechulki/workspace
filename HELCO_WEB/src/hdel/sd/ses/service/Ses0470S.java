package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0470D;
import hdel.sd.ses.domain.Ses0470;
import hdel.sd.ses.domain.Ses0470ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0470S extends SrmService {

	private Ses0470D Ses0470Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0470Dao = sqlSession.getMapper(Ses0470D.class);
	}
	
	public List<Ses0470> getListHeader(Ses0470ParamVO param) {
		// 기술검토 요청 의뢰 조회
		if (param.getQtso_no() != null && !"".equals(param.getQtso_no()))	{
			param.setQuery_yn("Y");
		}

		if (param.getFrzrqdat() != null && !"".equals(param.getFrzrqdat()))	{
			param.setQuery_yn("Y");
		}

		if (param.getZrqstat() != null && !"".equals(param.getZrqstat()))	{
			param.setQuery_yn("Y");
		}

		if ("P".equals(param.getGubun()) )	{	// 프로젝트 정보 조회
			return Ses0470Dao.selectListHeaderProject(param);
		}else	{								// 견적 정보 조회
			return Ses0470Dao.selectListHeaderQtnum(param);
		}
	}

	public List<Ses0470> getListDetail(Ses0470ParamVO param) {
		return Ses0470Dao.selectListDetail(param);
	}

	public List<Ses0470> getRequestList(Ses0470ParamVO param) {
		return Ses0470Dao.selectRequestList(param);
	}

}
