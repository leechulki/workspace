package hdel.sd.ssa.service;

import hdel.lib.service.SrmService;
import hdel.sd.ssa.dao.Ssa0020D;
import hdel.sd.ssa.domain.Ssa0020;
import hdel.sd.ssa.domain.Ssa0020ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


/**
 * 매출채권 명세 및 채권현황(청구별)(Ssa0020S) Service
 * @Comment
 *     	1.  void createDao 
 *		2.  List find_zclose  	( 마감여부 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Service
public class Ssa0020S extends SrmService {

	private Ssa0020D ssa0020Dao;
	
	public void createDao(SqlSession sqlSession) {
		ssa0020Dao = sqlSession.getMapper(Ssa0020D.class);
	}
	
	public List<Ssa0020> find_zclose(Ssa0020ParamVO param) {
		return ssa0020Dao.selectListZclose(param);
	}  
	
}
