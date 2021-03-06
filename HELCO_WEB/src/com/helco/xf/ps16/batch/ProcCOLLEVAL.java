/**
 * 파 일 명 : ProcCOLLEVAL.java
 * 설    명 : 협력사평가 협력도평가 협력사별 처리 BATCH
 *            service-helcoWeb.xml에 스케줄잡이 등록되어 반기별에 실행 됨.
 *            로그파일 위치는 SystemOut.log파일에 저장됨.
 *            예) /usr/WAS/AppServer/profiles/AppSrv01/logs/server1/SystemOut.log
 * 작 성 자 : SYJ
 * 작 성 일 : 2009-01-22 12:09
 * 변경내역 :
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


public class ProcCOLLEVAL extends AbstractBusinessService implements ScheduleTask
{
   static Logger logger; 
   private String mandt = "183";

   public void run() throws Exception {
      Connection conn = null;
      // Connection conn_hep = null;

      logger = ServiceManagerFactory.getLogger();
      System.out.println("==============================================================================");
      System.out.println("협력사평가 협력도평가 반기별 협력사별 BATCH(EL) 프로세스 START : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      System.out.println("==============================================================================\n");

 	  TransForCOLLEVAL emTran = new TransForCOLLEVAL(logger);
 	  
	  try
      {
		// String db_con = "jdbc/heq";
		 String db_con = "jdbc/hep";
		// String db_con_hep = Utillity.getSapJndi(mandt);
	     conn = getConnection(db_con);
	     //  conn_hep = getConnection(db_con_hep);
         if( conn == null ) return;
         conn.setAutoCommit(true);
         //  if( conn_hep == null ) return;
         //   conn_hep.setAutoCommit(true);
         System.out.println("ProcTransForCOLLEVAL1 start!!");
         int ret1 =   emTran.ProcTransForCOLLEVAL(mandt,"1", conn);
         System.out.println("ProcTransForCOLLEVAL1 END  !! [" + ret1);
           conn.commit();
           //  conn_hep.commit();
         
      }
      catch(SQLException se)
      {
         System.out.println("\n" +  se.toString());
         se.printStackTrace();
         try
         {
               conn.rollback();
               // conn_hep.rollback();
              BatchConstants.close(null,null,conn);
              // BatchConstants.close(null,null,conn_hep);
            
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
            //   conn_hep.rollback();
            BatchConstants.close(null,null,conn);
            //   BatchConstants.close(null,null,conn_hep);
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
        	 //	 BatchConstants.close(null,null,conn_hep);
         }
         catch(Exception e)
         {
             conn.close();
             //  conn_hep.close();
            e.printStackTrace();
            System.out.println("\n" + e.toString());
         }
      }
      System.out.println("==============================================================================");
      System.out.println("협력사평가 협력도평가 협력사별 BATCH 프로세스 END : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      System.out.println("==============================================================================\n");
   }

   /**
    * 작성자 : 
    * 작성일 : 
    * 설  명 : DB의 커넥션 가져오기 
    * 기  타 :
    */ 

   public Connection getConnection(String mdt) throws SQLException, Exception
   {
	   ResourceFactory resourceFactory =(ResourceFactory)
	   		//ServiceManagerFactory.getServiceObject("DB2_HEQ"); //아무거나 사용해도 무방
	   ServiceManagerFactory.getServiceObject("DB2_PRD"); //아무거나 사용해도 무방
	   
	   if( resourceFactory == null ) {
		   return null;
	   } 
	   TransactionResource res = resourceFactory.makeResource(mdt); //mdt를 jdbc/hed, jdbc/heq, jdbc/hep, jdbc/cs 중에 어느것을 사용할지가 중요

	   return (Connection) res.getResource();
   }
}
