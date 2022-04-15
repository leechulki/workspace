package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0210D;
import hdel.sd.com.domain.Com0210;
import hdel.sd.com.domain.Com0210ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

/**
 * 수주계획번호 조회(Com0210S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List find  	( 수주계획번호 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.08.29  김재영  :  initial version 
 * @version 1.0
 */

@Service
public class Com0210S extends SrmService {

	private Com0210D com0210Dao;

	public void createDao(SqlSession sqlSession) {
		com0210Dao = sqlSession.getMapper(Com0210D.class);
	}

	public List<Com0210> find(Com0210ParamVO param) {
		return com0210Dao.selectListSonnr(param);
	}
}
