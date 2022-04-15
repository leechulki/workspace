package hdel.sd.ssa.service;

/**
 * On-Hand �����ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.service.SrmService;
import hdel.sd.ssa.dao.Ssa0060D;
import hdel.sd.ssa.domain.Ssa0060ParamVO;
import hdel.sd.ssa.domain.Ssa0060VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Ssa0060S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Ssa0060D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Ssa0060D.class);
		
	}
 

	// ��ȸ
	public List<Ssa0060VO> find(Ssa0060ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");

		// �˻������� [�μ�]�� ���� �������
		String vkburYn = "N";
		if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
		{
			vkburYn = "Y";

			// ����
			return dao.SelectSalesExitWaerk(paramV);
		}
		else
		{

			// [WARERK - ��ȭ]�÷������� ���� �÷��� ���������Ͽ�
			// �÷������� �Ұ��÷��� ó��
			List<Ssa0060VO> list = dao.SelectWaerk(paramV);
			
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
					sofocaVal = "SUM(ZSDT1009.NETWR_SA)";
					
					sofoca += " WHEN VBAK.WAERK = '"+list.get(i).getWAERK()+"' THEN "+sofocaVal;
					sofoca += "\n";
					zpym += " WHEN VBAK.WAERK = '"+list.get(i).getWAERK()+"' THEN '000000'";
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

			// ����
			return dao.SelectSales(paramV);
		}

		
		
	}
	
}
