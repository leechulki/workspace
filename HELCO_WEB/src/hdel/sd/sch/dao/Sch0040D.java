package hdel.sd.sch.dao;

import hdel.sd.sch.domain.Sch0040ParamVO;
import hdel.sd.sch.domain.Sch0040VO;

import java.util.List;

/**
 * On-Hand 청구계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sch0040D { 

	public List<Sch0040VO> SelectCharge(Sch0040ParamVO param);		// 청구
}
