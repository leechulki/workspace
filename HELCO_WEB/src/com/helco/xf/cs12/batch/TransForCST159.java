/**
 * 파 일 명 : TransForCST159.java
 * 설    명 : 
 * 작 성 자 : HSS
 * 작 성 일 : 2008-08-13 오후 4:25:21
 * 변경내역 :
 * 
 */
package com.helco.xf.cs12.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import tit.service.core.logger.Logger;

import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ZCST159Vo;

public class TransForCST159
{
   static Logger logger;
   /**
    * 작성자 : 홍순석
    * 작성일 : 2008.08.12
    * 설  명 : 보수관리대수 집계
    * 기  타 : 
    */ 
   public int ProcTransForCST159 (String mandt, Connection conn, ZCST159Vo vo) throws SQLException, Exception
   {
      logger.debug("-- 보수관리대수 집계 저장 SQL ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
		sqlBuff.append("  INSERT INTO SAPHEE.ZCST159( \n");
		sqlBuff.append("  	 MANDT             \n");
		sqlBuff.append("  	, CS159_YYM        \n");
		sqlBuff.append("  	, CS159_BSU        \n");
		sqlBuff.append("  	, CS159_NRV        \n");
		sqlBuff.append("  	, CS159_MKJ        \n");
		sqlBuff.append("  	, CS159_UKJ        \n");
		sqlBuff.append("  	, CS159_MIJ        \n");
		sqlBuff.append("  	, CS159_UIJ        \n");
		sqlBuff.append("  	, CS159_SIL        \n");
		sqlBuff.append("  	, CS159_ISJ        \n");
		sqlBuff.append("  	, CS159_JE1        \n");
		sqlBuff.append("  	, CS159_JE2        \n");
		sqlBuff.append("  	, CS159_JE3        \n");
		sqlBuff.append("  	, CS159_MKM        \n");
		sqlBuff.append("  	, CS159_UKM        \n");
		sqlBuff.append("  	, CS159_MIM        \n");
		sqlBuff.append("  	, CS159_UIM        \n");
		sqlBuff.append("  	, CS159_ME1        \n");
		sqlBuff.append("  	, CS159_ME2        \n");
		sqlBuff.append("  	, CS159_ME3        \n");
		sqlBuff.append("  	, CS159_RDT        \n");
		sqlBuff.append("  	, CS159_UDT        \n");
		sqlBuff.append("   ) VALUES (          \n");
		sqlBuff.append("  	 ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? \n");
		sqlBuff.append("   )                   \n"); 

      try
      {
          int idxparam = 1;
          pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
          
          pstmt.setString(idxparam++, vo.getMandt());
          pstmt.setString(idxparam++, vo.getCs159_yym());
    	  pstmt.setString(idxparam++, vo.getCs159_bsu());      
    	  pstmt.setInt(idxparam++, vo.getCs159_nrv());      
          pstmt.setInt(idxparam++, vo.getCs159_mkj());
          pstmt.setInt(idxparam++, vo.getCs159_ukj());
          pstmt.setInt(idxparam++, vo.getCs159_mij());
          pstmt.setInt(idxparam++, vo.getCs159_uij());
          pstmt.setInt(idxparam++, vo.getCs159_sil());
          pstmt.setInt(idxparam++, vo.getCs159_isj());
          pstmt.setInt(idxparam++, vo.getCs159_je1());
          pstmt.setInt(idxparam++, vo.getCs159_je2());
          pstmt.setInt(idxparam++, vo.getCs159_je3());
          pstmt.setInt(idxparam++, vo.getCs159_mkm());
          pstmt.setInt(idxparam++, vo.getCs159_ukm());
          pstmt.setInt(idxparam++, vo.getCs159_mim());
          pstmt.setInt(idxparam++, vo.getCs159_uim());
          pstmt.setInt(idxparam++, vo.getCs159_me1());
          pstmt.setInt(idxparam++, vo.getCs159_me2());
          pstmt.setInt(idxparam++, vo.getCs159_me3());
          pstmt.setString(idxparam++, vo.getCs159_rdt());
    	  pstmt.setString(idxparam++, vo.getCs159_udt());   

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
      logger.debug("-- 보수관리대수 집계 SQL 종료 ");
      return 0;
   }

   public int ProcTransForCST159 (String mandt, Connection conn, ArrayList list) throws SQLException, Exception
   {
      logger.debug("-- 보수관리대수 집계 저장 SQL Batch시");
      StringBuffer               sqlBuff  = null;
      java.sql.Statement stmt = null; 
      ZCST159Vo vo = null;
      
		
      stmt = conn.createStatement();
		
      for(int i=0; i < list.size();i++)
      {
    	  
        vo = new ZCST159Vo();
        vo = (ZCST159Vo) list.get(i);
        sqlBuff  = new StringBuffer();

 		sqlBuff.append("  INSERT INTO SAPHEE.ZCST159( \n");
		sqlBuff.append("  	 MANDT             \n");
		sqlBuff.append("  	, CS159_YYM        \n");
		sqlBuff.append("  	, CS159_BSU        \n");
		sqlBuff.append("  	, CS159_NRV        \n");
		sqlBuff.append("  	, CS159_MKJ        \n");
		sqlBuff.append("  	, CS159_UKJ        \n");
		sqlBuff.append("  	, CS159_MIJ        \n");
		sqlBuff.append("  	, CS159_UIJ        \n");
		sqlBuff.append("  	, CS159_SIL        \n");
		sqlBuff.append("  	, CS159_ISJ        \n");
		sqlBuff.append("  	, CS159_JE1        \n");
		sqlBuff.append("  	, CS159_JE2        \n");
		sqlBuff.append("  	, CS159_JE3        \n");
		sqlBuff.append("  	, CS159_MKM        \n");
		sqlBuff.append("  	, CS159_UKM        \n");
		sqlBuff.append("  	, CS159_MIM        \n");
		sqlBuff.append("  	, CS159_UIM        \n");
		sqlBuff.append("  	, CS159_ME1        \n");
		sqlBuff.append("  	, CS159_ME2        \n");
		sqlBuff.append("  	, CS159_ME3        \n");
		sqlBuff.append("  	, CS159_RDT        \n");
		sqlBuff.append("  	, CS159_UDT        \n");
		sqlBuff.append("   ) VALUES (          \n");
		sqlBuff.append("  	 '"+vo.getMandt()+"', \n");
		sqlBuff.append("  	 '"+vo.getCs159_yym()+"', \n");
		sqlBuff.append("  	 '"+vo.getCs159_bsu()+"', \n");
		sqlBuff.append("  	 "+vo.getCs159_nrv()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_mkj()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_ukj()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_mij()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_uij()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_sil()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_isj()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_je1()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_je2()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_je3()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_mkm()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_ukm()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_mim()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_uim()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_me1()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_me2()+", \n");
		sqlBuff.append("  	 "+vo.getCs159_me3()+", \n");
		sqlBuff.append("  	 '"+vo.getCs159_rdt()+"', \n");
		sqlBuff.append("  	 '"+vo.getCs159_udt()+"' \n");
		sqlBuff.append("   )                   \n"); 

        stmt.addBatch(sqlBuff.toString());

      }		
      try
      {
          stmt.executeBatch();
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         BatchConstants.close(null,stmt,null);            
      }
      logger.debug("-- 보수관리대수 집계 SQL Batch종료 ");
      return 0;
   }
   
   public int delProcForCST159 (String mandt, Connection conn, String yym) throws SQLException, Exception
   {
      logger.debug("-- 보수관리대수 삭제 시작 ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
		sqlBuff.append("  DELETE FROM SAPHEE.ZCST159 \n");
		sqlBuff.append("   WHERE MANDT = ?           \n");
		sqlBuff.append("  	AND CS159_YYM = ?       \n"); 

      try
      {
       	  int idxparam = 1;
          pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
          
          pstmt.setString(idxparam++, mandt);
          pstmt.setString(idxparam++, yym);
         
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
      logger.debug("-- 보수관리대수 삭제 종료 ");
      return 0;
   }

   public TransForCST159(Logger pLogger)
   {
      logger = pLogger;
   }
}