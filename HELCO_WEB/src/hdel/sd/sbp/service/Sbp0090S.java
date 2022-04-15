package hdel.sd.sbp.service;

/**
 * �����ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.service.SrmService;
import hdel.sd.sbp.dao.Sbp0090D;
import hdel.sd.sbp.domain.Sbp0090ParamVO;
import hdel.sd.sbp.domain.Sbp0090VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Sbp0090S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sbp0090D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sbp0090D.class);
		
	} 

	// ��ȸ
	public List<Sbp0090VO> find(Sbp0090ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		// ����
		if ( "1".equals(paramV.getWHERE()) )
		{
			return dao.SelectOrder(paramV);
		}
		// ����
		else if ( "2".equals(paramV.getWHERE()) )
		{
			return dao.SelectSales(paramV);
		}
		// û��
		else if ( "3".equals(paramV.getWHERE()) )
		{
			return dao.SelectCharge(paramV);
		}
		// ����
		else
		{
			return dao.SelectCollection(paramV);
		}
		
	}

	public Float getExchangeBPUSD(String mandt, String bpyear) {
		return dao.getExchangeBPUSD(mandt, bpyear);
	}
}
