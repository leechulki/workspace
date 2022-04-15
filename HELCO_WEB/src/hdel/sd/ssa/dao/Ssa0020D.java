package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0020;
import hdel.sd.ssa.domain.Ssa0020ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


/**
 * ����ä�� �� �� ä����Ȳ(û����)(Ssa0020D) DAO
 * @Comment 
 *		- List selectListZclose  	( �������� ��ȸ ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


public interface Ssa0020D {  
	
	// �������� ��ȸ
	public List<Ssa0020> selectListZclose(Ssa0020ParamVO param);
	
}
