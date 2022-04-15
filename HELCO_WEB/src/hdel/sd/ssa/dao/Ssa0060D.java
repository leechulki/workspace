package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0060ParamVO;
import hdel.sd.ssa.domain.Ssa0060VO;

import java.util.List;

/**
 * On-Hand 매출계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Ssa0060D {

	public List<Ssa0060VO> SelectWaerk(Ssa0060ParamVO param);				// 통화종류 조회
	public List<Ssa0060VO> SelectSales(Ssa0060ParamVO param);				// 통화별 매출
	public List<Ssa0060VO> SelectSalesExitWaerk(Ssa0060ParamVO param);		// 월별 매출
}
