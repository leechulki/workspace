package hdel.sd.sbi.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import hdel.lib.exception.BizException;
import hdel.sd.com.dao.ComboD;
import hdel.sd.com.dao.Coms01AD;
import hdel.sd.sbi.dao.Sbi0090D;
import hdel.sd.sbi.dao.Sbi0092D;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.sbi.domain.ZSDT1140;

/**
 * �귣�� �������� ��� Service Ŭ����
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
public class Sbi0092S {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmSqlSession srmQqlSession;	
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Sbi0092D sbi0092D;

	private Sbi0090D sbi0090D;

	private Coms01AD Coms01ADao;
	
	private ComboD commonD;
	
	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	/** 
	 * �귣�� �������� �ʱⰪ ��ȸ
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void initSbi0092(MipParameterVO paramVO, MipResultVO resultVO) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		
		Coms01ADao =  session.getMapper(Coms01AD.class);
		commonD    = session.getMapper(ComboD.class);
		String gMandt = paramVO.getVariable("G_MANDT");
		String gLang  = paramVO.getVariable("G_LANG");
		try {
			// �귣���ڵ�
			Dataset dsAbrand = paramVO.getDataset("ds_abrand");
			ComboParamVO param = new ComboParamVO();
			param.setMandt(gMandt);  
			param.setLang(gLang);
			param.setVkbur("EL_ABRAND");
			List<ComboVO> brndList = Coms01ADao.selectAtwrt(param); 
			dsAbrand.deleteAll();
			int newRow = 0;
			// NOBRND �ο� �߰�
//			newRow = dsAbrand.appendRow();
//			dsAbrand.setColumn(newRow, "CODE", "NOBRND");  // �ڵ�
//			dsAbrand.setColumn(newRow, "CODE_NAME", "");   // �ڵ�� 
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
			for (int iRow=0;iRow < brndList.size();iRow++) {    
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
	 * �귣�� �������� ��ȸ
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public List<ZSDT1140> findZsdt1140List(Map<String, Object> paramMap) throws Exception {
		List<ZSDT1140> zSdt1140List = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0092D = session.getMapper(Sbi0092D.class);
		try {
			zSdt1140List = sbi0092D.selectZsdt1140List(paramMap);
			logger.debug("zSdt1140List:"+zSdt1140List);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return zSdt1140List;
	}
	
	/** 
	 * �귣�� �������� ����
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	public void saveZsdt1140(List<ZSDT1140> deleteZsdt1140, List<ZSDT1140> insertZsdt1140, List<ZSDT1140> updateZsdt1140) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		sbi0092D = session.getMapper(Sbi0092D.class);
		sbi0090D = session.getMapper(Sbi0090D.class);
		
		List<ZSDT1140> addZsdt1140 = new ArrayList<ZSDT1140>();
		try {
			for(int i=0; i < deleteZsdt1140.size(); i++) {
				// ���� �� �Ǽ��� üũ�Ѵ�.
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("MANDT", deleteZsdt1140.get(i).getMANDT());
				paramMap.put("BRNDSEQ", deleteZsdt1140.get(i).getBRNDSEQ());
				paramMap.put("ZPRDGBN", deleteZsdt1140.get(i).getZPRDGBN());
				paramMap.put("BRNDCD", deleteZsdt1140.get(i).getBRNDCD());
				if(sbi0090D.selectBrndCdZsdt1141Cnt(paramMap) == 0) {
					//String frDat = coms042D.selectBrnFrDat(insertZsdt1140.get(i));
					sbi0092D.deleteZsdt1140(deleteZsdt1140.get(i));

					// ���������� �������ڸ� 99991231�� �����Ѵ�.
					ZSDT1140 preZSDT1140 = sbi0092D.selectPreMaxDeleteBrndZsdt1140(deleteZsdt1140.get(i));
					if(preZSDT1140 != null) {
						sbi0092D.updateLastTodatZsdt1140(preZSDT1140);
						preZSDT1140.setFLAG("U");
						preZSDT1140.setSPRAS(deleteZsdt1140.get(i).getSPRAS());
						addZsdt1140.add(preZSDT1140);
					}
				} else {
					session.rollback();
					String message = "�ش� '"+deleteZsdt1140.get(i).getBRNDSEQ()+"' ������ '"+deleteZsdt1140.get(i).getBRNDCD()+"' �귣��� ����Ư���� ��ϵǾ� ������ �� �����ϴ�.";
					throw new BizException(message);
				}
			}

			for(int i=0; i < insertZsdt1140.size(); i++) {
				sbi0092D.insertZsdt1140(insertZsdt1140.get(i));
				ZSDT1140 preZSDT1140 = sbi0092D.selectPreMaxUpdateBrndZsdt1140(insertZsdt1140.get(i));
				if(preZSDT1140 != null) {
					// ���������� �������ڸ� �����Ѵ�.
					// FRDAT -1 �Ϸ� �������ڸ� �����Ѵ�.
					sbi0092D.updateTodatZsdt1140(preZSDT1140);

					preZSDT1140.setFLAG("U");
					preZSDT1140.setSPRAS(insertZsdt1140.get(i).getSPRAS());
					addZsdt1140.add(preZSDT1140);
				}
			}

			for(int i=0; i < updateZsdt1140.size(); i++) {
				String frDat = sbi0092D.selectBrnFrDat(updateZsdt1140.get(i));
				sbi0092D.updateZsdt1140(updateZsdt1140.get(i));
				
				// ������ �߿� �������ڰ� ���� �� ó���Ѵ�.
				if(!frDat.equals(updateZsdt1140.get(i).getFRDAT())) {

					ZSDT1140 preZSDT1140 = sbi0092D.selectPreMaxUpdateBrndZsdt1140(updateZsdt1140.get(i));
                    if(preZSDT1140 != null) {
    					// ���������� �������ڸ� �����Ѵ�.
    					// FRDAT -1 �Ϸ� �������ڸ� �����Ѵ�.
    					sbi0092D.updateTodatZsdt1140(preZSDT1140);
    					preZSDT1140.setFLAG("U");
    					preZSDT1140.setSPRAS(updateZsdt1140.get(i).getSPRAS());
    					addZsdt1140.add(preZSDT1140);
                    }
				}
			}

			// ���� ������ ���� �Ϸ� �� EAI ���� ������ �����Ѵ�.
			saveEaiZsdt1140(deleteZsdt1140, insertZsdt1140
                          , updateZsdt1140, addZsdt1140);
			
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.commit();
			session.close();
		}
	}

	/** 
	 * �귣�� �������� ����
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	private void saveEaiZsdt1140(List<ZSDT1140> deleteZsdt1140, List<ZSDT1140> insertZsdt1140
			                     , List<ZSDT1140> updateZsdt1140, List<ZSDT1140> addZsdt1140) throws Exception {
		
		for(int i=0; i < deleteZsdt1140.size(); i++) {
			sbi0092D.insertZeaitZsdt1140(deleteZsdt1140.get(i));
		}		

		for(int i=0; i < insertZsdt1140.size(); i++) {
			sbi0092D.insertZeaitZsdt1140(insertZsdt1140.get(i));
		}		

		for(int i=0; i < updateZsdt1140.size(); i++) {
			sbi0092D.insertZeaitZsdt1140(updateZsdt1140.get(i));
		}		

		for(int i=0; i < addZsdt1140.size(); i++) {
			sbi0092D.insertZeaitZsdt1140(addZsdt1140.get(i));
		}
	}
}
