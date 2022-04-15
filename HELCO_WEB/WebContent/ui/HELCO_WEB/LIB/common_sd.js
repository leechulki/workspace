﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/**
 * ============================================================================

 * ============================================================================
 */

#include "LIB::FormResize_sd.js";	// 공통 스크립트 
var gridNoDataMsg = "조회된 데이터가 없습니다.";
//*************************************************
// callServiceN
//*************************************************
function callServiceN(srvId, url, inDsList, outDsList, strParam, CallBackFunc) 
{
	// 시스템의 Mouse Cursor를 모래시계(Wait Cursor)로 설정하거나 해제
	SetWaitCursor(true);
	SetCapture();
	
	//if ( srvId != 'code' && srvId != 'sayang' && srvId != 'cost' && srvId != 'copy' )
	if ( srvId != 'code')
	{
		fn_createWait("",this);
	}
	
	
	//메시지바 초기화 하기
	// 조회후에 연이어 transation을 태우게 되면 사라지므로
	// 메시지 초기화는 transation태울때 처리하지 않는다.
	//gfn_clearMsgBar();
	
	// 화면에 해당하는 사용자의 그룹 코드 정보 가져오기 
	// @return 사용자 그룹 코드 정보 
	//var user_grp = gfn_getUserGroup(); 
        
	var svcUrl = "SVC::" + url;
	var params = "";
		params += fn_getGlobalVariable();	// 글로벌 변수값을 파라미터로 생성
		params += " " + strParam;
	
	// web
	destroy("ds_msg");
	Create("dataset", "ds_msg");
	object("ds_msg").AddColumn("CODE", "STRING", "256");
	object("ds_msg").AddColumn("CODE_NAME", "STRING", "256");
	
	// sap
	destroy("ds_error");
	Create("dataset", "ds_error");
	object("ds_error").AddColumn("IDX", "STRING", "256");
	object("ds_error").AddColumn("ERRMSG", "STRING", "256");
	
	outDsList += " ds_msg=ds_msg ds_error=ds_error";
	
    // 동기식 설정은 사용하지 않는다.
    //http.sync = true;
    
	if ( ( CallBackFunc == '' ) || ( CallBackFunc == null ) || ( CallBackFunc == 'fn_afterQuery' ) )
	{
		Transaction(srvId, svcUrl, inDsList, outDsList, params, "callResult");
		
	}
	else
	{
		Transaction(srvId, svcUrl, inDsList, outDsList, params, CallBackFunc);		
		// 조회중입니다. 메시지 삭제
		fn_destroyWait();

	}
}

//*************************************************
// callResult
//*************************************************
function callResult(strSvcID, nErrorCode, strErrorMag)
{
	//trace("@@@@ callResult :in@@@@@@@@@@@@@");

	//if ( strSvcID != 'code' && strSvcID != 'sayang' )
	// 2012.11.02 Progress Bar표시 변경
	if ( strSvcID != 'code' )
	{
		// 조회중입니다. 메시지 삭제
		fn_destroyWait();
	}
    
    // Transaction exception메시지 처리
    // sap Transaction exception메시지 처리
    if ( processExcepMsg() )
    {
		if ( processSapExcepMsg() )	{
			// callback 함수 호출
			fn_afterQuery(strSvcID, nErrorCode, strErrorMag);
		}
    }
}

//*************************************************
// Transaction exception메시지 처리
//*************************************************
function processExcepMsg()
{
		//trace(" @@@ java Exception Msg @@@ "+ds_msg.getRowCount());
	if ( ds_msg.getRowCount() != 0 )
	{
		//trace(" @@@ java Exception Msg @@@ ");
		gfn_showAlert(ds_msg.getColumn(0,"CODE"), ds_msg.getColumn(0,"CODE_NAME")); 
		return false;
	}
	return true;
}

//*************************************************
// sap Transaction exception메시지 처리
//*************************************************
function processSapExcepMsg()
{
		//trace(" @@@ java Exception Msg @@@ "+ds_error.getRowCount());
	if ( ds_error.getRowCount() != 0 )
	{
		//trace(" @@@ sap Exception Msg @@@ ");
		fn_showAlert(ds_error.getColumn(0,"IDX"), ds_error.getColumn(0,"ERRMSG")); 
		return false;
	}
	return true;
}

//*************************************************
// 하드코딩용 콤보리스트
// ( componentId, comboGubun, DatasetId, 전체여부, 그리드/콤보여부)
//*************************************************
function fn_SetListCode(pComboId, pComboNm, pDataSet, pIsAllYn, pCompFlag) 
{ 	
	http.Sync = true;

	var ds 			= pDataSet;
	
	destroy(ds);
	Create("dataset", ds);
	object(ds).AddColumn("CODE", "STRING", "256");
	object(ds).AddColumn("CODE_NAME", "STRING", "256");
	
	object(ds).ClearData();

	// 2012.11.13 영문용 처리 반영
	if (G_LANG == 'ko')
	{
		// 차수
		if (pComboNm == "chasu")  
		{ 
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "01");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "1차"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "02");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "2차"); 
			
		}  
		// 조회구분
		else if ( pComboNm == "whereGubun" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "1");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "수주"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "2");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "매출"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "3");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "청구"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "4");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "수금");
			
		}
		// 12개월
		else if ( pComboNm == "months" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "1");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "1월"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "2");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "2월"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "3");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "3월"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "4");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "4월");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "5");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "5월");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "6");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "6월");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "7");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "7월");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "8");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "8월");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "9");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "9월");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "10");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "10월");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "11");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "11월");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "12");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "12월");
			
		}
		// 이동계획
		else if ( pComboNm == "moveYn" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "X");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "포함"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", " ");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "미포함"); 
			
		}
		// 직영/대리점 구분 
		else if ( pComboNm == "agentGbn" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "ZS01");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "직영"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "ZS02");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "대리점"); 
			
		}
		// 영업수요정보 적용제품 
		else if ( pComboNm == "prod" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "FAG");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "물류"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "APG");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "주차리모델링"); 
			
		}
		// 수주가능성
		else if ( pComboNm == "soable" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "A");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "높음"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "B");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "중간"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "C");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "낮음"); 
		}
		// 계획구분
		else if ( pComboNm == "pgubun" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "계획");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "계획"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "추가");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "추가"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "이월");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "이월"); 
		}
		else
		{
			// 코드구분(popFlag)
			// 코드value & 명value --> param(where조건용)
			// ↑조건이 필요없는 코드성 데이터의 경우 '' 처리
			
			var outDs = ds+"=ds_output";
			
			callServiceN("code"
						, "com0060/find"	
						, ""
						, outDs
						, "popFlag="+pComboNm+" where=''"	
						, ""); 
			
			
			if ( pCompFlag == 'grid' )
			{
				Grid.SetCellProp('Body', pComboId, "Display", 		"expr:decode(rowlevel, '1', 'text', 'combo')");
				Grid.SetCellProp('Body', pComboId, "ComboDataset", 	ds);
				Grid.SetCellProp('Body', pComboId, "ComboCol", 		"CODE");
				Grid.SetCellProp('Body', pComboId, "ComboText", 	"CODE_NAME");
			}
		}
		
		if (pIsAllYn == "Y")
		{		
			object(ds).InsertRow(0);
			object(ds).setColumn(0, "CODE", "");
			object(ds).setColumn(0, "CODE_NAME", "- 전체 -");
		}
	}
	else		// 한국어 이외
	{
		// 차수
		if (pComboNm == "chasu")  
		{ 
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "01");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "1차_E"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "02");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "2차_E"); 
			
		}  
		// 조회구분
		else if ( pComboNm == "whereGubun" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "1");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "수주_E"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "2");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "매출_E"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "3");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "청구_E"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "4");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "수금_E");
			
		}
		// 12개월
		else if ( pComboNm == "months" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "1");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "1월_E"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "2");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "2월_E"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "3");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "3월_E"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "4");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "4월_E");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "5");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "5월_E");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "6");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "6월_E");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "7");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "7월_E");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "8");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "8월_E");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "9");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "9월_E");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "10");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "10월_E");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "11");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "11월_E");
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "12");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "12월_E");
			
		}
		// 이동계획
		else if ( pComboNm == "moveYn" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "X");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "포함_E"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", " ");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "미포함_E"); 
			
		}
		// 수주가능성
		else if ( pComboNm == "soable" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "A");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "High"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "B");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "Medium"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "C");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "Low"); 
		}
		// 계획구분
		else if ( pComboNm == "pgubun" )
		{
		
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "계획");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "Register"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "추가");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "Add"); 
			object(ds).addRow();
			object(ds).setColumn(object(ds).row, "CODE", "이월");
			object(ds).setColumn(object(ds).row, "CODE_NAME", "Next Month"); 
		}
		else
		{
			// 코드구분(popFlag)
			// 코드value & 명value --> param(where조건용)
			// ↑조건이 필요없는 코드성 데이터의 경우 '' 처리
			
			var outDs = ds+"=ds_output";
			
			callServiceN("code"
						, "com0060/find"	
						, ""
						, outDs
						, "popFlag="+pComboNm+" where=''"	
						, ""); 
			
			
			if ( pCompFlag == 'grid' )
			{
				Grid.SetCellProp('Body', pComboId, "Display", 		"expr:decode(rowlevel, '1', 'text', 'combo')");
				Grid.SetCellProp('Body', pComboId, "ComboDataset", 	ds);
				Grid.SetCellProp('Body', pComboId, "ComboCol", 		"CODE");
				Grid.SetCellProp('Body', pComboId, "ComboText", 	"CODE_NAME");
			}
		}
		
		if (pIsAllYn == "Y")
		{		
			object(ds).InsertRow(0);
			object(ds).setColumn(0, "CODE", "");
			object(ds).setColumn(0, "CODE_NAME", "- ALL -");
		}
	}


	if ( pCompFlag == 'grid' )
	{
		//trace("@@@@@@@ 그리드용 코드성 데이타 생성!"+pComboId);
		// transaction 태우지 않는 그리드 용
		Grid.SetCellProp('Body', pComboId, "Display", 		"expr:decode(rowlevel, '1', 'text', 'combo')");
		Grid.SetCellProp('Body', pComboId, "ComboDataset", 	ds);
		Grid.SetCellProp('Body', pComboId, "ComboCol", 		"CODE");
		Grid.SetCellProp('Body', pComboId, "ComboText", 	"CODE_NAME");
	}
	else
	{
		pComboId.CodeColumn 	= "CODE";
		pComboId.DataColumn 	= "CODE_NAME";
		pComboId.InnerDataset	= ds;
		pComboId.Redraw 		= true;
		pComboId.index 			= 0;
		pComboId.setFocus();
	}
	
	http.Sync = false;
} 


//*************************************************
// ZLCODE
// ( componentId, comboGubun, DatasetId, 전체여부, 그리드/콤보여부, 그리드id)
//*************************************************
function fn_SetListCodeZLCODE(pComboId, pComboNm, filter1, filter2, filter3, pDataSet, pIsAllYn, pCompFlag) 
{ 	
	http.Sync = true;

	var ds 			= pDataSet;
	
	destroy(ds);
	Create("dataset", ds);
	object(ds).AddColumn("CODE", "STRING", "256");
	object(ds).AddColumn("CODE_NAME", "STRING", "256");
	object(ds).AddColumn("CODE_NAME", "STRING", "256");
	object(ds).AddColumn("FILTER1", "STRING", "256");
	object(ds).AddColumn("FILTER2", "STRING", "256");
	object(ds).AddColumn("FILTER2", "STRING", "256");

	
	// 코드구분(popFlag)	
	var outDs = ds+"=ds_output";
	
	callServiceN("code"
				, "com0060/ZLCODE"	
				, ""
				, outDs
				, "popFlag="+pComboNm
				+" filter1="+filter1
				+" filter2="+filter2
				+" filter3="+filter3	
				, ""); 
	
	
	if ( pCompFlag == 'grid' )
	{
		Grid.SetCellProp('Body', pComboId, "Display", 		"expr:decode(rowlevel, '1', 'text', 'combo')");
		Grid.SetCellProp('Body', pComboId, "ComboDataset", 	ds);
		Grid.SetCellProp('Body', pComboId, "ComboCol", 		"CODE");
		Grid.SetCellProp('Body', pComboId, "ComboText", 	"CODE_NAME");
	}
	
	if (pIsAllYn == "Y")
	{		
		object(ds).InsertRow(0);
		object(ds).setColumn(0, "CODE", "");

		// 2012.11.13 영문 반영
		if (G_LANG == 'ko')
			object(ds).setColumn(0, "CODE_NAME", "- 전체 -");
		else
			object(ds).setColumn(0, "CODE_NAME", "- ALL -");

	}
	
	
	if ( pCompFlag == 'grid' )
	{
		//trace("@@@@@@@ 그리드용 코드성 데이타 생성!"+pComboId);
		// transaction 태우지 않는 그리드 용
		Grid.SetCellProp('Body', pComboId, "Display", 		"expr:decode(rowlevel, '1', 'text', 'combo')");
		Grid.SetCellProp('Body', pComboId, "ComboDataset", 	ds);
		Grid.SetCellProp('Body', pComboId, "ComboCol", 		"CODE");
		Grid.SetCellProp('Body', pComboId, "ComboText", 	"CODE_NAME");
	}
	
	http.Sync = false;
}

//*************************************************
// 그리드 동적용
// ( componentId, comboGubun, DatasetId, 전체여부, 그리드/콤보여부, 그리드id)
//*************************************************
function fn_SetListCodeGrid(pComboId, pComboNm, pDataSet, pGirdId) 
{ 		
	var ds = pDataSet;
	
	destroy(ds);
	Create("dataset", ds);
	object(ds).AddColumn("CODE", "STRING", "256");
	object(ds).AddColumn("CODE_NAME", "STRING", "256");
	object(ds).AddColumn("FILTER1", "STRING", "256");
	
	// 코드구분(popFlag)
	// 코드value & 명value --> param(where조건용)
	// ↑조건이 필요없는 코드성 데이터의 경우 '' 처리
	
	var outDs = ds+"=ds_output";
	
	callServiceN("code"
				, "com0060/find"	
				, ""
				, outDs
				, "popFlag="+pComboNm+" where=''"	
				, ""); 
	
	
	//object(pGirdId).SetCellProp('Body', pComboId, "Display", "combo");
	object(pGirdId).SetCellProp('Body', pComboId, "ComboDataset", ds);
	//object(pGirdId).SetCellProp('Body', pComboId, "ComboCol", "CODE");
	//object(pGirdId).SetCellProp('Body', pComboId, "ComboText", "CODE_NAME");
	
	
} 
//*************************************************
//  버튼 Disable 처리
//*************************************************
function ufn_enableBtn(btnStr, isEnable )
{
	object("btn_" + btnStr).enable = isEnable;
}


//*************************************************
//  component focus color 처리
//*************************************************
var beforeColor;
function ufn_OnFocus(obj)
{
	beforeColor = "";
	if ( obj.BKColor != "" )
	{
		beforeColor = obj.BKColor;
	}
	obj.BKColor = "wheat";
}

function ufn_OnKillFocus(obj)
{
	if ( beforeColor != "" )
	{
		obj.BKColor = beforeColor;
	}
	else
	{
		obj.BKColor = "";
	}
}

//*************************************************
// fn_Year 
// 년도콤보 데이타처리
//*************************************************
function fn_Year(curYear, sNum, eNum, yNum) 
{
	var rowCnt = eNum-sNum; // 년도 전체 범위
	var row = 0;			// row 카운터 변수

	// 년도dataset 생성
	create("dataset", "ds_year");
	object("ds_year").addColumn("CODE", "STRING", 256);
	object("ds_year").addColumn("CODE_NAME", "STRING", 256);
	
	//trace("@@@ rowCnt : "+rowCnt);
	
	// 년도 전체 범위만큼 루프
	for(var i=rowCnt; i>-1; i--) {
		object("ds_year").AddRow();
		object("ds_year").SetColumn(row,"CODE",parseInt(curYear)+i+parseInt(sNum));
		object("ds_year").SetColumn(row,"CODE_NAME",(parseInt(curYear)+i+parseInt(sNum))+"년");
		row++;
	}
	
	// dataset 매핑
	cb_year.InnerDataset = "ds_year";
	
	// 현재년도를 기본으로 셋팅
	cb_year.index = eNum-yNum; 
}

//*************************************************
// fn_ValidChk 
// [*] 문자가 존재하는지 체크
//*************************************************
function fn_ValidChk(val)
{
	if ( val.ToLOWerCase().indexOf('*',0) == -1 )
	{
		return false;
	}
	else
	{
		return true;
	}
}

//*************************************************
// 닫기  
// 버튼 처리중 유일하게 공용스크립트로 셋팅
// 로직이 들어갈경우 화면에서 제어
//*************************************************
function fn_close() {
	this.close();
}

/******************************************************
*  Grid 에서 컬럼 값 변경 시 해당 Flag에 대한 Bk 이미지 변경
******************************************************/
function fn_getFlagBkImageID( flagValue ) 
{
	var bkImg = "";
	switch( flagValue ) {
		case "insert":
			bkImg = "icon_grid_add";
			break;
		case "update":
			bkImg = "icon_grid_modify";
			break;
		case "delete":
			bkImg = "icon_grid_del";
			break;
		case "normal":
			bkImg = "";
			break;
	}
	return bkImg;
}


/****************************************************************
* 메시지 코드를 받아서 Alert 처리 - sap용
* @param msgCode 정의되어 있는 메시지 코드
* @param arr 메시지 대체 문자 또는 배열
***************************************************************/
function fn_showAlert( msgCode, arr) 
{
	var msg = gfn_getMsg(msgCode, arr);
	var msgType = "W";
	if ( msg == null ) 
	{
		msg = arr;
		msgType = charAt( msgCode, 1 );
	}

	gfn_openMsgWin(msg, msgType, "A", msgCode );
}

/******************************************************
 * 입력값에 숫자만 있는지 체크
*******************************************************/
function isNumber(input) {
    var chars = "0123456789";
    
    for (var inx = 0; inx < length(input); inx++) 
    {
       if ( indexOf(chars, charAt(input, inx)) == -1)
       {
           return false;
       }
    }
    return true;
}

/******************************************************
 * 소계부분을 제외한 일련번호 표시
*******************************************************/
function fn_row(currow, ds)
{
  var iCnt =0;
  for(var i=0;i<=currow;i++)
  {
    if(object(ds).GetRowlevel(i) == 1)
		iCnt ++;
  }

  return ((currow+1) - iCnt);
}

/****************************************************************
* 메시지 보여주기
* 내부적으로 Division 을 생성하여 해당 정보를 보여준다.
* @param strMsg 보여줄 메시지
* @param topObj 위치할 최상위 객체
* @param errMsg  에러 메시지
* @return 없음
******************************************************************/
var uvLoadingWin = "";
function fn_progressBarWait(strMsg, visi) {
	
	var msg = "";

	// 2012.11.13 영문반영
	if (G_LANG == 'ko')
	{
		if ( strMsg == 'sayang' )
		{
			msg = "변경된 정보의 사양을 매핑중입니다.\n잠시만 기다려 주십시오.";
		}
		else if ( strMsg == 'cost' )
		{
			msg = "원가를 계산중입니다.\n잠시만 기다려 주십시오.";
		}
		else if ( strMsg == 'copy' )
		{
			msg = "일괄복사중입니다.\n잠시만 기다려 주십시오.";
		}
	}
	else
	{
		if ( strMsg == 'sayang' )
		{
			msg = "변경된 정보의 사양을 매핑중입니다.\n잠시만 기다려 주십시오._E";
		}
		else if ( strMsg == 'cost' )
		{
			msg = "원가를 계산중입니다.\n잠시만 기다려 주십시오._E";
		}
		else if ( strMsg == 'copy' )
		{
			msg = "일괄복사중입니다.\n잠시만 기다려 주십시오._E";
		}
	}


	//trace(" uvLoadingWin 1:"+uvLoadingWin);
	if(uvLoadingWin != "")
	{
		GetFormFromHandle(uvLoadingWin).Close();
		uvLoadingWin = "";
	}
	else
	{
		var arg  = "msg="+quote(msg); // msg
		uvLoadingWin = open("SSO::Sso0050_progressbar.xml",arg,0,0,"titlebar=false closeFlag=false border=false AutoSize=true",-1,-1);
		
	}
	//trace(" uvLoadingWin 2:"+uvLoadingWin);
}


/****************************************************************
 * form 초기 Loading 처리
 * @return 없음
***************************************************************/
function fn_FormResize(bResize) 
{
	// X-Framework 생성
	//tit_createActionInfo();

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
		Gfn_ResizeInitSD(978,562);	
	}
	
		 
}

function fn_popupSearchHelp(pArg_code, pEd_code, pArg_name, pEd_name, pUrl, pDs_list, pColCode, pColName) {
	if(pDs_list == NULL) {	//Search
		var argument = pArg_code + " = "  + quote(pEd_code.Text) + "  " + pArg_name + " = " + quote(pEd_name.Text);

		if (length(pEd_code.Text) < 2)
			pEd_code.Text = '';
	
		var arr = gfn_openPopUpUrlHdel(pUrl, true, argument);
		if (arr != null && arr != 0) {
			pEd_code.Text = arr[0];
			pEd_name.Text = arr[1];
		}
	} else {				//Help
		if (pDs_list.RowCount() = 1) {
			pEd_code.Text = pDs_list.GetColumn(pDs_list.row, pColCode);
			pEd_name.Text = pDs_list.GetColumn(pDs_list.row, pColName);
		}
		else if (pDs_list.RowCount() > 1) {
			fn_popupSearchHelp(pArg_code, pEd_code, pArg_name, pEd_name, pUrl);
		}
	}
}

function gfn_queryDD07T(pDomainId,pDs,pConcatYn) {
	Destroy('ds_cond_dd07t');
	Create('dataset', 'ds_cond_dd07t');
	ds_cond_dd07t.AddColumn('DOMNAME');
	ds_cond_dd07t.AddColumn('DOMVALUE_L');
	ds_cond_dd07t.AddColumn('CONCAT_YN');
	
	ds_cond_dd07t.ClearData();	
	pDs.ClearData();

	ds_cond_dd07t.AddRow();
	ds_cond_dd07t.SetColumn(ds_cond_dd07t.row, 'DOMNAME'	, pDomainId);  
	ds_cond_dd07t.SetColumn(ds_cond_dd07t.row, 'CONCAT_YN'	, pConcatYn);  

	nsfRequest('DD07T','combo/comboDD07T','ds_cond_dd07t=ds_cond_dd07t '+pDs.ID+'='+pDs.ID
			   , pDs.ID+'='+pDs.ID
			   , ''
			   , 'fn_afterQueryDD07T_'+pDomainId);
}

function gfn_get_lad_baseurl(pCateg) {
	var address = '';
	switch(G_SYSID) {
		case 'HEP':
			switch(pCateg) {
				case 'J':
					address = 'http://parking.hdel.co.kr/';
					break;
				case 'R':
					address = 'http://203.242.40.' + (226 + Random(0,0)) + '/';	//rad 주소가 달리질지 모르지만, 하나 더 코드 를 생성함.
				    break;
				default:
					address = 'http://203.242.40.' + (226 + Random(0,0)) + '/';	//PLAD 오픈시 기존 LAD와 병행하기 때문에 과부하대비 226번으로 고정해달라는 모베이스 요청. 향후 Random(0,1) 수정 필요
					break;
			}
			break;

		default:
			address = 'http://10.51.8.199:8088/';
			break;
	}

	switch(pCateg) {
		case 'J':
			address = address + 'plad/';
			break;
		case 'R':
			address = address + 'rad/';
			break;
		case 'NEX':
			address = address;
			break;
		default:
			address = address + 'lad/';
			break;
	}

	return address;
}