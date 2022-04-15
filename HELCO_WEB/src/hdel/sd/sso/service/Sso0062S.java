package hdel.sd.sso.service;

import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0062D;
import hdel.sd.sso.domain.Sso0062ParamVO;
import hdel.sd.sso.domain.Sso0062VO;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 수주생성 사양내용(Sso0062S) Service
 * @Comment
 *     	1.  void createDao 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Service

public class Sso0062S extends SrmService {

	Logger logger = Logger.getLogger(this.getClass());

	private Sso0062D dao;

	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sso0062D.class); 
	}

	// 견적Detail 조회
	public Sso0062VO selectQtnumDetail(Sso0062ParamVO param) {  
		return dao.selectQtnumDetail(param); 
	}

	// 자재정보에 따른 제품구분 정보 조회
	public Sso0062VO selectZprdgbn(Sso0062ParamVO param) {  
		return dao.selectZprdgbn(param); 
	}
}
