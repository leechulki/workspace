package hdel.sd.smp.service;

/**
 * �̵���ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.service.SrmService;
import hdel.sd.smp.dao.Smp0110D;
import hdel.sd.smp.domain.Smp0110ParamVO;
import hdel.sd.smp.domain.Smp0110VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Smp0110S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Smp0110D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Smp0110D.class);
		
	}
 

	// ��ȸ
	public List<Smp0110VO> find(Smp0110ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");

		// �˻������� [�μ�]�� ���� �������
		String vkburYn = "N";
		if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
		{
			vkburYn = "Y";
			 
		}
		else
		{

			// [WARERK - ��ȭ]�÷������� ���� �÷��� ���������Ͽ�
			// �÷������� �Ұ��÷��� ó��
			List<Smp0110VO> list = dao.SelectWaerk(paramV);
			
			String sofoca 	= null;
			String zpym 	= null;
			
			logger.info("@@@@@@@@ list.size() :"+list.size());
			if ( list.size() > 0 )
			{
				sofoca 	= "CASE";
				zpym 	= "CASE";
				for ( int i = 0 ; i < list.size() ; i++ )
				{
					String sofocaVal = null;
					// ����
					if ( "1".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1036.SOFOCA)";
					}
					// ����
					else if ( "2".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1037.NETWR_SA)";
					}
					// û��
					else if ( "3".equals(paramV.getWHERE()) )
					{
						sofocaVal = "SUM(ZSDT1038.NETWR_RQ)";
					}
					// ����
					else
					{
						sofocaVal = "SUM(ZSDT1039.COLBI)";
					}
					sofoca += " WHEN ZSDT1036.WAERK = '"+list.get(i).getWAERK()+"' THEN "+sofocaVal;
					sofoca += "\n";
					zpym += " WHEN ZSDT1036.WAERK = '"+list.get(i).getWAERK()+"' THEN '000000'";
					zpym += "\n";
				}
				sofoca += "END AS SOFOCA";
				zpym += "END AS ZPYM";
			}
			else
			{
				sofoca 	= "0 AS SOFOCA";
				zpym 	= "'' AS ZPYM";
			}
			
			paramV.setFILTER1(sofoca);
			paramV.setFILTER2(zpym);
			logger.info(" @@@@@@@@@@@@@@ filter query : "+sofoca);
			logger.info(" @@@@@@@@@@@@@@ filter query : "+zpym);
		}
		
		// ����
		if ( "1".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectOrder(paramV);
		}
		else if ( "1".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectOrderExitWaerk(paramV);
		}
		// ����
		else if ( "2".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectSales(paramV);
		}
		else if ( "2".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectSalesExitWaerk(paramV);
		}
		// û��
		else if ( "3".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectCharge(paramV);
		}
		else if ( "3".equals(paramV.getWHERE()) && "Y".equals(vkburYn) )
		{
			return dao.SelectChargeExitWaerk(paramV);
		}
		// ����
		else if ( "4".equals(paramV.getWHERE()) && "N".equals(vkburYn) )
		{
			return dao.SelectCollection(paramV);
		}
		else 
		{
			return dao.SelectCollectionExitWaerk(paramV);
		}
		
	}
	
}
