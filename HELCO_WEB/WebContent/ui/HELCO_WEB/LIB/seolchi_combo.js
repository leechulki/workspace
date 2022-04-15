﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 공통 Script
 *  작성일자 : 2008/03/03
 *  버전 : 1.0
 *  변경 이력
    일자            작성자          비고
    ------------------------------------------------------------
    2008/03/03                      최초 작성
================================================================================*/

var is_area = false;
var is_pm   = false;
var is_vend = false;
var is_team = false;

/****************************************************************
* 설치 콤보 BOX 처리
* 개발자 사용 API 아님.
***************************************************************/
function gfn_seolchi_combo(fg_area, fg_pm, fg_vend, fg_team) {
	var param1 = "";
	var param2 = "";

	// 콤보박스 존재
	is_area = fg_area;
	is_pm   = fg_pm;
	is_vend = fg_vend;
	is_team = fg_team;
	
	// ACTION 정보 초기화
	tit_clearActionInfo();

	if (fg_area)
	{
		tit_addSearchActionInfo("wb01:SEOLCHI_AREA_S1");	// 코드콤보 설정 : 사무소
		param1 += " ds_zzactss_cd=ds_zzactss_cd"; 
	}
	if (fg_pm)
	{
		tit_addSearchActionInfo("wb01:SEOLCHI_PM_S1"); 	// 코드콤보 설정 : PM
		param1 += " ds_zzpmnum_cd=ds_zzpmnum_cd"; 
	}
	if (fg_vend)
	{
		tit_addSearchActionInfo("wb01:SEOLCHI_VEND_S1"); 	// 코드콤보 설정 : 협력업체
		param1 += " ds_zzlifnr_cd=ds_zzlifnr_cd"; 
	}
	if (fg_team)
	{
		tit_addSearchActionInfo("wb01:SEOLCHI_TEAM_S1"); 	// 코드콤보 설정 : TEAM
		param1 += " ds_zztemno_cd=ds_zzteamno_cd"; 
	}

	// 권한에 따른 분기
	if( gfn_getUserGroup() == '10' )
	{	
		ds_cond_cd.SetColumn(0, "AREA_CODE", gfn_getUserArea());	
		ds_cond_cd.SetColumn(0, "VEND_CODE", G_VEND_CODE);	
	}
	else if( gfn_getUserGroup() == '20' )
	{
		ds_cond_cd.SetColumn(0, "AREA_CODE", gfn_getUserArea());	
		ds_cond_cd.SetColumn(0, "USER_ID",   G_USER_ID);	
	}
	else if( gfn_getUserGroup() == '30' )
	{
		ds_cond_cd.SetColumn(0, "AREA_CODE", gfn_getUserArea());	
	}

	//  서비스 실행	 
	tit_callService(
		""
		, ""
		, "ds_cond=ds_cond_cd"
		, param1
		, ""
		, "fn_afterSearch"
		, true,true);
}

/********************************************************************************
* 기      능   : 조회 Callback  - 설치협력사 로그인시 협력업체코드 첫번째 선택
********************************************************************************/
function fn_afterSearch(errCode, errMsg) {
	if( gfn_getUserGroup() == '10' )
	{	
		if (is_area)
		{
			cb_zzactss.Index = 0;
			cb_zzactss.Enable = false;
		}
		if (is_pm)
		{
			cb_zzpmnum.Enable = false;
		}
		if (is_vend)
		{
			cb_zzlifnr.Index = 0;
			cb_zzlifnr.Enable = false;
		}
	}
	else if( gfn_getUserGroup() == '20' )
	{
		if (is_area)
		{
			cb_zzactss.Index = 0;
			cb_zzactss.Enable = false;
		}
		if (is_pm)
		{
			cb_zzpmnum.Index = 0;
			cb_zzpmnum.Enable = false;
		}
		if (is_vend)
		{
			gfn_insertEmptyRow(ds_zzlifnr_cd, G_CODE_ALL);
			cb_zzlifnr.value = "";	
		}
	}	
	else if( gfn_getUserGroup() == '30' )
	{
		if (is_area)
		{
			cb_zzactss.Index = 0;
			cb_zzactss.Enable = false;
		}
		if (is_vend)
		{
			gfn_insertEmptyRow(ds_zzlifnr_cd, G_CODE_ALL);
			cb_zzlifnr.value = "";
		}
	}	
	else if( gfn_getUserGroup() == '40' )
	{
		if (is_area)
		{
			gfn_insertEmptyRow(ds_zzactss_cd, G_CODE_ALL);
			cb_zzactss.value = "";	
		}
		if (is_vend)
		{
			gfn_insertEmptyRow(ds_zzlifnr_cd, G_CODE_ALL);
			cb_zzlifnr.value = "";
		}	
	}	
}
//=========================================================================================
// component Event 정의 : 각 Component 별 이벤트 
// 사무소값 변경되면 설치PM값을 filtering해준다
//=========================================================================================
function cb_zzactss_OnChanged(obj, strCode, strText, nOldIndex, nNewIndex)
{
	cb_zzpmnum.value = "";
	if ( strCode == "" ) {
//		ds_zzpmnum_cd.filter("ZZACTSS ==''");
	} else {
		ds_zzpmnum_cd.filter("ZZACTSS == " + quote( strCode) );
	}	
}
//=========================================================================================
// component Event 정의 : 각 Component 별 이벤트 
// 설치PM값 변경되면 협력업체값을 filtering해준다
//=========================================================================================
function cb_zzpmnum_OnChanged(obj, strCode, strText, nOldIndex, nNewIndex)
{
//	cb_zzlifnr.value = "";
//	cb_zztemno.value = "";
//	if ( strCode == "" ) {
//		ds_zzlifnr_cd.filter("ZZPMNUM ==''");
//	} else {
//		ds_zzlifnr_cd.filter("ZZPMNUM == " + quote( strCode) );
//	}	
}
//=========================================================================================
// component Event 정의 : 각 Component 별 이벤트 
// 협력업체값 변경되면 작업팀장값을 filtering해준다
//=========================================================================================
function cb_zzlifnr_OnChanged(obj, strCode, strText, nOldIndex, nNewIndex)
{
	cb_zztemno.value = "";
	
	if ( strCode == "" ) {
//		ds_zztemno_cd.filter("ZZLIFNR ==''");
	} else {
		ds_zztemno_cd.filter("ZZLIFNR == " + quote( strCode) );
	}	
}
