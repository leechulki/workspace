﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*===============================================================================
*   상수 선언 
================================================================================*/
var F_SELECT_TXT_OBJ = null;
var F_SELECT_RTN_OBJ = null;
var F_SELECT_BTN_OBJ = null;
var F_CODE_STR = "CODE";    // 데이터셋 내의 코드를 나타내는 값 
var F_CODE_NAME_STR = "CODE_NAME";   // 데이터셋 내의 명칭을 나타내는 값 
var F_RTN_VALUE = null;
var F_RTN_TYPE = "";	// 결과 return type 
/****************************************************************
 *  다중 선택 Combo 디자인 
 * @param dsObj 표시할 데이터 셋 정보 
 * @param txtObj  선택한 정보를 표시할 입력 Object  
 * @param btnObj  해당 Popup 화면을 띄운 이미지 객체 
 * @param selectStr  현재 선택되어 있는 값 또는 선택하고 싶은 값 , 여러개일 경우 , 로 표시  
 * @param dispType  코드 값 보여주는 타입 
                    T - 코드 명만 표시 
                    C - 코드만 표시 
                    A - 코드 + 코드명 표ㅣ 
                    기본은 T 
 * @param  rtnType  선택한 값 return 타입 
                    A - 선택한 코드 값 , 로 모두 표시    
                    B - 선택한 코드명 값 , 로 모두 표시 
 * @param w  넓이 : -1일 경우 사이즈 자동 계산 
 * @param h 높이 :  -1일 경우 기본 10개를 표시할 사이즈로 처리 
 * @param isRight : 오른쪽 기준으로 표시할지 여부 
 * @return 선택한 코드 배열 값 
***************************************************************/
function gfn_createSelectPopup(dsObj, txtObj, btnObj
	, selectStr, dispType, rtnType, w, h, rtnObj, isRight) {
    var nWidth = 0;
    var nHeight = 0;
    var nLeft = 0;
    var nTop = 0;
    
    // 넓이 
    if ( w == null || w < 1 ) {
        w = txtObj.width + btnObj.width;
    }
    
    nWidth = w;
    if ( isRight ) {
        nLeft = ClientToScreenX(btnObj, btnObj.width) - w;
    } else {
        nLeft = ClientToScreenX(txtObj, 0);
    }
    
    var tmpH = txtObj.height;
    // 높이 
    if ( h == null || h < 1 ) {
        h = 140;
    } 
    nHeight = h;
    
    nTop = ClientToScreenY(txtObj, 0) + txtObj.height;
    
    nLeft = nLeft -2;
    nTop = nTop - 1;

    // 표시 타입 처리 
    if ( length( dispType ) == 0 ) {
        dispType = "T";
    }
    // Dataset 컬럼 확인 
    if ( dsObj.GetColIndex("_chk") < 0 ) {
        dsObj.AddColumn("_chk", "STRING", 1);
	} else {
        // 해당 정보 초기화 
        for ( i = 0; i < dsObj.rowCount(); i++) {
            dsObj.setColumn(i, "_chk", "0");
        }
	}
	// 선택하고자 하는 값이 있을 경우 
	if ( selectStr != null && length( selectStr ) > 0 ) {
		var s = split( selectStr, ",");
        for ( i = 0; i < length(s); i++) {
            var r = dsObj.findRow(F_CODE_STR, s[i]);
            if ( r >= 0 ) {
                dsObj.setColumn(r, "_chk", "1");
            }
        }
	}
    // Grid 생성 String 
    var str = "<Contents>";
    str += "<Grid AutoEnter=\"TRUE\" BindDataset=\"";
    str += dsObj.id ;
    str += "\" BkColor2=\"default\" BoldHead=\"true\" border=\"none\" " ; 
    str += " AutoScrollBar=\"Vert\" UseSelColor=\"false\" " ; 
    str += " Bottom=\"" + nHeight + "\"";
    str += " Width=\"" + nWidth + "\"";
    str += " Height=\"" + nHeight + "\" Id=\"grd_list\" ColSizing=\"TRUE\" Editable=\"true\" Enable=\"true\" ";
    str += " EndLineColor=\"default\" InputPanel=\"FALSE\" Left=\"0\" LineColor=\"user20\" LineType=\"NONE\" ";
    str += " Style=\"sty_grid\" Right=\"" + nWidth +"\" Top=\"0\" AutoFit=\"false\" Visible=\"true\" VLineColor=\"default\">";
    str += " <contents>";
    str += " <format id=\"Default\">";
    str += " <columns> ";
    str += " <col width=\"20\"/>"; // check box 
    
    // code name 표시 부분 사이즈 계산 
    var codeWidth = nWidth - 20;
    if ( dispType == "A" ) {
        str += " <col width=\"30\"/>";
        codeWidth = codeWidth - 30;
    }
    
    str += " <col width=\"" + codeWidth + "\"/>";
    str += " </columns> ";
    str += "	<body> ";
    str += "    <cell col=\"0\" colid=\"_chk\" display=\"checkbox\" edit=\"checkbox\"/>";
    
    if ( dispType == "T") {
        str += "    <cell col=\"1\" colid=\"" + F_CODE_NAME_STR + "\" display=\"text\" edit=\"none\"/>";
    } else if ( dispType == "C") {
        str += "    <cell col=\"1\" colid=\"" + F_CODE_STR + "\" display=\"text\" edit=\"none\"/>";
    } else {
        str += "    <cell col=\"1\" colid=\"" + F_CODE_STR + "\" display=\"text\" edit=\"none\"/>";
        str += "    <cell col=\"2\" colid=\"" + F_CODE_NAME_STR + "\" display=\"text\" edit=\"none\"/>";
    }   
     
    str += "	</body> ";
    str += " </format>";
    str += " </contents>";
    str += " </Grid>";
    str += "</Contents>";
    
    
    // 멤버 변수로 
    F_SELECT_TXT_OBJ = txtObj;
    F_SELECT_BTN_OBJ = btnObj;
    F_SELECT_RTN_OBJ = rtnObj;
   
    
    F_RTN_TYPE = rtnType;
    
    _gfn_createSelectPopupDiv(str, nWidth, nHeight, nLeft, nTop );
    
    // 값 전달 
    var tmp = F_RTN_VALUE;
    F_RTN_VALUE = null;
    return tmp;
}
/**
 *  팝업 디비전 생성
 **/
function _gfn_createSelectPopupDiv(grdStr, nWidth, nHeight, nLeft, nTop ) {
    var obj = find("_pDiv_selectPopup");
    
    if ( obj == null ) {
        var arg = 'Border="Flat" BorderColor="#d0d0d0"';
        arg += ' Height=' + nHeight;
        arg += ' Width=' + nWidth ;
        arg += ' OnCloseUp="_pDiv_selectPopup_OnCloseUp" ';
        Create("PopupDiv", "_pDiv_selectPopup", arg);
    } else {
        _pDiv_selectPopup.width = nWidth;
        _pDiv_selectPopup.height = nHeight;
    }
    
    _pDiv_selectPopup.Contents = grdStr;
    _pDiv_selectPopup.TrackPopup(nLeft, nTop);
}
/**
*   팝업에서 선택하고 닫힐 경우 
**/
function _pDiv_selectPopup_OnCloseUp(obj,varReturn,bSelOk) {
    var dsObj = object(_pDiv_selectPopup.grd_list.bindDataset);
    
    var str = "";
    var nTot = 0;
    F_RTN_VALUE = Array();
    
    var nameArr = Array();
    for ( i = 0; i < dsObj.rowCount(); i++ ) {
        if ( dsObj.getColumn(i, "_chk") == "1") {
			nameArr[nTot] = dsObj.getColumn(i, F_CODE_NAME_STR);
			F_RTN_VALUE[nTot++] = dsObj.getColumn(i, F_CODE_STR);
        }
    }
    
    // 코드 값 표시 
    for( var i = 0; i < length( F_RTN_VALUE); i++ ) {
		if ( i > 0 ) {
			str += ",";
		}
		str += F_RTN_VALUE[i];
	}
	
	if ( F_SELECT_RTN_OBJ != null ) {
		F_SELECT_RTN_OBJ.value = str;
	}
	
    if ( F_RTN_TYPE == "B") {
		str = "";
		// 코드 명 풀어서 
		for( var i = 0; i < length( nameArr); i++ ) {
			if ( i > 0 ) {
				str += ",";
			}
			str += nameArr[i];
		}
	} else if ( F_RTN_TYPE != "A" && nTot > 1 ) {
        str = nameArr[i] + " 외 " + (nTot-1) + "종";
    }
    
    F_SELECT_TXT_OBJ.value = str;
    // 초기화 
    F_SELECT_TXT_OBJ = null;
    F_SELECT_BTN_OBJ = null;
    F_SELECT_RTN_OBJ = null;
}


/******************************************************
*  전화번호 Format 처리  
* @param  telStr  전화번호 String 
* @param  telObj  전화번호 입력 객체 명 
* @return  전화번호가 올바를 경우 "-" 가 들어간 전화번호 String 
*******************************************************/
function gfn_formatTelNo( telStr, telObj) {
    telStr = replace( telStr, "-", "");
	var len = length( telStr );

	// 7, 8 자리이면 기본적으로 02 로 설정 
	if ( len == 7 && left( telStr, 1 ) != "0") {
		return "02-" + left( telStr , 3 ) + "-" + substr(telStr, 3 );
	} else if ( len == 8 && left( telStr, 1 ) != "0" ) {
		return "02-" + left( telStr , 4 ) + "-" + substr(telStr, 4 );
	}

	if ( len < 9 || len > 11 ) {
		// 형식이 올바르지 않음. 
		return "";
	}
	
	if ( len == 12 ) {
        return telStr;
	}
	
	var startStr = fn_checkTelNo1(telStr);

	// 잘못된 경우 지움. 
	if ( length (startStr) == 0 ) {
		return "";
	}
	
	if ( startStr == "02" &&  len == 11 ) {
		return "";
	}
	
	return startStr + "-" 
		+ substr(telStr, length(startStr), len-4-length(startStr) )
		+ "-" + right( telStr, 4 );
}
/******************************************************
*  휴대폰 Format 처리  
* @param  telStr  휴대폰 String 
* @param  telObj  휴대폰 입력 객체 명 
* @return  휴대폰가 올바를 경우 "-" 가 들어간 전화번호 String 
*******************************************************/
function gfn_formatHpNo( telStr, telObj) {
    telStr = replace( telStr, "-", "");
    
	var len = length( telStr );
	
	if ( len < 10 || len > 11 ) {
		// 형식이 올바르지 않음. 
		return "";
	}
	
	if ( len == 12 ) {
        return telStr;
	}
	
	var startStr = fn_checkHpNo1(telStr);

	// 잘못된 경우 지움. 
	if ( length (startStr) == 0 ) {
		return "";
	}
	
	return startStr + "-" 
		+ substr(telStr, length(startStr), len-4-length(startStr) )
		+ "-" + right( telStr, 4 );
}

/******************************************************
*  전화번호 앞자리 확인 
* @param  telStr  전화번호 String 
* @return  전화번호 앞자리 
*******************************************************/
function fn_checkTelNo1(telNo) {
	if ( left( telNo, 2 ) == "02") {
		return left( telNo, 2 ) ;
	}
	
	var fullStr = "02,031,032,033,041,042,043,051,052,053,054,055,061,062,063,064";
	
	var str = left( telNo, 3 );
	
	if ( indexOf( fullStr, str ) != -1 ) {
		return str;
	}
	return "";
}
/******************************************************
*  휴대폰  앞자리 확인 
* @param  telStr  휴대폰 String 
* @return  휴대폰 앞자리 
*******************************************************/
function fn_checkHpNo1(telNo) {
	var fullStr = "010,011,016,017,018,019";
	
	var str = left( telNo, 3 );
	
	if ( indexOf( fullStr, str ) != -1 ) {
		return str;
	}
	return "";
}

/******************************************************
*  전화번호 입력 가능한 String 유무 
* @param  telStr  전화번호 String 
* @return  "0"이 들어간 전화번호 String 
*******************************************************/
function gfn_canInputTelNo( telObj, nChar, LLParam, HLParam ) {
	if ( nChar == 8 ||  (nChar==46 && HLParam==339)) {
		// backspace
		return true;
	}
	// 0 ~ 9 : 48 ~ 57 
	if ( nChar >= 48 && nChar <= 57 ) {
		return true;
	}
	
	return false;
}
/******************************************************
*  전화번호에 focus 올때 처리 
* @param  telObj  전화번호 Object
* @return  없음
*******************************************************/
function gfn_setFocusTelNo(telObj ) {
	telObj.value = replace( telObj.value, "-", "");
}

/******************************************************
 *  주민등록번호 적합성 여부 체크 함수
 * @param  val1  주민번호앞6자리
 * @param  val2  주민번호 뒤 7 자리 
 * @return  true : 성공 / false : 실패   
 ******************************************************/
function gfn_isValidJumin( val1, val2 ) {
	var tmp1, tmp2, tmp3;
	var t1, t2, t3, t4, t5, t6, t7;
	tmp1 = val1.substr( 2, 2 );
	tmp2 = val1.substr( 4 );
    tmp3 = val2.substr( 0, 1 );
           
	if( length( val1 ) != 6 
        && length( val2 ) != 7 ) {
		return false;
	}	
	
	if ( (tmp1 < "01") || (tmp1 > "12") ){
		return false;
	}	
	if ( (tmp2 < "01") || (tmp2 > "31") ) {
		return false;
	}	
	if ( (tmp3 < "1" ) || (tmp3 > "4" ) ) {
		return false;
	}	
	
	t1  = val1.substr( 0, 1 );
	t2  = val1.substr( 1, 1 );
	t3  = val1.substr( 2, 1 );
	t4  = val1.substr( 3, 1 );
	t5  = val1.substr( 4, 1 );
	t6  = val1.substr( 5, 1 );
	var t11 = val2.substr( 0, 1 );
	var t12 = val2.substr( 1, 1 );
	var t13 = val2.substr( 2, 1 );
	var t14 = val2.substr( 3, 1 );
	var t15 = val2.substr( 4, 1 );
	var t16 = val2.substr( 5, 1 );
	var t17 = val2.substr( 6, 1 );
	
	var tot = tointeger(t1)  * 2 + tointeger(t2)  * 3 + tointeger(t3)  * 4 + tointeger(t4)  * 5 + tointeger(t5)  * 6 + tointeger(t6)  * 7;
	tot += tointeger(t11) * 8 + tointeger(t12) * 9 + tointeger(t13) * 2 + tointeger(t14) * 3 + tointeger(t15) * 4 + tointeger(t16) * 5 ;
		
	var result = tointeger(tot) % 11;
	result = ( 11 - tointeger(result) ) % 10;
	
	if (result == t17) {
		return true;
	} else {
		return false;
	}
}
/**************************************************************************************
* E-Mail 입력 형식 체크
* @param  sValue  확인할 문자
* g : pattern에 맞는 문자들을 전부 검색한다. 
* i : 검색시 영어 대소문자를 구분하지 않는다. 
* gi : g와 i를 같이 지정한다. 

**************************************************************************************/
function gfn_isValidEmail(sValue){
	var sPattern = "[A-z0-9_.-]+[@][A-z0-9_-]+([.][A-z0-9_-]+)*[.][A-z]{2,4}";
	var regexp = CreateRegExp(sPattern,"gi");
    var objExpResult = regexp.Test(sValue);

    return ( objExpResult == 1 );
}

/**************************************************************************************
* 비밀번호 입력 형식 체크
* @param  sValue  확인할 문자
**************************************************************************************/
function gfn_isValidPass(sValue){
	/*
	var sPattern  = "[A-z0-9]{6,10}";
	var sPattern1 = "[A-z]";
	var sPattern2 = "[0-9]";
	var sPattern3 = "[^a-z0-9A-Z]+";
	
	var regexp  = CreateRegExp(sPattern,"gi");
	var regexp1 = CreateRegExp(sPattern1,"gi");
	var regexp2 = CreateRegExp(sPattern2,"gi");
	
    var ExR  = regexp.Test(sValue);
    var ExR1 = regexp1.Test(sValue);
    var ExR2 = regexp2.Test(sValue);
	
	if (ExR == "1" && ExR1 == "1" && ExR2 == "1")
	{
		return true;
	}
	else
	{
		return false;
	}
	*/
	
	 var sEng = "[a-zA-Z]+";
	 var sNum = "[0-9]+";
	 var sSpe = "[^a-z0-9A-Z ]+";   //Z 옆에 스페이스로 공백 한칸 주었습니다.
	 //var sSpace = "[ \f\n\r\t\v]+";
	 
	 var regEng = CreateRegExp(sEng, "ig");
	 var regNum = CreateRegExp(sNum, "g");
	 var regSpe = CreateRegExp(sSpe, "g");
	 //var regSpace = CreateRegExp(sSpace, "ig");
	  
	 var sEngTmp = regEng.Exec(sValue);
	 var sNumTmp = regNum.Exec(sValue);
	 var sSpeTmp = regSpe.Exec(sValue);
	 //var sSpaceTmp = regSpace.Exec(sValue);
	 
	 //trace("sEngTmp (영문) : "+sEngTmp);
	 //trace("sEngTmp (영문) : "+sEngTmp.length);
	 //trace("sNumTmp (숫자) : "+sNumTmp);
	 //trace("sNumTmp (숫자) : "+sNumTmp.length);
	 //trace("sSpeTmp (특문) : "+sSpeTmp);
	 //trace("sSpeTmp (특문) : "+sSpeTmp.length);
	 ////trace("sSpaceTmp (공백) : "+sSpaceTmp);
	 ////trace("sSpaceTmp (공백) : "+sSpaceTmp.length);
	 
	 if ( sValue.length < 8) {
	  //alert("비밀번호는 8자 이상이여야 합니다.");
	  return false;
	 } else if ( sValue.length >= 12) {
	  //alert("비밀번호는 11자 이하여야 합니다.");
	  return false;
	 }
	 
	 //if ( sSpaceTmp.length >= 1) {
	 // alert("비밀번호에 공백을 입력할 수 없습니다.");
	 // return false;
	 //}
	  if(Pos(sValue, " ") <> -1 ){
	  //alert("비밀번호에 공백을 입력할 수 없습니다.");
	  return false;
	  } 
	 
	 
	 if ( sEngTmp.length == 0 || sNumTmp.length == 0  || sSpeTmp.length == 0) {
	  //alert("비밀번호는 영문, 숫자, 특수문자를 모두 사용해야 합니다.");
	  return false; 
	 }
	 

	 
	 return true;
    
}

/****************************************************************
* 사업자 번호 정합성 체크 
* @param bizNo : 사업자 번호 
* @param isCorpBiz : 법인 사업자인지 여부 
* @param isMsg : 메시지 창 전시 여부 
* @param lbTxt : 메시지를 전시할 경우 해당 보여줄 명칭 
***************************************************************/
function gfn_isVaildBizNo( bizNo, isCorpBiz, isMsg, lbTxt) {
    if ( length( bizNo ) != 10 ) {
        if ( isMsg ) {
            gfn_showAlert("CW00038",  lbTxt);
        }
        return false;
    }
    
	if (isCorpBiz != "E")
	{
		alert("aa");
		var rtn = true;
		var nA = Array();
		for ( var i = 0; i < length( bizNo ); i++) {
			nA[i] = toInteger(charAt( bizNo, i ) );
		}
		
		var nSum = nA[0] * 100 + nA[1] * 10 + nA[2];
		
		if ( nSum < 101 || nSum > 622 ) {
			if ( isMsg ) {
				gfn_showAlert("CW00050" );
			}
			return false;
		}
	
		nSum = nA[3] * 10 + nA[4];
		if ( isCorpBiz ) {
			// 법인 사업자 확인
			if( nSum < 81 || nSum > 87 ) {
				if ( isMsg ) {
					gfn_showMsg("CW00051", "81~87" );
				}
				return false;
			}
		} else {
			// 개인 사업자 확인 
			if ( (nSum < 1 || nSum > 80) ) {
				if (nSum < 88 || nSum > 99) {
					if ( isMsg ) {
						gfn_showAlert("CW00051", "01~80 or 88~99" );
						//"사업자번호 중간 2자리가 01~80 또는 88~99에 포함되는 값이여야 합니다.\n확인하여 주십시요." );
					}
					return false;
				}
			}
		}
    }
    // 상세 법인 사업자 번호 확인 
    var rtn = gfn_isVaildSubBizNo(nA);
    
    if ( rtn == false && isMsg ) {
		gfn_showAlert("CW00038",  lbTxt);
    }
    return rtn;
}

/****************************************************************
* 사업자 번호 체크  ( 상세 )
***************************************************************/
function gfn_isVaildSubBizNo( nA ) {
    var nBuf = nA[8] * 5;
    var nK   = nBuf / 10;
    var nL   = nBuf % 10;

    var nSum = (nA[0] * 1) + (nA[1] * 3) + (nA[2] * 7) + (nA[3] * 1) + (nA[4] * 3) + (nA[5] * 7) 
            + (nA[6] * 1) + (nA[7] * 3) + nK + nL;

    var nMod  = nSum % 10;
    var nGrnd = 10 - nMod;
    
    if ( nMod == 0 ) {
        nGrnd = 0;
    }
   
    if (nGrnd == nA[9]) {
        return true;
    }
    
    return false;
}