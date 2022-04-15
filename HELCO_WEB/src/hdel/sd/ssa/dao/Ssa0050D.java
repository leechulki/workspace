package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0050ParamVO;
import hdel.sd.ssa.domain.Ssa0050VO;

import java.util.List;

/**
 * On-Hand 매출계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Ssa0050D { 

	public List<Ssa0050VO> SelectSales(Ssa0050ParamVO param);		// 매출
}
