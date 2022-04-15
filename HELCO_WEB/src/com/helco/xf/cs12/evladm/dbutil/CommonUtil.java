/**
 * �� �� �� : ���� ��ƿ Ŭ����
 * �� �� �� : moono
 * �� �� �� : 2006-02-17
 * ��    �� : 
 * ���泻�� :
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
   * No.     �޼ҵ��                                    �޼ҵ弳��
   *  --------------------------------------  ----------------------------------------------
   * 1       int2s                               		��¥��ȯ�� ������ ���
   * 2       addWeeks                      				���ó�¥ + n ��
   * 3       addMonthDate                     			���ó�¥�� �� + n �� = YYYY-MM-DD
   * 4       getCurrentTimeHHMM                       ����ð� HH:MM
   * 5       getCurrentTimeHHMMSS                     ����ð� HH:MM:SS
   * 6       getToday                                 ���糯¥�� YYYY-MM-DD
   * 7       getToday2                                ���糯¥�� YYYYMMDD
   * 8       getMonthFirstDay                         ������� ù°�� YYYY-MM-DD
   * 9       getMonthLastDay                          ������� �������� YYYY-MM-DD (���ް���)
   * 10      getLastDay                               �ش���� �ִ�����
   * 11      getLastDate                              �ش���� ��������¥ YYYY-MM-DD (���ް���)
   * 12      getYear                                  ���� �⵵ YYYY
   * 13      getMonth                                 ������ �� MM
   * 14      getDate                                  ������ �� DD
   * 15      getYearMD                                YYYYMMDD or YYYY-MM-DD �� YYYY�� MM�� DD�� ���·� ��ȯ
   * 16      getFormattedDate                         Date�� timezone�� format�� ���� ��¥ String���� ��ȯ�ؼ� ����
   * 17      getStrToDate                             String�� Date�������� String = "20030204101010" or "2003-02-04 10:10:10"
   * 18      getStrToDateDefault                      String�� Date�������� default���� ���
   * 19      strToYMHS                                ��¥��yyyymmddhhmmss ������ yyyy-mm-dd hh:mm:ss�������� ��ȯ
   * 20      strToCal                                 ��¥ YYYYMMDD�� Ư�� �����ڷ� �����Ͽ� ����
   * 21      strTimeFormat                            HHmmss�� HH:mm:ss�� ��ȯ
   * 22      strTimeUnFormat                          HH:mm:ss�� HHmmss�� ��ȯ
   * 23      SecToDateFormat                          int ����(�ʴ���)�� X����X��X�ð�X��X�� ������ �ٲٱ�
   * 24      getSysDateTime                           ������ YYYY-MM-DD HH:mm:ss
   * 25      getSysDateTimeMsec                       ������ YYYY-MM-DD HH:mm:ss.sss
   * 26      getPrevDate                              �����Ϸκ��� �������� ��¥..YYYY-MM-DD
   * 27      getNextDate                              �����Ϸκ��� ���� ���� ��¥ YYYY-MM-DD
   * 28      getDayName                               ������ ���� �ε��� ���� 1,2,3,,,7
   * 29      getDayNameKor                            ������ ���ϸ� ���� ������, ȭ����, ������...
   * 30      diffDate                                 ���� �� ��¥�� ����
   * 31      addDatePrevNext                          �Է��� ��¥ �������� ���� ��,��
   * 32      getDateBetweenInDAY_OF_WEEK              Ư�� ��¥ ������ ���ϸ� ���ϱ�..
   * 33      convertHtmlBr                            String ������ �޾Ƽ� Html �������� ��ȯ
   * 34      replaceSpecial                           ��Ʈ������ Ư�����ڸ� \�� ���� HTML�������� ��ȯ
   * 35      replaceEnterBr                           string�� <enter>���� html tag <br>�� ���δ�
   * 36      getSpecialChar                           HTML�� �����Ͽ� �Ϻ� Ư�����ڸ� ��ȯ
   * 37      getPageList                              ������ �׺���̼� �����ϱ�
   * 38      getTotalPage                             �������� ���ϱ�
   * 39      myIndexList                              ����¡ ����
   * 40      myIndexList2                             ����¡ ����2
   * 41      drawScroller                             ����¡ ����3
   * 42      pagingIndex                              paging ��  �Ѱ��� index���� ���
   * 43      replace                                  Ư�����ڸ� �ٸ� ���ڷ� ��ȯ
   * 44      nullToBlank                              �־��� ���ڿ� str��  null �Ǵ� "null" �̸� ������ ����Ѵ�.
   * 45      strAddLoop                               len �� ��ŭ ��Ʈ�� ä���ֱ�
   * 46      fillString                               ��Ʈ���� Ư�� ���� ��ŭ �յڷ� ���̱�
   * 47      nullToString                             null �Ǵ� "" �̸� Ư���� ���ڿ��� ��ġ
   * 48      cutString                                ��Ʈ���� ������ �����̻��϶� ".."�� �ٿ��� �߶���
   * 49      countChar                                ���ڿ����� Ư�������� ���� ������ ���Ѵ�.
   * 50      removeChar                               String���� Ư�����ڸ� ���ش�
   * 51      removeSpace                              String���� ���������� SPACE�� ���ش�
   * 52      getStreamToString                        InputStream �� �����͸� �ѱ� String ������ ��ȯ
   * 53      allZeroToStr                             '0'���� ��� ä���� ���ڿ��� Ư�����ڷ� �ٲپ���
   * 54      getTokenStr                              �����ڿ� ���� ���ڿ� �и� StringToken�̿�
   * 55      replaceIdxFrom                           �����ε��� ���� ���ڿ� replace �޼ҵ�
   * 56      addFillLeft                              ���ڿ��� ���ʺκп� Ư�����ڸ� �߰��ؼ� ���ϴ� ���̷� �����
   * 57      addFillRight                             ���ڿ��� �����ʺκп� Ư�����ڸ� �߰��ؼ� ���ϴ� ���̷� �����
   * 58      getMSG                                   �޽��� ���� CommonMSG.properties���Ͽ��� �ش� Ű������ �޽��� �б�
   * 59      MakeQuery                                ���� ���Ͽ��� ������ �б�
   * 60      MakeQueryNP                              ���� ���Ͽ��� ������ �б� No Parameter
   * 61      Round                                    ������ �޾Ƽ� �ݿø�(5���� ����, 5�̻� �ø�)
   * 62      Round                                    double�� �޾Ƽ� �ݿø�(5���� ����, 5�̻� �ø�)
   * 63      strResnoFormat                           �ֹι�ȣ 1234561234567�� '-'�ֱ�
   * 64      strBizFormat                             ����ڹ�ȣ�� '-'�ֱ�
   * 65      PostnoFormat                             �����ȣ�� '-'�ֱ�
   * 66      setFrm                                   ����, �ܰ�, �ݾ� ���� ����(double ��)
   * 67      setFrmZero                               ����, �ܰ�, �ݾ� ���� ���� 0���� ��ü (double ��)
   * 68      dFormat                                  int�� ���ڸ� 3�ڸ� ���� �޸����
   * 69      dFormat                                  long�� ���ڸ� 3�ڸ� ���� �޸����
   * 70      dFormat                                  double�� ���ڸ� 3�ڸ� ���� �޸����
   * 71      dFormat                                  String�� ���ڸ� 3�ڸ� ���� �޸����
   * 72      fFormat                                  String ���� #,###,###,##0.#0 �������� �ٲ�
   * 73      fFormat                                  ���� ���� #,###,###,##0.#0 �������� �Ҽ������� �ٲ�
   * 74      StringToFloat                            #.# �� ���� string ���� #.# float ������ ��ȯ
   * 75      setZero                                  ������ i_length��ŭ 0�� �߰�
   * 76      DoubleTypeRound                          Double���� ���ڸ� �Ҽ� i�ڸ����� �ݿø��� �Ѵ�.
   * 77      getSubstring                             ������� Zero String�� �����ϴ� substring �޼ҵ�
   * 78      makeIntToKorCurr                         ���ڷε� �ݾ��� ���ڷ� ǥ���� - ��õ��
   * 79      makeIntToNumKorCurr                      ���ڷε� �ݾ��� ���ڷ� ǥ���� - 3õ��
   * 80      BigDecimalAdd                            �� ���ڿ��� BigDecimal Type���� ���������ϰ� ������� ���ڿ��� ��ȯ
   * 81      BigDecimalSubtract                       �� ���ڿ��� BigDecimal Type���� ���������ϰ� ������� ���ڿ��� ��ȯ
   * 82      BigDecimalMultiply                       �� ���ڿ��� BigDecimal Type���� ���������ϰ� ������� ���ڿ��� ��ȯ
   * 83      BigDecimalDivide                         �� ���ڿ��� BigDecimal Type����  �����⿬���ϰ� ������� ���ڿ��� ��ȯ
   * 84      BigDecimalDivide                         �� ���ڿ��� BigDecimal Type����  �����⿬���ϰ� ������� ���ڿ��� ��ȯ, �ݿø������̿�
   * 85      BigDecimalCompare                        �� ���� ���ڿ��� BigDecimal Type���� ���ϰ� ������� int������ ��ȯ
   * 86      TokenSum                                 ���� token���� �и��� String�� ���� ���Ѵ�.
   * 87      commaFormat                              ���ڷ� ������ ���ڿ��� �ڸ��� ���� , �ֱ� �� type����
   * 88      TelnoFormat                              ������ȣ - ���� - ��ȭ��ȣ �������� �ٲٱ�
   * 89      IsIntegerCheck                           ���ڿ��� ���ڷθ� �����Ǿ����� üũ
   * 90      DecimalFormat                            �Ҽ����� ���Ե� ���� String�� Integer�� ��ȯ
   * 91      enToKo                                   �ѱۺ�ȯ 8859_1 - KSC5601
   * 92      koToEn                                   �ѱۺ�ȯ KSC5601 - 8859_1
   * 93      uni2Ksc                                  ������ ���� ����Ÿ�� encoding 8859_1 => KSC5601
   * 94      ksc2Uni                                  ����Ÿ�� encoding KSC5601 => 8859_1
   * 95      NullToBlank                              null - ""
   * 96      NullBlankToZero                          null, "" - "0"
   * 97      getQueryCharacter                        db query �� ����Ÿ Ư������ ó��..
   * 98      getQueryToHtml                           db query ����Ÿ�� HTML��������..
   * 99      setCurrentPage(int current_page)         ���� ������ ���� 
   * 100     getCurrentPage()								   ���������� ��� 
   * 101    getMonthBetween(String fromdt,String todt) �� ��¥ ������ ������ ��� 
   * 102    makeDateCombo()                           �� �޺� ����� 
   * 103    addMonthPrevNext                          �Է��� ��¥ �������� ���� ��,��
   * 104    getMonthPerDay                            �Է��� ���� �������� �״��� ������ ���ڸ� ����
   * 105    getCurMonthPerDay                         ������� �������� ������� ������ ���ڸ� ����
   * 106    getDatePrevNext                           �Է��� ��¥�� �������� ���� ���� + ��¥���� ����
   * 107    cropByte(String str, int end)             �Է��� ���ڿ��� byte������ �߶� ���� 
   * 108    getStrBytes(String str)                   �Է��� ���ڿ��� byte�� ����
   * 109    removeEnter(String strValue)              strValue ���ڿ����� ���๮�ڸ� ������ ���ڿ� ��ȯ
   * 110    filecopy(String source, String target)    source���� target������ ���� ����   
   */


public class CommonUtil {

	protected int mCurrentPage = 0;  // ���� ������
	
	//�⺻ ��¥ ����
	public final static String DEFAULT_DATE_FORMAT  = "yyyyMMddHHmmss";

	//�Ϲ� ��¥ ���� (KICC ��¥ ����)
	public final static String GENERAL_DATE_FORMAT  = "yyyyMMdd";
	//�⺻ TimeZone
	public final static String DEFAULT_TIMEZONE     = "JST";

	private static String QUERYROOT = null;


	public CommonUtil(){
	}



	/***********************************************************************************
	  ��¥ & �ð� ���� �޼ҵ� ����
	************************************************************************************/
    
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : getCurrentTime���� ȣ��
    *            Calendar���� �Ű����� �����ŭ �ڸ���
    * ��    Ÿ :
    */ 
	public static String int2s(int num, int size){
		String formatStr = "";
		for ( int iii = 0; iii < size; iii ++ )
			formatStr += "0";

		java.text.DecimalFormat  df  =  new  java.text.DecimalFormat(formatStr);

		return df.format((new Integer(num)).longValue());
	}


   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : date�� �������� n�ָ� ���� ��¥�� �����Ѵ�.
    * ��    Ÿ : IllegalArgumentException date�� null�̸� �߻�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : date�� �������� ���� ������ ��¥�� �����Ѵ�.
    * ��    Ÿ : return : ���ó�¥�� �� + n �� = YYYY-MM-DD
    *            IllegalArgumentException date�� null�̸� �߻�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����ð� HH:MM �� ���Ѵ�.
    * ��    Ÿ : return : ����ð� HH:MM
    */ 
	public static String getCurrentTimeHHMM() {
		String currentTimeHHMM = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		currentTimeHHMM = int2s(dt.get(Calendar.HOUR_OF_DAY), 2) + ":" + int2s(dt.get(Calendar.MINUTE), 2);

		return currentTimeHHMM;
	}

   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����ð� HH:MM:SS �� ���Ѵ�.
    * ��    Ÿ : return : ����ð� HH:MM:SS
    */ 
	public static String getCurrentTimeHHMMSS() {
		String currentTimeHHMMSS = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		currentTimeHHMMSS = int2s(dt.get(Calendar.HOUR_OF_DAY), 2) + ":" + int2s(dt.get(Calendar.MINUTE), 2) + ":" + int2s(dt.get(Calendar.SECOND), 2);

		return currentTimeHHMMSS;
	}
   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ ���Ѵ�.
    * ��    Ÿ : return : ���糯¥�� YYYY-MM-DD
    */ 
	public static String getToday(){
		String yyyymmdd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		yyyymmdd = int2s(dt.get(Calendar.YEAR), 4) + "-" + int2s(dt.get(Calendar.MONTH) + 1, 2) + "-" + int2s(dt.get(Calendar.DATE), 2);

		return yyyymmdd;
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ ���Ѵ�. (������ ������ 8�ڸ�)
    * ��    Ÿ : return : ���糯¥�� YYYYMMDD
    */ 
	public static String getToday2(){
		String yyyymmdd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		yyyymmdd = int2s(dt.get(Calendar.YEAR), 4) + int2s(dt.get(Calendar.MONTH) + 1, 2) + int2s(dt.get(Calendar.DATE), 2);

		return yyyymmdd;
	}


   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����� ù��°���� ���Ѵ�.
    * ��    Ÿ : return : ������� ù°�� YYYY-MM-DD
    */ 
	public static String getMonthFirstDay(){
		String yyyymmdd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		int MonthFirstDay = dt.get(Calendar.DATE)-(dt.get(Calendar.DATE)-1);
		yyyymmdd = int2s(dt.get(Calendar.YEAR), 4) + "-" + int2s(dt.get(Calendar.MONTH) + 1, 2) + "-" + int2s(MonthFirstDay, 2);

		return yyyymmdd;
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����� ���������� ���Ѵ�.
    * ��    Ÿ : return : ������� �������� YYYY-MM-DD
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �� ���� ������ ���ڸ� ���ϱ� (�� [31] ����)
    * ��    Ÿ : return : String
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �ش���� ������ ��¥ ���ϱ� (��� 6�ڸ�[200201])
    * ��    Ÿ : param s_date (YYYYMM ���ؿ�) , return : String (YYYY-MM-DD)
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����� ���Ѵ�.
    * ��    Ÿ : return : ��¥ String�� �����Ѵ�.
    */ 
	public static String getYear(){
		String year = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		year = int2s(dt.get(Calendar.YEAR), 4);
		return year;
	}

    
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����� ���Ѵ�.
    * ��    Ÿ : return : ��¥ String�� �����Ѵ�.
    */ 
	public static String getMonth(){
		String month = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		month = int2s(dt.get(Calendar.MONTH) + 1, 2);
		return month;
	}

    
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ ���� ���Ѵ�.
    * ��    Ÿ : return : String DD
    */ 
	public static String getDate(){
		String dd = "";
		Calendar dt = Calendar.getInstance(TimeZone.getTimeZone("JST"));
		dd = int2s(dt.get(Calendar.DATE), 2);
		return dd;
	}

    
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : YYYYMMDD or YYYY-MM-DD �� YYYY�� MM�� DD�� ���·� ��ȯ
    * ��    Ÿ : return : ��¥ String�� �����Ѵ�. ��ȯ���� error�߻��� null�� �����Ѵ�
    */ 
	public static String getYearMD(String date){
		try {
			date = replace(date.trim(), "-", "");
			if(date.equals("") || date.equals(" ") || date.equals("null")  || date.equals(null))
				return " ";

			java.util.TimeZone homeTz = java.util.TimeZone.getTimeZone("JST");
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy�� MM�� dd��");
			formatter.setTimeZone(homeTz);
			return formatter.format(getStrToDate(date,"yyyyMMdd"));
		} catch (Exception e) {
			return null;
		}
	}

    
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : Date�� timezone�� format�� ���� ��¥ String���� ��ȯ�ؼ� �����Ѵ�.
    * ��    Ÿ : param   : date ��¥ , format ��¥ ���� (��) yyyyMMddHHmmss , timezone Timezone (��) JST
    *            return  : ��¥ String�� �����Ѵ�. ��ȯ���� error�߻��� null�� �����Ѵ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ��¥ String�� Date�� ��ȯ�ؼ� �����Ѵ�.
    * ��    Ÿ : param   : date ��¥ 20030204101010 or 2003-02-04 10:10:10 , format ��¥ ����
    *            return  : Tue Feb 04 10:10:10 KST 2003 ��¥ String�� Date�� ��ȯ�ؼ� �����Ѵ�. ��ȯ���� error�߻��� null�� �����Ѵ�.
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ��¥ String�� Default format Date�� ��ȯ�ؼ� �����Ѵ�. (DEFAULT_DATE_FORMAT�� ���)
    * ��    Ÿ : param   : String 20030204101010 or 2003-02-04 10:10:10
    *            return  : ��¥ String�� Date�� ��ȯ�ؼ� �����Ѵ�. ��ȯ���� error�߻��� null�� �����Ѵ�.
    */ 
	public static Date getStrToDateDefault(String date){
		return getStrToDate(date, DEFAULT_DATE_FORMAT);
	}

    
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ��¥��yyyymmddhhmmss ������ yyyy-mm-dd hh:mm:ss�������� ��ȯ�ؼ� �����Ѵ�.
    * ��    Ÿ : param   : String
    *            return  : ��¥ String�� �����ڸ� �־� �����Ѵ�.
    */    
	public static String strToYMHS(String date){  // ��¥, ������
		if(date == null || date.trim().length() != 14){
			return date;
		}else{
			date = date.trim();
			return date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
		}

	}

    
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ��¥��yyyymmdd ������ yyyy-mm-dd�������� ��ȯ�ؼ� �����Ѵ�.
    * ��    Ÿ : param   : String
    *            return  : ��¥ String�� �����ڸ� �־� �����Ѵ�.
    */    
	public static String strToCal(String day, String div){  // ��¥, ������
		if (day == null)
			day = "";

		if (day.trim().length() == 6)
			day = day.substring(0,4)+ div +day.substring(4,6);

		if (day.trim().length() == 8)
			day = day.substring(0,4)+ div +day.substring(4,6)+ div +day.substring(6,8);

		return day;
	}

    
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : HHMMSS -> HH:MM:SS
    * ��    Ÿ : param   : str_time �ٲ� ���ڿ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : HH:MM:SS -> HHMMSS
    * ��    Ÿ : param   : str_time �ٲ� ���ڿ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : int ����(�ʴ���)�� X����X��X�ð�X��X�� ������ �ٲٱ�
    * ��    Ÿ : param   : int(second)
    *            return  : X����X��X�ð�X��X��
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
			datetime += month + "����";

		if(day !=0)
			  datetime += day + "��";

		if(hour !=0){
			datetime += hour + "�ð�";
		}else{
			datetime += "00�ð�";
		}

		if(minute !=0){
			datetime += minute + "��";
		}else{
			datetime += "00��";
		}

		if(second !=0){
			datetime += second + "��";
		}else{
			datetime += "00��";
		}
		//System.out.println("datetime ===>"+datetime);

		return datetime;
	}


   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �������� + ����ð��� ����
    * ��    Ÿ : param   : 
    *            return  : ������ YYYY-MM-DD HH:mm:ss
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �������� + ����ð��� ����
    * ��    Ÿ : param   : 
    *            return  : �����Ͻ� YYYY-MM-DD HH:mm:ss:sss
    */    
	public static String getSysDateTimeMsec(){
		int mSecond ;

		DecimalFormat dformat = new DecimalFormat("00");
		mSecond   = Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.MILLISECOND);

		return getSysDateTime()+"."+dformat.format(mSecond);
	}


   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �����Ϸκ��� ���� ���� ���ĸ� ����
    * ��    Ÿ : param   : int (������)
    *            return  : String �������� YYYY-MM-DD
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �����Ϸκ��� ���� ���� ���ĸ� ����
    * ��    Ÿ : param   : int (������)
    *            return  : String �������� YYYY-MM-DD
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���� ������ �����Ѵ�.
    * ��    Ÿ : param   : 
    *            return  : String ����(1:�Ͽ���  ... 7:�����)
    */    
	public static int getDayName(){
		return Calendar.getInstance(TimeZone.getTimeZone("JST")).get(Calendar.DAY_OF_WEEK);
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���� ������ �ѱ������� �����Ѵ�.
    * ��    Ÿ : param   : 
    *            return  : ����(�Ͽ���  ...    �����)
    */    
	public static String getDayNameKor(){
		int    li_day  = getDayName();
		String ls_week = "";

		switch (li_day)  {
			case  1: ls_week = "�Ͽ���";
					 break;
			case  2: ls_week = "������";
					 break;
			case  3: ls_week = "ȭ����";
					 break;
			case  4: ls_week = "������";
					 break;
			case  5: ls_week = "�����";
					 break;
			case  6: ls_week = "�ݿ���";
					 break;
			case  7: ls_week = "�����";
					 break;
		}
		return ls_week;
	}

   
	/**    
	 * �� �� �� : shhwang
	 * �� �� �� : 2006-03-13 ���� 2:13:10
	 * ��    �� : ���� �� ��¥�� ����  (��)  diffDate("20000629", "20000630") --> 1   
	 * ��    Ÿ : param   : String      sFirstDate, String      sSecondDate
	 *            return  : String      ��¥�� ���� (sSecondDate - sFirstDate)
	 *     0729 : shhwang ��¥ ��� ���� ���� 
	 */    
	public static String diffDate(String from, String to) throws java.text.ParseException 
	{
		String format = "yyyyMMdd";
		// ��¥ ���� ����
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

		// ��¥ �˻�
		java.util.Date d1 = check(from, format);
		java.util.Date d2 = check(to, format);
	
		long duration = d2.getTime() - d1.getTime();
	
		return String.valueOf((int)( duration/(1000 * 60 * 60 * 24) ));
	}
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �Է��� ��¥ �������� ���� ��,��
    * ��    Ÿ : param   : String date (19991002) , int �����ϼ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : Ư����¥ ������ Ư������ ���ϱ�
    * ��    Ÿ : param   : String, String, String[]
    *            return  : StringBuffer
    */    
	public static StringBuffer getDateBetweenInDAY_OF_WEEK(String fromdate, String todate, String days[]) {
		String tmpdate = "";                        //����� ���� �ӽ� ����
		StringBuffer sb_date = new StringBuffer();  //���� value ��¥
		//StringBuffer sb_days = new StringBuffer();
		int tmpdays = 0;                            //�ش� ��¥�� ���� �ӽú���

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
						//System.out.println("����");
						sb_date.append(strToCal(tmpdate, "-")+"/");    //��¥
						//sb_days.append(tmpdays+"/");
					}
				}

				//tmpdate = moveForward(tmpdate, "1");    //������¥..
				tmpdate = replace(addDatePrevNext(tmpdate, 1), "-", "");    //������¥..
			}

			//System.out.println("���ϳ�¥�迭="+sb_date.toString());
			//System.out.println("���Ͽ��Ϲ迭="+sb_days.toString());

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
	  HTML ���� �޼ҵ� ����
	************************************************************************************/

   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : String ������ �޾Ƽ� Html �������� ��ȯ
    * ��    Ÿ : param   : String
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ��Ʈ������ Ư�����ڸ� \�� ���� HTML�������� ��ȯ
    * ��    Ÿ : param   : String �ٲ� ���ڿ�
    *            return  : String
    */    


   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : string�� <enter>���� html tag <br>�� ���δ�
    * ��    Ÿ : param   : content  string
    *            return  : <br>�� ���� ���ο� string
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : HTML�� �����Ͽ� �Ϻ� Ư�����ڸ� ��ȯ
    * ��    Ÿ : param   : String ��ȯ�� ���ڿ�
    *            return  : String HTML Ư�����ڰ� ��ȯ�� ���ڿ�
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
	  ���������� ���� �޼ҵ����
	************************************************************************************/

   /**    
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ �׺���̼� �����ϱ�
    * ��    Ÿ : param   : ls_currentPage     ���� ������ ��ȣ, ls_totalPage       �� ������ ��, ls_pageStep       ��������Ǽ� 
    *            return  : String             ������ �������� HTML�� �ѱ�
    *             jsp�������� �Ʒ� ��ũ��Ʈ�߰�
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
			int          pageStart    = 1;     // ������ ����
			int          i            = 0;     // For Loop ����
			
			String       ls_fontColor = "#CE7128";
			String       ls_boldColor = "#CE7128";
			String       ls_space     = "";
			StringBuffer sb_page = new StringBuffer("");		

			pageStart = (((ls_currentPage-1)/ls_pageStep)*ls_pageStep) + 1; // ������ ���� ���ϱ�			
			
			//System.out.println("pageStart===> " + pageStart);
			int prev_page = pageStart - 1;
			
			System.out.println("prev_page===> " + prev_page);

			if ( prev_page < 1 ) prev_page = 1;

			sb_page.append("<font color=\"" + ls_fontColor + "\">");			

			if (pageStart > 1){
				//�������� ������ ���� �������� �ٽ� ���÷��� ���ָ� ������ ��ȣ�� ���´�.

				if (pageStart > ls_pageStep) {
					sb_page.append("<a href=\"javascript:jsGoPages(" + 1 + "); \">");
					sb_page.append("<img src=\"/bosu/images/arrow_L.gif\" align=\"absmiddle\" hspace=\"6\" border=\"0\" vspace=\"1\">");
					sb_page.append("</a>");
				}

				sb_page.append("<a href=\"javascript:jsGoPages(" + prev_page +");\">");	
				sb_page.append("���� ");			
				sb_page.append("</a>");		  

			}else{
				sb_page.append("���� ");
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
				sb_page.append(" ����");
			}else{
				sb_page.append("<a href=\"javascript:jsGoPages(" + i +");\"> ����");				
				sb_page.append("</a>");

				sb_page.append("<a href=\"javascript:jsGoPages(" + ls_totalPage + ");\">");
				sb_page.append("<img src=\"/bosu/images/arrow_R.gif\" align=\"absmiddle\" hspace=\"6\" border=\"0\" vspace=\"1\">");
				sb_page.append("</a>");
				
			}
			sb_page.append("</font>");

			return sb_page.toString();
		}
		
   
   /**    
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �������� ���ϱ�
    * ��    Ÿ : param   : total_count     �Խ��� �� �Ǽ� , num_per_page           �������� �Ǽ�
    *            return  : int               ��������
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


	// ������ư URL
	public static final String RIGHT_BTN_URL = "NEXT";

	//������ư URL
	public static final String LEFT_BTN_URL  = "PREV";


   
   /**    
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : POST ��� �� �Խ����� counter(1).
    * ��    Ÿ : param   : int : current_page , int : total_page
    *            return  : myIndexList(false, 10,current_page,total_page, LEFT_BTN_URL, RIGHT_BTN_URL, "goToPage", null) call
    */    
	public static String myIndexList(int current_page, int total_page){
		return myIndexList(false, 10,current_page,total_page, LEFT_BTN_URL, RIGHT_BTN_URL, "goToPage", null);
	}

   
   /**    
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����¡ ����
    * ��    Ÿ : param   : method_type  T/F : get/post
    *                      list_limit   setting number (displayed number)
    *                      current_page
    *                      total_page
    *                      left_image_url
    *                      right_image_url
    *                      callee_url    * 
    *            return  : html �� ��ȯ�Ǿ� return
    */    
   public static String myIndexList(boolean method_type, int list_limit, int current_page, int total_page, String left_image_url, String right_image_url, String callee_url, String font_color){

		int     pagenumber;       // ȭ�鿡 �������� ������ �ε��� ��
		int     startpage;        // ȭ�鿡 �������� ���������� ��ȣ
		int     endpage;          // ȭ�鿡 �������� ������������ ��ȣ
		int     curpage;          // �̵��ϰ��� �ϴ� ������ ��ȣ


		StringBuffer returnList = new StringBuffer();

		//���Ἲ ����
		if(list_limit    < 0)        list_limit      = 0;
		if(current_page  < 0)        current_page    = 0;
		if(total_page    < 0)        total_page      = 0;
		if(left_image_url   == null) left_image_url  = "";
		if(right_image_url  == null) right_image_url = "";
		if(callee_url       == null) callee_url      = "";
		if(font_color       == null) font_color      = "";

		// ���� ��������ȣ ���ϱ�
		startpage = ((current_page - 1) / list_limit) * list_limit + 1;
		// ������ ��������ȣ ���ϱ�
		endpage = (((startpage - 1) +  list_limit) / list_limit) * list_limit;
		// �� ������ ���� ���� ������������ ��ȣ���� �������
		// �� ������ ���� ������������ ��ȣ�� ��
		if (total_page <= endpage){
			endpage = total_page;
		}

		//������ ûũ������ �̵��� ��ư�̹���
		if(current_page > list_limit){
			curpage = startpage - 1;

			if(method_type)
				returnList.append("<a href='"+callee_url+"?page="+curpage+"' onFocus='this.blur()' class='link_pagenum_bg'>&nbsp;&nbsp;");
			else
				returnList.append("<a href='javascript:"+callee_url+"("+curpage+");' onFocus='this.blur()' class='link_pagenum_bg'>");

			returnList.append(left_image_url+"</a>&nbsp;&nbsp;");
		}

		// ���������� ��ȣ���� ������������ ��ȣ���� ȭ�鿡 ǥ��
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


		//���� ûũ������ �̵��� ��Ʈ�̹���
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
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����¡ ����
    * ��    Ÿ : param   : int : viewinpage
    *                      int : current_page
    *                      int : total_page
    *            return  : myIndexList(false, viewinpage, current_page, total_page, LEFT_BTN_URL, RIGHT_BTN_URL, "goToPage", null) call
    */    
	public static String myIndexList2(int viewinpage, int current_page, int total_page){
		return myIndexList2(false, viewinpage, current_page,total_page, LEFT_BTN_URL, RIGHT_BTN_URL, "goToPage", null);
	}

   
   /**    
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����¡ ����
    * ��    Ÿ : param   : method_type  T/F : get/post
    *                      list_limit   setting number (displayed number)
    *                      current_page
    *                      total_page
    *                      left_image_url
    *                      right_image_url
    *                      callee_url
    *            return  : html �� ��ȯ�Ǿ� return
    */    
	public static String myIndexList2(boolean method_type, int list_limit, int current_page, int total_page, String left_image_url, String right_image_url, String callee_url, String font_color){
		int     pagenumber;       // ȭ�鿡 �������� ������ �ε��� ��
		int     startpage;        // ȭ�鿡 �������� ���������� ��ȣ
		int     endpage;          // ȭ�鿡 �������� ������������ ��ȣ
		int     curpage;          // �̵��ϰ��� �ϴ� ������ ��ȣ
		StringBuffer returnList = new StringBuffer();

		//���Ἲ ����
		if(list_limit    < 0)        list_limit      = 0;
		if(current_page  < 0)        current_page    = 0;
		if(total_page    < 0)        total_page      = 0;
		if(left_image_url   == null) left_image_url  = "";
		if(right_image_url  == null) right_image_url = "";
		if(callee_url       == null) callee_url      = "";
		if(font_color       == null) font_color      = "";

		// ���� ��������ȣ ���ϱ�
		startpage = ((current_page - 1) / list_limit) * list_limit + 1;

		// ������ ��������ȣ ���ϱ�
		endpage = (((startpage - 1) +  list_limit) / list_limit) * list_limit;

		// �� ������ ���� ���� ������������ ��ȣ���� �������
		// �� ������ ���� ������������ ��ȣ�� ��
		if(total_page <= endpage){
			endpage = total_page;
		}

		//������ ûũ������ �̵��� ��ư�̹���
		if(current_page > list_limit){
			curpage = startpage - 1;

			if(method_type)
				returnList.append("<a href='"+callee_url+"?page="+curpage+"' onFocus='this.blur()' class='link_pagenum_bg'>&nbsp;&nbsp;");
			else
				returnList.append("<a href='javascript:"+callee_url+"("+curpage+");' onFocus='this.blur()' class='link_pagenum_bg'>");

			returnList.append(left_image_url+"</a>&nbsp;&nbsp;");
		}

		// ���������� ��ȣ���� ������������ ��ȣ���� ȭ�鿡 ǥ��
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

		//���� ûũ������ �̵��� ��Ʈ�̹���
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
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����¡ ����
    * ��    Ÿ : param   : out - printout ��ü
    *                      url - paging �� ���Ǵ� url
    *                      size - total record count
    *                      pageSize - �� page�� ����� record ����
    *                      scrollSize - display�� page ��ȣ ����
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
			buffer.append("�˻� ���1 " + size + " " + "��" + "&nbsp;");
		}else{
			buffer.append("�˻� ���2 " + size + "&nbsp;");
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

			// ó�� �������� �̵�
			if(pageNum != 1)
				buffer.append("<A HREF=\""+url+"pageNum=1" + "&pageSize=" + pageSize + "\"></A>&nbsp;&nbsp;");
			else
				buffer.append("");

			//100���� �̵�
			if(BlockNo2 != 1){
				buffer.append("<A HREF=\""+url+"pageNum="+(BlockNo2-100)+ "&pageSize=" + pageSize + "\">"+"<img src=/image/ppre.gif border=0 align=absmiddle>"+"</A>&nbsp;&nbsp;");
			}

			// ����
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

			// ����
			if(cBlockNo <= pageQuantity){
				buffer.append("<A HREF=\""+url+"pageNum="+cBlockNo+ "&pageSize=" + pageSize + "\">"+"<img src=/image/next.gif border=0 align=absmiddle>"+"</A>&nbsp;&nbsp;");
			}else{
				buffer.append("<img src=/image/next.gif border=0 align=absmiddle>");
			}

			//100���� �̵�
			if(BlockNo2 <= pageQuantity){
				buffer.append("<A HREF=\""+url+"pageNum="+BlockNo2+ "&pageSize=" + pageSize + "\">"+"<img src=/image/nnext.gif border=0 align=absmiddle>"+" </A>");
			}

			//������ �������� �̵�
			if(pageNum != pageQuantity)
				buffer.append("<A HREF=\""+url+"pageNum="+pageQuantity+ "&pageSize=" + pageSize + "\"></A>");
			else
				buffer.append("");
		}
		//String strSelect = "";

		out.println(buffer.toString());
	}

   
   /**    
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : paging ��  �Ѱ��� index���� ����Ͽ� ����Ѵ�.
    * ��    Ÿ : param   : fillChar       ������ ���̷� ���߱� ���� ä���ִ� ���ڿ�
    *                      length        ������ ����
    *                      pageNum   ������ ��ȣ
    *                      pageSize   ������ ������                    
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
	  ���ڿ� ���� �Լ�����
	************************************************************************************/

   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : Ư������ ��ȯ Check
    * ��    Ÿ : param   : str �ٲٷ��� ���ڿ��� ���� ����
    *                      pattern ã�� ���ڿ�
    *                      replace �ٲ��� ���ڿ�    
    *            return  : String
    */    
	public static String replace(String str, String pattern, String replace){
		int s = 0; // ã�� ������ ��ġ
		int e = 0; // StringBuffer�� append �� ��ġ
		StringBuffer result = new StringBuffer(); // ��� ���ڿ� ��ŵ� ��

		while ((e = str.indexOf(pattern, s)) >= 0){
			result.append(str.substring(s, e));
			result.append(replace);
			s = e+pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �־��� ���ڿ� str��  null�̸� ������ ����Ѵ�.
    * ��    Ÿ : param   : arg ����� ���ڿ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : len �� ��ŭ ��Ʈ�� ä���ֱ�
    * ��    Ÿ : param   : int len ����
    *                      String chr ä������ ����
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ��Ʈ���� Ư�� ���� ��ŭ �յڷ� ���̱�
    * ��    Ÿ : param   : String currStr ���� ��Ʈ��
    *                      int strLength �ݺ�Ƚ��
    *                      String fillStr �ݺ��� ����
    *                      boolean right_pos ture�̸� �����ʿ� �ƴϸ� ���ʿ� �߰�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : null �Ǵ� "" �̸� Ư���� ���ڿ��� ��ġ
    * ��    Ÿ : param   : String Str ���� ��Ʈ��
    *                      String conStr ��ü�� ���ڿ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ��Ʈ���� ������ �����̻��϶� ".."�� �ٿ��� �߶���
    * ��    Ÿ : param   : str  ��Ʈ������
    *                      len  �ڸ������ϴ� ����
    *            return  : ��ȯ�� ��Ʈ������
    */    
	public static String cutString(String str, int len){
		if(str.length() < (len+1))
			return str;
		else
			return (str.substring(0, len) + "..");
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���ڿ����� Ư�������� ���� ������ ���Ѵ�.
    * ��    Ÿ : param   : str  string
    *                      chr  string
    *            return  : ī��Ʈ ��
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : String���� Ư�����ڸ� ���ش�
    * ��    Ÿ : param   : String str ������� ���ڿ�
    *                      char tchar �����ϰ��� �� ����
    *            return  : tchar�� ���ŵ� ���ο� String
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : String���� ���������� SPACE�� ���ش�
    * ��    Ÿ : param   : String str �۾���� ���ڿ�
    *            return  : ���ο� String
    *            2006-03-20 �Ķ���Ͱ� null ��� ó�� �߰� (jkkoo)
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

		//�ѱ۹���ó��
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : InputStream �� �����͸� �ѱ� String ������ ��ȯ
    * ��    Ÿ : param   : InputStream   InputStream ��
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : '0'���� ��� ä���� ���ڿ��� Ư�����ڷ� �ٲپ���
    * ��    Ÿ : param   : str     �Է� ���ڿ�
    *                      rstr    ��� 0�ϰ�� �ٲܹ���
    *            return  : ����� ���ڿ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �����ڿ� ���� ���ڿ� �и� StringToken�̿�
    * ��    Ÿ : param   : str        �и��� ���ڿ�
    *                      token      ������
    *            return  : �и��� ���ڿ� �迭
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �����ε��� ���� ���ڿ� replace �޼ҵ�
    * ��    Ÿ : param   : String      text    ����
    *                      int         start   ���� index
    *                      String      src     ã�¹���
    *                      String      dest    �ٲٷ��¹���
    *            return  : String      ��� ���ڿ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���ڿ��� ���ʺκп� Ư�����ڸ� �߰��ؼ� ���ϴ� ���̷� �����
    * ��    Ÿ : param   : String str  ����
    *                      String fillChar �߰��� ����
    *            return  : int length     ��������ϴ� ���ڿ� ����
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���ڿ��� �����ʺκп� Ư�����ڸ� �߰��ؼ� ���ϴ� ���̷� �����
    * ��    Ÿ : param   : String str  ����
    *                      String fillChar �߰��� ����
    *            return  : int length     ��������ϴ� ���ڿ� ����
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �޽��� ���� CommonMSG.properties���Ͽ��� �ش� Ű������ �޽��� �б�
    * ��    Ÿ : param   : String inProperty   �޽����� �о� ���� Ű��
    *                      String strValue     Ű���� ��ġ�Ǵ� �޽��� ���ڿ�
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
				strValue = "CommonMSG.properties�� �о� ���� �� �����ϴ�.";
			}
		}catch(Exception e){
			strValue = "CLASSPATH���� CommonMSG.properties ������ ã�� �� �����ϴ�.";
		}
		return strValue;
		*/

		//non static�� ���
		InputStream is       = getClass().getResourceAsStream("CommonMSG.properties");
		Properties  prop = new Properties();
		String      strValue  = "";
		try{
			prop.load(is);
			strValue = enToKo(prop.getProperty(inProperty));
		}catch(Exception e){
			System.err.println("Can't read the properties file. " +
			  "Make sure CommonMSG.properties is in the CLASSPATH");
			strValue = "CLASSPATH���� CommonMSG.properties ������ ã�� �� �����ϴ�.";
		}
		return strValue;
	}


   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���� ���Ͽ��� ������ �б�
    * ��    Ÿ : param   : String filename     ���� ���ϸ�
    *                      String[] Param      ������ Parameter
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
				strNum = strNum + String.valueOf(i + 1).trim();                                         //�Ķ���� �ε���
				strQry = replace(strQry, "{%S" + strNum + "%}", "'" + Param[i] + "'");                 //���ڿ� �Ķ����
				strQry = replace(strQry, "{%N" + strNum + "%}", replace(Param[i], ",", ""));        //������ �Ķ����
				strQry = replace(strQry, "{%T" + strNum + "%}", Param[i]);                             //���̺��̳�, �ʵ�� �Ķ����
			}
		}catch(Exception e){
			System.out.println("[CommonUtil.MakeQuery()] ���� ������ ������ ���߽��ϴ�."+e.getMessage());
			strQry = "";
		}

		return strQry;
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���� ���Ͽ��� ������ �б� No Parameter
    * ��    Ÿ : param   : String filename     ���� ���ϸ�
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
			System.out.println("[CommonUtil.MakeQueryNP()] ���� ������ ������ ���߽��ϴ�."+e.getMessage());
			strQry = "";
		}

		return strQry;
	}





	/***********************************************************************************
	  ���� & �ݾ� ���� �޼ҵ�
	************************************************************************************/

   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ �޾Ƽ� �ݿø�(5���� ����, 5�̻� �ø�)
    * ��    Ÿ : param   : int
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : double�� �޾Ƽ� �ݿø�(5���� ����, 5�̻� �ø�)
    * ��    Ÿ : param   : double
    *            return  : long
    */    
	public static long Round(double number){
		long round_number = Math.round(number/10);
		long exchange_number = round_number * 10;
		return exchange_number;
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �ֹι�ȣ 1234561234567�� '-'�ֱ�
    * ��    Ÿ : param   : resno �ٲ� ���ڿ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����ڹ�ȣ�� '-'�ֱ�
    * ��    Ÿ : param   : resno �ٲ� ���ڿ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �����ȣ�� '-'�ֱ�
    * ��    Ÿ : param   : post �ٲ� ���ڿ�
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


	// �޴��� ����, �ܰ�, �ݾ�����
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����, �ܰ�, �ݾ� ���� ����(double ��)
    * ��    Ÿ : param   : double ����
    *                      String ���Ĺ�ȣ
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����, �ܰ�, �ݾ� ���� ���� 0���� ��ü (double ��)
    * ��    Ÿ : param   : double ����
    *                      String ���Ĺ�ȣ
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : int�� ���ڸ� 3�ڸ� ���� �޸����
    * ��    Ÿ : param   : num  int
    *            return  : ���ο� string
    */    
	public static String dFormat(int num){
		NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
		return nf.format(num);
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : long�� ���ڸ� 3�ڸ� ���� �޸����
    * ��    Ÿ : param   : num  long
    *            return  : ���ο� string
    */    
	public static String dFormat(long num){
		NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
		return nf.format(num);
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : double�� ���ڸ� 3�ڸ� ���� �޸����
    * ��    Ÿ : param   : num  double
    *            return  : ���ο� string
    */    
	public static String dFormat(double num){
		NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
		return nf.format(num);
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ ���ڸ� 3�ڸ� ���� �޸����
    * ��    Ÿ : param   : num  String
    *            return  : ���ο� string
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : String ���� #,###,###,##0.#0 �������� �ٲ�
    * ��    Ÿ : param   : String str     ���� ��
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���� ���� #,###,###,##0.#0 �������� �Ҽ������� �ٲ�
    * ��    Ÿ : param   : String str  ���� ��
    *                      int cnt     �Ҽ��� �ڸ���
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : #.# �� ���� string ���� #.# float ������ ��ȯ
    * ��    Ÿ : param   : String str
    *            return  : ��ȯ�Ǽ���
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ i_length��ŭ 0�� �߰�
    * ��    Ÿ : param   : s_Data   ���ڿ�
    *                      i_length �߰��� 0�� ����
    *            return  : String
    */    
	public static String setZero(String s_Data, int i_length){
		for (int i_cnt = 0; i_cnt < i_length; i_cnt++)
			s_Data = s_Data + "0";
		return s_Data;
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : Double���� ���ڸ� �Ҽ� i�ڸ����� �ݿø��� �Ѵ�.
    * ��    Ÿ : param   : String s_Data   Double�� ����
    *                      int i_num       �ݿø��� �ڸ�
    *            return  : String          �ݿø��� ������ ����
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������� Zero String�� �����ϴ� substring �޼ҵ�
    * ��    Ÿ : param   : String   str      string source
    *                      int      start    substring ������ġ
    *                      int      length   substring ����
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���ڷε� �ݾ��� ���ڷ� ǥ���� - ��õ��
    * ��    Ÿ : param   : String     str       �ݾ�String(����)
    *            return  : String     �ݾ�String(����)
    */    
	public static String makeIntToKorCurr(String str){
		String[] arrBigUnit   = {"", "��", "��", "��", "��", "��"};
		String[] arrOne       = {"", "��", "��", "��", "��", "��", "��", "ĥ", "��", "��"};
		String[] arrTwo       = {"", "��", "�̽�", "���", "���", "����", "����", "ĥ��", "�Ƚ�", "����"};
		String[] arrThr       = {"", "��", "�̹�", "���", "���", "����", "����", "ĥ��", "�ȹ�", "����"};
		String[] arrFou       = {"", "õ", "��õ", "��õ", "��õ", "��õ", "��õ", "ĥõ", "��õ", "��õ"};
		String   lastWord     = "��";

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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���ڷε� �ݾ��� ���ڷ� ǥ���� - 3õ��
    * ��    Ÿ : param   : String     str       �ݾ�String(����)
    *            return  : String     �ݾ�String(����)
    */    
	public static String makeIntToNumKorCurr(String str){
		String[] arrBigUnit   = {"", "��", "��", "��", "��", "��"};
		String[] arrOne       = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String[] arrTwo       = {"", "10", "20", "30", "40", "50", "60", "70", "80", "90"};
		String[] arrThr       = {"", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��"};
		String[] arrFou       = {"", "1õ", "2õ", "3õ", "4õ", "5õ", "6õ", "7õ", "8õ", "9õ"};
		String   lastWord     = "��";

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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �� ���ڿ��� BigDecimal Type���� ���������ϰ� ������� ���ڿ��� ��ȯ
    * ��    Ÿ : param   : String    str1    ���ڿ�
    *                      String    str2    ������ ���ڿ�
    *            return  : String    (str1 + str2)�� ����� String
    */   
	public static String BigDecimalAdd(String str1, String str2){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.add(B_decimal2);

		return B_decimal1.toString();
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �� ���ڿ��� BigDecimal Type���� ���������ϰ� ������� ���ڿ��� ��ȯ
    * ��    Ÿ : param   : String    str1    ���ڿ�
    *                      String    str2    ���̳ʽ��� ���ڿ�
    *            return  : String    (str1 - str2)�� ����� String
    */   
	public static String BigDecimalSubtract(String str1, String str2){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.subtract(B_decimal2);

		return B_decimal1.toString();
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �� ���ڿ��� BigDecimal Type���� ���������ϰ� ������� ���ڿ��� ��ȯ
    * ��    Ÿ : param   : String    str1    ���ڿ�
    *                      String    str2    ���� ���ڿ�
    *            return  : (str1 * str2)�� ����� String
    */   
	public static String BigDecimalMultiply(String str1, String str2){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.multiply(B_decimal2);

		return B_decimal1.toString();
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �� ���ڿ��� BigDecimal Type����  �����⿬���ϰ� ������� ���ڿ��� ��ȯ
    * ��    Ÿ : param   : String    str1    ���ڿ�
    *                      String    str2    ���� ���ڿ�
    *                      int       rd     �ݿø��� �ڸ��� (BigDecimal�޼ҵ��� divide�޼ҵ� ����)
    *            return  : (str1 / str2)�� scale�� ���� �ݿø��� �� String
    */   
	public static String BigDecimalDivide(String str1, String str2, int rd){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.divide(B_decimal2, rd, BigDecimal.ROUND_HALF_UP);

		return B_decimal1.toString();
	}


   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �� ���ڿ��� BigDecimal Type����  �����⿬���ϰ� ������� ���ڿ��� ��ȯ
    * ��    Ÿ : param   : String    str1    ���ڿ�
    *                      String    str2    ���� ���ڿ�
    *                      int      scale   �ݿø� �Ǵ� ������� ���� �ڸ��� (BigDecimal�޼ҵ��� divide�޼ҵ� ����)
    *                      int      rnd     �ݿø� �Ǵ� ���� ����
    *                          0 : ROUND_UP      ,    1 : ROUND_DOWN
    *                          2 : ROUND_CEILING ,    3 : ROUND_FLOOR
    *                          4 : ROUND_HALF_UP ,    5 : ROUND_HALF_DOWN
    *                          6 : ROUND_HALF_EVEN ,  7 : ROUND_UNNECESSARY
    *            return  : (str1 / str2)�� scale�ڸ������� rnd�������� ó���� ����� String
    */   
	public static String BigDecimalDivide(String str1, String str2, int scale, int rnd){
		BigDecimal B_decimal1 = new BigDecimal(str1.trim());
		BigDecimal B_decimal2 = new BigDecimal(str2.trim());

		B_decimal1 = B_decimal1.divide(B_decimal2, scale, rnd);

		return B_decimal1.toString();
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �� ���� ���ڿ��� BigDecimal Type���� ���ϰ� ������� int������ ��ȯ
    * ��    Ÿ : param   : String    str1    ���ڿ�
    *                      String    str2    ���� ���ڿ�
    *            return  : str1 �� B���� �� ��� int
    *                      str1 > str2 : �����
    *                      str1 < str2 : ������
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���� token���� �и��� String�� ���� ���Ѵ�.
    * ��    Ÿ : param   : str     ���޵� String
    *                      tokens  tokenizer
    *            return  : ó���� String
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���ڷ� ������ ���ڿ��� �ڸ��� ���� , �ֱ�..
    * ��    Ÿ : param   : String
    *            return  : String
    */   
	public static String commaFormat(String text, int type){
		if(text == null)
			return "";
		else if (text.trim().length() < 1)
			return text.trim();
		else if(text.trim().equals("null"))         //������� �߰���û..
			return "";

		boolean dc = false;         //����üũ..
		dc = IsIntegerCheck(text);      //���ڸ� true, �ƴϸ� false;

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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ȣ - ���� - ��ȭ��ȣ �������� �ٲٱ�
    * ��    Ÿ : param   : String arg ��ȭ��ȣ
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

			if(arg.length() >= 9 && arg.length() <= 12){  //�Ϲ���ȭ

				if(arg.substring(0,2).equals("02")){     //��������
					if(arg.length() == 9){
						telno    = arg.substring(0, 2)+"-"+arg.substring(2, 5)+"-"+arg.substring(5, 9);
					}else if(arg.length() == 10){
						telno    = arg.substring(0, 2)+"-"+arg.substring(2, 6)+"-"+arg.substring(6, 10);
					}else{

					}

				}else if(arg.substring(0,4).equals("0505")){    //������ȭ
					if(arg.length() == 11){
						telno    = arg.substring(0, 4)+"-"+arg.substring(4, 7)+"-"+arg.substring(7, 11);
					}else if(arg.length() == 12){
						telno    = arg.substring(0, 4)+"-"+arg.substring(4, 8)+"-"+arg.substring(8, 12);
					}else{

					}

				}else{  //��Ÿ��������
					if(arg.length() == 10){
						telno    = arg.substring(0, 3)+"-"+arg.substring(3, 6)+"-"+arg.substring(6, 10);
					}else if(arg.length() == 11){
						telno    = arg.substring(0, 3)+"-"+arg.substring(3, 7)+"-"+arg.substring(7, 11);
					}
				}

			}else if(arg.length() < 5){ //�������
				telno = arg;

			}else if(arg.length() < 9){     //�������� 8�ڸ� ����..
				telno = arg.substring(0, arg.length()-4)+"-"+arg.substring(arg.length()-4, arg.length());

			}else{  //���̻� ������ ����..
				telno = arg.substring(0, arg.length()-8)+"-"+arg.substring(arg.length()-8, arg.length()-4)+"-"+arg.substring(arg.length()-4, arg.length());
			}
		}
		return telno;
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���ڿ��� ���ڷθ� �����Ǿ����� üũ
    * ��    Ÿ : param   : String
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �Ҽ����� ���Ե� ���� String�� Integer�� ��ȯ
    * ��    Ÿ : param   : String
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
   Convert CharacterSet ���� �޼ҵ� ����
************************************************************************************/
   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �ѱۺ�ȯ
    * ��    Ÿ : param   : String
    *            return  : �ѱ۷� ��ȯ�� string���� �����Ѵ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �ѱۺ�ȯ
    * ��    Ÿ : param   : String
    *            return  : �ѱ۷� ��ȯ�� string���� �����Ѵ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ������ ���� ����Ÿ�� encoding 8859_1 => KSC5601
    * ��    Ÿ : param   : str     browser�� string(8859_1)
    *            return  : KSC5601 type�� String
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ����Ÿ�� encoding KSC5601 => 8859_1
    * ��    Ÿ : param   : str     string(KSC5601)
    *            return  : 8859_1 type�� String
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : conversion : null --> ""
    * ��    Ÿ : param   : str     ���޵� String
    *            return  : ó���� String
    */   
	public static String NullToBlank(String str){
		if(str == null)  str = "";
		return str;
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : conversion : null, "" --> "0"
    * ��    Ÿ : param   : str     ���޵� String
    *            return  : ó���� String
    */   
	public static String NullBlankToZero(String str){
		if(str == null || str.trim().equals(""))  str = "0";
		return str;
	}

   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : db query �� ����Ÿ Ư������ ó��..
    * ��    Ÿ : param   : String
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
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : db query ����Ÿ�� HTML��������..
    * ��    Ÿ : param   : String
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
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���� ������ ����
    * ��    Ÿ : param   : int
    *            return  : 
    */   
   public void setCurrentPage(int current_page)
   {
      mCurrentPage = current_page;
      System.out.println("mCurrentPage ="+mCurrentPage);
   }
   
   /**    
    * �� �� �� : Ȳ����
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : ���� �������� ����Լ�
    * ��    Ÿ : param   : 
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
		* �� �� �� : shhwang
		* �� �� �� : 2006-03-28 ���� 2:13:10
		* ��    �� :  �� ��¥ ������ ������ ��� 
		* ��    Ÿ : param   :  fromdt,todt    ���޵� String
		*            return  : ó���� int
		* �����⵵�� ������ ���� ���� ���� by Ȳ���� : 05.25
	*/   
  public static String getMonthBetween(String fromdt,String todt)  throws java.text.ParseException
  {
	return String.valueOf(monthsBetween(fromdt, todt, "yyyyMMdd"));
		
  }
	 
  /**    
		  * �� �� �� : shhwang
		  * �� �� �� : 2006-03-28 ���� 2:13:10
		  * ��    �� :  �� ��¥ ������ ������ ��� 
		  * ��    Ÿ : param   :  fromdt,todt    ���޵� String
		  *            return  : ó���� int
		  * �����⵵�� ������ ���� ���� ���� by Ȳ���� : 05.25
	  */    
	public static int monthsBetween(String from, String to, String format) throws java.text.ParseException 
	{
		// ���� ����
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
		// ��
		result += ((toYear - fromYear) * 12);
		// ��
		result += (toMonth - fromMonth);
		// ��
		if (((toDay - fromDay) > 0) ) result += toDate.compareTo(fromDate);

		return result;
	}
	
	/**    
		* �� �� �� : shhwang
		* �� �� �� : 2006-03-28 ���� 2:13:10
		* ��    �� :  ��¥�˻� 
		* ��    Ÿ : param   :  fromdt,todt    ���޵� String
		*            return  : ó���� int
		* �����⵵�� ������ ���� ���� ���� by Ȳ���� : 05.25
	*/   
	public static java.util.Date check(String s) throws java.text.ParseException {
		return check(s, "yyyyMMdd");
	}
	
	/**    
		* �� �� �� : shhwang
		* �� �� �� : 2006-03-28 ���� 2:13:10
		* ��    �� : ���ڰ˻� 
		* ��    Ÿ : param   :  fromdt,todt    ���޵� String
		*            return  : ó���� int
		* �����⵵�� ������ ���� ���� ���� by Ȳ���� : 05.25
	*/   
	public static java.util.Date check(String s, String format) throws java.text.ParseException 
	{
		// �Ķ���� �˻�
		if ( s == null )
		{
			throw new java.text.ParseException("date string to check is null", 0);
		}
		if ( format == null )
		{
			throw new java.text.ParseException("format string to check date is null", 0);
		}
	
		// ��¥ ���� ����
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);

		// �˻�
		java.util.Date date = null;
	
		try 
		{
			date = formatter.parse(s);
		}
		catch(java.text.ParseException e) 
		{
			throw new java.text.ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
		}

		// ��¥ ������ Ʋ�� ���
		if ( !formatter.format(date).equals(s))
		{
			throw new java.text.ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"",0);
		}
	
		// ����
		return date;
	}
		
	/**    
			* �� �� �� : shhwang
			* �� �� �� : 2006-04-01 ���� 2:13:10
			* ��    �� :  �ش���� ���� �� �޺� ����� 
			* ��    Ÿ : param   :   gubun �� Y �̸� ������ ��¥�� 99�� �Ѵ�. 
			*            return  : ó���� StringBuffer
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

			//�����ȯ
			return dateOption;
		}	
   
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-03-13 ���� 2:13:10
    * ��    �� : �Է��� ��¥ ��������  ���  ��,��
    * ��    Ÿ : param   : String date (19991002) , int �����ϼ�
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
    * �� �� �� : moono
    * �� �� �� : 2006-05-10 ���� 2:12:10
    * ��    �� : �Է��� ���� �������� �״��� ������ ���ڸ� ����
    * ��    Ÿ : param   : String date (199910)
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
      //�����ȯ
      return day;
   } 
   /**    
    * �� �� �� : moono
    * �� �� �� : 2006-05-10 ���� 2:12:10
    * ��    �� : ������� �������� ������� ������ ���ڸ� ����
    * ��    Ÿ : param   : 
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
      
      //�����ȯ
      return day;
   }     
   
	/**    
		* �� �� �� : shhwang
		* �� �� �� : 2006-05-24
		* ��    �� :�Է��� ��¥ �������� ���� ��,��
		* ��    Ÿ : param   : yyyymmdd, days,gubun
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
						 rightNow.add(rightNow.DATE, days+1);//�� �־�� �� 
						 date=rightNow.getTime();
						 yyyymmdd = int2s(rightNow.get(Calendar.YEAR), 4) + gubun + int2s(rightNow.get(Calendar.MONTH) + 1, 2) + gubun + int2s(rightNow.get(Calendar.DATE), 2);

						 return yyyymmdd;
					}
			  }catch(Exception e){
					return yyyymmdd;
			  }
		 }  
       
         
   /**    
      * �� �� �� : mhcho
      * �� �� �� : 2006-07-01
      * ��    �� : �Է��� ���ڿ��� byte������ �߶� ����
      * ��    Ÿ : param   : str, end
      *            return  : String yyyymmdd
      *            ex) String aaa="asbcd������" => cropByte(aaa, 7)  => aaa="abcd��"
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
      * �� �� �� : mhcho
      * �� �� �� : 2006-07-01
      * ��    �� : �Է��� ���ڿ��� byte������ �߶� ����
      * ��    Ÿ : param   : str, end
      *            return  : String yyyymmdd
      *            ex) String aaa="asbcd������" => cropByte(aaa, 7)  => aaa="abcd��"
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
      * �� �� �� : mhcho
      * �� �� �� : 2006-07-01
      * ��    �� : �Է��� ���ڿ��� �߶� ����
      * ��    Ÿ : param   : str, end
      *            return  : String yyyymmdd
      *            ex) String aaa="asbcd������" => cropByte(aaa, 7)  => aaa="abcd��"
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
	* �ݰ� ���ڸ� ���� ���ڷ� ��ȯ
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
		
		   if (j == 12288)                   // 2BYTE ���� 
		   {
			   Str.append(" ");
		   } 
		   else if (j > 65248)               //  �������ڸ� �ݰ����ڷ�
		   {
			   Str.append((char)(j-65248));
		   }
		   else                              // �������� 
		   {
			   Str.append((char)j);
		   }
	   }

	   return Str.toString();
   }
   /**
	* �ݰ� ���ڸ� ���� ���ڷ� ��ȯ
	* @return java.lang.String
	* @param tStr java.lang.String
	* @param tMax java.lang.int
	*/
   public static String conv2Byte(String tStr, int tMax) {
	   // �ִ� ���� ������ 0 ���� ������ ����
	   if (tMax <= 0) return null;
		
	   byte [] byStr = new byte[20];
	
	   StringBuffer Str = new StringBuffer();
			
	   int len = tStr.trim().length();
	   int j;

	   for(int i = 0; i < len; i++)
	   {
		   j = (int)tStr.charAt(i);
		
		   // ��������
		   if (j > 128) 
		   {
			   Str.append((char)j);
		   } 
		   //  �ݰ� ���� ������ �������� �������� ó��
		   //  �̷��� ���� ���� ��� ���� �� ���ڰ� ����.
		   else if (j == 32) 
		   {
			   Str.append("��");
		   }
		   // �ݰ����ڸ� �������ڷ�
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
	* �ݰ� ���ڸ� ���� ���ڷ� ��ȯ
	* @return java.lang.String
	* @param tStr java.lang.String : ���� 
	* @param tMax java.lang.int    : �ִ� ���� �������ڼ�(�ѱ۵� 1���ڷ� ���)
	* @param bLen java.lang.int    : �������� �������� ä���ߵǴ� ũ��
	��
	����tMax�� bLen�� ũ��� ����(DBCS) ���ڼ�
	*/
   public static String conv2Byte(String tStr, int tMax, int bLen) {
	   StringBuffer sb = new StringBuffer();

	   sb.append(tStr);

	   for(int i=tStr.length(); i < bLen; i++)
	   {
		   sb.append("��");
	   }

	   return conv2Byte(sb.toString(),tMax);
   }
   
   /**    
      * �� �� �� : mhcho
      * �� �� �� : 2006-08-14
      * ��    �� : �Է��� ���ڿ��� ����Ʈ�� ����
      * ��    Ÿ : param   : str
      *            return  : 
      */    
   public static int getStrBytes(String str){
      int l = 0;
      for (int i=0; i<str.length(); i++) l += (str.charAt(i) > 128) ? 2 : 1;
      return l;   
   }   
       
   /**    
		* �� �� �� : shhwang
		* �� �� �� : 2006-08-18
		* ��    �� : strValue ���ڿ����� ���๮�ڸ� ������ ���ڿ� ��ȯ
		* ��    Ÿ : param   : str
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
	* �� �� �� : sshong
	* �� �� �� : 2010-01-12
	* ��    �� : source���� target������ ���� ����  
	* ��    Ÿ :  
	*  @param source ������ ���ϸ��� ������ ���� ���   
	*  @param target ����� ���ϸ��� ������ ������  
	*  */ 

	public static void filecopy(String source, String target) {  //���� ����� �Ǵ� ���� ����   
		File sourceFile = new File( source ); //��Ʈ��, ä�� ����  
		FileInputStream inputStream = null;  
		FileOutputStream outputStream = null;  
		FileChannel fcin = null;  
		FileChannel fcout = null; 
		
		try {   //��Ʈ�� ����   
			inputStream = new FileInputStream(sourceFile);   
			outputStream = new FileOutputStream(target);   //ä�� ����   
			fcin = inputStream.getChannel();   
			fcout = outputStream.getChannel();      //ä���� ���� ��Ʈ�� ����   
			long size = fcin.size();   
			fcin.transferTo(0, size, fcout); 
		} catch (Exception e) {   
			e.printStackTrace();  
		} finally {   //�ڿ� ����   
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
	
	
   //�޼ҵ� �׽�Ʈ
	
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
		System.out.println("replaceSpecial="+replaceSpecial("�ȳ��ϼ���\"����"));
		String arrays[] = null;
		arrays = getTokenStr("�ȳ��ϼ���/�ݰ����ϴ�/�׷�/ /", "/");
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
