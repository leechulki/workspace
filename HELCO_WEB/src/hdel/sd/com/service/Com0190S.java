package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0190D;
import hdel.sd.com.domain.Com0190;
import hdel.sd.com.domain.Com0190ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


/**
 * 납품처목록 조회(Com0190S) Service
 * @Comment
 *     	1.  void createDao 
 *		2.  List find  	( 납품처목록 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Service
public class Com0190S extends SrmService {

	private Com0190D com0190Dao;
	
	public void createDao(SqlSession sqlSession) {
		com0190Dao = sqlSession.getMapper(Com0190D.class);
	}
	
	public List<Com0190> selectList(Com0190ParamVO param) {
		return com0190Dao.selectList(param);
	}  
	
}
