package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0190;
import hdel.sd.com.domain.Com0190ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
 
/**
 * 납품처목록 조회 (Com0190D) DAO
 * @Comment 
 *     	1. List selectListBuyr ( 납품처목록 조회 )
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */

public interface Com0190D {  
	
	// 납품처목록 조회
	public List<Com0190> selectList(Com0190ParamVO param);
	
}
