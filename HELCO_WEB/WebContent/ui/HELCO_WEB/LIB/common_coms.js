﻿﻿var FILE_UPLOAD_TYPE = "insert";
var FILE_UPLOAD_COL_SPLITTER = ";";
var FILE_UPLOAD_ROW_SPLITTER = "||";
var FILE_UPLOAD_VAL_SPLITTER = "=";
var FILE_UPLOAD_PARSE_PATTERN1 = "<param id=\"ErrorMsg\" type=\"STRING\">";
var FILE_UPLOAD_PARSE_PATTERN2 = "</param>";

//var G_COMS_SERVER 		= "http://localhost:7001/";
//var G_SVC_FILE_DM	   	= "E-COMS/ecoms/file/control/"; //"file";
//var G_SVC_COMM_FILE_UPLOAD_URL 		= G_COMS_SERVER + G_SVC_FILE_DM + "fileUpload.do";
var G_COMS_SERVER 		= "SVC";
var G_SVC_FILE_DM       = "file/fileUpload";
var G_SVC_COMM_FILE_UPLOAD_URL 		= G_COMS_SERVER + G_SVC_FILE_DM;
var G_SVC_COMM_FILE_DOWNLOAD_URL	= G_COMS_SERVER + G_SVC_FILE_DM + "fileDownload.do";


function gfn_CheckCompMandatory(arrItem)
{
	if( length(trim(arrItem)) == 0 ) return false;

	var colid = "";
	var col = -1;
	var text = "";
	var arrTemp = null;
	var comp = null;
	var text = "";
	var value = "";

	for (var i=0; i<bound(arrItem); i++)
	{
		arrTemp = split(arrItem[i], ":", "webStyle");
		obj     = object(arrTemp[0]);
		text    = arrTemp[1];

		if (obj == null) continue;

		_type = obj.GetType();

		if (_type== "Checkbox" 	|| _type == "Combo"  ||
			_type == "Calendar" || _type == "CalendarEx" ||
			_type == "Radio" 	|| _type == "Spin" ||
			_type == "Static"   || _type == "MaskEdit")
		{
			value = obj.Value;
		} else {
			value = obj.Text;
		}

		if (length(trim(value)) == 0)
		{
			if (_type== "Checkbox" 	|| _type == "Combo" ||
				_type == "Calendar" || _type == "CalendarEx" ||
				_type == "Spin")
			{
				alert(text + "은(는) 필수입력 항목입니다.");
			} else {
				alert(text + "은(는) 필수입력 항목입니다.");
			}

			obj.SetFocus();

			return false;
		}
	}

	return true;
}



function gfn_CheckToFromDate(fromDate, toDate)
{
//alert(fromDate +"::"+toDate);
	var stVal = fromDate.Value;
	var clsVal = toDate.Value;

	if(gfn_IsNull(stVal)){
		alert("시작 입력일이 없습니다.");
		fromDate.SetFocus();
		return false;
	} else if(gfn_IsNull(clsVal)){
		alert("종료 입력일이 없습니다.");
		toDate.SetFocus();
		return false;
	} else if(stVal > clsVal) {
		alert("시작입력일이 종료입력일 보다 큽니다.");
		fromDate.SetFocus();
		return false;
	}
	return true;
}

function gfn_IsNull(strValue)
{
	if (strValue == null || length(trim(strValue)) == 0)
		return true;
	else
		return false;
}

//////////////////////////////////////////////////////


function gfn_CommonComboString(){
	var strVal = "";
	for(var row = 0 ; row < dsCommonCombo.rowcount; row ++ ){

		strVal += dsCommonCombo.GetColumn(row, "DS_NAME") + "=" + dsCommonCombo.GetColumn(row, "DS_NAME");
		strVal += " ";
	}
	//trace("[strInDs :: DS <= VO] " + strVal );
	return strVal;
}

function gfn_LoadComCode(dsComCodeObj)
{
	var dsObj 	= "";
	var cmbObj 	= "";
	var sFilter 	= "";
	for(var row = 0 ; row < dsComCodeObj.rowcount ; row++) {

		dsObj 	= object(dsComCodeObj.GetColumn(row, "DS_NAME"));
		cmbObj 	= object(dsComCodeObj.GetColumn(row, "SVC_COMBO"));

		sFilter = "GROUPCODE = '" + dsComCodeObj.GetColumn(row, "GROUP_CODE") + "'";

		//trace("[sFilter]::"+sFilter);

		GDS_COMCODE.Filter(sFilter);
		dsObj.CopyF(GDS_COMCODE);
		GDS_COMCODE.UnFilter();

		//trace("[SaveXML]::"+dsObj.SaveXML());

		dsObj.FireEvent = false;
		dsObj.InsertRow(0);
		dsObj.SetColumn(0, "CODE", dsComCodeObj.GetColumn(row, "ADD_CODE") );
		dsObj.SetColumn(0, "NAME", dsComCodeObj.GetColumn(row, "ADD_NAME") );
		dsObj.FireEvent = true;

		cmbObj.CodeColumn 	= "CODE";
		cmbObj.DataColumn   = "NAME";
		cmbObj.DisplayRowCnt= "5";
		cmbObj.Editable		= "false";
		cmbObj.ImeMode 		= "none";
		cmbObj.InnerDataset	= dsComCodeObj.GetColumn(row, "DS_NAME");
		cmbObj.Index 		= 0;

	} // End for loop
}

function gfn_Dialog(strPrefix,strFormID,strInArgument,nWidth,nHeight,strOpenStyle,nLeft,nTop, strTitle)
{

	var strURL 		= strPrefix + "::" + strFormID + ".xml";

	strInArgument += " G_FORMID=" 		+ quote(strFormID);
	strInArgument += " G_FORMNAME=" 	+ quote(strTitle);

	if(nLeft.trim().length() == 0) nLeft = -1;
	if(nTop.trim().length() == 0) nTop = -1;

	//trace("strInArgument : " + strInArgument);

	var return_val = Dialog(strURL,strInArgument,nWidth,nHeight,strOpenStyle,nLeft,nTop);

	//trace(return_val);

	return return_val;
}

function gfn_GetMtProgcd(dsComCodeObj)
{
	var dsObj 	= "";
	var cmbObj 	= "";
	var sFilter 	= "";
	for(var row = 0 ; row < dsComCodeObj.rowcount ; row++) {

		dsObj 	= object(dsComCodeObj.GetColumn(row, "DS_NAME"));
		cmbObj 	= object(dsComCodeObj.GetColumn(row, "SVC_COMBO"));

		sFilter = "sysCd = '" + dsComCodeObj.GetColumn(row, "GROUP_CODE") + "'";

		//trace("[gfn_GetMtProgcd :: sFilter]::"+sFilter);

		GDS_MT_PROGCD.Filter(sFilter);
		dsObj.CopyF(GDS_MT_PROGCD);
		GDS_MT_PROGCD.UnFilter();

		//trace("[SaveXML]::"+dsObj.SaveXML());

		dsObj.FireEvent = false;
		dsObj.InsertRow(0);
		dsObj.SetColumn(0, "progCd", dsComCodeObj.GetColumn(row, "ADD_CODE") );
		dsObj.SetColumn(0, "progNm", dsComCodeObj.GetColumn(row, "ADD_NAME") );
		dsObj.FireEvent = true;

		cmbObj.CodeColumn 	= "progCd";
		cmbObj.DataColumn   = "progNm";
		cmbObj.DisplayRowCnt= "5";
		cmbObj.Editable		= "false";
		cmbObj.ImeMode 		= "none";
		cmbObj.InnerDataset	= dsComCodeObj.GetColumn(row, "DS_NAME");
		cmbObj.Index 		= 0;

	} // End for loop
}


function gfn_GetMtDivision(dsDataSetNm, cmbObjNm, initWord)
{

	var sFilter 	= "";
	var dsObj 	= object(dsDataSetNm);
	var cmbObj 	= object(cmbObjNm);

	dsObj.Copy(GDS_MT_DIVISION);

	dsObj.FireEvent = false;
	dsObj.InsertRow(0);
	dsObj.SetColumn(0, "divCd", "");
	dsObj.SetColumn(0, "divNm", "- " + initWord + " -" );
	dsObj.FireEvent = true;

	if(cmbObj != null) {
	    cmbObj.CodeColumn 	= "divCd";
	    cmbObj.DataColumn   = "divNm";
	    cmbObj.DisplayRowCnt= "5";
	    cmbObj.Editable		= "false";
	    cmbObj.ImeMode 		= "none";
	    cmbObj.InnerDataset	= dsDataSetNm;
	    cmbObj.Index 		= 0;
	}
	//trace("[gfn_GetMtDivision :: SaveXML]::"+dsObj.SaveXML());

}

function gfn_GetMtAppend(progId, corpInYn, corpViewYn, dsDataSetNm, cmbObjNm, initWord)
{
	var dsObj 	= object(dsDataSetNm);
	var cmbObj 	= object(cmbObjNm);
	var sFilter = iif(progId != null and progId != "", "progId = '" + progId + "'", "");

	if(corpInYn != null && length(corpInYn) > 0 ) {
		if(length(sFilter) > 0)
			sFilter += " && ";

		sFilter += "corpInYn = '" + corpInYn + "'";
	}

	if(corpViewYn != null && length(corpViewYn) > 0 ) {
		if(length(sFilter) > 0)
			sFilter += " && ";

		sFilter += "corpViewYn = '" + corpViewYn + "'";
	}

	//sFilter += iif(length(sFilter) == 0, "progId != 'CORP'", " && progId != 'CORP'");

	GDS_MT_APPEND.Filter(sFilter);
	dsObj.CopyF(GDS_MT_APPEND);
	GDS_MT_APPEND.UnFilter();

	dsObj.FireEvent = false;
	dsObj.InsertRow(0);
	dsObj.SetColumn(0, "appendCd", "");
	dsObj.SetColumn(0, "appendNm", "- " + initWord + " -" );
	dsObj.FireEvent = true;

	if(cmbObj != null) {
		cmbObj.CodeColumn 	= "appendCd";
		cmbObj.DataColumn   = "appendNm";
		cmbObj.DisplayRowCnt= "5";
		cmbObj.Editable		= "false";
		cmbObj.ImeMode 		= "none";
		cmbObj.InnerDataset	= dsDataSetNm;
		cmbObj.Index 		= 0;
	}
	//trace("[gfn_GetMtAppend :: SaveXML]::"+dsObj.SaveXML());

}

function gfn_GetMtProcess(divCd, dsDataSetNm, cmbObjNm, initWord)
{

	var sFilter 	= "";
	var dsObj 	= object(dsDataSetNm);
	var cmbObj 	= object(cmbObjNm);

	if(divCd != "9999"){
		sFilter = "divCd = '" + divCd + "'";
	}


	//trace("[sFilter]::"+sFilter);

	GDS_MT_PROCESS.Filter(sFilter);
	dsObj.CopyF(GDS_MT_PROCESS);
	GDS_MT_PROCESS.UnFilter();

	//trace("[SaveXML]::"+dsObj.SaveXML());

	dsObj.FireEvent = false;
	dsObj.InsertRow(0);
	dsObj.SetColumn(0, "processCd", "");
	dsObj.SetColumn(0, "processNm", "- " + initWord + " -" );
	dsObj.FireEvent = true;

	cmbObj.CodeColumn 	= "processCd";
	cmbObj.DataColumn   = "processNm";
	cmbObj.DisplayRowCnt= "10";
	cmbObj.Editable		= "false";
	cmbObj.ImeMode 		= "none";
	cmbObj.InnerDataset	= dsDataSetNm;
	//cmbObj.Index 		= 0;

	//trace("[gfn_GetMtProcess :: SaveXML]::"+dsObj.SaveXML());

}

function gfn_GetMtWorks(levelNo, pworksCd, dsDataSetNm, cmbObjNm, initWord)
{

	var sFilter 	= "";
	var dsObj 	= object(dsDataSetNm);
	var cmbObj 	= object(cmbObjNm);

	if(pworksCd == "9999"){
		sFilter = "levelNo = '" + levelNo + "'";
	} else {
		sFilter = "levelNo = '" + levelNo + "' && pworksCd = '" + pworksCd + "'";
	}


	//trace("[sFilter]::"+sFilter);

	GDS_MT_WORKS.Filter(sFilter);
	dsObj.CopyF(GDS_MT_WORKS);
	GDS_MT_WORKS.UnFilter();

	//trace("[SaveXML]::"+dsObj.SaveXML());

	dsObj.FireEvent = false;
	dsObj.InsertRow(0);
	dsObj.SetColumn(0, "worksCd", "");
	dsObj.SetColumn(0, "worksNm", "- " + initWord + " -" );
	dsObj.FireEvent = true;

	cmbObj.CodeColumn 	= "worksCd";
	cmbObj.DataColumn   = "worksNm";
	cmbObj.DisplayRowCnt= "5";
	cmbObj.Editable		= "false";
	cmbObj.ImeMode 		= "none";
	cmbObj.InnerDataset	= dsDataSetNm;
	cmbObj.Index 		= 0;

	//trace("[gfn_GetMtWorks :: SaveXML]::"+dsObj.SaveXML());

}

//데이터셋 내에서 선택된 Row 의 위치를 위, 아래로 변경해주는 함수
//파라미터 => ds : 해당데이터셋, row1 : 선택된 Row, row2 : 변경될 Row
function gfn_ChangeRowPosition(ds, row1, row2)
{
	ds.FireEvent = false;
	for(var col = 0 ; col < ds.colcount ; col++) {
		var colId = ds.GetColID(col);

		var colVal1 = ds.GetColumn(row1, colId);
		var colVal2 = ds.GetColumn(row2, colId);

		ds.SetColumn(row2, colId, colVal1);
		ds.SetColumn(row1, colId, colVal2);
	}
	ds.row = row2;
	ds.FireEvent = true;
}

//파일대화창에서 선택한 파일목록을 파일데이터셋에 설정해주는 함수
//파라미터 => fdFileUpload : File Dialog 객체, dsFileUpload : 파일데이터셋
function gfn_openFileDialog(fdFileUpload, dsFileUpload, dsRow)
{
	var FileFilter = "All Files(*.*)|*.xls; *.xlsx; *.doc; *.docx; *.gul; *.hwp; *.ppt; *.pptx; *.pdf; *.jpg; *.gif; *.bmp; *.zip; *.txt; *.eml|Excel Files (*.xls)|*.xls; *.xlsx|MS-Word Files (*.doc)|*.doc; *.docx|훈민정음 Files(*.gul)|*.gul|HWP Files(*.hwp)|*.hwp|MS-Powerpoint Files*.ppt)|*.ppt; *.pptx|Acrobet Reader(*.pdf)|*.pdf|Image Files(*.jpg/gif/bmp)|*.jpg; *.gif; *.bmp|Zip Files(*.zip)|*.zip|Etc Files(*.txt, *.eml)|*.txt; *.eml||";

	fdFileUpload.Filter = FileFilter;

	if(!fdFileUpload.Open()) return;
	var strFilePath = fdFileUpload.FilePath + "\\";

	var array = Array();
	if(fdFileUpload.MultiSelect)
		array = fdFileUpload.FileNameList;
	else
		array[0] = fdFileUpload.FileName;

	var nRow;
	for(i = 0; i < array.length; i++) {

		if(dsRow != null)
			nRow = dsRow;
		else
			nRow = dsFileUpload.addRow();

		dsFileUpload.SetColumn(nRow, "ZATTPATH", strFilePath + "\\" + array[i]);
		dsFileUpload.SetColumn(nRow, "ZATTFN", array[i]);
	}

	dsFileUpload.row = -1;
}

//파일업로드를 실행해주는 함수
//파라미터 => hfFileUpload : HTTP File 객체, dsFileUpload : 파일데이터셋
function gfn_UploadFile(hfFileUpload, dsFileUpload, sConsCd)
{
	var isUploadCompleted = false;

	hfFileUpload.MultiInit(); // 시작전에 초기화 처리

	var nFileCount = 0;

	for(row = 0; row < dsFileUpload.rowcount; row++) {
	    var strLocalFileName = dsFileUpload.GetColumn(row, "appendLocFileNm");

		if(dsFileUpload.GetRowType(row) == FILE_UPLOAD_TYPE and (strLocalFileName != null and strLocalFileName != "")) {
			hfFileUpload.MultiAddArguments(sConsCd+"_"+row, dsFileUpload.GetColumn(row, "appendLocFileNm"), true); // 파일:true
			nFileCount++;
		}
	}

	// 업로드할 파일이 존재하지 않을 경우에는 true 를 반환합니다.
	if(nFileCount == 0) return true;

	//파일 업로드-----------------------------------------------------------
	var bRtn = hfFileUpload.MultiRequest(G_SVC_COMM_FILE_UPLOAD_URL);
	//----------------------------------------------------------------------

	//호출 성공 여부
	if(bRtn == 1) {
		// 서버의 성공/실패 결과값을 받고싶으면 ErrorMsg로 전송받아 처리한다.
		gfn_ReceiveFileUploadResult(hfFileUpload.ErrorMsg, dsFileUpload);
		isUploadCompleted = true;
	}

	return isUploadCompleted;
}

function gfn_UploadFiles(hfFileUpload, dsFileUpload, strCons_Cd)
{
	var isUploadCompleted = false;

	hfFileUpload.MultiInit(); // 시작전에 초기화 처리

	var nFileCount = 0;
	for(row = 0; row < dsFileUpload.rowcount; row++) {
		//if(dsFileUpload.GetRowType(row) == FILE_UPLOAD_TYPE) {

			hfFileUpload.MultiAddArguments(strCons_Cd + "_" + row, dsFileUpload.GetColumn(row, "ZATTPATH"), true); // 파일:true
			nFileCount++;
		//}
	}

	// 업로드할 파일이 존재하지 않을 경우에는 true 를 반환합니다.
	if(nFileCount == 0) return true;

	//파일 업로드-----------------------------------------------------------
	var bRtn = hfFileUpload.MultiRequest(G_SVC_COMM_FILE_UPLOAD_URL);
	//----------------------------------------------------------------------

	//호출 성공 여부
	if(bRtn == 1) {
		// 서버의 성공/실패 결과값을 받고싶으면 ErrorMsg로 전송받아 처리한다.
		gfn_ReceiveFileUploadResults(hfFileUpload.ErrorMsg, dsFileUpload);
		isUploadCompleted = true;
	}

	return isUploadCompleted;
}


//파일업로드 후 받은 결과를 파일데이터셋에 갱신해주는 함수
//파라미터 => xmlFileInfo : 결과 XML String, dsFileInfo : 결과를 매핑해줄 파일데이터셋
function gfn_ReceiveFileUploadResult(xmlFileInfo, dsFileUpload)
{
	var arFileInfo = gfn_ParseFileUploadResult(xmlFileInfo);

	for(i = 0; i < arFileInfo.length; i++) {
		var arFileFieldInfo = split(arFileInfo[i], FILE_UPLOAD_COL_SPLITTER);
		gfn_SetFileUploadResultToDataset(arFileFieldInfo, dsFileUpload, i);
	}
}

function gfn_ReceiveFileUploadResults(xmlFileInfo, dsFileUpload)
{
	var arFileInfo = gfn_ParseFileUploadResult(xmlFileInfo);

	for(i = 0; i < arFileInfo.length; i++) {
		var arFileFieldInfo = split(arFileInfo[i], FILE_UPLOAD_COL_SPLITTER);

		gfn_SetFileUploadResultToDatasets(arFileFieldInfo, dsFileUpload);
		dsFileUpload.ApplyChange();
	}

}

//파일업로드 후 받은 결과를 배열로 파싱해서 반환해주는 함수
//파라미터 => xmlFileInfo : 결과 XML String
function gfn_ParseFileUploadResult(xmlFileInfo)
{
	var sindex, eindex, lindex;

	sindex = indexOf(xmlFileInfo, FILE_UPLOAD_PARSE_PATTERN1);
	eindex = indexOf(xmlFileInfo, FILE_UPLOAD_PARSE_PATTERN2, sindex);
	sindex = sindex + FILE_UPLOAD_PARSE_PATTERN1.length;
	lindex = eindex - sindex;

	var strFileInfo = substr(xmlFileInfo, sindex, lindex);

	return split(strFileInfo, FILE_UPLOAD_ROW_SPLITTER);
}

//파일업로드 후 받은 결과를 데이터셋에 매핑해주는 함수
//파라미터 => arFileInfo : 결과 배열 String, dsFileUpload : 파일데이터셋, uploadFileRow : 업로드된 파일순서
function gfn_SetFileUploadResultToDataset(arFileInfo, dsFileUpload, uploadFileRow)
{
	var insertRow = 0;
	for(row = 0; row < dsFileUpload.rowcount; row++) {
		if(dsFileUpload.GetRowType(row) == FILE_UPLOAD_TYPE) {
			if(insertRow == uploadFileRow) {
				for(i = 0; i < arFileInfo.length; i++) {
					var arFileValInfo = split(arFileInfo[i], FILE_UPLOAD_VAL_SPLITTER);
					dsFileUpload.SetColumn(row, arFileValInfo[0], arFileValInfo[1]);
				}
				return true;
			}
			insertRow++;
		}
	}
}


function gfn_SetFileUploadResultToDatasets(arFileInfo, dsFileUpload)
{
	var strFileName       = "";
	var strFileNameField  = "";
	var strFileSize       = "";
	var strFileSizeField  = "";
	var strSource         = "";
	var nRow                  ;

	for(i = 0; i < arFileInfo.length; i++) {
		var arFileValInfo = split(arFileInfo[i], FILE_UPLOAD_VAL_SPLITTER);

		if(arFileValInfo[0] == "appendFileNm") {
			strFileNameField = arFileValInfo[0];
			strFileName      = arFileValInfo[1];
		}

		if(arFileValInfo[0] == "appendFileSz") {
			strFileSizeField = arFileValInfo[0];
			strFileSize      = arFileValInfo[1];
		}

		if(arFileValInfo[0] == "sourceFileNm") {
			var nPos = IndexOf(arFileValInfo[1], "_", 0);

			if(nPos == -1) {
				nRow = -1;
			} else {
				nRow = substr(arFileValInfo[1], nPos + 1);
			}
		}
	}

	if(nRow != -1) {
		dsFileUpload.SetColumn(nRow, strFileNameField, strFileName);
		dsFileUpload.SetColumn(nRow, strFileSizeField, strFileSize);
	}

	return true;
}

function gfn_DownloadFile(hfFileDownload, fdFileDownload, dsFileDownload, nRow)
{
	fdFileDownload.FileName = dsFileDownload.GetColumn(nRow, "appendDispNm");
	if(!fdFileDownload.Open()) return;

	var lfilename = fdFileDownload.FilePath + "\\" + fdFileDownload.FileName;
	var sfilename = dsFileDownload.GetColumn(nRow, "appendFileNm");

	hfFileDownload.CookieParam = UrlEncode(sfilename);
	var rtn = hfFileDownload.OpenFile(G_SVC_COMM_FILE_DOWNLOAD_URL + "?filename=" + sfilename, lfilename, "GET");  //다운로드

	var arRtn = Array(2);
	if(rtn < 0) {
		arRtn[0] = "FAIL";
		arRtn[1] = "OpenFile Fail ::" + hfFileDownload.ErrorMsg;
		return arRtn;
	}

	var nReadSize;
	while(1) {
		nReadSize = hfFileDownload.ReadFile(G_SVC_COMM_FILE_DOWNLOAD_SIZE);
		if((nReadSize == 0) || (nReadSize == -1)) break;
	}

	hfFileDownload.CloseFile();

	if(nReadSize == -1)	{
		arRtn[0] = "FAIL";
		arRtn[1] = hfFileDownload.ErrorMsg;
		return arRtn;
	}

	arRtn[0] = "SUCCESS";
	return arRtn;
}

function gfn_DownloadFile2(hfFileDownload, fdFileDownload, locFileName, srcFileName)
{
	fdFileDownload.FileName = locFileName;
	if(!fdFileDownload.Open()) return;

	var lfilename = fdFileDownload.FilePath + "\\" + fdFileDownload.FileName;
	var sfilename = srcFileName;

	hfFileDownload.CookieParam = UrlEncode(sfilename);
	var rtn = hfFileDownload.OpenFile(G_SVC_COMM_FILE_DOWNLOAD_URL + "?filename=" + sfilename, lfilename, "GET");  //다운로드

	var arRtn = Array(2);
	if(rtn < 0) {
		arRtn[0] = "FAIL";
		arRtn[1] = "OpenFile Fail ::" + hfFileDownload.ErrorMsg;
		return arRtn;
	}

	var nReadSize;
	while(1) {
		nReadSize = hfFileDownload.ReadFile(G_SVC_COMM_FILE_DOWNLOAD_SIZE);
		if((nReadSize == 0) || (nReadSize == -1)) break;
	}

	hfFileDownload.CloseFile();

	if(nReadSize == -1)	{
		arRtn[0] = "FAIL";
		arRtn[1] = hfFileDownload.ErrorMsg;
		return arRtn;
	}

	ExecShell(lfilename);

	arRtn[0] = "SUCCESS";

	return arRtn;
}

//그리드 헤더에 위치한 체크박스를 클릭할 경우
// 1)체킹되지 않은 상태에서는 전체항목 체크를
// 2)체킹되어 있는 상태에서는 전체항목 체크해제를 수행하는 함수.
//파라미터 => obj : 그리드 Object, nCell : 그리드내 체크박스 cell index
function gfn_CheckAllItemInGrid(obj, nCell)
{
	var isChk = decode(obj.GetCellProp("Head", nCell, "Text"), "1", "0", "1");
	obj.SetCellProp("Head", nCell, "Text", isChk);

	var dataset = object(obj.BindDataset);
	dataset.FireEvent = false;

	var colId = obj.GetCellProp("Body", nCell, "colId");
	for(row = 0; row < dataset.rowcount; row++)
		dataset.SetColumn(row, colId, isChk);

	dataset.FireEvent = true;
}

function gfn_PrintDataset(dataset)
{
	for(row = 0; row < dataset.rowcount; row++) {
		var dataRow, colId;

		for(col = 0; col < dataset.colcount; col++) {
			colId = dataset.GetColID(col);
			dataRow += dataset.id + "[" + row + "][" + colId + " :: " +dataset.GetColumn(row, colId) + "] ";
		}
		trace(dataRow);
	}
}

function gfn_DateFormat(date)
{
	switch(length(date)) {
		case 6:
				date = substr(date,0,4) + "." + substr(date,4,2);
			break;

		case 8:
				date = substr(date,0,4) + "." + substr(date,4,2) + "." + substr(date,6,2);
			break;
		case 14:
			date = substr(date,0,4) + "." + substr(date,4,2) + "." + substr(date,6,2) + ' ' + substr(date,8,2) + ':' + substr(date,10,2) + ':' + substr(date,12,2);
			break;
	}

	return date;
}

function gfn_DateTimeFormat(dtimes)
{
	switch(length(dtimes)) {
		case 12:
				dtimes = gfn_DateFormat(substr(dtimes,0,8)) + " " + substr(dtimes,8,2) + ":" + substr(dtimes,10,2);
			break;
	}

	return dtimes;
}

function gfn_FormOnKeyDown(obj,objSenderObj,nChar,bShift,bControl,bAlt,nLLParam,nHLParam)
{
	if(nChar == CC_VK_ENTER && objSenderObj.GetType() == CC_CPNT_EDIT) {

		switch(gfnGetFrmDan()) {
			case "2":
					frmMain.div_content.fn_select();
				break;

			case "3":
					frmMain.div_list.fn_select();
				break;
		}
	}
}

function gfn_InitUserNmByAuth()
{
	//메뉴권한에 따라서
	switch(gvMenuRights) {

		case AUTH_CN:	//계약화면일 경우

				//사용자권한이 시행+계약이거나 계약일 경우
				if(gvRights == "AUTH_OP_CN" || gvRights == "AUTH_CN") {
					var userComp  = gfn_GetUserComp(COMP_CN_USER);
					userComp.Text = gfn_GetUserName();

					if(gvRights == "AUTH_OP_CN") {
						userComp.DisableBkColor = "user1";
						userComp.Enable = false;
					}
				}

			break;

		case AUTH_OP:	//시행화면일 경우
				//사용자권한이 시행+계약이거나 시행일 경우
				if(gvRights == "AUTH_OP_CN" || gvRights == "AUTH_OP") {
					var userComp  = gfn_GetUserComp(COMP_OP_USER);
					userComp.Text = gfn_GetUserName();
				}

			break;
	}
}

function gfn_GetUserComp(userAuthComp)
{
	var arCompPrx = ["edt", "ed"];
	var userComp;

	for(i = 0; i < arCompPrx.length; i++) {
		userComp = Find(arCompPrx[i] + userAuthComp);
		if(userComp != null) break;
	}

	return userComp;
}

function gfn_GetUserName()
{
	return GDS_USER_INFO.GetColumn(0, USER_INFO_NAME);
}

function gfn_GetUserInfo(sUserAttrName)
{
	return GDS_USER_INFO.GetColumn(0, sUserAttrName);
}

// 공고문 양식 중 <body></body> 사이의 문자만을 추출하는 함수
function gfn_source_extract(strForm)
{
	var strSource = strForm;
	var strTarger = "";
	var strKey    = "";
	var strIsKey  = "";
	var strEdKey  = "";
	var keyStart  = false;
	var keyFindSt = false;
	var keyFindEd = false;
	var nPos      = 0;

	if (length(strSource) <= 0) return strForm;

	while(nPos < length(strSource)) {

	    strKey   = substr(strSource, nPos, 1);
	    strIsKey = substr(strSource, nPos, 6);
	    strEdKey = substr(strSource, nPos, 7);

	    if(!keyStart) {
	       if(strIsKey == "<body>") {
	           keyStart  = true;
	           keyFindSt = true;

	           nPos      = nPos + 6;
	       }
	       else {
	           nPos = nPos + 1;
	       }
	    } else {
	       if(strEdKey == "</body>") {
			   keyFindEd  = true;

			   break;
	       } else {
			   strTarger = strTarger + strKey;
	           nPos      = nPos + 1;
	       }
	    }
	}

	if(!keyFindSt or !keyFindEd) {
		return "";
	}

	 return strTarger;
}

// 결재현황파일 Add(strForm에 결재현황 양식 strForm_apro를 붙여준다(<div id='APPROVAL'></div>
function gfn_form_apro_add(strSource, strForm_apro)
{
	var strTarger = "";
	var strKey    = "";
	var strIsKey  = "";
	var strEdKey  = "";
	var keyStart  = false;
	var keyFindSt = false;
	var keyFindEd = false;
	var nPos      = 0;

	if(strForm_apro == "" or strForm_apro == null) return strSource;

	if (length(strSource) <= 0) return strSource;

	while(nPos < length(strSource)) {

	    strKey   = substr(strSource, nPos, 1);
	    strIsKey = substr(strSource, nPos, 19);
	    strEdKey = substr(strSource, nPos, 6);

	    if(!keyStart) {
	       if(strIsKey == "<div id='APPROVAL'>") {
	           keyStart  = true;
	           keyFindSt = true;

	           strTarger = strTarger + strIsKey;
	           nPos      = nPos + 19;
	       }
	       else {
	           strTarger = strTarger + strKey;
	           nPos = nPos + 1;
	       }
	    } else {
	       if(strEdKey == "</div>") {
			   keyFindEd  = true;
			   strTarger = strTarger + strForm_apro;
			   strTarger = strTarger + substr(strSource, npos);

			   keyStart = false;

			   break;
	       } else {
	           nPos      = nPos + 1;
	       }
	    }
	}

	if(!keyFindSt or !keyFindEd) {
		return strSource;
	}

	 return strTarger;
}

// 첨부파일 Add(strForm에 첨부파일 양식 strForm_apnd를 붙여준다(<div id='APPEND'></div>
function gfn_form_apnd_add(strSource, strForm_apnd)
{
	var strTarger = "";
	var strKey    = "";
	var strIsKey  = "";
	var strEdKey  = "";
	var keyStart  = false;
	var keyFindSt = false;
	var keyFindEd = false;
	var nPos      = 0;

	if (strSource    == null or strSource    == "") return strSource;
	if (strForm_apnd == null or strForm_apnd == "") return strSource;

	while(nPos < length(strSource)) {
	    strKey   = substr(strSource, nPos, 1);
	    strIsKey = substr(strSource, nPos, 17);
	    strEdKey = substr(strSource, nPos, 6);

	    if(!keyStart) {
	       if(strIsKey == "<div id='APPEND'>") {
			   keyStart  = true;
	           keyFindSt = true;
	           keyFindEd = false;

	           strTarger = strTarger + strIsKey;
	           nPos      = nPos + 17;
	       }
	       else {
	           strTarger = strTarger + strKey;
	           nPos = nPos + 1;
	       }
	    } else {
	       if(strEdKey == "</div>") {
	           keyFindEd  = true;
			   strTarger = strTarger + strForm_apnd;
			   strTarger = strTarger + substr(strSource, npos);

			   break;
	       } else {
	           nPos = nPos + 1;
	       }
	    }
	}

	if(!keyFindSt or !keyFindEd) {
		return strSource;
	}

	return strTarger;
}


// 메일 발송 발송정보(제목)
function gfn_SetMailInfo(Subject, tfHtml, cntsAppend, emailId, Contents, dsMailInfo)
{
	dsMailInfo.ClearData();
	dsMailInfo.AddRow();

	dsMailInfo.SetColumn(0, "subject", Subject);
	dsMailInfo.SetColumn(0, "bHtmlContentCheck", tfHtml);
	dsMailInfo.SetColumn(0, "iFileCount", cntsAppend);
	dsMailInfo.SetColumn(0, "timeZone", "GMT+9");
	dsMailInfo.SetColumn(0, "isDst", "false");
	dsMailInfo.SetColumn(0, "senderMailAddr", emailId);
	dsMailInfo.SetColumn(0, "content", Contents);
	dsMailInfo.SetColumn(0, "locale", "ko_KR");
	dsMailInfo.SetColumn(0, "encoding", "euc-kr");
}

// 화폐단위 숫자
function gfn_Convert_Currency(strNumber)
{
    var retString = "";
    var nLength = lengthb(strNumber);

    for(i=0; i < nLength; i++) {
        var nRest   = (nLength - i + 2) - floor((nLength - i + 2) / 3) * 3;

        retString = retString + mid(strNumber, i, 1);

        if(nRest == 0 and (i + 1) != nLength) {
           retString = retString + ",";
        }
    }

    return retString;
}

// 첨부파일 리스트 생성
function gfn_create_append(dsAppend)
{
    var strFormApnd = "";

    if(dsAppend.rowcount == 0) return "";

    strFormApnd += "<table width='765' border='1' cellpadding='1' cellspacing='0' bordercolor='#dddcdd'>\n";
    strFormApnd += "  <tr>\n";
    strFormApnd += "  	<td width='120' bgcolor='#f3f3f3' class='item'>순번</td>\n";
    strFormApnd += "    <td width='120' bgcolor='#f3f3f3' class='item'>구분</td>\n";
    strFormApnd += "    <td width='523' bgcolor='#f3f3f3' class='item'>첨부파일명</td>\n";
    strFormApnd += "  </tr>\n";

    for(i = 0; i < dsAppend.rowcount; i++) {
		var seqNo            = dsAppend.GetColumn(i, "seqNo"        );
        var appendNm         = dsAppend.GetColumn(i, "appendNm"     );
        var appendFileNm     = dsAppend.GetColumn(i, "appendFileNm" );
        var appendDispNm     = dsAppend.GetColumn(i, "appendDispNm" );

        strFormApnd += "  <tr>\n";
		strFormApnd += "    <td width='120' height='60' class='contents'>" + seqNo    + "</td>\n";
		strFormApnd += "    <td width='120' height='60' class='contents'>" + appendNm + "</td>\n";
		strFormApnd += "    <td class='contents'><a href='#' onclick='fDownload(\""+appendFileNm+"\");'>" + appendDispNm + "</a></td>\n";
		strFormApnd += "  </tr>\n";
    }
	strFormApnd += "</table>\n";

    return strFormApnd;
}

// 첨부파일 리스트 생성(dsAppend : 첨부파일 데이터 셋, strSource : 원본 양식)
function gfn_append_add(dsAppend, strSource)
{
	var strAppend = gfn_create_append(dsAppend);
	return gfn_form_apnd_add(strSource, strAppend);
}

function gfn_SetMail(dsMailInfo, dsRecipientEty, sSubject, dsTarget, dsForm, sGub, sformId)
{
	var bHtmlContentCheck = "false";
	var iFileCount        = 0;
	var timeZone          = "GMT+9";
	var isDst             = "false";
	var senderMailAddr    = gfnGetUserInfo("emailId");
	var content           = gfn_FindContext(dsForm, "MAIL", sformId);
	var locale            = "ko_KR";
	var encoding          = "euc-kr";
	var iSeqID            = 0;
	var recType           = "t";
	var vendorNm          = "";
	var retMessage        = "";

	for(var nRow = 0; nRow < dsTarget.rowcount; nRow ++) {
	    var recAddr           = dsTarget.GetColumn(nRow, "emailId" );
	    //var recAddr           = "dhkim20683@gmail.com";
	    if(sGub == "VND")
			vendorNm = dsTarget.GetColumn(nRow, "vendorNm");
		else
		    vendorNm = dsTarget.GetColumn(nRow, "sawonNm" );

	    //trace(recAddr);
	    if (gfn_CheckEmail(recAddr)) {
			dsMailInfo.AddRow();

			dsMailInfo.SetColumn(dsMailInfo.row, "subject",           sSubject         );
			dsMailInfo.SetColumn(dsMailInfo.row, "bHtmlContentCheck", bHtmlContentCheck);
			dsMailInfo.SetColumn(dsMailInfo.row, "iFileCount",        iFileCount       );
			dsMailInfo.SetColumn(dsMailInfo.row, "timeZone",          timeZone         );
			dsMailInfo.SetColumn(dsMailInfo.row, "isDst",             isDst            );
			dsMailInfo.SetColumn(dsMailInfo.row, "senderMailAddr",    senderMailAddr   );
			dsMailInfo.SetColumn(dsMailInfo.row, "content",           content          );
			dsMailInfo.SetColumn(dsMailInfo.row, "locale",            locale           );
			dsMailInfo.SetColumn(dsMailInfo.row, "encoding",          encoding         );

			dsRecipientEty.AddRow();
			dsRecipientEty.SetColumn(dsRecipientEty.row, "iSeqID",     iSeqID          );
			dsRecipientEty.SetColumn(dsRecipientEty.row, "recType",    recType         );
			dsRecipientEty.SetColumn(dsRecipientEty.row, "recAddr",    recAddr         );
	    } else {
			retMessage += vendorNm + "[" + recAddr + "]\n";
	    }

	}

	return retMessage;
}

function gfn_SendMail(dsMailInfo, sSubject, dsTarget, dsForm, sGub, sformId)
{
	var emailTitle   = sSubject;
    var sendorName   = gfnGetUserInfo("userNm" );
    var senderEmail  = gfnGetUserInfo("emailId");
    var contents     = gfn_FindContext(dsForm, "MAIL", sformId);
    var receiverName ;
    var receiverEmail;
    var retMessage   = "";

    for(var nRow = 0; nRow < dsTarget.rowcount; nRow ++) {
	    var receiverEmail  = dsTarget.GetColumn(nRow, "emailId" );

	    if(sGub == "VND")
			receiverName = dsTarget.GetColumn(nRow, "vendorNm");
		else
		    receiverName = dsTarget.GetColumn(nRow, "sawonNm" );

	    if (gfn_CheckEmail(receiverEmail)) {
			dsMailInfo.AddRow();

			dsMailInfo.SetColumn(dsMailInfo.row, "emailTitle"   , emailTitle       );
			dsMailInfo.SetColumn(dsMailInfo.row, "contents"     , contents         );
			dsMailInfo.SetColumn(dsMailInfo.row, "senderName"   , sendorName       );
			dsMailInfo.SetColumn(dsMailInfo.row, "sendorEmail"  , senderEmail      );
			dsMailInfo.SetColumn(dsMailInfo.row, "receiverName" , receiverName     );
			dsMailInfo.SetColumn(dsMailInfo.row, "receiverEmail", receiverEmail    );
			dsMailInfo.SetColumn(dsMailInfo.row, "spisSendYn"   , "N"              );
	    } else {
			retMessage += receiverName + "[" + receiverEmail + "]\n";
	    }

	}

	return retMessage;
}

function gfn_FindContext(dsForm, formCd, formId)
{
	for(var nRow = 0; nRow < dsForm.rowcount; nRow++) {
		var strFormId  = dsForm.GetColumn(nRow, "formId" );
		var strFormCd  = dsForm.GetColumn(nRow, "formCd" );
		var strContext = dsForm.GetColumn(nRow, "context");

		if (strFormCd == formCd and strFormId == formId) {
			return strContext;
		}
	}

	return "";
}

// 이메일 ID 적합성 검사
function gfn_CheckEmail(sEmail)
{
	var sReturnValue = false;
	var sTmp = "";
	var sRegExp = "[a-z0-9]+[a-z0-9.,]+@[a-z0-9]+[a-z0-9.,]+\\.[a-z0-9]+";

	var regexp = CreateRegExp(sRegExp,"ig");
	sTmp = regexp.Exec(sEmail);

	if ( sTmp == null )
		sReturnValue = false;
	else {
		if ( ( sTmp.index == 0 ) && (sTmp.length == sEmail.length ) )
			sReturnValue = true;
		else
			sReturnValue = false;
	}

	return sReturnValue;
}

// 데이터 셋 단위 메일 체크
function gfn_CheckDutyMail(dsDataSet, argColumn, argDefaultMsg, argColumnMsg)
{
	if(dsDataSet.rowcount == 0) {
		alert(argDefaultMsg);
		return false;
	}

	for(row = 0; row < dsDataSet.rowcount; row++) {
		var sMail = dsDataSet.GetColumn(row, argColumn);

		if(!gfn_CheckEmail(sMail)) {
			alert(argColumnMsg);
			dsDataSet.row = row;
			return false;
		}
	}

	return true;
}

// SMS 메시지 발송 데이터 셋 설정
function gfn_SMS_SetData(dsSms, dsVendor, sMessage, sProgId)
{
	var strProgId  = "";
	var retMessage = "";

	if(sProgId == "COST")      strProgId = "CO";
	else if(sProgId == "BIDD") strProgId = "BD";
	else if(sProgId == "CONT") strProgId = "CT";
	else if(sProgId == "COMP") strProgId = "CP";
	else if(sProgId == "CORP") strProgId = "CR";
	else if(sProgId == "SAMT") strProgId = "SM";
	else if(sProgId == "CONS") strProgId = "CS";
	else strProgId = "CC";

	dsSms.ClearData();

	for(var nRow = 0; nRow < dsVendor.rowcount; nRow++) {
	    var hTelNo   = dsVendor.GetColumn(nRow, "htelNo"  );
	    var vendorNm = dsVendor.GetColumn(nRow, "vendorNm");

	    if(length(gfn_CheckHPhone(hTelNo)) != 0) {
			dsSms.AddRow();
			dsSms.setColumn(dsSms.row, "smsNo"      , GetDate()+lpad(nRow, "0", 3));
			dsSms.setColumn(dsSms.row, "receiver"   , hTelNo                      );
			dsSms.setColumn(dsSms.row, "sender"     , gfnGetUserInfo("telNo")     );
			dsSms.setColumn(dsSms.row, "reserveTime", "00000000000000"            );
			dsSms.setColumn(dsSms.row, "message"    , sMessage                    );
			dsSms.setColumn(dsSms.row, "codeDiv"    , "COM"                       );
			dsSms.setColumn(dsSms.row, "codeUse"    , strProgId                   );
			dsSms.setColumn(dsSms.row, "sendYn"     , "N"                         );
		} else {
			retMessage += vendorNm + "[" + hTelNo + "]\n";
		}
	}

	return retMessage;
}

function gfn_CheckHPhone(shTelNo)
{
	var shtel    = trim(replace(shTelNo, "-", ""));
	var retValue = "";

	if(length(shtel) >= 10 and length(shtel) <= 11) {
		var recNo = substr(shtel, 0, 3);
		var tNumb = substr(shtel, 3);

		if(recNo == "011" or recNo == "019" or recNo == "018" or
		   recNo == "017" or recNo == "016" or recNo == "010"   ) {

		   if(length(tNumb) == 7) {
		       retValue = recNo + "-" + substr(tNumb, 0, 3) + "-" + substr(tNumb, 3);
		   } else {
		       retValue = recNo + "-" + substr(tNumb, 0, 4) + "-" + substr(tNumb, 4);
		   }
		} else {
			retValue = "";
		}
	} else {
		retValue = "";
	}

	return retValue;
}
