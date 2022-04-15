package hdel.sd.sbp.dao;

import hdel.sd.sbp.domain.Sbp0090ParamVO;
import hdel.sd.sbp.domain.Sbp0090VO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 사업계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sbp0090D { 

	public List<Sbp0090VO> SelectOrder(Sbp0090ParamVO param);		// 수주
	public List<Sbp0090VO> SelectSales(Sbp0090ParamVO param);		// 매출
	public List<Sbp0090VO> SelectCharge(Sbp0090ParamVO param);		// 청구
	public List<Sbp0090VO> SelectCollection(Sbp0090ParamVO param);	// 수금
	public Float getExchangeBPUSD(@Param("mandt") String mandt, @Param("bsdat") String bsdat);
}
