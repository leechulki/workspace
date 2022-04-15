/**
 * 파 일 명 : TransForBWZ.java
 * 설    명 : 
 * 작 성 자 : ohb
 * 작 성 일 : 2006-09-09 오후 4:25:21
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

import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class TransForBWZ
{
   static Logger logger;
   /**
    * 작성자 : 오현범
    * 작성일 : 2006.09.09
    * 설  명 : 점검이관정보 작업결과 / 작업사유
    * 기  타 : 
    */ 
   public int ProcTransForBWZ (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- 점검이관정보 작업 결과 ");
      logger.debug("-- 점검이관정보 작업 결과 ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      sqlBuff.append(" UPDATE                     \n");
      sqlBuff.append("        SAPHEE.ZCST157      \n");
      sqlBuff.append("    SET                     \n");
      sqlBuff.append("        CS157_IJU =  ?,     \n");
      sqlBuff.append("        CS157_RLT =  ?,     \n");
      sqlBuff.append("        CS157_RSN =  ?      \n");
      sqlBuff.append("  WHERE 1=1                 \n");
      sqlBuff.append("    AND MANDT =  ?          \n");
      sqlBuff.append("    AND CS157_UPN =  ?      \n");
      sqlBuff.append("    AND CS157_CST =  ?      \n");
      sqlBuff.append("    AND CS157_PJT =  ?      \n");
      sqlBuff.append("    AND CS157_HNO =  ?      \n");
      sqlBuff.append("    AND CS157_IGM =  ?      \n");

      int idxparam = 1;
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
      
      pstmt.setString(idxparam++, vo.getCebwziju());
	  pstmt.setString(idxparam++, vo.getCebwzrlt());
	  pstmt.setString(idxparam++, vo.getCebwzrsn()); 
     
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, vo.getCebwzupn());
	  pstmt.setString(idxparam++, vo.getCebwzcst());      
	  pstmt.setString(idxparam++, vo.getCebwzpjt());      
      pstmt.setString(idxparam++, vo.getCebwzhno());
	  pstmt.setString(idxparam++, DateTime.getDateString("yyyyMM"));      

      //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      pstmt.executeUpdate();
      //logger.info("-- 점검이관정보 작업 결과 작성 종료 ");
      logger.debug("-- 점검이관정보 작업 결과 작성 종료 ");
      return 0;
   }
   
   /**
    * 작성자 : 이주형
    * 작성일 : 2008.10.07
    * 설  명 : PRPS 테이블에 LIFNR 넣어주기
    * 기  타 : 
    */ 
   public int ProcTransForPRPS (String mandt, Connection conn) throws SQLException, Exception
   {
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      sqlBuff.append(" UPDATE                                       \n");
      sqlBuff.append("        SAPHEE.ZV_PRPS AS D                   \n");
      sqlBuff.append("    SET                                       \n");
      sqlBuff.append("        D.ZZLIFNR = VALUE((                   \n");
      sqlBuff.append("                                              \n");
      sqlBuff.append("   SELECT                                     \n");
      sqlBuff.append("          MIN(B.LIFNR)                        \n");
      sqlBuff.append("     FROM                                     \n");
      sqlBuff.append("          SAPHEE.ZCST126 AS A,                \n");
      sqlBuff.append("          SAPHEE.ZMMT005 AS B,                \n");
      sqlBuff.append("          SAPHEE.PRPS AS C                    \n");
      sqlBuff.append("    WHERE                                     \n");
      sqlBuff.append("          A.MANDT = ?                         \n");
      sqlBuff.append("      AND A.MANDT = B.MANDT                   \n");
      sqlBuff.append("      AND A.CS126_BSU = B.LGORT               \n");
      sqlBuff.append("      AND A.MANDT = C.MANDT                   \n");
      sqlBuff.append("      AND A.CS126_GNO = C.POSID               \n");
      sqlBuff.append("      AND C.MANDT = D.MANDT                   \n");
      sqlBuff.append("      AND C.PSPNR = D.PSPNR                   \n");
      sqlBuff.append("      AND C.POSID LIKE '______U%'             \n");
      sqlBuff.append("      AND C.ZZLIFNR = ''                      \n");
      sqlBuff.append("                                              \n");
      sqlBuff.append("   ),'' )                                     \n");
      sqlBuff.append(" WHERE                                        \n");
      sqlBuff.append("       D.MANDT = ?                            \n");
      sqlBuff.append("   AND D.ZZLIFNR = ''                         \n");
      sqlBuff.append("   AND EXISTS (                               \n");
      sqlBuff.append("               SELECT                         \n");
      sqlBuff.append("                      1                       \n");
      sqlBuff.append("                 FROM                         \n");
      sqlBuff.append("                      SAPHEE.ZCST126 AS A,    \n");
      sqlBuff.append("                      SAPHEE.ZMMT005 AS B,    \n");
      sqlBuff.append("                      SAPHEE.PRPS AS C        \n");
      sqlBuff.append("                WHERE                         \n");
      sqlBuff.append("                      A.MANDT = ?             \n");
      sqlBuff.append("                  AND A.MANDT = B.MANDT       \n");
      sqlBuff.append("                  AND A.CS126_BSU = B.LGORT   \n");
      sqlBuff.append("                  AND A.MANDT = C.MANDT       \n");
      sqlBuff.append("                  AND A.CS126_GNO = C.POSID   \n");
      sqlBuff.append("                  AND C.MANDT = D.MANDT       \n");
      sqlBuff.append("                  AND C.PSPNR = D.PSPNR       \n");
      sqlBuff.append("                  AND C.POSID LIKE '______U%' \n");
      sqlBuff.append("                  AND C.ZZLIFNR = ''          \n");
      sqlBuff.append("              )                               \n");

      int idxparam = 1;
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, mandt);
      pstmt.setString(idxparam++, mandt);
      pstmt.executeUpdate();

      return 0;
   }
   
   /**
    * 작성자 : 오현범
    * 작성일 : 2006.09.29
    * 설  명 : 점검이관 정보 작업결과 처리 생성자.. 
    * 기  타 : 
    */ 
   public TransForBWZ(Logger pLogger)
   {
      logger = pLogger;
   }
}