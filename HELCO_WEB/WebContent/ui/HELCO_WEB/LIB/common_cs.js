﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 서비스 공통 Script
 *  작성일자 : 2016.07.15
================================================================================*/
/****************************************************************
* 본부장 결재 문서의 경우 의견입력 여부 체크
* 의견 필수 입력 대상 : 지사장
* @param docNo 결재문서 번호
***************************************************************/
function gfn_decideCommentCheck (docNo)
{
	gds_userlist.ClearData();
	gds_zwbap02.ClearData();
	
	tit_clearActionInfo();
	tit_addSearchActionInfo("cs04:CS0401001C_S3"); // 의견입력 대상 리스트
	tit_addSearchActionInfo("cs04:CS0401001C_S4"); // 결재문서 결재라인 리스트
	tit_callService(
        ""
        , ""
        , ""
        , "gds_userlist=ds_list gds_zwbap02=ds_list2"
        , "DOCNO="+docNo
        , ""
        , false
        , true
        , true);

	var idx;	 // 의견입력 대상 리스트 index
	var idx1;	 // 본부장 포함 결재문서 index
	var idx2;	 // 본인 결재문서 index
	var apdesc; // 본인 결재문서 의견내용
	var director; // 본부장 사번
	var user_no;  // 본인 사번
	var msg_text;
	
	idx = gds_userlist.FindRow("USERNUMB",G_USER_ID) ;
	director_no = "H" + gds_userlist.GetColumn(gds_userlist.FindRow("GUBUN","S"),"USERNUMB") ;		
	
	user_no = "H" + G_USER_ID;
	
	if(idx < 0  or director_no == user_no) {	
		return true;	// 의견입력 미대상 	or 본부장이 결재하는 경우
	} else {
		idx2 = gds_zwbap02.FindRow("APNUM",director_no) ;
		if(idx2 < 0 ) {
			return true;	// 결재선에 본부장 미포함
		} else {
			idx3 = gds_zwbap02.FindRow("APNUM", user_no) ;
			apdesc = gds_zwbap02.GetColumn(idx3,"APDESC");
			
			if(length(apdesc) == 0) { // 의견 미입력
				msg_text = "의견입력 필수 결재입니다." + "\n"
				            + "결재를 취소합니다." + "\n"
				            ;
				            
				gfn_openMsgWin( msg_text ,"W"); 	  
				return false;
			} else {
				return true;
			}
		}	
	}	
}

/****************************************************************
* 본부장 결재 문서의 경우 의견입력 여부 체크
* 의견 필수 입력 대상 : 지사장
* @param posid 결재문서 번호
***************************************************************/
function gfn_psdCheck(posid)
{
	gds_psd.ClearData();
	
	tit_clearActionInfo();
	tit_addSearchActionInfo("cs01:CS0101008B_S20"); // 이관대상 리스트

	tit_callService(
        ""
        , ""
        , ""
        , "gds_psd=ds_list"
        , "POSID="+posid
        , ""
        , false
        , true
        , true);

	
	if(gds_psd.GetColumn(0, "CNT") > 0) {
		return true;
    } else {
		return false;
    }   
	
}

/****************************************************************
* 서비스 수리부품 견적 원가 계산 
*  2017 . 01 . 적용 
*  2018 . 01 . 적용 
* @param p_reqkind      청구구분 
* @param p_spart	    제품군 
* @param p1_ucost	    재료비 
* @param p2_lcost	    외주인건비 
* @param p3_sum_ofr     견적가
* @param p4_netwr_ofr   자재판매가 
* @param p5_add_ucost   추가-재료비
* @param p6_add_cost    추가-인건비
* @param p7_add_inspec  추가-검사비
* @param p8_add_log     추가-운반비
* @param p9_add_etc     추가-기타경비
* @param ds_ind         간접비율 
* @return  str
****************************************************************/
function gfn_calculate_cost ( p_reqkind
                            , p_spart
                            , p1_ucost
                            , p2_lcost
                            , p3_sum_ofr
                            , p4_netwr_ofr
                            , p5_add_ucost
                            , p6_add_lcost
                            , p7_add_inspec
                            , p8_add_log
                            , p9_add_etc
                            //==================전현정대리요청 추가20181012==================//
							, p10_add_betc
							, p11_add_lift
							, p12_add_mmod
							, p13_add_safe
							//===============================================================//
                            , ds_ind )
{
/****************************************************************/	
// 간접비율
/****************************************************************/
	var INDIR_RAT  = 0;	//간접비율
	var MOINDIR_RAT= 0;//제조간접비율

	if(p_spart == "20") { // 주차
		INDIR_RAT = ds_ind.GetColumn(0, "CS604_PK");
	} else { // 승강기 포함 그 외

		if(p_reqkind == "1") {
			INDIR_RAT = ds_ind.GetColumn(0, "CS604_NB");
		} else if(p_reqkind == "7") {
			INDIR_RAT = ds_ind.GetColumn(0, "CS604_NS");
			MOINDIR_RAT= ds_ind.GetColumn(0, "CS604_NS_P");
		} else if(p_reqkind == "S") {
			INDIR_RAT = ds_ind.GetColumn(0, "CS604_NC");
			MOINDIR_RAT= ds_ind.GetColumn(0, "CS604_NC_P");
		} 
	}		
	INDIR_RAT = INDIR_RAT/100;
	MOINDIR_RAT = MOINDIR_RAT/100;
/*
	trace("  INDIR_RAT;; " + INDIR_RAT);
	trace("  MOINDIR_RAT;; " + MOINDIR_RAT);
	*/
/****************************************************************/	
// 원가 항목 셋팅 
/****************************************************************/

	var his_ucost = toNumber(p1_ucost);    // 자재비
	var his_lcost = toNumber(p2_lcost);    // 인건비  (일식외주와 통합)
	var his_oem = 0;                      // 일식외주	
	var his_inspec;   // 검사비
	var his_log;      // 운반비
	var his_etc;	  // 기타경비	
	var his_labor;    // 일반노무비 (제조간접비)
	var his_dirsum;   // 직접비
	var his_indsum;   // 간접비
	var his_totcost;  // 총원가
	var his_his_rate; //원가율(시행율)
	
	var sum_ofr    = toNumber(p3_sum_ofr);    // 견적가
	var netwr_ofr  = toNumber(p4_netwr_ofr);  // 자재판매가
	var ucost_add  = toNumber(p5_add_ucost);
	var lcost_add  = toNumber(p6_add_lcost);
	var inspec_add = toNumber(p7_add_inspec);
	var log_add    = toNumber(p8_add_log);
	var etc_add    = toNumber(p9_add_etc);
	
	//==================전현정대리요청 추가20181012==================//
	var his_betc;  // 건축부대
	var his_lift;  // 양중비
	var his_mmod;  // 무상mod
	var his_safe;  //안전관리비
	
	var his_dlab;  //직접노무비
	var his_emacid;//고용산재
	
	
	var betc_add   = toNumber(p10_add_betc);
	var lift_add   = toNumber(p11_add_lift);
	var mmod_add   = toNumber(p12_add_mmod);
	var safe_add   = toNumber(p13_add_safe);
	
	var his_ucost_fn_ss;  //재료비계
	var his_lab_fn_ss;  //노무비계
	var his_etc_fn_ss;  //경비계
	var his_indsum_fn_ss;  //간접비(기타)계
	var his_log_fn_ss;  //운반비계
	var his_ore_fn_ss;  //외주비계
	var his_inspec_fn_ss;  //검사비계
	//===============================================================//


/****************************************************************/	
// 원가 계산 
/****************************************************************/		
	his_ucost = his_ucost + ucost_add;
	his_lcost = his_lcost + lcost_add;
	his_oem = 0;	
	// 검사비 견적금액의 1 %  시스템공사의 경우만 부과 														
	if(p_reqkind == "S") {
		his_inspec = inspec_add;
	} else {
		his_inspec = 0;
	}	
	
	// 운반비 재료비의 3% => 1%  	
	//his_log = round(his_ucost * 0.03, 0) + log_add;										
	his_log = round(his_ucost * 0.01, 0) + log_add;	
										
	his_etc = etc_add ;
	
	//==================전현정대리요청 추가20181012==================//
	his_betc = betc_add ;
	his_lift = lift_add ;
	his_mmod = mmod_add ;
	his_safe = safe_add ;	
	//===============================================================//
	
	//==================전현정대리요청 추가20181012==================//
	//직접노무비 = 견적금액 * 1.5%  -> 변경 2018.11.13 (주차는 수리공사 : 0.01%, 시스템교체 : 0.01%, 부품판매 : 1.75% -> 2.25)
	if(p_spart == "20")	{
		if(p_reqkind == "7") {  // 수리공사
			//his_dlab = sum_ofr * 0.0001 ;   
			//his_dlab = (his_ucost + his_lcost) * 0.0001 ;  //박준성 책임 요청으로 견적금액이 아닌  (외주비+재료  * 0.0001) 변경  2019.08.09
			his_dlab = (his_ucost + his_lcost) * 0.0015 ;    //조은서 씨 요청으로 비율 변경 2021.02.08
		} else if(p_reqkind == "S") {   // 시스템공사
			//his_dlab = sum_ofr * 0.0001 ;
			//his_dlab = (his_ucost + his_lcost) * 0.0001 ;  //박준성 책임 요청으로 견적금액이 아닌  (외주비+재료  * 0.0001) 변경  2019.08.09
			//his_dlab = (his_ucost + his_lcost) * 0.0015 ;    //조은서 씨 요청으로 비율 변경 2021.02.08
			his_dlab = (his_ucost + his_lcost) * 0.0195 ;    //조은서 씨 요청으로 비율 변경 2021.04.30
		} else if(p_reqkind == "1") {  // 부품판매
			//his_dlab = sum_ofr * 0.0175 ;
			//his_dlab = (his_ucost + his_lcost) * 0.0225 ;  //박준성 책임 요청으로 견적금액이 아닌  (외주비+재료  * 0.0225) 변경  2019.08.09
			//his_dlab = (his_ucost + his_lcost) * 0.0319 ;  //비율변경  0.0225 -> 0.0319
			his_dlab = (his_ucost + his_lcost) * 0.0385 ;    //조은서 씨 요청으로 비율 변경 2021.02.08
		}	
	} else {	
		//his_dlab = sum_ofr * 0.015 ;
		
		//his_dlab = sum_ofr * 0.016 ;  //2020년 비율변경 
		// 김종욱씨 요청으로 변경 2021.02.08  -> 기존에는 청구종류 상관없이 동일 하였으나 분리 하는 것으로 변경//
		if(p_reqkind == "7") {
			his_dlab = sum_ofr * 0.011 ; 
		} else if(p_reqkind == "S") {
			his_dlab = sum_ofr * 0.011 ; 
		} else if(p_reqkind == "1") { 
			his_dlab = sum_ofr * 0.005 ; 
		}	
		//=====================================================================================================//
		
	}
	
	
	//고용산재 = (설치비 + 건축부대 + 무상보수 + 안전관리비 + 양중비) * 3.7%
	if(p_reqkind == "S") // 시스템 공사인 경우만 고용산재 계산
	{
		/*
		if(p_spart == "20") {
			his_emacid = 0;
		} else {
			//his_emacid = (his_lcost + his_betc + his_mmod + his_safe + his_lift) * 0.037;
			his_emacid = (his_lcost + his_betc + his_mmod + his_lift) * 0.037;
		}*/
		his_emacid = 0; // 2019년 고용산재보험 예산이 제조간접비로 포함됨에 따른 비율 변경. 전현정 DL 요청. 2019.01.09 Han J.H
	}
	else
	{
		his_emacid = 0;
	}
	//===============================================================//	
	 
	// 수정전 his_dirsum = his_ucost + his_lcost + his_oem + his_inspec + his_log + his_etc ;
	his_dirsum = his_ucost + his_lcost + his_oem + his_inspec + his_log + his_etc   + his_betc + his_lift + his_mmod + his_safe + his_dlab + his_emacid;
	
	// 간접비율 INDIR_RAT
	his_indsum = round(his_dirsum * INDIR_RAT, 0);
	
	// 제조간접비  직접비의 MOINDIR_RAT
	his_labor = round(his_dirsum  * MOINDIR_RAT, 0);
	
	// 총원가 = 직접비 + 간접비 + 제조간접비 
	his_totcost = his_dirsum + his_indsum + his_labor;
	
	// 시행율 = 총원가 / 견적가 
	his_rate = round((his_totcost / sum_ofr) * 100, 1);
	
	//==================전현정대리요청 추가20181012==================//
	his_ucost_fn_ss   = his_ucost    ;  //재료비계
	his_lab_fn_ss	  = his_dlab;  //노무비계
	his_etc_fn_ss     = his_etc + his_emacid + his_safe + his_log + his_inspec ;  //경비계
	his_indsum_fn_ss  = his_labor + his_indsum;  //간접비(기타)계
	his_log_fn_ss     = his_log;  //운반비계
	his_ore_fn_ss     = his_lcost + his_betc + his_mmod + his_lift  ;  //외주비계
	his_inspec_fn_ss  = his_inspec;  //검사비계
	//===============================================================//	
	

/*
	trace("  his_ucost;; " + his_ucost);
	trace("  his_lcost;; " + his_lcost);
	trace("  his_oem;; " + his_oem);
	trace("  his_inspec;; " + his_inspec);
	trace("  his_log;; " + his_log);
	trace("  his_etc;; " + his_etc);
	trace("  his_labor;; " + his_labor);
	trace("  his_dirsum;; " + his_dirsum);
	trace("  his_indsum;; " + his_indsum);
	trace("  his_totcost;; " + his_totcost);
	trace("  his_rate;; " + his_rate);
	trace("  sum_ofr;; " + sum_ofr);
*/
	var str = quote(his_ucost)    + "^";	
	    str += quote(his_lcost)   + "^";
	    str += quote(his_oem)     + "^";
	    str += quote(his_inspec)  + "^";
	    str += quote(his_log)     + "^";
	    str += quote(his_etc)     + "^";
	    str += quote(his_labor)   + "^";	
	    str += quote(his_dirsum)  + "^";
	    str += quote(his_indsum)  + "^";
	    str += quote(his_totcost) + "^";
	    
	    //==================전현정대리요청 추가20181012==================//
	    //str += quote(his_rate)    ;	
	    str += quote(his_rate)    + "^";	
	    str += quote(his_betc)    + "^";
	    str += quote(his_lift)    + "^";
	    str += quote(his_mmod)    + "^";	    
	    str += quote(his_safe)    + "^";
	    
	    str += quote(his_dlab)    + "^";	    
	    str += quote(his_emacid)  + "^";	
	    str += quote(his_ucost_fn_ss)  + "^";	//자재비계
		str += quote(his_lab_fn_ss)    + "^";	//노무비계
		str += quote(his_etc_fn_ss)    + "^";	//경비계
		str += quote(his_indsum_fn_ss) + "^";	//간접비(기타)계
		str += quote(his_log_fn_ss)    + "^";	//운반비계
		str += quote(his_ore_fn_ss)    + "^";	//외주비계
		str += quote(his_inspec_fn_ss)    ;     //검사비계

		//===============================================================//

	return str;
		
}

/****************************************************************
* 해외 서비스 부품수출 견적 원가 계산 
*  2017 . 06 . 적용 
* @param p1_ucost	    재료비 
* @param p3_sum_ofr     견적가
* @param p4_netwr_ofr   자재판매가 
* @param p5_add_ucost   추가-재료비
* @param p8_add_log     추가-운임 
* @return  str
****************************************************************/
function gfn_calculate_cost_zc01 ( p1_ucost
                                 , p3_sum_ofr
                                 , p4_netwr_ofr
                                 , p5_add_ucost
                                 , p8_add_log)
{
/****************************************************************/	
// 원가 항목 셋팅 
/****************************************************************/

	var his_ucost = toNumber(p1_ucost);    // 재료비
	var his_log;      // 운반비	
	var his_dirsum;   // 직접비
	var his_indsum;   // 간접비
	var his_totcost;  // 총원가
	var his_his_rate; //원가율(시행율)
	
	var sum_ofr    = toNumber(p3_sum_ofr);    // 견적가
	var netwr_ofr  = toNumber(p4_netwr_ofr);  // 자재판매가
	var ucost_add  = toNumber(p5_add_ucost);
	var log_add    = toNumber(p8_add_log);

/****************************************************************/	
// 원가 계산 
/****************************************************************/		
	his_ucost = his_ucost + ucost_add;

	// 운임 
	his_log = log_add;		
		
	his_dirsum = his_ucost + his_log ;
	// 간접비율 INDIR_RAT
	his_indsum = 0;
	// 제조간접비  직접비의 MOINDIR_RAT
	his_labor = 0;
	// 총원가 = 직접비 + 간접비 + 제조간접비 
	his_totcost = his_dirsum + his_indsum + his_labor;
	// 시행율 = 총원가 / 견적가 
	his_rate = round((his_totcost / sum_ofr) * 100, 1);
/*
	trace("  his_ucost;; " + his_ucost);
	trace("  his_log;; " + his_log);
	trace("  his_labor;; " + his_labor);
	trace("  his_dirsum;; " + his_dirsum);
	trace("  his_indsum;; " + his_indsum);
	trace("  his_totcost;; " + his_totcost);
	trace("  his_rate;; " + his_rate);
	trace("  sum_ofr;; " + sum_ofr);
*/
	var str = quote(his_ucost)    + "^";	
	    str += quote(his_log)     + "^";
	    str += quote(his_dirsum)  + "^";
	    str += quote(his_indsum)  + "^";
	    str += quote(his_totcost) + "^";
	    str += quote(his_rate)    ;	

	return str;
		
}

/****************************************************************
* SRM 권한 그룹 체크
* @param groupNm      그룹명
/****************************************************************/	
function gfn_getAuthGroup(groupNm){
	
	gds_userlist.ClearData();
	
	tit_clearActionInfo();
	tit_addSearchActionInfo("wb01:GET_AUTH_GROUP_S1"); // 권한 그룹 체크
	tit_callService(
        ""
        , ""
        , ""
        , "gds_userlist=ds_list"
        , "GROUPNM="+groupNm
        , ""
        , false
        , true
        , true);
        
    if(gds_userlist.RowCount() > 0) {
		return true;
    } else {
		return false;
    }   
}

function gfn_hideGridColumn(gridNm,colNm){
	// 컬럼 길이 조정 
	gridNm.SetColProp(colNm,"Width",0);
	// 컬럼 Display
	gridNm.SetCellProp("Head", colNm, "Display", "null");	
	//  컬럼 header 				
	gridNm.SetCellProp("Head", colNm, "text", "");	
	//  컬럼 바인딩 해제 					
	gridNm.SetCellProp("Body", colNm, "ColId", "null");
}


function gfn_getAuth(dsObj) {
	//gds_role.ClearData();
	//gds_rolecnt.ClearData();		
	destroy("gds_role");
	Create("dataset", "gds_role");
	object("gds_role").AddColumn("ZPROGRAM", "STRING", "256");
	object("gds_role").AddColumn("GBN", "STRING", "256");
	
	destroy("gds_rolecnt");
	Create("dataset", "gds_rolecnt");
	object("gds_rolecnt").AddColumn("CNT", "STRING", "256");	
	
	gds_role.copy( dsObj );
	
	tit_clearActionInfo();
	tit_addSearchActionInfo("wb01:GET_AUTH_S1"); // 권한 체크
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_role"
        , "gds_rolecnt=ds_list"
        , ""
        , ""
        , false
        , true
        , true);
        
    if(gds_rolecnt.GetColumn(0, "CNT") > 0) {
		return true;
    } else {
		return false;
    }   
}

function gfn_getAuth2(dsObj) {
	//gds_role.ClearData();
	//gds_rolecnt.ClearData();		
	destroy("gds_role");
	Create("dataset", "gds_role");
	object("gds_role").AddColumn("ZPROGRAM", "STRING", "256");
	object("gds_role").AddColumn("GBN", "STRING", "256");
	
	destroy("gds_rolecnt");
	Create("dataset", "gds_rolecnt");
	object("gds_rolecnt").AddColumn("CNT", "STRING", "256");	
	
	gds_role.copy( dsObj );
	
	tit_clearActionInfo();
	tit_addSearchActionInfo("wb01:GET_AUTH_S2"); // 부서별 권한 체크
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_role"
        , "gds_rolecnt=ds_list"
        , ""
        , ""
        , false
        , true
        , true);
        
    if(gds_rolecnt.GetColumn(0, "CNT") > 0) {
		return true;
    } else {
		return false;
    }   
}

function gfn_getAuthDpt(dsObj) {
	//gds_role.ClearData();
	//gds_rolecnt.ClearData();		
	destroy("gds_role");
	Create("dataset", "gds_role");
	object("gds_role").AddColumn("ZPROGRAM", "STRING", "256");
	object("gds_role").AddColumn("GBN", "STRING", "256");
	
	destroy("gds_roleDpt");
	Create("dataset", "gds_roleDpt");
	object("gds_roleDpt").AddColumn("DPT", "STRING", "256");	
	
	gds_role.copy( dsObj );
	
	tit_clearActionInfo();
	tit_addSearchActionInfo("wb01:GET_AUTH_DPT_S1"); // 결재권한
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_role"
        , "gds_roleDpt=ds_list"
        , ""
        , ""
        , false
        , true
        , true);

    if(length(gds_roleDpt.GetColumn(0, "DPT")) > 0) {
		return gds_roleDpt.GetColumn(0, "DPT");
    } else {
		return G_DPT1;
    }   
}

/****************************************************************
* 날짜 유효성 체크
* @param obj      날짜
/****************************************************************/
function gfn_dateValidChk(date) {
	
    var yyyyMMdd = date;
    var year = yyyyMMdd.SubStr(0,4);
    var month = yyyyMMdd.SubStr(4,2);
    var day = yyyyMMdd.SubStr(6,2);
    
    if (!isDigit(yyyyMMdd)) {
		gfn_showAlert("숫자형태로 입력해주세요. 예) 20190101");
        return false;
    }
    
    if(date.length != 8) {
		gfn_showAlert("자릿수를 확인해주세요. 예) 20190101");
        return false;
    }

    if (toNumber(month)>12 || toNumber(month)<1) {
		gfn_showAlert("입력한 년월 혹은 일자를 확인해주세요.");
        return false;
        
    }

    if (toNumber(gfn_getLeapLastDay(yyyyMMdd).SubStr(6,2))<day) {
		gfn_showAlert("올바른 일자를 입력해주세요. 예) 20190101");
        return false;
    }

    return true;

}



/****************************************************************
* 메시지 코드를 받아서 Confirm 처리 (서비스 전자결재)
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
* @return 확인 - true / 취소 - false
***************************************************************/
function gfn_showConfirm_decide( msgCode, arr) 
{
	var msg = gfn_getMsg(msgCode, arr);
	if ( msg == null ) 
	{
		msg = msgCode;
	}
	return gfn_openMsgWin_decide(msg, "I", "C", msgCode );
}

/****************************************************************
* 메시지창 띄우기
* @param sMsg 메시지 내용
* @param msgType 메시지 타입
* @param showType A - Alert , C - Confirm
* @param msgCode 메시지 코드
***************************************************************/
function gfn_openMsgWin_decide(sMsg, msgType, showType, msgCode ) 
{
	var arg = "fa_msg=" + quote( sMsg );
	if ( length( msgType ) == 0 ) 
	{
		msgType = "I";
	}
	arg += " fa_msgType=" + quote( msgType );
	if ( length( showType ) == 0 ) 
	{
		showType = "A";
	}
	arg += " fa_showType=" + quote( showType );
	if ( length( msgCode ) > 0 ) 
	{
		arg += " fa_msgCode=" + quote( msgCode );
	}

	return Dialog("CS96::DecideMsgForm.xml", arg, -1, -1, "titlebar=false statusBar=false");
}

/****************************************************************
* 해외 서비스 부품수출 견적 원가 계산 - CS8008001B 전용. 서비스자재팀 이지원 요청 
*  2021 . 06 . 적용 
* @param p1_ucost	    재료비 
* @param p3_sum_ofr     견적가
* @param p4_netwr_ofr   자재판매가 
* @param p5_add_ucost   추가-재료비
* @param p8_add_log     추가-운임
* @param p_netwr        SAP 수주가
* @return  str
****************************************************************/
function gfn_calculate_cost_zc01_cs8008001b ( p1_ucost
                                 , p3_sum_ofr
                                 , p4_netwr_ofr
                                 , p5_add_ucost
                                 , p8_add_log
                                 , p_netwr)
{
/****************************************************************/	
// 원가 항목 셋팅 
/****************************************************************/

	var his_ucost = toNumber(p1_ucost);    // 재료비
	var his_log;      // 운반비	
	var his_dirsum;   // 직접비
	var his_indsum;   // 간접비
	var his_totcost;  // 총원가
	var his_his_rate; //원가율(시행율)
	
	var sum_ofr    = toNumber(p3_sum_ofr);    // 견적가
	var netwr_ofr  = toNumber(p4_netwr_ofr);  // 자재판매가
	var ucost_add  = toNumber(p5_add_ucost);
	var log_add    = toNumber(p8_add_log);
	
	var his_netwr = toNumber(p_netwr); // SAP 수주가

/****************************************************************/	
// 원가 계산 
/****************************************************************/		
	his_ucost = his_ucost + ucost_add;

	// 운임 
	his_log = log_add;		
		
	his_dirsum = his_ucost + his_log ;
	// 간접비율 INDIR_RAT
	his_indsum = 0;
	// 제조간접비  직접비의 MOINDIR_RAT
	his_labor = 0;
	// 총원가 = 직접비 + 간접비 + 제조간접비 
	his_totcost = his_dirsum + his_indsum + his_labor;
	// 시행율 = (자재비 원가/SAP 수주가)*100
	his_rate = round((his_ucost / his_netwr) * 100, 1);
	
	var str = quote(his_ucost)    + "^";	
	    str += quote(his_log)     + "^";
	    str += quote(his_dirsum)  + "^";
	    str += quote(his_indsum)  + "^";
	    str += quote(his_totcost) + "^";
	    str += quote(his_rate)    ;	

	return str;
		
}

