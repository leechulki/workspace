﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 공통 Script
 *  작성일자 : 2008/03/03
 *  버전 : 1.0
 *  변경 이력
    일자            작성자          비고
    ------------------------------------------------------------
    2008/03/03                      최초 작성
================================================================================*/
/*===============================================================================
*   상수 선언
================================================================================*/
var G_CODE_ALL = 1;			// "전체" 라는 글자로 생성
var G_CODE_BLANK = 2;		// 공백으로 첫 줄 생성 , null일 경우 기본
var G_CODE_NONE = 3;		// 생성하지 않음.
var G_CODE_SELECT = 4;		// "선택" 라는 글자로 생성
var G_CS_BIZ = "CS_BIZ_XA";	// CS용 비지니스 
var G_HR_BIZ = "HR_BIZ_XA";	// HR용 비지니스 
var G_PRD_BIZ = "PRD_ACT";
var G_XA_ACT = "XA_ACT";	// XA용으로 DB를 2개 이상 사용할 경우 
var _tmp_dsObj = null;
/****************************************************************
* 공통 코드 조회하기
* @param codeArr  배열로 조회할 공통 그룹 코드 명
* @param dsObjArr	조회한 그룹코드들을 담을 Dataset 객체 아이디
* @param addTypeArr 콤보 등 전체 조건 필요시 ( 옵션 )
* @param  isSync 동기식을 사용해야 할 경우 처리 ( 옵션 ) - true / false
* @return  비동기식으로 처리 완료후 fn_completeInit () 함수를 호출하게 되어 있음.
***************************************************************/
var _tmp_codeArr = null;
var _tmp_dsObjArr = null;
var _tmp_addTypeArr = null;
function gfn_searchCommCode(codeArr, dsObjArr, addTypeArr, isSync) 
{
	_tmp_codeArr = codeArr;
	_tmp_dsObjArr = dsObjArr;
	_tmp_addTypeArr = addTypeArr;

	// var sLang = "KOR";
	// switch( G_LANG ) {
		// case "zh":
			// sLang = "CHN";
			// break;
		// case "en":
			// sLang = "ENG";
			// break;
	// }
    var sLang = G_LANG;
    
	gfn_initDs(gds_code_cond, true );
	gds_code_cond.setColumn(0, "LANG", sLang);
	gds_code_cond.setColumn(0, "S_CODE", codeArr[0]);

	for( var i = 1; i < length( codeArr ); i++ ) 
	{
		gds_code_cond.addRow();
		gds_code_cond.setColumn(i, "S_CODE", codeArr[i]);
	}
	// 정보 초기화
	tit_clearActionInfo();
	// 조회해야 하는 sql
	tit_addSearchActionInfo("wb01:ZWBTZ901_S1");
	// Transaction 처리
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_code_cond"
        , "gds_code=ds_list"
        , ""
        , "_fn_afterCodeSearch"
        , false
        , true
        , isSync);
}
/****************************************************************
* 공통 코드 조회하기
* @param codeArr  배열로 조회할 공통 그룹 코드 명
* @param dsObjArr	조회한 그룹코드들을 담을 Dataset 객체 아이디
* @param addTypeArr 콤보 등 전체 조건 필요시 ( 옵션 )
* @param orderTypeArr 코드 저건 처리시 오더 조건 입력 가능
* @param  isSync 동기식을 사용해야 할 경우 처리 ( 옵션 ) - true / false
* @return  비동기식으로 처리 완료후 fn_completeInit () 함수를 호출하게 되어 있음.
***************************************************************/
var _tmp_codeArr = null;
var _tmp_dsObjArr = null;
var _tmp_addTypeArr = null;
function gfn_searchCommCode2(codeArr, dsObjArr, addTypeArr, orderTypeArr, isSync) 
{
	_tmp_codeArr = codeArr;
	_tmp_dsObjArr = dsObjArr;
	_tmp_addTypeArr = addTypeArr;

	// var sLang = "KOR";
	// switch( G_LANG ) {
		// case "zh":
			// sLang = "CHN";
			// break;
		// case "en":
			// sLang = "ENG";
			// break;
	// }
    var sLang = G_LANG;
	gfn_initDs(gds_code_cond, true );
	gds_code_cond.setColumn(0, "LANG", sLang);
	gds_code_cond.setColumn(0, "S_CODE", codeArr[0]);

	var oder = null;
	if (orderTypeArr != null) {
		for( var i = 0; i < length( orderTypeArr ); i++ ) 
		{
			if (i == 0)
			{
				oder = orderTypeArr[i];
			}
			else {
				oder+= ","+orderTypeArr[i];
			}
		}
		gds_code_cond.setColumn(0, "ODER", oder);
	}

	for( var i = 1; i < length( codeArr ); i++ ) 
	{
		gds_code_cond.addRow();
		gds_code_cond.setColumn(i, "S_CODE", codeArr[i]);
	}
	// 정보 초기화
	tit_clearActionInfo();
	// 조회해야 하는 sql
	tit_addSearchActionInfo("wb01:ZWBTZ901_S1");
	// Transaction 처리
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_code_cond"
        , "gds_code=ds_list"
        , ""
        , "_fn_afterCodeSearch"
        , false
        , true
        , isSync);
}
/****************************************************************
* 공통 코드 조회 Callback
* 개발자 사용 API 아님.
***************************************************************/
function _fn_afterCodeSearch(errCode, errMsg) 
{
	for( var i = 0; i < length( _tmp_codeArr ); i++) 
	{
		if( _tmp_dsObjArr[i] != null ) 
		{
			gfn_copyDs(
				 gds_code
				, _tmp_dsObjArr[i]
				, " GROUP_CODE == '" + _tmp_codeArr[i] + "'");

			if ( _tmp_addTypeArr[i] !=  G_CODE_NONE ) 
			{
				_tmp_dsObjArr[i].insertRow(0);
				_tmp_dsObjArr[i].setColumn(0, "CODE", "");
				_tmp_dsObjArr[i].setColumn(0, "CODE_NAME", gfn_getCodeName(_tmp_addTypeArr[i]));
			} 
		}
	}

	// 처리 완료후 화면 호출
	fn_completeInit(_tmp_codeArr, _tmp_dsObjArr, _tmp_addTypeArr);
}
/****************************************************************
*  콤보 등에 첫줄에 전체, "" 등 기본 조건 추가
* @param dsObj 추가할 데이터셋 객체
* @param nType 추가할 데이터 타입
* @param codeColNm 코드에 해당하는 데이터셋 컬럼 명 ( 기본 : CODE )
* @param  nmColNm 코드명에 해당하는 데이터셋 컬럼 명 ( 기본 : CODE_NAME )
***************************************************************/
function gfn_insertEmptyRow(dsObj, nType, codeColNm, nmColNm) 
{
	if ( length( codeColNm ) == 0 ) 
	{
		codeColNm = "CODE";
	}

	if ( length( nmColNm ) == 0 ) 
	{
		nmColNm = "CODE_NAME";
	}

	if ( nType != G_CODE_NONE )
	{
		dsObj.insertRow(0);
		dsObj.setColumn(0, codeColNm, "");
		dsObj.setColumn(0, nmColNm, gfn_getCodeName(nType));
	}
}
/****************************************************************
*  콤보 등에 첫줄에 추가하는 명 
* @param sgubun
***************************************************************/
function gfn_getCodeName(sgubun)
{
	var rtn = "";
	
	if (sgubun == G_CODE_ALL)
	{
		switch( G_LANG ) 
		{
			case "ko" : rtn = "- 전체 -";
						break;
			case "zh" : rtn = "- 全體 -";
						break;
			case "en" : rtn = "- ALL -";
						break;
		}
	}
	else if (sgubun == G_CODE_SELECT)
	{
		switch( G_LANG ) 
		{
			case "ko" : rtn = "- 선택 -";
						break;
			case "zh" : rtn = "- 選擇 -";
						break;
			case "en" : rtn = "SELECT";
				        break;
		}
	}
	
	return rtn;
}
/****************************************************************
 * form 초기 Loading 처리
 * @return 없음
 * 호출 예 : gfn_initForm()
***************************************************************/
function gfn_initForm(bResize) 
{
	// X-Framework 생성
	tit_createActionInfo();

	// Popup 화면 처리 
	var isPopup = true;
	if( isExistVar("F_IS_POPUP")  ) 
	{
		if ( F_IS_POPUP == "N" ) 
		{
			isPopup = false;
		}
	}
	
	if ( isPopup && isExistVar("F_PGM_ID") ) 
	{
		var nRow = gds_auth.findRow("PGM_ID", F_PGM_ID);
		div_btn.fn_loadUseBtn(
			global.FRM_TOP.ufn_getBtnList(F_PGM_ID),
			gds_auth.getColumn(nRow, "PGM_NAME")+"["+F_PGM_ID+"]",
			global.FRM_TOP.ufn_getMyLevel(F_PGM_ID) 
		);  
	}
	
	if (isPopup && bResize)
	{
		// form resize 디자인시의 크기로 설정
		// trace("WorkForm : "+this.width+","+this.height);
		Gfn_ResizeInit(978,562);	
	}
}
/****************************************************************
 * form 초기 Loading 처리 : 권한이 없이 팝업 화면 처리할 경우 
 * @return 없음
 * 호출 예 : gfn_initFormNotAuth()
***************************************************************/
function gfn_initFormNotAuth(pgmId, pgmName, btnList) 
{
	// X-Framework 생성
	tit_createActionInfo();

	div_btn.fn_loadUseBtn(
			btnList
			, pgmName
			, "8");
}
/****************************************************************
* 해당 프로그램 아이디에 대한 사용 가능한 URL 가져오기
* @param pgmId  프로그램 아이디
* @return  없음
***************************************************************/
function gfn_getPgmUrl(pgmId) {
	// Program 에서 프로그램 가져오기
	pgmId = trim(pgmId);
    var nRow = gds_auth.findRow("PGM_ID" , pgmId);
    var sPgmUrl = gds_auth.GetColumn(nRow, "PGM_URL");
	if(nRow == -1 || length(trim(sPgmUrl)) == 0){
		// ZW00005=해당 프로그램을 사용할 권한이 없습니다.
		gfn_showAlert("ZW00003");
		return;
    }	

	// 내 권한이 해당 프로그램 권한보다 작아야 사용 가능
	if ( toNumber(global.FRM_TOP.ufn_getMyLevel(pgmId))
		> toNumber(gds_auth.getColumn(nRow, "PGM_LEVEL") ) ) {
		// 사용 권한이 없음.
		gfn_showAlert("ZW00003");
		return;
	}

	return sPgmUrl;
}
/*===============================================================================
*   메시지 처리
================================================================================*/
/***************************************************************************************
메시지 코드에 해당하는 메시지 가져오기
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
* @param isNotMakeLine \n에 대해서 Enter를 삽입할지 여부 ( 기본 : 삽입한다. )
* @return 메시지
****************************************************************************************/
function gfn_getMsg(msgCode, arr, isNotMakeLine) {
	var row = gds_message.findRow("MSG_ID", msgCode);
	if ( row == -1 ) {
		return null;
	}

	var msg = gds_message.getColumn(row, "MSG_DESC");

    // \n 처리
    if ( isNotMakeLine ) {
		msg = replace(msg,"\\n","");
    } else {
		msg = replace(msg,"\\n",chr(10));
	}

	if ( arr == null || arr.length == 0 || length( msg) == 0 ) {
		return msg;
	}

	//  ${} 포함 문자열 대체
	var sPos = Array();
	var oMsg = msg;
	var cnt = 0;
	var p = -1;
	var rMsg = "";
	while(true) {
		p = indexOf(oMsg, "${}");
		if ( p == -1 ) {
			break;
		}
		rMsg = rMsg + substr(oMsg, 0, p );
		if ( type(arr) == "ARRAY") {
			rMsg = rMsg + replace(arr[cnt], chr(10), " ");
		} else {
			rMsg = rMsg + replace(arr, chr(10), " ");
		}
		oMsg = substr(oMsg, p+3);
		cnt++;
	}

	rMsg = rMsg + oMsg;
	return rMsg;
}
/****************************************************************
* Alert 처리
* @param  msg - 메시지 String
***************************************************************/
function gfn_alert( msg ) 
{
	alert( msg );
}
/****************************************************************
* confirm 처리
* @param  msg - 메시지 String
* @return 확인 - true / 취소 - false
***************************************************************/
function gfn_confirm( msg ) 
{
	return confirm( msg );
}
/****************************************************************
* 메시지 코드를 받아서 Alert 처리
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
***************************************************************/
function gfn_showAlert( msgCode, arr) 
{
	var msg = gfn_getMsg(msgCode, arr);
	var msgType = "W";
	if ( msg == null ) 
	{
		msg = msgCode;
		msgType = charAt( msgCode, 1 );
	}

	gfn_openMsgWin(msg, msgType, "A", msgCode );
}
/****************************************************************
* 메시지 코드를 받아서 Confirm 처리
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
* @return 확인 - true / 취소 - false
***************************************************************/
function gfn_showConfirm( msgCode, arr) 
{
	var msg = gfn_getMsg(msgCode, arr);
	if ( msg == null ) 
	{
		msg = msgCode;
	}

	return gfn_openMsgWin(msg, "I", "C", msgCode );
}
/****************************************************************
* 메시지 코드를 받아서 Inform 처리
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
* @return 확인 - true / 취소 - false
***************************************************************/
function gfn_showInform( msgCode, arr) 
{
	var msg = gfn_getMsg(msgCode, arr);
	if ( msg == null ) 
	{
		msg = msgCode;
	}

	return gfn_openMsgWin(msg, "I", "I", msgCode );
}
/****************************************************************
* 메시지 코드를 받아서 Message Bar 영역에 메시지 표시
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
***************************************************************/
function gfn_showMsg( msgCode, arr ) 
{
	var txtColor = "#E8F3ED";
	var msg = gfn_getMsg(msgCode, arr, true);
	if ( msg == null ) 
	{
		msg = msgCode;
	} 
	else 
	{
		var iconName = "bottom_icon_info";
		switch( charAt(msgCode, 1) ) {
			case "W":
				txtColor = "yellow";
				iconName = "bottom_icon_caution";
				break;
			case "E":
				txtColor = "red";
				iconName = "bottom_icon_error";
				break;
		}

		global.FRM_BOTTOM.img_msgType.imageId = iconName;
	}

	global.FRM_BOTTOM.st_msg.color = txtColor;
	// Message 영역에 표시
	global.FRM_BOTTOM.st_msg.value = msg;
	// alert 등의 별도의 처리 할 것인지 ???
}

/****************************************************************
* 메시지창 띄우기
* @param sMsg 메시지 내용
* @param msgType 메시지 타입
* @param showType A - Alert , C - Confirm
* @param msgCode 메시지 코드
***************************************************************/
function gfn_openMsgWin(sMsg, msgType, showType, msgCode ) 
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

	return Dialog("WB01::CommMsgForm.xml", arg, -1, -1, "titlebar=false statusBar=false");
}
/****************************************************************
* 메시지바 초기화 하기
***************************************************************/
function gfn_clearMsgBar() 
{
	global.FRM_BOTTOM.ufn_clearMsg();
}
/*===============================================================================
*   공통 이벤트 처리
================================================================================*/
/****************************************************************
* Form에서 Enter Key 입력시 처리
* @param
***************************************************************/
function gfn_OnKeyDown_form(obj,objSenderObj,nChar,bShift,bControl,bAlt,nLLParam,nHLParam) 
{
	if ( nChar == 13 )	// enter 키 처리
	{
		// Textarea 무시
		if ( ToUpperCase(objSenderObj.GetType()) == "TEXTAREA" ) 
		{
			return;
		}

	    var tmpObj = null;
		if ( ToUpperCase(obj.GetNextComponent(true).GetType()) == "TAB" ) 
		{
			// 컴퍼넌트가 탭인 경우 포커스를 주면 탭버튼에 들어가므로
			// 해당 탭의 탭페이지로 따라 들어가줘야 함...
			tmpObj = obj.GetNextComponent(true);
			tmpObj = tmpObj.GetItem(tmpObj.TabIndex);
			obj = tmpObj;
			obj.setFocus();
		} 
		else if (ToUpperCase(objSenderObj.GetType()) == "TAB" ) 
		{
			tmpObj = objSenderObj.GetItem(objSenderObj.TabIndex);
			tmpObj.setFocus();
		} 
		else if ( ( ToUpperCase(objSenderObj.GetType()) == "GRID" ) && ( objSenderObj.Editable ) ) 
		{
			var ret = objSenderObj.MoveToNextCell();
			if ( !ret ) 
			{
				obj.GetNextComponent(true).setFocus();
			}
		} 
		else 
		{
			obj.GetNextComponent(true).setFocus();
		}
	}
}
/****************************************************************
* Opeen Form에서 Enter Key 처리
* @param
***************************************************************/
function gfn_EnterInForm(obj,objSenderObj,nChar,bShift,bControl,bAlt,nLLParam,nHLParam)
{
	if (nChar == "13")
	{
	
        //=========반복적인 enter 눌림 방지를 위하여 적용=========
        if (objSenderObj = object("div_btn.btn_query"))
        {
			return;
        }
        //========================================================	
	

		var objObject = object("div_btn.btn_query"); 
		
		if (IsValid (objObject) && objObject.Visible == true)
		{
			objObject.SetFocus();
			eval("fn_query()");
		}

	}
	else if ((objSenderObj.getType() == "Grid") && (bControl) && (nChar == 67))
	{
		// 영역 복사로 수정 2009.05.27 sshong
		// var row = eval(objSenderObj.BindDataset+".row");
		// var str = objSenderObj.GetCellText("body", row, objSenderObj.GetCellPos());

		// ClearClipboard();
		// SetClipBoard("CF_TEXT", str);
		grd_fn_ClipboardCopy(eval(objSenderObj.id), eval(objSenderObj.BindDataset), "	");
	}
}

/*********************************************************************
 * 기능 : 그리드의 선택영역을 Clipboard로 Copy 한다.
 * 인수 : objGrid       : Area Select 된 Grid
         strSeparator  : Colunm 구분자.
 * 예제 : grd_fn_ClipboardCopy(objGrid, ",");
 *********************************************************************/
function grd_fn_CellCopy(objGrid, orgDataset) 
{
	grd_fn_ClipboardCopy(objGrid, orgDataset, "	");
}

function grd_fn_ClipboardCopy(objGrid, orgDataset, strSeparator) 
{
//	var orgDataset = eval(objGrid.BindDataset);

	var strColID;
	var strValue;

	var strClipboard = "";

	var nAreaStartRow;
	var nAreaEndRow;
	var nAreaStartCol;
	var nAreaEndCol;

	if(objGrid.AreaSelect == true) 
	{
		nAreaStartRow = objGrid.GetAreaStartRow();
		nAreaEndRow   = objGrid.GetAreaEndRow();
		nAreaStartCol = objGrid.GetAreaStartCol();
		//nAreaEndCol   = objGrid.GetAreaEndCol(); // grid패치 이전 복사 기능 오류 수정 최신에서 사용가능
		nAreaEndCol   = objGrid.AreaEndCol;
	} 
	else 
	{
		nAreaStartRow = objGrid.CurrentRow;
		nAreaEndRow   = objGrid.CurrentRow;
		nAreaStartCol = objGrid.CurrentCell;
		nAreaEndCol   = objGrid.CurrentCell;
	}

	for(var nRow = nAreaStartRow; nRow <= nAreaEndRow; nRow++) 
	{
		for(var nCell = nAreaStartCol; nCell <= nAreaEndCol; nCell++) 
		{
			if(objGrid.GetColProp(nCell,"Width") > 0) 
			{
				strColID = objGrid.GetCellProp("body",nCell,"colid");
				strValue = orgDataset.GetColumn(nRow,strColID);
				strClipboard = strClipboard + strValue + strSeparator;
			}
		}

		strClipboard = substr(strClipboard,0,length(strClipboard)-1);
		strClipboard = strClipboard + "\n";
	}

	strClipboard = substr(strClipboard,0,length(strClipboard)-1);

	ClearClipboard();
	SetClipBoard("CF_UNICODETEXT", strClipboard);

	return;
}


// tab의 경우 cell위치 가져오기가 다름
function grd_fn_ClipboardCopy_tab(objGrid, orgDataset, strSeparator) 
{
//	var orgDataset = eval(objGrid.BindDataset);

	var strColID;
	var strValue;

	var strClipboard = "";

	var nAreaStartRow;
	var nAreaEndRow;
	var nAreaStartCol;
	var nAreaEndCol;

	if(objGrid.AreaSelect == true) 
	{
		nAreaStartRow = objGrid.GetAreaStartRow();
		nAreaEndRow   = objGrid.GetAreaEndRow();
		nAreaStartCol = objGrid.GetAreaStartCol();
		//nAreaEndCol   = objGrid.GetAreaEndCol(); // grid패치 이전 복사 기능 오류 수정 최신에서 사용가능
		nAreaEndCol   = objGrid.AreaEndCol;
	} 
	else 
	{
		nAreaStartRow = objGrid.CurrentRow;
		nAreaEndRow   = objGrid.CurrentRow;
		nAreaStartCol = objGrid.CurrentCell;
		nAreaEndCol   = objGrid.CurrentCell;
	}

	for(var nRow = nAreaStartRow; nRow <= nAreaEndRow; nRow++) 
	{
		for(var nCell = nAreaStartCol; nCell <= nAreaEndCol; nCell++) 
		{
			if(objGrid.GetColProp(nCell,"Width") > 0) 
			{
				strColID = objGrid.GetCellProp("body",nCell,"colid");
				strValue = orgDataset.GetColumn(nRow,strColID);
				strClipboard = strClipboard + strValue + strSeparator;
			}
		}

		strClipboard = substr(strClipboard,0,length(strClipboard)-1);
		strClipboard = strClipboard + "\n";
	}

	strClipboard = substr(strClipboard,0,length(strClipboard)-1);

	ClearClipboard();
	SetClipBoard("CF_UNICODETEXT", strClipboard);

	return;
}
/****************************************************************
* Form이 Unload 될 때의 처리
* @param
***************************************************************/
function gfn_isFormUnload(formObj) 
{
	// Tab에서 해당 화면 삭제
	global.FRM_TOP.ufc_deleteTab(parent.id);
	return true;
}
/*===============================================================================
*   그리드 Sort 처리
================================================================================*/
// 그리드 소트를 위한 소트 마크
// △▲▼▽↑↓∧∨
var CONST_ASC_MARK="▲";
var CONST_DESC_MARK="▼";
/****************************************************************
* 그리드 정렬
* @param gridObj 그리드 객체
* @param nCell 정렬이 필요한 Cell 번호
* @return Sort 된 Flag
          CONST_ASC_MARK 또는 CONST_DESC_MARK
***************************************************************/
function gfn_sortGrid(gridObj,nCell) 
{
	var nheadText,sflag;
	//  해당하는 셀의 타이틀의 소트마크를 추가 또는 변경한다.
	if(right(gridObj.GetCellProp("head",nCell,"text"),1) == CONST_ASC_MARK) 
	{
		sflag = CONST_DESC_MARK;
	} 
	else 
	{
		sflag = CONST_ASC_MARK;
	}

	gfn_sortGridByflag(gridObj,nCell,sflag);

	return sflag;
}
/****************************************************************
* 오름차순 / 내림 차순 Flag를 입력 받아 그리드 정렬
* @param gridObj 그리드 객체
* @param nCell 정렬이 필요한 Cell 번호
* @param flag 정렬할 Flag  (CONST_ASC_MARK 또는 CONST_DESC_MARK )
* @return  없음
***************************************************************/
function gfn_sortGridByflag(gridObj,nCell,flag) 
{
	var nheadText;
	var dsObj = object(gridObj.bindDataset);
	// 데이터셋의 rowcount가 1보다 작을 경우
	if ( dsObj.rowCount() < 2 ) 
	{
		return;
	}
    var HeadCol = gridObj.GetCellProp("Head",nCell,"col");
	//  해당하는 셀의 타이틀의 소트마크를 추가 또는 변경한다.
	if(flag == CONST_DESC_MARK) 
	{
		dsObj.sort(gridObj.GetCellProp("Body",HeadCol,"colid"),false);
		nheadText = gridObj.GetCellProp("head",nCell,"text");
		nheadText = replace(nheadText,CONST_ASC_MARK,"");
		nheadText = replace(nheadText,CONST_DESC_MARK,"");
		nheadText = nheadText + CONST_DESC_MARK;
	}
	else 
	{
		dsObj.sort(gridObj.GetCellProp("Body",HeadCol,"colid"),true);
		nheadText = gridObj.GetCellProp("head",nCell,"text");
		nheadText = replace(nheadText,CONST_ASC_MARK,"");
		nheadText = replace(nheadText,CONST_DESC_MARK,"");
		nheadText = nheadText + CONST_ASC_MARK;
	}

	gridobj.SetCellProp("head",nCell,"text",nheadText);
	gfn_clearGridSortMark( gridObj, nCell );
}
/****************************************************************
* Grid의 sort Mark 삭제
* @param gridObj 그리드 객체
* @param skipCell 제외해야 하는 Cell : 없을 경우 null 또는 -1
* @return 없음
***************************************************************/
function gfn_clearGridSortMark(gridObj, skipCell) 
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
function gfn_multiSortGrid(grdObj, isPop, strCol) 
{
	var grdNm = "";
	var str_arg, str_result;   

	if ( type( grdObj ) == "STRING") 
	{
		grdNm = grdObj;
	} 
	else 
	{
		grdNm = grdObj.id;
	}
	
	// 데이터셋의 rowcount가 1보다 작을 경우
	var dsObj = object(object(grdNm).bindDataset);

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
		dsObj.Sort(str_result);
	}	
}
/****************************************************************
* Grid 조회 후 검색 메시지 처리
* @param gridObj 그리드 객체
* @return 없음
***************************************************************/
function gfn_showSearchMsg(grdObj, errCode, errMsg) 
{
	if ( errCode == null ) 
	{
		errCode = 0;
	}
	
	var dsObj = object( grdObj.bindDataset);
	if ( errCode == -100 ) 
	{
		if( errMsg == "NO_DATA_FOUND" ) 
		{
			dsObj.clearData();
			gfn_showMsg("CW00010");
			return true;
		}
	} 
	else if ( errCode != 0 ) 
	{
		// 이 경우 처리 실패
		return false;
	}

	if ( dsObj.rowCount() > 0 ) 
	{
		// 조회 성공 메시지 처리
		gfn_showMsg("CI00002", dsObj.rowCount() + "");
	} 
	else 
	{
		// CW00010=데이터가 없습니다.
		gfn_showMsg("CW00010");
	}

	return true;
}
/*===============================================================================
*   입력 데이터 정합성 체크 함수
================================================================================*/
/**************************************************************************************
* 입력된 객체의 값이 Null인지 유무 확인하기
* @param  chkObj  확인할 객체
* @param  isShowMsg 메시지창 전시 여부
* @param  lbTxt 해당 항목 명칭
* @param  해당 객체가 아니라 Focus를 받을 별도의 객체가 존재할 경우 해당 객체
* @return null 인 경우 - true, 그외 false
**************************************************************************************/
function gfn_isNull(chkObj, isShowMsg, lbTxt, focusObj) {
	if( length( chkObj.value) > 0 ) {
		return false;
	}

	if( isShowMsg ) {
		// 메시지 처리
		if( length( lbTxt) == 0 ) {
			// 필수 입력항목 입니다. 확인하여 주십시오.
			gfn_showMsg("CW00002");
		} else {
			// lbTxt + " 항목에 값이 누락되어 있습니다. 확인하여 주십시오."
			gfn_showMsg("CW00001", lbTxt);
		}
	}

	if( focusObj == null ) {
		focusObj = chkObj;
	}

	focusObj.setFocus();
	return true;
}
/**************************************************************************************
* 입력된 객체의 값이 Null인지 유무 확인하기 ALERT 창 POPUP
* @param  chkObj  확인할 객체
* @param  isShowMsg 메시지창 전시 여부
* @param  lbTxt 해당 항목 명칭
* @param  해당 객체가 아니라 Focus를 받을 별도의 객체가 존재할 경우 해당 객체
* @return null 인 경우 - true, 그외 false
**************************************************************************************/
function gfn_isNull_alert(chkObj, isShowMsg, lbTxt, focusObj) {
	if( length( chkObj.value) > 0 ) {
		return false;
	}

	if( isShowMsg ) {
		// 메시지 처리
		if( length( lbTxt) == 0 ) {
			// 필수 입력항목 입니다. 확인하여 주십시오.
			gfn_showAlert("CW00002");
		} else {
			// lbTxt + " 항목에 값이 누락되어 있습니다. 확인하여 주십시오."
			gfn_showAlert("CW00001", lbTxt);
		}
	}

	if( focusObj == null ) {
		focusObj = chkObj;
	}

	focusObj.setFocus();
	return true;
}
/**************************************************************************************
* 값이 존재할 경우 해당하는 자리수로 입력되어 있는지 확인 
* @param  chkObj  확인할 객체
* @param  nLen 자리수 
* @param  isShowMsg 메시지창 전시 여부
* @return null 인 경우, 해당자리수 인 경우 - true, 그외 false
**************************************************************************************/
function gfn_isValidLength(chkObj, nLen, isShowMsg) {
	if ( chkObj.Enable == false
		|| length( chkObj.value ) == 0 ) {
		return true;
	}
	
	if ( length( chkObj.value ) != nLen ) {
		// 입력 가능할 때 10 자리이어야 함. 
		if( isShowMsg ) {
			gfn_showMsg("CW00011", nLen + "");
			chkObj.SetFocus();
		}	
		return false;
	}
	
	return true;
}
/**************************************************************************************
* 그리드의 null 여부 확인하기
* @param  gridObj  확인할 그리드 객체
* @param  dsColNmArr  확인할 Dataset의 컬럼 아이디
* @param  labelArr 표시할 Label 명
* @param  flagColNm  플래그 컬럼 명
* @param  flagValue 플래그 값 중 특정 값만 처리하고자 할 경우
* @return null 인 경우 - true, 그외 false
**************************************************************************************/
function gfn_isGridNull(gridObj, dsColNmArr, labelArr, flagColNm, flagValue) {
	if ( length( flagValue ) == 0 ) {
		flagValue = "I,U";
	}

    var dsObj = object(gridObj.bindDataset);
    for( i = 0; i < dsObj.rowCount(); i++ ) {
		// 플래그 존재시
		if ( length( flagColNm) > 0 ) {
			if( indexOf(flagValue, dsObj.getColumn(i, flagColNm) ) == -1 ) {
				continue;
			}
		}

        // 컬럼 확인
        for ( var c = 0; c < dsColNmArr.length(); c++) {
            if ( dsObj.getColumn(i, dsColNmArr[c]) == null || dsObj.getColumn(i, dsColNmArr[c]) == "" ) {
                var r= gfn_getColIdx( gridObj, dsColNmArr[c] );
                var str = "";
                if ( labelArr == null || length(labelArr[c]) == 0 ) {
                    str = replace(gridObj.getCellProp("head", r, "text"), "*", "");
                } else {
                    str = labelArr[c];
                }

                // 메시지 처리
				// lbTxt + " 항목에 값이 누락되어 있습니다. 확인하여 주십시오."
				gfn_showAlert("CW00001", str);

                dsObj.row = i;
                gridObj.setCellPos(r);
                gridObj.setFocus();

				return true;
            }   // end if
        }   // end for
    }   // end for

    return false;
}
/**************************************************************************************
* 조건 컬럼이 입력되었을 경우 그리드의 null 여부 확인하기
* @param  gridObj  확인할 그리드 객체
* @param  condColumn 조건 확인할 컬럼
* @param  dsArr  확인할 Dataset의 컬럼 아이디
* @param  labelArr 표시할 Label 명
* @param  flagColNm  플래그 컬럼 명
* @param  flagValue 플래그 값 중 특정 값만 처리하고자 할 경우
* @return null 인 경우 - true, 그외 false
**************************************************************************************/
function gfn_isGridNullByCond(gridObj, condColumn, dsArr, labelArr, flagColNm, flagValue) {
	if ( length( flagValue ) == 0 ) {
		flagValue = "I,U";
	}

    var dsObj = object(gridObj.bindDataset);

    var h = gfn_getColIdx( gridObj, condColumn);
    var bodyType = gridObj.getCellProp("body", h, "display");
    var title = replace(gridObj.getCellProp("head", h, "text"), "*", "");

    for( i = 0; i < dsObj.rowCount(); i++ ) {
		// 플래그 존재시
		if ( length( flagColNm) > 0 ) {
			if( indexOf(flagValue, dsObj.getColumn(i, flagColNm) ) == -1 ) {
				continue;
			}
		}

        if ( ( bodyType != "checkbox" && length( dsObj.getColumn(i, condColumn) ) > 0 )
            || ( bodyType == "checkbox" && dsObj.getColumn(i, condColumn) == "1" ) ) {

            // 컬럼 확인
            for ( var c = 0; c < dsArr.length(); c++) {
                if ( dsObj.getColumn(i, dsArr[c]) == null || dsObj.getColumn(i, dsArr[c]) == "" ) {
                    var r= gfn_getColIdx( gridObj, dsArr[c] );
                    var str = "";
                    if ( labelArr == null || length(labelArr[c]) == 0 ) {
                        str = replace(gridObj.getCellProp("head", r, "text"), "*", "");
                    } else {
                        str = labelArr[c];
                    }

                    var arr = [title, str];
                    // [${}] 항목이 입력될 경우 [${}] 항목은 필수입력입니다.\n확인하여 주십시오.
                    gfn_showAlert("CW00004", arr);
                    dsObj.row = i;
                    gridObj.setCellPos(r);
                    gridObj.setFocus();

                    return true;
                }   // end if
            }   // end for
        }   // end column if
    }   // end for

    return false;
}
/**************************************************************************************
* Dataset의 컬럼 아이디에 해당 인덱스 구하기
* @param  gridObj  확인할 그리드 객체
* @param  dsColNm  Dataset 컬럼 아이디
* @return 해당 Dataset 컬럼이 바인딩 되어 있는 그리드 컬럼 인덱스
**************************************************************************************/
function gfn_getColIdx( gridObj, dsColNm ) {
	for ( var a = 0; a < gridObj.getColCount(); a++) {
		if ( gridObj.getCellProp("body", a, "colid") == dsColNm ) {
			return a;
		}
	}

	return -1;
}
/**************************************************************************************
* 그리드의 입력된 데이터 중복 여부 확인하기
* @param  dsObj  확인할 데이터셋 객체
* @param  isShowMsg  메시지 전시 여부
* @param  row  dataset의 row
* @param  dsColNm 확인할 컬럼 명
* @param  lbTxt  해당 정보 항목 명
* @return  true - 중복되는 경우  / false - 그렇지 않은 경우
**************************************************************************************/
function gfn_isDupGridValue(gridObj, isShowMsg, row, dsColNm, lbTxt) {
    var dsObj = object(gridObj.bindDataset);
    var str = dsObj.getColumn(row, dsColNm);

    if ( length(str) == 0 || dsObj.rowCount() < 2 ) {
        return false;
    }

    for( var i = 0; i < dsObj.rowCount(); i++) {
        if ( i == row ) {
            continue;
        }

        // 같은 경우
        if ( dsObj.getColumn(i, dsColNm) == str) {
			if ( isShowMsg ) {
				if ( length(lbTxt) == 0 ) {
					 var r= gfn_getColIdx( gridObj, dsColNm );
					lbTxt = trim(gridObj.getCellProp("head", r, "text"));
				}

				//  항목의 값은 중복하여 입력할 수 없습니다.\n다른 값을 입력하여 주십시오."
				gfn_showMsg("CW00018", lbTxt);
				dsObj.setColumn(row, dsColNm, "");
			}
            return true;
        }
    }

    return false;
}
/**************************************************************************************
* 최소 한건 존재해야 할 경우 'D' 등을 제외하고, 처리 후 남을 건수 가져오기
* @param  dsObj  확인할 데이터셋 객체
* @param  flagColNm  플래그  컬럼 명
* @return  건수
**************************************************************************************/
function gfn_getMinDataCnt(dsObj, flagColNm) {
	var cnt = 0;
	for( var i = 0; i < dsObj.rowCount(); i++ ) {
		if ( dsObj.getColumn(i, flagColNm) != "D" ) {
			cnt++;
		}
	}

	return cnt;
}
/**************************************************************************************
* Dataset 변경 유무 확인하기
* @param  dsObj  확인할 데이터셋 객체
* @param  isShowMsg  메시지 창 전시 여부
* @param  flagColumn  플래그 컬럼 사용시 플래그 컬럼 명
* @param  lbTxt  해당 정보 항목 명
* @return  true - 변경된 경우 / false - 변경 안됐을 경우
**************************************************************************************/
function gfn_isChangeDs(dsObj, isShowMsg, flagColumn, lbTxt) {

    if ( length( flagColumn ) > 0 ) {
        for ( var i = 0; i < dsObj.rowCount(); i++ ) {
            var str = dsObj.getColumn(i, flagColumn);
            if ( str == "I" || str == "U" || str == "D") {
                return true;
            }
        }
    } else if ( dsObj.GetUpdate() ) {
		return true;
    }

    if ( isShowMsg ) {
		if ( length( lbTxt ) > 0 ) {
			// CW00006=${}에 변경된 정보가 없습니다.
			gfn_showAlert("CW00006", lbTxt);
		} else {
			// CW00005=변경된 정보가 없습니다.
			gfn_showAlert("CW00005");
		}
    }

    return false;
}
/**************************************************************************************
* 입력된 Char가 특수 문자인지 여부
* @param  nChar  확인할 하나의 Char
* @return  특수 문자일 경우 true, 아닐 경우 false
**************************************************************************************/
function gfn_isSpecialChar(nChar) {
	var str = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	for( var i = 0; i < length( str); i++ ) {
		if ( nChar == asc( str[i] ) ) {
			return true;
		}
	}
	return false;
}
/**************************************************************************************
* 입력된 문자열이 특수 문자인지 여부
* @param  inStr  확인할 문자열
* @return  특수 문자가 문자열에 포함되있을 경우 true, 아닐 경우 false
**************************************************************************************/
function gfn_isSpecialStr(inStr) {
	var str = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	for( var i = 0; i < length( str); i++ ) {
		for( var c = 0; c < length( inStr ); c++ ) {
			if ( asc( inStr[c] ) == asc( str[i] ) ) {
				return true;
			}
		}
	}

	return false;
}
/**************************************************************************************
* 문자열에서 특수문자를 ""로 변환
* @param  inStr  문자열
**************************************************************************************/
function gfn_replaceSpecialChar(inStr) {
	var str = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	var rStr = inStr;
	for( var i = 0; i < length( str); i++ ) {
		rStr = replace(rStr, str[i], "");
	}

	return rStr;
}
/**************************************************************************************
* 문자열에서 특수문자 존재여부 리턴
* 해당 컴포넌트의 onChar 이번트를 디폴트로 만든후 그 함수 안에
  return  gfn_OnChar_Special(obj, nChar, LLParam, HLParam, true[false]);
* @param  이벤트에서 생성된 동일한 객체 입력
* @return  특수 문자가 문자열에 포함되있을 경우 true, 아닐 경우 false
**************************************************************************************/
function gfn_OnChar_special(obj, nChar, LLParam, HLParam, isShowMsg ) {
	//DEL 허용
    if(nChar == 46 && LLParam == 1 && HLParam == 339) {
        return true;
    }

    // 특수문자 체크
    if(gfn_isSpecialChar(nChar)) {
        if ( isShowMsg ) {
			// 특수문자 포함되어 있음.
			 // 특수문자는 입력할 수 없습니다.
			gfn_showAlert("CW00003");
        }

        return false;
    }

    //Ctrl+V 에 의한 특수문자 체크
    if(nChar == 22) {
		if(gfn_isSpecialStr(obj.Text)) {
			obj.Text = gfn_replaceSpecialChar(obj.Text);
			if ( isShowMsg ) {
				// 특수문자 포함되어 있음.
				gfn_showAlert("CW00003");
			}
		}

		return false;
    }

    return true;
}
/**************************************************************************************
*  일자 크기 비교 ( toDt >= fromDt : true )
* 두 일자가 서로 같은 형식을 가져야 한다. ( YYYYMMDD 또는 YYYYMM )
* @param  fromDt  기준 일자
* @param  toDt  비교할 일자
* @param  isShowMsg 메시지창 전시 여부
* @param  focusObj  에러시 포커스 위치 객체
* @param  fromLbl 기준일자 표시 명
* @param  toLbl 비교할 일자 표시 명
* @return  toDt 가 크거나 같을 경우 true, 그와 false
**************************************************************************************/
function gfn_checkDate( fromDt, toDt, isShowMsg, focusObj, fromLbl, toLbl ) {
	if ( toNumber( fromDt ) <= toNumber( toDt ) ) {
		return true;
	}
	// 메시지 전시 여부
	if( isShowMsg ) {
		if ( length( fromLbl) > 0 && length( toLbl ) > 0 ) {
			// CW00007=${}은/는 ${} 보다 같거나 이후 일자로 입력하여 주십시오.
			var arr = [toLbl, fromLbl];
			gfn_showAlert("CW00007", arr );
		} else if ( length( fromLbl ) > 0 ) {
			// CW00008=${} 일자의 값이 잘못되었습니다.\n확인하여 주십시오.
			gfn_showAlert("CW00008", fromLbl );
		} else if ( length( toLbl ) > 0 ) {
			// CW00008=${} 일자의 값이 잘못되었습니다.\n확인하여 주십시오.
			gfn_showAlert("CW00008", toLbl );
		} else {
			// CW00009=기간이 잘못 입력되었습니다.\n확인하여 주십시오.
			gfn_showAlert("CW00009");
		}
	}

	if ( focusObj != null ) {
		focusObj.setfocus();
	}

	return false;
}
/**************************************************************************************
* 시작, 종료 형태로 입력되는 두 항목의 값 확인
* 시작 항목의 값은 종료 항목의 값보다 작거나 같아야 함.
* @param  fromObj  시작 객체
* @param  toObj  종료 객체
* @param  isShowMsg 메시지창 전시 여부
* @param  lbTxt  항목 표시 명
* @param  nLength 최대 자리수가 존재할 경우 0 보다 크게 자리수 입력
* @param  isRequired 항목이 필수 입력 항목일 경우 처리
* @return  처리 성공일 경우 true, 그와 false
**************************************************************************************/
function gfn_checkFromToObj(fromObj, toObj, isShowMsg, lbTxt, nLength, isRequired ) {
	if( isRequired == true
		&& length( fromObj.value ) == 0
		&& length( toObj.value ) == 0 ) {
		if ( isShowMsg ) {
			if ( length( lbTxt ) > 0 ) {
				// CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
				gfn_showMsg("CW00001", lbTxt + "(FROM)");
			} else {
				gfn_showMsg("CW00002");
			}

			fromObj.setFocus();
			return false;
		} else {
			return false;
		}
	}
	// from 일자 확인
	if ( length( fromObj.value ) == 0 ) {
		if ( length( toObj.value) == 0 ) {
			return true;
		}

		if ( isShowMsg ) {
			// 있어야 함.
			if ( length( lbTxt ) > 0 ) {
				// CW00004=[${}] 항목이 입력될 경우 [${}] 항목은 필수입력입니다.\n확인하여 주십시오.
				var arr = [lbTxt + "(TO)", lbTxt + "(FROM)"];
				gfn_showMsg("CW00004", arr);
			} else {
				// 필수 입력항목입니다.\n확인하여 주십시오.
				gfn_showMsg("CW00002");
			}

			fromObj.setFocus();
			return false;
		} else {
			return false;
		}
	}

	if ( length( toObj.value ) == 0 ) {
		if ( length( fromObj.value) == 0 ) {
			return true;
		}

		if ( isShowMsg ) {
			// 있어야 함.
			if ( length( lbTxt ) > 0 ) {
				// CW00004=[${}] 항목이 입력될 경우 [${}] 항목은 필수입력입니다.\n확인하여 주십시오.
				var arr = [lbTxt + "(FROM)", lbTxt + "(TO)"];
				gfn_showMsg("CW00004", arr);
			} else {
				// 필수 입력항목입니다.\n확인하여 주십시오.
				gfn_showMsg("CW00002");
			}

			toObj.setFocus();
			return false;
		} else {
			return false;
		}
	}

	if( toNumber(fromObj.value) > toNumber(toObj.value) ) {
	
	//alert(Trim(fromObj.value));
	//alert(Trim(toObj.value));
	
		// 시작항목의 값은 종료
		gfn_showMsg("CW00012");
		toObj.setFocus();
		return false;
	}

	if( toNumber( nLength) > 0 ) {
		var chkObj = null;
		if ( length( fromObj.value) != nLength ) {
			chkObj = fromObj;
		}
		if ( length( toObj.value) != nLength ) {
			chkObj = toObj;
		}

		if ( chkObj != null ) {
			// 값이 자리수(${})로 입력되어야 합니다.\n확인하여 주십시오.
			gfn_showMsg("CW00011", nLength+"");
			chkObj.setFocus();
			return false;
		}
	}

	return true;
}
/*===============================================================================
*   일자 관련 함수
================================================================================*/
/**************************************************************************************
*  월 별로 일수 구하기
* @param  fMonth  월 ( 1 ~ 12  숫자 )
* @return  해당 월의 총 일수
**************************************************************************************/
function gfn_getLastDay(fMonth) {
	var days = 31;
	switch ( fMonth ) {
		case 2:
			days = 28;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
	}

	return days;
}
/**************************************************************************************
*  YYYYMM 문자열을 입력하여 해당 월의 일수 구하기
* @param  sYYYYMM  ( ex: 200708 )
* @return  해당 월의 총 일수
**************************************************************************************/
function gfn_getLastDayByStr(sYYYYMM) {
	var iYear = toInteger( left( sYYYYMM, 4 ) );
	var iMonth = toInteger( substr( sYYYYMM, 4, 2 ) );
	if ( iMonth < 1 || iMonth > 12 ) {
		return -1;
	}

	var lastDay = gfn_getLastDay(iMonth);

	// 윤년인지 확인
	if ( iMonth == 2 ) {
		if ( ( iYear % 4 ) == 0
			&& (iYear % 100 ) != 0
			|| (iYear % 400 ) == 0 ) {
			lastDay = 29;
		}
	}

	return lastDay;
}
/**************************************************************************************
* 기준 일자로 부터 몇일 이후 일자 계산
* @param  orgDate  계산할 기준 일자 ( YYYYMMDD 형식 )
* @param  nDays  + 몇일
* @return  기준일자로 부터 이후 일자
**************************************************************************************/
function gfn_getAfterDate( orgDate, nDays) {
	var nDate = DateTime(_fn_getCorrectDate(orgDate));
	nDate = toNumber(nDate) + nDays;

	return DateTime(nDate);
}
/**************************************************************************************
* 기준 일자로 부터 몇일 이전 일자 계산
* @param  orgDate  계산할 기준 일자 ( YYYYMMDD 형식 )
* @param  nDays  + 몇일 이전
* @return  기준일자로 부터 이전 일자
**************************************************************************************/
function gfn_getBeforeDate( orgDate, nDays) {
	var nDate = DateTime(_fn_getCorrectDate(orgDate));
	nDate = toNumber(nDate) - nDays;

	return DateTime(nDate);
}
/**************************************************************************************
* 날짜 잘못 들어오는 것 체크 
**************************************************************************************/
function _fn_getCorrectDate(orgDate) {
	var lastDay = gfn_getLastDayByStr(orgDate);

	if ( toNumber( lastDay) < toNumber( right( orgDate, 2) ) ) {
		return left( orgDate, 6) + lastDay;
	}
	
	return orgDate;
}
/**************************************************************************************
* 기준 월로 부터 몇 달 이후 월 계산
* @param  orgMonth  계산할 기준 월  ( YYYYMM 형식 )
* @param  nDays  + 몇 달
* @return  기준월로 부터 이후 월
**************************************************************************************/
function gfn_getAfterMonth( orgMonth, nMonths) {
	var nDate = DateTime(orgMonth + "" +  gfn_getLastDayByStr(orgMonth));

	nDate = toNumber(nDate) + ( nMonths * 28 );
	if ( nMonths > 12 ) {
		nDate += ( (nMonths-12) * 3 );
	}
/* 2012.01.13 LJH 윤년 보정 작업(시작) */
	var year1 = substr(orgMonth, 0, 4);
	var year2 = substr(DateTime(nDate), 0, 4);

	if(nMonths == 12) {
		if((ParseInt(year1)%4 == 0 && ParseInt(year1)%100 != 0) || ParseInt(year1)%400 == 0) {
			nDate = nDate + 2;
		}

		if((ParseInt(year2)%4 == 0 && ParseInt(year2)%100 != 0) || ParseInt(year2)%400 == 0) {
			nDate = nDate + 2;
		}
	}
/* 2012.01.13 LJH 윤년 보정 작업(끝) */
	return substr(DateTime(nDate), 0, 6);
}
/**************************************************************************************
* 기준 월로 부터 몇 달 이전 월 계산
* @param  orgMonth  계산할 기준 월  ( YYYYMM 형식 )
* @param  nDays  + 몇 달 이전
* @return  기준월로 부터 이전 월
**************************************************************************************/
function gfn_getBeforeMonth( orgMonth, nMonths ) {
	var nDate = DateTime(orgMonth + "01");
	nDate = toNumber(nDate) - ( nMonths * 28 );
	if ( nMonths > 12 ) {
		nDate -= ( (nMonths-12) * 3 );
	}
	return substr(DateTime(nDate), 0, 6);
}
/**************************************************************************************
* 입력받은 from일로부터 입력to일까지 개월수반환하기
* @param  fdate  From 일 ( YYYYMMDD~ 으로 시작 )
* @param  tdate  To 일 ( YYYYMMDD ~ 으로 시작 )
* @return  총개월수   ( From 일이 클 경우 -1 값이 반환 됨. )
**************************************************************************************/
function gfn_getMonthCnt(fdate, tdate) {
  var iYear  = 0; //계산된 년수
  var iMonth = 0; //계산된 개월수
  var iDay   = 0; //계산된 일수
  var rMonth = 0; //return될 월수
  var fYear  = 0;
  var fMonth = 0;
  var fDay   = 0;
  var tYear  = 0;
  var tMonth = 0;
  var tDay   = 0;

  if( length(fdate) != 8 || length(tdate) != 8) {
	return -1;
  }	

  if(parseInt(fdate) <= parseInt(tdate)) {
    fYear  = parseInt(substr(fdate,0,4));
    fMonth = parseInt(substr(fdate,4,2));
    fDay   = parseInt(substr(fdate,6,2));
    tYear  = parseInt(substr(tdate,0,4));
    tMonth = parseInt(substr(tdate,4,2));
    tDay   = parseInt(substr(tdate,6,2));

	iYear  = tYear - fYear;
	iMonth = tMonth - fMonth;
	iDay   = tDay - fDay;  

	rMonth = iYear*12 + iMonth;
	if(fdate == tdate) {
	    rMonth = 0;
	}
	else if(fYear == tYear && fMonth == tMonth) {
	    rMonth = 1;
	}
	else if(fDay > tDay) {
	    rMonth = rMonth;
    }
	else if(fDay <= tDay) {
	    rMonth = rMonth + 1;
    }
  }
  else
  {
//	  rMonth = -1;
	  rMonth = 0;
  }

  return rMonth;
}
/**************************************************************************************
* 입력받은 from일로부터 입력to일까지 개월수반환하기
* @param  fMonth  From 일 ( YYYYMMDD ~ 으로 시작 )
* @param  tMonth  To 일 ( YYYYMMDD ~ 으로 시작 )
* @return  총개월수   ( From 월이 클 경우 - 값이 반환 됨. )
**************************************************************************************/
function gfn_getMonthCnt2(fdate, tdate) {
  var iMonth   = 0; //계산된 개월수
  var iYear    = 0; //계산된 년도
  var rMonth   = 0; //반환할 개월수

  if(parseInt(fMonth) <= parseInt(tMonth))
  {
  	  iYear =  parseInt(substr(tMonth, 0,4))- parseInt(substr(fMonth, 0,4)) ;
  	  iMonth = parseInt(substr(tMonth, 4,2))- parseInt(substr(fMonth, 4,2));
  	  rMonth = (12 * iYear) + iMonth + 1;
  	  return rMonth;
   } 
   else 
   {
 	  iYear =  parseInt(substr(fMonth, 0,4))- parseInt(substr(tMonth, 0,4)) ;
  	  iMonth = parseInt(substr(fMonth, 4,2))- parseInt(substr(tMonth, 4,2));
  	  rMonth = (12 * iYear) + iMonth ;
  	  return ( rMonth * -1 );
   }

   if(iYear == 1 && iMonth == 0 && parseInt(substr(fMonth, 6,2)) > parseInt(substr(tMonth, 6,2))) 
   {
	  return 12;
   }
}
/**************************************************************************************
*  입력받은 from일로부터 입력to일까지 일수반환하기
* @param dsObj	조회한 날짜를 담을 Dataset 객체 아이디(dsObj의 필드이름은 DAYS로 한다)
* @param  fdate  From 일 ( YYYYMMDD)
* @param  tdate  To 일 ( YYYYMMDD)
* @return  dsObj
**************************************************************************************/
function gfn_getDayCnt(dsObj, fdate, tdate) 
{
	gfn_getDayCnt2(dsObj, fdate, tdate);
	return _tmp_dsObj.getcolumn(0,"DAYS");
}

function gfn_getDayCnt2(dsObj, fdate, tdate) 
{
	_tmp_dsObj = dsObj;

  if( length(fdate) != 8 || length(tdate) != 8) 
  {
	_tmp_dsObj.setcolumn(0,"DAYS",-1);
	return;
  }	
  	
  if(parseInt(fdate) <= parseInt(tdate)) 
  {
    fdate = substr(fdate,0,4)+"-"+substr(fdate,4,2)+"-"+substr(fdate,6,2);
    tdate = substr(tdate,0,4)+"-"+substr(tdate,4,2)+"-"+substr(tdate,6,2);
    
	tit_clearActionInfo();

	tit_addSearchActionInfo("ps02:PS0209001A_S1");

	tit_callService(
          ""
        , ""
        , ""
        , _tmp_dsObj.id+"=ds_days"
        , "FDATE=" + quote(fdate) + " TDATE=" + quote(tdate)
        , "_fn_afterDaySearch"
        , false
        , false
        , true);
        
   } 
   else 
   {
	   _tmp_dsObj.setcolumn(0,"DAYS",-1);	
 	   return;
   }
}
/****************************************************************
* 구간일수 조회후 Callback
* RETURN 총 일수.
***************************************************************/
function _fn_afterDaySearch(errCode, errMsg) 
{
}
/**************************************************************************************
* 현재일자 구하기
* @return 현재일자 ( YYYYMMDD )
**************************************************************************************/
function gfn_getCurrDate() 
{
	if ( G_CURR_DATE > '' ) 
	{
		return G_CURR_DATE;
	} 
	else 
	{
		return today();
	}
}
/**************************************************************************************
* 현재일자 구하기
* @return 현재일자 ( YYYYMMDD )
**************************************************************************************/
function gfn_getCurrDate2() 
{
	if ( length(G_ORG_USER_INFO) == 0 )
	{
		tit_callService(
			  "COMM_ACT"
			, "getCurrDate"
			, ""
			, ""
			, " G_MANDT=" + quote(G_MANDT) + " G_USER_ID=" + quote(G_USER_ID) 
			, "ufn_afterCurrDate2"
			, false
			, true);
	}
	else
	{
		var arr = split(G_ORG_USER_INFO, "^", true);

		tit_callService(
			  "COMM_ACT"
			, "getCurrDate"
			, ""
			, ""
			, " G_MANDT=" + quote(G_MANDT) + " G_USER_ID=" + quote(arr[0]) 
			, "ufn_afterCurrDate2"
			, false
			, true);
	}
}

function ufn_afterCurrDate2() 
{
	// if ( G_LOGOUT == "0" )
	// {
		// Exit();
	// }
}

/**************************************************************************************
* 현재 월  구하기
* @return 현재월 ( YYYYMM )
**************************************************************************************/
function gfn_getCurrMonth() 
{
	return left( gfn_getCurrDate(), 6 );
}
/*===============================================================================
*   Dataset 관련 함수
================================================================================*/
/**************************************************************************************
* 콤보등의 선택된 값에 대한 코드 명 가져오기
* @param  dsObj  Data를 가지고 있는 Dataset 객체
* @param  codeValue 선택된 코드 값
* @param  codeColNm Dataset의 코드 컬럼 명 ( 기본 : CODE )
* @param  dataColNm  Dataset의 코드 명 컬럼 명 ( 기본 : CODE_NAME )
* @return  코드에 해당하는 코드 명
**************************************************************************************/
function gfn_getCodeName(dsObj, codeValue, codeColNm, dataColNm) 
{
    if ( length( codeColNm  ) == 0 ) 
    {
        codeColNm = "CODE";
    }

    if ( length( dataColNm  ) == 0 ) 
    {
        dataColNm = "CODE_NAME";
    }

    var row = dsObj.findRow(codeColNm, codeValue);
    if ( row == -1 ) 
    {
        return "";
    }

    return dsObj.getColumn(row, dataColNm);
}
/**************************************************************************************
* 데이터셋 초기화
* @param  dsObj  Dataset 객체
* @param  isAddRow 입력 가능한 Row 생성여부
* @return  없음.
**************************************************************************************/
function gfn_initDs(dsObj, isAddRow) 
{
	dsObj.clearData();
	if ( isAddRow ) 
	{
		dsObj.addRow();
	}
}
/**************************************************************************************
* 데이터셋을 조건에 따라 Target Dataset으로 복사
* @param  orgDsObj  복사할 원본 Dataset 객체
* @param  targetDsObj 복사 대상  Dataset 객체
* @param  cond  복사할 조건 ( and : && or : || )
* @return  없음.
**************************************************************************************/
function gfn_copyDs(orgDsObj, targetDsObj, cond) {
	targetDsObj.fireevent = false;
	if ( length( cond) > 0 ) {
		orgDsObj.filter(cond);
		targetDsObj.copyF( orgDsObj );
		orgDsObj.unFilter();
	} else {
		targetDsObj.copy( orgDsObj );
	}

	targetDsObj.fireevent = true;
}
/**************************************************************************************
* 데이터셋 생성하기
* @param  dsName  생성할 데이터셋 명
* @param  colNm  컬럼명 배열
* @param  colTypeNm  컬럼타입 배열
* @param  nColSize  사이즈 배열
* @return  생성한 데이터셋 객체
**************************************************************************************/
function gfn_createDs(dsName, colNm, colTypeNm, nColSize) {
	Create("Dataset", dsName);
	var dsObj = object(dsName);

	for( var i = 0; i < colNm.length(); i++ ) {
		dsObj.addColumn(colNm[i], colTypeNm[i], nColSize[i]);
	}

	return dsObj;
}
/**************************************************************************************
* 그리드에서 표시할 컬럼 선택 창 띄위기
* @param  grdObj  처리할 그리드 객체 또는 탭 등의 다른 컴포넌트에 포함되어 있을 경우
  전체 경로 ex) "tab_main.tab1.grd_list"
* @return  없음.
**************************************************************************************/
function gfn_selectGridCol(grdObj, isForce) {
	var grdNm = "";

	if ( type( grdObj ) == "STRING") {
		grdNm = grdObj;
	} else {
		grdNm = grdObj.id;
	}

	var mak = "N";
	if ( isForce == true ) {
		mak = "Y";
	}
	gfn_openPopupUrl("WB01::CommGridColSelect.xml", true, "fa_gridName='" + grdNm + "' fa_isMake='" + mak + "'" );
}
/**************************************************************************************
* 그리드에서 전체 선택 또는 선택 해제
* @param  grdObj  처리할 그리드 객체
* @param  nHeadCell  CheckBox가 포함된 Head의 colIdx
* @param  nBodyCell  CheckBox를 가지고 있는 Body의 colIdx
* @param  bEvent  처리즉시 화면에 표시할지 여부
* @return  없음.
**************************************************************************************/
function gfn_selectAllGrid(grdObj,nHeadCell,nBodyCell,bEvent)
{
	var strColID = grdObj.GetCellProp("body", nBodyCell, "Colid");

	if ( length( strColID ) == 0 ) {
		return;
	}
	var dsObj = object(grdObj.BindDataset);
	
	grdObj.SetCellPos(nHeadCell);

	// Check Box 전체 선택
	var nCount = dsObj.GetRowCount();

	var strChk = decode(grdObj.getCellProp("head", nHeadCell, "Text"), "1", "0", "1");

	grdObj.ReDraw = false;
	if (bEvent == false) {
		dsObj.FireEvent = false;
	}

	for (var i=0; i < dsObj.GetRowCount(); i++) {
		dsObj.SetColumn(i, strColID, strChk);
	}

	if (bEvent == false) {
		dsObj.FireEvent = true;
	}
	grdObj.SetCellProp("head", nHeadCell, "Text", strChk);
	grdObj.ReDraw = true;
}

function gfn_selectAllGrid2(grdObj,nHeadCell,nBodyCell,bEvent)
{
	var strColID = grdObj.GetCellProp("body", nBodyCell, "Colid");

	if ( length( strColID ) == 0 ) {
		return;
	}
	var dsObj = object(grdObj.BindDataset);

	grdObj.SetCellPos(nHeadCell);

	// Check Box 전체 선택
	var nCount = dsObj.GetRowCount();

	var strChk = decode(grdObj.getCellProp("head", nHeadCell, "Text"), "1", "0", "1");

	grdObj.ReDraw = false;
	if (bEvent == false) {
		dsObj.FireEvent = false;
	}

	for (var i=0; i < dsObj.GetRowCount(); i++) {
		if(grdObj.GetCurEditType() == "checkbox") {
			dsObj.SetColumn(i, strColID, strChk);
		}
	}

	if (bEvent == false) {
		dsObj.FireEvent = true;
	}
	grdObj.SetCellProp("head", nHeadCell, "Text", strChk);
	grdObj.ReDraw = true;
}
/******************************************************
 *  Grid의 데이터를 Excel로 저장
 * @param  gridObj  저장 처리할 Grid 객체
 * @param  bExec - 바로 실행 여부
 * @param  workSheetNm Excel에서 저장할 Sheet 이름
           null 일 경우 "sheet1"에 저장
 * @param  startCellNm Excel에서 저장할 Cell을 시작 이름
           null 일 경우 "A1"에 저장
 * @param  isOnlySelCol 그리드에서 선택한 영역만 Save할지 여부
 * @param  isAddHeadSumm  그리드의 Header / Summary 부분도 함께 Save 할지 여부
 * @param  isASync 비동기식 처리 여부
 * @param  isMerge  Cell이 Merge 된것도 그대로 Save 할지 여부
 * @param  notExportColNm  Export 처리 제외 대상 컬럼
           있을 경우 배열 형태로 입력
 * @return  변환된 String
 * 참고 : gfn_saveToExcel(strFileName,strDocName,strCell,bSelOnly,bAddHeadSumm,bAsync,bMerge);
******************************************************/
function gfn_saveToExcel(gridObj, bExec, workSheetNm, startCellNm
	, isOnlySelCol, isAddHeadSumm, isASync, isMerge
	, notExportColNm ) {

	if ( object( gridObj.bindDataset).rowCount() < 1 ) {
		return;
	}

	Create("FileDialog", "_fileDialog_", "");
	var dialogObj = object("_fileDialog_");

	dialogObj.Type = "Save";
	dialogObj.Filter = "Microsoft Excel File(*.xls)|*.xls|";

	if (!dialogObj.open()) {
		Destroy( "_fileDialog_" );
		return;
	}

	// 저장 처리
	gfn_saveToExcelFile(gridObj, bExec, workSheetNm, startCellNm
                         , isOnlySelCol, isAddHeadSumm, isASync, isMerge
                         , notExportColNm
                         , dialogObj.FilePath + "\\" + dialogObj.FileName );

	Destroy( "_fileDialog_" );
}
/******************************************************
 *  Grid의 데이터를 Excel로 저장
 * @param  gridObj  저장 처리할 Grid 객체
 * @param  bExec - 바로 실행 여부
 * @param  workSheetNm Excel에서 저장할 Sheet 이름
           null 일 경우 "sheet1"에 저장
 * @param  startCellNm Excel에서 저장할 Cell을 시작 이름
           null 일 경우 "A1"에 저장
 * @param  isOnlySelCol 그리드에서 선택한 영역만 Save할지 여부
 * @param  isAddHeadSumm  그리드의 Header / Summary 부분도 함께 Save 할지 여부
 * @param  isASync 비동기식 처리 여부
 * @param  isMerge  Cell이 Merge 된것도 그대로 Save 할지 여부
 * @param  notExportColNm  Export 처리 제외 대상 컬럼
           있을 경우 배열 형태로 입력
 * @param  xlsFileName 저장할 Excel 파일 명
 * @return  없음.
 * 참고 : gfn_saveToExcel(strFileName,strDocName,strCell,bSelOnly,bAddHeadSumm,bAsync,bMerge);
******************************************************/
function gfn_saveToExcelFile(gridObj, bExec, workSheetNm, startCellNm
	, isOnlySelCol, isAddHeadSumm, isASync, isMerge
	, notExportColNm, xlsFileName ) {

	if ( length(workSheetNm) == 0 ) {
		workSheetNm = "sheet1";
	}

	if ( length(startCellNm) == 0 ) {
		startCellNm = "A1";
	}

	var orgSize = null;
	if ( notExportColNm != null ) {
		// 비동기식으로 처리하지 않음.
		isASync = false;
		var colIdx = -1;
		orgSize = Array();
		for ( i = 0; i < notExportColNm.length; i++) {
			colIdx = gfn_getColIdx( gridObj, notExportColNm[i] );
			orgSize[i] = gridObj.getColProp(colIdx, "width");
			if ( colIdx >= 0 ) {
                gridObj.setColProp(colIdx, "width", 0);
            }
		}
	}

	var filePath = xlsFileName;
	if ( toLower( right( filePath, 4 ) ) != ".xls" ) {
		filePath = filePath + ".xls";
	}

	// Grid Open하여 excel 처리
	gridObj.SaveExcel(filePath, workSheetNm, startCellNm,isOnlySelCol, isAddHeadSumm, isASync, isMerge);

	if ( orgSize != null ) {
		var colIdx = -1;
		for ( i = 0; i < orgSize.length; i++) {
			colIdx = gfn_getColIdx( gridObj, notExportColNm[i] );
			gridObj.setColProp(colIdx, "width", orgSize[i]);
		}
	}

	if ( bExec ) {
		ExecShell(filePath);
	}
}
/**************************************************************************************
*  Excel로 Export 하기 ( 저장하지 않음. )
* @param  gridObj  해당 그리드 객체
* @param  sheetName - sheet 명
* @return  없음.
*****************************************************************************************/
function gfn_exportExcel(gridObj, sheetName) {
	gridObj.ExportExcelEx(sheetName);
}
/******************************************************
 * 액셀의 내용을 Dataset으로 load
 * @param  grdObj  import 처리할 그리드 객체
 * @param  autoSet  import 시 excel 자동 매핑 여부
 * 참고 :
******************************************************/
function gfn_importExcel(grdObj, autoSet) {
	Create("FileDialog", "_fileDialog_", "");
	var dialogObj = object("_fileDialog_");

	dialogObj.Type = "Open";
	dialogObj.FileName = "";
	dialogObj.Filter = "Microsoft Excel File(*.xls)|*.xls|";
	dialogObj.open();

	if (length(dialogObj.FileName) == 0) {
		Destroy( "_fileDialog_" );
		return;
	}

	var arg = "fa_grdName=" + quote( grdObj.id);
	arg+= " fa_excelFile=" + quote( dialogObj.FilePath + "\\" + dialogObj.FileName);
	arg+= " fa_autoSet=" + quote( autoSet);

	Destroy( "_fileDialog_" );
	gfn_openPopupUrl("WB01::CommExcelImportForm.xml", true, arg );
}
/**************************************************************************************
* Report Viewer 프로그램 보이기
* @param  reportDir  리포트 디렉토리
* @param  reportNm  리포트 이름
* @param  dsNameArr  리포트로 보낼 데이터셋 명 (ds_list,ds_list2) , 로 구분
* @param  sArg  리포트로 보낼 argument 명 ( argKey=value^argKey2=value)
* @param  isNotView - Viewer를 사용하지 않고 바로 Print로 연결할지 여부 : 
          1장만 출력 된다. 프린트로 바로 출력됨. 
* @param  isHorz 가로인지 여부 - default : false
* @return  없음.
**************************************************************************************/
function gfn_report( reportDir, reportNm, dsNameArr, sArg, isNotView, isHorz) {
	var arg = "fa_reportDir=" + quote( reportDir);
	arg += " fa_reportNm=" + quote( reportNm + ".jrf") ;
	arg += " fa_dsArr=" + quote( dsNameArr );
	arg += " fa_arg=" + quote( sArg );
	arg += " fa_isDirectPrint=" + quote( iif( isNotView, "Y", "N") );

	if( isNotView ) {
		gfn_openPopupUrl("WB01::CommReportViewer.xml", true, arg );
	} else {
		gfn_openPopupUrl("WB01::CommReportViewer.xml", true, arg );
	}	
}
/**************************************************************************************
* GIS 연계창 띄우기 
* @param  projectName  프로젝트 명 
* @return  배열 []
	0 - prj
	1 - bdcode
	2 - xpos
	3 - ypos
	4 - san
	5 - lotmain
	6 - lotsub
	7 - addr
**************************************************************************************/
function gfn_openGIS( projectName) {
	var arg = "fa_proName=" + quote( projectName);
	return gfn_openPopupUrl("WB01::CommGISViewer.xml", true, arg );
}
/*===============================================================================
*   기타 함수
================================================================================*/
/**************************************************************************************
* 팝업화면이 아닌 일반 화면으로 띄우기
* @param  pgmId  보여줄 프로그램 아이디
* @param  arg 해당 폼에 전송할 variable 정보
**************************************************************************************/
function gfn_openWin(pgmId, arg) {
	global.FRM_TOP.ufc_openForm( pgmId , arg);
}
/**************************************************************************************
* 팝업 화면 띄우기
* @param  pgmId  Popup을 띄울 프로그램 아이디
* @param  isModal - modal 여부 ( true : modal / false : modalless )
* @param  arg - parms ( "key=value" 형태로 Popup화면에 전달할 인자 )
* @param  w - width
* @param  h - height
* @param  x - screen 상의 X 위치
* @param  y - screen 상의 Y 위치
* @return 	Popup에서 close()로 전달한 값
**************************************************************************************/
function gfn_openPopup(pgmId, isModal, arg, w, h, x , y) {
	// Program 에서 프로그램 가져오기
	var screenName = gfn_getPgmUrl(pgmId);
	if ( screenName == null ) {
		// 사용 권한 없음.

		return;
	}

    // 프로그램 아이디 넘기기
    arg += " F_PGM_ID=" + quote( pgmId );

    return gfn_openPopupUrl(screenName, isModal, arg, w, h, x , y);
}

/**************************************************************************************
* 팝업 화면 띄우기
* @param  pgmId  Popup을 띄울 프로그램 아이디
* @param  isModal - modal 여부 ( true : modal / false : modalless )
* @param  arg - parms ( "key=value" 형태로 Popup화면에 전달할 인자 )
* @param  w - width
* @param  h - height
* @param openStyle - 팝업창 스타일
* @param  x - screen 상의 X 위치
* @param  y - screen 상의 Y 위치
* @return 	Popup에서 close()로 전달한 값
**************************************************************************************/
function gfn_openPopup2(pgmId, isModal, arg, w, h,openStyle, x , y) {
	// Program 에서 프로그램 가져오기
	var screenName = gfn_getPgmUrl(pgmId);
	if ( screenName == null ) {
		// 사용 권한 없음.

		return;
	}

    // 프로그램 아이디 넘기기
    arg += " F_PGM_ID=" + quote( pgmId );

    return gfn_openPopupUrl2(screenName, isModal, arg, w, h,openStyle, x , y);
}









/**************************************************************************************
* 팝업 화면 띄우기
* @param  url  팝업으로 띄울 .xml을 포함한 전체 경로
* @param  isModal - modal 여부 ( true : modal / false : modalless )
* @param  arg - parms ( "key=value" 형태로 Popup화면에 전달할 인자 )
* @param  w - width
* @param  h - height
* @param  x - screen 상의 X 위치
* @param  y - screen 상의 Y 위치
* @return 	Popup에서 close()로 전달한 값
**************************************************************************************/
function gfn_openPopupUrl(url, isModal, arg, w, h, x , y) {
	var leftX = x;
	var topY = y;

    if ( leftX == null || toNumber(leftX) <= 1) {
        leftX = -1;
    }

    if ( topY == null || toNumber(topY) <= 1 ) {
        topY = -1;
    }
    
    /*접속이력 데이타 처리*/ 
    var dsObjParam = Object("ds_pgmLog2"); 
	if(!IsValidObject(dsObjParam)){
		
		Create("Dataset","ds_pgmLog2");
	}
    
    
    /*
    var dsObjParam = find("ds_pgmLog2");       

    
	if( dsObjParam == null ) 
	{
		Create("Dataset", "ds_pgmLog2");
		dsObjParam = object("ds_pgmLog2");
	}
	*/
	ds_pgmLog2.AddColumn("PGMID", "STRING", 100);
    ds_pgmLog2.AddColumn("PGMNAME", "STRING", 10);
    ds_pgmLog2.AddColumn("PGMURL", "STRING", 200);
    ds_pgmLog2.AddColumn("USERID", "STRING", 200);    
    
    ds_pgmLog2.AddRow();
    ds_pgmLog2.SetColumn(0, "PGMID", url);
	ds_pgmLog2.SetColumn(0, "PGMNAME", url);
	ds_pgmLog2.SetColumn(0, "PGMURL", url);
	ds_pgmLog2.SetColumn(0, "USERID", G_USER_ID);
	
	/*
	// 정보 초기화
	tit_clearActionInfo();
	// 조회해야 하는 sql
	tit_addSearchActionInfo("wb01:PGMACCESSLOG_I02");

	tit_callService(
		""
		, ""
		, "ds_pgmLog=ds_pgmLog2"
		, ""
		, ""
		, ""
		, true);
	*/		
    /*접속이력 데이타 처리*/ 
    
   
	if ( isModal ) {
		return Dialog(url, arg, 1, 1, "TitleBar=false StatusBar=false", leftX, topY);
	} else {
		open(url, arg, 1, 1, "TitleBar=false StatusBar=false TaskBar=true ", leftX, topY);
	}
	
	
}

/**************************************************************************************
* 팝업 화면 띄우기
* @param  url  팝업으로 띄울 .xml을 포함한 전체 경로
* @param  isModal - modal 여부 ( true : modal / false : modalless )
* @param  arg - parms ( "key=value" 형태로 Popup화면에 전달할 인자 )
* @param  w - width
* @param  h - height
* @param openStyle - 팝업창 스타일
* @param  x - screen 상의 X 위치
* @param  y - screen 상의 Y 위치
* @return 	Popup에서 close()로 전달한 값
**************************************************************************************/
function gfn_openPopupUrl2(url, isModal, arg, w, h,openStyle, x , y) {
	var leftX = x;
	var topY = y;

    if ( leftX == null || toNumber(leftX) <= 1) {
        leftX = -1;
    }

    if ( topY == null || toNumber(topY) <= 1 ) {
        topY = -1;
    }
	
	
	/*접속이력 데이타 처리*/ 
    var dsObjParam = Object("ds_pgmLog2"); 
	if(!IsValidObject(dsObjParam)){
		
		Create("Dataset","ds_pgmLog2");
	}
    
    
    /*
    var dsObjParam = find("ds_pgmLog2");       

    
	if( dsObjParam == null ) 
	{
		Create("Dataset", "ds_pgmLog2");
		dsObjParam = object("ds_pgmLog2");
	}
	*/
	ds_pgmLog2.AddColumn("PGMID", "STRING", 100);
    ds_pgmLog2.AddColumn("PGMNAME", "STRING", 10);
    ds_pgmLog2.AddColumn("PGMURL", "STRING", 200);
    ds_pgmLog2.AddColumn("USERID", "STRING", 200);    
    
    ds_pgmLog2.AddRow();
    ds_pgmLog2.SetColumn(0, "PGMID", url);
	ds_pgmLog2.SetColumn(0, "PGMNAME", url);
	ds_pgmLog2.SetColumn(0, "PGMURL", url);
	ds_pgmLog2.SetColumn(0, "USERID", G_USER_ID);
	
	/*
	// 정보 초기화
	tit_clearActionInfo();
	// 조회해야 하는 sql
	tit_addSearchActionInfo("wb01:PGMACCESSLOG_I02");

	tit_callService(
		""
		, ""
		, "ds_pgmLog=ds_pgmLog2"
		, ""
		, ""
		, ""
		, true);
	*/
    /*접속이력 데이타 처리*/ 
	

	if ( isModal ) {
		return Dialog(url, arg, 1, 1, openStyle, leftX, topY);
	} else {
		open(url, arg, 1, 1, openStyle, leftX, topY);
	}
	
	
	
}

/**************************************************************************************
* 기본적으로 MaskEdit등을 사용하여 ,를 표시할 수 없을 경우
* 숫자에 , 표시 처리
* @param  orgNum  처리할 숫자
* @param  nMin 최소 자리 소수점 ( -1 경우 소수 자리 처리하지 않고 입력된 그대로 둠)
**************************************************************************************/
function gfn_formatComma(orgNum, nMin) {
    if ( nMin == null ) {
        nMin = 0;
    }

    var n = toNumber( orgNum );
    var s = n + "";
    var s1 = "";
    if ( indexOf(s, ".") != -1 ) {
        s1 = substr( s, indexOf(s, ".")+1);
        s = substr( s, 0, indexOf(s, "."));
    }

    var r = "";
    var len = length( s );
    // 1234567
    for ( var i = 0; i < len; i++ ) {
        if ( i > 0 && (len-i) % 3 == 0 ) {
            r += ",";
        }
        r += charAt(s, i);
    }

    if ( nMin > 0 ) {
        r += ".";
        for ( var i = 0; i < nMin; i++ ) {
            if( length( s1 ) > i ) {
                r += charAt( s1, i );
            } else {
                r += "0";
            }
        }
    } else if ( nMin == -1 && length( s1 ) > 0 ) {
        r += "." + s1;
    }
    return r;
}
/**************************************************************************************
* 기본적으로 MaskEdit등을 사용하여 일자를 표시할 수 없을 경우
* 문자에 - 등의 Delimiter 로 포맷팅 처리
* @param  sDate  날짜 ( YYYYMM 또는 YYYYMMDD )
* @param  sDel  . / 등의 구분자 ( 기본은 . 로 처리 )
**************************************************************************************/
function gfn_formatDate(sDate, sDel) {
	if ( length( sDel ) == 0 ) {
		sDel = ".";
	}

	var rtn = sDate;
	var sTime = "";
	switch( length( sDate ) ) {
		case 14:	// 시분초 처리
			sTime = " " + substr( sDate, 8, 2 ) + ":"
				+ substr( sDate, 10, 2 ) + ":"
				+ substr( sDate, 12, 2 );
		case 8:	// 일자 처리
			rtn = substr( sDate, 0, 4) + sDel
				+ substr( sDate, 4, 2 ) + sDel
				+ substr( sDate, 6, 2 );
			break;
		case 6:
			rtn = substr( sDate, 0, 4) + sDel + substr( sDate, 4, 2 );
			break;
	}

	return rtn + sTime;
}
/******************************************************
*  그리드 행 추가
* @param  gridObj  대상 Grid
* @param  r  추가할 Row 번지 : 없을 경우 마지막에 추가
* @param  flagColNm 해당 컬럼명 : 없을 경우 "FLAG"로 처리
* @return  추가된 Row 의 번지
******************************************************/
function gfn_addGridRow(gridObj, nRow, flagColNm) {
	var dsObj = object(gridObj.bindDataset);

	if ( nRow == null || toNumber(nRow) < 0) {
		nRow = dsObj.AddRow();
	} else {
		dsObj.insertRow( nRow );
	}

	if ( flagColNm == null ) {
		flagColNm = "FLAG";
	}

	dsObj.setColumn(nRow, flagColNm, "I");
	return nRow;
}
/******************************************************
*  그리드 행 삭제
* @param  gridObj  대상 Grid
* @param  nRow  삭제할 Row 번지
* @param  flagColNm 해당 컬럼명 : 없을 경우 "FLAG"로 처리
* @return  없음
******************************************************/
function gfn_deleteGridRow( gridObj, nRow, flagColNm ) {
	if ( flagColNm == null ) {
		flagColNm = "FLAG";
	}

	var dsObj = object(gridObj.bindDataset);

	if ( dsObj.GetRowType(nRow) == "insert"
		|| dsObj.getColumn(nRow, flagColNm) == "I" ) {
		dsObj.DeleteRow(nRow);
	} else {
		if ( dsObj.getColumn(nRow, flagColNm) == "D" ) {
			dsObj.setColumn(nRow, flagColNm, "");
		} else {
			dsObj.setColumn(nRow, flagColNm, "D");
		}	
	}
}
/******************************************************
*  Grid 에서 컬럼 값 변경 시 Flag 처리
* @param  dsObj 처리할 DataSet 객체
* @param  strColumnID  변경된 컬럼 명
* @param  nRow  해당 Row 번지
* @param  flagColNm  flag 컬럼 명 ( 필수 아님 ) :  Default ( "FLAG")
* @return  없음
******************************************************/
function gfn_changeFlag( dsObj, strColumnID, nRow, flagColNm ) {
	if ( flagColNm == null ) {
		flagColNm = "FLAG";
	}

	if ( strColumnID == flagColNm ) {
		return;
	}
	
	// flag 변경 시켜서 처리 
	dsObj.setColumn(nRow, flagColNm, null);	
	dsObj.fireevent = false;
	var status = dsObj.getRowType(nRow);
	var flag = null;
	if ( status == "insert" || dsObj.getColumn(nRow, flagColNm) == "I" )  {
		flag = "I";
	} else if ( status == "update") {
		flag = "U";
	}
	
	dsObj.setColumn(nRow, flagColNm, flag);

	dsObj.fireevent = true;
}
/******************************************************
*  Grid 에서 컬럼 값 변경 시 해당 Flag에 대한 Bk 이미지 변경
*  BKImageID 속성에 : gfn_getFlagBkImageID(getColumn(currow, "FLAG")) 로 정의
* @param  flagValue 해당하는 Flag 처리 값
* @return  플래그에 해당하는 bkImageID
******************************************************/
function gfn_getFlagBkImageID( flagValue ) {
	var bkImg = "";
	switch( flagValue ) {
		case "I":
			bkImg = "icon_grid_add";
			break;
		case "U":
			bkImg = "icon_grid_modify";
			break;
		case "D":
			bkImg = "icon_grid_del";
			break;
	}
	return bkImg;
}
/******************************************************
*  WebService 처리시 발생한 에러 정보 표시
* @param  grdObj 처리 그리드 객체
* @param  dsObj 데이터셋 객체
* @param  errDsObj 에러 정보 데이터셋 객체
* @param  flagColNm 플래그 컬럼명
* @return
******************************************************/
function gfn_openWSErrorWin(grdObj, dsObj, errDsObj, flagColNm) {
	var grdNm = "";
	
	if ( type( grdObj ) == "STRING") {
		grdNm = grdObj;
	} else {
		grdNm = grdObj.id;
	}
	
	var dsNm = dsObj.id;
	var errDsNm = errDsObj.id;

	var arg = "fa_flagNm=" + quote( flagColNm);
		arg += " fa_grdName=" + quote(grdNm);
		arg += " fa_dsName=" + quote( dsNm);
		arg += " fa_errDsName=" + quote( errDsNm );

	gfn_openPopupUrl("WB01::CommWSErrorForm.xml", true, arg);
}
/******************************************************
*  WebService 저장 후 메시지 표시
* @param  grdObj 처리 그리드 객체 또는 객체 스트링 
* @param  errDsObj 에러 정보 데이터셋 객체
* @param  flagColNm 플래그 컬럼명
* @param  errCode 처리 에러 코드
* @param  errMsg 처리 에러 메시지
* @return
******************************************************/
function gfn_showWSSaveMsgForQM(grdObj, errDsObj, flagColNm, errCode, errMsg) {
	if ( errCode != -100 && errCode != 0 ) {
		// 처리 실패 : 별도의 에러
		return false;
	}

	var grdNm = "";

	if ( type( grdObj ) == "STRING") {
		grdNm = grdObj;
	} else {
		grdNm = grdObj.id;
	}
	
	if ( errMsg == "INVALID_RQDATE" ) {
		// 현재일자 이전 설정
		// 생길일 없음.
	} else if ( errMsg == "INVALID_DATA" ) {
		// 구매문서번호,품목번호,검사요청일을 확인하세요.
		gfn_showMsg("CW00016");
	} else if ( errDsObj.rowcount() > 0 ) {
		gfn_showMsg("CE00001");
		// error 정보 표시
		gfn_openWSErrorWin(grdNm, object(object(grdNm).bindDataset), errDsObj, flagColNm);
	} else {
		gfn_showMsg("CI00003");
	}

	return true;
}
/******************************************************
*  폼에서 해당 객체 찾기
* @param  objName  찾을 객체 명
* @param  formObj  찾을 대상
* @return 존재 유무
******************************************************/
function gfn_findObj(objName, formObj) {
	if ( formObj == null ) {
		formObj = this;
	}

	var isExist = false;
	for ( var i = 0; i < formObj.Components.count; i++ ) {
		if( formObj.Components[i].id  == objName) {
			isExist = true;
			break;
		}
	}

	return isExist;
}
/******************************************************
*  내부 데이터셋 변경 이벤트
******************************************************/
function _fn_ds_col_OnColumnChanged(obj,nRow,strColumnID,varOldValue,varNewValue,nPivotIndex)
{
	if ( strColumnID != "CHK" ) {
		return;
	}

	var grdObj = object(obj.getcolumn(nRow, "GRD_NM"));

	var arr = split( obj.getColumn(nRow, "COL_ID"), ",", true);
	var wArr = split( obj.getColumn(nRow, "ORG_W"), ",", true);
	var isShow = varNewValue;
	for( var i = 0; i < arr.length(); i++) {
		if ( isShow == true ) {
			// 보여주기
			grdObj.setColProp( toNumber(arr[i]), "width", toNumber(wArr[i]));
		} else {
			grdObj.setColProp(toNumber(arr[i]), "width", 0);
		}
	}
}
/******************************************************
*  객체 enable / disable 처리
* @param  obj  처리할 객체
* @param  isEnable  true - enable / false - disable
******************************************************/
function gfn_enable(obj, isEnable) {
	switch( obj.getType() ) {
		case "Calendar":
		case "Checkbox":
			obj.enable = isEnable;
			break;
		default:
			obj.readOnly = !isEnable;
			obj.style = iif( obj.readOnly, "sty_ipt_form_ind", "sty_ipt_form");
			break;
	}
}
/******************************************************
* 주어진 글자를 len 만큼 잘라서 배열로 return
* @param  str  주어진 글자
* @param  len  자리수
******************************************************/
function gfn_substrToArray(str, len) {
	var rtn = array();
	var temp = "";
	var tCnt = 0;
	var cnt = 0;
	for( var i = 0; i < length( str); i++ ) {
		// 한글이면 더하면 크다 .
		if ( lengthb(charAt( str, i)) == 2 && (tCnt+2) > len ) {
			rtn[cnt++] = temp;
			temp = charAt( str, i);
			tCnt = 2;
			continue;
		} else if ( tCnt == len ) {
			rtn[cnt++] = temp;
			temp = charAt( str, i);
			tCnt = lengthb(charAt( str, i));
			continue;
		}

		temp += charAt( str, i);
		tCnt += lengthb(charAt( str, i));
	}

	if ( length( temp ) > 0 ) {
		rtn[cnt++] = temp;
	}

	return rtn;
}
/******************************************************
* 주어진 일자로 부터 몇일 이전/이후의 working date 조회 
* @param  nDayCnt 조회할 몇일 이전 / 이후 일수 
* @param  sWerks  플랜트 
* @param  sFromDate 조회 기준일자 : 옵션 - 없을 경우 현재일자 기준 
* @return 주어진 일자로부터의 working date
******************************************************/
var _WORKING_DATE = "";
function gfn_getWorkingDate(nDayCnt, sWerks, sFromDate) {
	if ( length( sFromDate ) == 0 ) {
		sFromDate = gfn_getCurrDate();
	}
	
	if ( nDayCnt == 0 || length( sWerks) == 0 ) {
		return "";
	}
	
	sFromDate = replace(sFromDate, ".", "");
	
	var nRow = gds_workingday.searchRow("DAYS== " + quote(nDayCnt) 
		+ " && FROM_DATE==" + quote( sFromDate )
		+ " && WERKS == " + quote( sWerks ) );
	
	if ( nRow >= 0 ) {
		return gds_workingday.getColumn(nRow, "WORKING_DATE");
	}
	
	tit_callService(
        "COMM_ACT"
        , "getWorkingDay"
        , ""
        , ""
        , "DATE_FROM=" + quote(sFromDate) + " DAYS=" 
				+ quote(nDayCnt) +" WERKS=" + quote(sWerks)
        , ""
        , false
        , true
        , true);
    
    nRow = gds_workingday.addRow();    
    gds_workingday.setColumn(nRow, "DAYS", nDayCnt);
    gds_workingday.setColumn(nRow, "FROM_DATE", sFromDate);
    gds_workingday.setColumn(nRow, "WERKS", sWerks);
    gds_workingday.setColumn(nRow, "WORKING_DATE", replace(_WORKING_DATE, "-", ""));
    
	return replace(_WORKING_DATE, "-", "");
}

/******************************************************
* 주어진 프로젝트의 미수금 여부 조회
* @param  nPspid 조회할 프로젝트 번호
* @return 미수 여부
******************************************************/
var _MISU = "";
function gfn_getMisu(nPspid) {
	
	if ( length( nPspid) == 0 ) {
		return "";
	}
	
	
	var nRow = gds_misu.searchRow("I_PSPID== " + quote(nPspid));
	
	if ( nRow >= 0 ) {
		return gds_misu.getColumn(nRow, "E_MISU");
	}
	
	tit_callService(
        "COMM_ACT"
        , "getMiSu"
        , ""
        , ""
        , "I_PSPID=" + quote(nPspid)
        , ""
        , false
        , true
        , true);
// 2010.07.08 미수현장과 일반 현장 모두 저장
//  E_MISU 가 "1"인 경우 미수현장 아닐경우 "0"
		nRow = gds_misu.addRow();    
		gds_misu.setColumn(nRow, "I_PSPID", nPspid);
		gds_misu.setColumn(nRow, "E_MISU", _MISU);
	return _MISU;
}


/******************************************************
* 화면에 해당하는 사용자의 지역 코드 정보 가져오기 
* @return 사용자 그룹 코드 정보 
******************************************************/
function gfn_getUserArea() {
	if ( !isExistVar("F_PGM_ID", true) ) {
		return G_AREA_CODE;
	}

	// 기본     : "Z"      - 보수영업 : "R"      - 설치     : "E"
    //  - 자재     : "M"      - 영업     : "B"
    var pgmId = left(F_PGM_ID, 2);
    var rtn = "";
    switch( pgmId ) {
		case "CS":	// 보수 영업 
			rtn = G_AREA_CODE_R;
			break;
		case "BS":	// 영업 
			rtn = G_AREA_CODE;
			break;
		case "PS":	// 설치 
			rtn = G_AREA_CODE_E;
			break;
		case "MM":	// 자재 
			rtn = G_AREA_CODE;
			break;
    }
    
    if ( length( rtn ) == 0 ) {
		rtn = G_AREA_CODE;
    }

    return rtn;
}

/******************************************************
* 화면에 해당하는 사용자의 그룹 코드 정보 가져오기 
* @return 사용자 그룹 코드 정보 
******************************************************/
function gfn_getUserGroup() {
	if ( !isExistVar("F_PGM_ID", true) ) {
		return substr(G_USER_GROUP, 1);
	}

	// 기본     : "Z"      - 보수영업 : "R"      - 설치     : "E"
    //  - 자재     : "M"      - 영업     : "B"
    var pgmId = left(F_PGM_ID, 2);
    var rtn = "";
    switch( pgmId ) {
		case "CS":	// 보수 영업 
			rtn = right(G_USER_GROUP_R, 2 );
			break;
		case "BS":	// 영업 
			rtn = right(G_USER_GROUP_B, 2 );
			break;
		case "PS":	// 설치 
			rtn = right(G_USER_GROUP_E, 2 );
			break;
		case "MM":	// 자재 
			rtn = right(G_USER_GROUP_M, 2 );
			break;
    }
    
    if ( length( rtn ) == 0 ) {
		rtn = right(G_USER_GROUP, 2 );
    }

    return rtn;
}

/******************************************************
* 화면에 해당하는 사용자의 원그룹 코드 정보 가져오기 
* @return 사용자 그룹 코드 정보 
******************************************************/
function gfn_getOrgUserGroup() {

	var arr = split(G_ORG_USER_INFO, "^", true);

	if (arr != null && arr.length < 12)
	{
		return gfn_getUserGroup();
		break;
	}
	
	var ORG_GROUP = arr[12];
	var ORG_GROUP_B = arr[13];
	var ORG_GROUP_E = arr[14];
	var ORG_GROUP_M = arr[15];
	var ORG_GROUP_R = arr[16];

	if ( !isExistVar("F_PGM_ID", true) ) {
		return substr(ORG_GROUP, 1);
	}

	// 기본     : "Z"      - 보수영업 : "R"      - 설치     : "E"
    //  - 자재     : "M"      - 영업     : "B"
    var pgmId = left(F_PGM_ID, 2);
    var rtn = "";
    switch( pgmId ) {
		case "CS":	// 보수 영업 
			rtn = right(ORG_GROUP_R, 2 );
			break;
		case "BS":	// 영업 
			rtn = right(ORG_GROUP_B, 2 );
			break;
		case "PS":	// 설치 
			rtn = right(ORG_GROUP_E, 2 );
			break;
		case "MM":	// 자재 
			rtn = right(ORG_GROUP_M, 2 );
			break;
    }
    
    if ( length( rtn ) == 0 ) {
		rtn = right(ORG_GROUP, 2 );
    }

    return rtn;
}

/******************************************************
* 화면에 해당하는 사용자의 업체 코드 정보 가져오기 
* @return 사용자 업체 코드 정보 
******************************************************/
function gfn_getUserVend() {
	if(!isExistVar("F_PGM_ID", true)) {
		return G_VEND_CODE;
	}

	// 기본 : "Z", 보수 : "R", 영업 : "B", 자재 : "M" , 설치 : "E"
    var pgmId = left(F_PGM_ID, 2);
    var rtn = "";
    switch(pgmId) {
		case "CS":	// 보수 
			rtn = G_VEND_CODE_R;
			break;
		case "BS":	// 영업 
			rtn = G_VEND_CODE_B;
			break;
		case "MM":	// 자재 
			rtn = G_VEND_CODE_M;
			break;
		case "PS":	// 설치 
			rtn = G_VEND_CODE_E;
			break;
    }

    if(length(rtn) == 0) {
		rtn = G_VEND_CODE;
    }

    return rtn;
}

/******************************************************
* 화면에 해당하는 사용자의 업체 코드 정보 가져오기 
* @param str  업무 구분 코드 - R : 보수, B : 영업, M : 자재, E : 설치
* @return 사용자 업체 코드 정보 
******************************************************/
function gfn_getUserVend(str) {
    var rtn = "";

    if(length(str) == 0) {
		rtn = G_VEND_CODE;
    }

    switch(str) {
		case "R":	// 보수 
			rtn = G_VEND_CODE_R;
			break;
		case "B":	// 영업 
			rtn = G_VEND_CODE_B;
			break;
		case "M":	// 자재 
			rtn = G_VEND_CODE_M;
			break;
		case "E":	// 설치 
			rtn = G_VEND_CODE_E;
			break;
    }

    return rtn;
}

/******************************************************
* 파일 업로드 창 관리 
* @param sDir 서버에 저장할 디렉토리 
* @param sFileName 서버에 저장되어 있는 파일 명 
* @param sLocalFileDir 로컬에서 선택할 파일 디렉토리 
* @param fileType - 선택 가능한 파일 타입 ( VIEW, EXCEL, IMG , ALL ( 기본 ))
         - 조회만 해야 할 경우 "VIEW" 로 입력 
* @param saveType 파일저장 타입 
* @param isDirectSave 오픈한 창에서 바로 저장할지 유무 
* @param saveFileName isDirectSave가 true일 경우 파일명을변경하고자 할 경우 저장할
         파일명 
* @param  isCanSave 서버에 저장되어 있던 파일 로컬 파일로 저장 가능 유무    
* @param  maxFileSize  선택 가능한 최대 사이즈  
* @return 선택한 파일 정보: null일 경우 변경한 정보 없음. 
          배열로 [0] - 파일 디렉토리 
                 [1] - 파일명 
******************************************************/
function gfn_openFileMgr(
	sDir
	, sFileName
	, sLocalFileDir
	, fileType
	, saveType
	, isDirectSave
	, saveFileName
	, isCanSave
	, maxFileSize ) {
	if ( length( fileType ) == 0 ) {
		fileType = "IMG";
	}
	
	if ( isDirectSave == null ) {
		isDirectSave = false;
	}
	
	if ( isCanSave == null ) {
		isCanSave = false;
	}
	
	var sArg = "fa_serverDir=" + quote(sDir)
		+ " fa_serverFileName=" + quote( sFileName )
		+ " fa_localFileDir=" + quote( sLocalFileDir )
		+ " fa_saveFileName=" + quote( saveFileName )
		+ " fa_saveType=" + quote( saveType )
		+ " fa_fileType=" + quote( fileType )
		+ " fa_isSave=" + quote( iif( isCanSave, 'Y', 'N') )
		+ " fa_isServerSave=" + quote( iif( isDirectSave, 'Y', 'N') )
		+ " fa_maxFileSize=" + quote( maxFileSize );

	return gfn_openPopupUrl("WB01::CommFileMgrForm.xml", true, sArg);
}
/****************************************************************
* 파일의 확장자 가져오기 
* @param sFileName 파일명 
* @return 파일확장자 ( . 제외 )
******************************************************************/
function gfn_getFileExt(sFileName) {
	var totLen = length(sFileName);
    var dotIdx = -1;
    for ( i = totLen; i >= 0; i--) {
        if ( charAt(sFileName, i) == ".") {
			// 확장자 
            dotIdx = i;
            break;
        }
    }
    
    if ( dotIdx > 0 ) {
		return substr(sFileName, dotIdx+1);
    }
    
    return "";
}
/*===============================================================================
*   tit_comm_0001의 공통 함수
================================================================================*/
/****************************************************************
* 조회 중 입니다 메시지 보여주기
* 내부적으로 Division 을 생성하여 해당 정보를 보여준다.
* @param strMsg 보여줄 메시지
* @param topObj 위치할 최상위 객체
* @param errMsg  에러 메시지
* @return 없음
******************************************************************/
var _f_cnt = 0;
function fn_createWait(strMsg, topObj) {
    _f_cnt++;
    
	if ( gfn_findObj("_Div_wait", this) == false ) { //find("_Div_wait") == null ) {
		var w = 320;
		var h = 96;
		var nLeft = (this.width /2) - (w/2);
		var nTop = (this.Height /2) - h + 20;

        // 생성하기
        // 2012.11.13 영문 메시지 적용
        if (G_LANG == 'ko')
        {
			Create("Div", "_Div_wait", "left='" + nLeft + "' border='none' top='"
				+ nTop + "' width='" + w + "' height='" + h + "' url='WB01::Progressbar.xml' syncContents='true'");
		}
		else
		{
			Create("Div", "_Div_wait", "left='" + nLeft + "' border='none' top='"
				+ nTop + "' width='" + w + "' height='" + h + "' url='COM::ProgressbarE.xml' syncContents='true'");
		}
    } else {
    	_Div_wait.visible = true;
    }
}
/*****************************************************************
* 조회 중 입니다 메시지 창 삭제하기
* @return 없음
******************************************************************/
function fn_destroyWait(){
    if ( gfn_findObj("_Div_wait", this) ) { //find("_Div_wait") != null ) {
        _f_cnt--;
        if ( _f_cnt <= 0 ) {
			SetWaitCursor(false);
			ReleaseCapture();

        //    Destroy("_Div_wait");
			_Div_wait.visible = false;
        }
    } else {
		SetWaitCursor(false);
		ReleaseCapture();
    }
}
/******************************************************
 * 서버에 전송할 Global 변수 정의
******************************************************/
function fn_getGlobalVariable() {
	var arg = "G_ORG_VEND_CODE=" + quote(G_ORG_VEND_CODE);
	arg += " G_ORG_VEND_CODE_B=" + quote(G_ORG_VEND_CODE_B);
	arg += " G_ORG_VEND_CODE_E=" + quote(G_ORG_VEND_CODE_E);
	arg += " G_ORG_VEND_CODE_M=" + quote(G_ORG_VEND_CODE_M);
	arg += " G_ORG_VEND_CODE_R=" + quote(G_ORG_VEND_CODE_R);
	arg += " G_LANG=" + quote(G_LANG);
	arg += " G_USER_GROUP=" + quote(G_USER_GROUP);
	arg += " G_USER_GROUP_B=" + quote(G_USER_GROUP_B);
	arg += " G_USER_GROUP_E=" + quote(G_USER_GROUP_E);
	arg += " G_USER_GROUP_M=" + quote(G_USER_GROUP_M);
	arg += " G_USER_GROUP_R=" + quote(G_USER_GROUP_R);
	arg += " G_USER_ID=" + quote(G_USER_ID);
	arg += " G_USER_NAME=" + quote(G_USER_NAME);
	arg += " G_VEND_CODE=" + quote(G_VEND_CODE);
	arg += " G_VEND_CODE_R=" + quote(G_VEND_CODE_R);
	arg += " G_VEND_CODE_B=" + quote(G_VEND_CODE_B);
	arg += " G_VEND_CODE_M=" + quote(G_VEND_CODE_M);
	arg += " G_VEND_CODE_E=" + quote(G_VEND_CODE_E);
	arg += " G_VEND_WGBN=" + quote(G_VEND_WGBN);
	arg += " G_SYSID=" + quote(G_SYSID);
	arg += " G_MANDT=" + quote(G_MANDT);
	arg += " G_LGORT=" + quote(G_LGORT);
	arg += " G_AREA_CODE=" + quote(G_AREA_CODE);
	arg += " G_AREA_CODE_E=" + quote(G_AREA_CODE_E);
	arg += " G_AREA_CODE_R=" + quote(G_AREA_CODE_R);
	arg += " G_SAP_USER_ID=" + quote(G_SAP_USER_ID);
	arg += " G_SAP_USER_NAME=" + quote(G_SAP_USER_NAME);
	arg += " G_SAP_USER_VBCD=" + quote(G_SAP_USER_VBCD);
	arg += " G_SAP_USER_VBNM=" + quote(G_SAP_USER_VBNM);
	arg += " G_SAP_USER_VGCD=" + quote(G_SAP_USER_VGCD);
	arg += " G_SAP_USER_VGNM=" + quote(G_SAP_USER_VGNM);
	arg += " G_USERTELE=" + quote(G_USERTELE);
	arg += " G_USERMBPN=" + quote(G_USERMBPN);	
	arg += " G_ORG_USER_INFO=" + quote(G_ORG_USER_INFO);	

	return arg;
}
/******************************************************
 * 서버에서 에러 메시지 수신 시 처리
******************************************************/
function fn_showSysError(sMsg, sType) {
	if( G_USER_ID = "" ) {
		// 로그인 전 
		gfn_alert( sMsg );
		return;
	}
	
	if ( sType == "T") {
		// Trace
		gfn_openMsgWin(sMsg, "E");
	} else {
		gfn_openMsgWin(sMsg, "E");
	}
}
/******************************************************
*  로그인 되어 있지 않을 경우
******************************************************/
function fn_notLogin() {
    gfn_showAlert("ZI00004");
    global.FRM_TOP.fn_logout("", "0", "");
}
/**************************************************************************************
*  YYYYMM 문자열을 입력하여 해당 월의 마지막 날자 구하기
* @param  date  ( ex: 20070810 )
* @return  해당 월의 마지막 날자
**************************************************************************************/
function gfn_getLeapLastDay(date) {
	var year,month,day;
	var lastDay = "31";

    if(date.length == 8 ) {
        year  = substr(date,0,4);
        month = substr(date,4,2);
        day   = substr(date,6,2);
    }
    else {
        return false;
    }

    if (year < '1900') return false;
    if (month < '01' || month > '12') return false;
    if (day < '01' || day > '31') return false;

	switch (month) {
        case '02' : if ((ParseInt(year)%4 == 0 && ParseInt(year)%100 != 0) || ParseInt(year)%400 == 0) {
						if (day <= 29) lastDay = 29;
					} else {
						if (day <= 28) lastDay = 28;
					}
					break;
        case '04' :
        case '06' :
        case '09' :
        case '11' : if (day <= 30) lastDay = 30;
    }
	return year+month+lastDay;
}
/******************************************************
*  SAP 시스템 Long text 조회 
* @param  dsObj     결과를 담아놓을 Dataset
* @param  tabObj     조회할 TableObject 명 
* @param  tabColName 조회할 Column명
* @param  tabId      Table Obj ID
* @return  조회된 Long Text
******************************************************/
var _LONGTEXT = "";
function gfn_getLongText(dsObj, tabObj, tabColName, tabId) {

	if ( length( tabObj) == 0 || length( tabColName) == 0 || length( tabId) == 0) {
		return "";
	}
	
	// 저장후 조회시 새로 보여야 함
	// var nRow = dsObj.searchRow("TDOBJ== " + quote(tabObj) 
		// + " && TDNAME ==" + quote( tabColName )
		// + " && TDID == " + quote( tabId ) );
	
	// if ( nRow >= 0 ) {
		// return dsObj.getColumn(nRow, "O_TEXT");
	// }
	
	tit_callService(
        "COMM_ACT"
        , "getLongText"
        , ""
        , ""
        , "TDID=" + quote(tabId) + " TDNAME=" 
				+ quote(tabColName) +" TDOBJECT=" + quote(tabObj)
        , ""
        , false
        , true
        , true);
    
    nRow = dsObj.addRow();    
    dsObj.setColumn(nRow, "TDOBJECT", tabObj);
    dsObj.setColumn(nRow, "TDNAME", tabColName);
    dsObj.setColumn(nRow, "TDID", tabId);
    dsObj.setColumn(nRow, "O_TEXT", _LONGTEXT);
    
	return _LONGTEXT;
}
/******************************************************
*  SAP 시스템 Long text 저장
* @param  dsObj     Longtext를 72byte로 잘라놓은  Dataset
* @param  tabObj     조회할 TableObject 명 
* @param  tabColName 조회할 Column명
* @param  tabId      Table Obj ID
* @return  성공  - true 실패 - false
******************************************************/

var _ETEXT = "";
function gfn_saveLongText(dsObj, txtDesc, tabId, tabColName, tabObj) {
	var len = 0;
	var str = array();
	str = gfn_substrToArray(txtDesc, 72);
	len = length(str);
	dsObj.clearData();
	for(var i=0; i<len; i++) 
	{
		dsObj.addRow();
		dsObj.SetColumn(i, "TEXT", str[i]);
		dsObj.SetColumn(i, "FLAG", "I");
	}
		
	var result = false;
	tit_callService(
        "COMM_ACT"
        , "saveLongText"
        , "ds_list="+ quote(dsObj.id)
        , ""
        , "TDID=" + quote(tabId) + " TDNAME=" 
				+ quote(tabColName) +" TDOBJECT=" + quote(tabObj)
		, ""
        , true
        , true
        , true);
    if (_ETEXT == "")
    {
		result = true;
    }
	return result;
}



/**************************************************************************************
*  숫자를 입력받아"00001" 형태의 Numeric char 작성
* 
* @param  orgNum  처리할 숫자
* @param  Mlen  리턴받을 전체 자리수
**************************************************************************************/
function gfn_fillZero(orgNum, Mlen) {
	if (Mlen == null) {
		Mlen = 0;
		return orgNum;
	}
	
	var toNum = orgNum;
	var len = length(orgNum);
	var size = Mlen - len;
	
	if (Mlen <= 0){
		return orgNum;
	} 
	for (var i = 0; i < size; i++) {
		toNum = "0" + toNum;
	}
	
	return toNum;
}


/**************************************************************************************
*  입력받은 길이 만큼 원하는 문자를 채움
* 
* @param  orgNum  처리할 숫자
* @param  Mlen  리턴받을 전체 자리수
**************************************************************************************/
function gfn_fillChar(orgCha, Mlen, inCha) {
	if (Mlen == null) {
		Mlen = 0;
		return orgCha;
	}
	
	var toCha = orgCha;
	var len = length(orgCha);
	var size = Mlen - len;
	
	if (Mlen <= 0){
		return orgCha;
	} 
	for (var i = 0; i < size; i++) {
		toCha = toCha + inCha;
	}
	
	return toCha;
}


/**************************************************************************************
*  입력받은 숫자를 한글/한문으로 변환
* 
* @param   inNum  처리할 숫자
* @param  gubn  리턴받을 전체 자리수
**************************************************************************************/
function gfn_changeStringNum(inNum, gubn) {

	var bigUnitKr = ["","만","억","조","경"];
	var unitKr    = ["","십","백","천"];
	var numbKr    = ["","일","이","삼","사","오","육","칠","팔","구"];

	var bigUnitCn = ["","萬","億","兆","京"];
	var unitCn    = ["","拾","百","千"];
	var numbCn    = ["","壹","貳","參","四","五","六","七","八","九"];	


	var bigUnitArray = array(5);
	var unitArray    = array(4);
	var hanArray     = array(10);

	if ( gubn == 0 )
	{
		bigUnitArray = bigUnitKr;
		unitArray    = unitKr;
		hanArray     = numbKr;
	}
	else
	{
		bigUnitArray = bigUnitCn;
		unitArray    = unitCn;
		hanArray     = numbCn;
	}
	var orgNum = toString(inNum);

	var idx =  ParseInt(length(orgNum)/4);
	var nam =  ParseInt(length(orgNum)%4);
	if (nam == 0)
	{
		idx--;
		nam = 4;
	}
	var mok    = 0;
	var tmplen = 0;
	var tmpmax = 0;
	var n      = 0;
		
	var han = "";                                                                

	for (var j = idx; j>=0; j--)
	{         
		if (substr(orgNum,mok,4) == ("0000"))
		{
		} 
		else 
		{
			tmplen = nam-mok;
			tmpmax = tmplen-1;
			for (var i=0; i < tmplen; i++) 
			{
				n = ToNumber(substr(orgNum,mok+i,1));
				if (n == 0)
				{
				} 
				else 
				{
					han += hanArray[n];
					han += unitArray[tmpmax-i];
				}
			}
			han += bigUnitArray[j];
			mok  = nam;
			nam += 4;
		}
	}                                                                      

	return han;
}

/**************************************************************************************
*  object의 Full Path 가져오기
* 
* @param   obj
**************************************************************************************/
function fn_path(obj)
{
	var rtn;   	

	if(sPath == "")
	{
		sPath = toString(obj.id);
	}
		
	if(toString(obj) == "[Global]")						// global은 최상위 오브젝트
	{
	   arr = split(sPath,".");

	   for ( var i = (arr.length()-1); i >=0; i -- ) 	// 순서를 역으로 변환
	   {
		  rtn = rtn + arr[i] + ".";
	   }
	   sPath = substr(rtn,0,length(rtn)-1);  			// -1의 의미는 마지막의 .을 지움
	   return ;
	}

	var obj = obj.getForm();							// 컴포넌트의 상위 컴포넌트 정보를 알려줌
	
	sPath = sPath + "." + toString(obj.id); 
	
	fn_path(obj);										// Recursive Call
}

/**************************************************************************************
*  부서 조직의 검색조건을 가져오기
*  return : 조건 String 반환
**************************************************************************************/
function gfn_deptQuery (gbn) {
	var user_grp = gfn_getUserGroup();


	if(user_grp == "10") { // 보수협력사일 경우
		ds_cond.SetColumn(0, "BSU", G_LGORT);
		ds_cond.SetColumn(0, "TEM", '');
		ds_cond.SetColumn(0, "BPM", '');
		ds_cond.SetColumn(0, "ARA", '');
		ds_cond.SetColumn(0, "DPT", '');
	} else if(user_grp == "20") { // 보수PM일 경우
		ds_cond.SetColumn(0, "BSU", trim(div_code.cb_small.value));
		ds_cond.SetColumn(0, "TEM", '');
		ds_cond.SetColumn(0, "BPM", G_USER_ID);
		ds_cond.SetColumn(0, "ARA", '');
		ds_cond.GetColumn(0, "BPM");
		ds_cond.SetColumn(0, "DPT", '');
	} else if(user_grp == "30") { // 지방사무소일 경우
		ds_cond.SetColumn(0, "BSU", trim(div_code.cb_small.value));
		ds_cond.SetColumn(0, "TEM", G_SAP_USER_VGCD);		
		ds_cond.SetColumn(0, "BPM", '');
		ds_cond.SetColumn(0, "ARA", '');
		ds_cond.SetColumn(0, "DPT", '');
	} else if(user_grp == "40") { // 고객지원부일 경우
		ds_cond.SetColumn(0, "BSU", trim(div_code.cb_small.value));
		if(div_code.cb_big.value == '1') { // 협력사 콤보1이 지역일 경우
			ds_cond.SetColumn(0, "DPT", '');
			ds_cond.SetColumn(0, "TEM", '');
			ds_cond.SetColumn(0, "BPM", '');
			ds_cond.SetColumn(0, "ARA", trim(div_code.cb_mid.value));
		} else if(div_code.cb_big.value == '2') { // 협력사 콤보1이 PM일 경우
			ds_cond.SetColumn(0, "DPT", '');
			ds_cond.SetColumn(0, "TEM", '');
			ds_cond.SetColumn(0, "BPM", trim(div_code.cb_mid.value));
			ds_cond.SetColumn(0, "ARA", '');
		} else if(div_code.cb_big.value == '4') { // 협력사 콤보1이 부서일 경우
			ds_cond.SetColumn(0, "DPT", trim(div_code.cb_mid.value));
			ds_cond.SetColumn(0, "TEM", trim(div_code.cb_small.value));
			ds_cond.SetColumn(0, "BSU", '');
			ds_cond.SetColumn(0, "BPM", '');
			ds_cond.SetColumn(0, "ARA", '');
			
		} else { // 협력사 콤보1이 팀일 경우
			ds_cond.SetColumn(0, "DPT", '');
			ds_cond.SetColumn(0, "TEM", trim(div_code.cb_mid.value));
			ds_cond.SetColumn(0, "BPM", '');
			ds_cond.SetColumn(0, "ARA", '');
		}
	}


	
	var q_str="";
	
	var v_ARA, v_BSU, v_BPM, v_TEM, v_DPT;
	
	v_ARA = ds_cond.GetColumn(0,"ARA");
	v_BSU = ds_cond.GetColumn(0,"BSU");
	v_BPM = ds_cond.GetColumn(0,"BPM");
	v_TEM = ds_cond.GetColumn(0,"TEM");
	v_DPT = ds_cond.GetColumn(0,"DPT");
	
//trace(ds_cond.SaveXml());	  
	q_str =  " AND ZC.MANDT='"+G_MANDT+"' 	\n";
	q_str += " AND ZC.LANG = '"+G_LANG+"' 	\n";
	q_str += " AND ZC.MANDT = B010.MANDT  	\n";
	q_str += " AND B010.LGORT > '' 			\n"; 
	q_str += " AND ZC.CODE1 = 'VKGRP'		\n"; 
	
	if(gbn == null) { 
	    //--자회사 무상발주 수정  --> 20210626 --
		//q_str += " AND ZC.DTEXT1 IN ('E900','EB00','EC00','EE00','EF00','EI00','EK00') \n"; 
		q_str += " AND ZC.DTEXT1 IN ('E900','EB00','EC00','EE00','EF00','EI00','EK00') \n"; 
		
	} else if( gbn == "A" ) {       // 유상보수 중도해지 조회 - CS1201002A
		q_str += " AND ZC.DTEXT1 IN ('E900','EB00','EC00','EE00','EF00','EI00','H100','EK00') \n";
	} else if( gbn == "C" ) {       // 유상보수 중도해지 조회 - CS1201002A
		q_str += " AND ZC.DTEXT1 IN ('E900','EB00','EC00','EE00','EF00','EI00','H100','EK00') \n";
	}
	
	//q_str += " AND B010.VKGRP = ZC.CODE2 	\n";
	if ( gbn == "C" ) {
		q_str += " AND (B010.VKGRP = ZC.CODE2 or B010.VKGRP_CO = ZC.CODE2)	\n";
	} else q_str += " AND B010.VKGRP = ZC.CODE2 	\n";
	
	if (v_ARA.length >0) {
		q_str += " AND B010.BSU_ARA='" +v_ARA +"' \n";
	} 
	
	if (v_BSU.length > 0) {
		q_str += " AND B010.LGORT='" +v_BSU +"' \n";
	} 
	
	if (v_BPM.length > 0) {
		q_str += " AND (B010.BSU_PM='" +v_BPM +"' OR B010.BSU_PM_J = '" +v_BPM +"') \n";
	}
	 
	if (v_TEM.length > 0) {
		q_str += " AND ZC.CODE2='" +v_TEM +"' \n";
	}
	 
	if (v_DPT.length > 0) {
		q_str += " AND ZC.CODE2  IN (SELECT CODE2 AS CODE  \n";
		q_str += "					   FROM SAPHEE.ZLCODE \n";
		q_str += "							  WHERE MANDT = '"+G_MANDT+"' \n";
		q_str += "							    AND LANG  = '"+G_KANG+"' \n"; 
		q_str += "							    AND CODE1 = 'VKGRP' \n";
		q_str += "								AND DTEXT1 = '"+v_DPT+"' )\n";
	} 
	return q_str;


	
	//return q_str;
}

/**************************************************************************************
*  분담이행방식 입력시 직영및 협력사별 비율
* 
* @param   cpd	: 계약방식 (A:FM, B: POG)
           abg  : 빌딩구분 (A:아파트,B~: 빌딩)
           bdgbn: 분담이행방식구분 (1:분담이행,0:주계약)
           amt_gbu : 구하고자하는 주체 비율(1:직영금액,3:협력사금액)
**************************************************************************************/
function gfn_bdgbnRate(bdgbn, cpd, abg, amt_gbu)
{
	if (length(bdgbn)   == 0) amt_gbu = 0;
	if (length(amt_gbu) == 0) amt_gbu = 1;
	if (length(cpd)     == 0) cpd = "A";
	if (length(abg)     == 0) abg = "A";

	var reRate = 0;
	if (bdgbn == "1" || bdgbn == "X"){							// 분담이행방식일 경우
		if (cpd == "A" || cpd == "1"){				   		    // FM 계약일 경우
			if (amt_gbu == "1")	reRate = 0.47;
			else                reRate = 0.53;
		} else {												// POG 계약일 경우
			if (abg == "A") {									// 건물일 아파트
				if (amt_gbu == "1")	reRate = 0.2;
				else                reRate = 0.8;
			} else {											// 아파트 외의 건물
				if (amt_gbu == "1")	reRate = 0.3;
				else                reRate = 0.7;
			}
		}
	} else {													// 분담이행이 아닐 경우
		if (amt_gbu == "1") reRate = 1;
	}
	
	return reRate;
}

/**************************************************************************************
* 자회사 결재 정보
* @param 	dsObj1 : 결재 head 정보(ZWBAP01)
            dsObj2 : 결재 detail 정보(ZWBAP02)
            isSync : 동기화여부 
***************************************************************************************/
function gfn_setZwbap(dsObj1, dsObj2, isSync) 
{
	// 그리드 정보 추가
	dsObj1.SetColumn(0, "APSTAT"  , "6");
	dsObj1.SetColumn(0, "CRDPT1"  , G_VEND_CODE);
	dsObj1.SetColumn(0, "CRDPT1_N", G_VEND_NAME);
	dsObj1.SetColumn(0, "CRIRUM"  , G_USER_NAME);
	dsObj1.SetColumn(0, "REDPT1"  , SUBSTR(G_SAP_USER_VBCD,0,2));
	dsObj1.SetColumn(0, "REDPT1_N", "");
	dsObj1.SetColumn(0, "FNAM"    , "");
	dsObj1.SetColumn(0, "FSIZE"   , "0");


	dsObj2.SetColumn(0, "APPGUBN" , "S");
	dsObj2.SetColumn(0, "SEQNO"   , "1");
	dsObj2.SetColumn(0, "SEQSTAT" , "3");
	dsObj2.SetColumn(0, "APDPT1"  , G_ORG_VEND_CODE);
	dsObj2.SetColumn(0, "APDPT1_N", G_VEND_NAME);
	dsObj2.SetColumn(0, "APNUM"   , G_SAP_USER_ID);
	dsObj2.SetColumn(0, "APRNK"   , "");
	dsObj2.SetColumn(0, "APIRUM"  , G_USER_NAME);


	//정보 초기화
	tit_clearActionInfo();
	//insert sql
	tit_addSingleActionInfo("cs04:CS0401001A_I1");	//ZWBAP01
	tit_addSingleActionInfo("cs04:CS0401001A_I2");	//ZWBAP02
	tit_addSingleActionInfo("cs04:CS0401002A_U2");	//ZWBAP02
	//Transaction 처리
	tit_callService(
        ""
        , ""
        , "ds_head="+dsObj1.id + " ds_list="+dsObj2.id + " ds_list_0=" + dsObj2.id
        , ""
        , ""
        , ""
        , false
        , true
        , isSync);
}

/**************************************************************************************
* 협력사/직영의 상위 팀, 부서정보 확인
* @param 	vLgort : 저장위치
            retVk  : return 할 정보 (VKBUR:부서정보,VKGRP:팀정보) : 기본값 VKBUR
***************************************************************************************/
function gfn_getBsu(vLgort, retVk) 
{

	Create("Dataset","gds_cond_bsu");
	Create("Dataset","gds_res_bsu");
	gds_cond_bsu.addColumn("LANG");
	gds_cond_bsu.addColumn("LGORT");
	gds_cond_bsu.addRow();
	
	gds_cond_bsu.setColumn(0, "LANG", G_LANG);
	gds_cond_bsu.setColumn(0, "LGORT", vLgort);

	// 정보 초기화
	tit_clearActionInfo();
	// 조회해야 하는 sql
	tit_addSearchActionInfo("cs19:CS1901002A_S8");
	// Transaction 처리
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_cond_bsu"
        , "gds_res_bsu=ds_bsu"
        , ""
        , ""
        , false
        , true
        , true);
    var vReturn = "";

    if (gds_res_bsu.GetRowCount() > 0){
		if (retVk == "VKGRP" || retVk == "vkgrp"){
			vReturn = gds_res_bsu.GetColumn(0, "VKGRP");
		} else {
			vReturn = gds_res_bsu.GetColumn(0, "VKBUR");
		}
    } 
    Destroy("gds_cond_bsu");
    Destroy("gds_res_bsu");
    
    return vReturn;
}