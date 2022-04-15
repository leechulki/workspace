package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0130ParamVO;
import hdel.sd.sso.domain.Sso0130VO;

import java.util.List;

/**
 * 수주계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0130D {

	public List<Sso0130VO> SelectWaerk(Sso0130ParamVO param);					// 통화종류 조회
	public List<Sso0130VO> SelectOrder(Sso0130ParamVO param);					// 통화별 수주
	public List<Sso0130VO> SelectOrderExitWaerk(Sso0130ParamVO param);			// 월별 수주
	public List<Sso0130VO> SelectSales(Sso0130ParamVO param);					// 통화별 매출
	public List<Sso0130VO> SelectSalesExitWaerk(Sso0130ParamVO param);			// 월별 매출
	public List<Sso0130VO> SelectCharge(Sso0130ParamVO param);					// 통화별 청구
	public List<Sso0130VO> SelectChargeExitWaerk(Sso0130ParamVO param);			// 월별 청구
	public List<Sso0130VO> SelectCollection(Sso0130ParamVO param);				// 통화별 수금
	public List<Sso0130VO> SelectCollectionExitWaerk(Sso0130ParamVO param);		// 월별 수금
}
 