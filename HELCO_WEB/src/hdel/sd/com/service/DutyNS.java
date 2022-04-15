package hdel.sd.com.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.sap.domain.Datum;
import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;
import com.sap.domain.Posnr;
import com.sap.domain.Vbeln;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDTDUTYEXCPTDD;
import hdel.lib.exception.BizException;
import hdel.lib.exception.NoMatchException;
import hdel.lib.exception.RequireException;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.DutyND;
import hdel.sd.com.domain.Duty;
import hdel.sd.com.domain.DutyDet;
import hdel.sd.com.domain.DutyObj;
import hdel.sd.com.domain.DutyOpr;

@Service
public class DutyNS extends SrmService {

    private Logger logger = Logger.getLogger(this.getClass());

    private DutyND dao;
    private ZSDTDUTYEXCPTDD dutyExcptDDao;

    public void createDao(SqlSession sqlSession) {
        dao = sqlSession.getMapper(DutyND.class);
        dutyExcptDDao = sqlSession.getMapper(ZSDTDUTYEXCPTDD.class);
    }

    // duck 는 디폴트 값을 입력하기 위한 처리
    // duckCheck 실제 입력된 값에 체크를 수행한다.
    //=========================================================================================
    //  함수기능 : 견적상세-Duty Call
    //  파라미터 : mandt, gtype, blockgbn, mapInput, lang
    //  리턴값   : dsTemplate1(사양서 입력체크 기준 체크 후 기본설정값이 적용된 사양서내역)
    //            ,ds_error(사양서 내역 중 필수값이 없는 경우 에러메시지 생성됨)
    //  기능ID   : UF003
    //  개선내역 : 사양 기준정보 및 사양 체크 디테일 처리 로직 개선
    //  HISTORY  : 2016.01.28 최초 코딩 박수근
    //=========================================================================================
    public void genSpecDutySingleMo(String mandt, String gtype, String blockgbn, HashMap<String, String> mapInput, 
    		                          String lang, String zflg) {

        // Duty정보 읽어오기 (블럭별로)
        // ***** 사양서 체크 로직에 대한 처리 속도 향상에 대한 기준은 listDuty 쿼리에 달려 있다 ****** //
    	//20190420 spras추가 by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		
    	DutyObj dutyObj;
        if("LXVF".equals(gtype) || "SSVF".equals(gtype) || "STVF".equals(gtype) || "HSVF".equals(gtype)) {
        	dutyObj = genDutyObj(mandt, gtype, true, blockgbn, zflg, spras);
        } else {
        	dutyObj = genDutyObj(mandt, gtype, false, blockgbn, zflg, spras);
        }
    	List<Duty> listDuty = dutyObj.getDutyList();
        

        // Duty가 존재하지 않는 경우 메세지 처리
        if(listDuty.size() == 0) {
            throw new BizException("No Data Duty Master");
        }

        int specMaxIdx;

        for (int i=0; i<listDuty.size(); i++) {
            Duty duty = listDuty.get(i);
            // Duty 한글 언어 정보값 설정
            // 2012.12.27 메세지 LANG추가
            duty.setLANG("3");
            if (!"ko".equals(lang)) {
                duty.setLANG("E");
            }

            // 상해작은 3으로 정보가 저장되어 있음
            if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )    {
                duty.setLANG("3");
            }

            specMaxIdx = getSpecMaxIdx( duty );	// 사양체크항목 갯수 가져오기

            // 기준 Duty가 가진 기준정보로 Mo 사양에 대한 필수 입력값 체크를 수행한다.
            try    {
                validateDuty(duty, mapInput, specMaxIdx);
            } catch    ( RuntimeException e )    {
                throw e;
            }

            // Duty Detail 정보 읽어오기
            List<DutyDet> listDutyD = dutyObj.getBlockDutyDList(duty.getBLOCKNO());

            if ( listDutyD == null )    {
                throw new BizException("No Data Duty Detail");
            }

            // 사양전개
            int matchIdx = getMatchIdx(duty, mapInput, specMaxIdx, listDutyD);
            if (-1 == matchIdx) {
    			String message = System.getProperty("line.separator") + "[Block No.] " + duty.getBLOCKNO()
    			+ System.getProperty("line.separator") + "[Block Name] " + duty.getBLOCKNM()
//    			+ System.getProperty("line.separator") + "[Field] " + duty.getGTYPE() + " : " + outName
    			;
    			throw new NoMatchException(message);	// 사양전개 후 match 없는 경우 에러
            } else {
            }

            // 체크 대상의 마스터 리스트에 해당되는 값이 존재하고 그 해당 마스터에 출력용으로 정의되는 PRH 값에 대한 부분에
            // 대한 사양 정보값을 정의한다.-> 즉 계산값에 의한 항목값들이 입력되게 된다.
            // int outMaxIdx = getOutMaxIdx( duty );    // 사양Out항목 갯수 가져오기
            // 사양서 산출항목 생성
            // getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
            int outMaxIdx = getOutMaxIdx( duty );	// 사양Out항목 갯수 가져오기

			// 사양 산출항목 생성
			getOut(duty, mapInput, listDutyD.get(matchIdx), outMaxIdx);
        }
    }

    //=========================================================================================
    //  함수기능 : 견적상세-종속성(단일 MO에 처리 수행)
    //  파라미터 : ds_template1(사양서내역 그리드 화면값)
    //  리턴값   : dsTemplate1(사양서 입력체크 기준 체크 후 기본설정값이 적용된 사양서내역)
    //            ,ds_error(사양서 내역 중 필수값이 없는 경우 에러메시지 생성됨)
    //  기능ID   : UF003
    //  개선내역 : 사양 기준정보 및 사양 체크 디테일 처리 로직 개선
    //  HISTORY  : 2016.01.28 최초 코딩 박수근 dependDutySingleMo
    //=========================================================================================
	// 2013.03.06 종속성 처리용 (표준Template 호출 시 처리로직과 분리)
	public void dependDutySingleMo(String mandt, String gtype, String blockgbn, HashMap<String, String> mapInput, 
			                         String lang, String zflg) {

    	//20190420 spras추가 by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";

		// Duty정보 읽어오기 (블럭별로)
		DutyObj dutyObj = genDutyObj(mandt, gtype, blockgbn, zflg,spras);
		List<Duty> listDuty = dutyObj.getDutyList();

		if( listDuty == null ) {
			mapInput = null;
		}

        int specMaxIdx;

        for (int i=0; i<listDuty.size(); i++) {
            Duty duty = listDuty.get(i);
            // Duty 한글 언어 정보값 설정
            // 2012.12.27 메세지 LANG추가
            duty.setLANG("3");
            if (!"ko".equals(lang)) {
                duty.setLANG("E");
            }

            // 상해작은 3으로 정보가 저장되어 있음
            if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )    {
                duty.setLANG("3");
            }

            specMaxIdx = getSpecMaxIdx( duty );	// 사양체크항목 갯수 가져오기

            // 기준 Duty가 가진 기준정보로 Mo 사양에 대한 필수 입력값 체크를 수행한다.
            try    {
            	validateDuty(duty, mapInput, specMaxIdx);
            } catch    ( RuntimeException e )    {
                throw e;
            }

            List<DutyDet> listDutyD = dutyObj.getBlockDutyDList(duty.getBLOCKNO());
            if( listDutyD == null ) {
            	mapInput = null;
            	break;
            }

            // 사양전개
			if( listDutyD != null && listDutyD.size() > 0 ) {
	            int matchIdx = getMatchIdx(duty, mapInput, specMaxIdx, listDutyD);
	            if (-1 == matchIdx) {
					throw new NoMatchException(duty.getBLOCKNM());	// 사양전개 후 match 없는 경우 에러
				}

	            int outMaxIdx = getOutMaxIdx( duty );	// 사양Out항목 갯수 가져오기

				// 사양 산출항목 생성
				getOut(duty, mapInput, listDutyD.get(matchIdx), outMaxIdx);
			}
		}
	}

    //=========================================================================================
    //  함수기능 : 여러건에 대한 사양내역 견적 사양체크 처리 시
    //  파라미터 : ds_template1(사양서내역 그리드 화면값)
    //  리턴값   : dsTemplate1(사양서 입력체크 기준 체크 후 기본설정값이 적용된 사양서내역)
    //            ,ds_error(사양서 내역 중 필수값이 없는 경우 에러메시지 생성됨)
    //  기능ID   : UF003
    //  개선내역 : 사양 기준정보 및 사양 체크 디테일 처리 로직 개선
    //  HISTORY  : 2016.01.28 최초 코딩 박수근 dependDutySingleMo
    //=========================================================================================
	public boolean genSpecCheck(String mandt, String flag, String item, Dataset template, Dataset log, 
            String lang, DutyObj dutyObj) {
		return genSpecCheck(mandt, flag, item, template, log, lang, dutyObj, new Dataset());
	}
	public boolean genSpecCheck(String mandt, String flag, String item, Dataset template, Dataset log, 
			                      String lang, DutyObj dutyObj, Dataset dsOrder) {
		boolean rtnValue = true;
        HashMap<String, String> mapInput = new HashMap<String, String>();
        String sItem = "";
        int cnt   = 0;
        int idxItem = 0;

        // 견적서 사양내용 N행 1열 데이터 형식을 1행 N열형식으로 변환하여 HashMap에 입력한다.
        // SAPHEE.CABN ATNAM  -> SAPHEE.ZSDT1048 PRH
		if ("Q".equals(flag)) {
			for (int i = 0; i < template.getRowCount(); i++) {
				sItem = template.getColumnAsString(i, "QTSEQ");
				if(!item.equals(sItem))	{
					continue;
				}
				idxItem = i;

				String value = template.getColumnAsString(i, "VALUE");	// 견적은 PRD -> VALUE
				if (null != value && ! "".equals(value)) {
					if("NUM".equals(template.getColumnAsString(i, "ATFOR"))) {
						mapInput.put(template.getColumnAsString(i, "ATNAM").replaceAll(",", ""), value);	// 견적은 PRH -> ATNAM
					} else {
						mapInput.put(template.getColumnAsString(i, "ATNAM"), value);	// 견적은 PRH -> ATNAM
					}
				}
			}
		}else	{
			sItem = template.getColumnAsString(0, "HOGI");
			for (int i = 0; i < template.getRowCount(); i++) {
				if(!item.equals(sItem))	{
					continue;
				}
				idxItem = i;

				String value = template.getColumnAsString(i, "PRD");
				if (null != value && ! "".equals(value)) {
					if("NUM".equals(template.getColumnAsString(i, "CNGBN"))) {
						mapInput.put(template.getColumnAsString(i, "PRH").replaceAll(",", ""), value);	// 견적은 PRH -> ATNAM
					} else {
						mapInput.put(template.getColumnAsString(i, "PRH"), value);	// 견적은 PRH -> ATNAM
					}
				}
			}
		}

        // 사양 체크 시 발생된 에러 메시지 데이터 
        //Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
        //try
        //{
        String gtype = "";
        String el_atyp = "";

		// 2013.01.29 Duty 안 타는 조건 추가
		String el_ditemuse = (String) mapInput.get("EL_DITEMUSE");
		String el_asply = (String) mapInput.get("EL_ASPLY");
		String pspid = template.getColumnAsString(idxItem, "PSPID");
		String uch_duty = template.getColumnAsString(idxItem, "UCH_DUTY");
		if ( el_ditemuse == null ) el_ditemuse = "";
		if ( el_asply == null ) el_asply = "";

		if ( el_ditemuse.equals("Y") || el_asply.equals("Y") ) {
			return true;
		}

		if (pspid.equals("126394")){ //20131030 kt 청진빌딩 비표준으로 필수체크 제외 -김선호
			return true;
		}
		
		if (uch_duty.equals("1")){
			return true;
		}
				
		el_atyp = (String) mapInput.get("EL_ATYP");
		
		if (el_atyp != null && !"".equals(el_atyp))	{
			gtype = "ELV1";
			// 선박용 처리
			if ("STSH1".equals(el_atyp) || "STSH5".equals(el_atyp))	{
				gtype = "ELV2";
			}
		} else {
			// 에스컬러에터
			el_atyp = (String) mapInput.get("ES_ATYP");
			if (el_atyp != null && !"".equals(el_atyp))	{
				gtype = "ESC1";	// 에스컬레이터는 Default로 처리
			}else {
				// 무빙워크
				el_atyp = (String) mapInput.get("MW_ATYP");
				if (el_atyp != null && !"".equals(el_atyp))	{
					gtype = "MWK1";	// 무빙워크는 Default로 처리
				} else {
					el_atyp = (String) mapInput.get("AP_ATYP");
					if (el_atyp != null && !"".equals(el_atyp))	{
						gtype = "ZPK1";	// 주차 Default로 처리
					} else	{	// 2013.01.03 이외에는 필수 체크 불필요
						return true;
					}
				}
			}
		}
		genCostDutySingleMo(mandt, gtype, mapInput, log, lang, item, cnt, dutyObj, flag, dsOrder);

        return rtnValue;
	}

    //=========================================================================================
    //  함수기능 : 견적상세-원가산정 종속성 체크(Single MO에 처리 수행)
	//             견적상세 원가산정 처리 시 입력된 값에 대한 종석성 체크를 수행한다.
	//             종속성 체크 수행 시 값에 대한 체크 발생 시 메시지를 통해 체크 내용을 전달한다.
    //  파라미터 : ds_template1(사양서내역 그리드 화면값)
    //  리턴값   : dsTemplate1(사양서 입력체크 기준 체크 후 기본설정값이 적용된 사양서내역)
    //            ,ds_error(사양서 내역 중 필수값이 없는 경우 에러메시지 생성됨)
    //  기능ID   : UF003
    //  개선내역 : 사양 기준정보 및 사양 체크 디테일 처리 로직 개선
    //  HISTORY  : 2016.01.28 최초 코딩 박수근 dependDutySingleMo
    //=========================================================================================
    public void genCostDutySingleMo(String mandt, String gtype, HashMap<String, String> mapInput, Dataset log, 
    		                         String lang, String item, int cnt, DutyObj dutyObj, String zflg, Dataset dsOrder) {

    	List<String> lstBlockNo = new ArrayList<String>();
    	if(dsOrder != null) {
    		lstBlockNo = dutyExcptDDao.getExceptionalBlockNoList(mandt, new OrderNo(dsOrder.getColumnAsString(0, "ordno")), dsOrder.getColumnAsInteger(0, "ordseq"), new OrderItem(dsOrder.getColumnAsString(0, "orditem")));
    		Datum ildat = dao.getIldat(mandt, new Vbeln(dsOrder.getColumnAsString(0, "ordno")), new Posnr(dsOrder.getColumnAsString(0, "orditem")));
    		String today = (new SimpleDateFormat("yyyyMMdd")).format(Calendar.getInstance().getTime());
    		if(ildat != null && !ildat.isInitial() && Float.valueOf(ildat.toString()) < Float.valueOf(today))
    			return;
    	}

    	//20190420 spras추가 by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                   spras = "E";
    	
    	// Duty정보 읽어오기 (블럭별로)
    	if(dutyObj == null) {
    		// 초기값 선언이 없는 경우 쿼리 수행
    		dutyObj = genDutyObj(mandt, gtype, "", zflg, lstBlockNo,spras);
    	} else {
        	// 검색어 값을 파라미터로 전달하여 중복된 파라미터에 대한 이전 쿼리 수행 자료가
        	// 존재하면 중복된 쿼리 수행을 방지하여 이미 존재하는 데이터를 가지고 비교한다.
        	boolean isData = dutyObj.getDataExists(mandt, gtype);
    		if( !isData ) {
    			// 데이터 미 존재 시 쿼리 수행
    			dutyObj = genDutyObj(mandt, gtype, false, "", zflg, dutyObj, lstBlockNo,spras);
    		}
        }

        List<Duty> listDuty = dutyObj.getDutyList();
        int specMaxIdx;
        
		for (int i = 0; i < listDuty.size(); i++) {
			Duty duty = listDuty.get(i);

            // Duty 한글 언어 정보값 설정
            // 2012.12.27 메세지 LANG추가
            duty.setLANG("3");
            if (!"ko".equals(lang)) {
                duty.setLANG("E");
            }

            // 상해작은 3으로 정보가 저장되어 있음
            if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )    {
                duty.setLANG("3");
            }
            specMaxIdx = getSpecMaxIdx( duty );	// 사양체크항목 갯수 가져오기

            // 기준 Duty가 가진 기준정보로 Mo 사양에 대한 필수 입력값 체크를 수행한다.
            try {
                validateDuty(duty, mapInput, specMaxIdx);
            } catch( RuntimeException e ) {
            	cnt = log.appendRow();
				log.setColumn(cnt, "IDX", cnt + 1);
				// 2020 브랜드
				log.setColumn(cnt, "PRCSYS",  "SRM");
				log.setColumn(cnt, "HOGI",  item);
				log.setColumn(cnt, "LOGMSG",  e.getMessage());
				//throw e;
            }
            
            List<DutyDet> listDutyD = dutyObj.getBlockDutyDList(duty.getBLOCKNO());
			if ( listDutyD != null && listDutyD.size() > 0 ) {
				// 사양전개
				//2019.02.10 try catch로 getMatchIdx에서 exception발생시 catch를 받음
	            int matchIdx = 0;
	            try {
	            		matchIdx = getMatchIdx(duty, mapInput, specMaxIdx, listDutyD);
	    				if (-1 == matchIdx) {
	    					cnt = log.appendRow();
	    					log.setColumn(cnt, "IDX", cnt + 1);
	    					log.setColumn(cnt, "PRCSYS",  "SRM");
	    					log.setColumn(cnt, "HOGI",  item);
	    					log.setColumn(cnt, "LOGMSG",  duty.getBLOCKNM());
	    				} else {
	    					int outMaxIdx = getOutMaxIdx( duty );	// 사양Out항목 갯수 가져오기
	    					// 사양 산출항목 생성
	    				    getOut(duty, mapInput, listDutyD.get(matchIdx), outMaxIdx);
	    				}
	            } catch (RuntimeException e) {
	            	e.printStackTrace();
					cnt = log.appendRow();
					log.setColumn(cnt, "IDX", cnt + 1);
					log.setColumn(cnt, "PRCSYS",  "SRM");
					log.setColumn(cnt, "HOGI",  item);
					log.setColumn(cnt, "LOGMSG",  duty.getBLOCKNM() +":"+ e.getMessage());
	            }
			}
		}
	}

    //=========================================================================================
    //  함수기능 : 사양서 체크를 위한 SAPHEE.ZSDTDUTY, SAPHEE.ZSDTDUTYD BLOCKNO별 데이터
    //             조회
    //  파라미터 : String mandt, String gtype, String blockgbn
    //  리턴값   : DutyObj
    //  기능ID   : UF003
    //  개선내역 : BLOCKNO별 개별 데이터 조회 방식을 전체 BLOCKNO 데이터 조회 형식으로 변경처리
    //  HISTORY  : 2016.01.28 최초 코딩 박수근
    //=========================================================================================
    private DutyObj genDutyObj(String mandt, String gtype, String blockgbn, String zflg, List<String> lstExcpBlockNo ,String lang) {
    	return genDutyObj(mandt, gtype, false, blockgbn, zflg, new DutyObj(), lstExcpBlockNo, lang);
    }
    private DutyObj genDutyObj(String mandt, String gtype, String blockgbn, String zflg,String lang) {
    	DutyObj dutyObj = new DutyObj();
    	return genDutyObj(mandt, gtype, false, blockgbn, zflg, dutyObj, lang);
    }
    private DutyObj genDutyObj(String mandt, String gtype, Boolean anyType, String blockgbn, String zflg,String lang) {
    	DutyObj dutyObj = new DutyObj();
    	return genDutyObj(mandt, gtype, anyType, blockgbn, zflg, dutyObj, lang);
    }

    //=========================================================================================
    //  함수기능 : 사양서 체크를 위한 SAPHEE.ZSDTDUTY, SAPHEE.ZSDTDUTYD BLOCKNO별 데이터
    //             조회
    //  파라미터 : String mandt, String gtype, String blockgbn, DutyObj dutyObj
    //  리턴값   : DutyObj
    //  기능ID   : UF003
    //  개선내역 : BLOCKNO별 개별 데이터 조회 방식을 전체 BLOCKNO 데이터 조회 형식으로 변경처리
    //  HISTORY  : 2016.01.28 최초 코딩 박수근
    //=========================================================================================
    private DutyObj genDutyObj(String mandt, String gtype, Boolean anyType, String blockgbn, String zflg, DutyObj dutyObj,String lang) {
    	return genDutyObj(mandt, gtype, anyType, blockgbn, zflg, dutyObj, new ArrayList<String>(), lang);
    }
	private DutyObj genDutyObj(String mandt, String gtype, Boolean anyType, String blockgbn, String zflg, DutyObj dutyObj, List<String> lstExcpBlockNo,String lang) {

    	List<Duty> dutyList = dao.selectDuty(mandt, gtype, anyType, blockgbn, zflg, lstExcpBlockNo,lang);
    	// 검색어 값을 파라미터로 전달하여 중복된 파라미터에 대한 이전 쿼리 수행 자료가
    	// 존재하면 중복된 쿼리 수행을 방지한다.
    	dutyObj.setDutyList(dutyList, mandt, gtype, zflg);

    	DutyDet dutyDet=null;
		DutyDet dutyDetNext=null;
        List<DutyDet> dutyDList = dao.selectDutydList(mandt, gtype, anyType, blockgbn, zflg, lstExcpBlockNo);
        int MaxSize = 0;
        if( dutyDList.size() > 1 ) {
        	MaxSize = dutyDList.size()-1;
        }
		List<DutyDet> tmpDutyDList = new ArrayList<DutyDet>();
        for(int i=0; i < dutyDList.size(); i++ ) {
        	if( i < MaxSize ) {
            	dutyDet = dutyDList.get(i);
            	dutyDetNext = dutyDList.get(i+1);
            	if( dutyDet.getBLOCKNO().equals(dutyDetNext.getBLOCKNO())) {
            		tmpDutyDList.add(dutyDet);
            	} else {
            		tmpDutyDList.add(dutyDet);
            		dutyObj.setBlockDutyDList(dutyDet.getBLOCKNO(), tmpDutyDList);
            		tmpDutyDList = new ArrayList<DutyDet>();
            	}
        	} else {
            	dutyDet = dutyDList.get(i);
        		tmpDutyDList.add(dutyDet);
        		dutyObj.setBlockDutyDList(dutyDet.getBLOCKNO(), tmpDutyDList);
        	}
        }

        return dutyObj;
    }

	// HashMap mapWork 은 in/out 처리
	// HashMap mapOut  은 in/out 처리
	private void validateDuty(Duty duty, HashMap<String,String> mapInput, int specMaxIdx) {
		String value = "";
		if (0 == specMaxIdx) return;

		Method dutySpecX, dutySpecDefvX, dutySpecEssX;
		int idx=1;
		try	{
			while(idx <= 20) {
					dutySpecX = duty.getClass().getMethod("getSPEC"+String.valueOf(idx));
					dutySpecDefvX = duty.getClass().getMethod("getSPECDEFV"+String.valueOf(idx));
					dutySpecEssX = duty.getClass().getMethod("getSPECESS"+String.valueOf(idx));

					value = (String)mapInput.get(dutySpecX.invoke(duty).toString());
					if (null == value || value.equals("")) {
						if (dutySpecEssX.invoke(duty).toString().equals("X")) {
							errorRaise(duty, dutySpecX.invoke(duty).toString());
						}

						if (!dutySpecDefvX.invoke(duty).toString().equals("")) {
							mapInput.put(dutySpecX.invoke(duty).toString(), dutySpecDefvX.invoke(duty).toString()); // Default 부분이 출력 되도록 추가 2012.10.12
						}
					}

				idx++;
	        }
		} catch(NoSuchMethodException e) {
			throw new BizException(e.getMessage());
		} catch(InvocationTargetException e) {
			throw new BizException(e.getMessage());
		} catch(IllegalAccessException e) {
			throw new BizException(e.getMessage());
		} catch(RuntimeException e) {
			throw e;
		}
	}

	private int getMatchIdx(Duty duty, HashMap<String, String> mapWork, int specMaxIdx, List<DutyDet> listDutyD) {
		int matchIdx = -1;

		// Duty에 Spec이 설정되지 않은 경우
		if (0 == specMaxIdx) {
			// Spec 리스트가 1건인 경우는 해당 단일건으로 산출
			if (1 == listDutyD.size()) return 0;
			// 이외에는 오류 (0 또는 다중은 처리 불가)
			else throw new BizException("No Spec & Multi Out Exception");
		}
		Method dutySpecX, dutyDSpecX;
		DutyDet dutyDet;
		for (int i = 0; i < listDutyD.size(); i++) {
			boolean matched = false;
			dutyDet = listDutyD.get(i);
//			Method dutySpecX, dutyDSpecX;

			int idx=1;
			try {
				while(idx <= 20) {
					if(idx > specMaxIdx) break;
	
					dutySpecX = duty.getClass().getMethod("getSPEC"+String.valueOf(idx));
					dutyDSpecX = dutyDet.getClass().getMethod("getSPEC"+String.valueOf(idx));
					
					matched = DutyOpr.compare((String) mapWork.get(dutySpecX.invoke(duty).toString()), dutyDSpecX.invoke(dutyDet).toString(), mapWork);
					if(!matched) break;
	
					idx++;
				}
				if (matched) {
					matchIdx = i;
					break;
				}
			} catch (NoSuchMethodException e) {
				throw new BizException(e.getMessage());
			} catch (InvocationTargetException e) {
				throw new BizException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new BizException(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException(e.getMessage());
			}
		}
		return matchIdx;
	}

	// outValue 모듈화 변경
	private String getOutValue(String outName, Duty duty, String outDutyD, HashMap<String, String> mapInput)	{

		String outValue = "";

		// 산출값이 '|' 인 경우는 해당 값을 그대로 출력, 기호는 제외
		if (0 <= outDutyD.indexOf("|")) {
			outValue = outDutyD.replace("|", "");
		// 산출값이 계산식(사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) 1900 + {EL_ECCH}
		} else if (0 <= outDutyD.indexOf("+") ||
			0 <= outDutyD.indexOf("-") ||
			0 <= outDutyD.indexOf("*") ||
			0 <= outDutyD.indexOf("/")) {
				if ("ES".equals(outName.substring(0,2)) || "MW".equals(outName.substring(0,2))){
					outValue = outDutyD;
				} else {
					if ("R/L".equals(outDutyD) || "R/R".equals(outDutyD)){
						// CAR GOVERNOR 위치 값은 R/L R/R -> 수식 계산으로 0 으로 출력되는 것을 방지 20141229 김선호
						outValue = outDutyD;
					}else{
						outValue = calc(duty, mapInput, outDutyD);
				    }
				}	
		// 산출값이 계산식(비사칙연산)인 경우 연산을 수행 후 산출값 리턴, ex) #MC1#
		} else if (0 <= outDutyD.indexOf("#")) {
			outValue = calcFix( duty, mapInput, outDutyD);
		// 산출값이 특성인 겅우, ex) {EL_ECWAD}
		} else if (0 <= outDutyD.indexOf("{") ||
				   0 <= outDutyD.indexOf("}") ) {
			outValue = (String) mapInput.get(outDutyD.replace("{", "").replace("}", ""));
		} else {
			outValue = outDutyD;
		}

		return outValue;
	}

	// HashMap mapWork 은 in/out 처리
	// HashMap mapOut  은 in/out 처리
	private void getOut(Duty duty, HashMap<String, String> mapInput, DutyDet dutyDet, int outMaxIdx) {
		String outName = "";
		String outValue = "";
		Method dutyOutX, dutyDetOutX;

		int idx=1;
		try {
			while(idx <= 10) {
					dutyOutX = duty.getClass().getMethod("getOUT"+String.valueOf(idx));
					dutyDetOutX = dutyDet.getClass().getMethod("getOUT"+String.valueOf(idx));
					outName = dutyOutX.invoke(duty).toString();
					outValue = getOutValue(outName, duty, dutyDetOutX.invoke(dutyDet).toString(), mapInput);
	
		            // 2012.10.29 카자중의 경우 1의자리에서 무조건 올림 사용 1184.88 -> 1190으로 결과값처리
	                if (outName.equals("EL_ECW") || outName.equals("EL_YECW_1") || outName.equals("EL_YECWR")) {
	                    outValue = (String) (new BigDecimal(outValue).setScale(-1, BigDecimal.ROUND_UP).intValue() + "");
	                }
	
					mapInput.put(outName, outValue);
	
				idx++;
	        }
		} catch (NoSuchMethodException e) {
			throw new BizException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new BizException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new BizException(e.getMessage());
		} catch(Exception e) {
			String message = "[Block No.] " + duty.getBLOCKNO()
			+ System.getProperty("line.separator") + "[Block Name] " + duty.getBLOCKNM()
			+ System.getProperty("line.separator") + "[Field] " + duty.getGTYPE() + " : " + outName
			+ System.getProperty("line.separator") + "[Class] " + e.getClass().toString()
			+ System.getProperty("line.separator") + "[Message] " + e.getMessage();
			throw new BizException(message);
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
	private String calc(Duty duty, HashMap<String, String> mapWork, String expr) {
		JexlEngine jexl = new JexlEngine();
		// 연산식 Parsing (연산인자 구분기호인 '{', '}'는 제외)
	    Expression e = jexl.createExpression(expr.replace("{", "").replace("}", ""));

	    // 연산식에 포함된 특성코드를 발췌 ex 3300 * {EL_ASTQ}, {EL_EHO} + {EL_EHTRH} + {EL_EHP}
	    List<String> listArgs = new ArrayList<String>();
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
	private String calcFix(Duty duty, HashMap<String, String> mapWork, String expr) {
		if (null == expr || "".equals(expr.trim())) return "";

		// [MC]를 소수점 둘째자리에서 올림한 값 (ex. 5.432356… → 5.5)
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
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		//리모델링 추가 5단위 반올림
		if ("#R5R#".equals(expr)) {
			String value = (String) mapWork.get("XC_R5R");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_R5R");
			
			float x = Float.parseFloat(value);
			x = x / 10;
			x = (float) (Math.ceil( x * 2 ) / 2);
			x = x * 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
		}
		//리모델링 추가 10단위 반올림
		if ("#R10R#".equals(expr)) {
			String value = (String) mapWork.get("XC_R10R");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_R10R");
			
			float x = Float.parseFloat(value);
			
			x = x / 10;
			// round, ceil ?
			x = (float) (Math.round( x ));
			x = x * 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
		}
		//리모델링 추가 10단위 버림
		if ("#R10T#".equals(expr)) {
			String value = (String) mapWork.get("XC_R10T");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_R10T");
			
			float x = Float.parseFloat(value);
			
			x = x / 10;
			x = (float) (Math.floor( x ));
			x = x * 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		//리모델링 추가 50단위 반올림
		if ("#R50R#".equals(expr)) {
			String value = (String) mapWork.get("XC_R50R");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_R50R");
			
			float x = Float.parseFloat(value);
			
			x = x / 100;
			x = (float)Math.ceil( x * 2 ) / 2;
			x = x * 100;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
		}

		return "";
	}

	private void errorRaise(Duty duty, String field) {
		String error = System.getProperty("line.separator") + "[Block No.] " + duty.getBLOCKNO()
					+ System.getProperty("line.separator") + "[Block Name] " + duty.getBLOCKNM()
					+ System.getProperty("line.separator") + "[Field] " + duty.getGTYPE() + " : " + field;

		if (0 > field.indexOf("XC_") &&
			!"PCELV".equals(duty.getGTYPE()) &&
			!"PCESMW".equals(duty.getGTYPE()) &&
			!"PCAP".equals(duty.getGTYPE())) {
			try {
				Map<?, ?> map = this.dao.selectError(duty.getMANDT(), duty.getZPRDGBN(), field, duty.getLANG());
				error = (String) map.get("ATBEZ");

			} catch (Exception e) {	}
		}

		throw new RequireException(error);
	}
}
