package hdel.sd.scl.service;

/**
 * On-Hand ���ݰ�ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.service.SrmService;
import hdel.sd.scl.dao.Scl0080D;
import hdel.sd.scl.domain.Scl0080ParamVO;
import hdel.sd.scl.domain.Scl0080VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Scl0080S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Scl0080D dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		dao = sqlSession.getMapper(Scl0080D.class);
		
	}
 

	// ��ȸ
	public List<Scl0080VO> find(Scl0080ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");

		// �˻������� [�μ�]�� ���� �������
		String vkburYn = "N";
		if ( !"".equals( paramV.getVKBUR_F() ) || !"".equals( paramV.getVKBUR_T() ) )
		{
			vkburYn = "Y";

			// ����
			return dao.SelectCollectionExitWaerk(paramV);
		}
		else
		{

			// [WARERK - ��ȭ]�÷������� ���� �÷��� ���������Ͽ�
			// �÷������� �Ұ��÷��� ó��
			List<Scl0080VO> list = dao.SelectWaerk(paramV);
			
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
					sofocaVal = "SUM(ZSDT1011.COLBI)";
					
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
			return dao.SelectCollection(paramV);
		}

		
		
	}
	
}
