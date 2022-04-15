﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 서버와의 통신 처리를 담당하는 스크립트
 *  작성일자 : 2007/10/02
 *  버전 : 1.0
 *  변경 이력 
    일자            작성자          비고
    ------------------------------------------------------------
    2007/10/02      TrueinfoTec     최초 작성 
================================================================================*/
/*===============================================================================
*   상수 선언 
================================================================================*/
var GF_DEFAULT_SERVER = "DEFAULT_SERVER";
var errorTrace;
var errorSqlCode;
var errorSqlStatus;
var GF_DS_CMD = "tit_ds_cmd";
var TIT_DEBUG_FILE = "F";
var TIT_DEBUG_TRACE = "T";
var TIT_DEBUG_TYPE = TIT_DEBUG_FILE;	// F - FILE , T - TRACE
var TIT_DEBUG_LEVEL = 0;	
var TIT_DEBUG_1 = 1;
var TIT_DEBUG_2 = 2;
var TIT_DEBUG_3 = 3;
var _CURR_BIZ = "";
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
var _startTime = 0;
function tit_callService(
          actionName
        , cmdName
        , inData
        , outData
        , otherArg
        , callBackFnc
        , isErrorNotCall 
        , isNotShow
        , isSync) 
{
//	trace(_startTime + "," +  (timem() - _startTime) );
	if(_startTime != 0 && (timem() - _startTime) > 1000 && cmdName != "getCurrDate") {
		trace("####중복호출 : " + actionName + "," + cmdName );
		return;
	}
	_startTime = timem();
	
	SetWaitCursor(true);
	SetCapture();
	
	if ( isErrorNotCall == null ) 
	{
        isErrorNotCall = false;
	}
	var param = callBackFnc + "&" + isErrorNotCall;
	
	// Argument 처리 
	var arg = "";
	actionName = fn_getActionName(actionName);
	arg += "actionName=" + quote( actionName );
	
	if ( length(cmdName) == 0 ) 
	{
        cmdName = "execute";
	}
	
	arg += " cmd=" + quote(cmdName);
	
	// global variable 처리 
	arg += " " + fn_getGlobalVariable();
		
	// log id 처리 
	var clientId = ext_GetIPAddress();
	clientId = replace(clientId, "[" , "");
	clientId = replace(clientId, "]" , "");
	arg += " titLogId=" + quote(G_USER_ID + "_" + clientId );
	
	if ( length(otherArg) > 0 ) 
	{
        arg += " " + otherArg;
	}
	
	if ( _GF_CREATE_DS == true ) 
	{
        inData += " ds_cmd=" + GF_DS_CMD;
	}   

	// Application 그룹별 서버 접속 Url 가져오기 
	// 2015.12.29 서비스 모니터링 시 호출 구분자 추가 
	//var serverUrl = GF_DEFAULT_SERVER + "::Main" + "?ACT="+ actionName +"&CMD=" + cmdName;
	var serverUrl = GF_DEFAULT_SERVER + "::Main/"+actionName +"/" + this.Url;
	if(length(F_PGM_ID) > 0){
		serverUrl = GF_DEFAULT_SERVER + "::Main/"+actionName +"/" + F_PGM_ID;
	}
	
    if ( isNotShow == null || isNotShow == false ) 
    {
        // 처리중입니다. 메시지 처리 
        fn_createWait("",this);
    }    
    // 동기식 설정 
    if ( isSync != null && isSync == true ) 
    {
        http.sync = true;
    }
    
//    setCapture();
    // 시작 시간 기록 
    if ( TIT_DEBUG_LEVEL >= TIT_DEBUG_2 ) 
    {
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
	Transaction(param, serverUrl, inData, outData, arg, "_fn_result");
	
	// 동기식 설정해제 
	if ( isSync != null && isSync == true ) 
	{
        http.sync = false;
    }
}
/****************************************************************
* 서버 호출후 전체 공통 Callback 서비스 
* @param param 
* @param errCode 에러 Code  
* @param errMsg  에러 메시지 
* @return 없음
******************************************************************/
function _fn_result(param, ErrorCode, ErrorMsg) 
{	
	_startTime = 0;
    fn_destroyWait();
    
    if ( TIT_DEBUG_LEVEL >= TIT_DEBUG_2 ) 
    {
        var str = split(param, "&", true);
        var lap = timem() - toNumber(str[2]);   // 걸리 시간 
        var t = Truncate(lap / 1000) + "." + (lap%1000); 
        tit_debug( "Transaction END: During - " + t + "(s)");
        if ( TIT_DEBUG_LEVEL >= TIT_DEBUG_3 ) 
        {
			tit_debug( "SEND : \n" + http.SendContents, TIT_DEBUG_3 );
			tit_debug( "REVC : \n" + http.RecvContents, TIT_DEBUG_3 );
		}	
    }
    // 분석 
    var str = split(param, "&", true );
    
    if ( ErrorCode != 0 ) 
    {
        if ( ErrorCode == -100 ) 
        {
            // Business Exception 처리 : 메시지 처리하지 않음. 
        } 
        else if ( ErrorCode == -200 ) 
        {
            // 로그인이 session 이 끈어진 경우 
            fn_notLogin();
            return;
        } 
        else 
        {            
			var newMsg = "";

			if( length(errorSqlStatus) > 0 ) 
			{
				//trace(errorSqlStatus); trace(ErrorMsg);
				// sql error 임.
				switch( errorSqlStatus ) 
				{
					case "07001":
								newMsg = "[SQLSTAT:07001]잘못된 숫자가 입력 되었습니다. 정보를 확인하여 주십시오.";
								break; 
					case "23000":
								newMsg = "[SQLSTAT:23000]자리수를 초과하여 입력했습니다. 정보를 확인하여 주십시오.";
								break; 
					case "21000":
								newMsg = "[SQLSTAT:21000]두개 이상의 결과가 존재합니다. 정보를 확인하여 주십시오.";
								break; 
					case "22001":
								newMsg = "[SQLSTAT:22001]데이터의 길이가 맞지 않습니다. 정보를 확인하여 주십시오.";
								break; 
					case "22007":
								newMsg = "[SQLSTAT:22007]잘못된 일자/시간/타입 데이터 입니다. 정보를 확인하여 주십시오.";
								break; 
					case "22018":
								newMsg = "[SQLSTAT:22018]잘못된 문자/숫자가 입력되었습니다. 정보를 확인하여 주십시오.";
								break; 
					case "23505":
								newMsg = "[SQLSTAT:23505]중복된 키값이 존재하거나 외부 참조값이 없습니다. 정보를 확인하여 주십시오.";
								break; 
					case "38552":
								newMsg = "[SQLSTAT:38552]포맷이 맞지 않거나 자리수 이상입니다. 정보를 확인하여 주십시오.";
								break; 
					case "42501":
								newMsg = "[SQLSTAT:42501]해당테이블 조회 권한이 없습니다. 담당자에게 문의바랍니다.";
								break; 
					case "42601":
								newMsg = "[SQLSTAT:42601]잘못된 SQL 구문입니다. 담당자에게 문의바랍니다.";
								break; 
					case "42703":
								newMsg = "[SQLSTAT:42703]잘못된 SQL 구문입니다. 담당자에게 문의바랍니다.";
								break; 
					case "57011":
								newMsg = "[SQLSTAT:57011]트랜잭션 로그 초과입니다. 시스템 관리자에게 문의바랍니다.";
								break; 
					case "":
								newMsg = "";
								break;
				}

				if( newMsg != "" ) 
				{
					fn_showSysError(newMsg, "M");
					tit_debug( "Error : \n" + newMsg, TIT_DEBUG_2 );
				}
			} 

			if( newMsg == "") 
			{
				var SYS_MSG = errorTrace;

				// 메시지 초기화 
				errorTrace = "";
				if ( length(SYS_MSG) != 0 ) 
				{
					fn_showSysError(SYS_MSG, "T");
					tit_debug( "Error : \n" + SYS_MSG, TIT_DEBUG_2 );
				} 
				else 
				{
					fn_showSysError(ErrorMsg, "M");
					tit_debug( "Error : \n" + ErrorMsg, TIT_DEBUG_2 );
				}
			}    
		}
        // 업무 호출 
        if ( str[1] != "1") 
        {
			if ( length(str[0]) > 0 ) 
			{
                eval(str[0] + "( ErrorCode, ErrorMsg )");
            }  
        }  
    } 
    else 
    {
        if ( length(str[0]) > 0 ) 
        {
            eval(str[0] + "( ErrorCode, ErrorMsg )");
        }  
    }  
}
/*****************************************************************
* Action 정보를 보내기 위한 기초 정보 생성
* @return 없음
******************************************************************/
var _GF_CREATE_DS = false;

function tit_createActionInfo() 
{    
    if ( _GF_CREATE_DS ) 
    {
        return;
    }
    var dsObj = find(GF_DS_CMD);
    if ( dsObj == null ) 
    {
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
    }
}
/*****************************************************************
* 조회용 Action 정보 생성 ( 자바 코드 없이 사용할 경우 )
* @param sqlName 조회할 SQL ID 
* @param isUseInput 조회한 결과는 Input Dataset으로 사용할지 여부 
* @param isNotUseOrder Dataset 컬럼 생성시 정렬할지 여부  
* @return 없음
******************************************************************/
function tit_addSearchActionInfo( sqlName, isUseInput, isNotUseOrder) 
{
    tit_addActionInfo( 
          "N"
        , sqlName
        , ""
        , ""
        , ""
        , ""
        , ""
        , ""
        , "");
    if ( isUseInput == true ) 
    {
        var cmdDs = object(GF_DS_CMD);
        cmdDs.setColumn(cmdDs.rowCount() - 1, "USE_INPUT", "Y");
    }
    
    if ( isNotUseOrder == true ) 
    {
		var cmdDs = object(GF_DS_CMD);
        cmdDs.setColumn(cmdDs.rowCount() - 1, "USE_ORDER", "N");
    }
}
/*****************************************************************
* 조회용 Action 정보 생성 ( 자바 코드 없이 사용할 경우 )
* CSV 용으로 사용 
* @param sqlName 조회할 SQL ID 
* @param recvCount  전송받을 Count ( 정의하지 않을 경우 기본 : 100 )
* @return 없음
******************************************************************/
function tit_addCsvSearchActionInfo(sqlName , recvCount ) 
{
    tit_addActionInfo(
          "CSV"
        , sqlName
        , ""
        , ""
        , ""
        , ""
        , ""
        , ""
        , "");
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
function tit_addSingleActionInfo(sqlName, keySqlName, keyIncrement, callbackSql) 
{
    tit_addActionInfo("N", sqlName, keySqlName, keyIncrement, callbackSql);
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
	keyZeroLen,
	execType) 
{
    tit_addActionInfo("S", "", keySqlName, keyIncrement, callbackSql, insertSql, updateSql, deleteSql, saveFlagColumn, keyZeroLen, execType);
}
/*****************************************************************
* 주어진 SQL을 Dataset 건수 만큼 반복적으로 처리하는 action 정보 생성 
* @param sqlId  처리할 SQL 
* @param keySqlName  Key SQL이 존재할 경우 SQL ID          
* @param keyIncrement  Key 증가 값 
* @param callbackSql  처리 완료 후 반환할 정보를 조회할 SQL ID   
* @param keyZeroLen  Key 에 0 채워야 할 경우 0 의 갯수
* @param execType 처리해야 하는 실행타입 정의 ( B - 배치 , N - 한건 씩 처리 ) 
* @return 없음
******************************************************************/
function tit_addMultiActionInfo(sqlId, keySqlName, keyIncrement, callbackSql, keyZeroLen, execType) 
{
    tit_addActionInfo("M", sqlId, keySqlName, keyIncrement, callbackSql, "", "", "", "", keyZeroLen, execType);
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
/*****************************************************************
* 자바 코딩 없이 사용할 경우 Action 호출 정보 초기화하기 
* @return  Action 정보를 저장하고 있는 Dataset이 초기화 된다. 
******************************************************************/
function tit_clearActionInfo() 
{
	_CURR_BIZ = "";
    object(GF_DS_CMD).clearData();
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
function tit_addActionInfo(
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
	    , execType
	    ) {
	    
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
	    
	    if( execType == null || execType == ""){
	      execType ="B";
	    }
	    
	    cmdDs.setColumn(row, "KEY_INCREMENT", keyIncrement);
	    cmdDs.setColumn(row, "CALLBACK_SQL_ID", callbackSql);
	    cmdDs.setColumn(row, "INSERT_SQL_ID", insertSql);
	    cmdDs.setColumn(row, "UPDATE_SQL_ID", updateSql);
	    cmdDs.setColumn(row, "DELETE_SQL_ID", deleteSql);
	    cmdDs.setColumn(row, "SAVE_FLAG_COLUMN", saveFlagColumn);
	    cmdDs.setColumn(row, "KEY_ZERO_LEN", keyZeroLen);
	    cmdDs.setColumn(row, "BIZ_NAME", _CURR_BIZ);
	    cmdDs.setColumn(row, "EXEC_TYPE", execType);
	}
/******************************************************
 *  서버로 파일 전송 처리 
 * @param  httpfileObj  http file 처리 객체  
 * @param  serverSaveDir  서버에 저장할 디렉토리 별명 
 * @param  localFilePath 로컬 경로 
 * @param  localFileName 로컬 파일명 
 * @param  sendFileName  서버에 전송하고 싶은 이름 
 * @param  fileProcType  서버에서 기존 파일을 처리하는 타입 정의  
           D - deleteFileName에 기술된 파일명이 존재할 경우만 파일 삭제 ( 기본 값 )
           A - 전송하는 파일명으로 해당 파일이 존재할 경우 삭제 후 저장 
               ( 파일명은 서버에 정의된 되로 타임을 추가해야 할 경우 추가한다. )
               또한, deleteFileName이 존재할 경우 함께 삭제한다. 
           U - 전송하는 파일명으로 해당 파일이 존재할 경우 삭제 후 저장 
               ( 파일명은 무조건 Client를 기준으로 저장한다. )
               또한, deleteFileName이 존재할 경우 함께 삭제한다. 
           N - 전송하는 파일명으로 해당 파일이 존재할 경우 저장하지 않는다. 
               ( 파일명은 무조건 Client를 기준으로 저장한다. )
               또한, deleteFileName이 존재할 경우 함께 삭제한다. 
 * @param  deleteFileName 서버에서 삭제해야 하는 파일 명
           isDelete가 true이면서 해당 파일명이 기술되어 있지 않을 경우 
           기존 파일을 삭제한다. 
 * @return  서버에 저장된 파일 정보 Array 
            0 - 성공 : S / 실패 : E 
            1 - 서버에 저장된 실제 경로 
            2 - 서버에 저장된 실제 파일명 
            3 - 서버에 저장된 파일 사이즈 
******************************************************/
var GF_FILE_SERVER_URL = G_SERVER_URL;
var GF_FILE_SERVICE_NAME = "FileUploader";
var GF_FILE_UPLOAD_SIZE = 10485760;   // 1M : 1048576;
var GF_FILE_DOWNLOAD_SIZE = 10485760;
function tit_fileWrite(httpfileObj
    , serverSaveDir
    , localFilePath
    , localFileName
    , sendFileName
    , fileProcType
    , deleteFileName) {

    var rMsg = Array(2);    // 처리 결과 전송 

    if ( length(localFilePath) == 0 || length(localFileName) == 0 ) {
        rMsg[0] = "E";
        rMsg[1] = "file is empty.";//"서버로 전송할 파일 정보가 없습니다.";
        return rMsg;
    }

    // 파일 사이즈 계산 

    var tmpFileObj = find("_tmpFile");
    if ( tmpFileObj == null ) {
        Create("File", "_tmpFile");
    }
    

    var size = _tmpFile.GetLength(localFilePath + "\\" + localFileName);

    if ( size > GF_FILE_UPLOAD_SIZE ) {
        rMsg[0] = "E";
        rMsg[1] = "파일은 최대 " + ( GF_FILE_UPLOAD_SIZE / 1024 ) + "KB 를 초과할 수 없습니다.";
        return rMsg;
    }
    
    if ( length(fileProcType) == 0 ) {
        fileProcType = "D";
    }
    
    if ( length(sendFileName) == 0 ) {
        sendFileName = localFileName;
    }
    
    if ( length(deleteFileName) == 0 ) {
        deleteFileName = "";
    }
    
    if( right(localFilePath, 2) != "\\" ) {
        localFilePath = localFilePath + "\\";
    }
    
    var fileUrl = localFilePath + localFileName;
    // 파일 처리 타입 , 서버 디렉토리 정보 , 파일 저장 이름, 이전에 삭제할 파일명 
    var sendStr = fileProcType + "|" + serverSaveDir + "|" + UrlEncode(sendFileName) + "|" + UrlEncode(deleteFileName);
    var serverUrl = GF_FILE_SERVER_URL + GF_FILE_SERVICE_NAME + "?mode=upload";

    var fileSize = httpfileObj.GetFileSizeLocal(fileUrl);
    if ( fileSize > GF_FILE_UPLOAD_SIZE ) {
        rMsg[0] = "E";
        rMsg[1] = "파일 사이즈는 최대 10M까지만 가능합니다.";
        return rMsg;
    }
    
       httpfileObj.CookieParam = sendStr;//UrlEncode(sendStr);//urlEncodeUtf8(sendStr); //UrlEncode(sendStr);     // 전송할 정보 
       var rtn = httpfileObj.OpenFile(serverUrl, fileUrl, "PUT");

       if( rtn < 0 ) {
             rMsg[0] = "E";
        rMsg[1] = "서버 파일 전송 실패::" + httpfileObj.ErrorMsg;
             return rMsg;
       }
       
       var sendSize = 0;
       var totSize = 0;
       while(1) {
             sendSize = httpfileObj.WriteFile(GF_FILE_UPLOAD_SIZE);
             totSize += sendSize;
             if( sendSize < GF_FILE_UPLOAD_SIZE ) {
                    break;
             }
       }
       
       httpfileObj.CloseFile();
      

       var serverRtn = UrlDecode(httpfileObj.CookieParam);//alert(serverRtn);
       serverRtn = Replace(serverRtn, "FileParam=", "");
       serverRtn = Replace(serverRtn, "\"", "");
       var rArr = Array(4);
       var cnt = 0;
       var prevPos = 0;
       for ( var i = 0; i < length(serverRtn); i++) {
        if ( charAt(serverRtn, i) == "|" ) {
            rArr[cnt] = substr(serverRtn, prevPos, (i - prevPos));
            prevPos = i + 1;
            cnt++;
        }
       }
       rArr[cnt] = substr(serverRtn, prevPos);
       return rArr;
}

/******************************************************
 *  서버로 파일 삭제 처리 
 * @param  serverSaveDir  서버에 저장할 디렉토리 별명 
 * @param  serverFileName 서버의 파일명 
 * @param  isNeedRtn 처리 결과 
 * @return  필요시 처리 성공 여부 : true / 그외 false 
 ******************************************************/

var _FILE_PROC_RTN = "";
var _FILE_PROC_MSG = "";

function tit_fileDelete(serverSaveDir, serverFileName, isNeedRtn) {

    var serverUrl = GF_FILE_SERVER_URL + GF_FILE_SERVICE_NAME + "?mode=delete";
    
    _FILE_PROC_RTN = "";
    _FILE_PROC_MSG = "";
 
    serverUrl = serverUrl + "&fileDir=" + UrlEncode(serverSaveDir) 
        + "&fileName=" + UrlEncode(serverFileName);
 
    if ( isNeedRtn == true) {
        http.sync = true;
    }
    
    Transaction("", serverUrl, "", "", "", "");    
    
    if ( isNeedRtn == true ) {
        http.sync = false;
        // 결과 전송 
        return _FILE_PROC_RTN;
    }
}
/******************************************************
 *  서버에서 파일 복사 처리 
 * @param  srcFileDir  서버에서 복사원본  디렉토리 별명 
 * @param  targerDir  서버에서 복사할 디렉토리 별명  
 * @param  srcFileName 서버에서 복사 원본 파일명 
 * @param  targetFileName 서버에서 복사 대상 파일명 ( rename 하고자할 경우만 정의 )   
 * @param  isDelete  복사후 삭제 여부 : 삭제시 ( true )
 * @param  isNeedRtn 처리 결과 
 * @return  필요시 처리 성공 여부 : true / 그외 false 
 ******************************************************/

function tit_fileCopy(srcFileDir, targerDir, srcFileName, targetFileName, isDelete, isNeedRtn) {
    var serverUrl = GF_FILE_SERVER_URL + GF_FILE_SERVICE_NAME + "?mode=copy";

    _FILE_PROC_RTN = "";
    _FILE_PROC_MSG = "";
 
    var isDel ="N";
    if ( isDelete == true ) {
        isDel = "Y";
    }
    if ( length( targetFileName ) == 0 ) {
        targetFileName = srcFileName;
    }
    serverUrl = serverUrl + "&srcFileDir=" + UrlEncode(srcFileDir) 
        + "&targetFileDir=" + UrlEncode(targerDir)
        + "&isDelete=" + isDel
        + "&targetFileName=" + UrlEncode(targetFileName)
        + "&srcFileName=" + UrlEncode(srcFileName);
 
    if ( isNeedRtn == true) {
        http.sync = true;
    }
    Transaction("", serverUrl, "", "", "", "");    
    if ( isNeedRtn == true ) {
        http.sync = false;
        // 결과 전송 
        return _FILE_PROC_RTN;
    }
}
/******************************************************
 *  서버로 파일 다운로드 처리 
 * @param  httpfileObj  http file 처리 객체  
 * @param  serverSaveDir  서버에 저장할 디렉토리 별명 
 * @param  serverFileName  서버에서 전송 받아야 하는 이름  
 * @param  localFilePath 로컬 경로 
 * @param  localFileName 로컬 파일명 
 * @return  서버에 저장된 파일 정보 Array 
            0 - 성공 : S / 실패 : E 
            1 - 서버에 실패한 메시지 
******************************************************/
function tit_fileRead(httpfileObj, serverSaveDir, serverFileName, localFilePath, localFileName){
    var rMsg = Array(2);    // 처리 결과 전송 
    if ( length(serverSaveDir) == 0 || length(serverFileName) == 0 ) {
        rMsg[0] = "E";
        rMsg[1] = "서버에서 확인할 파일명이 존재하지 않습니다.";
        return rMsg;
    }
    
    if ( length(localFilePath) == 0 || length(localFileName) == 0 ) {
        rMsg[0] = "E";
        rMsg[1] = "로컬에 저장할 파일명이 존재하지 않습니다.";
        return rMsg;
    }
   
    if( right(localFilePath, 2) != "\\" ) {
        localFilePath = localFilePath + "\\";
    }
    
    var fileUrl = localFilePath + localFileName;
    var sendStr = serverSaveDir + "|" + UrlEncode(serverFileName);
    var serverUrl = GF_FILE_SERVER_URL + GF_FILE_SERVICE_NAME + "?mode=download";
    
       httpfileObj.CookieParam = sendStr;//UrlEncode(sendStr); alert(sendStr);          // 전송할 정보 
       
       var rtn = httpfileObj.OpenFile(serverUrl, fileUrl, "GET");
       
       if( rtn < 0 ) {
             rMsg[0] = "E";
        rMsg[1] = "서버 파일 다운로드 실패::" + httpfileObj.ErrorMsg;
             return rMsg;
       }
       
       var readSize = -1;
       while(1) {
             readSize = httpfileObj.ReadFile(GF_FILE_DOWNLOAD_SIZE);
             if( readSize == 0 || readSize == -1 ) {
            break;
        }    
       }
       
       httpfileObj.CloseFile();
       if ( readSize < 0 ) {
             rMsg[0] = "E";
        rMsg[1] = "서버 파일 다운로드 실패::" + httpfileObj.ErrorMsg;
             return rMsg;
       } 
       
       var serverRtn = httpfileObj.CookieParam;
       serverRtn = Replace(serverRtn, "FileParam=", "");
       serverRtn = Replace(serverRtn, "\"", "");
       var rArr = Array(2);
       var cnt = 0;
       var prevPos = 0;
       for ( var i = 0; i < length(serverRtn); i++) {
        if ( charAt(serverRtn, i) == "|" ) {
            rArr[cnt] = substr(serverRtn, prevPos, (i - prevPos));
            prevPos = i + 1;
            cnt++;
        }
       }
       return rArr;
}

/******************************************************
 *  현재 서버에 이름이 변경되어 저장된 파일명을 원래의 파일명으로 
  변환 
 * @param  serverFileName  서버에서 저장되어 있는 파일 이름  
 * @return  로컬에 저장해야 하는 이름 
******************************************************/
var GF_USE_TIME = true;
function tit_getLocalFileName(serverFileName) 
{
    if ( GF_USE_TIME == false ) 
    {
        return serverFileName;
    }

    var totLen = length(serverFileName);
    var dotIdx = -1;
    var underIdx = -1;
    for ( i = totLen; i >= 0; i--) 
    {
        if ( charAt(serverFileName, i) == ".") 
        {
            if ( underIdx == -1 ) 
            {
                // 확장자 
                dotIdx = i;
            }
        } 
        else if ( charAt(serverFileName, i) == "_") 
        {
            underIdx = i;
            break;
        }
    }

    var expand = "";
    var fileNm = ""; 
    if ( dotIdx != -1 ) 
    {
        expand = substr(serverFileName, dotIdx);
    }

    if ( underIdx != -1) 
    {
		var s = substr(serverFileName, underIdx+1, 13) ; 
		if ( length(s) == 13 && toNumber(s) > 1000000000000) 
		{
			fileNm = substr(serverFileName, 0, underIdx);
		} 
		else 
		{
			fileNm = substr(serverFileName, 0, dotIdx);
		}
    } 
    else // _가 없을 경우도 파일명을 가져와야 함. 2016.06.23 hss
    {
		fileNm = substr(serverFileName, 0, dotIdx);
    }
    return fileNm + expand;
}
/******************************************************
 *  서버에서 실제 이미지 경로 가져오기 
 * @param  serverSaveDir  서버에 저장할 디렉토리 별명 
 * @param  fileName  보여줄 파일명 ( 옵션 )
 * @return  서버 전제 url 경로 
 ******************************************************/
var _DIR_LIST = "";
var GF_DIR_LIS_ARRAY;
function tit_getServerDir(serverSaveDir, fileName) {
/*
    if ( GF_DIR_LIS_ARRAY == null ) {
        var serverUrl = GF_FILE_SERVER_URL + GF_FILE_SERVICE_NAME + "?mode=getDirList";
    
        _DIR_LIST = "";
        http.sync = true;
        Transaction("", serverUrl, "", "", "", "");
        http.sync = false;
        
        if ( length(_DIR_LIST) > 0 ) {
            GF_DIR_LIS_ARRAY = split(_DIR_LIST, ":");
        }
    }
    
    var defUrl = "";
    var rootUrl = "";
    for( var i = 0; i < GF_DIR_LIS_ARRAY.length; i++ ) {
        var sub = split(GF_DIR_LIS_ARRAY[i], "=");
        if ( sub[0] == "_root" ) {
            rootUrl = sub[1];
            if( length( defUrl ) == 0 ) {
                defUrl = sub[1];
            } else {
                break;
            }
        } 
        
        if ( sub[0] == serverSaveDir ) {
            defUrl = sub[1];
            if ( length( rootUrl ) > 0 ) {
                break;
            }
        }
    }
    
    var url = GF_FILE_SERVER_URL;
    // root url 
    if ( right( url, 1 ) != "/" && left(rootUrl, 1) != "/") {
        url += "/";
    } 
    url += rootUrl;
    
    if ( defUrl != rootUrl ) {
        if ( right( url, 1 ) != "/" && left(defUrl, 1) != "/") {
            url += "/";
        } 
        url += defUrl;
    }
            
    if ( length( fileName ) > 0 ) {
        if ( right( url , 1) != "/") {
            url += "/" + fileName;
        } else {
            url += fileName;
        }
    }  
    
       
    return url;
*/
	return GF_FILE_SERVER_URL + "upload/" + serverSaveDir + "/" + fileName;
}
/*===============================================================================
*   디버깅 관련 
================================================================================*/
/**************************************************************************************
*  Debug Message 처리 
* @param  obj  debug 메시지를 표시할 객체 
**************************************************************************************/
function tit_debug(obj, level) 
{
	if ( level == null || toNumber( level ) <= 0 ) 
	{
		level = 0;
	}
	
	// 디버깅 모드가 아님. 
	if ( TIT_DEBUG_LEVEL < level ) 
	{
		return;
	}
	
	var str = "[" + tit_formatDate(getDate()) + "] ";
	var sType = toUpper(type( obj ));
	if ( sType == "OBJECT") 
	{
		if ( indexOf( obj, "[Dataset:") != -1 ) 
		{
			var dsObj = substr( obj, indexOf( obj, ":") + 1);
			dsObj = left( dsObj, length( dsObj) -1 );
			dsObj = object( dsObj );
			str += tit_showDs(dsObj, true);
			
		} 
		else if ( indexOf ( obj, "[Component:") != -1 ) 
		{
			var compObj = substr( obj, indexOf( obj, ":") + 1);
			compObj = left( compObj, length( compObj) -1 );
			str += object( compObj ).Text;
		}
	} 
	else if( sType == "ARRAY" ) 
	{
		for( var i = 0; i < length( obj ); i++ ) 
		{
			str += "\nARRAY[" + i + "] : " + obj[i];
		}
	} 
	else 
	{
		str += obj;
	}
	
	if ( TIT_DEBUG_TYPE == TIT_DEBUG_FILE ) 
	{
		var fileObj = Find("_titLogFile");
 
		if ( fileObj == null ) 
		{
			Create("file","_titLogFile", "left='0' top='0'");
		}
		_titLogFile.fileName = "C:\\tit_debug.log";

		var size = _titLogFile.GetLength();
		if ( size > 1048576 ) // 1K 이상
		{	 
			_titLogFile.open("w");
		} 
		else 
		{
			_titLogFile.open("at");
		}	
		_titLogFile.write(str + "\r\n");
		_titLogFile.close();
	} 
	else 
	{
		trace( str );
	}	
}
/**************************************************************************************
*  DataSet 전체 내용 보기 
* @param  dsObj  DataSet 객체 
**************************************************************************************/
function tit_showDs(dsObj, isNotTrace) 
{
	//Dataset ID 출력
	var tempStr = "Dataset [" + dsObj.id + "]\n"; 
	
	//Column Data 출력 
	for (var i=0; i < dsObj.getRowCount(); i++) 
	{
		tempStr = tempStr + "Row "+ i + " [" + dsObj.GetRowType(i) + "]\n";

		for (var j=0; j < dsObj.ColCount(); j++) 
		{
			tempGetColumn = dsObj.GetColumn(i, j);
			tempStr = tempStr + dsObj.GetColID(j) + ":" + tempGetColumn + "\n";
		}
	}
	
	// 삭제된 데이터 출력 
	for ( var i = 0; i < dsObj.getDelRowCount(); i++ ) 
	{
		tempStr = tempStr + "Deleted Row "+ i + "\n";
		
		for (var j=0; j < dsObj.ColCount(); j++) 
		{
			tempGetColumn = dsObj.GetDelColumn(i, j);
			tempStr = tempStr + dsObj.GetColID(j) + ":" + tempGetColumn + "\n";
		}
	}
	
	if ( isNotTrace ) 
	{
		return tempStr;
	}
	
	trace(tempStr);
}
/**************************************************************************************
* 기본적으로 MaskEdit등을 사용하여 일자를 표시할 수 없을 경우 
* 문자에 - 등의 Delimiter 로 포맷팅 처리 
* @param  sDate  날짜 ( YYYYMM 또는 YYYYMMDD ) 
* @param  sDel  - / 등의 구분자 ( 기본은 - 로 처리 )
**************************************************************************************/
function tit_formatDate(sDate, sDel) 
{
	if ( length( sDel ) == 0 ) 
	{
		sDel = "-";
	}
	
	var rtn = sDate;
	var sTime = "";
	switch( length( sDate ) ) 
	{
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
/****************************************************************
* action Name 가져오기  
* @param actionName 현재 처리해야 하는 action name 
* @return 없음
******************************************************************/
function fn_getActionName(actionName) 
{
	if ( length(actionName) > 0 ) 
	{
		if ( actionName == "XA_ACT") 
		{
			if( G_SYSTEM == "P" ) 
			{
				return "XA_PRD_ACT";
			} 
			else if( G_SYSTEM == "Q" ) 
			{
				return "XA_HEQ_ACT";
			} 
			else 
			{
				return "XA_ACT";
			}
		} 
		else 
		{
			return actionName;
		}	
	} 
	else 
	{
		// 시스템에 따라 분리 
		if( G_SYSTEM == "P" ) 
		{
			return "PRD_ACT";
		} 
		else if( G_SYSTEM == "Q" ) 
		{
			return "HEQ_ACT";
		} 	
	}

	return "defaultAction";
}
/******************************************************
 * 서버에서 로그인 끈어졌을 경우 처리 
 * 해당 스크립트의 재정의를 원할 경우 
 * tit_comm_0001.js에서 해당 메소드를 삭제하고 동일한 명의 메소드를 
   별도의 공통 스크립트 파일에 정의해서 사용한다. 
 ******************************************************/
//function fn_notLogin() {
//    alert("로그인을 먼저 처리해 주십시오.");
//}
/******************************************************
 * 서버에 전송할 Global 변수 정의 
******************************************************/
//function fn_getGlobalVariable() {
	
//}

/******************************************************
 * 서버에서 에러 메시지 수신 시 처리 
******************************************************/
//function fn_showSysError(sMsg, sType) {

//}

/*************************************
* UTF-8 Encode
**************************************/
var unreserved = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.~";
var reserved = "!*'();:@&=+$,/?%#[]";
var hexchars = "0123456789ABCDEFabcdef";

function gethex(decimal) 
{
  return "%" + charAt(hexchars,ext_getBitCalc(">>",decimal,4)) + 
               charAt(hexchars,ext_getBitCalc("&",decimal,15));
}

function urlEncodeUtf8(decoded) 
{
  // Some variables:
	var encoded = "";
	
	// ---------------- If UTF-8 character encoding was chosen: ----------------
	
	for (var i = 0; i < length(decoded); i++ ) 
	{
		var ch = charAt(decoded,i);
		// Check if character is an unreserved character:
		if (indexOf(unreserved,ch) != -1) {
			encoded = encoded + ch;
		} else {

			// The position in the Unicode table tells us how many bytes are needed.
			// Note that if we talk about first, second, etc. in the following, we are
			// counting from left to right:
			//
			//   Position in   |  Bytes needed   | Binary representation
			//  Unicode table  |   for UTF-8     |       of UTF-8
			// ----------------------------------------------------------
			//     0 -     127 |    1 byte       | 0XXX.XXXX
			//   128 -    2047 |    2 bytes      | 110X.XXXX 10XX.XXXX
			//  2048 -   65535 |    3 bytes      | 1110.XXXX 10XX.XXXX 10XX.XXXX
			// 65536 - 2097151 |    4 bytes      | 1111.0XXX 10XX.XXXX 10XX.XXXX 10XX.XXXX
	
			var charcode = Asc(charAt(decoded,i));
	
			// Position 0 - 127 is equal to percent-encoding with an ASCII character encoding:
			if (charcode < 128) {
			  encoded = encoded + gethex(charcode);
			}
	
			// Position 128 - 2047: two bytes for UTF-8 character encoding.
			if (charcode > 127 && charcode < 2048) {
			  // First UTF byte: Mask the first five bits of charcode with binary 110X.XXXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc(">>",charcode,6),12));
			  // Second UTF byte: Get last six bits of charcode and mask them with binary 10XX.XXXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc("&",charcode,63),128));
			}
	
			// Position 2048 - 65535: three bytes for UTF-8 character encoding.
			if (charcode > 2047 && charcode < 65536) {
			  // First UTF byte: Mask the first four bits of charcode with binary 1110.XXXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc(">>",charcode,12),224));
			  // Second UTF byte: Get the next six bits of charcode and mask them binary 10XX.XXXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc("&",ext_getBitCalc(">>",charcode,6),63),128));
			  // Third UTF byte: Get the last six bits of charcode and mask them binary 10XX.XXXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc("&",charcode,63),128));
			}
	
			// Position 65536 - : four bytes for UTF-8 character encoding.
			if (charcode > 65535) {
			  // First UTF byte: Mask the first three bits of charcode with binary 1111.0XXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc(">>",charcode,18),240));
			  // Second UTF byte: Get the next six bits of charcode and mask them binary 10XX.XXXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc("&",ext_getBitCalc(">>",charcode,12),63),128));
			  // Third UTF byte: Get the last six bits of charcode and mask them binary 10XX.XXXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc("&",ext_getBitCalc(">>",charcode,6),63),128));
			  // Fourth UTF byte: Get the last six bits of charcode and mask them binary 10XX.XXXX:
			  encoded = encoded + gethex(ext_getBitCalc("|",ext_getBitCalc("&",charcode,63),128));
			}
		}

	}  // end of for ...
	return encoded;
}


