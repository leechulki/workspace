﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 영업사양특성 복사 시 특성값 존재 체크 공통
 *  작성일자 : 20020/09/02
 *  버전 : 1.0
 *  변경 이력
    일자            작성자          비고
    ------------------------------------------------------------
    2020/09/02     박수근          최초 작성
================================================================================*/
/****************************************************************
* 브랜드별 영업특성값 조회
* brndseq: 브랜드적용차수
* brndcd: 브랜드코드
***************************************************************/
function fn_sayangPrdList(brndseq, brndcd) {
    if(brndcd == "") {
        brndcd = "NOBRND";
    }

    if(brndseq == "") {
        brndseq = "000";
    }

	var dsId = "ds_brnd_prd"+brndcd+brndseq;
    if(!IsValid(object(dsId))) {
		Create("dataset",  dsId);
		object(dsId).addColumn("BRNDCD", "STRING");
		object(dsId).addColumn("PRH", "STRING");
		object(dsId).addColumn("PRD", "STRING");
		object(dsId).addColumn("KEYIN", "STRING");

	    Create("dataset", "ds_cond_param");
	    object("ds_cond_param").addColumn("BRNDSEQ", "STRING");
	    object("ds_cond_param").addColumn("ZPRDGBN", "STRING");
	    object("ds_cond_param").addColumn("BRNDCD", "STRING");
        object("ds_cond_param").AddRow();
        
        object("ds_cond_param").SetColumn(0, "BRNDSEQ", brndseq);
        object("ds_cond_param").SetColumn(0, "ZPRDGBN", "ELV_01");
        object("ds_cond_param").SetColumn(0, "BRNDCD", brndcd);

		http.Sync = true;
		nsfRequest("sayangPrdList"
				   , "coms01a/sayangPrdList"  
				   , "ds_cond=ds_cond_param" 
				   , object(dsId).ID+"=ds_brnd_prd"
				   , ""
				   , "");
		http.Sync = false;

		destroy("ds_cond_param");
	}
}

/****************************************************************
* 브랜드별 영업특성 복사 시 특성값 존재여부 체크
* brndseq: 브랜드적용차수
* brndcd: 브랜드코드
* prh: 영업특성
* prd: 영업특성값
***************************************************************/
function fn_isBrndPrd(brndseq, brndcd, prh, prd) {
    //if(brndcd == "") {
    if(brndcd == "" || brndcd == null) {
        brndcd = "NOBRND";
    }

    if(brndseq == "") {
        brndseq = "000";
    }

    var dsId = "ds_brnd_prd"+brndcd+brndseq;
    var bChkRslt = false;
    var searchRow = 0;
    var filterString = "";

    // 브랜드값이 정의되어 있지 않으면 false
    /**
    if(brndcd == NULL) {
        return false;
    }

    if(brndcd == "") {
        brndcd = "NOBRND";
    }
   */

    // 체크 대상이 아님 true
    //if(prd == NULL && prd == "") {
    if(prd == null || prd == "") {
        return true;
    }

	// 이미 영업특성값이 존재함
    filterString = "BRNDCD == "+quote(brndcd)+ " && PRH == "+quote(prh)+ " && PRD == "+quote(prd);
    searchRow = object(dsId).searchRow(filterString);
    
    if(searchRow > -1) {
        bChkRslt = true;
    } else {
		
		filterString = "BRNDCD == "+quote(brndcd)+ " && PRH == "+quote(prh)+ " && KEYIN == 'Y'";
		searchRow = object(dsId).searchRow(filterString);
		
		if(searchRow > -1) {
			bChkRslt = true;
		} else {
			bChkRslt = false;
		}
    }

	return bChkRslt;
}

