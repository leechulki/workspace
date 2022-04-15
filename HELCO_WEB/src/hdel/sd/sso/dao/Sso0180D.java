package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0180ParamVO;
import hdel.sd.sso.domain.Sso0180VO;

import java.util.List;

/**
 * 수주관리 - 이동계획 현황(보수)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0180D { 

	public List<Sso0180VO> SelectOrder(Sso0180ParamVO param);		// 수주
	public List<Sso0180VO> SelectSales(Sso0180ParamVO param);		// 매출
	public List<Sso0180VO> SelectCharge(Sso0180ParamVO param);		// 청구
	public List<Sso0180VO> SelectCollection(Sso0180ParamVO param);	// 수금
}
