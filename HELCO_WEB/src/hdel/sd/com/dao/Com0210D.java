package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0210;
import hdel.sd.com.domain.Com0210ParamVO;

import java.util.List;
 
/**
 * 수주계획번호 조회 (Com0210D) DAO
 * @Comment
 *     	1. List selectListSonnr ( 수주계획번호 조회 )
 * @since 1.0
 * 		History
 * 		1.0  2012.08.01  김재영  :  initial version
 * @version 1.0
 */

public interface Com0210D {

	public List<Com0210> selectListSonnr(Com0210ParamVO param);	// 거래처목록 조회
}
