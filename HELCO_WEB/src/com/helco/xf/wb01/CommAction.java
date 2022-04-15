package com.helco.xf.wb01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.jsn.jdf.jsp.StringUtil;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.biz.BusinessException;
import tit.biz.session.SessionManager;
import tit.service.business.config.ConfigUtility;
import tit.service.mail.MailSendService;
import tit.service.mail.MailSender;
import tit.service.resource.TransactionManager;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.wb01.ws.ZQMS006;
import com.helco.xf.wb01.ws.ZQMS028;
import com.helco.xf.wb01.ws.ZQM_READ_TEXT;
import com.ksign.KsignSPinCrypto;
import com.tobesoft.platform.data.Dataset;

//�߰� 
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import tit.service.resource.ResourceFactory; 

/**
 * ���� ��û ���� �������� 
 */
public class CommAction extends AbstractAction {

	private static final String ZWEB_MM_GET_WORKINGDAY 	  = "com.helco.xf.wb01.ws.ZWEB_MM_GET_WORKINGDAY";
//	private static final String ZWEB_PS_CHECK_WORKING_DAY = "com.helco.xf.ps02.ws.ZWEB_PS_CHECK_WORKING_DAY";
//	private static final String ZQM_READ_TEXT             = "com.helco.xf.wb01.ws.ZQM_READ_TEXT";
	private static final String ZQM_SAVE_TEXT             = "com.helco.xf.wb01.ws.ZQM_SAVE_TEXT";
	private static final String ZWEB_MM_CHECK_MISU        = "com.helco.xf.wb01.ws.ZWEB_MM_CHECK_MISU";
	private static final String HOST  = "smtp.hdel.co.kr";
	private static final int    PORT  = 25;	

	/**
	 * SAP�� �ٹ����� ���ϱ�
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void getWorkingDay(BusinessContext ctx) throws Exception 
	{
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		Dataset dsError = null;
		SapFunction stub = null;
		
		if ( CallSAP.isJCO() ) 
		{
			
		} 
		else 
		{
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT")
							, ZWEB_MM_GET_WORKINGDAY
							, new Object[]{
									  CallSAP.getFormatDate(ctx.getInputVariable("DATE_FROM"))
									  						, new Integer(ctx.getInputVariable("DAYS"))
									  						, ctx.getInputVariable("WERKS")
									  						, msgList
							 }
							,false);
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}
		if ( dsError.getRowCount() == 0 ) 
		{
			ctx.addOutputVariable("_WORKING_DATE", stub.getOutput("E_WORKINGDAY"));
		} 
		else 
		{
			ctx.addOutputVariable("_WORKING_DATE", "ERROR");
		}
	}

	/**
	 * �̼����� ���ϱ�
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void getMiSu(BusinessContext ctx) throws Exception 
	{
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		Dataset dsError = null;
		SapFunction stub = null;

		if ( CallSAP.isJCO() ) 
		{
			
		} 
		else 
		{
			stub = CallSAP.callSap(
							ctx.getInputVariable("G_SYSID")
							,ctx.getInputVariable("G_MANDT")
							, ZWEB_MM_CHECK_MISU
							, new Object[]{
									  ctx.getInputVariable("I_PSPID")
									  , msgList
							 }
							 ,false);
	
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}

		if ( dsError.getRowCount() == 0) 
		{
			ctx.addOutputVariable("_MISU", stub.getOutput("E_MISU"));
		} 
		else 
		{
			ctx.addOutputVariable("_MISU", "ERROR");
		}

	}

	/**
	 * SAP�� LongText ��������
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void getLongText(BusinessContext ctx) throws Exception 
	{
		SapFunction stub = new ZQM_READ_TEXT();
	
		ZQMS028[] in = new ZQMS028[]{};
		try 
		{
			CallSAP.callInteral(
					stub
					, new Object[]{
							  in
							, ctx.getInputVariable("TDID")
							, ctx.getInputVariable("TDNAME")
							, ctx.getInputVariable("TDOBJECT")}
					, ctx.getInputVariable("G_SYSID")
					,ctx.getInputVariable("G_MANDT"));             //���� sap ����
			
			/*
			CallSAP.callInteralEai(
					stub
					, new Object[]{
							  in
							, ctx.getInputVariable("TDID")
							, ctx.getInputVariable("TDNAME")
							, ctx.getInputVariable("TDOBJECT")}
					, ctx.getInputVariable("G_SYSID")
					,ctx.getInputVariable("G_MANDT"));             // EAI ����
			*/
			
		} 
		catch( CommRfcException e) 
		{
			if ( e.getFaultString().equals(CallSAP.NO_DATA_FOUND)) 
			{
				ctx.addOutputVariable("_LONGTEXT", "");
				return;
			} 
			else 
			{
				throw e;
			}	
		} 
		catch( Exception ee ) 
		{
			throw ee;
		}
		
		ZQMS028[] out = (ZQMS028[])stub.getOutput("O_TAB");
    	ctx.addOutputVariable("_LONGTEXT", StringOperator.replaceString(out[0].getTEXT(), "<br>", "")); // enter�ߺ� �������� �������� ��
	}

	public void getLongText2(BusinessContext ctx) throws Exception 
	{
		SapFunction stub = new ZQM_READ_TEXT();
	
		ZQMS028[] in = new ZQMS028[]{};
		try 
		{
			CallSAP.callInteral(
					stub
					, new Object[]{
							  in
							, ctx.getInputVariable("TDID")
							, ctx.getInputVariable("TDNAME")
							, ctx.getInputVariable("TDOBJECT")}
					, ctx.getInputVariable("G_SYSID")
					,ctx.getInputVariable("G_MANDT"));         //���� SAP ����
			/*
			CallSAP.callInteralEai(
					stub
					, new Object[]{
							  in
							, ctx.getInputVariable("TDID")
							, ctx.getInputVariable("TDNAME")
							, ctx.getInputVariable("TDOBJECT")}
					, ctx.getInputVariable("G_SYSID")
					,ctx.getInputVariable("G_MANDT"));         // EAI ����
			*/
			
		} 
		catch( CommRfcException e) 
		{
			if ( e.getFaultString().equals(CallSAP.NO_DATA_FOUND)) 
			{
				ctx.addOutputVariable("_LONGTEXT", "");
				return;
			} 
			else 
			{
				throw e;
			}	
		} 
		catch( Exception ee ) 
		{
			throw ee;
		}
		
		ZQMS028[] out = (ZQMS028[])stub.getOutput("O_TAB");
    	ctx.addOutputVariable("_LONGTEXT", StringOperator.replaceString(out[0].getTEXT(), "<br>", "\n")); // enter�ߺ� �������� �������� ��
	}	
	
	
	/**
	 * SAP�� Long Text �����ϱ�
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void saveLongText(BusinessContext ctx) throws Exception 
	{
		Dataset dsList = ctx.getInputDataset("ds_list");
		
		try 
		{
			if ( !CallSAP.isJCO() ) 
			{
				ZQMS006[] list1 = (ZQMS006[])CallSAP.moveDs2Obj(dsList, ZQMS006.class, "FLAG");
				// ���� ó�� 
				CallSAP.callSap(
						ctx.getInputVariable("G_SYSID")
						,ctx.getInputVariable("G_MANDT")
						,ZQM_SAVE_TEXT
						, new Object[]{
						list1
						, ctx.getInputVariable("TDID") 
						, ctx.getInputVariable("TDNAME")
						, ctx.getInputVariable("TDOBJECT")}
						,false);                             //���� SAP ����
				/*
				CallSAP.callSapEai(
						ctx.getInputVariable("G_SYSID")
						,ctx.getInputVariable("G_MANDT")
						,ZQM_SAVE_TEXT
						, new Object[]{
						list1
						, ctx.getInputVariable("TDID") 
						, ctx.getInputVariable("TDNAME")
						, ctx.getInputVariable("TDOBJECT")}
						,false);                             //EAI ����
				*/
				
			}
		} 
		catch (Exception c) 
		{
			ctx.addOutputVariable("_ETEXT", "Long Text ���忡 �����߽��ϴ�.");
		}
	}
	
	/**
	 * �޼���(����) ���� ��������
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void readNote(BusinessContext ctx) throws Exception 
	{
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:NOTE_S1");
		sqlRequest.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlRequest.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		SqlExecutor db = new SimpleSqlExecutor(getSysConnBySysid(ctx.getInputVariable("G_SYSID")));
		
		ResultSet rs = (ResultSet)db.query(sqlRequest).getResultObject();
		
		if ( rs.next() ) 
		{
			ctx.addOutputVariable("NOTE_CNT", rs.getInt(1));
		}
		else 
		{
			ctx.addOutputVariable("NOTE_CNT", 0);
		}
	}
	
	/**
	 * �������� ��������
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void getCurrDate(BusinessContext ctx) throws Exception 
	{
		ctx.addOutputVariable("G_CURR_DATE", DateTime.getDateString("yyyyMMdd"));
		
		// SessionManager ����
		SessionManager sessionManager = (SessionManager)ServiceManagerFactory.getServiceObject("SessionManager");

		String sessionKey             = ctx.getInputVariable("G_SYSID") + ctx.getInputVariable("G_USER_ID");

		if( sessionManager.isUsing(sessionKey) )	// ���� ���� �� ���� 
		{
			ctx.addOutputVariable("G_LOGOUT", "1"); // �����
		}
		else
		{
			ctx.addOutputVariable("G_LOGOUT", "0"); // �α׾ƿ�
		}
	}

	/**
	 * PS �۾����� ��������
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void getPsWorkingDay(BusinessContext ctx) throws Exception 
	{
		SapFunction stub = new com.helco.xf.ps02.ws.ZWEB_PS_CHECK_WORKING_DAY();
		
		try 
		{
			CallSAP.callInteral(stub
					, new Object[]{
					  CallSAP.getFormatDate(ctx.getInputVariable("I_CHECK_DATE"))
					}
					, ctx.getInputVariable("G_SYSID")
					,ctx.getInputVariable("G_MANDT"));
			
			ctx.addOutputVariable("E_TYPE", stub.getOutput("E_TYPE"));//stub.getOutput("E_TYPE"));
			//stub.getOutput("E_TYPE");
			//ctx.makeSuccessResult("OK");
		} 
		catch( CommRfcException e ) 
		{
			throw new BusinessException(e.getText());
		}
	}

	/**
	 * �� ������ ������ �������� ��û�� ���� ���࿩��, ��ȿ�� �� ���ݾ� ��ȯ(SD FUNCTION)
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void getSdReturnDate(BusinessContext ctx) throws Exception 
	{
		SapFunction stub = new com.helco.xf.wb01.ws.ZSD_RETURN_DATE();
		try 
		{
			CallSAP.callInteral(stub
					, new Object[]{
						  CallSAP.getFormatDate(ctx.getInputVariable("I_DATE")) 
						, ctx.getInputVariable("I_GUBUN")
						, ctx.getInputVariable("I_PSPID")
					}
					, ctx.getInputVariable("G_SYSID")
					,ctx.getInputVariable("G_MANDT"));
			ctx.addOutputVariable("E_FLAG", stub.getOutput("E_FLAG"));
			ctx.addOutputVariable("E_FAKWR", stub.getOutput("E_FAKWR"));
			ctx.addOutputVariable("E_SUGM", stub.getOutput("E_SUGM"));
			ctx.addOutputVariable("E_MECH", stub.getOutput("E_MECH"));
			ctx.addOutputVariable("E_DATE", stub.getOutput("E_DATE"));
			ctx.addOutputVariable("E_TYPE", stub.getOutput("E_TYPE"));
		} 
		catch( CommRfcException e ) 
		{
			throw new BusinessException(e.getText());
		}
	}
	
	/*===========================���Ϲ߼� �߰�================================*/
	/**
	 * ����(����)�� ���� ���Ϲ߼� 
	 * @param ctx
	 * @throws Exception
	 */
	public void sendmail(BusinessContext ctx) throws Exception 
	{
		//SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:LOGIN_S2");
		//sqlRequest.addParamObject("SEND_USER", ctx.getInputVariable("SEND_USER"));
		//sqlRequest.addParamObject("RECE_USER", ctx.getInputVariable("RECE_USER"));
		//sqlRequest.addParamObject("MESGTITL", ctx.getInputVariable("MESGTITL"));
		//sqlRequest.addParamObject("MESGBODY", ctx.getInputVariable("MESGBODY"));
		
		//SqlExecutor db = new SimpleSqlExecutor(getSysConn(ctx.getInputVariable("MANDT")));
		//ResultSet rs = (ResultSet)db.query(sqlRequest).getResultObject();
		
		try {
			// ���� ���Ϸ� ������ 
			MailSendService service = (MailSendService)ServiceManagerFactory.getServiceObject("MailService");
					
			MailSender sendObj = service.createMailSender();
			sendObj.setTo(ctx.getInputVariable("RECE_USER"));
			//sendObj.setCc(ctx.getInputVariable("CC_USER"));
			sendObj.setFrom(ctx.getInputVariable("SEND_USER"));
			//sendObj.setSubject("login information [ hyundai elevator co. ]");
			sendObj.setSubject(ctx.getInputVariable("MESGTITL"));
			sendObj.setBodyType(MailSender.CONTENT_TYPE_TEXT);
			
			
			
			


			
			//String bodyStr = " Hyundai Elevator Web System \n"
			//		       + " user-id : "
			//		       + rs.getString("USERNUMB")
			//		       + " \n temporary-password : "
			//		       + tempPass;
					
			//sendObj.setBody(bodyStr);
			sendObj.setBody(ctx.getInputVariable("MESGBODY"));
					
			// ���� 
			sendObj.sendMessage();

			// ������ commit
			TransactionManager.commit();

		} catch (Exception e) {
			e.printStackTrace();
			TransactionManager.rollback();
			throw new BusinessException("CE00001");
		}
			
	}
	/*=====================================================================*/
	/**
	 * ���� �߼� ��� �߰� (2018-10-11. �۱���)
	 * ���� �߼� �� �߼���, CC, ������ ������� Ȯ�ΰ��� �ϵ��� �߰�.
	 * @param ctx
	 * @throws Exception
	 */
	public void sendmailNew(BusinessContext ctx) throws Exception 
	{
		String mdt = ctx.getInputVariable("G_MANDT");

		/* ���� */
		String recv_user    = ctx.getInputVariable("RECV_USER");    //������ 
		String cc_user      = ctx.getInputVariable("CC_USER");	    // CC
		String bcc_user      = ctx.getInputVariable("BCC_USER");	    // BCC
		String recv_user_nm = ctx.getInputVariable("RECV_USER_NM"); // �����ڸ�
		String cc_user_nm   = ctx.getInputVariable("CC_USER_NM");   // CC ��
		String bcc_user_nm   = ctx.getInputVariable("BCC_USER_NM");   // BCC ��
		
		/* ���� �߼� ��, �������� ��� �����͸� ������,�۱���,XXX �̷�������� ,�� ���� */
		String[] arrRecvUser   = recv_user.split(",", -1);
		String[] arrCcUser     = cc_user.split(",", -1);
		String[] arrBccUser     = bcc_user.split(",", -1);
		String[] arrRecvUserNm = recv_user_nm.split(",", -1);
		String[] arrCcUserNm   = cc_user_nm.split(",", -1);
		String[] arrBccUserNm   = bcc_user_nm.split(",", -1);
		
		String from_user    = ctx.getInputVariable("SEND_USER");    //�߽���
		String from_user_nm = ctx.getInputVariable("SEND_USER_NM"); //�߽��� ��
		String  subject ; //����

		try {
					
				Properties props = System.getProperties();
				props.setProperty("mail.transport.protocol", "smtp");
				props.setProperty("mail.host", HOST);
				props.put("mail.smpt.port", PORT);
				
				Session session = Session.getDefaultInstance(props);
				
		        MimeMessage sdmail = new MimeMessage(session);
		        sdmail.setSubject(ctx.getInputVariable("MESGTITL"));
		        sdmail.setFrom(new InternetAddress(from_user, from_user_nm));
		        
		        for(int i=0; i<arrRecvUser.length;i++)
				{
		        	if(arrRecvUser[i].length() != 0 && arrRecvUserNm[i].length() != 0)
		        	{
		        		sdmail.addRecipient(Message.RecipientType.TO, new InternetAddress(arrRecvUser[i],arrRecvUserNm[i]));
		        	}
		        	
				}
		        for(int i=0; i<arrCcUser.length;i++)
				{
		        	if(arrCcUser[i].length() != 0 && arrCcUserNm[i].length() != 0)
		        	{
		        		sdmail.addRecipient(Message.RecipientType.CC, new InternetAddress(arrCcUser[i],arrCcUserNm[i]));
		        	}
		        	
				}     
		        // �������� �߰�(2019.02.21 ����� å�� ��û����)
		        for(int i=0; i<arrBccUser.length;i++)
				{
		        	if(arrBccUser[i].length() != 0 && arrBccUserNm[i].length() != 0)
		        	{
		        		sdmail.addRecipient(Message.RecipientType.BCC, new InternetAddress(arrBccUser[i],arrBccUserNm[i]));
		        	}
		        	
				}
		        
		        
		        Multipart mtpart = new MimeMultipart();
		        BodyPart bdpart = new MimeBodyPart();
		      
		        bdpart.setContent(ctx.getInputVariable("MESGBODY"), "text/plain;charset=euc-kr");
		        mtpart.addBodyPart(bdpart);
	
		        sdmail.setContent(mtpart);
		        Transport.send(sdmail);
				
				TransactionManager.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
				TransactionManager.rollback();
				throw new BusinessException("CE00001");
			}
	}
	
	/**
	 * �α��� ȭ�鿡�� ����� ���� ã�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void findUserInfo(BusinessContext ctx) throws Exception 
	{
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:LOGIN_S2");
		sqlRequest.addParamObject("USER_NAME", ctx.getInputVariable("USER_NAME"));
		sqlRequest.addParamObject("USER_MAIL", ctx.getInputVariable("USER_EMAIL"));
		sqlRequest.addParamObject("G_MANDT", ctx.getInputVariable("MANDT"));
		
		SqlExecutor db = new SimpleSqlExecutor(getSysConnBySysid(ctx.getInputVariable("G_SYSID")));
		ResultSet rs = (ResultSet)db.query(sqlRequest).getResultObject();
	
		/////db ��ȣȭ ���� 2016.09.26 hss///
		KsignSPinCrypto crypto = new KsignSPinCrypto(ctx.getInputVariable("G_SYSID"));
		/////////////////////////////////////////////

		if ( rs.next() ) 
		{
			// �ӽ� ��й�ȣ ����
			String tempPass = shufflePasswd(10);
			
			SqlRequest sqlRequestUpdate	= SqlMapManagerUtility.makeSqlRequest("wb01:LOGIN_U3");
			sqlRequestUpdate.addParamObject("G_MANDT", ctx.getInputVariable("MANDT"));
			sqlRequestUpdate.addParamObject("G_USER_ID", rs.getString("USERNUMB"));
			sqlRequestUpdate.addParamObject("USER_PWD", tempPass);
			//sqlRequestUpdate.addParamObject("USER_PWD", crypto.encPwd(tempPass));   // db ��ȣȭ ���� 2016.09.26 hss
			sqlRequestUpdate.addParamObject("PWD_EXPR_DATE", DateTime.addDays( DateTime.getDateString("yyyyMMdd"), 7));
			
			if ( db.execute(sqlRequestUpdate) != 1 ) 
			{
				TransactionManager.rollback();
				throw new BusinessException("CE00001");
			} 
			else 
			{
				try {
					// ���� ���Ϸ� ������ 
					MailSendService service = (MailSendService)ServiceManagerFactory.getServiceObject("MailService");
					
					MailSender sendObj = service.createMailSender();
					sendObj.setTo(ctx.getInputVariable("USER_EMAIL"));
					sendObj.setFrom(ConfigUtility.getString("ADMIN_MAIL"));
					sendObj.setSubject("login information [ hyundai elevator co. ]");
					sendObj.setBodyType(MailSender.CONTENT_TYPE_TEXT);
					
					String bodyStr = " Hyundai Elevator Web System \n"
						           + " user-id : "
						           + rs.getString("USERNUMB")
						           + " \n temporary-password : "
						           + tempPass;
					
					sendObj.setBody(bodyStr);
					
					// ���� 
					sendObj.sendMessage();

					// ������ commit
					TransactionManager.commit();

				} catch (Exception e) {
					TransactionManager.rollback();
					throw new BusinessException("CE00001");
				}
			}
			ctx.makeSuccessResult("OK");
		}
		else 
		{
			throw new BusinessException("CW00052");
		}
	}

	/**
	 * �޽��� ������ ����. 
	 * -> �α��� ���� ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void getMsg(BusinessContext ctx) throws Exception 
	{
		// �α��� ���� �޽��� ��ȸ
		LoginService login = (LoginService)ServiceManagerFactory.getServiceObject("LoginService");
		
		Dataset ds = login.makeMessage(ctx.getInputVariable("lang"));
		
		ctx.addOutputVariable("F_T_MANDT", ConfigUtility.getString("MANDT"));	// sap client
		ctx.addOutputDataset(ds);
	}
	
	/**
	 * SAP System�� Connection ��������
	 * 
	 * @param mandt
	 * @return
	 * @throws Exception
	 */
//	public Connection getSysConn(String mandt) throws Exception 
//	{
//		Connection conn = null;
//		// �ý��� �Ǵ� 
//		if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
//		{
//			conn = getConnection("jdbc/hep");	// � 
//		} 
//		else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
//		{
//			conn = getConnection("jdbc/heq");	// ǰ�� 
//		} 
//		else 
//		{
//			conn = getConnection("jdbc/hed");	// ���� 
//		}
//		return conn;
//	}

	/**
	 * SAP System�� Connection ��������
	 * 
	 * @param sysid
	 * @return
	 * @throws Exception
	 */
	public Connection getSysConnBySysid(String sysid) throws Exception 
	{
		Connection conn = null;
		// �ý��� �Ǵ� 
		if ( ConfigUtility.getString("SYSID_HEP").equals(sysid)) 
		{
			conn = getConnection("jdbc/hep");	// � 
		} 
		else if ( ConfigUtility.getString("SYSID_HEQ").equals(sysid)) 
		{
			conn = getConnection("jdbc/heq");	// ǰ�� 
		} 
		else 
		{
			conn = getConnection("jdbc/hed");	// ���� 
		}
		return conn;
	}

	/**
     * ��ü �α����� ����� ��� ã��
     * @param ctx
     * @throws Exception
     */
	public void searchSessionList(BusinessContext ctx) throws Exception
	{
		// Input Dataset
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
	   
		// ���� ���� : dsSession �÷��� SESSION_ID, USER_ID
		//SessionManager sessionManager = (SessionManager)ServiceManagerFactory.getServiceObject("SessionManager");
		Dataset ds_list = getSessionList(ds_cond);
      
		// ����� ����� ����
		//ctx.addOutputDataset("ds_list", getSessionDatasetList(ds_cond, ds_list));
		ctx.addOutputDataset("ds_list", getSessionDatasetList(ds_cond, ds_list));
   }

	/**
     * ����� ���� ������ �����ϱ�
     * @param ctx
     * @throws Exception
     */
	public void removeSession(BusinessContext ctx) throws Exception
	{
		// SessionManager ����
		SessionManager sessionManager = (SessionManager)ServiceManagerFactory.getServiceObject("SessionManager");

		// Dataset ���� ��������
		Dataset ds_list = ctx.getInputDataset("ds_list");

		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
			if("D".equals(ds_list.getColumnAsString(i, "FLAG"))) 
			{//System.out.println(ds_list.getColumnAsString(i, "MANDT")+ds_list.getColumnAsString(i, "USERNUMB"));
				// ������ user id 
				//sessionManager.removeUserInfo(ds_list.getColumnAsString(i, "MANDT")+ds_list.getColumnAsString(i, "USERNUMB"));
				((HttpServletRequest ) ctx.getInputRequest()).getSession().setAttribute("sessionKey", ds_list.getColumnAsString(i, "MANDT")+ds_list.getColumnAsString(i, "USERNUMB"));
				sessionManager.removeUserInfo(ds_list.getColumnAsString(i, "MANDT")+ds_list.getColumnAsString(i, "USERNUMB"));
			}
		
		}

		ctx.makeSuccessResult("OK");
	}


	/**
	 * ����� ����� ���� -- ������� ���� 20160219 HSS
	 * @return Dataset
	 * @throws Exception
	 */
	private Dataset getSessionList(Dataset ds_cond) throws Exception
	{
		StringBuffer strBuf = new StringBuffer();
	
	
		// ����� ����
		strBuf.append("SELECT \n");
		strBuf.append(" MANDT    AS MANDT    \n");
		strBuf.append(",SESSION_KEY AS USERNUMB \n");
		strBuf.append(",LOGIN_TIME AS LOGIN_TIME \n");
		strBuf.append("FROM SAPHEE.ZWBT099 \n");
		strBuf.append("WHERE MANDT= '183' \n");
		strBuf.append("FOR FETCH ONLY \n");
		strBuf.append("WITH UR \n");

		// SqlRequest ����
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(strBuf.toString());
		
	
		// HEQ�� ���� ���������
		String db_con = "jdbc/heq";

		// ��ȸ ó�� 
		SqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = db.query(sqlRequest);
		
		// ��ȸ ��� ��ü ã�ƿ��� 
		return (Dataset)result.getResultObject();
	} 
	
	
	
	/**
	 * ����� ����� ����
	 * @param ds_cond
	 * @param ds_list
	 * @return
	 * @throws Exception
	 */
	private Dataset getSessionDatasetList(Dataset ds_cond, Dataset ds_list) throws Exception
	{
		StringBuffer strBuf = new StringBuffer();
		String userid = null;
		String loginTime = null;
		
		// �α��� �����
		strBuf.append("WITH TEMP1(MANDT,USERNUMB,LOGIN_TIME) AS (VALUES \n");		
		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
			userid = ds_list.getColumnAsString(i, "USERNUMB");
			//userSessionId = ds_list.getColumnAsString(i, "SESSION_ID");
			loginTime = ds_list.getColumnAsString(i, "LOGIN_TIME");
			
			if ( i > 0 )
			{
				strBuf.append(",");
			}
			
			strBuf.append("('")
			      .append(userid.substring(0, 3))
			      .append("','")
			      .append(userid.substring(3))
			      .append("','")
			      .append(loginTime)
			      .append("') \n");
		}		
		strBuf.append(") \n");
		
		// ����� ����
		strBuf.append("SELECT \n");
		strBuf.append(" B.MANDT    AS MANDT    \n");
		strBuf.append(",B.USERNUMB AS USERNUMB \n");
		strBuf.append(",B.USERNAME AS USERNAME \n");
		strBuf.append(",CASE WHEN H.PSNO IS NULL THEN SAPHEE.GET_VENDER_NAME(B.MANDT,B.USERCODE) ELSE DPT1_N END AS USERCODE \n");
		strBuf.append(",B.USERMAIL AS USERMAIL \n"); 
		strBuf.append(",B.USERTELE AS USERTELE \n");
		strBuf.append(",B.USERMBPN AS USERMBPN \n");
		strBuf.append(",B.USERLGIP AS USERLGIP \n");
/*		strBuf.append(",CASE WHEN A.MANDT = B.MANDT THEN \n");
		strBuf.append("           INSERT(INSERT(INSERT(INSERT(INSERT(A.USERLGTM,5,0,'.'),8,0,'.'),11,0,' '),14,0,':'),17,0,':') \n");
		strBuf.append("      ELSE '' END AS USERLGTM \n");*/
		strBuf.append(", A.LOGIN_TIME AS USERLGTM \n");
		strBuf.append(",''         AS FLAG     \n");
		strBuf.append(",'0'        AS CHECK    \n");
		strBuf.append(",A.MANDT|| A.USERNUMB AS SESSION_KEY    \n");
		strBuf.append("FROM SAPHEE.ZUSERF AS B \n");
		strBuf.append("    ,TEMP1         AS A \n");
		strBuf.append("     LEFT OUTER JOIN SAPHEE.ZHRT001 AS H ON  H.MANDT = A.MANDT \n");
		strBuf.append("                                         AND H.PSNO  = 'H' || A.USERNUMB \n");
		strBuf.append("WHERE A.USERNUMB = B.USERNUMB \n");
		// NULL�� �ƴ��� ���� Ȯ�� 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds_cond, "MANDT"))) 
		{
			strBuf.append("  AND B.MANDT = '").append(DatasetUtility.getString(ds_cond, "MANDT")).append("' \n");
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds_cond, "USERNUMB"))) 
		{
			strBuf.append("  AND B.USERNUMB LIKE '").append(DatasetUtility.getString(ds_cond, "USERNUMB")).append("%' \n");
		}
		strBuf.append("ORDER BY MANDT,USERNUMB \n");
		strBuf.append("FOR FETCH ONLY \n");
		strBuf.append("WITH UR \n");

		// SqlRequest ����
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(strBuf.toString());
		
//		// �Ķ���� �����ϱ� 
//		if (TitUtility.isNotNull(DatasetUtility.getString(ds_cond, "MANDT"))) 
//		{
//			sqlRequest.addParamObject("MANDT", DatasetUtility.getString(ds_cond, "MANDT"));
//		}
//		if (TitUtility.isNotNull(DatasetUtility.getString(ds_cond, "USERNUMB"))) 
//		{
//			sqlRequest.addParamObject("USERNUMB", DatasetUtility.getString(ds_cond, "USERNUMB"));
//		}
		
		// JNDI �̸� ��������
		String db_con = Utillity.getSapJndi(DatasetUtility.getString(ds_cond, "MANDT"));

		// ��ȸ ó�� 
		SqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = db.query(sqlRequest);
		
		// ��ȸ ��� ��ü ã�ƿ��� 
		return (Dataset)result.getResultObject();
	} 

	
	private static String shufflePasswd(int len) {
		  
		  char[] charSet = new char[]{
		    '0','1','2','3','4','5','6','7','8','9'
		    ,'A','B','C','D','E','F','G','H','I','J','K','L','M'
		    ,'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		  
		  int idx = 0;
		  StringBuffer sb = new StringBuffer();
		  for(int i=0; i<len; i++){
		   idx = (int)(charSet.length*Math.random());
		   sb.append(charSet[idx]);
		  }
		  
		  return sb.toString();
  }

	
	/**
	 *  ��й�ȣ ��ȣȭ �� ���� (DB��ȣȭ ����)
	 * @param ctx
	 * @throws Exception
	 */
	public void getEncPwd(BusinessContext ctx) throws Exception 
	{

		Dataset dsPwd = new Dataset("ds_pwd");
		
		
		/////db ��ȣȭ ���� 2016.09.26 hss///
		KsignSPinCrypto crypto = new KsignSPinCrypto(ctx.getInputVariable("G_SYSID"));
		/////////////////////////////////////////////		
		
		ctx.addOutputVariable("_ENC_PWD", crypto.encPwd(ctx.getInputVariable("ORG_PWD")));
	}


	/**
	 * dataset ��ȣȭ �� ���� (DB��ȣȭ ����)
	 * @param ctx
	 * @throws Exception
	 */
	public void getDsEncrypt(BusinessContext ctx) throws Exception 
	{

		Dataset dsEnc = ctx.getInputDataset("ds_enc");
		String encCol = ctx.getInputVariable("ENC_COL");
		int nType = Integer.parseInt(ctx.getInputVariable("ENC_TYP"));
		
		
		/////db ��ȣȭ ���� 2016.09.26 hss///
		KsignSPinCrypto crypto = new KsignSPinCrypto(ctx.getInputVariable("G_SYSID"));
		/////////////////////////////////////////////		

		
		for ( int i = 0; i < dsEnc.getRowCount(); i++) {
				switch(nType) {
				case 0   : if(dsEnc.getColumnAsString(i, encCol).length() > 10){ dsEnc.setColumn(i, encCol, crypto.encJumin(dsEnc.getColumnAsString(i, encCol))); }
					        break; 
				case 1   : dsEnc.setColumn(i, encCol, crypto.encAcc(dsEnc.getColumnAsString(i, encCol))); break;
				case 2   : dsEnc.setColumn(i, encCol, crypto.encCard(dsEnc.getColumnAsString(i, encCol))); break;
				default  : break;
				}
		}
		
		Dataset ds_list = dsEnc;
		ds_list.setId("ds_list");
		ctx.addOutputDataset(ds_list);
	}

	
	/**
	 *  dataset ��ȣȭ �� ���� (DB��ȣȭ ����)
	 * @param ctx
	 * @throws Exception
	 */
	public void getDsDecrypt(BusinessContext ctx) throws Exception 
	{

		Dataset dsDec = ctx.getInputDataset("ds_dec");
		String decCol = ctx.getInputVariable("DEC_COL");
		int nType = Integer.parseInt(ctx.getInputVariable("DEC_TYP"));
		
		
		/////db ��ȣȭ ���� 2016.09.26 hss///
		KsignSPinCrypto crypto = new KsignSPinCrypto(ctx.getInputVariable("G_SYSID"));
		/////////////////////////////////////////////		

		
		for ( int i = 0; i < dsDec.getRowCount(); i++) {
				switch(nType) {
				case 0   : if(dsDec.getColumnAsString(i, decCol).length() > 10){ dsDec.setColumn(i, decCol, crypto.decJumin(dsDec.getColumnAsString(i, decCol))); }
					        break; 
				case 1   : dsDec.setColumn(i, decCol, crypto.decAcc(dsDec.getColumnAsString(i, decCol))); break;
				case 2   : dsDec.setColumn(i, decCol, crypto.decCard(dsDec.getColumnAsString(i, decCol))); break;
				default  : break;
				}
		}
		
		Dataset ds_list = dsDec;
		ds_list.setId("ds_list");
		ctx.setOutput("ZLIB");
		ctx.addOutputDataset(ds_list);
	}

	
	
	/**
	 * dataset ��ƼĮ�� ��ȣȭ �� ���� (DB��ȣȭ ����)
	 * @param ctx
	 * @throws Exception
	 */
	public void getDsMultiEncrypt(BusinessContext ctx) throws Exception 
	{

		Dataset dsEnc = ctx.getInputDataset("ds_enc");
		String[] encCol = ctx.getInputVariable("ENC_COL").split(",");
		String[] encTyp = ctx.getInputVariable("ENC_TYP").split(",");
		
		
		/////db ��ȣȭ ���� 2016.09.26 hss///
		KsignSPinCrypto crypto = new KsignSPinCrypto(ctx.getInputVariable("G_SYSID"));
		/////////////////////////////////////////////		

		
		for ( int i = 0; i < dsEnc.getRowCount(); i++) {
			for (int j =0; j < encCol.length; j++)
			{
				int nType = Integer.parseInt(encTyp[j]);
				switch(nType) {
				case 0   : if(dsEnc.getColumnAsString(i, encCol[j]).length() > 10){ dsEnc.setColumn(i, encCol[j], crypto.encJumin(dsEnc.getColumnAsString(i, encCol[j]))); }
					        break; 
				case 1   : dsEnc.setColumn(i, encCol[j], crypto.encAcc(dsEnc.getColumnAsString(i, encCol[j]))); break;
				case 2   : dsEnc.setColumn(i, encCol[j], crypto.encCard(dsEnc.getColumnAsString(i, encCol[j]))); break;
				default  : break;
				}
			}
		}
		
		Dataset ds_list = dsEnc;
		ds_list.setId("ds_list");
		ctx.addOutputDataset(ds_list);
	}


	/**
	 * dataset ��ƼĮ�� ��ȣȭ �� ���� (DB��ȣȭ ����)
	 * @param ctx
	 * @throws Exception
	 */
	public void getDsMultiDecrypt(BusinessContext ctx) throws Exception 
	{

		Dataset dsDec = ctx.getInputDataset("ds_dec");
		String[] decCol = ctx.getInputVariable("DEC_COL").split(",");
		String[] decTyp = ctx.getInputVariable("DEC_TYP").split(",");
		
		
		/////db ��ȣȭ ���� 2016.09.26 hss///
		KsignSPinCrypto crypto = new KsignSPinCrypto(ctx.getInputVariable("G_SYSID"));
		/////////////////////////////////////////////		

		
		for ( int i = 0; i < dsDec.getRowCount(); i++) {
			for (int j =0; j < decCol.length; j++)
			{
				int nType = Integer.parseInt(decTyp[j]);
				switch(nType) {
				case 0   : if(dsDec.getColumnAsString(i, decCol[j]).length() > 10){ dsDec.setColumn(i, decCol[j], crypto.decJumin(dsDec.getColumnAsString(i, decCol[j]))); }
					        break; 
				case 1   : dsDec.setColumn(i, decCol[j], crypto.decAcc(dsDec.getColumnAsString(i, decCol[j]))); break;
				case 2   : dsDec.setColumn(i, decCol[j], crypto.decCard(dsDec.getColumnAsString(i, decCol[j]))); break;
				default  : break;
				}
			}
		}
		
		Dataset ds_list = dsDec;
		ds_list.setId("ds_list");
		ctx.setOutput("ZLIB");
		ctx.addOutputDataset(ds_list);
	}
	
	/**
	 * ����� ��ȿ ��� ��� �� ���ι�ȣ ���� 
	 * @param ctx
	 * @throws Exception
	 */
	public void approvalInfoSend(BusinessContext ctx) throws Exception 
	{
		// 2015.12.10 LJH �̻������ ����
	}

	/**
	 * ����� ��ȿ ��� ��� �� ���ι�ȣ ���� 
	 * @param ctx
	 * @throws Exception
	 */
	public void approvalInfoSend2(BusinessContext ctx) throws Exception 
	{
		// 2015.12.10 LJH �̻������ ����
	}
	
	
	public int getEaiChk(String mandt, String sysid, String rfcName) throws Exception 
	{		
		int cnt ;
		StringBuffer sqlBuff = new StringBuffer();
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		sqlBuff.append("SELECT                          \n");
		//sqlBuff.append(" COUNT(*) AS CNT     			\n");
		sqlBuff.append(" NVL(COUNT(*),0) AS CNT    	\n"); //2020.08.16 jss
		sqlBuff.append("FROM SAPHEE.ZEAICHK             \n");
		sqlBuff.append("WHERE MANDT   = ?           	\n");
		sqlBuff.append("  AND RFCNAME = ?           	\n");
		sqlBuff.append("  AND EAICHK  = 'X'           	\n");
		sqlBuff.append("WITH UR \n");
		
		try {						
			String db_con = Utillity.getSapJndiBySysid(sysid);					
			conn  = DBUtil.getConnection(db_con);			
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			pstmt.setString(1, mandt);
			pstmt.setString(2, rfcName);
			rs = pstmt.executeQuery();
			
			
			if (rs.next()) {
				cnt = rs.getInt("CNT");
			}
			else
				cnt = 0;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				DBUtil.close(rs, pstmt, conn);
				
			} catch (Exception e) {
			}
		}
		
		return cnt;		
	}
	
	
	
}
