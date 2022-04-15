package com.helco.xf.comm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author db2admin
 *
 * 이 생성된 유형 주석에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창&gt;환경설정&gt;Java&gt;코드 생성&gt;코드 및 주석
 */
public class RegExp
{

   /**
    * 금지어 필터링하기
    *   
    * @author Sehwan Noh <sehnoh@gmail.com> 
    * @version 1.0 - 2006. 08. 22 
    * @since JDK 1.4 
    *
    * @param sText  : 원본 문자열
    * @param xxx    : 금지어
    * @return
    */
   public static String filterText(String sText, String xxx) 
   { 
      Pattern p = Pattern.compile(xxx, Pattern.CASE_INSENSITIVE); 
      
      Matcher m = p.matcher(sText); 
      
      StringBuffer sb = new StringBuffer(); 
      
      while (m.find()) 
      { 
         m.appendReplacement(sb, maskWord(m.group()));         
      } 

      m.appendTail(sb); 
      
      return sb.toString(); 
   } 
   /**
    * 메일 주소의 유효성을 검사한다.<br>
    *  <code>
    *  String mail = "cjyoon@shinbiro.com";<br>
    *  boolean result = RegExp.isValidEmail(mail);<br>
    *  </code> <code>result</code>는 <code>true</code> 을 가지게 된다.
    *
    * @param email : 메일주소
    * @return
    */
   public static boolean isValidEmail(String email) 
   { 
      Pattern p = Pattern.compile("^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$"); 

      Matcher m = p.matcher(email); 

      return m.matches(); 
   }

   /**
    * HTML 링크 만들기
    *   
    * @author Sehwan Noh <sehnoh@gmail.com> 
    * @version 1.0 - 2006. 08. 22 
    * @since JDK 1.4 
    *
    * @param sText
    * @return
    */
   public static String linkedText(String sText) 
   { 
      Pattern p = Pattern.compile( "(http|https|ftp)://[^\\s^\\.]+(\\.[^\\s^\\.]+)*"); 
      
      Matcher m = p.matcher(sText); 
      
      StringBuffer sb = new StringBuffer(); 
      
      while (m.find()) 
      { 
         m.appendReplacement(sb, "<a href='" + m.group()+"'>" + m.group() + "</a>"); 
      } 
      
      m.appendTail(sb); 
      
      return sb.toString(); 
   } 

   /**
    *  원본 문자열을 마스크 문자열에 패턴 문자열을 찾아 치환한다.<br>
    *  <code>
    *  String source = "200705";<br>
    *  String result = RegExp.mask(source, "XXXX 년 XX 월","X");<br>
    *  </code> <code>result</code>는 <code>"2007 년 05 월"</code> 을 가지게 된다.
    *
    * @param s       : 원본 문자열
    * @param mask    : 마스크 문자열 
    * @param pattern : 패턴 문자열 
    * @return
    */
   public static String maskEdit(String s, String mask, String pattern)
   {
      StringBuffer sb = new StringBuffer(mask.length());
      
      Pattern p = Pattern.compile(pattern);
      
      Matcher m = p.matcher(mask);
      
      if (m.find())
      {
         char[] s1 = s.toCharArray();
         int max = s1.length;
         int i   = 0;
         
         do
         {
            m.appendReplacement(sb, String.valueOf(s1[i++]));   
         }while(m.find() && i<max);
         
         m.appendTail(sb);
      }
                 
      return sb.toString();
   }
   
   /**
    * 문자열 마스킹
    *   
    * @author Sehwan Noh <sehnoh@gmail.com> 
    * @version 1.0 - 2006. 08. 22 
    * @since JDK 1.4 
    *
    * @param word : 대상 문자열
    * @return
    */
   public static String maskWord(String word) 
   { 
      StringBuffer buff = new StringBuffer(); 

      char[] ch = word.toCharArray(); 
      
      for (int i = 0; i < ch.length; i++) 
      { 
         if (i < 1) 
         { 
            buff.append(ch[i]); 
         } 
         else 
         { 
            buff.append("*"); 
         } 
      } 
      
      return buff.toString();    
   } 
   
   /**
    *  HTML 태그 제거.
    *   
    * @author Sehwan Noh <sehnoh@gmail.com> 
    * @version 1.0 - 2006. 08. 22 
    * @since JDK 1.4 
    *
    *
    * @param htmlStr
    * @return
    */
   public static String stripHTML(String htmlStr) 
   { 
      Pattern p = Pattern.compile("<(?:.|\\s)*?>"); 
      
      Matcher m = p.matcher(htmlStr); 
      
      return m.replaceAll(""); 
   }
   
   /**
    *  전화번호 검사
    *   
    * @author cjyoon 
    * @version 1.0 - 2008. 08. 09 
    * @since JDK 1.4 
    *
    *
    * @param pno
    * @return
    */
   public static boolean isValidPhone(String phone) 
   { 
      Pattern p = Pattern.compile("^0\\d{1,2}-[1-9]\\d{2,3}-\\d{4}$"); 

      Matcher m = p.matcher(phone); 

      return m.matches(); 
   }

   /**
    * 정규식을 이용한 문자열 치환
    * 
    * @param sText : 대상 문자열
    * @param pText : 정규식 패턴
    * @param cText : 치환 문자열
    * @return
    * 
    * 예 : 숫자를 공백으로
    *      replaceAll("-1,123,000,000.12","(\-?\d+\,?)($|\.\d+)","");
    */
   public static String replaceAll(String sText, String pText, String cText) 
   {
	   return replaceAll(getPattern(pText), sText, cText); 
   }   
   
   /**
    * 정규식 Pattern  
    * @param sText
    * @return
    */
   public static Pattern getPattern(String pText)
   {
      return Pattern.compile(pText, Pattern.CASE_INSENSITIVE); 
   }
 
   /**
    * 정규식 Pattern  
    * @param p
    * @param sText
    * @param cText
    * @return
    */
   public static String replaceAll(Pattern p, String sText, String cText) 
   { 
      Matcher m = p.matcher(sText); 
      
      StringBuffer sb = new StringBuffer(); 
      
      while (m.find()) 
      { 
    	  m.appendReplacement(sb, cText);         
      } 

      m.appendTail(sb); 
      
      return sb.toString(); 
   } 
 
}
