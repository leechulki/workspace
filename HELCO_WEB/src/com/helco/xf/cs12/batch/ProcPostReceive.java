package com.helco.xf.cs12.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;

import tit.biz.BusinessException;
import tit.service.business.config.ConfigUtility;
import tit.service.core.logger.Logger;
import tit.service.mail.MailSendService;
import tit.service.mail.MailSender;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.wb01.batch.FileFilter;

public class ProcPostReceive extends AbstractBusinessService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger; 
//	private static final String PATH1        =  "C:\\Users\\user\\Desktop\\test\\";
	private static final String PATH1        =  "/srm/HELCO_WEB/HELCO_WEB.war/svc/POSTNET_AGENT/env/attachment/receive/";
	private static String PATH2 = "";
	
	/*-----------------------------------------------------
	 *  ����û �������� �������� ���� ����
	 *  2017-10-18   ���� ������ 
	 ------------------------------------------------------*/
	public int isExistFile(String date){

		String tdt = date;		
		String day1 = "";
		
		//srm ������� - ��������(to) ���� ���� ���� ���Ϸ� ����
		if(tdt.length() == 0){
			day1 = CommonUtil.getToday2();
		} else {
			day1 = tdt;
		}
		
		PATH2 = PATH1 + day1 + "/";
		
		File fDir1 = new File(PATH1);
		File fDir2 = new File(PATH2);
		
		//PATH2  ���丮�� ���� ��� ����
		if(fDir2.exists()== false){
			fDir2.mkdirs();
		}	
		
		// ���� ���ڷ� ������ ���� ����
		FileFilter tfilter = new FileFilter("*"+day1+"*.txt");
	
		File[] tfiles = fDir1.listFiles(tfilter); 
		for (int i=0; i< tfiles.length; i++){
			// ���� ���� ������ ���� ī�� �� ����
			CommonUtil.filecopy(PATH1+tfiles[i].getName(), PATH2+tfiles[i].getName());
			tfiles[i].delete();
		}			
		
		// �̵��� ������ ���� ���� üũ
		// ������ ���� ��� ��� Agent ���� üũ �ʿ� 
		// email ���� 
		File[] check_files = fDir2.listFiles(tfilter); 			

		System.out.println("���� ����üũ" + check_files.length);
		if(check_files.length == 0){	
			return 1;
		} else {			
			return 0;
		}
	}
	
	/*-----------------------------------------------------
	 *  ���� ����  DB INSERT
	 *  2017-10-18   ���� ������ 
	 ------------------------------------------------------*/
	public void insertDB(String mdt, Connection conn, String tdt) throws Exception{

		logger = ServiceManagerFactory.getLogger();	

		DatasetSqlExecutor executor = new DatasetSqlExecutor(conn);
				
		DatasetSqlRequest zcst714i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4201001C_I3");
		zcst714i.addParamObject("G_MANDT", mdt);
		
		DatasetSqlRequest zcst715i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4201001C_I4");
		zcst715i.addParamObject("G_MANDT", mdt);

		DatasetSqlRequest zcst716i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4201001C_I5");
		zcst716i.addParamObject("G_MANDT", mdt);
		
		PATH2 = PATH1 + tdt + "/";
		
		File fDir2 = new File(PATH2);

		// ����, ���, ���� ����� ������ ���� ����
		FileFilter mfilter = new FileFilter("make_*.txt");
		FileFilter rfilter = new FileFilter("recv_*.txt");
		FileFilter sfilter = new FileFilter("result_*.txt");

		File[] mfiles = fDir2.listFiles(mfilter); 
		File[] rfiles = fDir2.listFiles(rfilter); 
		File[] sfiles = fDir2.listFiles(sfilter); 
		
		//make ���۰�� insert
		for (int i=0; i< mfiles.length; i++){
			
			if(mfiles[i].length() > 0 && mfiles[i].exists()) {
				String mfname1 = PATH2 + mfiles[i].getName();
				FileInputStream fis = new FileInputStream(new File(mfname1 )); 
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8"); 
				BufferedReader br = new BufferedReader(isr);	

				try{
					while(true){
						String str = br.readLine();
						if(str!=null){
							String tok[] = str.split("\\|",7);

							//System.out.println(str);
							
							String zcol2 = tok[1];
							String zcol3 = tok[2];
							String zcol4 = tok[3];
							String zcol5 = tok[4];
							String zcol6 = tok[5];
	
							zcst715i.addParamObject("ZCOL02", zcol2);
							zcst715i.addParamObject("ZCOL03", zcol3);
							zcst715i.addParamObject("ZCOL04", zcol4);
							zcst715i.addParamObject("ZCOL05", zcol5);
							zcst715i.addParamObject("ZCOL06", zcol6);
					
							executor.execute(zcst715i);
						
						}else {
							break;
						}
					}			
					
				}catch(Exception e){
					e.printStackTrace();
				}	
				br.close();
				isr.close();
				fis.close();	
			}
		}

		//recv ������� insert
		for (int i=0; i< rfiles.length; i++){
			
			if(rfiles[i].length() > 0 && rfiles[i].exists()) {
				String rfname1 = PATH2 + rfiles[i].getName();
				int lineCnt = 0;

				FileInputStream fis = new FileInputStream(new File(rfname1 )); 
				
				//System.out.println(rfname1);
				
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8"); 
				BufferedReader br = new BufferedReader(isr);	
				try{
					while(true){
						String str = br.readLine();
						if(str!=null){
							String tok[] = str.split("\\|",12);
							//System.out.println(str);
	
							String zcol1 = tok[0];
							String zcol2 = tok[1];
							String zcol3 = tok[2];
							String zcol4 = tok[3];
							String zcol5 = tok[4];
							String zcol6 = tok[5];
							String zcol7 = tok[6];
							String zcol8 = tok[7];
							String zcol9 = tok[8];
							String zcol10 = tok[9];
							String zcol11 = tok[10];
	
							zcst714i.addParamObject("ZCOL01", zcol1);
							zcst714i.addParamObject("ZCOL02", zcol2);
							zcst714i.addParamObject("ZCOL03", zcol3);
							zcst714i.addParamObject("ZCOL04", zcol4);
							zcst714i.addParamObject("ZCOL05", zcol5);
							zcst714i.addParamObject("ZCOL06", zcol6);
							zcst714i.addParamObject("ZCOL07", zcol7);
							zcst714i.addParamObject("ZCOL08", zcol8);
							zcst714i.addParamObject("ZCOL09", zcol9);
							zcst714i.addParamObject("ZCOL10", zcol10);
							zcst714i.addParamObject("ZCOL11", zcol11);
							
							executor.execute(zcst714i);
	
			                lineCnt++;
						} else {
							break;
						}
					}			
					
				}catch(Exception e){
					e.printStackTrace();
				}
				br.close();
				isr.close();
				fis.close();
			}
		}		
		
		//result ��ް�� insert
		for (int i=0; i< sfiles.length; i++){
			
			if(sfiles[i].length() > 0 && sfiles[i].exists()) {
				String sfname1 = PATH2 + sfiles[i].getName();
				FileInputStream fis = new FileInputStream(new File(sfname1 )); 
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8"); 
				BufferedReader br = new BufferedReader(isr);	
				try{

					while(true){
						String str = br.readLine();
						if(str!=null){
							String tok[] = str.split("\\|",12);
							//System.out.println(str);
	
							String zcol1 = tok[0];
							String zcol2 = tok[1];
							String zcol3 = tok[2];
							String zcol4 = tok[3];
							String zcol5 = tok[4];
							String zcol6 = tok[5];
							String zcol7 = tok[6];
							String zcol8 = tok[7];
							String zcol9 = tok[8];
							String zcol10 = tok[9];
							String zcol11 = tok[10];
	
							zcst716i.addParamObject("ZCOL01", zcol1);
							zcst716i.addParamObject("ZCOL02", zcol2);
							zcst716i.addParamObject("ZCOL03", zcol3);
							zcst716i.addParamObject("ZCOL04", zcol4);
							zcst716i.addParamObject("ZCOL05", zcol5);
							zcst716i.addParamObject("ZCOL06", zcol6);
							zcst716i.addParamObject("ZCOL07", zcol7);
							zcst716i.addParamObject("ZCOL08", zcol8);
							zcst716i.addParamObject("ZCOL09", zcol9);
							zcst716i.addParamObject("ZCOL10", zcol10);
							zcst716i.addParamObject("ZCOL11", zcol11);
							zcst716i.setRowIndex(i);
							executor.execute(zcst716i);
						} else {
							break;
						}

					}			
					
				}catch(Exception e){
					e.printStackTrace();
				}	
				br.close();
				isr.close();
				fis.close();			
			}
		}	
			
	}


	/*-----------------------------------------------------
	 *  ���� ����  ���� ��� ����� ���� ���� 
	 *  2017-10-18   ���� ������ 
	 ------------------------------------------------------*/
	public void sendMailTo(String date) {

		MailSendService service = (MailSendService)ServiceManagerFactory.getServiceObject("MailService");
		
		MailSender sendObj = service.createMailSender();
		
		//InternetAddress[] address = {new InternetAddress("harim.lee@hdel.co.kr"),new InternetAddress("ar.yong@hdel.co.kr")};

		sendObj.setTo("gitae.bang@hdel.co.kr");
		sendObj.setCc("junghoon.han@hdel.co.kr");
		
		sendObj.setFrom(ConfigUtility.getString("ADMIN_MAIL"));
		
		sendObj.setSubject("���ڿ��� ��� Agent Ȯ��");
		sendObj.setBodyType(MailSender.CONTENT_TYPE_TEXT);
		
		String bodyStr = date + " : ���ڿ��� ���� ���� ����.\n"
				       + "���ڿ���  ��� Agent Ȯ��.";
		
		sendObj.setBody(bodyStr);
		// ���� 
		try {
			sendObj.sendMessage();
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}

}
