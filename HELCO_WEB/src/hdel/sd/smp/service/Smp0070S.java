package hdel.sd.smp.service;

/**
 * 이동계획 오픈/마감
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.smp.dao.Smp0070D;
import hdel.sd.smp.domain.Smp0070ParamVO;
import hdel.sd.smp.domain.Smp0070VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Smp0070S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Smp0070D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Smp0070D.class);
		
	}

	// 조회
	public List<Smp0070VO> find(Smp0070ParamVO param) 
	{
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		return dao.Select(param);
	}
	
	// copy
	public int saveCopy(Smp0070ParamVO param)
	{
		int rs = 0; // 성공건수
		
		logger.info("@@@@@@@@@@ copyOrder service in -> dao @@@@@@@@@");
		
		rs = dao.InsertCopyOrder(param);				// 수주
		logger.info("@@@@@@@@@@ 수주 :"+rs);
		//if( rs < 0){throw new BizException("CW10022,마감(수주)");}

		rs = dao.InsertCopyOrderRepair(param);			// 수주(보수)
		//if( rs < 0){throw new BizException("CW10022,마감(수주_보수)");}
		
		logger.info("@@@@@@@@@@ copySales service in -> dao @@@@@@@@@");
		
		rs = dao.InsertCopySales(param);				// 매출
		//if( rs < 0){throw new BizException("CW10022,마감(매출)");}

		rs = dao.InsertCopySalesRepair(param);			// 매출(보수)
		//if( rs < 0){throw new BizException("CW10022,마감(매출_보수)");}
		
		logger.info("@@@@@@@@@@ copyCharge service in -> dao @@@@@@@@@");
		
		rs = dao.InsertCopyCharge(param);				// 청구
		//if( rs < 0){throw new BizException("CW10022,마감(청구)");}
		
		rs = dao.InsertCopyChargeRepair(param);			// 청구(보수)
		//if( rs < 0){throw new BizException("CW10022,마감(청구_보수)");}
		
		logger.info("@@@@@@@@@@ copyCollection service in -> dao @@@@@@@@@");
		
		rs = dao.InsertCopyCollection(param);			// 수금
		//if( rs < 0){throw new BizException("CW10022,마감(수금)");}
		
		rs = dao.InsertCopyCollectionRepair(param);		// 수금(보수)
		//if( rs < 0){throw new BizException("CW10022,마감(수금_보수)");}
		
		
		return rs;
	}

	// cancel
	public int saveCancel(Smp0070ParamVO param)
	{
		int rs = 0;
		
		logger.info("@@@@@@@@@@ cancelOrder service in -> dao @@@@@@@@@");
		
		rs = dao.DeleteCancelOrder(param);					// 수주
		//if( rs < 0){throw new BizException("CW10022,취소(수주)");}
		
		rs = dao.DeleteCancelOrderRepair(param);			// 수주(보수)
		//if( rs < 0){throw new BizException("CW10022,취소(수주_보수)");}
		
		logger.info("@@@@@@@@@@ cancelSales service in -> dao @@@@@@@@@");
		
		rs = dao.DeleteCancelSales(param);					// 매출
		//if( rs < 0){throw new BizException("CW10022,취소(매출)");}
		
		rs = dao.DeleteCancelSalesRepair(param);			// 매출(보수)
		//if( rs < 0){throw new BizException("CW10022,취소(매출_보수)");}
		
		logger.info("@@@@@@@@@@ cancelCharge service in -> dao @@@@@@@@@");
		
		rs = dao.DeleteCancelCharge(param);					// 청구
		//if( rs < 0){throw new BizException("CW10022,취소(청구)");}
		
		rs = dao.DeleteCancelChargeRepair(param);			// 청구(보수)
		//if( rs < 0){throw new BizException("CW10022,취소(청구_보수)");}
		
		logger.info("@@@@@@@@@@ cancelCollection service in -> dao @@@@@@@@@");
		
		rs = dao.DeleteCancelCollection(param);				// 수금
		//if( rs < 0){throw new BizException("CW10022,취소(수금)");}
		
		rs = dao.DeleteCancelCollectionRepair(param);		// 수금(보수)
		//if( rs < 0){throw new BizException("CW10022,취소(수금_보수)");}
		
		
		return rs;
	}

	// 마감후 저장
	public int saveCopyNext(Smp0070ParamVO param) {
		logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		return dao.MergeCopyNext(param);
	}

	// 취소후 저장
	public int saveCancelNext(Smp0070ParamVO param) {
		logger.info("@@@@@@@@@@ saveCancelNext service in -> dao @@@@@@@@@");
		
		int rs = 0;
		
		// 갱신
		if ( "update".equals( param.getROW_TYPE() ) ) 
		{
			logger.info("@@@@@@@@@@ cancelNextUpd service in -> dao @@@@@@@@@");
			rs = dao.MergeCancelNextUpd(param);
		}
		// 삭제
		else
		{
			logger.info("@@@@@@@@@@ cancelNextDel service in -> dao @@@@@@@@@");
			rs = dao.MergeCancelNextDel(param);
		}
		return rs;
	}
	
	
}
