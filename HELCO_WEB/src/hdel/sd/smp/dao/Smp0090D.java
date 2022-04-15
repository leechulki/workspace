package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0090ParamVO;
import hdel.sd.smp.domain.Smp0090VO;

import java.util.List;

/**
 * 이동계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Smp0090D { 

	public List<Smp0090VO> SelectOrder(Smp0090ParamVO param);		// 수주
	public List<Smp0090VO> SelectSales(Smp0090ParamVO param);		// 매출
	public List<Smp0090VO> SelectCharge(Smp0090ParamVO param);		// 청구
	public List<Smp0090VO> SelectCollection(Smp0090ParamVO param);	// 수금
}
