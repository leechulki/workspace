/**
 * 파 일 명 : TransForBSC.java
 * 설    명 : 
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-05-24 오후 4:25:21
 * 변경내역 :
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
    * 작성자 : 이주형
    * 작성일 : 2008.10.15
    * 설  명 : 점검이관처리중 통합프로젝트정보 갱신(유상보수의 경우) - ZMASTER02
    * 기  타 : 
    */ 
   public int ProcTransForBSC (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치) 처리 종료 ");
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
      
      pstmt.setString(idxparam++, vo.getCebwzcha()); // 이관 후, 보수협력사
      pstmt.setString(idxparam++, bpm); // 보수PM
     
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, vo.getCebwzpjt());
      pstmt.setString(idxparam++, CommonUtil.removeSpace(vo.getCebwzpjt()) + CommonUtil.removeSpace(vo.getCebwzhno()));

      //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      pstmt.executeUpdate();
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치) 처리 종료 ");
      return 0;
   }

   /**
    * 작성자 : 이주형
    * 작성일 : 2008.10.15
    * 설  명 : 점검이관처리중 통합프로젝트정보 갱신(유상보수의 경우) - ZMASTER01
    * 기  타 : 
    */ 
   public int ProcTransForBSC2 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치)2 처리 종료 ");
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
      
      pstmt.setString(idxparam++, ara); // 지역
     
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, vo.getCebwzpjt());

      //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      pstmt.executeUpdate();
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치)2 처리 종료 ");
      return 0;
   }
   
   /**
    * 작성자 : 이주형
    * 작성일 : 2008.10.15
    * 설  명 : 점검이관처리중 통합프로젝트 호기추가요청 정보
    * 기  타 : 
    */ 
   public int ProcTransForBSC3 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치)3 처리 종료 ");
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
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치)3 처리 종료 ");
      return 0;
   }

   /**
    * 작성자 : 이주형
    * 작성일 : 2008.10.15
    * 설  명 : 점검이관처리중 인원상주정보
    * 기  타 : 
    */ 
   public int ProcTransForBSC4 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치)4 처리 종료 ");
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
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치)4 처리 종료 ");
      return 0;
   }

   /**
    * 작성자 : 이주형
    * 작성일 : 2008.10.15
    * 설  명 : 점검이관처리중 실패현장보고
    * 기  타 : 
    */ 
   public int ProcTransForBSC5 (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치)5 처리 종료 ");
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
      //logger.info("-- 호기관리(설치) 처리 종료 ");
      logger.debug("-- 호기관리(설치)5 처리 종료 ");
      return 0;
   }

   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.25
    * 설  명 : 협력사 코드에 대한 보수 PM 정보 가져오기 
    * 기  타 :
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
    * 작성자 : 구자경
    * 작성일 : 2006.05.25
    * 설  명 : 협력사 코드에 대한 지역 정보 가져오기 
    * 기  타 :
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
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 통합프로젝트정보에 대한 점검이관 처리 생성자.. 
    * 기  타 : 
    */ 
   public TransForBSC(Logger pLogger)
   {
      logger = pLogger;
   }
}
