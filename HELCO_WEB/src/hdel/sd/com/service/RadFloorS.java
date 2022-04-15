package hdel.sd.com.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

import hdel.lib.exception.BizException;
import hdel.sd.com.domain.FloorNmVO;
import hdel.sd.com.domain.FloorVO;
import hdel.sd.com.domain.SuvFloorVO;

import org.apache.log4j.Logger;

/**
 * 리모델링용 층별전개 서비스
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
public class RadFloorS {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
    /**
	 * 
	 * 영업사양과 실측사양으로 층고 전개를 수행한다.
	 * @param Map<String, String> prhMap, List<Map<String, String>>
	 * @param model
	 * @return
	 */
	public List<String> getFloorSpreadOut(String sUVSN, Map<String, String> prhMap, Map<String, FloorNmVO> floorPrhMap, List<SuvFloorVO> suvFloorList
			                             , List<Map> suvJambList, Map<Integer, String> floorSuvMap) {
        // floor 키값 맵을 만든다. - 문자셑이 다른 경우 floor값으로 매핑을 자동 처리한다.
		Map<Integer, String> floorKeyMap = new HashMap<Integer, String>();

        // 0. 전체 층수 정보를 통해서
		List<String> errMsgList = new ArrayList<String>();
		// A008 \층수(FLOOR Q'TY)
		String elAfq =   prhMap.get("EL_AFQ");
		int   iElAfq = new Integer(elAfq).intValue();
		
		// A009 \정지층수(STOP Q'TY)
		String elAstq =   prhMap.get("EL_ASTQ");
		int  iElAstq = new Integer(elAstq).intValue();
		
		// A013 \부정지층수(NON STOP Q'TY)
		String elAnstq =   prhMap.get("EL_ANSTQ");
        int  iElAnstq = 0;
		// A013 \부정지층수(NON STOP Q'TY)
        if(elAnstq == null) {
        	iElAnstq = 0;
        } else {
        	if("".equals(elAnstq)) {
        		iElAnstq = 0;
        	} else {
        		iElAnstq = new Integer(elAnstq).intValue();
        	}
        }
        // A014 부정지층 표기
		String elAnst =   prhMap.get("EL_ANST");
		if(elAnst == null) elAnst = "";
		
		// A018 REAR 열림층 표기
		String elaff =   prhMap.get("EL_AFF");
		// 부정지층 리스트
		List<FloorVO> elAnstNmList = null;
		if("".equals(elAnst)) {
			List<String> errMsgTmpListB = getEnterSeqnFloorList(sUVSN, elaff, floorPrhMap, floorKeyMap);
			for(String errMsg: errMsgTmpListB) {
				errMsgList.add(errMsg+"\n");
			}
		} else {
			// 부정치층 표기값을 시퀀스값을 생성하여 순차적인 데이터를 생성한다.
			// 부정지층 표기 처리를 위한 대안을 만든다.
			elAnstNmList = getFloorNm(elAnst, floorPrhMap, floorKeyMap);
			Map<Integer, String> elAnstNmMap = new HashMap<Integer, String>();
			for(int i=0; i < elAnstNmList.size(); i++) {
				FloorVO floorVO =  elAnstNmList.get(i);
				elAnstNmMap.put(floorVO.getiFloor(), floorVO.getFloorNm());
			}
			
			List<FloorVO> elaffNmList = getFloorNmSeqn(elaff, floorPrhMap);
			String addElaff = elaff;
			for(int i=0; i < elaffNmList.size(); i++) {
				FloorVO floorVO =  elaffNmList.get(i);
				if(elAnstNmMap.containsKey(floorVO.getiFloor()+1)) {
					int adIndex = addElaff.indexOf(floorVO.getFloorNm());
					if(adIndex > -1 && adIndex < addElaff.length()) {
						addElaff = addElaff.substring(0, adIndex+1) + "," + elAnstNmMap.get(floorVO.getiFloor()+1) + addElaff.substring(addElaff.length()) + addElaff.substring(adIndex+1);
					} else if(adIndex > -1 && adIndex == addElaff.length()) {
						addElaff = addElaff.substring(0, adIndex+1) + "," + elAnstNmMap.get(floorVO.getiFloor()+1) + addElaff.substring(adIndex+1);
					}
				}
			}
			
            // 마지막 문자열값이 , 존재 시 제거			
			if (addElaff.endsWith(",")) {
				addElaff = addElaff.substring(0, addElaff.length() - 1);
			}

			// 부정지층도 적용해서 층별전개를 수행한다.
			List<String> errMsgTmpListB = getEnterSeqnFloorList(sUVSN, addElaff, floorPrhMap, floorKeyMap);
			for(String errMsg: errMsgTmpListB) {
				errMsgList.add(errMsg+"\n");
			}
		} 

		// 영업사양의 모델은 영업사양에 정의된 문자 값으로 정의한다.
		
		// 1. JAMB 적용층 전개 1,23
		// C002 \JAMB(1)
		String elCjm1 =   prhMap.get("EL_CJM1");
		// C007 JAMB(1) 적용층
		String elCjm1F = prhMap.get("EL_CJM1F");
		// C001 \JAMB(1) 수량
		String elCjm1Q = prhMap.get("EL_CJM1Q");
		// C003 \HPI(1)
	    String elChpi1 = prhMap.get("EL_CHPI1");
		// jamp1 수량
//		int iElCjm1Q = 0;
//		if(elCjm1Q != null && elChpi1 != null) {
//			if(!"".equals(elCjm1Q) && !"".equals(elChpi1)) {
//				iElCjm1Q = new Integer(elCjm1Q).intValue();	
//			}
//		}
		List<String> errMsgTmpList1 = getJambHpiFloorList(sUVSN, "1", elCjm1, elCjm1F, elCjm1Q, elChpi1, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList1) {
			errMsgList.add(errMsg+"\n");
		}
	
		// C011 \JAMB(2)
		String elCjm2 =   prhMap.get("EL_CJM2");
		// C016 JAMB(2) 적용층
		String elCjm2F = prhMap.get("EL_CJM2F");
		// C010 \JAMB(2) 수량
		String elCjm2Q = prhMap.get("EL_CJM2Q");
		// C012 \HPI(2)
	    String elChpi2 = prhMap.get("EL_CHPI2");
		// jamp2 수량
//		int iElCjm2Q = 0;
//		if(elCjm2Q != null && elChpi2 != null) {
//			if(!"".equals(elCjm2Q) && !"".equals(elChpi2)) {
//				iElCjm2Q = new Integer(elCjm2Q).intValue();	
//			}
//		}
		List<String> errMsgTmpList2 = getJambHpiFloorList(sUVSN, "2", elCjm2, elCjm2F, elCjm2Q, elChpi2, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList2) {
			errMsgList.add(errMsg+"\n");
		}
		// C020 \JAMB(3)
		String elCjm3 =   prhMap.get("EL_CJM3");
		// C025 JAMB(3) 적용층
		String elCjm3F = prhMap.get("EL_CJM3F");
		// C019 \JAMB(3) 수량
		String elCjm3Q = prhMap.get("EL_CJM3Q");
		// C021 \HPI(3)
	    String elChpi3 = prhMap.get("EL_CHPI3");
		// jamp3 수량
//		int iElCjm3Q = 0;
//		if(elCjm3Q != null && elChpi3 != null) {
//			if(!"".equals(elCjm3Q) && !"".equals(elChpi3)) {
//				iElCjm3Q = new Integer(elCjm3Q).intValue();	
//			}
//		}
		List<String> errMsgTmpList3 = getJambHpiFloorList(sUVSN, "3", elCjm3, elCjm3F, elCjm3Q, elChpi3, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList3) {
			errMsgList.add(errMsg+"\n");
		}	    
	    // hpb 층별사양전개 수행
	    String elChpbm1 = prhMap.get("EL_CHPBM1");
	    String elChpbm1f = prhMap.get("EL_CHPBM1F");
	    String elChpbm1q = prhMap.get("EL_CHPBM1Q");
		// hpb1 수량
//		int iElChpbm1q = 0;
//		if(elChpbm1q != null) {
//			if(!"".equals(elChpbm1q)) {
//				iElChpbm1q = new Integer(elChpbm1q).intValue();	
//			}
//		}
	    
		List<String> errMsgTmpList4 = getHpbFloorList("1", elChpbm1, elChpbm1f , elChpbm1q, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList4) {
			errMsgList.add(errMsg+"\n");
		}
	    
	    String elChpbm2 = prhMap.get("EL_CHPBM2");
	    String elChpbm2f = prhMap.get("EL_CHPBM2F");
	    String elChpbm2q = prhMap.get("EL_CHPBM2Q");
		// hpb2 수량
//		int iElChpbm2q = 0;
//		if(elChpbm2q != null) {
//			if(!"".equals(elChpbm2q)) {
//				iElChpbm2q = new Integer(elChpbm2q).intValue();	
//			}
//		}
		List<String> errMsgTmpList5 = getHpbFloorList("2", elChpbm2, elChpbm2f , elChpbm2q, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList5) {
			errMsgList.add(errMsg+"\n");
		}

	    String elChpbt = prhMap.get("EL_CHPBT");
	    String elChpbtf = prhMap.get("EL_CHPBTF");
	    String elChpbtq = prhMap.get("EL_CHPBTQ");
		// hpb3 수량
//		int iElChpbtq = 0;
//		if(elChpbtq != null) {
//			if(!"".equals(elChpbtq)) {
//				iElChpbtq = new Integer(elChpbtq).intValue();	
//			}
//		}
		List<String> errMsgTmpList6 = getHpbFloorList("3", elChpbt, elChpbtf , elChpbtq, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList6) {
			errMsgList.add(errMsg+"\n");
		}

	    String elChpbb = prhMap.get("EL_CHPBB");
	    String elChpbbf = prhMap.get("EL_CHPBBF");
	    String elChpbbq = prhMap.get("EL_CHPBBQ");
		// hpb4 수량
//		int iElChpbbq = 0;
//		if(elChpbbq != null) {
//			if(!"".equals(elChpbbq)) {
//				iElChpbbq = new Integer(elChpbbq).intValue();	
//			}
//		}
		List<String> errMsgTmpList7 = getHpbFloorList("4", elChpbb, elChpbbf , elChpbbq, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList7) {
			errMsgList.add(errMsg+"\n");
		}

	    // hip 층전개
	    String elChipm1 = prhMap.get("EL_CHIPM1");
	    String elChipm1f = prhMap.get("EL_CHIPM1F");
	    String elChipm1q = prhMap.get("EL_CHIPM1Q");
		// hip1 수량
//		int iElChipm1q = 0;
//		if(elChipm1q != null) {
//			if(!"".equals(elChipm1q)) {
//				iElChipm1q = new Integer(elChipm1q).intValue();	
//			}
//		}
		List<String> errMsgTmpList8 = getHipFloorList("1", elChipm1, elChipm1f , elChipm1q, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList8) {
			errMsgList.add(errMsg+"\n");
		}
	    
	    String elChipm2 = prhMap.get("EL_CHIPM2");
	    String elChipm2f = prhMap.get("EL_CHIPM2F");
	    String elChipm2q = prhMap.get("EL_CHIPM2Q");
		// hip2 수량
//		int iElChipm2q = 0;
//		if(elChipm2q != null) {
//			if(!"".equals(elChipm2q)) {
//				iElChipm2q = new Integer(elChipm2q).intValue();	
//			}
//		}
		List<String> errMsgTmpList9 = getHipFloorList("2", elChipm2, elChipm2f , elChipm2q, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList9) {
			errMsgList.add(errMsg+"\n");
		}
	    
	    String elChipt = prhMap.get("EL_CHIPT");
	    String elChiptf = prhMap.get("EL_CHIPTF");
	    String elChiptq = prhMap.get("EL_CHIPTQ");
		// hip3 수량
//		int iElChiptq = 0;
//		if(elChiptq != null) {
//			if(!"".equals(elChiptq)) {
//				iElChiptq = new Integer(elChiptq).intValue();	
//			}
//		}
		List<String> errMsgTmpList10 = getHipFloorList("3", elChipt, elChiptf , elChiptq, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList10) {
			errMsgList.add(errMsg+"\n");
		}
	    
	    String elChipb = prhMap.get("EL_CHIPB");
	    String elChipbf = prhMap.get("EL_CHIPBF");
	    String elChipbq = prhMap.get("EL_CHIPBQ");
		// hip4 수량
//		int iElChipbq = 0;
//		if(elChipbq != null) {
//			if(!"".equals(elChipbq)) {
//				iElChipbq = new Integer(elChipbq).intValue();	
//			}
//		}
		List<String> errMsgTmpList11 = getHipFloorList("4", elChipb, elChipbf , elChipbq, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList11) {
			errMsgList.add(errMsg+"\n");
		}
	 
	    // 홀랜턴 층고 전개
	    String elChl = prhMap.get("EL_CHL");
	    String elChlf = prhMap.get("EL_CHLF");
	    String elChlq = prhMap.get("EL_CHLQ");
		// 홀랜턴1 수량
//		int iElChlq = 0;
//		if(elChlq != null) {
//			if(!"".equals(elChlq)) {
//				iElChlq = new Integer(elChlq).intValue();	
//			}
//		}
		List<String> errMsgTmpList12 = getHltFloorList("1", elChl, elChlf , elChlq, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList12) {
			errMsgList.add(errMsg+"\n");
		}
	    
	    String elChl2 = prhMap.get("EL_CHL2");
	    String elChl2f = prhMap.get("EL_CHL2F");
	    String elChl2q = prhMap.get("EL_CHL2Q");
		// 홀랜턴2 수량
//		int iElChl2q = 0;
//		if(elChl2q != null) {
//			if(!"".equals(elChl2q)) {
//				iElChl2q = new Integer(elChl2q).intValue();	
//			}
//		}
		List<String> errMsgTmpList13 = getHltFloorList("2", elChl2, elChl2f , elChl2q, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList13) {
			errMsgList.add(errMsg+"\n");
		}
	    
	    // 소방스위치 전개
	    String elCfrsw = prhMap.get("EL_CFRSW");
	    String elEmf = prhMap.get("EL_EMF");
		// 소방스위치 수량
//		int iElCfrsw = 0;
//		if(elCfrsw != null) {
//			if(!"".equals(elCfrsw)) {
//				iElCfrsw = 1;	
//			}
//		}
		List<String> errMsgTmpList14 = getFswFloorList(elCfrsw, elEmf, floorPrhMap, floorKeyMap);
		for(String errMsg: errMsgTmpList14) {
			errMsgList.add(errMsg+"\n");
		}

		// 전체층별갑에 대한 메시지 체크
        // A008 \층수(FLOOR Q'TY) = A009 \정지층수(STOP Q'TY) + A013 \부정지층수(NON STOP Q'TY)
		if(iElAfq != (iElAstq + iElAnstq)) {
	    	String errMsg = "A008 \\층수(FLOOR Q'TY) = A009 \\정지층수(STOP Q'TY) + A013 \\부정지층수(NON STOP Q'TY) 합계가 일치하지 않습니다. 적용층 등록값을 확인해 주십시요";
	    	errMsgList.add(errMsg+"\n");
		}

	    // 전체 층별전개 개수 체크 메시지
	    if( floorPrhMap.size() < iElAfq ) {
	    	String errMsg = "A014 부정지층 표기와 A018 REAR 열림층 표기 적용층으로 전개한 층별 값에 문제가 있습니다. 적용층 등록값을 확인해 주십시요.";
	    	errMsgList.add(errMsg+"\n");
	    }
	    
	    // 영업사양명 최초 생성된 값
	    Map<String, String> floorOrgMap = new HashMap<String, String>();
		for( String key : floorPrhMap.keySet() ) {
			FloorNmVO floorNmVO = floorPrhMap.get(key);
			floorOrgMap.put(floorNmVO.getFLOORNM(), floorNmVO.getFLOOR());
		}	    

		// 전층영업사양과 층고 실측값을 매핑한다. 실측값 기준으로 영업사양의 층값을 맞춰준다.
		getFloorSuvList(sUVSN, floorPrhMap, floorKeyMap, floorSuvMap);

        // 표시기별 실측사양 전개
	    Map<String, FloorNmVO> cpFloorPrhMap = new HashMap<String, FloorNmVO>();
	    floorKeyMap.clear();
        for( String key : floorPrhMap.keySet() ) {
        	FloorNmVO floorNmVO = floorPrhMap.get(key);
        	FloorNmVO chFloorNmVO = new FloorNmVO();
        	chFloorNmVO.setMANDT(floorNmVO.getMANDT());
        	chFloorNmVO.setSUVSN(floorNmVO.getSUVSN());
        	chFloorNmVO.setFLOOR(floorNmVO.getFLOOR());
        	chFloorNmVO.setFLOORDB(floorNmVO.getFLOORDB());
        	chFloorNmVO.setFLOORNUM(floorNmVO.getFLOORNUM());
        	chFloorNmVO.setFLOORNM(floorNmVO.getFLOORNM());
        	chFloorNmVO.setJAMBINFO(floorNmVO.getJAMBINFO());
        	chFloorNmVO.setJAMBGRP(floorNmVO.getJAMBGRP());
        	chFloorNmVO.setJAMBMODEL(floorNmVO.getJAMBMODEL());
        	chFloorNmVO.setHPIMODEL(floorNmVO.getHPIMODEL());
        	chFloorNmVO.setHPBMODEL(floorNmVO.getHPBMODEL());
        	chFloorNmVO.setHIPMODEL(floorNmVO.getHIPMODEL());
        	chFloorNmVO.setHLTNMODEL(floorNmVO.getHLTNMODEL());
        	chFloorNmVO.setFSWMODEL(floorNmVO.getFSWMODEL());
        	chFloorNmVO.setHPIPRD(floorNmVO.getHPIPRD());
        	chFloorNmVO.setHPBPRD(floorNmVO.getHPBPRD());
        	chFloorNmVO.setHIPPRD(floorNmVO.getHIPPRD());
        	chFloorNmVO.setHLTNPRD(floorNmVO.getHLTNPRD());
        	chFloorNmVO.setPRKSWPRD(floorNmVO.getPRKSWPRD());
        	chFloorNmVO.setFSWPRD(floorNmVO.getFSWPRD());
        	chFloorNmVO.setHPIAPPLYFLOOR(floorNmVO.getHPIAPPLYFLOOR());
        	chFloorNmVO.setHPBAPPLYFLOOR(floorNmVO.getHPBAPPLYFLOOR());
        	chFloorNmVO.setHIPAPPLYFLOOR(floorNmVO.getHIPAPPLYFLOOR());
        	chFloorNmVO.setHLTNAPPLYFLOOR(floorNmVO.getHLTNAPPLYFLOOR());
        	chFloorNmVO.setPRKSWAPPLYFLOOR(floorNmVO.getPRKSWAPPLYFLOOR());
        	chFloorNmVO.setFSWAPPLYFLOOR(floorNmVO.getFSWAPPLYFLOOR());
        	chFloorNmVO.setFLOORPRD(floorNmVO.getFLOORPRD());
        	chFloorNmVO.setJAMBTP(floorNmVO.getJAMBTP());
        	chFloorNmVO.setFLOORYSNO(floorNmVO.getFLOORYSNO());
        	chFloorNmVO.setISFLOOR(floorNmVO.isISFLOOR());
        	chFloorNmVO.setSUVPRH(floorNmVO.getSUVPRH());
        	chFloorNmVO.setSUVPRD(floorNmVO.getSUVPRD());
        	chFloorNmVO.setINPUTTYPE(floorNmVO.getINPUTTYPE());
        	
        	cpFloorPrhMap.put(floorNmVO.getFLOORNM(), chFloorNmVO);
        	floorKeyMap.put(floorNmVO.getFLOORNUM(), floorNmVO.getFLOORNM());
        }

	    boolean isMap = false;
        // 층고 전개 되면서 문자 중복되는 데이터가 생성됨
        // 이런 경우에는 문자로 매핑은 불가능
        if(floorPrhMap.size() == cpFloorPrhMap.size()) {
            // cpFloorPrhMap.putAll(floorPrhMap);  // 전체 시퀀스 순서도 이미 맞춰진 상태임
    	    for(int i=0; i<suvFloorList.size(); i++) {
    	    	SuvFloorVO suvFloorVO = suvFloorList.get(i);
    	    	List<String> errMsgTmpList15 = getSuvPrhFloorNmList(sUVSN, suvFloorVO.getSUVPRD(), suvFloorVO.getAPPLYFLOOR(), cpFloorPrhMap, floorKeyMap);
//    			for(String errMsg: errMsgTmpList15) {
//    				errMsgList.add(errMsg+"\n");
//    			}
    	    }

            // cpFloorPrhMap 매핑결과를 확인한다.
            for( String key : cpFloorPrhMap.keySet() ) {
            	FloorNmVO floorNmVO = cpFloorPrhMap.get(key);
            	String FLOORYSNO = floorNmVO.getFLOORYSNO();
            	boolean ISFLOOR = floorNmVO.isISFLOOR();
            	if("Y".equals(FLOORYSNO) && ISFLOOR) {
            		isMap = true;
            	} else {
            		isMap = false;
            		break;
            	}
            }
        } else {
    		isMap = false;
        }

        for( String key : cpFloorPrhMap.keySet() ) {
        	FloorNmVO floorNmVO = cpFloorPrhMap.get(key);
        }
        if(isMap) {
        	floorPrhMap.clear();
        	floorPrhMap.putAll(cpFloorPrhMap);
        } else {
        	// 층으로 표시기 사양을 매핑한다.
        	cpFloorPrhMap.clear();
        	floorKeyMap.clear();
            for( String key : floorPrhMap.keySet() ) {
            	FloorNmVO floorNmVO = floorPrhMap.get(key);
            	FloorNmVO chFloorNmVO = new FloorNmVO();
            	chFloorNmVO.setMANDT(floorNmVO.getMANDT());
            	chFloorNmVO.setSUVSN(floorNmVO.getSUVSN());
            	chFloorNmVO.setFLOOR(floorNmVO.getFLOOR());
            	chFloorNmVO.setFLOORDB(floorNmVO.getFLOORDB());
            	chFloorNmVO.setFLOORNUM(floorNmVO.getFLOORNUM());
            	chFloorNmVO.setFLOORNM(floorNmVO.getFLOORNM());
            	chFloorNmVO.setJAMBINFO(floorNmVO.getJAMBINFO());
            	chFloorNmVO.setJAMBGRP(floorNmVO.getJAMBGRP());
            	chFloorNmVO.setJAMBMODEL(floorNmVO.getJAMBMODEL());
            	chFloorNmVO.setHPIMODEL(floorNmVO.getHPIMODEL());
            	chFloorNmVO.setHPBMODEL(floorNmVO.getHPBMODEL());
            	chFloorNmVO.setHIPMODEL(floorNmVO.getHIPMODEL());
            	chFloorNmVO.setHLTNMODEL(floorNmVO.getHLTNMODEL());
            	chFloorNmVO.setFSWMODEL(floorNmVO.getFSWMODEL());
            	chFloorNmVO.setHPIPRD(floorNmVO.getHPIPRD());
            	chFloorNmVO.setHPBPRD(floorNmVO.getHPBPRD());
            	chFloorNmVO.setHIPPRD(floorNmVO.getHIPPRD());
            	chFloorNmVO.setHLTNPRD(floorNmVO.getHLTNPRD());
            	chFloorNmVO.setPRKSWPRD(floorNmVO.getPRKSWPRD());
            	chFloorNmVO.setFSWPRD(floorNmVO.getFSWPRD());
            	chFloorNmVO.setHPIAPPLYFLOOR(floorNmVO.getHPIAPPLYFLOOR());
            	chFloorNmVO.setHPBAPPLYFLOOR(floorNmVO.getHPBAPPLYFLOOR());
            	chFloorNmVO.setHIPAPPLYFLOOR(floorNmVO.getHIPAPPLYFLOOR());
            	chFloorNmVO.setHLTNAPPLYFLOOR(floorNmVO.getHLTNAPPLYFLOOR());
            	chFloorNmVO.setPRKSWAPPLYFLOOR(floorNmVO.getPRKSWAPPLYFLOOR());
            	chFloorNmVO.setFSWAPPLYFLOOR(floorNmVO.getFSWAPPLYFLOOR());
            	chFloorNmVO.setFLOORPRD(floorNmVO.getFLOORPRD());
            	chFloorNmVO.setJAMBTP(floorNmVO.getJAMBTP());
            	chFloorNmVO.setFLOORYSNO(floorNmVO.getFLOORYSNO());
            	chFloorNmVO.setISFLOOR(floorNmVO.isISFLOOR());
            	chFloorNmVO.setSUVPRH(floorNmVO.getSUVPRH());
            	chFloorNmVO.setSUVPRD(floorNmVO.getSUVPRD());
            	chFloorNmVO.setINPUTTYPE(floorNmVO.getINPUTTYPE());

            	cpFloorPrhMap.put(floorNmVO.getFLOOR(), chFloorNmVO);
            	floorKeyMap.put(floorNmVO.getFLOORNUM(), chFloorNmVO.getFLOOR());
            }    	    

    	    for(int i=0; i<suvFloorList.size(); i++) {
    	    	SuvFloorVO suvFloorVO = suvFloorList.get(i);
    	    	List<String> errMsgTmpList15 = getSuvPrhFloorList(sUVSN, suvFloorVO.getSUVPRD(), suvFloorVO.getAPPLYFLOOR(), cpFloorPrhMap, floorKeyMap);
    	    }

    	    // cpFloorPrhMap 매핑결과를 확인한다.
            for( String key : cpFloorPrhMap.keySet() ) {
            	FloorNmVO floorNmVO = cpFloorPrhMap.get(key);
            	String FLOORYSNO = floorNmVO.getFLOORYSNO();
            	boolean ISFLOOR = floorNmVO.isISFLOOR();
            	if("Y".equals(FLOORYSNO) && ISFLOOR) {
            		isMap = true;
            	} else {
            		if("O".equals(floorNmVO.getINPUTTYPE())) {
                		isMap = false;
                		break;
            		} else {
                		isMap = true;
            		}
            	}
            }

            if(isMap) {
            	floorPrhMap.clear();
            	floorPrhMap.putAll(cpFloorPrhMap);
            } else {
            	// 이부분 처리 로직에 대한 검증이 필요하다.
            	// 만약 이 모든게 일차하지 않는 경우
            	cpFloorPrhMap.clear();
            	floorKeyMap.clear();

            	// 전체
        		List<String> rmList = new ArrayList<String>();
        		for( String key : floorPrhMap.keySet() ) {
        			FloorNmVO floorNmVO = floorPrhMap.get(key);
        			if(
        			   !"O".equals(floorNmVO.getINPUTTYPE()) 
        			   ) {
        				rmList.add(key);	
        			}
                }

        		// 삭제
        		for(int i=0; i < rmList.size(); i++) {
        			floorPrhMap.remove(rmList.get(i));
        		}

            	// cpFloorPrhMap 표시기 매핑 전체 현장
        	    for(int i=0; i<suvFloorList.size(); i++) {
        	    	SuvFloorVO suvFloorVO = suvFloorList.get(i);
        	    	List<String> errMsgTmpList15 = getSuvPrhFloorOrderMap(sUVSN, suvFloorVO.getSUVPRD(), suvFloorVO.getAPPLYFLOOR(), cpFloorPrhMap, floorKeyMap);
        	    }

            	// 층고명은 다르지만 표시기 정보는 동일하니 층 정렬순서로 매핑을 수행한다.
            	// 시퀀스 별 해쉬맵 데이터를 생성한다.
        	    // 표시기별 실측사양 전개
        	    Map<Integer, FloorNmVO> seqFloorPrhMap = new HashMap<Integer, FloorNmVO>();
        	    Integer seqKey[] = new Integer[floorPrhMap.size()];
        	    int seqKeyCnt = 0;
                for( String key : floorPrhMap.keySet() ) {
                	FloorNmVO floorNmVO = floorPrhMap.get(key);
                	seqFloorPrhMap.put(floorNmVO.getFLOORNUM(), floorNmVO);
                	seqKey[seqKeyCnt] = floorNmVO.getFLOORNUM();
                	seqKeyCnt = seqKeyCnt + 1;
                }
                Arrays.sort(seqKey);

        	    Map<Integer, FloorNmVO> seqCpFloorPrhMap = new HashMap<Integer, FloorNmVO>();
        	    Integer seqCpKey[] = new Integer[cpFloorPrhMap.size()];
        	    int seqCpKeyCnt = 0;
                for( String key : cpFloorPrhMap.keySet() ) {
                	FloorNmVO floorNmVO = cpFloorPrhMap.get(key);
                	seqCpFloorPrhMap.put(floorNmVO.getFLOORNUM(), floorNmVO);
                	seqCpKey[seqCpKeyCnt] = floorNmVO.getFLOORNUM();
                	seqCpKeyCnt = seqCpKeyCnt + 1;
                }
                Arrays.sort(seqCpKey);

                // 소트 기준으로 매핑을 시작한다.
                seqCpKeyCnt = 0;
                for( int i=0; i < seqKey.length; i++ ) {
                	FloorNmVO floorNmVO   = seqFloorPrhMap.get(seqKey[i]);
                	String FLOORYSNO = floorNmVO.getFLOORYSNO();
                	// 값이 존재하는 경우 소트 정렬해서 값을 정의해 준다.
                	if("Y".equals(FLOORYSNO) && seqCpFloorPrhMap.containsKey(seqCpKey[seqCpKeyCnt])) {
                    	FloorNmVO chFloorNmVO = seqCpFloorPrhMap.get(seqCpKey[seqCpKeyCnt]);
	        			floorNmVO.setHPBAPPLYFLOOR(chFloorNmVO.getHPBAPPLYFLOOR());
	        			floorNmVO.setHPBPRD(chFloorNmVO.getHPBPRD());
	        			floorNmVO.setHIPAPPLYFLOOR(chFloorNmVO.getHIPAPPLYFLOOR());
	        			floorNmVO.setHIPPRD(chFloorNmVO.getHIPPRD());
	        			floorNmVO.setHPIAPPLYFLOOR(chFloorNmVO.getHPIAPPLYFLOOR());
	        			floorNmVO.setHPIPRD(chFloorNmVO.getHPIPRD());
	        			floorNmVO.setHLTNAPPLYFLOOR(chFloorNmVO.getHLTNAPPLYFLOOR());
	        			floorNmVO.setHLTNPRD(chFloorNmVO.getHLTNPRD());
	        			floorNmVO.setPRKSWAPPLYFLOOR(chFloorNmVO.getPRKSWAPPLYFLOOR());
	        			floorNmVO.setPRKSWPRD(chFloorNmVO.getPRKSWPRD());
	        			floorNmVO.setFSWAPPLYFLOOR(chFloorNmVO.getFSWAPPLYFLOOR());
	        			floorNmVO.setFSWPRD(chFloorNmVO.getFSWPRD());
	        			seqCpKeyCnt = seqCpKeyCnt + 1;
                	}
                }

                // floorPrhMap 초기화
                floorPrhMap.clear();
                floorKeyMap.clear();
                for( Integer key : seqFloorPrhMap.keySet() ) {
                	FloorNmVO floorNmVO = seqFloorPrhMap.get(key);
                	floorPrhMap.put(floorNmVO.getFLOOR(), floorNmVO);
                	floorKeyMap.put(floorNmVO.getFLOORNUM(), floorNmVO.getFLOORNM());
                }
            }
        }

	    // 실측 JAMB 모델 실측사양 전개
	    if(suvJambList != null) {
            // 만약 사이즈가 1인 경우
	    	if(suvJambList.size() == 1) {
	    		Map mapVo = suvJambList.get(0);
	    		String ELR_E_PNLH = (String)mapVo.get("ELR_E_PNLH");
	    		for( String key : floorPrhMap.keySet() ){
	    			FloorNmVO floorNmVO = floorPrhMap.get(key);
                    if(!"".equals(floorNmVO.getHPBMODEL()) || !"".equals(floorNmVO.getHIPMODEL())) {
    	    			floorNmVO.setJAMBTP(ELR_E_PNLH);
                    }
	            }
	    	} else {
	    		// jamb 전개
		    	for(int i=0; i<suvJambList.size(); i++) {
			    	Map mapVo = suvJambList.get(i);
			    	String APPLYFLOOR = (String)mapVo.get("APPLYFLOOR");
			    	String ELR_E_PNLH = (String)mapVo.get("ELR_E_PNLH");
			    	getSuvJambFloorList(sUVSN, ELR_E_PNLH, APPLYFLOOR, floorPrhMap, floorKeyMap);
			    }
	    	}
	    }
	    
		List<String> rmList = new ArrayList<String>();
		for( String key : floorPrhMap.keySet() ) {
			FloorNmVO floorNmVO = floorPrhMap.get(key);
			if(
			   !"O".equals(floorNmVO.getINPUTTYPE()) 
			   ) {
				rmList.add(key);	
			}
			
			// 영업사양 기본값 기준으로 층 데이터를 보정한다.  floorOrgMap
			if(floorOrgMap.containsKey(floorNmVO.getFLOORNM())) {
				// 최초 영업사양 층으로 보정처리한다.
				floorNmVO.setFLOOR(floorOrgMap.get(floorNmVO.getFLOORNM()));
			}
        }

		// 삭제
		for(int i=0; i < rmList.size(); i++) {
			floorPrhMap.remove(rmList.get(i));
		}
	    
	    // 층별 전개된 결과값이 없는 경우 메시지 처리
	    if( floorPrhMap.size() == 0) {
	    	String errMsg = "C007 JAMB(1) 적용층, C016 JAMB(2) 적용층, C025 JAMB(3) 적용층 입력값을 확인해 주세요";
	    	errMsgList.add(errMsg+"\n");
	    }

	    // 층전개에 대한 전체 개수를 통해 전개 결과를 확인한다.
	    // 1 HIP총수량-HIP 모델값이 존재하는 경우(C001 \JAMB(1) 수량 + C010 \JAMB(2) 수량 + C019 \JAMB(3) 수량) >= 실측 HIP총수량
	    //if((iElCjm1Q + iElCjm2Q + iElCjm3Q) < hipCnt ) {
	    	// 에러메시지 처리
	    //	String errMsg = "실측된 HIP 총수량이 영업사양 HIP 총수량보다 많습니다. HIP 모델이 등록된 JAMB(1,2,3) 적용층, JAMB(1,2,3) 수량 입력값을 확인하세요\n"
        //			                + "영업사양 입력값에 문제가 없는 경우에는 HIP 실측값 확인 요청을 하십시요";
	    //	errMsgList.add(errMsg+"\n");
	    //}

	    // 2. HPB총개수(C074 \HPB: MIDDLE(1) : 수량 + C079 \HPB: MIDDLE(2) : 수량) + HIP총수량(C095 \HIP: MIDDLE(1) : 수량 + C100 \HIP: MIDDLE(2) : 수량) >= 실측HPB총개수 + 실측HIP총개수
        //if( (iElChpbm1q + iElChpbm2q + iElChpbtq + iElChpbbq +iElChipm1q + iElChipm2q + iElChiptq + iElChipbq ) < (hpbCnt + hipCnt ) ) {
	    	// 에러메시지 처리
        	//String errMsg = "실측된 HPB, HIP 총수량이 영업사양 HPB, HIP 총수량보다 많습니다. HPB, HIP 적용층과 HPB, HIP 수량 입력값을 확인하세요\n"
        	//		                + "영업사양 입력값에 문제가 없는 경우에는 HPB, HIP 실측값 확인 요청을 하십시요";
	    	//errMsgList.add(errMsg+"\n");
        //}

        // 3. 홀랜턴 개수 체크
        //if( (iElChlq + iElChl2q) < hTnCnt ) {
	    	// 에러메시지 처리
        	//String errMsg = "실측된 홀랜턴 총수량이 영업사양 홀랜턴 총수량보다 많습니다. 홀랜턴 적용층, 홀랜턴 수량 입력값을 확인하세요\n"
        	//		                + "영업사양 입력값에 문제가 없는 경우에는 홀랜턴 실측값 확인 요청을 하십시요";
	    	//errMsgList.add(errMsg+"\n");
        //}

        // 4. 실측치에는 소방스위치가 존재하나 영업사양에는 등록되어 있는 경우
        //if( iElCfrsw < fswCnt ) {
	    	// 에러메시지 처리
        	//String errMsg = "실측된 소방스위치가 있으나 영업사양 소방스위치 입력값이 없습니다.\n"
        	//		           + "영업사양 입력값에 문제가 없는 경우에는 소방스위치 실측값 확인 요청을 하십시요";
	    	//errMsgList.add(errMsg+"\n");
        //}

        // 실측 적용층은 존재하나 영업 JAMB이 정의되어 있지 않은 경우에도 메시지를 출력한다.
//        for (String key : floorPrhMap.keySet()) {
//        	FloorNmVO floorNmVO = floorPrhMap.get(key);
//        	if("".equals(floorNmVO.getJAMBGRP())) {
//        		String preMsg = "";
//        		String errMsg = "";
//        		if("HPB".equals(floorNmVO.getHPBPRD())) {
//        			preMsg = "HPB";
//        		} else if("HIP".equals(floorNmVO.getHIPPRD())) {
//        			preMsg = "HIP";
//        		} else if("HPI".equals(floorNmVO.getHPIPRD())) {
//        			preMsg = "HPI";
//        		} else if("HLT".equals(floorNmVO.getHLTNPRD())) {
//        			preMsg = "홀랜턴";
//        		} else if("FSW".equals(floorNmVO.getFSWPRD())) {
//        			preMsg = "소방스위치";
//        		}
//        	    errMsg = "실측사양("+preMsg+") 층명("+floorNmVO.getFLOORNM()+")에 해당하는 영업사양 층명이 존재하지 않습니다. 영업사양 또는 실측값을 확인하십시요";
//        	    errMsgList.add(errMsg);
//        	}
//		}
        return errMsgList;
	}

	/**
	 * 출입구 전층 적용층에 대한 층별 전개를 수행한다. 시퀀스 처리
	 */
	private List<String> getEnterSeqnFloorList(String sUVSN, String apployFloor, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
        // 먼저 부정지 층을 전개한다.
       	List<FloorVO> floorNmList = getFloorNmSeqn(apployFloor, floorPrhMap);
        for(int i=0; i < floorNmList.size(); i++) {
        	FloorVO floorVO = floorNmList.get(i);
        	String floorKey = floorVO.getFloorNm();
        	if(floorPrhMap.containsKey(floorKey)) {
        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
        		floorNmVO.setFLOORNUM(floorVO.getiFloor());
        		floorNmVO.setFLOORNM(floorVO.getFloorNm());
        		floorNmVO.setFLOOR(floorVO.getFloor());
        	} else {
        		FloorNmVO floorNmVO = new FloorNmVO();
        		floorNmVO.setSUVSN(sUVSN);
        		floorNmVO.setFLOORNUM(floorVO.getiFloor());
        		floorNmVO.setFLOORNM(floorVO.getFloorNm());
        		floorNmVO.setFLOOR(floorVO.getFloor());
        		floorNmVO.setINPUTTYPE("O"); // 영업기준 층고
        		floorPrhMap.put(floorKey, floorNmVO);
        		floorKeyMap.put(floorVO.getiFloor(), floorVO.getFloorNm());
        	}
        }

        return errMsgList;
	}
	
	/**
	 * JAMB GROUP ID, JAMB 적용층, JAMB 수량, HPI 전개
	 * JAMB 적용층 전개를 수행한다.
	 */
	private List<String> getJambHpiFloorList(String sUVSN, String jambGrp, String jamModel, String elCjmF, String elCjmQ
			     , String elChpi, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();

		// JAMB 층고 전개를 수행한다.
	    if( elCjmF != null) {
	    	if(!elCjmF.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(elCjmF, floorPrhMap, floorKeyMap);
		        // JAMB 1 층고 전개가 정상적으로 수행됨
		        for(int i=0; i < floorNmList.size(); i++) {
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		floorNmVO.setJAMBGRP(jambGrp);
		        		floorNmVO.setJAMBMODEL(jamModel);
		        		floorNmVO.setJAMBINFO(jambGrp+"-"+jamModel);
		        		// hpi 모델
		        		floorNmVO.setHPIMODEL(elChpi);
		        	} else {
		        		String errMsg = "JAMB("+jambGrp+") 적용층 입력값 중 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
		        		errMsgList.add(errMsg);
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}
	
	/**
	 * HPB 적용층 전개를 수행한다.
	 */
	private List<String> getHpbFloorList(String div, String model, String applyFloor , String applyQnty, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		// HPB 층고 전개
	    if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
			    
			    int iApplyQnty = 0;
		        if(applyQnty !=null) {
		        	if(!"".equals(applyQnty)) {
		        		iApplyQnty = new Integer(applyQnty).intValue();
		        	}
		        }

		        if(iApplyQnty != floorNmList.size()) {
		        	// 1: EL_CHPBM1, 2:EL_CHPBM2, 3:EL_CHPBT 4:EL_CHPBB
		        	if("1".equals(div)) {
		        		String errMsg = "C073 HPB: MIDDLE(1) : 적용층 층수("+floorNmList.size()
	                       +")와 C074 \\HPB: MIDDLE(1) : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	} else if("2".equals(div)) {
		        		String errMsg = "C078 HPB: MIDDLE(2) : 적용층 층수("+floorNmList.size()
	                       +")와 C079 \\HPB: MIDDLE(2) : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	} else if("3".equals(div)) {
		        		String errMsg = "C083 HPB: TOP : 적용층 층수("+floorNmList.size()
	                       +")와 C084 \\HPB: TOP : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	} else if("4".equals(div)) {
		        		String errMsg = "C088 HPB: BOTTOM : 적용층 층수("+floorNmList.size()
	                       +")와 C089 \\HPB: BOTTOM : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	}
		        }

		        for(int i=0; i < floorNmList.size(); i++) {
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		String inModel =  floorNmVO.getHPBMODEL();
		        		if(inModel == null) inModel = "";
		        		if(!"".equals(inModel)) {
			        		String errMsg = "HPB 적용층에 중복 입력된 적용층이 존재합니다. HPB 적용층 입력값을 확인하십시요";
			        		errMsgList.add(errMsg);
		        		}
		        		floorNmVO.setHPBMODEL(model);
		        	} else {
			        	if("1".equals(div)) {
			        		String errMsg = "C073 HPB: MIDDLE(1) : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	} else if("2".equals(div)) {
			        		String errMsg = "C078 HPB: MIDDLE(2) : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	} else if("3".equals(div)) {
			        		String errMsg = "C083 HPB: TOP : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	} else if("4".equals(div)) {
			        		String errMsg = "C088 HPB: BOTTOM : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	}
		        	}
		        }
	    	}
	    }
	    return errMsgList;
	}


	/**
	 * HIP 적용층 전개를 수행한다.
	 */
	private List<String> getHipFloorList(String div, String model, String applyFloor , String applyQnty, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		// HIP 층고 전개
	    if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
			    
			    int iApplyQnty = 0;
		        if(applyQnty !=null) {
		        	if(!"".equals(applyQnty)) {
		        		iApplyQnty = new Integer(applyQnty).intValue();
		        	}
		        }

		        if(iApplyQnty != floorNmList.size()) {
		        	if("1".equals(div)) {
		        		String errMsg = "C094 HIP: MIDDLE(1) : 적용층 층수("+floorNmList.size()
	                       +")와 C095 \\HIP: MIDDLE(1) : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	} else if("2".equals(div)) {
		        		String errMsg = "C099 HIP: MIDDLE(2) : 적용층 층수("+floorNmList.size()
	                       +")와 C100 \\HIP: MIDDLE(2) : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	} else if("3".equals(div)) {
		        		String errMsg = "C104 HIP: TOP : 적용층 층수("+floorNmList.size()
	                       +")와 C105 \\HIP: TOP : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	} else if("4".equals(div)) {
		        		String errMsg = "C109 HIP: BOTTOM : 적용층 층수("+floorNmList.size()
	                       +")와 C110 \\HIP: BOTTOM : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	}
		        }

		        for(int i=0; i < floorNmList.size(); i++) {
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		String inModel =  floorNmVO.getHIPMODEL();
		        		if(inModel == null) inModel = "";
		        		if(!"".equals(inModel)) {
			        		String errMsg = "HIP 적용층에 중복 입력된 적용층이 존재합니다. HIP 적용층 입력값을 확인하십시요";
			        		errMsgList.add(errMsg);
		        		}		        		
		        		floorNmVO.setHIPMODEL(model);
		        	} else {
		        		// jamb 적용층에 적용되지 않은 층 정보가 존재함
			        	if("1".equals(div)) {
			        		String errMsg = "C094 HIP: MIDDLE(1) : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	} else if("2".equals(div)) {
			        		String errMsg = "C099 HIP: MIDDLE(2) : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	} else if("3".equals(div)) {
			        		String errMsg = "C104 HIP: TOP : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	} else if("4".equals(div)) {
			        		String errMsg = "C109 HIP: BOTTOM : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	}
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}

	/**
	 * 홀랜턴 적용층 전개를 수행한다.
	 */
	private List<String> getHltFloorList(String div, String model, String applyFloor , String applyQnty, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		// HIP 층고 전개
	    if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
			    
			    int iApplyQnty = 0;
		        if(applyQnty !=null) {
		        	if(!"".equals(applyQnty)) {
		        		iApplyQnty = new Integer(applyQnty).intValue();
		        	}
		        }

		        if(iApplyQnty != floorNmList.size()) {
		        	if("1".equals(div)) {
		        		String errMsg = "C113 \\홀랜턴(1) : 적용층 층수("+floorNmList.size()
	                       +")와 C114 \\홀랜턴(1) : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	} else if("2".equals(div)) {
		        		String errMsg = "C117 \\홀랜턴(2) : 적용층 층수("+floorNmList.size()
	                       +")와 C118 \\홀랜턴(2) : 수량("+applyQnty+")이 일치하지 않습니다.";
		        		errMsgList.add(errMsg);
		        	}
		        }

		        for(int i=0; i < floorNmList.size(); i++) {
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		String inModel =  floorNmVO.getHLTNMODEL();
		        		if(inModel == null) inModel = "";
		        		if(!"".equals(inModel)) {
			        		String errMsg = "홀랜턴 적용층에 중복 입력된 적용층이 존재합니다. 홀랜턴 적용층 입력값을 확인하십시요";
			        		errMsgList.add(errMsg);
		        		}		        		
		        		floorNmVO.setHLTNMODEL(model);
		        	} else {
		        		// jamb 적용층에 적용되지 않은 층 정보가 존재함
			        	if("1".equals(div)) {
			        		String errMsg = "C113 \\홀랜턴(1) : 적용층값에 열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	} else if("2".equals(div)) {
			        		String errMsg = "C117 \\홀랜턴(2) : 적용층값에  열림층 표기에 입력되지 않은 문자값이 존재합니다.";
			        		errMsgList.add(errMsg);
			        	}
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}
	
	/**
	 * 소방스위치 적용층 층고 전개를 수행한다.
	 */
	private List<String> getFswFloorList(String model, String applyFloor , Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
        String applyQnty = "1";
		// 소방스위치 층고 전개
	    if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
			    
			    int iApplyQnty = 0;
		        if(applyQnty !=null) {
		        	if(!"".equals(applyQnty)) {
		        		iApplyQnty = new Integer(applyQnty).intValue();
		        	}
		        }

		        if(iApplyQnty != floorNmList.size()) {
		        	String errMsg = "C123 기준층(기본입력) 층수("+floorNmList.size()
                       +")와 C114 \\홀랜턴(1) : 수량(1)이 일치하지 않습니다.";
	        		errMsgList.add(errMsg);
		        }

		        for(int i=0; i < floorNmList.size(); i++) {
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		String inModel =  floorNmVO.getFSWMODEL();
		        		if(inModel == null) inModel = "";
		        		if(!"".equals(inModel)) {
			        		String errMsg = "소방스위치 적용층에 중복 입력된 적용층이 존재합니다. 소방스위치 적용층 입력값을 확인하십시요";
			        		errMsgList.add(errMsg);
		        		}		        		
		        		floorNmVO.setFSWMODEL(model);
		        	} else {
		        		// jamb 적용층에 적용되지 않은 층 정보가 존재함
		        		String errMsg = "C123 기준층(기본입력)값에  열림층 표기에 입력되지 않은 문자값이 존재합니다.";
		        		errMsgList.add(errMsg);
		        	}
		        }
	    	}
	    }
	    
	    return errMsgList;
	}

	/**
	 * 영업사양 문자 기준으로 표시기 층고사양 전개한다.
	 */
	private List<String> getSuvPrhFloorNmList(String sUVSN, String model, String applyFloor, Map<String, FloorNmVO> floorPrhMap
			                        ,Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
		        for(int i=0; i < floorNmList.size(); i++) {
		        	// 층별 전개된 전체 카운트
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		// 표시기별 실측적용층 전개 데이터
		        		// HPB	HPB(승장버튼)
		        		// HIP	HIP(수평위치표시기)
		        		// HPI	HPI(수평위치표시기)
		        		// HLT	홀랜턴
		        		// PKS	PARKING S/W
		        		// FSW	소방스위치
		        		if("HPB".equals(model) || "HPBCIRCLE".equals(model)) {
		        			floorNmVO.setHPBAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHPBPRD(model);
		        		} else if("HIP".equals(model)) {
		        			floorNmVO.setHIPAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHIPPRD(model);
		        		} else if("HPI".equals(model)) {
		        			floorNmVO.setHPIAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHPIPRD(model);
		        		} else if("HLT".equals(model)) {
		        			floorNmVO.setHLTNAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHLTNPRD(model);
		        		} else if("PKS".equals(model)) {
		        			floorNmVO.setPRKSWAPPLYFLOOR(applyFloor);
		        			floorNmVO.setPRKSWPRD(model);
		        		} else if("FSW".equals(model)) {
		        			floorNmVO.setFSWAPPLYFLOOR(applyFloor);
		        			floorNmVO.setFSWPRD(model);
		        		}
		        		// 표시기 매핑 여부
		        		floorNmVO.setISFLOOR(true);
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}

	/**
	 * 영업사양 층고 기준으로 표시기 층고사양 전개한다.
	 */
	private List<String> getSuvPrhFloorList(String sUVSN, String model, String applyFloor, Map<String, FloorNmVO> floorPrhMap
			                        ,Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
		        for(int i=0; i < floorNmList.size(); i++) {
		        	// 층별 전개된 전체 카운트
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloor();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		// 표시기별 실측적용층 전개 데이터
		        		// HPB	HPB(승장버튼)
		        		// HIP	HIP(수평위치표시기)
		        		// HPI	HPI(수평위치표시기)
		        		// HLT	홀랜턴
		        		// PKS	PARKING S/W
		        		// FSW	소방스위치
		        		if("HPB".equals(model) || "HPBCIRCLE".equals(model)) {
		        			floorNmVO.setHPBAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHPBPRD(model);
		        		} else if("HIP".equals(model)) {
		        			floorNmVO.setHIPAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHIPPRD(model);
		        		} else if("HPI".equals(model)) {
		        			floorNmVO.setHPIAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHPIPRD(model);
		        		} else if("HLT".equals(model)) {
		        			floorNmVO.setHLTNAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHLTNPRD(model);
		        		} else if("PKS".equals(model)) {
		        			floorNmVO.setPRKSWAPPLYFLOOR(applyFloor);
		        			floorNmVO.setPRKSWPRD(model);
		        		} else if("FSW".equals(model)) {
		        			floorNmVO.setFSWAPPLYFLOOR(applyFloor);
		        			floorNmVO.setFSWPRD(model);
		        		}
		        		// 표시기 매핑 여부
		        		floorNmVO.setISFLOOR(true);
		        	} else {
		        		// 층별 사양값이 존재하지 않지만 정보를 생성해서 처리한다.
		        		FloorNmVO floorNmVO = new FloorNmVO();
		        		floorNmVO.setSUVSN(sUVSN);
		        		floorNmVO.setFLOORNUM(floorVO.getiFloor());
		        		floorNmVO.setFLOORNM(floorVO.getFloorNm());
		        		floorNmVO.setFLOOR(floorVO.getFloor());
		        		if("HPB".equals(model) || "HPBCIRCLE".equals(model)) {
		        			floorNmVO.setHPBAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHPBPRD(model);
		        		} else if("HIP".equals(model)) {
		        			floorNmVO.setHIPAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHIPPRD(model);
		        		} else if("HPI".equals(model)) {
		        			floorNmVO.setHPIAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHPIPRD(model);
		        		} else if("HLT".equals(model)) {
		        			floorNmVO.setHLTNAPPLYFLOOR(applyFloor);
		        			floorNmVO.setHLTNPRD(model);
		        		} else if("PKS".equals(model)) {
		        			floorNmVO.setPRKSWAPPLYFLOOR(applyFloor);
		        			floorNmVO.setPRKSWPRD(model);
		        		} else if("FSW".equals(model)) {
		        			floorNmVO.setFSWAPPLYFLOOR(applyFloor);
		        			floorNmVO.setFSWPRD(model);
		        		}
		        		floorPrhMap.put(floorKey, floorNmVO);
		        		floorNmVO.setISFLOOR(false);
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}
	
	/**
	 *  영업사양 층문자, 층별 전개 실패 시 층고 개수 일치하면 층고 정렬순서로 매핑한다.
	 */
	private List<String> getSuvPrhFloorOrderMap(String sUVSN, String model, String applyFloor, Map<String, FloorNmVO> floorPrhMap
			                        ,Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
			    for(int i=0; i < floorNmList.size(); i++) {
		        	// 층별 전개된 전체 카운트
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloor();

		        	FloorNmVO floorNmVO = new FloorNmVO();
	        		floorNmVO.setSUVSN(sUVSN);
	        		floorNmVO.setFLOORNUM(floorVO.getiFloor());
	        		floorNmVO.setFLOORNM(floorVO.getFloorNm());
	        		floorNmVO.setFLOOR(floorVO.getFloor());
	        		if("HPB".equals(model) || "HPBCIRCLE".equals(model)) {
	        			floorNmVO.setHPBAPPLYFLOOR(applyFloor);
	        			floorNmVO.setHPBPRD(model);
	        		} else if("HIP".equals(model)) {
	        			floorNmVO.setHIPAPPLYFLOOR(applyFloor);
	        			floorNmVO.setHIPPRD(model);
	        		} else if("HPI".equals(model)) {
	        			floorNmVO.setHPIAPPLYFLOOR(applyFloor);
	        			floorNmVO.setHPIPRD(model);
	        		} else if("HLT".equals(model)) {
	        			floorNmVO.setHLTNAPPLYFLOOR(applyFloor);
	        			floorNmVO.setHLTNPRD(model);
	        		} else if("PKS".equals(model)) {
	        			floorNmVO.setPRKSWAPPLYFLOOR(applyFloor);
	        			floorNmVO.setPRKSWPRD(model);
	        		} else if("FSW".equals(model)) {
	        			floorNmVO.setFSWAPPLYFLOOR(applyFloor);
	        			floorNmVO.setFSWPRD(model);
	        		}
	        		floorPrhMap.put(floorKey, floorNmVO);
		        }
	    	}
	    }

	    return errMsgList;
	}
	
	/**
	 * 실측 JAMB 실측사양 층고를 전개한다.
	 */
	private void getSuvJambFloorList(String sUVSN, String model, String applyFloor, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
		        for(int i=0; i < floorNmList.size(); i++) {
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floor = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floor)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floor);
		        		floorNmVO.setJAMBTP(model);
		        	} else {
		        		Integer iFloor = floorVO.getiFloor();
		        		if(floorKeyMap.containsKey(iFloor)) {
		        			floor = floorKeyMap.get(iFloor);
				        	if(floorPrhMap.containsKey(floor)) {
				        		FloorNmVO floorNmVO = floorPrhMap.get(floor);
				        		floorNmVO.setJAMBTP(model);
				        	}		        			
		        		}
		        	}
		        }
	    	}
	    }
	}	

	/**
	 * 출입구 전층 적용층과 층고층을 비교하에 층고 데이터를 매핑한다.
	 */
	private void getFloorSuvList(String sUVSN, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap, Map<Integer, String> floorSuvMap) {

		int floorStIndex = 9999;
		int floorEnIndex = -9999;
		int suvStIndex = 9999;
		int floorSuvSize = 0;
		Map<Integer, FloorNmVO> chFloorPrhMap = new LinkedHashMap<Integer, FloorNmVO>();
		Integer seqChKey[] = new Integer[floorKeyMap.size()];
		int seqChKeyCnt = 0;
        for( String key : floorPrhMap.keySet() ) {
        	FloorNmVO floorNmVO = floorPrhMap.get(key);
        	chFloorPrhMap.put(floorNmVO.getFLOORNUM(), floorNmVO);
        	seqChKey[seqChKeyCnt] = floorNmVO.getFLOORNUM();
        	seqChKeyCnt = seqChKeyCnt + 1;
        }
        Arrays.sort(seqChKey);
        
        for( Integer key : floorSuvMap.keySet() ) {
			String sFloorNm[] = floorSuvMap.get(key).split(",");
        	if(!"N".equals(sFloorNm[1])) {
            	floorSuvSize = floorSuvSize + 1;
        	}			
        }

        for( Integer key : chFloorPrhMap.keySet() ) {
        	if(key < floorStIndex) {
        		floorStIndex = key;
        	}
        	if(key > floorEnIndex) {
        		floorEnIndex = key;
        	}
        }

		Integer seqSuvKey[] = new Integer[floorSuvMap.size()];
		int seqSuvKeyCnt = 0;
		for( Integer key : floorSuvMap.keySet() ) {
			seqSuvKey[seqSuvKeyCnt] = key;
        	if(key < suvStIndex) {
        		suvStIndex = key;
        	}
        	seqSuvKeyCnt = seqSuvKeyCnt + 1;
		}
        Arrays.sort(seqSuvKey);

		if( floorPrhMap.size() == floorSuvSize && floorPrhMap.size() == floorSuvMap.size() ) {
			floorPrhMap.clear();
			floorKeyMap.clear();
	        // 값이 존재하지 않는 경우 floorIndex = 0;
	        int floorIndex = floorStIndex;
			for( Integer key : floorSuvMap.keySet() ) {
				String sFloorNm[] = floorSuvMap.get(key).split(",");
    			FloorNmVO floorNmVO = chFloorPrhMap.get(floorIndex);
        		floorNmVO.setFLOORNUM(key);
        		floorNmVO.setFLOOR(sFloorNm[0]);
        		floorNmVO.setFLOORYSNO("Y");
        		floorNmVO.setSUVPRH(sFloorNm[2]);
        		floorNmVO.setSUVPRD(sFloorNm[3].trim());
        		floorPrhMap.put(floorNmVO.getFLOORNM(), floorNmVO);
    			floorKeyMap.put(key, floorNmVO.getFLOORNM());
        		floorIndex = floorIndex + 1;	            		
			}
		} else if(floorSuvMap.size() >  floorPrhMap.size()) {
			floorPrhMap.clear();
			floorKeyMap.clear();
			int floorIndex =0;
			for(int i =0 ; i < seqSuvKey.length; i++) {
				String sFloorNm[] = floorSuvMap.get(seqSuvKey[i]).split(",");
            	if("N".equals(sFloorNm[1])) {
            		FloorNmVO floorNmVO = new FloorNmVO();
            		floorNmVO.setSUVSN(sUVSN);
            		floorNmVO.setFLOORNUM(seqSuvKey[i]);
            		floorNmVO.setFLOORNM(sFloorNm[0]);
            		floorNmVO.setFLOOR(sFloorNm[0]);
            		floorNmVO.setFLOORYSNO("N");
            		floorNmVO.setSUVPRH(sFloorNm[2]);
            		floorNmVO.setSUVPRD(sFloorNm[3].trim());
            		
            		
            		floorPrhMap.put(sFloorNm[0], floorNmVO);
        			floorKeyMap.put(seqSuvKey[i], sFloorNm[0]);
	        	} else {
					if(floorIndex < seqChKey.length) {
	        			FloorNmVO floorNmVO = chFloorPrhMap.get(seqChKey[floorIndex]);
	            		floorNmVO.setFLOORNUM(seqSuvKey[i]);
	            		floorNmVO.setFLOOR(sFloorNm[0]);
	            		floorNmVO.setSUVPRH(sFloorNm[2]);
	            		floorNmVO.setSUVPRD(sFloorNm[3].trim());
	            		floorNmVO.setFLOORYSNO("Y");
	            		floorPrhMap.put(sFloorNm[0], floorNmVO);
	        			floorKeyMap.put(seqSuvKey[i], sFloorNm[0]);
	        			floorIndex = floorIndex + 1;
					}
	        	}
			}
		} else if(floorSuvMap.size() < floorPrhMap.size()) {
			floorPrhMap.clear();
			floorKeyMap.clear();
			int floorIndex =0;
			for(int i =0 ; i < seqChKey.length; i++) {
				FloorNmVO floorNmVO = chFloorPrhMap.get(seqChKey[i]);
				if(i < seqSuvKey.length) {
					String sFloorNm[] = floorSuvMap.get(seqSuvKey[i]).split(",");
            		floorNmVO.setFLOORNUM(seqChKey[i]);
            		floorNmVO.setFLOORNM(sFloorNm[0]);
            		floorNmVO.setFLOOR(sFloorNm[0]);
            		floorNmVO.setFLOORYSNO(sFloorNm[1]);
            		floorNmVO.setSUVPRH(sFloorNm[2]);
            		floorNmVO.setSUVPRD(sFloorNm[3].trim());
            		floorPrhMap.put(sFloorNm[0], floorNmVO);
        			floorKeyMap.put(seqChKey[floorIndex], sFloorNm[0]);
				}	
			}
		}
		//else {
		//	throw new BizException("영업사양 적용층, 층고 실측사양, 표시기 실측사양 층별전개를 매핑할 수 없습니다. 입력된 적용층, 층고 실측사양, 표시기 실측사양값을 확인하십시요.");
		//}
	}

	/**
	 * 
	 * 적용층 입력값을 파싱해서 층명, 층정수값을 추출한다. 해당 적용층은 층별 시퀀스를 적용해서 처리한다.
	 */
	private List<FloorVO> getFloorNmSeqn(String applyFloor, Map<String, FloorNmVO> floorPrhMap) {
		List<FloorVO> floorNmList = new ArrayList<FloorVO>();
		Map<String, Integer> startMap = new HashMap<String, Integer>();
		
        // , 값이 존재하면 mult 층이 존재하는 것으로 판단한다.
		if(applyFloor == null) return floorNmList;
		
        // no emty값만 있는 경우
		applyFloor = applyFloor.trim();
		if(applyFloor.equals("")) return floorNmList;
		
		// 모든 적용층 입력값은 대문자로 변환해서 처리한다.
		applyFloor = applyFloor.trim().toUpperCase();
		applyFloor = applyFloor.replaceAll("[.]", ",");
		// 층 전부 제거
		applyFloor = applyFloor.replaceAll("층", "");
		
		// 정상적으로 값이 존재하는 경우 처리
		if(applyFloor.indexOf(",") > -1) {
            String mApplyFloor[] = applyFloor.split(",");
            if(mApplyFloor.length > 1) {
            	// 최초 문자가 문자값인 경우 체크
    	        int startNumFrom = -6;
            	int startNmIndex = -6;
            	boolean isStart = false;
            	char tmp;
            	for(int i=0; i < mApplyFloor.length; i++) {
                	for(int j=0; j < 1; j++) {
                		tmp = mApplyFloor[i].charAt(j);
                		// 첫문자가 0~9 이거나 B인 경우 -> 정의된 층으로 간주
                		if(('0' <= tmp && tmp <'9') || 'B' == tmp) {
                			// 문자가 아닌 인덱스
                			startNmIndex = i;
                			isStart = true;
                			break;
                		}
                	}

                	if(isStart) {
                		break;
                	}
            	}

           		startNumFrom = getSingleFloorNmMap(mApplyFloor[startNmIndex], floorPrhMap);
           		startNumFrom = startNumFrom - startNmIndex;
            	startMap.put("startNumFrom", startNumFrom);
                for(int i=0; i < mApplyFloor.length; i++) {
                	getMultiFloorNmSeqn(mApplyFloor[i], floorNmList, startMap, floorPrhMap);
                }
            }
		} else {
			// 단일값 floor
			getSingleFloorNmSeqn(applyFloor, floorNmList, startMap, floorPrhMap);
		}
		return floorNmList;
	}

	/**
	 * 
	 * 적용층 입력값을 파싱해서 층명, 층정수값을 추출한다.
	 */
	private List<FloorVO> getFloorNm(String applyFloor, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<FloorVO> floorNmList = new ArrayList<FloorVO>();
        // , 값이 존재하면 mult 층이 존재하는 것으로 판단한다.
		if(applyFloor == null) return floorNmList;
		
        // no emty값만 있는 경우
		applyFloor = applyFloor.trim();
		if(applyFloor.equals("")) return floorNmList;
		
		// 모든 적용층 입력값은 대문자로 변환해서 처리한다.
		applyFloor = applyFloor.trim().toUpperCase();
		applyFloor = applyFloor.replaceAll("[.]", ",");
		// 층 전부 제거
		applyFloor = applyFloor.replaceAll("층", "");
		
		// 정상적으로 값이 존재하는 경우 처리
		if(applyFloor.indexOf(",") > -1) {
            String mApplyFloor[] = applyFloor.split(",");
            if(mApplyFloor.length > 1) {
            	// 시작값
                for(int i=0; i < mApplyFloor.length; i++) {
                   	getMultiFloorNm(mApplyFloor[i], floorNmList, floorPrhMap, floorKeyMap);
                }
            }
		} else {
			// 단일값 floor
			getSingleFloorNm(applyFloor, floorNmList, floorPrhMap, floorKeyMap);
		}
		
		return floorNmList;
	}

	/**
	 * 
	 * 적용층 입력값을 파싱해서 단일층 층명, 정수형 층을 추출한다. 시퀀스 적용
	 */
	private void getSingleFloorNmSeqn(String applyFloor, List<FloorVO> floorNmList, Map<String, Integer> startMap, 
			Map<String, FloorNmVO> floorPrhMap) {
    	FloorVO floorVO = new FloorVO();
 		applyFloor = applyFloor.trim();
 		
 		// 만약 데이터 중에 space가 존재하면 space를 제거한다.
 		applyFloor = applyFloor.replaceAll(" ", "");
		// 층 전부 제거
		applyFloor = applyFloor.replaceAll("층", "");
 		
 		// 만약 범위 기준 데이터 존재 시
 		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
 			int startNumFrom = getSingleFloorNmMap(applyFloor, floorPrhMap);
        	startMap.put("startNumFrom", startNumFrom);
        	startMap.put("i", 0);
 			getRangeFloorNmSeqn(applyFloor, floorNmList, startMap, floorPrhMap);
 		} else if(applyFloor.equals("F")) {
 	 		floorVO.setFloorNm(applyFloor);
 			floorVO.setiFloor(3);
 			floorVO.setFloor(applyFloor);
 			floorNmList.add(floorVO);
 		} else if(applyFloor.equals("L")) {
 			// L: 로비
 	 		floorVO.setFloorNm(applyFloor);
 			floorVO.setiFloor(0);
 			floorVO.setFloor(applyFloor);
 			floorNmList.add(floorVO);
 		} else {
 			// 층, F 문구가 존재시 제거하고 처리
 			applyFloor = applyFloor.replaceAll("F", "");
 			floorVO.setFloorNm(applyFloor);
 			if(applyFloor.indexOf("B") > -1) {
 				// 지하층 표기
 				////System.out.println("앞자리:"+applyFloor.substring(0, 1));
 				////System.out.println("뒷자리:"+applyFloor.substring(1));
 				int floorNum = new Integer("-"+applyFloor.substring(1)).intValue();
 	 			floorVO.setiFloor(floorNum);
 	 			floorVO.setFloor(applyFloor);
 				floorNmList.add(floorVO);
 			} else {
 				////System.out.println("앞자리:"+applyFloor);
 				int floorNum = new Integer(applyFloor).intValue() - 1;
 	 			floorVO.setiFloor(floorNum);
 	 			floorVO.setFloor(applyFloor);
 				floorNmList.add(floorVO);
 			}
 		}
 		//System.out.println("floorVO:"+floorVO.toString());
     }
	
	/**
	 * 
	 * 적용층 입력값을 파싱해서 단일층 층명, 정수형 층을 추출한다.
	 */
	private void getSingleFloorNm(String applyFloor, List<FloorVO> floorNmList, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
    	FloorVO floorVO = new FloorVO();
 		applyFloor = applyFloor.trim();
 		
 		// 만약 데이터 중에 space가 존재하면 space를 제거한다.
 		applyFloor = applyFloor.replaceAll(" ", "");
		// 층 전부 제거
		applyFloor = applyFloor.replaceAll("층", "");
 		floorVO.setFloorNm(applyFloor);
 		
 		// 만약 범위 기준 데이터 존재 시
 		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
 			getRangeFloorNm(applyFloor, floorNmList, floorPrhMap, floorKeyMap);
 		} else if(applyFloor.equals("F")) {
 			floorVO.setiFloor(3);
 			floorVO.setFloor(applyFloor);
 			floorNmList.add(floorVO);
 		} else if(applyFloor.equals("L")) {
 			// L: 로비
 			floorVO.setiFloor(0);
 			floorVO.setFloor(applyFloor);
 			floorNmList.add(floorVO);
 		} else {
 			// 층, F 문구가 존재시 제거하고 처리
 			applyFloor = applyFloor.replaceAll("F", "");
 			if(floorPrhMap.containsKey(applyFloor)) {
 	 			floorVO.setiFloor(floorPrhMap.get(applyFloor).getFLOORNUM());
 	 			floorVO.setFloor(floorPrhMap.get(applyFloor).getFLOOR());
 				floorNmList.add(floorVO);
 			} else {
 	 			if(applyFloor.indexOf("B") > -1) {
 	 				// 지하층 표기
 	 				////System.out.println("앞자리:"+applyFloor.substring(0, 1));
 	 				////System.out.println("뒷자리:"+applyFloor.substring(1));
 	 				int floorNum = new Integer("-"+applyFloor.substring(1)).intValue();
 	 	 			floorVO.setiFloor(floorNum);
 	 	 			floorVO.setFloor(applyFloor);
 	 				floorNmList.add(floorVO);
 	 			} else {
 	 				////System.out.println("앞자리:"+applyFloor);
 	 				int floorNum = new Integer(applyFloor).intValue() - 1;
 	 	 			floorVO.setiFloor(floorNum);
 	 	 			floorVO.setFloor(applyFloor);
 	 				floorNmList.add(floorVO);
 	 			}
 			}
 			
 		}
 		//System.out.println("floorVO:"+floorVO.toString());
     }

	/**
	 * 
	 * 적용층 입력값을 파싱해서 단일층 층명, 정수형 층을 추출한다. 시퀀스 적용
	 */
	private void getMultiFloorNmSeqn(String applyFloor, List<FloorVO> floorNmList, Map<String, Integer> startMap, Map<String, FloorNmVO> floorPrhMap) {
    	FloorVO floorVO = new FloorVO();
 		applyFloor = applyFloor.trim();
 		// 만약 데이터 중에 space가 존재하면 space를 제거한다.
 		applyFloor = applyFloor.replaceAll(" ", "");
 		int startNumFrom = startMap.get("startNumFrom");

 		// 만약 범위 기준 데이터 존재 시
 		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
 			getRangeFloorNmSeqn(applyFloor, floorNmList, startMap, floorPrhMap);
 		} else {
 			int floor = startNumFrom;
			// 순차적인 값으 문의 층번호를 정의한다.
        	if(floor >= 0) {
        		floorVO.setiFloor(floor);
        		floorVO.setFloor(new Integer(floor+1).toString());
        		floorNmList.add(floorVO);
        	} else {
        		floorVO.setiFloor(floor);
        		String floorNm = new Integer(floor).toString();
        		floorVO.setFloor("B"+floorNm.substring(1));
        		floorNmList.add(floorVO);
        	}
 	 		floorVO.setFloorNm(applyFloor);
 	 		startMap.put("startNumFrom", (startNumFrom + 1));
 		}
     }
	
	/**
	 * 
	 * 적용층 입력값을 파싱해서 단일층 층명, 정수형 층을 추출한다.
	 */
	private void getMultiFloorNm(String applyFloor, List<FloorVO> floorNmList, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
    	FloorVO floorVO = new FloorVO();
 		applyFloor = applyFloor.trim();
 		// 만약 데이터 중에 space가 존재하면 space를 제거한다.
 		applyFloor = applyFloor.replaceAll(" ", "");
 		// 만약 범위 기준 데이터 존재 시
 		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
 			getRangeFloorNm(applyFloor, floorNmList, floorPrhMap, floorKeyMap);
 		} else {
 	 		floorVO.setFloorNm(applyFloor);
 			// 만약 문자값이 존재하면 처리
 			if(floorPrhMap.containsKey(applyFloor)) {
        		floorVO.setiFloor(floorPrhMap.get(applyFloor).getFLOORNUM());
        		floorVO.setFloor(floorPrhMap.get(applyFloor).getFLOOR());
        		floorNmList.add(floorVO);
 			} else {
 	        	int floor = getSingleFloorNmMap(applyFloor, floorPrhMap);
 				// 순차적인 값으 문의 층번호를 정의한다.
 	        	if(floor >= 0) {
 	        		floorVO.setiFloor(floor);
 	        		floorVO.setFloor(new Integer(floor+1).toString());
 	        		floorNmList.add(floorVO);
 	        	} else {
 	        		floorVO.setiFloor(floor);
 	        		String floorNm = new Integer(floor).toString();
 	        		floorVO.setFloor("B"+floorNm.substring(1));
 	        		floorNmList.add(floorVO);
 	        	}
 			}
 		}
    }

 	/**
 	 * 
 	 * 적용층 입력값을 파싱해서 단일층 층명, 정수형 층을 추출한다.
 	 */
	private int getSingleFloorNmMap(String applyFloor, Map<String, FloorNmVO> floorPrhMap) {
  		int floorNum = 0;
  		applyFloor = applyFloor.trim();
  		
  		// 만약 범위기준 데이터가 존재하면 맨마지막 문자열을 시작문자열로 처리한다.
  		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
  			applyFloor = applyFloor.replaceAll("-", "~");
  			String mApplyFloor[] = applyFloor.split("~");
  			applyFloor = mApplyFloor[0].trim();
  		}

  		// 단일 문자 기준값을 가지고 있으면 이미 매핑된 값을 전달한다.
        if(floorPrhMap.containsKey(applyFloor)) {
        	floorNum = floorPrhMap.get(applyFloor).getFLOORNUM();
      		return floorNum;
        }

  		// 만약 범위 기준 데이터 존재 시
  		if(applyFloor.equals("F")) {
  			floorNum = 3;
  		} else if(applyFloor.equals("L")) {
  			// L: 로비
  			floorNum = 0;
  		} else {
  			// 층, F 문구가 존재시 제거하고 처리
  			applyFloor = applyFloor.replaceAll("층", "");
  			applyFloor = applyFloor.replaceAll("F", "");
  			if(applyFloor.indexOf("B") > -1) {
  				// 지하층 표기
  				////System.out.println("앞자리:"+applyFloor.substring(0, 1));
  				////System.out.println("뒷자리:"+applyFloor.substring(1));
  				floorNum = new Integer("-"+applyFloor.substring(1)).intValue();
  			} else {
  				////System.out.println("앞자리:"+applyFloor);
  				floorNum = new Integer(applyFloor).intValue() - 1;
  			}
  		}
  		
  		return floorNum;
    }

 	/**
 	 * 
 	 * 적용층 입력값을 파싱해서 범위형 층명, 정수형 층을 추출한다. 시퀀스 적용
 	 */
	private void getRangeFloorNmSeqn(String applyFloor, List<FloorVO> floorNmList, Map<String, Integer> startMap, Map<String, FloorNmVO> floorPrhMap) {
		
        // 만약 범위 기준 특수문자로 -값으로 표신된경우 ~로 변경해서 처리
    	applyFloor =  applyFloor.replaceAll("-", "~");
    	String mApplyFloor[] = applyFloor.split("~");
 		int startNumFrom = startMap.get("startNumFrom");
    	if(mApplyFloor.length == 2) {
        	int iCharFloorFrom = getSingleFloorNmMap(mApplyFloor[0], floorPrhMap);
        	int floorNumTo = getSingleFloorNmMap(mApplyFloor[1], floorPrhMap);
        	for(int i=iCharFloorFrom; i <= floorNumTo; i++) {
        		FloorVO floorVO = new FloorVO();
            	if(startNumFrom >= 0) {
            		// 지상층 범위
            		int floor = startNumFrom;
            		floorVO.setiFloor(floor);
            		floorVO.setFloor(new Integer(floor+1).toString());
            		floorNmList.add(floorVO);
            	} else {
            		// 지하층 범위
            		int floor = startNumFrom;
            		floorVO.setiFloor(floor);
            		String floorNm = new Integer(floor).toString();
            		floorVO.setFloor("B"+floorNm.substring(1));
            		floorNmList.add(floorVO);
            	}

                if(i >=0) {
            		floorVO.setFloorNm(new Integer(i+1).toString());
                } else {
            		String floorNm = new Integer(i).toString();
            		floorVO.setFloorNm("B"+floorNm.substring(1));
                }

                if(i == iCharFloorFrom) {
                	floorVO.setFloorNm(mApplyFloor[0]);
                } else if(i == floorNumTo) {
                	floorVO.setFloorNm(mApplyFloor[1]);
                }
                startNumFrom = startNumFrom +1;
        	}
    	}
    	// 연속 시퀀스 값을 유지한다. -> 결국 순차, 인덱스값을 생성한다.
    	startMap.put("startNumFrom", startNumFrom);
    }

 	/**
 	 * 
 	 * 적용층 입력값을 파싱해서 범위형 층명, 정수형 층을 추출한다.
 	 */
	private void getRangeFloorNm(String applyFloor, List<FloorVO> floorNmList, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap ) {
        // 만약 범위 기준 특수문자로 -값으로 표신된경우 ~로 변경해서 처리
    	applyFloor =  applyFloor.replaceAll("-", "~");
    	String mApplyFloor[] = applyFloor.split("~");
        
    	// from to 값으로 처리가 되어야 하는군
    	if(mApplyFloor.length == 2) {
        	int floorNumFrom = getSingleFloorNmMap(mApplyFloor[0], floorPrhMap);
        	int floorNumTo = getSingleFloorNmMap(mApplyFloor[1], floorPrhMap);
        	for(int i=floorNumFrom; i <= floorNumTo; i++) {
        		FloorVO floorVO = new FloorVO();
                if(floorKeyMap.containsKey(i)) {
                	FloorNmVO floorNmVO = floorPrhMap.get(floorKeyMap.get(i));
            		floorVO.setiFloor(floorNmVO.getFLOORNUM());
            		floorVO.setFloor(floorNmVO.getFLOOR());
            		floorVO.setFloorNm(floorNmVO.getFLOORNM());
            		floorNmList.add(floorVO);
                } else {
                	if(i >= 0) {
                		// 지상층 범위
                		int floor = i;
                		floorVO.setiFloor(floor);
                		floorVO.setFloor(new Integer(floor+1).toString());
                		floorVO.setFloorNm(new Integer(floor+1).toString());
                		floorNmList.add(floorVO);
                	} else {
                		// 지하층 범위
                		int floor = i;
                		floorVO.setiFloor(floor);
                		String floorNm = new Integer(floor).toString();
                		floorVO.setFloor("B"+floorNm.substring(1));
                		floorVO.setFloorNm("B"+floorNm.substring(1));
                		floorNmList.add(floorVO);
                	}
                }
        	}
    	}
    }

 	/**
 	 * 
 	 * 적용층 출력값 중 연속층에 대해서는 ~ 적용층으로 변환하는 함수
 	 */
	public String getGroupFloorNm(String FLOOR, String FLOORNM) {
		String grpFloorNm = FLOORNM;
		
        String[] FLOORList = FLOOR.split(","); 		
        String[] FLOORNMList = FLOORNM.split(","); 		
        int iStartFloor = -6;
        int iNextFloor = -6;
        int addInterval = -6;
        int iDif = -1;
        int startIndex = 0;
        String tmpGrpNm = "";
        // 그룹표현 시 ~ 을 사용하자.
        if(FLOORList.length > 2) {
        	for(int i=1; i < FLOORList.length; i++) {
            	iStartFloor = new Integer(FLOORList[i-1]).intValue();
        		iNextFloor = new Integer(FLOORList[i]).intValue();
        		if(iStartFloor > 0) {
            		iStartFloor = iStartFloor -1;
            	}
        		if(iNextFloor > 0) {
            		iNextFloor = iNextFloor -1;
            	}
            	if(i==1) {
            		addInterval = 0;
            		startIndex  = 0;
            	}
        		iDif = iNextFloor - iStartFloor;
                if(iDif != 1) {
                	if(addInterval == 0) {
                		tmpGrpNm = tmpGrpNm +","+FLOORNMList[startIndex];
                	} else if(addInterval == 1) {
                		tmpGrpNm = tmpGrpNm +","+FLOORNMList[startIndex] + ","+FLOORNMList[startIndex+addInterval];
                	} else {
                		tmpGrpNm = tmpGrpNm +","+FLOORNMList[startIndex] + "~"+FLOORNMList[startIndex+addInterval];
                	}
                	startIndex = i;
                	addInterval = 0;
                } else {
                	addInterval = addInterval+ 1;
                }

                if(i == (FLOORList.length-1)) {
                	if(addInterval == 0) {
                		tmpGrpNm = tmpGrpNm +","+FLOORNMList[i];
                	} else if(addInterval == 1) {
                		tmpGrpNm = tmpGrpNm +","+FLOORNMList[i-1] + ","+FLOORNMList[i];
                	} else {
                		tmpGrpNm = tmpGrpNm +","+FLOORNMList[startIndex] + "~"+FLOORNMList[i];
                	}
                }
        	}
            grpFloorNm = tmpGrpNm.substring(1);
        }
		return grpFloorNm;
    }

	/**
	 * 
	 * 적용층 입력값을 파싱해서 층명, 층정수값을 추출한다.
	 */
	private String getFloor(int iFloor) {
		String floor = "";
    	if(iFloor >= 0) {
    		floor = new Integer(iFloor+1).toString();
    	} else {
    		// 지하층 범위
    		String floorTmp = new Integer(iFloor).toString();
    		floor = "B"+floorTmp.substring(1);
    	}
		return floor;
	}

}
