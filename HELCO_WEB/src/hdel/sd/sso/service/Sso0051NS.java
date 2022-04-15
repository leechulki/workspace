package hdel.sd.sso.service;
 
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.service.SrmService;
import hdel.sd.com.domain.BrndSayang;
import hdel.sd.sso.dao.Sso0051ND;
import hdel.sd.sso.domain.Sso0051;
import hdel.sd.sso.domain.Sso0051ParamVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;



/**
 * 사양복사 (Sso0051S) Service
 * @Comment
 *     	1.  void createDao  
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2013.03.11  김선호  :  initial version 
 * @version 1.0
 */


@Service
public class Sso0051NS extends SrmService {
	
	Logger logger = Logger.getLogger(this.getClass());

	private Sso0051ND dao;
	
	public void createDao(SqlSession sqlSession) { 
		dao = sqlSession.getMapper(Sso0051ND.class);
	} 
	
	// 호기번호및  변경차수로 사양 조회
	public MipResultVO selectListSpec(MipParameterVO paramVO, SqlSession sessionVO) {
		MipResultVO resultVO = new MipResultVO();
		
		// INPUT DATASET GET
		String i_pspHogi 	= paramVO.getVariable("pspHogi"); // 호기번호
		String i_seq        = paramVO.getVariable("fa_seq"); // 변경 차수  
//		String tab_index    = paramVO.getVariable("tab_index"); // 탭 index

		Dataset dsList = paramVO.getDataset("ds_output_ZSDS0016");	// UI로 return할 out dataset 

		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		try { 
			// DATASET 초기화
			dsList.deleteAll();
						
//			if("0".equals(tab_index)) {
				Sso0051ParamVO param = new Sso0051ParamVO();

				param.setMANDT(paramVO.getVariable("G_MANDT"));  								// 조건_SAP CLIENT  
				param.setHOGI(i_pspHogi);        //호기
				param.setSEQ(i_seq);             //순번 
				
				List<Sso0051> list = dao.selectListSpec(param);
				CallSAPHdel.moveListToDsSub(dsList, Sso0051.class, list);
//			} else if("1".equals(tab_index)) {
//				// vo생성
//				Ses0031ParamVO param  = new Ses0031ParamVO();
//				
//				// 변수 세팅
//				param.setMandt(paramVO.getVariable("G_MANDT"));
//				param.setQtnum(i_pspHogi.substring(0, i_pspHogi.length()-2));
//				param.setQtser(i_pspHogi.substring(i_pspHogi.length()-2, i_pspHogi.length()-1));
//				param.setQtseq(i_pspHogi.substring(i_pspHogi.length()-1, i_pspHogi.length()));
//				if("en".equals(paramVO.getVariable("G_LANG"))) param.setSpras("E");
//				else param.setSpras("3");
//				
//				Ses0031Dao = sessionVO.getMapper(Ses0031ND.class);
//				
//				List<Ses0031> list = Ses0031Dao.searchTemplatePop(param);
//				
//				for ( int i = 0 ; i < list.size(); i++ ) {
//					dsList.appendRow();
//					dsList.setColumn(i, "NAM_CHAR", list.get(i).getPrh());
//					dsList.setColumn(i, "ATBEZ", list.get(i).getPrhname());
//					dsList.setColumn(i, "VALUE1", list.get(i).getPrd());
//					dsList.setColumn(i, "ATWTB1", list.get(i).getAtkla());
//					dsList.setColumn(i, "KUNRG_NM", list.get(i).getGsnam());
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}
		resultVO.addDataset(dsList);
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		
		return resultVO;
	}
	
	// 호기번호및  변경차수로 사양 조회
	public MipResultVO listSpecCopy(MipParameterVO paramVO) {
		// INPUT DATASET GET
		Dataset ds_ZSDS0016_input 	= paramVO.getDataset("ds_ZSDS0016_input"); 	// 복사할 영업사양
		Dataset ds_ZSDT0091_input 	= paramVO.getDataset("ds_ZSDT0091_input"); 	// 복사할 대상리스트
		Dataset ds_ZSDT0094_input 	= paramVO.getDataset("ds_ZSDT0094_input"); 	// 복사할 대상리스트의 사양들
		String flag 				= paramVO.getVariable("flag"); 				// 복사 구분
		Dataset ds_result = new Dataset();
		
		// 2020 브랜드 맵
		Map<String, Map<String, Object>> brndMap = new HashMap<String, Map<String, Object>>();
		
		//flag = 1 이면 부분복사 / 2 이면 전체복사
		//tabIndex = 0 이면 영업 / 1 이면 견적 
		try{	
			int match = 0;
			int ZSDS0016_cnt = 0;
			String[] matchCount = new String[ds_ZSDS0016_input.getRowCount()];
			
			for ( int a = 0 ; a < ds_ZSDS0016_input.getRowCount() ; a++ ) {
				matchCount[a] = "";
			}
			
			String nam_char, value1, atwtb1, posnr, strCharValue, strAtwtb, strStatus;
			
			// 2020 브랜드
			String mandt;
			String brndcd;
			String brndseq;
			String zprdgbn;
			String parambrndcd;
			boolean isCopy = false;
			
			String disptp; // 출력여부
			String moditp; // 수정여부
			String cnt;
			
			// 2020 브랜드 영업사양 특성값 조회 로직 추가
			for(int j = 0; j < ds_ZSDT0091_input.getRowCount(); j++) {
				if("1".equals( ds_ZSDT0091_input.getColumnAsObject(j, "CHK"))) {
					mandt  = ds_ZSDT0091_input.getColumnAsString(j, "MANDT");
					brndcd = ds_ZSDT0091_input.getColumnAsString(j, "BRNDCD");
					parambrndcd = ds_ZSDT0091_input.getColumnAsString(j, "BRNDCD");
					brndseq = ds_ZSDT0091_input.getColumnAsString(j, "BRNDSEQ");
					zprdgbn = ds_ZSDT0091_input.getColumnAsString(j, "CLASS");
					// brndcd 데이터가 없는 경우 NOBRND 값 치환
					if(parambrndcd == null) {
						parambrndcd = "NOBRND";
					} else {
						if("".equals(parambrndcd)) {
							parambrndcd = "NOBRND";
						}
					}
					
					if(!brndMap.containsKey(brndcd+brndseq)) {
						Map<String, Object> sayanMap = selectsSyangPrd(mandt, brndseq, parambrndcd, zprdgbn);
						brndMap.put(brndcd+brndseq, sayanMap);
					}
				}
			}

			if("1".equals(flag)) {
				// 복사할 사양 총건수
				for(int i = 0; i < ds_ZSDS0016_input.getRowCount(); i++) {
					// 복사할 사양총건 중 체크가 된 로우일경우
					if("1".equals( ds_ZSDS0016_input.getColumnAsObject(i, "CHK"))) {
						ZSDS0016_cnt++;
						// 복사할 대상리스트 총 건수
						for(int j = 0; j < ds_ZSDT0091_input.getRowCount(); j++) {
							// 복사할 대살리스트 총 건중 체크가 된 리스트일 경우
							if("1".equals( ds_ZSDT0091_input.getColumnAsObject(j, "CHK"))) {
								nam_char = ds_ZSDS0016_input.getColumnAsString(i, "NAM_CHAR");	// 특성
								value1 	= ds_ZSDS0016_input.getColumnAsString(i, "VALUE1");		// 특성값
								atwtb1 	= ds_ZSDS0016_input.getColumnAsString(i, "ATWTB1");		// 특성값 내역
								posnr	= ds_ZSDT0091_input.getColumnAsString(j, "POSNR"); 		// 품목번호

								mandt  = ds_ZSDT0091_input.getColumnAsString(j, "MANDT");
								brndcd = ds_ZSDT0091_input.getColumnAsString(j, "BRNDCD");
								brndseq = ds_ZSDT0091_input.getColumnAsString(j, "BRNDSEQ");
								
								// 처리결과 메시지용
								matchCount[i] = nam_char;
								
								// 복사할 대상 리스트에 해당하는 사양건수 중
								for(int k = 0; k < ds_ZSDT0094_input.getRowCount(); k++) {
									// [판매품목]이 동일하고
									if(posnr.equals( ds_ZSDT0094_input.getColumnAsObject(k, "POSNR"))) {
										// [특성]이 동일하다면
										if(!"CO_".equals(nam_char.substring(1, 3))) {
											if(nam_char.equals( ds_ZSDT0094_input.getColumnAsObject(k, "NAM_CHAR"))) {
												//System.out.println("atbez:"+atbez+", nam_char:"+nam_char+", value1:"+value1);
												// 처리결과 메시지용
												matchCount[i] = "Y";
												match++;

												// 해당정보로 갱신
												// 2013.03.04 STATUS 컬럼 추가로 STATUS 관리
												strCharValue = ds_ZSDT0094_input.getColumnAsString(k, "CHAR_VALUE");
												strAtwtb     = ds_ZSDT0094_input.getColumnAsString(k, "ATWTB");
												strStatus    = ds_ZSDT0094_input.getColumnAsString(k, "STATUS");
												// 2020 브랜드 추가
												disptp       = ds_ZSDT0094_input.getColumnAsString(k, "DISPTP");
												moditp       = ds_ZSDT0094_input.getColumnAsString(k, "MODITP");
												cnt          = ds_ZSDT0094_input.getColumnAsString(k, "CNT");
												
												if(strCharValue == null) strCharValue = "";
												if(strAtwtb     == null) strAtwtb     = "";
												if(strStatus    == null) strStatus    = "";
												
												// 2020 브랜드 추가
												if(disptp == null) disptp = "";
												if(moditp == null) moditp = "";

												// 2020 브랜드 사양복사 대상 조건 추가, 영업사양 특성값 존재 여부 체크
												if("0".equals(cnt)) {
													isCopy = true;
												} else {
													isCopy = isSyangPrdExist(brndMap.get(brndcd+brndseq), nam_char, value1);
												}
												if(!strCharValue.equals(value1) && disptp.equals("X") && moditp.equals("X") && isCopy) {
													if(!strStatus.equals("insert")) {
														ds_ZSDT0094_input.setColumn(k, "STATUS", "update");
													}
													ds_ZSDT0094_input.setColumn(k, "CHAR_VALUE", value1 );	// 특성값
													ds_ZSDT0094_input.setColumn(k, "ATWTB", atwtb1 );		// 특성값 내역
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} else if("2".equals(flag)) {
				for(int i = 0; i < ds_ZSDS0016_input.getRowCount(); i++) {
					// 복사할 사양총건 중 체크가 된 로우일경우
					if ("1".equals( ds_ZSDS0016_input.getColumnAsObject(i, "CHK"))) {
						ZSDS0016_cnt++;
						// 복사할 대상리스트 총 건수
						for(int j = 0; j < ds_ZSDT0091_input.getRowCount(); j++) {
							// 복사할 대살리스트 총 건중 체크가 된 리스트일 경우
							if("1".equals( ds_ZSDT0091_input.getColumnAsObject(j, "CHK"))) {
								nam_char = ds_ZSDS0016_input.getColumnAsString(i, "NAM_CHAR");	// 특성
								value1 	= ds_ZSDS0016_input.getColumnAsString(i, "VALUE1");		// 특성값
								atwtb1 	= ds_ZSDS0016_input.getColumnAsString(i, "ATWTB1");		// 특성값 내역
								posnr	= ds_ZSDT0091_input.getColumnAsString(j, "POSNR"); 		// 품목번호
								
								brndcd = ds_ZSDT0091_input.getColumnAsString(j, "BRNDCD");
								brndseq = ds_ZSDT0091_input.getColumnAsString(j, "BRNDSEQ");
								
								for(int k = 0; k < ds_ZSDT0094_input.getRowCount(); k++) {
									// [판매품목]이 동일하고
									if(posnr.equals( ds_ZSDT0094_input.getColumnAsObject(k, "POSNR"))) {							
										// 	[특성]이 동일하다면
										if(nam_char.equals( ds_ZSDT0094_input.getColumnAsObject(k, "NAM_CHAR"))) {
											// 처리결과 메시지용
											matchCount[i] = "Y";
											match++;

											// 해당정보로 갱신
											// 2013.03.04 STATUS 컬럼 추가로 STATUS 관리
											strCharValue = ds_ZSDT0094_input.getColumnAsString(k, "CHAR_VALUE");
											strAtwtb     = ds_ZSDT0094_input.getColumnAsString(k, "ATWTB");
											strStatus    = ds_ZSDT0094_input.getColumnAsString(k, "STATUS");
											// 2020 브랜드 추가
											disptp       = ds_ZSDT0094_input.getColumnAsString(k, "DISPTP");
											moditp       = ds_ZSDT0094_input.getColumnAsString(k, "MODITP");
											cnt          = ds_ZSDT0094_input.getColumnAsString(k, "CNT");
								
											if(strCharValue == null) strCharValue = "";
											if(strAtwtb     == null) strAtwtb     = "";
											if(strStatus    == null) strStatus    = "";
								
											// 2020 브랜드 사양복사 대상 조건 추가, 영업사양 특성값 존재 여부 체크
											if("0".equals(cnt)) {
												isCopy = true;
											} else {
												isCopy = isSyangPrdExist(brndMap.get(brndcd+brndseq), nam_char, value1);
											}
//											if(!strCharValue.equals(value1) || !strAtwtb.equals(atwtb1) && disptp.equals("X") && moditp.equals("X") && isExis) {
//												if(!strStatus.equals("insert")) {
//													ds_ZSDT0094_input.setColumn(k, "STATUS", "update");
//												}
//											}

											if(!strCharValue.equals(value1) || !strAtwtb.equals(atwtb1) && disptp.equals("X") && moditp.equals("X") && isCopy) {
												if(!strStatus.equals("insert")) {
												    ds_ZSDT0094_input.setColumn(k, "STATUS", "update");
											    }
											    ds_ZSDT0094_input.setColumn(k, "CHAR_VALUE", value1 );	// 특성값
											    ds_ZSDT0094_input.setColumn(k, "ATWTB", atwtb1 );		// 특성값 내역
											}
								
										}		
							/*
							String is_spec = "";
							// 해당정보로 갱신
							for(int ii = 0; ii < ds_ZSDS0016_input.getRowCount(); ii++) {
								nam_char = ds_ZSDS0016_input.getColumnAsString(ii, "NAM_CHAR");	// 특성
								if(nam_char.equals( ds_ZSDT0094_input.getColumnAsObject(j, "NAM_CHAR") ) ) {	
									is_spec = "Y";
								}
							}	
							
							if(is_spec != "Y") {
								// 2013.03.04 STATUS 컬럼 추가로 STATUS 관리
								strCharValue = ds_ZSDT0094_input.getColumnAsString(j, "CHAR_VALUE");
								strAtwtb     = ds_ZSDT0094_input.getColumnAsString(j, "ATWTB");
								strStatus    = ds_ZSDT0094_input.getColumnAsString(j, "STATUS");
								
								if(strCharValue == null) strCharValue = "";
								if(strAtwtb     == null) strAtwtb     = "";
								if(strStatus    == null) strStatus    = "";
								
								if(!strCharValue.equals(value1) || !strAtwtb.equals(atwtb1)) {
									if(!strStatus.equals("insert")) {
										ds_ZSDT0094_input.setColumn(j, "STATUS", "update");
									}
								}
								
								ds_ZSDT0094_input.setColumn(j, "CHAR_VALUE", "");	// 특성값
								ds_ZSDT0094_input.setColumn(j, "ATWTB", "");		// 특성값 내역
							} 
							*/
									}
								}
							}
						}
					}
				}
			}
			
			// 처리결과 중 실패한 특성 리스트
			String failVal = "";
			for ( int a = 0 ; a < ZSDS0016_cnt ; a++ ) {
				if ( !matchCount[a].toString().equals("Y") && matchCount[a].toString() != null ) {
					failVal += " "+matchCount[a].toString();
				}
			}
			
			String result_msg = ""+ZSDS0016_cnt ;
			result_msg += "," + match;
			result_msg += "," + failVal;
			
			ds_result.setId("ds_result");
			ds_result.addColumn("RESULT", ColumnInfo.COLTYPE_STRING, 256);
			ds_result.appendRow();
			ds_result.setColumn(0, "RESULT", result_msg);
			
		} catch (Exception e) {
			// java Exc
			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();
		}	 
		
		// miplatform으로 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_ZSDT0094_input); 			// 처리결과내역
		resultVO.addDataset(ds_result); 					// 처리결과내역
				
		return resultVO;
	}

	// 2020 브랜드
	private Map<String, Object> selectsSyangPrd(String mandt, String brndseq, String brndcd, String zprdgbn) {
		Map<String, Object> sayanMap = new HashMap<String, Object>();

		HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("MANDT", mandt);
        paraMap.put("BRNDSEQ", brndseq);
        paraMap.put("ZPRDGBN", zprdgbn);
        paraMap.put("BRNDCD", brndcd);
		
        List<BrndSayang> sayangList = dao.selectsSyangPrd(paraMap);
        for(int i=0; i < sayangList.size(); i++) {
        	String prh = sayangList.get(i).getPRH();
        	String prd = sayangList.get(i).getPRD();
        	sayanMap.put(prh+prd, prd);
        }
		return sayanMap;
	}
	
	// 2020 브랜드
	private boolean isSyangPrdExist(Map<String, Object> sayanMap, String prh, String prd) {
		boolean isExist = false;
		
		if(prd == null) {
			return true;
		} else {
			if("".equals(prd)) {
				return true;
			}
		}
		
		if(sayanMap.containsKey(prh+prd)) {
           	isExist = true;
		}
		
		return isExist;
	}
}
