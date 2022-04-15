package hdel.sd.sbi.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.dao.ComboD;
import hdel.sd.sbi.dao.Sbi0090D;
import hdel.sd.sbi.dao.Sbi0091D;
import hdel.sd.sbi.domain.SanyangPrd;
import hdel.sd.sbi.domain.SanyangPrh;
import hdel.sd.sbi.domain.ZSDT1141;
import hdel.sd.sbi.domain.ZSDT1142;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;

/**
 * 브랜드 등록 Service 클래스
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
 *   2020.08.20         박수근          최초 생성
 * 
 * </pre>
 */
@Service
public class Sbi0090S {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmSqlSession srmQqlSession;	
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Sbi0090D sbi0090D;

	private Sbi0091D sbi0091D;
	
	private ComboD commonD;
	
	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	/** 
	 * 브랜드 적용일자 초기값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void initSbi0090(MipParameterVO paramVO, MipResultVO resultVO) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		
		sbi0090D   = session.getMapper(Sbi0090D.class);
		sbi0091D   = session.getMapper(Sbi0091D.class);
		commonD    = session.getMapper(ComboD.class);
		String gMandt = paramVO.getVariable("G_MANDT");
		String gLang  = paramVO.getVariable("G_LANG");
		try {
			// 브랜드 차수
			String maxBrndSeq = sbi0091D.selectMaxBrndseq(gMandt);
			resultVO.addVariable("F_BRNDSEQ", maxBrndSeq);

			// 브랜드코드
			Dataset dsAbrand = paramVO.getDataset("ds_abrand");
			ComboParamVO param = new ComboParamVO();
			param.setMandt(gMandt);  
			param.setLang(gLang);
			param.setVkgrp(maxBrndSeq);
			param.setVkbur("EL_ABRAND");
			List<ComboVO> brndList = sbi0090D.selectBrndSeqAtwrt(param);
			dsAbrand.deleteAll();
			
			int newRow = 0;
			// NOBRND 로우 추가
			for (int iRow=0;iRow < brndList.size();iRow++) {    
				newRow = dsAbrand.appendRow();   
				dsAbrand.setColumn(newRow, "CODE"		, brndList.get(iRow).getCode()); 	 // 코드
				dsAbrand.setColumn(newRow, "CODE_NAME", brndList.get(iRow).getCodeName()); // 코드명 
			}
			resultVO.addDataset(dsAbrand);

			// 제품구분
			Dataset dsZtplgbn = paramVO.getDataset("ds_zprdgnb");
			param.setMclass("");
			List<ComboVO> ztplgbnList = commonD.selectClass(param);
			dsZtplgbn.deleteAll();
			for (int iRow=0;iRow < ztplgbnList.size();iRow++) {    
				dsZtplgbn.appendRow();   
				dsZtplgbn.setColumn(iRow, "CODE"		, ztplgbnList.get(iRow).getCode());  // 코드
				dsZtplgbn.setColumn(iRow, "CODE_NAME", ztplgbnList.get(iRow).getCodeName()); // 코드명 
			}

			newRow = dsZtplgbn.appendRow();
			dsZtplgbn.setColumn(newRow, "CODE", "ELV_L1");
			dsZtplgbn.setColumn(newRow, "CODE_NAME", "ELV_L1"); 
			newRow = dsZtplgbn.appendRow();
			dsZtplgbn.setColumn(newRow, "CODE", "SHN_01");
			dsZtplgbn.setColumn(newRow, "CODE_NAME", "SHN_01"); 
			newRow = dsZtplgbn.appendRow();
			dsZtplgbn.setColumn(newRow, "CODE", "SHN_02");
			dsZtplgbn.setColumn(newRow, "CODE_NAME", "SHN_02"); 
			newRow = dsZtplgbn.appendRow();
			dsZtplgbn.setColumn(newRow, "CODE", "WL_01");
			dsZtplgbn.setColumn(newRow, "CODE_NAME", "WL_01"); 

			resultVO.addDataset(dsZtplgbn);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	/** 
	 * 브랜드차수별 브랜드코드
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void findAbrnd(MipParameterVO paramVO, MipResultVO resultVO) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		
		sbi0090D   = session.getMapper(Sbi0090D.class);
		String gMandt = paramVO.getVariable("G_MANDT");
		String gLang  = paramVO.getVariable("G_LANG");
		String gBrndseq = paramVO.getVariable("BRNDSEQ");
		try {
			// 브랜드코드
			Dataset dsAbrand = paramVO.getDataset("ds_abrand");
			ComboParamVO param = new ComboParamVO();
			param.setMandt(gMandt);  
			param.setLang(gLang);
			param.setVkgrp(gBrndseq);
			param.setVkbur("EL_ABRAND");
			List<ComboVO> brndList = sbi0090D.selectBrndSeqAtwrt(param);
			dsAbrand.deleteAll();

			int newRow = 0;
			// NOBRND 로우 추가
			for (int iRow=0;iRow < brndList.size();iRow++) {    
				newRow = dsAbrand.appendRow();   
				dsAbrand.setColumn(newRow, "CODE"		, brndList.get(iRow).getCode()); 	 // 코드
				dsAbrand.setColumn(newRow, "CODE_NAME", brndList.get(iRow).getCodeName()); // 코드명 
			}
			resultVO.addDataset(dsAbrand);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	/** 
	 * 브랜드 영업특성 조회
	 * @param Map<String, Object> paramMap
	 * @return List<Com040SanyangPrh>
	 * @throws Exception
	 */
	public List<SanyangPrh> findZsdt1141List(Map<String, Object> paramMap) throws Exception {
		List<SanyangPrh> zSdt1141List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0090D = session.getMapper(Sbi0090D.class);
		try {
			zSdt1141List = sbi0090D.selectZsdt1141List(paramMap);
			logger.debug("zSdt1141List:"+zSdt1141List);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return zSdt1141List;
	}

	/** 
	 * 브랜드 영업특성값 조회
	 * @param Map<String, Object> paramMap
	 * @return List<Com040SanyangPrd>
	 * @throws Exception
	 */
	public List<SanyangPrd> findZsdt1142List(Map<String, Object> paramMap) throws Exception {
		List<SanyangPrd> zSdt1142List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0090D = session.getMapper(Sbi0090D.class);
		try {
			zSdt1142List = sbi0090D.selectZsdt1142List(paramMap);
			logger.debug("zSdt1142List:"+zSdt1142List);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return zSdt1142List;
	}
	
	/** 
	 * 브랜드 엑셀다운로드 영업특성값 조회
	 * @param Map<String, Object> paramMap
	 * @return List<Com040SanyangPrd>
	 * @throws Exception
	 */
	public List<SanyangPrd> excelZsdt1142List(Map<String, Object> paramMap) throws Exception {
		List<SanyangPrd> zSdt1142List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0090D = session.getMapper(Sbi0090D.class);
		try {
			zSdt1142List = sbi0090D.selectExcelZsdt1142List(paramMap);
			logger.debug("zSdt1142List:"+zSdt1142List);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return zSdt1142List;
	}
	
	/** 
	 * 브랜드 영업특성, 특성값 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void saveSayang(List<ZSDT1141> deleteZsdt1141, List<ZSDT1141> insertZsdt1141, List<ZSDT1141> updateZsdt1141
			               ,List<ZSDT1142> deleteZsdt1142, List<ZSDT1142> insertZsdt1142, List<ZSDT1142> updateZsdt1142) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		
		sbi0090D = session.getMapper(Sbi0090D.class);
		try {
			// 영업특성 delete, insert, update 처리
			for(int i=0; i < deleteZsdt1141.size(); i++) {
				ZSDT1141 zsdt1141 = deleteZsdt1141.get(i);
				sbi0090D.deleteZsdt1141(zsdt1141);
			}
			for(int i=0; i < insertZsdt1141.size(); i++) {
				ZSDT1141 zsdt1141 = insertZsdt1141.get(i);
				sbi0090D.insertZsdt1141(zsdt1141);
			}
			for(int i=0; i < updateZsdt1141.size(); i++) {
				ZSDT1141 zsdt1141 = updateZsdt1141.get(i);
				sbi0090D.updateZsdt1141(zsdt1141);
			}

			// 영업특성값 delete, insert, update 처리
			for(int i=0; i < deleteZsdt1142.size(); i++) {
				ZSDT1142 zsdt1142 = deleteZsdt1142.get(i);
				sbi0090D.deleteZsdt1142(zsdt1142);
			}
			for(int i=0; i < insertZsdt1142.size(); i++) {
				ZSDT1142 zsdt1142 = insertZsdt1142.get(i);
				sbi0090D.insertZsdt1142(zsdt1142);
			}
			for(int i=0; i < updateZsdt1142.size(); i++) {
				ZSDT1142 zsdt1142 = updateZsdt1142.get(i);
				sbi0090D.updateZsdt1142(zsdt1142);
			}
			
			// 원본 데이터 저장 완료 후 EAI 연계 데이터 생성한다.
			saveEaiSayang(deleteZsdt1141, insertZsdt1141, updateZsdt1141
		                 ,deleteZsdt1142, insertZsdt1142, updateZsdt1142);
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.commit();
			session.close();
		}
	}
	
	/** 
	 * 브랜드 영업특성, 특성값 연동 데이터 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	private void saveEaiSayang(List<ZSDT1141> deleteZsdt1141, List<ZSDT1141> insertZsdt1141, List<ZSDT1141> updateZsdt1141
            ,List<ZSDT1142> deleteZsdt1142, List<ZSDT1142> insertZsdt1142, List<ZSDT1142> updateZsdt1142) throws Exception {
		for(int i=0; i < deleteZsdt1141.size(); i++) {
			ZSDT1141 zsdt1141 = deleteZsdt1141.get(i);
			sbi0090D.insertZeaitZsdt1141(zsdt1141);
		}

		for(int i=0; i < insertZsdt1141.size(); i++) {
			ZSDT1141 zsdt1141 = insertZsdt1141.get(i);
			sbi0090D.insertZeaitZsdt1141(zsdt1141);
		}

		for(int i=0; i < updateZsdt1141.size(); i++) {
			ZSDT1141 zsdt1141 = updateZsdt1141.get(i);
			sbi0090D.insertZeaitZsdt1141(zsdt1141);
		}
		
		for(int i=0; i < deleteZsdt1142.size(); i++) {
			ZSDT1142 zsdt1142 = deleteZsdt1142.get(i);
			sbi0090D.insertZeaitZsdt1142(zsdt1142);
		}

		for(int i=0; i < insertZsdt1142.size(); i++) {
			ZSDT1142 zsdt1142 = insertZsdt1142.get(i);
			sbi0090D.insertZeaitZsdt1142(zsdt1142);
		}

		for(int i=0; i < updateZsdt1142.size(); i++) {
			ZSDT1142 zsdt1142 = updateZsdt1142.get(i);
			sbi0090D.insertZeaitZsdt1142(zsdt1142);
		}
	}	
}
