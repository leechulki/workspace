/**
 * �� �� �� : ProcTEAMEVAL.java
 * ��    �� : ���»��� ���µ��� �ݱ⺰ ���庰 BATCH ó��
 *            service-helcoWeb.xml�� ���������� ��ϵǾ� �ݱ⺰�� ���� ��.
 *            �α����� ��ġ�� SystemOut.log���Ͽ� �����.
 *            ��) /usr/WAS/AppServer/profiles/AppSrv01/logs/server1/SystemOut.log
 * �� �� �� : SYJ
 * �� �� �� : 2009-01-22 12:09
 * ���泻�� :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.ps16.batch;

import java.sql.Connection;
import java.sql.SQLException;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.scheduler.ScheduleTask;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.batch.BatchConstants;


public class ProcTEAMEVAL extends AbstractBusinessService implements ScheduleTask
{
   static Logger logger; 
   private String mandt = "183";

   public void run() throws Exception {
      Connection conn = null;
    //  Connection conn_hep = null;

      logger = ServiceManagerFactory.getLogger();
      System.out.println("==============================================================================");
      System.out.println("���»��� ���µ��� �ݱ⺰ ���庰 BATCH ���μ��� START : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      System.out.println("==============================================================================\n");

      TransForTEAMEVAL emTran = new TransForTEAMEVAL(logger);
 	  
	  try
      {
		// String db_con = "jdbc/heq";
	      String db_con = "jdbc/hep";
		// String db_con_hep = Utillity.getSapJndi(mandt);
	     conn = getConnection(db_con);
	 //    conn_hep = getConnection(db_con_hep);
         if( conn == null ) return;
         conn.setAutoCommit(true);
     //    if( conn_hep == null ) return;
     //    conn_hep.setAutoCommit(true);
         System.out.println("ProcTransForCOLLTEAMEVAL start!!");
         int ret2 =   emTran.ProcTransForCOLLTEAMEVAL(mandt, "2", conn);
         System.out.println("ProcTransForCOLLTEAMEVAL END  !! [" + ret2);
         conn.commit();
      //   conn_hep.commit();
         
      }
      catch(SQLException se)
      {
         System.out.println("\n" +  se.toString());
         se.printStackTrace();
         try
         {
            conn.rollback();
        //    conn_hep.rollback();
            BatchConstants.close(null,null,conn);
        //    BatchConstants.close(null,null,conn_hep);
            
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
        //    conn_hep.rollback();
            BatchConstants.close(null,null,conn);
        //    BatchConstants.close(null,null,conn_hep);
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
       // 	 BatchConstants.close(null,null,conn_hep);
         }
         catch(Exception e)
         {
             conn.close();
       //      conn_hep.close();
            e.printStackTrace();
            System.out.println("\n" + e.toString());
         }
      }
      System.out.println("==============================================================================");
      System.out.println("���»��� ���µ��� ���庰 BATCH ���μ��� END : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      System.out.println("==============================================================================\n");
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
	   	//	ServiceManagerFactory.getServiceObject("DB2_HEQ"); //�ƹ��ų� ����ص� ����
	           ServiceManagerFactory.getServiceObject("DB2_PRD"); //�ƹ��ų� ����ص� ����
	   
	   if( resourceFactory == null ) {
		   return null;
	   } 
	   TransactionResource res = resourceFactory.makeResource(mdt); //mdt�� jdbc/hed, jdbc/heq, jdbc/hep, jdbc/cs �߿� ������� ��������� �߿�

	   return (Connection) res.getResource();
   }
}