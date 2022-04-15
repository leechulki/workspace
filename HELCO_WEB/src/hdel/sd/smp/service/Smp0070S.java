package hdel.sd.smp.service;

/**
 * �̵���ȹ ����/����
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.smp.dao.Smp0070D;
import hdel.sd.smp.domain.Smp0070ParamVO;
import hdel.sd.smp.domain.Smp0070VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Smp0070S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Smp0070D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Smp0070D.class);
		
	}

	// ��ȸ
	public List<Smp0070VO> find(Smp0070ParamVO param) 
	{
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		return dao.Select(param);
	}
	
	// copy
	public int saveCopy(Smp0070ParamVO param)
	{
		int rs = 0; // �����Ǽ�
		
		logger.info("@@@@@@@@@@ copyOrder service in -> dao @@@@@@@@@");
		
		rs = dao.InsertCopyOrder(param);				// ����
		logger.info("@@@@@@@@@@ ���� :"+rs);
		//if( rs < 0){throw new BizException("CW10022,����(����)");}

		rs = dao.InsertCopyOrderRepair(param);			// ����(����)
		//if( rs < 0){throw new BizException("CW10022,����(����_����)");}
		
		logger.info("@@@@@@@@@@ copySales service in -> dao @@@@@@@@@");
		
		rs = dao.InsertCopySales(param);				// ����
		//if( rs < 0){throw new BizException("CW10022,����(����)");}

		rs = dao.InsertCopySalesRepair(param);			// ����(����)
		//if( rs < 0){throw new BizException("CW10022,����(����_����)");}
		
		logger.info("@@@@@@@@@@ copyCharge service in -> dao @@@@@@@@@");
		
		rs = dao.InsertCopyCharge(param);				// û��
		//if( rs < 0){throw new BizException("CW10022,����(û��)");}
		
		rs = dao.InsertCopyChargeRepair(param);			// û��(����)
		//if( rs < 0){throw new BizException("CW10022,����(û��_����)");}
		
		logger.info("@@@@@@@@@@ copyCollection service in -> dao @@@@@@@@@");
		
		rs = dao.InsertCopyCollection(param);			// ����
		//if( rs < 0){throw new BizException("CW10022,����(����)");}
		
		rs = dao.InsertCopyCollectionRepair(param);		// ����(����)
		//if( rs < 0){throw new BizException("CW10022,����(����_����)");}
		
		
		return rs;
	}

	// cancel
	public int saveCancel(Smp0070ParamVO param)
	{
		int rs = 0;
		
		logger.info("@@@@@@@@@@ cancelOrder service in -> dao @@@@@@@@@");
		
		rs = dao.DeleteCancelOrder(param);					// ����
		//if( rs < 0){throw new BizException("CW10022,���(����)");}
		
		rs = dao.DeleteCancelOrderRepair(param);			// ����(����)
		//if( rs < 0){throw new BizException("CW10022,���(����_����)");}
		
		logger.info("@@@@@@@@@@ cancelSales service in -> dao @@@@@@@@@");
		
		rs = dao.DeleteCancelSales(param);					// ����
		//if( rs < 0){throw new BizException("CW10022,���(����)");}
		
		rs = dao.DeleteCancelSalesRepair(param);			// ����(����)
		//if( rs < 0){throw new BizException("CW10022,���(����_����)");}
		
		logger.info("@@@@@@@@@@ cancelCharge service in -> dao @@@@@@@@@");
		
		rs = dao.DeleteCancelCharge(param);					// û��
		//if( rs < 0){throw new BizException("CW10022,���(û��)");}
		
		rs = dao.DeleteCancelChargeRepair(param);			// û��(����)
		//if( rs < 0){throw new BizException("CW10022,���(û��_����)");}
		
		logger.info("@@@@@@@@@@ cancelCollection service in -> dao @@@@@@@@@");
		
		rs = dao.DeleteCancelCollection(param);				// ����
		//if( rs < 0){throw new BizException("CW10022,���(����)");}
		
		rs = dao.DeleteCancelCollectionRepair(param);		// ����(����)
		//if( rs < 0){throw new BizException("CW10022,���(����_����)");}
		
		
		return rs;
	}

	// ������ ����
	public int saveCopyNext(Smp0070ParamVO param) {
		logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		return dao.MergeCopyNext(param);
	}

	// ����� ����
	public int saveCancelNext(Smp0070ParamVO param) {
		logger.info("@@@@@@@@@@ saveCancelNext service in -> dao @@@@@@@@@");
		
		int rs = 0;
		
		// ����
		if ( "update".equals( param.getROW_TYPE() ) ) 
		{
			logger.info("@@@@@@@@@@ cancelNextUpd service in -> dao @@@@@@@@@");
			rs = dao.MergeCancelNextUpd(param);
		}
		// ����
		else
		{
			logger.info("@@@@@@@@@@ cancelNextDel service in -> dao @@@@@@@@@");
			rs = dao.MergeCancelNextDel(param);
		}
		return rs;
	}
	
	
}
