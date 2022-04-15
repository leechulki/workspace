package hdel.sd.sbp.dao;

import hdel.sd.sbp.domain.Sbp0110ParamVO;
import hdel.sd.sbp.domain.Sbp0110VO;

import java.util.List;

/**
 * 사업계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sbp0110D {

	public List<Sbp0110VO> SelectWaerk(Sbp0110ParamVO param);					// 통화종류 조회
	public List<Sbp0110VO> SelectOrderZpym(Sbp0110ParamVO param);				// 수주 계획년월 조회
	public List<Sbp0110VO> SelectSalesZpym(Sbp0110ParamVO param);				// 매출 계획년월 조회
	public List<Sbp0110VO> SelectChargeZpym(Sbp0110ParamVO param);				// 청구 계획년월 조회
	public List<Sbp0110VO> SelectCollectionZpym(Sbp0110ParamVO param);			// 수금 계획년월 조회
	public List<Sbp0110VO> SelectOrder(Sbp0110ParamVO param);					// 통화별 수주
	public List<Sbp0110VO> SelectOrderExitWaerk(Sbp0110ParamVO param);			// 월별 수주
	public List<Sbp0110VO> SelectSales(Sbp0110ParamVO param);					// 통화별 매출
	public List<Sbp0110VO> SelectSalesExitWaerk(Sbp0110ParamVO param);			// 월별 매출
	public List<Sbp0110VO> SelectCharge(Sbp0110ParamVO param);					// 통화별 청구
	public List<Sbp0110VO> SelectChargeExitWaerk(Sbp0110ParamVO param);			// 월별 청구
	public List<Sbp0110VO> SelectCollection(Sbp0110ParamVO param);				// 통화별 수금
	public List<Sbp0110VO> SelectCollectionExitWaerk(Sbp0110ParamVO param);		// 월별 수금
}
 