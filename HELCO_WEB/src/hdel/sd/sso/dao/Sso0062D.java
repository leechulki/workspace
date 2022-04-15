package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0062ParamVO;
import hdel.sd.sso.domain.Sso0062VO;

/**
 * 수주생성 사양내용(Sso0062D) DAO
 * @Comment 
 *		- List selectListZclose  	( 마감여부 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */

public interface Sso0062D {

	// 견적정보 Detail 조회
	public Sso0062VO selectQtnumDetail(Sso0062ParamVO param);
	
	public Sso0062VO selectZprdgbn(Sso0062ParamVO param);
}
