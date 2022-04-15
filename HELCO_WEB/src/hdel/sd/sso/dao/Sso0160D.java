package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0160ParamVO;
import hdel.sd.sso.domain.Sso0160VO;

import java.util.List;

/**
 * 수주관리 - 이동계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0160D { 

	public List<Sso0160VO> SelectOrder(Sso0160ParamVO param);		// 수주
	public List<Sso0160VO> SelectSales(Sso0160ParamVO param);		// 매출
	public List<Sso0160VO> SelectCharge(Sso0160ParamVO param);		// 청구
	public List<Sso0160VO> SelectCollection(Sso0160ParamVO param);	// 수금
}
