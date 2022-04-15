package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0020;
import hdel.sd.ssa.domain.Ssa0020ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


/**
 * 매출채권 명세 및 채권현황(청구별)(Ssa0020D) DAO
 * @Comment 
 *		- List selectListZclose  	( 마감여부 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public interface Ssa0020D {  
	
	// 마감일자 조회
	public List<Ssa0020> selectListZclose(Ssa0020ParamVO param);
	
}
