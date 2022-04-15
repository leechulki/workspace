package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0040ParamVO;
import hdel.sd.ssa.domain.Ssa0040;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


/**
 * ���ݰ�꼭Cû����Ȳ(Ssa0040D) DAO
 * @Comment 
 *		- List reason_list  	( �����ڵ� ��ȸ ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.11.13  ������  :  initial version 
 * @version 1.0
 */


public interface Ssa0040D {  
	
	//��������
	public List<Ssa0040> selectListAuart(Ssa0040ParamVO param);
	
	// �����ڵ� ��ȸ
	public List<Ssa0040> selectListReason();
	
}
