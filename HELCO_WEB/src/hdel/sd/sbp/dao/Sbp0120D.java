package hdel.sd.sbp.dao;

import hdel.sd.sbp.domain.Sbp0120ParamVO;
import hdel.sd.sbp.domain.Sbp0120VO;

import java.util.List;

/**
 * 사업계획 현황(보수집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sbp0120D {

	public List<Sbp0120VO> SelectWaerk(Sbp0120ParamVO param);					// 통화종류 조회
	public List<Sbp0120VO> SelectOrderZpym(Sbp0120ParamVO param);				// 수주 계획년월 조회
	public List<Sbp0120VO> SelectSalesZpym(Sbp0120ParamVO param);				// 매출 계획년월 조회
	public List<Sbp0120VO> SelectChargeZpym(Sbp0120ParamVO param);				// 청구 계획년월 조회
	public List<Sbp0120VO> SelectCollectionZpym(Sbp0120ParamVO param);			// 수금 계획년월 조회
	public List<Sbp0120VO> SelectOrder(Sbp0120ParamVO param);					// 통화별 수주
	public List<Sbp0120VO> SelectOrderExitWaerk(Sbp0120ParamVO param);			// 월별 수주
	public List<Sbp0120VO> SelectSales(Sbp0120ParamVO param);					// 통화별 매출
	public List<Sbp0120VO> SelectSalesExitWaerk(Sbp0120ParamVO param);			// 월별 매출
	public List<Sbp0120VO> SelectCharge(Sbp0120ParamVO param);					// 통화별 청구
	public List<Sbp0120VO> SelectChargeExitWaerk(Sbp0120ParamVO param);			// 월별 청구
	public List<Sbp0120VO> SelectCollection(Sbp0120ParamVO param);				// 통화별 수금
	public List<Sbp0120VO> SelectCollectionExitWaerk(Sbp0120ParamVO param);		// 월별 수금
}
 