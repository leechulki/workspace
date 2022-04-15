package hdel.sd.sbi.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tobesoft.platform.data.Dataset;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.dao.Coms01AD;
import hdel.sd.sbi.dao.Sbi0095D;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.sbi.domain.ZSDT1144;

/**
 * 브랜드-기종 등록 Service 클래스
 * 
 * @author  박수근
 * @since 2020.09.25
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.09.25         박수근          최초 생성
 * 
 * </pre>
 */
@Service
public class Sbi0095S {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmSqlSession srmQqlSession;	
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Sbi0095D sbi0095D;

	private Coms01AD Coms01ADao;
	
	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	/** 
	 * 브랜드-기종 초기값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void initSbi0095(MipParameterVO paramVO, MipResultVO resultVO) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		
		Coms01ADao =  session.getMapper(Coms01AD.class);
		String gMandt = paramVO.getVariable("G_MANDT");
		String gLang  = paramVO.getVariable("G_LANG");
		try {
			// 브랜드코드
			Dataset dsAbrand = paramVO.getDataset("ds_abrand");
			ComboParamVO param = new ComboParamVO();
			param.setMandt(gMandt);  
			param.setLang(gLang);
			param.setVkbur("EL_ABRAND");
			List<ComboVO> brndList = Coms01ADao.selectAtwrt(param); 
			dsAbrand.deleteAll();
			int newRow = 0;
			for (int iRow=0;iRow < brndList.size();iRow++) {    
				newRow = dsAbrand.appendRow();   
				dsAbrand.setColumn(newRow, "CODE"		, brndList.get(iRow).getCode()); 	 // 코드
				dsAbrand.setColumn(newRow, "CODE_NAME", brndList.get(iRow).getCodeName()); // 코드명 
			}
			resultVO.addDataset(dsAbrand);

			// 기종
			Dataset dsGtype = paramVO.getDataset("ds_gtype");
			param.setMandt(gMandt);  
			param.setLang(gLang);
			param.setVkbur("EL_ATYP");
			List<ComboVO> gtypeList = Coms01ADao.selectAtwrt(param); 
			dsGtype.deleteAll();
			newRow = 0;
			for (int iRow=0;iRow < gtypeList.size();iRow++) {    
				newRow = dsGtype.appendRow();   
				dsGtype.setColumn(newRow, "CODE"		, gtypeList.get(iRow).getCode()); 	 // 코드
				dsGtype.setColumn(newRow, "CODE_NAME", gtypeList.get(iRow).getCodeName()); // 코드명 
			}
			resultVO.addDataset(dsGtype);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	/** 
	 * 브랜드-기종 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public List<ZSDT1144> findZsdt1144List(Map<String, Object> paramMap) throws Exception {
		List<ZSDT1144> zSdt1144List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0095D = session.getMapper(Sbi0095D.class);
		try {
			zSdt1144List = sbi0095D.selectZsdt1144List(paramMap);
			logger.debug("zSdt1144List:"+zSdt1144List);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return zSdt1144List;
	}

	/** 
	 * 브랜드-기종 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void saveZsdt1144(List<ZSDT1144> deleteZsdt1144, List<ZSDT1144> insertZsdt1144, List<ZSDT1144> updateZsdt1144) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0095D = session.getMapper(Sbi0095D.class);
		try {
			for(int i=0; i < deleteZsdt1144.size(); i++) {
				sbi0095D.deleteZsdt1144(deleteZsdt1144.get(i));
			}

			for(int i=0; i < insertZsdt1144.size(); i++) {
				sbi0095D.insertZsdt1144(insertZsdt1144.get(i));
			}

			for(int i=0; i < updateZsdt1144.size(); i++) {
				sbi0095D.insertZsdt1144(updateZsdt1144.get(i));
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
