package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0450D;
import hdel.sd.ses.domain.Ses0450;
import hdel.sd.ses.domain.Ses0450ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0450S extends SrmService {

	private Ses0450D Ses0450Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0450Dao = sqlSession.getMapper(Ses0450D.class);
	}
	
	public List<Ses0450> getListHeader(Ses0450ParamVO param) {
		// 기술검토 요청 의뢰 조회 야브
		if (param.getQtso_no() != null && !"".equals(param.getQtso_no()))	{
			param.setQuery_yn("Y");
		}

		if (param.getFrdlvdat() != null && !"".equals(param.getFrdlvdat()))	{
			param.setQuery_yn("Y");
		}

		if (param.getZrqstat() != null && !"".equals(param.getZrqstat()))	{
			param.setQuery_yn("Y");
		}

		if ("P".equals(param.getGubun()) )	{	// 프로젝트 정보 조회
			return Ses0450Dao.selectListHeaderProject(param);
		}else	{
			return Ses0450Dao.selectListHeaderQtnum(param);
		}
	}


	public List<Ses0450> getListDetail(Ses0450ParamVO param) {
		return Ses0450Dao.selectListDetail(param);
	}

	public List<Ses0450> getRequestList(Ses0450ParamVO param) {
		return Ses0450Dao.selectRequestList(param);
	}
}
