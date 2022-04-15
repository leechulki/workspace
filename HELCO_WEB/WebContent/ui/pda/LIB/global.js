﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/********************************************************************************
* 기      능   : 부 메뉴 화면 이동 처리 
********************************************************************************/
function GFN_MoveScreen(obj){    
	var objForm = obj.getForm();
	var sPgmId = obj.UserData;

	if(GFN_IsNull(sPgmId)){
		return;
	}
	if(right(sPgmId,1) == "0"){
		sPgmId = "PCC0" + left(sPgmId,1) + "01000";
	}else{
//		sPgmId = "PCC0" + left(sPgmId,2) + "10" + right(sPgmId,2);
		sPgmId = "PCC0" + sPgmId + "001";
	}

	var nRow = gds_auth.findRow("PGM_ID", sPgmId);

	var sPgmUrl = gds_auth.getColumn(nRow, "PGM_URL");
	var sPgmNm = gds_auth.getColumn(nRow, "PGM_NAME");
	var sPgmYn = gds_auth.getColumn(nRow, "POPUP_YN");

	if(sPgmUrl == null || sPgmNm == null){
		GFN_ShowMsg(objForm,"CW00001");
		return;
	}
	
	//화면여부설정
	if(sPgmYn == "N"){
		GV_OLBJUMPNO = GV_NEWJUMPNO;
		GV_NEWJUMPNO = obj.UserData;
	}

	GV_PREVSCREENNO = GV_CURRSCREENNO;
	GV_CURRSCREENNO = sPgmId;
	GV_CURRFORMNAME = sPgmNm;
	objForm.Go(sPgmUrl);
}

/********************************************************************************
* 기      능   : jump 창에서'ok'or Enter 선택시 해당화면으로 이동 
********************************************************************************/
function GFN_Jump(objPgmId){	 
   
	var sPgmId = objPgmId.Text;
	if(GFN_IsNull(sPgmId)){
		return;
	}
/*	
	if(right(sPgmId,1) == "0"){
		sPgmId = "PCC0" + left(sPgmId,1) + "01000";
	}else{
		sPgmId = "PCC0" + left(sPgmId,1) + "010" + right(sPgmId,2);	
	}   
*/ 
	if(right(sPgmId,1) == "0"){
		sPgmId = "PCC0" + left(sPgmId,1) + "01000";
	}else{
		//sPgmId = "PCC0" + left(sPgmId,2) + "10" + right(sPgmId,2);
		sPgmId = "PCC0" + sPgmId + "001";
	}

	var nRow = gds_auth.findRow("PGM_ID", sPgmId);
	var sPgmUrl = gds_auth.getColumn(nRow, "PGM_URL");
	var sPgmNm = gds_auth.getColumn(nRow, "PGM_NAME");
	var sPgmYn = gds_auth.getColumn(nRow, "POPUP_YN");
	
	if(nRow == -1){
		GFN_ShowMsg(objPgmId.getForm(),"CW00001");
		objPgmId.SetFocus();
		objPgmId.SetSel(0,length(objPgmId.Text));
		return;
	}
	
	GV_CURRSCREENNO =sPgmId;
	
	//if(sPgmYn == "Y"){
		GV_OLBJUMPNO = GV_NEWJUMPNO;
		GV_NEWJUMPNO = objPgmId.Text;
	//}
	GV_CURRFORMNAME = sPgmNm;
	objPgmId.getForm().GetForm().GO(sPgmUrl);	
	objPgmId.getForm().Close();
}
/********************************************************************************
* 기      능   : jump button 클릭시 jump 창 띠우기
********************************************************************************/
function GFN_JumpPopup(obj){
	if(AllWindows.count > 1){
		return;
	}
	
	obj.getForm().Dialog("PCC04::Jump.xml","",235,190,"Title=false");
}

/********************************************************************************
* 기      능   : 'UP' button클리시 상위 메뉴 체크 및 화면 이동
********************************************************************************/
function GFN_GoUpMenu(obj){

	if(AllWindows.count > 1){
		return;
	}

	var nRow = gds_auth.findRow("PGM_ID", GV_CURRSCREENNO);
	var sPgmId = gds_auth.getColumn(nRow, "PGM_ID");
	var sPgmUrl = gds_auth.getColumn(nRow, "PGM_URL");
	var sPgmNm = gds_auth.getColumn(nRow, "PGM_NAME");
	var sPgmYn = gds_auth.getColumn(nRow, "POPUP_YN");

	if(sPgmYn == "Y"){
		//sPgmUrl = gds_auth.getColumn(0, "PGM_URL");
		//sPgmNm = gds_auth.getColumn(0, "PGM_NAME");
		GV_CURRSCREENNO = "000";
		sPgmUrl ="PCC04::Main.xml";
	}else{
		sPgmId = left(sPgmId,5) + "01000";
		for(var i=nRow; i >= 0 ; i--){
			if( gds_auth.getColumn(i, "PGM_ID") == sPgmId ){
				sPgmNm = gds_auth.getColumn(i, "PGM_NAME");
				sPgmUrl = gds_auth.getColumn(i, "PGM_URL");
				GV_CURRSCREENNO = gds_auth.getColumn(i, "PGM_ID");
				break;
			}
		}
	}

	if( length(trim(sPgmUrl)) == 0 ){
		return;
	}
	GV_PREVSCREENNO = GV_CURRSCREENNO;
	GV_CURRFORMNAME = sPgmNm;
	obj.getForm().Go(sPgmUrl);
}


function GFN_TrimAll(str){
	var ret = "";

	for ( var i=0; i < length(str);i++){
		if(CharAt(str, i) <> ' '){
			ret += CharAt(str, i);
		}
	}

	return ret;
}

/********************************************************************************
* 기      능   : 입력 상자  title 클릭시 ime창 popup 처리
********************************************************************************/
function GFN_ImePopUp(obj){
	obj.getForm().Dialog("base::ime.xml","selectedObj=" + obj.id,190,172);
	var sourceObj = obj.getForm().Object(obj.userData);
	
	sourceObj.SetFocus();
	sourceObj.SetSel(0,length(sourceObj.Value));

}

function GFN_CompareValueUsingAllTrim(param1, param2){
	if( trimAll(param1) == trimAll(param2) ){
		return true;
	}else{
		return false;
	}
}

function GFN_CompareLocNo(param1, param2){
	var LocNoFull;
	var LocNoCut;

	if( length(trim(param1)) == 0 || length(trim(param2)) == 0 ){
		return false;
	}
	if( length(trim(param1)) > 10 || length(trim(param2)) > 10 ){
		return false;
	}
	if(length(trim(param1)) == 10){
		LocNoFull = trim(param1);
		LocNoCut = trim(param2);
	}else if(length(trim(param2)) == 10){
		LocNoFull = trim(param2);
		LocNoCut = trim(param1);
	}

	if(trim(LocNoFull) == trim(LocNoCut)){
		return true;
	}

	if(length(trim(LocNoFull)) == 10 && length(trim(LocNoCut)) == 10 && trim(LocNoFull) <> trim(LocNoCut)){
		return false;
	}

	if( SubStr(LocNoFull,0,length(LocNoFull) -1 ) == LocNoCut ){
		return true;
	}
	return false;
}

/********************************************************************************
* 기      능   : success or fail시 wav파일(소리)로 알려주는 기능
********************************************************************************/
function GFN_PlayWave(errorCode){
	if(toNumber(errorCode) >= 0){
		//success
		Play("/dingdong.wav");
	}else{
		//fail
		Play("/error.wav");
	}
}

/********************************************************************************
* 기      능   : Focus이동시 입력된 내용 전체 선택 된상태로 Focus이동
********************************************************************************/
function GFN_SetFocusAndCaret(obj){
	obj.SetFocus();
	obj.SetSel(0,length(obj.Text));
}

/********************************************************************************
* 기      능   : EXIT시 확인 popup 처리
********************************************************************************/
function GFN_CheckExit(){
	if(confirm("종료하시겠습니까?")){
//		http.DisConnect();
//		ExecShell("\\HELCO\\HDELGPSMKill.exe");
		return true;
	}else{
		return false;
	}
}
/****************************************************************
* 메시지 코드를 받아서 Message Bar 영역에 메시지 표시
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
***************************************************************/
function GFN_ShowMsg(objForm, msgCode, arr ) {
	var txtColor = "#E8F3ED";
	var msg = GFN_GetMsg(msgCode, arr, true);
	if ( msg == null ) {
		msg = msgCode;
	} 

	if(objform != null && objform.id.indexOf("Popup") == -1){
		objform.st_Message.text = msg;
	}else{
		if(objform != null){
			objform.alert(msg);
		}
	}
}
/****************************************************************
* 메시지 코드를 받아서 Message Bar 영역에 메시지 표시
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
***************************************************************/
function GFN_ShowAlert(objForm, msgCode, arr ) {
	var txtColor = "#E8F3ED";
	var msg = GFN_GetMsg(msgCode, arr, true);
	if ( msg == null ) {
		msg = msgCode;
	} 
	objform.alert(msg);
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
function GFN_GetMsg(msgCode, arr, isNotMakeLine) {
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
function GFN_OpenPopupUrl(objForm,url, isModal, arg, w, h, x , y) {
	var leftX = x;
	var topY = y;

    if ( leftX == null || toNumber(leftX) <= 1) {
        leftX = -1;
    }

    if ( topY == null || toNumber(topY) <= 1 ) {
        topY = -1;
    }

	if ( isModal ) {
		return objForm.Dialog(url, arg, 1, 1, "Scroll=true TitleBar=false StatusBar=false", leftX, topY);
	} else {
		objForm.open(url, arg, 1, 1, "TitleBar=false StatusBar=false TaskBar=true", leftX, topY);
	}
}
/*===============================================================================
*   입력 데이터 정합성 체크 함수
================================================================================*/
/****************************************************************
* FUNCTION NAME     : gfn_isNull
* FUNCTION DESC 	: Null 
* @param 
			
*	strValue : value
* @return : value값이있으면 false, value값이 없으면 true
*	true/false
*****************************************************************/
function GFN_IsNull(strValue){
	if (strValue == null || length(trim(strValue)) == 0){
		return true;
	}else{
		return false;
	}
}

function GFN_OnDeActive(){
	if(Confirm("종료하시겠습니까?")){
		exit();
	}
	return true;
}

function GFN_InitForm(objForm){
	objForm.title = "현대엘리베이터";

	if(right(GV_CURRSCREENNO,3) == "000") {
		objForm.st_HeadCaption.Text = GV_CURRFORMNAME;
	} else {
		objForm.st_HeadCaption.Text = GV_CURRFORMNAME + " <" + GV_NEWJUMPNO + ">";
	}
	
	if(GFN_FindObj("ds_combo", objForm)){
		objForm.gfn_GetCommCodes();
	}
}

/******************************************************
*  폼에서 해당 객체 찾기
* @param  objName  찾을 객체 명
* @param  formObj  찾을 대상
* @return 존재 유무
******************************************************/
function GFN_FindObj(objName, formObj) {
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
function GFN_Chk_AccPass(Obj)
{
	if ( Length(Obj.text) != 4 )
	{
		alert("통장 비밀번호는 4자리 입니다.");
		Obj.SetFocus();
		return false;
	}
	return true;
}


function GFN_Chk_Date_Useable( DateObj, nLen )
{
    var Year;
    var nMonth;
    var Day;
    var lM;

    if( DateObj.value.length() == 0){
        alert ("날짜(숫자)를 정확히 입력하여 주십시오.") ;
		DateObj.SetFocus();
		DateObj.value = "";
        return false;
	}

    // 숫자여부 및 길이 확인
    if(DateObj.value.length() < nLen ){
        alert ("날짜(숫자)를 정확히 입력하여 주십시오.") ;
		DateObj.SetFocus();
		DateObj.value = ""; 
        return false ;
    }

	if(nLen == 8){ 
		// 년도확인  1900 < Year < 2010
		Year = parseInt(DateObj.value.mid(0,4)); 
		if(Year <= 1900 || Year >= 2100){
			alert ("조회 년도를 확인하여 주십시오. (1900 - 2100)") ;
			DateObj.SetFocus();
			DateObj.value = "";
			return false ;
		}
		lM = 4;
	}
	else if(nLen == 4){
		var Today = today();
		Year = Today.mid(0,4);
		lM=0;
	}
	else {
        alert ("년월일을 정확히 입력하여 주십시오. (YYYYMMDD)") ;
		DateObj.SetFocus();
		DateObj.value = "";
		return false;
	}

    // 월확인    1 <= Month <= 12
    nMonth = parseInt(DateObj.value.mid(lM,2));
    if((nMonth <= 0 ) || (nMonth >= 13)){
        alert ("조회 월을 확인하여 주십시오. (1 - 12)") ;
        DateObj.SetFocus();
		DateObj.value = "";
        return false ;
    }

	var total_days;
	if(nMonth == 1) total_days = 31;
	else if(nMonth == 2) {
		if(((Year % 4 == 0) && (Year % 100 != 0)) || (Year % 400 == 0))
			 total_days = 29;
        else total_days = 28;
    }
	else if(nMonth == 3) total_days = 31;
	else if(nMonth == 4) total_days = 30;
	else if(nMonth == 5) total_days = 31;
	else if(nMonth == 6) total_days = 30;
	else if(nMonth == 7) total_days = 31;
	else if(nMonth == 8) total_days = 31;
	else if(nMonth == 9) total_days = 30;
	else if(nMonth == 10) total_days = 31;
	else if(nMonth == 11) total_days = 30;
	else if(nMonth == 12) total_days = 31;

	Day = parseInt(DateObj.value.mid(lM+2));
	if(Day <= 0 || Day > total_days){
	   alert ("일자를 확인하여 주십시오. (1 - 31)");
	   DateObj.SetFocus();
	   DateObj.value = "";
	   return false;
    }
    return true;
}




function GFN_Chk_Jumin(Obj)
{

	var ret1;

	if ( Length(Obj.value) != 10 && Length(Obj.value) != 13 )
	{
		alert("실명번호는 주민등록번호 13자 또는 사업자번호 10자로 입력하여 주십시오.");
		Obj.value = "";
		Obj.SetFocus();
		return false;
	}

	if ( Length(Obj.value) == 13 )
	{

		ret1 = GFN_Chk_resno(Obj.value);
		if ( ret1 == 0 ) {
			Obj.SetFocus();
			Obj.value = "";
			return false;
		}
	}

	return true;

}

function GFN_Chk_resno(j_no)
{
    var num = 0;
    var num7 = 0;
    var num13 = 0;
    var totalnum = 0;
    var chknum = 0;
    num7 = parseInt(j_no.substr(6,1)) ;
    num  = parseInt(j_no.substr(0,1))   * 2 +
          parseInt(j_no.substr(1,1))   * 3 +
          parseInt(j_no.substr(2,1))   * 4 +
          parseInt(j_no.substr(3,1))   * 5 +
          parseInt(j_no.substr(4,1))   * 6 +
          parseInt(j_no.substr(5,1))   * 7 +
          parseInt(j_no.substr(6,1))   * 8 +
          parseInt(j_no.substr(7,1))   * 9 +
          parseInt(j_no.substr(8,1))   * 2 +
          parseInt(j_no.substr(9,1))  * 3 +
          parseInt(j_no.substr(10,1)) * 4 +
          parseInt(j_no.substr(11,1)) * 5;
    num13 = parseInt(j_no.substr(12,1));

    totalnum = num % 11;

    chknum   = 11 - totalnum;

    if ( chknum >= 10 ) chknum = chknum - 10;

    if ( (num13 == chknum) && ( num7 == 1 || num7 == 2 ) )
        return 1;
    else
    {
		if(num7 > 4) return 1;
		else if(j_no.substr(10,3) == "001") return 1;
        alert("주민등록번호를 올바르게 입력하여 주십시오.");
        return 0;
    }
}

function GFN_Chk_Alpa( str )
{
	var v1Len = str.Length();
	var i;
	var ch;

	for ( i = 0 ; i < v1Len ; i = i + 1 )
	{
		ch = str.charAt(i);

		if ( ch >= 'A' && ch <= 'Z' ) return true;
		if ( ch >= 'a' && ch <= 'z' ) return true;
	}
    return false;
}


function GFN_Chk_password(Obj)
{

	var v_len = Length(Obj.text);
    if( v_len == 0 || v_len < 6 || v_len > 8){
		alert("이체 비밀번호는 영숫자 6~8자(영문 1자 이상)로 입력해야 합니다.");
		Obj.SetFocus();
		return false;
	}

	var ret = GFN_Chk_Alpa(Obj.text);
	if( !ret ){
		alert("이체 비밀번호는 반드시 영문자 1자 이상이 포함되어야 합니다.");
		Obj.SetFocus();
		return false;
	}
	return true;
}

function GFN_GetGPSData(){
  var gpsData[5];   //0:날짜+ 1:시간 2:X  3:Y   4:수신유무
 	var dateTime;
	gpsData[0] = "";
	gpsData[1] = "";
	gpsData[2] = "";
	gpsData[3] = "";
	dateTime = GetReg("LOCATION_DATE","20060101 010101");
	//                                 01233456 789123  
	gpsData[0] = Mid(dateTime,0,8);
	gpsData[1] = Mid(dateTime,8,6);
	gpsData[2] = GetReg("LOCATION_X","0");;
	gpsData[3] = GetReg("LOCATION_Y","0");
	gpsData[4] = GetReg("LOCATION_STATUS","2");
	return gpsData;
}


/****************************************************************
* 메시지 코드를 받아서 Confirm 처리
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
* @return 확인 - true / 취소 - false
***************************************************************/
function GFN_ShowConfirm( msgCode, arr) {
	var msg = GFN_GetMsg(msgCode, arr);
	if ( msg == null ) {
		msg = msgCode;
	}

	return Confirm(msg);
}

/********************************************************************************
* 기      능   : Http Error
********************************************************************************/
function GFN_Http_OnError(obj,strUrl,nErrorCode,strErrorMsg,nHttp_Code,strLocation){
	// 화면 없음
	if((toNumber(nErrorCode) == -2085613056) || (toNumber(nErrorCode) == -2085605340)){
		var nPos = pos(strErrorMsg,"ui")+3 ;
		var nLPos = pos(strErrorMsg,".xml",nPos);
		var strForm = toUpper(mid(strErrorMsg,nPos,nLPos-nPos));

		var strPreFix = left(strForm,pos(strForm,"\\",5));
		var strXML = mid(strForm,pos(strForm,"\\",5) + 1,20);
	
		if(strPreFix.length() == 0){
			alert("","화면 또는 공통함수 open error.\n" + strErrorMsg);
		}else{
			alert("", "찾을 수없는 화면입니다.\nPrefix:" + strPreFix + "\nXML:" + strXML);
		}
	}
	// Session TimeOut
	if(nHttp_Code == 302){
		alert("Session TimeOut");
		GV_EXITFLAG = "SESSIONOUT";
		Exit();
	}
	GV_HTTPCODE = nErrorCode + chr(30) + strErrorMsg + chr(30) + strUrl;
}
/********************************************************************************
* 기      능   : Global OnError
********************************************************************************/
function GFN_OnError(obj,nErrorCode,strErrorMsg,strID){
	// ESC SERVICE DISCONNECT 처리 안함
	if(toNumber(nErrorCode) == -2085612799) {
		GV_DIV_FORM._f_cnt--;
		GV_DIV_FORM._Div_wait.visible = false;
		return;
	}else if((toNumber(nErrorCode) == -2085613056) || (toNumber(nErrorCode) == -2085605340)){
		var nPos = pos(strErrorMsg,"ui")+3 ;
		var nLPos = pos(strErrorMsg,".xml",nPos);
		var strForm = toUpper(mid(strErrorMsg,nPos,nLPos-nPos));

		var strPreFix = left(strForm,pos(strForm,"\\",5));
		var strXML = mid(strForm,pos(strForm,"\\",5) + 1,20);
	
		if(strPreFix.length() == 0){
			alert("","화면 또는 공통함수 open error.\n" + strErrorMsg);
		}else{
			alert("", "찾을 수없는 화면입니다.\nPrefix:" + strPreFix + "\nXML:" + strXML);
		}
	}
}

/**************************************************************************************
* 현재일자 구하기
* @return 현재일자 ( YYYYMMDD )
**************************************************************************************/
function gfn_getCurrDate() 
{
	return today();
}

/********************************************************************************
* 기      능   : 전화번호에 MASK 적용하기
********************************************************************************/
function GFN_MaskTel(obj) {
	var ivalue = "";
	var rtn = "";

	ivalue = replace(replace(obj.Text,"-",""),"_","");

	if(length(ivalue) == 11) {
		rtn = "###-####-####";
	} else if(length(ivalue) == 10) {
		if(substr(ivalue,0,2) == "02") {
			rtn = "##-####-####";
		} else {
			rtn = "###-###-####";
		}
	} else if(length(ivalue) == 9) {
		rtn = "##-###-####";
	} else {
		rtn = "###-####-####";
	}

	obj.Mask = rtn;
	obj.Text = ivalue;
}
