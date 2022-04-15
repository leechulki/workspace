package com.helco.xf.wb01;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tit.base.ServiceManagerFactory;
import tit.biz.BusinessException;
import tit.biz.session.SessionManager;
import tit.biz.session.UserInfo;
import tit.service.miplatform.MiplatformBusinessContext;
import tit.service.resource.TransactionManager;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;


/**
 * �α��� ó��  ���� 
 */
public class LoginServlet extends HttpServlet 
{
	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		process(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		process(request, response);
	}
	/**
	 * ����� ȣ�� ó��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MiplatformBusinessContext ctx = null;

		try 
		{
			SessionManager session = (SessionManager)ServiceManagerFactory.getServiceObject("SessionManager");

			LoginService login = (LoginService)ServiceManagerFactory.getServiceObject("LoginService");
			
			ctx = new MiplatformBusinessContext(request, response);
			
			if (session == null || login == null ) 
			{
				ctx.makeErrorResult("Login Service is not found.");
			} 
			else if ( ctx.getInputVariable("cmd") != null && ctx.getInputVariable("cmd").equals("logout")) 
			{
				// �α� �ƿ� ó�� 
				session.removeUserInfo(request);
				
				ctx.makeSuccessResult("0", "OK");
			}
			// 2013.06.18 MACA ���� Ȯ���� ���� USER���� ��ȸ
			else if ( ctx.getInputVariable("cmd") != null && ctx.getInputVariable("cmd").equals("userinfo")) 
			{
				Dataset dsIn = ctx.getInputDataset("ds_cond");
				Dataset ds = new Dataset("ds_user");
				
				ds = login.getUserInfoData(dsIn.getColumnAsString(0, "MANDT"), 
										   dsIn.getColumnAsString(0, "LANG") ,
										   dsIn.getColumnAsString(0, "USER_ID")  );

				ctx.addOutputDataset("ds_user", ds);
				
				ctx.makeSuccessResult("0", "OK");
			} 
			// 2013.06.18 MACA ����  ��ȸ
			else if ( ctx.getInputVariable("cmd") != null && ctx.getInputVariable("cmd").equals("usermaca")) 
			{
				Dataset dsIn = ctx.getInputDataset("ds_cond");
				Dataset ds = new Dataset("ds_user");
				
				ds = login.getUserMacaData(dsIn.getColumnAsString(0, "MANDT"), 
										   dsIn.getColumnAsString(0, "LANG") ,
										   dsIn.getColumnAsString(0, "USER_ID")  );

				ctx.addOutputDataset("ds_user", ds);
				
				ctx.makeSuccessResult("0", "OK");
			} 
			// 2013.06.18 MACA ���� �ڵ����
			else if ( ctx.getInputVariable("cmd") != null && ctx.getInputVariable("cmd").equals("macasave")) 
			{
				Dataset dsIn = ctx.getInputDataset("ds_usermaca");
				
				int iResult = login.setUserMacaData(dsIn.getColumnAsString(0, "USERNUMB") ,
									  dsIn.getColumnAsString(0, "MANDT"),
									  dsIn.getColumnAsString(0, "IPADDR"),
									  dsIn.getColumnAsString(0, "MACADDR"),
									  dsIn.getColumnAsString(0, "CONFNO"),
									  dsIn.getColumnAsString(0, "CONFDT"),
									  dsIn.getColumnAsString(0, "BIGO"),
									  dsIn.getColumnAsString(0, "FLAG"));

				if (iResult == 0)	{
					ctx.makeSuccessResult("0", "OK");
				}else	{
					ctx.makeSuccessResult("-999", "NG");
				}
			} 

			else 
			{
				Dataset dsIn = ctx.getInputDataset("ds_cond");

				String sessionKey = dsIn.getColumnAsString(0, "SYSID") + dsIn.getColumnAsString(0, "USER_ID");
				String login_gb   = dsIn.getColumnAsString(0, "LOGIN_GB");
				
				// �ߺ� �α��� ���� �ʿ� ����. 
				if( session.isUsing(sessionKey)) 
				{
					if (login_gb == null || !login_gb.equals("I"))
					{
						// �̹� ������̴�. 
						throw new BusinessException("ZW00011");
					}
					else
					{
						// ���� Session ����
						session.removeUserInfo(sessionKey);
					}
				}
				
				
				// session ���� ���� 
				UserInfo info = login.makeUserInfo(
						dsIn.getColumnAsString(0, "SYSID"),
						dsIn.getColumnAsString(0, "MANDT"), 
                        dsIn.getColumnAsString(0, "USER_ID"), 
                        dsIn.getColumnAsString(0, "PWD"), 
                        dsIn.getColumnAsString(0, "CLIENT_IP"), 
                        dsIn.getColumnAsString(0, "LANG"));

				session.makeNewUserInfo(request, info, sessionKey);
				
				// ���콺�� �ߺ� �α��� üũ�� ���ؼ� �߰� : 16.02.19 
				((HttpServletRequest ) ctx.getInputRequest()).getSession().setAttribute("sessionKey", sessionKey);
				
				Dataset ds = new Dataset("ds_login");
				Map all = info.getAllInfo();
				Iterator ir = all.keySet().iterator();
				String tempKey = null;
				while( ir.hasNext() ) 
				{
					tempKey = (String)ir.next();
					ds.addColumn(tempKey, ColumnInfo.COLTYPE_STRING, 50);
				}
				ds.addColumn("SESSION_ID", ColumnInfo.COLTYPE_STRING, 50);
				// �ٽ� �б� 
				ir = all.keySet().iterator();
				ds.appendRow();
				while( ir.hasNext() ) 
				{
					tempKey = (String)ir.next();
					ds.setColumn(0, tempKey, info.get(tempKey));
				}
				// ����ID
				ds.setColumn(0, "SESSION_ID", request.getSession().getId());

				ctx.addOutputDataset(ds);
				
				// message �б� 
				if( !info.get(HelcoUserInfo.LANG).equals("ko")) 
				{
					Dataset dsMsg = login.makeMessage(info.get(HelcoUserInfo.LANG));
					ctx.addOutputDataset("gds_message", dsMsg);
				}
				
				// ���� ���� �������� 
				Dataset dsAuth = login.makeAuthData(info.get("SYSID"),info.get(HelcoUserInfo.USER_ID),info.get(HelcoUserInfo.LANG),dsIn.getColumnAsString(0, "MANDT"));
				if ( dsAuth != null ) 
				{
					ctx.addOutputDataset("gds_auth", dsAuth);
				}
				// �޴� ���� �б�
				Dataset dsMenu = login.makeMenuData(info.get("SYSID"),info.get(HelcoUserInfo.USER_ID),info.get(HelcoUserInfo.LANG),dsIn.getColumnAsString(0, "MANDT"));
				if ( dsMenu != null ) 
				{
					ctx.addOutputDataset("gds_menu", dsMenu);
				}
				
				ctx.makeSuccessResult("OK");
			}
		} 
		catch( Throwable e) 
		{
			// Exception ó�� 
//			e.printStackTrace();
			if ( ctx == null ) 
			{
				ctx = new MiplatformBusinessContext(response);
			}
			// Error ���� ���� 
			ctx.makeErrorResult(e);
		} 
		finally 
		{
			try 
			{
				TransactionManager.commit();
				TransactionManager.close();
				//ctx.setOutput("ZLIB");
				ctx.sendData();
			} 
			catch( Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}
