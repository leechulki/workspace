/**
 * �� �� �� : TransForBWA.java
 * ��    �� : 
 * �� �� �� : jkkoo
 * �� �� �� : 2006-05-24 ���� 4:22:06
 * ���泻�� :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.cs12.batch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import tit.service.core.logger.Logger;

import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class TransForBWA
{
   static Logger logger;
   /**
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.05.24ss
    * ��  �� : ȣ�⸶���Ϳ� ���� �����̰� ó�� ����.. 
    * ��  Ÿ : 
    */ 
   public int ProcTransForBWA (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      String                     before_jrol     = "";
      String                     before_brol     = "";
      String                     after_jrol      = "";
      String                     after_brol      = "";
      
      if("1".equals(vo.getCebwzghb()))
      {
         before_jrol = "26002";
         before_brol = "25004";
      }
      else if("3".equals(vo.getCebwzghb()))
      {
         before_jrol = "26006";
         before_brol = "25007";
      }
      
      if("1".equals(vo.getCebwzgha()))
      {
         after_jrol = "26002";
         after_brol = "25004";
      }
      else if("3".equals(vo.getCebwzghb()))
      {
         after_jrol = "26006";
         after_brol = "25007";
      }
      
      String bpm = getBPM(mandt, vo.getCebwzcha(), conn);
      String ara = getARA(mandt, vo.getCebwzcha(), conn);
      
      sqlBuff.append(" UPDATE                   \n");
      sqlBuff.append("        SAPHEE.ZCST111    \n");
      sqlBuff.append("    SET                   \n");
      sqlBuff.append("        BSU = ?,          \n");
      sqlBuff.append("        ARA = ?,          \n");
      sqlBuff.append("        BPM = ?,          \n");
      sqlBuff.append("        GBU = ?,          \n");
      sqlBuff.append("        AGB = ?,          \n");
      sqlBuff.append("        JUJ = ?,          \n");
      sqlBuff.append("        BUJ = ?,          \n");
      sqlBuff.append("        RDD = ?,          \n");
      sqlBuff.append("        RDT = ?,          \n");
      sqlBuff.append("        RSB = 'system'    \n");
      sqlBuff.append("  WHERE 1=1               \n");
      sqlBuff.append("    AND MANDT = ?         \n");
      sqlBuff.append("    AND PJT = ?           \n");
      sqlBuff.append("    AND HNO = ?           \n");

      try
      {
/* 2010.08.30 LJH ����(��/�������� ����) */
//    	 String aju = BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), after_jrol, conn);
//    	 String abu = BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), after_brol, conn);
    	 String aju = vo.getCebwzjua();
    	 String abu = vo.getCebwzbua();
/* */
    	 int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(idxparam++, vo.getCebwzcha()); // �̰� ��, �������»�
         pstmt.setString(idxparam++, ara); // ����
         pstmt.setString(idxparam++, bpm); // ����PM
         pstmt.setString(idxparam++, vo.getCebwzgha()); // �̰� ��, ����/���»� ����
         pstmt.setString(idxparam++, vo.getCebwzgba()); // �̰� ��, �ó�����
         pstmt.setString(idxparam++, aju); // ��������
         pstmt.setString(idxparam++, abu); // ��������
         pstmt.setString(idxparam++, DateTime.getShortDateString());
         pstmt.setString(idxparam++, DateTime.getShortTimeString());
      
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         pstmt.executeUpdate();
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         BatchConstants.close(null,pstmt,null);            
      }
      //logger.info(" ȣ�⸶���� ó�� ���� ");
      logger.debug(" ȣ�⸶���� ó�� ���� ");
      return 0;
   }
   
   /**
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.05.24ss
    * ��  �� : ȣ�⸶���Ϳ� ���� �����̰� ó�� ����.. 
    * ��  Ÿ : 
    */ 
   public int ProcTransForBWA2 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      String                     before_jrol     = "";
      String                     before_brol     = "";
      String                     after_jrol      = "";
      String                     after_brol      = "";
      
      if("1".equals(vo.getCebwzghb()))
      {
         before_jrol = "26002";
         before_brol = "25004";
      }
      else if("3".equals(vo.getCebwzghb()))
      {
         before_jrol = "26006";
         before_brol = "25007";
      }
      
      if("1".equals(vo.getCebwzgha()))
      {
         after_jrol = "26002";
         after_brol = "25004";
      }
      else if("3".equals(vo.getCebwzghb()))
      {
         after_jrol = "26006";
         after_brol = "25007";
      }
      
      String bpm = getBPM(mandt, vo.getCebwzcha(), conn);
      String ara = getARA(mandt, vo.getCebwzcha(), conn);
      
      sqlBuff.append(" UPDATE                   \n");
      sqlBuff.append("        SAPHEE.ZCST101    \n");
      sqlBuff.append("    SET                   \n");
      sqlBuff.append("        CS101_BSU = ?,    \n");
      sqlBuff.append("        CS101_ARA = ?,    \n");
      sqlBuff.append("        CS101_BPM = ?,    \n");
      sqlBuff.append("        CS101_AGB = ?     \n");
      sqlBuff.append("  WHERE 1=1               \n");
      sqlBuff.append("    AND MANDT = ?         \n");
      sqlBuff.append("    AND CS101_PJT = ?     \n");
      sqlBuff.append("    AND CS101_HNO = ?     \n");

      try
      {
    	 int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(idxparam++, vo.getCebwzcha()); // �̰� ��, �������»�
         pstmt.setString(idxparam++, ara); // ����
         pstmt.setString(idxparam++, bpm); // ����PM
         pstmt.setString(idxparam++, vo.getCebwzgba()); // �̰� ��, �ó�����
      
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         pstmt.executeUpdate();
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         BatchConstants.close(null,pstmt,null);            
      }
      //logger.info(" ȣ�⸶���� ó�� ���� ");
      logger.debug(" ȣ�⸶���� ó�� ���� ");
      return 0;
   }
   
   /**
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.05.25
    * ��  �� : ���»� �ڵ忡 ���� ���� PM ���� �������� 
    * ��  Ÿ :
    */ 
   public String getBPM(String mandt, String cha, Connection conn) throws Exception
   {  
      StringBuffer               sqlBuff     = new StringBuffer();
      LoggablePreparedStatement  pstmt       = null;
      ResultSet                  rs          = null;
      String                     returnVal   = "";
      
      sqlBuff.append("SELECT                           \n");
      sqlBuff.append("       WB010.BSU_PM              \n");
      sqlBuff.append("  FROM                           \n");
      sqlBuff.append("       SAPHEE.ZWBT010 WB010,     \n");
      sqlBuff.append("       SAPHEE.ZMMT005 MM005      \n");
      sqlBuff.append(" WHERE 1=1                       \n");
      sqlBuff.append("   AND WB010.MANDT = ?           \n");
      sqlBuff.append("   AND MM005.LGORT = ?           \n");
      sqlBuff.append("   AND WB010.MANDT = MM005.MANDT \n");
      sqlBuff.append("   AND WB010.LIFNR = MM005.LIFNR \n");
      sqlBuff.append("  WITH UR                        \n");
      
      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(1, mandt);
         pstmt.setString(2, cha);
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            returnVal = rs.getString("BSU_PM");
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
   /**
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.05.25
    * ��  �� : ���»� �ڵ忡 ���� ���� ���� �������� 
    * ��  Ÿ :
    */ 
   public String getARA(String mandt, String cha, Connection conn) throws Exception
   {  
      StringBuffer               sqlBuff     = new StringBuffer();
      LoggablePreparedStatement  pstmt       = null;
      ResultSet                  rs          = null;
      String                     returnVal   = "";
      
      sqlBuff.append("SELECT                           \n");
      sqlBuff.append("       WB010.BSU_ARA             \n");
      sqlBuff.append("  FROM                           \n");
      sqlBuff.append("       SAPHEE.ZWBT010 WB010,     \n");
      sqlBuff.append("       SAPHEE.ZMMT005 MM005      \n");
      sqlBuff.append(" WHERE 1=1                       \n");
      sqlBuff.append("   AND WB010.MANDT = ?           \n");
      sqlBuff.append("   AND MM005.LGORT = ?           \n");
      sqlBuff.append("   AND WB010.MANDT = MM005.MANDT \n");
      sqlBuff.append("   AND WB010.LIFNR = MM005.LIFNR \n");
      sqlBuff.append("  WITH UR                        \n");
      
      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(1, mandt);
         pstmt.setString(2, cha);
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            returnVal = rs.getString("BSU_ARA");
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
   /**
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.05.24
    * ��  �� : ȣ�⸶���Ϳ� ���� �����̰� ó�� ������.. 
    * ��  Ÿ : 
    */ 
   public TransForBWA(Logger pLogger)
   {
      logger = pLogger;
   }
}
