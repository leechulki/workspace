/**
 * �� �� �� : BatchConstants.java
 * ��    �� : 
 * �� �� �� : jkkoo
 * �� �� �� : 2006-05-24 ���� 1:21:24
 * ���泻�� :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.cs12.batch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class BatchConstants
{
   public static String DRIVER = "COM.ibm.db2.jdbc.app.DB2Driver";
   public static String DB = "jdbc:db2:EVLDB2";
   public static String USER = "evldb4";
   public static String PASS = "evldb4";
   
   //public static String HOSTDB = "jdbc:db2:sample";
   //public static String HOSTUSER = "jkkoo";
   //public static String HOSTPASS = "jkkoo123";
   
   //public static String CSDB = "jdbc:db2:CSDB";
   //public static String CSUSER = "db2inst1";
   //public static String CSPASS = "hyundai";
   
   public static String MU_GONGSA   = "A";
   public static String MU_ILBAN    = "B";
   public static String YU_GONGSA   = "C";
   public static String YU_ILBAN    = "D";
   /**
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.02.14
    * ��  �� : resource�� ���� 
    * ��  Ÿ :
    */     
   public static void close(ResultSet rs, Statement stmt, Connection conn)
   {
      if (rs != null)
         try
         {
            rs.close();
         }
         catch (Exception e)
         {
         }
      if (stmt != null)
         try
         {
            stmt.close();
         }
         catch (Exception e)
         {
         }
      if (conn != null)
         try
         {
            conn.close();
         }
         catch (Exception e)
         {
         }
   } 
   /**
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.05.25
    * ��  �� : ������ ���� ��������. 
    * ��  Ÿ :
    */ 
   public static String getCcsb1emp(String mandt, String cod, String rol, Connection conn) throws Exception
   {  
      StringBuffer               sqlBuff     = new StringBuffer();
      LoggablePreparedStatement  pstmt       = null;
      ResultSet                  rs          = null;
      String                     returnVal   = "";

      sqlBuff.append("SELECT                        \n");
      sqlBuff.append("       CCSB1EMP               \n");
      sqlBuff.append("  FROM                        \n");
      sqlBuff.append("       SAPHEE.ZTBCSB1         \n");
      sqlBuff.append(" WHERE 1=1                    \n");
      sqlBuff.append("   AND MANDT = ?              \n");
      sqlBuff.append("   AND CCSB1COD = ?           \n");
      //sqlBuff.append("   AND CCSB1ROL = ?           \n");
      sqlBuff.append("  WITH UR                     \n");
      
      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(1, mandt);
         pstmt.setString(2, cod);
         //2005.05.26 �̽��������� ������ �����ö��� ��å�� ���ǿ��� ����
         //pstmt.setString(2, rol);
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            returnVal = rs.getString("CCSB1EMP");
         }
      }
      catch (Exception e)
      {
         throw e;
      }
      finally
      {
         try
         {
            BatchConstants.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }      
      
      return returnVal;
   }   
}
