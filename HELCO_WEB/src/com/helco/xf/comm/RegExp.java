package com.helco.xf.comm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author db2admin
 *
 * �� ������ ���� �ּ��� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â&gt;ȯ�漳��&gt;Java&gt;�ڵ� ����&gt;�ڵ� �� �ּ�
 */
public class RegExp
{

   /**
    * ������ ���͸��ϱ�
    *   
    * @author Sehwan Noh <sehnoh@gmail.com> 
    * @version 1.0 - 2006. 08. 22 
    * @since JDK 1.4 
    *
    * @param sText  : ���� ���ڿ�
    * @param xxx    : ������
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
    * ���� �ּ��� ��ȿ���� �˻��Ѵ�.<br>
    *  <code>
    *  String mail = "cjyoon@shinbiro.com";<br>
    *  boolean result = RegExp.isValidEmail(mail);<br>
    *  </code> <code>result</code>�� <code>true</code> �� ������ �ȴ�.
    *
    * @param email : �����ּ�
    * @return
    */
   public static boolean isValidEmail(String email) 
   { 
      Pattern p = Pattern.compile("^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$"); 

      Matcher m = p.matcher(email); 

      return m.matches(); 
   }

   /**
    * HTML ��ũ �����
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
    *  ���� ���ڿ��� ����ũ ���ڿ��� ���� ���ڿ��� ã�� ġȯ�Ѵ�.<br>
    *  <code>
    *  String source = "200705";<br>
    *  String result = RegExp.mask(source, "XXXX �� XX ��","X");<br>
    *  </code> <code>result</code>�� <code>"2007 �� 05 ��"</code> �� ������ �ȴ�.
    *
    * @param s       : ���� ���ڿ�
    * @param mask    : ����ũ ���ڿ� 
    * @param pattern : ���� ���ڿ� 
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
    * ���ڿ� ����ŷ
    *   
    * @author Sehwan Noh <sehnoh@gmail.com> 
    * @version 1.0 - 2006. 08. 22 
    * @since JDK 1.4 
    *
    * @param word : ��� ���ڿ�
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
    *  HTML �±� ����.
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
    *  ��ȭ��ȣ �˻�
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
    * ���Խ��� �̿��� ���ڿ� ġȯ
    * 
    * @param sText : ��� ���ڿ�
    * @param pText : ���Խ� ����
    * @param cText : ġȯ ���ڿ�
    * @return
    * 
    * �� : ���ڸ� ��������
    *      replaceAll("-1,123,000,000.12","(\-?\d+\,?)($|\.\d+)","");
    */
   public static String replaceAll(String sText, String pText, String cText) 
   {
	   return replaceAll(getPattern(pText), sText, cText); 
   }   
   
   /**
    * ���Խ� Pattern  
    * @param sText
    * @return
    */
   public static Pattern getPattern(String pText)
   {
      return Pattern.compile(pText, Pattern.CASE_INSENSITIVE); 
   }
 
   /**
    * ���Խ� Pattern  
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
