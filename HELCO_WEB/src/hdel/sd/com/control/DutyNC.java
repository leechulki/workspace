package hdel.sd.com.control;

import java.util.HashMap;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.RequireException;
import hdel.sd.com.domain.DutyObj;
import hdel.sd.com.service.DutyNS;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("dutyN")
public class DutyNC {

    @Autowired
    private SrmView view;

    @Autowired
    private SrmSqlSession sqlSession;

    @Autowired
    private DutyNS service;

    private Logger logger = Logger.getLogger(this.getClass());

    //=========================================================================================
    //  함수기능 : 견적상세-Duty Call(단일 MO에 처리 수행)
    //  파라미터 : ds_cond, ds_template1(사양서내역 그리드 화면값)
    //  리턴값   : dsTemplate1(사양서 입력체크 기준 체크 후 기본설정값이 적용된 사양서내역)
    //            ,ds_error(사양서 내역 중 필수값이 없는 경우 에러메시지 생성됨)
    //  기능ID   : UF003
    //  개선내역 : 사양 기준정보 및 사양 체크 디테일 처리 로직 개선
    //  HISTORY  : 2016.01.28 최초 코딩 박수근
    //=========================================================================================
    @RequestMapping(value="genSpecDutySingleMo")
    public View genSpecDutySingleMo(MipParameterVO paramVO, Model model) {
        // 견적서 상세 화면에서 전달되는 파라미터
        Dataset dsCond      = paramVO.getDatasetList().getDataset("ds_cond");
        Dataset dsTemplate1 = paramVO.getDatasetList().getDataset("ds_template1");

        // DB 세션 생성, G_MANDT 값에 따라 개발, 품질, 운영 DB로 접속된다.
        service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

        // 견적서 사양내용 N행 1열 데이터 형식을 1행 N열형식으로 변환하여 HashMap에 입력한다.
        // SAPHEE.CABN ATNAM  -> SAPHEE.ZSDT1048 PRH
        HashMap<String, String> mapInput = new HashMap<String, String>();
        for (int i=0; i<dsTemplate1.getRowCount(); i++) {
            String value = dsTemplate1.getColumnAsString(i, "PRD");
            if (null != value && ! "".equals(value)) {
                mapInput.put(dsTemplate1.getColumnAsString(i, "PRH"), value);
            }
        }

        // 사양 체크 시 발생된 에러 메시지 데이터 
        Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
        try
        {
            String gtype = "";
            String xx_atyp = "";

            xx_atyp = (String) mapInput.get("EL_ATYP");
            if ("WBSS".equals(xx_atyp)) {
                xx_atyp = "SSVF";
            }
            
            if ("GTLX".equals(xx_atyp) || "GTSS".equals(xx_atyp)) {
            	xx_atyp = "GSP";
            }
            
            if (xx_atyp != null && !"".equals(xx_atyp))    {
                gtype = (String) mapInput.get("EL_ATYP");
                if ("WBSS".equals(gtype)) {
                  gtype = "SSVF";
                }
                
                if ("GTLX".equals(gtype) || "GTSS".equals(gtype)) {
                	gtype = "GSP";
                }
                
            } else {
                // 에스컬러에터
                xx_atyp = (String) mapInput.get("ES_ATYP");
                if (xx_atyp != null && !"".equals(xx_atyp)) {
                	// 에스컬레이터는 Default로 처리
                    gtype = "MLBT";    
                } else {
                    // 무빙워크
                    xx_atyp = (String) mapInput.get("MW_ATYP");
                    if (xx_atyp != null && !"".equals(xx_atyp)) {
                    	// 무빙워크는 Default로 처리
                        gtype = "PMBTL";    
					} else {
						xx_atyp = (String) mapInput.get("AP_ATYP");
						if (xx_atyp != null && !"".equals(xx_atyp))	{
							gtype = "ELV";	// 주차는 Default로 처리
						}						
                    }
                }
            }

            if (null == gtype || "".equals(gtype))     {
                if (!"ko".equals(paramVO.getVariable("G_LANG"))) {
                    throw new RequireException("TYPE");
                } else {
                    throw new RequireException("기종");
                }
            }
        
            service.genSpecDutySingleMo(
            		               paramVO.getVariable("G_MANDT"), 
            		               gtype, 
            		               dsCond.getColumnAsString(0, "BLOCKGBN"), 
            		               mapInput,
            		               paramVO.getVariable("G_LANG"),
            		               dsCond.getColumnAsString(0, "ZFLG")
            		               );

        } catch (RuntimeException e) {
            e.printStackTrace();
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        }

        String prh      = "";
        String prhname  = "";
        String prd      = "";
        String atkla    = "";

        // 견적서 사양내용 체크된 1행 N열형식의 데이터를 N행 1열의 형식으로 자료 형태를 변경하여
        // dsTemplate1에 반영해 준다.
        for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
            prh  = dsTemplate1.getColumnAsString(i, "PRH");
            prhname  = dsTemplate1.getColumnAsString(i, "PRHNAME");
            prd  = (String) mapInput.get(dsTemplate1.getColumnAsString(i, "PRH"));
            atkla = dsTemplate1.getColumnAsString(i, "ATKLA");

            if (prh  == null)  prh   = "";
            if (atkla == null) atkla = "";

            // 견적서 사양서 값이 없는 PRH에 대해서 기본값 및 사양서 out에 정의된 사양값을 입력하게 처리한다.
            if (null != prd && ! "".equals(prd)) {
                dsTemplate1.setColumn(i, "PRH"     , prh );
                dsTemplate1.setColumn(i, "PRHNAME" , prhname );
                // 사양서 PRH 기준내역에 정의값 VALUE
                dsTemplate1.setColumn(i, "PRD"     , prd );
                dsTemplate1.setColumn(i, "ATKLA"   , atkla);
            }
        }

        MipResultVO resultVO = new MipResultVO();

        // 견적상세 화면에 DUTY-CALL 결과값을 리턴한다.
        resultVO.addDataset(dsTemplate1);
        resultVO.addDataset(ds_error);
        model.addAttribute("resultVO", resultVO);

        long lEndTime = System.currentTimeMillis();
        return view;
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
	@RequestMapping(value="dependDutySingleMo")
	public View dependDutySingleMo(MipParameterVO paramVO, Model model) {
        // 견적서 상세 화면에서 전달되는 파라미터
		Dataset dsTemplate1 = paramVO.getDatasetList().getDataset("ds_template1");
		
        // DB 세션 생성, G_MANDT 값에 따라 개발, 품질, 운영 DB로 접속된다.
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

        // 견적서 사양내용 N행 1열 데이터 형식을 1행 N열형식으로 변환하여 HashMap에 입력한다.
        // SAPHEE.CABN ATNAM  -> SAPHEE.ZSDT1048 PRH
		HashMap<String, String> mapInput = new HashMap<String, String>();
		// ORG 사양정보 임시 저장
		for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
			String value = dsTemplate1.getColumnAsString(i, "PRD");
			if (null != value && ! "".equals(value)) {
				mapInput.put(dsTemplate1.getColumnAsString(i, "PRH"), value);
			}
		}

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset

		try {

			// 기종(표준테플릿 처리 기종)
			String sGtype    = (String) paramVO.getVariable("strGtype");
			String sZflg    = (String) paramVO.getVariable("strZflg");
			String sBlockGbn = "00";
			String gtype     = "";

			String sGubun = (String) mapInput.get("EL_ATYP");
			if (sGubun != null && !"".equals(sGubun))	{
				gtype = (String) mapInput.get("EL_ATYP");
			} else {
				// 에스컬러에터
				sGubun = (String) mapInput.get("ES_ATYP");
				if (sGubun != null && !"".equals(sGubun))	{
					gtype = "MLBT";	// 에스컬레이터는 Default로 처리
				}else {
					// 무빙워크
					sGubun = (String) mapInput.get("MW_ATYP");
					if (sGubun != null && !"".equals(sGubun))	{
						gtype = "PMBTL";	// 무빙워크는 Default로 처리
					}
				}
			}

			if (null == gtype || "".equals(gtype))	 {
				if (!"ko".equals(paramVO.getVariable("G_LANG")))		{
					throw new RequireException("TYPE");
				}else	{
					throw new RequireException("기종");
				}
			}
			
            service.dependDutySingleMo(paramVO.getVariable("G_MANDT"), sGtype, sBlockGbn, mapInput, paramVO.getVariable("G_LANG"), sZflg);
			
		} catch (RuntimeException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		// 제품구분에 해딩하는 정보가 존재하지 않는 경우 원 Template 정보 return.
		if (mapInput != null)	{
			String prh  = "";
			String prhname  = "";
			String prd  = "";
			String atkla = "";
			
			for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
				prh      = dsTemplate1.getColumnAsString(i, "PRH");
				prhname  = dsTemplate1.getColumnAsString(i, "PRHNAME");
				prd      = (String) mapInput.get(dsTemplate1.getColumnAsString(i, "PRH"));
				atkla    = dsTemplate1.getColumnAsString(i, "ATKLA");
	
				if (prh   == null) prh   = "";
				if (atkla == null) atkla = "";
					
				if (null != prd && ! "".equals(prd)) {
					dsTemplate1.setColumn(i, "PRH",     prh );
					dsTemplate1.setColumn(i, "PRHNAME", prhname );
					dsTemplate1.setColumn(i, "PRD",     prd );
					dsTemplate1.setColumn(i, "ATKLA",   atkla);
				}
			}
		}

		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsTemplate1);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

        long lEndTime = System.currentTimeMillis();

		return view;
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
	@RequestMapping(value="getQCostDutySingleMo")
	public View getQCostDutySingleMo(MipParameterVO paramVO, Model model) {
        // 견적서 상세 화면에서 전달되는 파라미터
//        String  item = paramVO.getVariable("QTSEQ"); // QTSEQ번호
//        String  zflg = paramVO.getVariable("ZFLG"); // 견적영업구분
//        Dataset ds_mospec = paramVO.getDatasetList().getDataset("ds_mospec");
//        int     cnt   = 0;
//        Dataset ds_log = new Dataset("ds_log");
//        
//        // DB 세션 생성, G_MANDT 값에 따라 개발, 품질, 운영 DB로 접속된다.
//        service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
//
//        // 견적서 사양내용 N행 1열 데이터 형식을 1행 N열형식으로 변환하여 HashMap에 입력한다.
//        // SAPHEE.CABN ATNAM  -> SAPHEE.ZSDT1048 PRH
//        HashMap<String, String> mapInput = new HashMap<String, String>();
//        for (int i=0; i<ds_mospec.getRowCount(); i++) {
//            String value = ds_mospec.getColumnAsString(i, "PRD");
//            if (null != value && ! "".equals(value)) {
//                mapInput.put(ds_mospec.getColumnAsString(i, "PRH"), value);
//            }
//        }
//
//        // 사양 체크 시 발생된 에러 메시지 데이터 
//        Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
//        try
//        {
//            String gtype = "";
//            String sGubun = "";
//            boolean bgenExe = true;
//
//    		// 2013.01.29 Duty 안 타는 조건 추가
//    		String sGubun1 = (String) mapInput.get("EL_DITEMUSE");
//    		String sGubun2 = (String) mapInput.get("EL_ASPLY");
//    		//String sGubun3 =  template.getColumnAsString(0, "PSPID");
//    		if ( sGubun1 == null ) sGubun1 = "";
//    		if ( sGubun2 == null ) sGubun2 = "";
//    		
//    		if ( sGubun1.equals("Y") || sGubun2.equals("Y") ) {
//    			//return true;
//    			bgenExe = false; 
//    		}
//
//    		//if (sGubun3.equals("126394")){ //20131030 kt 청진빌딩 비표준으로 필수체크 제외 -김선호
//    		//	return true;
//    		//}
//
//    		sGubun = (String) mapInput.get("EL_ATYP");
//    		if (sGubun != null && !"".equals(sGubun))	{
//    			gtype = "ELV1";
//    			// 선박용 처리
//    			if ("STSH1".equals(sGubun) || "STSH5".equals(sGubun))	{
//    				gtype = "ELV2";
//    			}
//    		}else {
//    			// 에스컬러에터
//    			sGubun = (String) mapInput.get("ES_ATYP");
//    			if (sGubun != null && !"".equals(sGubun))	{
//    				gtype = "ESC1";	// 에스컬레이터는 Default로 처리
//    			}else {
//    				// 무빙워크
//    				sGubun = (String) mapInput.get("MW_ATYP");
//    				if (sGubun != null && !"".equals(sGubun))	{
//    					gtype = "MWK1";	// 무빙워크는 Default로 처리
//    				} else {	// 2013.01.03 이외에는 필수 체크 불필요
//    					bgenExe = false;
//    				}
//    			}
//    		}
//
//    		if (null == gtype || "".equals(gtype))	 {
//    			if (!"ko".equals(paramVO.getVariable("G_LANG"))) {
//    				throw new RequireException("TYPE");
//    			}else	{
//    				throw new RequireException("기종");
//    			}
//    		}
//
//    		if( bgenExe ) {
//    			DutyObj dutyObj = new DutyObj();
//                service.genCostDutySingleMo(
//                		                   paramVO.getVariable("G_MANDT"), 
//                		                   gtype, 
//                		                   mapInput,
//                		                   ds_log,
//                		                   paramVO.getVariable("G_LANG"),
//                		                   item,
//                		                   cnt,
//                		                   dutyObj,
//                		                   zflg
//                		                   );
//    		}
//        } catch (RuntimeException e) {
//            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "MO [" + item + "] " + e.getMessage(), "Y", "Y");
//        }
//
//		MipResultVO resultVO = new MipResultVO();
//		ds_error.setId("ds_error");
//		resultVO.addDataset(ds_error);
//		model.addAttribute("resultVO", resultVO);
//
//        long lEndTime = System.currentTimeMillis();
		return view;
	}
}

