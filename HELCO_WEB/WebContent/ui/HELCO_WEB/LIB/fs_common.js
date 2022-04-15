﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/******************************************************
* 품목 항목 드룹다운 이미지 상태 변경 함수
* @param  flagValue 해당하는 Flag 처리 값
* @return  플래그에 해당하는 bkImageID
******************************************************/
function gfn_ItemImage( dsObj, nRow ) {
	var dataObject = Object(dsObj.DataObject);
	var nRowNF = dsObj.GetRowIndexNF(nRow);
	var rowTotal = dsObj.GetRowCountNF(); 
	var nRowNextNF = -1;
	if (dsObj.GetRowCount() >= nRowNF){ 
		nRowNextNF = dsObj.GetRowIndexNF(nRow+1);
	}
	return iif ((nRowNF+1) == nRowNextNF, "icon_open_item" , "icon_close_item");
}
﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/******************************************************
* 결재 테이블 조회를 통하여 다음 결재자와 결재 상태 조회
* @param  ds_appr 결재선 정보 DataSet
* @return  [현재 결재 상태, 다음 결재 상태,결재 권한자, 기안자, 심사자, 승인자]
******************************************************/
function gfn_docApprInfo( ds_appr )
{
	var rtnInfo = array();
	
	var sDrafStat = ds_appr.GetColumn(0, "DRAF_STAT");
	var sAditStat = ds_appr.GetColumn(0, "ADIT_STAT");
	var sApprStat = ds_appr.GetColumn(0, "APPR_STAT");
	var sDraf = ds_appr.GetColumn(0, "DRAF");
	var sAdit = ds_appr.GetColumn(0, "ADIT");
	var sAppr = ds_appr.GetColumn(0, "APPR");
	
	var currApprStat = "";
	var nextApprUser = "";
	var nextApprStat = "";
	
	if (sDrafStat == "150")
	{
		currApprStat = sDrafStat;
		nextApprUser = "";
		nextApprStat = "";
	}
	else if (sDrafStat == "510")
	{
		currApprStat = sDrafStat;
		nextApprUser = "";
		nextApprStat = "";
	}
	else if (sDrafStat != "190")
	{
		currApprStat = sDrafStat;
		nextApprUser = sDraf;
		nextApprStat = "190";
	}
	else
	{
		var checkAppr = true;
		if ( length( trim( sAdit + "" ) ) > 0 )
		{
			if (sAditStat == "040")
			{
				currApprStat = sAditStat;
				nextApprUser = sDraf;
				nextApprStat = "110";
				checkAppr = false;
			}
			else if (sAditStat == "250" )
			{
				currApprStat = sAditStat;
				nextApprUser = "";
				nextApprStat = "";
				checkAppr = false;
			}
			else if (sAditStat != "290")
			{
				currApprStat = sAditStat;
				nextApprUser = sAdit;
				nextApprStat = "290";
				checkAppr = false;
			}
		} 
		if (checkAppr) {
			if (length( trim( sAppr + "" ) ) > 0)
			{
				if (sApprStat == "040")
				{
					currApprStat = sApprStat;
					nextApprUser = sDraf;
					nextApprStat = "190";
				}
				else if (sApprStat == "390" )
				{
					currApprStat = sApprStat;
					nextApprUser = "";
					nextApprStat = "";
				}
				else 
				{
					currApprStat = sAditStat;
					nextApprUser = sAppr;
					nextApprStat = "390";
				}
			}
			else
			{
				currApprStat = sAditStat;
				nextApprUser = sAdit;
				nextApprStat = "250";
			}
		}
	}
	else
	{
		currApprStat = sDrafStat;
		nextApprUser = sDraf;
		nextApprStat = "190";
	}
	rtnInfo[0] = currApprStat;
	rtnInfo[1] = nextApprStat;
	rtnInfo[2] = nextApprUser;
	rtnInfo[3] = sDraf;
	rtnInfo[4] = sAdit;
	rtnInfo[5] = sAppr;
	return rtnInfo;
}

﻿﻿﻿﻿﻿﻿﻿﻿/******************************************************
* gfn_showConfirm 함수의 Alias 함수
* @param  orgString
******************************************************/
function gfn_messageWin(msgCode, param, isAlert, msgType)
 {
	var arg = array();
	if (type(param) == "string") 
	{
		if (length( param ) > 0) arg[0] = param;
	}
	else 
	{
		arg = param;
	}
	var msg = gfn_getMsg(msgCode, arg);
	var sAlert = iif(isAlert==true, "A", "C");
	if (length( msgType ) < 1) msgType = "I";
	msgType = decode(ToUpper( msgType ) , "E", "E", "W", "W", "I") ;
	if ( msg == null ) 
	{
		msg = msgCode;
		msgCode = "";
	}
	else
	{
		msgType = charAt( msgCode, 1 );
	}
	return gfn_openMsgWin(msg, msgType, sAlert, msgCode );
 }

﻿﻿﻿﻿﻿﻿﻿﻿/******************************************************
* 텍스트 변환 함수 Null 을 공백으로
* @param  orgString
******************************************************/
function gfn_toString(orgString, defString)
{
	defString = iif(defString==null, "" , defString);
	return iif( trim( orgString) == null , defString, trim( orgString) );
}

﻿﻿﻿﻿﻿﻿﻿﻿/******************************************************
* 조회시 상태바 메시지 표현
* @param  nRowCount : 상태바에 표현할 조회 레코드수
*		, errCode : 에러코드
* @return  에러일경우 true
******************************************************/
function gfn_barMsgSearch(nRowCount, errCode, isAlert)
{
	isAlert = iif(isAlert, true, false);
	if( errCode != 0 ) {
		if (isAlert) gfn_showAlert("CE00001");
		else gfn_showMsg("CE00001");
		return true;
	}
	var arrParam = Array(1);
	arrParam[0] = nRowCount;
	if (isAlert) gfn_showAlert( "CI00002", arrParam );
	else gfn_showMsg( "CI00002", arrParam );
	return false;
}

﻿﻿﻿﻿﻿﻿﻿﻿/******************************************************
* 삭제시 상태바 메시지 표현
* @param   errCode : 에러코드
* @return  에러일경우 true
******************************************************/
function gfn_barMsgDelete(errCode, isAlert)
{
	isAlert = iif(isAlert, true, false);
	if( errCode != 0 ) {
		if (isAlert) gfn_showAlert("CE00001");
		else gfn_showMsg("CE00001"); 
		return true;
	}
	if (isAlert) gfn_showAlert("CI00004");
	else gfn_showMsg("CI00004"); 
	return false;
}

﻿﻿﻿﻿﻿﻿﻿﻿/******************************************************
* 저장시 상태바 메시지 표현
* @param   errCode : 에러코드
* @return  에러일경우 true
******************************************************/
function gfn_barMsgSave(errCode, isAlert)
{
	isAlert = iif(isAlert, true, false);
	if( errCode != 0 ) {
		if (isAlert) gfn_showAlert("CE00001");
		else gfn_showMsg("CE00001"); 
		return true;
	}
	if (isAlert) gfn_showAlert("CI00003");
	else gfn_showMsg("CI00003"); 
	return false;
}

﻿﻿﻿﻿﻿﻿﻿﻿/******************************************************
* 화면 종료 함수
* @param  bStatus : 화면의 변경여부
******************************************************/
function gfn_formClose( bStatus )
{
	if ( bStatus && !gfn_openMsgWin("저장처리 없이 종료하시겠습니까?", "I","C") )  return;
	close();
}

/******************************************************
* 그리드 입력값 변경 처리
* @param  rowType 해당하는 Flag 처리 값
*         ,strStatusField 사용자 상태값 필드
* @return  플래그에 해당하는 bkImageID
******************************************************/
function gfn_DataStatImage( rowType ) {
	var bkImg = "";
	switch( rowType ) {
		case "insert":
			bkImg = "icon_grid_add";
			break;
		case "update":
			bkImg = "icon_grid_modify";
			break;
		case "delete":
			bkImg = "icon_grid_del";
			break;
		default:
			bkImg = "";
			break;
	}
	return bkImg;
}

/******************************************************
* 그리드 DrillDown Folder Image Icon 변경
* @param  obj : DataSet 객체
		, nRow : 현재행
		, strBaseFilter : 기본적으로 적용되어야 하는 필터조건
		, strActionID : 하위 단계를 접도록 설정하는 필드
		, strSidID : 데이터에서 유일값을 구분하는 키 필드
		, strPSidID : 데이터에서 상위 유일값을 구분하는 필드 
* @return  플래그에 해당하는 bkImageID
******************************************************/
function gfn_DrillDownFoldingImage(filterDS, nRow)
{
	var orgDataset = Object(filterDS.DataObject); 
	if ( type(orgDataset) != "object" ) return "";
	var nRowNF = filterDS.GetRowIndexNF(nRow);
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strActionID = GetVariable(filterDS.ID+"_ACTION", "Local");
	var strSidID = GetVariable(filterDS.ID+"_SID", "Local");
	var strPSidID = GetVariable(filterDS.ID+"_PSID", "Local");
	var strActionValue = GetVariable(filterDS.ID+"_ACTVALUE", "Local");
	var strRootPSidValue = GetVariable(filterDS.ID+"_PSIDVALUE", "Local");
	var sidValue = filterDS.GetColumn(nRow, strSidID);
	var nextRowNF = orgDataset.SearchRow(strBaseFilter + " && " + strPSidID + " == \""+sidValue+"\"", nRowNF); 
	if ( nextRowNF >= 0 ){
		var rowHide = filterDS.GetColumn(nRow, strActionID); 
		return iif( rowHide == strActionValue , "icon_open_item", "icon_close_item" );
	}
	return "";
}

/******************************************************
* 그리드 DrillDown 필터 
* @param  obj : DataSet 객체
		, nRow : 현재행
		, strBaseFilter : 기본적으로 적용되어야 하는 필터조건
		, strActionID : 하위 단계를 접도록 설정하는 필드
		, strSidID : 데이터에서 유일값을 구분하는 키 필드
		, strPSidID : 데이터에서 상위 유일값을 구분하는 필드 
* @return  행 필터 결과
******************************************************/
function gfn_DrillDownFilter(filterDS, nRow)
{
	var orgDataset = Object(filterDS.DataObject); 
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strActionID = GetVariable(filterDS.ID+"_ACTION", "Local");
	var strLevelID = GetVariable(filterDS.ID+"_LEVEL", "Local");
	var strSidID = GetVariable(filterDS.ID+"_SID", "Local");
	var strPSidID = GetVariable(filterDS.ID+"_PSID", "Local");
	var nRowNF = filterDS.GetRowIndexNF(nRow);
	if (orgDataset.SearchRow(strBaseFilter, nRowNF, nRowNF) >= 0) {
		var psidValue = filterDS.GetColumnNF(nRow, strPSidID);
		var pIndex = filterDS.FindRowNF( strSidID, psidValue );
		while( pIndex >= 0 )
		{
			if ( filterDS.GetColumnNF(pIndex, strActionID) == '0' ) return false;
			psidValue = filterDS.GetColumnNF(pIndex, strPSidID);
			pIndex = filterDS.FindRowNF( strSidID, psidValue );
		}
		return true;
	}
	return false;
}

/******************************************************
* 그리드 DrillDown 설정
* @param  obj : DrillDown 그리드
		, nRow : 추가하려는 기준 행번호
		, isChild : TRUE - 기준행 하위 레벨로 삽입, FALSE - 기준행과 동일 레벨로 삽입
		, oNewSid : 신규로 추가하려는 행의 키값
		, fn_setter : 삽입후 데이터 설정 함수명, 전달인자로 새로 추가된 행번호와 oNewSid(문자열)를 전달
* @return  원본 Dataset에 추가된 행번호 , -1은 실패
******************************************************/
function gfn_DrillDownAddRow(obj, nRow, isChild, oNewSid, fn_setter )
{
	var filterDS = Object(obj.BindDataset);
	var orgDataset = Object(filterDS.DataObject); 
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strActionID = GetVariable(filterDS.ID+"_ACTION", "Local");
	var strLevelID = GetVariable(filterDS.ID+"_LEVEL", "Local");
	var strSidID = GetVariable(filterDS.ID+"_SID", "Local");
	var strPSidID = GetVariable(filterDS.ID+"_PSID", "Local");
	var strCheckID = GetVariable(filterDS.ID+"_CHECK", "Local");
	var strActionValue = GetVariable(filterDS.ID+"_ACTVALUE", "Local");
	var strRootPSidValue = GetVariable(filterDS.ID+"_PSIDVALUE", "Local");

	var iRow = 0;
	var nRowPos = -1;
	var nRowNF = filterDS.GetRowIndexNF(nRow);
	
	var upKeys = gfn_DrillDownUpPath(filterDS, nRowNF);
	var upKeyLen = length( upKeys );
	upKeys[ upKeyLen ] = strRootPSidValue;
	for( iRow = iif( isChild , 0 , 1 ); iRow <= upKeyLen; iRow++ )
	{
		nRowPos = orgDataset.SearchRow(strBaseFilter + " && " + strPSidID + " == '" + upKeys[iRow] + "'", nRowNF+1);
		if ( nRowPos >= 0 ) break;
	}
	if ( nRowPos < 0 ) nRowPos = orgDataset.GetCount();
	
	obj.Redraw = false;
	orgDataset.FireEvent = false;
	iRow = orgDataset.InsertRow( nRowPos );
	orgDataset.SetColumn(iRow, strActionID, strActionValue);
	orgDataset.SetColumn(iRow, strLevelID, parseInt( filterDS.GetColumn(nRow, strLevelID) ) + iif( isChild , 1,0) );
	orgDataset.SetColumn(iRow, strPSidID, iif( isChild , filterDS.GetColumn(nRow, strSidID), filterDS.GetColumn(nRow, strPSidID) ));
	orgDataset.SetColumn(iRow, strSidID, oNewSid);
	if ( trim( strCheckID ) != "" ) 
	{
		orgDataset.SetColumn(iRow, strCheckID, "");
	}
	if (trim(fn_setter) != "") eval( fn_setter+"("+iRow+", '"+oNewSid+"')");
	filterDS.SetColumn( nRow , strActionID , strActionValue );
	filterDS.RecalcFilter(); 
	filterDS.RowPos = filterDS.SearchRow(strBaseFilter + " && " + strSidID + "=='"+ oNewSid +"'" );
	orgDataset.FireEvent = true;
	obj.Redraw = true;
	return iRow;
}

/******************************************************
* 그리드 DrillDown Row Delete
* @param  obj : DrillDown Grid
		, nRow : Filter Dataset에서 삭제할 행번호
* @return  성공시 0 나머지는 실패
******************************************************/
function gfn_DrillDownDeleteRow(obj, nRow)
{
	var filterDS = Object(obj.BindDataset);
	var orgDataset = Object(filterDS.DataObject); 
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strPSidID = GetVariable(filterDS.ID+"_PSID", "Local");
	var nRowNF = filterDS.GetRowIndexNF(nRow);
	var iRow = 0; 
	var nRowPos = -1; 
	
	var upKeys = gfn_DrillDownUpPath(filterDS, nRowNF);
	var upKeyLen = length( upKeys );
	upKeys[ upKeyLen ] = strRootPSidValue;
	for( iRow = 0; iRow <= upKeyLen; iRow++ )
	{
		nRowPos = orgDataset.SearchRow(strBaseFilter + " && " + strPSidID + " == '" + upKeys[iRow] + "'", nRowNF+1);
		if ( nRowPos >= 0 ) break;
	}
	if (nRowPos < 0) nRowPos = orgDataset.GetCount();
	
	obj.Redraw = false;
	if ( nRowPos > 0 ){
		for(iRow = nRowPos-1; iRow > nRowNF; iRow--)
		{
			orgDataset.DeleteRow(iRow);
		}
	}
	iRow = orgDataset.DeleteRow(nRowNF);
	filterDS.RecalcFilter(); 
	filterDS.Row = nRow-1;
	obj.Redraw = true;
	return iRow;
}

/******************************************************
* 그리드 DrillDown Row Move
* @param  obj : DrillDown Grid
		, nRow : Filter Dataset에서 이동하려는 행번호
		, isUp : TRUE - 위로 이동, FALSE - 아래로 이동
		, nCount : 이동하려는 행수
* @return  성공시 0 나머지는 실패
		-2 : 이동불가
******************************************************/
function gfn_DrillDownMoveRow(obj, nRow, isUp, nCount)
{
	var filterDS = Object(obj.BindDataset);
	var orgDataset = Object(filterDS.DataObject); 
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strLevelID = GetVariable(filterDS.ID+"_LEVEL", "Local");
	var strSidID = GetVariable(filterDS.ID+"_SID", "Local");
	var strPSidID = GetVariable(filterDS.ID+"_PSID", "Local");
	var strRootPSidValue = GetVariable(filterDS.ID+"_PSIDVALUE", "Local");

	var movePos = iif(isUp , -1, 1);
	 
	nCount = abs( parseInt ( nCount + "" ) );
	nCount = iif( nCount < 1 , 1 , nCount );
	var nRowCount = orgDataset.GetCount();
	var nRowNF = filterDS.GetRowIndexNF(nRow); 
	var iRow = 1;
	var mRowNF = -1;
	var strSid = orgDataset.GetColumn( nRowNF , strSidID );
	var strPSid = orgDataset.GetColumn( nRowNF , strPSidID );
	var nRowPos = orgDataset.SearchRow(strBaseFilter + " && " + strSidID + "=='"+ strPSid +"'" ) ;
	var aPids = Array();
	var aPidLen = 0;
	var nRowIndex = -1;
	while(nRowPos >= 0)
	{
		nRowPos = orgDataset.SearchRow(strBaseFilter + " && " + strPSidID + "=='"+ strPSid +"'" , nRowPos+1 );
		if ( nRowPos >= 0 ) 
		{
			if ( nRowPos == nRowNF ) nRowIndex = aPidLen;
			aPids[ aPidLen ] = nRowPos;
			aPidLen = length(aPids);
		}
	}
	if ( aPidLen == 0 ) return -2;
	if ( nRowIndex < 0 ) return -1;
	
	nRowIndex = nRowIndex + ( movePos * nCount ) ;
	if ( nRowIndex < 0 ) 
	{
		nRowIndex = 0 ;
	}
	else if ( nRowIndex >= aPidLen ) 
	{
		nRowIndex = aPidLen-1 ;
	}
	mRowNF = aPids[ nRowIndex ] ;
	
	var upKeys = gfn_DrillDownUpPath(filterDS, nRowNF);
	var upKeyLen = length( upKeys ); 
	upKeys[ upKeyLen ] = strRootPSidValue;
	for( iRow = 0; iRow <= upKeyLen; iRow++ )
	{
		nRowPos = orgDataset.SearchRow(strBaseFilter + " && " + strPSidID + " == '" + upKeys[iRow] + "'", nRowNF+1);
		if ( nRowPos >= 0 ) break;
	}
	if (nRowPos < 0) nRowPos = nRowCount; 
	
	var msRowPos = decode( movePos , -1 , nRowPos-1, nRowNF);
	var moRowCount = nRowPos - nRowNF;
	
	if ( movePos > 0 ){
		upKeys = gfn_DrillDownUpPath(filterDS, mRowNF);
		upKeyLen = length( upKeys ); 
		upKeys[ upKeyLen ] = strRootPSidValue;
		for( iRow = 0; iRow <= upKeyLen; iRow++ )
		{
			nRowPos = orgDataset.SearchRow(strBaseFilter + " && " + strPSidID + " == '" + upKeys[iRow] + "'", mRowNF+1);
			if ( nRowPos >= 0 ) break;
		}
		mRowNF = nRowPos - 1;
		if (nRowPos < 0) nRowPos = nRowCount; 
	}
	
	obj.Redraw = false;
	for(iRow = 0; iRow < moRowCount; iRow++)
	{
		orgDataset.MoveRow( msRowPos, mRowNF );
	}
	filterDS.RecalcFilter(); 
	filterDS.Row = filterDS.SearchRow( strSidID + " == '" + strSid + "'" );
	obj.Redraw = true;
	return 0;
}

/******************************************************
* 그리드 DrillDown CheckBox
* @param  obj : DrillDown Grid
		, nRow : 체크 하고자하는 행
		, setValue : 0 -> 기본, 1 -> 체크, -1 -> 언체크
* @return  없음
******************************************************/
function gfn_DrillDownCheckBox(obj, nRow, setValue)
{
	var filterDS = Object(obj.BindDataset);
	var orgDataset = Object(filterDS.DataObject); 
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strSidID = GetVariable(filterDS.ID+"_SID", "Local");
	var strPSidID = GetVariable(filterDS.ID+"_PSID", "Local");
	var strCheckID = GetVariable(filterDS.ID+"_CHECK", "Local");
	var strRootPSidValue = GetVariable(filterDS.ID+"_PSIDVALUE", "Local");
	var nRowNF = filterDS.GetRowIndexNF(nRow);
	var iRow = 0; 
	var nRowPos = -1; 
	
	var currentChecked = orgDataset.GetColumn( nRowNF , strCheckID );
	var newChecked = iif( currentChecked != "", "", "1");
	if (setValue < 0) newChecked = "";
	else if (setValue > 0) newChecked = "1";
	currentChecked = iif( currentChecked != "", "", "1");
	
	var upKeys = gfn_DrillDownUpPath(filterDS, nRowNF);
	var upKeyLen = length( upKeys );
	upKeys[ upKeyLen ] = strRootPSidValue;
	for( iRow = 0; iRow <= upKeyLen; iRow++ )
	{
		nRowPos = orgDataset.SearchRow(strBaseFilter + " && " + strPSidID + " == '" + upKeys[iRow] + "'", nRowNF+1);
		if ( nRowPos >= 0 ) break;
	}
	if (nRowPos < 0) nRowPos = orgDataset.GetCount();
	
	obj.Redraw = false;
	if ( nRowPos > 0 ){
		for(iRow = nRowPos-1; iRow > nRowNF; iRow--)
		{
			orgDataset.SetColumn(iRow, strCheckID, newChecked);
		}
	}
	orgDataset.SetColumn(nRowNF, strCheckID, currentChecked);
	
	var checkCount = 0;
	var tricheckCount = 0;
	var nRowCount = 0;
	for( iRow = 0; iRow < upKeyLen; iRow++ )
	{
		nRowPos = orgDataset.SearchRow( strBaseFilter + " && " + strSidID + " == '" + upKeys[iRow] + "'" );
		nRowCount = orgDataset.CaseCount( strBaseFilter + " && " + strPSidID + " == '" + upKeys[iRow] + "'");
		checkCount = orgDataset.CaseCount( strBaseFilter + " && " + strPSidID + " == '" + upKeys[iRow] + "' && " + strCheckID + " == '1'" );
		tricheckCount = orgDataset.CaseCount( strBaseFilter + " && " + strPSidID + " == '" + upKeys[iRow] + "' && " + strCheckID + " == '0'" );
		if ( nRowPos >= 0 ) orgDataset.SetColumn(nRowPos, strCheckID, iif( checkCount + tricheckCount == 0 , "", iif( checkCount == nRowCount , "1", "0")) );
	}
	filterDS.RecalcFilter(); 
	obj.Redraw = true;
}

/******************************************************
* 그리드 DrillDown Find Row Up Path
* @param  filterDS : DrillDown BindDataset
		, nRow : 상위 경로를 차고자하는 행
		, isGetId : 결과로 SID값을 가져오는지에 대한 여부, FLASE로 지정시 INDEX 집합 리턴
* @return  상위 패스 경로
******************************************************/
function gfn_DrillDownUpPath(filterDS, nRowNF, isGetId)
{
	isGetId = iif(isGetId == false, false, true);
	var orgDataset = Object(filterDS.DataObject); 
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strSidID = GetVariable(filterDS.ID+"_SID", "Local");
	var strPSidID = GetVariable(filterDS.ID+"_PSID", "Local");
	var strRootPSidValue = GetVariable(filterDS.ID+"_PSIDVALUE", "Local");
	
	var rowSID = orgDataset.GetColumn(nRowNF, strSidID);
	var rowPSID = orgDataset.GetColumn(nRowNF, strPSidID);
	//var nRowNF = filterDS.GetRowIndexNF(nRow);
	var iRow = 0; 
	var upKeys = Array();
	
	for( iRow = nRowNF -1; iRow >= 0; iRow--)
	{
		iRow = orgDataset.SearchRow(strBaseFilter + " && " + strSidID + " == '" + rowPSID + "'", 0, iRow);
		if (iRow < 0) break;
		rowPSID = orgDataset.GetColumn(iRow, strPSidID); 
		rowSID = orgDataset.GetColumn(iRow, strSidID);
		upKeys[ length(upKeys) ] = iif(isGetId, rowSID, iRow);
	}
	return upKeys;
}

/******************************************************
* 그리드 Checked Box 3 Status
* @param  filterDS : DrillDown BindDataset
		, nRow : 상위 경로를 차고자하는 행
		, isGetId : 결과로 SID값을 가져오는지에 대한 여부, FLASE로 지정시 INDEX 집합 리턴
* @return  Checkbox Image
******************************************************/
function gfn_CheckBoxTriStatus(CheckBoxValue)
{
	return decode( trim( CheckBoxValue ) , "1", "icon-checkbox-checked", "0" , "icon-checkbox-tristatus", "icon-checkbox-unchecked");
}

/******************************************************
* 그리드 DrillDown Checked Box 3 Status
* @param  filterDS : DrillDown Grid
* @return  Checkbox Image
******************************************************/
function gfn_CheckBoxGridHeaderTriStatus( filterGrid )
{
	var filterDS = Object(filterGrid.BindDataset);
	var orgDataset = Object(filterDS.DataObject); 
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strCheckID = GetVariable(filterDS.ID+"_CHECK", "Local");
	var nRowCount = orgDataset.CaseCount( strBaseFilter );
	var checkedCount = orgDataset.CaseCount( strBaseFilter + " && " + strCheckID + " == '1' " );
	return decode( checkedCount , 0, "icon-checkbox-unchecked" ,  nRowCount, "icon-checkbox-checked" , "icon-checkbox-tristatus");
}

/******************************************************
* 그리드 DrillDown Sum
* @param  filterDS : 필터 Dataset
		, nRow : 선택행
		, strCmpExpr : 기본 필터
		, strSumField : 합계열
* @return  없음
******************************************************/
function gfn_DrillDownSum(filterDS, nRow, strCmpExpr, strSumField)
{
	var orgDataset = Object(filterDS.DataObject); 
	var strBaseFilter = GetVariable(filterDS.ID+"_FILTER", "Local");
	var strSidID = GetVariable(filterDS.ID+"_SID", "Local");
	var strPSidID = GetVariable(filterDS.ID+"_PSID", "Local");
	var strRootPSidValue = GetVariable(filterDS.ID+"_PSIDVALUE", "Local");
	var nRowNF = filterDS.GetRowIndexNF(nRow);
	var iRow = 0; 
	var nRowPos = -1; 
	var strSid = "";
	
	if (trim( strCmpExpr ) == "") strCmpExpr = "true"; 
	
	var upKeys = gfn_DrillDownUpPath(filterDS, nRowNF);
	var upKeyLen = length( upKeys );
	upKeys[ upKeyLen ] = strRootPSidValue;
	nRowPos = nRowNF;
	
	//orgDataset.FireEvent = false;
	strSid = orgDataset.GetColumn( nRowPos , strSidID );
	orgDataset.SetColumn( nRowPos , strSumField , orgDataset.CaseSum( strPSidID + "=='" + strSid + "' ", strSumField ) );
	for( iRow = 0; iRow <= upKeyLen; iRow++ )
	{
		nRowPos = orgDataset.SearchRow( strBaseFilter + " && " + strSidID + " == '" + upKeys[iRow] + "'");
		if ( nRowPos < 0 ) break; 
		orgDataset.SetColumn( nRowPos , strSumField , orgDataset.CaseSumNF( strPSidID + "=='" + upKeys[iRow] + "' ",  strSumField ) );
	}
	//orgDataset.FireEvent = true;
}

/******************************************************
* 그리드 DrillDown 설정 , Logical DataSet에 원본 Dataset을 설정해야한다.
* @param  baseDS : 원본 DataSet
		, filterGrid : DrillDown으로 표현된 그리드
		, strBaseFilter : 기본적으로 적용되어야 하는 필터
		, strActionID : 접거나 펼치기가 적용될 필드(값은 0 or 1)
		, strLevelID : 하위 단계를 표현하는 필드드
		, strSidID : 데이터에서 유일값을 구분하는 키 필드
		, strPSidID : 데이터에서 상위 유일값을 구분하는 필드 
		, strLabelID : 데이터에서 각 항목의 단위를 표현하는 필드
		, strActionValue : 펼쳐진형태로 표현될 ACTION필드의 값 (기본값 : 1)
		, strRootPSidValue : ROOT 행으로 인식할 PSID의 값 (기본값 : "")
* @return  없음
******************************************************/
function gfn_InitDrillDown(baseDS, filterGrid, strBaseFilter, strActionID, strLevelID, strSidID, strPSidID, strLabelID, strCheckID, strActionValue, strRootPSidValue)
{
	var filterDS = Object(filterGrid.BindDataset);
	filterDS.DataObject = baseDS.ID;
	if (trim(strActionValue) == "") strActionValue = "1";
	AddVariable(filterDS.ID+"_FILTER", strBaseFilter, "Local");
	AddVariable(filterDS.ID+"_ACTION", strActionID, "Local");
	AddVariable(filterDS.ID+"_LEVEL", strLevelID, "Local");
	AddVariable(filterDS.ID+"_SID", strSidID, "Local");
	AddVariable(filterDS.ID+"_PSID", strPSidID, "Local");
	AddVariable(filterDS.ID+"_LABEL", strLabelID, "Local");
	AddVariable(filterDS.ID+"_CHECK", strCheckID, "Local");
	AddVariable(filterDS.ID+"_ACTVALUE", strActionValue, "Local");
	AddVariable(filterDS.ID+"_PSIDVALUE", trim(strRootPSidValue+""), "Local");
	filterDS.FilterExpr = "gfn_DrillDownFilter("+filterDS.ID+", currow)";
	
	var strActionIDIndex = filterGrid.GetBindCellIndex("Body", strActionID);
	var strLabelIDIndex = filterGrid.GetBindCellIndex("Body", strLabelID);
	if (strCheckID != "" ){
		var strCheckIDIndex = filterGrid.GetBindCellIndex("Body", strCheckID);
		filterGrid.SetCellProp("Head", strCheckIDIndex, "Display", "Image");
		filterGrid.SetCellProp("Head", strCheckIDIndex, "Cursor", "HAND");
		filterGrid.SetCellProp("Head", strCheckIDIndex, "Expr", "gfn_CheckBoxGridHeaderTriStatus( "+filterGrid.ID+" )");
		filterGrid.SetCellProp("Body", strCheckIDIndex, "Display", "Image");
		filterGrid.SetCellProp("body", strCheckIDIndex, "Cursor", "HAND");
		filterGrid.SetCellProp("Body", strCheckIDIndex, "Expr", "gfn_CheckBoxTriStatus("+strCheckID+")");
	}
	filterGrid.SetCellProp("Body", strActionIDIndex, "Display", "Image");
	filterGrid.SetCellProp("Body", strActionIDIndex, "Cursor", "HAND");
	filterGrid.SetCellProp("Body", strActionIDIndex, "Expr", "gfn_DrillDownFoldingImage("+filterDS.ID+", currow)");
	if ( strLabelID != "" && strLevelID != "" ){
		filterGrid.SetCellProp("Body", strLabelIDIndex, "Expr", "Lpad('','　',   ( ToNumber("+strLevelID+") - 1 ) ) + "+strLabelID);
	}
	filterGrid.Redraw = false;
	baseDS.FireEvent = false;
	if (strCheckID != "" ){
		var rowCountNF = baseDS.GetCount();
		for(var iRow = 0; iRow < rowCountNF; iRow++){
			baseDS.SetColumn(iRow, strCheckID, "");
		}
	}
	baseDS.FireEvent = true;
	filterDS.RecalcFilter();
	filterGrid.Redraw = true;
}

/******************************************************
* 물류서비스 공통 코드 콤보 데이터 셑 정보 조회
* @param  CODE_GROP:DatsetD Array
* @return  combo dataset
******************************************************/
function gn_fsComboDataSet(codeArray) 
{
	gn_sapComboDataSet(codeArray);
}

/******************************************************
* 물류서비스 공통 코드 콤보 데이터 셑 정보 조회
* @param  CODE_GROP:DatsetD:LANG Array
* @return  combo dataset
******************************************************/
function gn_fsCombQuery()
{
    tit_clearActionInfo();
    tit_addSearchActionInfo("fs01:FS_COM0101_S2");
    tit_callService(
                    ""
                   ,""
                   ,"ds_cond=DS_FS_COMMON_CODE"
                   ,"gds_code=ds_fsCode"
                   ,""
                   ,"gn_afterFsCombQuery"
                   ,"true"
                   ,"true"
                   ,"true"
                   );
}

/******************************************************
* 물류서비스 공통 코드 콤보 데이터 셑 정보 조회
* @param  CODE_GROP:DatsetID[;연결여부][:SORT[:조건절(GRUP,CDUP,EXTN1,EXTN2,EXTN3,EXTN4,EXTN5를 키로가지고 공백으로분리된 문자열)[:LANG]]]Array
* @return  combo dataset
******************************************************/
function gn_sapComboDataSet(codeArray)
{
	//if ( type(object("DS_FS_COMMON_CODE")) != "object" )
	if ( find("DS_FS_COMMON_CODE") == null )
	{
		Create("Dataset","DS_FS_COMMON_CODE",'DataSetType="Dataset"'); 
		DS_FS_COMMON_CODE.AddColumn("S_CODE", "STRING", 256);
		DS_FS_COMMON_CODE.AddColumn("ODER", "STRING", 256);
		DS_FS_COMMON_CODE.AddColumn("SORT", "STRING", 256);
		DS_FS_COMMON_CODE.AddColumn("LANG", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("GRUP", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("CDUP", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("EXTN1", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("EXTN2", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("EXTN3", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("EXTN4", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("EXTN5", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("NEXTN1", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("NEXTN2", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("NEXTN3", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("NEXTN4", "STRING", 256); 
		DS_FS_COMMON_CODE.AddColumn("NEXTN5", "STRING", 256); 
	}
	var oDataSet = object("DS_FS_COMMON_CODE");
	var queryId = "";
	var len = length( codeArray );
	var i = 0;
	var iRow = -1;
	var aData = array();
	var aDataLen = 0;
	var awData = array();
	var awDataLen = 0;
	var wData = array(2);
	var j = 0;
	for( i=0; i < len; i++ )
	{
		queryId = "";
		aData = split(codeArray[i], ":", true);
		aDataLen = length( aData );
		oDataSet.ClearData();
		iRow = oDataSet.AppendRow();
        oDataSet.SetColumn(iRow, "LANG", '3');
        oDataSet.SetColumn(iRow, "SORT", "");
		oDataSet.SetColumn(iRow, "S_CODE", aData[0]);
		oDataSet.SetColumn(iRow, "GRUP", ""); 
		oDataSet.SetColumn(iRow, "CDUP", ""); 
		oDataSet.SetColumn(iRow, "EXTN1", ""); 
		oDataSet.SetColumn(iRow, "EXTN2", ""); 
		oDataSet.SetColumn(iRow, "EXTN3", ""); 
		oDataSet.SetColumn(iRow, "EXTN4", ""); 
		oDataSet.SetColumn(iRow, "EXTN5", ""); 
		oDataSet.SetColumn(iRow, "NEXTN1", ""); 
		oDataSet.SetColumn(iRow, "NEXTN2", ""); 
		oDataSet.SetColumn(iRow, "NEXTN3", ""); 
		oDataSet.SetColumn(iRow, "NEXTN4", ""); 
		oDataSet.SetColumn(iRow, "NEXTN5", ""); 
		if ( aDataLen > 1 ) oDataSet.SetColumn(iRow, "ODER", aData[1]);
		if ( aDataLen > 2 && length( aData[2] ) > 0 ) oDataSet.SetColumn(iRow, "SORT", aData[2]);
		if ( aDataLen > 3 && length( aData[3] ) > 0 ) 
		{
			if ( indexOf( aData[3] , "=" ) > 0 )
			{
				awData = split( aData[3] , ";", true, false );
				awDataLen = length( awData );
				for( j = 0; j < awDataLen; j++ )
				{
					wData[0] = ToUpper( trim( left( awData[j] , indexOf( awData[j], "=") ) ) );
					wData[1] = UnQuote( trim( mid( awData[j] , indexOf( awData[j], "=")+1 ) ) );
				 	if ( oDataSet.GetColIndex(wData[0]) >= 0 ) oDataSet.SetColumn(iRow, wData[0], wData[1] );
				}
			}
			else
			{
				oDataSet.SetColumn(iRow, "LANG", aData[4]);
			}
		}
		if ( aDataLen > 4 && length( aData[4] ) > 0 ) oDataSet.SetColumn(iRow, "LANG", aData[4]);
        switch( aData[0] ) {
            // SAP 오더유형
		    case "S01":
            // SAP 유통경로
		    case "S02":
            // SAP 제품군
		    case "S03":
            // SAP 통화
		    case "S04":
            // SAP 가격결정
		    case "S05":
            // SAP 인도조건
		    case "S06":
            // SAP 지급조건(PayT)
		    case "S07":
            // SAP 지급보증절차
		    case "S08":
            // SAP 플랜트(Pint)
		    case "S09":
            // SAP ItCa(품목범주)
		    case "S09":
            // SAP ICnTy
		    case "S11":
            // SAP 청구계획유형
		    case "S12":
            // SAP 일자내역
		    case "S13":
            // SAP 종별구분
		    case "S14":
            // SAP 대금청구규칙
		    case "S15":
            // SAP 빌링상태
		    case "S16":
            // SAP 파트너역활
		    case "S17":
            // SAP 단위
		    case "S19":
			    queryId = "";
			    break;
            // SAP 국가
		    case "S18":
			    queryId = "FS_COM0103_S18";
			    break;
	    }
	    if (queryId == "") 
	    {
			gn_fsCombQuery();
		}
		else
		{
			gn_fsSapCombQuery(queryId);
		}
	}
}

/******************************************************
* 물류서비스 공통 코드 콤보 데이터 셑 정보 조회
* @param  CODE_GROP:DatsetD:LANG Array
* @return  combo dataset
******************************************************/
function gn_fsSapCombQuery(queryId)
{
    tit_clearActionInfo();
    tit_addSearchActionInfo("fs01:"+queryId);
    tit_callService(
                    ""
                   ,""
                   ,"ds_cond=DS_FS_COMMON_CODE"
                   ,"gds_code=ds_sap_code"
                   ,""
                   ,"gn_afterFsCombQuery"
                   ,"true"
                   ,"true"
                   ,"true"
                   );
}

/******************************************************
* 물류서비스 공통 코드 콤보 데이터 셑 정보 조회 callBack 함수
******************************************************/
function gn_afterFsCombQuery(errCode, errMsg) 
{
    if( errCode != 0 ) {
        gfn_openMsgWin("공용코드 combo dataset 조회 오류:"+errMsg, "", "E", "");
    } else {
        var oDataSet;
        var i=0;
        var iRow = -1;
        var nRowCount = gds_code.GetRowCount();
        var strDataSetId = "";
        var isDataSetAdd = false;
        var nCodeFirstRow = false;
        var aParseDataSet = Array();
        if( nRowCount > 0 ) {
        	aParseDataSet = split(gds_code.GetColumn(0, "DATASET_ID"), ";");
    		strDataSetId = aParseDataSet[0];
        	if ( length( aParseDataSet ) > 1 )
    		{
        		isDataSetAdd = iif( aParseDataSet[1] , true, false );
    		}
            oDataSet = Object( strDataSetId );
            if ( type( oDataSet ) == "object" ) 
            {
				oDataSet.FireEvent = false;
				nCodeFirstRow = oDataSet.FindRow("CODE", "");
				if ( !isDataSetAdd || nCodeFirstRow < 0 ) 
				{
					oDataSet.ClearData();
					oDataSet.Copy(gds_code);
				}
				else
				{
					oDataSet.AppendDataset(gds_code);
				}
				if ( nCodeFirstRow < 0 )
				{
					oDataSet.InsertRow(0);
					oDataSet.SetColumn(0, "CODE", "");
					oDataSet.SetColumn(0, "CODE_NAME", gfn_getCodeName(G_CODE_SELECT));
				}
				oDataSet.FireEvent = true;
			}
        }
    }
}


/****************************************************************
* 그리드 정렬
* @param gridObj 그리드 객체
* @param nCell 정렬이 필요한 Cell 번호
* @return Sort 된 Flag
          CONST_ASC_MARK 또는 CONST_DESC_MARK
***************************************************************/
function gfn_sortGridFS(gridObj,nCell) 
{
	var flag;
	var nheadText;
	var oDataSet = object(gridObj.bindDataset);
	var dsObj = null;
	var isFilter = ( oDataSet.DatasetType == "Filter" );
	if ( isFilter ) 
	{
		dsObj = Object(oDataSet.DataObject);
	}
	else
	{
		dsObj = oDataSet;
	}
	// 데이터셋의 rowcount가 1보다 작을 경우
	if ( dsObj.rowCount() < 2 ) 
	{
		return;
	}
	
	dsObj.FireEvent = false;
	if ( nCell < 0 )
	{
		dsObj.sort();
	}
	else
	{
		var HeadCol = gridObj.GetCellProp("Head",nCell,"col");
		//  해당하는 셀의 타이틀의 소트마크를 추가 또는 변경한다.
		if(right(gridObj.GetCellProp("head",nCell,"text"),1) == CONST_ASC_MARK) 
		{
			flag = CONST_DESC_MARK;
			dsObj.sort(gridObj.GetCellProp("Body",HeadCol,"colid"),false);
			nheadText = gridObj.GetCellProp("head",nCell,"text");
			nheadText = replace(nheadText,CONST_ASC_MARK,"");
			nheadText = replace(nheadText,CONST_DESC_MARK,"");
			nheadText = nheadText + CONST_DESC_MARK;
		} 
		else 
		{
			flag = CONST_ASC_MARK;
			dsObj.sort(gridObj.GetCellProp("Body",HeadCol,"colid"),true);
			nheadText = gridObj.GetCellProp("head",nCell,"text");
			nheadText = replace(nheadText,CONST_ASC_MARK,"");
			nheadText = replace(nheadText,CONST_DESC_MARK,"");
			nheadText = nheadText + CONST_ASC_MARK;
		}
		gridObj.SetCellProp("head",nCell,"text",nheadText);
	}
	if ( isFilter ) oDataSet.RecalcFilter();
	gridObj.Redraw("body");
	dsObj.FireEvent = true;
	
	gfn_clearGridSortMarkFS( gridObj, nCell );
	return flag;
}
/****************************************************************
* Grid의 sort Mark 삭제
* @param gridObj 그리드 객체
* @param skipCell 제외해야 하는 Cell : 없을 경우 null 또는 -1
* @return 없음
***************************************************************/
function gfn_clearGridSortMarkFS(gridObj, skipCell) 
{
	var nColCnt = gridObj.GetCellCount("head");
	var sRepText="";
	for(i=0;i<nColCnt;i++) 
	{
		if(skipCell <> i) 
		{
			sRepText = replace(gridObj.GetCellProp("head",i,"text"), CONST_ASC_MARK,"");
			sRepText = replace(sRepText, CONST_DESC_MARK,"");
			gridObj.SetCellProp("head",i,"text", sRepText);
		}
	}
}
/****************************************************************
* 그리드 정렬
* @param gridObj 그리드 객체
* @param isPop   팝업창 여부
* @param strCol  정렬대상 컬럼의 시작위치
***************************************************************/
function gfn_multiSortGridFS(gridObj, isPop, strCol) 
{
	var grdNm = "";
	var str_arg, str_result;   

	if ( type( gridObj ) == "STRING") 
	{
		grdNm = gridObj;
	} 
	else 
	{
		grdNm = gridObj.id;
	}
	
	// 데이터셋의 rowcount가 1보다 작을 경우
	var oDataSet = object(object(grdNm).bindDataset);
	var dsObj = null;
	var isFilter = ( oDataSet.DatasetType == "Filter" );
	if ( isFilter ) 
	{
		dsObj = Object(oDataSet.DataObject);
	}
	else
	{
		dsObj = oDataSet;
	}
	
	if ( dsObj.rowCount() < 2 ) 
	{
		return;
	}

	// 파라메터 설정
	str_arg = "arg_grid='" + grdNm + "' strDivFlag=" + isPop + " startCol=" + strCol;
	
	str_result = gfn_openPopupUrl("WB01::CommMultiSortGrid.xml", true, str_arg);

	// 결과 처리
	if ( (str_result <> null)  && (str_result <> "") )
	{
		dsObj.FireEvent = false;
		dsObj.Sort(str_result);
		if ( isFilter ) oDataSet.RecalcFilter();
		dsObj.FireEvent = true;
	}	
}
/********************************************************************************
* 컬럼에 데이터를 일괄 설정한다.
* @param oDataset 일괄 설정할 Grid
* @param aSetFields 일괄설정할 정보 "필드=값"형식의 배열
* @param strCmpExpr 특정 조건을 기준으로 설정할때 행을 찾기 위한 비교식
* @param callbackSql 처리가 완료된 후 결과로써 반환할 Sql Id 
* @return 없음
********************************************************************************/
function gfn_setColumnData(oGrid, aSetFields, strCmpExpr)
{
	strCmpExpr = trim( strCmpExpr + "" );
	var oDataset = object( oGrid.BindDataset );
	var iRow = 0;
	var aIdx = 0;
	var aLen = length( aSetFields );
	var aData = array(2);
	var nRowCount = oDataset.GetRowCount();
	if (length( strCmpExpr ) > 0) iRow = oDataset.SearchRow(strCmpExpr, 0);
	oGrid.Redraw = false;
	var isCheckEvent = oDataset.FireEvent;
	if(isCheckEvent) oDataset.FireEvent = false;
	while( iRow >= 0 && iRow < nRowCount )
	{
		for( aIdx = 0; aIdx < aLen; aIdx++ )
		{
			aData = split( aSetFields[ aIdx ] , "=" );
			if ( oDataset.GetColIndex( aData[0] ) >= 0 )
			{
				oDataset.SetColumn( iRow , aData[0] , UnQuote( trim( aData[1] + "" ) ) );
			}
		}
		if (length( strCmpExpr ) > 0) iRow = oDataset.SearchRow(strCmpExpr, iRow+1);
		else iRow++;
	}
	if(isCheckEvent) oDataset.FireEvent = true;
	oGrid.Redraw = true;
}


/**************************************************************************************
*  숫자를 입력받아"00001" 형태의 Numeric char 작성
* 
* @param  orgNum  처리할 숫자
* @param  Mlen  리턴받을 전체 자리수
**************************************************************************************/
function gfn_fsFillZero(orgNum, Mlen) {
	if (Mlen == null) {
		Mlen = 0;
		return orgNum;
	}
	
	var toNum = "";
	var len = length(""+orgNum);
	var size = Mlen - len;
	if (Mlen <= 0){
		return orgNum;
	}
	
	for (var i = 0; i < size; i++) {
		toNum = "0" + toNum;
	}
	
	return toNum+orgNum;
}
