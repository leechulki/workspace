package hdel.sd.smp.service;

/**
 * 이동계획 현황(보수)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.service.SrmService;
import hdel.sd.smp.dao.Smp0100D;
import hdel.sd.smp.domain.Smp0100ParamVO;
import hdel.sd.smp.domain.Smp0100VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Smp0100S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Smp0100D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Smp0100D.class);
		
	} 

	// 조회
	public List<Smp0100VO> find(Smp0100ParamVO paramV) {
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
