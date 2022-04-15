package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0110ParamVO;
import hdel.sd.smp.domain.Smp0110VO;
import hdel.sd.smp.domain.Smp0120ParamVO;
import hdel.sd.smp.domain.Smp0120VO;

import java.util.List;

/**
 * 이동계획 현황(보수집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Smp0120D {

	public List<Smp0120VO> SelectWaerk(Smp0120ParamVO param);					// 통화종류 조회
	public List<Smp0120VO> SelectOrder(Smp0120ParamVO param);					// 통화별 수주
	public List<Smp0120VO> SelectOrderExitWaerk(Smp0120ParamVO param);			// 월별 수주
	public List<Smp0120VO> SelectSales(Smp0120ParamVO param);					// 통화별 매출
	public List<Smp0120VO> SelectSalesExitWaerk(Smp0120ParamVO param);			// 월별 매출
	public List<Smp0120VO> SelectCharge(Smp0120ParamVO param);					// 통화별 청구
	public List<Smp0120VO> SelectChargeExitWaerk(Smp0120ParamVO param);			// 월별 청구
	public List<Smp0120VO> SelectCollection(Smp0120ParamVO param);				// 통화별 수금
	public List<Smp0120VO> SelectCollectionExitWaerk(Smp0120ParamVO param);		// 월별 수금
}
 