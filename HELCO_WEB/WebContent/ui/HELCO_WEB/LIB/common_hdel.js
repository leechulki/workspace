﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*================================================================================
 *  프로그램 설명 : 공통 Script
 *  프로그램 ID : common_hdel
 *  작성일자 : 2012/05/11
 *  버전 : V1.0 : 2012.09.03 : 신규
 *  변경 이력
    일자            작성자          비고
    ------------------------------------------------------------
    2012/05/11                      최초 작성
================================================================================*/

/*===============================================================================
*   상수 선언
================================================================================*/ 

var GC_CDGB_SPART 			= "SPART"; 			// 제품군 
var GC_CDGB_REGION 			= "REGION"; 		// 지역 
var GC_CDGB_WAERK 			= "WAERK"; 			// 통화 
var GC_CDGB_BDGBN			= "BDGBN";			// 빌딩구분
var GC_CDGB_CHEOR			= "CHEOR";			// 검사기관
var GC_CDGB_TURNKEY 		= "TURNKEY";		// 공사구분 
var GC_CDGB_CHRES    		= "CHRES";		    // 변경사유
var GC_CDGB_REDEP 	    	= "REDEP";		    // 귀책부서 
var GC_CDGB_LIFNRCHK 		= "LIFNRCHK";		// 계약형태 
var GC_CDGB_KISCON 			= "KISCON";			// KISCON등록(원도급) 
var GC_CDGB_HEL_REG 		= "HEL_REG";		// 당사등록  
var GC_CDGB_HB_STN 			= "HB_STN";			// 하자보증개시기준   
var GC_CDGB_MB_STN 			= "MB_STN";			// 무상보수개시기준   
var GC_CDGB_HB_PUB 			= "HB_PUB";			// 보증보험발행처   
var GC_CDGB_KVGR1 			= "KVGR1";			// 계산서 구분

var GC_DOMNAME_ZFID_REASON 	= "ZFID_REASON"; 	// 공통코드 : 채권사유
var GC_DOMNAME_ZSDDMEKIND 	= "ZSDDMEKIND"; 	// 공통코드 : 법조치종류
var GC_DOMNAME_ZSDDDEBTR 	= "ZSDDDEBTR"; 		// 공통코드 : 채무자정보

var GC_PGMID_SSO0060     	= "SDSO000060";		// 프로그램ID	: 수주생성

var GC_FONTCOLOR_EDITABLE_F = "user21";   		// 그리드헤더컬러_수정불가능한 컬럼	(검정)
var GC_FONTCOLOR_EDITABLE_T = "user34";   		// 그리드헤더컬러_수정가능한 컬럼	(파랑)
var GC_STATUS_RED 			= "status_red";   	// 그리드 레코드 상태 - 오류
var GC_STATUS_YELLOW 		= "status_yellow";  // 그리드 레코드 상태 - 확인필요
var GC_STATUS_GREEN 		= "status_green";   // 그리드 레코드 상태 - 정상
var GC_SO_FILTER_COND       = "sign='I'||sign='E'"; // SO 필터조건
var GC_BUYR_FILTER_COND     = "sign='I'||sign='E'"; // 거래선 필터조건
var GC_PRJT_FILTER_COND     = "sign='I'||sign='E'"; // 프로젝트 필터조건
/****************************************************************
* 공통코드로 관리하지 않는 코드성 리스트를 데이타셋에 추가하기
* 2012.05.11 : GNY 
* @param pFrmNm   : FORM NAME
* @param pDsNm   : dataset 구분명
                   - spart : 제품군 
* @param pDsObj   : dataset obj
* @param pDefaultIndex   : pDefaultIndex
* @param pIsAllYn : 전체 추가여부 (Y/N)
* @param pIsSelYn : 선택 추가여부  (Y/N)
***************************************************************/
function gfn_SetListCode(pFrmNm, pDsNm, pDsObj, pCtlObj, pDefaultIndex, pIsAllYn, pIsSelYn) 
{ 
	// trace("gfn_SetListCode.pDsNm : " + pDsNm);
	// trace("gfn_SetListCode.pIsAllYn : " + pIsAllYn);
	// trace("gfn_SetListCode.pIsSelYn : " + pIsSelYn); 
	
	
	var ds 			= pDsObj;
	var all_cd 		= "";
	var all_name 	= gfn_getCodeNameHdel(G_CODE_ALL); 
	var sel_cd 		= "";
	var sel_name 	= gfn_getCodeNameHdel(G_CODE_SELECT);
	
	if (pIsAllYn == "Y")
	{		
		ds.addRow();
		ds.setColumn(ds.row, "CODE", all_cd);
		ds.setColumn(ds.row, "CODE_NAME", all_name);
	}
	if (pIsSelYn == "Y")
	{		
		ds.addRow();
		ds.setColumn(ds.row, "CODE", sel_cd);
		ds.setColumn(ds.row, "CODE_NAME", sel_name);
	} 

	// 2012.11.13 영문반영
	if (G_LANG == 'ko')
	{
		if (pDsNm == GC_CDGB_SPART)  // 제품군
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "10");
			ds.setColumn(ds.row, "CODE_NAME", "승강기"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "20");
			ds.setColumn(ds.row, "CODE_NAME", "주차"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "30");
			ds.setColumn(ds.row, "CODE_NAME", "물류"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "40");
			ds.setColumn(ds.row, "CODE_NAME", "PSD");
		}  
		else if (pDsNm == GC_CDGB_WAERK)  // 통화
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "KRW");
			ds.setColumn(ds.row, "CODE_NAME", "KRW"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "USD");
			ds.setColumn(ds.row, "CODE_NAME", "USD");  
		}  
		else if (pDsNm == GC_CDGB_REGION)  // 지역
		{     
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "E5");
			ds.setColumn(ds.row, "CODE_NAME", "서울/경기");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G1");
			ds.setColumn(ds.row, "CODE_NAME", "부산");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G2");
			ds.setColumn(ds.row, "CODE_NAME", "대구");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G3");
			ds.setColumn(ds.row, "CODE_NAME", "광주");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G4");
			ds.setColumn(ds.row, "CODE_NAME", "대전");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G5");
			ds.setColumn(ds.row, "CODE_NAME", "전주");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G6");
			ds.setColumn(ds.row, "CODE_NAME", "강원");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G7");
			ds.setColumn(ds.row, "CODE_NAME", "울산");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G8");
			ds.setColumn(ds.row, "CODE_NAME", "인천");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G9");
			ds.setColumn(ds.row, "CODE_NAME", "중부");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "H1");
			ds.setColumn(ds.row, "CODE_NAME", "마창");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "H2");
			ds.setColumn(ds.row, "CODE_NAME", "제주"); 
		}
		else if (pDsNm == GC_CDGB_BDGBN)  // 빌딩구분
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "A");
			ds.setColumn(ds.row, "CODE_NAME", "아파트"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "B");
			ds.setColumn(ds.row, "CODE_NAME", "빌딩");  
		}  
		else if (pDsNm == GC_CDGB_CHEOR)  // 검사기관
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "01");
			ds.setColumn(ds.row, "CODE_NAME", "승강기 관리원"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "02");
			ds.setColumn(ds.row, "CODE_NAME", "표준 협회");  
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "03");
			ds.setColumn(ds.row, "CODE_NAME", "현대 엘리베이터");  
		}
		else if (pDsNm == GC_CDGB_TURNKEY )  // 공사구분 
		{ /*
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "일반공사"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "관급(주공,SH,시구청발주 등)");   
			*/			
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "A");
			ds.setColumn(ds.row, "CODE_NAME", "삼성물산"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "B");
			ds.setColumn(ds.row, "CODE_NAME", "지하철 발전소");   
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "C");
			ds.setColumn(ds.row, "CODE_NAME", "1군건설사/10대이상현장"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "D");
			ds.setColumn(ds.row, "CODE_NAME", "일반공사");   
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "E");
			ds.setColumn(ds.row, "CODE_NAME", "관급공사"); 	
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "F");
			ds.setColumn(ds.row, "CODE_NAME", "교체공사 10대이상"); 				
		}  
		else if (pDsNm == GC_CDGB_LIFNRCHK )  // 계약형태 
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "직발주(2000입력)"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "공통수급(협력업체입력)");   
			//ds.addRow();
			//ds.setColumn(ds.row, "CODE", "3");
			//ds.setColumn(ds.row, "CODE_NAME", "공통수급(미지정)");   
		}  
		
		else if (pDsNm == GC_CDGB_KISCON )  // KISCON등록(원도급) 
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "등록"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "미등록");    
		}  
		
		else if (pDsNm == GC_CDGB_HEL_REG )  // 당사등록 --> 건설공사대장 통보유무
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "대상"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "비대상");  
			/*ds.addRow();
			ds.setColumn(ds.row, "CODE", "3");
			ds.setColumn(ds.row, "CODE_NAME", "등록불가");  
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "4");
			ds.setColumn(ds.row, "CODE_NAME", "보류");  */ //엔트리제외 설치부요청 2013.07.02
		}  
		
		else if (pDsNm == GC_CDGB_HB_STN)  // 하자보증개시기준   
		{  
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "발행비대상(완성검사시)"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "발행비대상(입주시)");   
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "3");
			ds.setColumn(ds.row, "CODE_NAME", "발행비대상(건물준공시)"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "4");
			ds.setColumn(ds.row, "CODE_NAME", "발행비대상(고객인도시)"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "5");
			ds.setColumn(ds.row, "CODE_NAME", "발행대상"); 
		}  		
		else if (pDsNm == GC_CDGB_MB_STN)  // 무상보수개시기준   
		{  
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "완성검사시"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "입주시");   
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "3");
			ds.setColumn(ds.row, "CODE_NAME", "건물준공시"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "4");
			ds.setColumn(ds.row, "CODE_NAME", "고객인도시"); 
		}  
		else if (pDsNm == GC_CDGB_HB_PUB )  // 보증보험발행처      
		{   
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "서울보증보험"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "대한보증보험");    
		}
		else if (pDsNm == GC_CDGB_KVGR1 )  // 계산서 구분 
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "");
			ds.setColumn(ds.row, "CODE_NAME", "일반"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "001");
			ds.setColumn(ds.row, "CODE_NAME", "지로"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "002");
			ds.setColumn(ds.row, "CODE_NAME", "지로 선발행"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "003");
			ds.setColumn(ds.row, "CODE_NAME", "CMS"); 
		}
		else if (pDsNm == GC_CDGB_CHRES )  // 변경사유
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "C");
			ds.setColumn(ds.row, "CODE_NAME", "고객요청");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "S");
			ds.setColumn(ds.row, "CODE_NAME", "영업누락/오류");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "L");
			ds.setColumn(ds.row, "CODE_NAME", "LAYOUT변경");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "E");
			ds.setColumn(ds.row, "CODE_NAME", "승인사양입력");
		}
		else if (pDsNm == GC_CDGB_REDEP )  // 귀책부서
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "S");
			ds.setColumn(ds.row, "CODE_NAME", "영업");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "T");
			ds.setColumn(ds.row, "CODE_NAME", "기술영업");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "D");
			ds.setColumn(ds.row, "CODE_NAME", "설계");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "I");
			ds.setColumn(ds.row, "CODE_NAME", "설치");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "C");
			ds.setColumn(ds.row, "CODE_NAME", "고객");
		}
	}
	else	// 한국어 이외
	{
		if (pDsNm == GC_CDGB_SPART)  // 제품군
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "10");
			ds.setColumn(ds.row, "CODE_NAME", "승강기_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "20");
			ds.setColumn(ds.row, "CODE_NAME", "주차_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "30");
			ds.setColumn(ds.row, "CODE_NAME", "물류_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "40");
			ds.setColumn(ds.row, "CODE_NAME", "PSD");
		}  
		else if (pDsNm == GC_CDGB_WAERK)  // 통화
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "KRW");
			ds.setColumn(ds.row, "CODE_NAME", "KRW"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "USD");
			ds.setColumn(ds.row, "CODE_NAME", "USD");  
		}  
		else if (pDsNm == GC_CDGB_REGION)  // 지역
		{     
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "E5");
			ds.setColumn(ds.row, "CODE_NAME", "서울/경기_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G1");
			ds.setColumn(ds.row, "CODE_NAME", "부산_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G2");
			ds.setColumn(ds.row, "CODE_NAME", "대구_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G3");
			ds.setColumn(ds.row, "CODE_NAME", "광주_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G4");
			ds.setColumn(ds.row, "CODE_NAME", "대전_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G5");
			ds.setColumn(ds.row, "CODE_NAME", "전주_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G6");
			ds.setColumn(ds.row, "CODE_NAME", "강원_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G7");
			ds.setColumn(ds.row, "CODE_NAME", "울산_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G8");
			ds.setColumn(ds.row, "CODE_NAME", "인천_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "G9");
			ds.setColumn(ds.row, "CODE_NAME", "중부_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "H1");
			ds.setColumn(ds.row, "CODE_NAME", "마창_E");
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "H2");
			ds.setColumn(ds.row, "CODE_NAME", "제주_E"); 
		}
		else if (pDsNm == GC_CDGB_BDGBN)  // 빌딩구분
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "A");
			ds.setColumn(ds.row, "CODE_NAME", "APARTMENT"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "B");
			ds.setColumn(ds.row, "CODE_NAME", "BUILD");  
		}  
		else if (pDsNm == GC_CDGB_CHEOR)  // 검사기관
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "01");
			ds.setColumn(ds.row, "CODE_NAME", "승강기 관리원_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "02");
			ds.setColumn(ds.row, "CODE_NAME", "표준 협회_E");  
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "03");
			ds.setColumn(ds.row, "CODE_NAME", "현대 엘리베이터_E");  
		}
		else if (pDsNm == GC_CDGB_TURNKEY )  // 공사구분 
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "일반공사_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "관급(주공,SH,시구청발주 등)_E");   
		}  
		else if (pDsNm == GC_CDGB_LIFNRCHK )  // 계약형태 
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "직발주(2000입력)_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "공통수급(협력업체입력)_E");   
		}  
		
		else if (pDsNm == GC_CDGB_KISCON )  // KISCON등록(원도급) 
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "등록_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "미등록_E");    
		}  
		
		else if (pDsNm == GC_CDGB_HEL_REG )  // 당사등록 
		{ 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "등록_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "미등록_E");  
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "3");
			ds.setColumn(ds.row, "CODE_NAME", "등록불가_E");  
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "4");
			ds.setColumn(ds.row, "CODE_NAME", "보류_E");  
		}  
		
		else if (pDsNm == GC_CDGB_HB_STN)  // 하자보증개시기준   
		{  
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "발행비대상(완성검사시)_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "발행비대상(입주시)_E");   
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "3");
			ds.setColumn(ds.row, "CODE_NAME", "발행비대상(건물준공시)_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "4");
			ds.setColumn(ds.row, "CODE_NAME", "발행비대상(고객인도시)_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "5");
			ds.setColumn(ds.row, "CODE_NAME", "발행대상_E"); 
		}  
		else if (pDsNm == GC_CDGB_HB_PUB )  // 보증보험발행처      
		{   
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "1");
			ds.setColumn(ds.row, "CODE_NAME", "서울보증보험_E"); 
			ds.addRow();
			ds.setColumn(ds.row, "CODE", "2");
			ds.setColumn(ds.row, "CODE_NAME", "대한보증보험_E");    
		}
	}

	if (pCtlObj.GetType() == "Combo")
	{
		pCtlObj.index  = pDefaultIndex;
	}
	else if (pCtlObj.GetType() == "Grid")
	{
		
	}
}

/****************************************************************
* 공통코드로 관리하지 않는 코드성 리스트의 코드에 해당하는 코드값 리턴
* 2012.05.11 : GNY 
* @param pFrmNm   : FORM NAME
* @param pCdGb   : 코드구분
                   - spart : 제품군 
* @param pCd  : 코드 
***************************************************************/
function gfn_GetListCodeName(pFrmNm, pCdGb, pCd) 
{ 
	// 2012.11.13 영문반영
	if (G_LANG == 'ko')
	{
		if (pCdGb == GC_CDGB_SPART)  // 제품군
		{ 
			if (pCd == "10") return "승강기";  
			if (pCd == "20") return "주차";  
			if (pCd == "30") return "물류";  
			if (pCd == "40") return "PSD";   
		}  
		else if (pDsNm == GC_CDGB_WAERK)  // 통화
		{
			if (pCd == "KRW") return "KRW";  
			if (pCd == "USD") return "USD"; 
		}
		else if (pCdGb == GC_CDGB_SPART)  // 지역
		{
			if (pCd == "E5") return "서울/경기";
			if (pCd == "G1") return "부산";
			if (pCd == "G2") return "대구";
			if (pCd == "G3") return "광주";
			if (pCd == "G4") return "대전";
			if (pCd == "G5") return "전주";
			if (pCd == "G6") return "강원";
			if (pCd == "G7") return "울산";
			if (pCd == "G8") return "인천";
			if (pCd == "G9") return "중부";
			if (pCd == "H1") return "마창";
			if (pCd == "H2") return "제주";
		}
		else if (pDsNm == GC_CDGB_BDGBN)  // 빌딩구분
		{
			if (pCd == "A") return "아파트";
			return "빌딩";  		// 그 외는 모두 빌딩
		}
		else if (pDsNm == GC_CDGB_CHEOR)  // 검사기관
		{
			if (pCd == "01") return "승강기 관리원";  
			if (pCd == "02") return "표준 협회"; 
			if (pCd == "03") return "현대 엘리베이터"; 
		}
		
		
		
		else if (pDsNm == GC_CDGB_TURNKEY)  // 공사구분 
		{
			if (pCd == "1") return "일반공사";  
			if (pCd == "2") return "관급";  
		}
		else if (pDsNm == GC_CDGB_LIFNRCHK)  // 계약형태 
		{
			if (pCd == "1") return "직발주";  
			if (pCd == "2") return "공통수급";  
		}
		else if (pDsNm == GC_CDGB_KISCON)  // KISCON등록(원도급)  
		{
			if (pCd == "1") return "등록";  
			if (pCd == "2") return "미등록";  
		}
		else if (pDsNm == GC_CDGB_HEL_REG)  // 당사등록 
		{
			if (pCd == "1") return "등록";  
			if (pCd == "2") return "미등록"; 
			if (pCd == "3") return "등록불가"; 
			if (pCd == "3") return "보류"; 
		}
	}
	else	// 한국어 이외
	{
		if (pCdGb == GC_CDGB_SPART)  // 제품군
		{ 
			if (pCd == "10") return "승강기_E";  
			if (pCd == "20") return "주차_E";  
			if (pCd == "30") return "물류_E";  
			if (pCd == "40") return "PSD";   
		}  
		else if (pDsNm == GC_CDGB_WAERK)  // 통화
		{
			if (pCd == "KRW") return "KRW";  
			if (pCd == "USD") return "USD"; 
		}
		else if (pCdGb == GC_CDGB_SPART)  // 지역
		{
			if (pCd == "E5") return "서울/경기_E";
			if (pCd == "G1") return "부산_E";
			if (pCd == "G2") return "대구_E";
			if (pCd == "G3") return "광주_E";
			if (pCd == "G4") return "대전_E";
			if (pCd == "G5") return "전주_E";
			if (pCd == "G6") return "강원_E";
			if (pCd == "G7") return "울산_E";
			if (pCd == "G8") return "인천_E";
			if (pCd == "G9") return "중부_E";
			if (pCd == "H1") return "마창_E";
			if (pCd == "H2") return "제주_E"; 
		}
		else if (pDsNm == GC_CDGB_BDGBN)  // 빌딩구분
		{
			if (pCd == "A") return "APARTMENT";
			return "BUILD";  		// 그 외는 모두 빌딩
		}
		else if (pDsNm == GC_CDGB_CHEOR)  // 검사기관
		{
			if (pCd == "01") return "승강기 관리원_E";  
			if (pCd == "02") return "표준 협회_E"; 
			if (pCd == "03") return "현대 엘리베이터_E"; 
		}
		
		
		
		else if (pDsNm == GC_CDGB_TURNKEY)  // 공사구분 
		{
			if (pCd == "1") return "일반공사_E";  
			if (pCd == "2") return "관급_E";  
		}
		else if (pDsNm == GC_CDGB_LIFNRCHK)  // 계약형태 
		{
			if (pCd == "1") return "직발주_E";  
			if (pCd == "2") return "공통수급_E";  
		}
		else if (pDsNm == GC_CDGB_KISCON)  // KISCON등록(원도급)  
		{
			if (pCd == "1") return "등록_E";  
			if (pCd == "2") return "미등록_E";  
		}
		else if (pDsNm == GC_CDGB_HEL_REG)  // 당사등록 
		{
			if (pCd == "1") return "등록_E";  
			if (pCd == "2") return "미등록_E"; 
			if (pCd == "3") return "등록불가_E"; 
			if (pCd == "3") return "보류_E"; 
		}
	}
}

/****************************************************************
* 특정 컨트롤에 포커싱을 위치시키고 반전표시하기
* 2012.05.11 : GNY
* @param pObj   : 컨트롤 obj
* @param pIsSelYn : 반전표시여부  (Y/N) 
***************************************************************/
function gfn_SetFocus(pObj, pIsSelYn)
{
	pObj.SetFocus();					// 포커싱
	if (pIsSelYn == "Y") pObj.SetSel(); // 반전표시
} 

/****************************************************************
* 팝업 오픈
* 2012.05.11 : GNY
* @param pUrl  : 컨트롤 obj
* @param pModalYn  : 컨트롤 obj
* @param pArg : 파라메터
***************************************************************/
function gfn_openPopUpUrlHdel(pUrl, pModalYn, pArg)
{
	// 임시
	return gfn_openPopupUrl(pUrl, pModalYn, pArg);	
	//return gfn_openPopup(pUrl,true,pArg); // AS-IS
}
 
/****************************************************************
* 문자열에 LPAD, RPAD
* 2012.05.15 : GNY
* @param pStr  : 원 문자열
* @param pPadStr  : 붙이고자 하는 문자
* @param pTotLen : 전체길이
* @param pPosGb : 붙이고자 하는 위치 (L:Left, R:Right)
***************************************************************/
function gfn_SetStrPad(pStr, pPadStr, pTotLen, pPosGb)
{
	var RetStr ="";
	
	if (trim(pStr).length == 0) return pStr;  
	
	if (pPosGb == "L")  	// lpad
	{
		RetStr = Lpad(trim(pStr), pPadStr, pTotLen);
	}
	else if (pPosGb == "R")	// rpad
	{
		RetStr = Rpad(trim(pStr), pPadStr, pTotLen);
	}
	else 
	{
		RetStr = pStr;
	}
	return RetStr;
}

 

/****************************************************************
* 콤보에 - 전체 - 또는 - 선택 - 행 추가
* 2012.05.17 : GNY
* @param pDsObj  : 데이타셋
* @param pRowIndex  : 붙이고자 하는 위치인덱스번호(-1:맨뒤, 그 외 는 그 위치)
* @param pAllYn : 전체 추가여부
* @param pSelYn : 선택 추가여부 
* @param pCodeColId : 코드 컬럼ID  (default : "CODE")
* @param pNameColId : 코드명 컬럼ID (default : "CODE_NAME")
***************************************************************/
function gfn_InsertRowCombo(pDsObj, pRowIndex, pAllYn, pSelYn, pCodeColId, pNameColId)
{  
	var code_name = decode(pAllYn, "Y", gfn_getCodeNameHdel(G_CODE_ALL)
								, decode(pSelYn,"Y", gfn_getCodeNameHdel(G_CODE_SELECT), "")
						   ); 
	var code_colid = iif(length(pCodeColId)==0, "CODE"		, pCodeColId);
	var name_colid = iif(length(pNameColId)==0, "CODE_NAME"	, pNameColId);
	
	if (pRowIndex == -1)  // 맨뒤에 추가
	{
		pDsObj.AddRow();
	}
	else				// 원하는 위치에 추가
	{ 
		pDsObj.InsertRow(pRowIndex); 
	}
	pDsObj.SetColumn(pDsObj.row, code_colid, "");
	pDsObj.SetColumn(pDsObj.row, name_colid, code_name);
	pDsObj.ApplyChange();  
		
}



/****************************************************************
* 날짜 문자열을 받아서 날짜가 아닌 경우 "" return
* 2012.05.30 : GNY
* @param pFrmNm   : FORM NAME
* @param pDate   : 날짜 문자열  
***************************************************************/
function gfn_getChkDate(pFrmNm, pDate)
{  
	var RetStr = "";
	
	if (trim(pDate).length == 0) return RetStr;  
	
	RetStr = DECODE(pDate	,"0000-00-00", ""
							,"0000-00-0 ", ""
							,"    -  -  ", ""
							, pDate
					);
	RetStr = replace(RetStr,"-","");
	RetStr = replace(RetStr,".","");
	
	return RetStr;
		
}  


/****************************************************************
* 날짜 문자열을 받아서 날짜가 아닌 경우 "" return
* 2012.05.30 : GNY
* @param pFrmNm   : FORM NAME
* @param pDate   : 날짜 문자열  
* @param pMask   : mask 문자 (default: ".")
***************************************************************/
function gfn_getFormatDate(pFrmNm, pDate, pMask)
{  
	//trace("gfn_getFormatDate");
	
	var RetStr = "";
	var len = length(trim(pDate));
	
	// trace("gfn_getFormatDate.pDate : " + pDate);
	// trace("gfn_getFormatDate.len : " + len);
	// trace("gfn_getFormatDate.pMask : " + pMask);
	
	if (len == 0) return RetStr; 
	
	RetStr = substr(pDate, 0, 4) 							// 년도
			+ iif(len>=6, pMask + substr(pDate, 4, 2), "")	// 월
			+ iif(len==8, pMask + substr(pDate, 6, 2), "")	// 일
			; 
	
	//trace("gfn_getFormatDate.RetStr : " + RetStr);
	
	return RetStr;
		
}

/****************************************************************
* 날짜문자열에 대해 화면에 보여질 mask 설정
* 2012.05.30 : GNY
* @param pFrmNm   : FORM NAME  
* @param pLen   : 날짜 길이
***************************************************************/
function gfn_getMaskDate(pFrmNm, pLen, pMask)
{    
	var mask = iif(length(pMask)==0, ".", pMask);
	var RetStr = "####" + mask + "##" +mask + "##"; 
	
	if (isValidObject(pLen)==false)
	{ 
		if (pLen==6) RetStr = "####" + mask + "##";
		if (pLen==14) RetStr = "####" + mask + "##" + mask + "## ##:##:##";
	}
	
	return RetStr; 
} 
 
 
/****************************************************************
* 그리드의 특정열에서 스크롤 되지 않도록 FIX
* 2012.06.12: GNY
* @param pFrmNm   : FORM NAME  
***************************************************************/
function gfn_grid_fix(pObjjGrd, pFixColIndex)
{ 
	for (var i=0; i<=pFixColIndex;i++)
	{
		pObjjGrd.SetColProp(i, "Fix", true); 
	}
}


/****************************************************************
* 그리드에서 keydown 시 클립보드 기능
* 2012.06.14: GNY
* @param pObjGrd   : 그리드 오브젝트
* @param pObjDataset : 데이타셋 오브젝트
* @param pCtrl   : 컨트롤키
* @param pChar   : 문자
***************************************************************/
function gfn_GrdCellCopyPasteReset(pObjGrd, pObjDataset, pCtrl, pChar)
{    
	
	// Ctrl + C
	if ( pCtrl == 1 && pChar == 67 ) 
	{ 
	
		// trace("gfn_GrdCellCopyPasteReset.Ctrl + C");
		gfn_GrdCellCopy(pObjGrd, pObjDataset);
	} 
	// Ctrl + V
	else if ( pCtrl == 1 && pChar == 86 ) 
	{ 
		if (pObjGrd.Editable == false) return;
		gfn_GrdCellPaste(pObjGrd, pObjDataset);    
	}
	// Ctrl + Z
	else if ( pCtrl == 1 && pChar == 90 ) 
	{ 
		if (pObjGrd.Editable == false) return;
		gfn_GrdCellReset(pObjGrd, pObjDataset);
	} 
	// Ctrl + P
	else if ( pCtrl == 1 && pChar == 80 ) 
	{   
		if (pObjGrd.Format == "Default")
		{
			pObjGrd.Format = "Sum";
		}
		else if (pObjGrd.Format == "Sum")
		{
			pObjGrd.Format = "Default";
		}
	}
	
}

/*********************************************************************
 * 기능 : 그리드의 선택영역을 Clipboard로 Copy 한다.
 * 인수 : objGrid       : Area Select 된 Grid
         strSeparator  : Colunm 구분자.
 * 예제 : grd_fn_ClipboardCopy(objGrid, ",");
 *********************************************************************/
function gfn_GrdCellCopy(objGrid, orgDataset) 
{
	//trace("gfn_GrdCellCopy");
	gfn_GrdClipboardCopy(objGrid, orgDataset, "	");
}

function gfn_GrdClipboardCopy(objGrid, orgDataset, strSeparator) 
{
//	var orgDataset = eval(objGrid.BindDataset);

// trace("gfn_GrdClipboardCopy");

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
		nAreaEndCol   = objGrid.GetAreaEndCol();
	} 
	else 
	{
		nAreaStartRow = objGrid.CurrentRow;
		nAreaEndRow   = objGrid.CurrentRow;
		nAreaStartCol = objGrid.CurrentCell;
		nAreaEndCol   = objGrid.CurrentCell+1;
	}

	// trace("nAreaStartRow : " + nAreaStartRow);
	// trace("nAreaEndRow : " + nAreaEndRow); 
	
	for(var nRow = nAreaStartRow; nRow <= nAreaEndRow; nRow++) 
	{
	

	// trace("nAreaStartCol : " + nAreaStartCol);
	// trace("nAreaEndCol : " + nAreaEndCol); 

		for(var nCell = nAreaStartCol; nCell < nAreaEndCol; nCell++) 
		{
			if(objGrid.GetColProp(nCell,"Width") > 0) 
			{
				strColID = objGrid.GetCellProp("body",nCell,"colid");
				strValue = orgDataset.GetColumn(nRow,strColID); 
				strClipboard = strClipboard + strValue + strSeparator; 
				
				// trace("strColID bef : " + strColID); 
				// trace("strValue : " + strValue); 
				// trace("strClipboard: " + strClipboard); 
				
			}
		}

		strClipboard = substr(strClipboard,0,length(strClipboard)-1);
		strClipboard = strClipboard + "\n";
	}


	strClipboard = substr(strClipboard,0,length(strClipboard)-1);

// trace("strClipboard : " + strClipboard); 

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
function gfn_GrdCellPaste(objGrid, orgDataset) 
{
	gfn_GrdClipboardPaste(objGrid, orgDataset, "	");
}

function gfn_GrdClipboardPaste(objGrid, orgDataset, strSeparator) 
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
				
				//trace("grd_fn_ClipboardPaste.strColID : " + strColID);
				//trace("grd_fn_ClipboardPaste.strValue : " + strValue);
				
				
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
function gfn_GrdCellReset(objGrid, objDataset) 
{
//	var objDataset = eval(objGrid.BindDataset);
  
	var strColID;

	var nAreaStartRow = objGrid.GetAreaStartRow();
	var nAreaEndRow   = objGrid.GetAreaEndRow();
	var nAreaStartCol = objGrid.GetAreaStartCol();
	var nAreaEndCol   = objGrid.GetAreaEndCol(); 
	
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
  
	// trace("nAreaStartRow 	: " + nAreaStartRow);
	// trace("nAreaEndRow 		: " + nAreaEndRow);
	// trace("nAreaStartCol 	: " + nAreaStartCol);
	// trace("nAreaEndCol 		: " + nAreaEndCol); 
	
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
 
/****************************************************************
* 메시지 코드, 메세지를 받아서 Message Bar 영역에 메시지 표시
* @param pMsgDesc 	메세지
* @param pMsgId 	정의되어 있는 메시지 코드
* @param pArr 		메시지 대체 문자 또는 배열
* @param pMsgGbn 	메시지구분 (I, W, E:default)
* @param arr 
***************************************************************/
function gfn_showMsgBar(pMsgDesc, pMsgId, pArr, pMsgGbn, pArr) 
{ 
	var info 	= "#E8F3ED";	// Info
	var warning = "yellow";  	// Warning
	var error 	= "red";		// Error 
	var iconName = "bottom_icon_info";  
	var msg      = "";
	var color    = error;
	
	global.FRM_BOTTOM.img_msgType.imageId 	= iconName; 
	
	if (length(pMsgId) > 0)
	{
		var nRow = gds_message.FindRow("MSG_ID", pMsgId); 
		msg  = iif(nRow > 0, gds_message.GetColumn(nRow, "MSG_DESC"), pMsgId); 
		color 	= decode(substr(pMsgId,1,1), "I", info, "W", warning, "E", error, error);  
	}  
	else 
	{
		msg 	= pMsgDesc;  
		color 	= decode(pMsgGbn, "I", info, "W", warning, "E", error, error);  
	}
	
	global.FRM_BOTTOM.st_msg.value 	= msg;  	// Message 영역에 표시 
	global.FRM_BOTTOM.st_msg.color 	= color; 
		
}  

/****************************************************************
* 그리드의 특정 행, 컬럼에 포커싱
* @param pObjGrd 그리드 
* @param pRowNum 그리드 행번호
* @param pColNum 그리드 컬럼번호
***************************************************************/
function gfn_SetFocusGrid(pObjGrd, pRowNum, pColNum)  
{   
	var ObjDataset =  object(pObjGrd.BindDataset);
	
	pObjGrd.SetFocus();
	pObjGrd.SetCellPos(pColNum);
	
	ObjDataset.Fireevent 	= false; 
	ObjDataset.row 			= pRowNum;
	ObjDataset.Fireevent 	= true;
}

/****************************************************************
* 그리드의 특정 행, 컬럼의 값을 원래 값으로 복원한다.
* @param pObjGrd 그리드 
* @param pRowNum 그리드 행번호
* @param pColNum 그리드 컬럼번호
***************************************************************/
function gfn_SetOrgColumn(pObjGrd,pRowNum, pColNum)  
{      
	var ds = object(pObjGrd.BindDataset); 
	var ColId = "";   
	var Value = ""; 
	
	// trace("gfn_SetOrgColumn.pRowNum : " + pRowNum);
	// trace("gfn_SetOrgColumn.pColNum : " + pColNum);
	
	if (pColNum = -1)  // 전체 컬럼의 값을 원복한다.
	{
		for (var i=0;i<ds.ColCount();i++)
		{ 
			ColId = pObjGrd.GetCellProp("body", i, "colid");   
			Value = ds.GetOrgColumn(pRowNum, ColId);  
			
			
			// trace("gfn_SetOrgColumn.ColId : " + ColId);
			// trace("gfn_SetOrgColumn.Value : " + Value);
			
			// if (Value == null || Value == "") 
			// {		
				// ds.SetColumn(pRowNum, ColId, "");  
			// }
			// else
			// {
				ds.SetColumn(pRowNum, ColId, Value);  
			// }
		}
	}
	else
	{
		ColId = pObjGrd.GetCellProp("body", pColNum, "colid");   
		Value = ds.GetOrgColumn(pRowNum, ColId); 
		
		// trace("gfn_SetOrgColumn.ColId : " + ColId);
		// trace("gfn_SetOrgColumn.Value : " + Value);
		
		// if (Value == null) trace("gfn_SetOrgColumn.Value == null");
		// if (Value == "") trace("gfn_SetOrgColumn.Value == ''");
			
		// if (Value == null || Value == "") 
		// {		
			// ds.SetColumn(pRowNum, ColId, "");  
		// }
		// else
		// {
			ds.SetColumn(pRowNum, ColId, Value);  
		// }
	}
}


/****************************************************************
* 그리드의 특정 행의 변경여부(변경이면 "Y" 리턴, 아니면 "N" 리턴)
* @param pObjGrd 그리드 
* @param pRowNum 그리드 행번호 
* @param pArrColId 데이타셋 컬럼ID배열
* @param pIncludeYN 데이타셋 컬럼ID배열에 대해서만 체크할 지 여부
* ret :  Y(변경), N(변경없음), Z(대상아님)
***************************************************************/
function gfn_GetDatasetChgYN(pObjGrd, pRowNum, pArrColId, pIncludeYN)  
{     
	// trace("gfn_GetDatasetChgYN"); 
	// trace("gfn_GetDatasetChgYN.pArrColId        : " + pArrColId);
	// trace("gfn_GetDatasetChgYN.pArrColId.length : " + pArrColId.length());
	// trace("gfn_GetDatasetChgYN.pRowNum : " + pRowNum);
	
	var ret 		= "N";							// 리턴값 (Y,N,Z) 
	var ds 			= object(pObjGrd.BindDataset);  
	var len	 		= pArrColId.length(); 
	var OldValue 	= "";
	var NewValue 	= ""; 
	var ColID    	= "";
	var ExcludeYN 	= "Y";  // 체크제외컬럼여부
	
	// 등록, 삭제행 이면 "Y" 리턴
	if (ds.GetColumn(pRowNum, "FLAG") == "I" || ds.GetColumn(pRowNum, "FLAG") == "D") return "Y"; 
	
	// 파라메터로 넘어온 컬럼 배열에서만 체크하고자 할 경우
	if (pIncludeYN == "Y")
	{
		// 컬럼ID 배열이 넘어온 경우에는 해당 컬럼ID에 대해서만 체크
		if (pArrColId.length() > 0)
		{
			for (var i=0;i<pArrColId.length();i++)
			{
				OldValue = ds.GetOrgColumn(pRowNum, pArrColId[i]);
				NewValue = ds.GetColumn(pRowNum, pArrColId[i]);
				// trace("gfn_GetDatasetChgYN.ColId : " + pArrColId[i]);
				// trace("gfn_GetDatasetChgYN.OldValue : " + OldValue);
				// trace("gfn_GetDatasetChgYN.NewValue : " + NewValue); 
				if (OldValue <> NewValue) return "Y";
			}
		}
	}
	// 그 외에는 그리드에 조회된 모든 컬럼ID에 대해서 체크
	else
	{  
		for (var i=0;i<pObjGrd.GetColCount();i++)
		{  
			ColID = pObjGrd.GetCellProp("body", i, "colid");  
			
			//단, 하기 컬럼은 체크에서 제외
			for (var i=0;i<pArrColId.length();i++)
			{ 
				if (ColID == pArrColId[i]) ExcludeYN = "Y"; // 제외여부
			}
			
			// 체크 제외컬럼이 아닌 경경우
			if (ExcludeYN == "N")
			{
				OldValue = ds.GetOrgColumn(pRowNum, ColID);
				NewValue = ds.GetColumn(pRowNum, ColID); 
				
				// trace("gfn_GetDatasetChgYN.OldValue : " + OldValue);  
				// trace("gfn_GetDatasetChgYN.NewValue : " + NewValue); 
			
				if (OldValue <> NewValue) return "Y"; 
			}
		}
	}
	 
	return  ret;
}


/****************************************************************
* 데이타셋의 변경속성 초기화
* @param pObjDs 데이타셋 
* @param pRowNum 그리드 행번호  
***************************************************************/ 
function gfn_SetDatasetChgFlag(pObjDs, pRowNum)  
{     
	pObjDs.Fireevent = false;
	pObjDs.SetColumn(pRowNum, "CHK" , "0");	// 선택여부 초기화
	pObjDs.Fireevent = true;   
	pObjDs.SetColumn(pRowNum, "FLAG" , "");	// 변경구분 초기화
}  

/****************************************************************
* 데이타셋의 복사
* @param pObjOrgDs 원소스 데이타셋 
* @param pObjTgtDs 대상 데이타셋 
* @param pCondText 복사조건
* @param pFrText 원소스 데이타셋에 데이타가 존재하지 않을 경우 대상 데이타셋 LOW 에 설정할 값
* @param pToText 원소스 데이타셋에 데이타가 존재하지 않을 경우 대상 데이타셋 HIGH에 설정할 값
***************************************************************/ 
function gfn_copyDsRtn(pObjOrgDs, pObjTgtDs, pCondText, pFrText, pToText)  
{    
	trace("gfn_copyDsRtn.pCondText : " + pCondText);
	trace("gfn_copyDsRtn.pFrText : " + pFrText);
	trace("gfn_copyDsRtn.pToText : " + pToText); 
	
	// 원소스 데이타셋을 대상 데이타셋에 복사
	gfn_copyDs(pObjOrgDs, pObjTgtDs, pCondText);
	
	trace("gfn_copyDsRtn.pObjTgtDs.RowCount() : " + pObjTgtDs.RowCount()); 
	
	// 복사된 건수가 0 건이면
	if (pObjTgtDs.RowCount()==0)
	{ 
		if (length(trim(pFrText)) > 0)
		{
			pObjTgtDs.AddRow();
			pObjTgtDs.SetColumn(0, "SIGN"	, "I");									// 포함 
			pObjTgtDs.SetColumn(0, "OPTION"	, iif(length(pToText)==0, "EQ", "BT"));	// 단일
			pObjTgtDs.SetColumn(0, "LOW"	, trim(pFrText));
			pObjTgtDs.SetColumn(0, "HIGH"	, trim(pToText));  
		}
	} 
}

 

/****************************************************************
* 데이타셋의 컬럼값 변경
* @param pObjDs 데이타셋 
* @param pRowNum 데이타셋 행번호  
* @param pColID 데이타셋 컬럼ID
* @param pColValue 데이타셋 컬럼에 설정할 값  
***************************************************************/ 
function gfn_SetColumn(pObjDs, pRowNum, pColID, pColValue)  
{      
	pObjDs.Fireevent = false;
	pObjDs.SetColumn(pRowNum, pColID, pColValue);
	pObjDs.Fireevent = true;
}


/****************************************************************
* 특정 항목에 데이타셋 Binding
* @param pObj 데이타셋 
* @param pDsID 데이타셋ID
* @param pColID 데이타셋 컬럼ID 
***************************************************************/ 
function gfn_BindColumn(pObj, pDsID, pColID)  
{      
	//trace("gfn_BindColumn.pDsID : " + pDsID);
	//trace("gfn_BindColumn.pColID : " + pColID);
	pObj.BindDataset = pDsID; 
	pObj.Column 	 = pColID; 
}
	

/****************************************************************
* 날짜 오류 검증
* @param pDate 날짜문자열 
* @param pDateLbl 날짜항목명
* @param pIsShowMsg 메세지 출력여부
* 날짜 오류 시 -1이 return됨
***************************************************************/ 
function gfn_chkDate(pDate, pDateLbl, pIsShowMsg)  
{    
	var date =gfn_getChkDate("", pDate);
	
	if (length(date)==0) return 0;
	
	var day = GetDay(iif(length(date)==6, date+"01", date));
	if (day == -1 && pIsShowMsg == true)
	{
	 // "CW10012", "${}의 날짜형식이 잘못되었습니다."
	 gfn_showAlert("CW10012",pDateLbl);
	}
	
	return day;
}	


/****************************************************************
* 서비스 호출 시의 TimeOut 시간 변경
* @param pTime TimeOut 시간 (초단위) 
***************************************************************/ 
function gfn_setSvcTimeout(pTime)
{
	if (length(pTime) == 0) return;
	
	// HTTP SESSION TIMEOUT을 변경
	http.TimeOut = toNumber(pTime);
}
	
/****************************************************************
*  콤보 등에 첫줄에 추가하는 명 
* @param sgubun
***************************************************************/
function gfn_getCodeNameHdel(sgubun)
{
	var rtn = "";
	
	if (sgubun == G_CODE_ALL)
	{
		switch( G_LANG ) 
		{
			case "ko" : rtn = "- 전체 -";
						break;
			case "zh" : rtn = "- 全體 -";
						break;
			case "en" : rtn = "- ALL -";
						break;
		}
	}
	else if (sgubun == G_CODE_SELECT)
	{
		switch( G_LANG ) 
		{
			case "ko" : rtn = "- 선택 -";
						break;
			case "zh" : rtn = "- 選擇 -";
						break;
			case "en" : rtn = "SELECT";
				        break;
		}
	}
	
	return rtn;
}


/****************************************************************
* 특정이벤트를 통해 화면을 메뉴로 오픈하고자 할 경우 사용
* @param pPgmId : 프로그램관리에 등록된 프로그램코드
* @param pParam : 화면을 오픈할 때 사용하고자 하는 파라메터정보
***************************************************************/
function gfn_form_activate(pPgmId, pParam)
{ 
	var nRow = gds_auth.searchRow("PGM_ID== " + quote(pPgmId)); 
	if (nRow < 0)
	{ 
		// "ZW00003", "해당 프로그램을 사용할 권한이 없습니다."); 
		gfn_showAlert("ZW00003");
		return;
	}	
	global.FRM_TOP.ufc_openForm(pPgmId, pParam, gds_auth.getColumn(nRow, "MULT")); 
}

// 임시
// HDEL용 메세지 신규코드는 반드시 10000번대 부터 채번할것
function gfn_message()
{
	
	gds_message.ClearData();
	
	// # C : COMMON
	// # Z : 공통 프로그램이 사용  
	// # I : Info W : Warm E : Error + SEQ ( 5 )
	gfn_message_add("CW00001", "[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00002", "필수 입력항목입니다.\n확인하여 주십시오.");
	gfn_message_add("CW00003", "특수 문자는 입력할 수 없습니다.");
	gfn_message_add("CW00004", "[${}] 항목이 입력될 경우 [${}] 항목은 필수입력입니다.\n확인하여 주십시오.");
	gfn_message_add("CW00005", "변경된 정보가 없습니다.");
	gfn_message_add("CW00006", "[${}]에 변경된 정보가 없습니다.");
	gfn_message_add("CW00007", "[${}]은/는 [${}] 보다 같거나 이후 일자로 입력하여 주십시오.");
	gfn_message_add("CW00008", "[${}] 일자의 값이 잘못되었습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00009", "기간이 잘못 입력되었습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00010", "데이터가 없습니다.");
	gfn_message_add("CW00011", "값이 자리수[${}]로 입력되어야 합니다.\n확인하여 주십시오.");
	gfn_message_add("CW00012", "항목의 시작 값은 종료 값보다 작거나 같아야 합니다.\n확인하여 주십시오.");
	gfn_message_add("CW00013", "처리할 자료를 먼저 선택해 주십시오.");
	gfn_message_add("CW00014", "[${}]: [${}] 이 아닌 경우는 검사요청일 수정 및 취소가 불가합니다.");
	gfn_message_add("CW00015", "[${}]값은 [${}] 보다 같거나 작은값을 입력하여 주십시오.");
	gfn_message_add("CW00016", "구매문서번호,품목번호,검사요청일을 확인하세요.");
	gfn_message_add("CW00017", "수정할 수 없습니다.상태를 확인하세요.");
	gfn_message_add("CW00018", "[${}] 중복된 값을 입력할 수 없습니다.\n다른 값을 입력하여 주십시오.");
	gfn_message_add("CW00019", "삭제대상 문제점보고항목은 이미 사용하였으므로 삭제가 불가능합니다.");
	gfn_message_add("CW00020", "문제점분류를 선택하십시오.");
	gfn_message_add("CW00021", "입력된 값이 있습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00022", "[${}] 을/를 확인하십시요.");
	gfn_message_add("CW00023", "[${}]은/는 [${}] 보다 이전 일자로 입력하여 주십시오.");
	gfn_message_add("CW00024", "[${}]은/는 [${}] 보다 이후 일자로 입력하여 주십시오.");
	gfn_message_add("CW00025", "[${}]은/는 [${}] 과 동일인이 될 수 없습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00026", "거래명세서 저장취소는 거래명세서생성 상태에서만 가능합니다.");
	gfn_message_add("CW00027", "선택한 자료는 이미 전송을 완료한 자료입니다.");
	gfn_message_add("CW00028", "선택한 자료는 전송이 불가능한 자료입니다.\n[${}]");
	gfn_message_add("CW00029", "납품 기준일자 이전으로, 거래명세서 작업이 불가합니다.\n납품 기준일자 ; [${}]");
	gfn_message_add("CW00030", "납품 기준일자가 현재일자를 초과하여 거래명세서 작업이 불가합니다.\n납품 기준일자 ; [${}]");
	gfn_message_add("CW00031", "포함되지 않은 Box자재가 있습니다.\n Box 자재 : [${}]");
	gfn_message_add("CW00032", "전송을 취소할 수 없는 거래명세서 입니다.");
	gfn_message_add("CW00033", "발송대상 자료가 존재하지 않습니다.");
	gfn_message_add("CW00034", "문제점검사 항목이 없습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00035", "이미지 저장을 실패했습니다.");
	gfn_message_add("CW00036", "[${}]은/는 [${}] 보다 같거나 이전 일자로 입력하여 주십시오.");
	gfn_message_add("CW00037", "[${}]번째 행의 [${}]은/는 [${}] 보다 같거나 이후 일자로 입력하여 주십시오.");
	gfn_message_add("CW00038", "${} 값이 잘못 입력되었습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00039", "[${}]번째 행의 호기가 유상계약기간과 중첩됩니다.");
	gfn_message_add("CW00040", "[${}]와 동일한 값이 이미 존재합니다.");
	gfn_message_add("CW00041", "수리공사에서만 NS로 시작되는 자재번호 입력이 가능 합니다.");
	gfn_message_add("CW00042", "[${}]번째 행의 진행상태를 확인하십시오.");
	gfn_message_add("CW00043", "무상일반의 경우 반드시 [${}]번째 행의 확인서를 체크하셔야 합니다.");
	gfn_message_add("CW00044", "청구종류가 다릅니다.청구종류를 확인하여 주십시오.");
	gfn_message_add("CW00045", "[${}] 항목 또는 [${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00046", "정보를 먼저 선택해 주십시오.");
	gfn_message_add("CW00047", "파일은 최대 [${}] 용량까지만 첨부 가능합니다.");
	gfn_message_add("CW00048", "파일의 사이즈가 0인 파일은 첨부 할 수 없습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00049", "현재 ROW의 [${}] 변경사유를 입력하여 주십시오.");
	gfn_message_add("CW00050", "사업자번호 시작 3자리가 올바르지 않습니다.\n확인하여 주십시요.");
	gfn_message_add("CW00051", "사업자번호 중간 2자리가 ${}에 포함되는 값이여야 합니다.\n확인하여 주십시요.");
	gfn_message_add("CW00052", "일치하는 자료가 존재하지 않습니다.");
	gfn_message_add("CW00053", "[${}] 대상이 아닙니다.\n확인하여 주십시오.");
	gfn_message_add("CW00054", "조회 후 작업이 가능합니다.");
	gfn_message_add("CW00055", "[${}]번째 행의 [${}] 을/를 확인하십시요.");
	gfn_message_add("CW00056", "조회 조건이 변경되었습니다.\n다시조회해 주세요.");
	gfn_message_add("CW00057", "[${}]번째 행 : 계약기간이 중복되는 호기가 존재합니다.");
	gfn_message_add("CW00058", "[${}]번째 행 : 계약기간이 이미 만료된 호기가 존재합니다.");
	gfn_message_add("CW00059", "[${}]번째 행 : 타사신규 계약대상이 아닌 호기가 존재합니다.");
	gfn_message_add("CW00060", "기존 자료는 상주기간을 수정할 수 없습니다.");
	gfn_message_add("CW00061", "[${}]번째 행의 [${}]은/는 [${}] 보다 이후 일자로 입력하여 주십시오.");
	gfn_message_add("CW00062", "[${}]번째 행의 [${}]은/는 [${}] 보다 이전 일자로 입력하여 주십시오.");
	gfn_message_add("CW00063", "매출계획 정보가 존재하여 삭제가 불가능합니다.");
	gfn_message_add("CW00064", "해당 정보에 대해 [${}] 정보가 존재합니다.\n [${}] 를 먼저 삭제해 주십시오.");
	gfn_message_add("CW00065", "[${}]을/를 올바르게 선택하셔야 합니다.");
	gfn_message_add("CW00066", "현장답사 정보를 사전에 등록하여야, 착준공계획정보를 입력할 수 있습니다.");
	gfn_message_add("CW00067", "선택한 일자는 휴일입니다.휴일은 입력이 불가합니다.");
	gfn_message_add("CW00068", "진행상태가 [${}]인 경우만 [${}]작업이 가능합니다.\n확인하여 주십시요.");
	gfn_message_add("CW00069", "이미 고객지원부에서 기성마감을 실시하였습니다.\n확인하여 주십시요.");
	gfn_message_add("CW00070", "이미 디지털 세금계산서 작업을 실시하였습니다.\n확인하여 주십시요.");
	gfn_message_add("CW00071", "처리할수 없는 [${}] 입니다.\n확인하여 주십시오.");
	gfn_message_add("CW00072", "삭제가 불가한 데이터입니다.");
	gfn_message_add("CW00073", "수정이 불가한 데이터입니다.");
	gfn_message_add("CW00074", "[${}] 행의 기관 및 접수일을 입력하셔야 합니다.");
	gfn_message_add("CW00075", "[${}] 행은 검사 접수된 데이터가 아닙니다.");
	gfn_message_add("CW00076", "[${}] 행의 접수번호 및 검사예정일을 입력하셔야 합니다.");
	gfn_message_add("CW00077", "[${}] 행의 검사완료일을 입력하셔야 합니다.");
	gfn_message_add("CW00078", "조회기간은 최대 [${}]개월 까지만 가능합니다.\n확인하여 주십시요.");
	gfn_message_add("CW00079", "[${}] 번째 행의 계약기간은 최대 1년을 초과할 수 없습니다.");
	gfn_message_add("CW00080", "고객번호가 등록되지 않았습니다.\n확인하여 주시기 바랍니다.");
	gfn_message_add("CW00081", "통합 Proj. No는 4자리 이상 입력 하여야 합니다.");
	gfn_message_add("CW00082", "[${}] 행의 데이터는 발주정보가 없으므로 삭제가 불가한 데이터입니다.");
	gfn_message_add("CW00083", "[${}] 행의 데이터는 이미 무상발주 정보가 존재합니다.");
	gfn_message_add("CW00084", "[${}] 행의 데이터는 이미 유상일반계약 정보가 존재합니다.");
	gfn_message_add("CW00085", "[${}] 행의 데이터는 이미 유상공사계약 정보가 존재합니다.");
	gfn_message_add("CW00086", "통합프로젝트의 거래선과 계약종류의 데이터가 서로 일치하지 않습니다.");
	gfn_message_add("CW00087", "하나의 거래명세서에 ${}건 이상의 아이템은 저장할 수 없습니다.");
	gfn_message_add("CW00088", "삭제된 거래명세서가 있습니다.[${}]");
	gfn_message_add("CW00089", "[${}] 행의 데이터는 프로젝트 및 호기정보 작성을 미완료하였습니다.");
	gfn_message_add("CW00090", "[${}] 행의 데이터는 프로젝트정보 작성을 미완료하였습니다.");
	gfn_message_add("CW00091", "[${}] 행의 데이터는 호기정보 작성을 미완료하였습니다.");
	gfn_message_add("CW00092", "호기번호 검색조건은 최소 6자 이상 입력 하십시오.");
	gfn_message_add("CW00093", "저장대상 중 유상일반이 아닌 경우가 있습니다.\n확인하여 주십시요.");
	gfn_message_add("CW00094", "저장대상 중 이미 실패보고가 승인된 호기가 있습니다.\n확인하여 주십시요.");
	gfn_message_add("CW00095", "검사 대상 자재의 하나만 선택 하십시오.");
	gfn_message_add("CW00096", "자재번호 검색조건은 최소 6자 이상 입력 하십시오.");
	gfn_message_add("CW00097", "거래명세서는 전송 후 출력 하십시오.");
	gfn_message_add("CW00098", "선택한 자료구분과 실제계약정보가 불일치합니다.");
	gfn_message_add("CW00099", "직영간의 이관은 불가합니다.");
	gfn_message_add("CW00100", "해당월에는 이미 이관요청 작업을 실시하였습니다.(익월로 처리가능)");
	gfn_message_add("CW00101", "이관작업요청이 불가합니다.");
	gfn_message_add("CW00102", "승인 혹은 취소가 불가한 데이터입니다.");
	gfn_message_add("CW00103", "이미 해당 통합Pjt No에 존재하는 호기입니다.");
	gfn_message_add("CW00104", "이미 요청중인 호기입니다.");
	gfn_message_add("CW00105", "해당 사업자등록번호는 존재하지 않습니다.");
	gfn_message_add("CW00106", "본 통합프로젝트에 이미 존재하는 사업자번호 입니다.");
	gfn_message_add("CW00107", "[${}] 항목을 수정하실수 없습니다.");
	gfn_message_add("CW00108", "거래처구분이 '전체' 혹은 '정기'인 경우에만 수정하실수 있습니다.");
	gfn_message_add("CW00109", "[${}] 정보가 존재하므로 삭제가 불가합니다.");
	gfn_message_add("CW00110", "이미 견적정보가 존재합니다.견적정보 삭제 후 수정바랍니다.\n 견적번호 : [${}]");
	gfn_message_add("CW00111", "해당 호기가 이미 호기정보에 존재합니다.");
	gfn_message_add("CW00112", "호기번호는 'L,S,W,G,M,J' 로 시작되어야합니다.");
	gfn_message_add("CW00113", "[${}] 자료가 존재하지 않습니다.");
	gfn_message_add("CW00114", "[${}] 불가능합니다.");
	gfn_message_add("CW00115", "[${}] 같을수 없습니다.");
	gfn_message_add("CW00116", "[${}] 선택하십시오.");
	gfn_message_add("CW00117", "품목표 상세정보가 없습니다.");
	gfn_message_add("CW00118", "[${}]은 100을 넘을 수 없습니다.");
	gfn_message_add("CW00119", "[${}] 건의 미작성된 자료가 있습니다, 발송하시겠습니까?");
	gfn_message_add("CW00120", "[${}] 행의 [${}]은 100을 넘을 수 없습니다.");
	gfn_message_add("CW00121", "대상 중 이미  [${}] 된 데이터가 있습니다.");
	gfn_message_add("CW00122", "대상 중 [${}] 이외의 데이터가 있습니다.");
	gfn_message_add("CW00123", "[${}]은/는 현재년월과 같거나 이후 년월만 가능합니다.\n확인하여 주십시오.");
	gfn_message_add("CW00124", "[${}] 선택 가능합니다.");
	gfn_message_add("CW00125", "주점검자, 부점검자 동인인은 입력할 수 없습니다.");
	gfn_message_add("CW00126", "[${}]행의 [${}]은/는 [${}]보다 작을 수 없습니다.");
	gfn_message_add("CW00127", "프로젝트번호와 호기구분이 동일하여야만 복수선택이 가능합니다.");
	gfn_message_add("CW00128", "시작줄은 완료줄보다 작아야 합니다.");
	gfn_message_add("CW00129", "복사 할 번호와 붙여넣기 할 번호는 달라야 합니다.");
	gfn_message_add("CW00130", "[ , ], [ - ] 제외한 특수문자가 있거나 문장형식이 올바르지 않습니다.");
	gfn_message_add("CW00131", "[ - ] 또는 [ , ] 는 연속해서 사용할 수 없습니다.");
	gfn_message_add("CW00132", "[${}]번째 행의 [${}] 는 승인상태 이므로 삭제하실수 없습니다.");
	gfn_message_add("CW00133", "[${}]은/는 [${}] 미만으로 입력하여야 합니다.");
	gfn_message_add("CW00134", "동일한 층이 있습니다.");
	gfn_message_add("CW00135", "CAR(좌)를 한개이상 입력해 주십시오.");
	gfn_message_add("CW00136", "층을 한개이상 입력해 주십시오.");
	gfn_message_add("CW00137", "이미 해당 자료가 존재합니다.");
	gfn_message_add("CW00138", "[${}] 행의 층은 이미 입력되었습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00139", "해당 호기는 이미 실측정보가 존재합니다.");
	gfn_message_add("CW00140", "[${}]번째 행의 진행상태가 승인이 아니므로 중도해지 하실수 없습니다.");
	gfn_message_add("CW00141", "자재번호 검색조건은 최소 ${}자 이상 입력 하십시오.");
	gfn_message_add("CW00142", "[${}]의 합은 [${}]이어야 합니다.\n확인하여 주십시오.");
	gfn_message_add("CW00143", "이미 견적승인 판정결과가 등록 되어, [${}]이/가 불가합니다.\n확인하여 주십시오.");
	gfn_message_add("CW00144", "[${}] 항목의 [${}] 값이 입력될 경우 [${}] 항목은 필수입력입니다.\n확인하여 주십시오.");
	gfn_message_add("CW00145", "[${}] 행의 데이터는 통합프로젝트 호기정보 데이터가  없으므로 삭제가 불가 합니다.");
	gfn_message_add("CW00146", "[${}] 행의 데이터는 요청상태가 아니므로 승인 혹은 취소가 불가 합니다.");
	gfn_message_add("CW00147", "WBS입력란에 프로젝트 번호 6자리 입력후 등록 가능합니다.");
	gfn_message_add("CW00148", "해당 데이터는 SAP에서 먼저 고객등록을 한후 등록, 수정이 가능합니다.");
	gfn_message_add("CW00149", "이미 저장을 하셨습니다.다시 저장하시려면 현재 창을 다시 열어주십시요.");
	gfn_message_add("CW00150", "[${}] 행의 데이터중 누락된 협력사, 지역, 시내외구분, 빌딩구분을 호기기본정보에서 수정하신후 저장 가능합니다.");
	
	gfn_message_add("CW00151", "해당 협력사로 조회 후 발주서 출력이 가능합니다.");
	gfn_message_add("CW00152", "진행상태를 완료 조회 후 발주서 출력이 가능합니다.");
	gfn_message_add("CW00153", "입력된 데이터가 없습니다.");
	gfn_message_add("CW00154", "프로젝트, 호기번호가 존재하지 않습니다.");
	gfn_message_add("CW00155", "업체코드가 존재 하지 않습니다.");
	gfn_message_add("CW00156", "프로젝트, 호기번호를 누락되었습니다.");
	gfn_message_add("CW00157", "업체코드가 누락되었습니다.");
	gfn_message_add("CW00158", "유상/무상 구분이 누락되었습니다.");
	gfn_message_add("CW00159", "기성비가 확정된 데이터가 있습니다.중도해지가 불가능합니다.");
	gfn_message_add("CW00160", "무상만료일자를 입력하셔야 합니다.");
	gfn_message_add("CW00161", "무상만료일자를 입력하시고 저장 처리 하실수 없습니다.");
	gfn_message_add("CW00162", "거래선을 선택하십시요.");
	gfn_message_add("CW00163", "무상만료일이 발주개시일보다 작습니다.");
	gfn_message_add("CW00164", "무상만료일이 발주만기일보다 큽니다.");
	gfn_message_add("CW00165", "M프로젝트만 수정 가능합니다.");
	gfn_message_add("CW00166", "일식외주와 일반발주는 \n같은 거래명세서에서 작성이 불가합니다.");
	gfn_message_add("CW00167", "승인 취소와 접수 취소를 동시에 할수 없습니다.");
	gfn_message_add("CW00168", "운송회사로 데이터가 전송되어 변경할 수 없습니다.\n운송회사에 연락바랍니다.");
	gfn_message_add("CW00169", "현장 직투입 자재는 변경할 수 없습니다.");
	gfn_message_add("CW00170", "[${}]번째 행의 [${}] 이 누락되어 있습니다.\n호기기본정보정정에서 수정하신후 저장하십시오.");
	gfn_message_add("CW00171", "[${}] 정보가 존재하므로 취소가 불가합니다.");
	gfn_message_add("CW00172", "해당 월의 기성마감이 확정되었습니다.\n승인작업을 취소합니다.");
	gfn_message_add("CW00173", "무상공사 입력 정보가 없습니다.확인하시기 바랍니다.");
	gfn_message_add("CW00174", "[${}] 검색조건은 최소 [${}]자 이상 입력 하십시오.");
	gfn_message_add("CW00175", "반송된 데이터는 삭제를 하신 후 발주 등록을 다시 하셔야 합니다.");
	gfn_message_add("CW00176", "설치팀 지정이 안된 데이터는 등록요청 대상이 아닙니다.\n확인하시기 바랍니다.");
	gfn_message_add("CW00177", "[${}] 행의 기성은 이미 접수 혹은 승인 처리되었습니다.\n무상만료일자를 다시 입력하십시요.");
	gfn_message_add("CW00178", "취소작업이 불가능 합니다.\n입력하신 데이터를 다시 한번 확인하시기 바랍니다.");
	gfn_message_add("CW00179", "[${}] 행의 기성 계획년월은 이미 마감 완료 되었습니다.\n기성 계획년월을 다시 입력하십시요.");
	gfn_message_add("CW00180", "[${}]은/는 하나 이상 선택하십시오.");
	gfn_message_add("CW00181", "[${}] 행의 발주 잔여 개월수가 없습니다.\n고객지원부에 문의 바랍니다.");
	gfn_message_add("CW00182", "[${}] 처리데이타가 없습니다.");
	gfn_message_add("CW00183", "파일은 최대 [${}]를 초과할 수 없습니다.\n확인하시기 바랍니다.");
	gfn_message_add("CW00184", "[${}:${}]은(는) 오류 데이터가 포함되어있습니다.\n 담당자 문의 바랍니다.");
	gfn_message_add("CW00185", "[${}]를 변경할 수 있는 권한이 없습니다.\n확인하시기 바랍니다.");
	gfn_message_add("CW00186", "해당 프로젝트는 현재 계약이 진행중이므로 삭제가 불가능 합니다.");
	gfn_message_add("CW00187", "매출 계획년월은 이미 종료 되었습니다.\n매출 계획년월을 다시 입력하십시요.");
	gfn_message_add("CW00188", "[${}] 에 (-)값이 입력 되었습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00189", "프로젝트번호는 5자리 이상입니다.");
	gfn_message_add("CW00190", "[${}]이/가 [${}]보다 큰 값이 입력 되었습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00191", "대표,팀장,사원중 하나 이상 선택하십시오.");
	gfn_message_add("CW00192", "팀장제외시 먼저 팀장이름을 변경하세요.");
	gfn_message_add("CW00193", "호기번호: [${}] 은(는) \n사유: [${}] 에 의해 저장할 수 없습니다.");
	gfn_message_add("CW00194", "[${}]번째 행 : 계약구분 항목이 이전 계약과 동일 합니다.계약구분을 변경하십시요.");
	gfn_message_add("CW00195", "[${}]번째 행 : M프로젝트는 계약구분이 타사신규만 가능합니다.");
	gfn_message_add("CW00196", "인수 데이터가 없습니다.다시 한번 확인 바랍니다.");
	gfn_message_add("CW00197", "현재 진행중인 유상계약 데이터가 있습니다.다시 한번 확인 바랍니다.");
	gfn_message_add("CW00198", "현재 회수 등록중인 데이터가 있습니다.다시 한번 확인 바랍니다.");
	gfn_message_add("CW00199", "[${}]일자가 [${}]월 이전 날짜는 확정취소 할 수 없습니다.\n확인하여 주십시오.");
	gfn_message_add("CW00200", "현재 실패 또는 삭제 상태의 데이터가 있습니다.다시 한번 확인 바랍니다.");
	
	gfn_message_add("CW00201", "M 프로젝트는 회수 입력이 불가합니다.");
	gfn_message_add("CW00202", "변경된 정보가 있습니다.저장 후 출력하여 주십시오.");
	gfn_message_add("CW00203", "현재 진행중인 무상발주 데이터가 있습니다.다시 한번 확인 바랍니다.");
	gfn_message_add("CW00204", "하나의 [${}]만 검사신청서 출력이 가능합니다.다시 한번 확인 바랍니다.");
	gfn_message_add("CW00205", "한국승강기안전기술원/관리원만 검사신청서 출력이 가능합니다.다시 한번 확인 바랍니다.");
	gfn_message_add("CW00206", "FM계약 현장이 아니거나 계약이 만료된 현장입니다.\n확인 하시기 바랍니다.");
	gfn_message_add("CW00207", "[${}]중 하나이상 필수선택입니다.확인바랍니다.");
	gfn_message_add("CW00208", "[${}]은/는 필수입력 입니다.확인바랍니다.");
	gfn_message_add("CW00209", "주민등록번호 입력이 잘못되었습니다.확인바랍니다.");
	gfn_message_add("CW00210", "[${}]에서 현재 [${}]으로 근무중입니다.설치부에 확인바랍니다.");
	gfn_message_add("CW00211", "[${}]은/는 부적격자 관리 인원입니다.설치부에 확인바랍니다.");
	gfn_message_add("CW00212", "이미 [${}]신청되어 결재중입니다.");
	gfn_message_add("CW00213", "한글 파일명은 UPLOAD할 수 없습니다.파일명을 영문으로 입력바랍니다.");
	gfn_message_add("CW00214", "진행상태가 중복 선택 되었습니다.확인바랍니다.");
	gfn_message_add("CW00215", "한글은 2자리 이상 입력 하여야 합니다.");
	gfn_message_add("CW00216", "[${}]와 [${}]은/는 동시 수정이 불가합니다.확인하여 주십시오.");
	gfn_message_add("CW00217", "[${}] 보다 작은 값을 입력할 수 없습니다.확인하여 주십시오.");
	gfn_message_add("CW00218", "[${}] 호기가 아닙니다.\n확인하여 주십시오.");
	gfn_message_add("CW00219", "다른 기종의 사양은 복사가 불가능 합니다.");
	gfn_message_add("CW00220", "같은 호기입니다.");
	gfn_message_add("CW00221", "[${}](은)는 필수 입력 사양입니다.\n확인하여 주십시오.");
	gfn_message_add("CW00222", "하나의 자료만 선택하여 주십시오.");
	gfn_message_add("CW00223", "현재 실패등록 되어 있습니다.다시 한번 확인 바랍니다.");
	gfn_message_add("CW00224", "[${}]번째 행의 [${}]은/는 필수입력 항목입니다.\n확인하여 주십시오.");
	gfn_message_add("CW00225", "완성검사 [${}]이/가 이미 완료되었습니다.\n완성검사 [${}]을/를 할 수 없습니다.");
	gfn_message_add("CW00226", "저장을 완료했습니다.\n등록/삭제조회에서 사진 및 정보를 다시한번 확인 후 신청바랍니다.");
	gfn_message_add("CW00227", "핀제거 값이 누락되었습니다.현장답사 정보 입력시 핀제거 [완료] 값은 필수이오니 입력바랍니다.");
	gfn_message_add("CW00228", "완성검사가 신청이 안되었습니다.\n완성검사 신청후 수시검사 신청 가능합니다.");
	gfn_message_add("CW00229", "(기작업+실작업) 수량이 계획량을 초과할 수 없습니다.\n확인 하시기 바랍니다.");
	gfn_message_add("CW00230", "입력된 작업시간(M/H)이(가) 다릅니다.\n확인 하시기 바랍니다.");
	gfn_message_add("CW00231", "[${}]번째 행:동일자재에 대해 평가유형이 다르게 입력할수 없습니다.\n확인 하시기 바랍니다.");
	gfn_message_add("CI00001", "조회를 완료했습니다.");
	gfn_message_add("CI00002", "조회를 완료했습니다.조회건수 [${}건]");
	gfn_message_add("CI00003", "저장을 완료했습니다.");
	gfn_message_add("CI00004", "삭제를 완료했습니다.");
	gfn_message_add("CI00005", "처리를 완료했습니다.");
	gfn_message_add("CI00006", "${} 처리를 완료했습니다.");
	gfn_message_add("CI00007", "화면 초기화를 완료했습니다.");
	gfn_message_add("CI00008", "저장 하시겠습니까?");
	gfn_message_add("CI00009", "저장을 취소 하시겠습니까?");
	gfn_message_add("CI00010", "전송 하시겠습니까?");
	gfn_message_add("CI00011", "전송을 취소 하시겠습니까?");
	gfn_message_add("CI00012", "청구종류를 변경하면 작업내용이 초기화 됩니다.\n변경 하시겠습니까?");
	gfn_message_add("CI00013", "${}을/를 취소 하시겠습니까?");
	gfn_message_add("CI00014", "${}을/를 저장 하시겠습니까?");
	gfn_message_add("CI00015", "${}을/를 삭제 하시겠습니까?");
	gfn_message_add("CI00016", "[${}] 생성을 완료했습니다.");
	gfn_message_add("CI00017", "거래명세서 삭제 상태에서는 작성하실 수 없습니다.\n 작성을 위해 다시 조회 하시겠습니까?");
	gfn_message_add("CI00018", "[${}]을/를 계산하시겠습니까?");
	gfn_message_add("CI00019", "정상적으로 계산되었습니다.");
	
	gfn_message_add("CI00020", "선택하신 자재가 있습니다.\n호차 변경 시 선택된 자재는 초기화 됩니다.\n\n 변경 하시겠습니까?");
	gfn_message_add("CI00021", "납품처에 [${}]정보가 등록되지 않아 \n출하처리를 할 수 없습니다.");
	gfn_message_add("CI00022", "선택하신 자재를 삭제할 경우 배차된 자재가 없게 됩니다.\n 배차를 취소 하시겠습니까?");
	gfn_message_add("CI00023", "원본품목 번호를 선택후 복사품목 번호를 ;로 구분하여 입력합니다.예) 10;30; ");
	gfn_message_add("CI00024", "${} 작업을 진행 하시겠습니까?");
	gfn_message_add("CI00025", "작성된 ${} 작업일보가 없습니다.\\n 최근 ${} 작업일보를 복사 하시겠습니까?");
	gfn_message_add("CE00001", "처리에 실패했습니다.\n확인 후 다시 처리해 주십시오.");
	gfn_message_add("CE00002", "${} 처리에 실패했습니다.\n확인 후 다시 처리해 주십시오.");
	gfn_message_add("ZW00001", "해당 사용자 정보를 찾을 수 없습니다.\n확인하여 주십시오.");
	gfn_message_add("ZW00002", "사용자 정보가 올바르지 않습니다.\n확인하여 주십시오. (문의:031-644-5190)");
	gfn_message_add("ZW00003", "해당 프로그램을 사용할 권한이 없습니다.");
	gfn_message_add("ZW00004", "최대 사용할 수 있는 화면 갯수는 최대 ${}개 입니다.");
	gfn_message_add("ZW00005", "로그인이 제한 되었습니다.\n관리자에게 문의하세요. (031-644-5190)");
	gfn_message_add("ZW00006", "신규 비밀번호와 신규 비밀번호 확인 값이 동일하지 않습니다.\n확인하여 주십시오.");
	gfn_message_add("ZW00007", "신규 비밀번호는 이전 비밀번호와 동일하게 입력할 수 없습니다.\n확인하여 주십시오.");
	gfn_message_add("ZW00008", "하위 게시물이 존재하는 게시물은 삭제할 수 없습니다.\n하위 게시물을 먼저 삭제한 후 삭제해 주십시오.");
	gfn_message_add("ZW00009", "ID 또는 비밀번호가 ${}회 잘못 입력되었습니다.\n5회 이상 잘못 입력시 로그인이 제한됩니다.");
	gfn_message_add("ZW00010", "비밀번호가 일치하지 않습니다.");
	gfn_message_add("ZW00011", "[${}] 사용자가 [${}] client에서 로그인하여 사용하고 있는중입니다.\n이전 사용자의 접속을 종료시키고 로그인하시겠습니까 ?");
	gfn_message_add("ZW00012", "신규 비밀번호는 영문자와 숫자 혼용만  가능하고 최소4, 최대10자입니다.\n확인하여 주십시오.");
	gfn_message_add("ZI00001", "비밀번호가 ${}에 만료됩니다.\n${} 이후에는 로그인이 제한됩니다.\n비밀번호를 변경하시겠습니까?");
	gfn_message_add("ZI00002", "로그아웃 하시겠습니까?");
	gfn_message_add("ZI00003", "메일로 로그인 정보를 전송했습니다.");
	gfn_message_add("ZI00004", "일정 시간 사용하지 않아 세션이 종료되었습니다.\n다시 로그인해 주십시오.");
	
	/*----------------------------------------------------------------------------------------------*/
	// HDEL 용 메세지 작업
	// HDEL용 메세지 신규코드는 반드시 10000번대 부터 채번할것
	//---------------------------------------------------------------------------------------------------
	// [ GRY START ]
	gfn_message_add("CW10001", "[${}:${}] 해당 정보를 찾을 수 없습니다.\n확인하여 주십시오.");
	gfn_message_add("CW10002", "${}년 ${}은 이미 마감되어 ${} 할 수 없습니다.\n확인하여 주십시오.");
	gfn_message_add("CW10003", "변경된 ${}이 존재하여 ${}이 불가능합니다. 변경된 정보를 저장후 다시 실행하여 주십시오.");
	gfn_message_add("CW10004", "${}은(는) ${}되지 않아 ${} 할 수 없습니다.");
	gfn_message_add("CW10005", "[${}] 회수계획일자는 현재월 ~ 익월 범위로 입력 가능합니다.");
	gfn_message_add("CW10006", "[${}] 미수금액을 초과해서 회수계획을 수립할 수 없습니다.");
	gfn_message_add("CW10007", "${}의 ${}는 ${}보다 작거나 같아야 합니다.");
	gfn_message_add("CW10008", "SO번호를 입력하시거나 부서를 선택하시기 바랍니다.");
	gfn_message_add("CW10009", "${}의 ${}는 반드시 입력하셔야 합니다.");
	gfn_message_add("CW10010", "이미 입력되어 있습니다.");
	gfn_message_add("CW10011", "${}의 [${}]은(는) [${}]와(과) 동일해야 합니다."); 
	gfn_message_add("CW10012", "${}의 날짜형식이 잘못되었습니다."); 
	gfn_message_add("CW10013", "입찰완료정보가 존재하지 않는 견적번호입니다."); 
	gfn_message_add("CW10024", "이미 수주생성이 완료된 견적번호 입니다."); 
	gfn_message_add("CW10025", "호기번호관련 문제발생. 정보기술팀으로 필히 연락바랍니다 644-5194.");
	gfn_message_add("CI10001", "변경된 자료가 존재합니다. \n${} 할 경우 기존에 변경된 정보가 모두 소실됩니다.\n${} 하시겠습니까?"); 
	gfn_message_add("CI10002", "${}자료가 존재합니다. \n${}을(를) 선택하실 경우 ${}자료가 모두 소실됩니다.\n${}을(를)선택하시겠습니까?"); 
	gfn_message_add("CE10001", "거래선코드가 유효하지 않습니다.\n다시 한번 확인 바랍니다."); 
	gfn_message_add("CE10002", "오픈되지 않은 편성년도의 사업계획입니다.\n다시 한번 확인 바랍니다."); 
	gfn_message_add("CE10003", "사업계획 저장 시 매출/청구/수금 자동산출 결과가 존재하지 않습니다. 확인 바랍니다."); 
	gfn_message_add("CE10004", "사업계획정보는 정상처리되었으나\n매출/청구/수금 저장 시 오류가 발생하였습니다.\n자동 재조회되면 확인 후 재처리 바랍니다."); 
	
	// [ GRY END ]
	/*----------------------------------------------------------------------------------------------*/
	//trace(gds_message.SaveXML());
	
}

// 임시
function gfn_message_add(msg_id, msg_desc)
{	
	gds_message.AddRow();
	gds_message.SetColumn(gds_message.row, "MSG_ID", msg_id);
	gds_message.SetColumn(gds_message.row, "MSG_DESC", msg_desc);
}

//다국어 지원
function gfn_translation(formObj, lang)
{	
	if ( formObj == null ) {
		formObj = this;
	}

	if (lang == "ko") {
		return;
	}
	
	formObj.visible = false;

	for ( var i = 0; i < formObj.Components.count; i++ ) {

		//변환하지 않는 필드 제거
		if (ToUpperCase(formObj.Components[i].Gettype()) != "TEXT" ||
			ToUpperCase(formObj.Components[i].Gettype()) != "TEXTAREA" ||
			ToUpperCase(formObj.Components[i].Gettype()) != "COMBO" ||
			ToUpperCase(formObj.Components[i].Gettype()) != "DATASET" ||
			ToUpperCase(formObj.Components[i].Gettype()) != "Calendar") {
			
			//그리드 변환
			if (ToUpperCase(formObj.Components[i].Gettype()) == "GRID") {
	
				for (var t = 0; t < formObj.Components[i].GetColCount(); t++) {
	
					var oldname = formObj.Components[i].GetCellProp('Head',t,"text");
					var subname = formObj.Components[i].GetSubCellProp('Head', t, 0, "text");
	
					var rename = "";
					
						if (oldname == "") {
							rename = gfn_rename(subname, lang);
							formObj.Components[i].SetCellProp('Head', t, "font", "굴림,9");
						} else {
							rename = gfn_rename(oldname, lang);
						}
								
					if (length(rename) >= 0) {
							formObj.Components[i].SetCellProp('Head', t,"text", rename);
					}
				}		
			} else if (ToUpperCase(formObj.Components[i].Gettype()) == "TAB") {

				if (formObj.Components[i].TabCount < 1 )
					return true;
	
				var tabPg;	
				for(var x=0;x<formObj.Components[i].TabCount;x++){
							tabPg =formObj.Components[i].GetItem(x);

					var comp = null;
					for ( var c = 0; c < Length(tabPg.Components); c++ ) {
						comp = tabPg.components(c);

						for ( var i = 0; i < comp.Components.count; i++ ) {
	
							//TAB 내부의 그리드 변환
							if (ToUpperCase(comp.Components[i].Gettype()) == "GRID") {
		
								for (var t = 0; t < comp.Components[i].GetColCount(); t++) {
					
									var oldname = comp.Components[i].GetCellProp('Head',t,"text");
									var subname = comp.Components[i].GetSubCellProp('Head', t, 0, "text");

									var rename = "";
									
										if (oldname == "") {
											rename = gfn_rename(subname, lang);
											comp.Components[i].SetCellProp('Head', t, "font", "굴림,9");
										} else {
											rename = gfn_rename(oldname, lang);
										}
												
									if (length(rename) >= 0) {
											comp.Components[i].SetCellProp('Head', t,"text", rename);
									}
								}
										
							} else if (ToUpperCase(comp.Components[i].Gettype()) == "LABEL" ||
									   ToUpperCase(comp.Components[i].Gettype()) == "BUTTON" ||
									   ToUpperCase(comp.Components[i].Gettype()) == "STATIC") {
					
								var oldname = comp.Components[i].Text;

								var rename = gfn_rename(oldname, lang);
											
								if (length(rename) >= 0) {
					
									comp.Components[i].Text = rename;
								}	
							}
						}
					}
				}
			} else if (ToUpperCase(formObj.Components[i].Gettype()) == "LABEL" ||
					   ToUpperCase(formObj.Components[i].Gettype()) == "BUTTON" ||
					   ToUpperCase(formObj.Components[i].Gettype()) == "STATIC") {
	
				var oldname = formObj.Components[i].Text;
				
				var rename = gfn_rename(oldname, lang);
							
				if (length(rename) >= 0) {
	
					formObj.Components[i].Text = rename;
				}	
			}
		}
	}

	formObj.visible = true;
	
}

function gfn_rename(rename, lang) {

	//if ( length( rename) == 0 ) {
	//	return "";
	//}

									
	trace("grid old name = " + rename);
									
									
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!추후 영문으로 수정 en !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
	if (lang == "en") {

		var nRow = gds_translation.searchRow("LANG_KO == " + quote(ltrim(rtrim(rename))));

		if ( nRow >= 0 ) {
			return gds_translation.getColumn(nRow, "LANG_EN");
		} else {
			return rename;
		}
	}
}

// 2012.11.28 SPEC DISPLAY
// 자재번호, 기종, 인승/폭, 속도/각도, 층수/높이, 용량, 용도, 열림, 정지층수/RISE/길이 
function gfn_specInfo(strVkbur, strMatnr, strRtype, strType1, strType2, strType3, strZacapa, strZuse, strZopn, strZlen)
{
    var strSpec = "";
	var strType1Capa = "";
	switch(strMatnr) 
	{
		case "L-1000":
		case "L-2000":
			// 국내의 경우
			if (strVkbur != "B500" && strVkbur != "B600")
			{
				if (strZuse == "DW" || strZuse == "TL" || strZuse == "AM" || strZuse == "HA" || 
					strZuse == "HF" || strZuse == "BD" || strZuse == "BH" || strZuse == "FT" )	{
					strType1Capa = strType1;
					if (length(trim(strZacapa)) > 0)	{
						strType1Capa = strZacapa;
					}
				}else	{
					strType1Capa = strZacapa;
					if (length(trim(strType1)) > 0)	{
						strType1Capa = strType1;
					}
				}
				
			}else	{
				strType1Capa = strType1;
				if (length(trim(strZacapa)) > 0)	{
					strType1Capa = strZacapa;
				}
			}

			strSpec  = strRtype + " " + strZuse + strType1Capa + "-" + strZopn + strType2 + "-" + strType3;

			if (length(trim(strZlen)) > 0)
			{
				if (strType3 != strZlen)
					strSpec += "/" + strZlen;
			}

			break;
		case "S-1000":
			strSpec  = strRtype + " " + strType1 + "-" + strType3 + "-" + strType2;

			break;
		case "W-1000":
			strSpec  = strRtype + " " + strType1;
			
			if (length(trim(strType2)) > 0)
			{
				strSpec += "-" + strType2 + "-" + strType3;
			}
			else
			{
				if (length(trim(strZlen)) > 0)
				{
					strSpec += "-" + strZlen;
				}
			}

			break;
		case "J-1000":
		    //strSpec  = strRtype + " " + strType1 + "-" + strType2 + "-" + strType3;
		    //주차 견적양식 반양 위해 수정 2013.06.01 
		    if ( strType1 <> "" ) {  //승입방향은 필수 값이 아님 
                    //	모델명  +  승입방향 + 수용대수 + (리프트수)		
				if( strZlen <> "" ) {
					strSpec  = strZuse + strType1 + "-" + strType3 +  "-L" + strZlen ;
				} else {
				    strSpec  = strZuse + strType1 + "-" + strType3;
				}
			} else {
					//	모델명  +  단수 + 수용대수 + (리프트수)	
				if( strZlen <> "" ) {
					strSpec  = strZuse + strType2 + "-" + strType3 +  "-L" + strZlen ;   
				} else { 
					strSpec  = strZuse + strType2 + "-" + strType3 ;   
			    }
			}
			
			break;
		case "G-1000":
		case "L-1100":
		case "L-1200":
		case "T-1000":
		case "Y-1000":
		case "F-1000":
			strSpec  = strRtype;

			break;
	}

	return strSpec;
}

/*********************************************************************
* Function : 엑셀파일을 DataGrid로 업로드
* Argument : objGrid	- 엑셀업로드 대상이 되는 그리드
*            strDsName	- 그리드에 매핑된 데이타셋 이름
* Return   : 
**********************************************************************/
function gfn_ExcelToGrid(objGrid, strDsName){
	//Dialog 생성
	if(!IsValid(object("_fdgCommon"))){
		Create( "FileDialog" , "_fdgCommon");
	}
	_fdgCommon.Type = "Open";
	_fdgCommon.Filter = "Excel 통합문서 (*.xlsx)|*.xlsx|Excel 97-2003 통합문서 (*.xls)|*.xls|";

	//실패시 컴포넌트 제거
	if(!_fdgCommon.Open()){
		Destroy("_fdgCommon");
	}
	
	objGrid.redraw = false;
	var IRtn = ext_ExcelImportByIndex(_fdgCommon.FilePath +"\\"+_fdgCommon.FileName,0,strDsName,1, 1, 0, 2,,);
	objGrid.redraw = true;
	Destroy("_fdgCommon");
}



// 현재 컬럼의 MAX 구하기 2013.01.14
function gfn_getColMax(ds, colNm) { 
	var max_seq 	= 0;
	var seq 		= 0;

	for (var i = 0; i < ds.GetCount(); i++) {
		seq = ds.GetColumn(i, colNm);
		if (ToNumber(seq) > ToNumber(max_seq))
			max_seq = seq;
	}
	return max_seq;
}

// 특정 프로그램의 사용 여부 확인 2013.01.21
function gfn_setLock(vPgmCode, vKeyId, vUser)
{
	http.Sync = true;

	var output = " ds_error=ds_error";
	var vParam = "PGMCODE=" + quote(vPgmCode) + " KEYID=" + quote(vKeyId) + " USERID=" + quote(vUser);

	callServiceN("setLock"									// srvId
				, "lock/setLock"							// url
				, ""										// biz에서 받는 명, mip에서 보내는 명
				, output									// mip에서 받는 명, biz에서 보내는 명
				, vParam									// strParam
				, "gfn_afterLock"							// CallBackFunc (임이의 callback 처리)
				);

	http.Sync = false;

	if(ds_error.getRowCount() > 0)	{
		gfn_showAlert(ds_error.GetColumn(0, "ERRMSG"));
		return false;
	}
	
	return true;
}

function gfn_setUnLock(vPgmCode, vKeyId, vUser)
{
	var output = " ds_error=ds_error";
	var vParam = "PGMCODE=" + quote(vPgmCode) + " KEYID=" + quote(vKeyId) + " USERID=" + quote(vUser);

	callServiceN("releaseLock"								// srvId
				, "lock/releasLock"							// url
				, ""										// biz에서 받는 명, mip에서 보내는 명
				, output									// mip에서 받는 명, biz에서 보내는 명
				, vParam									// strParam
				, "gfn_afterLock"							// CallBackFunc (임이의 callback 처리, 오류시 무시 )
				);
}

function gfn_afterLock(strSvcID, nErrorCode, strErrorMag)
{
	return;
}

/*********************************************************************
* Function : 숫저의 경우 처음 0를 제거 처리
* Argument : 
* Return   : 
**********************************************************************/
function gfn_firstZeroOmit(strValue)
{
	var returnStr = strValue;
	if (IsDigit( strValue ))	{
		var charCnt = 0;
		for (var inx = 0; inx < length(strValue); inx++) 			{
			if ( indexOf("0", charAt(strValue, inx)) == -1)	{
				break;
			}else	{
				charCnt++;
			}
		}
		
		if (charCnt > 0)	{
			returnStr = substr(strValue, charCnt);
		}
	}
	return returnStr;
}


/*********************************************************************
* Function : 월을 영문으로 표기
* Argument : 
* Return   : 
**********************************************************************/
function gfn_MonthToEng(strMonth)
{
	var rtnMonth = "";
	switch(strMonth)	{
		case "01" : rtnMonth = "Jan"; break;
		case "02" : rtnMonth = "Feb"; break;
		case "03" : rtnMonth = "Mar"; break;
		case "04" : rtnMonth = "Apr"; break;
		case "05" : rtnMonth = "May"; break;
		case "06" : rtnMonth = "Jun"; break;
		case "07" : rtnMonth = "Jul"; break;
		case "08" : rtnMonth = "Aug"; break;
		case "09" : rtnMonth = "Sep"; break;
		case "10" : rtnMonth = "Oct"; break;
		case "11" : rtnMonth = "Nov"; break;
		case "12" : rtnMonth = "Dec"; break;
	}
	return rtnMonth;
}

/*********************************************************************
* Function : 디자인의뢰서 Disply 사양 추출
* Argument : 
* Return   : 
**********************************************************************/
function gfn_drawTemplateSet(ds_template, ds_templateSd120)
{
	var searchRow = -1;
	var varPrh = "";
	var varPrd = "";
	var scolor = "";

	Destroy("ds_template_temp");
	Create("DataSet" , "ds_template_temp");
	ds_template_temp.Copy("ds_template");
	ds_template_temp.ClearData();

	for(var i = 0; i < ds_templateSd120.GetRowCount(); i++)	{
		varPrh    = trim(ds_templateSd120.GetColumn(i, "PRH"));
		scolor    = trim(ds_templateSd120.GetColumn(i, "SCOLOR"));
		searchRow = ds_template.SearchRow("PRH == " + quote(varPrh));
        
		if (searchRow >= 0)	{
			varPrd = trim(ds_template.GetColumn(searchRow, "PRD"));
			if (varPrd != null && varPrd != "")		{
				ds_template_temp.addRow();				
				ds_template_temp.copyrow(ds_template_temp.row, "ds_template", searchRow);
				ds_template_temp.setColumn(ds_template_temp.row, "SCOLOR", scolor);
			}
		}
	}

	return ds_template_temp;
}