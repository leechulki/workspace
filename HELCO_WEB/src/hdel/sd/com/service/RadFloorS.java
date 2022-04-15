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
 * ���𵨸��� �������� ����
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
public class RadFloorS {

	// �α׼���
	Logger logger = Logger.getLogger(this.getClass());
	
    /**
	 * 
	 * �������� ����������� ���� ������ �����Ѵ�.
	 * @param Map<String, String> prhMap, List<Map<String, String>>
	 * @param model
	 * @return
	 */
	public List<String> getFloorSpreadOut(String sUVSN, Map<String, String> prhMap, Map<String, FloorNmVO> floorPrhMap, List<SuvFloorVO> suvFloorList
			                             , List<Map> suvJambList, Map<Integer, String> floorSuvMap) {
        // floor Ű�� ���� �����. - ���ڙV�� �ٸ� ��� floor������ ������ �ڵ� ó���Ѵ�.
		Map<Integer, String> floorKeyMap = new HashMap<Integer, String>();

        // 0. ��ü ���� ������ ���ؼ�
		List<String> errMsgList = new ArrayList<String>();
		// A008 \����(FLOOR Q'TY)
		String elAfq =   prhMap.get("EL_AFQ");
		int   iElAfq = new Integer(elAfq).intValue();
		
		// A009 \��������(STOP Q'TY)
		String elAstq =   prhMap.get("EL_ASTQ");
		int  iElAstq = new Integer(elAstq).intValue();
		
		// A013 \����������(NON STOP Q'TY)
		String elAnstq =   prhMap.get("EL_ANSTQ");
        int  iElAnstq = 0;
		// A013 \����������(NON STOP Q'TY)
        if(elAnstq == null) {
        	iElAnstq = 0;
        } else {
        	if("".equals(elAnstq)) {
        		iElAnstq = 0;
        	} else {
        		iElAnstq = new Integer(elAnstq).intValue();
        	}
        }
        // A014 �������� ǥ��
		String elAnst =   prhMap.get("EL_ANST");
		if(elAnst == null) elAnst = "";
		
		// A018 REAR ������ ǥ��
		String elaff =   prhMap.get("EL_AFF");
		// �������� ����Ʈ
		List<FloorVO> elAnstNmList = null;
		if("".equals(elAnst)) {
			List<String> errMsgTmpListB = getEnterSeqnFloorList(sUVSN, elaff, floorPrhMap, floorKeyMap);
			for(String errMsg: errMsgTmpListB) {
				errMsgList.add(errMsg+"\n");
			}
		} else {
			// ����ġ�� ǥ�Ⱚ�� ���������� �����Ͽ� �������� �����͸� �����Ѵ�.
			// �������� ǥ�� ó���� ���� ����� �����.
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
			
            // ������ ���ڿ����� , ���� �� ����			
			if (addElaff.endsWith(",")) {
				addElaff = addElaff.substring(0, addElaff.length() - 1);
			}

			// ���������� �����ؼ� ���������� �����Ѵ�.
			List<String> errMsgTmpListB = getEnterSeqnFloorList(sUVSN, addElaff, floorPrhMap, floorKeyMap);
			for(String errMsg: errMsgTmpListB) {
				errMsgList.add(errMsg+"\n");
			}
		} 

		// ��������� ���� ������翡 ���ǵ� ���� ������ �����Ѵ�.
		
		// 1. JAMB ������ ���� 1,23
		// C002 \JAMB(1)
		String elCjm1 =   prhMap.get("EL_CJM1");
		// C007 JAMB(1) ������
		String elCjm1F = prhMap.get("EL_CJM1F");
		// C001 \JAMB(1) ����
		String elCjm1Q = prhMap.get("EL_CJM1Q");
		// C003 \HPI(1)
	    String elChpi1 = prhMap.get("EL_CHPI1");
		// jamp1 ����
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
		// C016 JAMB(2) ������
		String elCjm2F = prhMap.get("EL_CJM2F");
		// C010 \JAMB(2) ����
		String elCjm2Q = prhMap.get("EL_CJM2Q");
		// C012 \HPI(2)
	    String elChpi2 = prhMap.get("EL_CHPI2");
		// jamp2 ����
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
		// C025 JAMB(3) ������
		String elCjm3F = prhMap.get("EL_CJM3F");
		// C019 \JAMB(3) ����
		String elCjm3Q = prhMap.get("EL_CJM3Q");
		// C021 \HPI(3)
	    String elChpi3 = prhMap.get("EL_CHPI3");
		// jamp3 ����
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
	    // hpb ����������� ����
	    String elChpbm1 = prhMap.get("EL_CHPBM1");
	    String elChpbm1f = prhMap.get("EL_CHPBM1F");
	    String elChpbm1q = prhMap.get("EL_CHPBM1Q");
		// hpb1 ����
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
		// hpb2 ����
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
		// hpb3 ����
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
		// hpb4 ����
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

	    // hip ������
	    String elChipm1 = prhMap.get("EL_CHIPM1");
	    String elChipm1f = prhMap.get("EL_CHIPM1F");
	    String elChipm1q = prhMap.get("EL_CHIPM1Q");
		// hip1 ����
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
		// hip2 ����
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
		// hip3 ����
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
		// hip4 ����
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
	 
	    // Ȧ���� ���� ����
	    String elChl = prhMap.get("EL_CHL");
	    String elChlf = prhMap.get("EL_CHLF");
	    String elChlq = prhMap.get("EL_CHLQ");
		// Ȧ����1 ����
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
		// Ȧ����2 ����
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
	    
	    // �ҹ潺��ġ ����
	    String elCfrsw = prhMap.get("EL_CFRSW");
	    String elEmf = prhMap.get("EL_EMF");
		// �ҹ潺��ġ ����
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

		// ��ü�������� ���� �޽��� üũ
        // A008 \����(FLOOR Q'TY) = A009 \��������(STOP Q'TY) + A013 \����������(NON STOP Q'TY)
		if(iElAfq != (iElAstq + iElAnstq)) {
	    	String errMsg = "A008 \\����(FLOOR Q'TY) = A009 \\��������(STOP Q'TY) + A013 \\����������(NON STOP Q'TY) �հ谡 ��ġ���� �ʽ��ϴ�. ������ ��ϰ��� Ȯ���� �ֽʽÿ�";
	    	errMsgList.add(errMsg+"\n");
		}

	    // ��ü �������� ���� üũ �޽���
	    if( floorPrhMap.size() < iElAfq ) {
	    	String errMsg = "A014 �������� ǥ��� A018 REAR ������ ǥ�� ���������� ������ ���� ���� ������ �ֽ��ϴ�. ������ ��ϰ��� Ȯ���� �ֽʽÿ�.";
	    	errMsgList.add(errMsg+"\n");
	    }
	    
	    // �������� ���� ������ ��
	    Map<String, String> floorOrgMap = new HashMap<String, String>();
		for( String key : floorPrhMap.keySet() ) {
			FloorNmVO floorNmVO = floorPrhMap.get(key);
			floorOrgMap.put(floorNmVO.getFLOORNM(), floorNmVO.getFLOOR());
		}	    

		// ������������ ���� �������� �����Ѵ�. ������ �������� ��������� ������ �����ش�.
		getFloorSuvList(sUVSN, floorPrhMap, floorKeyMap, floorSuvMap);

        // ǥ�ñ⺰ ������� ����
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
        // ���� ���� �Ǹ鼭 ���� �ߺ��Ǵ� �����Ͱ� ������
        // �̷� ��쿡�� ���ڷ� ������ �Ұ���
        if(floorPrhMap.size() == cpFloorPrhMap.size()) {
            // cpFloorPrhMap.putAll(floorPrhMap);  // ��ü ������ ������ �̹� ������ ������
    	    for(int i=0; i<suvFloorList.size(); i++) {
    	    	SuvFloorVO suvFloorVO = suvFloorList.get(i);
    	    	List<String> errMsgTmpList15 = getSuvPrhFloorNmList(sUVSN, suvFloorVO.getSUVPRD(), suvFloorVO.getAPPLYFLOOR(), cpFloorPrhMap, floorKeyMap);
//    			for(String errMsg: errMsgTmpList15) {
//    				errMsgList.add(errMsg+"\n");
//    			}
    	    }

            // cpFloorPrhMap ���ΰ���� Ȯ���Ѵ�.
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
        	// ������ ǥ�ñ� ����� �����Ѵ�.
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

    	    // cpFloorPrhMap ���ΰ���� Ȯ���Ѵ�.
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
            	// �̺κ� ó�� ������ ���� ������ �ʿ��ϴ�.
            	// ���� �� ���� �������� �ʴ� ���
            	cpFloorPrhMap.clear();
            	floorKeyMap.clear();

            	// ��ü
        		List<String> rmList = new ArrayList<String>();
        		for( String key : floorPrhMap.keySet() ) {
        			FloorNmVO floorNmVO = floorPrhMap.get(key);
        			if(
        			   !"O".equals(floorNmVO.getINPUTTYPE()) 
        			   ) {
        				rmList.add(key);	
        			}
                }

        		// ����
        		for(int i=0; i < rmList.size(); i++) {
        			floorPrhMap.remove(rmList.get(i));
        		}

            	// cpFloorPrhMap ǥ�ñ� ���� ��ü ����
        	    for(int i=0; i<suvFloorList.size(); i++) {
        	    	SuvFloorVO suvFloorVO = suvFloorList.get(i);
        	    	List<String> errMsgTmpList15 = getSuvPrhFloorOrderMap(sUVSN, suvFloorVO.getSUVPRD(), suvFloorVO.getAPPLYFLOOR(), cpFloorPrhMap, floorKeyMap);
        	    }

            	// ������� �ٸ����� ǥ�ñ� ������ �����ϴ� �� ���ļ����� ������ �����Ѵ�.
            	// ������ �� �ؽ��� �����͸� �����Ѵ�.
        	    // ǥ�ñ⺰ ������� ����
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

                // ��Ʈ �������� ������ �����Ѵ�.
                seqCpKeyCnt = 0;
                for( int i=0; i < seqKey.length; i++ ) {
                	FloorNmVO floorNmVO   = seqFloorPrhMap.get(seqKey[i]);
                	String FLOORYSNO = floorNmVO.getFLOORYSNO();
                	// ���� �����ϴ� ��� ��Ʈ �����ؼ� ���� ������ �ش�.
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

                // floorPrhMap �ʱ�ȭ
                floorPrhMap.clear();
                floorKeyMap.clear();
                for( Integer key : seqFloorPrhMap.keySet() ) {
                	FloorNmVO floorNmVO = seqFloorPrhMap.get(key);
                	floorPrhMap.put(floorNmVO.getFLOOR(), floorNmVO);
                	floorKeyMap.put(floorNmVO.getFLOORNUM(), floorNmVO.getFLOORNM());
                }
            }
        }

	    // ���� JAMB �� ������� ����
	    if(suvJambList != null) {
            // ���� ����� 1�� ���
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
	    		// jamb ����
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
			
			// ������� �⺻�� �������� �� �����͸� �����Ѵ�.  floorOrgMap
			if(floorOrgMap.containsKey(floorNmVO.getFLOORNM())) {
				// ���� ������� ������ ����ó���Ѵ�.
				floorNmVO.setFLOOR(floorOrgMap.get(floorNmVO.getFLOORNM()));
			}
        }

		// ����
		for(int i=0; i < rmList.size(); i++) {
			floorPrhMap.remove(rmList.get(i));
		}
	    
	    // ���� ������ ������� ���� ��� �޽��� ó��
	    if( floorPrhMap.size() == 0) {
	    	String errMsg = "C007 JAMB(1) ������, C016 JAMB(2) ������, C025 JAMB(3) ������ �Է°��� Ȯ���� �ּ���";
	    	errMsgList.add(errMsg+"\n");
	    }

	    // �������� ���� ��ü ������ ���� ���� ����� Ȯ���Ѵ�.
	    // 1 HIP�Ѽ���-HIP �𵨰��� �����ϴ� ���(C001 \JAMB(1) ���� + C010 \JAMB(2) ���� + C019 \JAMB(3) ����) >= ���� HIP�Ѽ���
	    //if((iElCjm1Q + iElCjm2Q + iElCjm3Q) < hipCnt ) {
	    	// �����޽��� ó��
	    //	String errMsg = "������ HIP �Ѽ����� ������� HIP �Ѽ������� �����ϴ�. HIP ���� ��ϵ� JAMB(1,2,3) ������, JAMB(1,2,3) ���� �Է°��� Ȯ���ϼ���\n"
        //			                + "������� �Է°��� ������ ���� ��쿡�� HIP ������ Ȯ�� ��û�� �Ͻʽÿ�";
	    //	errMsgList.add(errMsg+"\n");
	    //}

	    // 2. HPB�Ѱ���(C074 \HPB: MIDDLE(1) : ���� + C079 \HPB: MIDDLE(2) : ����) + HIP�Ѽ���(C095 \HIP: MIDDLE(1) : ���� + C100 \HIP: MIDDLE(2) : ����) >= ����HPB�Ѱ��� + ����HIP�Ѱ���
        //if( (iElChpbm1q + iElChpbm2q + iElChpbtq + iElChpbbq +iElChipm1q + iElChipm2q + iElChiptq + iElChipbq ) < (hpbCnt + hipCnt ) ) {
	    	// �����޽��� ó��
        	//String errMsg = "������ HPB, HIP �Ѽ����� ������� HPB, HIP �Ѽ������� �����ϴ�. HPB, HIP �������� HPB, HIP ���� �Է°��� Ȯ���ϼ���\n"
        	//		                + "������� �Է°��� ������ ���� ��쿡�� HPB, HIP ������ Ȯ�� ��û�� �Ͻʽÿ�";
	    	//errMsgList.add(errMsg+"\n");
        //}

        // 3. Ȧ���� ���� üũ
        //if( (iElChlq + iElChl2q) < hTnCnt ) {
	    	// �����޽��� ó��
        	//String errMsg = "������ Ȧ���� �Ѽ����� ������� Ȧ���� �Ѽ������� �����ϴ�. Ȧ���� ������, Ȧ���� ���� �Է°��� Ȯ���ϼ���\n"
        	//		                + "������� �Է°��� ������ ���� ��쿡�� Ȧ���� ������ Ȯ�� ��û�� �Ͻʽÿ�";
	    	//errMsgList.add(errMsg+"\n");
        //}

        // 4. ����ġ���� �ҹ潺��ġ�� �����ϳ� ������翡�� ��ϵǾ� �ִ� ���
        //if( iElCfrsw < fswCnt ) {
	    	// �����޽��� ó��
        	//String errMsg = "������ �ҹ潺��ġ�� ������ ������� �ҹ潺��ġ �Է°��� �����ϴ�.\n"
        	//		           + "������� �Է°��� ������ ���� ��쿡�� �ҹ潺��ġ ������ Ȯ�� ��û�� �Ͻʽÿ�";
	    	//errMsgList.add(errMsg+"\n");
        //}

        // ���� �������� �����ϳ� ���� JAMB�� ���ǵǾ� ���� ���� ��쿡�� �޽����� ����Ѵ�.
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
//        			preMsg = "Ȧ����";
//        		} else if("FSW".equals(floorNmVO.getFSWPRD())) {
//        			preMsg = "�ҹ潺��ġ";
//        		}
//        	    errMsg = "�������("+preMsg+") ����("+floorNmVO.getFLOORNM()+")�� �ش��ϴ� ������� ������ �������� �ʽ��ϴ�. ������� �Ǵ� �������� Ȯ���Ͻʽÿ�";
//        	    errMsgList.add(errMsg);
//        	}
//		}
        return errMsgList;
	}

	/**
	 * ���Ա� ���� �������� ���� ���� ������ �����Ѵ�. ������ ó��
	 */
	private List<String> getEnterSeqnFloorList(String sUVSN, String apployFloor, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
        // ���� ������ ���� �����Ѵ�.
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
        		floorNmVO.setINPUTTYPE("O"); // �������� ����
        		floorPrhMap.put(floorKey, floorNmVO);
        		floorKeyMap.put(floorVO.getiFloor(), floorVO.getFloorNm());
        	}
        }

        return errMsgList;
	}
	
	/**
	 * JAMB GROUP ID, JAMB ������, JAMB ����, HPI ����
	 * JAMB ������ ������ �����Ѵ�.
	 */
	private List<String> getJambHpiFloorList(String sUVSN, String jambGrp, String jamModel, String elCjmF, String elCjmQ
			     , String elChpi, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();

		// JAMB ���� ������ �����Ѵ�.
	    if( elCjmF != null) {
	    	if(!elCjmF.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(elCjmF, floorPrhMap, floorKeyMap);
		        // JAMB 1 ���� ������ ���������� �����
		        for(int i=0; i < floorNmList.size(); i++) {
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		floorNmVO.setJAMBGRP(jambGrp);
		        		floorNmVO.setJAMBMODEL(jamModel);
		        		floorNmVO.setJAMBINFO(jambGrp+"-"+jamModel);
		        		// hpi ��
		        		floorNmVO.setHPIMODEL(elChpi);
		        	} else {
		        		String errMsg = "JAMB("+jambGrp+") ������ �Է°� �� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
		        		errMsgList.add(errMsg);
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}
	
	/**
	 * HPB ������ ������ �����Ѵ�.
	 */
	private List<String> getHpbFloorList(String div, String model, String applyFloor , String applyQnty, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		// HPB ���� ����
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
		        		String errMsg = "C073 HPB: MIDDLE(1) : ������ ����("+floorNmList.size()
	                       +")�� C074 \\HPB: MIDDLE(1) : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
		        		errMsgList.add(errMsg);
		        	} else if("2".equals(div)) {
		        		String errMsg = "C078 HPB: MIDDLE(2) : ������ ����("+floorNmList.size()
	                       +")�� C079 \\HPB: MIDDLE(2) : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
		        		errMsgList.add(errMsg);
		        	} else if("3".equals(div)) {
		        		String errMsg = "C083 HPB: TOP : ������ ����("+floorNmList.size()
	                       +")�� C084 \\HPB: TOP : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
		        		errMsgList.add(errMsg);
		        	} else if("4".equals(div)) {
		        		String errMsg = "C088 HPB: BOTTOM : ������ ����("+floorNmList.size()
	                       +")�� C089 \\HPB: BOTTOM : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
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
			        		String errMsg = "HPB �������� �ߺ� �Էµ� �������� �����մϴ�. HPB ������ �Է°��� Ȯ���Ͻʽÿ�";
			        		errMsgList.add(errMsg);
		        		}
		        		floorNmVO.setHPBMODEL(model);
		        	} else {
			        	if("1".equals(div)) {
			        		String errMsg = "C073 HPB: MIDDLE(1) : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	} else if("2".equals(div)) {
			        		String errMsg = "C078 HPB: MIDDLE(2) : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	} else if("3".equals(div)) {
			        		String errMsg = "C083 HPB: TOP : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	} else if("4".equals(div)) {
			        		String errMsg = "C088 HPB: BOTTOM : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	}
		        	}
		        }
	    	}
	    }
	    return errMsgList;
	}


	/**
	 * HIP ������ ������ �����Ѵ�.
	 */
	private List<String> getHipFloorList(String div, String model, String applyFloor , String applyQnty, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		// HIP ���� ����
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
		        		String errMsg = "C094 HIP: MIDDLE(1) : ������ ����("+floorNmList.size()
	                       +")�� C095 \\HIP: MIDDLE(1) : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
		        		errMsgList.add(errMsg);
		        	} else if("2".equals(div)) {
		        		String errMsg = "C099 HIP: MIDDLE(2) : ������ ����("+floorNmList.size()
	                       +")�� C100 \\HIP: MIDDLE(2) : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
		        		errMsgList.add(errMsg);
		        	} else if("3".equals(div)) {
		        		String errMsg = "C104 HIP: TOP : ������ ����("+floorNmList.size()
	                       +")�� C105 \\HIP: TOP : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
		        		errMsgList.add(errMsg);
		        	} else if("4".equals(div)) {
		        		String errMsg = "C109 HIP: BOTTOM : ������ ����("+floorNmList.size()
	                       +")�� C110 \\HIP: BOTTOM : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
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
			        		String errMsg = "HIP �������� �ߺ� �Էµ� �������� �����մϴ�. HIP ������ �Է°��� Ȯ���Ͻʽÿ�";
			        		errMsgList.add(errMsg);
		        		}		        		
		        		floorNmVO.setHIPMODEL(model);
		        	} else {
		        		// jamb �������� ������� ���� �� ������ ������
			        	if("1".equals(div)) {
			        		String errMsg = "C094 HIP: MIDDLE(1) : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	} else if("2".equals(div)) {
			        		String errMsg = "C099 HIP: MIDDLE(2) : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	} else if("3".equals(div)) {
			        		String errMsg = "C104 HIP: TOP : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	} else if("4".equals(div)) {
			        		String errMsg = "C109 HIP: BOTTOM : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	}
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}

	/**
	 * Ȧ���� ������ ������ �����Ѵ�.
	 */
	private List<String> getHltFloorList(String div, String model, String applyFloor , String applyQnty, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		// HIP ���� ����
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
		        		String errMsg = "C113 \\Ȧ����(1) : ������ ����("+floorNmList.size()
	                       +")�� C114 \\Ȧ����(1) : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
		        		errMsgList.add(errMsg);
		        	} else if("2".equals(div)) {
		        		String errMsg = "C117 \\Ȧ����(2) : ������ ����("+floorNmList.size()
	                       +")�� C118 \\Ȧ����(2) : ����("+applyQnty+")�� ��ġ���� �ʽ��ϴ�.";
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
			        		String errMsg = "Ȧ���� �������� �ߺ� �Էµ� �������� �����մϴ�. Ȧ���� ������ �Է°��� Ȯ���Ͻʽÿ�";
			        		errMsgList.add(errMsg);
		        		}		        		
		        		floorNmVO.setHLTNMODEL(model);
		        	} else {
		        		// jamb �������� ������� ���� �� ������ ������
			        	if("1".equals(div)) {
			        		String errMsg = "C113 \\Ȧ����(1) : ���������� ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	} else if("2".equals(div)) {
			        		String errMsg = "C117 \\Ȧ����(2) : ����������  ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
			        		errMsgList.add(errMsg);
			        	}
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}
	
	/**
	 * �ҹ潺��ġ ������ ���� ������ �����Ѵ�.
	 */
	private List<String> getFswFloorList(String model, String applyFloor , Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
        String applyQnty = "1";
		// �ҹ潺��ġ ���� ����
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
		        	String errMsg = "C123 ������(�⺻�Է�) ����("+floorNmList.size()
                       +")�� C114 \\Ȧ����(1) : ����(1)�� ��ġ���� �ʽ��ϴ�.";
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
			        		String errMsg = "�ҹ潺��ġ �������� �ߺ� �Էµ� �������� �����մϴ�. �ҹ潺��ġ ������ �Է°��� Ȯ���Ͻʽÿ�";
			        		errMsgList.add(errMsg);
		        		}		        		
		        		floorNmVO.setFSWMODEL(model);
		        	} else {
		        		// jamb �������� ������� ���� �� ������ ������
		        		String errMsg = "C123 ������(�⺻�Է�)����  ������ ǥ�⿡ �Էµ��� ���� ���ڰ��� �����մϴ�.";
		        		errMsgList.add(errMsg);
		        	}
		        }
	    	}
	    }
	    
	    return errMsgList;
	}

	/**
	 * ������� ���� �������� ǥ�ñ� ������ �����Ѵ�.
	 */
	private List<String> getSuvPrhFloorNmList(String sUVSN, String model, String applyFloor, Map<String, FloorNmVO> floorPrhMap
			                        ,Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
		        for(int i=0; i < floorNmList.size(); i++) {
		        	// ���� ������ ��ü ī��Ʈ
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloorNm();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		// ǥ�ñ⺰ ���������� ���� ������
		        		// HPB	HPB(�����ư)
		        		// HIP	HIP(������ġǥ�ñ�)
		        		// HPI	HPI(������ġǥ�ñ�)
		        		// HLT	Ȧ����
		        		// PKS	PARKING S/W
		        		// FSW	�ҹ潺��ġ
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
		        		// ǥ�ñ� ���� ����
		        		floorNmVO.setISFLOOR(true);
		        	}
		        }
	    	}
	    }

	    return errMsgList;
	}

	/**
	 * ������� ���� �������� ǥ�ñ� ������ �����Ѵ�.
	 */
	private List<String> getSuvPrhFloorList(String sUVSN, String model, String applyFloor, Map<String, FloorNmVO> floorPrhMap
			                        ,Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
		        for(int i=0; i < floorNmList.size(); i++) {
		        	// ���� ������ ��ü ī��Ʈ
		        	FloorVO floorVO = floorNmList.get(i);
		        	String floorKey = floorVO.getFloor();
		        	if(floorPrhMap.containsKey(floorKey)) {
		        		FloorNmVO floorNmVO = floorPrhMap.get(floorKey);
		        		// ǥ�ñ⺰ ���������� ���� ������
		        		// HPB	HPB(�����ư)
		        		// HIP	HIP(������ġǥ�ñ�)
		        		// HPI	HPI(������ġǥ�ñ�)
		        		// HLT	Ȧ����
		        		// PKS	PARKING S/W
		        		// FSW	�ҹ潺��ġ
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
		        		// ǥ�ñ� ���� ����
		        		floorNmVO.setISFLOOR(true);
		        	} else {
		        		// ���� ��簪�� �������� ������ ������ �����ؼ� ó���Ѵ�.
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
	 *  ������� ������, ���� ���� ���� �� ���� ���� ��ġ�ϸ� ���� ���ļ����� �����Ѵ�.
	 */
	private List<String> getSuvPrhFloorOrderMap(String sUVSN, String model, String applyFloor, Map<String, FloorNmVO> floorPrhMap
			                        ,Map<Integer, String> floorKeyMap) {
		List<String> errMsgList=new ArrayList<String>();
		if( applyFloor != null) {
	    	if(!applyFloor.equals("")) {
			    List<FloorVO> floorNmList = getFloorNm(applyFloor, floorPrhMap, floorKeyMap);
			    for(int i=0; i < floorNmList.size(); i++) {
		        	// ���� ������ ��ü ī��Ʈ
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
	 * ���� JAMB ������� ���� �����Ѵ�.
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
	 * ���Ա� ���� �������� �������� ���Ͽ� ���� �����͸� �����Ѵ�.
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
	        // ���� �������� �ʴ� ��� floorIndex = 0;
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
		//	throw new BizException("������� ������, ���� �������, ǥ�ñ� ������� ���������� ������ �� �����ϴ�. �Էµ� ������, ���� �������, ǥ�ñ� ������簪�� Ȯ���Ͻʽÿ�.");
		//}
	}

	/**
	 * 
	 * ������ �Է°��� �Ľ��ؼ� ����, ���������� �����Ѵ�. �ش� �������� ���� �������� �����ؼ� ó���Ѵ�.
	 */
	private List<FloorVO> getFloorNmSeqn(String applyFloor, Map<String, FloorNmVO> floorPrhMap) {
		List<FloorVO> floorNmList = new ArrayList<FloorVO>();
		Map<String, Integer> startMap = new HashMap<String, Integer>();
		
        // , ���� �����ϸ� mult ���� �����ϴ� ������ �Ǵ��Ѵ�.
		if(applyFloor == null) return floorNmList;
		
        // no emty���� �ִ� ���
		applyFloor = applyFloor.trim();
		if(applyFloor.equals("")) return floorNmList;
		
		// ��� ������ �Է°��� �빮�ڷ� ��ȯ�ؼ� ó���Ѵ�.
		applyFloor = applyFloor.trim().toUpperCase();
		applyFloor = applyFloor.replaceAll("[.]", ",");
		// �� ���� ����
		applyFloor = applyFloor.replaceAll("��", "");
		
		// ���������� ���� �����ϴ� ��� ó��
		if(applyFloor.indexOf(",") > -1) {
            String mApplyFloor[] = applyFloor.split(",");
            if(mApplyFloor.length > 1) {
            	// ���� ���ڰ� ���ڰ��� ��� üũ
    	        int startNumFrom = -6;
            	int startNmIndex = -6;
            	boolean isStart = false;
            	char tmp;
            	for(int i=0; i < mApplyFloor.length; i++) {
                	for(int j=0; j < 1; j++) {
                		tmp = mApplyFloor[i].charAt(j);
                		// ù���ڰ� 0~9 �̰ų� B�� ��� -> ���ǵ� ������ ����
                		if(('0' <= tmp && tmp <'9') || 'B' == tmp) {
                			// ���ڰ� �ƴ� �ε���
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
			// ���ϰ� floor
			getSingleFloorNmSeqn(applyFloor, floorNmList, startMap, floorPrhMap);
		}
		return floorNmList;
	}

	/**
	 * 
	 * ������ �Է°��� �Ľ��ؼ� ����, ���������� �����Ѵ�.
	 */
	private List<FloorVO> getFloorNm(String applyFloor, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
		List<FloorVO> floorNmList = new ArrayList<FloorVO>();
        // , ���� �����ϸ� mult ���� �����ϴ� ������ �Ǵ��Ѵ�.
		if(applyFloor == null) return floorNmList;
		
        // no emty���� �ִ� ���
		applyFloor = applyFloor.trim();
		if(applyFloor.equals("")) return floorNmList;
		
		// ��� ������ �Է°��� �빮�ڷ� ��ȯ�ؼ� ó���Ѵ�.
		applyFloor = applyFloor.trim().toUpperCase();
		applyFloor = applyFloor.replaceAll("[.]", ",");
		// �� ���� ����
		applyFloor = applyFloor.replaceAll("��", "");
		
		// ���������� ���� �����ϴ� ��� ó��
		if(applyFloor.indexOf(",") > -1) {
            String mApplyFloor[] = applyFloor.split(",");
            if(mApplyFloor.length > 1) {
            	// ���۰�
                for(int i=0; i < mApplyFloor.length; i++) {
                   	getMultiFloorNm(mApplyFloor[i], floorNmList, floorPrhMap, floorKeyMap);
                }
            }
		} else {
			// ���ϰ� floor
			getSingleFloorNm(applyFloor, floorNmList, floorPrhMap, floorKeyMap);
		}
		
		return floorNmList;
	}

	/**
	 * 
	 * ������ �Է°��� �Ľ��ؼ� ������ ����, ������ ���� �����Ѵ�. ������ ����
	 */
	private void getSingleFloorNmSeqn(String applyFloor, List<FloorVO> floorNmList, Map<String, Integer> startMap, 
			Map<String, FloorNmVO> floorPrhMap) {
    	FloorVO floorVO = new FloorVO();
 		applyFloor = applyFloor.trim();
 		
 		// ���� ������ �߿� space�� �����ϸ� space�� �����Ѵ�.
 		applyFloor = applyFloor.replaceAll(" ", "");
		// �� ���� ����
		applyFloor = applyFloor.replaceAll("��", "");
 		
 		// ���� ���� ���� ������ ���� ��
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
 			// L: �κ�
 	 		floorVO.setFloorNm(applyFloor);
 			floorVO.setiFloor(0);
 			floorVO.setFloor(applyFloor);
 			floorNmList.add(floorVO);
 		} else {
 			// ��, F ������ ����� �����ϰ� ó��
 			applyFloor = applyFloor.replaceAll("F", "");
 			floorVO.setFloorNm(applyFloor);
 			if(applyFloor.indexOf("B") > -1) {
 				// ������ ǥ��
 				////System.out.println("���ڸ�:"+applyFloor.substring(0, 1));
 				////System.out.println("���ڸ�:"+applyFloor.substring(1));
 				int floorNum = new Integer("-"+applyFloor.substring(1)).intValue();
 	 			floorVO.setiFloor(floorNum);
 	 			floorVO.setFloor(applyFloor);
 				floorNmList.add(floorVO);
 			} else {
 				////System.out.println("���ڸ�:"+applyFloor);
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
	 * ������ �Է°��� �Ľ��ؼ� ������ ����, ������ ���� �����Ѵ�.
	 */
	private void getSingleFloorNm(String applyFloor, List<FloorVO> floorNmList, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
    	FloorVO floorVO = new FloorVO();
 		applyFloor = applyFloor.trim();
 		
 		// ���� ������ �߿� space�� �����ϸ� space�� �����Ѵ�.
 		applyFloor = applyFloor.replaceAll(" ", "");
		// �� ���� ����
		applyFloor = applyFloor.replaceAll("��", "");
 		floorVO.setFloorNm(applyFloor);
 		
 		// ���� ���� ���� ������ ���� ��
 		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
 			getRangeFloorNm(applyFloor, floorNmList, floorPrhMap, floorKeyMap);
 		} else if(applyFloor.equals("F")) {
 			floorVO.setiFloor(3);
 			floorVO.setFloor(applyFloor);
 			floorNmList.add(floorVO);
 		} else if(applyFloor.equals("L")) {
 			// L: �κ�
 			floorVO.setiFloor(0);
 			floorVO.setFloor(applyFloor);
 			floorNmList.add(floorVO);
 		} else {
 			// ��, F ������ ����� �����ϰ� ó��
 			applyFloor = applyFloor.replaceAll("F", "");
 			if(floorPrhMap.containsKey(applyFloor)) {
 	 			floorVO.setiFloor(floorPrhMap.get(applyFloor).getFLOORNUM());
 	 			floorVO.setFloor(floorPrhMap.get(applyFloor).getFLOOR());
 				floorNmList.add(floorVO);
 			} else {
 	 			if(applyFloor.indexOf("B") > -1) {
 	 				// ������ ǥ��
 	 				////System.out.println("���ڸ�:"+applyFloor.substring(0, 1));
 	 				////System.out.println("���ڸ�:"+applyFloor.substring(1));
 	 				int floorNum = new Integer("-"+applyFloor.substring(1)).intValue();
 	 	 			floorVO.setiFloor(floorNum);
 	 	 			floorVO.setFloor(applyFloor);
 	 				floorNmList.add(floorVO);
 	 			} else {
 	 				////System.out.println("���ڸ�:"+applyFloor);
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
	 * ������ �Է°��� �Ľ��ؼ� ������ ����, ������ ���� �����Ѵ�. ������ ����
	 */
	private void getMultiFloorNmSeqn(String applyFloor, List<FloorVO> floorNmList, Map<String, Integer> startMap, Map<String, FloorNmVO> floorPrhMap) {
    	FloorVO floorVO = new FloorVO();
 		applyFloor = applyFloor.trim();
 		// ���� ������ �߿� space�� �����ϸ� space�� �����Ѵ�.
 		applyFloor = applyFloor.replaceAll(" ", "");
 		int startNumFrom = startMap.get("startNumFrom");

 		// ���� ���� ���� ������ ���� ��
 		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
 			getRangeFloorNmSeqn(applyFloor, floorNmList, startMap, floorPrhMap);
 		} else {
 			int floor = startNumFrom;
			// �������� ���� ���� ����ȣ�� �����Ѵ�.
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
	 * ������ �Է°��� �Ľ��ؼ� ������ ����, ������ ���� �����Ѵ�.
	 */
	private void getMultiFloorNm(String applyFloor, List<FloorVO> floorNmList, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap) {
    	FloorVO floorVO = new FloorVO();
 		applyFloor = applyFloor.trim();
 		// ���� ������ �߿� space�� �����ϸ� space�� �����Ѵ�.
 		applyFloor = applyFloor.replaceAll(" ", "");
 		// ���� ���� ���� ������ ���� ��
 		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
 			getRangeFloorNm(applyFloor, floorNmList, floorPrhMap, floorKeyMap);
 		} else {
 	 		floorVO.setFloorNm(applyFloor);
 			// ���� ���ڰ��� �����ϸ� ó��
 			if(floorPrhMap.containsKey(applyFloor)) {
        		floorVO.setiFloor(floorPrhMap.get(applyFloor).getFLOORNUM());
        		floorVO.setFloor(floorPrhMap.get(applyFloor).getFLOOR());
        		floorNmList.add(floorVO);
 			} else {
 	        	int floor = getSingleFloorNmMap(applyFloor, floorPrhMap);
 				// �������� ���� ���� ����ȣ�� �����Ѵ�.
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
 	 * ������ �Է°��� �Ľ��ؼ� ������ ����, ������ ���� �����Ѵ�.
 	 */
	private int getSingleFloorNmMap(String applyFloor, Map<String, FloorNmVO> floorPrhMap) {
  		int floorNum = 0;
  		applyFloor = applyFloor.trim();
  		
  		// ���� �������� �����Ͱ� �����ϸ� �Ǹ����� ���ڿ��� ���۹��ڿ��� ó���Ѵ�.
  		if(applyFloor.indexOf("~") > -1 || applyFloor.indexOf("-") > -1) {
  			applyFloor = applyFloor.replaceAll("-", "~");
  			String mApplyFloor[] = applyFloor.split("~");
  			applyFloor = mApplyFloor[0].trim();
  		}

  		// ���� ���� ���ذ��� ������ ������ �̹� ���ε� ���� �����Ѵ�.
        if(floorPrhMap.containsKey(applyFloor)) {
        	floorNum = floorPrhMap.get(applyFloor).getFLOORNUM();
      		return floorNum;
        }

  		// ���� ���� ���� ������ ���� ��
  		if(applyFloor.equals("F")) {
  			floorNum = 3;
  		} else if(applyFloor.equals("L")) {
  			// L: �κ�
  			floorNum = 0;
  		} else {
  			// ��, F ������ ����� �����ϰ� ó��
  			applyFloor = applyFloor.replaceAll("��", "");
  			applyFloor = applyFloor.replaceAll("F", "");
  			if(applyFloor.indexOf("B") > -1) {
  				// ������ ǥ��
  				////System.out.println("���ڸ�:"+applyFloor.substring(0, 1));
  				////System.out.println("���ڸ�:"+applyFloor.substring(1));
  				floorNum = new Integer("-"+applyFloor.substring(1)).intValue();
  			} else {
  				////System.out.println("���ڸ�:"+applyFloor);
  				floorNum = new Integer(applyFloor).intValue() - 1;
  			}
  		}
  		
  		return floorNum;
    }

 	/**
 	 * 
 	 * ������ �Է°��� �Ľ��ؼ� ������ ����, ������ ���� �����Ѵ�. ������ ����
 	 */
	private void getRangeFloorNmSeqn(String applyFloor, List<FloorVO> floorNmList, Map<String, Integer> startMap, Map<String, FloorNmVO> floorPrhMap) {
		
        // ���� ���� ���� Ư�����ڷ� -������ ǥ�ŵȰ�� ~�� �����ؼ� ó��
    	applyFloor =  applyFloor.replaceAll("-", "~");
    	String mApplyFloor[] = applyFloor.split("~");
 		int startNumFrom = startMap.get("startNumFrom");
    	if(mApplyFloor.length == 2) {
        	int iCharFloorFrom = getSingleFloorNmMap(mApplyFloor[0], floorPrhMap);
        	int floorNumTo = getSingleFloorNmMap(mApplyFloor[1], floorPrhMap);
        	for(int i=iCharFloorFrom; i <= floorNumTo; i++) {
        		FloorVO floorVO = new FloorVO();
            	if(startNumFrom >= 0) {
            		// ������ ����
            		int floor = startNumFrom;
            		floorVO.setiFloor(floor);
            		floorVO.setFloor(new Integer(floor+1).toString());
            		floorNmList.add(floorVO);
            	} else {
            		// ������ ����
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
    	// ���� ������ ���� �����Ѵ�. -> �ᱹ ����, �ε������� �����Ѵ�.
    	startMap.put("startNumFrom", startNumFrom);
    }

 	/**
 	 * 
 	 * ������ �Է°��� �Ľ��ؼ� ������ ����, ������ ���� �����Ѵ�.
 	 */
	private void getRangeFloorNm(String applyFloor, List<FloorVO> floorNmList, Map<String, FloorNmVO> floorPrhMap, Map<Integer, String> floorKeyMap ) {
        // ���� ���� ���� Ư�����ڷ� -������ ǥ�ŵȰ�� ~�� �����ؼ� ó��
    	applyFloor =  applyFloor.replaceAll("-", "~");
    	String mApplyFloor[] = applyFloor.split("~");
        
    	// from to ������ ó���� �Ǿ�� �ϴ±�
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
                		// ������ ����
                		int floor = i;
                		floorVO.setiFloor(floor);
                		floorVO.setFloor(new Integer(floor+1).toString());
                		floorVO.setFloorNm(new Integer(floor+1).toString());
                		floorNmList.add(floorVO);
                	} else {
                		// ������ ����
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
 	 * ������ ��°� �� �������� ���ؼ��� ~ ���������� ��ȯ�ϴ� �Լ�
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
        // �׷�ǥ�� �� ~ �� �������.
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
	 * ������ �Է°��� �Ľ��ؼ� ����, ���������� �����Ѵ�.
	 */
	private String getFloor(int iFloor) {
		String floor = "";
    	if(iFloor >= 0) {
    		floor = new Integer(iFloor+1).toString();
    	} else {
    		// ������ ����
    		String floorTmp = new Integer(iFloor).toString();
    		floor = "B"+floorTmp.substring(1);
    	}
		return floor;
	}

}
