package com.helco.xf.cs12.batch;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;


import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.wb01.batch.FileFilter;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.biz.BusinessException;
import tit.service.core.logger.Logger;
import tit.service.mail.MailSendService;
import tit.service.mail.MailSender;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.scheduler.ScheduleTask;

public class ProcPost extends AbstractBusinessService implements ScheduleTask{

    static Logger logger; 
    private String mandt = "183";
   
	public void run() throws Exception {

		logger = ServiceManagerFactory.getLogger();		
		logger.debug("\n전자우편 결과 수신 배치 프로세스 Start : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
		
		try
	      {
			String tdt = CommonUtil.getToday2();
			ProcPostReceive PostReceive = new ProcPostReceive();
			
			int rtnCode = PostReceive.isExistFile(tdt);
								
			if(rtnCode == 1){	
				// 파일 없는 경우 agent 체크 위한 담당자 메일 전송
				PostReceive.sendMailTo(tdt);					
			} else {
				
				String mdt = mandt;
				String db_con = Utillity.getSapJndi(mdt);
				
				Connection conn = null;
				conn = getConnection(db_con);

		        conn.setAutoCommit(false);
		        
				//  파일 존재하는 경우 db insert 
				PostReceive.insertDB(mdt,conn,tdt);

		        conn.commit();					
			}
	      }
		catch(Exception se)
	      {
	         logger.debug("\n" +  se.toString());
	         se.printStackTrace();
	      }
	
        logger.debug("\n전자우편 결과 수신 배치 프로세스 End : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
	}

   public Connection getConnection(String mdt) throws SQLException, Exception
	   {
		   ResourceFactory resourceFactory =(ResourceFactory)
		   		ServiceManagerFactory.getServiceObject("DB2_HEQ"); //아무거나 사용해도 무방
		   
		   if( resourceFactory == null ) {
			   return null;
		   } 
		   TransactionResource res = resourceFactory.makeResource(mdt); //mdt를 jdbc/hed, jdbc/heq, jdbc/hep 중에 어느것을 사용할지가 중요

		   return (Connection) res.getResource();
   }
}
