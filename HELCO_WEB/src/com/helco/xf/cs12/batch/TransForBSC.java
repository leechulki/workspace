/**
 * �� �� �� : TransForBSC.java
 * ��    �� : 
 * �� �� �� : jkkoo
 * �� �� �� : 2006-05-24 ���� 4:25:21
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

import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class TransForBSC
{
   static Logger logger;
   /**
    * �ۼ��� : ������
    * �ۼ��� : 2008.10.15
    * ��  �� : �����̰�ó���� ����������Ʈ���� ����(���󺸼��� ���) - ZMASTER02
    * ��  Ÿ : 
    */ 
   public int ProcTransForBSC (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ) ó�� ���� ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      String bpm = getBPM(mandt, vo.getCebwzcha(), conn);
      
      sqlBuff.append("UPDATE                    \n");
      sqlBuff.append("       SAPHEE.ZMASTER02   \n");
      sqlBuff.append("   SET                    \n");
      sqlBuff.append("       ZZBSU = ?,         \n");
      sqlBuff.append("       ZZBPM = ?          \n");
      sqlBuff.append(" WHERE 1=1                \n");
      sqlBuff.append("   AND MANDT = ?          \n");
      sqlBuff.append("   AND POSID = ?          \n");
      sqlBuff.append("   AND POSID_1 = ?        \n");

      int idxparam = 1;
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
      
      pstmt.setString(idxparam++, vo.getCebwzcha()); // �̰� ��, �������»�
      pstmt.setString(idxparam++, bpm); // ����PM
     
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, vo.getCebwzpjt());
      pstmt.setString(idxparam++, CommonUtil.removeSpace(vo.getCebwzpjt()) + CommonUtil.removeSpace(vo.getCebwzhno()));

      //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      pstmt.executeUpdate();
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ) ó�� ���� ");
      return 0;
   }

   /**
    * �ۼ��� : ������
    * �ۼ��� : 2008.10.15
    * ��  �� : �����̰�ó���� ����������Ʈ���� ����(���󺸼��� ���) - ZMASTER01
    * ��  Ÿ : 
    */ 
   public int ProcTransForBSC2 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ)2 ó�� ���� ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      String ara = getARA(mandt, vo.getCebwzcha(), conn);
      
      sqlBuff.append("UPDATE                    \n");
      sqlBuff.append("       SAPHEE.ZMASTER01   \n");
      sqlBuff.append("   SET                    \n");
      sqlBuff.append("       ZZARA = ?          \n");
      sqlBuff.append(" WHERE 1=1                \n");
      sqlBuff.append("   AND MANDT = ?          \n");
      sqlBuff.append("   AND POSID = ?          \n");

      int idxparam = 1;
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
      
      pstmt.setString(idxparam++, ara); // ����
     
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, vo.getCebwzpjt());

      //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      pstmt.executeUpdate();
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ)2 ó�� ���� ");
      return 0;
   }
   
   /**
    * �ۼ��� : ������
    * �ۼ��� : 2008.10.15
    * ��  �� : �����̰�ó���� ����������Ʈ ȣ���߰���û ����
    * ��  Ÿ : 
    */ 
   public int ProcTransForBSC3 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ)3 ó�� ���� ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      String ara = getARA(mandt, vo.getCebwzcha(), conn);
      
      sqlBuff.append("UPDATE                    \n");
      sqlBuff.append("       SAPHEE.ZCST123     \n");
      sqlBuff.append("   SET                    \n");
      sqlBuff.append("       CS123_BSU = ?      \n");
      sqlBuff.append(" WHERE 1=1                \n");
      sqlBuff.append("   AND MANDT = ?          \n");
//      sqlBuff.append("   AND CS123_UPN = ?      \n");
//      sqlBuff.append("   AND CS123_CST = ?      \n");
      sqlBuff.append("   AND CS123_PJT = ?      \n");
      sqlBuff.append("   AND CS123_HNO = ?      \n");

      int idxparam = 1;
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
      
      pstmt.setString(idxparam++, vo.getCebwzcha());
      pstmt.setString(idxparam++, mandt);
//      pstmt.setString(idxparam++, vo.getCebwzupn());
//      pstmt.setString(idxparam++, vo.getCebwzcst());
      pstmt.setString(idxparam++, vo.getCebwzpjt());
      pstmt.setString(idxparam++, vo.getCebwzhno());

      //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      pstmt.executeUpdate();
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ)3 ó�� ���� ");
      return 0;
   }

   /**
    * �ۼ��� : ������
    * �ۼ��� : 2008.10.15
    * ��  �� : �����̰�ó���� �ο���������
    * ��  Ÿ : 
    */ 
   public int ProcTransForBSC4 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ)4 ó�� ���� ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      String ara = getARA(mandt, vo.getCebwzcha(), conn);
      
      sqlBuff.append("UPDATE                    \n");
      sqlBuff.append("       SAPHEE.ZCST127     \n");
      sqlBuff.append("   SET                    \n");
      sqlBuff.append("       CS127_BSU = ?      \n");
      sqlBuff.append(" WHERE 1=1                \n");
      sqlBuff.append("   AND MANDT = ?          \n");
      sqlBuff.append("   AND CS127_UPN = ?      \n");
      sqlBuff.append("   AND CS127_CST = ?      \n");

      int idxparam = 1;
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
      
      pstmt.setString(idxparam++, vo.getCebwzcha());
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, vo.getCebwzupn());
      pstmt.setString(idxparam++, vo.getCebwzcst());

      //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      pstmt.executeUpdate();
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ)4 ó�� ���� ");
      return 0;
   }

   /**
    * �ۼ��� : ������
    * �ۼ��� : 2008.10.15
    * ��  �� : �����̰�ó���� �������庸��
    * ��  Ÿ : 
    */ 
   public int ProcTransForBSC5 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ)5 ó�� ���� ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      String ara = getARA(mandt, vo.getCebwzcha(), conn);
      
      sqlBuff.append("UPDATE                    \n");
      sqlBuff.append("       SAPHEE.ZCST141     \n");
      sqlBuff.append("   SET                    \n");
      sqlBuff.append("       CS141_BSU = ?      \n");
      sqlBuff.append(" WHERE 1=1                \n");
      sqlBuff.append("   AND MANDT = ?          \n");
      sqlBuff.append("   AND CS141_UPN = ?      \n");
      sqlBuff.append("   AND CS141_CST = ?      \n");

      int idxparam = 1;
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
      
      pstmt.setString(idxparam++, vo.getCebwzcha());
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, vo.getCebwzupn());
      pstmt.setString(idxparam++, vo.getCebwzcst());

      //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      pstmt.executeUpdate();
      //logger.info("-- ȣ�����(��ġ) ó�� ���� ");
      logger.debug("-- ȣ�����(��ġ)5 ó�� ���� ");
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
    * ��  �� : ����������Ʈ������ ���� �����̰� ó�� ������.. 
    * ��  Ÿ : 
    */ 
   public TransForBSC(Logger pLogger)
   {
      logger = pLogger;
   }
}
