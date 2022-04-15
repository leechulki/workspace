package hdel.sd.scl.dao;

import hdel.sd.scl.domain.Scl0080ParamVO;
import hdel.sd.scl.domain.Scl0080VO;

import java.util.List;

/**
 * On-Hand 수금계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Scl0080D {

	public List<Scl0080VO> SelectWaerk(Scl0080ParamVO param);					// 통화종류 조회
	public List<Scl0080VO> SelectCollection(Scl0080ParamVO param);				// 통화별 수금
	public List<Scl0080VO> SelectCollectionExitWaerk(Scl0080ParamVO param);		// 월별 수금
}
 