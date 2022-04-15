package hdel.sd.smp.service;

/**
 * 이동계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.service.SrmService;
import hdel.sd.smp.dao.Smp0090D;
import hdel.sd.smp.domain.Smp0090ParamVO;
import hdel.sd.smp.domain.Smp0090VO;
import hdel.sd.smp.domain.Smp0110ParamVO;
import hdel.sd.smp.domain.Smp0110VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Smp0090S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Smp0090D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Smp0090D.class);
		
	} 

	// 조회
	public List<Smp0090VO> find(Smp0090ParamVO paramV) {
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
