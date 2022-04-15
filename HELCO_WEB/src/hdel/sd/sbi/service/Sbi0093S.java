package hdel.sd.sbi.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hdel.lib.dao.SrmSqlSession;
import hdel.sd.sbi.dao.Sbi0093D;
import hdel.sd.sbi.domain.ZSDT1143;

/**
 * Ư��ǥ�� �׷� Service Ŭ����
 * 
 * @author  �ڼ���
 * @since 2020.08.20
 * @version 1.0
 * @see 
 * <pre>
 *  == �����̷�(Modification Information) ==
 *   
 *          ������          ������           ��������
 *  ----------------    ------------    ---------------------------
 *   2020.09.01         �ڼ���          ���� ����
 * 
 * </pre>
 */
@Service
public class Sbi0093S {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmSqlSession srmQqlSession;
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Sbi0093D sbi0093D;

	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	/** 
	 * Ư���׷� ��ȸ
	 * @param ZPRDGBN(��ǰ����)
	 * @return List<ZSDT1143>
	 * @throws Exception
	 */
	public List<ZSDT1143> selectZsdt1143List(Map<String, Object> param) throws Exception {
		List<ZSDT1143> zSdt1143List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);

		// dao ���� ����
		sbi0093D = session.getMapper(Sbi0093D.class);
		try {
			logger.debug("param:"+param);
			zSdt1143List = sbi0093D.selectZsdt1143List(param);
			logger.debug("zSdt1143List:"+zSdt1143List);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return zSdt1143List; 
	}
	
	/** 
	 * �귣�� �ұ׷� Ÿ��Ʋ ����
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void saveZsdt1143(List<ZSDT1143> deleteZsdt1143, List<ZSDT1143> insertZsdt1143, List<ZSDT1143> updateZsdt1143) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0093D = session.getMapper(Sbi0093D.class);
		try {
			for(int i=0; i < deleteZsdt1143.size(); i++) {
				sbi0093D.deleteZsdt1143(deleteZsdt1143.get(i));
			}

			for(int i=0; i < insertZsdt1143.size(); i++) {
				sbi0093D.insertZsdt1143(insertZsdt1143.get(i));
			}

			for(int i=0; i < updateZsdt1143.size(); i++) {
				sbi0093D.updateZsdt1143(updateZsdt1143.get(i));
			}
			
			// ���� ������ ���� �Ϸ� �� EAI ���� ������ �����Ѵ�.
			// ���� Ÿ��Ʋ ������ �����Ѵ�.
			saveEaiZsdt1143(deleteZsdt1143, insertZsdt1143, updateZsdt1143);			

		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.commit();
			session.close();
		}
	}
	
	/** 
	 * �귣�� �ұ׷� Ÿ��Ʋ ���������� ����
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	private void saveEaiZsdt1143(List<ZSDT1143> deleteZsdt1143, List<ZSDT1143> insertZsdt1143, List<ZSDT1143> updateZsdt1143) throws Exception {
		for(int i=0; i < deleteZsdt1143.size(); i++) {
			sbi0093D.insertZeaitZsdt1143(deleteZsdt1143.get(i));
		}

		for(int i=0; i < insertZsdt1143.size(); i++) {
			sbi0093D.insertZeaitZsdt1143(insertZsdt1143.get(i));
		}

		for(int i=0; i < updateZsdt1143.size(); i++) {
			sbi0093D.insertZeaitZsdt1143(updateZsdt1143.get(i));
		}
	}
	
}
