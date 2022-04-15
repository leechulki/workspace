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
		logger.debug("\n���ڿ��� ��� ���� ��ġ ���μ��� Start : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
		
		try
	      {
			String tdt = CommonUtil.getToday2();
			ProcPostReceive PostReceive = new ProcPostReceive();
			
			int rtnCode = PostReceive.isExistFile(tdt);
								
			if(rtnCode == 1){	
				// ���� ���� ��� agent üũ ���� ����� ���� ����
				PostReceive.sendMailTo(tdt);					
			} else {
				
				String mdt = mandt;
				String db_con = Utillity.getSapJndi(mdt);
				
				Connection conn = null;
				conn = getConnection(db_con);

		        conn.setAutoCommit(false);
		        
				//  ���� �����ϴ� ��� db insert 
				PostReceive.insertDB(mdt,conn,tdt);

		        conn.commit();					
			}
	      }
		catch(Exception se)
	      {
	         logger.debug("\n" +  se.toString());
	         se.printStackTrace();
	      }
	
        logger.debug("\n���ڿ��� ��� ���� ��ġ ���μ��� End : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
	}

   public Connection getConnection(String mdt) throws SQLException, Exception
	   {
		   ResourceFactory resourceFactory =(ResourceFactory)
		   		ServiceManagerFactory.getServiceObject("DB2_HEQ"); //�ƹ��ų� ����ص� ����
		   
		   if( resourceFactory == null ) {
			   return null;
		   } 
		   TransactionResource res = resourceFactory.makeResource(mdt); //mdt�� jdbc/hed, jdbc/heq, jdbc/hep �߿� ������� ��������� �߿�

		   return (Connection) res.getResource();
   }
}
