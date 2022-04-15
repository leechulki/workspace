/**
 * 파 일 명 : 공통 유틸 클래스
 * 작 성 자 : moono
 * 작 성 일 : 2006-02-17
 * 설    명 : 
 * 변경내역 :
 *  
 */
package com.helco.xf.cs12.evladm.dbutil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.jsp.JspWriter;

  /**
   * - Util Index -
   * No.     메소드명                                    메소드설명
   *  --------------------------------------  ----------------------------------------------
   * 1       int2s                               		날짜변환에 내부적 사용
   * 2       addWeeks                      				오늘날짜 + n 주
   * 3       addMonthDate                     			오늘날짜의 월 + n 월 = YYYY-MM-DD
   * 4       getCurrentTimeHHMM                       현재시간 HH:MM
   * 5       getCurrentTimeHHMMSS                     현재시간 HH:MM:SS
   * 6       getToday                                 현재날짜의 YYYY-MM-DD
   * 7       getToday2                                현재날짜의 YYYYMMDD
   * 8       getMonthFirstDay                         현재월의 첫째날 YYYY-MM-DD
   * 9       getMonthLastDay                          현재월의 마지막날 YYYY-MM-DD (윤달가능)
   * 10      getLastDay                               해당월의 최대일자
   * 11      getLastDate                              해당월의 마지막날짜 YYYY-MM-DD (윤달가능)
   * 12      getYear                                  당해 년도 YYYY
   * 13      getMonth                                 당해의 월 MM
   * 14      getDate                                  당해의 일 DD
   * 15      getYearMD                                YYYYMMDD or YYYY-MM-DD 를 YYYY년 MM월 DD일 형태로 변환
   * 16      getFormattedDate                         Date를 timezone과 format에 따른 날짜 String으로 변환해서 리턴
   * 17      getStrToDate                             String을 Date형식으로 String = "20030204101010" or "2003-02-04 10:10:10"
   * 18      getStrToDateDefault                      String을 Date형식으로 default포멧 사용
   * 19      strToYMHS                                날짜의yyyymmddhhmmss 형식을 yyyy-mm-dd hh:mm:ss형식으로 변환
   * 20      strToCal                                 날짜 YYYYMMDD를 특정 구분자로 구분하여 리턴
   * 21      strTimeFormat                            HHmmss를 HH:mm:ss로 변환
   * 22      strTimeUnFormat                          HH:mm:ss를 HHmmss로 변환
   * 23      SecToDateFormat                          int 형식(초단위)을 X개월X일X시간X분X초 등으로 바꾸기
   * 24      getSysDateTime                           현재의 YYYY-MM-DD HH:mm:ss
   * 25      getSysDateTimeMsec                       현재의 YYYY-MM-DD HH:mm:ss.sss
   * 26      getPrevDate                              현재일로부터 몇일전의 날짜..YYYY-MM-DD
   * 27      getNextDate                              현재일로부터 몇일 후의 날짜 YYYY-MM-DD
   * 28      getDayName                               오늘의 요일 인덱스 리턴 1,2,3,,,7
   * 29      getDayNameKor                            오늘의 요일명 리턴 월요일, 화요일, 수요일...
   * 30      diffDate                                 들어온 두 날짜의 차이
   * 31      addDatePrevNext                          입력한 날짜 기준으로 몇일 전,후
   * 32      getDateBetweenInDAY_OF_WEEK              특정 날짜 구간의 요일명 구하기..
   * 33      convertHtmlBr                            String 형식을 받아서 Html 형식으로 변환
   * 34      replaceSpecial                           스트링내의 특수문자를 \를 붙인 HTML형식으로 변환
   * 35      replaceEnterBr                           string의 <enter>값에 html tag <br>을 붙인다
   * 36      getSpecialChar                           HTML과 관련하여 일부 특수문자를 반환
   * 37      getPageList                              페이지 네비게이션 제어하기
   * 38      getTotalPage                             총페이지 구하기
   * 39      myIndexList                              페이징 구현
   * 40      myIndexList2                             페이징 구현2
   * 41      drawScroller                             페이징 구현3
   * 42      pagingIndex                              paging 시  넘겨줄 index값을 계산
   * 43      replace                                  특정문자를 다른 문자로 변환
   * 44      nullToBlank                              주어진 문자열 str이  null 또는 "null" 이면 공백을 출력한다.
   * 45      strAddLoop                               len 수 만큼 스트링 채워넣기
   * 46      fillString                               스트링에 특정 길이 만큼 앞뒤로 붙이기
   * 47      nullToString                             null 또는 "" 이면 특정한 문자열로 대치
   * 48      cutString                                스트링을 일정한 길이이상일때 ".."를 붙여서 잘라줌
   * 49      countChar                                문자열에서 특정문자의 포함 개수를 구한다.
   * 50      removeChar                               String에서 특정문자를 없앤다
   * 51      removeSpace                              String에서 전각문자의 SPACE를 없앤다
   * 52      getStreamToString                        InputStream 형 데이터를 한글 String 형으로 변환
   * 53      allZeroToStr                             '0'으로 모두 채워진 문자열을 특정문자로 바꾸어줌
   * 54      getTokenStr                              구분자에 따라 문자열 분리 StringToken이용
   * 55      replaceIdxFrom                           지정인덱스 이후 문자열 replace 메소드
   * 56      addFillLeft                              문자열의 왼쪽부분에 특정문자를 추가해서 원하는 길이로 만들기
   * 57      addFillRight                             문자열의 오른쪽부분에 특정문자를 추가해서 원하는 길이로 만들기
   * 58      getMSG                                   메시지 파일 CommonMSG.properties파일에서 해당 키값으로 메시지 읽기
   * 59      MakeQuery                                쿼리 파일에서 쿼리문 읽기
   * 60      MakeQueryNP                              쿼리 파일에서 쿼리문 읽기 No Parameter
   * 61      Round                                    정수를 받아서 반올림(5이하 버림, 5이상 올림)
   * 62      Round                                    double를 받아서 반올림(5이하 버림, 5이상 올림)
   * 63      strResnoFormat                           주민번호 1234561234567에 '-'넣기
   * 64      strBizFormat                             사업자번호에 '-'넣기
   * 65      PostnoFormat                             우편번호에 '-'넣기
   * 66      setFrm                                   수량, 단가, 금액 형식 지정(double 형)
   * 67      setFrmZero                               수량, 단가, 금액 형식 지정 0으로 대체 (double 형)
   * 68      dFormat                                  int형 숫자를 3자리 마다 콤마찍기
   * 69      dFormat                                  long형 숫자를 3자리 마다 콤마찍기
   * 70      dFormat                                  double형 숫자를 3자리 마다 콤마찍기
   * 71      dFormat                                  String형 숫자를 3자리 마다 콤마찍기
   * 72      fFormat                                  String 값을 #,###,###,##0.#0 형식으로 바꿈
   * 73      fFormat                                  받은 값을 #,###,###,##0.#0 형식으로 소수점지정 바꿈
   * 74      StringToFloat                            #.# 로 오는 string 값을 #.# float 값으로 변환
   * 75      setZero                                  우측에 i_length만큼 0을 추가
   * 76      DoubleTypeRound                          Double형의 문자를 소수 i자리에서 반올림을 한다.
   * 77      getSubstring                             에러대신 Zero String을 리턴하는 substring 메소드
   * 78      makeIntToKorCurr                         숫자로된 금액을 문자로 표시함 - 삼천원
   * 79      makeIntToNumKorCurr                      숫자로된 금액을 문자로 표시함 - 3천원
   * 80      BigDecimalAdd                            두 문자열을 BigDecimal Type으로 덧셈연산하고 결과값을 문자열로 반환
   * 81      BigDecimalSubtract                       두 문자열을 BigDecimal Type으로 뺄셈연산하고 결과값을 문자열로 반환
   * 82      BigDecimalMultiply                       두 문자열을 BigDecimal Type으로 곱셈연산하고 결과값을 문자열로 반환
   * 83      BigDecimalDivide                         두 문자열을 BigDecimal Type으로  나누기연산하고 결과값을 문자열로 반환
   * 84      BigDecimalDivide                         두 문자열을 BigDecimal Type으로  나누기연산하고 결과값을 문자열로 반환, 반올림패턴이용
   * 85      BigDecimalCompare                        두 개의 문자열을 BigDecimal Type으로 비교하고 결과값을 int형으로 반환
   * 86      TokenSum                                 지정 token으로 분리된 String의 합을 구한다.
   * 87      commaFormat                              숫자로 구성된 문자열에 자리수 마다 , 넣기 및 type지정
   * 88      TelnoFormat                              지역번호 - 국번 - 전화번호 형식으로 바꾸기
   * 89      IsIntegerCheck                           문자열이 숫자로만 구성되었는지 체크
   * 90      DecimalFormat                            소수점이 포함된 숫자 String을 Integer로 변환
   * 91      enToKo                                   한글변환 8859_1 - KSC5601
   * 92      koToEn                                   한글변환 KSC5601 - 8859_1
   * 93      uni2Ksc                                  웹에서 받은 데이타를 encoding 8859_1 => KSC5601
   * 94      ksc2Uni                                  데이타를 encoding KSC5601 => 8859_1
   * 95      NullToBlank                              null - ""
   * 96      NullBlankToZero                          null, "" - "0"
   * 97      getQueryCharacter                        db query 시 데이타 특수문자 처리..
   * 98      getQueryToHtml                           db query 데이타를 HTML형식으로..
   * 99      setCurrentPage(int current_page)         현재 페이지 셋팅 
   * 100     getCurrentPage()								   현재페이지 얻기 
   * 101    getMonthBetween(String fromdt,String todt) 두 날짜 사이의 개월수 얻기 
   * 102    makeDateCombo()                           일 콤보 만들기 
   * 103    addMonthPrevNext                          입력한 날짜 기준으로 몇일 전,후
   * 104    getMonthPerDay                            입력한 월을 기준으로 그달의 마지막 일자를 구함
   * 105    getCurMonthPerDay                         현재월을 기준으로 현재월의 마지막 일자를 구함
   * 106    getDatePrevNext                           입력한 날짜를 기준으로 몇일 전후 + 날짜형식 지정
   * 107    cropByte(String str, int end)             입력한 문자열을 byte단위로 잘라서 리턴 
   * 108    getStrBytes(String str)                   입력한 문자열의 byte수 리턴
   * 109    removeEnter(String strValue)              strValue 문자열에서 개행문자를 제거한 문자열 반환
   * 110    filecopy(String source, String target)    source에서 target으로의 파일 복사   
   */


public class CommonUtil {

	protected int mCurrentPage = 0;  // 현재 페이지
	
	//기본 날짜 포맷
	public final static String DEFAULT_DATE_FORMAT  = "yyyyMMddHHmmss";

	//일반 날짜 포맷 (KICC 날짜 포맷)
	public final static String GENERAL_DATE_FORMAT  = "yyyyMMdd";
	//기본 TimeZone
	public final static String DEFAULT_TIMEZONE     = "JST";

	private static String QUERYROOT = null;


	public CommonUtil(){
	}



	/***********************************************************************************
	  날짜 & 시간 관련 메소드 모음
	************************************************************************************/
    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : getCurrentTime에서 호출
    *            Calendar에서 매개변수 사이즈만큼 자른다
    * 기    타 :
    */ 
	public static String int2s(int num, int size){
		String formatStr = "";
		for ( int iii = 0; iii < size; iii ++ )
			formatStr += "0";

		java.text.DecimalFormat  df  =  new  java.text.DecimalFormat(formatStr);

		return df.format((new Integer(num)).longValue());
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : date를 기준으로 n주를 더한 날짜를 리턴한다.
    * 기    타 : IllegalArgumentException date가 null이면 발생
    */ 
	public static String addWeeks(int n) {
		String yyyymmdd;
		Calendar rightNow = Calendar.getInstance();
		Date date = rightNow.getTime();
		rightNow.add(Calendar.DATE, 7*n);
		date=rightNow.getTime();
		yyyymmdd = int2s(rightNow.get(Calendar.YEAR), 4) + "-" + int2s(rightNow.get(Calendar.MONTH) + 1, 2) + "-" + int2s(rightNow.get(Calendar.DATE), 2);
		return yyyymmdd;
	}

   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : date를 기준으로 월을 가감한 날짜를 리턴한다.
    * 기    타 : return : 오늘날짜의 월 + n 월 = YYYY-MM-DD
    *            IllegalArgumentException date가 null이면 발생
    */ 
	public static String addMonthDate(int mon) {
		try{
			String yyyymmdd;
			Calendar rightNow = Calendar.getInstance();
			Date date = rightNow.getTime();
			rightNow.add(Calendar.MONTH, mon);
			date=rightNow.getTime();
			yyyymmdd = int2s(rightNow.get(Calendar.YEAR), 4) + "-" + int2s(rightNow.get(Calendar.MONTH) + 1, 2) + "-" + int2s(rightNow.get(Calendar.DATE), 2);
			return yyyymmdd;
		}catch(Exception e){
			return "";
		}
	}
   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재시간 HH:MM 을 구한다.
    * 기    타 : return : 현재시간 HH:MM
    */ 
	public static String getCurrentTimeHHMM() {
		String currentTimeHHMM = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		currentTimeHHMM = int2s(dt.get(Calendar.HOUR_OF_DAY), 2) + ":" + int2s(dt.get(Calendar.MINUTE), 2);

		return currentTimeHHMM;
	}

   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재시간 HH:MM:SS 을 구한다.
    * 기    타 : return : 현재시간 HH:MM:SS
    */ 
	public static String getCurrentTimeHHMMSS() {
		String currentTimeHHMMSS = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		currentTimeHHMMSS = int2s(dt.get(Calendar.HOUR_OF_DAY), 2) + ":" + int2s(dt.get(Calendar.MINUTE), 2) + ":" + int2s(dt.get(Calendar.SECOND), 2);

		return currentTimeHHMMSS;
	}
   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 당일을 구한다.
    * 기    타 : return : 현재날짜의 YYYY-MM-DD
    */ 
	public static String getToday(){
		String yyyymmdd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		yyyymmdd = int2s(dt.get(Calendar.YEAR), 4) + "-" + int2s(dt.get(Calendar.MONTH) + 1, 2) + "-" + int2s(dt.get(Calendar.DATE), 2);

		return yyyymmdd;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 당일을 구한다. (하이픈 제거한 8자리)
    * 기    타 : return : 현재날짜의 YYYYMMDD
    */ 
	public static String getToday2(){
		String yyyymmdd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		yyyymmdd = int2s(dt.get(Calendar.YEAR), 4) + int2s(dt.get(Calendar.MONTH) + 1, 2) + int2s(dt.get(Calendar.DATE), 2);

		return yyyymmdd;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 당월의 첫번째일을 구한다.
    * 기    타 : return : 현재월의 첫째날 YYYY-MM-DD
    */ 
	public static String getMonthFirstDay(){
		String yyyymmdd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		int MonthFirstDay = dt.get(Calendar.DATE)-(dt.get(Calendar.DATE)-1);
		yyyymmdd = int2s(dt.get(Calendar.YEAR), 4) + "-" + int2s(dt.get(Calendar.MONTH) + 1, 2) + "-" + int2s(MonthFirstDay, 2);

		return yyyymmdd;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 당월의 마지막일을 구한다.
    * 기    타 : return : 현재월의 마지막날 YYYY-MM-DD
    */ 
	public static String getMonthLastDay(){
		String yyyymmdd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		dt.set(dt.get(Calendar.YEAR), dt.get(Calendar.MONTH) + 1, 1);
		dt.setTime(new java.util.Date(dt.getTime().getTime() - 86400000));
		yyyymmdd = int2s(dt.get(Calendar.YEAR), 4) + "-" + int2s(dt.get(Calendar.MONTH) + 1, 2) + "-" + dt.get(Calendar.DATE);

		return yyyymmdd;
	}

   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 그 달의 마지막 일자만 구하기 (일 [31] 리턴)
    * 기    타 : return : String
    */ 
	public static String getLastDay(String s_date){
		int Year  = Integer.parseInt(s_date.substring(0,4));
		int Month = Integer.parseInt(s_date.substring(4,6));

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		cal.set(Year, Month, 1);
		cal.setTime(new java.util.Date(cal.getTime().getTime() - 86400000));

		return String.valueOf(cal.get(Calendar.DATE));
	}
   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 해당월의 마지막 날짜 구하기 (년월 6자리[200201])
    * 기    타 : param s_date (YYYYMM 기준월) , return : String (YYYY-MM-DD)
    */ 
	public static String getLastDate(String s_date){
		int Year  = Integer.parseInt(s_date.substring(0,4));
		int Month = Integer.parseInt(s_date.substring(4,6));
		String s_month = "";

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		cal.set(Year, Month, 1);
		cal.setTime(new java.util.Date(cal.getTime().getTime() - 86400000));

		if (Month < 10)
			s_month = "0" + String.valueOf(Month);

		return Year + "-" + s_month + "-" + cal.get(Calendar.DATE);
	}
    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 당년을 구한다.
    * 기    타 : return : 날짜 String을 리턴한다.
    */ 
	public static String getYear(){
		String year = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		year = int2s(dt.get(Calendar.YEAR), 4);
		return year;
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 당월을 구한다.
    * 기    타 : return : 날짜 String을 리턴한다.
    */ 
	public static String getMonth(){
		String month = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		month = int2s(dt.get(Calendar.MONTH) + 1, 2);
		return month;
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 오늘의 일을 구한다.
    * 기    타 : return : String DD
    */ 
	public static String getDate(){
		String dd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		dd = int2s(dt.get(Calendar.DATE), 2);
		return dd;
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : YYYYMMDD or YYYY-MM-DD 를 YYYY년 MM월 DD일 형태로 변환
    * 기    타 : return : 날짜 String을 리턴한다. 변환도중 error발생시 null을 리턴한다
    */ 
	public static String getYearMD(String date){
		try {
			date = replace(date.trim(), "-", "");
			if(date.equals("") || date.equals(" ") || date.equals("null")  || date.equals(null))
				return " ";

			java.util.TimeZone homeTz = java.util.TimeZone.getTimeZone("JST");
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy년 MM월 dd일");
			formatter.setTimeZone(homeTz);
			return formatter.format(getStrToDate(date,"yyyyMMdd"));
		} catch (Exception e) {
			return null;
		}
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : Date를 timezone과 format에 따른 날짜 String으로 변환해서 리턴한다.
    * 기    타 : param   : date 날짜 , format 날짜 포맷 (예) yyyyMMddHHmmss , timezone Timezone (예) JST
    *            return  : 날짜 String을 리턴한다. 변환도중 error발생시 null을 리턴한다
    */ 
	public static String getFormattedDate(Date date, String format, String timezone) {
		try {
			java.util.TimeZone homeTz = java.util.TimeZone.getTimeZone(timezone);
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
			formatter.setTimeZone(homeTz);
			return formatter.format(date);
		} catch (Exception e) {
			return null;
		}
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 날짜 String을 Date로 변환해서 리턴한다.
    * 기    타 : param   : date 날짜 20030204101010 or 2003-02-04 10:10:10 , format 날짜 포맷
    *            return  : Tue Feb 04 10:10:10 KST 2003 날짜 String을 Date로 변환해서 리턴한다. 변환도중 error발생시 null을 리턴한다.
    */ 
	public static Date getStrToDate(String date, String format) {
		if ((date == null) || (format == null))
			return null;

		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
			return formatter.parse(date, new java.text.ParsePosition(0));
		} catch (Exception e) {
			return null;
		}
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 날짜 String을 Default format Date로 변환해서 리턴한다. (DEFAULT_DATE_FORMAT을 사용)
    * 기    타 : param   : String 20030204101010 or 2003-02-04 10:10:10
    *            return  : 날짜 String을 Date로 변환해서 리턴한다. 변환도중 error발생시 null을 리턴한다.
    */ 
	public static Date getStrToDateDefault(String date){
		return getStrToDate(date, DEFAULT_DATE_FORMAT);
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 날짜의yyyymmddhhmmss 형식을 yyyy-mm-dd hh:mm:ss형식으로 변환해서 리턴한다.
    * 기    타 : param   : String
    *            return  : 날짜 String을 구분자를 넣어 리턴한다.
    */    
	public static String strToYMHS(String date){  // 날짜, 구분자
		if(date == null || date.trim().length() != 14){
			return date;
		}else{
			date = date.trim();
			return date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
		}

	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 날짜의yyyymmdd 형식을 yyyy-mm-dd형식으로 변환해서 리턴한다.
    * 기    타 : param   : String
    *            return  : 날짜 String을 구분자를 넣어 리턴한다.
    */    
	public static String strToCal(String day, String div){  // 날짜, 구분자
		if (day == null)
			day = "";

		if (day.trim().length() == 6)
			day = day.substring(0,4)+ div +day.substring(4,6);

		if (day.trim().length() == 8)
			day = day.substring(0,4)+ div +day.substring(4,6)+ div +day.substring(6,8);

		return day;
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : HHMMSS -> HH:MM:SS
    * 기    타 : param   : str_time 바꿀 문자열
    *            return  : String
    */    
	public static String strTimeFormat(String str_time){
		if (str_time == null || str_time.length() < 5){
			return str_time;
		}else{
			if (str_time.length() == 5)
				str_time    = "0" + str_time;

			str_time = str_time.substring(0,2) + ":" + str_time.substring(2,4) + ":" + str_time.substring(4);

			return str_time;
		}
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : HH:MM:SS -> HHMMSS
    * 기    타 : param   : str_time 바꿀 문자열
    *            return  : String
    */    
	public static String strTimeUnFormat(String str_time){
		if (str_time == null || str_time.length() != 8){
			return str_time;
		}else{
			StringTokenizer st = new StringTokenizer(str_time,":");
         String rtnStrTime = "";
			while(st.hasMoreTokens()){
            rtnStrTime = rtnStrTime + st.nextToken();
			}
			return rtnStrTime;
		}
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : int 형식(초단위)을 X개월X일X시간X분X초 등으로 바꾸기
    * 기    타 : param   : int(second)
    *            return  : X개월X일X시간X분X초
    */    
	public static String SecToDateFormat(int i_second){
		String datetime = "";
		int second     = i_second%60;
		int ttl_minute = i_second/60;
		int minute     = ttl_minute%60;
		int ttl_hour   = ttl_minute/60;
		int hour       = ttl_hour%24;
		int ttl_day    = ttl_hour/24;
		int day        = ttl_day%30;
		int month      = ttl_day/30;
	//  System.out.println("time calculate!!!!!!"+month+"-"+day+"-"+hour+"-"+minute+"-"+second);
		if(month !=0)
			datetime += month + "개월";

		if(day !=0)
			  datetime += day + "일";

		if(hour !=0){
			datetime += hour + "시간";
		}else{
			datetime += "00시간";
		}

		if(minute !=0){
			datetime += minute + "분";
		}else{
			datetime += "00분";
		}

		if(second !=0){
			datetime += second + "초";
		}else{
			datetime += "00초";
		}
		//System.out.println("datetime ===>"+datetime);

		return datetime;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재일자 + 현재시간를 구함
    * 기    타 : param   : 
    *            return  : 현재의 YYYY-MM-DD HH:mm:ss
    */    
	public static String getSysDateTime(){
		int chrYear,chrMonth,chrDay ;
		int chrHour,chrMinute,chrSecond ;

		DecimalFormat dformat = new DecimalFormat("00");
		chrYear  = Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.YEAR);
		chrMonth = Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.MONTH) + 1;
		chrDay   = Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.DATE);

		chrHour   = Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.HOUR_OF_DAY);
		chrMinute = Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.MINUTE) ;
		chrSecond = Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.SECOND);

		return String.valueOf(chrYear)+'-'+dformat.format(chrMonth)+'-'+ dformat.format(chrDay)+' '+dformat.format(chrHour)+':'+dformat.format(chrMinute)+':'+dformat.format(chrSecond);
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재일자 + 현재시간를 구함
    * 기    타 : param   : 
    *            return  : 현재일시 YYYY-MM-DD HH:mm:ss:sss
    */    
	public static String getSysDateTimeMsec(){
		int mSecond ;

		DecimalFormat dformat = new DecimalFormat("00");
		mSecond   = Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.MILLISECOND);

		return getSysDateTime()+"."+dformat.format(mSecond);
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재일로부터 이전 몇일 이후를 구함
    * 기    타 : param   : int (이전일)
    *            return  : String 이전일자 YYYY-MM-DD
    */    
	public static String getPrevDate(long day){
		int chrYear,chrMonth,chrDay ;
		DecimalFormat dformat = new DecimalFormat("00");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		calendar.setTime(new Date(System.currentTimeMillis() - (day * 86400000)));
		chrYear  = calendar.get(Calendar.YEAR);
		chrMonth = calendar.get(Calendar.MONTH) + 1;
		chrDay   = calendar.get(Calendar.DATE);

		return String.valueOf(chrYear) + "-" + dformat.format(chrMonth) + "-" + dformat.format(chrDay);
	 }

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재일로부터 다음 몇일 이후를 구함
    * 기    타 : param   : int (다음일)
    *            return  : String 다음일자 YYYY-MM-DD
    */    
	public static String getNextDate(long day){
		int chrYear,chrMonth,chrDay ;
		DecimalFormat dformat = new DecimalFormat("00");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("JST"));

		calendar.setTime(new Date(System.currentTimeMillis() + (day * 86400000)));
		chrYear  = calendar.get(Calendar.YEAR);
		chrMonth = calendar.get(Calendar.MONTH) + 1;
		chrDay   = calendar.get(Calendar.DATE);

		return String.valueOf(chrYear) + "-" + dformat.format(chrMonth) + "-" + dformat.format(chrDay);
	 }

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재 요일을 리턴한다.
    * 기    타 : param   : 
    *            return  : String 요일(1:일요일  ... 7:토요일)
    */    
	public static int getDayName(){
		return Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.DAY_OF_WEEK);
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재 요일을 한국식으로 리턴한다.
    * 기    타 : param   : 
    *            return  : 요일(일요일  ...    토요일)
    */    
	public static String getDayNameKor(){
		int    li_day  = getDayName();
		String ls_week = "";

		switch (li_day)  {
			case  1: ls_week = "일요일";
					 break;
			case  2: ls_week = "월요일";
					 break;
			case  3: ls_week = "화요일";
					 break;
			case  4: ls_week = "수요일";
					 break;
			case  5: ls_week = "목요일";
					 break;
			case  6: ls_week = "금요일";
					 break;
			case  7: ls_week = "토요일";
					 break;
		}
		return ls_week;
	}

   
	/**    
	 * 작 성 자 : shhwang
	 * 작 성 일 : 2006-03-13 오후 2:13:10
	 * 설    명 : 들어온 두 날짜의 차이  (예)  diffDate("20000629", "20000630") --> 1   
	 * 기    타 : param   : String      sFirstDate, String      sSecondDate
	 *            return  : String      날짜의 차이 (sSecondDate - sFirstDate)
	 *     0729 : shhwang 날짜 계산 오류 수정 
	 */    
	public static String diffDate(String from, String to) throws java.text.ParseException 
	{
		String format = "yyyyMMdd";
		// 날짜 형식 지정
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

		// 날짜 검사
		java.util.Date d1 = check(from, format);
		java.util.Date d2 = check(to, format);
	
		long duration = d2.getTime() - d1.getTime();
	
		return String.valueOf((int)( duration/(1000 * 60 * 60 * 24) ));
	}
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 입력한 날짜 기준으로 몇일 전,후
    * 기    타 : param   : String date (19991002) , int 증감일수
    *            return  : String YYYY-MM-DD
    */    
	public static String addDatePrevNext(String yyyymmdd, int days) {
		try{
			yyyymmdd = replace(yyyymmdd, "-", "").trim();

			if(yyyymmdd.length() != 8){
				return yyyymmdd;
			}else{
				int Year  = Integer.parseInt(yyyymmdd.substring(0,4));
				int Month = Integer.parseInt(yyyymmdd.substring(4,6));
				int Dates = Integer.parseInt(yyyymmdd.substring(6,8));

				Calendar rightNow = Calendar.getInstance(TimeZone.getTimeZone("JST"));
				rightNow.set(Year, Month-1, Dates);
				rightNow.setTime(new java.util.Date(rightNow.getTime().getTime() - 86400000));

				Date date = rightNow.getTime();
				rightNow.add(Calendar.DATE, days+1);
				date=rightNow.getTime();
				yyyymmdd = int2s(rightNow.get(Calendar.YEAR), 4) + "-" + int2s(rightNow.get(Calendar.MONTH) + 1, 2) + "-" + int2s(rightNow.get(Calendar.DATE), 2);

				return yyyymmdd;
			}
		}catch(Exception e){
			return yyyymmdd;
		}
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 특정날짜 구간의 특정요일 구하기
    * 기    타 : param   : String, String, String[]
    *            return  : StringBuffer
    */    
	public static StringBuffer getDateBetweenInDAY_OF_WEEK(String fromdate, String todate, String days[]) {
		String tmpdate = "";                        //계산을 위한 임시 변수
		StringBuffer sb_date = new StringBuffer();  //리턴 value 날짜
		//StringBuffer sb_days = new StringBuffer();
		int tmpdays = 0;                            //해당 날짜의 요일 임시변수

		try{
			Calendar cal = new GregorianCalendar();
			fromdate = replace(fromdate.trim(), "-", "");
			todate = replace(todate.trim(), "-", "");
			//System.out.println("fromdate="+fromdate+" todate="+todate);

			tmpdate = fromdate;

			while(Integer.parseInt(tmpdate) <= Integer.parseInt(todate)){
				cal.set(Integer.parseInt(tmpdate.substring(0, 4)), Integer.parseInt(tmpdate.substring(4,6))-1, Integer.parseInt(tmpdate.substring(6,8)));
				tmpdays = cal.get(Calendar.DAY_OF_WEEK);
				//System.out.println("com.addDatePrevNext()="+tmpdate+" ====> "+tmpdays);

				for(int i=0; i<days.length; i++){
					//System.out.println("i="+i);
					if(tmpdays == Integer.parseInt(days[i].trim())){
						//System.out.println("있음");
						sb_date.append(strToCal(tmpdate, "-")+"/");    //날짜
						//sb_days.append(tmpdays+"/");
					}
				}

				//tmpdate = moveForward(tmpdate, "1");    //다음날짜..
				tmpdate = replace(addDatePrevNext(tmpdate, 1), "-", "");    //다음날짜..
			}

			//System.out.println("리턴날짜배열="+sb_date.toString());
			//System.out.println("리턴요일배열="+sb_days.toString());

			if(sb_date.length() > 0){
				sb_date.delete(sb_date.length()-1, sb_date.length());
			}
			cal.clear();
		}catch(Exception e){
			sb_date = null;
		}finally{
			return sb_date;
		}
	}





	/***********************************************************************************
	  HTML 관련 메소드 모음
	************************************************************************************/

   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : String 형식을 받아서 Html 형식으로 변환
    * 기    타 : param   : String
    *            return  : long
    */    
	public static String convertHtmlBr(String comment){
		int length = comment.length();
		StringBuffer buffer = new StringBuffer();
		if(comment.equals(null)){
			buffer.append("");
			return buffer.toString();
		}

		for (int i = 0; i < length; ++i){
			String comp = comment.substring(i, i+1);
			if ("\r".compareTo(comp) == 0){
				comp = comment.substring(++i, i+1);
				if ("\n".compareTo(comp) == 0)
					buffer.append("<BR>\r");
				else
					buffer.append("\r");
			}
			buffer.append(comp);
		}
		return buffer.toString();
	}

    
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 스트링내의 특수문자를 \를 붙인 HTML형식으로 변환
    * 기    타 : param   : String 바꿀 문자열
    *            return  : String
    */    


   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : string의 <enter>값에 html tag <br>을 붙인다
    * 기    타 : param   : content  string
    *            return  : <br>이 붙은 새로운 string
    */    
	public static String replaceEnterBr(String content){
		String tempStr = "";
		byte cmpChar = 0x0d;

		for(int i = 0; i < content.length(); i++){
			 if((byte)content.charAt(i) == cmpChar)
				if((byte)content.charAt(++i) == 0x0a){
					 tempStr += "<br>";
				}else{
					 --i;
				}
			 else
				 tempStr += String.valueOf(content.charAt(i));
		}
		return tempStr;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : HTML과 관련하여 일부 특수문자를 반환
    * 기    타 : param   : String 변환할 문자열
    *            return  : String HTML 특수문자가 변환된 문자열
    */    
	public static String getSpecialChar(String str){
		str = replace(str, "&", "&amp;");
		str = replace(str, "<", "&lt;");
		str = replace(str, ">", "&gt;");
		str = replace(str, "'", "&acute;");
		str = replace(str, "\"", "&cute;");
		str = replace(str, "|", "&brvbar;");

		str = replace(str, "\n", "<BR>");

		return str;
	}



	/***********************************************************************************
	  페이지제어 관련 메소드모음
	************************************************************************************/

   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 페이지 네비게이션 제어하기
    * 기    타 : param   : ls_currentPage     현재 페이지 번호, ls_totalPage       총 페이지 수, ls_pageStep       페이지당건수 
    *            return  : String             페이지 제어결과를 HTML로 넘김
    *             jsp페이지에 아래 스크립트추가
    *             function jsGoPages(iPagesNum)
    *             {  
    *                form = document.searchForm;
    *                form.page.value = iPagesNum;
    *                form.submit();
    *             }
    */    
		public String getPageList(int ls_currentPage, int ls_totalPage,int ls_pageStep){
			//System.out.println("ls_currentPage=" + ls_currentPage);
			//System.out.println("ls_totalPage=" + ls_totalPage);
			//System.out.println("ls_pageStep=" + ls_pageStep);
			int          pageStart    = 1;     // 페이지 시작
			int          i            = 0;     // For Loop 변수
			
			String       ls_fontColor = "#CE7128";
			String       ls_boldColor = "#CE7128";
			String       ls_space     = "";
			StringBuffer sb_page = new StringBuffer("");		

			pageStart = (((ls_currentPage-1)/ls_pageStep)*ls_pageStep) + 1; // 페이지 시작 구하기			
			
			//System.out.println("pageStart===> " + pageStart);
			int prev_page = pageStart - 1;
			
			System.out.println("prev_page===> " + prev_page);

			if ( prev_page < 1 ) prev_page = 1;

			sb_page.append("<font color=\"" + ls_fontColor + "\">");			

			if (pageStart > 1){
				//페이지를 가지고 현재 페이지를 다시 리플레시 해주면 페이지 번호가 나온다.

				if (pageStart > ls_pageStep) {
					sb_page.append("<a href=\"javascript:jsGoPages(" + 1 + "); \">");
					sb_page.append("<img src=\"/bosu/images/arrow_L.gif\" align=\"absmiddle\" hspace=\"6\" border=\"0\" vspace=\"1\">");
					sb_page.append("</a>");
				}

				sb_page.append("<a href=\"javascript:jsGoPages(" + prev_page +");\">");	
				sb_page.append("이전 ");			
				sb_page.append("</a>");		  

			}else{
				sb_page.append("이전 ");
			}

			for(i = pageStart; i < pageStart+ls_pageStep; i++){
			//for(i = pageStart; i < pageStart+10; i++){
				if(i == ls_totalPage + 1){
					break;
				}

				if(i != pageStart) ls_space = "&nbsp;";

				if(ls_currentPage == i)
					sb_page.append(ls_space + "<font color=\"" + ls_boldColor + "\"><b>" + i + "</b></font>");
				else					
				   sb_page.append(ls_space + "[<a href=\"javascript:jsGoPages(" + i +");\">"+i+"</a>]");
			}

			if ( i > ls_totalPage ){
				sb_page.append(" 다음");
			}else{
				sb_page.append("<a href=\"javascript:jsGoPages(" + i +");\"> 다음");				
				sb_page.append("</a>");

				sb_page.append("<a href=\"javascript:jsGoPages(" + ls_totalPage + ");\">");
				sb_page.append("<img src=\"/bosu/images/arrow_R.gif\" align=\"absmiddle\" hspace=\"6\" border=\"0\" vspace=\"1\">");
				sb_page.append("</a>");
				
			}
			sb_page.append("</font>");

			return sb_page.toString();
		}
		
   
   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 총페이지 구하기
    * 기    타 : param   : total_count     게시판 총 건수 , num_per_page           페이지당 건수
    *            return  : int               총페이지
    */    
	public static int getTotalPage(int total_count, int num_per_page) {	
			if(total_count == 0){
					 return 1;    
			}else{
					if((total_count % num_per_page) == 0) {  
						return  (total_count / num_per_page);
					}else{
						return (total_count / num_per_page + 1);
					}   
			}	
}


	// 좌측버튼 URL
	public static final String RIGHT_BTN_URL = "NEXT";

	//우측버튼 URL
	public static final String LEFT_BTN_URL  = "PREV";


   
   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : POST 방식 의 게시판의 counter(1).
    * 기    타 : param   : int : current_page , int : total_page
    *            return  : myIndexList(false, 10,current_page,total_page, LEFT_BTN_URL, RIGHT_BTN_URL, "goToPage", null) call
    */    
	public static String myIndexList(int current_page, int total_page){
		return myIndexList(false, 10,current_page,total_page, LEFT_BTN_URL, RIGHT_BTN_URL, "goToPage", null);
	}

   
   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 페이징 구현
    * 기    타 : param   : method_type  T/F : get/post
    *                      list_limit   setting number (displayed number)
    *                      current_page
    *                      total_page
    *                      left_image_url
    *                      right_image_url
    *                      callee_url    * 
    *            return  : html 로 변환되어 return
    */    
   public static String myIndexList(boolean method_type, int list_limit, int current_page, int total_page, String left_image_url, String right_image_url, String callee_url, String font_color){

		int     pagenumber;       // 화면에 보여지는 페이지 인덱스 수
		int     startpage;        // 화면에 보여지는 시작페이지 번호
		int     endpage;          // 화면에 보여지는 마지막페이지 번호
		int     curpage;          // 이동하고자 하는 페이지 번호


		StringBuffer returnList = new StringBuffer();

		//무결성 설정
		if(list_limit    < 0)        list_limit      = 0;
		if(current_page  < 0)        current_page    = 0;
		if(total_page    < 0)        total_page      = 0;
		if(left_image_url   == null) left_image_url  = "";
		if(right_image_url  == null) right_image_url = "";
		if(callee_url       == null) callee_url      = "";
		if(font_color       == null) font_color      = "";

		// 시작 페이지번호 구하기
		startpage = ((current_page - 1) / list_limit) * list_limit + 1;
		// 마지막 페이지번호 구하기
		endpage = (((startpage - 1) +  list_limit) / list_limit) * list_limit;
		// 총 페이지 수가 계산된 마지막페이지 번호보다 작을경우
		// 총 페이지 수가 마지막페이지 번호가 됨
		if (total_page <= endpage){
			endpage = total_page;
		}

		//좌측에 청크단위로 이동될 버튼이미지
		if(current_page > list_limit){
			curpage = startpage - 1;

			if(method_type)
				returnList.append("<a href='"+callee_url+"?page="+curpage+"' onFocus='this.blur()' class='link_pagenum_bg'>&nbsp;&nbsp;");
			else
				returnList.append("<a href='javascript:"+callee_url+"("+curpage+");' onFocus='this.blur()' class='link_pagenum_bg'>");

			returnList.append(left_image_url+"</a>&nbsp;&nbsp;");
		}

		// 시작페이지 번호부터 마지막페이지 번호까지 화면에 표시
		curpage = startpage;
		while(curpage <= endpage){
			if(curpage == current_page){
				returnList.append(current_page+"&nbsp;");
			}else{
				if(method_type)
					returnList.append(" <a href='"+callee_url+"?page="+curpage+"' onFocus='this.blur()' class='link_pagenum_bg'><font color='"+font_color+"'>"+curpage+"</font></a>&nbsp;");
				else
					returnList.append(" <a href='javascript:"+callee_url+"("+curpage+");' onFocus='this.blur()' class='link_pagenum_bg'><font color='"+font_color+"'>"+curpage+"</font></a>&nbsp; ");
			}

			curpage++;
		}


		//우측 청크단위로 이동될 버트이미지
		if(total_page > endpage){
			if(method_type)
				returnList.append("&nbsp;<a href='"+callee_url+"?page="+curpage+"' onFocus='this.blur()' class='link_pagenum_bg'>");
			else
				returnList.append("&nbsp;<a href='javascript:"+callee_url+"("+curpage+");' onFocus='this.blur()' class='link_pagenum_bg'>");

			returnList.append(right_image_url+"</a>");
		}

		return returnList.toString();

	}   //end String myIndexList()

   
   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 페이징 구현
    * 기    타 : param   : int : viewinpage
    *                      int : current_page
    *                      int : total_page
    *            return  : myIndexList(false, viewinpage, current_page, total_page, LEFT_BTN_URL, RIGHT_BTN_URL, "goToPage", null) call
    */    
	public static String myIndexList2(int viewinpage, int current_page, int total_page){
		return myIndexList2(false, viewinpage, current_page,total_page, LEFT_BTN_URL, RIGHT_BTN_URL, "goToPage", null);
	}

   
   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 페이징 구현
    * 기    타 : param   : method_type  T/F : get/post
    *                      list_limit   setting number (displayed number)
    *                      current_page
    *                      total_page
    *                      left_image_url
    *                      right_image_url
    *                      callee_url
    *            return  : html 로 변환되어 return
    */    
	public static String myIndexList2(boolean method_type, int list_limit, int current_page, int total_page, String left_image_url, String right_image_url, String callee_url, String font_color){
		int     pagenumber;       // 화면에 보여지는 페이지 인덱스 수
		int     startpage;        // 화면에 보여지는 시작페이지 번호
		int     endpage;          // 화면에 보여지는 마지막페이지 번호
		int     curpage;          // 이동하고자 하는 페이지 번호
		StringBuffer returnList = new StringBuffer();

		//무결성 설정
		if(list_limit    < 0)        list_limit      = 0;
		if(current_page  < 0)        current_page    = 0;
		if(total_page    < 0)        total_page      = 0;
		if(left_image_url   == null) left_image_url  = "";
		if(right_image_url  == null) right_image_url = "";
		if(callee_url       == null) callee_url      = "";
		if(font_color       == null) font_color      = "";

		// 시작 페이지번호 구하기
		startpage = ((current_page - 1) / list_limit) * list_limit + 1;

		// 마지막 페이지번호 구하기
		endpage = (((startpage - 1) +  list_limit) / list_limit) * list_limit;

		// 총 페이지 수가 계산된 마지막페이지 번호보다 작을경우
		// 총 페이지 수가 마지막페이지 번호가 됨
		if(total_page <= endpage){
			endpage = total_page;
		}

		//좌측에 청크단위로 이동될 버튼이미지
		if(current_page > list_limit){
			curpage = startpage - 1;

			if(method_type)
				returnList.append("<a href='"+callee_url+"?page="+curpage+"' onFocus='this.blur()' class='link_pagenum_bg'>&nbsp;&nbsp;");
			else
				returnList.append("<a href='javascript:"+callee_url+"("+curpage+");' onFocus='this.blur()' class='link_pagenum_bg'>");

			returnList.append(left_image_url+"</a>&nbsp;&nbsp;");
		}

		// 시작페이지 번호부터 마지막페이지 번호까지 화면에 표시
		curpage = startpage;
		while(curpage <= endpage){
			if(curpage == current_page){
				returnList.append(current_page+"&nbsp;");
			}else{
				if(method_type)
					returnList.append(" <a href='"+callee_url+"?page="+curpage+"' onFocus='this.blur()' class='link_pagenum_bg'><font color='"+font_color+"'>"+curpage+"</font></a>&nbsp;");
				else
					returnList.append(" <a href='javascript:"+callee_url+"("+curpage+");' onFocus='this.blur()' class='link_pagenum_bg'><font color='"+font_color+"'>"+curpage+"</font></a>&nbsp; ");
			}
			curpage++;
		}

		//우측 청크단위로 이동될 버트이미지
		if(total_page > endpage){
			if(method_type)
				returnList.append("&nbsp;<a href='"+callee_url+"?page="+curpage+"' onFocus='this.blur()' class='link_pagenum_bg'>");
			else
				returnList.append("&nbsp;<a href='javascript:"+callee_url+"("+curpage+");' onFocus='this.blur()' class='link_pagenum_bg'>");

			returnList.append(right_image_url+"</a>");
		}

		return returnList.toString();
	}   //end String myIndexList2()


   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 페이징 구현
    * 기    타 : param   : out - printout 객체
    *                      url - paging 이 사용되는 url
    *                      size - total record count
    *                      pageSize - 한 page당 출력할 record 갯수
    *                      scrollSize - display할 page 번호 갯수
    *                      pageNum - page number
    *            return  : String
    */    
	public void drawScroller(JspWriter out, String url, long size, long pageSize, long scrollSize, long pageNum) throws IOException {
		StringBuffer buffer = new StringBuffer();

		if(size < pageSize && size !=0){
			buffer.append("&nbsp;");
			//buffer.append("<img src=/image/ppre.gif border=0 align=absmiddle>&nbsp;");
			buffer.append("<img src=/image/pre.gif border=0 align=absmiddle>&nbsp;");
			buffer.append("[1]&nbsp;");
			buffer.append("<img src=/image/next.gif border=0 align=absmiddle>&nbsp;");
			//buffer.append("<img src=/image/nnext.gif border=0 align=absmiddle>&nbsp;");
			buffer.append("");
		}

		if(size == pageSize){
			buffer.append("&nbsp;");
			buffer.append("<img src=/image/pre.gif border=0 align=absmiddle>&nbsp;");
			buffer.append("[1]&nbsp;");
			buffer.append("<img src=/image/next.gif border=0 align=absmiddle>&nbsp;");
			buffer.append("");
		}

		if(size == 0){
			buffer.append("&nbsp;");
			//buffer.append("<img src=/image/ppre.gif border=0 align=absmiddle>&nbsp;");
			buffer.append("<img src=/image/pre.gif border=0 align=absmiddle>&nbsp;&nbsp;");
			buffer.append("<img src=/image/next.gif border=0 align=absmiddle>&nbsp;");
			//buffer.append("<img src=/image/nnext.gif border=0 align=absmiddle>&nbsp;");
			buffer.append("");
		}

		int pageQuantity    = (int)( size/pageSize );
		int i = 0;

		if( ((float)size)/((float)pageSize) > pageQuantity ){
			pageQuantity++;
		}

		if(pageNum < 1)  pageNum = 1;
		if(pageNum > pageQuantity && pageQuantity > 0)   pageNum = pageQuantity;
		/*
		if(size > 20){
			buffer.append("검색 결과1 " + size + " " + "중" + "&nbsp;");
		}else{
			buffer.append("검색 결과2 " + size + "&nbsp;");
		}
		*/

		//navigation
		if(pageQuantity > 1){
			long displacement = (int)(pageNum-1)/scrollSize;

			for( i = 1; i <=scrollSize ; i++ ) {
				long page = i+displacement*scrollSize;
				long from = (page-1)*pageSize   + 1;
				long to = page*pageSize;
				if(to > size)  to = size;

				if(pageNum != page ){
				}else{
					//buffer.append(from+"-"+to);
				}

				//if ( page == pageQuantity )   i = 11;
			}

			long cBlockNo = (((pageNum-1)/scrollSize) * scrollSize) + 1;
			long BlockNo2 = (((pageNum-1)/100) * 100) + 1;

			// 처음 페이지로 이동
			if(pageNum != 1)
				buffer.append("<A HREF=\""+url+"pageNum=1" + "&pageSize=" + pageSize + "\"></A>&nbsp;&nbsp;");
			else
				buffer.append("");

			//100단위 이동
			if(BlockNo2 != 1){
				buffer.append("<A HREF=\""+url+"pageNum="+(BlockNo2-100)+ "&pageSize=" + pageSize + "\">"+"<img src=/image/ppre.gif border=0 align=absmiddle>"+"</A>&nbsp;&nbsp;");
			}

			// 이전
			if(cBlockNo != 1){
				buffer.append("<A HREF=\""+url+"pageNum="+(cBlockNo-scrollSize)+ "&pageSize=" + pageSize + "\">"+"<img src=/image/pre.gif border=0 align=absmiddle>"+"</A>&nbsp;");
			}else{
				buffer.append("<img src=/image/pre.gif border=0 align=absmiddle>&nbsp;");
			}


			for(i=1; i<=(int)scrollSize; i++){
				if(cBlockNo == pageNum){
					buffer.append("<font color=red><b>[" + cBlockNo + "]</b></font> ");
				}else if(cBlockNo <= pageQuantity){
					buffer.append("<A HREF=\""+url+"pageNum="+cBlockNo+ "&pageSize=" + pageSize + "\">["+cBlockNo+"]</A> ");
				}else{
					break;
				}

				cBlockNo+=1;
			}

			BlockNo2+=100;

			// 다음
			if(cBlockNo <= pageQuantity){
				buffer.append("<A HREF=\""+url+"pageNum="+cBlockNo+ "&pageSize=" + pageSize + "\">"+"<img src=/image/next.gif border=0 align=absmiddle>"+"</A>&nbsp;&nbsp;");
			}else{
				buffer.append("<img src=/image/next.gif border=0 align=absmiddle>");
			}

			//100단위 이동
			if(BlockNo2 <= pageQuantity){
				buffer.append("<A HREF=\""+url+"pageNum="+BlockNo2+ "&pageSize=" + pageSize + "\">"+"<img src=/image/nnext.gif border=0 align=absmiddle>"+" </A>");
			}

			//마지막 페이지로 이동
			if(pageNum != pageQuantity)
				buffer.append("<A HREF=\""+url+"pageNum="+pageQuantity+ "&pageSize=" + pageSize + "\"></A>");
			else
				buffer.append("");
		}
		//String strSelect = "";

		out.println(buffer.toString());
	}

   
   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : paging 시  넘겨줄 index값을 계산하여 출력한다.
    * 기    타 : param   : fillChar       정해진 길이로 맞추기 위해 채워넣는 문자열
    *                      length        정해진 길이
    *                      pageNum   페이지 번호
    *                      pageSize   페이지 사이즈                    
    *            return  : String
    */    
	public static String pagingIndex(String fillChar, int length, String pageNum, String pageSize) {

		long index = ((Long.parseLong(pageNum) - 1) * Long.parseLong(pageSize) ) + 1;
		String sindex = index + "";

		if(sindex.length() > length)
			return sindex;

		String returnStr = "";
		int i;
		for(i = sindex.length(); i < length; i++){
			returnStr += fillChar;
		}

		returnStr += sindex;

		return returnStr;
	}





	/***********************************************************************************
	  문자열 관련 함수모음
	************************************************************************************/

   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 특정문자 변환 Check
    * 기    타 : param   : str 바꾸려는 문자열을 가진 원본
    *                      pattern 찾을 문자열
    *                      replace 바꿔줄 문자열    
    *            return  : String
    */    
	public static String replace(String str, String pattern, String replace){
		int s = 0; // 찾기 시작할 위치
		int e = 0; // StringBuffer에 append 할 위치
		StringBuffer result = new StringBuffer(); // 잠시 문자열 담궈둘 놈

		while ((e = str.indexOf(pattern, s)) >= 0){
			result.append(str.substring(s, e));
			result.append(replace);
			s = e+pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 주어진 문자열 str이  null이면 공백을 출력한다.
    * 기    타 : param   : arg 출력할 문자열
    *            return  : String
    */    
	public static final String  nullToBlank(String arg){
		String str="";
		if(arg == null){
			arg = "";
		}else if(arg.equals("null")){
			arg = "";
		}

		arg = arg.trim();
		//System.out.println("return value = " + arg);

		return arg;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : len 수 만큼 스트링 채워넣기
    * 기    타 : param   : int len 길이
    *                      String chr 채워넣을 문자
    *            return  : String
    */    
	public static String strAddLoop(int len, String chr){
		String str = "";
		for (int i = 1; i <= len ; i++){
			str += chr;
		}
		return str;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 스트링에 특정 길이 만큼 앞뒤로 붙이기
    * 기    타 : param   : String currStr 원본 스트링
    *                      int strLength 반복횟수
    *                      String fillStr 반복할 문자
    *                      boolean right_pos ture이면 오른쪽에 아니면 왼쪽에 추가
    *            return  : String
    */    
	public static String fillString(String currStr, int strLength, String fillStr, boolean right_pos){
		int    cnt = 0;
		byte[] b = currStr.getBytes();
		cnt = currStr.length();
		cnt = b.length;

		for(int i = cnt; i < strLength; i++){
			if(right_pos == true){
				currStr += fillStr;
			}else{
				currStr = fillStr + currStr;
			}
		}

		return currStr;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : null 또는 "" 이면 특정한 문자열로 대치
    * 기    타 : param   : String Str 원본 스트링
    *                      String conStr 대체할 문자열
    *            return  : String
    */    
	public static String nullToString(String Str, String conStr){
		String retValue = "";
		if(Str == null || Str.equals(""))
			retValue = conStr;
		else
			retValue = Str;
		return retValue;
	}


   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 스트링을 일정한 길이이상일때 ".."를 붙여서 잘라줌
    * 기    타 : param   : str  스트링문자
    *                      len  자르고자하는 길이
    *            return  : 변환된 스트링문자
    */    
	public static String cutString(String str, int len){
		if(str.length() < (len+1))
			return str;
		else
			return (str.substring(0, len) + "..");
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 문자열에서 특정문자의 포함 개수를 구한다.
    * 기    타 : param   : str  string
    *                      chr  string
    *            return  : 카운트 수
    */    
	public static int countChar(String str, char chr){
		int count = 0;
		for(int i=0; i<str.length(); i++){
			if(str.charAt(i) == chr)
				count++;
		}
		return count;
	 }

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : String에서 특정문자를 없앤다
    * 기    타 : param   : String str 원본대상 문자열
    *                      char tchar 제거하고자 할 문자
    *            return  : tchar가 제거된 새로운 String
    */    
	public static String removeChar(String str, char tchar){
		StringBuffer tempStr = new StringBuffer();

		for(int i=0; i<str.length(); i++){
			if(str.charAt(i) != tchar)
				tempStr = tempStr.append(str.charAt(i));
		}

		return new String(tempStr);
	 }


   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : String에서 전각문자의 SPACE를 없앤다
    * 기    타 : param   : String str 작업대상 문자열
    *            return  : 새로운 String
    *            2006-03-20 파라미터가 null 경우 처리 추가 (jkkoo)
    */    
	public static String removeSpace(String str){
      if (str == null) return ""; // 2005-03-20 jkkoo
		String newStr = str.trim();
		byte b[] = newStr.getBytes();
		int nSize = b.length;

		for(int i=(nSize-1); i>=0; i--){
			if(i > 0 &&
				( (b[i-1] == 0xffffffa1 && b[i] == 0xffffffa1 ) ||
				  (b[i-1] == 0xffffffe1 && b[i] == 0xffffffe1 ) ||
				  (b[i-1] == 0xffffffa1 && b[i] == 0xffffffe1 ) )){

				b[i]   = 0x20;
				b[i-1] = 0x20;
			}
		}

		//한글반쪽처리
		for(int i=0; i<=(nSize-1); i++){
			if(i%2 != 0){
				if(( b[i-1]>0xffffff80 && b[i-1] != 0x20)  && b[i]==0x20){
					b[i] = 0xffffffa1;
				}
			}
		}

		return (new String(b)).trim();
	 }


   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : InputStream 형 데이터를 한글 String 형으로 변환
    * 기    타 : param   : InputStream   InputStream 값
    *            return  : InputStream   Value
    */    
	public static String getStreamToString(InputStream is){
		if (is == null) return "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String r = null;
		try{
			// NEED UPGRADE
			int t;
			while((t=is.read()) >= 0){
				baos.write(t);
			}

			try{
				r = baos.toString("KSC5601");
			}catch(Exception e){
				r = baos.toString();
			}
			baos.close();
		}catch(Exception e){
			e.printStackTrace(System.err);
			if(baos != null)
				try{
					baos.close();
					baos = null;
				}catch(Exception le){}
		}
		return r;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : '0'으로 모두 채워진 문자열을 특정문자로 바꾸어줌
    * 기    타 : param   : str     입력 문자열
    *                      rstr    모두 0일경우 바꿀문자
    *            return  : 변경된 문자열
    */    
	public static String allZeroToStr(String str, String rstr){
		if(str == null){
			return "";
		}else{
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<str.length(); i++){
				sb.append('0');
			}
			String newStr = new String(sb);

			if(str.equals(newStr))
				return rstr;
			else
				return str;
		}
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 구분자에 따라 문자열 분리 StringToken이용
    * 기    타 : param   : str        분리할 문자열
    *                      token      구분자
    *            return  : 분리된 문자열 배열
    */    
	public static String[] getTokenStr(String str, String token){
		String array[] = null;
		try{
			StringTokenizer st = new StringTokenizer(str.trim(), token);
			StringBuffer sb = new StringBuffer();
			String temp = "";

			int i=0;
			while(st.hasMoreTokens()){
				temp = st.nextToken().trim();

				if(!temp.equals("") && st.hasMoreTokens()){
					sb.append(temp+token);
					i++;
				}else if(!temp.equals("")){
					sb.append(temp);
					i++;
				}
			}

			if(i > 0){
				array = new String[i];
				StringTokenizer st2 = new StringTokenizer(sb.toString(), token);
				int j=0;
				while(st2.hasMoreTokens()){
					array[j++] = st2.nextToken();
				}
			}
			return array;
		}catch(Exception e){
			return null;
		}
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 지정인덱스 이후 문자열 replace 메소드
    * 기    타 : param   : String      text    원본
    *                      int         start   시작 index
    *                      String      src     찾는문자
    *                      String      dest    바꾸려는문자
    *            return  : String      결과 문자열
    */    
	public static String replaceIdxFrom(String text, int start, String src, String dest){
		if(text==null) return null;
		if(src==null || dest==null)   return text;

		int textlen = text.length();
		int srclen  = src.length();
		int diff    = dest.length() - srclen;
		int d = 0;

		StringBuffer t = new StringBuffer(text);
		while(start<textlen){
			start = text.indexOf(src, start);

			if(start<0) break;

			t.replace(start+d, start+d+srclen, dest);
			start   += srclen;
			d       += diff;
		}
		return t.toString();
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 문자열의 왼쪽부분에 특정문자를 추가해서 원하는 길이로 만들기
    * 기    타 : param   : String str  원문
    *                      String fillChar 추가할 문자
    *            return  : int length     만들고자하는 문자열 길이
    */    
	public static String addFillLeft(String str, String fillChar, int length){
		if(str.length() > length)
			return str;

		String returnStr = "";
		int i;
		for(i = str.length(); i < length; i++){
			returnStr += fillChar;
		}

		return returnStr + str;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 문자열의 오른쪽부분에 특정문자를 추가해서 원하는 길이로 만들기
    * 기    타 : param   : String str  원문
    *                      String fillChar 추가할 문자
    *            return  : int length     만들고자하는 문자열 길이
    */    
	public static String addFillRight(String str, String fillChar, int length){
		if(str.length() > length)
			return str;

		String returnStr = "";
		int i;
		for(i = str.length(); i < length; i++){
			returnStr += fillChar;
		}

		return str + returnStr;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 메시지 파일 CommonMSG.properties파일에서 해당 키값으로 메시지 읽기
    * 기    타 : param   : String inProperty   메시지를 읽어 들일 키값
    *                      String strValue     키값에 메치되는 메시지 문자열
    */    
   
	//public static String getMSG(String inProperty){
	public String getMSG(String inProperty){
	/*
		//InputStream is       = getClass().getResourceAsStream("CommonMSG.properties");
		String      strValue  = "";
		try{
			FileInputStream is       = new FileInputStream("CommonMSG.properties");
			Properties  prop = new Properties();

			try{
				prop.load(is);
				strValue = enToKo(prop.getProperty(inProperty));
			}catch(Exception e){
				System.err.println("Can't read the properties file. " +
				  "Make sure CommonMSG.properties is in the CLASSPATH");
				strValue = "CommonMSG.properties을 읽어 들일 수 없습니다.";
			}
		}catch(Exception e){
			strValue = "CLASSPATH에서 CommonMSG.properties 파일을 찾을 수 없습니다.";
		}
		return strValue;
		*/

		//non static일 경우
		InputStream is       = getClass().getResourceAsStream("CommonMSG.properties");
		Properties  prop = new Properties();
		String      strValue  = "";
		try{
			prop.load(is);
			strValue = enToKo(prop.getProperty(inProperty));
		}catch(Exception e){
			System.err.println("Can't read the properties file. " +
			  "Make sure CommonMSG.properties is in the CLASSPATH");
			strValue = "CLASSPATH에서 CommonMSG.properties 파일을 찾을 수 없습니다.";
		}
		return strValue;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 쿼리 파일에서 쿼리문 읽기
    * 기    타 : param   : String filename     쿼리 파일명
    *                      String[] Param      쿼리문 Parameter
    */    
	public String MakeQuery(String filename, String[] Param){
		String strQry = "";
		String strSub = "";
		String strNum = "";
		String strPath = "";

		try {
			//sql file path
			strPath = QUERYROOT+"/"+filename.substring(0,3)+"/"+filename.substring(0);
			//System.out.println("query File = "+strPath);

			File file = new File(strPath);

			int size  = (int)file.length();
			int chars_read = 0;
			FileReader in = new FileReader(file);
			char[] data = new char[size];
			while(in.ready()) {
				chars_read += in.read(data, chars_read, size - chars_read);
			}
			in.close();

			strQry = new String(data, 0, chars_read);

			//paramter replace
			for(int i = 0; i < Param.length; i++) {
				if(Param.length < 100) {
					if(i + 1 < 10) strNum = "0";
					else           strNum = "";
				}else{
					if(i + 1 < 100) {
						strNum = "0";
						if(i + 1 < 10) strNum = strNum + "0";
					}else{
						strNum = "";
					}
				}
				strNum = strNum + String.valueOf(i + 1).trim();                                         //파라미터 인덱스
				strQry = replace(strQry, "{%S" + strNum + "%}", "'" + Param[i] + "'");                 //문자열 파라미터
				strQry = replace(strQry, "{%N" + strNum + "%}", replace(Param[i], ",", ""));        //숫자형 파라미터
				strQry = replace(strQry, "{%T" + strNum + "%}", Param[i]);                             //테이블이나, 필드명 파라미터
			}
		}catch(Exception e){
			System.out.println("[CommonUtil.MakeQuery()] 쿼리 파일을 만들지 못했습니다."+e.getMessage());
			strQry = "";
		}

		return strQry;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 쿼리 파일에서 쿼리문 읽기 No Parameter
    * 기    타 : param   : String filename     쿼리 파일명
    */    
	public String MakeQueryNP(String filename){
		String strQry = "";
		String strSub = "";
		String strNum = "";
		String strPath = "";

		try {
			//sql file path
			strPath = QUERYROOT+"/"+filename.substring(0,3)+"/"+filename.substring(0);
			//System.out.println("query File = "+strPath);

			File file = new File(strPath);

			int size  = (int)file.length();
			int chars_read = 0;
			FileReader in = new FileReader(file);
			char[] data = new char[size];
			while(in.ready()) {
				chars_read += in.read(data, chars_read, size - chars_read);
			}
			in.close();

			strQry = new String(data, 0, chars_read);

		}catch(Exception e){
			System.out.println("[CommonUtil.MakeQueryNP()] 쿼리 파일을 만들지 못했습니다."+e.getMessage());
			strQry = "";
		}

		return strQry;
	}





	/***********************************************************************************
	  숫자 & 금액 관련 메소드
	************************************************************************************/

   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 정수를 받아서 반올림(5이하 버림, 5이상 올림)
    * 기    타 : param   : int
    *            return  : long
    */    
	public static long Round(int number){
		Integer i = new Integer(number);
		double d_number = i.doubleValue();
		long round_number = Math.round(d_number/10);
		long exchange_number = round_number * 10;
		return exchange_number;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : double를 받아서 반올림(5이하 버림, 5이상 올림)
    * 기    타 : param   : double
    *            return  : long
    */    
	public static long Round(double number){
		long round_number = Math.round(number/10);
		long exchange_number = round_number * 10;
		return exchange_number;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 주민번호 1234561234567에 '-'넣기
    * 기    타 : param   : resno 바꿀 문자열
    *            return  : String
    */    
	public static String strResnoFormat(String resno){
		String em_resno = "";

		if(resno == null)
			return em_resno;

		if(resno != null && (resno.trim().length() != 13 || resno.indexOf("-") > -1))
			return replace(resno, " ", "");

		em_resno  = resno.trim().substring(0, 6) + "-" + resno.trim().substring(6);
		return em_resno;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 사업자번호에 '-'넣기
    * 기    타 : param   : resno 바꿀 문자열
    *            return  : String
    */    
	public static String strBizFormat(String resno){
		String em_resno = "";

		if(resno == null)
			return em_resno;

		if(resno != null && (resno.trim().length() != 10 || resno.indexOf("-") > -1))
			return replace(resno, " ", "");

		em_resno  = resno.trim().substring(0, 3) + "-" + resno.trim().substring(3, 5) + "-" + resno.trim().substring(5);
		return em_resno;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 우편번호에 '-'넣기
    * 기    타 : param   : post 바꿀 문자열
    *            return  : String
    */    
	public static String PostnoFormat(String post){
		String postno = "";

		if(post == null)
			return postno;

		if(post != null && (post.trim().length() != 6 || post.indexOf("-") > -1))
			return replace(post, " ", "");

		postno  = post.trim().substring(0, 3) + "-" + post.trim().substring(3,6);
		return postno;
	}


	// 메뉴별 수량, 단가, 금액형식
	DecimalFormat NumFrm0 = new DecimalFormat("###,###,###,##0;-###,###,###,##0");
	DecimalFormat NumFrm1 = new DecimalFormat("###,###,###,##0.#;-###,###,###,##0.#");
	DecimalFormat NumFrm2 = new DecimalFormat("###,###,###,##0.##;-###,###,###,##0.##");
	DecimalFormat NumFrm3 = new DecimalFormat("###,###,###,##0.###;-###,###,###,##0.###");
	DecimalFormat NumFrm4 = new DecimalFormat("###,###,###,##0.####;-###,###,###,##0.####");
	DecimalFormat NumFrm5 = new DecimalFormat("###,###,###,##0.#####;-###,###,###,##0.#####");
	DecimalFormat NumFrm6 = new DecimalFormat("###,###,###,##0.######;-###,###,###,##0.######");
	DecimalFormat NumFrm7 = new DecimalFormat("############;-############");

	DecimalFormat zNumFrm0 = new DecimalFormat("###,###,###,##0;-###,###,###,##0");
	DecimalFormat zNumFrm1 = new DecimalFormat("###,###,###,##0.0;-###,###,###,##0.0");
	DecimalFormat zNumFrm2 = new DecimalFormat("###,###,###,##0.00;-###,###,###,##0.00");
	DecimalFormat zNumFrm3 = new DecimalFormat("###,###,###,##0.000;-###,###,###,##0.000");
	DecimalFormat zNumFrm4 = new DecimalFormat("###,###,###,##0.0000;-###,###,###,##0.0000");
	DecimalFormat zNumFrm5 = new DecimalFormat("###,###,###,##0.00000;-###,###,###,##0.00000");
	DecimalFormat zNumFrm6 = new DecimalFormat("###,###,###,##0.000000;-###,###,###,##0.000000");

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 수량, 단가, 금액 형식 지정(double 형)
    * 기    타 : param   : double 숫자
    *                      String 형식번호
    *            return  : String
    */    
	public String setFrm(double inNum, String inStr) {
		String retValue = "";
		if(inStr.equals("0"))      retValue = NumFrm0.format(inNum);
		else if(inStr.equals("1")) retValue = NumFrm1.format(inNum);
		else if(inStr.equals("2")) retValue = NumFrm2.format(inNum);
		else if(inStr.equals("3")) retValue = NumFrm3.format(inNum);
		else if(inStr.equals("4")) retValue = NumFrm4.format(inNum);
		else if(inStr.equals("5")) retValue = NumFrm5.format(inNum);
		else if(inStr.equals("6")) retValue = NumFrm6.format(inNum);
		else if(inStr.equals("7")) retValue = NumFrm7.format(inNum);

		return retValue;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 수량, 단가, 금액 형식 지정 0으로 대체 (double 형)
    * 기    타 : param   : double 숫자
    *                      String 형식번호
    *            return  : String
    */    
	public String setFrmZero(double inNum, String inStr) {
		String retValue = "";
		if(inStr.equals("0"))      retValue = zNumFrm0.format(inNum);
		else if(inStr.equals("1")) retValue = zNumFrm1.format(inNum);
		else if(inStr.equals("2")) retValue = zNumFrm2.format(inNum);
		else if(inStr.equals("3")) retValue = zNumFrm3.format(inNum);
		else if(inStr.equals("4")) retValue = zNumFrm4.format(inNum);
		else if(inStr.equals("5")) retValue = zNumFrm5.format(inNum);
		else                       retValue = zNumFrm6.format(inNum);

		return retValue;
	}


	public String setFrm(long inNum, String inStr) {
		String retValue = "";
		retValue = NumFrm0.format(inNum);
		return retValue;
	}


	public String setFrm(String inNum, String inStr) {
		String retValue = "";
		if(inNum.equals("")){
			retValue = "0";
			return retValue;
		}
		//if(inNum.indexOf(".") > 0) retValue = setFrm(Double.parseDouble(replace(inNum, ",", "")), inStr);
		//else                       retValue = setFrm(Long.parseLong(replace(inNum, ",", "")), inStr);
		retValue = setFrm(Double.parseDouble(replace(inNum, ",", "")), inStr);
		return retValue;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : int형 숫자를 3자리 마다 콤마찍기
    * 기    타 : param   : num  int
    *            return  : 새로운 string
    */    
	public static String dFormat(int num){
		NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
		return nf.format(num);
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : long형 숫자를 3자리 마다 콤마찍기
    * 기    타 : param   : num  long
    *            return  : 새로운 string
    */    
	public static String dFormat(long num){
		NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
		return nf.format(num);
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : double형 숫자를 3자리 마다 콤마찍기
    * 기    타 : param   : num  double
    *            return  : 새로운 string
    */    
	public static String dFormat(double num){
		NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
		return nf.format(num);
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 문자형 숫자를 3자리 마다 콤마찍기
    * 기    타 : param   : num  String
    *            return  : 새로운 string
    */    
	public static String dFormat(String str){
		if (str == null)       return "";
		if (str.length() == 0) return "";

		NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
		Number num  =  null ;
		try{
			num  =  nf.parse(str);
		}catch(java.text.ParseException e){
		}
		return dFormat(num.longValue());
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : String 값을 #,###,###,##0.#0 형식으로 바꿈
    * 기    타 : param   : String str     받은 값
    *            return  : #,###,###,###.00
    */    
	public static String fFormat(String str){
		if (str == null)       return "";
		if (str.length() == 0) return "";

		DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
		String retstr=null;

		try{
			return df.format(Long.parseLong(str));
		}catch(NumberFormatException nfe){
			try{
				return df.format(Double.valueOf(str).doubleValue());
			}catch(Exception ee){
				return "0";
			}
		}catch(Exception e){
			return "0";
		}
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 받은 값을 #,###,###,##0.#0 형식으로 소수점지정 바꿈
    * 기    타 : param   : String str  숫자 값
    *                      int cnt     소수점 자릿수
    *            return  : #,###,###,###.xxxx
    */    
	public static String fFormat(String str, int cnt){
		if (str == null)       return "";
		if (str.length() == 0) return "";

		//String s_format = "#,###,###,##0.";
		StringBuffer sb_format = new StringBuffer("#,###,###,##0.");

		if(cnt <= 0){
			sb_format.deleteCharAt(13);
		}else{
			for(int i=0; i< cnt; i++){
				sb_format.append("0");
			}
		}

		DecimalFormat df = new DecimalFormat(sb_format.toString());
		String retstr=null;

		try{
			retstr = df.format(Long.parseLong(str));
			return retstr;
		}catch(NumberFormatException nfe){
			try{
				retstr = df.format(Double.valueOf(str).doubleValue());
				return retstr;
			}catch(Exception ee){
				return "0";
			}
		}catch(Exception e){
			return "0";
		}
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : #.# 로 오는 string 값을 #.# float 값으로 변환
    * 기    타 : param   : String str
    *            return  : 변환실수값
    */    
	public static float StringToFloat(String str){
		float fTmp;

		try {
			fTmp = Float.valueOf(str).floatValue();
		}catch(Exception e){
			fTmp = 0;
		}
		return fTmp;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 우측에 i_length만큼 0을 추가
    * 기    타 : param   : s_Data   문자열
    *                      i_length 추가할 0의 갯수
    *            return  : String
    */    
	public static String setZero(String s_Data, int i_length){
		for (int i_cnt = 0; i_cnt < i_length; i_cnt++)
			s_Data = s_Data + "0";
		return s_Data;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : Double형의 문자를 소수 i자리에서 반올림을 한다.
    * 기    타 : param   : String s_Data   Double형 문자
    *                      int i_num       반올림할 자리
    *            return  : String          반올림된 문자형 숫자
    */    
	public static String DoubleTypeRound(String s_Data,int i_num){
		String s_temp  = "";
		String s_temp1 = "";
		int    i_index = 0;
		double d_temp  = 0.0;
		s_Data = NullToBlank(s_Data.trim());

		if((s_Data.length() != 0) && (!s_Data.equals("0"))){
			for(int i=0; i < s_Data.length(); i++){
				s_temp = s_Data.substring(i,i+1);
				if(s_temp.equals(".")) i_index = i;
			}

			if(i_index != 0){
				if((s_Data.length()-(i_index+1)) < i_num){
					s_Data=setZero(s_Data,i_num-(s_Data.length()-(i_index+1)));
				}
				s_temp  = s_Data.substring(0,i_index)+"."+s_Data.substring(i_index+1,i_index+i_num);
				s_temp1 = s_Data.substring(i_index+i_num,i_index+i_num+1);

				if(Integer.parseInt(s_temp1) >= 5 ) d_temp = Math.pow(0.1,i_num-1);

				s_Data  = new Double(((Double.parseDouble(s_temp)*Math.pow(10.0,i_num-1))+d_temp*Math.pow(10.0,i_num-1))/Math.pow(10.0,i_num-1)).toString();
			}
		}
		return s_Data;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 에러대신 Zero String을 리턴하는 substring 메소드
    * 기    타 : param   : String   str      string source
    *                      int      start    substring 시작위치
    *                      int      length   substring 길이
    *            return  : String 
    */    
	public static String getSubstring(String Str, int start, int len){
		if(Str == null) return "";
		int slen = Str.length();

		if((slen < 1) || (start<0) || (len < 1) ) return "";

		if((slen-1) < start) return "";

		if(slen < (start+len)){
			return Str.substring(start,Str.length());
		}else{
			return Str.substring(start,start+len);
		}
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 숫자로된 금액을 문자로 표시함 - 삼천원
    * 기    타 : param   : String     str       금액String(숫자)
    *            return  : String     금액String(문자)
    */    
	public static String makeIntToKorCurr(String str){
		String[] arrBigUnit   = {"", "만", "억", "조", "경", "해"};
		String[] arrOne       = {"", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		String[] arrTwo       = {"", "십", "이십", "삼십", "사십", "오십", "육십", "칠십", "팔십", "구십"};
		String[] arrThr       = {"", "백", "이백", "삼백", "사백", "오백", "육백", "칠백", "팔백", "구백"};
		String[] arrFou       = {"", "천", "이천", "삼천", "사천", "오천", "육천", "칠천", "팔천", "구천"};
		String   lastWord     = "원";

		String   returnValue  = "";
		String   oneWord      = "";
		String   unitWord     = "";

		int big   = 0;

		if(str == null || str.trim().length() > 24){
			return "";
		}

		for(int nI = str.length(); str != ""; nI -= 4){
			int nK = 0;
			String unitKor = "";
			unitWord = getSubstring(str, (nI > 4) ? nI - 4 : 0, 4);

			for(int nJ = unitWord.length() - 1; nJ >= 0; nJ--){
				nK++;
				oneWord = getSubstring(unitWord, nJ, 1);

				switch(nK){
					case 1:
						unitKor = arrOne[Integer.parseInt(oneWord)];
						break;
					case 2:
						unitKor = arrTwo[Integer.parseInt(oneWord)] + unitKor;
						break;
					case 3:
						unitKor = arrThr[Integer.parseInt(oneWord)] + unitKor;
						break;
					case 4:
						unitKor = arrFou[Integer.parseInt(oneWord)] + unitKor;
						break;
				}
			}

			if(!unitKor.equals(""))
				returnValue = unitKor + arrBigUnit[big++] + returnValue;
			else
				big++;

			str = getSubstring(str, 0, str.length() - 4);

		}

		return returnValue + lastWord;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 숫자로된 금액을 문자로 표시함 - 3천원
    * 기    타 : param   : String     str       금액String(숫자)
    *            return  : String     금액String(문자)
    */    
	public static String makeIntToNumKorCurr(String str){
		String[] arrBigUnit   = {"", "만", "억", "조", "경", "해"};
		String[] arrOne       = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String[] arrTwo       = {"", "10", "20", "30", "40", "50", "60", "70", "80", "90"};
		String[] arrThr       = {"", "1백", "2백", "3백", "4백", "5백", "6백", "7백", "8백", "9백"};
		String[] arrFou       = {"", "1천", "2천", "3천", "4천", "5천", "6천", "7천", "8천", "9천"};
		String   lastWord     = "원";

		String   returnValue  = "";
		String   oneWord      = "";
		String   unitWord     = "";

		int big   = 0;

		if(str == null || str.trim().length() > 24){
			return "";
		}

		for(int nI = str.length(); str != ""; nI -= 4){
			int nK = 0;
			String unitKor = "";
			unitWord = getSubstring(str, (nI > 4) ? nI - 4 : 0, 4);

			for(int nJ = unitWord.length() - 1; nJ >= 0; nJ--){
				nK++;
				oneWord = getSubstring(unitWord, nJ, 1);

				switch(nK){
					case 1:
						unitKor = arrOne[Integer.parseInt(oneWord)];
						break;
					case 2:
						unitKor = arrTwo[Integer.parseInt(oneWord)] + unitKor;
						break;
					case 3:
						unitKor = arrThr[Integer.parseInt(oneWord)] + unitKor;
						break;
					case 4:
						unitKor = arrFou[Integer.parseInt(oneWord)] + unitKor;
						break;
				}
			}

			if(!unitKor.equals(""))
				returnValue = unitKor + arrBigUnit[big++] + returnValue;
			else
				big++;

			str = getSubstring(str, 0, str.length() - 4);
		}
		return returnValue + lastWord;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 두 문자열을 BigDecimal Type으로 덧셈연산하고 결과값을 문자열로 반환
    * 기    타 : param   : String    str1    문자열
    *                      String    str2    더해질 문자열
    *            return  : String    (str1 + str2)의 결과값 String
    */   
	public static String BigDecimalAdd(String str1, String str2){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.add(B_decimal2);

		return B_decimal1.toString();
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 두 문자열을 BigDecimal Type으로 뺄셈연산하고 결과값을 문자열로 반환
    * 기    타 : param   : String    str1    문자열
    *                      String    str2    마이너스할 문자열
    *            return  : String    (str1 - str2)의 결과값 String
    */   
	public static String BigDecimalSubtract(String str1, String str2){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.subtract(B_decimal2);

		return B_decimal1.toString();
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 두 문자열을 BigDecimal Type으로 곱셈연산하고 결과값을 문자열로 반환
    * 기    타 : param   : String    str1    문자열
    *                      String    str2    곱할 문자열
    *            return  : (str1 * str2)의 결과값 String
    */   
	public static String BigDecimalMultiply(String str1, String str2){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.multiply(B_decimal2);

		return B_decimal1.toString();
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 두 문자열을 BigDecimal Type으로  나누기연산하고 결과값을 문자열로 반환
    * 기    타 : param   : String    str1    문자열
    *                      String    str2    나눌 문자열
    *                      int       rd     반올림할 자리수 (BigDecimal메소드의 divide메소드 참조)
    *            return  : (str1 / str2)를 scale에 맞춰 반올림한 값 String
    */   
	public static String BigDecimalDivide(String str1, String str2, int rd){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.divide(B_decimal2, rd, BigDecimal.ROUND_HALF_UP);

		return B_decimal1.toString();
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 두 문자열을 BigDecimal Type으로  나누기연산하고 결과값을 문자열로 반환
    * 기    타 : param   : String    str1    문자열
    *                      String    str2    나눌 문자열
    *                      int      scale   반올림 또는 결과값이 나올 자리수 (BigDecimal메소드의 divide메소드 참조)
    *                      int      rnd     반올림 또는 절사 패턴
    *                          0 : ROUND_UP      ,    1 : ROUND_DOWN
    *                          2 : ROUND_CEILING ,    3 : ROUND_FLOOR
    *                          4 : ROUND_HALF_UP ,    5 : ROUND_HALF_DOWN
    *                          6 : ROUND_HALF_EVEN ,  7 : ROUND_UNNECESSARY
    *            return  : (str1 / str2)를 scale자리수에서 rnd패턴으로 처리한 결과값 String
    */   
	public static String BigDecimalDivide(String str1, String str2, int scale, int rnd){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.divide(B_decimal2, scale, rnd);

		return B_decimal1.toString();
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 두 개의 문자열을 BigDecimal Type으로 비교하고 결과값을 int형으로 반환
    * 기    타 : param   : String    str1    문자열
    *                      String    str2    비교할 문자열
    *            return  : str1 와 B값의 비교 결과 int
    *                      str1 > str2 : 양수값
    *                      str1 < str2 : 음수값
    *                      str1 = str2 : 0* 
    */   
	public static int  BigDecimalCompare(String str1, String str2){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());
		int        i_rtn = 0;

		i_rtn = B_decimal1.compareTo(B_decimal2);

		return i_rtn;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 지정 token으로 분리된 String의 합을 구한다.
    * 기    타 : param   : str     전달된 String
    *                      tokens  tokenizer
    *            return  : 처리된 String
    */   
	public static String TokenSum(String str, String tokens){
		String   ls_return = "0";
		try{
			String[] ls_str    = getTokenStr(str, tokens.trim());
			int      i;

			for ( i = 0; i < ls_str.length; i++ ) {
				ls_return = BigDecimalAdd(ls_return, ls_str[i]);
			}
		}catch(Exception e){
			ls_return = "0";
		}
		return ls_return;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 숫자로 구성된 문자열에 자리수 마다 , 넣기..
    * 기    타 : param   : String
    *            return  : String
    */   
	public static String commaFormat(String text, int type){
		if(text == null)
			return "";
		else if (text.trim().length() < 1)
			return text.trim();
		else if(text.trim().equals("null"))         //방수윤씨 추가요청..
			return "";

		boolean dc = false;         //숫자체크..
		dc = IsIntegerCheck(text);      //숫자면 true, 아니면 false;

		if(dc){
			DecimalFormat decimalformat = null;
			String rtn = "";
			double d = Double.valueOf(text).doubleValue();

			if (type == 0 )
				decimalformat = new DecimalFormat("###,###,###,###,###,##0");
			else if(type == 1)
				decimalformat = new DecimalFormat("###,###,###,###,###,##0.0");
			else if(type == 2)
				decimalformat = new DecimalFormat("###,###,###,###,###,##0.00");
			else if(type == 3)
				decimalformat = new DecimalFormat("###,###,###,###,###,##0.000");
			else if(type == 4)
				decimalformat = new DecimalFormat("###,###,###,###,###,##0.0000");

			rtn = decimalformat.format(d);
			return rtn;
		}else{
			return text;
		}
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 지역번호 - 국번 - 전화번호 형식으로 바꾸기
    * 기    타 : param   : String arg 전화번호
    *            return  : making telno
    */   
	public static final String TelnoFormat( String arg) {
		String arg1 = "";
		String arg2 = "";
		String arg3 = "";
		String telno = "";
		int arglen = 0;

		if(arg == null){
			telno = "";
		}else{
			arg = replace(arg, " ", "");

			if(arg.length() >= 9 && arg.length() <= 12){  //일반전화

				if(arg.substring(0,2).equals("02")){     //서울지역
					if(arg.length() == 9){
						telno    = arg.substring(0, 2)+"-"+arg.substring(2, 5)+"-"+arg.substring(5, 9);
					}else if(arg.length() == 10){
						telno    = arg.substring(0, 2)+"-"+arg.substring(2, 6)+"-"+arg.substring(6, 10);
					}else{

					}

				}else if(arg.substring(0,4).equals("0505")){    //가상전화
					if(arg.length() == 11){
						telno    = arg.substring(0, 4)+"-"+arg.substring(4, 7)+"-"+arg.substring(7, 11);
					}else if(arg.length() == 12){
						telno    = arg.substring(0, 4)+"-"+arg.substring(4, 8)+"-"+arg.substring(8, 12);
					}else{

					}

				}else{  //기타예외지역
					if(arg.length() == 10){
						telno    = arg.substring(0, 3)+"-"+arg.substring(3, 6)+"-"+arg.substring(6, 10);
					}else if(arg.length() == 11){
						telno    = arg.substring(0, 3)+"-"+arg.substring(3, 7)+"-"+arg.substring(7, 11);
					}
				}

			}else if(arg.length() < 5){ //내선경우
				telno = arg;

			}else if(arg.length() < 9){     //국번없는 8자리 이하..
				telno = arg.substring(0, arg.length()-4)+"-"+arg.substring(arg.length()-4, arg.length());

			}else{  //그이상 복잡한 형태..
				telno = arg.substring(0, arg.length()-8)+"-"+arg.substring(arg.length()-8, arg.length()-4)+"-"+arg.substring(arg.length()-4, arg.length());
			}
		}
		return telno;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 문자열이 숫자로만 구성되었는지 체크
    * 기    타 : param   : String
    *            return  : String
    */   
	public static boolean IsIntegerCheck(String digit){
		boolean rtn = true;    //default
		if(digit.trim().length() > 1){
			for(int i=0; i<digit.trim().length(); i++){
				if(digit.charAt(i) < '0' || digit.charAt(i) > '9'){
					rtn = false;
					break;
				}
			}
		}else{
			rtn = false;
		}

		return rtn;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 소수점이 포함된 숫자 String을 Integer로 변환
    * 기    타 : param   : String
    *            return  : String
    */   
	public static String DecimalFormat(String str) {

		if (str != null && !str.trim().equals("")) {
			StringTokenizer st = new StringTokenizer(str,".");
			String head = "";
			String back = "";

			while(st.hasMoreTokens()){
				head = st.nextToken();
				back = st.nextToken();
			}

			head  = commaFormat(head, 0);
			str = head + "." + back ;

		} else {
			str = "";
		}
			return str;
	}


/***********************************************************************************
   Convert CharacterSet 관련 메소드 모음
************************************************************************************/
   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 한글변환
    * 기    타 : param   : String
    *            return  : 한글로 변환된 string값을 리턴한다
    */   
	public static String enToKo(String en){
		String korean=null;
		try{
			korean = new String(en.getBytes("8859_1"),"KSC5601");
		}catch(Exception e){
			e.printStackTrace();
			return korean;
		}
		return korean;
	}

 
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 한글변환
    * 기    타 : param   : String
    *            return  : 한글로 변환된 string값을 리턴한다
    */   
	public static String koToEn(String ko){
		String english=null;
		try{
			english = new String(ko.getBytes("KSC5601"),"8859_1");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return english;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 웹에서 받은 데이타를 encoding 8859_1 => KSC5601
    * 기    타 : param   : str     browser의 string(8859_1)
    *            return  : KSC5601 type의 String
    */   
	public static String uni2Ksc(String str){
		String retstr = null;

		if(str != null){
			try{
				retstr = new String(str.getBytes("8859_1"), "KSC5601");
			}catch(Exception e){
			}
			return retstr;
		}else{
			return "";
		}
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 데이타를 encoding KSC5601 => 8859_1
    * 기    타 : param   : str     string(KSC5601)
    *            return  : 8859_1 type의 String
    */   
	public static String ksc2Uni(String str){
		String retstr=null;
		if(str != null){
			try{
				retstr = new String(str.getBytes("KSC5601"), "8859_1");
			}catch(Exception e){
			}
			return retstr;
		}else{
			return "";
		}
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : conversion : null --> ""
    * 기    타 : param   : str     전달된 String
    *            return  : 처리된 String
    */   
	public static String NullToBlank(String str){
		if(str == null)  str = "";
		return str;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : conversion : null, "" --> "0"
    * 기    타 : param   : str     전달된 String
    *            return  : 처리된 String
    */   
	public static String NullBlankToZero(String str){
		if(str == null || str.trim().equals(""))  str = "0";
		return str;
	}

   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : db query 시 데이타 특수문자 처리..
    * 기    타 : param   : String
    *            return  : String
    */   
	public static String getQueryCharacter(String str){
		if(str != null && !str.equals("")){
			str = replace(str, "&", "&amp;");
			str = replace(str, "<", "&lt;");
			str = replace(str, ">", "&gt;");
			str = replace(str, "'", "&acute;");
			str = replace(str, "\"", "&quot;");
			str = replace(str, "|", "&brvbar;");
		}
		return str;
	}


   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : db query 데이타를 HTML형식으로..
    * 기    타 : param   : String
    *            return  : String
    */   
	public static String getQueryToHtml(String str){
		if(str != null && !str.equals("")){
			str = replace(str, "&amp;", "&");
			str = replace(str, "&lt;", "<");
			str = replace(str, "&gt;", ">");
			str = replace(str, "&acute;", "'");
			str = replace(str, "&quot;", "\"");
			str = replace(str, "&brvbar;", "|");
		}

		return str;
	}


   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재 페이지 셋팅
    * 기    타 : param   : int
    *            return  : 
    */   
   public void setCurrentPage(int current_page)
   {
      mCurrentPage = current_page;
      System.out.println("mCurrentPage ="+mCurrentPage);
   }
   
   /**    
    * 작 성 자 : 황선헌
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 현재 페이지를 얻는함수
    * 기    타 : param   : 
    *            return  : int
    */   
   public int getCurrentPage()
   {
      if(mCurrentPage <= 0) {
         mCurrentPage = 1;
      }
      System.out.println("getCurrentPage ="+mCurrentPage);
      return mCurrentPage;
   }
   
	/**    
		* 작 성 자 : shhwang
		* 작 성 일 : 2006-03-28 오후 2:13:10
		* 설    명 :  두 날짜 사이의 개월수 계산 
		* 기    타 : param   :  fromdt,todt    전달된 String
		*            return  : 처리된 int
		* 같은년도의 개월수 차이 오류 수정 by 황선헌 : 05.25
	*/   
  public static String getMonthBetween(String fromdt,String todt)  throws java.text.ParseException
  {
	return String.valueOf(monthsBetween(fromdt, todt, "yyyyMMdd"));
		
  }
	 
  /**    
		  * 작 성 자 : shhwang
		  * 작 성 일 : 2006-03-28 오후 2:13:10
		  * 설    명 :  두 날짜 사이의 개월수 계산 
		  * 기    타 : param   :  fromdt,todt    전달된 String
		  *            return  : 처리된 int
		  * 같은년도의 개월수 차이 오류 수정 by 황선헌 : 05.25
	  */    
	public static int monthsBetween(String from, String to, String format) throws java.text.ParseException 
	{
		// 형식 지정
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);

		java.util.Date fromDate = check(from, format);
		java.util.Date toDate   = check(to,   format);

		// if two date are same, return 0.
		if (fromDate.compareTo(toDate) == 0) return 0;

		java.text.SimpleDateFormat yearFormat  = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
		java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM",   java.util.Locale.KOREA);
		java.text.SimpleDateFormat dayFormat   = new java.text.SimpleDateFormat("dd",   java.util.Locale.KOREA);

		int fromYear  = Integer.parseInt(yearFormat.format(fromDate));
		int toYear    = Integer.parseInt(yearFormat.format(toDate));
		int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
		int toMonth   = Integer.parseInt(monthFormat.format(toDate));
		int fromDay   = Integer.parseInt(dayFormat.format(fromDate));
		int toDay     = Integer.parseInt(dayFormat.format(toDate));

		int result = 0;
		// 년
		result += ((toYear - fromYear) * 12);
		// 월
		result += (toMonth - fromMonth);
		// 일
		if (((toDay - fromDay) > 0) ) result += toDate.compareTo(fromDate);

		return result;
	}
	
	/**    
		* 작 성 자 : shhwang
		* 작 성 일 : 2006-03-28 오후 2:13:10
		* 설    명 :  날짜검사 
		* 기    타 : param   :  fromdt,todt    전달된 String
		*            return  : 처리된 int
		* 같은년도의 개월수 차이 오류 수정 by 황선헌 : 05.25
	*/   
	public static java.util.Date check(String s) throws java.text.ParseException {
		return check(s, "yyyyMMdd");
	}
	
	/**    
		* 작 성 자 : shhwang
		* 작 성 일 : 2006-03-28 오후 2:13:10
		* 설    명 : 일자검사 
		* 기    타 : param   :  fromdt,todt    전달된 String
		*            return  : 처리된 int
		* 같은년도의 개월수 차이 오류 수정 by 황선헌 : 05.25
	*/   
	public static java.util.Date check(String s, String format) throws java.text.ParseException 
	{
		// 파라메터 검사
		if ( s == null )
		{
			throw new java.text.ParseException("date string to check is null", 0);
		}
		if ( format == null )
		{
			throw new java.text.ParseException("format string to check date is null", 0);
		}
	
		// 날짜 형식 지정
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);

		// 검사
		java.util.Date date = null;
	
		try 
		{
			date = formatter.parse(s);
		}
		catch(java.text.ParseException e) 
		{
			throw new java.text.ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
		}

		// 날짜 포멧이 틀린 경우
		if ( !formatter.format(date).equals(s))
		{
			throw new java.text.ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"",0);
		}
	
		// 리턴
		return date;
	}
		
	/**    
			* 작 성 자 : shhwang
			* 작 성 일 : 2006-04-01 오후 2:13:10
			* 설    명 :  해당월에 대한 일 콤보 만들기 
			* 기    타 : param   :   gubun 이 Y 이면 마지막 날짜를 99로 한다. 
			*            return  : 처리된 StringBuffer
			*/   	
	public static StringBuffer makeDateCombo(String gubun) {
		  
			StringBuffer dateOption = new StringBuffer();
			int dd = 0;
			int zz = 0;
			String xx = "";
			String selected = "";
                
			int year = 0;
			int month = 0;
			int day = 0;
        
			Calendar cal = Calendar.getInstance();
        
			dd = cal.get(Calendar.DATE);
        
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH)+1;

			switch(month)
			{
				case 1: day = 31; break;
				case 3: day = 31; break;
				case 4: day = 30; break;
				case 5: day = 31; break;
				case 6: day = 30; break;
				case 7: day = 31; break;
				case 8: day = 31; break;
				case 9: day = 30; break;
				case 10: day = 31; break;
				case 11: day = 30; break;
				case 12:  day = 31; break;
				case 2: if ((year % 4) == 0) {
							if ((year % 100) == 0 && (year % 400) != 0) { day = 28; }
							else { day = 29; }
						} else { day = 28; }
						break;
				default: day = 30;
			}        
        
			zz = day;
        
			for (int i=1; i<zz+1; i++) {
				if (i == dd) {
					if(gubun != "Y"){
						selected = "selected";
					}
					
				} else {
					selected = "";
				}
				            
				if (i < 10) {
					xx = "0" + Integer.toString(i);
				} else {
					xx = Integer.toString(i);
				}
            if(gubun == "Y" && i == zz ){
            	xx = "99";
					dateOption.append("<option value='"+xx+"'>"+xx+"</option>\n"); 
            }else{
					dateOption.append("<option value='"+xx+"' "+selected+">"+xx+"</option>\n"); 
            }
				
			}

			//결과반환
			return dateOption;
		}	
   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-03-13 오후 2:13:10
    * 설    명 : 입력한 날짜 기준으로  몇월  전,후
    * 기    타 : param   : String date (19991002) , int 증감일수
    *            return  : String YYYY-MM-DD
    */    
   public static String addMonthPrevNext(String yyyymmdd, int mon, String del) {
      try{
         yyyymmdd = replace(yyyymmdd, "-", "").trim();

         if(yyyymmdd.length() != 8){
            return yyyymmdd;
         }else{
            int Year  = Integer.parseInt(yyyymmdd.substring(0,4));
            int Month = Integer.parseInt(yyyymmdd.substring(4,6));
            int Dates = Integer.parseInt(yyyymmdd.substring(6,8));

            Calendar rightNow = Calendar.getInstance(TimeZone.getTimeZone("JST"));
            rightNow.set(Year, Month-1, Dates);
            rightNow.setTime(new java.util.Date(rightNow.getTime().getTime() - 86400000));

            Date date = rightNow.getTime();
            rightNow.add(Calendar.MONTH, mon);
            date=rightNow.getTime();
            yyyymmdd = int2s(rightNow.get(Calendar.YEAR), 4) + del + int2s(rightNow.get(Calendar.MONTH) + 1, 2) + del + int2s(rightNow.get(Calendar.DATE), 2);

            return yyyymmdd;
         }
      }catch(Exception e){
         return yyyymmdd;
      }
   }
   
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-05-10 오후 2:12:10
    * 설    명 : 입력한 월을 기준으로 그달의 마지막 일자를 구함
    * 기    타 : param   : String date (199910)
    *            return  : String 31
    */    
   public static String getMonthPerDay(String yyyymm) {

      int year = 0;
      int month = 0;
      String day = "";     
      
      if(yyyymm.length()==6){  
         year = Integer.parseInt(yyyymm.substring(0,4));
         month = Integer.parseInt(yyyymm.substring(4));

         switch(month)
         {
            case 1: day = "31"; break;
            case 3: day = "31"; break;
            case 4: day = "30"; break;
            case 5: day = "31"; break;
            case 6: day = "30"; break;
            case 7: day = "31"; break;
            case 8: day = "31"; break;
            case 9: day = "30"; break;
            case 10: day = "31"; break;
            case 11: day = "30"; break;
            case 12:  day = "31"; break;
            case 2: if ((year % 4) == 0) {
                        if ((year % 100) == 0 && (year % 400) != 0) { day = "28"; }
                        else { day = "29"; }
                     } else { day = "28"; }
                     break;
            default: day = "30";
         }
      }else{
         day="";    
      }
      //결과반환
      return day;
   } 
   /**    
    * 작 성 자 : moono
    * 작 성 일 : 2006-05-10 오후 2:12:10
    * 설    명 : 현재월을 기준으로 현재월의 마지막 일자를 구함
    * 기    타 : param   : 
    *            return  : String 31
    */    
   public static String getCurMonthPerDay() {

      int year = 0;
      int month = 0;
      String day = "";   
      
      Calendar cal   = Calendar.getInstance();        
      year           = cal.get(Calendar.YEAR);
      month          = cal.get(Calendar.MONTH)+1;        

      switch(month)
      {
         case 1: day = "31"; break;
         case 3: day = "31"; break;
         case 4: day = "30"; break;
         case 5: day = "31"; break;
         case 6: day = "30"; break;
         case 7: day = "31"; break;
         case 8: day = "31"; break;
         case 9: day = "30"; break;
         case 10: day = "31"; break;
         case 11: day = "30"; break;
         case 12:  day = "31"; break;
         case 2: if ((year % 4) == 0) {
                     if ((year % 100) == 0 && (year % 400) != 0) { day = "28"; }
                     else { day = "29"; }
                  } else { day = "28"; }
                  break;
         default: day = "30";
      }
      
      //결과반환
      return day;
   }     
   
	/**    
		* 작 성 자 : shhwang
		* 작 성 일 : 2006-05-24
		* 설    명 :입력한 날짜 기준으로 몇일 전,후
		* 기    타 : param   : yyyymmdd, days,gubun
		*            return  : String yyyymmdd
		*/    
		 public static String getDatePrevNext(String yyyymmdd, int days,String gubun) {
			  try{
					yyyymmdd = replace(yyyymmdd, "-", "").trim();

					if(yyyymmdd.length() != 8){
						 return yyyymmdd;
					}else{
						 int Year  = Integer.parseInt(yyyymmdd.substring(0,4));
						 int Month = Integer.parseInt(yyyymmdd.substring(4,6));
						 int Dates = Integer.parseInt(yyyymmdd.substring(6,8));

						 Calendar rightNow = Calendar.getInstance(TimeZone.getTimeZone("JST"));
						 rightNow.set(Year, Month-1, Dates);
						 rightNow.setTime(new java.util.Date(rightNow.getTime().getTime() - 86400000));

						 Date date = rightNow.getTime();
						 rightNow.add(rightNow.DATE, days+1);//꼭 있어야 됨 
						 date=rightNow.getTime();
						 yyyymmdd = int2s(rightNow.get(Calendar.YEAR), 4) + gubun + int2s(rightNow.get(Calendar.MONTH) + 1, 2) + gubun + int2s(rightNow.get(Calendar.DATE), 2);

						 return yyyymmdd;
					}
			  }catch(Exception e){
					return yyyymmdd;
			  }
		 }  
       
         
   /**    
      * 작 성 자 : mhcho
      * 작 성 일 : 2006-07-01
      * 설    명 : 입력한 문자열을 byte단위로 잘라서 리턴
      * 기    타 : param   : str, end
      *            return  : String yyyymmdd
      *            ex) String aaa="asbcd가나다" => cropByte(aaa, 7)  => aaa="abcd가"
      */    
   
   public static String cropByte(String str, int end) { 
      if (str == null) 
         return ""; 
      String tmp = str; 
      try { 
         if (tmp.getBytes("MS949").length > end) { 
            int slen = 0, blen = 0; 
            char c; 
            while (blen + 1 <= end) { 
               c = tmp.charAt(slen); 
               blen++; 
               slen++; 
               if (c > 127) 
                  blen++; //2-byte character.. 
            } 
            tmp = tmp.substring(0, slen); 

         } 
      } catch (Exception e) { 
         return ""; 
      } 
      return tmp; 
   } 
   
   /**    
      * 작 성 자 : mhcho
      * 작 성 일 : 2006-07-01
      * 설    명 : 입력한 문자열을 byte단위로 잘라서 리턴
      * 기    타 : param   : str, end
      *            return  : String yyyymmdd
      *            ex) String aaa="asbcd가나다" => cropByte(aaa, 7)  => aaa="abcd가"
      */    
   
   public static String substringByte(String str, int start, int end) { 
      if (str == null) 
         return ""; 
      String tmp = str; 
      
      System.out.println("tmp::::::::::::::::::::"+tmp);
      
      try { 
         System.out.println("tmp.getBytes().length::::::::::::::::::::"+tmp.getBytes("MS949").length);
         if (tmp.getBytes("MS949").length >= start) {
            if(tmp.getBytes("MS949").length <= end)  end = tmp.getBytes("MS949").length; 
            int slen = start, blen = start; 
            char c; 
            while (blen + 1 <= end) { 
               c = tmp.charAt(slen); 
               blen++; 
               slen++; 
               if (c > 127) 
                  blen++; //2-byte character.. 
            } 
            tmp = tmp.substring(start, slen); 

         }else{
            tmp   = ""; 
         }
      } catch (Exception e) { 
         return ""; 
      } 
      return tmp; 
   } 
   
   /**    
      * 작 성 자 : mhcho
      * 작 성 일 : 2006-07-01
      * 설    명 : 입력한 문자열을 잘라서 리턴
      * 기    타 : param   : str, end
      *            return  : String yyyymmdd
      *            ex) String aaa="asbcd가나다" => cropByte(aaa, 7)  => aaa="abcd가"
      */    
   
   public static String utilSubstring(String str, int start, int end) { 
      if (str == null) 
         return ""; 
      String tmp = str; 
      try {
         if (tmp.length() >= start) {
            if(tmp.length() <= end)  end = tmp.length(); 
            int slen = start, blen = start; 
            char c; 
            while (blen + 1 <= end) { 
               c = tmp.charAt(slen); 
               blen++; 
               slen++; 
               if (c > 127) 
                  blen++; //2-byte character.. 
            } 
            tmp = tmp.substring(start, slen); 

         }else{
            tmp   = ""; 
         }
      } catch (Exception e) { 
         return ""; 
      } 
      return tmp; 
   }    
   
   /**
	* 반각 문자를 전각 문자로 변환
	* @return java.lang.String
	* @param tStr java.lang.String
	* @param tMax java.lang.int
	*/
   public static String conv1Byte(String tStr) {
	   if ( tStr == null ) return null;
	
	   StringBuffer Str = new StringBuffer();
			
	   int len = tStr.trim().length();
	   int j;

	   for(int i = 0; i < len; i++)
	   {
		   j = (int)tStr.charAt(i);
		
		   if (j == 12288)                   // 2BYTE 공백 
		   {
			   Str.append(" ");
		   } 
		   else if (j > 65248)               //  전각문자를 반각문자로
		   {
			   Str.append((char)(j-65248));
		   }
		   else                              // 전각문자 
		   {
			   Str.append((char)j);
		   }
	   }

	   return Str.toString();
   }
   /**
	* 반각 문자를 전각 문자로 변환
	* @return java.lang.String
	* @param tStr java.lang.String
	* @param tMax java.lang.int
	*/
   public static String conv2Byte(String tStr, int tMax) {
	   // 최대 문자 갯수가 0 보다 작으면 리턴
	   if (tMax <= 0) return null;
		
	   byte [] byStr = new byte[20];
	
	   StringBuffer Str = new StringBuffer();
			
	   int len = tStr.trim().length();
	   int j;

	   for(int i = 0; i < len; i++)
	   {
		   j = (int)tStr.charAt(i);
		
		   // 전각문자
		   if (j > 128) 
		   {
			   Str.append((char)j);
		   } 
		   //  반각 문자 공백을 전각문자 공백으로 처리
		   //  이렇게 하지 않을 경우 공백 전 문자가 깨짐.
		   else if (j == 32) 
		   {
			   Str.append("　");
		   }
		   // 반각문자를 전각문자로
		   else 
		   {
			   byStr[0]=(byte)163;
			   byStr[1]=(byte)(j + 128);
			   Str.append(new String(byStr,0,2));
		   }
		
		   --tMax;
		   if(tMax <= 0)
		   {
			   break;
		   }
	   }

	   return Str.toString();
   }
   /**
	* 반각 문자를 전각 문자로 변환
	* @return java.lang.String
	* @param tStr java.lang.String : 원본 
	* @param tMax java.lang.int    : 최대 저장 전각문자수(한글도 1문자로 계산)
	* @param bLen java.lang.int    : 전각문자 공백으로 채워야되는 크기
	＊
	＊　tMax와 bLen의 크기는 전각(DBCS) 문자수
	*/
   public static String conv2Byte(String tStr, int tMax, int bLen) {
	   StringBuffer sb = new StringBuffer();

	   sb.append(tStr);

	   for(int i=tStr.length(); i < bLen; i++)
	   {
		   sb.append("　");
	   }

	   return conv2Byte(sb.toString(),tMax);
   }
   
   /**    
      * 작 성 자 : mhcho
      * 작 성 일 : 2006-08-14
      * 설    명 : 입력한 문자열의 바이트수 리턴
      * 기    타 : param   : str
      *            return  : 
      */    
   public static int getStrBytes(String str){
      int l = 0;
      for (int i=0; i<str.length(); i++) l += (str.charAt(i) > 128) ? 2 : 1;
      return l;   
   }   
       
   /**    
		* 작 성 자 : shhwang
		* 작 성 일 : 2006-08-18
		* 설    명 : strValue 문자열에서 개행문자를 제거한 문자열 반환
		* 기    타 : param   : str
		*            return  : 
		*/    
   public static String removeEnter(String strValue){
	String returnStr="";
	for(int i=0;i<strValue.length();i++){
			if(strValue.charAt(i)!=13 && strValue.charAt(i)!=10){
				returnStr=returnStr+strValue.substring(i,1);
			}
		}
		return returnStr;
	 }                     

   /**    
	* 작 성 자 : sshong
	* 작 성 일 : 2010-01-12
	* 설    명 : source에서 target으로의 파일 복사  
	* 기    타 :  
	*  @param source 복사할 파일명을 포함한 절대 경로   
	*  @param target 복사될 파일명을 포함한 절대경로  
	*  */ 

	public static void filecopy(String source, String target) {  //복사 대상이 되는 파일 생성   
		File sourceFile = new File( source ); //스트림, 채널 선언  
		FileInputStream inputStream = null;  
		FileOutputStream outputStream = null;  
		FileChannel fcin = null;  
		FileChannel fcout = null; 
		
		try {   //스트림 생성   
			inputStream = new FileInputStream(sourceFile);   
			outputStream = new FileOutputStream(target);   //채널 생성   
			fcin = inputStream.getChannel();   
			fcout = outputStream.getChannel();      //채널을 통한 스트림 전송   
			long size = fcin.size();   
			fcin.transferTo(0, size, fcout); 
		} catch (Exception e) {   
			e.printStackTrace();  
		} finally {   //자원 해제   
			try{    
				fcout.close();   
			}catch(IOException ioe){}   
			try{    
				fcin.close();   
			}catch(IOException ioe){}   
			try{    
				outputStream.close();   
			}catch(IOException ioe){}   
			try{    
				inputStream.close();   
			}catch(IOException ioe){}  
		}
	}
	
	
   //메소드 테스트
	
	public static void main(String args[]){
		/*
		System.out.println(CommonUtil.diffDate("20070201","20060526"));
		
	
		System.out.println("getMonthFirstDay="+getMonthFirstDay());
		System.out.println("getMonthLastDay="+getMonthLastDay());
		System.out.println("getLastDay="+getLastDay("200402"));
		System.out.println("getLastDate="+getLastDate("200403"));
		System.out.println("getDate="+getDate());
		System.out.println("getYearMD="+getYearMD("2003-02-03"));
		System.out.println("getStrToDate="+getStrToDate("20030204101010", DEFAULT_DATE_FORMAT));
		System.out.println("getStrToDateDefault="+getStrToDateDefault("2003-02-04 10:10:10"));
		System.out.println("getPrevDate="+getPrevDate(5));
		System.out.println("getNextDate="+getNextDate(17));
		System.out.println("getDayName="+getDayName());
		System.out.println("addDatePrevNext="+addDatePrevNext("20040202", -1));
		System.out.println("addWeeks(n)="+addWeeks(1));
		String days[] = {"1", "2"};
		System.out.println("getDateBetweenInDAY_OF_WEEK="+getDateBetweenInDAY_OF_WEEK("20040201", "20040210", days));
		System.out.println("replaceSpecial="+replaceSpecial("안녕하세요\"다음"));
		String arrays[] = null;
		arrays = getTokenStr("안녕하세요/반갑습니다/그럼/ /", "/");
		System.out.println("getTokenStr="+arrays[0]+"///"+arrays[1]+"///"+arrays[2]);
		System.out.println("replaceAll="+replaceIdxFrom("aaaaaa", 3, "a", "b"));
		System.out.println("dFormat int="+dFormat(-1000));
		System.out.println("dFormat String="+dFormat("1234556"));
		System.out.println("fFormat ="+fFormat("234555"));
		System.out.println("fFormat ="+fFormat("234555.3", 3));
		System.out.println("DoubleTypeRound="+DoubleTypeRound("123.0", 2));
		//System.out.println("strSum="+strSum("12,43,34a"));
		System.out.println("TokenSum="+TokenSum("12,43,3a4", ","));
		*/
		
	}
}
