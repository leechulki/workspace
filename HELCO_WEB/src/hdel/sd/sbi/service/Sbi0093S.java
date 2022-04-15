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
 * 특성표출 그룹 Service 클래스
 * 
 * @author  박수근
 * @since 2020.08.20
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.09.01         박수근          최초 생성
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
	 * 특성그룹 조회
	 * @param ZPRDGBN(제품구분)
	 * @return List<ZSDT1143>
	 * @throws Exception
	 */
	public List<ZSDT1143> selectZsdt1143List(Map<String, Object> param) throws Exception {
		List<ZSDT1143> zSdt1143List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);

		// dao 서비스 선언
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
	 * 브랜드 소그룹 타이틀 저장
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
			
			// 원본 데이터 저장 완료 후 EAI 연계 데이터 생성한다.
			// 영문 타이틀 정보를 전송한다.
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
	 * 브랜드 소그룹 타이틀 연동데이터 저장
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
