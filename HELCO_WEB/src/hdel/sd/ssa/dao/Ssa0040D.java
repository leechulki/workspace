package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0040ParamVO;
import hdel.sd.ssa.domain.Ssa0040;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


/**
 * 세금계산서C청구현황(Ssa0040D) DAO
 * @Comment 
 *		- List reason_list  	( 사유코드 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.11.13  구란이  :  initial version 
 * @version 1.0
 */


public interface Ssa0040D {  
	
	//오더유형
	public List<Ssa0040> selectListAuart(Ssa0040ParamVO param);
	
	// 사유코드 조회
	public List<Ssa0040> selectListReason();
	
}
