package hdel.sd.sbp.service;

/**
 * 사업계획 현황(보수)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.service.SrmService;
import hdel.sd.sbp.dao.Sbp0100D;
import hdel.sd.sbp.domain.Sbp0100ParamVO;
import hdel.sd.sbp.domain.Sbp0100VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Sbp0100S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sbp0100D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sbp0100D.class);
		
	} 

	// 조회
	public List<Sbp0100VO> find(Sbp0100ParamVO paramV) {
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
