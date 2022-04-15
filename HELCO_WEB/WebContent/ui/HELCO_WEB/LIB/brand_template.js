﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 영업사양특성 브랜드 적용 및 PLM 연동 처리 공통 스크립트
 *  작성일자 : 20020/06/17
 *  버전 : 1.0
 *  변경 이력
    일자            작성자          비고
    ------------------------------------------------------------
    2020/006/17     박수근          최초 작성
================================================================================*/
/****************************************************************
* 영업사양 option 소그룹데이터 생성 조회
***************************************************************/
var G_grpCodeArray = ["EL_ERHDH" ,"EL_EMRLC" ,"EL_FDBBT" ,"EL_FDSP1" ,"EL_AFFT1" ,"EL_AFFT2" ,"EL_AFFT3" ,"EL_AFFT4" ,"EL_AFFT5" ,"EL_AFFT6" ,"EL_AFFT7" ,"EL_AFFT0" ,"EL_EFHB5"];
var G_grpNameArray = ["교체공사" ,"자동도면" ,"설치옵션" ,"개별견적" ,"승장사양(1) - 기준층 포함층" ,"승장사양(2)" ,"승장사양(3)" ,"승장사양(4)" ,"승장사양(5)" ,"승장사양(6)" ,"승장사양(7)" ,"승장사양(CP)" ,"층고"];
function fn_findZsdt1143List() {
	// DS 생성
	Create("dataset", "ds_zsdt1143");
	object("ds_zsdt1143").addColumn("MANDT", "STRING");
	object("ds_zsdt1143").addColumn("GRPPRH", "STRING");
	object("ds_zsdt1143").addColumn("GRPDESC", "STRING");

	Create("dataset", "ds_cond_param");
	object("ds_cond_param").addColumn("MANDT", "STRING");
	object("ds_cond_param").addColumn("ZPRDGBN", "STRING");
    object("ds_cond_param").AddRow();
    object("ds_cond_param").SetColumn(0, "ZPRDGBN", "ELV_01");

    http.Sync = true;
    nsfRequest("optGrpTitle"
			, "coms01a/findGroupTitList"
			, "ds_cond=ds_cond_param ds_zsdt1143=ds_zsdt1143"
			, "ds_zsdt1143=ds_zsdt1143 ds_error=ds_error"
			, ""
			, ""
			);
	http.Sync = false;

	if(ds_error.GetRowCount() > 0 )	{
	    gfn_showAlert(ds_error.GetColumn(0,"ERRMSG"));
		return false;
	} else {
	    G_grpCodeArray = Array(object("ds_zsdt1143").GetRowCount());
	    G_grpNameArray = Array(object("ds_zsdt1143").GetRowCount());
	    for(var i=0; i < object("ds_zsdt1143").GetRowCount(); i++) {
	        G_grpCodeArray[i] = object("ds_zsdt1143").GetColumn(i, "GRPPRH");
	        G_grpNameArray[i] = object("ds_zsdt1143").GetColumn(i, "GRPDESC");
	    }
	}

	// DS 삭제
	destroy("ds_zsdt1143");
	destroy("ds_cond_param");
}

/****************************************************************
* 영업사양 option 소그룹데이터 생성
***************************************************************/
function initCreateOptGroup(ds_template) {
	var searchRow = 0;
	var searchString = "";
	var qtseq;
	var grpCodeArray = G_grpCodeArray;
	var grpNameArray = G_grpNameArray;
	// 가로셀 머지
    for(var intRow =0; intRow < ds_detail.GetRowCount(); intRow++) {
		qtseq = ds_detail.GetColumn(intRow, "QTSEQ");
        for(var codeRow = 0; codeRow < Length(grpCodeArray); codeRow++) {
            // 메인 사양이 화면에 출력시 표기
			searchString = "QTSEQ =="+quote(qtseq)+"&&PRH=="+quote(grpCodeArray[codeRow])+"&&DISPTP=="+quote("X");
			searchRow = ds_template.SearchRow(searchString);
			if(searchRow > -1) {
			    // 만약 이미 값이 추가 되어 있으면 패스
			    var agoPRHNAME = ds_template.GetColumn(searchRow-1, "PRHNAME");
			    if(agoPRHNAME != grpNameArray[codeRow]) {
					var tmpMandt = ds_template.GetColumn(searchRow, "MANDT");
					var tmpQTNUM = ds_template.GetColumn(searchRow, "QTNUM");
					var tmpQTSER = ds_template.GetColumn(searchRow, "QTSER");
					var tmpQTSEQ = ds_template.GetColumn(searchRow, "QTSEQ");
					var tmpATKLA = ds_template.GetColumn(searchRow, "ATKLA");
					var tmpPRHNAME = ds_template.GetColumn(searchRow, "PRHNAME");
	
					var nRow = ds_template.InsertRow(searchRow);
					ds_template.SetColumn(nRow, "MANDT", tmpMandt);
					ds_template.SetColumn(nRow, "QTNUM", tmpQTNUM);
					ds_template.SetColumn(nRow, "QTSEQ", tmpQTSEQ);
					ds_template.SetColumn(nRow, "QTSER", tmpQTSER);
					ds_template.SetColumn(nRow, "ATKLA", tmpATKLA);
					ds_template.SetColumn(nRow, "ATFOR", "CHAR");
					ds_template.SetColumn(nRow, "CNT", "1");
					ds_template.SetColumn(nRow, "FLAG", "");
					ds_template.SetColumn(nRow, "PRH", "OPT_GROUP_TITLE");
					ds_template.SetColumn(nRow, "PRHNAME", grpNameArray[codeRow]);
					ds_template.SetColumn(nRow, "PRD", '');
					ds_template.SetColumn(nRow, "DISPTP", 'X');
					ds_template.SetColumn(nRow, "MODITP", 'X');
			    }
			}
        }
    }
}

/****************************************************************
* 영업사양 option 소그룹데이터 생성
***************************************************************/
function optGroupStyle(grdObj) {
    // user21, lightskyblue, lightgrey  #E4EDF4  #E4EDF4 #E4EDF4
	grdObj.Redraw = false;
	grdObj.SetCellProp("body", 1, "bkColor", "expr:iif(PRH == 'OPT_GROUP_TITLE', '#E4EDF4', 'default')");
	grdObj.SetCellProp("body", 1, "bkColor2", "expr:iif(PRH == 'OPT_GROUP_TITLE', '#E4EDF4', 'default')");

    // 소그룹 타이틀 지정
	grdObj.SetCellProp("body", 2, "font", "expr:iif(PRH == 'OPT_GROUP_TITLE', '맑은 고딕, 10, Bold', '')");

    // 색깔지정
    grdObj.SetCellProp("body", 2, "bkColor", "expr:iif(PRH == 'OPT_GROUP_TITLE', '#E4EDF4', iif(trim(PCNCD) = 'C' && MODITP = 'X', 'peachpuff', iif(trim(PCNCD) = 'P' && MODITP = 'X', 'lightpink', iif(MODITP = '', 'lightgrey','default'))))");
    grdObj.SetCellProp("body", 2, "bkColor2", "expr:iif(PRH == 'OPT_GROUP_TITLE', '#E4EDF4', iif(trim(PCNCD) = 'C' && MODITP = 'X', 'peachpuff', iif(trim(PCNCD) = 'P' && MODITP = 'X', 'lightpink', iif(MODITP = '', 'lightgrey', 'User Color22'))))");
	grdObj.SetCellProp("body", 3, "bkColor", "expr:iif(PRH == 'OPT_GROUP_TITLE', '#E4EDF4', iif(trim(PCNCD) = 'C' && MODITP = 'X', 'peachpuff', iif(trim(PCNCD) = 'P' && MODITP = 'X', 'lightpink', iif(MODITP = '', 'lightgrey', 'default'))))");
	grdObj.SetCellProp("body", 3, "bkColor2", "expr:iif(PRH == 'OPT_GROUP_TITLE', '#E4EDF4', iif(trim(PCNCD) = 'C' && MODITP = 'X', 'peachpuff', iif(trim(PCNCD) = 'P' && MODITP = 'X', 'lightpink', iif(MODITP = '', 'lightgrey', 'User Color22'))))");

    // 정렬처리
    grdObj.SetCellProp("body", 2, "align", "expr:iif(PRH == 'OPT_GROUP_TITLE', 'right', 'left')");

	// 쓰기 금지
	grdObj.SetCellProp("body", 3, "edit", "expr:iif(MODITP == 'X', decode(ATFOR,'NUM','number','normal'), 'none')");
	grdObj.Redraw = true;
}

/****************************************************************
* 영업사양 필터링 상태 플래그 설정
***************************************************************/
function setDsTemplateFlag(src_ds, nRow, strColumnId, varNewValue) {
    src_ds.OnColumnChanged = "";
	var dsOrgValue = src_ds.GetOrgColumn(nRow, "PRD");
	var flag = src_ds.GetColumn(nRow, "FLAG");
	src_ds.SetColumn(nRow, strColumnId, varNewValue);
    if( dsOrgValue != varNewValue ) {
        if(flag == "I") {
            src_ds.SetColumn(nRow, "FLAG", "I");
        } else {
            src_ds.SetColumn(nRow, "FLAG", "U");
        }
    } else if( dsOrgValue == varNewValue ) {
        if(ToLower(src_ds.GetRowType(nRow)) == "update") {
            src_ds.SetColumn(nRow, "FLAG", "U");
        }
    }
	src_ds.OnColumnChanged = "ds_template_OnColumnChanged";		
}

/****************************************************************
* 영업사양 NON 필터링 상태 플래그 설정
***************************************************************/
function setDsTemplateFlagNF(src_ds, nRow, strColumnId, varNewValue) {
    src_ds.OnColumnChanged = "";
	var dsOrgValue = src_ds.GetOrgColumnNF(nRow, "PRD");
	var flag = src_ds.GetColumnNF(nRow, "FLAG");
	src_ds.SetColumnNF(nRow, strColumnId, varNewValue);
    if( dsOrgValue != varNewValue ) {
        if(flag == "I") {
            src_ds.SetColumnNF(nRow, "FLAG", "I");
        } else {
            src_ds.SetColumnNF(nRow, "FLAG", "U");
        }
    } else if( dsOrgValue == varNewValue ) {
        if(ToLower(src_ds.GetRowTypeNF(nRow)) == "update") {
            src_ds.SetColumnNF(nRow, "FLAG", "U");
        }
    }
	src_ds.OnColumnChanged = "ds_template_OnColumnChanged";		
}

/****************************************************************
* 브랜드 적용 플래그 조회
* 견적일자 기준
* return 변수명: F_BRND_FLAG
***************************************************************/
function fn_brndFlag(qtnum) {
	nsfRequest("brndFlag"
			, "coms01a/brndFlag"
			, "ds_brnd_flag=ds_brnd_flag"
			, "ds_error=ds_error ds_brnd_flag=ds_brnd_flag"
			, "G_QTNUM="+quote(qtnum)
			, "fn_brndFlagafterQuery"
			);
}

/****************************************************************
* 브랜드 적용 플래그 조회
* 데이터셑 ID:ds_brnd_flag
* 견적일자 기준 
***************************************************************/
function fn_brndFlagafterQuery(svcId, errCode, errMsg) {
	if(ds_error.rowcount() > 0) {
		gfn_showMsg("CE00001");
	} else {
	    if(ds_brnd_flag.GetRowCount() > 0) {
	        F_BRND_FLAG = ds_brnd_flag.GetColumn(0, "F_BRND_FLAG");
	    }
	}
}
