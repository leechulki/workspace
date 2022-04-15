package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0100ParamVO;
import hdel.sd.smp.domain.Smp0100VO;

import java.util.List;

/**
 * 이동계획 현황(보수)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Smp0100D { 

	public List<Smp0100VO> SelectOrder(Smp0100ParamVO param);		// 수주
	public List<Smp0100VO> SelectSales(Smp0100ParamVO param);		// 매출
	public List<Smp0100VO> SelectCharge(Smp0100ParamVO param);		// 청구
	public List<Smp0100VO> SelectCollection(Smp0100ParamVO param);	// 수금
}
