package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0180D;
import hdel.sd.com.domain.Com0180;
import hdel.sd.com.domain.Com0180ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

/**
 * 공사번호 조회(Com0180S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List find  	( 공사번호 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.08.01  김재영  :  initial version 
 * @version 1.0
 */

@Service
public class Com0180S extends SrmService {

	private Com0180D com0180Dao;

	public void createDao(SqlSession sqlSession) {
		com0180Dao = sqlSession.getMapper(Com0180D.class);
	}
	
	public List<Com0180> find(Com0180ParamVO param) {
		return com0180Dao.selectListBuyr(param);
	}
}
