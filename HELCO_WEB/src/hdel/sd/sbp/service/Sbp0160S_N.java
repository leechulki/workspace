package hdel.sd.sbp.service;


import hdel.lib.service.SrmService;
import hdel.sd.com.domain.Com0060ParamVO;
import hdel.sd.sbp.dao.Sbp0160D_N;
import hdel.sd.sbp.domain.Sbp0160ParamVO;
import hdel.sd.sbp.domain.Sbp0160VO_N;
import hdel.sd.sbp.domain.Sbp0161ParamVO;
import hdel.sd.sbp.domain.Sbp0161VO_N;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Sbp0160S_N extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sbp0160D_N dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sbp0160D_N.class);
		
	}

	// 조회
	public List<Sbp0160VO_N> selectZSDT1001_N(Sbp0160ParamVO param) {
		return dao.selectZSDT1001_N(param);
	}
	
	// 해외대리점 조회
	public List<Sbp0160VO_N> selectZSDT1001E_N(Sbp0160ParamVO param) {
		return dao.selectZSDT1001E_N(param);
	}

	// 실기종
	public List<Com0060ParamVO> selectRTYPE(Com0060ParamVO param) {
		return dao.selectRTYPE(param);
	}

	// 원가산출용 템플릿 정보 조회 (템플릿번호로 조회)
	public List<Sbp0160VO_N> selectListTempletInfo(Sbp0160ParamVO param) {
		return dao.selectListTempletInfo(param);
	}
	
	// 수주계획번호별 매출청구수금 조회(존재하는 데이터)
	public List<Sbp0161VO_N> selectListDetail_N(Sbp0161ParamVO param) {
		return dao.selectListDetail_N(param);
	}

	// 수주계획번호별 매출청구수금 조회(존재하는 않는 데이터)
	public List<Sbp0161VO_N> selectListDetail_Null(Sbp0161ParamVO param) {
		return dao.selectListDetail_Null(param);
	}
	
	// 이동계획 1차 마감여부
	public List<Sbp0160VO_N> selectZclflg(Sbp0160ParamVO param) {
		return dao.selectZclflg(param);
	}
	
}
