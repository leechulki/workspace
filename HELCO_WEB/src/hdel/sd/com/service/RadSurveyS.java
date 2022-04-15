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
 * ���𵨸��� Duty ó�� ���� Ŭ����
 * 
 * @author  �ڼ���
 * @since 2021.01.26
 * @version 1.0
 * @see 
 * <pre>
 *  == �����̷�(Modification Information) ==
 *   
 *          ������          ������           ��������
 *  ----------------    ------------    ---------------------------
 *   2021.01.26         �ڼ���          ���� ����
 * 
 * </pre>
 */

@Service
public class RadSurveyS {

	// �α׼���
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
	private String[] floorNm  = {"��/ȣ��", "PIT" ,"40��"  ,"39��"  ,"38��"  ,"37��"  ,"36��"  ,"35��"  ,"34��"  ,"33��"  ,"32��"  ,"31��"  ,"30��"  ,"29��"  ,"28��"  ,"27��"  ,"26��"  ,"25��"  ,"24��"  ,"23��"  ,"22��"  ,"21��"  ,"20��"  ,"19��"  ,"18��"  ,"17��"  ,"16��"  ,"15��"  ,"14��"  ,"13��"  ,"12��"  ,"11��"  ,"10��"  ,"09��"  ,"08��"  ,"07��"  ,"06��"  ,"05��"  ,"04��"  ,"03��"  ,"02��"  ,"01��"  ,"B1��"  ,"B2��"  ,"B3��"  ,"B4��"  ,"B5��"}; // ,"B6��"       , "B7��"       ,"B8��"        ,"B9��"        ,"B10��"
	private int[] floorNum    = {1 ,2 ,3 ,4 ,5 ,6 ,7 ,8 ,9 ,10 ,11 ,12 ,13 ,14 ,15 ,16 ,17 ,18 ,19 ,20 ,21 ,22 ,23 ,24 ,25 ,26 ,27 ,28 ,29 ,30 ,31 ,32 ,33 ,34 ,35 ,36 ,37 ,38 ,39 ,40 ,41 ,42 ,43 ,44 ,45 ,46 ,47}; // ,48           , 49           ,50            ,51            ,52

	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate)srmQqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	/**
	 * 
	 * ������ ���𵨸� ������� ���� ��ȸ
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
	 * ���� ���𵨸� ������� ���� ��ȸ
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
	 * ������ ���𵨸� ������� ��ȸ
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
	 * sap ���𵨸� ������� ��ȸ
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
	 * ���� ���𵨸� ������� ��ȸ
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
	 * ���� ǥ��� �Է� ������ ��ȸ
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
	 * ���� JAMB �Է� ������ ��ȸ
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
	 * �������� ����������� ���� ������ �����Ѵ�.
	 * @param Map<String, String> prhMap, List<Map<String, String>>
	 * @param model
	 * @return
	 */
	public List<String> getFloorSpreadOut(String sUVSN, Map<String, String> prhMap, Map<String, FloorNmVO> floorPrhMap, List<SuvFloorVO> suvFloorList
			                             , List<Map> suvJambList, Map<Integer, String> floorSuvMap) {
		// ������� �������� ���� Ŭ���� �и�
		List<String> errMsgList = radFloorS.getFloorSpreadOut(sUVSN, prhMap, floorPrhMap, suvFloorList, suvJambList, floorSuvMap);
        return errMsgList;
	}

	/**
	 * 
	 * ������� �� ���� �������� ������ ����
	 * @throws SQLException 
	 */
	public void saveFloorOut(Map<String, Object> inMap, List<FloorNmVO> floorPrhList, HashMap<String, String> suvPrhMap, Map<String, Object> outMap) throws Exception {
		
		// floorPrhList: ȭ�鿡�� ���� ����������� ��
		// suvPrhMap: ��簪
		
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
				// FLOORDB �� ����
				floorNmVO.setFLOORDB(floorNmVO.getFLOOR().replace("B", "-"));
				dao.insertZPST1138(floorNmVO);
				dao.insertZPST1139(floorNmVO);
			}			
			session.commit();
			
			int tmpMaxFloorNum = -99;
			String maxFloor = ""; // �ƽ� �� ����
            // ��ID �������� �������� ����
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
						// ������ ���� �����͸� �����Ѵ�.
						floorID = "ELR_C_EFHB"+floorNmVO.getFLOOR().substring(1);
						floorNmID = "ELC_C_EFNB"+floorNmVO.getFLOOR().substring(1);
						if(suvPrhMap.get(floorID) != null) {
							if(!"".equals(suvPrhMap.get(floorID))) {
								outMap.put("ELC_C_EFNB"+floorNmVO.getFLOOR().substring(1), floorNmVO.getFLOORNM());
							}
						}
					} else {
						// ������ �������͸� �����Ѵ�.
						if( (new Integer(floorNmVO.getFLOOR()).intValue()) < 10 ) {
							floorID = "ELR_C_EFH0"+floorNmVO.getFLOOR();
							floorNmID = "ELC_C_EFN0"+floorNmVO.getFLOOR();
						} else {
							floorID = "ELR_C_EFH"+floorNmVO.getFLOOR();
							floorNmID = "ELC_C_EFN"+floorNmVO.getFLOOR();
						}
					}
					
					// ���� ������ ����
					outMap.put(floorID, suvprd);
					// ���� ������ ����
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

			// �� -> ��ID �������� ����ID �����Ϳ� ����
			// �� --> ����ID �������� ����ID �����Ϳ� ����
			// ����ġ �� �ֻ����� E008 Overhead �� �Է� �մϴ�.
			// ���� ���� ������ ���� �����Ͱ� ���� ū ���̷� ���� ���� ����������� �����Ѵ�.
			// ����ġ ������ ���� �ջ� �Ͽ�. E009 Travel Height ���. ( ���� �ֻ����� �ջ꿡�� ����. )
			// ����ġ �� Pit ��E010 Pit �� �Է��մϴ�.
			String ELR_C_EHO = "";
			String ELC_C_EHTRH = "";
			String ELC_C_EHTH  = "";
			int travelHeight = 0;
			int allHeight = 0;
			String tmpFLOORPRD = "";
			for( String key : floorMap.keySet() ) {
				FloorNmVO floorVo = floorMap.get(key);
				// �ƽ� ���� �ƴѰ��
				if(maxFloor.equals(key)) {
					// �ֻ��� ( OH ) �� ��� 5���� �������� ���� �ٶ��ϴ�.
					// ����ġ 5679 ==> 5675 ����, ����ġ 5674 ==> 5670 ���� ����
					tmpFLOORPRD = getFiveTrunck(new Double(floorVo.getFLOORPRD()).doubleValue());
					floorVo.setFLOORPRD(tmpFLOORPRD);
				} else {
                    // ���� ��. �ֻ����� ������ �� ������ 50������ �ø��Ͽ� ���� �ٶ��ϴ�
					// ex ) ����ġ 2782 ==> 2800 ����, ����ġ 2733 ==> 2750���� ����
					tmpFLOORPRD = getFiftyRound(new Double(floorVo.getFLOORPRD()).doubleValue());
					floorVo.setFLOORPRD(tmpFLOORPRD);
					travelHeight = travelHeight + new Integer(tmpFLOORPRD).intValue();
				}
				allHeight = allHeight + new Integer(tmpFLOORPRD).intValue();
				
				// ���� �����
				outMap.put(floorVo.getFLOORNM(), floorVo.getFLOORPRD());
	        }

			String ELR_C_EHP = suvPrhMap.get("ELR_C_EHP");
			allHeight = allHeight + new Integer(getFiveTrunck(new Double(ELR_C_EHP).doubleValue())).intValue();
			ELC_C_EHTRH = new Integer(travelHeight).toString();
			FloorNmVO maxfloorVo = floorMap.get(maxFloor);
			ELR_C_EHO = maxfloorVo.getFLOORPRD();
			ELC_C_EHTH = new Integer(allHeight).toString();
			
			// ����� ����
			outMap.put("ELR_C_EHO", ELR_C_EHO);
			outMap.put("ELC_C_EHTRH", ELC_C_EHTRH);
			outMap.put("ELC_C_EHTH", ELC_C_EHTH);
			
			// pit�� 5���� �������� ����
			outMap.put("ELR_C_EHP", getFiveTrunck(new Double(ELR_C_EHP).doubleValue()));
			
			// ������ �׷� �����͸� ��ȸ�Ѵ�.
			// ��ȸ�� �׷��� 3�� �̻��̸� ���� ������ ����ó���� ����ڰ� �����Ѵ�.
			List<Map> jambList = new ArrayList<Map>();
			List<Map> jambTmpList = dao.selectJampPrhSubPrhList(inMap);
			
			// ���� jAMB�� ���� ��� ��Ű�� ���� ��ȸ�� ����Ʈ���� ������ �����Ѵ�.
			// EL_EMF C123 ������(�⺻�Է�)
            String EL_EMF = (String)inMap.get("EL_EMF");
            if(EL_EMF == null) {
            	EL_EMF = "1";
            } else {
            	if("".equals(EL_EMF)) {
                	EL_EMF = "1";
            	}
            }

            // �⺻���� ����� ������ �׷��� ã�´�.
            // mainl �׷����� ���� ��µǾ�� �Ѵ�.
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
			
			// ���� ��ȸ�� �׷��� 3�� �̻��̸� ���� �������� �Ұ� �޽����� ����Ѵ�.
			if(jambList.size() > 3) {
	            throw new BizException("������ ���Ա� ���嵵 �׷� �����Ͱ� "+jambList.size()+"���� �����Ǿ� ���� ��������� ������ �� �����ϴ�.");
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

			    // JAMB �׷����� ����
			    outMap.put("ELC_E_CJM"+JAMBGRPNUM, JAMBMODEL);
			    
			    // ������ ������ �׷��� ó���ؼ� �����͸� ����� JAMB �������� �����ؾ� �Ѵ�.
			    String grpFloorNm = radFloorS.getGroupFloorNm(FLOOR, FLOORNM);
			    outMap.put("ELC_E_CJMF"+JAMBGRPNUM, grpFloorNm);
			    
			    // HPI ������� �����͸� �����Ѵ�.
			    if(!"".equals(HPIMODEL)) {
			    	Map<String, Object> prdMap = null;
			    	// ��ǰġ�� �����͸� ��ȸ�Ѵ�.
			    	inMap.put("SUVATTR", HPIMODEL);
			    	prdMap = dao.selectHpiZPST1133(inMap);
                    
			    	if( prdMap == null ) {
			            throw new BizException("��ϵ� HPI("+HPIMODEL+") ���� ���𵨸� �ڵ����� ���� ����� �ƴմϴ�.");
			    	}
			    	
			    	// ��ǰ����
			    	String ELC_E_HPBX = (String)prdMap.get("ELC_E_HPBX");
			    	String ELC_E_HPW = (String)prdMap.get("ELC_E_HPW");
			    	String ELC_E_HPD = (String)prdMap.get("ELC_E_HPD");
			    	//String ELC_E_M_FORM = (String)prdMap.get("ELC_E_M_FORM");
			    	String ELC_E_BP_TYPE = (String)prdMap.get("ELC_E_BP_TYPE");
			    	if(ELC_E_HPBX == null) ELC_E_HPBX = "";
			    	
			    	if( "".equals(ELC_E_HPBX)) {
			            throw new BizException("��ϵ� HPI("+HPIMODEL+") ���� ���𵨸� �ڵ����� ���� ����� �ƴմϴ�.");
		    		} else {
		    			// hpi ������
		    			String HPI_ELR_E_A = (String)jampMap.get("HPI_ELR_E_A");
		    			String HPI_ELR_E_B = (String)jampMap.get("HPI_ELR_E_B");
		    			
		    			// �������� �������� �ʴ� ��� ó��
		    			if(HPI_ELR_E_A == null) {
		    				// ��ǰ�԰ݻ����� ����
		    				// �׷�ó���� ��ǰ �԰ݸ� �����Ѵ�.
		    				outMap.put("ELC_E_HPI"+JAMBGRPNUM, HPIMODEL);
		    				outMap.put("ELC_E_HPBX"+JAMBGRPNUM, ELC_E_HPBX);
		    				outMap.put("ELC_E_HPW"+JAMBGRPNUM, ELC_E_HPW);
		    				outMap.put("ELC_E_HPD"+JAMBGRPNUM, ELC_E_HPD);
		    			} else {
		    				outMap.put("ELC_E_HPI"+JAMBGRPNUM, HPIMODEL);
		    				outMap.put("ELC_E_HPBX"+JAMBGRPNUM, ELC_E_HPBX);
		    				// hpi ��ǰ����
		    				outMap.put("ELC_E_HPW"+JAMBGRPNUM, ELC_E_HPW);
		    				outMap.put("ELC_E_HPD"+JAMBGRPNUM, ELC_E_HPD);
		    				// hpi ��������
		    				outMap.put("ELC_E_HPA"+JAMBGRPNUM, HPI_ELR_E_A);
		    				outMap.put("ELC_E_HPB"+JAMBGRPNUM, HPI_ELR_E_B);
		    				
		    				// base Plate ��갪
		    				String sCalRslt[] = radEnterS.getCalBasePlate(HPI_ELR_E_A, HPI_ELR_E_B, ELC_E_HPW, ELC_E_HPD, "HPI", ELC_E_BP_TYPE);

		    				// ���̽� �÷���Ʈ ���뿩�� üũ
                        	// ELC_E_HBW -- ��ǰ����
                        	// ELC_E_HBD -- ��ǰ����
                        	double delcehpw = new Double(ELC_E_HPW).doubleValue();
                        	double delcehpd = new Double(ELC_E_HPD).doubleValue();
                        	double dcalRslt0 = new Double(sCalRslt[0]).doubleValue();
                        	double dcalRslt1 = new Double(sCalRslt[1]).doubleValue();

                        	// ��ǰǥ�� ����� ���̽� �÷���Ʈ ������ �����ϸ� ���̽� �÷���Ʈ ���ذ��� �������� �ʴ´�.
                        	if((delcehpw >= dcalRslt0) && (delcehpd >= dcalRslt1)) {
                        	} else {
                        		sCalRslt = radEnterS.getCalBasePlateAdd(HPI_ELR_E_A, HPI_ELR_E_B, ELC_E_HPW, ELC_E_HPD, "HPI", ELC_E_BP_TYPE);
                                if("P".equals(ELC_E_BP_TYPE)) {
        		    				outMap.put("ELC_E_HPW"+JAMBGRPNUM, sCalRslt[0]);
        		    				outMap.put("ELC_E_HPD"+JAMBGRPNUM, sCalRslt[1]);
                                } else {
                            		// ���̽� �÷���Ʈ�� ����� ��
                                	outMap.put("ELC_E_HPPW"+JAMBGRPNUM, sCalRslt[0]);
                                	outMap.put("ELC_E_HPPD"+JAMBGRPNUM, sCalRslt[1]);
                                }
                        	}
		    			}
		    		}                    
			    }

			    // HPB, HIP ������� �����͸� �����Ѵ�.
			    if(!"".equals(HPBMODEL) || !"".equals(HIPMODEL)) {
			    	Map<String, Object> prdMap = null;
			    	// ��ǰġ�� �����͸� ��ȸ�Ѵ�.
			    	if(!"".equals(HPBMODEL)) {
				    	inMap.put("SUVATTR", HPBMODEL);
				    	prdMap = dao.selectHpbZPST1133(inMap);
			    	} else {
				    	inMap.put("SUVATTR", HIPMODEL);
				    	prdMap = dao.selectHipZPST1133(inMap);
			    	}

			    	if( prdMap == null) {
			            throw new BizException("��ϵ� HPB("+HPBMODEL+") ���� ���𵨸� �ڵ����� ���� ����� �ƴմϴ�.");
			    	}

			    	// ��ǰ����
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
	    				// ��ŷŰ ���� ��ǰ�԰� ����
                		ELC_E_HBW = ELC_E_HBW_PKS;
                		ELC_E_HBD = ELC_E_HBD_PKS;
                	}

			    	if(ELC_E_HBBX == null) ELC_E_HBBX = ""; 
			    	
			    	if( "".equals(ELC_E_HBBX)) {
			            throw new BizException("��ϵ� HPB("+HPBMODEL+") ���� ���𵨸� �ڵ����� ���� ����� �ƴմϴ�.");
		    		} else {
		    			// base plate ���� ���� ó���� �����Ѵ�.
		    			// ��ŷ����ġ ���� ���� �б�
		    			// hpb ������
		    			String BTN_ELR_E_A = (String)jampMap.get("BTN_ELR_E_A");
		    			String BTN_ELR_E_B = (String)jampMap.get("BTN_ELR_E_B");
		    			String BTN_ELR_E_F = (String)jampMap.get("BTN_ELR_E_F");
		    			String BTN_ELC_E_CHB = (String)jampMap.get("BTN_ELC_E_CHB"); // ������ư �߰�
		    			//String BTN_ELR_E_C = (String)jampMap.get("BTN_ELR_E_C");
		    			//String BTN_ELR_E_D = (String)jampMap.get("BTN_ELR_E_D");
		    			String BTN_ELR_E_E = (String)jampMap.get("BTN_ELR_E_E");
		    			if(BTN_ELR_E_A == null) BTN_ELR_E_A = "";
		    			if(BTN_ELR_E_E == null) BTN_ELR_E_E = "";
		    			
		    			// ��ŷ����ġ ������
		    			String PKS_ELR_E_A = (String)jampMap.get("PKS_ELR_E_A");
		    			String PKS_ELR_E_B = (String)jampMap.get("PKS_ELR_E_B");
		    			String PKS_ELR_E_F = (String)jampMap.get("PKS_ELR_E_F");
		    			//String PKS_ELR_E_C = (String)jampMap.get("PKS_ELR_E_C");
		    			//String PKS_ELR_E_D = (String)jampMap.get("PKS_ELR_E_D");
		    			//String PKS_ELR_E_E = (String)jampMap.get("PKS_ELR_E_E");
	    				//  calWith, calHeigth �����Ϸ�
		    			if(PKS_ELR_E_A == null) PKS_ELR_E_A = "";

		    			String modelType = "";
		    			if(!"".equals(HPBMODEL)) {
		    				modelType = "HPB";
		    				outMap.put("ELC_E_HB"+JAMBGRPNUM, HPBMODEL);
		    			} else {
		    				modelType = "HIP";
		    				outMap.put("ELC_E_HB"+JAMBGRPNUM, HIPMODEL);
		    			}

		    			// ������ư Ÿ�� ���� ��簪 ����
		    			outMap.put("ELC_E_CHB"+JAMBGRPNUM, BTN_ELC_E_CHB);
		    			
		    			// ���� �������� ������ ��ǰ�԰ݸ� ����
		    			if("".equals(BTN_ELR_E_A)) {
		    				outMap.put("ELC_E_HBBX"+JAMBGRPNUM, ELC_E_HBBX);
		    				outMap.put("ELC_E_HBW"+JAMBGRPNUM, ELC_E_HBW);
		    				outMap.put("ELC_E_HBD"+JAMBGRPNUM, ELC_E_HBD);
		    			} else {
		    				outMap.put("ELC_E_HBBX"+JAMBGRPNUM, ELC_E_HBBX);
		    				outMap.put("ELC_E_HBW"+JAMBGRPNUM, ELC_E_HBW);
		    				outMap.put("ELC_E_HBD"+JAMBGRPNUM, ELC_E_HBD);

		    				// ��ư ��������
		    				outMap.put("ELC_E_HBA"+JAMBGRPNUM, BTN_ELR_E_A);
		    				outMap.put("ELC_E_HBB"+JAMBGRPNUM, BTN_ELR_E_B);
		    				
		    				String sCalRslt[] = null;
                            if("".equals(PKS_ELR_E_A)) {
                            	outMap.put("ELC_E_APK"+JAMBGRPNUM, "N");
                            	
                                if("Y".equals(BTN_ELC_E_CHB) && !"".equals(BTN_ELR_E_E)) {
                                	// ������ư 2���� ��� ���� ������� = BTN_ELR_E_A + BTN_ELR_E_E
                                	int cirHeigth = new Integer(BTN_ELR_E_A).intValue() + new Integer(BTN_ELR_E_E).intValue();
        		    				sCalRslt = radEnterS.getCalBasePlate(BTN_ELR_E_A, new Integer(cirHeigth).toString(), ELC_E_HBW, ELC_E_HBD, modelType, ELC_E_BP_TYPE);
                                } else {
        		    				// base Plate ��갪
        		    				sCalRslt = radEnterS.getCalBasePlate(BTN_ELR_E_A, BTN_ELR_E_B, ELC_E_HBW, ELC_E_HBD, modelType, ELC_E_BP_TYPE);
                                }
                            } else {
                            	outMap.put("ELC_E_APK"+JAMBGRPNUM, "Y");
    		    				// base Plate ��갪
                            	// 1. ELC_E_HBW_PKS
                            	// 2. ELC_E_HBD_PKS
                            	// 3. BTN_ELR_E_F
                            	// 4. PKS_ELR_E_A
                            	// 5. PKS_ELR_E_F
                            	// 6. ELC_E_HBW_PKS
                            	// 7. ELC_E_HBD_PKS
                            	// 8. modelType
                            	// 9. ELC_E_BP_TYPE
                            	// �ش� ������ ���� ���� ���߰ڱ���
                            	// BTN_ELR_E_E�� �����ϸ� ��ư 2�� ¥���� �����Ѵ�.
                                if("Y".equals(BTN_ELC_E_CHB) && !"".equals(BTN_ELR_E_E)) {
                                	// ������ư 2���� ��� ���� ������� = BTN_ELR_E_A + BTN_ELR_E_E
                                	int cirHeigth = new Integer(BTN_ELR_E_A).intValue() + new Integer(BTN_ELR_E_E).intValue();
                                	sCalRslt = radEnterS.getCalPksBasePlate(BTN_ELR_E_A, new Integer(cirHeigth).toString(), BTN_ELR_E_F, PKS_ELR_E_A, PKS_ELR_E_B, PKS_ELR_E_F
				                              , ELC_E_HBW_PKS, ELC_E_HBD_PKS, modelType, ELC_E_BP_TYPE);
                                } else {
                                	sCalRslt = radEnterS.getCalPksBasePlate(BTN_ELR_E_A, BTN_ELR_E_B, BTN_ELR_E_F, PKS_ELR_E_A, PKS_ELR_E_B, PKS_ELR_E_F
				                              , ELC_E_HBW_PKS, ELC_E_HBD_PKS, modelType, ELC_E_BP_TYPE);
                                }
                            }

                        	// ���̽� �÷���Ʈ ���뿩�� üũ
                        	// ELC_E_HBW -- ��ǰ����
                        	// ELC_E_HBD -- ��ǰ����
                        	double delcehbw = new Double(ELC_E_HBW).doubleValue();
                        	double delcehbd = new Double(ELC_E_HBD).doubleValue();
                        	double dcalRslt0 = new Double(sCalRslt[0]).doubleValue();
                        	double dcalRslt1 = new Double(sCalRslt[1]).doubleValue();

                        	//System.out.println("delcehbw:"+delcehbw+", dcalRslt0:"+dcalRslt0+", delcehbd:"+delcehbd+", dcalRslt1:"+dcalRslt1);
                        	// ��ǰǥ�� ����� ���̽� �÷���Ʈ ������ �����ϸ� ���̽� �÷���Ʈ ���ذ��� �������� �ʴ´�.
                        	if((delcehbw >= dcalRslt0) && (delcehbd >= dcalRslt1)) {
                        	} else {
                                if("".equals(PKS_ELR_E_A)) {
                                	outMap.put("ELC_E_APK"+JAMBGRPNUM, "N");
                                    if("Y".equals(BTN_ELC_E_CHB) && !"".equals(BTN_ELR_E_E)) {
                                    	// ������ư 2���� ��� ���� ������� = BTN_ELR_E_A + BTN_ELR_E_E
                                    	int cirHeigth = new Integer(BTN_ELR_E_A).intValue() + new Integer(BTN_ELR_E_E).intValue();
            		    				sCalRslt = radEnterS.getCalBasePlateAdd(BTN_ELR_E_A, new Integer(cirHeigth).toString(), ELC_E_HBW, ELC_E_HBD, modelType, ELC_E_BP_TYPE);
                                    } else {
            		    				// base Plate ��갪
            		    				sCalRslt = radEnterS.getCalBasePlateAdd(BTN_ELR_E_A, BTN_ELR_E_B, ELC_E_HBW, ELC_E_HBD, modelType, ELC_E_BP_TYPE);
                                    }
                                } else {
                                	outMap.put("ELC_E_APK"+JAMBGRPNUM, "Y");
                                    if("Y".equals(BTN_ELC_E_CHB) && !"".equals(BTN_ELR_E_E)) {
                                    	// ������ư 2���� ��� ���� ������� = BTN_ELR_E_A + BTN_ELR_E_E
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
                            		// ���̽� �÷���Ʈ�� ����� ��
    								outMap.put("ELC_E_HBPW"+JAMBGRPNUM, sCalRslt[0]);
    								outMap.put("ELC_E_HBPD"+JAMBGRPNUM, sCalRslt[1]);
                                }
                        	}

    	    				// Ȧ��ư ���� --> ����ο� ���� ó�� ���� ����
    	    				// ������� ��쿡�� ó��
    	    				// EL_AUSE : HC(���),  EH(���,���), OH(����,���), BH(����,���), NH(����,���), HF(��ȭ,���)
                            // String EL_AUSE
    	    				String elAuse = (String)inMap.get("EL_AUSE");
                            if(elAuse == null) elAuse = "";
                            if("HC".equals(elAuse) || "EH".equals(elAuse) || "OH".equals(elAuse) || "BH".equals(elAuse) || "HF".equals(elAuse)) {
                            	// ���� ó������ �߰�
                            	if(BTN_ELR_E_F == null) BTN_ELR_E_F = "";
                            	if(BTN_ELR_E_B == null) BTN_ELR_E_B = "";

                			    // if(!"".equals(HPBMODEL) || !"".equals(HIPMODEL)) {
            			    	if( "".equals(BTN_ELR_E_F) || "".equals(BTN_ELR_E_B)) {
            			    		if( "".equals(BTN_ELR_E_F) && !"".equals(BTN_ELR_E_B)) {
                			            throw new BizException("Ȧ��ư(HPI, HBP)�� F ����ġ���� ���� ����� Ȧ���̸� ����� �� �����ϴ�. Ȧ��ư(HPI, HBP) F �������� Ȯ���� �ֽʽÿ�.");
            			    		} if( !"".equals(BTN_ELR_E_F) && "".equals(BTN_ELR_E_B)) {
                			            throw new BizException("Ȧ��ư(HPI, HBP)�� B ����ġ���� ���� ����� Ȧ���̸� ����� �� �����ϴ�. Ȧ��ư(HPI, HBP) B �������� Ȯ���� �ֽʽÿ�.");
            			    		} if( "".equals(BTN_ELR_E_F) && "".equals(BTN_ELR_E_B)) {
                			            throw new BizException("Ȧ��ư(HPI, HBP)�� B,F ����ġ���� ���� ����� Ȧ���̸� ����� �� �����ϴ�. Ȧ��ư(HPI, HBP) B,F �������� Ȯ���� �ֽʽÿ�.");
                 			    	}                                 
            			    	}                       	
                            	// ����� ������ ��� Ȧ���̸� ����Ѵ�.
        	    				String sNudeHeight = radEnterS.getNudeCalHeight(BTN_ELR_E_F, BTN_ELR_E_B);
        	    				outMap.put("ELC_E_HBF"+JAMBGRPNUM, sNudeHeight);
                            }
		    			}
		    		}
			    }

			    // Ȧ���� ������� �����͸� �����Ѵ�.
			    if(!"".equals(HLTNMODEL)) {
			    	Map<String, Object> prdMap = null;
			    	// ��ǰġ�� �����͸� ��ȸ�Ѵ�.
			    	inMap.put("SUVATTR", HLTNMODEL);
			    	prdMap = dao.selectHltZPST1133(inMap);

			    	if( prdMap == null) {
			            throw new BizException("��ϵ� Ȧ����("+HLTNMODEL+") ���� ���𵨸� �ڵ����� ���� ����� �ƴմϴ�.");
			    	}
			    	
			    	// ��ǰ����
			    	String ELC_E_HLBX = (String)prdMap.get("ELC_E_HLBX");
			    	String ELC_E_HLW = (String)prdMap.get("ELC_E_HLW");
			    	String ELC_E_HLD = (String)prdMap.get("ELC_E_HLD");
			    	String ELC_E_M_FORM = (String)prdMap.get("ELC_E_M_FORM");
			    	String ELC_E_BP_TYPE = (String)prdMap.get("ELC_E_BP_TYPE");
			    	if(ELC_E_HLBX == null) ELC_E_HLBX = "";

			    	if( "".equals(ELC_E_HLBX)) {
			            throw new BizException("��ϵ� Ȧ����("+HLTNMODEL+") ���� ���𵨸� �ڵ����� ���� ����� �ƴմϴ�.");
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
		    			
		    			// �������� ���� ��� ó��
		    			if("".equals(HLT_ELR_E_A)) {
		    				outMap.put("ELC_E_HL"+JAMBGRPNUM, HLTNMODEL);
		    				// ��ǰ�԰�
		    				outMap.put("ELC_E_HLBX"+JAMBGRPNUM, ELC_E_HLBX);
		    				outMap.put("ELC_E_HLW"+JAMBGRPNUM, ELC_E_HLW);
		    				outMap.put("ELC_E_HLD"+JAMBGRPNUM, ELC_E_HLD);
		    				// Ȧ���� ��ġ
		    				// ���� Ȧ���� ��ġġ ���� ���� ���� ?  EL_EHBLC: �ڵ����� Ȧ��ư ��ġ -> ELR_H_BTNP
		    				if(HLT_ELR_E_HLLC == null) {
		    					HLT_ELR_E_HLLC = suvPrhMap.get("ELR_H_BTNP");
		    				}
		    				outMap.put("ELC_E_HLLC"+JAMBGRPNUM, HLT_ELR_E_HLLC);
		    			} else {
		    				outMap.put("ELC_E_HL"+JAMBGRPNUM, HLTNMODEL);
		    				// ��ǰ�԰�
		    				outMap.put("ELC_E_HLBX"+JAMBGRPNUM, ELC_E_HLBX);
		    				outMap.put("ELC_E_HLW"+JAMBGRPNUM, ELC_E_HLW);
		    				outMap.put("ELC_E_HLD"+JAMBGRPNUM, ELC_E_HLD);
		    				// ���� Ȧ���� ��ġġ ���� ���� ���� ?  EL_EHBLC: �ڵ����� Ȧ��ư ��ġ -> ELR_H_BTNP
		    				if(HLT_ELR_E_HLLC == null) {
		    					HLT_ELR_E_HLLC = suvPrhMap.get("ELR_H_BTNP");
		    				}
		    				
		    				// Ȧ���� ��ġ
		    				outMap.put("ELC_E_HLLC"+JAMBGRPNUM, HLT_ELR_E_HLLC);
		    				// ��ǰ�԰�	    			
		    				// ��ǰ ����� �ٸ� ��� ��ǰ�԰� ���ǰ� �޶�����.
		    				if("C".equals(ELC_E_M_FORM)) {
		    				    // ������ ��� �߰��Ǵ� ����
		    					outMap.put("ELC_E_HLF"+JAMBGRPNUM, HLT_ELR_E_F);
		    					outMap.put("ELC_E_HLC"+JAMBGRPNUM, HLT_ELR_E_C);
		    				} else {
		    					// �簢���� ��� �߰��Ǵ� ����
		    					outMap.put("ELC_E_HLA"+JAMBGRPNUM, HLT_ELR_E_A);
		    					outMap.put("ELC_E_HLB"+JAMBGRPNUM, HLT_ELR_E_B);
			    				
			    				// ���̽��÷���Ʈ ����
			    				// base Plate ��갪
			    				String sCalRslt[] = radEnterS.getCalBasePlate(HLT_ELR_E_A, HLT_ELR_E_B, ELC_E_HLW, ELC_E_HLD, "HLT", ELC_E_BP_TYPE);

	                            // PLATE ���� Ÿ�� P: �÷���Ʈ ������ - ��ǰ������� ��ü, N: �÷���Ʈ ������
                            	// ���̽� �÷���Ʈ ���뿩�� üũ
                            	// ELC_E_HLW -- ��ǰ����
                            	// ELC_E_HLD -- ��ǰ����
                            	double delcehlw = new Double(ELC_E_HLW).doubleValue();
                            	double delcehhld = new Double(ELC_E_HLD).doubleValue();
                            	double dcalRslt0 = new Double(sCalRslt[0]).doubleValue();
                            	double dcalRslt1 = new Double(sCalRslt[1]).doubleValue();
                            	// ��ǰǥ�� ����� ���̽� �÷���Ʈ ������ �����ϸ� ���̽� �÷���Ʈ ���ذ��� �������� �ʴ´�.
                            	if((delcehlw >= dcalRslt0) && (delcehhld >= dcalRslt1)) {
                            	} else {
                            		sCalRslt = radEnterS.getCalBasePlateAdd(HLT_ELR_E_A, HLT_ELR_E_B, ELC_E_HLW, ELC_E_HLD, "HLT", ELC_E_BP_TYPE);
                                    if("P".equals(ELC_E_BP_TYPE)) {
            		    				outMap.put("ELC_E_HLW"+JAMBGRPNUM, sCalRslt[0]);
            		    				outMap.put("ELC_E_HLD"+JAMBGRPNUM, sCalRslt[1]);
                                    } else {
                                		// ���̽� �÷���Ʈ�� ����� ��
                                		outMap.put("ELC_E_HLPW"+JAMBGRPNUM, sCalRslt[0]);
    	                            	outMap.put("ELC_E_HLPD"+JAMBGRPNUM, sCalRslt[1]);
                                    }
                            	}
		    				}
		    			}
		    		}
			    }

			    // �ҹ潺��ġ ������� �����͸� �����Ѵ�.
			    if(!"".equals(FSWMODEL)) {
			    	Map<String, Object> prdMap = null;
			    	// ��ǰġ�� �����͸� ��ȸ�Ѵ�.
			    	inMap.put("SUVATTR", FSWMODEL);
			    	prdMap = dao.selectFswZPST1133(inMap);

			    	if( prdMap == null) {
			            throw new BizException("��ϵ� �ҹ潺��ġ("+FSWMODEL+") ���� ���𵨸� �ڵ����� ���� ����� �ƴմϴ�.");
			    	}
			    	
			    	String ELC_E_FSBX = (String)prdMap.get("ELC_E_FSBX");
			    	String ELC_E_FSW = (String)prdMap.get("ELC_E_FSW");
			    	String ELC_E_FSD = (String)prdMap.get("ELC_E_FSD");
			    	//String ELC_E_M_FORM = (String)prdMap.get("ELC_E_M_FORM");
			    	String ELC_E_BP_TYPE = (String)prdMap.get("ELC_E_BP_TYPE");
			    	if(ELC_E_FSBX == null) ELC_E_FSBX = "";
			    	
			    	if( "".equals(ELC_E_FSBX)) {
			            throw new BizException("��ϵ� �ҹ潺��ġ("+FSWMODEL+") ���� ���𵨸� �ڵ����� ���� ����� �ƴմϴ�.");
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
                            // ����ġ�� ���� ���
                        	// �ҹ潺��ġ ��ǰ����
                        	outMap.put("ELC_E_FSBX"+JAMBGRPNUM, ELC_E_FSBX);
                        	outMap.put("ELC_E_FSW"+JAMBGRPNUM, ELC_E_FSW);
                        	outMap.put("ELC_E_FSD"+JAMBGRPNUM, ELC_E_FSD);
                        } else {
                        	// ����ġ�� �ִ� ��� ����
                        	// �ҹ潺��ġ ��ǰ����
                        	outMap.put("ELC_E_FSBX"+JAMBGRPNUM, ELC_E_FSBX);
                        	outMap.put("ELC_E_FSW"+JAMBGRPNUM, ELC_E_FSW);
                        	outMap.put("ELC_E_FSD"+JAMBGRPNUM, ELC_E_FSD);

    	    				// �ҹ潺��ġ ��������
                        	outMap.put("ELC_E_FSA"+JAMBGRPNUM, FSW_ELR_E_A);
                        	outMap.put("ELC_E_FSB"+JAMBGRPNUM, FSW_ELR_E_B);
                        	outMap.put("ELC_E_FSLC"+JAMBGRPNUM, FSW_ELR_E_FSLC);
    	    				
		    				// ���̽��÷���Ʈ ����
		    				// base Plate ��갪
		    				String sCalRslt[] = radEnterS.getCalBasePlate(FSW_ELR_E_A, FSW_ELR_E_B, ELC_E_FSW, ELC_E_FSD, "FSW", ELC_E_BP_TYPE);

                            // PLATE ���� Ÿ�� P: �÷���Ʈ ������ - ��ǰ������� ��ü, N: �÷���Ʈ ������
                        	// ���̽� �÷���Ʈ ���뿩�� üũ
                        	// ELC_E_FSW-- ��ǰ����
                        	// ELC_E_FSD-- ��ǰ����
                        	double delcefsw = new Double(ELC_E_FSW).doubleValue();
                        	double delcefsd = new Double(ELC_E_FSD).doubleValue();
                        	double dcalRslt0 = new Double(sCalRslt[0]).doubleValue();
                        	double dcalRslt1 = new Double(sCalRslt[1]).doubleValue();
                        	// ��ǰǥ�� ����� ���̽� �÷���Ʈ ������ �����ϸ� ���̽� �÷���Ʈ ���ذ��� �������� �ʴ´�.
                        	if((delcefsw >= dcalRslt0) && (delcefsd >= dcalRslt1)) {
                        	} else {
                        		sCalRslt = radEnterS.getCalBasePlateAdd(FSW_ELR_E_A, FSW_ELR_E_B, ELC_E_FSW, ELC_E_FSD, "FSW", ELC_E_BP_TYPE);
                                if("P".equals(ELC_E_BP_TYPE)) {
                                	outMap.put("ELC_E_FSW"+JAMBGRPNUM, sCalRslt[0]);
                                	outMap.put("ELC_E_FSD"+JAMBGRPNUM, sCalRslt[1]);
                                } else {
                            		// ���̽� �÷���Ʈ�� ����� ��
                            		outMap.put("ELC_E_FSPW"+JAMBGRPNUM, sCalRslt[0]);
                                	outMap.put("ELC_E_FSPD"+JAMBGRPNUM, sCalRslt[1]);
                                }
                        	}
                        }
		    		}
			    }
			}

			//���嵵���ǳڳ��� ��ȸ ELC_E_PNLH
			Map<String, Object> eMap = dao.selectElrEPnlh(inMap);
			String ELR_E_PNLH = (String)eMap.get("ELR_E_PNLH");
			String ELC_E_PNLHDEGR = (String)eMap.get("ELC_E_PNLHDEGR");
			// ���Ա� ����
			String ELR_H_HH = suvPrhMap.get("ELR_H_HH");
			outMap.put("ELC_E_PNLHDEGR", ELC_E_PNLHDEGR);
			if(ELR_E_PNLH != null) {
				if(!"".equals(ELR_E_PNLH)) {
					int iELR_E_PNLH = new Integer(ELR_E_PNLH).intValue();
				    int iELR_H_HH = new Integer(ELR_H_HH).intValue();
                    // 10���� ���� ������
				    String sCELR_H_HH = getTenTrunck(iELR_H_HH);
				    int iCELR_H_HH = new Integer(sCELR_H_HH).intValue();
				    if(iELR_E_PNLH <= (iELR_H_HH + 50) ) {
				    	// 10���� ���� ���������� ó���Ѵ�.
				    	iELR_E_PNLH = iCELR_H_HH + 30;
				    	outMap.put("ELC_E_PNLH", new Integer(iELR_E_PNLH).toString());
				    } else {
				    	// 10���� ���� ���������� ó���Ѵ�.
				    	iELR_E_PNLH = iCELR_H_HH + 80;
				    	outMap.put("ELC_E_PNLH", new Integer(iELR_E_PNLH).toString());
				    }
				}
			}

			// EL_DJM	K017 \(����) JAMB(MAIN)
			// EL_DJO	K018 \(����) JAMB(OTHER)
			// ELR_H_JJ	JJ CH-HH ���������ȿ� �־����
			// ELR_H_HH	HH
            // 50���� �ݿø�
			
			// ���� ���� ������ �����͸� �����ϰ� ����ó���� �����Ѵ�.
			dao.deleteEnterElcData(inMap);
			// ������ ������� ����Ŀ� �����Ѵ�.
			Map<String, Object> suvPrdMap = new HashMap<String, Object>();
			suvPrdMap.put("MANDT", inMap.get("MANDT"));
			suvPrdMap.put("SUVSN", inMap.get("SUVSN"));
			suvPrdMap.put("USER_NAME", inMap.get("G_USER_NAME"));
			suvPrdMap.put("SUVTYPE", "E");

			for( String key : outMap.keySet() ) {
				// ����� ����� �����Ѵ�.
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
	 * ������� �� ���� ������ �� ������ ����
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
	 * ������簪 ���� ��ȸ
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
	 * ������ ���� ������ ��ȸ
	 * @throws SQLException 
	 */
	public Map<String, Object> selectRadExcelData(Map<String, Object> inMap, XLSTransformer xls) throws Exception {
		Map<String, Object> inputData = new HashMap<String, Object>();
		SqlSession session =  sqlSessionFactory.openSession(false);
		session.getConnection().setAutoCommit(false);
		dao = session.getMapper(RadSurveyD.class);
		List<HashMap<String, Object>> hogiMapList = null;

		// �°��� �κ�
		HashMap<String, String> masterHMap = new HashMap<String, String>();
		// ���Ǻκ�
		HashMap<String, String> masterMMap = new HashMap<String, String>();
		// CWT, ���帶����
		HashMap<String, String> masterCEMap = new HashMap<String, String>();
		// �°��� �κ� (��Ⱦ�� �и�)
		HashMap<String, String> masterLdHMap = new HashMap<String, String>();
		// �°��� �κ� (��Ⱦ�� ����)
		HashMap<String, String> masterLcHMap = new HashMap<String, String>();
		// ���� �κ� (��Ⱦ�� �и�,����)
		HashMap<String, String> masterLcMMap = new HashMap<String, String>();
		// �°��� �κ� (��Ⱦ�� �и�))
		HashMap<String, String> masterRdHMap = new HashMap<String, String>();
		// �°��� �κ� (��Ⱦ�� ����)
		HashMap<String, String> masterRcHMap = new HashMap<String, String>();
		// ���� �κ� (��Ⱦ�� �и�,����))
		HashMap<String, String> masterRcMMap = new HashMap<String, String>();

		// �°��κκ�
		List<HashMap<String, Object>> hMapList = new ArrayList<HashMap<String, Object>>();
		// ���Ǻκ�
		List<HashMap<String, Object>> mMapList = new ArrayList<HashMap<String, Object>>();
		// CWT-����
		List<HashMap<String, Object>> ceMapList = new ArrayList<HashMap<String, Object>>();
		// ���� ������
		Map<String, Object> floorMap = new HashMap<String, Object>();
		Map<Integer, Object> floorTmpMap = new HashMap<Integer, Object>();
		List<HashMap<String, Object>> floorList = new ArrayList<HashMap<String, Object>>();
		
		// ǥ�ñ� ������		
		List<HashMap<String, Object>> viewMapList = new ArrayList<HashMap<String, Object>>();
		// Jamb ������		
		List<HashMap<String, Object>> jambMapList = new ArrayList<HashMap<String, Object>>();

		// �°��� �κ� (��Ⱦ�� �и�)
		List<HashMap<String, Object>> ldhMapList = new ArrayList<HashMap<String, Object>>();
		// �°��� �κ� (��Ⱦ�� ����)
		List<HashMap<String, Object>> lchMapList = new ArrayList<HashMap<String, Object>>();
		// ���� �κ� (��Ⱦ�� �и�,����)
		List<HashMap<String, Object>> lmMapList = new ArrayList<HashMap<String, Object>>();
		
		// �°��� �κ� (��Ⱦ�� �и�))
		List<HashMap<String, Object>> rdhMapList = new ArrayList<HashMap<String, Object>>();
		// �°��� �κ� (��Ⱦ�� ����)
		List<HashMap<String, Object>> rchMapList = new ArrayList<HashMap<String, Object>>();
		// ���� �κ� (��Ⱦ�� �и�,����))
		List<HashMap<String, Object>> rmMapList = new ArrayList<HashMap<String, Object>>();

		try {
			if(inMap.get("PRJNUM") != null) {
			    // ���� �� ������ ���� ������ ��ȸ
				hogiMapList = dao.selectOrderHGList(inMap);
			} else {
			    // ���� �� ������ ���� ������ ��ȸ
				hogiMapList = dao.selectQtHGList(inMap);
			}
			
			if(hogiMapList.size() == 0) return inputData;
			// �°��� �κ�
			masterHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterHMap.put("SUVNAM", "");
			masterHMap.put("SUVDAT", "");
			masterHMap.put("HOGI", "");
			masterHMap.put("UPS", "");
			// ���Ǻκ�
			masterMMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterMMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterMMap.put("SUVNAM", "");
			masterMMap.put("SUVDAT", "");
			masterMMap.put("HOGI", "");
			masterMMap.put("UPS", "");
			// CWT, ���帶����
			masterCEMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterCEMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterCEMap.put("SUVNAM", "");
			masterCEMap.put("SUVDAT", "");
			masterCEMap.put("HOGI", "");
			masterCEMap.put("UPS", "");
			
			// �°��� �κ� (��Ⱦ�� �и�)
			masterLdHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterLdHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterLdHMap.put("SUVNAM", "");
			masterLdHMap.put("SUVDAT", "");
			masterLdHMap.put("HOGI", "");
			masterLdHMap.put("UPS", "");
			
			// �°��� �κ� (��Ⱦ�� ����)
			masterLcHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterLcHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterLcHMap.put("SUVNAM", "");
			masterLcHMap.put("SUVDAT", "");
			masterLcHMap.put("HOGI", "");
			masterLcHMap.put("UPS", "");
			
			// ���� �κ� (��Ⱦ�� �и�,����)
			masterLcMMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterLcMMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterLcMMap.put("SUVNAM", "");
			masterLcMMap.put("SUVDAT", "");
			masterLcMMap.put("HOGI", "");
			masterLcMMap.put("UPS", "");
			
			// �°��� �κ� (��Ⱦ�� �и�))
			masterRdHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterRdHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterRdHMap.put("SUVNAM", "");
			masterRdHMap.put("SUVDAT", "");
			masterRdHMap.put("HOGI", "");
			masterRdHMap.put("UPS", "");
			
			// �°��� �κ� (��Ⱦ�� ����)
			masterRcHMap.put("PRJNUM", (String)hogiMapList.get(0).get("PRJNUM"));
			masterRcHMap.put("GSNAM", (String)hogiMapList.get(0).get("GSNAM"));
			masterRcHMap.put("SUVNAM", "");
			masterRcHMap.put("SUVDAT", "");
			masterRcHMap.put("HOGI", "");
			masterRcHMap.put("UPS", "");
			
			// ���� �κ� (��Ⱦ�� �и�,����))
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
				
				// �°��κκ�-���� �κ�-CWT��-���� ��ȸ
				List<HashMap<String, Object>> suvDataList = dao.selectSuvDataList(pramMap);
				// ����Ʈ ������ �ʵ����� ����
				HashMap<String, Object> suvHDataMap = new HashMap<String, Object>();
				HashMap<String, Object> suvCEDataMap = new HashMap<String, Object>();
				HashMap<String, Object> suvMDataMap = new HashMap<String, Object>();
				for(int j=0; j < suvDataList.size(); j++) {
					// �ڵ� ���ΰ����� ��ü�Ѵ�.
					String itemdiv = (String)suvDataList.get(j).get("ITEMDIV");
					if("M".equals(itemdiv)) {
						suvMDataMap.put((String)suvDataList.get(j).get("SUVPRH"), suvDataList.get(j).get("SUVPRDNAME"));
					} else if("B".equals(itemdiv) || "H".equals(itemdiv)) {
						suvHDataMap.put((String)suvDataList.get(j).get("SUVPRH"), suvDataList.get(j).get("SUVPRDNAME"));
					} else {
						// CWT, ���� ������
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

				// �°��� ���� ������ ����
				if("R".equals(ecwtp)) {
                    // �°��� �κ�, ���� �κ�
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

				//  CWT.�󼼹�����κ�, ����κ�
				ceMapList.add(suvCEDataMap);
				// CWT - ���� ������ ���� ����
				suvDat = masterCEMap.get("SUVDAT") + ", "+suvDatTmp;
				hogi = masterCEMap.get("HOGI") + ", "+hogiTmp;
				suvnam = masterCEMap.get("SUVNAM") + ", "+suvnamTmp;
				ups = masterCEMap.get("UPS") + ", "+upsTmp;
				masterCEMap.put("SUVDAT", suvDat);
				masterCEMap.put("HOGI", hogi);
				masterCEMap.put("SUVNAM", suvnam);
				masterCEMap.put("UPS", ups);
				
				// ǥ�ñ� ������ ������ ��ȸ
				List<HashMap<String, Object>> suvViewDataList = dao.selectSuvViewDataList(pramMap);
				if(suvViewDataList.size()>0) {
					for(int z=0; z < suvViewDataList.size(); z++) {
						suvViewDataList.get(z).put("ELR_H_ELVDONG", dong);					
					}
					viewMapList.addAll(suvViewDataList);
				}
				
				// jamb ������ ������ ��ȸ
				List<HashMap<String, Object>> suvJambDataList = dao.selectSuvJamDataList(pramMap);
                if(suvJambDataList.size()>0) {
					for(int z=0; z < suvJambDataList.size(); z++) {
						suvJambDataList.get(z).put("ELR_H_ELVDONG", dong);					
					}
                	jambMapList.addAll(suvJambDataList);
                }
                
                // ���� ������ ����
                String fNmId = "F_"+i;
                String dataKey = "";
                String hogiData = "";
                String hogiDataTmp = "";
                for(int ii=0; ii < floorIds.length; ii++) {
                	String fdata = (String)suvCEDataMap.get(floorIds[ii]);
                	if(fdata !=null) {
                		if(!"".equals(fdata)) {
                    		if(ii== 0) {
                    			// ȣ�� ������
                    			hogiDataTmp = fdata;
                    		} else {
                        		// ���� ������ ����
                        		dataKey = dataKey + floorNm[ii]+fdata;
                    		}
                		}
                	}
                }

                // �����Ͽ� ��Ƽ ȣ�Ⱚ ����
                if(cmpMap.containsKey(dataKey)) {
                	// ȣ�ⵥ���͸� �����ؼ� �Է��Ѵ�.
                	hogiData = cmpMap.get(dataKey);
                	hogiData = hogiData + "," + hogiDataTmp;
                	cmpMap.put(dataKey, hogiData);

                	String  tmpKey  = inHogiMap.get(dataKey);
                	HashMap<String, Object> dataMap = (HashMap<String, Object>) floorTmpMap.get(1);
                	dataMap.put(tmpKey, hogiData);
                } else {
                	// �ű� ������ �Է�
                	cmpMap.put(dataKey, hogiDataTmp);
                    for(int jj=0; jj < floorIds.length; jj++) {
                		// ���� ȣ������ ���� �ü��� ������
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
                            		// ���� ���� floorNm
                            		dataMap.put("NM", floorNm[jj]);
                            		floorTmpMap.put(floorNum[jj], dataMap);
                            	}
                    		}
                    	}
                    }
                }
			}
			
			// ��Ʈ ó���� ����
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
			// ���� ������ �Է�
			inputData.put("floorMap", floorMap);
			inputData.put("floorList", floorList);

			// ����ó���ؼ� ������ ���� �����Ѵ�.
			//  floorList ��ȸ�� ���� �����Ͱ� 16�� �Ѵ� ��� ���� ó���Ѵ�.
		    // , ����
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

			// ���������� �Է�
			inputData.put("masterHMap", masterHMap);
			inputData.put("masterMMap", masterMMap);
			inputData.put("masterCEMap", masterCEMap);
			inputData.put("masterLdHMap", masterLdHMap);
			inputData.put("masterLcHMap", masterLcHMap);
			inputData.put("masterLcMMap", masterLcMMap);
			inputData.put("masterRdHMap", masterRdHMap);
			inputData.put("masterRcHMap", masterRcHMap);
			inputData.put("masterRcMMap", masterRcMMap);
			
			// �°��� �κ�
			inputData.put("hMapList", hMapList);
			// ���� �κ�
			inputData.put("mMapList", mMapList);
			// CWT.�󼼹�����κ�
			inputData.put("ceMapList", ceMapList);
			// ��������
			inputData.put("floorMap", floorMap);
			// ǥ����
			inputData.put("viewMapList", viewMapList);
			// Jamb ����Ʈ
			inputData.put("jambMapList", jambMapList);
			
			// �°��� �κ� (��Ⱦ�� �и�)
			inputData.put("ldhMapList", ldhMapList);
			// �°��� �κ� (��Ⱦ�� ����)
			inputData.put("lchMapList", lchMapList);
			// ���� �κ� (��Ⱦ�� �и�,����)
			inputData.put("lmMapList", lmMapList);
			// �°��� �κ� (��Ⱦ�� �и�))
			inputData.put("rdhMapList", rdhMapList);
			// �°��� �κ� (��Ⱦ�� ����)
			inputData.put("rchMapList", rchMapList);
			// ���� �κ� (��Ⱦ�� �и�,����))
			inputData.put("rmMapList", rmMapList);
			
			List<String> rmSheet = new ArrayList<String>();
			if(hMapList.size()==0) {
				rmSheet.add("�°��� �κ�");
			}
			if(mMapList.size()==0) {
				rmSheet.add("���� �κ�");
			}
			if(ldhMapList.size()==0) {
				rmSheet.add("�°��� �κ� (��Ⱦ�� �и�)");
			}
			if(lchMapList.size()==0) {
				rmSheet.add("�°��� �κ� (��Ⱦ�� ����)");
			}
			if(lmMapList.size()==0) {
				rmSheet.add("���� �κ� (��Ⱦ�� �и�,����)");
			}
			if(rdhMapList.size()==0) {
				rmSheet.add("�°��� �κ� (��Ⱦ�� �и�))");
			}
			if(rchMapList.size()==0) {
				rmSheet.add("�°��� �κ� (��Ⱦ�� ����)");
			}
			if(rmMapList.size()==0) {
				rmSheet.add("���� �κ� (��Ⱦ�� �и�,����))");
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
	 * 50���� �ø�
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
	 * 5���� ���� �Լ�
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
	 * 10���� ���� �Լ�
	 */
	private String getTenTrunck(double x) {
		x = x / 10;
		x = (float) (Math.floor( x ));
		x = x * 10;
		BigDecimal bd = new BigDecimal((double)x);
		return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
	}
}
