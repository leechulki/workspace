/**
 * 파 일 명 : TransForBXL.java
 * 설    명 : 
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-05-24 오후 4:26:04
 * 변경내역 :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.cs12.batch;

import java.sql.Connection;
import java.sql.SQLException;

import tit.service.core.logger.Logger;

import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class TransForBXL
{
   static Logger logger;
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 점검이관처리에 대한 매출계획 정보 갱신(유상의 경우)
    * 기  타 : 
    */ 
   public int ProcTransForBXL (String mandt, Connection conn, TBEBWZF1Vo vo, String seq) throws SQLException, Exception
   {
/*
	   //logger.info("-- 매출계획 처리 시작 ");
      logger.debug("-- 매출계획 처리 시작 ");
      
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
	  String                     before_jrol     = "";      
      
      sqlBuff.append(" UPDATE                        \n");
      sqlBuff.append("        SAPHEE.ZCST131         \n");
      sqlBuff.append("    SET                        \n");
      sqlBuff.append("        CS131_BSU =  ?,        \n");
      sqlBuff.append("        CS131_GBU =  ?         \n");
      sqlBuff.append("  WHERE 1=1                    \n");
      sqlBuff.append("    AND MANDT =  ?             \n");
      sqlBuff.append("    AND CS131_UPN =  ?         \n");
      sqlBuff.append("    AND CS131_CST =  ?         \n");
      sqlBuff.append("    AND CS131_PJT =  ?         \n");
      sqlBuff.append("    AND CS131_HNO =  ?         \n");
      sqlBuff.append("    AND CS131_SEQ =  ?         \n");
      sqlBuff.append("    AND CS131_GND =  ?         \n");
      sqlBuff.append("    AND CS131_MYM >=  ?        \n");
      sqlBuff.append("    AND RTRIM(CS131_MSA) =  '' \n");
 
      try
      {
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
    
         pstmt.setString(idxparam++, vo.getCebwzcha());
         pstmt.setString(idxparam++, vo.getCebwzgha());

         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzupn());
         pstmt.setString(idxparam++, vo.getCebwzcst());
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         pstmt.setString(idxparam++, vo.getCebwzseq());
         pstmt.setString(idxparam++, vo.getCebwzgnd());
         pstmt.setString(idxparam++, vo.getCebwzigm());
         
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
      
      //logger.info("-- 매출계획 처리 종료 ");
      logger.debug("-- 매출계획 처리 종료 ");
      return 0;
*/
      //logger.info("-- 매출계획 처리 시작 ");
      logger.debug("-- 매출계획 처리 시작 ");
      
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
	  String                     before_jrol     = "";      
      
		sqlBuff.append(" UPDATE                                                                                         \n");
		sqlBuff.append("        SAPHEE.ZCST131 A                                                                        \n");
		sqlBuff.append("    SET                                                                                         \n");
		sqlBuff.append("        (                                                                                       \n");
		sqlBuff.append("         A.CS131_ARA,                                                                           \n");
		sqlBuff.append("         A.CS131_BSU,                                                                           \n");
		sqlBuff.append("         A.CS131_GBU,                                                                           \n");
		sqlBuff.append("         A.CS131_MYB,                                                                           \n");
		sqlBuff.append("         A.CS131_MSA,                                                                           \n");
		sqlBuff.append("         A.CS131_AGB,                                                                           \n");
		sqlBuff.append("         A.AEDAT,                                                                               \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.10.29 Han J.H
		sqlBuff.append("         A.AEZEIT                                                                               \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.10.29 Han J.H
		sqlBuff.append("        )                                                                                       \n");
		sqlBuff.append("        =                                                                                       \n");
		sqlBuff.append("        (                                                                                       \n");
		sqlBuff.append("         SELECT                                                                                 \n");
		sqlBuff.append("                B.BSU_ARA,                                                                      \n");
		sqlBuff.append("                (SELECT C.LGORT FROM SAPHEE.ZMMT005 C WHERE C.MANDT = ? AND C.LIFNR = B.LIFNR), \n");
		sqlBuff.append("                B.BSU_GB,                                                                       \n");
		sqlBuff.append("                CASE B.BSU_GB WHEN '1' THEN '' WHEN '3' THEN '' ELSE '' END,                    \n");
		sqlBuff.append("                CASE B.BSU_GB WHEN '1' THEN '09' WHEN '3' THEN '' ELSE '' END,                  \n");
		sqlBuff.append("                CAST(? AS VARCHAR(1)),                                                          \n");
		sqlBuff.append("                HEX(CURRENT DATE) AS AEDAT,                                                     \n");
		sqlBuff.append("                HEX(CURRENT TIME) AS AEZEIT                                                     \n");
		sqlBuff.append("           FROM                                                                                 \n");
		sqlBuff.append("                SAPHEE.ZWBT010 B                                                                \n");
		sqlBuff.append("          WHERE                                                                                 \n");
		sqlBuff.append("                B.MANDT = ?                                                                     \n");
		sqlBuff.append("            AND B.LGORT = ?                                                                     \n");
		sqlBuff.append("        )                                                                                       \n");
		sqlBuff.append("  WHERE 1=1                                                                                     \n");
		sqlBuff.append("    AND A.MANDT     =  ?                                                                        \n");
//		sqlBuff.append("    AND A.CS131_UPN =  ?                                                                        \n");
//		sqlBuff.append("    AND A.CS131_CST =  ?                                                                        \n");
		sqlBuff.append("    AND A.CS131_PJT =  ?                                                                        \n");
		sqlBuff.append("    AND A.CS131_HNO =  ?                                                                        \n");
//		sqlBuff.append("    AND A.CS131_SEQ >= ?                                                                        \n");
		sqlBuff.append("    AND A.CS131_GND =  ?                                                                        \n");
		sqlBuff.append("    AND A.CS131_YYM >= ?                                                                        \n");
		sqlBuff.append("    AND RTRIM(A.CS131_MYB) =  ''                                                                \n");
 
      try
      {
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
    
		pstmt.setString(idxparam++, mandt);
		pstmt.setString(idxparam++, vo.getCebwzgba());
		pstmt.setString(idxparam++, mandt);
		pstmt.setString(idxparam++, vo.getCebwzcha());
		pstmt.setString(idxparam++, mandt);
//		pstmt.setString(idxparam++, vo.getCebwzupn());
//		pstmt.setString(idxparam++, vo.getCebwzcst());
		pstmt.setString(idxparam++, vo.getCebwzpjt());
		pstmt.setString(idxparam++, vo.getCebwzhno());
//		pstmt.setString(idxparam++, seq);
		pstmt.setString(idxparam++, vo.getCebwzgnd());
		pstmt.setString(idxparam++, vo.getCebwzigm());
         
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
      
      //logger.info("-- 매출계획 처리 종료 ");
      logger.debug("-- 매출계획 처리 종료 ");
      return 0;
   }
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 매출계획 정보에 대한 점검이관 처리 생성자.. 
    * 기  타 : 
    */ 

   public TransForBXL(Logger pLogger)
   {
      logger = pLogger;
   }
}
