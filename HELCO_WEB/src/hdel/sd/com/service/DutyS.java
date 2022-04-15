package hdel.sd.com.service;

import hdel.lib.exception.BizException;
import hdel.lib.exception.NoMatchException;
import hdel.lib.exception.RequireException;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.DutyD;
import hdel.sd.com.domain.Duty;
import hdel.sd.com.domain.DutyDet;
import hdel.sd.com.domain.DutyOpr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tobesoft.platform.data.Dataset;

@Service
public class DutyS extends SrmService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private DutyD dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(DutyD.class);
	}


	public HashMap genSpec(String mandt, String gtype, String blockgbn, HashMap mapInput, String lang) {
		// Output Data Map
		HashMap mapOut = new HashMap();

		// Input data정보
		HashMap mapWork = (HashMap) mapInput.clone();
		
    	//20190420 spras추가 by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		// Duty정보 읽어오기 (블럭별로)
		List<Duty> listDuty = dao.selectDuty(mandt, gtype, blockgbn, spras);

		// Duty가 존재하지 않는 경우 메세지 처리
		if ( listDuty == null )	{
			throw new BizException("No Data Duty Master");
		}
		
		for (int i=0; i<listDuty.size(); i++) {
			Duty duty = listDuty.get(i);
			int specMaxIdx = getSpecMaxIdx( duty );	// 사양체크항목 갯수 가져오기
			
			// 필수 체크 및 기본값 확인
			try	{
				// 2012.12.27 메세지 LANG추가 
				duty.setLANG("3");

				if (!"ko".equals(lang))		{
					duty.setLANG("E");
				}

				// 상해작은 3으로 정보가 저장되어 있음

				if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )	{
					duty.setLANG("3");
				}

				validateDuty(duty, mapWork, mapOut, specMaxIdx);
			}catch	( RuntimeException e )	{
				throw e;
			}
			
			// Duty Detail 정보 읽어오기
			List<DutyDet> listDutyD = dao.selectListDutyD(duty.getMANDT(), duty.getBLOCKNO());

			if ( listDutyD == null )	{
				throw new BizException("No Data Duty Detail");
			}

			// 사양전개
			int matchIdx = getMatchIdx(duty, mapWork, specMaxIdx, listDutyD);
			if (-1 == matchIdx) {
				logger.error(mapWork.toString());
				throw new NoMatchException(duty.getBLOCKNM());	// 사양전개 후 match 없는 경우 에러
			}
	
			int outMaxIdx = getOutMaxIdx( duty );	// 사양Out항목 갯수 가져오기
			
			// 사양 산출항목 생성
			getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
		}
		
		return mapOut;
	}

	// 2013.01.08 설계뿌의 제품으로 
	public HashMap stdGenSpec(String mandt, String gtype, String blockgbn, HashMap mapInput, String lang) {
		// Output Data Map
		HashMap mapOut = new HashMap();

		// Input data정보
		HashMap mapWork = (HashMap) mapInput.clone();
		
    	//20190420 spras추가 by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		// Duty정보 읽어오기 (블럭별로)
		List<Duty> listDuty = dao.selectDuty(mandt, gtype, blockgbn, spras);

		// Duty가 존재하지 않는 종료 처리
		if ( listDuty == null )	{
			return null;
		}
		
		for (int i = 0; i < listDuty.size(); i++) {
			Duty duty = listDuty.get(i);
			int specMaxIdx = getSpecMaxIdx( duty );	// 사양체크항목 갯수 가져오기

			// 필수 체크 및 기본값 확인
			try	{
				duty.setLANG("3");

				if (!"ko".equals(lang))		{
					duty.setLANG("E");
				}

				// 상해작은 3으로 정보가 저장되어 있음
				if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )	{
					duty.setLANG("3");
				}

				validateDuty(duty, mapWork, mapOut, specMaxIdx);
			}catch	( RuntimeException e )	{
				throw e;
			}
			
			// Duty Detail 정보 읽어오기
			List<DutyDet> listDutyD = dao.selectListDutyD(duty.getMANDT(), duty.getBLOCKNO());

			if ( listDutyD == null )	{
				return null;
			}

			// 사양전개
			int matchIdx = getMatchIdx(duty, mapWork, specMaxIdx, listDutyD);
			if (-1 == matchIdx) {
				logger.error(mapWork.toString());
				throw new NoMatchException(duty.getBLOCKNM());	// 사양전개 후 match 없는 경우 에러
			}
	
			int outMaxIdx = getOutMaxIdx( duty );	// 사양Out항목 갯수 가져오기
			
			// 사양 산출항목 생성
			getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
		}
		
		return mapOut;
	}

	// 2013.03.06 종속성 처리용 (표준Template 호출 시 처리로직과 분리)
	public HashMap dependSpec(String mandt, String gtype, String blockgbn, HashMap mapInput, String lang) {
		// Output Data Map
		HashMap mapOut = new HashMap();

		// Input data정보
		HashMap mapWork = (HashMap) mapInput.clone();
		
    	//20190420 spras추가 by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		// Duty정보 읽어오기 (블럭별로)
		List<Duty> listDuty = dao.selectDuty(mandt, gtype, blockgbn, spras);

		// Duty가 존재하지 않는 종료 처리
		if ( listDuty == null )	{
			return null;
		}
		
		for (int i = 0; i < listDuty.size(); i++) {
			Duty duty = listDuty.get(i);
			int specMaxIdx = getSpecMaxIdx( duty );	// 사양체크항목 갯수 가져오기

			// 필수 체크 및 기본값 확인
			try	{
				duty.setLANG("3");

				if (!"ko".equals(lang))		{
					duty.setLANG("E");
				}

				// 상해작은 3으로 정보가 저장되어 있음
				if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )	{
					duty.setLANG("3");
				}

				validateDuty(duty, mapWork, mapOut, specMaxIdx);
			}catch	( RuntimeException e )	{
				throw e;
			}
			
			// Duty Detail 정보 읽어오기
			List<DutyDet> listDutyD = dao.selectListDutyD(duty.getMANDT(), duty.getBLOCKNO());

			if ( listDutyD == null )	{
				return null;
			}

			// 사양전개
			int matchIdx = getMatchIdx(duty, mapWork, specMaxIdx, listDutyD);
			if (-1 == matchIdx) {
				logger.error(mapWork.toString());
				throw new NoMatchException(duty.getBLOCKNM());	// 사양전개 후 match 없는 경우 에러
			}
	
			int outMaxIdx = getOutMaxIdx( duty );	// 사양Out항목 갯수 가져오기
			
			// 사양 산출항목 생성
			getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
		}
		
		return mapOut;
	}

	// 사양SPEC저장 시 필수 입력값 체크 로직
	// 견적 Q, 계약 P
	public boolean genSpecCheck(String mandt, String flag, String item, Dataset template, Dataset log, String lang) {
		boolean rtnValue = true;

		HashMap mapInput = new HashMap();
        
		String sItem = "";
		int    cnt   = 0;
		
		if ("Q".equals(flag))	{
			for (int i = 0; i < template.getRowCount(); i++) {
				sItem = template.getColumnAsString(i, "QTSEQ");
				if(!item.equals(sItem))	{
					continue;
				}

				String value = template.getColumnAsString(i, "VALUE");	// 견적은 PRD -> VALUE
				if (null != value && ! "".equals(value)) {
					mapInput.put(template.getColumnAsString(i, "ATNAM"), value);	// 견적은 PRH -> ATNAM
				}
			}
		}else	{
			sItem = template.getColumnAsString(0, "HOGI");

			for (int i = 0; i < template.getRowCount(); i++) {

				if(!item.equals(sItem))	{
					continue;
				}

				String value = template.getColumnAsString(i, "PRD");
				if (null != value && ! "".equals(value)) {
					mapInput.put(template.getColumnAsString(i, "PRH"), value);
				}
			}
		}

		// Output Data Map
		HashMap mapOut = new HashMap();

		// Input data정보
		HashMap mapWork = (HashMap) mapInput.clone();

		String gtype = "";
		String sGubun = "";

		// 2013.01.29 Duty 안 타는 조건 추가
		String sGubun1 = (String) mapWork.get("EL_DITEMUSE");
		String sGubun2 = (String) mapWork.get("EL_ASPLY");
		String sGubun3 =  template.getColumnAsString(0, "PSPID");
		if ( sGubun1 == null ) sGubun1 = "";
		if ( sGubun2 == null ) sGubun2 = "";
		if ( sGubun1.equals("Y") || sGubun2.equals("Y") ) {
			return true;
		}
		if (sGubun3.equals("126394")){ //20131030 kt 청진빌딩 비표준으로 필수체크 제외 -김선호
			return true;
		}

		sGubun = (String) mapWork.get("EL_ATYP");
		if (sGubun != null && !"".equals(sGubun))	{
			gtype = "ELV1";

			// 선박용 처리
			if ("STSH1".equals(sGubun) || "STSH5".equals(sGubun))	{
				gtype = "ELV2";
			}
		}else {
			// 에스컬러에터
			sGubun = (String) mapWork.get("ES_ATYP");
			if (sGubun != null && !"".equals(sGubun))	{
				gtype = "ESC1";	// 에스컬레이터는 Default로 처리
			}else {
				// 무빙워크
				sGubun = (String) mapWork.get("MW_ATYP");
				if (sGubun != null && !"".equals(sGubun))	{
					gtype = "MWK1";	// 무빙워크는 Default로 처리
				} else {
					sGubun = (String) mapInput.get("AP_ATYP");
					if (sGubun != null && !"".equals(sGubun))	{
						gtype = "ZPK1";	// 주차 Default로 처리
					} else	{	// 2013.01.03 이외에는 필수 체크 불필요
						return true;
					}
				}
			}
		}
		

		if (null == gtype || "".equals(gtype))	 {
			if (!"ko".equals(lang))		{
				throw new RequireException("TYPE");
			}else	{
				throw new RequireException("기종");
			}
		}

		//gtype = gtype + "_CHECK";
    	//20190420 spras추가 by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";

		// Duty정보 읽어오기 (블럭별로)
		List<Duty> listDuty = dao.selectDuty(mandt, gtype, "", spras);

		// Duty가 존재하지 않는 경우 메세지 처리
		if ( listDuty == null || listDuty.size() == 0)	{
			rtnValue = false;
			//throw new BizException("No Data Duty Master");
		}else	{

			for (int i = 0; i < listDuty.size(); i++) {
				Duty duty = listDuty.get(i);
	
				int specMaxIdx = getSpecMaxIdx( duty );	// 사양체크항목 갯수 가져오기
				
				// 필수 체크 및 기본값 확인
				try	{
					duty.setLANG("3");
	
					if (!"ko".equals(lang))		{
						duty.setLANG("E");
					}

					// 상해작은 3으로 정보가 저장되어 있음
					if ("SHN_01".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )	{
						duty.setLANG("3");
					}
	
					//validateDutyCheck(duty, mapWork, specMaxIdx); 필수 체크에서 입력 범위 체크 변경에 따라 불필요
					validateDuty(duty, mapWork, mapOut, specMaxIdx);
				}catch	( RuntimeException e )	{
					e.printStackTrace();
					rtnValue = false;
					throw e;
				}
				
				// Duty Detail 정보 읽어오기
				List<DutyDet> listDutyD = dao.selectListDutyD(duty.getMANDT(), duty.getBLOCKNO());

				if ( listDutyD != null && listDutyD.size() > 0 )	{
					// 사양전개
					int matchIdx = getMatchIdx(duty, mapWork, specMaxIdx, listDutyD);
					if (-1 == matchIdx) {
						logger.error(mapWork.toString());
						//throw new NoMatchException(duty.getBLOCKNM());	// 사양전개 후 match 없는 경우 에러   -임시 김선호 201504

						log.appendRow();
						log.setColumn(cnt, "IDX", cnt + 1);
						log.setColumn(cnt, "LOGMSG",  "[" + item + "]: " + duty.getBLOCKNM());
						cnt++;
						
					} else {

						int outMaxIdx = getOutMaxIdx( duty );	// 사양Out항목 갯수 가져오기
					
						// 사양 산출항목 생성
						getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
					}
				}
			}
		}
		
		return rtnValue;
	}

	// HashMap mapWork 은 in/out 처리
	// HashMap mapOut  은 in/out 처리
	private void validateDuty(Duty duty, HashMap mapWork, HashMap mapOut, int specMaxIdx) {
		String value = "";
		
		if (0 == specMaxIdx) return;

		try	{
		
			if (1 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC1());
	
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS1())) {
						errorRaise(duty, duty.getSPEC1());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV1())) {
						mapOut.put(duty.getSPEC1(), duty.getSPECDEFV1());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC1(), duty.getSPECDEFV1());
					}
				}
			}
			if (2 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC2());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS2())) {
						errorRaise(duty, duty.getSPEC2());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV2())) {
						mapOut.put(duty.getSPEC2(), duty.getSPECDEFV2());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC2(), duty.getSPECDEFV2());
					}
				}
			}
			if (3 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC3());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS3())) {
						errorRaise(duty, duty.getSPEC3());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV3())) {
						mapOut.put(duty.getSPEC3(), duty.getSPECDEFV3());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC3(), duty.getSPECDEFV3());
					}
				}
			}
			if (4 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC4());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS4())) {
						errorRaise(duty, duty.getSPEC4());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV4())) {
						mapOut.put(duty.getSPEC4(), duty.getSPECDEFV4());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC4(), duty.getSPECDEFV4());
					}
				}
			}
			if (5 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC5());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS5())) {
						errorRaise(duty, duty.getSPEC5());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV5())) {
						mapOut.put(duty.getSPEC5(), duty.getSPECDEFV5());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC5(), duty.getSPECDEFV5());
					}
				}
			}
			if (6 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC6());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS6())) {
						errorRaise(duty, duty.getSPEC6());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV6())) {
						mapOut.put(duty.getSPEC6(), duty.getSPECDEFV6());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC6(), duty.getSPECDEFV6());
					}
				}
			}
			if (7 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC7());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS7())) {
						errorRaise(duty, duty.getSPEC7());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV7())) {
						mapOut.put(duty.getSPEC7(), duty.getSPECDEFV7());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC7(), duty.getSPECDEFV7());
					}
				}
			}
			if (8 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC8());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS8())) {
						errorRaise(duty, duty.getSPEC8());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV8())) {
						mapOut.put(duty.getSPEC8(), duty.getSPECDEFV8());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC8(), duty.getSPECDEFV8());
					}
				}
			}
			if (9 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC9());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS9())) {
						errorRaise(duty, duty.getSPEC9());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV9())) {
						mapOut.put(duty.getSPEC9(), duty.getSPECDEFV9());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC9(), duty.getSPECDEFV9());
					}
				}
			}
			if (10 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC10());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS10())) {
						errorRaise(duty, duty.getSPEC10());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV10())) {
						mapOut.put(duty.getSPEC10(), duty.getSPECDEFV10());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC10(), duty.getSPECDEFV10());
					}
				}
			}
			if (11 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC11());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS11())) {
						errorRaise(duty, duty.getSPEC11());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV11())) {
						mapOut.put(duty.getSPEC11(), duty.getSPECDEFV11());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC11(), duty.getSPECDEFV11());
					}
				}
			}
			if (12 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC12());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS12())) {
						errorRaise(duty, duty.getSPEC12());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV12())) {
						mapOut.put(duty.getSPEC12(), duty.getSPECDEFV12());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC12(), duty.getSPECDEFV12());
					}
				}
			}
			if (13 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC13());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS13())) {
						errorRaise(duty, duty.getSPEC13());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV13())) {
						mapOut.put(duty.getSPEC13(), duty.getSPECDEFV13());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC13(), duty.getSPECDEFV13());
					}
				}
			}
			if (14 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC14());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS14())) {
						errorRaise(duty, duty.getSPEC14());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV14())) {
						mapOut.put(duty.getSPEC14(), duty.getSPECDEFV14());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC14(), duty.getSPECDEFV14());
					}
				}
			}
			if (15 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC15());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS15())) {
						errorRaise(duty, duty.getSPEC15());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV15())) {
						mapOut.put(duty.getSPEC15(), duty.getSPECDEFV15());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC15(), duty.getSPECDEFV15());
					}
				}
			}
			if (16 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC16());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS16())) {
						errorRaise(duty, duty.getSPEC16());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV16())) {
						mapOut.put(duty.getSPEC16(), duty.getSPECDEFV16());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC16(), duty.getSPECDEFV16());
					}
				}
			}
			if (17 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC17());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS17())) {
						errorRaise(duty, duty.getSPEC17());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV17())) {
						mapOut.put(duty.getSPEC17(), duty.getSPECDEFV17());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC17(), duty.getSPECDEFV17());
					}
				}
			}
			if (18 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC18());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS18())) {
						errorRaise(duty, duty.getSPEC18());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV18())) {
						mapOut.put(duty.getSPEC18(), duty.getSPECDEFV18());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC18(), duty.getSPECDEFV18());
					}
				}
			}
			if (19 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC19());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS19())) {
						errorRaise(duty, duty.getSPEC19());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV19())) {
						mapOut.put(duty.getSPEC19(), duty.getSPECDEFV19());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC19(), duty.getSPECDEFV19());
					}
				}
			}
			if (20 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC20());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS20())) {
						errorRaise(duty, duty.getSPEC20());
					}
					// Duty에 사양기본값이 지정된 경우 기본값 적용
					if (! "".equals(duty.getSPECDEFV20())) {
						mapOut.put(duty.getSPEC20(), duty.getSPECDEFV20());	// Default 부분이 출력 되도록 추가 2012.10.12
						mapWork.put(duty.getSPEC20(), duty.getSPECDEFV20());
					}
				}
			}
		}catch	( RuntimeException e )	{
			throw e;
		}
	}

	// 필수체크 용도 활용(Default 값 처리 제외)
	private void validateDutyCheck(Duty duty, HashMap mapWork, int specMaxIdx) {
		String value = "";
		
		if (0 == specMaxIdx) return;

		try	{
		
			if (1 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC1());
	
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS1())) {
						errorRaise(duty, duty.getSPEC1());
					}
				}
			}
			if (2 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC2());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS2())) {
						errorRaise(duty, duty.getSPEC2());
					}
				}
			}
			if (3 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC3());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS3())) {
						errorRaise(duty, duty.getSPEC3());
					}
				}
			}
			if (4 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC4());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS4())) {
						errorRaise(duty, duty.getSPEC4());
					}
				}
			}
			if (5 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC5());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS5())) {
						errorRaise(duty, duty.getSPEC5());
					}
				}
			}
			if (6 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC6());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS6())) {
						errorRaise(duty, duty.getSPEC6());
					}
				}
			}
			if (7 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC7());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS7())) {
						errorRaise(duty, duty.getSPEC7());
					}
				}
			}
			if (8 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC8());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS8())) {
						errorRaise(duty, duty.getSPEC8());
					}
				}
			}
			if (9 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC9());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS9())) {
						errorRaise(duty, duty.getSPEC9());
					}
				}
			}
			if (10 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC10());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS10())) {
						errorRaise(duty, duty.getSPEC10());
					}
				}
			}
			if (11 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC11());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS11())) {
						errorRaise(duty, duty.getSPEC11());
					}
				}
			}
			if (12 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC12());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS12())) {
						errorRaise(duty, duty.getSPEC12());
					}
				}
			}
			if (13 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC13());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS13())) {
						errorRaise(duty, duty.getSPEC13());
					}
				}
			}
			if (14 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC14());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS14())) {
						errorRaise(duty, duty.getSPEC14());
					}
				}
			}
			if (15 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC15());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS15())) {
						errorRaise(duty, duty.getSPEC15());
					}
				}
			}
			if (16 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC16());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS16())) {
						errorRaise(duty, duty.getSPEC16());
					}
				}
			}
			if (17 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC17());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS17())) {
						errorRaise(duty, duty.getSPEC17());
					}
				}
			}
			if (18 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC18());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS18())) {
						errorRaise(duty, duty.getSPEC18());
					}
				}
			}
			if (19 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC19());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS19())) {
						errorRaise(duty, duty.getSPEC19());
					}
				}
			}
			if (20 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC20());
				// 사양 입력값이 없는 경우
				if (null == value || "".equals(value)) {
					// Duty에 사양값이 필수인 경우 에러 처리
					if ("X".equals(duty.getSPECESS20())) {
						errorRaise(duty, duty.getSPEC20());
					}
				}
			}
		}catch	( RuntimeException e )	{
			throw e;
		}
	}
	
	private int getMatchIdx(Duty duty, HashMap mapWork, int specMaxIdx, List<DutyDet> listDutyD) {
		int matchIdx = -1;
		
		// Duty에 Spec이 설정되지 않은 경우
		if (0 == specMaxIdx) {
			// Spec 리스트가 1건인 경우는 해당 단일건으로 산출
			if (1 == listDutyD.size()) return 0;
			// 이외에는 오류 (0 또는 다중은 처리 불가)
			else throw new BizException("No Spec & Multi Out Exception");
		}
		
		for (int i = 0; i < listDutyD.size(); i++) {
			boolean match = false;
			DutyDet dutyDet = listDutyD.get(i);
			
			if (1 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC1()), dutyDet.getSPEC1(), mapWork);
				if (! match) continue;
				if (1 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (2 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC2()), dutyDet.getSPEC2(), mapWork);
				if (! match) continue;
				if (2 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (3 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC3()), dutyDet.getSPEC3(), mapWork);
				if (! match) continue;
				if (3 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (4 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC4()), dutyDet.getSPEC4(), mapWork);
				if (! match) continue;
				if (4 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (5 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC5()), dutyDet.getSPEC5(), mapWork);
				if (! match) continue;
				if (5 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (6 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC6()), dutyDet.getSPEC6(), mapWork);
				if (! match) continue;
				if (6 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (7 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC7()), dutyDet.getSPEC7(), mapWork);
				if (! match) continue;
				if (7 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (8 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get( duty.getSPEC8()), dutyDet.getSPEC8(), mapWork);
				if (! match) continue;
				if (8 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (9 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get( duty.getSPEC9()), dutyDet.getSPEC9(), mapWork);
				if (! match) continue;
				if (9 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (10 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC10()), dutyDet.getSPEC10(), mapWork);
				if (! match) continue;
				if (10 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (11 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC11()), dutyDet.getSPEC11(), mapWork);
				if (! match) continue;
				if (11 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (12 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC12()), dutyDet.getSPEC12(), mapWork);
				if (! match) continue;
				if (12 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (13 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get( duty.getSPEC13()), dutyDet.getSPEC13(), mapWork);
				if (! match) continue;
				if (13 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (14 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC14()), dutyDet.getSPEC14(), mapWork);
				if (! match) continue;
				if (14 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (15 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC15()), dutyDet.getSPEC15(), mapWork);
				if (! match) continue;
				if (15 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (16 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC16()), dutyDet.getSPEC16(), mapWork);
				if (! match) continue;
				if (16 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (17 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC17()), dutyDet.getSPEC17(), mapWork);
				if (! match) continue;
				if (17 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (18 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get( duty.getSPEC18()), dutyDet.getSPEC18(), mapWork);
				if (! match) continue;
				if (18 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (19 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC19()), dutyDet.getSPEC19(), mapWork);
				if (! match) continue;
				if (19 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (20 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC20()), dutyDet.getSPEC20(), mapWork);
				if (! match) continue;
				if (20 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
		}
		return matchIdx;
	}

	// outValue 모듈화 변경
	private String getOutValue(String outName, Duty duty, String outDutyD, HashMap mapWork)	{

		String outValue = "";

		
		
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= outDutyD.indexOf("|")) {
				outValue = outDutyD.replace("|", "");
		    // 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) 1900 + {EL_ECCH}
			} else if (0 <= outDutyD.indexOf("+") ||
				0 <= outDutyD.indexOf("-") ||
				0 <= outDutyD.indexOf("*") ||
				0 <= outDutyD.indexOf("/")) {
				if ("ES".equals(outName.substring(0,2))){
					outValue = outDutyD;
				}else if("GTYPE".equals(outName)){
					outValue = outDutyD; //GTYPE OUT2 문자열'-'포함으로 인한 오류 정정 jss
				}else {
					if ("R/L".equals(outDutyD) || "R/R".equals(outDutyD)){
						// CAR GOVERNOR 위치 값은 R/L R/R -> 수식 계산으로 0 으로 출력되는 것을 방지 20141229 김선호
						outValue = outDutyD;
					}else{
						outValue = calc(duty, mapWork, outDutyD);
				    }
				}	
		    // 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= outDutyD.indexOf("#")) {
				outValue = calcFix( duty, mapWork, outDutyD);
		    // 산출값이 특성인 겅우, ex) {EL_ECWAD}
		    } else if (0 <= outDutyD.indexOf("{") ||
		    		0 <= outDutyD.indexOf("}") ) {
		    	outValue = (String) mapWork.get(outDutyD.replace("{", "").replace("}", ""));
		    } else {
		    	outValue = outDutyD;
		    }
		
		return outValue;
	}

	// HashMap mapWork 은 in/out 처리
	// HashMap mapOut  은 in/out 처리
	private void getOut(Duty duty, HashMap mapWork, HashMap mapOut, DutyDet dutyDet, int outMaxIdx) {
		String outName = "";
		String outValue = "";

		if (1 <= outMaxIdx) {

			outName = duty.getOUT1();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT1(), mapWork);

			/*
			outName = duty.getOUT1();
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT1().indexOf("|")) {
				outValue = dutyDet.getOUT1().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) 1900 + {EL_ECCH}
			} else if (0 <= dutyDet.getOUT1().indexOf("+") ||
				0 <= dutyDet.getOUT1().indexOf("-") ||
				0 <= dutyDet.getOUT1().indexOf("*") ||
				0 <= dutyDet.getOUT1().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT1());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT1().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT1());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT1().indexOf("{") ||
					   0 <= dutyDet.getOUT1().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT1().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT1();
			}
			*/

			// 2012.10.29 카자중의 경우 1의자리에서 무조건 올림 사용 1184.88 -> 1190으로 결과값처리
			// 처리 group -> 01, 산출 out1은 EL_ECW의 경우만 처리 ==> 카자중
			if("01".equals(duty.getBLOCKGBN()))	{
				if ("EL_ECW".equals(outName))	{
					BigDecimal bd = new BigDecimal(outValue);
					outValue = (String) (bd.setScale(-1, BigDecimal.ROUND_UP).intValue() + "");
				}
			}

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (2 <= outMaxIdx) {
			outName = duty.getOUT2();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT2(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT2().indexOf("|")) {
				outValue = dutyDet.getOUT2().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT2().indexOf("+") ||
				0 <= dutyDet.getOUT2().indexOf("-") ||
				0 <= dutyDet.getOUT2().indexOf("*") ||
				0 <= dutyDet.getOUT2().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT2());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT2().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT2());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT2().indexOf("{") ||
					   0 <= dutyDet.getOUT2().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT2().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT2();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (3 <= outMaxIdx) {
			outName = duty.getOUT3();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT3(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT3().indexOf("|")) {
				outValue = dutyDet.getOUT3().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT3().indexOf("+") ||
				0 <= dutyDet.getOUT3().indexOf("-") ||
				0 <= dutyDet.getOUT3().indexOf("*") ||
				0 <= dutyDet.getOUT3().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT3());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT3().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT3());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT3().indexOf("{") ||
					   0 <= dutyDet.getOUT3().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT3().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT3();
			}
			 */

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (4 <= outMaxIdx) {
			outName = duty.getOUT4();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT4(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT4().indexOf("|")) {
				outValue = dutyDet.getOUT4().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT4().indexOf("+") ||
				0 <= dutyDet.getOUT4().indexOf("-") ||
				0 <= dutyDet.getOUT4().indexOf("*") ||
				0 <= dutyDet.getOUT4().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT4());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT4().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT4());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT4().indexOf("{") ||
					   0 <= dutyDet.getOUT4().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT4().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT4();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (5 <= outMaxIdx) {
			outName = duty.getOUT5();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT5(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT5().indexOf("|")) {
				outValue = dutyDet.getOUT5().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT5().indexOf("+") ||
				0 <= dutyDet.getOUT5().indexOf("-") ||
				0 <= dutyDet.getOUT5().indexOf("*") ||
				0 <= dutyDet.getOUT5().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT5());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT5().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT5());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT5().indexOf("{") ||
					   0 <= dutyDet.getOUT5().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT5().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT5();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (6 <= outMaxIdx) {
			outName = duty.getOUT6();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT6(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT6().indexOf("|")) {
				outValue = dutyDet.getOUT6().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT6().indexOf("+") ||
				0 <= dutyDet.getOUT6().indexOf("-") ||
				0 <= dutyDet.getOUT6().indexOf("*") ||
				0 <= dutyDet.getOUT6().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT6());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT6().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT6());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT6().indexOf("{") ||
					   0 <= dutyDet.getOUT6().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT6().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT6();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (7 <= outMaxIdx) {
			outName = duty.getOUT7();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT7(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT7().indexOf("|")) {
				outValue = dutyDet.getOUT7().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT7().indexOf("+") ||
				0 <= dutyDet.getOUT7().indexOf("-") ||
				0 <= dutyDet.getOUT7().indexOf("*") ||
				0 <= dutyDet.getOUT7().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT7());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT7().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT7());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT7().indexOf("{") ||
					   0 <= dutyDet.getOUT7().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT7().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT7();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (8 <= outMaxIdx) {
			outName = duty.getOUT8();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT8(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT8().indexOf("|")) {
				outValue = dutyDet.getOUT8().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT8().indexOf("+") ||
				0 <= dutyDet.getOUT8().indexOf("-") ||
				0 <= dutyDet.getOUT8().indexOf("*") ||
				0 <= dutyDet.getOUT8().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT8());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT8().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT8());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT8().indexOf("{") ||
					   0 <= dutyDet.getOUT8().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT8().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT8();
			}
			*/
			
			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (9 <= outMaxIdx) {
			outName = duty.getOUT9();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT9(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT9().indexOf("|")) {
				outValue = dutyDet.getOUT9().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT9().indexOf("+") ||
				0 <= dutyDet.getOUT9().indexOf("-") ||
				0 <= dutyDet.getOUT9().indexOf("*") ||
				0 <= dutyDet.getOUT9().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT9());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT9().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT9());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT9().indexOf("{") ||
					   0 <= dutyDet.getOUT9().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT9().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT9();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (10 <= outMaxIdx) {
			outName = duty.getOUT10();
			// outValue 모듈화 변경.
			outValue = getOutValue(outName, duty, dutyDet.getOUT10(), mapWork);

			/*
			// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
			if (0 <= dutyDet.getOUT10().indexOf("|")) {
				outValue = dutyDet.getOUT10().replace("|", "");
			// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴
			} else if (0 <= dutyDet.getOUT10().indexOf("+") ||
				0 <= dutyDet.getOUT10().indexOf("-") ||
				0 <= dutyDet.getOUT10().indexOf("*") ||
				0 <= dutyDet.getOUT10().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT10());
			// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
			} else if (0 <= dutyDet.getOUT10().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT10());
			// 산출값이 특성인 겅우, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT10().indexOf("{") ||
					   0 <= dutyDet.getOUT10().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT10().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT10();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
	}
	
	private int getSpecMaxIdx(Duty duty) {
		int idx = 0;
		String spec = "";
		
		spec = duty.getSPEC1().trim();	if (! "".equals(spec)) idx = 1;
		spec = duty.getSPEC2().trim();	if (! "".equals(spec)) idx = 2;
		spec = duty.getSPEC3().trim();	if (! "".equals(spec)) idx = 3;
		spec = duty.getSPEC4().trim();	if (! "".equals(spec)) idx = 4;
		spec = duty.getSPEC5().trim();	if (! "".equals(spec)) idx = 5;
		spec = duty.getSPEC6().trim();	if (! "".equals(spec)) idx = 6;
		spec = duty.getSPEC7().trim();	if (! "".equals(spec)) idx = 7;
		spec = duty.getSPEC8().trim();	if (! "".equals(spec)) idx = 8;
		spec = duty.getSPEC9().trim();	if (! "".equals(spec)) idx = 9;
		spec = duty.getSPEC10().trim();	if (! "".equals(spec)) idx = 10;
		spec = duty.getSPEC11().trim();	if (! "".equals(spec)) idx = 11;
		spec = duty.getSPEC12().trim();	if (! "".equals(spec)) idx = 12;
		spec = duty.getSPEC13().trim();	if (! "".equals(spec)) idx = 13;
		spec = duty.getSPEC14().trim();	if (! "".equals(spec)) idx = 14;
		spec = duty.getSPEC15().trim();	if (! "".equals(spec)) idx = 15;
		spec = duty.getSPEC16().trim();	if (! "".equals(spec)) idx = 16;
		spec = duty.getSPEC17().trim();	if (! "".equals(spec)) idx = 17;
		spec = duty.getSPEC18().trim();	if (! "".equals(spec)) idx = 18;
		spec = duty.getSPEC19().trim();	if (! "".equals(spec)) idx = 19;
		spec = duty.getSPEC20().trim();	if (! "".equals(spec)) idx = 20;
		
		return idx;
	}
	
	private int getOutMaxIdx(Duty duty) {
		int idx = -1;
		String out = "";
		
		out = duty.getOUT1().trim();	if (! "".equals(out)) idx = 1;
		out = duty.getOUT2().trim();	if (! "".equals(out)) idx = 2;
		out = duty.getOUT3().trim();	if (! "".equals(out)) idx = 3;
		out = duty.getOUT4().trim();	if (! "".equals(out)) idx = 4;
		out = duty.getOUT5().trim();	if (! "".equals(out)) idx = 5;
		out = duty.getOUT6().trim();	if (! "".equals(out)) idx = 6;
		out = duty.getOUT7().trim();	if (! "".equals(out)) idx = 7;
		out = duty.getOUT8().trim();	if (! "".equals(out)) idx = 8;
		out = duty.getOUT9().trim();	if (! "".equals(out)) idx = 9;
		out = duty.getOUT10().trim();	if (! "".equals(out)) idx = 10;
		
		return idx;
	}
	
	/**
	 * 연산식 처리, 사칙연산만 가능 ( + - * / )
	 * @param expr
	 * @return
	 */
	private String calc(Duty duty, HashMap mapWork, String expr) {
		JexlEngine jexl = new JexlEngine();
		// 연산식 Parsing (연산인자 구분기호인 '{', '}'는 제외)
	    Expression e = jexl.createExpression(expr.replace("{", "").replace("}", ""));
	    
	    // 연산식에 포함된 특성코드를 발췌 ex 3300 * {EL_ASTQ}, {EL_EHO} + {EL_EHTRH} + {EL_EHP}
	    List listArgs = new ArrayList();
	    int pos = 0;
	    while (true) {
	    	// 더 이상 대상이 없을 경우 종료
			if (0 > expr.indexOf("{", pos)) break;
			
			int start = expr.indexOf("{", pos) + 1;
			int end = expr.indexOf("}", pos);
			
			listArgs.add(expr.substring(start, end).trim());
			
			pos = end + 1;
	    }
	    
	    // 연산인자 대입
	    JexlContext context = new MapContext();
	    for (int i=0; i<listArgs.size(); i++) {
	    	if (null == mapWork.get((String)listArgs.get(i)) || "".equals((String)mapWork.get((String)listArgs.get(i)))) errorRaise(duty, (String)listArgs.get(i));	// 계산식에 적용되는 값이 없는 경우 오류
	    	
	    	context.set((String)listArgs.get(i), mapWork.get((String)listArgs.get(i)));
	    }
	    
		return String.valueOf(e.evaluate(context));
	}
	
	/**
	 * 연산식 처리, 사칙연산 이외의 처리
	 * @param expr
	 * @return
	 */
	private String calcFix(Duty duty, HashMap mapWork, String expr) {
		if (null == expr || "".equals(expr.trim())) return "";
		
		// [MC]를 소수점 둘째자리부터 내린 후 + 0.1 (ex. 5.402356… → 5.5)
		if ("#MC1#".equals(expr)) {
			String mc = (String) mapWork.get("XC_MC");
			if (null == mc || "".equals(mc)) errorRaise(duty, "XC_MC");
		
			BigDecimal bd = new BigDecimal(mc);
			return String.valueOf(bd.setScale(1, BigDecimal.ROUND_FLOOR).add(new BigDecimal("0.1")));
		}
		// [MC]를 소수점 첫째자리에서 올림한 정수 값 (ex. 5.432356… → 6)
		if ("#MC2#".equals(expr)) {
			String mc = (String) mapWork.get("XC_MC");
			if (null == mc || "".equals(mc)) errorRaise(duty, "XC_MC");
			
			// 소수점 둘째자리이하 버림
			//float x = Float.parseFloat(mc);
			//x = x * 10;
			//x = (float) Math.floor((double)x);
			//x = x / 10;
			
			//BigDecimal bd = new BigDecimal((double)x);
			//return String.valueOf(bd.setScale(0, BigDecimal.ROUND_CEILING));
			//return String.valueOf((long) Math.ceil(Double.parseDouble(mc)));
			
			// 2012.11.01  소수점 2째자리 반올림 후 다시 소수점 첫째자리에서 반올림한 정수
			BigDecimal bd = new BigDecimal(mc);
			return String.valueOf( bd.setScale(1, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP) );
		}
		// CRCK
		if ("#CRCK#".equals(expr)) {
			String elEcrl = (String) mapWork.get("EL_ECRL");
			if (null == elEcrl || "".equals(elEcrl)) errorRaise(duty, "EL_ECRL");
			String crck = (String) mapWork.get("XC_CRCK");
			if (null == crck || "".equals(crck)) errorRaise(duty, "XC_CRCK");
			
			if (Float.parseFloat(elEcrl) >= Float.parseFloat(crck)) {
				return elEcrl;
			} else {
				return crck;
				//return (String) this.mapWork.get("XC_CR1");
			}
		}
		// CWRCK
		if ("#CWRCK#".equals(expr)) {
			String elEcwrl = (String) mapWork.get("EL_ECWRL");
			if (null == elEcwrl || "".equals(elEcwrl)) errorRaise(duty, "EL_ECWRL");
			String cwrck = (String) mapWork.get("XC_CWRCK");
			if (null == cwrck || "".equals(cwrck)) errorRaise(duty, "XC_CWRCK");
			
			if ("LXVF".equals(duty.getGTYPE()) || "STVF".equals(duty.getGTYPE())) {	// 2012.11.01 LXVF반영
				// 기종이 STVF인 경우에 EL_ECWRL값과 CWRCK값이 7,8인 경우는 7이 큰값임
				if ("8".equals(elEcwrl) && "7".equals(cwrck)) {
					return cwrck;
				} else if ("7".equals(elEcwrl) && "8".equals(cwrck)) {
					return elEcwrl;
				} else {
					if (Float.parseFloat(elEcwrl) >= Float.parseFloat(cwrck)) {
						return elEcwrl;
					} else {
						return cwrck;
						//return (String) this.mapWork.get("XC_CR2");
					}
				}
			} else {
				if (Float.parseFloat(elEcwrl) >= Float.parseFloat(cwrck)) {
					return elEcwrl;
				} else {
					return cwrck;
					//return (String) this.mapWork.get("XC_CR2");
				}
			}
		}
		// LWCK
		if ("#LWCK#".equals(expr)) {
			String elErpr = (String) mapWork.get("EL_ERPR");
			if (null == elErpr || "".equals(elErpr)) errorRaise(duty, "EL_ERPR");
			String elErpw = (String) mapWork.get("EL_ERPW");
			if (null == elErpw || "".equals(elErpw)) errorRaise(duty, "EL_ERPW");
			if ("1:1".equals(elErpr)) {
				String xcLwck = (String) mapWork.get("XC_LWCK");
				if (null == xcLwck || "".equals(xcLwck)) errorRaise(duty, "XC_LWCK");
				if (0 <= elErpw.compareTo(xcLwck)) return elErpw;
				else return xcLwck;
			} else {
				return elErpw;
			}
		}
		// EL_ERPW를 소수점 첫째자리에서 올림한 정수 값 (ex. 5.1… → 6)
		if ("#EL_ERPW_CELL0#".equals(expr)) {
			String value = (String) mapWork.get("EL_ERPW");
			if (null == value || "".equals(value)) errorRaise(duty, "EL_ERPW");
			
			// 소수점 둘째자리이하 버림
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;
			
			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_CEILING));
//			float x = Float.parseFloat(value);
//			long xl = (long) Math.ceil((double)x);
//			return String.valueOf(xl);
		}

		// 2013.03.07 종속성에서 처리 소수점 첫째자리에서 올림한 정수 값 (ex. 5.5… → 6)
		if ("#RBQ1#".equals(expr)) {
			String value = (String) mapWork.get("XC_RBQ");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_RBQ");

			BigDecimal bd = new BigDecimal(value + "");
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_HALF_UP));
		}

		// 2013.03.20 종속성에서 처리 소수점 첫째자리에서 올림한 정수 값 (ex. 5.5… → 6)
		if ("#SPBQ1#".equals(expr)) {
			String value = ((String) mapWork.get("XC_SPBQ")).trim();
			if (null != value && !"".equals(value)) 	{
				BigDecimal bd = new BigDecimal(value + "");
				return String.valueOf(bd.setScale(0, BigDecimal.ROUND_HALF_UP));
			}else	{
				return value;
			}
		}
		// 2014.12.29 종속성에서 처리 소수점 첫째자리에서 올림한 정수 값 (ex. 5.5… → 6)
		if ("#RAL#".equals(expr)) {
			String value = (String) mapWork.get("XC_RAL");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_RAL");

			// 소수점 둘째자리이하 버림
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;
			
			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		if ("#BRKU#".equals(expr)) {
			String value = (String) mapWork.get("XC_RAL2");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_RAL2");

			// 소수점 둘째자리이하 버림
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;
			
			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
		}
		if ("#BRKD#".equals(expr)) {
			String value = (String) mapWork.get("XC_RAL2");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_RAL2");

			// 소수점 둘째자리이하 버림
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;
			
			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		//RAIL 수량 공식
		if ("#V1C#".equals(expr)) {
			String value = (String) mapWork.get("XC_V1C");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_V1C");

			// 소수점 둘째자리이하 버림
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;
			
			BigDecimal bd = new BigDecimal((double)x);
			logger.info("test #V1C#"+bd);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		if ("#V1W#".equals(expr)) {
			String value = (String) mapWork.get("XC_V1W");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_V1W");

			// 소수점 둘째자리이하 버림
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;
			
			BigDecimal bd = new BigDecimal((double)x);
			logger.info("test #V1W#"+bd);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		return "";
	}
	
	private void errorRaise(Duty duty, String field) {
		String error = "require field : " + duty.getGTYPE() + " : " + field;
		
		if (0 > field.indexOf("XC_") &&
			!"PCELV".equals(duty.getGTYPE()) &&
			!"PCESMW".equals(duty.getGTYPE()) &&
			!"PCAP".equals(duty.getGTYPE())) {
			try {
				Map map = this.dao.selectError(duty.getMANDT(), duty.getZPRDGBN(), field, duty.getLANG());
				error = (String) map.get("ATBEZ");
				
				Logger logger = Logger.getLogger(this.getClass());
				logger.debug("require field : " + map.get("CLASSNM") + " " + map.get("ATKLA") + " " + map.get("ATBEZ"));
			} catch (Exception e) {	}
		}
		
		throw new RequireException(error);
	}
}
