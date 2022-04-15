package hdel.sd.sch.service;

/**
 * On-Hand 청구계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.service.SrmService;
import hdel.sd.sch.dao.Sch0040D;
import hdel.sd.sch.domain.Sch0040ParamVO;
import hdel.sd.sch.domain.Sch0040VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Sch0040S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sch0040D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sch0040D.class);
		
	} 

	// 조회
	public List<Sch0040VO> find(Sch0040ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		// 청구
		return dao.SelectCharge(paramV);
		
	}
	
}
