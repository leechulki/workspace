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
import hdel.sd.sbi.dao.Sbi0100D;
import hdel.sd.sbi.domain.SanyangPrd;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;

/**
 * 브랜드 조회 Service 클래스
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
public class Sbi0100S {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmSqlSession srmQqlSession;	
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Sbi0100D sbi01000D;

	private Sbi0090D sbi0090D;
	
	private Sbi0091D sbi0091D;
	
	private ComboD commonD;
	
	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	/** 
	 * 브랜드 조회 초기값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void initSbi0100(MipParameterVO paramVO, MipResultVO resultVO) throws Exception {
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
	 * 브랜드 영업사양 조회
	 * @param Map<String, Object> paramMap
	 * @return List<Com040SanyangPrd>
	 * @throws Exception
	 */
	public List<SanyangPrd> findSayangList(Map<String, Object> paramMap) throws Exception {
		List<SanyangPrd> zSdt1142List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi01000D = session.getMapper(Sbi0100D.class);
		try {
			zSdt1142List = sbi01000D.selectSayangList(paramMap);
			logger.debug("zSdt1142List:"+zSdt1142List);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return zSdt1142List;
	}
}
