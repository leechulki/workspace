package hdel.sd.com.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;
import java.sql.SQLException;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.exception.BizException;
import hdel.sd.com.dao.RadSurveyD;
import hdel.sd.com.domain.FloorNmVO;
import hdel.sd.com.domain.SuvFloorVO;
import hdel.sd.com.domain.SuvPrhVO;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * 리모델링용 Duty 처리 서비스 클래스
 * 
 * @author  박수근
 * @since 2021.01.26
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2021.01.26         박수근          최초 생성
 * 
 * </pre>
 */

@Service
public class RadSurveyS {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmSqlSession srmQqlSession;	
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private SqlSessionFactory sqlSessionFactory;
	
    private RadSurveyD dao;

	@Autowired
	private RadFloorS radFloorS;	
    
	@Autowired
	private RadEnterS radEnterS;
	
	
	private String[] floorIds = {"ELR_H_ELVDONG", "ELR_C_EHP", "ELR_C_EFH40" ,"ELR_C_EFH39" ,"ELR_C_EFH38" ,"ELR_C_EFH37" ,"ELR_C_EFH36" ,"ELR_C_EFH35" ,"ELR_C_EFH34" ,"ELR_C_EFH33" ,"ELR_C_EFH32" ,"ELR_C_EFH31" ,"ELR_C_EFH30" ,"ELR_C_EFH29" ,"ELR_C_EFH28" ,"ELR_C_EFH27" ,"ELR_C_EFH26" ,"ELR_C_EFH25" ,"ELR_C_EFH24" ,"ELR_C_EFH23" ,"ELR_C_EFH22" ,"ELR_C_EFH21" ,"ELR_C_EFH20" ,"ELR_C_EFH19" ,"ELR_C_EFH18" ,"ELR_C_EFH17" ,"ELR_C_EFH16" ,"ELR_C_EFH15" ,"ELR_C_EFH14" ,"ELR_C_EFH13" ,"ELR_C_EFH12" ,"ELR_C_EFH11" ,"ELR_C_EFH10" ,"ELR_C_EFH09" ,"ELR_C_EFH08" ,"ELR_C_EFH07" ,"ELR_C_EFH06" ,"ELR_C_EFH05" ,"ELR_C_EFH04" ,"ELR_C_EFH03" ,"ELR_C_EFH02" ,"ELR_C_EFH01" ,"ELR_C_EFHB1" ,"ELR_C_EFHB2" ,"ELR_C_EFHB3" ,"ELR_C_EFHB4" ,"ELR_C_EFHB5"}; //,"ELR_C_EFHB6", "ELR_C_EFHB7", "ELR_C_EFHB8", "ELR_C_EFHB9", "ELR_C_EFHB10"
	private String[] floorNm  = {"동/호기", "PIT" ,"40층"  ,"39층"  ,"38층"  ,"37층"  ,"36층"  ,"35층"  ,"34층"  ,"33층"  ,"32층"  ,"31층"  ,"30층"  ,"29층"  ,"28층"  ,"27층"  ,"26층"  ,"25층"  ,"24층"  ,"23층"  ,"22층"  ,"21층"  ,"20층"  ,"19층"  ,"18층"  ,"17층"  ,"16층"  ,"15층"  ,"14층"  ,"13층"  ,"12층"  ,"11층"  ,"10층"  ,"09층"  ,"08층"  ,"07층"  ,"06층"  ,"05층"  ,"04층"  ,"03층"  ,"02층"  ,"01층"  ,"B1층"  ,"B2층"  ,"B3층"  ,"B4층"  ,"B5층"}; // ,"B6층"       , "B7층"       ,"B8층"        ,"B9층"        ,"B10층"
	private int[] floorNum    = {1 ,2 ,3 ,4 ,5 ,6 ,7 ,8 ,9 ,10 ,11 ,12 ,13 ,14 ,15 ,16 ,17 ,18 ,19 ,20 ,21 ,22 ,23 ,24 ,25 ,26 ,27 ,28 ,29 ,30 ,31 ,32 ,33 ,34 ,35 ,36 ,37 ,38 ,39 ,40 ,41 ,42 ,43 ,44 ,45 ,46 ,47}; // ,48           , 49           ,50            ,51            ,52

	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	/**
	 * 
	 * 견적서 리모델링 실측등록 상태 조회
	 * @throws SQLException 
	 */
	public Map<String, Object> selectEstSuvYsno(Map<String, Object> inMap) throws Exception {
		Map<String, Object> suvstMap;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			suvstMap = dao.selectEstSuvYsno(inMap);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return suvstMap;
	}

	/**
	 * 
	 * 수주 리모델링 실측등록 상태 조회
	 * @throws SQLException 
	 */
	public Map<String, Object> selectOrderSuvYsno(Map<String, Object> inMap) throws Exception {
		Map<String, Object> suvstMap;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			suvstMap = dao.selectOrderSuvYsno(inMap);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return suvstMap;
	}
	
	/**
	 * 
	 * 견적서 리모델링 실측사양 조회
	 * @throws SQLException 
	 */
	public List<SuvPrhVO> selectSuvPrhList(Map<String, Object> inMap) throws Exception {
		List<SuvPrhVO> suvPrhList = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			suvPrhList = dao.selectSuvPrhList(inMap);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		
		return suvPrhList;
	}

	/**
	 * 
	 * sap 리모델링 실측사양 조회
	 * @throws SQLException 
	 */
	public List<SuvPrhVO> selectSapSuvPrhList(Map<String, Object> inMap) throws Exception {
		List<SuvPrhVO> suvPrhList = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			suvPrhList = dao.selectSapSuvPrhList(inMap);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		
		return suvPrhList;
	}

	/**
	 * 
	 * 수주 리모델링 실측사양 조회
	 * @throws SQLException 
	 */
	public List<SuvPrhVO> selectOrderSuvPrhList(Map<String, Object> inMap) throws Exception {
		List<SuvPrhVO> suvPrhList = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			suvPrhList = dao.selectOrderSuvPrhList(inMap);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		
		return suvPrhList;
	}

	/**
	 * 
	 * 실측 표기시 입력 데이터 조회
	 * @throws SQLException 
	 */
	public List<SuvFloorVO> selectSuvFloorList(Map<String, Object> inMap) throws Exception {
		List<SuvFloorVO> suvFloorList = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			suvFloorList = dao.selectSuvFloorList(inMap);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return suvFloorList;
	}

	/**
	 * 
	 * 실측 JAMB 입력 데이터 조회
	 * @throws SQLException 
	 */
	public List<Map> selectElrEPnlhList(Map<String, Object> inMap) throws Exception {
		List<Map> suvJambList = null;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			suvJambList  = dao.selectElrEPnlhList(inMap);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return suvJambList;
	}
	
	/**
	 * 
	 * 영업사양과 실측사양으로 층고 전개를 수행한다.
	 * @param Map<String, String> prhMap, List<Map<String, String>>
	 * @param model
	 * @return
	 */
	public List<String> getFloorSpreadOut(String sUVSN, Map<String, String> prhMap, Map<String, FloorNmVO> floorPrhMap, List<SuvFloorVO> suvFloorList
			                             , List<Map> suvJambList, Map<Integer, String> floorSuvMap) {
		// 영업사양 층별전개 서비스 클래스 분리
		List<String> errMsgList = radFloorS.getFloorSpreadOut(sUVSN, prhMap, floorPrhMap, suvFloorList, suvJambList, floorSuvMap);
        return errMsgList;
	}

	/**
	 * 
	 * 영업사양 및 실측 층별전개 데이터 저장
	 * @throws SQLException 
	 */
	public void saveFloorOut(Map<String, Object> inMap, List<FloorNmVO> floorPrhList, HashMap<String, String> suvPrhMap, Map<String, Object> outMap) throws Exception {
		
		// floorPrhList: 화면에서 받은 층고전개결과 값
		// suvPrhMap: 사양값
		
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			// 
			dao.deleteZPST1138(inMap);
			dao.deleteZPST1139(inMap);
			dao.updateHZPST1139(inMap);
			session.commit();
			for(int i=0; i < floorPrhList.size(); i++) {
				FloorNmVO floorNmVO = floorPrhList.get(i);
				// FLOORDB 값 생성
				floorNmVO.setFLOORDB(floorNmVO.getFLOOR().replace("B", "-"));
				dao.insertZPST1138(floorNmVO);
				dao.insertZPST1139(floorNmVO);
			}			
			session.commit();
			
			int tmpMaxFloorNum = -99;
			String maxFloor = ""; // 맥스 층 추출
            // 층ID 기준으로 층고데이터 생성
			Map<String, FloorNmVO> floorMap = new HashMap<String, FloorNmVO>();
			for(int i=0; i < floorPrhList.size(); i++) {
				FloorNmVO floorVo = new FloorNmVO();
				FloorNmVO floorNmVO = floorPrhList.get(i);
				String floorID = "";
				String floorNmID = "";
				String suvprd = floorNmVO.getSUVPRD();
				if(suvprd == null) suvprd = "";
				if(!"".equals(suvprd)) {
					if(floorNmVO.getFLOOR().indexOf("B") > -1 ) {
						// 지하층 층고 데이터를 생성한다.
						floorID = "ELR_C_EFHB"+floorNmVO.getFLOOR().substring(1);
						floorNmID = "ELC_C_EFNB"+floorNmVO.getFLOOR().substring(1);
						if(suvPrhMap.get(floorID) != null) {
							if(!"".equals(suvPrhMap.get(floorID))) {
								outMap.put("ELC_C_EFNB"+floorNmVO.getFLOOR().substring(1), floorNmVO.getFLOORNM());
							}
						}
					} else {
						// 지상층 층고데이터를 생성한다.
						if( (new Integer(floorNmVO.getFLOOR()).intValue()) < 10 ) {
							floorID = "ELR_C_EFH0"+floorNmVO.getFLOOR();
							floorNmID = "ELC_C_EFN0"+floorNmVO.getFLOOR();
						} else {
							floorID = "ELR_C_EFH"+floorNmVO.getFLOOR();
							floorNmID = "ELC_C_EFN"+floorNmVO.getFLOOR();
						}
					}
					
					// 층고 데이터 생성
					outMap.put(floorID, suvprd);
					// 층명 데이터 생성
					outMap.put(floorNmID, floorNmVO.getFLOORNM());

					floorVo.setFLOORNM(floorID);
					floorVo.setFLOORPRD(suvprd);
					floorMap.put(floorID, floorVo);
					if(floorNmVO.getFLOORNUM() > tmpMaxFloorNum) {
	                	tmpMaxFloorNum = floorNmVO.getFLOORNUM();
	                	maxFloor = floorID;
	                }
				}
			}

			// 층 -> 층ID 기준으로 층고ID 데이터와 매핑
			// 층 --> 층고ID 기준으로 층고ID 데이터와 매핑
			// 실측치 중 최상층을 E008 Overhead 로 입력 합니다.
			// 둘중 층고 데이터 매핑 데이터가 가장 큰 데이로 층고 실측 연산실측값을 생성한다.
			// 실측치 각각의 층고를 합산 하여. E009 Travel Height 계산. ( 계산시 최상층은 합산에서 제외. )
			// 실측치 중 Pit 를E010 Pit 로 입력합니다.
			String ELR_C_EHO = "";
			String ELC_C_EHTRH = "";
			String ELC_C_EHTH  = "";
			int travelHeight = 0;
			int allHeight = 0;
			String tmpFLOORPRD = "";
			for( String key : floorMap.keySet() ) {
				FloorNmVO floorVo = floorMap.get(key);
				// 맥스 층이 아닌경우
				if(maxFloor.equals(key)) {
					// 최상층 ( OH ) 의 경우 5단위 버림으로 적용 바랍니다.
					// 실측치 5679 ==> 5675 으로, 실측치 5674 ==> 5670 으로 적용
					tmpFLOORPRD = getFiveTrunck(new Double(floorVo.getFLOORPRD()).doubleValue());
					floorVo.setFLOORPRD(tmpFLOORPRD);
				} else {
                    // 층고 중. 최상층을 제외한 각 층들은 50단위로 올림하여 적용 바랍니다
					// ex ) 실측치 2782 ==> 2800 으로, 실측치 2733 ==> 2750으로 적용
					tmpFLOORPRD = getFiftyRound(new Double(floorVo.getFLOORPRD()).doubleValue());
					floorVo.setFLOORPRD(tmpFLOORPRD);
					travelHeight = travelHeight + new Integer(tmpFLOORPRD).intValue();
				}
				allHeight = allHeight + new Integer(tmpFLOORPRD).intValue();
				
				// 층고값 등록함
				outMap.put(floorVo.getFLOORNM(), floorVo.getFLOORPRD());
	        }

			String ELR_C_EHP = suvPrhMap.get("ELR_C_EHP");
			allHeight = allHeight + new Integer(getFiveTrunck(new Double(ELR_C_EHP).doubleValue())).intValue();
			ELC_C_EHTRH = new Integer(travelHeight).toString();
			FloorNmVO maxfloorVo = floorMap.get(maxFloor);
			ELR_C_EHO = maxfloorVo.getFLOORPRD();
			ELC_C_EHTH = new Integer(allHeight).toString();
			
			// 연산식 생성
			outMap.put("ELR_C_EHO", ELR_C_EHO);
			outMap.put("ELC_C_EHTRH", ELC_C_EHTRH);
			outMap.put("ELC_C_EHTH", ELC_C_EHTH);
			
			// pit도 5단위 버림으로 적용
			outMap.put("ELR_C_EHP", getFiveTrunck(new Double(ELR_C_EHP).doubleValue()));
			
			// 승장사양 그룹 데이터를 조회한다.
			// 조회된 그룹이 3개 이상이면 층별 사양실적 보정처리는 담당자가 수행한다.
			List<Map> jambList = new ArrayList<Map>();
			List<Map> jambTmpList = dao.selectJampPrhSubPrhList(inMap);
			
			// 메인 jAMB을 먼저 출력 시키기 위해 조회된 리스트에서 순서를 조정한다.
			// EL_EMF C123 기준층(기본입력)
            String EL_EMF = (String)inMap.get("EL_EMF");
            if(EL_EMF == null) {
            	EL_EMF = "1";
            } else {
            	if("".equals(EL_EMF)) {
                	EL_EMF = "1";
            	}
            }

            // 기본층이 적용된 적용층 그룹을 찾는다.
            // mainl 그룹층이 먼저 출력되어야 한다.
            int iMainIndex = 0;
            for(int i=0; i <jambTmpList.size(); i++) {
            	Map<String, Object> jampMap = jambTmpList.get(i);
            	String FLOORNM = (String)jampMap.get("FLOORNM");
            	String FLOORNMList[] = FLOORNM.split(",");
                if(FLOORNMList.length > 0 ) {
                	for(int j=0; j < FLOORNMList.length; j++) {
                    	if(FLOORNMList[j].equals(EL_EMF)) {
                    		iMainIndex = i;
                    		break;
                    	}
                	}
                } else {
                	if(FLOORNM.equals(EL_EMF)) {
                		iMainIndex = i;
                	}
                }
            }

            jambList.add(jambTmpList.get(iMainIndex));
            jambTmpList.remove(iMainIndex);
            for(int i=0; i <jambTmpList.size(); i++) {
            	Map<String, Object> jampMap = jambTmpList.get(i);
            	jambList.add(jampMap);
            }
			
			// 만약 조회된 그룹이 3개 이상이면 실측 층별전개 불가 메시지를 출력한다.
			if(jambList.size() > 3) {
	            throw new BizException("생성된 출입구 의장도 그룹 데이터가 "+jambList.size()+"개가 생성되어 층별 견적사양을 적용할 수 없습니다.");
			}

			for(int i=0; i <jambList.size(); i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> jampMap = jambList.get(i);
				String JAMBGRPNUM = new Integer(i+1).toString();
				String FLOOR   = (String)jampMap.get("FLOOR"); 
				String FLOORNM = (String)jampMap.get("FLOORNM");
				String JAMBMODEL = (String)jampMap.get("JAMBMODEL");
			    String HPIMODEL = (String)jampMap.get("HPIMODEL");
			    String HPBMODEL = (String)jampMap.get("HPBMODEL");
			    String HIPMODEL = (String)jampMap.get("HIPMODEL");
			    String HLTNMODEL = (String)jampMap.get("HLTNMODEL");
			    String FSWMODEL = (String)jampMap.get("FSWMODEL");

			    // JAMB 그룹정보 생성
			    outMap.put("ELC_E_CJM"+JAMBGRPNUM, JAMBMODEL);
			    
			    // 적용층 정보를 그룹핑 처리해서 데이터를 만들어 JAMB 적용층을 적용해야 한다.
			    String grpFloorNm = radFloorS.getGroupFloorNm(FLOOR, FLOORNM);
			    outMap.put("ELC_E_CJMF"+JAMBGRPNUM, grpFloorNm);
			    
			    // HPI 도면생성 데이터를 생성한다.
			    if(!"".equals(HPIMODEL)) {
			    	Map<String, Object> prdMap = null;
			    	// 제품치수 데이터를 조회한다.
			    	inMap.put("SUVATTR", HPIMODEL);
			    	prdMap = dao.selectHpiZPST1133(inMap);
                    
			    	if( prdMap == null ) {
			            throw new BizException("등록된 HPI("+HPIMODEL+") 모델은 리모델링 자동도면 생성 대상이 아닙니다.");
			    	}
			    	
			    	// 제품정보
			    	String ELC_E_HPBX = (String)prdMap.get("ELC_E_HPBX");
			    	String ELC_E_HPW = (String)prdMap.get("ELC_E_HPW");
			    	String ELC_E_HPD = (String)prdMap.get("ELC_E_HPD");
			    	//String ELC_E_M_FORM = (String)prdMap.get("ELC_E_M_FORM");
			    	String ELC_E_BP_TYPE = (String)prdMap.get("ELC_E_BP_TYPE");
			    	if(ELC_E_HPBX == null) ELC_E_HPBX = "";
			    	
			    	if( "".equals(ELC_E_HPBX)) {
			            throw new BizException("등록된 HPI("+HPIMODEL+") 모델은 리모델링 자동도면 생성 대상이 아닙니다.");
		    		} else {
		    			// hpi 실측값
		    			String HPI_ELR_E_A = (String)jampMap.get("HPI_ELR_E_A");
		    			String HPI_ELR_E_B = (String)jampMap.get("HPI_ELR_E_B");
		    			
		    			// 실측값이 존재하지 않는 경우 처리
		    			if(HPI_ELR_E_A == null) {
		    				// 제품규격사이즈 적용
		    				// 그룹처리된 제품 규격만 생성한다.
		    				outMap.put("ELC_E_HPI"+JAMBGRPNUM, HPIMODEL);
		    				outMap.put("ELC_E_HPBX"+JAMBGRPNUM, ELC_E_HPBX);
		    				outMap.put("ELC_E_HPW"+JAMBGRPNUM, ELC_E_HPW);
		    				outMap.put("ELC_E_HPD"+JAMBGRPNUM, ELC_E_HPD);
		    			} else {
		    				outMap.put("ELC_E_HPI"+JAMBGRPNUM, HPIMODEL);
		    				outMap.put("ELC_E_HPBX"+JAMBGRPNUM, ELC_E_HPBX);
		    				// hpi 제품정보
		    				outMap.put("ELC_E_HPW"+JAMBGRPNUM, ELC_E_HPW);
		    				outMap.put("ELC_E_HPD"+JAMBGRPNUM, ELC_E_HPD);
		    				// hpi 실측정보
		    				outMap.put("ELC_E_HPA"+JAMBGRPNUM, HPI_ELR_E_A);
		    				outMap.put("ELC_E_HPB"+JAMBGRPNUM, HPI_ELR_E_B);
		    				
		    				// base Plate 계산값
		    				String sCalRslt[] = radEnterS.getCalBasePlate(HPI_ELR_E_A, HPI_ELR_E_B, ELC_E_HPW, ELC_E_HPD, "HPI", ELC_E_BP_TYPE);

		    				// 베이스 플레이트 적용여부 체크
                        	// ELC_E_HBW -- 제품가로
                        	// ELC_E_HBD -- 제품세로
                        	double delcehpw = new Double(ELC_E_HPW).doubleValue();
                        	double delcehpd = new Double(ELC_E_HPD).doubleValue();
                        	double dcalRslt0 = new Double(sCalRslt[0]).doubleValue();
                        	double dcalRslt1 = new Double(sCalRslt[1]).doubleValue();

                        	// 제품표준 사이즈가 베이스 플레이트 기준을 만족하면 베이스 플레이트 기준값을 적용하지 않는다.
                        	if((delcehpw >= dcalRslt0) && (delcehpd >= dcalRslt1)) {
                        	} else {
                        		sCalRslt = radEnterS.getCalBasePlateAdd(HPI_ELR_E_A, HPI_ELR_E_B, ELC_E_HPW, ELC_E_HPD, "HPI", ELC_E_BP_TYPE);
                                if("P".equals(ELC_E_BP_TYPE)) {
        		    				outMap.put("ELC_E_HPW"+JAMBGRPNUM, sCalRslt[0]);
        		    				outMap.put("ELC_E_HPD"+JAMBGRPNUM, sCalRslt[1]);
                                } else {
                            		// 베이스 플레이트가 적용된 값
                                	outMap.put("ELC_E_HPPW"+JAMBGRPNUM, sCalRslt[0]);
                                	outMap.put("ELC_E_HPPD"+JAMBGRPNUM, sCalRslt[1]);
                                }
                        	}
		    			}
		    		}                    
			    }

			    // HPB, HIP 도면생성 데이터를 생성한다.
			    if(!"".equals(HPBMODEL) || !"".equals(HIPMODEL)) {
			    	Map<String, Object> prdMap = null;
			    	// 제품치수 데이터를 조회한다.
			    	if(!"".equals(HPBMODEL)) {
				    	inMap.put("SUVATTR", HPBMODEL);
				    	prdMap = dao.selectHpbZPST1133(inMap);
			    	} else {
				    	inMap.put("SUVATTR", HIPMODEL);
				    	prdMap = dao.selectHipZPST1133(inMap);
			    	}

			    	if( prdMap == null) {
			            throw new BizException("등록된 HPB("+HPBMODEL+") 모델은 리모델링 자동도면 생성 대상이 아닙니다.");
			    	}

			    	// 제품정보
			    	String ELC_E_HBBX = (String)prdMap.get("ELC_E_HBBX");
			    	String ELC_E_HBW = (String)prdMap.get("ELC_E_HBW");
			    	String ELC_E_HBD = (String)prdMap.get("ELC_E_HBD");
			    	String ELC_E_HBW_PKS = (String)prdMap.get("ELC_E_HBW_PKS");
			    	String ELC_E_HBD_PKS = (String)prdMap.get("ELC_E_HBD_PKS");
			    	//String ELC_E_M_FORM = (String)prdMap.get("ELC_E_M_FORM");
			    	String ELC_E_BP_TYPE = (String)prdMap.get("ELC_E_BP_TYPE");
			    	String EL_DPK = suvPrhMap.get("EL_DPK");

			    	if(EL_DPK == null) EL_DPK = "N";
                	if("Y".equals(EL_DPK)) {
	    				// 파킹키 적용 제품규격 전달
                		ELC_E_HBW = ELC_E_HBW_PKS;
                		ELC_E_HBD = ELC_E_HBD_PKS;
                	}

			    	if(ELC_E_HBBX == null) ELC_E_HBBX = ""; 
			    	
			    	if( "".equals(ELC_E_HBBX)) {
			            throw new BizException("등록된 HPB("+HPBMODEL+") 모델은 리모델링 자동도면 생성 대상이 아닙니다.");
		    		} else {
		    			// base plate 적용 연산 처리를 수행한다.
		    			// 파킹스위치 적용 여부 분기
		    			// hpb 실측값
		    			String BTN_ELR_E_A = (String)jampMap.get("BTN_ELR_E_A");
		    			String BTN_ELR_E_B = (String)jampMap.get("BTN_ELR_E_B");
		    			String BTN_ELR_E_F = (String)jampMap.get("BTN_ELR_E_F");
		    			String BTN_ELC_E_CHB = (String)jampMap.get("BTN_ELC_E_CHB"); // 원형버튼 추가
		    			//String BTN_ELR_E_C = (String)jampMap.get("BTN_ELR_E_C");
		    			//String BTN_ELR_E_D = (String)jampMap.get("BTN_ELR_E_D");
		    			String BTN_ELR_E_E = (String)jampMap.get("BTN_ELR_E_E");
		    			if(BTN_ELR_E_A == null) BTN_ELR_E_A = "";
		    			if(BTN_ELR_E_E == null) BTN_ELR_E_E = "";
		    			
		    			// 파킹스위치 실측값
		    			String PKS_ELR_E_A = (String)jampMap.get("PKS_ELR_E_A");
		    			String PKS_ELR_E_B = (String)jampMap.get("PKS_ELR_E_B");
		    			String PKS_ELR_E_F = (String)jampMap.get("PKS_ELR_E_F");
		    			//String PKS_ELR_E_C = (String)jampMap.get("PKS_ELR_E_C");
		    			//String PKS_ELR_E_D = (String)jampMap.get("PKS_ELR_E_D");
		    			//String PKS_ELR_E_E = (String)jampMap.get("PKS_ELR_E_E");
	    				//  calWith, calHeigth 생성완료
		    			if(PKS_ELR_E_A == null) PKS_ELR_E_A = "";

		    			String modelType = "";
		    			if(!"".equals(HPBMODEL)) {
		    				modelType = "HPB";
		    				outMap.put("ELC_E_HB"+JAMBGRPNUM, HPBMODEL);
		    			} else {
		    				modelType = "HIP";
		    				outMap.put("ELC_E_HB"+JAMBGRPNUM, HIPMODEL);
		    			}

		    			// 원형버튼 타입 연산 사양값 정의
		    			outMap.put("ELC_E_CHB"+JAMBGRPNUM, BTN_ELC_E_CHB);
		    			
		    			// 만약 실측값이 없으면 제품규격만 전달
		    			if("".equals(BTN_ELR_E_A)) {
		    				outMap.put("ELC_E_HBBX"+JAMBGRPNUM, ELC_E_HBBX);
		    				outMap.put("ELC_E_HBW"+JAMBGRPNUM, ELC_E_HBW);
		    				outMap.put("ELC_E_HBD"+JAMBGRPNUM, ELC_E_HBD);
		    			} else {
		    				outMap.put("ELC_E_HBBX"+JAMBGRPNUM, ELC_E_HBBX);
		    				outMap.put("ELC_E_HBW"+JAMBGRPNUM, ELC_E_HBW);
		    				outMap.put("ELC_E_HBD"+JAMBGRPNUM, ELC_E_HBD);

		    				// 버튼 실측정보
		    				outMap.put("ELC_E_HBA"+JAMBGRPNUM, BTN_ELR_E_A);
		    				outMap.put("ELC_E_HBB"+JAMBGRPNUM, BTN_ELR_E_B);
		    				
		    				String sCalRslt[] = null;
                            if("".equals(PKS_ELR_E_A)) {
                            	outMap.put("ELC_E_APK"+JAMBGRPNUM, "N");
                            	
                                if("Y".equals(BTN_ELC_E_CHB) && !"".equals(BTN_ELR_E_E)) {
                                	// 원형버튼 2개인 경우 세로 사이즈는 = BTN_ELR_E_A + BTN_ELR_E_E
                                	int cirHeigth = new Integer(BTN_ELR_E_A).intValue() + new Integer(BTN_ELR_E_E).intValue();
        		    				sCalRslt = radEnterS.getCalBasePlate(BTN_ELR_E_A, new Integer(cirHeigth).toString(), ELC_E_HBW, ELC_E_HBD, modelType, ELC_E_BP_TYPE);
                                } else {
        		    				// base Plate 계산값
        		    				sCalRslt = radEnterS.getCalBasePlate(BTN_ELR_E_A, BTN_ELR_E_B, ELC_E_HBW, ELC_E_HBD, modelType, ELC_E_BP_TYPE);
                                }
                            } else {
                            	outMap.put("ELC_E_APK"+JAMBGRPNUM, "Y");
    		    				// base Plate 계산값
                            	// 1. ELC_E_HBW_PKS
                            	// 2. ELC_E_HBD_PKS
                            	// 3. BTN_ELR_E_F
                            	// 4. PKS_ELR_E_A
                            	// 5. PKS_ELR_E_F
                            	// 6. ELC_E_HBW_PKS
                            	// 7. ELC_E_HBD_PKS
                            	// 8. modelType
                            	// 9. ELC_E_BP_TYPE
                            	// 해당 로직은 좀더 살펴 봐야겠구나
                            	// BTN_ELR_E_E이 존재하면 버튼 2개 짜리로 간주한다.
                                if("Y".equals(BTN_ELC_E_CHB) && !"".equals(BTN_ELR_E_E)) {
                                	// 원형버튼 2개인 경우 세로 사이즈는 = BTN_ELR_E_A + BTN_ELR_E_E
                                	int cirHeigth = new Integer(BTN_ELR_E_A).intValue() + new Integer(BTN_ELR_E_E).intValue();
                                	sCalRslt = radEnterS.getCalPksBasePlate(BTN_ELR_E_A, new Integer(cirHeigth).toString(), BTN_ELR_E_F, PKS_ELR_E_A, PKS_ELR_E_B, PKS_ELR_E_F
				                              , ELC_E_HBW_PKS, ELC_E_HBD_PKS, modelType, ELC_E_BP_TYPE);
                                } else {
                                	sCalRslt = radEnterS.getCalPksBasePlate(BTN_ELR_E_A, BTN_ELR_E_B, BTN_ELR_E_F, PKS_ELR_E_A, PKS_ELR_E_B, PKS_ELR_E_F
				                              , ELC_E_HBW_PKS, ELC_E_HBD_PKS, modelType, ELC_E_BP_TYPE);
                                }
                            }

                        	// 베이스 플레이트 적용여부 체크
                        	// ELC_E_HBW -- 제품가로
                        	// ELC_E_HBD -- 제품세로
                        	double delcehbw = new Double(ELC_E_HBW).doubleValue();
                        	double delcehbd = new Double(ELC_E_HBD).doubleValue();
                        	double dcalRslt0 = new Double(sCalRslt[0]).doubleValue();
                        	double dcalRslt1 = new Double(sCalRslt[1]).doubleValue();

                        	//System.out.println("delcehbw:"+delcehbw+", dcalRslt0:"+dcalRslt0+", delcehbd:"+delcehbd+", dcalRslt1:"+dcalRslt1);
                        	// 제품표준 사이즈가 베이스 플레이트 기준을 만족하면 베이스 플레이트 기준값을 적용하지 않는다.
                        	if((delcehbw >= dcalRslt0) && (delcehbd >= dcalRslt1)) {
                        	} else {
                                if("".equals(PKS_ELR_E_A)) {
                                	outMap.put("ELC_E_APK"+JAMBGRPNUM, "N");
                                    if("Y".equals(BTN_ELC_E_CHB) && !"".equals(BTN_ELR_E_E)) {
                                    	// 원형버튼 2개인 경우 세로 사이즈는 = BTN_ELR_E_A + BTN_ELR_E_E
                                    	int cirHeigth = new Integer(BTN_ELR_E_A).intValue() + new Integer(BTN_ELR_E_E).intValue();
            		    				sCalRslt = radEnterS.getCalBasePlateAdd(BTN_ELR_E_A, new Integer(cirHeigth).toString(), ELC_E_HBW, ELC_E_HBD, modelType, ELC_E_BP_TYPE);
                                    } else {
            		    				// base Plate 계산값
            		    				sCalRslt = radEnterS.getCalBasePlateAdd(BTN_ELR_E_A, BTN_ELR_E_B, ELC_E_HBW, ELC_E_HBD, modelType, ELC_E_BP_TYPE);
                                    }
                                } else {
                                	outMap.put("ELC_E_APK"+JAMBGRPNUM, "Y");
                                    if("Y".equals(BTN_ELC_E_CHB) && !"".equals(BTN_ELR_E_E)) {
                                    	// 원형버튼 2개인 경우 세로 사이즈는 = BTN_ELR_E_A + BTN_ELR_E_E
                                    	int cirHeigth = new Integer(BTN_ELR_E_A).intValue() + new Integer(BTN_ELR_E_E).intValue();
                                    	sCalRslt = radEnterS.getCalPksBasePlateAdd(BTN_ELR_E_A, new Integer(cirHeigth).toString(), BTN_ELR_E_F, PKS_ELR_E_A, PKS_ELR_E_B, PKS_ELR_E_F
    				                              , ELC_E_HBW_PKS, ELC_E_HBD_PKS, modelType, ELC_E_BP_TYPE);
                                    } else {
                                    	sCalRslt = radEnterS.getCalPksBasePlateAdd(BTN_ELR_E_A, BTN_ELR_E_B, BTN_ELR_E_F, PKS_ELR_E_A, PKS_ELR_E_B, PKS_ELR_E_F
    				                              , ELC_E_HBW_PKS, ELC_E_HBD_PKS, modelType, ELC_E_BP_TYPE);
                                    }
                                }
                                
                                if("P".equals(ELC_E_BP_TYPE)) {
        		    				outMap.put("ELC_E_HBW"+JAMBGRPNUM, sCalRslt[0]);
        		    				outMap.put("ELC_E_HBD"+JAMBGRPNUM, sCalRslt[1]);
                                } else {
                            		// 베이스 플레이트가 적용된 값
    								outMap.put("ELC_E_HBPW"+JAMBGRPNUM, sCalRslt[0]);
    								outMap.put("ELC_E_HBPD"+JAMBGRPNUM, sCalRslt[1]);
                                }
                        	}

    	    				// 홀버튼 높이 --> 장애인용 별도 처리 로직 삽입
    	    				// 장애인인 경우에만 처리
    	    				// EL_AUSE : HC(장애),  EH(비상,장애), OH(전망,장애), BH(병원,장애), NH(누드,장애), HF(인화,장애)
                            // String EL_AUSE
    	    				String elAuse = (String)inMap.get("EL_AUSE");
                            if(elAuse == null) elAuse = "";
                            if("HC".equals(elAuse) || "EH".equals(elAuse) || "OH".equals(elAuse) || "BH".equals(elAuse) || "HF".equals(elAuse)) {
                            	// 오류 처리로직 추가
                            	if(BTN_ELR_E_F == null) BTN_ELR_E_F = "";
                            	if(BTN_ELR_E_B == null) BTN_ELR_E_B = "";

                			    // if(!"".equals(HPBMODEL) || !"".equals(HIPMODEL)) {
            			    	if( "".equals(BTN_ELR_E_F) || "".equals(BTN_ELR_E_B)) {
            			    		if( "".equals(BTN_ELR_E_F) && !"".equals(BTN_ELR_E_B)) {
                			            throw new BizException("홀버튼(HPI, HBP)의 F 실측치값이 없어 장애인 홀높이를 계산할 수 없습니다. 홀버튼(HPI, HBP) F 실측값을 확인해 주십시요.");
            			    		} if( !"".equals(BTN_ELR_E_F) && "".equals(BTN_ELR_E_B)) {
                			            throw new BizException("홀버튼(HPI, HBP)의 B 실측치값이 없어 장애인 홀높이를 계산할 수 없습니다. 홀버튼(HPI, HBP) B 실측값을 확인해 주십시요.");
            			    		} if( "".equals(BTN_ELR_E_F) && "".equals(BTN_ELR_E_B)) {
                			            throw new BizException("홀버튼(HPI, HBP)의 B,F 실측치값이 없어 장애인 홀높이를 계산할 수 없습니다. 홀버튼(HPI, HBP) B,F 실측값을 확인해 주십시요.");
                 			    	}                                 
            			    	}                       	
                            	// 장애인 기종인 경우 홀높이를 계산한다.
        	    				String sNudeHeight = radEnterS.getNudeCalHeight(BTN_ELR_E_F, BTN_ELR_E_B);
        	    				outMap.put("ELC_E_HBF"+JAMBGRPNUM, sNudeHeight);
                            }
		    			}
		    		}
			    }

			    // 홀랜턴 도면생성 데이터를 생성한다.
			    if(!"".equals(HLTNMODEL)) {
			    	Map<String, Object> prdMap = null;
			    	// 제품치수 데이터를 조회한다.
			    	inMap.put("SUVATTR", HLTNMODEL);
			    	prdMap = dao.selectHltZPST1133(inMap);

			    	if( prdMap == null) {
			            throw new BizException("등록된 홀랜턴("+HLTNMODEL+") 모델은 리모델링 자동도면 생성 대상이 아닙니다.");
			    	}
			    	
			    	// 제품정보
			    	String ELC_E_HLBX = (String)prdMap.get("ELC_E_HLBX");
			    	String ELC_E_HLW = (String)prdMap.get("ELC_E_HLW");
			    	String ELC_E_HLD = (String)prdMap.get("ELC_E_HLD");
			    	String ELC_E_M_FORM = (String)prdMap.get("ELC_E_M_FORM");
			    	String ELC_E_BP_TYPE = (String)prdMap.get("ELC_E_BP_TYPE");
			    	if(ELC_E_HLBX == null) ELC_E_HLBX = "";

			    	if( "".equals(ELC_E_HLBX)) {
			            throw new BizException("등록된 홀랜턴("+HLTNMODEL+") 모델은 리모델링 자동도면 생성 대상이 아닙니다.");
		    		} else {
		    			String HLT_ELR_E_HLLC = (String)jampMap.get("HLT_ELR_E_HLLC");
		    			String HLT_ELR_E_A = (String)jampMap.get("HLT_ELR_E_A");
		    			String HLT_ELR_E_B = (String)jampMap.get("HLT_ELR_E_B");
		    			String HLT_ELR_E_F = (String)jampMap.get("HLT_ELR_E_F");
		    			String HLT_ELR_E_C = (String)jampMap.get("HLT_ELR_E_C");
		    			//String HLT_ELR_E_D = (String)jampMap.get("HLT_ELR_E_D");
		    			//String HLT_ELR_E_E = (String)jampMap.get("HLT_ELR_E_E");
		    			//String HLT_ELR_E_H = (String)jampMap.get("HLT_ELR_E_H");
		    			
		    			if(HLT_ELR_E_A == null) HLT_ELR_E_A = "";
		    			
		    			// 실측값이 없는 경우 처리
		    			if("".equals(HLT_ELR_E_A)) {
		    				outMap.put("ELC_E_HL"+JAMBGRPNUM, HLTNMODEL);
		    				// 제품규격
		    				outMap.put("ELC_E_HLBX"+JAMBGRPNUM, ELC_E_HLBX);
		    				outMap.put("ELC_E_HLW"+JAMBGRPNUM, ELC_E_HLW);
		    				outMap.put("ELC_E_HLD"+JAMBGRPNUM, ELC_E_HLD);
		    				// 홀랜턴 위치
		    				// 만약 홀버턴 위치치 값이 없는 경우는 ?  EL_EHBLC: 자동도면 홀버튼 위치 -> ELR_H_BTNP
		    				if(HLT_ELR_E_HLLC == null) {
		    					HLT_ELR_E_HLLC = suvPrhMap.get("ELR_H_BTNP");
		    				}
		    				outMap.put("ELC_E_HLLC"+JAMBGRPNUM, HLT_ELR_E_HLLC);
		    			} else {
		    				outMap.put("ELC_E_HL"+JAMBGRPNUM, HLTNMODEL);
		    				// 제품규격
		    				outMap.put("ELC_E_HLBX"+JAMBGRPNUM, ELC_E_HLBX);
		    				outMap.put("ELC_E_HLW"+JAMBGRPNUM, ELC_E_HLW);
		    				outMap.put("ELC_E_HLD"+JAMBGRPNUM, ELC_E_HLD);
		    				// 만약 홀버턴 위치치 값이 없는 경우는 ?  EL_EHBLC: 자동도면 홀버튼 위치 -> ELR_H_BTNP
		    				if(HLT_ELR_E_HLLC == null) {
		    					HLT_ELR_E_HLLC = suvPrhMap.get("ELR_H_BTNP");
		    				}
		    				
		    				// 홀랜턴 위치
		    				outMap.put("ELC_E_HLLC"+JAMBGRPNUM, HLT_ELR_E_HLLC);
		    				// 제품규격	    			
		    				// 제품 모양이 다른 경우 제품규격 정의가 달라진다.
		    				if("C".equals(ELC_E_M_FORM)) {
		    				    // 원형인 경우 추가되는 실측
		    					outMap.put("ELC_E_HLF"+JAMBGRPNUM, HLT_ELR_E_F);
		    					outMap.put("ELC_E_HLC"+JAMBGRPNUM, HLT_ELR_E_C);
		    				} else {
		    					// 사각형인 경우 추가되는 실측
		    					outMap.put("ELC_E_HLA"+JAMBGRPNUM, HLT_ELR_E_A);
		    					outMap.put("ELC_E_HLB"+JAMBGRPNUM, HLT_ELR_E_B);
			    				
			    				// 베이스플레이트 적용
			    				// base Plate 계산값
			    				String sCalRslt[] = radEnterS.getCalBasePlate(HLT_ELR_E_A, HLT_ELR_E_B, ELC_E_HLW, ELC_E_HLD, "HLT", ELC_E_BP_TYPE);

	                            // PLATE 적용 타입 P: 플레이트 미적용 - 제품사이즈로 대체, N: 플레이트 미적용
                            	// 베이스 플레이트 적용여부 체크
                            	// ELC_E_HLW -- 제품가로
                            	// ELC_E_HLD -- 제품세로
                            	double delcehlw = new Double(ELC_E_HLW).doubleValue();
                            	double delcehhld = new Double(ELC_E_HLD).doubleValue();
                            	double dcalRslt0 = new Double(sCalRslt[0]).doubleValue();
                            	double dcalRslt1 = new Double(sCalRslt[1]).doubleValue();
                            	// 제품표준 사이즈가 베이스 플레이트 기준을 만족하면 베이스 플레이트 기준값을 적용하지 않는다.
                            	if((delcehlw >= dcalRslt0) && (delcehhld >= dcalRslt1)) {
                            	} else {
                            		sCalRslt = radEnterS.getCalBasePlateAdd(HLT_ELR_E_A, HLT_ELR_E_B, ELC_E_HLW, ELC_E_HLD, "HLT", ELC_E_BP_TYPE);
                                    if("P".equals(ELC_E_BP_TYPE)) {
            		    				outMap.put("ELC_E_HLW"+JAMBGRPNUM, sCalRslt[0]);
            		    				outMap.put("ELC_E_HLD"+JAMBGRPNUM, sCalRslt[1]);
                                    } else {
                                		// 베이스 플레이트가 적용된 값
                                		outMap.put("ELC_E_HLPW"+JAMBGRPNUM, sCalRslt[0]);
    	                            	outMap.put("ELC_E_HLPD"+JAMBGRPNUM, sCalRslt[1]);
                                    }
                            	}
		    				}
		    			}
		    		}
			    }

			    // 소방스위치 도면생성 데이터를 생성한다.
			    if(!"".equals(FSWMODEL)) {
			    	Map<String, Object> prdMap = null;
			    	// 제품치수 데이터를 조회한다.
			    	inMap.put("SUVATTR", FSWMODEL);
			    	prdMap = dao.selectFswZPST1133(inMap);

			    	if( prdMap == null) {
			            throw new BizException("등록된 소방스위치("+FSWMODEL+") 모델은 리모델링 자동도면 생성 대상이 아닙니다.");
			    	}
			    	
			    	String ELC_E_FSBX = (String)prdMap.get("ELC_E_FSBX");
			    	String ELC_E_FSW = (String)prdMap.get("ELC_E_FSW");
			    	String ELC_E_FSD = (String)prdMap.get("ELC_E_FSD");
			    	//String ELC_E_M_FORM = (String)prdMap.get("ELC_E_M_FORM");
			    	String ELC_E_BP_TYPE = (String)prdMap.get("ELC_E_BP_TYPE");
			    	if(ELC_E_FSBX == null) ELC_E_FSBX = "";
			    	
			    	if( "".equals(ELC_E_FSBX)) {
			            throw new BizException("등록된 소방스위치("+FSWMODEL+") 모델은 리모델링 자동도면 생성 대상이 아닙니다.");
		    		} else {
		    			
		    			String FSW_ELR_E_FSLC = (String)jampMap.get("FSW_ELR_E_FSLC");
		    	        String FSW_ELR_E_A = (String)jampMap.get("FSW_ELR_E_A");
		    	        String FSW_ELR_E_B = (String)jampMap.get("FSW_ELR_E_B");
		    	        //String FSW_ELR_E_F = (String)jampMap.get("FSW_ELR_E_F");
		    	        //String FSW_ELR_E_C = (String)jampMap.get("FSW_ELR_E_C");
		    	        //String FSW_ELR_E_D = (String)jampMap.get("FSW_ELR_E_D");
		    	        //String FSW_ELR_E_E = (String)jampMap.get("FSW_ELR_E_E");
                        if( FSW_ELR_E_A == null) FSW_ELR_E_A = "";
                        
                        if("".equals(FSW_ELR_E_A)) {
                            // 실측치가 없는 경우
                        	// 소방스위치 제품정보
                        	outMap.put("ELC_E_FSBX"+JAMBGRPNUM, ELC_E_FSBX);
                        	outMap.put("ELC_E_FSW"+JAMBGRPNUM, ELC_E_FSW);
                        	outMap.put("ELC_E_FSD"+JAMBGRPNUM, ELC_E_FSD);
                        } else {
                        	// 실측치가 있는 경우 적용
                        	// 소방스위치 제품정보
                        	outMap.put("ELC_E_FSBX"+JAMBGRPNUM, ELC_E_FSBX);
                        	outMap.put("ELC_E_FSW"+JAMBGRPNUM, ELC_E_FSW);
                        	outMap.put("ELC_E_FSD"+JAMBGRPNUM, ELC_E_FSD);

    	    				// 소방스위치 실측정보
                        	outMap.put("ELC_E_FSA"+JAMBGRPNUM, FSW_ELR_E_A);
                        	outMap.put("ELC_E_FSB"+JAMBGRPNUM, FSW_ELR_E_B);
                        	outMap.put("ELC_E_FSLC"+JAMBGRPNUM, FSW_ELR_E_FSLC);
    	    				
		    				// 베이스플레이트 적용
		    				// base Plate 계산값
		    				String sCalRslt[] = radEnterS.getCalBasePlate(FSW_ELR_E_A, FSW_ELR_E_B, ELC_E_FSW, ELC_E_FSD, "FSW", ELC_E_BP_TYPE);

                            // PLATE 적용 타입 P: 플레이트 미적용 - 제품사이즈로 대체, N: 플레이트 미적용
                        	// 베이스 플레이트 적용여부 체크
                        	// ELC_E_FSW-- 제품가로
                        	// ELC_E_FSD-- 제품세로
                        	double delcefsw = new Double(ELC_E_FSW).doubleValue();
                        	double delcefsd = new Double(ELC_E_FSD).doubleValue();
                        	double dcalRslt0 = new Double(sCalRslt[0]).doubleValue();
                        	double dcalRslt1 = new Double(sCalRslt[1]).doubleValue();
                        	// 제품표준 사이즈가 베이스 플레이트 기준을 만족하면 베이스 플레이트 기준값을 적용하지 않는다.
                        	if((delcefsw >= dcalRslt0) && (delcefsd >= dcalRslt1)) {
                        	} else {
                        		sCalRslt = radEnterS.getCalBasePlateAdd(FSW_ELR_E_A, FSW_ELR_E_B, ELC_E_FSW, ELC_E_FSD, "FSW", ELC_E_BP_TYPE);
                                if("P".equals(ELC_E_BP_TYPE)) {
                                	outMap.put("ELC_E_FSW"+JAMBGRPNUM, sCalRslt[0]);
                                	outMap.put("ELC_E_FSD"+JAMBGRPNUM, sCalRslt[1]);
                                } else {
                            		// 베이스 플레이트가 적용된 값
                            		outMap.put("ELC_E_FSPW"+JAMBGRPNUM, sCalRslt[0]);
                                	outMap.put("ELC_E_FSPD"+JAMBGRPNUM, sCalRslt[1]);
                                }
                        	}
                        }
		    		}
			    }
			}

			//승장도어판넬높이 조회 ELC_E_PNLH
			Map<String, Object> eMap = dao.selectElrEPnlh(inMap);
			String ELR_E_PNLH = (String)eMap.get("ELR_E_PNLH");
			String ELC_E_PNLHDEGR = (String)eMap.get("ELC_E_PNLHDEGR");
			// 출입구 높이
			String ELR_H_HH = suvPrhMap.get("ELR_H_HH");
			outMap.put("ELC_E_PNLHDEGR", ELC_E_PNLHDEGR);
			if(ELR_E_PNLH != null) {
				if(!"".equals(ELR_E_PNLH)) {
					int iELR_E_PNLH = new Integer(ELR_E_PNLH).intValue();
				    int iELR_H_HH = new Integer(ELR_H_HH).intValue();
                    // 10단위 버림 보정값
				    String sCELR_H_HH = getTenTrunck(iELR_H_HH);
				    int iCELR_H_HH = new Integer(sCELR_H_HH).intValue();
				    if(iELR_E_PNLH <= (iELR_H_HH + 50) ) {
				    	// 10단위 내림 보정값으로 처리한다.
				    	iELR_E_PNLH = iCELR_H_HH + 30;
				    	outMap.put("ELC_E_PNLH", new Integer(iELR_E_PNLH).toString());
				    } else {
				    	// 10단위 내림 보정값으로 처리한다.
				    	iELR_E_PNLH = iCELR_H_HH + 80;
				    	outMap.put("ELC_E_PNLH", new Integer(iELR_E_PNLH).toString());
				    }
				}
			}

			// EL_DJM	K017 \(재사용) JAMB(MAIN)
			// EL_DJO	K018 \(재사용) JAMB(OTHER)
			// ELR_H_JJ	JJ CH-HH 일정범위안에 있어야함
			// ELR_H_HH	HH
            // 50단위 반올림
			
			// 먼저 기존 생성된 데이터를 삭제하고 저장처리를 수행한다.
			dao.deleteEnterElcData(inMap);
			// 생성된 결과값을 연산식에 저장한다.
			Map<String, Object> suvPrdMap = new HashMap<String, Object>();
			suvPrdMap.put("MANDT", inMap.get("MANDT"));
			suvPrdMap.put("SUVSN", inMap.get("SUVSN"));
			suvPrdMap.put("USER_NAME", inMap.get("G_USER_NAME"));
			suvPrdMap.put("SUVTYPE", "E");

			for( String key : outMap.keySet() ) {
				// 연산된 결과를 저장한다.
				suvPrdMap.put("SUVPRH", key);
				Object calPrd = outMap.get(key);
				if(calPrd == null) {
					suvPrdMap.put("CALPRD", "");
				} else {
					suvPrdMap.put("CALPRD", outMap.get(key));
				}
				dao.insertCalZPST1136(suvPrdMap);
	        }
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * 영업사양 및 실측 연산결과 및 조정값 저장
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveCalModyPrd(List<Map> calList, List<Map> modyList) throws Exception {
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			for(int i=0; i < calList.size(); i++) {
				Map inMap = calList.get(i);
				dao.insertCalZPST1136(inMap);
			}

			for(int i=0; i < modyList.size(); i++) {
				Map inMap = modyList.get(i);
				dao.updateModyPST1136(inMap);
			}
			session.commit();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	/**
	 * 
	 * 영업사양값 내역 조회
	 * @throws SQLException 
	 */
	public String selectATWTB(Map<String, Object> inMap) throws Exception {
		String ATWTB;
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		try {
			ATWTB = dao.selectATWTB(inMap);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return ATWTB;
	}

	
	/**
	 * 
	 * 실측값 엑셀 데이터 조회
	 * @throws SQLException 
	 */
	public Map<String, Object> selectRadExcelData(Map<String, Object> inMap, XLSTransformer xls) throws Exception {
		Map<String, Object> inputData = new HashMap<String, Object>();
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		List<HashMap<String, Object>> hogiMapList = null;

		// 승강로 부분
		HashMap<String, String> masterHMap = new HashMap<String, String>();
		// 기계실부분
		HashMap<String, String> masterMMap = new HashMap<String, String>();
		// CWT, 승장마스터
		HashMap<String, String> masterCEMap = new HashMap<String, String>();
		// 승강로 부분 (좌횡락 분리)
		HashMap<String, String> masterLdHMap = new HashMap<String, String>();
		// 승강로 부분 (좌횡락 공용)
		HashMap<String, String> masterLcHMap = new HashMap<String, String>();
		// 기계실 부분 (좌횡락 분리,공용)
		HashMap<String, String> masterLcMMap = new HashMap<String, String>();
		// 승강로 부분 (우횡락 분리))
		HashMap<String, String> masterRdHMap = new HashMap<String, String>();
		// 승강로 부분 (우횡락 공용)
		HashMap<String, String> masterRcHMap = new HashMap<String, String>();
		// 기계실 부분 (우횡락 분리,공용))
		HashMap<String, String> masterRcMMap = new HashMap<String, String>();

		// 승강로부분
		List<HashMap<String, Object>> hMapList = new ArrayList<HashMap<String, Object>>();
		// 기계실부분
		List<HashMap<String, Object>> mMapList = new ArrayList<HashMap<String, Object>>();
		// CWT-승장
		List<HashMap<String, Object>> ceMapList = new ArrayList<HashMap<String, Object>>();
		// 층고 데이터
		Map<String, Object> floorMap = new HashMap<String, Object>();
		Map<Integer, Object> floorTmpMap = new HashMap<Integer, Object>();
		List<HashMap<String, Object>> floorList = new ArrayList<HashMap<String, Object>>();
		
		// 표시기 데이터		
		List<HashMap<String, Object>> viewMapList = new ArrayList<HashMap<String, Object>>();
		// Jamb 데이터		
		List<HashMap<String, Object>> jambMapList = new ArrayList<HashMap<String, Object>>();

		// 승강로 부분 (좌횡락 분리)
		List<HashMap<String, Object>> ldhMapList = new ArrayList<HashMap<String, Object>>();
		// 승강로 부분 (좌횡락 공용)
		List<HashMap<String, Object>> lchMapList = new ArrayList<HashMap<String, Object>>();
		// 기계실 부분 (좌횡락 분리,공용)
		List<HashMap<String, Object>> lmMapList = new ArrayList<HashMap<String, Object>>();
		
		// 승강로 부분 (우횡락 분리))
		List<HashMap<String, Object>> rdhMapList = new ArrayList<HashMap<String, Object>>();
		// 승강로 부분 (우횡락 공용)
		List<HashMap<String, Object>> rchMapList = new ArrayList<HashMap<String, Object>>();
		// 기계실 부분 (우횡락 분리,공용))
		List<HashMap<String, Object>> rmMapList = new ArrayList<HashMap<String, Object>>();

		try {
			if(inMap.get("PRJNUM") != null) {
			    // 수주 시 실측값 엑셀 데이터 조회
				hogiMapList = dao.selectOrderHGList(inMap);
			} else {
			    // 견적 시 실측값 엑셀 데이터 조회
				hogiMapList = dao.selectQtHGList(inMap);
			}
			
			if(hogiMapList.size() == 0) return inputData;
			// 승강로 부분
			masterHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterHMap.put("SUVNAM", "");
			masterHMap.put("SUVDAT", "");
			masterHMap.put("HOGI", "");
			masterHMap.put("UPS", "");
			// 기계실부분
			masterMMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterMMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterMMap.put("SUVNAM", "");
			masterMMap.put("SUVDAT", "");
			masterMMap.put("HOGI", "");
			masterMMap.put("UPS", "");
			// CWT, 승장마스터
			masterCEMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterCEMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterCEMap.put("SUVNAM", "");
			masterCEMap.put("SUVDAT", "");
			masterCEMap.put("HOGI", "");
			masterCEMap.put("UPS", "");
			
			// 승강로 부분 (좌횡락 분리)
			masterLdHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterLdHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterLdHMap.put("SUVNAM", "");
			masterLdHMap.put("SUVDAT", "");
			masterLdHMap.put("HOGI", "");
			masterLdHMap.put("UPS", "");
			
			// 승강로 부분 (좌횡락 공용)
			masterLcHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterLcHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterLcHMap.put("SUVNAM", "");
			masterLcHMap.put("SUVDAT", "");
			masterLcHMap.put("HOGI", "");
			masterLcHMap.put("UPS", "");
			
			// 기계실 부분 (좌횡락 분리,공용)
			masterLcMMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterLcMMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterLcMMap.put("SUVNAM", "");
			masterLcMMap.put("SUVDAT", "");
			masterLcMMap.put("HOGI", "");
			masterLcMMap.put("UPS", "");
			
			// 승강로 부분 (우횡락 분리))
			masterRdHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterRdHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterRdHMap.put("SUVNAM", "");
			masterRdHMap.put("SUVDAT", "");
			masterRdHMap.put("HOGI", "");
			masterRdHMap.put("UPS", "");
			
			// 승강로 부분 (우횡락 공용)
			masterRcHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterRcHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterRcHMap.put("SUVNAM", "");
			masterRcHMap.put("SUVDAT", "");
			masterRcHMap.put("HOGI", "");
			masterRcHMap.put("UPS", "");
			
			// 기계실 부분 (우횡락 분리,공용))
			masterRcMMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterRcMMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterRcMMap.put("SUVNAM", "");
			masterRcMMap.put("SUVDAT", "");
			masterRcMMap.put("HOGI", "");
			masterRcMMap.put("UPS", "");
 
			String suvDat = "";
			String hogi = "";
			String suvnam = "";
			String ups = "";

			HashMap<String, String> cmpMap = new HashMap<String, String>();
			HashMap<String, String> rmHogiMap = new HashMap<String, String>();
			HashMap<String, String> inHogiMap = new HashMap<String, String>();
			for(int i=0; i < hogiMapList.size(); i++) {
				Map<String, Object> pramMap = hogiMapList.get(i);
				String suvDatTmp = (String)pramMap.get("SUVDAT");
				String hogiTmp = (String)pramMap.get("HOGI");
				
				String suvnamTmp = (String)pramMap.get("SUVNAM");
				String upsTmp = ""; 
				String ecwtp = (String)pramMap.get("ECWTP");
				String ebrk = (String)pramMap.get("CBRK");
				String dong = "";
				if(ecwtp == null) ecwtp = "";
				if(ebrk == null) ebrk = "";
				if(suvDatTmp == null) suvDatTmp = "";
				
				// 승강로부분-기계실 부분-CWT상세-층고 조회
				List<HashMap<String, Object>> suvDataList = dao.selectSuvDataList(pramMap);
				// 리스트 데이터 맵데이터 변경
				HashMap<String, Object> suvHDataMap = new HashMap<String, Object>();
				HashMap<String, Object> suvCEDataMap = new HashMap<String, Object>();
				HashMap<String, Object> suvMDataMap = new HashMap<String, Object>();
				for(int j=0; j < suvDataList.size(); j++) {
					// 코드 매핑값으로 대체한다.
					String itemdiv = (String)suvDataList.get(j).get("ITEMDIV");
					if("M".equals(itemdiv)) {
						suvMDataMap.put((String)suvDataList.get(j).get("SUVPRH"), suvDataList.get(j).get("SUVPRDNAME"));
					} else if("B".equals(itemdiv) || "H".equals(itemdiv)) {
						suvHDataMap.put((String)suvDataList.get(j).get("SUVPRH"), suvDataList.get(j).get("SUVPRDNAME"));
					} else {
						// CWT, 승장 데이터
						suvCEDataMap.put((String)suvDataList.get(j).get("SUVPRH"), suvDataList.get(j).get("SUVPRDNAME"));
					}
				}
				dong = (String)suvHDataMap.get("ELR_H_ELVDONG");
				if(dong == null) {
					dong = hogiTmp;
				} else {
					if(dong.equals("")) {
						dong = hogiTmp;
					}
				}
				
				suvHDataMap.put("ELR_H_ELVDONG", dong);
				suvCEDataMap.put("ELR_H_ELVDONG", dong);
				suvMDataMap.put("ELR_H_ELVDONG", dong);
				upsTmp = suvHDataMap.get("ELR_H_AUSE")+"/"+suvHDataMap.get("ELR_H_AMAN")+"/"+suvHDataMap.get("ELR_H_ASPD");
				if(suvDatTmp.length() == 8) {
					suvDatTmp = suvDatTmp.substring(0,4)+"."+suvDatTmp.substring(4,6)+"."+suvDatTmp.substring(6,8);
				}

				// 승강로 기계실 데이터 생성
				if("R".equals(ecwtp)) {
                    // 승강로 부분, 기계실 부분
					hMapList.add(suvHDataMap);
					mMapList.add(suvMDataMap);

					suvDat = masterHMap.get("SUVDAT") + ", "+suvDatTmp;
					hogi = masterHMap.get("HOGI") + ", "+hogiTmp;
					
					suvnam = masterHMap.get("SUVNAM") + ", "+suvnamTmp;
					ups = masterHMap.get("UPS") + ", "+upsTmp;
					masterHMap.put("SUVDAT", suvDat);
					masterHMap.put("HOGI", hogi);
					masterHMap.put("SUVNAM", suvnam);
					masterHMap.put("UPS", ups);
					
					masterMMap.put("SUVDAT", suvDat);
					masterMMap.put("HOGI", hogi);
					masterMMap.put("SUVNAM", suvnam);
					masterMMap.put("UPS", ups);
					
				} else if("F/L".equals(ecwtp) || "R/L".equals(ecwtp)) { //FRONT LEFT, // REAR LEFT
					if("Y".equals(ebrk)) {
						lchMapList.add(suvHDataMap);
						suvDat = masterLcHMap.get("SUVDAT") + ", "+suvDatTmp;
						hogi = masterLcHMap.get("HOGI") + ", "+hogiTmp;
						suvnam = masterLcHMap.get("SUVNAM") + ", "+suvnamTmp;
						ups = masterLcHMap.get("UPS") + ", "+upsTmp;
						masterLcHMap.put("SUVDAT", suvDat);
						masterLcHMap.put("HOGI", hogi);
						masterLcHMap.put("SUVNAM", suvnam);
						masterLcHMap.put("UPS", ups);
					} else {
						ldhMapList.add(suvHDataMap);
						suvDat = masterLdHMap.get("SUVDAT") + ", "+suvDatTmp;
						hogi = masterLdHMap.get("HOGI") + ", "+hogiTmp;
						suvnam = masterLdHMap.get("SUVNAM") + ", "+suvnamTmp;
						ups = masterLdHMap.get("UPS") + ", "+upsTmp;
						masterLdHMap.put("SUVDAT", suvDat);
						masterLdHMap.put("HOGI", hogi);
						masterLdHMap.put("SUVNAM", suvnam);
						masterLdHMap.put("UPS", ups);
					}
					lmMapList.add(suvMDataMap);
					masterLcMMap.put("SUVDAT", suvDat);
					masterLcMMap.put("HOGI", hogi);
					masterLcMMap.put("SUVNAM", suvnam);
					masterLcMMap.put("UPS", ups);
				} else if("F/R".equals(ecwtp) || "R/R".equals(ecwtp)) { //FRONT RIGHT, // REAR RIGHT
					if("Y".equals(ebrk)) {
						rchMapList.add(suvHDataMap);
						suvDat = masterRcHMap.get("SUVDAT") + ", "+suvDatTmp;
						hogi = masterRcHMap.get("HOGI") + ", "+hogiTmp;
						suvnam = masterRcHMap.get("SUVNAM") + ", "+suvnamTmp;
						ups = masterRcHMap.get("UPS") + ", "+upsTmp;
						masterRcHMap.put("SUVDAT", suvDat);
						masterRcHMap.put("HOGI", hogi);
						masterRcHMap.put("SUVNAM", suvnam);
						masterRcHMap.put("UPS", ups);
					} else {
						rdhMapList.add(suvHDataMap);
						suvDat = masterRdHMap.get("SUVDAT") + ", "+suvDatTmp;
						hogi = masterRdHMap.get("HOGI") + ", "+hogiTmp;
						suvnam = masterRdHMap.get("SUVNAM") + ", "+suvnamTmp;
						ups = masterRdHMap.get("UPS") + ", "+upsTmp;
						masterRdHMap.put("SUVDAT", suvDat);
						masterRdHMap.put("HOGI", hogi);
						masterRdHMap.put("SUVNAM", suvnam);
						masterRdHMap.put("UPS", ups);
					}
					rmMapList.add(suvMDataMap);
					masterRcMMap.put("SUVDAT", suvDat);
					masterRcMMap.put("HOGI", hogi);
					masterRcMMap.put("SUVNAM", suvnam);
					masterRcMMap.put("UPS", ups);
				}

				//  CWT.상세및층고부분, 승장부분
				ceMapList.add(suvCEDataMap);
				// CWT - 승장 마스터 정보 생성
				suvDat = masterCEMap.get("SUVDAT") + ", "+suvDatTmp;
				hogi = masterCEMap.get("HOGI") + ", "+hogiTmp;
				suvnam = masterCEMap.get("SUVNAM") + ", "+suvnamTmp;
				ups = masterCEMap.get("UPS") + ", "+upsTmp;
				masterCEMap.put("SUVDAT", suvDat);
				masterCEMap.put("HOGI", hogi);
				masterCEMap.put("SUVNAM", suvnam);
				masterCEMap.put("UPS", ups);
				
				// 표시기 적용층 데이터 조회
				List<HashMap<String, Object>> suvViewDataList = dao.selectSuvViewDataList(pramMap);
				if(suvViewDataList.size()>0) {
					for(int z=0; z < suvViewDataList.size(); z++) {
						suvViewDataList.get(z).put("ELR_H_ELVDONG", dong);					
					}
					viewMapList.addAll(suvViewDataList);
				}
				
				// jamb 적용층 데이터 조회
				List<HashMap<String, Object>> suvJambDataList = dao.selectSuvJamDataList(pramMap);
                if(suvJambDataList.size()>0) {
					for(int z=0; z < suvJambDataList.size(); z++) {
						suvJambDataList.get(z).put("ELR_H_ELVDONG", dong);					
					}
                	jambMapList.addAll(suvJambDataList);
                }
                
                // 층고 데이터 생성
                String fNmId = "F_"+i;
                String dataKey = "";
                String hogiData = "";
                String hogiDataTmp = "";
                for(int ii=0; ii < floorIds.length; ii++) {
                	String fdata = (String)suvCEDataMap.get(floorIds[ii]);
                	if(fdata !=null) {
                		if(!"".equals(fdata)) {
                    		if(ii== 0) {
                    			// 호기 데이터
                    			hogiDataTmp = fdata;
                    		} else {
                        		// 값비교 데이터 생성
                        		dataKey = dataKey + floorNm[ii]+fdata;
                    		}
                		}
                	}
                }

                // 값비교하여 멀티 호기값 생성
                if(cmpMap.containsKey(dataKey)) {
                	// 호기데이터를 변경해서 입력한다.
                	hogiData = cmpMap.get(dataKey);
                	hogiData = hogiData + "," + hogiDataTmp;
                	cmpMap.put(dataKey, hogiData);

                	String  tmpKey  = inHogiMap.get(dataKey);
                	HashMap<String, Object> dataMap = (HashMap<String, Object>) floorTmpMap.get(1);
                	dataMap.put(tmpKey, hogiData);
                } else {
                	// 신규 데이터 입력
                	cmpMap.put(dataKey, hogiDataTmp);
                    for(int jj=0; jj < floorIds.length; jj++) {
                		// 최초 호기정보 생성 시섬의 데이터
                    	inHogiMap.put(dataKey, fNmId);
                    	String fdata = (String)suvCEDataMap.get(floorIds[jj]);
                    	if(fdata !=null) {
                    		if(!"".equals(fdata)) {
                            	if(floorTmpMap.containsKey(floorNum[jj])) {
                            		HashMap<String, Object> dataMap = (HashMap<String, Object>) floorTmpMap.get(floorNum[jj]);
                            		dataMap.put(fNmId, fdata);
                            		floorTmpMap.put(floorNum[jj], dataMap);
                            	} else {
                            		HashMap<String, Object> dataMap = new HashMap<String, Object>();
                            		dataMap.put(fNmId, fdata);
                            		// 최초 생성 floorNm
                            		dataMap.put("NM", floorNm[jj]);
                            		floorTmpMap.put(floorNum[jj], dataMap);
                            	}
                    		}
                    	}
                    }
                }
			}
			
			// 소트 처리를 하자
			Integer[] mapkey = new Integer[floorTmpMap.size()];
			int tmpI = 0;
			for( Integer tmpKey : floorTmpMap.keySet() ){
				mapkey[tmpI] = tmpKey;
				tmpI = tmpI +1;
	        }
			Arrays.sort(mapkey);
			for(int iRow=0; iRow<mapkey.length; iRow++) {
				HashMap<String, Object> dataMap = (HashMap<String, Object>) floorTmpMap.get(mapkey[iRow]);
				if(iRow < 3) {
					if(iRow == 2) {
						for( String key : dataMap.keySet() ) {
							if(key.equals("NM")) {
								dataMap.put("NM", "OH "+dataMap.get(key));
							}
						}
					} else {
						for( String key : dataMap.keySet() ) {
							if(key.equals("NM")) {
								floorMap.put(key+"_"+iRow, dataMap.get(key));
							} else {
								floorMap.put(key+iRow, dataMap.get(key));
							}
						}
					}
				}

				if(iRow > 1) {
				    floorList.add(dataMap);
				}
			}
			// 층별 데이터 입력
			inputData.put("floorMap", floorMap);
			inputData.put("floorList", floorList);

			// 정렬처리해서 데이터 새로 생성한다.
			//  floorList 조회된 층별 데이터가 16개 넘는 경우 별도 처리한다.
		    // , 제거
			if(masterHMap.get("SUVNAM").length()>2) masterHMap.put("SUVNAM", masterHMap.get("SUVNAM").substring(2));
			if(masterHMap.get("SUVDAT").length()>2) masterHMap.put("SUVDAT", masterHMap.get("SUVDAT").substring(2));
			if(masterHMap.get("HOGI").length()>2) masterHMap.put("HOGI", masterHMap.get("HOGI").substring(2));
			if(masterHMap.get("UPS").length()>2) masterHMap.put("UPS", masterHMap.get("UPS").substring(2));

			if(masterMMap.get("SUVNAM").length()>2) masterMMap.put("SUVNAM", masterMMap.get("SUVNAM").substring(2));
			if(masterMMap.get("SUVDAT").length()>2) masterMMap.put("SUVDAT", masterMMap.get("SUVDAT").substring(2));
			if(masterMMap.get("HOGI").length()>2) masterMMap.put("HOGI", masterMMap.get("HOGI").substring(2));
			if(masterMMap.get("UPS").length()>2) masterMMap.put("UPS", masterMMap.get("UPS").substring(2));

			if(masterCEMap.get("SUVNAM").length()>2) masterCEMap.put("SUVNAM", masterCEMap.get("SUVNAM").substring(2));
			if(masterCEMap.get("SUVDAT").length()>2) masterCEMap.put("SUVDAT", masterCEMap.get("SUVDAT").substring(2));
			if(masterCEMap.get("HOGI").length()>2) masterCEMap.put("HOGI", masterCEMap.get("HOGI").substring(2));
			if(masterCEMap.get("UPS").length()>2) masterCEMap.put("UPS", masterCEMap.get("UPS").substring(2));			
			
			if(masterLdHMap.get("SUVNAM").length()>2) masterLdHMap.put("SUVNAM", masterLdHMap.get("SUVNAM").substring(2));
			if(masterLdHMap.get("SUVDAT").length()>2) masterLdHMap.put("SUVDAT", masterLdHMap.get("SUVDAT").substring(2));
			if(masterLdHMap.get("HOGI").length()>2) masterLdHMap.put("HOGI", masterLdHMap.get("HOGI").substring(2));
			if(masterLdHMap.get("UPS").length()>2) masterLdHMap.put("UPS", masterLdHMap.get("UPS").substring(2));

			if(masterLcHMap.get("SUVNAM").length()>2) masterLcHMap.put("SUVNAM", masterLcHMap.get("SUVNAM").substring(2));
			if(masterLcHMap.get("SUVDAT").length()>2) masterLcHMap.put("SUVDAT", masterLcHMap.get("SUVDAT").substring(2));
			if(masterLcHMap.get("HOGI").length()>2) masterLcHMap.put("HOGI", masterLcHMap.get("HOGI").substring(2));
			if(masterLcHMap.get("UPS").length()>2) masterLcHMap.put("UPS", masterLcHMap.get("UPS").substring(2));

			if(masterLcMMap.get("SUVNAM").length()>2) masterLcMMap.put("SUVNAM", masterLcMMap.get("SUVNAM").substring(2));
			if(masterLcMMap.get("SUVDAT").length()>2) masterLcMMap.put("SUVDAT", masterLcMMap.get("SUVDAT").substring(2));
			if(masterLcMMap.get("HOGI").length()>2) masterLcMMap.put("HOGI", masterLcMMap.get("HOGI").substring(2));
			if(masterLcMMap.get("UPS").length()>2) masterLcMMap.put("UPS", masterLcMMap.get("UPS").substring(2));
			
			if(masterRdHMap.get("SUVNAM").length()>2) masterRdHMap.put("SUVNAM", masterRdHMap.get("SUVNAM").substring(2));
			if(masterRdHMap.get("SUVDAT").length()>2) masterRdHMap.put("SUVDAT", masterRdHMap.get("SUVDAT").substring(2));
			if(masterRdHMap.get("HOGI").length()>2) masterRdHMap.put("HOGI", masterRdHMap.get("HOGI").substring(2));
			if(masterRdHMap.get("UPS").length()>2) masterRdHMap.put("UPS", masterRdHMap.get("UPS").substring(2));

			if(masterRcHMap.get("SUVNAM").length()>2) masterRcHMap.put("SUVNAM", masterRcHMap.get("SUVNAM").substring(2));
			if(masterRcHMap.get("SUVDAT").length()>2) masterRcHMap.put("SUVDAT", masterRcHMap.get("SUVDAT").substring(2));
			if(masterRcHMap.get("HOGI").length()>2) masterRcHMap.put("HOGI", masterRcHMap.get("HOGI").substring(2));
			if(masterRcHMap.get("UPS").length()>2) masterRcHMap.put("UPS", masterRcHMap.get("UPS").substring(2));
			
			if(masterRcMMap.get("SUVNAM").length()>2) masterRcMMap.put("SUVNAM", masterRcMMap.get("SUVNAM").substring(2));
			if(masterRcMMap.get("SUVDAT").length()>2) masterRcMMap.put("SUVDAT", masterRcMMap.get("SUVDAT").substring(2));
			if(masterRcMMap.get("HOGI").length()>2) masterRcMMap.put("HOGI", masterRcMMap.get("HOGI").substring(2));
			if(masterRcMMap.get("UPS").length()>2) masterRcMMap.put("UPS", masterRcMMap.get("UPS").substring(2));

			// 실측데이터 입력
			inputData.put("masterHMap", masterHMap);
			inputData.put("masterMMap", masterMMap);
			inputData.put("masterCEMap", masterCEMap);
			inputData.put("masterLdHMap", masterLdHMap);
			inputData.put("masterLcHMap", masterLcHMap);
			inputData.put("masterLcMMap", masterLcMMap);
			inputData.put("masterRdHMap", masterRdHMap);
			inputData.put("masterRcHMap", masterRcHMap);
			inputData.put("masterRcMMap", masterRcMMap);
			
			// 승강로 부분
			inputData.put("hMapList", hMapList);
			// 기계실 부분
			inputData.put("mMapList", mMapList);
			// CWT.상세및층고부분
			inputData.put("ceMapList", ceMapList);
			// 층고데이터
			inputData.put("floorMap", floorMap);
			// 표시지
			inputData.put("viewMapList", viewMapList);
			// Jamb 리스트
			inputData.put("jambMapList", jambMapList);
			
			// 승강로 부분 (좌횡락 분리)
			inputData.put("ldhMapList", ldhMapList);
			// 승강로 부분 (좌횡락 공용)
			inputData.put("lchMapList", lchMapList);
			// 기계실 부분 (좌횡락 분리,공용)
			inputData.put("lmMapList", lmMapList);
			// 승강로 부분 (우횡락 분리))
			inputData.put("rdhMapList", rdhMapList);
			// 승강로 부분 (우횡락 공용)
			inputData.put("rchMapList", rchMapList);
			// 기계실 부분 (우횡락 분리,공용))
			inputData.put("rmMapList", rmMapList);
			
			List<String> rmSheet = new ArrayList<String>();
			if(hMapList.size()==0) {
				rmSheet.add("승강로 부분");
			}
			if(mMapList.size()==0) {
				rmSheet.add("기계실 부분");
			}
			if(ldhMapList.size()==0) {
				rmSheet.add("승강로 부분 (좌횡락 분리)");
			}
			if(lchMapList.size()==0) {
				rmSheet.add("승강로 부분 (좌횡락 공용)");
			}
			if(lmMapList.size()==0) {
				rmSheet.add("기계실 부분 (좌횡락 분리,공용)");
			}
			if(rdhMapList.size()==0) {
				rmSheet.add("승강로 부분 (우횡락 분리))");
			}
			if(rchMapList.size()==0) {
				rmSheet.add("승강로 부분 (우횡락 공용)");
			}
			if(rmMapList.size()==0) {
				rmSheet.add("기계실 부분 (우횡락 분리,공용))");
			}

			if(rmSheet.size()>0) {
				String[] rmArray = new String[rmSheet.size()];
				for(int i=0; i < rmSheet.size(); i++) {
					rmArray[i] = rmSheet.get(i);
				}
				xls.setSpreadsheetsToRemove(rmArray);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return inputData;
	}
	
	/**
	 * 
	 * 50단위 올림
	 */
	private String getFiftyRound(double x) {
		x = x / 100;
		x = Math.ceil( x * 2 ) / 2;
		x = x * 100;
		BigDecimal bd = new BigDecimal((double)x);
		return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
	}	

	/**
	 * 
	 * 5단위 버림 함수
	 */
	private String getFiveTrunck(double x) {
		x = x / 10;
		x = Math.floor( x * 2 ) / 2;
		x = x * 10;
		BigDecimal bd = new BigDecimal((double)x);
		return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
	}

	/**
	 * 
	 * 10단위 버림 함수
	 */
	private String getTenTrunck(double x) {
		x = x / 10;
		x = (float) (Math.floor( x ));
		x = x * 10;
		BigDecimal bd = new BigDecimal((double)x);
		return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
	}
}
