package com.helco.xf.cs12.evladm.dbutil;

/**
 * ���뿤��������-�λ�ý���
 * 
 * �ۼ� ��¥: (2001-04-16 ���� 10:35:50)
 * @author: Administrator
 */
public final class DateTime {

/**
 * DateTime ������ �ּ�.
 * Don't let anyone instantiate this class
 */
private DateTime() {
	super();
}
/**
 * ��� �ϼ� ���ϱ�
 * ��¥�� ����(Format) ����.
 *
 * return add day to date strings
 * @param String date string
 * @param int ���� �ϼ�
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� �ϼ� ���ϱ�
 *             ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
*/
public static String addDays(String s, int day) throws java.text.ParseException 
{
	return addDays(s,day,"yyyyMMdd");
}
/**
 * ��� �ϼ� ���ϱ�
 *
 * return add day to date strings with user defined format.
 * @param String date string
 * @param int ���� �ϼ�
 * @param format string representation of the date format. For example, "yyyy-MM-dd".
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� �ϼ� ���ϱ�
 *             ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static String addDays(String s, int day, String format) throws java.text.ParseException
{
	// ��¥ ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	
	// ��¥ �˻�
	java.util.Date date = check(s, format);

	// �Ϸ�� (1/1000��*60��*60��*24��)
	date.setTime(date.getTime() + ((long)day * 1000 * 60 * 60 * 24));

	// Date���� String������ 
	return formatter.format(date);
}
/**
 * ��� ���� ���ϱ�
 * ��¥�� ����(Format) ����.
 *
 * return add month to date strings
 * @param String date string
 * @param int ���� ����
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ���� ���ϱ�
 *             ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static String addMonths(String s, int month) throws Exception 
{
	return addMonths(s, month, "yyyyMMdd");
}
/**
 * ��� ���� ���ϱ�
 *
 * return add month to date strings with user defined format.
 * @param String date string
 * @param int ���� ����
 * @param format string representation of the date format. For example, "yyyy-MM-dd".
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ���� ���ϱ�
 *             ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static String addMonths(String s, int addMonth, String format) throws Exception 
{
	// ��¥ ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	
	// ��¥ �˻�
	java.util.Date date = check(s, format);

	java.text.SimpleDateFormat yearFormat  = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
	java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
	java.text.SimpleDateFormat dayFormat   = new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);

	int year  = Integer.parseInt(yearFormat.format(date));
	int month = Integer.parseInt(monthFormat.format(date));
	int day   = Integer.parseInt(dayFormat.format(date));

	month += addMonth;
	if (addMonth > 0) 
	{
		while (month > 12) {
			month -= 12;
			year  += 1;
		}
	} 
	else 
	{
		while (month <= 0) {
			month += 12;
			year  -= 1;
		}
	}

	java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
 	java.text.DecimalFormat twoDf  = new java.text.DecimalFormat("00");
 	
	String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month)) + String.valueOf(twoDf.format(day));
	
	java.util.Date targetDate = null;

	// ��¥�˻�
	try 
	{
		targetDate = check(tempDate, "yyyyMMdd");
	} 
	catch(java.text.ParseException pe) 
	{
		day = lastDay(year, month);
		tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month)) + String.valueOf(twoDf.format(day));
		targetDate = check(tempDate, "yyyyMMdd");
	}

	return formatter.format(targetDate);
}
/**
 * ��� ��� ���ϱ�
 * ��¥�� ����(Format) ����.
 *
 * return add day to date strings
 * @param String date string
 * @param int ���� ���
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� �ϼ� ���ϱ�
 *             ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
*/
public static String addYears(String s, int year) throws Exception 
{
	return addYears(s, year, "yyyyMMdd");	
}
/**
 * ��� ��� ���ϱ�
 *
 * return add month to date strings with user defined format.
 * @param String date string
 * @param int ���� ���
 * @param format string representation of the date format. For example, "yyyy-MM-dd".
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ���� ���ϱ�
 *             ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static String addYears(String s, int addYear, String format) throws Exception 
{
		return addMonths(s, addYear*12, format);	

}
/**
 * ���� ���ϱ�
 *
 * return age between two date strings with default defined format.("yyyyMMdd")
 * @param String from date string
 * @param String to date string
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int ageBetween(String from, String to) throws java.text.ParseException 
{
	return ageBetween(from, to, "yyyyMMdd");
}
/**
 * ���� ���ϱ�
 *
 * return age between two date strings with user defined format.
 * @param String from date string
 * @param String to date string
 * @param format string representation of the date format. For example, "yyyy-MM-dd".
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int ageBetween(String from, String to, String format) throws java.text.ParseException 
{
	return (int)(daysBetween(from, to, format) / 365 );
}
/**
 * ��¥ �˻�
 * ��¥�� ����(Format) ����.
 *
 * check date string validation with the default format "yyyyMMdd".
 * @param s date string you want to check with default format "yyyyMMdd".
 * @return date java.util.Date
 */
public static java.util.Date check(String s) throws java.text.ParseException {
	return check(s, "yyyyMMdd");
}
/**
 * ���� �˻�
 * 
 * check date string validation with an user defined format.
 * @param s date string you want to check.
 * @param format string representation of the date format. For example, "yyyy-MM-dd".
 * @return date java.util.Date
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
 * �Ⱓ ���ϱ�
 * ��¥�� ����(Format) ����.
 *
 * return days between two date strings with default defined format.("yyyyMMdd")
 * @param String from date string
 * @param String to date string
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int daysBetween(String from, String to) throws java.text.ParseException 
{
	return daysBetween(from, to, "yyyyMMdd");
}
/**
 * ���� ������ �Ⱓ ���ϱ�
 *
 * return days between two date strings with user defined format.
 * @param String from date string
 * @param String to date string
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int daysBetween(String from, String to, String format) throws java.text.ParseException 
{
	// ��¥ ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

	// ��¥ �˻�
	java.util.Date d1 = check(from, format);
	java.util.Date d2 = check(to, format);
	
	long duration = d2.getTime() - d1.getTime();
	
	return (int)( duration/(1000 * 60 * 60 * 24) );
}
/**
 * ���� ���� ���ϱ�
 *
 * @return formatted string representation of current day with  "yyyy.MM.dd".
 */
public static String getDateString() 
{
	return getDateString("yyyyMMdd");
}
/**
 *	���� �� �κ� ���ϱ�
 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
 *
 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
 * @return formatted string representation of current day and time with  your pattern.
 */
public static int getDay() {
	return getNumberByPattern("dd");
}
/**
 * �������� ������ �������� ��ȯ
 * ��¥�� ����(Format) ����.
 *
 * �ۼ� ��¥: (2001-04-16 ���� 12:29:51)
 * @return java.lang.String
 * @param s java.lang.String
 * @param format java.lang.String
 * @exception java.lang.Exception ���� ����.
 */
public static String getDisDate(String s) throws java.lang.Exception 
{
	return getDisDate(s,"yyyy.MM.dd");
}
/**
 * �������� ������ �������� ��ȯ
 *
 * �ۼ� ��¥: (2001-04-16 ���� 12:29:51)
 * @return java.lang.String
 * @param s java.lang.String
 * @param format java.lang.String
 * @exception java.lang.Exception ���� ����.
 */
public static String getDisDate(String s, String format) throws java.lang.Exception 
{
	// ��¥ ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
	
	// ��¥ �˻�	
	java.util.Date targetDate = null;

	try 
	{
		targetDate = check(s);
	} 
	catch(java.text.ParseException pe)
	{
		// �����Ϸ� Setting
		// targetDate = new java.util.Date();	
		return "";	
	}

	return formatter.format(targetDate);
}
/**
 * ���� ���ڸ� ������ �������� ���� ��ȯ
 *
 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
 *
 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
 * @return formatted string representation of current day and time with  your pattern.
 */
public static String getFormatString(String pattern) 
{
	// ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
	
	// ������ ��������
	String dateString = formatter.format(new java.util.Date());
	
	return dateString;
}
/**
 * ���� �� ���ϱ�
 *
 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
 *
 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
 * @return formatted string representation of current day and time with  your pattern.
 */
public static int getMonth() 
{
	return getNumberByPattern("MM");
}
/**
 * ���� ���ڸ� ������ �������� int������ ��ȯ
 *
 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
 *
 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
 * @return formatted string representation of current day and time with  your pattern.
 */
public static int getNumberByPattern(String pattern) 
{
	// ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern, java.util.Locale.KOREA);

	// ���Ŀ� ���� ���� ���ڸ� ������ 
	String dateString = formatter.format(new java.util.Date());
	
	return Integer.parseInt(dateString);
}
/**
 * ���� ���ϱ�
 *
 * @return formatted string representation of current day with  "yyyyMMdd".
 */
public static String getShortDateString() 
{
	// ��¥ ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA);

	return formatter.format(new java.util.Date());
}
/**
 * �ð� ���ϱ�
 *
 * @return formatted string representation of current time with  "HHmmss".
 */
public static String getShortTimeString() 
{
	// ��¥ ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("HHmmss", java.util.Locale.KOREA);

	return formatter.format(new java.util.Date());
}
/**
 * TimeStamp ���ϱ�
 *
 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
 */
public static String getTimeStampString() 
{
	// ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("yyyy-MM-dd-HH:mm:ss:SSS", java.util.Locale.KOREA);
	
	return formatter.format(new java.util.Date());
}
/**
 * ���� �ð� ���ϱ�
 *
 * @return formatted string representation of current time with  "HH:mm:ss".
 */
public static String getTimeString()
{
	// ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("HH:mm:ss", java.util.Locale.KOREA);
	
	// �ð� ������
	return formatter.format(new java.util.Date());
}
/**
 * ���� �⵵ ���ϱ�
 *
 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
 *
 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
 * @return formatted string representation of current day and time with  your pattern.
 */
public static int getYear() 
{
	return getNumberByPattern("yyyy");
}
/**
 * ���ڸ� �˻�
 * ���� ������ ����
 *
 * check date string validation with the default format "yyyyMMdd".
 * @param s date string you want to check with default format "yyyyMMdd"
 * @return boolean true ��¥ ������ �°�, �����ϴ� ��¥�� ��
 *                 false ��¥ ������ ���� �ʰų�, �������� �ʴ� ��¥�� ��
 */
public static boolean isValid(String s) throws Exception {
	return DateTime.isValid(s, "yyyyMMdd");
}
/**
 * ���ڸ� �˻�
 *
 * check date string validation with an user defined format.
 * @param s date string you want to check.
 * @param format string representation of the date format. For example, "yyyy-MM-dd".
 * @return boolean true ��¥ ������ �°�, �����ϴ� ��¥�� ��
 *                 false ��¥ ������ ���� �ʰų�, �������� �ʴ� ��¥�� ��
 */
public static boolean isValid(String s, String format) 
{
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);

	java.util.Date date = null;

	try 
	{
		date = formatter.parse(s);
	}
	catch(java.text.ParseException e) 
	{
		return false;
	}

	if ( ! formatter.format(date).equals(s) )
		return false;

	return true;
}
/**
 * ���� ������ ����
 */
private static int lastDay(int year, int month) throws java.text.ParseException 
{
	int day = 0;
	switch(month)
	{
		case  1:
		case  3:
		case  5:
		case  7:
		case  8:
		case 10:
		case 12: day = 31;break;
		case  2: if ((year % 4) == 0) 
					{
				   	if ((year % 100) == 0 && (year % 400) != 0) { day = 28; }
				   	else { day = 29; }
					} 
					else { day = 28; }
					break;
		default: day = 30;
	}
	return day;
}
/**
 * ���� �������� ���ϱ�
 */
public static String lastDayOfMonth(String src) throws java.text.ParseException 
{
	return lastDayOfMonth(src, "yyyyMMdd");
}
/**
 * ���� �������� ���ϱ�
 */
public static String lastDayOfMonth(String src, String format) throws java.text.ParseException 
{
	// ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	
	// ��¥ �˻�
	java.util.Date date = check(src, format);

 	java.text.SimpleDateFormat yearFormat  = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
 	java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);

	int year  = Integer.parseInt(yearFormat.format(date));
	int month = Integer.parseInt(monthFormat.format(date));
	int day   = lastDay(year, month);

	java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
	java.text.DecimalFormat twoDf  = new java.text.DecimalFormat("00");

	String tempDate = String.valueOf(fourDf.format(year)) + String.valueOf(twoDf.format(month)) + String.valueOf(twoDf.format(day));
	
	date = check(tempDate, format);

	return formatter.format(date);
}
/**
 * �Ⱓ(����) ���ϱ�
 *
 * return days between two date strings with default defined format.("yyyyMMdd")
 * @param String from date string
 * @param String to date string
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int monthsBetween(String from, String to) throws java.text.ParseException 
{
	return monthsBetween(from, to, "yyyyMMdd");
}
/**
 * �Ⱓ(����) ���ϱ�
 *
 * return days between two date strings with default defined format.("yyyyMMdd")
 * @param String from date string
 * @param String to date string
 * @param String format format string
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
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
 * ���� �˻�.
 *
 *		1: �Ͽ��� (java.util.Calendar.SUNDAY �� ��)
 *	   2: ������ (java.util.Calendar.MONDAY �� ��)
 *    3: ȭ���� (java.util.Calendar.TUESDAY �� ��)
 *    4: ������ (java.util.Calendar.WENDESDAY �� ��)
 *    5: ����� (java.util.Calendar.THURSDAY �� ��)
 *    6: �ݿ��� (java.util.Calendar.FRIDAY �� ��)
 *    7: ����� (java.util.Calendar.SATURDAY �� ��)
 *
 * @param s date string you want to check.
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ������ ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int whichDay(String s) throws java.text.ParseException 
{
	return whichDay(s, "yyyyMMdd");
}
/**
 * ���� �˻�.
 *
 *	1: �Ͽ��� (java.util.Calendar.SUNDAY �� ��)
 *	2: ������ (java.util.Calendar.MONDAY �� ��)
 *  3: ȭ���� (java.util.Calendar.TUESDAY �� ��)
 *  4: ������ (java.util.Calendar.WENDESDAY �� ��)
 *  5: ����� (java.util.Calendar.THURSDAY �� ��)
 *  6: �ݿ��� (java.util.Calendar.FRIDAY �� ��)
 *  7: ����� (java.util.Calendar.SATURDAY �� ��)
 *
 * @param s date string you want to check.
 * @param format string representation of the date format. For example, "yyyy-MM-dd".
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ������ ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int whichDay(String s, String format) throws java.text.ParseException 
{
	// ��¥ ���� ����
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);

	// ��¥ �˻�
	java.util.Date date = check(s, format);
	
	// Calendar ���� 
	java.util.Calendar calendar = formatter.getCalendar();
	
	calendar.setTime(date);
	
	return calendar.get(java.util.Calendar.DAY_OF_WEEK);
}
/**
 * �Ⱓ(���) ���ϱ�
 *
 * return days between two date strings with default defined format.("yyyyMMdd")
 * @param String from date string
 * @param String to date string
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int yearsBetween(String from, String to) throws Exception 
{
	return yearsBetween(from, to, "yyyyMMdd");
}
/**
 * �Ⱓ(���) ���ϱ�
 *
 * return days between two date strings with default defined format.("yyyyMMdd")
 * @param String from date string
 * @param String to date string
 * @param String format format string
 * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
 *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
 */
public static int yearsBetween(String from, String to, String format) throws Exception 
{
	// ������ �����Ŀ� ����� ȯ��
	int result = monthsBetween(from, to, format) / 12;
	
	// �������� ������ �⵵�� 1�� ����.
	if ((monthsBetween(from, to, format) % 12) > 0) result += 1;

	// ����� ����
	return result;	
}

/**
 * ���� ���� ���ϱ�
 *
 * @return formatted string representation of current day with  "yyyy.MM.dd".
 */
public static String getDateString(String format) 
{
	// ���� ����
	java.text.SimpleDateFormat formatter =	new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

	return formatter.format(new java.util.Date());
}
}