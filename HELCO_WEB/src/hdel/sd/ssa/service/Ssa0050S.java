package hdel.sd.ssa.service;

/**
 * On-Hand �����ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.service.SrmService;
import hdel.sd.ssa.dao.Ssa0050D;
import hdel.sd.ssa.domain.Ssa0050ParamVO;
import hdel.sd.ssa.domain.Ssa0050VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Ssa0050S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Ssa0050D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Ssa0050D.class);
		
	} 

	// ��ȸ
	public List<Ssa0050VO> find(Ssa0050ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		// ����
		return dao.SelectSales(paramV);
		
	}
	
}
