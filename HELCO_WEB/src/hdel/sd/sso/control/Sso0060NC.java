package hdel.sd.sso.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.OrderNo;
import com.sap.domain.OrderItem;
import com.sap.domain.SapBool;
import com.sap.domain.Vbeln;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0057;
import hdel.lib.domain.ZSDT1046;
import hdel.lib.service.ZSDT0057S;
import hdel.lib.service.ZSDT1046S;
import hdel.sd.com.domain.Com0160;
import hdel.sd.com.domain.Com0160ParamVO;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.service.Com0160S;
import hdel.sd.com.service.ComboS;
import hdel.sd.com.service.Coms01AS;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.service.Sso0060NS;
import tit.util.DatasetUtility;
import webservice.stub.sap.SAPStub;
import webservice.stub.sap.ZWEB_SD_IS_STO_ORDERStub;
import webservice.stub.sap.ZWEB_SD_SET_STO_INFOStub;

/*
 ******************************************************************************************
 * 기      능   : 수주생성 조회 및 저장(Sso0060C) Control
 * @Comment
 *         1. View find ( 기준년월에 해당하는 마감년월 조회 )
 *         2. View find         ( 매출채권 명세 및 채권현황(청구별) 목록 조회 )
 *            - T-CODE             :     ZSDR152
 *            - RFC URL              :     (조회)                     http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FBE0081D32E0110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *            - RFC OPERATION NAME :     (미수, 부도 조회)            ZWEB_SD_CHN_152         (ZSDS0034)
 *                                   (수금리포트 조회)            ZWEB_SD_CHN_152         (ZSDS0036)
 *            - RFC OBJ NAME          :     (미수, 부도 조회)            ZSDS0034
 *                                   (수금리포트 조회)            ZSDS0036
 *         3. View update_plan ( 기준년월에 해당하는 미수, 부도의 수금계획변경 )
 *            - T-CODE             :     ZSDR152
 *            - RFC URL              :     (미수, 부도 수금계획 저장)     http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FC4981489370110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *            - RFC OPERATION NAME :     (미수, 부도 수금계획 저장)    ZWEB_SD_CHN_152_PLAN    (ZSDS0039)
 *            - RFC OBJ NAME          :     (미수, 부도 수금계획 저장)    ZSDS0039
 *         4. View update_reason ( 기준년월에 해당하는 미수, 부도의 채권사유변경 )
 *            - T-CODE             :     ZSDR152
 *            - RFC URL              :     (미수, 부도 채권사유 저장)     http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FC22D3D89930018E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *            - RFC OPERATION NAME :     (미수, 부도 채권사유 저장)     ZWEB_SD_CHN_152_RES       (ZSDT0053)
 *            - RFC OBJ NAME          :     (미수, 부도 채권사유 저장)     ZSDT0053
 * 작  성  자   : 박수근
 * 작성  일자   : 2016.02.12
 * 기능ID       : UF014
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 개선 내용 재 코딩 박수근
 ******************************************************************************************
*/

@Controller
@RequestMapping("sso0060N")
public class Sso0060NC {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SrmView view;

    @Autowired
    private SrmSqlSession sqlSession;

    @Autowired
    private Sso0060NS sso0060NS;
    @Autowired
    private ZSDT0057S zsdt0057s;

    @Autowired
    private ZSDT1046S zsdt1046s;
    
    @Autowired
    private ComboS comboS;

    @Autowired
    private Com0160S com0160S;
    
    @Autowired
    private ExchangeS ExchangeS;

    // 2020 브랜드
    @Autowired
    private Coms01AS coms01AS;
    
    //=========================================================================================
    //  함수기능  : 수주변경 요청시 필요한 공용코드 정보 조회(문서유형, 건물용도, 지급조건, 인도조건, 종별구분)
    //  파라미터  : 문서유형(ds_auart_cd)
    //              건물용도(ds_cond_build)
    //              지급조건(ds_list_zterm)
    //              인도조건(ds_list_inco1)
    //              종별구분(ds_list_mlbez)
    //  리턴값    :
    //  기능ID    : UF014
    //  개선내역  : 공통코드 개별 액션 호출 로직을 하나의 액션 호출로 변경
    //=========================================================================================
    @RequestMapping(value="CommonCode")
    public View Sso0060CommonCode(MipParameterVO paramVO, Model model) {
        // dataset을 return
        MipResultVO resultVO = new MipResultVO();
        Dataset     ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

        try {
            // DB 접속용 컨넥션
            comboS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

            // 문서유형 데이터셋
            Dataset ds_auart_list = paramVO.getDataset("ds_auart_cd");

            // 파라미터 선언
            ComboParamVO comboParamVO = new ComboParamVO();
            comboParamVO.setMandt(paramVO.getVariable("G_MANDT")); // 채널

            // 서비스호출
            List<ComboVO> listAuartCombo = comboS.selectAuartNm(comboParamVO);     // 오더유형코드, 오더유형명 조회

            // 호출결과(listCombo)를 데이타셋(ds_list)에 복사
            ds_auart_list.deleteAll();
            for (int iRow=0;iRow<listAuartCombo.size();iRow++) {
                ds_auart_list.appendRow();
                ds_auart_list.setColumn(iRow, "CODE"        , listAuartCombo.get(iRow).getCode());         // 코드
                ds_auart_list.setColumn(iRow, "CODE_NAME"    , listAuartCombo.get(iRow).getCodeName());    // 코드명
            }
            resultVO.addDataset(ds_auart_list);

            // OUTPUT DATASET DECLARE
            // 건물용도 데이터셋
            Dataset dsCond = paramVO.getDataset("ds_cond_build");
            
            // UI로 return할 out dataset
            Dataset ds_list_build         = paramVO.getDataset("ds_list_build");
            Dataset ds_list_build_l       = paramVO.getDataset("ds_list_build_l"); //2020.12 jss
            
            com0160S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

            Com0160ParamVO com0160ParamVO = new Com0160ParamVO();
            // SAP CLIENT
            com0160ParamVO.setMandt(paramVO.getVariable("G_MANDT"));
            com0160ParamVO.setBuilduse(DatasetUtility.getString(dsCond,"BUILDUSE"));
            com0160ParamVO.setStext(DatasetUtility.getString(dsCond,"STEXT"));
            com0160ParamVO.setWhere(DatasetUtility.getString(dsCond,"WHERE"));
            
            // 서비스CALL
            List<Com0160> listBuild = com0160S.find(com0160ParamVO);
            List<Com0160> listBuild_l = com0160S.find_l(com0160ParamVO); //2020.12 jss

            ds_list_build.deleteAll();
            // 호출결과(list)를 데이타셋(ds_list_build)에 복사
            for (int iRow=0;iRow<listBuild.size();iRow++) {
                // 행추가
                ds_list_build.appendRow();
                // 컬럼SET
                for (int iCol=0;iCol<ds_list_build.getColumnCount();iCol++) {
                    if (ds_list_build.getColumnID(iCol).equals("BUILDUSE")) {
                        // 거래처코드
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getBuilduse());
                    } 
                    else if (ds_list_build.getColumnID(iCol).equals("STEXT")) {
                        // 거래처명
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getStext());
                    }
                    //2020.11.30 jss
                    else if (ds_list_build.getColumnID(iCol).equals("BUILDUSE_L")) {
                        //건물용도대분류
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getBuilduse_l());
                    }
                }
            }
            
            //2020.12 jss : 건물용도 대분류 세팅
            ds_list_build_l.deleteAll();
            for (int iRow=0;iRow<listBuild_l.size();iRow++) {
                // 행추가
                ds_list_build_l.appendRow();
                // 컬럼SET
                for (int iCol=0;iCol<ds_list_build_l.getColumnCount();iCol++) {
                    if (ds_list_build_l.getColumnID(iCol).equals("BUILDUSE_L")) {
                        // 거래처코드
                        ds_list_build_l.setColumn(iRow, iCol    , listBuild_l.get(iRow).getBuilduse_l());
                    } 
                    else if (ds_list_build_l.getColumnID(iCol).equals("STEXT")) {
                        // 거래처명
                        ds_list_build_l.setColumn(iRow, iCol    , listBuild_l.get(iRow).getStext());
                    }
                }
            }
            
            resultVO.addDataset(ds_list_build);
            resultVO.addDataset(ds_list_build_l); //2020.12 jss

            // 지급조건 ds_list_zterm
            // INPUT/OUTPUT DATASET GET
            Dataset ds_list_zterm = paramVO.getDataset("ds_list_zterm");

            // 서비스호출
            // 지급조건 조회
            List<ComboVO> listZtermCombo = comboS.selectListZterm(comboParamVO);

            // 호출결과(listCombo)를 데이타셋(ds_list)에 복사
            ds_list_zterm.deleteAll();
            for (int iRow=0;iRow<listZtermCombo.size();iRow++) {
                ds_list_zterm.appendRow();
                ds_list_zterm.setColumn(iRow, "CODE"        , listZtermCombo.get(iRow).getCode());         // 코드
                ds_list_zterm.setColumn(iRow, "CODE_NAME"    , listZtermCombo.get(iRow).getCodeName());    // 코드명
            }
            resultVO.addDataset(ds_list_zterm);

            // 인도조건(ds_list_inco1)
            Dataset ds_list_inco1 = paramVO.getDataset("ds_list_inco1");
            // 서비스호출
            List<ComboVO> listIncoCombo = comboS.selectListInco(comboParamVO);     // 인도조건 조회
            ds_list_inco1.deleteAll();
            for (int iRow=0;iRow<listIncoCombo.size();iRow++) {
                ds_list_inco1.appendRow();
                ds_list_inco1.setColumn(iRow, "CODE"        , listIncoCombo.get(iRow).getCode());         // 코드
                ds_list_inco1.setColumn(iRow, "CODE_NAME"    , listIncoCombo.get(iRow).getCodeName());    // 코드명
            }
            resultVO.addDataset(ds_list_inco1);

            // 종별구분(ds_list_mlbez)
            Dataset ds_list_mlbez = paramVO.getDataset("ds_list_mlbez");
            // 서비스호출
            List<ComboVO> listMlbezCombo = comboS.selectListMlbez(comboParamVO);     // 종별구분 조회

            // 호출결과(listCombo)를 데이타셋(ds_list)에 복사
            ds_list_mlbez.deleteAll();
            for (int iRow=0;iRow<listMlbezCombo.size();iRow++)     {
                ds_list_mlbez.appendRow();
                ds_list_mlbez.setColumn(iRow, "CODE"        , listMlbezCombo.get(iRow).getCode());         // 코드
                ds_list_mlbez.setColumn(iRow, "CODE_NAME"    , listMlbezCombo.get(iRow).getCodeName());    // 코드명
            }
            resultVO.addDataset(ds_list_mlbez);
            
            
            // 2020 브랜드코드 조회
            coms01AS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
            Dataset ds_list_abrand = paramVO.getDataset("ds_list_abrand");
            comboParamVO.setVkbur("EL_ABRAND");
            
            // 서비스호출
            List<ComboVO> listAbrand = coms01AS.selectAtwrt(comboParamVO);     // 종별구분 조회
            
            ds_list_abrand.deleteAll();
            for (int iRow=0;iRow<listAbrand.size();iRow++)     {
            	ds_list_abrand.appendRow();
            	ds_list_abrand.setColumn(iRow, "CODE"       , listAbrand.get(iRow).getCode());        // 코드
            	ds_list_abrand.setColumn(iRow, "CODE_NAME"  , listAbrand.get(iRow).getCodeName());    // 코드명
            }
            resultVO.addDataset(ds_list_abrand);
        } catch(Exception e) {
            // 서버단과 화면단에서 에러에 대한 부분 처리로직 필요함
            logger.error(e.getMessage());
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        } finally {
            // 반드시 수행되어야 할 처리 로직
            // 만약 서비스 단에서 error 데이터  존재하는 경우에 리턴값 정의
        }

        // 지급조건
        model.addAttribute("resultVO", resultVO);
        return view;
    }

    //=========================================================================================
    //  함수기능  : 견적번호 또는 견적자료 또는 프로젝트번호에 해당하는 수주생성자료 조회 수주 사용자 입력 data 조회
    //  파라미터  : MipParameterVO paramVO, Model model
    //  리턴값    : MipResultVO resultVO
    //  기능ID    : UF014
    //  개선내역  : 수주 사용자 입력 data 조회(수주생성임시저장)
    //=========================================================================================
    @RequestMapping(value="findEstimateCreate")
    public View Sso0060EstiMateCreateFindView(MipParameterVO paramVO, Model model) {
        // INPUT DATASET GET
        Dataset ds_cond         = paramVO.getDataset("ds_cond");             // IN_검색조건
        Dataset ds_dtl          = paramVO.getDataset("ds_dtl");              // UI로 return할 out dataset (HEADER)
        Dataset ds_list_partner = paramVO.getDataset("ds_list_partner");     // OUT_파트너정보
        Dataset ds_list_item    = paramVO.getDataset("ds_list_item");        // UI로 return할 out dataset (ITEM)
        Dataset ds_list_bill    = paramVO.getDataset("ds_list_bill");        // UI로 return할 out dataset (BILLING PLAN)
        Dataset ds_list_txt     = paramVO.getDataset("ds_list_txt");         // UI로 return할 out dataset (TEXT)
        Dataset ds_manager		= paramVO.getDataset("ds_manager");

        // 변수 GET
        String mandt             = paramVO.getVariable("G_MANDT");                // IN_CLIENT
        String cmd               = DatasetUtility.getString(ds_cond,"CMD") ;      // 처리구분(DISP:조회, SAVE:수주생성, UPDT:수주승인전변경)
        String qry_gb            = DatasetUtility.getString(ds_cond,"QRY_GB") ;   // 조회구분(Q:견적번호,P:프로젝트)
        String qtnum             = DatasetUtility.getString(ds_cond,"QTNUM")  ;   // 견적번호
        String qtser             = DatasetUtility.getString(ds_cond,"QTSER")  ;   // 견적차수
        String pspid             = DatasetUtility.getString(ds_cond,"PSPID") ;    // 프로젝트번호
        // 2012.10.19 견적진행상태 코드 추가 반영
        String zprgflg          = "";                                             // 견적진행상태 조회정보값.
        
		
        // OUTPUT DATASET DECLARE
        Dataset     ds_error    = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

        // 결과 VIEW
        MipResultVO resultVO = new MipResultVO();
        
        //--------------------------------------------------------------------------------------------
        // INPUT PARAM INFO
        // sap client (개발 : 310 or 910)
        logger.debug("paramVO.G_MANDT     ===>"+mandt    );     // CLIENT
        logger.debug("ds_cond.CMD         ===>"+cmd);           // 처리구분(DISP:조회, SAVE:수주생성, UPDT:수주승인전변경)
        logger.debug("ds_cond.QRY_GB      ===>"+qry_gb);        // 조회구분(Q:견적번호,P:프로젝트)
        logger.debug("ds_cond.QTNUM       ===>"+qtnum);         // 견적번호
        logger.debug("ds_cond.QTSER       ===>"+qtser);         // 견적차수
        logger.debug("ds_cond.PSPID       ===>"+pspid );        // 프로젝트번호
        //--------------------------------------------------------------------------------------------

        try {
            sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
            zsdt1046s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));                        
            ExchangeS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

            // IN, OUT class declare
            List<Sso0060>     list     = null;                    // 결과리스트SET
            Sso0060ParamVO    param    = new Sso0060ParamVO();    // 조건
            param.setMANDT(mandt);                                // 조건_SAP CLIENT
            param.setQTNUM(qtnum);                                // 견적번호

            logger.debug("1.1 견적번호로 입찰성공한 견적차수 미존재시 오류 RETURN");
            list = sso0060NS.selectListQtser(param);

            // 견적테이블에 프로젝트번호에 해당하는 자료 미존재 시
            if (list == null || list.size() == 0) {
                // "CW10013", "${}는 입찰완료정보가 존재하지 않는 견적번호입니다.");
                ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW10013", "", "Y", "Y");
                ds_error.setId("ds_error");               // ERROR
                model.addAttribute("resultVO", resultVO);
                logger.debug("입찰완료정보 미존재 견적번호 : [" + qtnum + "]");
                return view;
            }

            // 1.2 조회한 프로젝트번호 SET
            logger.debug("1.2 조회한 프로젝트번호 SET");
            pspid   = list.get(0).getVBELN();    // 프로젝트번호
            qtnum   = list.get(0).getQTNUM();    // 견적번호
            qtser   = list.get(0).getQTSER();    // 견적차수
            zprgflg = list.get(0).getZPRGFLG();  // 견적진행상태코드
            logger.debug("견적진행상태:"+zprgflg);
            logger.debug("pspid:"+pspid);
            
            ZSDT1046 zsdt1046 = zsdt1046s.select(mandt, qtnum, Integer.parseInt(qtser));

            // 1.3 견적HEADER T/B에 프로젝트번호가 존재할 경우 기 수주생성된 견적자료이므로
            // SAP FUNCTION CALL로 변경한다.
            logger.debug("1.3 견적HEADER T/B에 프로젝트번호가 존재할 경우 기 수주생성된 견적자료이므로 SAP FUNCTION CALL로 변경한다. ");
            if (StringUtils.isNotBlank(pspid))     {
                logger.debug("getSapXmlEstimatDataset 프로젝트번호로 수주생성자료 조회");
                // 3.1 프로젝트번호로 견적번호, 견적차수 조회
                logger.debug("프로젝트번호로 견적번호, 견적차수 조회");
                param.setQTSER(qtser);    // 견적일련번호
                param.setVBELN(pspid);    // 프로젝트번호
                qry_gb = "P";

                // XML 방식 SAP 호출 방식
                logger.debug("XML 방식 데이터 조회");
                sso0060NS.getSapXmlEstimatDataset(paramVO.getVariable("G_SYSID"), cmd, zprgflg, qry_gb, param, ds_list_partner, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error, resultVO);
                // JCO 방식 SAP 호출 방식
                //logger.debug("JCO 방식 SAP 호출 방식");
                //sso0060NS.getSapJcoEstimatDataset(cmd, zprgflg, qry_gb, param, ds_list_partner, ds_error, resultVO);
                
                resultVO.addVariable("F_TMPSEARCH", "N");
            }
            else {
                // 파라미터 선언
                logger.debug("2.1 조회 파라메터 설정");
                param.setQTSER(qtser);    // 견적일련번호
                
                // 임시 견적서 정보를 조회한다.
                boolean bIsData = sso0060NS.getTmpEstimatDataset(zprgflg, qry_gb, param, ds_list_partner, ds_manager, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error, resultVO);  
                
                // 임시 견적서 정보가 없는 경우 견적서 테이블에서 정보를 조회한다.
                if( !bIsData ) {
                    // 템프테이블에 자료가 없는 경우 견적서 테이블에서 해당 자료를 조회한다.
                    // 2.2 견적 HEADER정보 조회   (파트너정보도 함께 조회)
                    sso0060NS.getEstimatDataset(zprgflg, qry_gb, param, ds_list_partner, ds_manager, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error, resultVO);
                    //수주생성시 견적 계약형태 연동 -- 2020.07.23 by eunha
                    String lifnrchk = zsdt1046.getLifnrchk();
                    if (lifnrchk.equals("1")) {
                    	ds_dtl.setColumn(0, "LIFNRCHK", "1");
                    	ds_dtl.setColumn(0, "LIFNR", "2000");
                    }
                    else if (lifnrchk.equals("3") || lifnrchk.equals("4")) {
                    	ds_dtl.setColumn(0, "LIFNRCHK", "2");
                    }

                    resultVO.addVariable("F_TMPSEARCH", "N");
                } else {
                	resultVO.addVariable("F_TMPSEARCH", "Y");
                }
                
            }
            
            ds_dtl.setColumn(0, "QTDAT", zsdt1046.getQtdat());
            logger.debug("최종조회구분      : " + qry_gb);
            logger.debug("최종 프로젝트번호 : " + pspid);
            logger.debug("최종 견적번호     : " + qtnum);
            logger.debug("최종 견적차수     : " + qtser);
        } catch(DataAccessException de) {
        	SQLException se = (SQLException)((DataAccessException)de).getRootCause();
        	logger.error("DataAccessException:"+se.getMessage());
            logger.error("Exception:"+de.getMessage());
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", de.getMessage(), "Y", "Y");
        } catch(Exception e) {
            // 서버단과 화면단에서 에러에 대한 부분 처리로직 필요함
            logger.error("Exception:"+e.getMessage());
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        } finally {
            // 반드시 수행되어야 할 처리 로직
            // 만약 서비스 단에서 error 데이터  존재하는 경우에 리턴값 정의
            // 5. RFC 출력 dataset을 return
            resultVO.addDataset        (ds_error);            // ERROR
            model.addAttribute        ("resultVO", resultVO);
            logger.debug("Sso0060FindView addAttribute end" + "");
        }
        
        return view;
    }

    //=========================================================================================
    //  함수기능  : 수주변경 자료 조회
    //  파라미터  : MipParameterVO paramVO, Model model
    //  리턴값    : MipResultVO resultVO
    //  기능ID    : UF015
    //  개선내역  : 기존 수주변경 자료 조회 메소드 분리 
    //=========================================================================================
    @RequestMapping(value="findOrderChange")
    public View Sso0060OrderChangeFindView(MipParameterVO paramVO, Model model) {
        // INPUT DATASET GET
        Dataset ds_cond         = paramVO.getDataset("ds_cond");             // IN_검색조건
        @SuppressWarnings("unused")
		Dataset ds_dtl          = paramVO.getDataset("ds_dtl");              // UI로 return할 out dataset (HEADER)
        Dataset ds_sto          = paramVO.getDataset("ds_sto");
        Dataset ds_list_partner = paramVO.getDataset("ds_list_partner");     // OUT_파트너정보
        Dataset ds_manager		= paramVO.getDataset("ds_manager");			 // OUT_관리담당자정보
        @SuppressWarnings("unused")
		Dataset ds_list_item    = paramVO.getDataset("ds_list_item");        // UI로 return할 out dataset (ITEM)
        @SuppressWarnings("unused")
		Dataset ds_list_bill    = paramVO.getDataset("ds_list_bill");        // UI로 return할 out dataset (BILLING PLAN)
        @SuppressWarnings("unused")
		Dataset ds_list_txt     = paramVO.getDataset("ds_list_txt");         // UI로 return할 out dataset (TEXT)

        // 변수 GET
        String mandt             = paramVO.getVariable("G_MANDT");                // IN_CLIENT
        String cmd               = DatasetUtility.getString(ds_cond,"CMD") ;      // 처리구분(DISP:조회, SAVE:수주생성, UPDT:수주승인전변경)
        String qry_gb            = DatasetUtility.getString(ds_cond,"QRY_GB") ;   // 조회구분(Q:견적번호,P:프로젝트)
        String qtnum             = DatasetUtility.getString(ds_cond,"QTNUM")  ;   // 견적번호
        String qtser             = DatasetUtility.getString(ds_cond,"QTSER")  ;   // 견적차수
        String pspid             = DatasetUtility.getString(ds_cond,"PSPID") ;    // 프로젝트번호
        // 2012.10.19 견적진행상태 코드 추가 반영
        String zprgflg          = "";                                             // 견적진행상태 조회정보값.

        Dataset     ds_error    = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

        MipResultVO resultVO = new MipResultVO();

        try {
            sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
            zsdt0057s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

            List<Sso0060>     list     = null;                    // 결과리스트SET
            Sso0060ParamVO    param    = new Sso0060ParamVO();    // 조건
            param.setMANDT(mandt);                                // 조건_SAP CLIENT
			param.setVBELN(pspid);	                              // 프로젝트번호

			list = sso0060NS.selectListQtnum(param);

			// 프로젝트 번호의 정보가 존재하는 경우에만 처리
			if (list != null && list.size() > 0 )
			{
				// 3.2 조회한 견적번호 SET
				pspid = list.get(0).getVBELN();	// 프로젝트번호  
				qtnum = list.get(0).getQTNUM();	// 견적번호
				qtser = list.get(0).getQTSER();	// 견적차수
				zprgflg = list.get(0).getZPRGFLG();  // 견적진행상태코드
	            param.setQTNUM(qtnum);    // 견적번호
	            param.setQTSER(qtser);    // 견적일련번호
			}
            // 1.3 견적HEADER T/B에 프로젝트번호가 존재할 경우 기 수주생성된 견적자료이므로
            // SAP FUNCTION CALL로 변경한다.
            if (StringUtils.isNotBlank(pspid))     {
                // 3.1 프로젝트번호로 견적번호, 견적차수 조회
                param.setQTSER(qtser);    // 견적일련번호
                param.setVBELN(pspid);    // 프로젝트번호
                qry_gb = "P";

                // XML 방식 SAP 호출 방식
                sso0060NS.getSapXmlEstimatDataset(paramVO.getVariable("G_SYSID"), cmd, zprgflg, qry_gb, param, ds_list_partner, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error, resultVO);

                // JCO 방식 SAP 호출 방식
                // logger.debug("JCO 방식 SAP 호출 방식");
                // sso0060NS.getSapJcoEstimatDataset(cmd, zprgflg, qry_gb, param, ds_list_partner, ds_error, resultVO);
                try {
                    ZSDT0057 zsdt0057 = zsdt0057s.select(mandt, new Vbeln(param.getVBELN()), new SapBool(false));
                    ds_sto.deleteAll();
                    ds_sto.appendRow();
                    DatasetUtil.moveObjToDsRow(zsdt0057, ds_sto, 0);
                    ds_sto.setColumn(0, "ordno", zsdt0057.getVbeln().toString());
                } catch(Exception e) {
                	System.out.println("### zsdt0057s.select error ###");
                	e.printStackTrace();
                	System.out.println("### zsdt0057s.select error ###");
                }
            }
        } catch(DataAccessException de) {
        	SQLException se = (SQLException)((DataAccessException)de).getRootCause();
        	logger.error("DataAccessException:"+se.getMessage());
        } catch(Exception e) {
            // 서버단과 화면단에서 에러에 대한 부분 처리로직 필요함
            logger.error("Exception:"+e.getMessage());
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        } finally {
            // 반드시 수행되어야 할 처리 로직
            // 만약 서비스 단에서 error 데이터  존재하는 경우에 리턴값 정의
            // 5. RFC 출력 dataset을 return
			if( ds_error.getRowCount() > 0 ) {
				resultVO.addDataset	(ds_error);  	// 오류정보
			}
            resultVO.addDataset(ds_sto);
            model.addAttribute        ("resultVO", resultVO);
        }

        return view;
    }

    //=========================================================================================
    //  함수기능  : 수주자료 생성 저장
    //  파라미터  : MipParameterVO paramVO, Model model
    //  리턴값    : MipResultVO resultVO
    //  기능ID    : UF015
    //  개선내역  : 수주 생성 로직 개선
    //=========================================================================================
	@RequestMapping(value="saveXmlEstiMateCreate")
	public View Sso0060SaveXmlEstiMateCreateView(MipParameterVO paramVO, Model model) {
		Dataset ds_cond 	 = paramVO.getDataset("ds_cond");
		Dataset ds_dtl 		 = paramVO.getDataset("ds_dtl_save");        	// HEADER  (PARTNER 정보 포함)
		Dataset ds_list_item = paramVO.getDataset("ds_list_item_save");  	// ITEM
		Dataset ds_list_bill = paramVO.getDataset("ds_list_bill_save");  	// BILLING PLAN
		Dataset ds_list_txt  = paramVO.getDataset("ds_list_txt_save");  	// TEXT
		Dataset ds_sto = paramVO.getDataset("ds_sto");

		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		String mandt  = paramVO.getVariable("G_MANDT");				    // IN_CLIENT
		String cmd 	  = DatasetUtility.getString(ds_cond,"CMD") ;		// 처리구분(DISP:조회,SAVE:수주생성,UPDT:수주변경,DELE:수주삭제)
		String qry_gb = DatasetUtility.getString(ds_cond,"QRY_GB") ;	// 조회구분(Q:견적번호,P:프로젝트)
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// 견적번호
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// 견적차수
		String pspid  = DatasetUtility.getString(ds_cond,"PSPID") ;		// 프로젝트번호
		


		MipResultVO resultVO = new MipResultVO();
		try {
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			zsdt0057s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			Sso0060ParamVO 	param 	= new Sso0060ParamVO();   	// 조건
			List<Sso0060> 	list 	= null;

			if (qtnum != null && !"".equals(qtnum)) 	{
				param.setMANDT(mandt);
				param.setQTNUM(qtnum);
				list = sso0060NS.selectListQtser(param);
				if( list.size() > 0 ) {
				    param.setQTSER(list.get(0).getQTSER().trim());	
				}
			}
			else if (pspid != null && !"".equals(pspid)) {
				param.setMANDT(mandt);
				param.setVBELN(pspid);
				list = sso0060NS.selectListQtnum(param);
			}

			if (list == null || list.size() == 0) {
				Sso0060 sso0060 = new Sso0060();
				sso0060.setVBELN(pspid);

				if (list == null) {
					list = new ArrayList<Sso0060>();
				}
				list.add(sso0060);
			} 
			else if (!"".equals(list.get(0).getVBELN().trim())) {
				pspid = list.get(0).getVBELN().trim();
				qtnum = list.get(0).getQTNUM().trim();
				qtser = list.get(0).getQTSER().trim();
			}

			if ("SAVE".equals(cmd)) {
				if (!"".equals(list.get(0).getVBELN().trim())) {
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW10024", "", "Y", "Y");
				}
				if(sso0060NS.validQtdat(paramVO.getVariable("G_SYSID"), mandt, ds_dtl.getColumnAsString(0, "QTDAT"), ds_dtl.getColumnAsString(0, "KUNNR_Z2")) == false) {
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", 
							"산출된 원가의 유효기간이 지나 수주생성이 불가하오니 원가 재산출 바랍니다."+System.getProperty("line.separator")
							+"Cost Engineering Team으로 문의하시기 바랍니다.",
							"Y", "Y");
				}
				
			}
			else if ("UPDT".equals(cmd) || "DELE".equals(cmd)) {
				if ("".equals(list.get(0).getVBELN().trim()))
				{
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW00052", "", "Y", "Y");	//수주생성정보 미존재 프로젝트
				}
			}

			
			System.out.println("!!!!!!! ds_list_item:"+ds_list_item);
			if ( ds_error.getRowCount() == 0 ) {
				
				logger.debug("4. 오류가 없을 경우 ");
				Vbeln vbeln = sso0060NS.saveSapXmlProject(paramVO.getVariable("G_SYSID"), cmd, paramVO.getVariable("G_USER_ID"), param,
	                                        ds_cond, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, 
	                                        ds_error);
				if(SapBool.parseBoolean(ds_sto.getColumnAsString(0, "changed"))) {
					ds_sto.setColumn(0, "vbeln", vbeln.toString());
					setStoInfoDpcpd(paramVO);
				}
			} else {
				if( "SAVE".equals(cmd)) {
					sso0060NS.deleteTmpEstiMateTable(mandt, ds_cond);
				}
			}
		} catch (CommRfcException e) {
			logger.error("Sso0060SaveView CommRfcException ERROR!!!" + "");
			e.printStackTrace();     
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
	    } catch (Exception e) {
			logger.error("Sso0060SaveView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			//----------------------------------------------------------------
			//	3. 결과 리턴
			//----------------------------------------------------------------
			if( ds_error.getRowCount() > 0 ) {
				logger.debug("3. 결과 리턴");
				ds_error.setId		("ds_error");
				resultVO.addDataset	(ds_error);  	// 오류정보
			}
			model.addAttribute	("resultVO", resultVO);
		}
		logger.debug("Sso0060SaveProjectView end" + "");
		return view;
	}
    
    //=========================================================================================
    //  함수기능  : 수주자료 생성 저장
    //  파라미터  : MipParameterVO paramVO, Model model
    //  리턴값    : MipResultVO resultVO
    //  기능ID    : UF015
    //  개선내역  : 수주 생성 로직 개선
    //=========================================================================================
	@RequestMapping(value="saveEstiMateCreate")
	public View Sso0060SaveEstiMateCreateView(MipParameterVO paramVO, Model model) {

		logger.debug("Sso0060SaveProjectView START");

		// INPUT DATASET GET
		Dataset ds_cond 	 = paramVO.getDataset("ds_cond");        		// 조건
		Dataset ds_dtl 		 = paramVO.getDataset("ds_dtl_save");        	// HEADER  (PARTNER 정보 포함)
		Dataset ds_list_item = paramVO.getDataset("ds_list_item_save");  	// ITEM
		Dataset ds_list_bill = paramVO.getDataset("ds_list_bill_save");  	// BILLING PLAN
		Dataset ds_list_txt  = paramVO.getDataset("ds_list_txt_save");  	// TEXT

		// UI로 return할 error out dataset
		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		// 변수 GET
		String mandt  = paramVO.getVariable("G_MANDT");				    // IN_CLIENT
		String cmd 	  = DatasetUtility.getString(ds_cond,"CMD") ;		// 처리구분(DISP:조회,SAVE:수주생성,UPDT:수주변경,DELE:수주삭제)
		String qry_gb = DatasetUtility.getString(ds_cond,"QRY_GB") ;	// 조회구분(Q:견적번호,P:프로젝트)
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// 견적번호
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// 견적차수
		String pspid  = DatasetUtility.getString(ds_cond,"PSPID") ;		// 프로젝트번호

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (개발 : 310 or 910)
		logger.debug("paramVO.G_MANDT 	===>"+mandt	);		// CLIENT
		logger.debug("ds_cond.CMD   	===>"+cmd);		    // 처리구분(DISP:조회,SAVE:수주생성,UPDT:수주변경,DELE:수주삭제)
		logger.debug("ds_cond.QRY_GB   	===>"+qry_gb);		// 조회구분(Q:견적번호,P:프로젝트)
		logger.debug("ds_cond.QTNUM   	===>"+qtnum);		// 견적번호
		logger.debug("ds_cond.QTSER   	===>"+qtser);		// 견적차수
		logger.debug("ds_cond.PSPID   	===>"+pspid );		// 프로젝트번호
		logger.debug("ds_dtl.getRowCount()	     ===>"	+ds_dtl.getRowCount() +"");
		logger.debug("ds_list_item.getRowCount() ===>"	+ds_list_item.getRowCount() +"");
		logger.debug("ds_list_txt.getRowCount()	 ===>"	+ds_list_txt.getRowCount() +"");
		logger.debug("ds_list_bill.getRowCount() ===>"	+ds_list_bill.getRowCount() +"");

		try {
			// Session GET
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			Sso0060ParamVO 	param 	= new Sso0060ParamVO();   	// 조건
			List<Sso0060> 	list 	= null;

			// 1. IN_견적번호 존재 시 견적번호로 프로젝트번호 조회
			if (qtnum != null && !"".equals(qtnum)) 	{
				logger.debug("1. 견적번호 존재 시 견적번호로 프로젝트번호 조회 ");
				param.setMANDT(mandt);  					// 조건_SAP CLIENT
				param.setQTNUM(qtnum);						// 견적번호
				list = sso0060NS.selectListQtser(param);		// 조회 서비스 호출
				if( list.size() > 0 ) {
				    param.setQTSER(list.get(0).getQTSER().trim());	
				}
			}
			// 1. IN_프로젝트번호 존재 시 프로젝트번호로 견적번호 조회
			else if (pspid != null && !"".equals(pspid)) {
				logger.debug("1. 프로젝트번호 존재 시 프로젝트번호로 견적번호 조회");
				param.setMANDT(mandt); 						// 조건_SAP CLIENT
				param.setVBELN(pspid);						// 프로젝트번호
				list = sso0060NS.selectListQtnum(param); 		// 조회 서비스 호출
			}

			// 2. 조회한 견적번호, 견적차수, 프로젝트번호 재설정
			logger.debug("2. 조회한 프로젝트번호 SET");
			if (list == null || list.size() == 0) {
				Sso0060 sso0060 = new Sso0060();
				sso0060.setVBELN(pspid);

				if (list == null) {
					list = new ArrayList<Sso0060>();
				}
				list.add(sso0060);
			} 
			else if (!"".equals(list.get(0).getVBELN().trim())) { // 견적테이블에 프로젝트번호 존재 시
				pspid = list.get(0).getVBELN().trim();	// 프로젝트번호
				qtnum = list.get(0).getQTNUM().trim();	// 견적번호
				qtser = list.get(0).getQTSER().trim();	// 견적차수
			}

			// 3. 신규 수주생성 요청(SAVE)인 경우 이미 생성된 수주건인지 체크
			if ("SAVE".equals(cmd)) {
				logger.debug("3. 신규 수주생성 요청(SAVE)인 경우 이미 생성된 수주건인지 체크 ");
				if (!"".equals(list.get(0).getVBELN().trim())) {	// 견적테이블에 프로젝트번호 존재 시
					// "CW10024", "이미 수주생성이 완료된 견적번호 입니다."
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW10024", "", "Y", "Y");
					logger.info("이미 수주생성이 완료된 견적번호 : [" + qtnum + "]");
				}
			} 	// end of if ("SAVE".equals(cmd))
			// 3. 수주변경 요청(UPDT) 이거나 수주삭제 요청(DELE) 인 경우  대상 프로젝트 미존재시 오류처리
			else if ("UPDT".equals(cmd) || "DELE".equals(cmd)) {
				logger.debug("1. 수주변경 요청(UPDT) 이거나 수주삭제 요청(DELE) 인 경우  대상 프로젝트 미존재시 오류처리 ");
				if ("".equals(list.get(0).getVBELN().trim()))		// 견적테이블에 프로젝트번호에 해당하는 자료 미존재 시
				{
					// "CW00052", "일치하는 자료가 존재하지 않습니다.");
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW00052", "", "Y", "Y");
					logger.info("수주생성정보 미존재 프로젝트 : [" + qtnum + "]");
				}
			}

			//----------------------------------------------------------------
			//	4. 오류가 없을 경우
			//----------------------------------------------------------------
			if ( ds_error.getRowCount() == 0 ) {
				
				logger.debug("4. 오류가 없을 경우 ");
				// jco 방식인 경우
				sso0060NS.saveSapJcoProject(paramVO.getVariable("G_SYSID"), cmd, paramVO.getVariable("G_USER_ID"), param,
			                                ds_cond, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, 
			                                ds_error, resultVO);

			} else {
				if( "SAVE".equals(cmd)) {
					// 임시저장된 데이터를 모두 삭제 처리한다.
					sso0060NS.deleteTmpEstiMateTable(mandt, ds_cond);
				}
			}
		} catch (CommRfcException e) {
			logger.error("Sso0060SaveView CommRfcException ERROR!!!" + "");
			e.printStackTrace();     
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
	    } catch (Exception e) {
			logger.error("Sso0060SaveView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			//----------------------------------------------------------------
			//	3. 결과 리턴
			//----------------------------------------------------------------
			if( ds_error.getRowCount() > 0 ) {
				logger.debug("3. 결과 리턴");
				ds_error.setId		("ds_error");
				resultVO.addDataset	(ds_error);  	// 오류정보
			}
			model.addAttribute	("resultVO", resultVO);
		}

		logger.debug("Sso0060SaveProjectView end" + "");
		return view;
	}

    //=========================================================================================
    //  함수기능  : 수주자료 생성 임시 저장
    //  파라미터  : MipParameterVO paramVO, Model model
    //  리턴값    : MipResultVO resultVO
    //  기능ID    : UF015
    //  개선내역  : 수주 생성 로직 개선
    //=========================================================================================
	@RequestMapping(value="tmpSaveEstiMateCreate")
	public View Sso0060TmpSaveEstiMateCreateView(MipParameterVO paramVO, Model model) {

		logger.debug("Sso0060TmpSaveEstiMateCreateView START");

		// INPUT DATASET GET
		Dataset ds_cond 	 = paramVO.getDataset("ds_cond");        		// 조건
		Dataset ds_dtl 		 = paramVO.getDataset("ds_dtl_save");        	// HEADER  (PARTNER 정보 포함)
		Dataset ds_list_item = paramVO.getDataset("ds_list_item_save");  	// ITEM
		Dataset ds_list_bill = paramVO.getDataset("ds_list_bill_save");  	// BILLING PLAN
		Dataset ds_list_txt  = paramVO.getDataset("ds_list_txt_save");  	// TEXT

		// UI로 return할 error out dataset
		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		// 변수 GET
		String mandt  = paramVO.getVariable("G_MANDT");				    // IN_CLIENT
		String cmd 	  = DatasetUtility.getString(ds_cond,"CMD") ;		// 처리구분(DISP:조회,SAVE:수주생성,UPDT:수주변경,DELE:수주삭제)
		String qry_gb = DatasetUtility.getString(ds_cond,"QRY_GB") ;	// 조회구분(Q:견적번호,P:프로젝트)
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// 견적번호
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// 견적차수
		String pspid  = DatasetUtility.getString(ds_cond,"PSPID") ;		// 프로젝트번호

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (개발 : 310 or 910)
		logger.debug("paramVO.G_MANDT 	===>"+mandt	);		// CLIENT
		logger.debug("ds_cond.CMD   	===>"+cmd);		    // 처리구분(DISP:조회,SAVE:수주생성,UPDT:수주변경,DELE:수주삭제)
		logger.debug("ds_cond.QRY_GB   	===>"+qry_gb);		// 조회구분(Q:견적번호,P:프로젝트)
		logger.debug("ds_cond.QTNUM   	===>"+qtnum);		// 견적번호
		logger.debug("ds_cond.QTSER   	===>"+qtser);		// 견적차수
		logger.debug("ds_cond.PSPID   	===>"+pspid );		// 프로젝트번호
		logger.debug("ds_dtl.getRowCount()	===>"		+ds_dtl.getRowCount() +"");
		logger.debug("ds_list_item.getRowCount()	===>"	+ds_list_item.getRowCount() +"");
		logger.debug("ds_list_txt.getRowCount()	===>"	+ds_list_txt.getRowCount() +"");
		logger.debug("ds_list_bill.getRowCount()	===>"	+ds_list_bill.getRowCount() +"");

		try {
			// Session GET
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			sso0060NS.saveTmpEstiMate(paramVO.getVariable("G_USER_ID"), mandt, ds_cond, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error);

		} catch (Exception e) {
			logger.error("Sso0060SaveView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			//----------------------------------------------------------------
			//	3. 결과 리턴
			//----------------------------------------------------------------
			logger.debug("3. 결과 리턴");
			ds_error.setId		("ds_error");
			resultVO.addDataset	(ds_error);  	// 오류정보
			model.addAttribute	("resultVO", resultVO);
		}

		logger.debug("Sso0060TmpSaveEstiMateCreateView end" + "");
		return view;
	}
	
    //=========================================================================================
    //  함수기능  : 수주자료 생성 임시 저장 Data 삭제 
    //  파라미터  : MipParameterVO paramVO, Model model
    //  리턴값    : MipResultVO resultVO
    //  기능ID    : UF015
    //  개선내역  : 수주 생성 로직 개선
    //=========================================================================================
	@RequestMapping(value="tmpDeleteEstiMateCreate")
	public View Sso0060TmpDeleteEstiMateCreateView(MipParameterVO paramVO, Model model) {

		logger.debug("Sso0060TmpDeleteEstiMateCreateView START");

		// INPUT DATASET GET
		Dataset ds_cond 	 = paramVO.getDataset("ds_cond");        		// 조건

		// UI로 return할 error out dataset
		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		// 변수 GET
		String mandt  = paramVO.getVariable("G_MANDT");				    // IN_CLIENT
		String cmd 	  = DatasetUtility.getString(ds_cond,"CMD") ;		// 처리구분(DISP:조회,SAVE:수주생성,UPDT:수주변경,DELE:수주삭제)
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// 견적번호
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// 견적차수
		String pspid  = DatasetUtility.getString(ds_cond,"PSPID") ;		// 프로젝트번호

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (개발 : 310 or 910)
		logger.debug("paramVO.G_MANDT 	===>"+mandt	);		// CLIENT
		logger.debug("ds_cond.CMD   	===>"+cmd);		    // 처리구분(DISP:조회,SAVE:수주생성,UPDT:수주변경,DELE:수주삭제)
		logger.debug("ds_cond.QTNUM   	===>"+qtnum);		// 견적번호
		logger.debug("ds_cond.QTSER   	===>"+qtser);		// 견적차수
		logger.debug("ds_cond.PSPID   	===>"+pspid );		// 프로젝트번호

		try {
			// Session GET			
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			sso0060NS.deleteTmpEstiMateTable(mandt, ds_cond);
		} catch (Exception e) {
			logger.error("Sso0060SaveView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			//----------------------------------------------------------------
			//	3. 결과 리턴
			//----------------------------------------------------------------
			logger.debug("3. 결과 리턴");
			ds_error.setId		("ds_error");
			resultVO.addDataset	(ds_error);  	// 오류정보
			model.addAttribute	("resultVO", resultVO);
		}

		logger.debug("Sso0060TmpDeleteEstiMateCreateView end" + "");
		return view;
	}	

	@RequestMapping(value="isStoOrder")
	public View isStoOrder(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsSto = paramVO.getDataset("ds_sto");

		OrderNo ordNo = new OrderNo(dsSto.getColumnAsString(0, "ordno"));
		OrderItem orderItem = new OrderItem(dsSto.getColumnAsString(0, "ordseq")); 
		ZWEB_SD_IS_STO_ORDERStub stub = (ZWEB_SD_IS_STO_ORDERStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_LANG"))
				.create(ZWEB_SD_IS_STO_ORDERStub.class);
		ZWEB_SD_IS_STO_ORDERStub.ZWEB_SD_IS_STO_ORDER param;
		param = new ZWEB_SD_IS_STO_ORDERStub.ZWEB_SD_IS_STO_ORDER();
		param.setIV_ORDNO(ZWEB_SD_IS_STO_ORDERStub.Char10.Factory.fromString(ordNo.toString(), ""));
		param.setIV_ORDSEQ(new org.apache.axis2.databinding.types.UnsignedByte(orderItem.toString()));
		ZWEB_SD_IS_STO_ORDERStub.ZWEB_SD_IS_STO_ORDERResponse response;
		try {
			response = stub.zWEB_SD_IS_STO_ORDER(param);
			SapBool isStoOrder = new SapBool(response.getEV_ISSTO().toString());

			resultVO.addVariable("isStoOrder", isStoOrder.toString());
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	public void setStoInfoDpcpd(MipParameterVO paramVO) {
		Dataset dsSto = paramVO.getDataset("ds_sto");
		Vbeln vbeln = new OrderNo(dsSto.getColumnAsString(0, "vbeln"));
		String dpcpd = dsSto.getColumnAsString(0, "dpcpd");

		ZWEB_SD_SET_STO_INFOStub stub = (ZWEB_SD_SET_STO_INFOStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_LANG"))
				.create(ZWEB_SD_SET_STO_INFOStub.class);
		ZWEB_SD_SET_STO_INFOStub.ZWEB_SD_SET_STO_INFO param;
		param = new ZWEB_SD_SET_STO_INFOStub.ZWEB_SD_SET_STO_INFO();
		param.setIV_VBELN(ZWEB_SD_SET_STO_INFOStub.Char10.Factory.fromString(vbeln.getValue(), ""));
		param.setIV_DPCPD(ZWEB_SD_SET_STO_INFOStub.Date.Factory.fromString(dpcpd.replaceFirst("(\\d{4})(\\d{2})(\\d+)", "$1-$2-$3"), ""));

		try {
			stub.zWEB_SD_SET_STO_INFO(param);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="findSopind")
	public View findSopind(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsSopind = paramVO.getDataset("ds_sopind");
		Dataset dsDtl = paramVO.getDataset("ds_dtl");
		
		String mandt = paramVO.getVariable("G_MANDT");
		String auart = DatasetUtility.getString(dsDtl, "AUART");
		
		try {
			Sso0060ParamVO param = new Sso0060ParamVO();
			param.setMANDT(mandt);
			param.setAUART(auart);
			
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			List<Sso0060> listSelectSopind = sso0060NS.searchSopind(param);
			dsSopind.deleteAll();
						
			for(int i=0; i<listSelectSopind.size(); i++) {
				dsSopind.appendRow();
				dsSopind.setColumn(i, "AUART", listSelectSopind.get(i).getAUART());
				dsSopind.setColumn(i, "SOPIND", listSelectSopind.get(i).getSOPIND());
			}
			
			resultVO.addDataset(dsSopind);
			
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			if( ds_error.getRowCount() > 0 ) {
				ds_error.setId("ds_error");
				resultVO.addDataset(ds_error);
			}			
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}
}
