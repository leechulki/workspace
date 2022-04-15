package com.helco.xf.wb01.ws;

import java.sql.Connection;
import java.sql.ResultSet;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.wb01.ws.ZQMS006;
import com.helco.xf.wb01.ws.ZQMS028;
import com.helco.xf.wb01.ws.ZQM_READ_TEXT;
import com.tobesoft.platform.data.Dataset;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.biz.BusinessException;
import tit.service.business.config.ConfigUtility;
import tit.service.mail.MailSendService;
import tit.service.mail.MailSender;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.util.StringOperator;

/**
 * ���� ��û ���� �������� 
 */
public class WB0199001A_ACT extends AbstractAction 
{
	/**
	 * �α��� ȭ�鿡�� ����� ���� ã�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void findUserInfo(BusinessContext ctx) throws Exception 
	{
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:LOGIN_S2");
		sqlRequest.addParamObject("USER_JUMIN", ctx.getInputVariable("USER_JUMIN"));
		sqlRequest.addParamObject("USER_VEND", ctx.getInputVariable("USER_VEND"));
		sqlRequest.addParamObject("G_MANDT", ConfigUtility.getString("MANDT"));
		
		SqlExecutor db = new SimpleSqlExecutor(getSysConn(ConfigUtility.getString("MANDT")));
		ResultSet rs = (ResultSet)db.query(sqlRequest).getResultObject();
		
		if ( rs.next() ) 
		{
			// ���� ���Ϸ� ������ 
			MailSendService service = (MailSendService)ServiceManagerFactory.getServiceObject("MailService");
			
			MailSender sendObj = service.createMailSender();
			sendObj.setTo(ctx.getInputVariable("USER_EMAIL"));
			sendObj.setFrom(ConfigUtility.getString("ADMIN_MAIL"));
			sendObj.setSubject("login information [ hyundai elevator co. ]");
			sendObj.setBodyType(MailSender.CONTENT_TYPE_TEXT);
			
			String bodyStr = "user-id : "
				           + rs.getString("USERNUMB")
				           + " \nuser-password : "
				           + rs.getString("USERPASS");
			
			sendObj.setBody(bodyStr);
			
			// ���� 
			sendObj.sendMessage();
			ctx.makeSuccessResult("OK");
		}
		else 
		{
			throw new BusinessException("CW00052");
		}
	}

	/**
	 * SAP System�� Conection ��������
	 * 
	 * @param mandt
	 * @return
	 * @throws Exception
	 */
	public Connection getSysConn(String mandt) throws Exception 
	{
		Connection conn = null;
		// �ý��� �Ǵ� 
		if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
		{
			conn = getConnection("jdbc/hep");	// � 
		} 
		else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
		{
			conn = getConnection("jdbc/heq");	// ǰ�� 
		} 
		else 
		{
			conn = getConnection("jdbc/hed");	// ���� 
		}

		return conn;
	}
}
