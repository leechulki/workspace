/**
 * 파 일 명 : TransForBSG.java
 * 설    명 : 
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-05-24 오후 4:24:29
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
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class TransForBSG
{
   static Logger logger;
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 점검이관처리중 협력사(BSG)의 점검자이력 정보 갱신
    * 기  타 : 
    */ 
   public int ProcTransForBSG (String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      //logger.info("-- 점검자이력 처리 시작 ");
      logger.debug("-- 점검자이력 처리 시작 ");

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
      
      sqlBuff.append(" INSERT INTO SAPHEE.ZCST162    \n");
      sqlBuff.append("             (                 \n");
      sqlBuff.append("              MANDT,           \n");
      sqlBuff.append("              CS162_PJT,       \n");
      sqlBuff.append("              CS162_HNO,       \n");
      sqlBuff.append("              CS162_SEQ,       \n");
      sqlBuff.append("              CS162_CDT,       \n");
      sqlBuff.append("              CS162_BJU,       \n");
      sqlBuff.append("              CS162_AJU,       \n");
      sqlBuff.append("              CS162_BBU,       \n");
      sqlBuff.append("              CS162_ABU,       \n");
      sqlBuff.append("              CS162_USR,       \n");
      sqlBuff.append("              CS162_DSC        \n");
      sqlBuff.append("             )                 \n");
      sqlBuff.append("             VALUES            \n");
      sqlBuff.append("             (                 \n");
      sqlBuff.append("              ?,               \n"); // mandt
      sqlBuff.append("              ?,               \n"); // 원프로젝트No
      sqlBuff.append("              ?,               \n"); // 호기번호
//      sqlBuff.append("              ?,               \n"); // seq
      sqlBuff.append("              (SELECT VALUE(MAX(CS162_SEQ),0) + 1 FROM SAPHEE.ZCST162 WHERE MANDT = ? AND CS162_PJT = ? AND CS162_HNO = ?), \n"); // seq
      sqlBuff.append("              ?,               \n"); // 시스템날자
      sqlBuff.append("              ?,               \n"); // 변경전 주점검자
      sqlBuff.append("              ?,               \n"); // 변경후 주점검자
      sqlBuff.append("              ?,               \n"); // 변경전 부점검자
      sqlBuff.append("              ?,               \n"); // 변경후 부점검자
      sqlBuff.append("              ' ',             \n");
      sqlBuff.append("              '점검이관 작업'     \n");
      sqlBuff.append("             )                 \n");

      try
      {
/* 2010.08.30 LJH 수정(주/부점검자 변경) */
//         String bju = BatchConstants.getCcsb1emp(mandt, vo.getCebwzchb(), before_jrol, conn);
//         String bbu = BatchConstants.getCcsb1emp(mandt, vo.getCebwzchb(), before_brol, conn);
//         String aju = BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), after_jrol, conn);
//         String abu = BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), after_brol, conn);
         String bju = vo.getCebwzjub();
         String bbu = vo.getCebwzbub();
         String aju = vo.getCebwzjua();
         String abu = vo.getCebwzbua();
/* */
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
//         pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwzseq()));
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         pstmt.setString(idxparam++, DateTime.getShortDateString());
         pstmt.setString(idxparam++, bju);
         pstmt.setString(idxparam++, aju);
         pstmt.setString(idxparam++, bbu);
         pstmt.setString(idxparam++, abu);

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
      
      //logger.info("-- 점검자이력 처리 종료 ");
      logger.debug("-- 점검자이력 처리 종료 ");
      return 0;
   }
   
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 협력사(BSG)에 대한 점검이관 처리 생성자.. 
    * 기  타 : 
    */ 
   public TransForBSG(Logger pLogger)
   {
      logger = pLogger;
   }
}
