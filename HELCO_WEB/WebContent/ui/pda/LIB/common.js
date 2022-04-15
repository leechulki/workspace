﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 공통 Script
 *  작성일자 : 2009/01/07
 *  버전 : 1.0
 *  변경 이력
    일자            작성자          비고
    ------------------------------------------------------------
    2009/01/07     김용호            최초 작성
================================================================================*/
/*===============================================================================
*   상수 선언
================================================================================*/
var errorTrace;
var errorSqlStatus;	// sql status code
var errorSqlCode;
var G_CS_BIZ = "CS_BIZ_XA";	// CS용 비지니스 
var G_HR_BIZ = "HR_BIZ_XA";	// HR용 비지니스 
var G_PRD_BIZ = "PRD_BIZ_XA";	// 운영 비지니스 
var G_XA_ACT = "XA_ACT";	// XA용으로 DB를 2개 이상 사용할 경우 
var GF_DS_CMD = "tit_ds_cmd";
var _CURR_BIZ = "";
var TIT_DEBUG_LEVEL = 0;
var TIT_DEBUG_2 = 2;

// 0 - debug 하지 않음. 1 - 개발자 debug 2 - 해당 스크립트 3 - http 내용 
/****************************************************************
 * 서버 호출
 * @param actionName  서버에서 호출할 Action의 별명 
                      기본은 defaultAction을 호출하도록 되어있다. 
 * @param cmdName  Action 안에서 호출해야 하는 메소드 명 
                   기본은 execute를 호출하도록 되어있다. 
 * @param inData  서버로 전송할 DataSet 객체 Str
                  예: ds_select=dst_select:A ds_select2=dst_select2
 * @param outData  서버로부터 전송 받을  DataSet 객체 String
                   예:  dst_master=ds_servername     
 * @param otherArg  서버로 전송할 기타 Argument 정보 String
                    예: key=value key2=value2
 * @param callBackFnc  서버에서 처리가 완료된 후 Callback 받을 Function 명
                       메소드 작성은 callBackFnc(errCode, errMsg) 형태로 작성 
 * @param isErrorNotCall  에러 발생 시 Callback 함수 호출 여부                           
 * @param isNotShow  처리중입니다. 메시지 창 보여줄지 여부 설정하기   
 * @param isSync  동기식으로 설정할지 여부                          
 * @return 없음
 * 호출 예 : tit_callService(this, "", "actionName, "search", "dst_master=ds_master");
***************************************************************/
function tit_CallService(
        actionName
        , cmdName
        , inData
        , outData
        , otherArg
        , callBackFnc
        , isErrorNotCall 
        , isNotShow
        , isSync) {
        
 	SetWaitCursor(true);
	SetCapture();
	
	if ( isErrorNotCall == null ) {
        isErrorNotCall = false;
	}
	var param = callBackFnc + "&" + isErrorNotCall;
	
	// Argument 처리 
	var arg = "";
	actionName = fn_getActionName(actionName);
	arg += "actionName=" + quote( actionName );

	if ( length( cmdName) == 0 ) {
        cmdName = "execute";
	}
	
	arg += " cmd=" + quote(cmdName);
	
	// global variable 처리 
	arg += " " + fn_getGlobalVariable();
		
	// log id 처리 
	//var clientId = "";//ext_GetIPAddress();
	var clientId = "";//ext_GetIPAddress();

	if(GFN_FindObj("ed_userId", this) == true) {
		clientId = trim(ed_userId.Text);
	} else {
		clientId = trim(GV_USER_ID);
	}

	clientId = replace(clientId, "[" , "");
	clientId = replace(clientId, "]" , "");
	arg += " titLogId=" + quote("SPHONE" + "_" + clientId );
	
	if ( length(otherArg) > 0 ) {
        arg += " " + otherArg;
	}
	
	// Local인 경우 Local service 타도록
	var strDataGroup = "svc_dev";
	if(GV_ISLOCAL != "Y") {
		strDataGroup = "svcWeb";
	}
		
	if ( _GF_CREATE_DS == true ) {
        inData += " ds_cmd=" + GF_DS_CMD;
        outData += " " + GF_DS_CMD + "=ds_cmd";
	}   

	// Application 그룹별 서버 접속 Url 가져오기 
	var serverUrl = strDataGroup + "::PDAMain";
	
    if ( isNotShow == null || isNotShow == false ) {
        // 처리중입니다. 메시지 처리 
       fn_CreateWait("처리중입니다. 잠시만 기다려주세요.",this);
    }    
    // 동기식 설정 
    if ( isSync != null && isSync == true ) {
        http.sync = false;
    }

    // 시작 시간 기록 
    if ( TIT_DEBUG_LEVEL >= TIT_DEBUG_2 ) {
		tit_debug("Transaction Start : " 
			+ actionName 
			+ "/" 
			+ cmdName 
			+ "/" 
			+ param
			+ "/"
			+ isSync, TIT_DEBUG_2 );
	}	
		
	param += "&" + timem();
/*
	alert(actionName);
	alert(param);
	alert(serverUrl);
	alert(inData);
	alert(outData);
	alert(arg);
*/
	Transaction(param, serverUrl, inData, outData, arg, "_fn_result");
	
	// 동기식 설정해제 
	if ( isSync != null && isSync == true ) {
        http.sync = false;
    }
}
/*****************************************************************
* 단일 처리 Action 정보 생성 
* @param sqlName 단일 처리 SQL ID 
* @param keySqlName PK 조회용 SQL ID 
* @param keyIncrement keySqlName이 존재할 경우만 사용되며, 
                      반복적인 처리 Sql의 경우 다음 정보 key 증가 값 
                      현재는 integer 형의 숫자형 key만 처리 가능 
* @param callbackSql 처리가 완료된 후 결과로써 반환할 Sql Id 
* @return 없음
******************************************************************/
function tit_AddSingleActionInfo(sqlName, keySqlName, keyIncrement, callbackSql) {
    tit_addActionInfo("N", sqlName, keySqlName, keyIncrement, callbackSql);
}
/*****************************************************************
* 주어진 SQL을 Dataset 건수 만큼 반복적으로 처리하는 action 정보 생성 
* @param sqlId  처리할 SQL 
* @param keySqlName  Key SQL이 존재할 경우 SQL ID          
* @param keyIncrement  Key 증가 값 
* @param callbackSql  처리 완료 후 반환할 정보를 조회할 SQL ID   
* @param keyZeroLen  Key 에 0 채워야 할 경우 0 의 갯수 
* @return 없음
******************************************************************/
function tit_AddMultiActionInfo(sqlId, keySqlName, keyIncrement
	, callbackSql, keyZeroLen, execType) {
    tit_addActionInfo("M", sqlId, keySqlName, keyIncrement, callbackSql
        , "", "", "", "", keyZeroLen, execType);
}

/*****************************************************************
* SaveAction 정보 생성 ( getRowType() 또는 주어진 Flat 컬럼의 값을 읽어 
  등록, 수정, 삭제를 처리는 SQL 요청 정보 생성 )
* @param insertSql  txType이 'S'인 경우 또는 sqlName 파라메터가 존재하면서 해당하는 
                    main sql에 대해서 master-detail 구조일 경우 
                    insert를 처리할 SQL ID 
* @param updateSql  txType이 'S'인 경우 또는 sqlName 파라메터가 존재하면서 해당하는 
                    main sql에 대해서 master-detail 구조일 경우 
                    update를 처리할 SQL ID            
* @param deleteSql  txType이 'S'인 경우 또는 sqlName 파라메터가 존재하면서 해당하는 
                    main sql에 대해서 master-detail 구조일 경우 
                    delete를 처리할 SQL ID     
* @param saveFlagColumn  insertSql, updateSql, deleteSql이 존재하여 신규, 수정, 삭제를 
                         처리할 경우 기본적으로 Dataset의 getRowStates()로 판단하여 처리
                         하나 이를 Flag 컬럼으로 대체하고 싶은 경우 사용
                         해당 Flag 컬럼의 값은 신규 - I , 수정 - U , 삭제 - D의 값을 
                         가져야 한다. 
* @param keySqlName PK 조회용 SQL ID 
* @param keyIncrement keySqlName이 존재할 경우만 사용되며, 
                      반복적인 처리 Sql의 경우 다음 정보 key 증가 값 
                      현재는 integer 형의 숫자형 key만 처리 가능 
* @param callbackSql 처리가 완료된 후 결과로써 반환할 Sql Id      
* @param keyZeroLen  Key 에 0 채워야 할 경우 0 의 갯수                     
* @return 없음
******************************************************************/
function tit_addSaveActionInfo(
	insertSql, 
	updateSql, 
	deleteSql,
	saveFlagColumn, 
	keySqlName, 
	keyIncrement, 
	callbackSql, 
	keyZeroLen) 
{
    tit_addActionInfo("S", "", keySqlName, keyIncrement, callbackSql, insertSql, updateSql, deleteSql, saveFlagColumn, keyZeroLen);
}

/****************************************************************
* 서버 호출후 전체 공통 Callback 서비스 
* @param param 
* @param errCode 에러 Code  
* @param errMsg  에러 메시지 
* @return 없음
******************************************************************/
function _fn_Result(param, ErrorCode, ErrorMsg) {	
    fn_destroyWait();
    releaseCapture();
    
    

    if ( TIT_DEBUG_LEVEL >= TIT_DEBUG_2 ) {
        var str = split(param, "&", true);
        var lap = timem() - toNumber(str[2]);   // 걸리 시간 
        var t = Truncate(lap / 1000) + "." + (lap%1000); 
        tit_debug( "Transaction END: During - " + t + "(s)");
        if ( TIT_DEBUG_LEVEL >= TIT_DEBUG_3 ) {
			tit_debug( "SEND : \n" + http.SendContents, TIT_DEBUG_3 );
			tit_debug( "REVC : \n" + http.RecvContents, TIT_DEBUG_3 );
		}	
    }
    // 분석 
    var str = split(param, "&", true );
    
	// Http Error
	if(GV_HTTPCODE.length() > 0){
		var arrErrorList = split(GV_HTTPCODE,chr(30));
		ErrorCode = toNumber(arrErrorList[0]);
		ErrorMsg = arrErrorList[1];
		
		ErrorMsg = replace(ErrorMsg,toString(ErrorCode) + ":","");		
		
		if((ErrorCode == -2085613056) || (ErrorCode == -2085605317)) {
			ErrorMsg += "\n\n동일한 현상이 발생할 경우, 시스템 관리자에게 연락하시기 바랍니다.";
		} else if((ErrorCode == -2085601264) || (ErrorCode == -2085613056)) {
			ErrorMsg = replace(ErrorMsg,'해당경로에 파일을 찾을수 없습니다','네트워크 연결에 실패 하였습니다');
			ErrorMsg += "\n\n동일한 현상이 발생할 경우, 시스템 관리자에게 연락하시기 바랍니다.";
		}
	}
	    
    
    if ( ErrorCode != 0 ) {
        if ( ErrorCode == -100 ) {
            // Business Exception 처리 : 메시지 처리하지 않음. 
        } else if ( ErrorCode == -200 ) {
            // 로그인이 session 이 끈어진 경우 
            fn_notLogin();
            return;
        } else {
            var SYS_MSG = errorTrace;
            // 메시지 초기화 
            errorTrace = "";
            if ( length(SYS_MSG) != 0 ) {
                alert(SYS_MSG);
                tit_debug( "Error : \n" + SYS_MSG, TIT_DEBUG_2 );
            } else {
				alert(ErrorMsg);
                tit_debug( "Error : \n" + ErrorMsg, TIT_DEBUG_2 );
            }
        }
        // 업무 호출 
        if ( str[1] != "1") {
             if ( length(str[0]) > 0 ) {
                eval(str[0] + "( ErrorCode, ErrorMsg )");
            }  
        }  
    } else {
        if ( length(str[0]) > 0 ) {
            eval(str[0] + "( ErrorCode, ErrorMsg )");
        }  
    }  
}
/****************************************************************
* 조회 중 입니다 메시지 보여주기
* 내부적으로 Division 을 생성하여 해당 정보를 보여준다.
* @param strMsg 보여줄 메시지
* @param topObj 위치할 최상위 객체
* @param errMsg  에러 메시지
* @return 없음
******************************************************************/
var _f_cnt = 0;
function fn_CreateWait(strMsg, topObj) {
    _f_cnt++;
    
	if ( GFN_FindObj("_Div_wait", this) == false ) { 
		var w = 330;
		var h = 30;
		var nLeft = (this.width /2) - (w/2);
		var nTop = (this.Height /2) - h + 20;
		
        // 생성하기
        Create("Div", "_Div_wait", "left='" + nLeft + "' border='none' top='"
            + nTop + "' width='" + w + "' height='" + h + "' syncContents='true'");    
        var strContent  = "<Contents>";
        strContent += " "  ;
        strContent += "  ";
        strContent += "<Static Height=\"30\" Id=\"st_message\" Left=\"2\" TabOrder=\"4\" Top=\"8\" Width=\"330\"  Style=\"se_lable\" bkColor=\"#EBF4FD\" Text=\"" + strMsg +"\"></Static>";         
        strContent += "</Contents>";
         _Div_wait.Contents = strContent;
       
    } else {
    	_Div_wait.visible = true;
    }
}
/*****************************************************************
* 조회 중 입니다 메시지 창 삭제하기
* @return 없음
******************************************************************/
function fn_DestroyWait(){
    if ( GFN_FindObj("_Div_wait", this) ) { //find("_Div_wait") != null ) {
        _f_cnt--;
        if ( _f_cnt <= 0 ) {
			SetWaitCursor(false);
			ReleaseCapture();
			_Div_wait.visible = false;
        }
    } else {
		SetWaitCursor(false);
		ReleaseCapture();
    }
}

/*****************************************************************
* Action 정보를 보내기 위한 기초 정보 생성
* @return 없음
******************************************************************/
var _GF_CREATE_DS = false;
function tit_CreateActionInfo() {    
    if ( _GF_CREATE_DS ) {
        return;
    }
    var dsObj = find(GF_DS_CMD);
    if ( dsObj == null ) {
        // 데이터 셋 생성 
        Create("Dataset", GF_DS_CMD);
        dsObj = object(GF_DS_CMD);
        
        _GF_CREATE_DS = true;
        
        dsObj.AddColumn("TX_NAME", "STRING", 100);
        dsObj.AddColumn("TYPE", "STRING", 10);
        dsObj.AddColumn("SQL_ID", "STRING", 200);
        dsObj.AddColumn("KEY_SQL_ID", "STRING", 200);
        dsObj.AddColumn("KEY_INCREMENT", "INT", 10);
        dsObj.AddColumn("CALLBACK_SQL_ID", "STRING", 200);
        dsObj.AddColumn("INSERT_SQL_ID", "STRING", 200);
        dsObj.AddColumn("UPDATE_SQL_ID", "STRING", 200);
        dsObj.AddColumn("DELETE_SQL_ID", "STRING", 200);
        dsObj.AddColumn("SAVE_FLAG_COLUMN", "STRING", 200);
        dsObj.AddColumn("USE_INPUT", "STRING", 1);
        dsObj.AddColumn("USE_ORDER", "STRING", 1);
        dsObj.AddColumn("KEY_ZERO_LEN", "INT", 10);
        dsObj.AddColumn("BIZ_NAME", "STRING", 100);
        
        dsObj.AddColumn("PAGE_NO", "INT", 10);
        dsObj.AddColumn("PAGE_SIZE", "INT", 10);
        dsObj.AddColumn("READ_ALL", "STRING", 1);	// Y - 또는 N 
        dsObj.addColumn("EXEC_TYPE", "STRING", 1);	// 처리 방법 - B ( 배치 ), N( 단건 )
        
        // return 되는 정보 
        dsObj.addColumn("EXEC", "STRING", 1);	// 처리 여부
        dsObj.addColumn("FAIL", "STRING", 1);	// 실패 여부 
        dsObj.addColumn("FAIL_MSG", "STRING", 200);		// 실패 메시지
        dsObj.addColumn("EXEC_CNT", "INT", 1);	// 반영건수
        dsObj.addColumn("MSG", "STRING", 200);	// 메시지 
    }
    
}
/*================================================================================
 *  프로그램 설명 : 서버와의 통신 처리를 담당하는 스크립트
 *  작성일자 : 2007/10/02
 *  버전 : 1.0
 *  변경 이력 
    일자            작성자          비고
    ------------------------------------------------------------
    2007/10/02      TrueinfoTec     최초 작성 
================================================================================*/
/*****************************************************************
* 자바 코딩 없이 사용할 경우 Action 호출 정보 초기화하기 
* @return  Action 정보를 저장하고 있는 Dataset이 초기화 된다. 
******************************************************************/
function tit_ClearActionInfo() {
	_CURR_BIZ = "";
    object(GF_DS_CMD).clearData();
}
/*****************************************************************
* 조회용 Action 정보 생성 ( 자바 코드 없이 사용할 경우 )
* @param sqlName 조회할 SQL ID 
* @param isUseInput 조회한 결과는 Input Dataset으로 사용할지 여부 
* @param isNotUseOrder Dataset 컬럼 생성시 정렬할지 여부  
* @return 없음
******************************************************************/
function tit_AddSearchActionInfo( sqlName, isUseInput, isNotUseOrder) {
    tit_addActionInfo( "N"
        , sqlName
        , ""
        , ""
        , ""
        , ""
        , ""
        , ""
        , "");
    if ( isUseInput == true ) {
        var cmdDs = object(GF_DS_CMD);
        cmdDs.setColumn(cmdDs.rowCount() - 1, "USE_INPUT", "Y");
    }
    
    if ( isNotUseOrder == true ) {
		var cmdDs = object(GF_DS_CMD);
        cmdDs.setColumn(cmdDs.rowCount() - 1, "USE_ORDER", "N");
    }
}
/*****************************************************************
* 자바 코딩 없이 사용할 경우 서버에서 처리하기 위한 Action 호출 
* 정보를 추가
* @param txType 호출하는 Transaction 타입 
         S - Insert, Update , Delete 동시 처리 
         M - 주어진 SQL 정보에 대해 Dataset이 존재하는 건 만큼 반복처리 
         CSV - 조회용 SQL 만 가능하며, 데이터 조회 후 CSV 형태로 데이터를 넘겨 줌.
* @param sqlName 처리할 Main SQL ID
                 txType이 'S'인 경우에는 해당 sql은 사용되지 않는다. 
* @param keySqlName PK 조회용 SQL ID 
* @param keyIncrement keySqlName이 존재할 경우만 사용되며, 
                      반복적인 처리 Sql의 경우 다음 정보 key 증가 값 
                      현재는 integer 형의 숫자형 key만 처리 가능 
* @param callbackSql 처리가 완료된 후 결과로써 반환할 Sql Id 
* @param insertSql  txType이 'S'인 경우 또는 sqlName 파라메터가 존재하면서 해당하는 
                    main sql에 대해서 master-detail 구조일 경우 
                    insert를 처리할 SQL ID 
* @param updateSql  txType이 'S'인 경우 또는 sqlName 파라메터가 존재하면서 해당하는 
                    main sql에 대해서 master-detail 구조일 경우 
                    update를 처리할 SQL ID            
* @param deleteSql  txType이 'S'인 경우 또는 sqlName 파라메터가 존재하면서 해당하는 
                    main sql에 대해서 master-detail 구조일 경우 
                    delete를 처리할 SQL ID     
* @param saveFlagColumn  insertSql, updateSql, deleteSql이 존재하여 신규, 수정, 삭제를 
                         처리할 경우 기본적으로 Dataset의 getRowStates()로 판단하여 처리
                         하나 이를 Flag 컬럼으로 대체하고 싶은 경우 사용
                         해당 Flag 컬럼의 값은 신규 - I , 수정 - U , 삭제 - D의 값을 
                         가져야 한다. 
* @return 없음
******************************************************************/
function tit_AddActionInfo(
    txType
    , sqlName
    , keySqlName
    , keyIncrement
    , callbackSql
    , insertSql
    , updateSql
    , deleteSql
    , saveFlagColumn
    , keyZeroLen
    , execType ) {
    
    var cmdDs = object(GF_DS_CMD);
    var row = cmdDs.addRow();
    
    cmdDs.setColumn(row, "TYPE", txType);
    cmdDs.setColumn(row, "SQL_ID", sqlName);
    cmdDs.setColumn(row, "KEY_SQL_ID", keySqlName);
    if ( keyIncrement == null || keyIncrement == ""
        || keyIncrement < 0 ) {
        keyIncrement = "0";
    }
    if ( keyZeroLen == null || keyZeroLen == ""
        || keyZeroLen < 0 ) {
        keyZeroLen = "0";
    }
    
    cmdDs.setColumn(row, "KEY_INCREMENT", keyIncrement);
    cmdDs.setColumn(row, "CALLBACK_SQL_ID", callbackSql);
    cmdDs.setColumn(row, "INSERT_SQL_ID", insertSql);
    cmdDs.setColumn(row, "UPDATE_SQL_ID", updateSql);
    cmdDs.setColumn(row, "DELETE_SQL_ID", deleteSql);
    cmdDs.setColumn(row, "SAVE_FLAG_COLUMN", saveFlagColumn);
    cmdDs.setColumn(row, "KEY_ZERO_LEN", keyZeroLen);
    cmdDs.setColumn(row, "BIZ_NAME", _CURR_BIZ);
    
    if( length(execType) == 0 ) {
		cmdDs.setColumn(row, "EXEC_TYPE", "B");
    } else {
		cmdDs.setColumn(row, "EXEC_TYPE", execType);
    }
}

/*****************************************************************
* SELECT를 수행한 후 정보 존재 유무에 따라 별도의 SQL을 처리할 수 있는 
  Action 정보 생성 
* @param selectSqlId  조회 처리용 SQL 
* @param notFoundSql  조회 결과 없을 경우 처리할 SQL          
* @param foundSql  조회 결과가 존재할 경우 처리할 SQL     
* @return 없음
******************************************************************/
function tit_addSelectExActionInfo(selectSqlId, notFoundSql, foundSql) 
{
    tit_addActionInfo("AS", selectSqlId, "", "", "", notFoundSql, foundSql, "", "");
}

/*===============================================================================
*   디버깅 관련 
================================================================================*/
/**************************************************************************************
*  Debug Message 처리 
* @param  obj  debug 메시지를 표시할 객체 
**************************************************************************************/
function tit_Debug(obj, level) {
	if ( level == null || toNumber( level ) <= 0 ) {
		level = 0;
	}
	
	// 디버깅 모드가 아님. 
	if ( TIT_DEBUG_LEVEL < level ) {
		return;
	}
	
	var str = "[" + tit_formatDate(getDate()) + "] ";
	var sType = toUpper(type( obj ));
	if ( sType == "OBJECT") {
		if ( indexOf( obj, "[Dataset:") != -1 ) {
			var dsObj = substr( obj, indexOf( obj, ":") + 1);
			dsObj = left( dsObj, length( dsObj) -1 );
			dsObj = object( dsObj );
			str += tit_showDs(dsObj, true);
			
		} else if ( indexOf ( obj, "[Component:") != -1 ) {
			var compObj = substr( obj, indexOf( obj, ":") + 1);
			compObj = left( compObj, length( compObj) -1 );
			str += object( compObj ).Text;
		}
	} else if( sType == "ARRAY" ) {
		for( var i = 0; i < length( obj ); i++ ) {
			str += "\nARRAY[" + i + "] : " + obj[i];
		}
	} else {
		str += obj;
	}
	
	if ( TIT_DEBUG_TYPE == TIT_DEBUG_FILE ) {
		var fileObj = Find("_titLogFile");
 
		if ( fileObj == null ) {
			Create("file","_titLogFile", "left='0' top='0'");
		}
		_titLogFile.fileName = "C:\\tit_debug.log";
		var size = _titLogFile.GetLength();
		if ( size > 1048576 ) {	// 1K 이상 
			_titLogFile.open("w");
		} else {
			_titLogFile.open("at");
		}	
		_titLogFile.write(str + "\r\n");
		_titLogFile.close();
	} else {
		trace( str );
	}	
}
/******************************************************
 * 서버에서 에러 메시지 수신 시 처리
******************************************************/
function fn_ShowSysError(sMsg) {
	if( GV_USER_ID == "" ) {
		alert(sMsg);
		return;
	}
	Alert(errorSqlCode+"::"+sMsg);
}
/****************************************************************
* action Name 가져오기  
* @param actionName 현재 처리해야 하는 action name 
* @return 없음
******************************************************************/
function fn_GetActionName(actionName) {
	return iif( length(actionName) == 0, "PDA_ACT", actionName);
}

/******************************************************
*  X-FRAMEWORK 용 서버로 전송되는 GLOBAL VARIABLE 정보 
******************************************************/
function fn_GetGlobalVariable() {
	var arg = "";	
	return arg;
}
/******************************************************
*  로그인 사용자 아이디
******************************************************/
function gfn_GetUserId(){
	if( length( GDS_USERINFO.GetColumn(0,"USER_ID") ) == 0 ) {
		return "TEST_USER";
	}
	
	return GDS_USERINFO.GetColumn(0,"USER_ID");
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
function gfn_SortGrid(gridObj,nCell) {
	var nheadText,sflag;
	//  해당하는 셀의 타이틀의 소트마크를 추가 또는 변경한다.
	if(right(gridObj.GetCellProp("head",nCell,"text"),1) == CONST_ASC_MARK) {
		sflag = CONST_DESC_MARK;
	} else {
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
function gfn_SortGridByflag(gridObj,nCell,flag) {
	var nheadText;
	var dsObj = object(gridObj.bindDataset);
	// 데이터셋의 rowcount가 1보다 작을 경우
	if ( dsObj.rowCount() < 2 ) {
		return;
	}

	//  해당하는 셀의 타이틀의 소트마크를 추가 또는 변경한다.
	if(flag == CONST_DESC_MARK) {
		dsObj.sort(gridObj.GetCellProp("Body",nCell,"colid"),false);
		nheadText = gridObj.GetCellProp("head",nCell,"text");
		nheadText = replace(nheadText,CONST_ASC_MARK,"");
		nheadText = replace(nheadText,CONST_DESC_MARK,"");
		nheadText = nheadText + CONST_DESC_MARK;
	}
	else {
		dsObj.sort(gridObj.GetCellProp("Body",nCell,"colid"),true);
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
function gfn_ClearGridSortMark(gridObj, skipCell) {
	var nColCnt = gridObj.GetCellCount("head");
	var sRepText="";
	for(i=0;i<nColCnt;i++) {
		if(skipCell <> i) {
			sRepText = replace(gridObj.GetCellProp("head",i,"text"), CONST_ASC_MARK,"");
			sRepText = replace(sRepText, CONST_DESC_MARK,"");
			gridObj.SetCellProp("head",i,"text", sRepText);
		}
	}
}

/*===============================================================================
*   입력 데이터 정합성 체크 함수
================================================================================*/
/********************************************************************************
* 기      능   : DataSet Count조회
* return 값: true : count가 1개 이상일 경우  false : count가 0개 경우
********************************************************************************/
function gfn_GetDataSetCount(dataSetObj){
	var dsCount = dataSetObj.count();
	if(dsCount >0){
		return true;
	}else{
		return false;
	}
}
/****************************************************************
* 공통 콤보 서비스 호출
* @return 없음
******************************************************************/
var dsCodes=null;
var CONST_COMBO_CACHE = "N";
function gfn_GetCommCodes(){
	//----------------------------------------------------------------------------------------   
	// Combo Cache 처리를 위한 부분
	//----------------------------------------------------------------------------------------   
	
	if(CONST_COMBO_CACHE == "Y"){
		for(nRow=(ds_combo.getRowCount()-1);nRow>-1;nRow--){
			GDS_COMMONCOMBO.UnFilter();
			if (GDS_COMMONCOMBO.FindRow("CD_KND",ds_combo.getColumn(nRow,"CD_KND")) != -1) {
				_fn_ProcessCommCode(GDS_COMMONCOMBO,nRow);
				ds_combo.deleteRow(nRow);
			}
		}		
	}
	
	if(ds_combo.getRowCount() < 1){
		return;
	}
		
	if(dsCodes == null){
		Create("dataset","dsCodeTmp");
		dsCodes = dsCodeTmp;
	}

	tit_ClearActionInfo();	
	tit_AddSearchActionInfo("common::COM_CODE_SL00");	// sqlMap
		
	tit_CallService(
		"",
		"",
		"ds_combo=ds_combo",
		"dsCodeTmp=ds_codes",
		"",
		"gfn_CommCodeCallBack",
		false,
		false
	);
}

/****************************************************************
* 공통 콤보 서비스 호출콜백
* @return 없음
******************************************************************/
function gfn_CommCodeCallBack(sTrId,sErr,sMsg){	

    for (nRow=0; nRow<ds_combo.getRowCount(); nRow++){
		dsCodeTmp.UnFilter();
				
        if (dsCodeTmp.FindRow("CD_KND",ds_combo.getColumn(nRow,"CD_KND")) == -1) {
            trace("해당코드없음:::::" + lvArrFilterCode[nRow]);
        } else {              
			_fn_ProcessCommCode(dsCodeTmp,nRow);
        }
    }
    dsCodeTmp.UnFilter();
    GDS_COMMONCOMBO.AppendDataset(dsCodeTmp);    
	dsCodeTmp.clear();
	
	if(IsExistFunc("fn_AfterCommCode")){
		fn_AfterCommCode(sErr,sMsg);
	}
	
    return true;
}
/*===============================================================================
*   상수 선언
================================================================================*/
var G_CODE_ALL = 1;	// "전체" 라는 글자로 생성
var G_CODE_BLANK = 2;	// 공백으로 첫 줄 생성 , null일 경우 기본
var G_CODE_SELECT = 3;	// "선택" 이라는 글자로 생성
var G_CODE_NONE = 4;	// 생성하지 않음.
var G_CS_BIZ = "CS_BIZ_XA";	// CS용 비지니스 
var G_HR_BIZ = "HR_BIZ_XA";	// HR용 비지니스 
var G_XA_ACT = "XA_ACT";	// XA용으로 DB를 2개 이상 사용할 경우 
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
function gfn_SearchCommCode(codeArr, dsObjArr, addTypeArr, isSync) {
	_tmp_codeArr = codeArr;
	_tmp_dsObjArr = dsObjArr;
	_tmp_addTypeArr = addTypeArr;

	var sLang = "KOR";
/*
	switch( G_LANG) {
		case "zh":
			sLang = "CHN";
			break;
		case "en":
			sLang = "ENG";
			break;
	}
*/

	gfn_initDs(gds_code_cond, true);

	gds_code_cond.setColumn(0, "LANG", sLang);
	gds_code_cond.setColumn(0, "S_CODE", codeArr[0]);

	for( var i = 1; i < length( codeArr ); i++ ) {
		gds_code_cond.addRow();
		gds_code_cond.setColumn(i, "S_CODE", codeArr[i]);
	}

	// 정보 초기화
	tit_clearActionInfo();

	// 조회해야 하는 sql
	tit_addSearchActionInfo("pcc04:ZMMT028_S1");

	// Transaction 처리
/*
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_code_cond"
        , "gds_code=ds_list"
        , ""
        , "_fn_afterCodeSearch"
        , false
        , false
        , isSync);
*/
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_code_cond"
        , "gds_code=ds_list"
        , ""
        , "_fn_afterCodeSearch"
        ,true
        );
}

/****************************************************************
* 공통 코드 조회하기2
* @param codeArr  배열로 조회할 공통 그룹 코드 명
* @param dsObjArr	조회한 그룹코드들을 담을 Dataset 객체 아이디
* @param addTypeArr 콤보 등 전체 조건 필요시 ( 옵션 )
* @param  isSync 동기식을 사용해야 할 경우 처리 ( 옵션 ) - true / false
* @return  비동기식으로 처리 완료후 fn_completeInit () 함수를 호출하게 되어 있음.
***************************************************************/
var _tmp_codeArr = null;
var _tmp_dsObjArr = null;
var _tmp_addTypeArr = null;
function gfn_SearchCommCode2(codeArr, dsObjArr, addTypeArr, isSync) {
	_tmp_codeArr = codeArr;
	_tmp_dsObjArr = dsObjArr;
	_tmp_addTypeArr = addTypeArr;

	var sLang = "KOR";

	gfn_initDs(gds_code_cond, true);

	gds_code_cond.setColumn(0, "LANG", sLang);
	gds_code_cond.setColumn(0, "S_CODE", codeArr[0]);

	for( var i = 1; i < length( codeArr ); i++ ) {
		gds_code_cond.addRow();
		gds_code_cond.setColumn(i, "S_CODE", codeArr[i]);
	}

	// 정보 초기화
	tit_clearActionInfo();

	// 조회해야 하는 sql
	tit_addSearchActionInfo("pcc04:CODEBOOK_S1");

	// Transaction 처리
	tit_callService(
        ""
        , ""
        , "ds_cond=gds_code_cond"
        , "gds_code=ds_list"
        , ""
        , "_fn_afterCodeSearch"
        ,true
        );
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

/****************************************************************
* 공통 코드 조회 Callback
* 개발자 사용 API 아님.
***************************************************************/
function _fn_afterCodeSearch(errCode, errMsg) {

	for( var i = 0; i < length( _tmp_codeArr ); i++) {
		if( _tmp_dsObjArr[i] != null ) {
			gfn_copyDs(gds_code
				, _tmp_dsObjArr[i]
				, " GROUP_CODE == '" + _tmp_codeArr[i] + "'");

			if ( _tmp_addTypeArr[i] == G_CODE_ALL ) {
				_tmp_dsObjArr[i].insertRow(0);
				_tmp_dsObjArr[i].setColumn(0, "CODE", "");
				_tmp_dsObjArr[i].setColumn(0, "CODE_NAME", "-- 전체 --");
			} else if ( _tmp_addTypeArr[i] == G_CODE_SELECT ) {
				_tmp_dsObjArr[i].insertRow(0);
				_tmp_dsObjArr[i].setColumn(0, "CODE", "");
				_tmp_dsObjArr[i].setColumn(0, "CODE_NAME", "-- 선택 --");
			} else if ( _tmp_addTypeArr[i] != G_CODE_NONE ) {
				_tmp_dsObjArr[i].insertRow(0);
				_tmp_dsObjArr[i].setColumn(0, "CODE", "");
				_tmp_dsObjArr[i].setColumn(0, "CODE_NAME", "");
			}
		}
	}

	// 처리 완료후 화면 호출
	fn_completeInit(_tmp_codeArr, _tmp_dsObjArr, _tmp_addTypeArr);
}

/**************************************************************************************
* 현재일자 구하기
* @return 현재일자 ( YYYYMMDD )
**************************************************************************************/
function gfn_getCurrDate() 
{
	return today();
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
* 처리해야 하는 비지니스 명 
* @param  newBizName  - 업무 비지니스 명 
**************************************************************************************/
function tit_setBiz(newBizName) 
{
	_CURR_BIZ = newBizName;
}
/**************************************************************************************
* 처리해야 하는 비지니스 명 초기화 
**************************************************************************************/
function tit_clearBiz() 
{
	_CURR_BIZ = "";
}
