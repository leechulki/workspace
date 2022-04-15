package com.helco.xf.cs01.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.comm.Utillity;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class CS0108002B_ServiceImpl extends AbstractBusinessService implements CS0108002B_Service {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static Logger logger;

    /**
     * 서비스 BOM 정합성 체크 수행
     * @param BusinessContext ctx, Dataset ds_cond, Dataset ds_list2, Dataset ds_match_rslt
     * @throws Exception
     */
    @Override
    public void specMatchCheck(
                                BusinessContext ctx
                               ,Dataset ds_cond
                               ,Dataset ds_list_match
                               ,Dataset ds_match_rslt
                               ) throws Exception {
        logger = ServiceManagerFactory.getLogger();

        // 서비스 사양 리스트의 특성 코드를 hashMap 변수에 담는다.
        // 정합성 체크 메세지 에러 중 체크 오류에 대한 메세지는 던지지 않는다.
        // 발생된 오류에 대해서 값 전달용 데이터셑에 오류 메시지를 담든다.
        HashMap<String, String> inputKndValue = new HashMap<String, String>();
        HashMap<String, Integer> inputIndex   = new HashMap<String, Integer>();
        // 공사번호에 대한 값을 inputKndValue에 입력해 준다.
        String posid = ds_cond.getColumnAsString(0, "POSID");
        if( posid != null || !"".equals(posid) ) {
            inputKndValue.put("공사번호", ds_cond.getColumnAsString(0, "POSID"));
        }

        for (int i=0; i< ds_list_match.getRowCount(); i++) {
            String value = ds_list_match.getColumnAsString(i, "KNM");
            if (value == null) {
                String NUM = ds_list_match.getColumnAsString(i, "NUM");
                if( "Y".equals(NUM)) {
                    // 숫자형 값이 없는 경우 0값으로 처리한다.
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                } else {
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                }
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            } else if( "".equals(value) ) {
                String NUM = ds_list_match.getColumnAsString(i, "NUM");
                if( "Y".equals(NUM)) {
                    // 숫자형 값이 없는 경우 0값으로 처리한다.
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                } else {
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                }
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            } else {
                inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), value);
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            }
        }

        // 서비스 사양은 해당 입력값이 참인 경우에 오류로 간주하여 메시지를 표현한다.
        try {
            // db 접속을 통해 연산 수식 데이터 변수 구조를 생성한다.
            HashMap<String, MatchDutyCalcu> calcuMap = specCalcuMap(ctx);

            // DB 접속을 통해 체크 데이터를 생성한다.
            List<Match> matchList = specMatchList(ctx, ds_cond);

            // 정합성 체크 로직을 수행한다.
            specMatchExe(ds_cond
                       ,ds_list_match
                       ,ds_match_rslt
                       ,inputKndValue
                       ,inputIndex
                       ,matchList
                       ,calcuMap
                       );
        } catch(Exception e ) {
            e.printStackTrace();
            throw new SpecDutyException(e.getLocalizedMessage());
        }
    }

    /**
     * 정합성 체크 리스트 조회
     * @param ctx
     * @throws Exception
     */
    private List<Match> specMatchList(BusinessContext ctx, Dataset ds_cond) throws Exception {
        List<Match> matchList = new ArrayList<Match>();
        String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
        DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
        DatasetSqlRequest sqlReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002C_S1");

        sqlReq.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
        sqlReq.addParamObject("ds_cond", ds_cond);

        Dataset ds_spec_list = (Dataset)executor.query(sqlReq).getResultObject();

        String BLOCKNOSEQ;
        HashMap<String, Match> matchMap = new HashMap<String, Match>();
        List<String> matchKeyList = new ArrayList<String>();
        for (int i=0; i< ds_spec_list.getRowCount(); i++) {
            BLOCKNOSEQ = ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ");

            if( matchMap.containsKey(BLOCKNOSEQ) ) {
                // 키가 있는 경우
                Match match = (Match)matchMap.get(BLOCKNOSEQ);
                MatchDutyD matchDutyD = new MatchDutyD();
                matchDutyD.setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                matchDutyD.setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                matchDutyD.setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                matchDutyD.setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                matchDutyD.setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                matchDutyD.setZORDER(ds_spec_list.getColumnAsString(i, "ZORDER"));
                matchDutyD.setSPEC(ds_spec_list.getColumnAsString(i, "SPEC"));
                matchDutyD.setCALCULAT(ds_spec_list.getColumnAsString(i, "CALCULAT"));
                matchDutyD.setCALCULATCK(ds_spec_list.getColumnAsString(i, "CALCULATCK"));
                matchDutyD.setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));
                match.getMatchDutyList().add(matchDutyD);

            } else {
                // 키값이 없는 경우
                Match match = new Match();
                match.getMatchDuty().setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                match.getMatchDuty().setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                match.getMatchDuty().setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                match.getMatchDuty().setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                match.getMatchDuty().setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                match.getMatchDuty().setMESSAGE(ds_spec_list.getColumnAsString(i, "MESSAGE"));
                match.getMatchDuty().setTRANS(ds_spec_list.getColumnAsString(i, "TRANS"));
                match.getMatchDuty().setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));

                MatchDutyD matchDutyD = new MatchDutyD();
                matchDutyD.setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                matchDutyD.setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                matchDutyD.setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                matchDutyD.setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                matchDutyD.setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                matchDutyD.setZORDER(ds_spec_list.getColumnAsString(i, "ZORDER"));
                matchDutyD.setSPEC(ds_spec_list.getColumnAsString(i, "SPEC"));
                matchDutyD.setCALCULAT(ds_spec_list.getColumnAsString(i, "CALCULAT"));
                matchDutyD.setCALCULATCK(ds_spec_list.getColumnAsString(i, "CALCULATCK"));
                matchDutyD.setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));
                match.getMatchDutyList().add(matchDutyD);

                matchMap.put(BLOCKNOSEQ, match);
                matchKeyList.add(BLOCKNOSEQ);
            }
        }

        // 해당 조건을 리스트 변수로 치환하여 전달
        for(int j=0; j < matchKeyList.size(); j++ ) {
            matchList.add(matchMap.get(matchKeyList.get(j)));
        }

        return matchList;
    }

    /**
     * 정합성 체크 수행
     * @param Dataset ds_list2 Dataset ds_match_rslt HashMap<String, String> inputKndValue HashMap<String, Integer> inputIndex List<Match> matchList HashMap<String, MatchDutyCalcu> calcuMap
     * @throws Exception
     */
    private void specMatchExe(Dataset ds_cond
                               ,Dataset ds_list2
                               ,Dataset ds_match_rslt
                               ,HashMap<String, String> inputKndValue
                               ,HashMap<String, Integer> inputIndex
                               ,List<Match> matchList
                               ,HashMap<String, MatchDutyCalcu> calcuMap
                               ) throws Exception {
        int errIndex = 1;
        // 체크 로직에 연산식이 있는 경우에 우선 연산 데이터를 생성한 후 입력값을 보정한다.
        // 연산 SPECT : AAAA = {EL_AFQ} * 12
        // 해당 스펙별 연산 수행 결과 값을 담는 해쉬맵
        // 연산식 데이터 치환시 사용된 spec 코드를 담는다.
        HashMap<String, String> calcuMapSpec = new HashMap<String,String>();

        // 정합성 수행여부 결과 셑팅
        ds_cond.setColumn(0, "MATCHFLAG", "Y");
        ds_cond.setColumn(0, "TRNSYSNO", "Y");

        for(int i=0; i < matchList.size(); i++) {
            Match match = matchList.get(i);
            MatchDuty matchDuty = match.getMatchDuty();
            List<MatchDutyD> matchDutyDList = match.getMatchDutyList();

            boolean bRslt = true;
            for(int j=0; j < matchDutyDList.size(); j++) {
                MatchDutyD matchDutyD = matchDutyDList.get(j);
                // 체크 로직 수행
                // 만약 out 정보인 경우에는 계산해서 inputKndValue에 입력해 준다
                // 물론 입력 값은 산술연산에 의해서 값을 계산한다.
                // 파라미터: 스펙변환된 입력값, 정의된 연산식, 전체 입력값(변환 처리를 위해서)
                try {

                    // 연산수식결과를 처리한다.
                    if( "Y".equals(matchDutyD.getCALCULATCK())) {
                        // 연산처리 결과 미존재 시 연산처리를 수행한다.
                        if( !inputKndValue.containsKey(matchDutyD.getSPEC()) ) {
                            // 복합산술연산식을 수행한다.
                            // 수행된 결과는 inputKndValue 해쉬맵에 담는다.
                            specCalcuExe(matchDuty
                                        ,matchDutyD
                                        ,inputKndValue
                                        ,calcuMap
                                        ,calcuMapSpec
                                        );
                        }
                    }

                    // 체크 로직이 참이면 에러 메세지를 생성한다.
//                    logger.debug("조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n");
//                    if( inputKndValue.containsKey(matchDutyD.getSPEC()) ) {
//                        logger.debug("조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성값:"+inputKndValue.get(matchDutyD.getSPEC())+"\n");
//                    } else {
//                        logger.debug("조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성값:복합 연산 처리 결과 없음");
//                    }
//                    logger.debug("조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyD.getCALCULAT()+"\n");

                    if( MatchDutyOpr.serviceCompare(inputKndValue.get(matchDutyD.getSPEC()), matchDutyD.getCALCULAT(), inputKndValue) ) {
                        bRslt = true;
//                        logger.debug("조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 결과:"+bRslt);
                    } else {
                        bRslt = false;
//                        logger.debug("조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 결과:"+bRslt);
                        break;
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    String error = "조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n";
                    error = error +"조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성값:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
                    error = error +"조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyD.getCALCULAT()+"\n";
                    error = error +"조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 오류메세지:"+e.getLocalizedMessage();
                    throw new SpecDutyException(error);

/*
                    String error = "오류 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n";
                    error = error +"오류 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성값:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
                    error = error +"오류 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyD.getCALCULAT()+"\n";
                    error = error +"오류 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 오류메세지:"+e.getLocalizedMessage();
                    logger.debug(error);
*/
                }
            }

            //logger.debug("조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDuty.getBLOCKNOSEQ()+"] 최종 연산결과:"+bRslt);
            if( bRslt ) {
                // 잘못 입력된 자료 이미로 해당 데이터 셑 정보에 오류 에러 메시지를 출력한다.
                String sendYsno = "전송가능";
                for(int z=0; z < matchDutyDList.size(); z++) {
                    MatchDutyD matchDutyD = matchDutyDList.get(z);

                    int rows = ds_match_rslt.appendRow();
                    ds_match_rslt.setColumn(rows, "BLOCKNO", matchDutyD.getBLOCKNO());
                    ds_match_rslt.setColumn(rows, "BLOCKNOSEQ", matchDutyD.getBLOCKNOSEQ());
                    ds_match_rslt.setColumn(rows, "TRANS", matchDuty.getTRANS());

                    ds_match_rslt.setColumn(rows, "SEQN", new Integer(errIndex).toString());
                    if( !"Y".equals(matchDuty.getTRANS())) {
                        sendYsno = "전송불가";
                        // 정합성 전송불가 정보 셑팅 -> 화면에서 사용될 플래그값
                        ds_cond.setColumn(0, "TRNSYSNO", "N");
                    }
                    ds_match_rslt.setColumn(rows, "SENDYSNO", sendYsno);
                    ds_match_rslt.setColumn(rows, "ERR_MESSAGE", matchDuty.getMESSAGE());
                    // CNM
                    if( inputIndex.containsKey(matchDutyD.getSPEC())) {
                        ds_match_rslt.setColumn(rows, "CNM", ds_list2.getColumnAsString(inputIndex.get(matchDutyD.getSPEC()), "CNM"));
                    } else {
                        ds_match_rslt.setColumn(rows, "CNM", matchDutyD.getSPEC());
                    }

                    ds_match_rslt.setColumn(rows, "COD", matchDutyD.getSPEC());
                    ds_match_rslt.setColumn(rows, "CALCULAT", matchDutyD.getCALCULAT());

                    if( "Y".equals(matchDutyD.getCALCULATCK())) {
                        if( calcuMapSpec.containsKey(matchDutyD.getSPEC())) {
                            ds_match_rslt.setColumn(rows, "CALCOD", calcuMapSpec.get(matchDutyD.getSPEC()));
                        }

                        // 연산처리 결과가 존재하면 화면에 로그로 표시한다.
                        if( inputKndValue.containsKey(matchDutyD.getSPEC())) {
                            ds_match_rslt.setColumn(rows, "CALRESULT", inputKndValue.get(matchDutyD.getSPEC()));
                        }
                    }

//                    String error = "정합성 오류 ["+matchDutyD.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n";
//                    error = error +"정합성 오류 ["+matchDutyD.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성값:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
//                    error = error +"정합성 오류 ["+matchDutyD.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyD.getCALCULAT()+"\n";
                    if( "Y".equals(matchDutyD.getCALCULATCK())) {
                        // 연산처리 결과가 존재하면 화면에 로그로 표시한다.
                        if( inputKndValue.containsKey(matchDutyD.getSPEC())) {
//                            error = error +"정합성 오류 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산결과:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
                            ds_match_rslt.setColumn(rows, "CALRESULT", inputKndValue.get(matchDutyD.getSPEC()));
                        }
                    }
//                    error = error +"정합성 오류 ["+matchDutyD.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 메세지:"+matchDuty.getMESSAGE()+"\n";
//                    logger.debug(error);
                }
                errIndex = errIndex + 1;
            }
        }
        //logger.debug("입력 오류 메세지 Dataset:"+ds_match_rslt);
    }

    /**
     * 서비스 BOM Duty 수행
     * @param BusinessContext ctx, Dataset ds_cond, Dataset ds_list2, Dataset ds_match_rslt
     * @throws Exception
     */
    @Override
    public void specDutyCheck(
                                BusinessContext ctx
                               ,Dataset ds_cond
                               ,Dataset ds_list_match
                               ,Dataset ds_duty_rslt
                               ) throws Exception {
        logger = ServiceManagerFactory.getLogger();

        // 서비스 사양 리스트의 특성 코드를 hashMap 변수에 담는다.
        // 정합성 체크 메세지 에러 중 체크 오류에 대한 메세지는 던지지 않는다.
        // 발생된 오류에 대해서 값 전달용 데이터셑에 오류 메시지를 담든다.
        HashMap<String, String> inputKndValue = new HashMap<String, String>();
        HashMap<String, String> orgKndValue = new HashMap<String, String>();
        HashMap<String, Integer> inputIndex   = new HashMap<String, Integer>();
        // 공사번호에 대한 값을 inputKndValue에 입력해 준다.
        String posid = ds_cond.getColumnAsString(0, "POSID");
        if( posid != null || !"".equals(posid) ) {
            inputKndValue.put("공사번호", ds_cond.getColumnAsString(0, "POSID"));
        }

        for (int i=0; i< ds_list_match.getRowCount(); i++) {
            String value = ds_list_match.getColumnAsString(i, "KNM");
            if (value == null) {
                String NUM = ds_list_match.getColumnAsString(i, "NUM");
                if( "Y".equals(NUM)) {
                    // 숫자형 값이 없는 경우 0값으로 처리한다.
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                    orgKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                } else {
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                    orgKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                }
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            } else if( "".equals(value) ) {
                String NUM = ds_list_match.getColumnAsString(i, "NUM");
                if( "Y".equals(NUM)) {
                    // 숫자형 값이 없는 경우 0값으로 처리한다.
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                    orgKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                } else {
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                    orgKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                }
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            } else {
                inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), value);
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            }
        }

        // spec type 정의 -> 화면에서 정의
        //ds_cond.setColumn(0, "PARAMTYPE", "EL");
        //ds_cond.setColumn(0, "PARAMCTYPE", "M");

        // 서비스 사양은 해당 입력값이 참인 경우에 오류로 간주하여 메시지를 표현한다.
        try {
            // db 접속을 통해 연산 수식 데이터 변수 구조를 생성한다.
            HashMap<String, MatchDutyCalcu> calcuMap = specCalcuMap(ctx);

            // 정합성 Duty 로직을 수행한다.
            // F001 프로젝트 종류 AN 신규 또는 리모델링 공사에 대해서만 Duty를 수행한다.
            List<Match> dutyList = specDutyList(ctx, ds_cond);
            specDutyExe(ds_cond
                       ,ds_list_match
                       ,ds_duty_rslt
                       ,inputKndValue
                       ,inputIndex
                       ,dutyList
                       ,calcuMap
                       ,orgKndValue
                       );
        } catch(Exception e ) {
            e.printStackTrace();
            throw new SpecDutyException(e.getLocalizedMessage());
        }
    }

    /**
     * 정합성 DUTY 리스트 조회
     * @param ctx
     * @throws Exception
     */
    private List<Match> specDutyList(BusinessContext ctx, Dataset ds_cond) throws Exception {
        List<Match> dutyList = new ArrayList<Match>();
        String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
        DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
        // 정합성 Duty 리스트를 조회한다.
        DatasetSqlRequest sqlReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002C_S3");

        sqlReq.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
        sqlReq.addParamObject("ds_cond", ds_cond);

        Dataset ds_spec_list = (Dataset)executor.query(sqlReq).getResultObject();

        String BLOCKNOSEQ;
        HashMap<String, Match> matchMap = new HashMap<String, Match>();
        List<String> matchKeyList = new ArrayList<String>();
        for (int i=0; i< ds_spec_list.getRowCount(); i++) {
            BLOCKNOSEQ = ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ");

            if( matchMap.containsKey(BLOCKNOSEQ) ) {
                // 키가 있는 경우
                Match match = (Match)matchMap.get(BLOCKNOSEQ);
                MatchDutyD matchDutyD = new MatchDutyD();
                matchDutyD.setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                matchDutyD.setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                matchDutyD.setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                matchDutyD.setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                matchDutyD.setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                matchDutyD.setZORDER(ds_spec_list.getColumnAsString(i, "ZORDER"));
                matchDutyD.setSPEC(ds_spec_list.getColumnAsString(i, "SPEC"));
                matchDutyD.setCALCULAT(ds_spec_list.getColumnAsString(i, "CALCULAT"));
                matchDutyD.setCALCULATCK(ds_spec_list.getColumnAsString(i, "CALCULATCK"));
                matchDutyD.setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));
                match.getMatchDutyList().add(matchDutyD);

            } else {
                // 키값이 없는 경우
                Match match = new Match();
                match.getMatchDuty().setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                match.getMatchDuty().setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                match.getMatchDuty().setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                match.getMatchDuty().setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                match.getMatchDuty().setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                match.getMatchDuty().setMESSAGE(ds_spec_list.getColumnAsString(i, "MESSAGE"));
                match.getMatchDuty().setTRANS(ds_spec_list.getColumnAsString(i, "TRANS"));
                match.getMatchDuty().setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));

                MatchDutyD matchDutyD = new MatchDutyD();
                matchDutyD.setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                matchDutyD.setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                matchDutyD.setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                matchDutyD.setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                matchDutyD.setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                matchDutyD.setZORDER(ds_spec_list.getColumnAsString(i, "ZORDER"));
                matchDutyD.setSPEC(ds_spec_list.getColumnAsString(i, "SPEC"));
                matchDutyD.setCALCULAT(ds_spec_list.getColumnAsString(i, "CALCULAT"));
                matchDutyD.setCALCULATCK(ds_spec_list.getColumnAsString(i, "CALCULATCK"));
                matchDutyD.setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));
                match.getMatchDutyList().add(matchDutyD);

                matchMap.put(BLOCKNOSEQ, match);
                matchKeyList.add(BLOCKNOSEQ);
            }
        }

        // 해당 조건을 리스트 변수로 치환하여 전달
        for(int j=0; j < matchKeyList.size(); j++ ) {
            dutyList.add(matchMap.get(matchKeyList.get(j)));
        }

        return dutyList;
    }

    /**
     * 정합성 Duty 수행
     * @param Dataset ds_list2 Dataset ds_match_rslt HashMap<String, String> inputKndValue HashMap<String, Integer> inputIndex List<Match> matchList HashMap<String, MatchDutyCalcu> calcuMap
     * @throws Exception
     */
    private void specDutyExe(Dataset ds_cond
                              ,Dataset ds_list2
                              ,Dataset ds_duty_rslt
                              ,HashMap<String, String> inputKndValue
                              ,HashMap<String, Integer> inputIndex
                              ,List<Match> dutyList
                              ,HashMap<String, MatchDutyCalcu> calcuMap
                              ,HashMap<String, String> orgKndValue
                               ) throws Exception {
        // 체크 로직에 연산식이 있는 경우에 우선 연산 데이터를 생성한 후 입력값을 보정한다.
        // 연산 SPECT : AAAA = {EL_AFQ} * 12
        // 해당 스펙별 연산 수행 결과 값을 담는 해쉬맵
        // 연산식 데이터 치환시 사용된 spec 코드를 담는다.
        HashMap<String, String> calcuMapSpec = new HashMap<String,String>();
        for(int i=0; i < dutyList.size(); i++) {
            Match match = dutyList.get(i);
            MatchDuty matchDuty = match.getMatchDuty();
            List<MatchDutyD> matchDutyDList = match.getMatchDutyList();

            boolean bRslt = true;
            for(int j=0; j < matchDutyDList.size(); j++) {
                MatchDutyD matchDutyD = matchDutyDList.get(j);
                // 체크 로직 수행
                // 만약 out 정보인 경우에는 계산해서 inputKndValue에 입력해 준다
                // 물론 입력 값은 산술연산에 의해서 값을 계산한다.
                // 파라미터: 스펙변환된 입력값, 정의된 연산식, 전체 입력값(변환 처리를 위해서)
                try {

                    // 연산수식결과를 처리한다.
                    if( "Y".equals(matchDutyD.getCALCULATCK())) {
                        // 연산처리 결과 미존재 시 연산처리를 수행한다.
                        if( !inputKndValue.containsKey(matchDutyD.getSPEC()) ) {
                            // 복합산술연산식을 수행한다.
                            // 수행된 결과는 inputKndValue 해쉬맵에 담는다.
                            specCalcuExe(matchDuty
                                        ,matchDutyD
                                        ,inputKndValue
                                        ,calcuMap
                                        ,calcuMapSpec
                                        );
                        }
                    }

                    // 만약 정합성 체크 데이터 수식에 {} 해당 존재할 경우 ds_duty_rslt에 해당 입력값을 반영한다.
                    if( matchDutyD.getCALCULAT().indexOf("{") > -1 && matchDutyD.getCALCULAT().indexOf("}") > -1 ) {
                        if( bRslt ) {
                        	// 계산값을 변경하여 입력한다.
                            String checkValue = "";
                            String dutyValue = "";
                            checkValue = matchDutyD.getCALCULAT().replace("{", "").replace("}", "");
                            
                            if( inputKndValue.containsKey(checkValue) ) {
                                 dutyValue = (String)inputKndValue.get(checkValue);
                            } else {
                                // duty에서 직접 입력한 값을 특성값으로 적용한다.
                                dutyValue = checkValue;
                            }

                            if( dutyValue != null ) {
                            	if( orgKndValue.containsKey(matchDutyD.getSPEC())) {
                                    String knm = orgKndValue.get(matchDutyD.getSPEC());
                                    if( !dutyValue.toString().equals("") && !dutyValue.toString().equals(knm) ) {
                                        int rows = ds_duty_rslt.appendRow();
                                        ds_duty_rslt.setColumn(rows, "COD", matchDutyD.getSPEC());
                                        ds_duty_rslt.setColumn(rows, "KNM", dutyValue);
                                        ds_duty_rslt.setColumn(rows, "DUTYMSG", matchDuty.getMESSAGE());
                                    }
                            	} else {
                            		// 더미 연산식에 의해서 정의된 경우 input 값에 추가해 준다.
                                    if( calcuMap.containsKey(matchDutyD.getSPEC()) ) {
                                		String fomat = (String)calcuMap.get(matchDutyD.getSPEC()).getFOMAT();
                                		if( "E".equals(fomat) ) {
                                        	inputKndValue.put(matchDutyD.getSPEC(), dutyValue);
                                		}
                                    }
                            	}
                            }
                        }
                    } else {
                        // 체크 로직이 참이면 에러 메세지를 생성한다.
                        //logger.debug("Duty 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n");
                        if( inputKndValue.containsKey(matchDutyD.getSPEC()) ) {
                            //logger.debug("Duty 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성값:"+inputKndValue.get(matchDutyD.getSPEC())+"\n");
                        } else {
                            //logger.debug("Duty 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성값:복합 연산 처리 결과 없음");
                        }
                        //logger.debug("Duty 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyD.getCALCULAT()+"\n");

                        // DUTY 에서는 입력값이 없는 경우 true로 처리해서 동작한다.
                        if( matchDutyD.getCALCULAT() == null ) {
                            bRslt = true;
                        } else {
                            // duty check 값이 null 이면 무조건 true
                            if( matchDutyD.getCALCULAT().equals("") ) {
                                bRslt = true;
                            } else {
                                if( MatchDutyOpr.serviceCompare(inputKndValue.get(matchDutyD.getSPEC()), matchDutyD.getCALCULAT(), inputKndValue) ) {
                                    bRslt = true;
//                                    logger.debug("Duty 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 결과:"+bRslt);
                                } else {
                                    bRslt = false;
//                                    logger.debug("Duty 조건연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 결과:"+bRslt);
                                    break;
                                }
                            }
                        }
                    }
                } catch(Exception e) {
                    String error = "Duty 조건연산식 에러["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n";
                    error = error +"Duty 조건연산식 에러["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성값:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
                    error = error +"Duty 조건연산식 에러["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyD.getCALCULAT()+"\n";
                    error = error +"Duty 조건연산식 에러["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 오류메세지:"+e.getLocalizedMessage();
                    e.printStackTrace();
                    throw new SpecDutyException(error);
                }
            }
        }
    }

    /**
     * 정합성 체크 연산수식 정보 조회
     * @param ctx
     * @throws Exception
     */
    private HashMap<String, MatchDutyCalcu> specCalcuMap(BusinessContext ctx) throws Exception {
        HashMap<String, MatchDutyCalcu> calcuMap = new HashMap<String, MatchDutyCalcu>();
        String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
        DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
        DatasetSqlRequest sqlReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002C_S2");
        sqlReq.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
        Dataset ds_calcu_list = (Dataset)executor.query(sqlReq).getResultObject();

        String SPEC = "";
        for (int i=0; i< ds_calcu_list.getRowCount(); i++) {
            SPEC = ds_calcu_list.getColumnAsString(i, "SPEC");
            MatchDutyCalcu matchDutyCalcu = new MatchDutyCalcu();
            matchDutyCalcu.setMANDT(ds_calcu_list.getColumnAsString(i, "MANDT"));
            matchDutyCalcu.setSPEC(ds_calcu_list.getColumnAsString(i, "SPEC"));
            matchDutyCalcu.setCALCULAT(ds_calcu_list.getColumnAsString(i, "CALCULAT"));
            matchDutyCalcu.setFOMAT(ds_calcu_list.getColumnAsString(i, "FOMAT"));
            matchDutyCalcu.setBIGO(ds_calcu_list.getColumnAsString(i, "BIGO"));
            calcuMap.put(SPEC, matchDutyCalcu);
        }
        return calcuMap;
    }

    /**
     * 복합 산술연산 수행
     * @param ctx
     * @throws SpecDutyException
     * @throws Exception
     */
    private void specCalcuExe(MatchDuty matchDuty
                              ,MatchDutyD matchDutyD
                              ,HashMap<String, String> inputKndValue
                              ,HashMap<String, MatchDutyCalcu> calcuMap
                              ,HashMap<String, String> calcuMapSpec) throws SpecDutyException {
        if( calcuMap.containsKey(matchDutyD.getSPEC()) ) {
            MatchDutyCalcu matchDutyCalcu = calcuMap.get(matchDutyD.getSPEC());
            // 연산식이 없는 경우에는 Dummy 변수 이므로 연삭수식을 실행하지 않는다.
            if( !"".equals(matchDutyCalcu.getCALCULAT()) ) {
                String spec     = matchDutyCalcu.getSPEC();
                String calType  = matchDutyCalcu.getFOMAT();
                String calculat = MatchDutyOpr.calValueChange(spec, matchDutyCalcu.getCALCULAT(), inputKndValue, calcuMapSpec);
                String format = "";
                String calRslt = "";
                try {
                    /* 연산 수식 유형(FORMAT)
                    A: 산술연산
                    AT,2: 산술연산 후 소수점2자리 절삭
                    AR,2: 산술연산 후 소수점2자리 반올림
                    B: 조건수식 문자결과 리턴
                    C: 조건수식 산술연산 숫자결과 리턴
                    CT,2: 조건수식 산술연산 후 소수점2자리 절삭
                    CR,2: 조건수식 산술연산 후 소수점2자리 반올림
                    D: 개별 조건 문자결과 리턴
                    */
//                    logger.debug("복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n");
//                    logger.debug("복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyCalcu.getCALCULAT()+"\n");
//                    logger.debug("복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식 포맷:"+matchDutyCalcu.getFOMAT()+"\n");
//                    logger.debug("복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 치환 연산식:"+calculat+"\n");

                    if( calType.indexOf("A") > -1 ) {
                        if( calType.length() > 1 ) {
                            format = calType.substring(1);
                        }
                        calRslt = MatchDutyOpr.calculation(spec, calculat, format, inputKndValue);
                        inputKndValue.put(spec, calRslt);
//                        logger.debug("복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산결과:"+calRslt+"\n");
                    } else if( calType.indexOf("B") > -1 ) {
                        calRslt = MatchDutyOpr.ifElseStringFunction(spec, calculat);
                        inputKndValue.put(spec, calRslt);
//                        logger.debug("복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산결과:"+calRslt+"\n");
                    } else if( calType.indexOf("C") > -1 ) {
                        if( calType.length() > 1 ) {
                            format = calType.substring(1);
                        }
                        calRslt = MatchDutyOpr.ifElseNumFunction(spec, format, calculat);
                        inputKndValue.put(spec, calRslt);
//                        logger.debug("복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산결과:"+calRslt+"\n");
                    } else if( calType.indexOf("D") > -1 ) {
                        calRslt = MatchDutyOpr.ifStringFunction(spec, calculat);
                        inputKndValue.put(spec, calRslt);
//                        logger.debug("복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산결과:"+calRslt+"\n");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    String error = "오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n";
                    error = error +"오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyCalcu.getCALCULAT()+"\n";
                    error = error +"오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 치환 연산식:"+calculat+"\n";
                    error = error +"오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 오류메세지:"+e.getLocalizedMessage();
                    throw new SpecDutyException(error);
                    /*
                    String error = "오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n";
                    error = error +"오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 연산식:"+matchDutyCalcu.getCALCULAT()+"\n";
                    error = error +"오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 치환 연산식:"+calculat+"\n";
                    error = error +"오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 오류메세지:"+e.getLocalizedMessage();
                    logger.debug(error);
                    */
                }
            }
        } else {
            // 정의된 연산 수식인 없는 경우
            String error = "오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 특성코드:"+matchDutyD.getSPEC()+"\n";
            error = error +"오류 복합연산식 ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] 오류메세지:정의된 연산식이 없습니다.";
            logger.debug(error);
            throw new SpecDutyException(error);
        }
    }

    /**
     * 정합성 연산식 검증
     * @param ctx
     * @throws Exception
     */
    @Override
    public void specBomMatchVerify(BusinessContext ctx, Dataset ds_cond)
            throws Exception {

        // 정합성 처리를 위한 변수값을 0값으로 변경 후 정합성 처리 로직 수행
        String calculation =  ds_cond.getColumnAsString(0, "CALCULAT");
        String calType = ds_cond.getColumnAsString(0, "FOMAT");
        String regexp = "\\{(\\w+)\\}";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(calculation.replaceAll(System.getProperty("line.separator"), ""));
        StringBuffer result = new StringBuffer();
        while ( matcher.find()) {
            matcher.appendReplacement( result, "1");
        }
        matcher.appendTail(result);
        calculation = result.toString();

        try {
            if( calType.indexOf("A") > -1 ) {
                MatchDutyOpr.calculation("A", calculation, calType, null);
            } else if( calType.indexOf("B") > -1 ) {
                MatchDutyOpr.ifElseStringFunction("B", calculation);
            } else if( calType.indexOf("C") > -1 ) {
                MatchDutyOpr.ifElseNumFunction("C", calType, calculation);
            } else if( calType.indexOf("D") > -1 ) {
                MatchDutyOpr.ifStringFunction("D", calculation);
            }
        } catch(Exception e) {
            String error = "연산식 검증 오류:"+e.getLocalizedMessage();
            throw new SpecDutyException(error);
        }
    }
}
