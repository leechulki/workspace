package hdel.sd.smp.service;

/**
 * �̵���ȹ
 * �̵���ȹ ��
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.service.SrmService;
import hdel.sd.smp.dao.Smp0010D;
import hdel.sd.smp.domain.Smp0010ParamVO;
import hdel.sd.smp.domain.SmpComParameterVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

@Service
public class Smp0010S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Smp0010D Smp0010D;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		Smp0010D = sqlSession.getMapper(Smp0010D.class);
		
	}

	// ��ü ��ȸ
	public List<Smp0010ParamVO> find(Smp0010ParamVO param) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		return Smp0010D.find(param);
	}

	// ����
	public void update(Smp0010ParamVO param) {
		logger.info("@@@@@@@@@@ update service in -> dao @@@@@@@@@");
		Smp0010D.update(param);
	}

	// �߰�
	public void insert(Smp0010ParamVO param) {
		logger.info("@@@@@@@@@@ insert service in -> dao @@@@@@@@@");
		Smp0010D.insert(param);
	}

	// ����
	public void delete(Smp0010ParamVO param) {
		logger.info("@@@@@@@@@@ delete service in -> dao @@@@@@@@@");
		Smp0010D.delete(param);
	}
	
	// �ڵ� ��ȸ
	public List<SmpComParameterVO> teamCd(SmpComParameterVO param) {
		logger.info("@@@@@@@@@@ teamCd service in -> dao @@@@@@@@@");
		return Smp0010D.teamCd(param);
	}

	
}
