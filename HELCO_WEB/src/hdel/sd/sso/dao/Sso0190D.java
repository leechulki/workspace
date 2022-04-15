package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0190ParamVO;
import hdel.sd.sso.domain.Sso0190VO;

import java.util.List;

/**
 * 수주관리 - 이동계획 현황(보수집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0190D {

	public List<Sso0190VO> SelectWaerk(Sso0190ParamVO param);					// 통화종류 조회
	public List<Sso0190VO> SelectOrder(Sso0190ParamVO param);					// 통화별 수주
	public List<Sso0190VO> SelectOrderExitWaerk(Sso0190ParamVO param);			// 월별 수주
	public List<Sso0190VO> SelectSales(Sso0190ParamVO param);					// 통화별 매출
	public List<Sso0190VO> SelectSalesExitWaerk(Sso0190ParamVO param);			// 월별 매출
	public List<Sso0190VO> SelectCharge(Sso0190ParamVO param);					// 통화별 청구
	public List<Sso0190VO> SelectChargeExitWaerk(Sso0190ParamVO param);			// 월별 청구
	public List<Sso0190VO> SelectCollection(Sso0190ParamVO param);				// 통화별 수금
	public List<Sso0190VO> SelectCollectionExitWaerk(Sso0190ParamVO param);		// 월별 수금
}
 