package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0051;
import hdel.sd.sso.domain.Sso0051ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


/**
 * ��纹��(Sso0051D) DAO
 * @Comment 
 *		- List selectListSpec 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2013.03.11  �輱ȣ  :  initial version 
 * @version 1.0
 */


public interface Sso0051D {  
	
	// ������Ʈ��ȣ�� ������ȣ ��ȸ
	public List<Sso0051> selectListSpec(Sso0051ParamVO param);
	
}
