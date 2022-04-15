package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0062ParamVO;
import hdel.sd.sso.domain.Sso0062VO;

/**
 * ���ֻ��� ��系��(Sso0062D) DAO
 * @Comment 
 *		- List selectListZclose  	( �������� ��ȸ ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */

public interface Sso0062D {

	// �������� Detail ��ȸ
	public Sso0062VO selectQtnumDetail(Sso0062ParamVO param);
	
	public Sso0062VO selectZprdgbn(Sso0062ParamVO param);
}
