package hdel.sd.sbi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.exception.BizException;
import hdel.sd.sbi.dao.Sbi0091D;
import hdel.sd.sbi.dao.Sbi0092D;
import hdel.sd.sbi.domain.ZSDT1139;

/**
 * �귣������ ��� Service Ŭ����
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
public class Sbi0091S {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmSqlSession srmQqlSession;	
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Sbi0091D sbi0091D;

	private Sbi0092D sbi0092D;
	
	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}


	/** 
	 * �귣�� ���� ��ȸ
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public List<ZSDT1139> findZsdt1139List(Map<String, Object> paramMap) throws Exception {
		List<ZSDT1139> zSdt1139List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0091D = session.getMapper(Sbi0091D.class);
		try {
			zSdt1139List = sbi0091D.selectZsdt1139List(paramMap);
			logger.debug("zSdt1139List:"+zSdt1139List);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return zSdt1139List;
	}
	
	/** 
	 * �귣�� ���� ����
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void saveZsdt1139(List<ZSDT1139> deleteZsdt1139, List<ZSDT1139> insertZsdt1139, List<ZSDT1139> updateZsdt1139) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0091D = session.getMapper(Sbi0091D.class);
		sbi0092D = session.getMapper(Sbi0092D.class);
		try {
			// �Է� �� üũ �߰�
			if(insertZsdt1139.size() > 0) {
				int maxBrndseq = new Integer(sbi0091D.selectMaxBrndseq(insertZsdt1139.get(0).getMANDT())).intValue();
				maxBrndseq = maxBrndseq + insertZsdt1139.size();
				if(maxBrndseq >= 1000) {
					throw new BizException("'1000'�̻� ������ �Է��� �� �����ϴ�.");
				}
			}

			for(int i=0; i < deleteZsdt1139.size(); i++) {
				// ���� �� �Ǽ��� üũ�Ѵ�.
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("MANDT", deleteZsdt1139.get(i).getMANDT());
				paramMap.put("BRNDSEQ", deleteZsdt1139.get(i).getBRNDSEQ());
				if(sbi0092D.selectBrndSeqZsdt1140Cnt(paramMap) == 0) {
					sbi0091D.deleteZsdt1139(deleteZsdt1139.get(i));
				} else {
					session.rollback();
					String message = "�ش� '"+deleteZsdt1139.get(i).getBRNDSEQ()+"' ������ �귣�� �������ڰ� ��ϵǾ� ������ �� �����ϴ�.";
					throw new BizException(message);
				}
			}
			
			for(int i=0; i < insertZsdt1139.size(); i++) {
				sbi0091D.insertZsdt1139(insertZsdt1139.get(i));
			}
			
			for(int i=0; i < updateZsdt1139.size(); i++) {
				sbi0091D.updateZsdt1139(updateZsdt1139.get(i));
			}
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.commit();
			session.close();
		}
	}
}
