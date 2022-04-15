package hdel.sd.scl.service;

/**
 * On-Hand ���ݰ�ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.service.SrmService;
import hdel.sd.scl.dao.Scl0070D;
import hdel.sd.scl.domain.Scl0070ParamVO;
import hdel.sd.scl.domain.Scl0070VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Scl0070S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Scl0070D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Scl0070D.class);
		
	} 

	// ��ȸ
	public List<Scl0070VO> find(Scl0070ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		// ����
		return dao.SelectCollection(paramV);
		
	}
	
}
