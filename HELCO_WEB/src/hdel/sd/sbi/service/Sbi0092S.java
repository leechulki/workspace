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
 * 브랜드 적용일자 등록 Service 클래스
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
	 * 브랜드 적용일자 초기값 조회
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
			// 브랜드코드
			Dataset dsAbrand = paramVO.getDataset("ds_abrand");
			ComboParamVO param = new ComboParamVO();
			param.setMandt(gMandt);  
			param.setLang(gLang);
			param.setVkbur("EL_ABRAND");
			List<ComboVO> brndList = Coms01ADao.selectAtwrt(param); 
			dsAbrand.deleteAll();
			int newRow = 0;
			// NOBRND 로우 추가
//			newRow = dsAbrand.appendRow();
//			dsAbrand.setColumn(newRow, "CODE", "NOBRND");  // 코드
//			dsAbrand.setColumn(newRow, "CODE_NAME", "");   // 코드명 
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
			for (int iRow=0;iRow < brndList.size();iRow++) {    
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
	 * 브랜드 적용일자 조회
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
	 * 브랜드 적용일자 저장
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
				// 삭제 시 건수를 체크한다.
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("MANDT", deleteZsdt1140.get(i).getMANDT());
				paramMap.put("BRNDSEQ", deleteZsdt1140.get(i).getBRNDSEQ());
				paramMap.put("ZPRDGBN", deleteZsdt1140.get(i).getZPRDGBN());
				paramMap.put("BRNDCD", deleteZsdt1140.get(i).getBRNDCD());
				if(sbi0090D.selectBrndCdZsdt1141Cnt(paramMap) == 0) {
					//String frDat = coms042D.selectBrnFrDat(insertZsdt1140.get(i));
					sbi0092D.deleteZsdt1140(deleteZsdt1140.get(i));

					// 이전차수의 종료일자를 99991231로 갱신한다.
					ZSDT1140 preZSDT1140 = sbi0092D.selectPreMaxDeleteBrndZsdt1140(deleteZsdt1140.get(i));
					if(preZSDT1140 != null) {
						sbi0092D.updateLastTodatZsdt1140(preZSDT1140);
						preZSDT1140.setFLAG("U");
						preZSDT1140.setSPRAS(deleteZsdt1140.get(i).getSPRAS());
						addZsdt1140.add(preZSDT1140);
					}
				} else {
					session.rollback();
					String message = "해당 '"+deleteZsdt1140.get(i).getBRNDSEQ()+"' 차수의 '"+deleteZsdt1140.get(i).getBRNDCD()+"' 브랜드는 영업특성이 등록되어 삭제할 수 없습니다.";
					throw new BizException(message);
				}
			}

			for(int i=0; i < insertZsdt1140.size(); i++) {
				sbi0092D.insertZsdt1140(insertZsdt1140.get(i));
				ZSDT1140 preZSDT1140 = sbi0092D.selectPreMaxUpdateBrndZsdt1140(insertZsdt1140.get(i));
				if(preZSDT1140 != null) {
					// 이전차수의 종료일자를 갱신한다.
					// FRDAT -1 일로 종료일자를 갱신한다.
					sbi0092D.updateTodatZsdt1140(preZSDT1140);

					preZSDT1140.setFLAG("U");
					preZSDT1140.setSPRAS(insertZsdt1140.get(i).getSPRAS());
					addZsdt1140.add(preZSDT1140);
				}
			}

			for(int i=0; i < updateZsdt1140.size(); i++) {
				String frDat = sbi0092D.selectBrnFrDat(updateZsdt1140.get(i));
				sbi0092D.updateZsdt1140(updateZsdt1140.get(i));
				
				// 수정값 중에 시작일자가 변경 시 처리한다.
				if(!frDat.equals(updateZsdt1140.get(i).getFRDAT())) {

					ZSDT1140 preZSDT1140 = sbi0092D.selectPreMaxUpdateBrndZsdt1140(updateZsdt1140.get(i));
                    if(preZSDT1140 != null) {
    					// 이전차수의 종료일자를 갱신한다.
    					// FRDAT -1 일로 종료일자를 갱신한다.
    					sbi0092D.updateTodatZsdt1140(preZSDT1140);
    					preZSDT1140.setFLAG("U");
    					preZSDT1140.setSPRAS(updateZsdt1140.get(i).getSPRAS());
    					addZsdt1140.add(preZSDT1140);
                    }
				}
			}

			// 원본 데이터 저장 완료 후 EAI 연계 데이터 생성한다.
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
	 * 브랜드 적용일자 저장
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
