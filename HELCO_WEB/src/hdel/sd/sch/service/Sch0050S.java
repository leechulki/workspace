package hdel.sd.sch.service;

/**
 * On-Hand û����ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.service.SrmService;
import hdel.sd.sch.dao.Sch0050D;
import hdel.sd.sch.domain.Sch0050ParamVO;
import hdel.sd.sch.domain.Sch0050VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Sch0050S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sch0050D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Sch0050D.class);
		
	}
 

	// ��ȸ
	public List<Sch0050VO> find(Sch0050ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");

		// �˻������� [�μ�]�� ���� �������
		String vkburYn = "N";
		if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
		{
			vkburYn = "Y";

			// û��
			return dao.SelectChargeExitWaerk(paramV);
		}
		else
		{

			// [WARERK - ��ȭ]�÷������� ���� �÷��� ���������Ͽ�
			// �÷������� �Ұ��÷��� ó��
			List<Sch0050VO> list = dao.SelectWaerk(paramV);
			
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
					
					// û��
					sofocaVal = "SUM(ZSDT1010.NETWR_RQ)";
					
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
			
			// û��
			return dao.SelectCharge(paramV);
		}
		
		
	}
	
}
