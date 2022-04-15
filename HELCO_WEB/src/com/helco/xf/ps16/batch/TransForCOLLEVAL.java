/**
 * 파 일 명 : TransForCOLLEVAL.java
 * 설    명 : 협력사평가 협력도평가 협력사별 처리 BATCH(EL)
 * 작 성 자 : swn
 * 작 성 일 : 2019-12-18 12:10
 * 변경내역 :
 *
 */
package com.helco.xf.ps16.batch;

import java.sql.Connection;
import java.sql.SQLException;

import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.batch.BatchConstants;

public class TransForCOLLEVAL
{
   static Logger logger;
   /**
    * 작성자 : swn
    * 작성일 : 2019-12-18
    * 설  명 : 협력사평가 집계 처리
    * 기  타 :
    */
   public int ProcTransForCOLLEVAL (String mandt,String scrGubun, Connection conn) throws SQLException, Exception
   {
      SqlExecutor db = null;
      SqlResult result = null;
      SqlResult resultVar = null;
      SqlResult resultMonths = null;
      DatasetSqlRequest masterMonths = null;
      DatasetSqlRequest master = null;
      DatasetSqlRequest masterVar = null;
      Dataset returnDs = null;
      Dataset returnDsVar = null;
      Dataset returnDsMonths = null;
     db = new DatasetSqlExecutor(conn);
     String today = CommonUtil.getToday2();
     //for test1
      today = "20190705"; // 상반기
     // today = "20200105";  //하반기

     String months = today.substring(4,6);
     String years = today.substring(0,4);
     String gubun1 = "";
     if ( "01".equals(months)) {
         System.out.println("#### this time  " +today);
         gubun1 = "2";
     } else if ( "07".equals(months)) {
         System.out.println("#### this time  " +today);
         gubun1 = "1";
     } else {
         System.out.println("#### not this time  " +today);
         return 0;
     }

     String months6 = CommonUtil.addMonthDate(-6);
     String months1 = CommonUtil.addMonthDate(-1);
     //for test2
       months6 = "20190101";  //상반기
       months1 = "20190630";  //상반기
     //  months6 = "20190701";   //하반기
     //  months1 = "20191201";   //하반기
     String months62 = months6.replace("-","").substring(0,6);
     String months12 = months1.replace("-","").substring(0,6);

     masterMonths = SqlMapManagerUtility.makeSqlRequestForDataset("ps16:PS1601009A_S7");
     masterMonths.addParamObject("YYYYMMF", months62);
     masterMonths.addParamObject("YYYYMMT", months12);
     resultMonths = db.query(masterMonths);
     returnDsMonths = (Dataset)resultMonths.getResultObject();
     if (returnDsMonths.getRowCount() <= 0 ) {
         System.out.println("####returnDsMonths error = [" + returnDsMonths.getRowCount());
         return 0;
     }
     String YYYYMMDD0 = "";
     String YYYYMMDD1 = "";
     String YYYYMMDD2 = "";
     String YYYYMMDD3 = "";
     String YYYYMMDD4 = "";
     String YYYYMMDD5 = "";

     for(int i=0; i<returnDsMonths.getRowCount(); i++) {
         if (i == 6) break;
        
         if (i == 0) {
             String YYYYMMDD01=returnDsMonths.getColumnAsString(i, "DIFFMONTHS").toString().substring(0,8);
             YYYYMMDD0 = YYYYMMDD01.substring(0,6) + CommonUtil.getLastDay(YYYYMMDD01);
         }
         else if (i == 1){
             String YYYYMMDD11=returnDsMonths.getColumnAsString(i, "DIFFMONTHS").toString().substring(0,8);
             YYYYMMDD1 = YYYYMMDD11.substring(0,6) + CommonUtil.getLastDay(YYYYMMDD11);
         }
         else if (i == 2){
             String YYYYMMDD21=returnDsMonths.getColumnAsString(i, "DIFFMONTHS").toString().substring(0,8);
             YYYYMMDD2 = YYYYMMDD21.substring(0,6) + CommonUtil.getLastDay(YYYYMMDD21);
         }
         else if (i == 3){
             String YYYYMMDD31=returnDsMonths.getColumnAsString(i, "DIFFMONTHS").toString().substring(0,8);
             YYYYMMDD3 = YYYYMMDD31.substring(0,6) + CommonUtil.getLastDay(YYYYMMDD31);
         }
         else if (i == 4){
             String YYYYMMDD41=returnDsMonths.getColumnAsString(i, "DIFFMONTHS").toString().substring(0,8);
             YYYYMMDD4 = YYYYMMDD41.substring(0,6) + CommonUtil.getLastDay(YYYYMMDD41);
         }
         else if (i == 5){
             String YYYYMMDD51=returnDsMonths.getColumnAsString(i, "DIFFMONTHS").toString().substring(0,8);
             YYYYMMDD5 = YYYYMMDD51.substring(0,6) + CommonUtil.getLastDay(YYYYMMDD51);
         }
     }
     
     String VAR1 = "";
     String VAR2 = "";
     String VAR3 = "";
     String VAR4 = "";
     String VAR5 = "";
     String VAR6 = "";
     String VAR7 = "";
     String VAR8 = "";
     String VAR9 = "";
     String VAR10 = "";
     String VAR11 = "";
     String VAR12 = "";
     String VAR13 = "";
     String VAR14 = "";
     masterVar = SqlMapManagerUtility.makeSqlRequestForDataset("ps16:PS1601011A_S8");
     masterVar.addParamObject("G_MANDT", "183");
     masterVar.addParamObject("GUBUN1", "1");
     masterVar.addParamObject("ORD", "1");
     resultVar = db.query(masterVar);
     returnDsVar = (Dataset)resultVar.getResultObject();
     if (returnDsVar.getRowCount() <= 0 ) {
         System.out.println("####returnDsVar error = [" + returnDsVar.getRowCount());
         return 0;
     }
     for(int i=0; i<returnDsVar.getRowCount(); i++) {
         
         if ( returnDsVar.getColumnAsString(i, "SCR_ID").equals( "1601001")) 
         {
             VAR1 = returnDsVar.getColumnAsString(i,"VAR1").toString();  
         }
         else if ( returnDsVar.getColumnAsString(i,"SCR_ID").equals( "1601002")) 
         {
             VAR2 = returnDsVar.getColumnAsString(i,"VAR1").toString();  
             VAR3 = returnDsVar.getColumnAsString(i,"VAR2").toString();  
             VAR4 = returnDsVar.getColumnAsString(i,"VAR3").toString();  
             VAR5 = returnDsVar.getColumnAsString(i,"VAR4").toString();   
             
         }
         else if ( returnDsVar.getColumnAsString(i,"SCR_ID").equals( "1601004")) 
         {
             VAR6 = returnDsVar.getColumnAsString(i,"VAR1").toString();   
             VAR7 = returnDsVar.getColumnAsString(i,"VAR2").toString(); 
             VAR8 = returnDsVar.getColumnAsString(i,"VAR3").toString();  
             VAR9 = returnDsVar.getColumnAsString(i,"VAR4").toString();   
         }
         else if ( returnDsVar.getColumnAsString(i,"SCR_ID").equals( "1601005")) 
         {  
             VAR10 = returnDsVar.getColumnAsString(i,"VAR1").toString();   
         }
         else if ( returnDsVar.getColumnAsString(i,"SCR_ID").equals( "1601006"))   
         {
             VAR11 = returnDsVar.getColumnAsString(i,"VAR1").toString();   
         }
         else if ( returnDsVar.getColumnAsString(i,"SCR_ID").equals( "1601008"))
         {
             VAR12 = returnDsVar.getColumnAsString(i,"VAR1").toString();   
         }
         else if ( returnDsVar.getColumnAsString(i,"SCR_ID").equals( "1601009"))
         {
             VAR13 = returnDsVar.getColumnAsString(i,"VAR1").toString();   
             VAR14 = returnDsVar.getColumnAsString(i,"VAR2").toString();   
         }
     }
     years = YYYYMMDD0.substring(0,4);
      master = SqlMapManagerUtility.makeSqlRequestForDataset("ps16:PS1601010A_S1");
      master.addParamObject("G_MANDT", "183");
      master.addParamObject("YYYYMMDD0", YYYYMMDD0);
      master.addParamObject("YYYYMMDD1", YYYYMMDD1);
      master.addParamObject("YYYYMMDD2", YYYYMMDD2);
      master.addParamObject("YYYYMMDD3", YYYYMMDD3);
      master.addParamObject("YYYYMMDD4", YYYYMMDD4);
      master.addParamObject("YYYYMMDD5", YYYYMMDD5);
      master.addParamObject("CHK_GBN", "10");
      master.addParamObject("CHK_GBN2", "01");
      master.addParamObject("CHK_GBN3", "A");
      master.addParamObject("VAR1",  VAR1);
      master.addParamObject("VAR2",  VAR2);
      master.addParamObject("VAR3",  VAR3);
      master.addParamObject("VAR4",  VAR4);
      master.addParamObject("VAR5",  VAR5);
      master.addParamObject("VAR6",  VAR6);
      master.addParamObject("VAR7",  VAR7);
      master.addParamObject("VAR8",  VAR8);
      master.addParamObject("VAR9",  VAR9);
      master.addParamObject("VAR10", VAR10);
      master.addParamObject("VAR11", VAR11);
      master.addParamObject("VAR12", VAR12);
      master.addParamObject("VAR13", VAR13);
      master.addParamObject("VAR14", VAR14);

      master.addParamObject("YYYYMMF", YYYYMMDD0.toString().substring(0,6));
      master.addParamObject("YYYYMMT", YYYYMMDD5.toString().substring(0,6));
      result = db.query(master);
      returnDs = (Dataset)result.getResultObject();
     // System.out.println("PS1601010A_S1 getRowCount!!"+ returnDs.getRowCount());
      if(returnDs.getRowCount() != 0){
          System.out.println("==============================================================================");
    	  System.out.println("협력사평가 협력도평가 협력사별 처리 BATCH(EL)는 반기에 한번 실행됩니다.협력사평가 협력도평가 협력사별처리 BATCH가 이미 실행되었습니다.");
          System.out.println("==============================================================================\n");
    	  //return 0;
      }

      //SAPHEE.ZPSTW1701 테이블에 N건 INSERT한다.
      StringBuffer               sqlBuff   = new StringBuffer();
      LoggablePreparedStatement  pstmt     = null;
      System.out.println("####ZPSTW1701 insert STart!!");
      sqlBuff.append("  INSERT INTO SAPHEE.ZPSTW1701 (   \n");  
      sqlBuff.append("  MANDT                            \n");
     sqlBuff.append("  ,TOT_YYYY                         \n");
     sqlBuff.append("  ,GUBUN1                           \n");
     sqlBuff.append("  ,GUBUN2                           \n"); 
     sqlBuff.append("  ,SCR_GUBUN                        \n");
     sqlBuff.append("  ,TEAMNO                           \n");
     sqlBuff.append("  ,ZZACTSS                          \n"); 
     sqlBuff.append("  ,LIFNO                            \n");
     sqlBuff.append("  ,PRST_PERNO                       \n");
     sqlBuff.append("  ,TEMNO                            \n");
     sqlBuff.append("  ,QC_RT                            \n");
     sqlBuff.append("  ,QC_POINT                         \n"); 
     sqlBuff.append("  ,JQPR_CNT                         \n");
     sqlBuff.append("  ,JQPR_POINT                       \n");
     sqlBuff.append("  ,AVG_PER_CNT                      \n");
     sqlBuff.append("  ,AVG_PER_POINT                    \n");
     sqlBuff.append("  ,INP_RT                           \n");
     sqlBuff.append("  ,INP_POINT                        \n");
     sqlBuff.append("  ,PER_INST_CNT                     \n");
     sqlBuff.append("  ,PER_INST_POINT                   \n");
     sqlBuff.append("  ,SAF_ACCT_CNT                     \n");
     sqlBuff.append("  ,SAF_ACCT_POINT                   \n");
     sqlBuff.append("  ,EVAL_IMNO_CNT                    \n");
     sqlBuff.append("  ,EVAL_IMNO_POINT                  \n");
     sqlBuff.append("  ,PER_PER_CNT                      \n");
     sqlBuff.append("  ,PER_PER_POINT                    \n");
     sqlBuff.append("  ,MINWON_CNT                       \n");
     sqlBuff.append("  ,MINWON_POINT                     \n");
     sqlBuff.append("  ,TEAM_MANG_1                      \n");
     sqlBuff.append("  ,TEAM_MANG_2                      \n");
     sqlBuff.append("  ,TEAM_MANG_3                      \n");
     sqlBuff.append("  ,TEAM_MANG_POINT                  \n"); 
     sqlBuff.append("  ,BRCH_MANG_1                      \n");
     sqlBuff.append("  ,BRCH_MANG_2                     \n");
     sqlBuff.append("  ,BRCH_MANG_3                     \n");
     sqlBuff.append("  ,BRCH_MANG_POINT                 \n"); 
     sqlBuff.append("  ,DRCT_1                          \n");
     sqlBuff.append("  ,DRCT_2                          \n");
     sqlBuff.append("  ,DRCT_3                          \n");
     sqlBuff.append("  ,DRCT_POINT                      \n");
     sqlBuff.append("  ,TOT_POINT                      \n");
     sqlBuff.append("  ) VALUES (                       \n");
     sqlBuff.append("     '183'   --mandt CHAR    4   NOT NULL    PK \n");
     sqlBuff.append("   ,  ?      --집계년도 CHAR    4   NOT NULL    PK \n");
     sqlBuff.append("  ,  ?     --반기구분 CHAR    1   NOT NULL  (1:상반기  2: 하반기)  PK \n");
     sqlBuff.append("  ,  '10'    --설치구분 CHAR    1   NOT NULL    PK \n");
     sqlBuff.append("  ,  '1'     --화면구분 CHAR    1   NOT NULL    PK (1:설치협력사  , 2: 팀장별) \n");
     sqlBuff.append("  ,  ?   --팀번호  CHAR    2   NOT NULL    PK \n");
     sqlBuff.append("  ,  ?   --지사번호 CHAR    3   NOT NULL     \n");
     sqlBuff.append("  ,  ?   --업체번호 CHAR    10  NOT NULL    PK \n");
     sqlBuff.append("  ,  ?   --대표자사원번호  CHAR    6   NULL     \n");
     sqlBuff.append("  ,  ?   --팀장번호 CHAR    6           NULL     \n");
     sqlBuff.append("  ,  ?   --QC합격율    INT4    10.1       NOT NULL     \n");
     sqlBuff.append("  ,  ?   --품질환산점수   INT4    10.1     NOT NULL     \n");
     sqlBuff.append("  ,  ?   --JQPR귀책건수 INT1    10.1     NOT NULL     \n");
     sqlBuff.append("  ,  ?   --자재환산점수   INT4    10.1     NOT NULL     \n");
     sqlBuff.append("  ,  ?   --평균보유인원   INT1    10.1     NOT NULL     \n");
     sqlBuff.append("  ,  ?   --인원환산점수   INT4    10.1     NOT NULL     \n");
     sqlBuff.append("  ,  ?   --투입률  INT1          10.1    NOT NULL     \n");
     sqlBuff.append("  ,  ?   --작업환산점수4  INT4    10.1    NOT NULL     \n");
     sqlBuff.append("  ,  ?   --인당기성설치대수 INT1    10.2    NOT NULL     \n");
     sqlBuff.append("  ,  ?   --설치환산점수5  INT4    10.2    NOT NULL     \n");
     sqlBuff.append("  ,  ?   --안전사고 INT1         10      NOT NULL    \n");
     sqlBuff.append("  ,  ?   --사고환산점수   INT4    10.1    NOT NULL     \n");
     sqlBuff.append("  ,  ?   --중대재해위반   INT1    10  NOT NULL     \n");
     sqlBuff.append("  ,  ?   --점검환산점수   INT4    10.1    NOT NULL     \n");
     sqlBuff.append("  ,  ?   --인당대수 INT1        10.2    NOT NULL     \n");
     sqlBuff.append("  ,  ?   --준공환산점수   INT4    10.1    NOT NULL     \n");
     sqlBuff.append("  ,  ?   --민원건수 INT1        10  NOT NULL         \n");
     sqlBuff.append("  ,  ?   --민원환산점수   INT4    10.1    NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --팀장상  CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --팀장중  CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --팀장하  CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  0   --팀장점수 INT1    5   NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --지사장상 CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --지사장중 CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --지사장하 CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  0   --지사장정수    INT1    5   NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --본부장상 CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --본부장중 CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  '0'   --본부장하 CHAR    1   NOT NULL     \n");
     sqlBuff.append("  ,  0   --본부장점수    INT1    5   NOT NULL    \n"); 
     sqlBuff.append("  ,  ?   --총점수   INT4    10.2    NOT NULL     \n");
     sqlBuff.append("  ) \n");

  
      try
      {
    	 //ZPSTW1701 INSERT 
        
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for(int i=0; i<returnDs.getRowCount(); i++) {
            // System.out.println("####ZPSTW1701 insert LIFNO ");
             
             pstmt.setString(1,  years);   
             pstmt.setString(2,  gubun1);          //(1:상반기  2: 하반기)
             pstmt.setString(3, returnDs.getColumnAsString(i, "TEAM_CD"));  
             pstmt.setString(4, returnDs.getColumnAsString(i, "ZZACTSS_CD"));
             pstmt.setString(5, returnDs.getColumnAsString(i, "LIFNR"));
             pstmt.setString(6, returnDs.getColumnAsString(i, "PRST_PERNO"));
             pstmt.setString(7, returnDs.getColumnAsString(i, "TEMNO"));
             pstmt.setDouble(8, returnDs.getColumnAsDouble(i, "QC_RT"));
             pstmt.setDouble(9, returnDs.getColumnAsDouble(i, "QC_POINT"));
             pstmt.setDouble(10, returnDs.getColumnAsDouble(i, "JQPR_CNT"));
             pstmt.setDouble(11, returnDs.getColumnAsDouble(i, "JQPR_POINT"));
             pstmt.setDouble(12, returnDs.getColumnAsDouble(i, "AVG_PER_CNT"));
             pstmt.setDouble(13, returnDs.getColumnAsDouble(i, "AVG_PER_POINT"));
             pstmt.setDouble(14, returnDs.getColumnAsDouble(i, "INP_RT"));
             pstmt.setDouble(15, returnDs.getColumnAsDouble(i, "INP_POINT"));
             pstmt.setDouble(16, returnDs.getColumnAsDouble(i, "PER_INST_CNT"));
             pstmt.setDouble(17, returnDs.getColumnAsDouble(i, "PER_INST_POINT"));
             pstmt.setDouble(18, returnDs.getColumnAsDouble(i, "SAF_ACCT_CNT"));
             pstmt.setDouble(19, returnDs.getColumnAsDouble(i, "SAF_ACCT_POINT"));
             pstmt.setDouble(20, returnDs.getColumnAsDouble(i, "EVAL_IMNO_CNT"));
             pstmt.setDouble(21, returnDs.getColumnAsDouble(i, "EVAL_IMNO_POINT"));
             pstmt.setDouble(22, returnDs.getColumnAsDouble(i, "PER_PER_CNT"));
             pstmt.setDouble(23, returnDs.getColumnAsDouble(i, "PER_PER_POINT"));
             pstmt.setDouble(24, returnDs.getColumnAsDouble(i, "MINWON_CNT"));
             pstmt.setDouble(25, returnDs.getColumnAsDouble(i, "MINWON_POINT"));
             pstmt.setDouble(26, returnDs.getColumnAsDouble(i, "TOT_POINT"));
             pstmt.addBatch();

		 }
        
         pstmt.executeBatch();
         System.out.println("####ZPSTW1701 1 end ");
         
     
      }
      catch(Exception e)
      {
          System.out.println("####ZPSTW1701 error !!" );
          System.out.println("ZPSTW1701 \n" + e.toString());
          pstmt.close();
          result = null;
          master = null;
          returnDs = null;
          resultMonths = null;
          masterMonths = null;
          returnDsMonths = null;
          resultVar = null;
          masterVar = null;
          returnDsVar = null;
          resultVar = null;
          masterVar = null;
          returnDsVar = null;
          BatchConstants.close(null,pstmt,null);
          throw e;
      }
      finally
      {
          System.out.println("####ZPSTW1701 finally !!");
          pstmt.close();
          result = null;
          master = null;
          returnDs = null;
          resultMonths = null;
          masterMonths = null;
          returnDsMonths = null;
          resultVar = null;
          masterVar = null;
          returnDsVar = null;
          resultVar = null;
          masterVar = null;
          returnDsVar = null;
          BatchConstants.close(null,pstmt,null);
      }
      
      return 1;
   }
  

   public int delProcForZPSTW1701 (String mandt, Connection conn, String yym) throws SQLException, Exception
   {
      logger.debug("-- 보수관리대수 삭제 시작 ");
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
        sqlBuff.append("  DELETE FROM SAPHEE.ZPSTW1701 \n");
        sqlBuff.append("   WHERE MANDT = ?           \n");
        sqlBuff.append("    AND TOT_YYYY = ?       \n"); 
        sqlBuff.append("    AND GUBUN1 = ?       \n"); 
        sqlBuff.append("    AND GUBUN2 = ?       \n"); 
        sqlBuff.append("    AND SCR_GUBUN = ?       \n"); 
     //   sqlBuff.append("    AND LIFNO = ?       \n"); 

 /*       sqlBuff.append("  MANDT                            \n");
        sqlBuff.append("  ,TOT_YYYY                         \n");
        sqlBuff.append("  ,GUBUN1                           \n");
        sqlBuff.append("  ,GUBUN2                          \n"); 
        sqlBuff.append("  ,SCR_GUBUN                       \n");
        sqlBuff.append("  ,TEAMNO                          \n");
        sqlBuff.append("  ,ZZACTSS                         \n"); 
        sqlBuff.append("  ,LIFNO                           \n");*/
      try
      {
          int idxparam = 1;
          pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
          
          pstmt.setString(idxparam++, mandt);
          pstmt.setString(idxparam++, mandt);
          pstmt.setString(idxparam++, mandt);
          pstmt.setString(idxparam++, mandt);
          pstmt.setString(idxparam++, mandt);
       //   pstmt.setString(idxparam++, mandt);
          
//          sqlBuff.append("   WHERE MANDT = ?           \n");
//          sqlBuff.append("    AND TOT_YYYY = ?       \n"); 
//          sqlBuff.append("    AND GUBUN1 = ?       \n"); 
//          sqlBuff.append("    AND GUBUN2 = ?       \n"); 
//          sqlBuff.append("    AND SCR_GUBUN = ?       \n"); 
//          sqlBuff.append("    AND LIFNO = ?       \n"); 
         
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
      return 1;
   }
   public TransForCOLLEVAL(Logger pLogger)
   {
      logger = pLogger;
   }
}