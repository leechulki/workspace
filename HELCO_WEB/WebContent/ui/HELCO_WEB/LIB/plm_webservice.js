﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/****************************************************************
* PLM Duty, 종속성, 제한조건 호출
* prgDivs: 견적화면 호출, 계약변경화면 호출: Q:견적, P: 계약변경
* ds_header: 헤더 데이터셑
* ds_template: 영업특성 데이터셑
* opt1: DUTY CALL : "DUTY", 종속성 : "DEPENDENCY", 제한조건 : "CONSTRAINT", 사양전개:FLOOR
* opt2: S: MF: 마이플랫폼 default: JSON
***************************************************************/
//var G_HEP_PLM_URL = "http://203.242.40.208:58001/ElvAutoCalc_SoapService/183";
//var G_HED_PLM_URL = "http://203.242.37.91:58001/ElvAutoCalc_SoapService/183";
//var G_HEP_PLM_URL = "http://203.242.40.208:58001/ElvAutoCalc_SoapService/183";
//var G_HED_PLM_URL = "http://203.242.40.208:58001/ElvAutoCalc_SoapService/183";
var G_HEP_PLM_URL = "http://plmwebsvc.hdel.co.kr/services/ElvAutoCalcSoap";
var G_HED_PLM_URL = "http://plmwebsvc.hdel.co.kr/services/ElvAutoCalcSoap";
var G_SND_MOD = "SRM";   // PLM: 화면 -> PLM, SRM: 화면 -> SRM -> PLM
var G_ERRMSG = "";
var G_STATUS = "";
var G_outputXml = "";
/****************************************************************
* Duty 웹서비스 호출
***************************************************************/
function callPlmWebService(prgDivs, opt1, opt2, ds_header, ds_template, callFunction) {
    var isRst = true;
    if(G_SND_MOD == "PLM") {
        // PLM 직접 Duty Call 호출
        isRst = plmDirectWebService(prgDivs, opt1, opt2, ds_header, ds_template, callFunction);
    } else if(G_SND_MOD == "SRM") {
        // SRM 서버를 거쳐서 PLM Duty Call 호출
        isRst = srmProxyPlmWebService(prgDivs, opt1, opt2, ds_header, ds_template, callFunction);
    }
    return isRst;
}

/****************************************************************
* SRM 웹서비스 호출
***************************************************************/
function srmProxyPlmWebService(prgDivs, opt1, opt2, ds_header, ds_template, callFunction) {
    var rtnData = "";
    var inputXml = "";
    G_ERRMSG = "";
    G_outputXml = "";

	// 오픈젝트생성
	destroy("g_plm_out_ds_xml");
	Create("dataset", "g_plm_out_ds_xml");
	object("g_plm_out_ds_xml").addColumn("ID", "STRING");
	object("g_plm_out_ds_xml").addColumn("XMLData", "STRING");
	object("g_plm_out_ds_xml").ClearData();

    // duty 체크 input 데이터를 생성한다.
    inputXml = createSrmDutySoapXml(opt1, opt2, ds_header, ds_template);
	
	// 동기처리
	http.Sync = true;
	nsfRequest("plmDutySoap"
			, "plmDutySoapC/plmDuty"
			, ""
			, "ds_error=ds_error G_outputXml=G_outputXml"
			, "jsonData="+quote(inputXml)+" opt1="+quote(opt1)+" opt2="+quote(opt2)
			, "fn_srmProxyPlmWebServiceAfter"
			);
	http.Sync = false;
	
    if(G_ERRMSG == "") {
        G_outputXml = ext_Base64Decode(G_outputXml);
        G_outputXml = Replace(G_outputXml, "</root>", "");
	    var xmlList = Split(G_outputXml, "<dataset","webstyle");
	    for(var i=1; i < Length(xmlList); i++) {
	        startIndex = IndexOf(xmlList[i], 'id="')+4;
            endIndex   = IndexOf(xmlList[i], '">') - startIndex;
	        id = SUBSTR(xmlList[i], startIndex, endIndex);
	        newRow = object("g_plm_out_ds_xml").AddRow();
	        object("g_plm_out_ds_xml").SetColumn(newRow, "ID", id);
	        object("g_plm_out_ds_xml").SetColumn(newRow, "XMLData", ext_Base64Encode("<dataset"+xmlList[i]));
	    }
    }

    // trace("g_plm_out_ds_xml:"+object("g_plm_out_ds_xml").SaveXML());
    // call Back 메시지 처리
	var f = callFunction + "()";
	return eval(f);
}

/****************************************************************
* SRM 웹서비스 호출
***************************************************************/
function fn_srmProxyPlmWebServiceAfter(svcId, errCode, errMsg) {
    var status;
	if(ds_error.rowcount > 0) {
		G_ERRMSG = ds_error.GetColumn(0,"ERRMSG");
		if(IndexOf(G_ERRMSG, "400") > -1) {
			G_ERRMSG = "에러코드:400";
			G_STATUS = "400";
			gfn_showAlert(G_ERRMSG+", 서버가 이해할수 없는 요청을 하였습니다. 호출 URL을 확인하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else if(IndexOf(G_ERRMSG, "403") > -1) {
			G_ERRMSG = "에러코드:403";
			G_STATUS = "403";
			gfn_showAlert(G_ERRMSG+", 콘텐츠 접근 권한이 없습니다. 호출 URL을 확인하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else if(IndexOf(G_ERRMSG, "404") > -1) {
			G_ERRMSG = "에러코드:404";
			G_STATUS = "404";
			gfn_showAlert(G_ERRMSG+", 요청 콘텐츠가 존재하지 않습니다. 호출 URL을 확인하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else if(IndexOf(G_ERRMSG, "405") > -1) {
			G_ERRMSG = "에러코드:405";
			G_STATUS = "405";
			gfn_showAlert(G_ERRMSG+", 요청 콘텐츠는 식별되었으나 제거되어 사용할 수 없습니다. PLM 시스템 관리자에게 문의하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else if(IndexOf(G_ERRMSG, "407") > -1) {
			G_ERRMSG = "에러코드:407";
			G_STATUS = "407";
			gfn_showAlert(G_ERRMSG+", 프록시에 완료된 인증이 필요합니다. PLM 시스템 관리자에게 문의하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else if(IndexOf(G_ERRMSG, "408") > -1) {
			G_ERRMSG = "에러코드:408";
			G_STATUS = "408";
			gfn_showAlert(G_ERRMSG+", 응답없이 서버 연결이 끊어졌습니다. PLM 시스템 관리자에게 문의하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else if(IndexOf(G_ERRMSG, "500") > -1) {
			G_ERRMSG = "에러코드:500";
			G_STATUS = "500";
			gfn_showAlert(G_ERRMSG+", 서버 내부 오류가 발생하였습니다. PLM 시스템 관리자에게 문의하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else if(IndexOf(G_ERRMSG, "501") > -1) {
			G_ERRMSG = "에러코드:501";
			G_STATUS = "501";
			gfn_showAlert(G_ERRMSG+", 서버에서 지원하지 않는 요청입니다. 호출 URL을 확인하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else if(IndexOf(G_ERRMSG, "502") > -1) {
			G_ERRMSG = "에러코드:502";
			G_STATUS = "";
		} else if(IndexOf(G_ERRMSG, "504") > -1) {
			G_ERRMSG = "에러코드:504";
			G_STATUS = "504";
			gfn_showAlert(G_ERRMSG+", 서버가 접속이 끊어졌습니다. PLM 시스템 관리자에게 문의하십시요\n"+ds_error.GetColumn(0,"ERRMSG"));
		} else {
		    if(ds_error.GetColumn(0, "ERRCODE") != "OK") {
				gfn_showAlert("PLM 시스템 관리자에게 문의하십시요. 에러메시지:"+G_ERRMSG);
				G_STATUS = "ERROR";
		    } else {
				G_STATUS = "200";
				G_ERRMSG = "";
		    }
		}
		ds_error.clearData();
        fn_destroyWait();
	} else {
        G_STATUS = "200";
	    G_ERRMSG = "";
	}
}

/****************************************************************
* PLM 웹서비스 호출
***************************************************************/
function plmDirectWebService(prgDivs, opt1, opt2, ds_header, ds_template, callFunction) {
    var strUrl = G_HED_PLM_URL;
	var inputXml = "";
	var outputXml = "";
	var startIndex = 0;
	var endIndex   = 0;
    var faultcode = '';
    var errMsg = "";
	var xmlList;
	var id;
	var startIndex = 0;
	var endIndex = 0;
	var newRow = 0;
	G_STATUS = "";

	// 오픈젝트생성
	destroy("g_plm_out_ds_xml");
	Create("dataset", "g_plm_out_ds_xml");
	object("g_plm_out_ds_xml").addColumn("ID", "STRING");
	object("g_plm_out_ds_xml").addColumn("XMLData", "STRING");

    G_ERRMSG = "";
    
    if(G_SYS_ID != 'HEP') {
        strUrl = G_HEP_PLM_URL;
    }

    // 로컬 테스트 진행
    // alert("strUrl:"+strUrl);
    //strUrl = "http://localhost:8080/plmSoap/services/ElvAutoCalcSoap";

/*
    var xhttp = createObject("MSXML2.ServerXMLHTTP");
    if(xhttp == NULL) {
        xhttp = createObject("Microsoft.XMLHTTP");    
    }
*/
    
    var xhttp = createObject("Microsoft.XMLHTTP");
    if(xhttp == NULL) {
        xhttp = createObject("MSXML2.ServerXMLHTTP");    
    }
    
    // duty 체크 input 데이터를 생성한다.
    inputXml = createDutySoapXml(opt1, opt2, ds_header, ds_template);

    xhttp.Open("POST", strUrl, false, "", "");
    // 5분 응답시간 대기 처리 설정    
    xhttp.setTimeouts(0, 300000, 60000, 300000); 
    //xhttp.setTimeouts(0, 60000, 30000, 60000); 
    xhttp.setRequestHeader("content-type", "text/xml;charset=UTF-8");
    xhttp.setRequestHeader("SOAPAction", strUrl);
    xhttp.Send(inputXml);
    
    // 강제종료 처리
    //xhttp.abort();
    if(xhttp.status == '200') {
		startIndex = IndexOf(xhttp.responseText, "<faultcode>") + Length("<faultcode>");
		endIndex = IndexOf(xhttp.responseText, "</faultcode>");
		faultcode = Substr(xhttp.responseText, startIndex, endIndex-startIndex);
		if( faultcode != '') {
		    var faultstring = '';
			startIndex = IndexOf(xhttp.responseText, "<faultstring>") + Length("<faultstring>");
			endIndex = IndexOf(xhttp.responseText, "</faultstring>");
			faultstring = Substr(xhttp.responseText, startIndex, endIndex-startIndex);
			G_ERRMSG = "status:"+xhttp.status+", faultcode:"+faultcode+", 내역:"+faultstring;
			gfn_showAlert("시스템 오류로 관리자에게 문의하십시요.");

			fn_destroyWait();
			return false;
		} else {
			startIndex = IndexOf(xhttp.responseText, "<calcReturn>") + Length("<calcReturn>");
			endIndex = IndexOf(xhttp.responseText, "</calcReturn>");
			outputXml = ext_Base64Decode(Substr(xhttp.responseText, startIndex, endIndex-startIndex));
		}
    } else if(xhttp.status == '400') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 서버가 이해할수 없는 요청을 하였습니다. 호출 URL을 확인하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '403') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 콘텐츠 접근 권한이 없습니다. 호출 URL을 확인하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '404') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 요청 콘텐츠가 존재하지 않습니다. 호출 URL을 확인하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '405') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 요청 콘텐츠는 식별되었으나 제거되어 사용할 수 없습니다. PLM 시스템 관리자에게 문의하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '407') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 프록시에 완료된 인증이 필요합니다. PLM 시스템 관리자에게 문의하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '408') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 응답없이 서버 연결이 끊어졌습니다. PLM 시스템 관리자에게 문의하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '500') {
		startIndex = IndexOf(xhttp.responseText, "<faultcode>") + Length("<faultcode>");
		endIndex = IndexOf(xhttp.responseText, "</faultcode>");
		faultcode = Substr(xhttp.responseText, startIndex, endIndex-startIndex);
		if( faultcode != '') 
		{
		    var faultstring = '';
			startIndex = IndexOf(xhttp.responseText, "<faultstring>") + Length("<faultstring>");
			endIndex = IndexOf(xhttp.responseText, "</faultstring>");
			faultstring = Substr(xhttp.responseText, startIndex, endIndex-startIndex);
			G_ERRMSG = "status:"+xhttp.status+", faultcode:"+faultcode+", 내역:"+faultstring;
			if(Length(faultstring) > 0) {
	            gfn_showAlert(G_ERRMSG+", 서버 내부 오류가 발생하였습니다. PLM 시스템 관리자에게 문의하십시요");
			}
		}
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '501') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 서버에서 지원하지 않는 요청입니다. 호출 URL을 확인하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '502') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 서버가 게이트웨이로 작업하는 동안 오류가 발생하였습니다. PLM 시스템 관리자에게 문의하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '504') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert(G_ERRMSG+", 서버가 접속이 끊어졌습니다. PLM 시스템 관리자에게 문의하십시요");
		fn_destroyWait();
		return false;
    } else if(xhttp.status == '12029') {
        G_ERRMSG = "에러코드:"+xhttp.status;
	    gfn_showAlert("시스템 오류로 PLM 시스템 관리자에게 문의하십시요.");
		fn_destroyWait();
		return false;
    } else {
		startIndex = IndexOf(xhttp.responseText, "<faultcode>") + Length("<faultcode>");
		endIndex = IndexOf(xhttp.responseText, "</faultcode>");
		faultcode = Substr(xhttp.responseText, startIndex, endIndex-startIndex);
		if( faultcode != '') 
		{
		    var faultstring = '';
			startIndex = IndexOf(xhttp.responseText, "<faultstring>") + Length("<faultstring>");
			endIndex = IndexOf(xhttp.responseText, "</faultstring>");
			faultstring = Substr(xhttp.responseText, startIndex, endIndex-startIndex);
			G_ERRMSG = "status:"+xhttp.status+", faultcode:"+faultcode+", 내역:"+faultstring;
			if(Length(faultstring) > 0) {
			    gfn_showAlert("시스템 오류로 관리자에게 문의하십시요.");
			}
			fn_destroyWait();
			return false;
		}
    }

    G_STATUS = xhttp.status;

    //trace("outputXml:\n"+outputXml);
    if(G_ERRMSG == "") {
        outputXml = Replace(outputXml, "</root>", "");
	    xmlList = Split(outputXml, "<dataset","webstyle");
	    for(var i=1; i < Length(xmlList); i++) {
	        startIndex = IndexOf(xmlList[i], 'id="')+4;
            endIndex   = IndexOf(xmlList[i], '">') - startIndex;
	        id = SUBSTR(xmlList[i], startIndex, endIndex);
	        newRow = object("g_plm_out_ds_xml").AddRow();
	        object("g_plm_out_ds_xml").SetColumn(newRow, "ID", id);
	        object("g_plm_out_ds_xml").SetColumn(newRow, "XMLData", ext_Base64Encode("<dataset"+xmlList[i]));
	    }
    }

    // trace("g_plm_out_ds_xml:"+object("g_plm_out_ds_xml").SaveXML());
    // call Back 메시지 처리
	var f = callFunction + "()";
	return eval(f);
}

/****************************************************************
* plm_out_ds_template 결과 리턴
***************************************************************/
function getPlmOutDs(ds_out) {
    var findIndex = 0;
    findIndex = object("g_plm_out_ds_xml").FindRow("ID", ds_out.ID);
    if(findIndex > -1) {
        ds_out.ClearData();
        ds_out.LoadXML(ext_Base64Decode(object("g_plm_out_ds_xml").GetColumn(findIndex, "XMLData"), "EUC-KR"));
        // 조회된 결과 데이터 삭제
        object("g_plm_out_ds_xml").DeleteRow(findIndex);
    } else {
        //if(G_ERRMSG == "") {
	    //    gfn_showAlert("PLM 연동결과 오류로 "+ds_out.ID+" 처리 결과가 없습니다.");
        //}
    }
    return ds_out;
}

/****************************************************************
* PLM Duuty Web Service Soap Xml 메시지를 생성한다.
* ds_header: 헤더 데이터셑
* ds_template: 영업특성 데이터셑
* opt1: DUTY CALL : "DUTY", 종속성 : "DEPENDENCY", 제한조건 : "CONSTRAINT"
***************************************************************/
function createDutySoapXml(opt1, opt2, ds_header, ds_template) {
    var soapMsg = "";
    var inputXml = "";

    inputXml = inputXml + '<?xml version="1.0" encoding="UTF-8"?>\n';
    inputXml = inputXml + '<root>\n<params>\n</params>\n';
    inputXml = inputXml + Trim(getDatasetXml(ds_header)) + "\n"+ Trim(getDatasetXml(ds_template));
    inputXml = inputXml + "</root>\n";
    inputXml = ext_Base64Encode(inputXml, "UTF-8");

    soapMsg = soapMsg + '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:plm="http://plm_sd_soap.plmetc.dyna">';
    soapMsg = soapMsg + '<soapenv:Header/>';
    soapMsg = soapMsg + '<soapenv:Body>';
    soapMsg = soapMsg + '<plm:calc>';
    soapMsg = soapMsg + '<plm:jsonData>'+inputXml+'</plm:jsonData>';
    soapMsg = soapMsg + '<plm:opt1>'+opt1+'</plm:opt1>';
    soapMsg = soapMsg + '<plm:opt2>'+opt2+'</plm:opt2>';
    soapMsg = soapMsg + '</plm:calc>';
    soapMsg = soapMsg + '</soapenv:Body>';
    soapMsg = soapMsg + '</soapenv:Envelope>';

    //trace("soapMsg:\n"+soapMsg);
    return soapMsg;
}

/****************************************************************
* PLM Duuty Web Service Soap Xml 메시지를 생성한다.
* ds_header: 헤더 데이터셑
* ds_template: 영업특성 데이터셑
* opt1: DUTY CALL : "DUTY", 종속성 : "DEPENDENCY", 제한조건 : "CONSTRAINT"
***************************************************************/
function createSrmDutySoapXml(opt1, opt2, ds_header, ds_template) {
    var inputXml = "";
    inputXml = inputXml + '<?xml version="1.0" encoding="UTF-8"?>\n';
    inputXml = inputXml + '<root>\n<params>\n</params>\n';
    inputXml = inputXml + Trim(getDatasetXml(ds_header)) + "\n"+ Trim(getDatasetXml(ds_template));
    inputXml = inputXml + "</root>\n";
    inputXml = ext_Base64Encode(inputXml, "UTF-8");
    return inputXml;
}

/****************************************************************
* data set input Xml 데이터 생성
***************************************************************/
function getDatasetXml(ds_input) 
{
    var dsXml = "";
    var sIndex = 0;
    var eIndex = 0;

    dsXml  = ds_input.SaveXML();
	sIndex = IndexOf(dsXml, "<root>");
	dsXml  = Substr(dsXml, sIndex+6);
	dsXml  = Replace(dsXml, "</root>", "");
	
	return dsXml;
}