package hdel.sd.sso.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.sap.domain.Vbeln;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDT1074D;
import hdel.lib.domain.BAPIRET2;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT1074;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.ExchangeD;
import hdel.sd.com.dao.TestindD;
import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.ses.dao.Ses0031D;
import hdel.sd.sso.dao.Sso0050D;
import hdel.sd.sso.dao.Sso0060ND;
import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.domain.ZSDS0060;
import hdel.sd.sso.domain.ZSDS0060T;
import hdel.sd.sso.domain.ZSDS0061;
import hdel.sd.sso.domain.ZSDS0062;
import hdel.sd.sso.domain.ZSDT1100;
import hdel.sd.sso.domain.ZSDT1101;
import hdel.sd.sso.domain.ZSDT1102;
import hdel.sd.sso.domain.ZSDT1103;
import tit.service.sapjco3.RfcException;
import tit.util.DatasetUtility;
import webservice.stub.sap.SAPStub;
import webservice.stub.sap.ZWEB_SD_VALID_QTDATStub;

/*
******************************************************************************************
* 기      능   : 수주생성 조회 및 저장(Sso0060NS) Service
* 작  성  자   : 박수근
* 작성  일자   : 2016.02.12
* 기능ID       : UF014
* ----------------------------------------------------------------------------------------
* HISTORY      :  2016.02.12 개선 내용 재 코딩 박수근
******************************************************************************************
*/

@Service
public class Sso0060NS extends SrmService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private Sso0060NJS sso0060NJS;

    private Sso0060ND sso0060NDao;
    private Ses0031D ses0031Dao;
    private Sso0050D sso0050Dao;
    private TestindD TestindDao;
    private ExchangeD ExchangeDao;
    private ZSDT1074D zsdt1074d;
    
	@Autowired
	private ExchangeS ExchangeS;
    
    public void createDao(SqlSession sqlSession) {
        sso0060NDao = sqlSession.getMapper(Sso0060ND.class);
        ses0031Dao = sqlSession.getMapper(Ses0031D.class);
        sso0050Dao = sqlSession.getMapper(Sso0050D.class);
        zsdt1074d = sqlSession.getMapper(ZSDT1074D.class);
        TestindDao = sqlSession.getMapper(TestindD.class);
        ExchangeDao = sqlSession.getMapper(ExchangeD.class);
    }
    
	// 프로젝트번호로 견적번호 조회
	public List<Sso0060> selectListQtnum(Sso0060ParamVO paramV) {
		return sso0060NDao.selectListQtnum(paramV);
	}

    // 견적번호로 입찰성공 견적차수 및 프로젝트번호 조회
    public List<Sso0060> selectListQtser(Sso0060ParamVO paramV) {
        return sso0060NDao.selectListQtser(paramV);
    }

    //=========================================================================================
    //  함수기능  : 임시 저장 견적서 정보 조회
    //  파라미터  : String qry_gb, Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_list_item Dataset ds_list_bill, Dataset ds_list_txt
    //  리턴값    : void( Dataset ds_dtl, Dataset ds_list_item, ds_error
    //  기능ID    : UF014
    //  개선내역  : 수주 사용자 입력 data 조회(수주생성임시저장)
    //=========================================================================================
    public boolean getTmpEstimatDataset(String zprgflg, String qry_gb, Sso0060ParamVO param, Dataset ds_list_partner, Dataset ds_manager, Dataset ds_dtl, Dataset ds_list_item,
    		                              Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error, MipResultVO resultVO) throws Exception {
    	boolean bIsData = false;

        // RFC INPUT PARAM DECLARE
        List<ZSDT1100>  list_head = null; // S/O 생성 HEADER DATA
        List<ZSDT1101>  list_item = null; // S/O 생성 ITEM DATA
        List<ZSDT1102>  list_bill = null; // S/O 생성 BILLING PLAN
        List<ZSDT1103>  list_txt  = null; // S/O 생성 TEXT

        // HEADER 조회
        list_head = sso0060NDao.selectTmpQtHeader(param);
        if( list_head.size() > 0 ) {
        	bIsData = true;
            CallSAPHdel.moveListToDs(ds_dtl, ZSDT1100.class, list_head);
            
            // ds_dtl CNT 값을 강제로 삽입함
            ds_dtl.setColumn(0, "CNT", "1");
            
            // ITEM 조회
            list_item = sso0060NDao.selectTmpQtItem(param);
            CallSAPHdel.moveListToDs(ds_list_item, ZSDT1101.class, list_item);

            // 청구계획 조회
            list_bill = sso0060NDao.selectTmpQtBiliingPlan(param);
            CallSAPHdel.moveListToDs(ds_list_bill, ZSDT1102.class, list_bill);

            // 텍스트 조회
            list_txt = sso0060NDao.selectTmpQtText(param);
            CallSAPHdel.moveListToDs(ds_list_txt, ZSDT1103.class, list_txt);

            if (ds_error.getRowCount() == 0) {
	           	// 견적정보 조회에서 오류가 없는 경우 견적마스터 부서정보 추가
	           	setEstimatAddVbVgr(zprgflg, param, ds_dtl);
	
	           	//견적정보 조회에서 오류가 없는 경우 파트너 정보 추가
	           	setEstimatAddPartner(qry_gb, param, ds_dtl, ds_list_partner, ds_list_item, ds_list_bill);
	           	
	           	// 견적정보 조회에서 오류가 없는 경우 담당자 정보 추가
		       	setEstimatAddManager(param, ds_dtl, ds_manager);
            }
            
            resultVO.addDataset        (ds_dtl);              // HEADER
            resultVO.addDataset        (ds_list_partner);     // PARTNER
            resultVO.addDataset		   (ds_manager);		  // MANAGER
            resultVO.addDataset        (ds_list_item);        // ITEM
            resultVO.addDataset        (ds_list_bill);        // BILLING PLAN
            resultVO.addDataset        (ds_list_txt);         // TEXT
        }

        return bIsData;
    }

    //=========================================================================================
    //  함수기능  : 견적번호로 수주생성 자료 작성을 위한 견적서 정보를 조회한다.
    //  파라미터  : Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_list_item
    //  리턴값    : void( Dataset ds_dtl, Dataset ds_list_item, ds_error
    //  기능ID    : UF014
    //  개선내역  : 수주 사용자 입력 data 조회(수주생성임시저장)
    //=========================================================================================
    public void getEstimatDataset(String zprgflg, String qry_gb, Sso0060ParamVO param, Dataset ds_list_partner, Dataset ds_manager, Dataset ds_dtl, Dataset ds_list_item,
                                   Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error, MipResultVO resultVO) throws Exception {
        List<Sso0060> list = null;
       
        list = sso0060NDao.selectListQtnumInfo(param);

        String sWAERK = "";
        String sAuart = "";
        String sQtdat = "";
        if (list != null && list.size() > 0)    {
            sWAERK = list.get(0).getWAERK();    // 화폐단위(견적헤더)
            sAuart = list.get(0).getAUART();    //
            sQtdat = list.get(0).getQTDAT();    // 견적일자
        }

        CallSAPHdel.moveListToDs(ds_dtl, Sso0060.class, list);          // DATASET 복사

        // 2.3 견적 DETAIL정보(품목 목록 ) 조회
        list = null;
        list = sso0060NDao.selectListQtnumItemInfo(param);
        //CallSAPHdel.moveListToDs(ds_list_item, Sso0060.class, list);      // DATASET 복사

        // 2.4 조회된 견적 DETAIL정보로 품목별 대수 만큼 품목 목록을 재생성하고 금액, 원가는 대수로 나누어 SETTING한다.
        // 2.4 조회된 견적 DETAIL정보로 품목별 대수 만큼 품목 목록을 재생성하고 금액, 원가는 대수로 나누어 SETTING한다.
        if ( list != null && list.size() > 0 )
        {
            int         nRow           = -1;          // 품목 데이타셋 행번호(0부터 시작)
            Integer     posnr           = 0;          // 품목번호 (100부터 시작)
            String      matnr           = "";         // 자재번호
            Double      netwr_bef       = null;      // 분할 전 금액
            Double      wavwr_bef       = null;      // 분할 전 원가
            Integer     znumber         = 0;          // 분할 대수
            BigDecimal  netwr_div       = null;      // 분할 후 금액
            BigDecimal  wavwr_div       = null;      // 분할 후 원가
            Double      netwr_def       = null;      // 분할 전 금액 - sum(분할 후 금액)
            Double      wavwr_def       = null;      // 분할 전 원가 - sum(분할 후 원가)

            /******************************************************************************
            * 2018.03.06 견적환율, 수주환율 가져오기 START 
            ******************************************************************************/
            BigDecimal amtEx0031 = null;
            BigDecimal amtEx0050 = null;
            
            // 현재 날짜 가져오기
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String sysDate = dateFormat.format(date);
            
            // 견적환율 = 견적일자 기준 
            String exchangeR = null;
			if (sWAERK != null && !"KRW".equals(sWAERK) && sAuart.substring(0,2).equals("ZE")) {
				exchangeR = ExchangeS.getExchangeRate(param.getMANDT(), "Q", sQtdat, sWAERK, "KRW");		
				if (Double.parseDouble(exchangeR) > 0 ) {
	                amtEx0031 = new BigDecimal(exchangeR);				 
				}
				
				// 수주환율 = 수주 생성일자 기준
	            exchangeR = "0";
				exchangeR = ExchangeS.getExchangeRate(param.getMANDT(), "M", sysDate, sWAERK, "KRW");		
				if (Double.parseDouble(exchangeR) > 0 ) {
					amtEx0050 = new BigDecimal(exchangeR);
					
					BigDecimal exchangR_dec = new BigDecimal(exchangeR);
					ds_dtl.setColumn(0, "EXCHRT", exchangR_dec.setScale(1,BigDecimal.ROUND_HALF_UP).toString()); 
				}
			} else if (sAuart.substring(0,2).equals("ZT")) {
				
				String waerkBas = ExchangeDao.searchWaerkBase(param.getMANDT(), sQtdat);
				String waerkCon = ExchangeDao.searchWaerkBase(param.getMANDT(), sysDate);
				String message = "견적 시 배포 통화(" + waerkBas + ") 와 수주 시 배포통화 (" + waerkCon +")가 상이 합니다."; 
				if ( !waerkBas.equals(waerkCon) ){
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", 
							message+System.getProperty("line.separator")
							+"견적을 다시 진행하시기 바랍니다.",
							"Y", "Y");
					return;
				}
				
				amtEx0031 = getExchangeRateTrade(param.getMANDT(), "Q", sQtdat, sWAERK );				
				amtEx0050 = getExchangeRateTrade(param.getMANDT(), "M", sysDate, sWAERK );
				ds_dtl.setColumn(0, "EXCHRT", amtEx0050.setScale(4,BigDecimal.ROUND_HALF_UP).toString()); 

			}			
            /******************************************************************************
            * 2018.03.06 견적환율, 수주환율 가져오기 END  
            ******************************************************************************/
			
/* -----< 2018.03.06 환율 관리 공통모듈화ExchangeS.getExchangeRate 처리에 따른 주석 by mj.Shin 		Start
            /******************************************************************************
            * 2013.02.20 변경 기준환율 가져오기 START
            ******************************************************************************
            BigDecimal amtEx0031 = null;
            BigDecimal amtEx0050 = null;
            // 현재 날짜 가져오기
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String sysDate = dateFormat.format(date);
            
            if ( sAuart.substring(0,2).equals("ZE") && !sWAERK.equals("KRW") ) {
                // 현재 날짜 가져오기
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String sysDate = dateFormat.format(date);
				
                // 견적 기준환율 가져오기
                Ses0031 ses0031 = ses0031Dao.selectNewExchange(param.getMANDT(), sQtdat);
                if ( ses0031 == null ) {
                    throw new BizException("CE10007");
                }
                if ( "USD".equals(sWAERK.toUpperCase()) ) {
                    String strAmtEx0031 = ses0031.getKrwusd();
                    if ( strAmtEx0031 == null ) {
                        throw new BizException("CE10007");
                    } else if ( strAmtEx0031.trim() == "" ) {
                        throw new BizException("CE10007");
                    } else if ( Double.parseDouble(strAmtEx0031) <= 0 ) {
                        throw new BizException("CE10007");
                    }
                    amtEx0031 = new BigDecimal(strAmtEx0031);
                } else if ( "JPY".equals(sWAERK.toUpperCase()) ) {
                    String strAmtEx0031 = ses0031.getKrwjpy();
                    if ( strAmtEx0031 == null ) {
                        throw new BizException("CE10007");
                    } else if ( strAmtEx0031.trim() == "" ) {
                        throw new BizException("CE10007");
                    } else if ( Double.parseDouble(strAmtEx0031) <= 0 ) {
                        throw new BizException("CE10007");
                    }
                    amtEx0031 = new BigDecimal(strAmtEx0031);
                } else if ( "EUR".equals(sWAERK.toUpperCase()) ) {
                    String strAmtEx0031 = ses0031.getKrweur();
                    if ( strAmtEx0031 == null ) {
                        throw new BizException("CE10007");
                    } else if ( strAmtEx0031.trim() == "" ) {
                        throw new BizException("CE10007");
                    } else if ( Double.parseDouble(strAmtEx0031) <= 0 ) {
                        throw new BizException("CE10007");
                    }
                    amtEx0031 = new BigDecimal(strAmtEx0031);
                }
				
                // 수주 기준환율 가져오기
                Sso0050ParamVO sso0050ParamVO = new Sso0050ParamVO();
                sso0050ParamVO.setMANDT(param.getMANDT());
                sso0050ParamVO.setTCURRDATE(sysDate);

                List<Sso0050VO> listEx0050 = sso0050Dao.selectListExchange(sso0050ParamVO);
                if ( listEx0050.size() > 0 ) {
                    if ( "USD".equals(sWAERK.toUpperCase()) ) {
                        String strAmtEx0050 = listEx0050.get(0).getKRWUSD();
                        if ( strAmtEx0050 == null ) {
                            throw new BizException("CE10007");
                        } else if ( strAmtEx0050.trim() == "" ) {
                            throw new BizException("CE10007");
                        } else if ( Double.parseDouble(strAmtEx0050) <= 0 ) {
                            throw new BizException("CE10007");
                        }
                        amtEx0050 = new BigDecimal(strAmtEx0050);
                    } else if ( "JPY".equals(sWAERK.toUpperCase()) ) {
                        String strAmtEx0050 = listEx0050.get(0).getKRWJPY();
                        if ( strAmtEx0050 == null ) {
                            throw new BizException("CE10007");
                        } else if ( strAmtEx0050.trim() == "" ) {
                            throw new BizException("CE10007");
                        } else if ( Double.parseDouble(strAmtEx0050) <= 0 ) {
                            throw new BizException("CE10007");
                        }
                        amtEx0050 = new BigDecimal(strAmtEx0050);
                    } else if ( "EUR".equals(sWAERK.toUpperCase()) ) {
                        String strAmtEx0050 = listEx0050.get(0).getKRWEUR();
                        if ( strAmtEx0050 == null ) {
                            throw new BizException("CE10007");
                        } else if ( strAmtEx0050.trim() == "" ) {
                            throw new BizException("CE10007");
                        } else if ( Double.parseDouble(strAmtEx0050) <= 0 ) {
                            throw new BizException("CE10007");
                        }
                        amtEx0050 = new BigDecimal(strAmtEx0050);
                    }
                }
                if ( amtEx0050 == null ) {
                    throw new BizException("CE10007");
                }
            }

            /******************************************************************************
            * 2013.02.20 변경 기준환율 가져오기 END
            ******************************************************************************
			
-----> 2018.03.06 환율 관리 공통모듈화ExchangeS.getExchangeRate 처리에 따른 주석 by mj.Shin 		End */
				
            String qtnum = param.getQTNUM();

            for( int i = 0; i < list.size(); i++ )
            {
                // 2.4.1 변수 SET
                netwr_bef     = list.get(i).getNETWR().doubleValue() ;                        // 분할 전 금액
                wavwr_bef     = list.get(i).getWAVWR().doubleValue() ;                        // 분할 전 원가
                if( qtnum.substring(0,1).equals("J") || qtnum.substring(0,1).equals("G") || qtnum.substring(0,1).equals("F")){
                    znumber     = 1;                                        // 분할 대수
                } else {
                    znumber     = list.get(i).getZNUMBER();                                   // 분할 대수
                }
                matnr         = list.get(i).getMATNR().toString();                            // 자재번호
                netwr_div     = new BigDecimal(list.get(i).getNETWR().doubleValue()/znumber);   // 분할 후 금액
                wavwr_div     = new BigDecimal(list.get(i).getWAVWR().doubleValue()/znumber);    // 분할 후 원가

                /******************************************************************************
                * 2013.02.20 변경 기준환율 적용 START
                ******************************************************************************/
                if ( sAuart.substring(0,2).equals("ZE") || sAuart.substring(0,2).equals("ZT") ) {
                    if ( amtEx0031 != null && amtEx0050 != null ) {
                        wavwr_div = new BigDecimal(wavwr_div.doubleValue()*amtEx0031.doubleValue());
                        wavwr_div = new BigDecimal(wavwr_div.doubleValue()/amtEx0050.doubleValue());

                        // 2013.03.19 분할 전 원가도 환율을 적용한 금액을 사용한다.
                        wavwr_bef = wavwr_bef*amtEx0031.doubleValue()/amtEx0050.doubleValue();
                        wavwr_bef = Double.valueOf((new java.text.DecimalFormat("#.##")).format(wavwr_bef));
                    }
                }
                /******************************************************************************
                * 2013.02.20 변경 기준환율 적용 END
                ******************************************************************************/

                // 2013.01.17 물류의 경우 USD의 경우 소수점 이하 3자리 절사
                if ("USD".equals(sWAERK) || "EUR".equals(sWAERK) || "MYR".equals(sWAERK))    { // 2013.02.26 USD,EUR,MYR은 소수 2자리(버림), JPY-소수 0자리 (반올림), KRW-소수 0자리
                    netwr_div    = netwr_div.setScale(2, RoundingMode.DOWN);                   // 금액을 원단위에서 절사
                    wavwr_div   = wavwr_div.setScale(2, RoundingMode.DOWN);                    // 원가를 원단위에서 절사
                } else if ("JPY".equals(sWAERK)) {
                    netwr_div    = netwr_div.setScale(0, RoundingMode.HALF_UP);               // 금액을 소수 1자리에서 반올림
                    wavwr_div   = wavwr_div.setScale(0, RoundingMode.HALF_UP);                // 원가를 소수 1자리에서 반올림
                } else {
                    netwr_div    = netwr_div.setScale(0, RoundingMode.DOWN);                   // 금액을 원단위에서 절사
                    wavwr_div   = wavwr_div.setScale(0, RoundingMode.DOWN);                    // 원가를 원단위에서 절사
                }

                // 2.4.2 분할 대수 만큼 Loop
                for( int j = 0; j < znumber; j++ )
                {
                    nRow++;                                                                     // ds_list_item 행번호
                    posnr = posnr + 100;                                                        // 품목번호
                    ds_list_item.appendRow();                                                   // 행추가
                    ds_list_item.setColumn(nRow, "QTSEQ", list.get(i).getQTSEQ().toString());   // 견적일련번호
                    ds_list_item.setColumn(nRow, "POSNR", StringUtils.leftPad(Integer.toString(posnr), 6, "0")); // 품목번호
                    ds_list_item.setColumn(nRow, "MATNR", matnr);                               // 자재번호
                    ds_list_item.setColumn(nRow, "NETWR", netwr_div.doubleValue());             // 분할금액
                    ds_list_item.setColumn(nRow, "WAVWR", wavwr_div.doubleValue());             // 분할원가
                    
                    // 2020 브랜드
                    ds_list_item.setColumn(nRow, "BRNDCD", list.get(i).getBRNDCD());
                    ds_list_item.setColumn(nRow, "BRNDSEQ", list.get(i).getBRNDSEQ());
                }     // end of for( int j = 0; j < znumber; j++ )

                // 2.4.3  분할 대수가 1 보다 큰 경우 품목 마지막 행의 금액, 원가 재설정
                //if (znumber > 1)
                //{ //대수 = 1인  경우도 BigDecimal 적용 시 차액 발생(해외영업) - 2014.01.28 ,신미정
                    // 차액 산출
                    netwr_def = netwr_bef - (netwr_div.doubleValue() * znumber.doubleValue());    // 금액 차액
                    wavwr_def = wavwr_bef - (wavwr_div.doubleValue() * znumber.doubleValue());    // 원가 차액
                    // 금액, 원가 재설정 (차액만큼 추가로 sum처리)
                    ds_list_item.setColumn(nRow, "NETWR", netwr_div.doubleValue() + netwr_def);  // 금액
                    ds_list_item.setColumn(nRow, "WAVWR", wavwr_div.doubleValue() + wavwr_def);  // 원가
                //}
            }         // end of for( int i = 0; i < list.size(); i++ )
            
            
            
        }             // end of if ( list != null && list.size() > 0)

        if (ds_error.getRowCount() == 0) {
           	// 견적정보 조회에서 오류가 없는 경우 견적마스터 부서정보 추가
           	setEstimatAddVbVgr(zprgflg, param, ds_dtl);

           	//견적정보 조회에서 오류가 없는 경우 파트너 정보 추가
           	setEstimatAddPartner(qry_gb, param, ds_dtl, ds_list_partner, ds_list_item, ds_list_bill);
           	
           	// 견적정보 조회에서 오류가 없는 경우 담당자 정보 추가
	       	setEstimatAddManager(param, ds_dtl, ds_manager);
        }

        resultVO.addDataset        (ds_dtl);              // HEADER
        resultVO.addDataset        (ds_list_partner);     // PARTNER
        resultVO.addDataset		   (ds_manager);		  // MANAGER
        resultVO.addDataset        (ds_list_item);        // ITEM
        resultVO.addDataset        (ds_list_bill);        // BILLING PLAN
        resultVO.addDataset        (ds_list_txt);         // TEXT
    }

    //=========================================================================================
    //  함수기능  : 견적번호로 수주생성 자료 작성을 위한 견적서 정보를 SAP XML 방식으로 호출한다.
    //  파라미터  : String cmd, String zprgflg, String qry_gb, Sso0060ParamVO param, Dataset ds_list_partner, Dataset ds_dtl, Dataset ds_list_item
    //              Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error
    //  리턴값    : void( Dataset ds_dtl, Dataset ds_list_item, ds_list_bill, ds_list_txt, ds_error )
    //  기능ID    : UF014
    //  개선내역  : 수주 사용자 입력 data 조회(수주생성임시저장)
    //=========================================================================================
    public void getSapXmlEstimatDataset(String sysid, String cmd, String zprgflg, String qry_gb, Sso0060ParamVO param, Dataset ds_list_partner,
    		                             Dataset ds_dtl, Dataset ds_list_item,
                                         Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error,
                                         MipResultVO resultVO) throws CommRfcException, Exception {
        // RFC INPUT PARAM DECLARE
        ZSDS0060[]  list_head	= new ZSDS0060[]{};      // S/O 생성 HEADER DATA
        ZSDS0061[]  list_item	= new ZSDS0061[]{};      // S/O 생성 ITEM DATA
        ZSDS0062[]  list_bill	= new ZSDS0062[]{};      // S/O 생성 BILLING PLAN
        ZSDS0060T[]	list_txt	= new ZSDS0060T[]{};      // S/O 생성 TEXT
        BAPIRET2[]	bapiret2	= new BAPIRET2[]{};

        // 3.3.1 RFC INPUT SET (파라메터 순서엄수)
        Object obj[] = new Object[]{
                                      list_txt            // O_목록 : ZSDS0060T : TEXT
                                    , list_bill            // O_목록 : ZSDS0062  : BILLING PLAN
                                    , cmd               // 처리구분 : DISP(조회), SAVE(저장), UPDT(변경)
                                    , param.getVBELN()    // 프로젝트번호
                                    , param.getQTNUM()    // 견적번호
                                    , param.getQTSER()    // 견적차수
                                    , list_item            // O_목록 : ZSDS0061  : ITEM
                                    , bapiret2
                                    , list_head            // O_목록 : ZSDS0060  : HEADER
                            };

        // 3.3.2 RFC CALL
        SapFunction stub = CallSAP.callSap(sysid, param.getMANDT() , "hdel.sd.sso.domain.ZWEB_SD_CREATE_SO" , obj, false);

        // 3.3.4 RFC 호출결과(T_ITAB,T_ITAB2)를 out dataset(ds)에 옮기기
        CallSAP.moveObj2Ds(ds_dtl        , stub.getOutput("T_ITAB"));         // HEADER
        CallSAP.moveObj2Ds(ds_list_item  , stub.getOutput("P_ITAB"));         // ITEM
        CallSAP.moveObj2Ds(ds_list_bill  , stub.getOutput("B_ITAB"));         // BILLING PLAN
        CallSAP.moveObj2Ds(ds_list_txt   , stub.getOutput("BIGO"));           // TEXT
        
        // 3.3.6 RFC 호출결과 오류정보
		if (((BAPIRET2[])stub.getOutput("TT_BAPIRET2")).length > 0) {
			for(BAPIRET2 bp2 : (BAPIRET2[])stub.getOutput("TT_BAPIRET2")) {
				if(bp2.getTYPE().equals("S"))
					continue;
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", bp2.getMESSAGE().toString(), "Y", "Y");
				break;
			}
			//SAP RFC 수행결과 return message Type이 S로만 구성되었는데도 처리실패인 경우가 발생하기 때문에 임시조치
			if(ds_error.getRowCount() == 0) {
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", ((BAPIRET2[])stub.getOutput("TT_BAPIRET2"))[0].getMESSAGE().toString(), "Y", "Y");
			}
		} else {
/*			2018.03.30  환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start
 * 			기존 : 가격결정일자 환율 읽어옴 / 변경 : 수주일자 환율 조회 / 사유 : 가격결정일자 프로젝트 결산 시 결산월의 마지막 일자로 변경됨.  
			//환율표기 2015.03.19
			Sso0060 Vo = sso0060NDao.selectExchangeRate(param);
            for(int i = 0; i< ds_dtl.getRowCount(); i++) {
                //ds_dtl.setColumn(i, "WAERK", ds_dtl.getColumn(i, "WAERK")+ "/" + Vo.getEXRATE().setScale(1,BigDecimal.ROUND_UP));
            	ds_dtl.setColumn(i, "EXCHRT", Vo.getEXRATE().setScale(1,BigDecimal.ROUND_UP).toString());
            }	
*/
			String Auart = ds_dtl.getColumnAsString(0,"AUART");
			String Waerk = ds_dtl.getColumnAsString(0, "WAERK");
			BigDecimal exchangR_dec = new BigDecimal("1.0");
			ExchangeVO exchangeRate = new ExchangeVO();
			ExchangeVO paramExchangeVO = new ExchangeVO();
			String Bstdk = dateReplace(ds_dtl.getColumnAsString(0, "BSTDK"));
			
			if (Auart.substring(0,2).equals("ZE")) {
				paramExchangeVO.setMandt(param.getMANDT());
				paramExchangeVO.setKurst("M");
				paramExchangeVO.setBasedt(Bstdk);
				paramExchangeVO.setFcurr(Waerk);
				paramExchangeVO.setTcurr("KRW");
				
				if (Waerk != null && !"KRW".equals(Waerk) ) {
					exchangeRate = ExchangeDao.selectSoExchange(paramExchangeVO);
					if (exchangeRate == null) {
						exchangR_dec = new BigDecimal("0.0");
					} else {
						exchangR_dec = new BigDecimal(exchangeRate.getUkurs());
					}
					ds_dtl.setColumn(0, "EXCHRT", exchangR_dec.setScale(1,BigDecimal.ROUND_UP).toString());
				} 
			} else if (Auart.substring(0,2).equals("ZT")) {
				exchangR_dec = getExchangeRateTrade(param.getMANDT(), "M", Bstdk, Waerk);
				ds_dtl.setColumn(0, "EXCHRT", exchangR_dec.setScale(4,BigDecimal.ROUND_UP).toString());
			}
			

//			2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End 
			
        	// 견적정보 조회에서 오류가 없는 경우 견적마스터 부서정보 추가
	       	setEstimatAddVbVgr(zprgflg, param, ds_dtl);
	       	//견적정보 조회에서 오류가 없는 경우 파트너 정보 추가
	       	setEstimatAddPartner(qry_gb, param, ds_dtl, ds_list_partner, ds_list_item, ds_list_bill);

	       	ZSDT1074 zsdt1074 = zsdt1074d.select(param.getMANDT(), new Vbeln(param.getVBELN()));
	       	if(zsdt1074 != null) {
		       	ds_dtl.setColumn(0, "LHCHK"	, zsdt1074.getLh());
		       	ds_dtl.setColumn(0, "KUNAPRV", zsdt1074.getKunaprv());
	       	}
        }

        resultVO.addDataset        (ds_dtl);              // HEADER
        resultVO.addDataset        (ds_list_partner);     // PARTNER
        resultVO.addDataset        (ds_list_item);        // ITEM
        resultVO.addDataset        (ds_list_bill);        // BILLING PLAN
        resultVO.addDataset        (ds_list_txt);         // TEXT
        resultVO.addDataset        (ds_error);	          // ERROR 
    }

    //=========================================================================================
    //  함수기능  : 견적정보 조회에서 오류가 없는 경우 견적마스터 부서정보 추가
    //  파라미터  : String zprgflg, Sso0060ParamVO param, Dataset ds_dtl
    //  기능ID    : UF014
    //  개선내역  : 수주 사용자 입력 data 조회(수주생성임시저장)
    //=========================================================================================
    public void setEstimatAddVbVgr(String zprgflg, Sso0060ParamVO param, Dataset ds_dtl) throws Exception {

    	// 4.1 HEAD 결과  dataset에 프로젝트번호, 견적번호, 견적차수 재설정
		ds_dtl.setColumn(0, "QTNUM"	, param.getQTNUM());	// 견적번호
		ds_dtl.setColumn(0, "QTSER"	, param.getQTSER());	// 견적차수
		ds_dtl.setColumn(0, "VBELN"	, param.getVBELN());	// 프로젝트번호
		// 2012.10.19 견적진행상태코드 추가반영
		ds_dtl.setColumn(0, "ZPRGFLG", zprgflg);	// 견적진행상태

		// 4.2 HEAD 결과  dataset에 부서명, 팀명  SET
		param.setVKBUR(ds_dtl.getColumnAsString(0, "VKBUR"));	// 부서코드
		param.setVKGRP(ds_dtl.getColumnAsString(0, "VKGRP"));	// 팀코드
		List<Sso0060> list = null;
		
		list = sso0060NDao.selectListVbVg(param);					// 부서명, 팀명 조회
		if (list != null && list.size()>0)
		{
			ds_dtl.setColumn(0, "VKBUR_NM"	, list.get(0).getVKBUR_NM().toString());	// 부서명
			ds_dtl.setColumn(0, "VKGRP_NM"	, list.get(0).getVKGRP_NM().toString()); 	// 팀명
		}

		// 4.3 HEAD 결과  dataset에 날짜형식항목 재설정
		ds_dtl.setColumn(0, "CONTR_DA"		, dateReplace(ds_dtl.getColumnAsString(0, "CONTR_DA"))); // 계약일
		ds_dtl.setColumn(0, "BSTDK"			, dateReplace(ds_dtl.getColumnAsString(0, "BSTDK"	))); // 수주일
		ds_dtl.setColumn(0, "RECAD_DA"		, dateReplace(ds_dtl.getColumnAsString(0, "RECAD_DA"))); // 수주승인일
		ds_dtl.setColumn(0, "VDATU"			, dateReplace(ds_dtl.getColumnAsString(0, "VDATU"	))); // 납품요청일
		ds_dtl.setColumn(0, "COMPL_DA"		, dateReplace(ds_dtl.getColumnAsString(0, "COMPL_DA"))); // 건물준공일
		ds_dtl.setColumn(0, "FINAL_DA"		, dateReplace(ds_dtl.getColumnAsString(0, "FINAL_DA"))); // 최종완료일
		ds_dtl.setColumn(0, "HB_FROM"		, dateReplace(ds_dtl.getColumnAsString(0, "HB_FROM"	))); // 하자보증기간_FROM
		ds_dtl.setColumn(0, "HB_TO"			, dateReplace(ds_dtl.getColumnAsString(0, "HB_TO"	))); // 하자보증기간_FROM
		ds_dtl.setColumn(0, "ACHDT"			, dateReplace(ds_dtl.getColumnAsString(0, "ACHDT"	))); // 건축 허가일자 
		ds_dtl.setColumn(0, "SOPDT"			, dateReplace(ds_dtl.getColumnAsString(0, "SOPDT"	))); // 개설일자
		ds_dtl.setColumn(0, "SOP_Y_REGDT"	, dateReplace(ds_dtl.getColumnAsString(0, "SOP_Y_REGDT"	))); // 개설등록일자
    }

    //=========================================================================================
    //  함수기능  : 견적정보 조회에서 오류가 없는 경우 파트너 정보 추가
    //  파라미터  : String qry_gb, Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_list_partner, Dataset ds_list_item, Dataset ds_list_bill
    //  기능ID    : UF014
    //  개선내역  : 수주 사용자 입력 data 조회(수주생성임시저장)
    //=========================================================================================
    public void setEstimatAddPartner(String qry_gb, Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_list_partner, Dataset ds_list_item, Dataset ds_list_bill) throws Exception {
		// 4.4 파트너정보를 조회하여  결과 dataset에 설정
		String kunnr_rg = "";	// 판매처
		String kunnr_we = "";	// 납품처
		String kunnr_z1 = "";	// 대리점
		String kunnr_z2 = "";	// 담당자
		String kunnr_z3 = "";	// 기술영업담당자
		for (int i = 0; i < 6; i++)
		{
			List<Sso0060> list = null;
			ds_list_partner.appendRow();

			if (i == 0)	// 판매처
			{
				kunnr_rg = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_RG"));
				if (StringUtils.isNotBlank(kunnr_rg))
				{
					param.setCODE(kunnr_rg);									// 조건_판매처코드
					list = sso0060NDao.selectListKunnrRg(param);					// 판매처정보 조회
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_RG");		// 파트너구분컬럼ID
			}

			else if (i == 1)	// 지급인  추가 kdh
			{
				if ("Q".equals(qry_gb))
				{
						kunnr_rg = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_RG"));
						if (StringUtils.isNotBlank(kunnr_rg))
						{
							param.setCODE(kunnr_rg);									// 조건_판매처코드
							list = sso0060NDao.selectListKunnrRg(param);					// 판매처정보 조회
						}
						ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_RG2");		// 파트너구분컬럼ID
				}
				else if ("P".equals(qry_gb))
				{
						kunnr_rg = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_RG2"));
						if (StringUtils.isNotBlank(kunnr_rg))
						{
							param.setCODE(kunnr_rg);									// 조건_판매처코드
							list = sso0060NDao.selectListKunnrRg(param);					// 판매처정보 조회
						}
						ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_RG2");		// 파트너구분컬럼ID
				}
			}

			else if (i == 2)	// 납품처
			{
				kunnr_we = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_WE"));
				if (StringUtils.isNotBlank(kunnr_we))
				{
					param.setCODE(kunnr_we);									// 조건_납품처코드
					list = sso0060NDao.selectListKunnrWe(param);					// 납품처정보 조회
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_WE");		// 파트너구분컬럼ID
			}

			else if (i == 3)	// 대리점
			{
				kunnr_z1 = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_Z1"));
				if (StringUtils.isNotBlank(kunnr_z1))
				{
					param.setCODE(kunnr_z1);									// 조건_대리점정보코드
					list = sso0060NDao.selectListKunnrZ1(param);					// 대리점정보정보 조회
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_Z1");		// 파트너구분컬럼ID
			}

			else if (i == 4)	// 담당자
			{
				kunnr_z2 = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_Z2"));
				if (StringUtils.isNotBlank(kunnr_z2))
				{
					param.setCODE(kunnr_z2);									// 조건_담당자정보코드
					list = sso0060NDao.selectListKunnrZ2(param);					// 담당자정보정보 조회
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_Z2");		// 파트너구분컬럼ID
			}

			else if (i == 5)	// 기술영업담당자
			{
				kunnr_z3 = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_Z3"));
				if (StringUtils.isNotBlank(kunnr_z3))
				{
					param.setCODE(kunnr_z3);									// 조건_기술영업담당자정보코드F
					list = sso0060NDao.selectListKunnrZ3(param);					// 기술영업담당자정보정보 조회
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_Z3");		// 파트너구분컬럼ID
			}
			// 조회된 자료가 존재할 경우 정보 SET
			if (list != null && list.size() > 0)
			{
				ds_list_partner.setColumn(i, "PARTNER_CD"	, list.get(0).getCODE().toString());
				ds_list_partner.setColumn(i, "PARTNER_NM"	, list.get(0).getNAME().toString());
				ds_list_partner.setColumn(i, "PARTNER_INFO"	, list.get(0).getINFO().toString());
			}
		}  // end of for (int i=0; i<5; i++)

		// 4.5 ITEM 결과  dataset에 컬럼 추가 및 날짜형식항목 재설정
		for (int i = 0; i < ds_list_item.getRowCount(); i++)
		{
			ds_list_item.setColumn(i, "CHK"	 , "0");
			ds_list_item.setColumn(i, "FLAG" , "" );
			ds_list_item.setColumn(i, "EDATU", dateReplace(ds_list_item.getColumnAsString(i, "EDATU"))); // 납기일
			ds_list_item.setColumn(i, "CONDA", dateReplace(ds_list_item.getColumnAsString(i, "CONDA"))); // 협의납기일
			ds_list_item.setColumn(i, "STADA", dateReplace(ds_list_item.getColumnAsString(i, "STADA"))); // 착공예정일
			ds_list_item.setColumn(i, "ZRMDA", dateReplace(ds_list_item.getColumnAsString(i, "ZRMDA"))); // 골조완료예정일
		}

		// 4.6 BILLING 결과  dataset에 날짜형식항목 재설정 및 빌링구분 설정
		for (int i = 0; i < ds_list_bill.getRowCount(); i++)
		{
			ds_list_bill.setColumn(i, "FKDAT", dateReplace(ds_list_bill.getColumnAsString(i, "FKDAT"))); // 청구계획일
			// 판매문서품목번호가 없는 경우
			if ("".equals(ds_list_bill.getColumnAsString(i, "POSNR"))
				 || "000000".equals(ds_list_bill.getColumnAsString(i, "POSNR"))
				)
			{
				ds_list_bill.setColumn(i, "BILL_GB", "H"); // 프로젝트빌링
			}
			// 판매문서품목번호가 있는 경우
			else
			{
				ds_list_bill.setColumn(i, "BILL_GB", "P"); // 호기별빌링
			}
		}
    }

    //=========================================================================================
    //  함수기능  : 수주 생성 저장,수정
    //  파라미터  : String cmd, String userId, Sso0060ParamVO 	param
    //              Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    //              Dataset ds_error
    //  리턴값    : void( Dataset ds_dtl, Dataset ds_list_item, ds_list_bill, ds_list_txt, ds_error )
    //  기능ID    : UF014, UF015
    //  개선내역  : 수주 생성 RFC API 변경
    //=========================================================================================
    public Vbeln saveSapXmlProject(String sysid, String cmd, String userId, Sso0060ParamVO 	param,
    		                       Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    		                       Dataset ds_error) throws RfcException, Exception {
    	Vbeln vbeln = new Vbeln();
    	// RFC INPUT PARAM DECLARE
		// input dataset을 CLASS() 에 옮기기
    	ZSDS0060[] 	list_head 	= (ZSDS0060[])CallSAPHdel.moveDs2Obj2(ds_dtl		, ZSDS0060.class, "");  // HEADER
    	ZSDS0061[] 	list_item 	= (ZSDS0061[])CallSAPHdel.moveDs2Obj2(ds_list_item	, ZSDS0061.class, "");	// ITEM
    	ZSDS0062[] 	list_bill 	= (ZSDS0062[])CallSAPHdel.moveDs2Obj2(ds_list_bill	, ZSDS0062.class, ""); 	// BILLING PLAN
    	ZSDS0060T[] list_txt 	= (ZSDS0060T[])CallSAPHdel.moveDs2Obj2(ds_list_txt	, ZSDS0060T.class, "");	// TEXT
        BAPIRET2[]	bapiret2	= new BAPIRET2[]{};

    	// 2013.02.25 소수점이하 자릿수 오류 문제로 원래 값을 다시 넣어줌
    	for ( int i = 0 ; i < list_item.length ; i++ ) {
    		BigDecimal bd = new BigDecimal(ds_list_item.getColumnAsString(i, "WAVWR"));
    		list_item[i].setWAVWR(new BigDecimal(bd.toPlainString()));
    	}

		Object obj[] = new Object[] {
				  list_txt										// O_목록 : ZSDS0060T : TEXT
				, list_bill										// O_목록 : ZSDS0062 : BILLING PLAN
				, cmd											// 처리구분(DISP:조회,SAVE:저장,UPDT:변경,DELE:삭제)
				, DatasetUtility.getString(ds_cond, "PSPID")  	// 프로젝트번호
				, DatasetUtility.getString(ds_cond, "QTNUM")	// 견적번호
				, DatasetUtility.getString(ds_cond, "QTSER")	// 견적차수
				, list_item										// O_목록 : ZSDS0061 : ITEM
				, bapiret2
				, list_head										// O_목록 : ZSDS0060 : HEADER
		};

		SapFunction stub = CallSAP.callSap(sysid, param.getMANDT()
										   , "hdel.sd.sso.domain.ZWEB_SD_CREATE_SO"
										   , obj, false);

		if (((BAPIRET2[])stub.getOutput("TT_BAPIRET2")).length > 0) {
			for(BAPIRET2 bp2 : (BAPIRET2[])stub.getOutput("TT_BAPIRET2")) {
				if(bp2.getTYPE().equals("S"))
					continue;
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", bp2.getMESSAGE().toString(), "Y", "Y");
				break;
			}
			//SAP RFC 수행결과 return message Type이 S로만 구성되었는데도 처리실패인 경우가 발생하기 때문에 임시조치
			if(ds_error.getRowCount() == 0) {
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", ((BAPIRET2[])stub.getOutput("TT_BAPIRET2"))[0].getMESSAGE().toString(), "Y", "Y");
			}
		} else {
			vbeln = new Vbeln(((ZSDS0060[])stub.getOutput("T_ITAB"))[0].getVBELN());
			ds_dtl.setColumn(0, "VBELN", vbeln.toString());

			ZSDT1074 zsdt1074 = new ZSDT1074();
			zsdt1074.setMandt(param.getMANDT());
			zsdt1074.setVbeln(vbeln);
			zsdt1074.setLh(DatasetUtility.getString(ds_dtl,"LHCHK"));
			zsdt1074.setKunaprv(DatasetUtility.getString(ds_dtl,"KUNAPRV"));
			zsdt1074.getTstmp().setTimeStamp(userId);

			// 2012.10.22 견적진행상태 변경 처리 견적진행상태를 수주생성 '90'
			if ( "SAVE".equals(cmd) || "DELE".equals(cmd) )	{
				Sso0060 uVO = new Sso0060();
				uVO.setMANDT(param.getMANDT());
				uVO.setQTNUM(param.getQTNUM());
				uVO.setQTSER(param.getQTSER());
				uVO.setUDATE(DateTime.getDateString());
				uVO.setUTIME(DateTime.getShortTimeString());
				uVO.setUUSER(userId);

				if ("SAVE".equals(cmd)) {
					uVO.setZPRGFLG("90");		// 수주진행상태를 수주작성
					sso0060NDao.updateZsdt1046Zprgflg(uVO);
					zsdt1074d.insert(zsdt1074);
				} 	// end of if ("SAVE".equals(cmd))
				else if ("DELE".equals(cmd)) {
					uVO.setZPRGFLG("50");		// 수주진행상태를 입찰결과등록이 삭제되어 상태를 입찰로 변경 '30' -> 50으로 변경
					sso0060NDao.updateZsdt1046Zprgflg(uVO);
					sso0060NDao.updateZsdt1001Sorlt(uVO);
				}
			} else if("UPDT".equals(cmd)) {
				zsdt1074d.update(zsdt1074);
			}
		}

		return vbeln;
    }

    //=========================================================================================
    //  함수기능  : 수주 생성/변경
    //  파라미터  : String cmd, Sso0060ParamVO 	param
    //              Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    //              Dataset ds_error
    //  리턴값    : void( Dataset ds_dtl, Dataset ds_list_item, ds_list_bill, ds_list_txt, ds_error )
    //  기능ID    : UF014, UF015
    //  개선내역  : 수주 생성 RFC API 변경
    //=========================================================================================
    public void saveSapJcoProject(String sysid, String cmd, String userId, Sso0060ParamVO 	param,
            Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
            Dataset ds_error, MipResultVO resultVO) throws RfcException, Exception {
    	
/*        Rfc rfc = null;

    	// 2013.02.25 소수점이하 자릿수 오류 문제로 원래 값을 다시 넣어줌
        for ( int i = 0 ; i < ds_list_item.getRowCount() ; i++ ) {
    		BigDecimal bd = new BigDecimal(ds_list_item.getColumnAsString(i, "WAVWR"));
    		ds_list_item.setColumn(i, "WAVWR", bd.toPlainString());
    	}

        // RFC CALL
        rfc = sso0060NJS.callProjectDataSet(sysid, cmd, param.getVBELN(), param.getQTNUM(), param.getQTSER(), "ZWEB_SD_CREATE_SO", ds_dtl, ds_list_item, ds_list_bill, ds_list_txt);
    	*/

    	ZSDS0060[] 	list_head 	= (ZSDS0060[])CallSAPHdel.moveDs2Obj2(ds_dtl		, ZSDS0060.class, "");  // HEADER
    	ZSDS0061[] 	list_item 	= (ZSDS0061[])CallSAPHdel.moveDs2Obj2(ds_list_item	, ZSDS0061.class, "");	// ITEM
    	ZSDS0062[] 	list_bill 	= (ZSDS0062[])CallSAPHdel.moveDs2Obj2(ds_list_bill	, ZSDS0062.class, ""); 	// BILLING PLAN
    	ZSDS0060T[] list_txt 	= (ZSDS0060T[])CallSAPHdel.moveDs2Obj2(ds_list_txt	, ZSDS0060T.class, "");	// TEXT
        BAPIRET2[]	bapiret2	= new BAPIRET2[]{};

		for(int i = 0 ; i < list_item.length ; i++ ) {
    		BigDecimal bd = new BigDecimal(ds_list_item.getColumnAsString(i, "WAVWR"));
    		list_item[i].setWAVWR(new BigDecimal(bd.toPlainString()));
    	}

		Object obj[] = new Object[] {
				  list_txt										// O_목록 : ZSDS0060T : TEXT
				, list_bill										// O_목록 : ZSDS0062 : BILLING PLAN
				, cmd											// 처리구분(DISP:조회,SAVE:저장,UPDT:변경,DELE:삭제)
				, param.getVBELN()
				, param.getQTNUM()
				, param.getQTSER()
				, list_item										// O_목록 : ZSDS0061 : ITEM
				, bapiret2
				, list_head										// O_목록 : ZSDS0060 : HEADER
		};

		SapFunction stub = CallSAP.callSap(sysid, param.getMANDT(), "ZWEB_SD_CREATE_SO", obj, false);
		if (((BAPIRET2[])stub.getOutput("TT_BAPIRET2")).length > 0) {
			for(BAPIRET2 bp2 : (BAPIRET2[])stub.getOutput("TT_BAPIRET2")) {
				if(bp2.getTYPE().equals("S"))
					continue;
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", bp2.getMESSAGE().toString(), "Y", "Y");
				resultVO.addDataset	(ds_error);
				break;
			}
			//SAP RFC 수행결과 return message Type이 S로만 구성되었는데도 처리실패인 경우가 발생하기 때문에 임시조치
			if(ds_error.getRowCount() == 0) {
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", ((BAPIRET2[])stub.getOutput("TT_BAPIRET2"))[0].getMESSAGE().toString(), "Y", "Y");
			}
		} else {
			// 2012.10.22 견적진행상태 변경 처리 견적진행상태를 수주생성 '90'
			if ( "SAVE".equals(cmd) || "DELE".equals(cmd) )	{
				Sso0060 uVO = new Sso0060();
				uVO.setMANDT(param.getMANDT());
				uVO.setQTNUM(param.getQTNUM());
				uVO.setQTSER(param.getQTSER());
				uVO.setUDATE(DateTime.getDateString());
				uVO.setUTIME(DateTime.getShortTimeString());
				uVO.setUUSER(userId);

				if ("SAVE".equals(cmd)) {
					uVO.setZPRGFLG("90");		// 수주진행상태를 수주작성
					sso0060NDao.updateZsdt1046Zprgflg(uVO);
				} 	// end of if ("SAVE".equals(cmd))
				else if ("DELE".equals(cmd)) {
					uVO.setZPRGFLG("50");		// 수주진행상태를 입찰결과등록이 삭제되어 상태를 입찰로 변경 '30' -> 50으로 변경
					sso0060NDao.updateZsdt1046Zprgflg(uVO);
					sso0060NDao.updateZsdt1001Sorlt(uVO);
				}
			}
		}    	
    }
    
    //=========================================================================================
    //  함수기능  : 수주정보 임시 저장
    //  파라미터  : Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    //              Dataset ds_error
    //  리턴값    : void
    //  기능ID    : UF014, UF015
    //  개선내역  : 수주정보 임시 저장 기능 추가
    //=========================================================================================
    public void saveTmpEstiMate(String userId, String mandt, Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, 
    		                     Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error) throws Exception {

		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// 견적번호
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// 견적차수

    	ZSDT1100[] 	list_head 	= (ZSDT1100[])CallSAPHdel.moveDs2Obj2(ds_dtl		, ZSDT1100.class, "");  // HEADER  
    	ZSDT1101[] 	list_item 	= (ZSDT1101[])CallSAPHdel.moveDs2Obj2(ds_list_item	, ZSDT1101.class, "");	 // ITEM 
    	ZSDT1102[] 	list_bill 	= (ZSDT1102[])CallSAPHdel.moveDs2Obj2(ds_list_bill	, ZSDT1102.class, ""); 	 // BILLING PLAN 
    	ZSDT1103[]  list_txt 	= (ZSDT1103[])CallSAPHdel.moveDs2Obj2(ds_list_txt	, ZSDT1103.class, "");	 // TEXT  

    	// header DB 저장/수정/삭제
    	// 최총 저장시에는 품목에 대한 초기 저장 값에 Flag값이 다르므로 별도 처리가 필요함
    	for(int i = 0; i < list_head.length; i ++ ) {
        	int iCount = 0;
    		list_head[i].setMANDT(mandt);
    		list_head[i].setQTNUM(qtnum);
    		list_head[i].setQTSER(qtser);
    		// 해당 값에 대해서 최초 입력값인지 확인한다.
    		// 날짜형 데이터 형변환(YYYYMMDD)
    		
    		list_head[i].setBSTDK(dateReplace(list_head[i].getBSTDK()));
    		list_head[i].setCOMPL_DA(dateReplace(list_head[i].getCOMPL_DA()));
    		list_head[i].setCONTR_DA(dateReplace(list_head[i].getCONTR_DA()));
    		list_head[i].setFINAL_DA(dateReplace(list_head[i].getFINAL_DA()));
    		list_head[i].setFRMDA(dateReplace(list_head[i].getFRMDA()));
    		list_head[i].setHB_FROM(dateReplace(list_head[i].getHB_FROM()));
    		list_head[i].setHB_TO(dateReplace(list_head[i].getHB_TO()));
    		list_head[i].setRECAD_DA(dateReplace(list_head[i].getRECAD_DA()));
    		list_head[i].setSTADA(dateReplace(list_head[i].getSTADA()));
    		list_head[i].setVDATU(dateReplace(list_head[i].getVDATU()));    		
    		list_head[i].setACHDT(dateReplace(list_head[i].getACHDT()));
    		list_head[i].setSOPDT(dateReplace(list_head[i].getSOPDT()));
    		
    		Sso0060ParamVO param = new Sso0060ParamVO();
    		param.setMANDT(mandt);
    		param.setQTNUM(qtnum);
    		param.setQTSER(qtser);
    		iCount = sso0060NDao.selectTmpCount(param);
    		//sso0060NDao.mergeTmpQtHeader(list_head[i]);
    		if( iCount == 0 ) {
    			sso0060NDao.insertTmpQtHeader(list_head[i]);
    		} else {
    			sso0060NDao.updateTmpQtHeader(list_head[i]);
    		}

        	// 삭제하고 입력한다.
        	sso0060NDao.deleteTmpQtBiliing(param);

        	// 삭제하고 입력한다.
        	sso0060NDao.deleteTmpQtMsTxtSer(param);
        	
        	// item DB 저장/수정/삭제
    		// int bilMaxSeq = 0;
    		// int txtMaxSeq = 0;
        	for(int j = 0; j < list_item.length; j ++ ) {
                // 최초 등록 시 품목정보 FLAG값이 "", "I", "U"로 들어 올수 있으나 모든 값에 대해서 I로 변경해서 처리한다.
        		if( iCount == 0 ) {
        			list_item[j].setFLAG("I");
        		} else {
        			list_item[j].setFLAG("U");
        			/*
        			if( list_bill.length > 0 ) {
        				bilMaxSeq = sso0060NDao.selectTmpQtBiliingMaxSeq(param);
        			}
                    if( list_txt.length > 0 ) {        			
    				    txtMaxSeq = sso0060NDao.selectTmpQtTxtMaxSeq(param);
                    }
                    **/
        		}

            	// 2013.02.25 소수점이하 자릿수 오류 문제로 원래 값을 다시 넣어줌
        		BigDecimal bd = new BigDecimal(ds_list_item.getColumnAsString(j, "WAVWR"));
        		list_item[j].setWAVWR(new BigDecimal(bd.toPlainString()));

        		list_item[j].setCONDA(dateReplace(list_item[j].getCONDA()));
        		list_item[j].setEDATU(dateReplace(list_item[j].getEDATU()));
        		list_item[j].setSTADA(dateReplace(list_item[j].getSTADA()));
        		list_item[j].setZRMDA(dateReplace(list_item[j].getZRMDA()));
        		
        		//if( list_item[j].getFLAG() != null ) {
        			list_item[j].setMANDT(mandt);
        			list_item[j].setQTNUM(qtnum);
        			list_item[j].setQTSER(qtser);
        			if( "I".equals(list_item[j].getFLAG())) {
        				sso0060NDao.insertTmpQtItem(list_item[j]);
        			} else if( "U".equals(list_item[j].getFLAG())) {
        				sso0060NDao.updateTmpQtItem(list_item[j]);
        			} else if( "D".equals(list_item[j].getFLAG())) {
        				sso0060NDao.deleteTmpQtItem(list_item[j]);
        			}
        		//}
        	}

        	// billingPlan 저장/수정/삭제
        	for(int j = 0; j < list_bill.length; j ++ ) {
        		//if( list_bill[j].getFLAG() != null ) {
        			list_bill[j].setMANDT(mandt);
        			list_bill[j].setQTNUM(qtnum);
        			list_bill[j].setQTSER(qtser);
        			list_bill[j].setSEQ(j);
        			list_bill[j].setFKDAT(dateReplace(list_bill[j].getFKDAT()));
        			
        			if( list_bill[j].getPOSNR() == null ) {
        				list_bill[j].setPOSNR("000000");
        			} else {
        				if( "".equals(list_bill[j].getPOSNR()) ) { 
        				    list_bill[j].setPOSNR("000000");  // 헤더 기준임
        				}
        			}
        			
        			sso0060NDao.insertTmpQtBiliingPlan(list_bill[j]);
        			/*
        			if( "I".equals(list_bill[j].getFLAG())) {
        				sso0060NDao.insertTmpQtBiliingPlan(list_bill[j]);
        			} else if( "U".equals(list_bill[j].getFLAG())) {
        				sso0060NDao.insertTmpQtBiliingPlan(list_bill[j]);
        				//sso0060NDao.updateTmpQtBiliingPlan(list_bill[j]);
        			} //else if( "D".equals(list_bill[j].getFLAG())) {
        			  //	sso0060NDao.deleteTmpQtBiliingPlan(list_bill[j]);
        			  //}
        		   */
        		//}
        	}

        	// 삭제하고 입력한다.
        	// 텍스트 저장/수정/삭제
        	for(int j = 0; j < list_txt.length; j ++ ) {
        		//if( list_txt[j].getFLAG() != null ) {
        			list_txt[j].setMANDT(mandt);
        			list_txt[j].setQTNUM(qtnum);
        			list_txt[j].setQTSER(qtser);
        			list_txt[j].setSER(new Integer(j).toString());
        			sso0060NDao.insertTmpQtText(list_txt[j]);
        			
        			/*
        			if( "I".equals(list_txt[j].getFLAG())) {
        				sso0060NDao.insertTmpQtText(list_txt[j]);
        			} else if( "U".equals(list_txt[j].getFLAG())) {
        				sso0060NDao.insertTmpQtText(list_txt[j]);
        				// sso0060NDao.updateTmpQtText(list_txt[j]);
        			} //else if( "D".equals(list_txt[j].getFLAG())) {
        				//sso0060NDao.deleteTmpQtText(list_txt[j]);
        			  //}
        			*/
        		//}
        	}    	
    	}
    }

    //=========================================================================================
    //  함수기능  : 수주정보 임시 저장 데이터 삭제
    //  파라미터  : Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    //              Dataset ds_error
    //  리턴값    : void
    //  기능ID    : UF014, UF015
    //  개선내역  : 수주정보 임시 저장 삭제
    //=========================================================================================
    public void deleteTmpEstiMateTable(String mandt, Dataset ds_cond) throws Exception {
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// 견적번호
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// 견적차수

		ZSDT1100 zSDT1100 = new ZSDT1100();
		zSDT1100.setMANDT(mandt);
		zSDT1100.setQTNUM(qtnum);
		zSDT1100.setQTSER(qtser);
		sso0060NDao.deleteTmpQtHeader(zSDT1100);
		
		ZSDT1101 zSDT1101 = new ZSDT1101();
		zSDT1101.setMANDT(mandt);
		zSDT1101.setQTNUM(qtnum);
		zSDT1101.setQTSER(qtser);
		sso0060NDao.deleteAllTmpQtItem(zSDT1101);

		Sso0060ParamVO param = new Sso0060ParamVO();
		param.setMANDT(mandt);
		param.setQTNUM(qtnum);
		param.setQTSER(qtser);
		sso0060NDao.deleteTmpQtBiliing(param);
		sso0060NDao.deleteTmpQtMsTxtSer(param);
    }
    
    //=========================================================================================
    //  함수기능  : 견적정보 조회에서 오류가 없는 경우 관리담당자 정보 추가
    //  파라미터  : Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_manager
    //=========================================================================================
    public void setEstimatAddManager(Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_manager) {
    	ds_manager.appendRow();
    	
    	String auart = StringUtils.trim(ds_dtl.getColumnAsString(0, "AUART"));
    	String zkunnr = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_Z2"));
    	
    	param.setZKUNNR(zkunnr);
    	
    	if (auart.equals("ZS02")) {
    		param.setSDFIELD("NI");
    	} else if (auart.equals("ZN02")) {
    		param.setSDFIELD("REM");
    	} else if (auart.equals("ZJ02")) {
    		param.setSDFIELD("PRK");
    	} else {
    		param.setSDFIELD("");
    	}
    	
    	List<Sso0060> managerList = sso0060NDao.selectListManager(param);
    	if (managerList.isEmpty()) {
    		ds_manager.setColumn(0, "MANAGER", "N");
    	}
    	else {
    		ds_manager.setColumn(0, "MANAGER", "Y");
    	}
    }

    // 날짜 타입의 데이타를 8자리로 재설정
	public String dateReplace(String pText)
	{ 
		String retText = pText;
		
		if( retText == null ) {
			return null;
		} else {
			retText = retText.replaceAll("[-]", "");
			retText = retText.replaceAll("[.]", "");
			retText = retText.replaceAll("[_]", "");
			if( retText.length() > 8 ) {
				retText = retText.substring(0, 8);	
			}
		}
		return retText;
	}
	
	public Boolean validQtdat(String sysid, String mandt, String qtdat, String kunnrZ2) throws ParseException, RemoteException  {
		if(qtdat == null)
			return false;

		ZWEB_SD_VALID_QTDATStub stub;
		ZWEB_SD_VALID_QTDATStub.ZWEB_SD_VALID_QTDAT param;
		ZWEB_SD_VALID_QTDATStub.ZWEB_SD_VALID_QTDATResponse response;

		stub = (ZWEB_SD_VALID_QTDATStub) new SAPStub(sysid, mandt, "").create(ZWEB_SD_VALID_QTDATStub.class);

		param = new ZWEB_SD_VALID_QTDATStub.ZWEB_SD_VALID_QTDAT();

		param.setIV_KUNNRZ2(ZWEB_SD_VALID_QTDATStub.Char10.Factory.fromString(kunnrZ2, ""));
		param.setIV_QTDAT(ZWEB_SD_VALID_QTDATStub.Date.Factory.fromString(qtdat.replaceFirst("(\\d{4})(\\d{2})(\\d+)", "$1-$2-$3"), ""));

		response = stub.zWEB_SD_VALID_QTDAT(param);
		return Boolean.parseBoolean(response.getEV_VALID().toString());
	}
	
	public BigDecimal getExchangeRateTrade(String mandt, String sKurst, String sGdatu, String sWAERK )  throws Exception {
		String waerkBas = null;
		String exchangeBas = null;				//기준통화
		String exchangeCon = null;				//계약통화
		BigDecimal rtnAmt = null;
		
		waerkBas = ExchangeDao.searchWaerkBase(mandt, sGdatu);
		if (!waerkBas.equals(sWAERK)) {
			exchangeBas = ExchangeS.getExchangeRate(mandt, sKurst, sGdatu, waerkBas, "KRW");		// 기준통화 -> 원화 
			if ("KRW".equals(sWAERK)){
				exchangeCon = "1";
			} else {
				exchangeCon = ExchangeS.getExchangeRate(mandt, sKurst, sGdatu, sWAERK, "KRW");		// 계약통화 -> 원화
			}
		}
		else {
			return new BigDecimal(1.0);
		}

		if (Double.parseDouble(exchangeBas) > 0 && Double.parseDouble(exchangeCon) > 0) {
			BigDecimal exchangeBas_dec = new BigDecimal(exchangeBas);
			BigDecimal exchangeCon_dec = new BigDecimal(exchangeCon);
			rtnAmt = exchangeCon_dec.divide(exchangeBas_dec, 4, BigDecimal.ROUND_HALF_UP);		
		}
		
		return rtnAmt;
	}

	public List<Sso0060> searchSopind(Sso0060ParamVO param) {
		return sso0060NDao.selectSopind(param);
	}
}