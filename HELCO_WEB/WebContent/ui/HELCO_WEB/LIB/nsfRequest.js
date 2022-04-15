﻿﻿﻿/**
 * ============================================================================
 * Nsf request plugin
 * 
 * @author		<A Href="mailto:jissfa@naver.com</A>
 * @version		1.0
 * @since		1.0
 * 
 * History
 * 1.0	2009. 1. 20.	jissfa	Initial Version
 * ============================================================================
 */

var G_SRM_SVC = "SVC";
var G_CallBackFuncName;
function nsfRequest(srvId, url, inDsList, outDsList, strParam, CallBackFunc) {
	var svcUrl = G_SRM_SVC + "::" + url;
	var params = "";
	params += fn_getGlobalVariable();	// 글로벌 변수값을 파라미터로 생성
	params += " " + strParam;
	
	Transaction(srvId, svcUrl, inDsList, outDsList, params, CallBackFunc);

}

//nsfRequest With CallBack Parameter
function nsfRequestWCBP(srvId, url, inDsList, outDsList, strParam, CallBackFunc) {
	G_CallBackFuncName = CallBackFunc;;

	var svcUrl = G_SRM_SVC + "::" + url;
	var params = "";
	params += fn_getGlobalVariable();	// 글로벌 변수값을 파라미터로 생성
	params += " " + strParam;
	
	Transaction(srvId, svcUrl, inDsList, outDsList, params, "after_nsfRequestWCBP");
}
function after_nsfRequestWCBP(strSvcID, nErrorCode, strErrorMag) {
	var f = "global.GetTopWindow().div_main." + G_CallBackFuncName + "()";
	eval(f);
}