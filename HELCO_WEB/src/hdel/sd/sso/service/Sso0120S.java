package hdel.sd.sso.service;

/**
 * 수주계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0120D;
import hdel.sd.sso.domain.Sso0120ParamVO;
import hdel.sd.sso.domain.Sso0120VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Sso0120S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sso0120D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sso0120D.class);
		
	} 

	// 조회
	public List<Sso0120VO> find(Sso0120ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		// 수주
		if ( "1".equals(paramV.getWHERE()) )
		{
			return dao.SelectOrder(paramV);
		}
		// 매출
		else if ( "2".equals(paramV.getWHERE()) )
		{
			return dao.SelectSales(paramV);
		}
		// 청구
		else if ( "3".equals(paramV.getWHERE()) )
		{
			return dao.SelectCharge(paramV);
		}
		// 수금
		else
		{
			return dao.SelectCollection(paramV);
		}
		
	}
	
}
