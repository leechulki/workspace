package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0120ParamVO;
import hdel.sd.sso.domain.Sso0120VO;

import java.util.List;

/**
 * 수주계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0120D { 

	public List<Sso0120VO> SelectOrder(Sso0120ParamVO param);		// 수주
	public List<Sso0120VO> SelectSales(Sso0120ParamVO param);		// 매출
	public List<Sso0120VO> SelectCharge(Sso0120ParamVO param);		// 청구
	public List<Sso0120VO> SelectCollection(Sso0120ParamVO param);	// 수금
}
