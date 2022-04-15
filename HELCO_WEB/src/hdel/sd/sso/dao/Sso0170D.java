package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0170ParamVO;
import hdel.sd.sso.domain.Sso0170VO;

import java.util.List;

/**
 * 수주관리 - 이동계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0170D {

	public List<Sso0170VO> SelectWaerk(Sso0170ParamVO param);					// 통화종류 조회
	public List<Sso0170VO> SelectOrder(Sso0170ParamVO param);					// 통화별 수주
	public List<Sso0170VO> SelectOrderExitWaerk(Sso0170ParamVO param);			// 월별 수주
	public List<Sso0170VO> SelectSales(Sso0170ParamVO param);					// 통화별 매출
	public List<Sso0170VO> SelectSalesExitWaerk(Sso0170ParamVO param);			// 월별 매출
	public List<Sso0170VO> SelectCharge(Sso0170ParamVO param);					// 통화별 청구
	public List<Sso0170VO> SelectChargeExitWaerk(Sso0170ParamVO param);			// 월별 청구
	public List<Sso0170VO> SelectCollection(Sso0170ParamVO param);				// 통화별 수금
	public List<Sso0170VO> SelectCollectionExitWaerk(Sso0170ParamVO param);		// 월별 수금
}
 