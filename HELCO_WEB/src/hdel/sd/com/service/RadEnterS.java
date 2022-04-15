package hdel.sd.com.service;

import org.springframework.stereotype.Service;

import hdel.lib.exception.BizException;

import org.apache.log4j.Logger;

/**
 * 리모델링용 출입구 의장도 서비스 클래스
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
public class RadEnterS {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * Base Plate 적용 가로, 세로 계산
	 * Base Plate 적용 전제 조건: 표시기별 실측 데이터가 존재해야 한다.
	 * 홀버튼(HPB, HIP) 노출형은 무조건 적용
	 * 홀랜턴는 모델별로 적용
	 * 소방스위치는 모든 모델 적용
	 * HPI는 모델별로 적용
	 * return Basee Plate 적용된 가로,세로
	 * * @throws SQLException 
	 */
    public String[] getCalBasePlate(String suvWith, String suvHeight, String prdWith, String prdHeight
    		                        ,String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input suvWith:"+suvWith+", suvHeight:"+suvHeight+", prdWith:"+prdWith+", prdHeight:"+prdHeight);
    	// 0: 가로, 1: 세로
    	String[] rslt = new String[2];
        float calWith = new Float(suvWith).floatValue();
        float calHeight = new Float(suvHeight).floatValue();
        int[] chiPlate = getchoiceBasePlateAddValue(ELR_E_IDTP, ELC_E_BP_TYPE);
        int baseAddWith   = chiPlate[0]; // 실측 bpase add 가로 
        int baseAddHeight = chiPlate[1]; // 실측 bpase add 세로
        int prdAddWith    = chiPlate[2]; // 제품 bpase add 가로
        int prdAddHeight  = chiPlate[3]; // 제품 bpase add 세로

        // 리모델링 실측치 가로, 세로 베이스 플레이트 반올림값 적용
        if("P".equals(ELC_E_BP_TYPE)) {
            calWith   = getPlateRoundCalWith(calWith, prdAddWith);
            calHeight = getPlateRoundCalHeight(calHeight, prdAddHeight);
        } else {
            calWith   = getPlateRoundCalWith(calWith, baseAddWith);
            calHeight = getPlateRoundCalHeight(calHeight, baseAddHeight);
        }
        
        int tmp = 0;
        // 가로 데이터 생성
        if(calWith%1 == 0) {
        	tmp = new Float(calWith).intValue();
        	rslt[0] = new Integer(tmp).toString(); 
        } else {
        	rslt[0] = new Float(calWith).toString();
        }
        
        // 세로 데이터 생성
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	rslt[1] = new Integer(tmp).toString(); 
        } else {
        	rslt[1] = new Float(calHeight).toString();
        }

        logger.debug("out:"+rslt);
        
        return rslt;
    }

	/**
	 * 
	 * Base Plate 적용 가로, 세로 계산
	 * Base Plate 적용 전제 조건: 표시기별 실측 데이터가 존재해야 한다.
	 * 홀버튼(HPB, HIP) 노출형은 무조건 적용
	 * 홀랜턴는 모델별로 적용
	 * 소방스위치는 모든 모델 적용
	 * HPI는 모델별로 적용
	 * return Basee Plate 적용된 가로,세로
	 * * @throws SQLException 
	 */
    public String[] getCalBasePlateAdd(String suvWith, String suvHeight, String prdWith, String prdHeight
    		                        ,String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input suvWith:"+suvWith+", suvHeight:"+suvHeight+", prdWith:"+prdWith+", prdHeight:"+prdHeight);
    	// 0: 가로, 1: 세로
    	String[] rslt = new String[2];
        float calWith = new Float(suvWith).floatValue();
        float calHeight = new Float(suvHeight).floatValue();
        float fPrdWith = new Float(prdWith).floatValue();
        float fPrdHeight = new Float(prdHeight).floatValue();
        
        int[] chiPlate = getchoiceBasePlateAddValue(ELR_E_IDTP, ELC_E_BP_TYPE);
        int baseAddWith   = chiPlate[0]; // 실측 bpase add 가로 
        int baseAddHeight = chiPlate[1]; // 실측 bpase add 세로
        int prdAddWith    = chiPlate[2]; // 제품 bpase add 가로
        int prdAddHeight  = chiPlate[3]; // 제품 bpase add 세로

        // 리모델링 실측치 가로, 세로 베이스 플레이트 반올림값 적용
        calWith   = getPlateRoundCalWith(calWith, baseAddWith);
        calHeight = getPlateRoundCalHeight(calHeight, baseAddHeight);

        // 제품 베이스플레이트 추가값 적용된 가로, 세로
        fPrdWith = fPrdWith + prdAddWith;
        fPrdHeight = fPrdHeight + prdAddHeight;
        
        // 베이스플레이트 적용 가로, 제품 베이스플레이트 적용 가로 비교
        if(fPrdWith > calWith) {
        	calWith = fPrdWith;
        }
        
        // 베이스플레이트 적용 세로, 제품 베이스플레이트 적용 세로 비교
        if(fPrdHeight > calHeight) {
        	calHeight = fPrdHeight;
        }
        
        int tmp = 0;
        // 가로 데이터 생성
        if(calWith%1 == 0) {
        	tmp = new Float(calWith).intValue();
        	rslt[0] = new Integer(tmp).toString(); 
        } else {
        	rslt[0] = new Float(calWith).toString();
        }
        
        // 세로 데이터 생성
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	rslt[1] = new Integer(tmp).toString(); 
        } else {
        	rslt[1] = new Float(calHeight).toString();
        }
        
        return rslt;
    }
    
	/**
	 * 
	 * 파킹키 적용 Base Plate 적용 가로, 세로 계산
	 * 파킹키 적용 Base Plate 적용 전제 조건: 표시기별 실측 데이터가 존재해야 한다.
	 * 홀버튼(HPB, HIP) 노출형은 무조건 적용
	 * 홀랜턴는 모델별로 적용
	 * 소방스위치는 모든 모델 적용
	 * HPI는 모델별로 적용
	 * return Basee Plate 적용된 가로,세로
	 * * @throws SQLException 
	 */
    public String[] getCalPksBasePlate(String suvWith, String suvHeight, String suvF, String pksA, String pksB, String pksF, String prdWith, String prdHeight
    		                        ,String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input suvWith:"+suvWith+", suvHeight:"+suvHeight+", prdWith:"+prdWith+", prdHeight:"+prdHeight+", suvF:"+suvF+", pksA:"+pksA+", pksF:"+pksF);
        // 0: 가로, 1: 세로
    	String[] rslt = new String[2];
        float calWith = new Float(suvWith).floatValue();
        float calHeight = new Float(suvHeight).floatValue();
        float fSuvF =  new Float(suvF).floatValue();  // 제품 F값
        
        // 파킹 키 실측값
        float fPksA = new Float(pksA).floatValue();
        float fPksB = new Float(pksB).floatValue();
        float fPksF = new Float(pksF).floatValue();

        int[] chiPlate = getchoiceBasePlateAddValue(ELR_E_IDTP, ELC_E_BP_TYPE);
        int baseAddWith   = chiPlate[0]; // 실측 bpase add 가로 
        int baseAddHeight = chiPlate[1]; // 실측 bpase add 세로
        int prdAddWith    = chiPlate[2]; // 제품 bpase add 가로
        int prdAddHeight  = chiPlate[3]; // 제품 bpase add 세로
        
        // 파킹키 적용된 가로값
        if(fPksA > calWith) {
        	calWith = fPksA;
        }
        
        //파킹 키 위 아래 위치에 따라 세로값 적용 식을 변경한다.
        if(fSuvF >= fPksF) {
        	// 파킹키가 아래에 있는 것으로 간주한다.
            calHeight = (fSuvF + calHeight) - fPksF;
        } else {
        	// 파킹키가 위에 있는 것을 간주한다.
            calHeight = (fPksF + fPksB) - fSuvF;
        }

        if("P".equals(ELC_E_BP_TYPE)) {
            // 파킹키가 적용된 실측 가로, 세로 사이즈로 베이스 플레이트 반올림 적용        
            calWith   = getPlateRoundCalWith(calWith, prdAddWith);
            calHeight = getPlateRoundCalHeight(calHeight, prdAddHeight);
        } else {
            // 파킹키가 적용된 실측 가로, 세로 사이즈로 베이스 플레이트 반올림 적용        
            calWith   = getPlateRoundCalWith(calWith, baseAddWith);
            calHeight = getPlateRoundCalHeight(calHeight, baseAddHeight);
        }

        int tmp = 0;
        // 가로 데이터 생성
        if(calWith%1 == 0) {
        	tmp = new Float(calWith).intValue();
        	rslt[0] = new Integer(tmp).toString(); 
        } else {
        	rslt[0] = new Float(calWith).toString();
        }
        
        // 세로 데이터 생성
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	rslt[1] = new Integer(tmp).toString(); 
        } else {
        	rslt[1] = new Float(calHeight).toString();
        }
        logger.debug("out:"+rslt);
        return rslt;
    }

	/**
	 * 
	 * 파킹키 적용 Base Plate 적용 가로, 세로 계산
	 * 파킹키 적용 Base Plate 적용 전제 조건: 표시기별 실측 데이터가 존재해야 한다.
	 * 홀버튼(HPB, HIP) 노출형은 무조건 적용
	 * 홀랜턴는 모델별로 적용
	 * 소방스위치는 모든 모델 적용
	 * HPI는 모델별로 적용
	 * return Basee Plate 적용된 가로,세로
	 * * @throws SQLException 
	 */
    public String[] getCalPksBasePlateAdd(String suvWith, String suvHeight, String suvF, String pksA, String pksB, String pksF, String prdWith, String prdHeight
    		                        ,String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input suvWith:"+suvWith+", suvHeight:"+suvHeight+", prdWith:"+prdWith+", prdHeight:"+prdHeight+", suvF:"+suvF+", pksA:"+pksA+", pksF:"+pksF);
        // 0: 가로, 1: 세로
    	String[] rslt = new String[2];
        float calWith = new Float(suvWith).floatValue();
        float calHeight = new Float(suvHeight).floatValue();
        float fSuvF =  new Float(suvF).floatValue();  // 제품 F값
        float fPrdWith = new Float(prdWith).floatValue();
        float fPrdHeight = new Float(prdHeight).floatValue();
        
        // 파킹 키 실측값
        float fPksA = new Float(pksA).floatValue();
        float fPksB = new Float(pksB).floatValue();
        float fPksF = new Float(pksF).floatValue();

        int[] chiPlate = getchoiceBasePlateAddValue(ELR_E_IDTP, ELC_E_BP_TYPE);
        int baseAddWith   = chiPlate[0]; // 실측 bpase add 가로 
        int baseAddHeight = chiPlate[1]; // 실측 bpase add 세로
        int prdAddWith    = chiPlate[2]; // 제품 bpase add 가로
        int prdAddHeight  = chiPlate[3]; // 제품 bpase add 세로
        
        // 파킹키 적용된 가로값
        if(fPksA > calWith) {
        	calWith = fPksA;
        }
        
        //파킹 키 위 아래 위치에 따라 세로값 적용 식을 변경한다.
        if(fSuvF >= fPksF) {
        	// 파킹키가 아래에 있는 것으로 간주한다.
            calHeight = (fSuvF + calHeight) - fPksF;
        } else {
        	// 파킹키가 위에 있는 것을 간주한다.
            calHeight = (fPksF + fPksB) - fSuvF;
        }
        
        // 파킹키가 적용된 실측 가로, 세로 사이즈로 베이스 플레이트 반올림 적용        
        calWith   = getPlateRoundCalWith(calWith, baseAddWith);
        calHeight = getPlateRoundCalHeight(calHeight, baseAddHeight);
        
        // 제품 베이스플레이트 추가값 적용된 가로, 세로
        fPrdWith = fPrdWith + prdAddWith;
        fPrdHeight = fPrdHeight + prdAddHeight;
        
        // 베이스플레이트 적용 가로, 제품 베이스플레이트 적용 가로 비교
        if(fPrdWith > calWith) {
        	calWith = fPrdWith;
        }
        
        // 베이스플레이트 적용 세로, 제품 베이스플레이트 적용 세로 비교
        if(fPrdHeight > calHeight) {
        	calHeight = fPrdHeight;
        }
        
        int tmp = 0;
        // 가로 데이터 생성
        if(calWith%1 == 0) {
        	tmp = new Float(calWith).intValue();
        	rslt[0] = new Integer(tmp).toString(); 
        } else {
        	rslt[0] = new Float(calWith).toString();
        }
        
        // 세로 데이터 생성
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	rslt[1] = new Integer(tmp).toString(); 
        } else {
        	rslt[1] = new Float(calHeight).toString();
        }
        
        return rslt;
    }
    
	/**
	 * 
	 * 장애인 홀버튼 높이 계산
	 * 적용대상: EL_AUSE : HC(장애),  EH(비상,장애), OH(전망,장애), BH(병원,장애), NH(누드,장애), HF(인화,장애)
	 * 높이 = F + B/2
	 * return 장애인용 계산된 홀버튼 높이
	 * * @throws SQLException 
	 */
    public String getNudeCalHeight(String suvF, String suvB) {
        logger.debug("input suvF:"+suvF+", suvB:"+suvB);
    	float calHeight = 0;
    	String sCalHeight = "";
    	calHeight =  (new Float(suvF).floatValue()) + (new Float(suvB).floatValue())/2;
    	// 세로 데이터 생성
    	int tmp = 0;
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	sCalHeight = new Integer(tmp).toString(); 
        } else {
        	sCalHeight = new Float(calHeight).toString();
        }
        logger.debug("out sCalHeight:"+sCalHeight);
    	return sCalHeight;
    }

	/**
     * PLATE 적용 타입: H	수평형 베이스 플레이트, V	수직형 베이스 플레이트, P	플레이트 미적용 - 제품사이즈로 대체, N	플레이트 미적용
	 * 표시기 구분별 PLATE ADD 가로, 세로와 제품 ADD 가로, 세로 값을 선택하여 전달한다.
	 * @param floate with
	 * @return chiPlate[0]: plate add 가로, chiPlate[1]: plate add 세로, chiPlate[2]: 제품 add 가로, chiPlate[3]: 제품 add 세로
	 */
    private int[] getchoiceBasePlateAddValue(String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input ELR_E_IDTP:"+ELR_E_IDTP+", ELC_E_BP_TYPE:"+ELC_E_BP_TYPE);
    	int[] chiPlate = new int[4];
    	/*
    	PLATE 적용 타입                        : 플레이트 추가 가로: 플레이트 추가 세로: 제품추가 가로: 제품추가 세로
    	수직형 홀버튼  HPB,HIP,소방스위치,HPI    10                  80                  10             60
    	수직형 홀랜턴                            20                  80                  10             60
    	수평형 HPI                               20                  80                  20             80
    	*/
        int[] type1 = { 10, 80, 10, 60};
        int[] type2 = { 20, 80, 10, 60};
        //int[] type3 = { 20, 80, 20, 80};
        int[] type3 = { 80, 20, 80, 20};
        // ELR_E_IDTP: 표시기 구분 HPB(승장버튼), HIP(수평위치표시기), HPI(수평위치표시기), PKS	PARKING S/W, HLT	홀랜턴, FSW	소방스위치
        // ELC_E_BP_TYPE PLATE 적용 타입: H	수평형 베이스 플레이트, V	수직형 베이스 플레이트, P	플레이트 미적용 - 제품사이즈로 대체, N	플레이트 미적용
        if( "V".equals(ELC_E_BP_TYPE)) {
            if("HPB".equals(ELR_E_IDTP) || "HIP".equals(ELR_E_IDTP) || "PKS".equals(ELR_E_IDTP) || "HPI".equals(ELR_E_IDTP) || "HPBCIRCLE".equals(ELR_E_IDTP) || "FSW".equals(ELR_E_IDTP)) {
                logger.debug("out type1:"+type1);
            	return type1; 
            } else if("HPB".equals(ELR_E_IDTP)) {
                logger.debug("out type2:"+type1);
            	return type2;
            } else {
	            throw new BizException("제품규격 기준데이터를 확인하십시요. 영업기술 담당자에 문의하십시요.");
            }
        } else if( "H".equals(ELC_E_BP_TYPE)) {
        	if("HPI".equals(ELR_E_IDTP)) {
                logger.debug("out type3:"+type1);
            	return type3;
        	} else {
	            throw new BizException("제품규격 기준데이터를 확인하십시요. 영업기술 담당자에 문의하십시요.");
        	}
        }
    	return chiPlate;
    }
    
	/**
	 * 
	 * 베이스 프레이트 반올림 적용된 with
	 * @param floate with
	 * @return floate with
	 */
    private float getPlateRoundCalWith(float with, int plateWith) {
    	float calWith = 0L;
    	String sWith = new Float(with).toString();
        String tmp1 = "";
        String tmp2 = "";
        int    index = sWith.lastIndexOf("."); 
    	if(with > 10) {
    		tmp1 = sWith.substring(index-1, index);
    		tmp2 = sWith.substring(0, index-1)+"0"+sWith.substring(index);
    		if("1".equals(tmp1)||"0".equals(tmp1)) {
    			tmp1 = "0";
    		} else if("2".equals(tmp1) || "3".equals(tmp1) || "4".equals(tmp1)|| "5".equals(tmp1)|| "6".equals(tmp1)) {
    			tmp1 = "5";
    		} else if("7".equals(tmp1) || "8".equals(tmp1) || "9".equals(tmp1)) {
    			tmp1 = "10";
    	    }
			calWith = new Float(tmp2).floatValue() + new Float(tmp1).floatValue() + plateWith;
        	return calWith;
    	} else {
    		return with + plateWith;
    	}
    }

	/**
	 * 
	 * 베이스 프레이트 반올림 적용된 height
	 * @param String btnf, String btnb, String pswf
	 * @return floate height
	 */
    private float getPlateRoundCalHeight(float height, int plateHeight) {
    	float calHeight = 0L;
    	String sHeight = new Float(height).toString();
        String tmp1 = "";
        String tmp2 = "";
        int    index = sHeight.lastIndexOf("."); 
    	if(height > 10) {
    		tmp1 = sHeight.substring(index-1, index);
    		tmp2 = sHeight.substring(0, index-1)+"0"+sHeight.substring(index);
    		if("0".equals(tmp1)) {
    			tmp1 = "0";
    		} else {
    			tmp1 = "10";
    		}
			calHeight = new Float(tmp2).floatValue() + new Float(tmp1).floatValue() + plateHeight;
        	return calHeight;
    	} else {
    		return height + plateHeight;
    	}
    }

}
