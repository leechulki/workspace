﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*********************************************************************
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
	
	//alert(nAreaStartRow+":"+nAreaEndRow+":"+nAreaStartCol+":"+nAreaEndCol);

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
		//nAreaEndCol   = objGrid.GetAreaEndCol();  // grid패치 이전 복사 기능 오류 수정 최신에서 사용가능
		nAreaEndCol   = objGrid.AreaEndCol;

	} 
	else 
	{
		nAreaStartRow = objGrid.CurrentRow;
		nAreaEndRow   = objGrid.CurrentRow;
		nAreaStartCol = objGrid.CurrentCell;
		nAreaEndCol   = objGrid.CurrentCell;     // 구버전 그리드
//		nAreaEndCol   = objGrid.CurrentCell+1;   그리드 신버전인 경우

		// for(var nRow = nAreaStartRow; nRow <= nAreaEndRow; nRow++) 
		// {
			// for(var nCell = nAreaStartCol; nCell <= nAreaEndCol; nCell++) 
			// {
				// if(objGrid.GetColProp(nCell,"Width") > 0) 
				// {
					// strColID = objGrid.GetCellProp("body",nCell,"colid");
					// strValue = orgDataset.GetColumn(nRow,strColID);
					// strClipboard = strClipboard + strValue + strSeparator;
				// }
			// }
	
			// strClipboard = substr(strClipboard,0,length(strClipboard)-1);
			// strClipboard = strClipboard + "\n";
		// }

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
/*********************************************************************
 * 기능 : Clipboard에 Copy된 내용을 그리드의 선택된 영역에 Paste 한다.
 * 인수 : objGrid       : Area Select 된 Grid
         strSeparator  : Colunm 구분자.
 * 예제 : grd_fn_ClipboardPaste(objGrid, ",");
 *********************************************************************/
function grd_fn_CellPaste(objGrid, orgDataset) 
{
	grd_fn_ClipboardPaste(objGrid, orgDataset, "	");
}

function grd_fn_ClipboardPaste(objGrid, orgDataset, strSeparator) 
{
//	var orgDataset = eval(objGrid.BindDataset);

	var nSearchRow = 0;
	var nSearchCol = 0;
	var strColID;
	var strValue;
	var strBgColor;

	// 숫자 자릿수 구분은 comma 를 사용하기 때문에 호환을 위해서 comma 를 제거한다.
	var strClipboardData = replace(GetClipBoard("CF_UNICODETEXT"),",","");

	if(strSeparator != " ") 
	{
		// 유럽의 숫자 자릿수 구분은 space 를 사용하기 때문에 호환을 위해서 space 를 제거한다.
		strClipboardData = replace(strClipboardData," ","");
	}

	var strClipboardRecord = split(strClipboardData,"\n","webstyle");
	var strClipboardColunm;

	var nAreaStartRow;
	var nAreaEndRow;
	var nAreaStartCol;
	var nAreaEndCol;

	if(objGrid.AreaSelect == true) 
	{
		nAreaStartRow = objGrid.GetAreaStartRow();
		nAreaEndRow   = objGrid.GetAreaEndRow();
		nAreaStartCol = objGrid.GetAreaStartCol();
		nAreaEndCol   = objGrid.GetAreaEndCol();
	} 
	else 
	{
		nAreaStartRow = objGrid.CurrentRow;
		nAreaEndRow   = objGrid.CurrentRow;
		nAreaStartCol = objGrid.CurrentCell;
		nAreaEndCol   = objGrid.CurrentCell+1;
	}

	for(var nRow = nAreaStartRow; nRow < (nAreaStartRow + length(strClipboardRecord)); nRow++) 
	{
		strClipboardColunm = split(strClipboardRecord[nSearchRow],strSeparator,"webstyle");

		nSearchCol = 0;
		var nAreaCell = nAreaStartCol + length(strClipboardColunm);
		for(var nCell = nAreaStartCol; nCell < nAreaCell && nCell < objGrid.GetColCount(); nCell++) 
		{
			if(objGrid.GetColProp(nCell,"Width") > 0) 
			{
				strColID = objGrid.GetCellProp("body",nCell,"colid");
				strBgColor = orgDataset.GetColumn(nRow,"BGCOLOR"+strColID);

				strValue = trim(strClipboardColunm[nSearchCol]);
				orgDataset.SetColumn(nRow,strColID,strValue);

				nSearchCol++;

			} 
			else 
			{
				nAreaCell++;
			}
		}

		nSearchRow++;
	}

	return;
}

/*********************************************************************
 * 기능 : 그리드의 선택된 영역을 Reset 한다.
 * 인수 : objGrid     : Area Select 된 Grid
 * 예제 : grd_fn_CellReset(objGrid);
 *********************************************************************/
function grd_fn_CellReset(objGrid, orgDataset) 
{
//	var objDataset = eval(objGrid.BindDataset);

	var strColID;

	var nAreaStartRow = objGrid.GetAreaStartRow();
	var nAreaEndRow   = objGrid.GetAreaEndRow();
	var nAreaStartCol = objGrid.GetAreaStartCol();
	var nAreaEndCol   = objGrid.GetAreaEndCol();

	var strRowTitle;

	for(var nRow = nAreaStartRow; nRow <= nAreaEndRow; nRow++) 
	{
		for(var nCell = nAreaStartCol; nCell < nAreaEndCol; nCell++) 
		{
			strColID = objGrid.GetCellProp("body",nCell,"colid");
			objDataset.SetColumn(nRow, strColID, objDataset.GetOrgColumn(nRow, strColID));
		}
	}
}

/*********************************************************************
* 기능 : 그리드 Excel Export 시에 Confidential 문장 출력 
* 인수 : strGridContents
* 예제 : grd_fn_SetConfidential(strGridContents);
 *********************************************************************/
function grd_fn_SetConfidential(strGridContents) 
{
	return grd_fn_SetExcelHead(strGridContents,"Confidential","red","","left");
}

/*********************************************************************
* 기능 : 그리드 Excel Export 시에 Excel Head 출력 
* 인수 : strGridContents
*        strTitle : "그리드 타이틀"
*        strColor : "#ffffff" or "red"
*        strFont  : "돋움,9,Bold"
*        strAlign : "center"
* 예제 : grd_fn_SetExcelHead(strGridContents,strTitle,strColor,strFont,strAlign);
**********************************************************************/
function grd_fn_SetExcelHead(strGridContents,strTitle,strColor,strFont,strAlign) 
{
	strGridContents = replace(strGridContents,'row="0"','');

	var nStartPos = indexOf(strGridContents,"<head>");
	var nEndPos = indexOf(strGridContents,"</head>");

	var strPrevContents = substr(strGridContents,0,nStartPos);
	var strHeadContents = substr(strGridContents,(nStartPos+length("<head>")),(nEndPos-(nStartPos+length("<head>"))) );
	var strNextContents = substr(strGridContents,(nEndPos+length("</head>")),length(strGridContents));

	var arrContents = split(strHeadContents,"\n","webstyle");

	var nRow;
	var nMaxColumn = 0;
	var nCurrMaxColumn = 0;
	var strPrevRowText;
	var strCurrRowText;

	var strNewGridContents;
	var strRowIndex;
	var strColIndex;

	strHeadContents = "";

	for(var nIdx = 0; nIdx < length(arrContents); nIdx++) 
	{
		nStartPos = indexOf(arrContents[nIdx],"row=");

		if(nStartPos > -1) 
		{
			nEndPos = indexOf(arrContents[nIdx]," ",nStartPos);
			strCurrRowText = substr(arrContents[nIdx],nStartPos,(nEndPos-nStartPos));

			strRowIndex = replace(strCurrRowText,'row=','');
			nRow = toNumber(replace(strRowIndex,'"','')) + 1;

			arrContents[nIdx] = replace(arrContents[nIdx],strCurrRowText,'row="'+nRow+'"');
		} 
		else 
		{
			arrContents[nIdx] = replace(arrContents[nIdx],'/>',' row="1"/>');
		}

		if(length(trim(arrContents[nIdx])) > 0) 
		{
			strHeadContents += "\n" + arrContents[nIdx];

			nStartPos = indexOf(arrContents[nIdx],'col=');
			nEndPos   = indexOf(arrContents[nIdx],' ',nStartPos);
			strColIndex = substr(arrContents[nIdx],nStartPos,(nEndPos-nStartPos));
			strColIndex = replace(strColIndex,'col=','');
			nCurrMaxColumn = toNumber(replace(strColIndex,'"',''));

			if(nMaxColumn < nCurrMaxColumn) 
			{
				nMaxColumn = nCurrMaxColumn;
			}
		}
	}

	var strHeadStart = '<head>\n<cell align="'+strAlign+'" color="'+strColor+'" font="'+strFont+'" bkcolor="white" col="0" colspan="'+(nMaxColumn+1)+'" display="text" text="'+strTitle+'"/>';
	strHeadContents  = strHeadStart + strHeadContents + "</head>";

	strNewGridContents = strPrevContents + strHeadContents + strNextContents;

	return strNewGridContents;
}