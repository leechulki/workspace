package hdel.sd.sch.dao;

import hdel.sd.sch.domain.Sch0050ParamVO;
import hdel.sd.sch.domain.Sch0050VO;

import java.util.List;

/**
 * On-Hand 청구계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sch0050D {

	public List<Sch0050VO> SelectWaerk(Sch0050ParamVO param);				// 통화종류 조회
	public List<Sch0050VO> SelectCharge(Sch0050ParamVO param);				// 통화별 청구
	public List<Sch0050VO> SelectChargeExitWaerk(Sch0050ParamVO param);		// 월별 청구
}
 