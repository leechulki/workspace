package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0140ParamVO;
import hdel.sd.sso.domain.Sso0140VO;

import java.util.List;

/**
 * 수주계획 현황(보수)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0140D { 

	public List<Sso0140VO> SelectOrder(Sso0140ParamVO param);		// 수주
	public List<Sso0140VO> SelectSales(Sso0140ParamVO param);		// 매출
	public List<Sso0140VO> SelectCharge(Sso0140ParamVO param);		// 청구
	public List<Sso0140VO> SelectCollection(Sso0140ParamVO param);	// 수금
}
