package hdel.sd.sbp.dao;

import hdel.sd.sbp.domain.Sbp0100ParamVO;
import hdel.sd.sbp.domain.Sbp0100VO;

import java.util.List;

/**
 * 사업계획 현황(보수)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sbp0100D { 

	public List<Sbp0100VO> SelectOrder(Sbp0100ParamVO param);		// 수주
	public List<Sbp0100VO> SelectSales(Sbp0100ParamVO param);		// 매출
	public List<Sbp0100VO> SelectCharge(Sbp0100ParamVO param);		// 청구
	public List<Sbp0100VO> SelectCollection(Sbp0100ParamVO param);	// 수금
}
