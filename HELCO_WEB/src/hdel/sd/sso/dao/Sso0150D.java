package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0150ParamVO;
import hdel.sd.sso.domain.Sso0150VO;

import java.util.List;

/**
 * 수주계획 현황(보수집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0150D {

	public List<Sso0150VO> SelectWaerk(Sso0150ParamVO param);					// 통화종류 조회
	public List<Sso0150VO> SelectOrder(Sso0150ParamVO param);					// 통화별 수주
	public List<Sso0150VO> SelectOrderExitWaerk(Sso0150ParamVO param);			// 월별 수주
	public List<Sso0150VO> SelectSales(Sso0150ParamVO param);					// 통화별 매출
	public List<Sso0150VO> SelectSalesExitWaerk(Sso0150ParamVO param);			// 월별 매출
	public List<Sso0150VO> SelectCharge(Sso0150ParamVO param);					// 통화별 청구
	public List<Sso0150VO> SelectChargeExitWaerk(Sso0150ParamVO param);			// 월별 청구
	public List<Sso0150VO> SelectCollection(Sso0150ParamVO param);				// 통화별 수금
	public List<Sso0150VO> SelectCollectionExitWaerk(Sso0150ParamVO param);		// 월별 수금
}
 