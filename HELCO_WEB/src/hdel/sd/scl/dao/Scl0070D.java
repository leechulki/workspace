package hdel.sd.scl.dao;

import hdel.sd.scl.domain.Scl0070ParamVO;
import hdel.sd.scl.domain.Scl0070VO;

import java.util.List;

/**
 * On-Hand 수금계획 현황
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Scl0070D { 

	public List<Scl0070VO> SelectCollection(Scl0070ParamVO param);		// 수금
}
