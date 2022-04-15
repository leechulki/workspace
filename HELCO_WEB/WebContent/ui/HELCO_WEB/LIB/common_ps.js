﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 설치  도면  PDF 업로드 /다운로드  Script
 *  작성일자 : 2017/12/13
 *  버전 : 1.0
 *  변경 이력
    일자            작성자          비고
    ------------------------------------------------------------

================================================================================*/
/******************************************************
 *  서버로 PDF파일 전송 처리
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
var GF_FILE_UPLOAD_SIZE_PS = 104857600;   // 1M : 1048576;
var GF_FILE_DOWNLOAD_SIZE_PS = 104857600;
function tit_fileWrite_ps(httpfileObj
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
    //trace(size);
    //trace(GF_FILE_UPLOAD_SIZE_PS);


    if ( size > GF_FILE_UPLOAD_SIZE_PS ) {
        rMsg[0] = "E";
       rMsg[1] = "파일은 최대 " + ( GF_FILE_UPLOAD_SIZE_PS / 1024 ) + "KB 를 초과할 수 없습니다.";
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

    //trace(fileSize);
    if ( fileSize > GF_FILE_UPLOAD_SIZE_PS ) {
        rMsg[0] = "E";
        rMsg[1] = "파일 사이즈는 최대 100M까지만 가능합니다.";
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
             sendSize = httpfileObj.WriteFile(GF_FILE_UPLOAD_SIZE_PS);
             totSize += sendSize;
             if( sendSize < GF_FILE_UPLOAD_SIZE_PS ) {
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
function tit_fileRead_ps(httpfileObj, serverSaveDir, serverFileName, localFilePath, localFileName){
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
             readSize = httpfileObj.ReadFile(GF_FILE_DOWNLOAD_SIZE_PS);
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

function tit_fileReadFromFtpServer(httpfileObj, serverSaveDir, serverFileName, localFilePath, localFileName) {
    var rMsg = Array(2);    // 처리 결과 전송
    if (length(serverSaveDir) == 0 || length(serverFileName) == 0) {
        rMsg[0] = "E";
        rMsg[1] = "서버에서 확인할 파일명이 존재하지 않습니다.";
        return rMsg;
    }

    if (right(localFilePath, 2) != "\\") {
        localFilePath = localFilePath + "\\";
    }

    var fileUrl = localFilePath + localFileName;
    var sendStr = serverSaveDir + "|" + UrlEncode(serverFileName);
    var serverUrl = GF_FILE_SERVER_URL + 'FtpFileDownloader' + "?mode=download&path=" + serverSaveDir + '&name=' + serverFileName;
    //trace('fileUrl : ' + fileUrl);
    //trace('sendStr : ' + sendStr);
    //trace('serverUrl : ' + serverUrl);
    httpfileObj.CookieParam = sendStr;//UrlEncode(sendStr); alert(sendStr);          // 전송할 정보

    var rtn = httpfileObj.OpenFile(serverUrl, fileUrl, "GET");

    if (rtn < 0) {
        rMsg[0] = "E";
        rMsg[1] = "서버 파일 다운로드 실패::" + httpfileObj.ErrorMsg;
        return rMsg;
    }

    var readSize = -1;
    while (1) {
        readSize = httpfileObj.ReadFile(GF_FILE_DOWNLOAD_SIZE);
        if (readSize == 0 || readSize == -1) {
            break;
        }
    }

    httpfileObj.CloseFile();
    if (readSize < 0) {
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
    for (var i = 0; i < length(serverRtn); i++) {
        if (charAt(serverRtn, i) == "|") {
            rArr[cnt] = substr(serverRtn, prevPos, (i - prevPos));
            prevPos = i + 1;
            cnt++;
        }
    }
    return rArr;
}

var _CATE_LIST = '';
var _FNAME_LIST = '';
var _FPATH_LIST = '';
var _UDATE_LIST = '';
var _startTime = 0;
function tit_fileListFromFtpServer(cate, path)
{
    if(_startTime != 0 && (timem() - _startTime) > 1000) {
        trace("####중복호출 : tit_fileListFromFtpServer");
        return;
    }
    _startTime = timem();

    SetWaitCursor(true);
    SetCapture();

    // Argument 처리
    //var arg = "actionName=" + quote( 'defaultAction' );
    //arg += " cmd=" + quote('execute');
    //arg += ' path=' + quote(path);
    // global variable 처리
    //arg += " " + fn_getGlobalVariable();

    // log id 처리
    //var clientId = ext_GetIPAddress();
    //clientId = replace(clientId, "[" , "");
    //clientId = replace(clientId, "]" , "");
    //arg += " titLogId=" + quote(G_USER_ID + "_" + clientId );

    //trace('[tit_fileListFromFtpServer] arg : ' + arg);

    // Application 그룹별 서버 접속 Url 가져오기
    // 2015.12.29 서비스 모니터링 시 호출 구분자 추가
    //var serverUrl = GF_DEFAULT_SERVER + "::Main" + "?ACT="+ actionName +"&CMD=" + cmdName;
    var serverUrl = GF_FILE_SERVER_URL + 'FtpFileDownloader' + "?mode=list&path=" + path + '&cate=' + cate;
    //trace('[tit_fileListFromFtpServer] serverUrl : ' + serverUrl);

    // 처리중입니다. 메시지 처리
    fn_createWait("",this);

    // 동기식 설정
    http.sync = true;

    Transaction(
        '',         // parameter
        serverUrl,  // server url
        '',         // in data
        '',    // out data
        '',        // argument
        "_fn_result");

    //trace('[tit_fileListFromFtpServer] _FNAME_LIST : ' + _FNAME_LIST);
    //trace('[tit_fileListFromFtpServer] _CATE_LIST : ' + _CATE_LIST);
    //trace('[tit_fileListFromFtpServer] _FPATH_LIST : ' + _FPATH_LIST);
    //trace('[tit_fileListFromFtpServer] _UDATE_LIST : ' + _UDATE_LIST);

    // 동기식 설정해제
    http.sync = false;
}
