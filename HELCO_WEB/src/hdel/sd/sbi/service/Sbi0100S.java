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
 * �귣�� ��ȸ Service Ŭ����
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
 *   2020.08.20         �ڼ���          ���� ����
 * 
 * </pre>
 */
@Service
public class Sbi0100S {

	// �α׼���
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
	 * �귣�� ��ȸ �ʱⰪ ��ȸ
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
			// �귣�� ����
			String maxBrndSeq = sbi0091D.selectMaxBrndseq(gMandt);
			resultVO.addVariable("F_BRNDSEQ", maxBrndSeq);

			// �귣���ڵ�
			Dataset dsAbrand = paramVO.getDataset("ds_abrand");
			ComboParamVO param = new ComboParamVO();
			param.setMandt(gMandt);  
			param.setLang(gLang);
			param.setVkgrp(maxBrndSeq);
			param.setVkbur("EL_ABRAND");
			List<ComboVO> brndList = sbi0090D.selectBrndSeqAtwrt(param);
			dsAbrand.deleteAll();
			
			int newRow = 0;
			// NOBRND �ο� �߰�
			for (int iRow=0;iRow < brndList.size();iRow++) {    
				newRow = dsAbrand.appendRow();   
				dsAbrand.setColumn(newRow, "CODE"		, brndList.get(iRow).getCode()); 	 // �ڵ�
				dsAbrand.setColumn(newRow, "CODE_NAME", brndList.get(iRow).getCodeName()); // �ڵ�� 
			}
			resultVO.addDataset(dsAbrand);

			// ��ǰ����
			Dataset dsZtplgbn = paramVO.getDataset("ds_zprdgnb");
			param.setMclass("");
			List<ComboVO> ztplgbnList = commonD.selectClass(param);
			dsZtplgbn.deleteAll();
			for (int iRow=0;iRow < ztplgbnList.size();iRow++) {    
				dsZtplgbn.appendRow();   
				dsZtplgbn.setColumn(iRow, "CODE"		, ztplgbnList.get(iRow).getCode());  // �ڵ�
				dsZtplgbn.setColumn(iRow, "CODE_NAME", ztplgbnList.get(iRow).getCodeName()); // �ڵ�� 
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
	 * �귣�� ������� ��ȸ
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
