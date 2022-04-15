/**
 * �� �� �� : ProcMSGTRAN.java
 * ��    �� : �Ⱓ ���� ���� ���� batch ó��
 *            service-helcoWeb.xml�� ���������� ��ϵǾ� ���� ������ ���� ��.
 *            �α����� ��ġ�� SystemOut.log���Ͽ� �����.
 *            ��) /usr/WAS/AppServer/profiles/AppSrv01/logs/server1/SystemOut.log
 * �� �� �� : HSS
 * �� �� �� : 2009-04-23 17:09
 * ���泻�� :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.wb01.batch;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.service.core.logger.Logger;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.scheduler.ScheduleTask;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.batch.BatchConstants;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.tobesoft.platform.data.Dataset;


public class ProcMSGTRAN extends AbstractBusinessService implements ScheduleTask
{
   static Logger logger; 
   private String mandt = "183";
   private static String PATH = "/usr/WAS/AppServer/profiles/AppSrv01/installedApps/elswebCell01/HELCO_WEB.ear/HELCO_WEB.war/upload/NOTE";
  // private static String PATH = "D:/dev/work_src/HELCO_WEB/WebContent/upload/NOTE";
   
   public void run() throws Exception {

	  SqlExecutor db = null;
      SqlResult result = null;
      DatasetSqlRequest master = null;
      Dataset returnDs = null;
      
      Connection conn = null;

      System.out.println("==============================================================================");
      System.out.println("�Ⱓ ���� ���� ���� BATCH ���μ��� START : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      System.out.println("==============================================================================\n");
  
	  try
      {
		  String con_hep = Utillity.getSapJndi(mandt);
		  conn = getConnection(con_hep);
		  if( conn == null ) return;
	      conn.setAutoCommit(false);
		  db = new DatasetSqlExecutor(conn);
	      
	      master = SqlMapManagerUtility.makeSqlRequestForDataset("wb01:WB0199042A_S1");
	      master.addParamObject("G_MANDT", mandt);
	      master.addParamObject("DEL_DATE", DateTime.addMonths(DateTime.getDateString("yyyyMMdd"), -3));
	      result = db.query(master);
	      returnDs = (Dataset)result.getResultObject();

	      System.out.println("==============================================================================");
	      System.out.println("�Ⱓ ���� ���� ���� ���� : " + returnDs.getRowCount());
	      System.out.println("==============================================================================\n");
	      
	      delProcForMSG(conn, returnDs);
    	  conn.commit();
      }
      catch(SQLException se)
      {
         System.out.println("\n" +  se.toString());
         se.printStackTrace();
         try
         {
            conn.rollback();
            BatchConstants.close(null,null,conn);
         }
         catch(Exception e)
         {
         }
      }
      catch(Exception e)
      {
         System.out.println("\n" +  e.toString());
         e.printStackTrace();
         try
         {
            conn.rollback();
            BatchConstants.close(null,null,conn);
         }
         catch(Exception te)
         {
         }
      }      
      finally
      {
         try
         {
        	 BatchConstants.close(null,null,conn);
         }
         catch(Exception e)
         {
            e.printStackTrace();
            System.out.println("\n" + e.toString());
         }
      }
      System.out.println("==============================================================================");
      System.out.println("�Ⱓ ���� ���� ���� BATCH ���μ��� END : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      System.out.println("==============================================================================\n");
   }
	
   /**
    * �ۼ��� : 
    * �ۼ��� : 
    * ��  �� : ��������
    * ��  Ÿ :
    */ 
   public void delProcForMSG (Connection conn, Dataset returnDs) throws SQLException, Exception
   {
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
		sqlBuff.append("  DELETE FROM SAPHEE.ZWBMESG \n");
		sqlBuff.append("   WHERE MANDT = ?           \n");
		sqlBuff.append("  	AND MESGSEND = ?       \n"); 
		sqlBuff.append("  	AND MESGNUMB = ?       \n"); 

      try
      {
          pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
          for(int i=0; i<returnDs.getRowCount(); i++) {
             pstmt.setString(1, returnDs.getColumnAsString(i, "MANDT"));
   			 pstmt.setString(2, returnDs.getColumnAsString(i, "MESGSEND"));
             pstmt.setString(3, returnDs.getColumnAsString(i, "MESGNUMB"));


             pstmt.addBatch();
 		  }
          pstmt.executeBatch();
          
          File fDir = new File(PATH);
          for (int i=0; i< returnDs.getRowCount(); i++)
          {
        	  if (!returnDs.getColumnAsString(i, "MESGFNAM").equals(""))
        	  {
                  File file = new File(fDir, returnDs.getColumnAsString(i, "MESGFNAM"));
          		  file.delete();
        	  }
          }
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         BatchConstants.close(null,pstmt,null);            
      }
   }

   /**
    * �ۼ��� : 
    * �ۼ��� : 
    * ��  �� : DB�� Ŀ�ؼ� �������� 
    * ��  Ÿ :
    */ 

   public Connection getConnection(String mdt) throws SQLException, Exception
   {
	   ResourceFactory resourceFactory =(ResourceFactory)
	   		ServiceManagerFactory.getServiceObject("DB2_HEQ"); //�ƹ��ų� ����ص� ����
	   
	   if( resourceFactory == null ) {
		   return null;
	   } 
	   TransactionResource res = resourceFactory.makeResource(mdt); //mdt�� jdbc/hed, jdbc/heq, jdbc/hep, jdbc/cs �߿� ������� ��������� �߿�

	   return (Connection) res.getResource();
   }
}
