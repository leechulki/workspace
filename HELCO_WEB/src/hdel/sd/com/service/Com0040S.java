package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0040D;
import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


/**
 * 거래선목록 조회(Com0040S) Service
 * @Comment
 *     	1.  void createDao 
 *		2.  List find  	( 거래선목록 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Service
public class Com0040S extends SrmService {

	private Com0040D com0040Dao;
	
	public void createDao(SqlSession sqlSession) {
		com0040Dao = sqlSession.getMapper(Com0040D.class);
	}
	
	public List<Com0040> find(Com0040ParamVO param) {
		return com0040Dao.selectListBuyr(param);
	}  
	
}
