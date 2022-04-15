package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0110ParamVO;
import hdel.sd.smp.domain.Smp0110VO;

import java.util.List;

/**
 * 이동계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Smp0110D {

	public List<Smp0110VO> SelectWaerk(Smp0110ParamVO param);					// 통화종류 조회
	public List<Smp0110VO> SelectOrder(Smp0110ParamVO param);					// 통화별 수주
	public List<Smp0110VO> SelectOrderExitWaerk(Smp0110ParamVO param);			// 월별 수주
	public List<Smp0110VO> SelectSales(Smp0110ParamVO param);					// 통화별 매출
	public List<Smp0110VO> SelectSalesExitWaerk(Smp0110ParamVO param);			// 월별 매출
	public List<Smp0110VO> SelectCharge(Smp0110ParamVO param);					// 통화별 청구
	public List<Smp0110VO> SelectChargeExitWaerk(Smp0110ParamVO param);			// 월별 청구
	public List<Smp0110VO> SelectCollection(Smp0110ParamVO param);				// 통화별 수금
	public List<Smp0110VO> SelectCollectionExitWaerk(Smp0110ParamVO param);		// 월별 수금
}
 