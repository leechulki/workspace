/**
 * �� �� �� : TransForEMTRAN.java
 * ��    �� :
 * �� �� �� : SYJ
 * �� �� �� : 2009-01-22 12:10
 * ���泻�� :
 *
 */
package com.helco.xf.ps02.batch;

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
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.batch.BatchConstants;

public class TransForEMTRAN
{
   static Logger logger;
   /**
    * �ۼ��� : SYJ
    * �ۼ��� : 2009-01-22
    * ��  �� : ����/�ذ������� SMS�߼� batch ó��
    * ��  Ÿ :
    */
   public int ProcTransForEMTRAN (String mandt, Connection conn, Connection conn_hep) throws SQLException, Exception
   {
      SqlExecutor db = null;
      SqlResult result = null;
      DatasetSqlRequest master = null;
      Dataset returnDs = null;
      String today = "Y";
      String work_date = "";
      
      db = new DatasetSqlExecutor(conn_hep);
      
      master = SqlMapManagerUtility.makeSqlRequestForDataset("ps02:PS0299001A_S2");
      master.addParamObject("G_MANDT", mandt);
      result = db.query(master);
      returnDs = (Dataset)result.getResultObject();
      
      if(returnDs.getRowCount() != 0){
          System.out.println("==============================================================================");
    	  System.out.println("����/�ذ������� SMS�߼� BATCH�� �Ϸ翡 �ѹ� ����˴ϴ�.����/�ذ������� SMS�߼� BATCH�� �̹� ����Ǿ����ϴ�.");
          System.out.println("==============================================================================\n");
    	  return 0;
      }
      
      // Working Day ��¥ ��������
      master = SqlMapManagerUtility.makeSqlRequestForDataset("ps02:PS0299001A_S3");
      master.addParamObject("G_MANDT", mandt);
      result = db.query(master);
      returnDs = (Dataset)result.getResultObject();
      
      if(returnDs.getColumn(0, "TODAY").toString().equals("N"))	// ������ WORKING DAY �� �ƴ� ���
      {
    	  today = "N";
    	  work_date = returnDs.getColumn(0, "WORK_DATE").toString();
      }
      
      master = SqlMapManagerUtility.makeSqlRequestForDataset("ps02:PS0299001A_S1");
      master.addParamObject("G_MANDT", mandt);
      result = db.query(master);
      returnDs = (Dataset)result.getResultObject();
      
      
      System.out.println("==============================================================================");
      System.out.println("======== ����/�ذ������� SMS ("+returnDs.getRowCount()+") �� �߼ۿ��� �Դϴ�. ========");
      System.out.println("==============================================================================\n");
      
   
      
      //HDCS.EM_TRAN ���̺� N�� INSERT�Ѵ�.
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      StringBuffer               sqlBuff1  = new StringBuffer();
      LoggablePreparedStatement  pstmt1    = null;

/*      
      sqlBuff.append(" INSERT INTO HDCS.EM_TRAN(            \n");
      sqlBuff.append("       TRAN_PR                        \n");
      sqlBuff.append(" 	   , TRAN_ID                        \n");
      sqlBuff.append(" 	   , TRAN_PHONE                     \n");
      sqlBuff.append(" 	   , TRAN_CALLBACK                  \n");
      sqlBuff.append(" 	   , TRAN_STATUS                    \n");
      sqlBuff.append(" 	   , TRAN_DATE                      \n");
      sqlBuff.append(" 	   , TRAN_MSG                       \n");
      sqlBuff.append(" 	   , TRAN_TYPE                      \n");
      sqlBuff.append(" 	   , TRAN_ETC1                      \n");
      sqlBuff.append(" 	   , TRAN_ETC2                      \n");
      sqlBuff.append("     ) VALUES (                       \n");
      sqlBuff.append("       NEXTVAL FOR HDCS.SEQ_EM_TRANS  \n");
      sqlBuff.append(" 	   , ''                             \n");
      sqlBuff.append(" 	   , ?                              \n");
      sqlBuff.append(" 	   , ?                              \n");
      sqlBuff.append(" 	   , '1'                            \n");
      sqlBuff.append(" 	   , HDCS.TO_DATETIME()             \n");
      sqlBuff.append(" 	   , ?                              \n");
      sqlBuff.append(" 	   , 1                              \n");
      sqlBuff.append(" 	   , ?                              \n");
      sqlBuff.append(" 	   , ?                              \n");
      sqlBuff.append("  )                                   \n");
*/
      
      sqlBuff.append(" INSERT INTO HCSADM.MMS_MSG(          \n");
      sqlBuff.append("       msgkey                         \n");      
      sqlBuff.append(" 	   , phone                          \n"); 
      sqlBuff.append(" 	   , callback                       \n");
      sqlBuff.append(" 	   , status                         \n");
      sqlBuff.append(" 	   , reqdate                        \n");
      sqlBuff.append(" 	   , msg                            \n");
      sqlBuff.append(" 	   , type                           \n");
      sqlBuff.append(" 	   , id                             \n");     
      sqlBuff.append("     ) VALUES (                       \n");
      sqlBuff.append("       HCSADM.MMS_MSG_SEQ.NEXTVAL     \n");      
      sqlBuff.append(" 	   , ?                              \n");	// ?
      sqlBuff.append(" 	   , ?                              \n");
      sqlBuff.append(" 	   , '0'                            \n");
      if(today.equals("Y"))
      {
    	  sqlBuff.append(" 	   , HCSADM.UF_SYSDATE('CURRENT','YYYYMMDDHH24MISS',0)             \n");
      }
      else
      {
    	  sqlBuff.append(" 	   , HCSADM.UF_SYSDATE("+work_date+",'YYYYMMDDHH24MISS',0)             \n");
      }
      
      
      sqlBuff.append(" 	   , ?                              \n");
      sqlBuff.append(" 	   , '0'                            \n");
      sqlBuff.append(" 	   , ?                              \n");      
      sqlBuff.append("  )                                   \n");      
      
      
      sqlBuff1.append(" INSERT INTO SAPHEE.ZPST0131(     \n");
      sqlBuff1.append("       MANDT                      \n");
      sqlBuff1.append("     , INDATE                     \n");
      sqlBuff1.append(" 	, GUBUN                      \n");
      sqlBuff1.append(" ) VALUES (                       \n");
      sqlBuff1.append("       ?                          \n");
      sqlBuff1.append(" 	,  HEX(CURRENT DATE)         \n");
      sqlBuff1.append(" 	, 'Y'                        \n");
      sqlBuff1.append(" )                                \n");
      
      try
      {
    	 //EM_TRAN�� INSERT 
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for(int i=0; i<returnDs.getRowCount(); i++) {
            pstmt.setString(1, returnDs.getColumnAsString(i, "TRAN_PHONE"));
  			pstmt.setString(2, returnDs.getColumnAsString(i, "TRAN_CALLBACK"));
            pstmt.setString(3, returnDs.getColumnAsString(i, "TRAN_MSG"));
            pstmt.setString(4, returnDs.getColumnAsString(i, "TRAN_ETC1"));
            //pstmt.setString(5, returnDs.getColumnAsString(i, "TRAN_ETC2"));

            pstmt.addBatch();
		 }
         pstmt.executeBatch();
         
         //ZPST0131(���� ��ġ���࿩�� ���̺�)�� INSERT
         pstmt1 = new LoggablePreparedStatement(conn_hep, sqlBuff1.toString());
         pstmt1.setString(1, mandt);
         pstmt1.executeUpdate();
      
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         BatchConstants.close(null,pstmt,null);
         BatchConstants.close(null,pstmt1,null);
      }
      return 0;
   }

   public Dataset QueryEMTRAN (String mandt, Connection conn,BusinessContext ctx) throws SQLException, Exception
   {
      SqlExecutor db = null;
      SqlResult result = null;
      DatasetSqlRequest master = null;
      Dataset returnDs = null;
      
      db = new DatasetSqlExecutor(conn);
      
      master = SqlMapManagerUtility.makeSqlRequestForDataset("wb01:WB0107001A_S2");
      master.addParamObject("DATEF", DatasetUtility.getString(ctx.getInputDataset("ds_cond"), "DATEF", ""));
      master.addParamObject("DATET", DatasetUtility.getString(ctx.getInputDataset("ds_cond"), "DATET", ""));
      
      result = db.query(master);
      returnDs = (Dataset)result.getResultObject();

      return returnDs;
   }
   
   public TransForEMTRAN(Logger pLogger)
   {
      logger = pLogger;
   }
}