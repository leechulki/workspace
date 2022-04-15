package hdel.sd.sso.service;
 
import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0051D;
import hdel.sd.sso.domain.Sso0051;
import hdel.sd.sso.domain.Sso0051ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;



/**
 * ��纹�� (Sso0051S) Service
 * @Comment
 *     	1.  void createDao  
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2013.03.11  �輱ȣ  :  initial version 
 * @version 1.0
 */


@Service
public class Sso0051S extends SrmService {
	
	Logger logger = Logger.getLogger(this.getClass());

	private Sso0051D dao;
	
	public void createDao(SqlSession sqlSession) { 
		dao = sqlSession.getMapper(Sso0051D.class);
	} 
	
	// ȣ���ȣ��  ���������� ��� ��ȸ
	public List<Sso0051> selectListSpec(Sso0051ParamVO paramV) {  
		return dao.selectListSpec(paramV); 
	}	
}
