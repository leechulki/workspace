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
 * 로그인 처리  서블릿 
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
	 * 사용자 호출 처리
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
				// 로그 아웃 처리 
				session.removeUserInfo(request);
				
				ctx.makeSuccessResult("0", "OK");
			}
			// 2013.06.18 MACA 정보 확인을 위한 USER정보 조회
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
			// 2013.06.18 MACA 정보  조회
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
			// 2013.06.18 MACA 정보 자동등록
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
				
				// 중복 로그인 관련 필요 없음. 
				if( session.isUsing(sessionKey)) 
				{
					if (login_gb == null || !login_gb.equals("I"))
					{
						// 이미 사용중이다. 
						throw new BusinessException("ZW00011");
					}
					else
					{
						// 기존 Session 삭제
						session.removeUserInfo(sessionKey);
					}
				}
				
				
				// session 정보 생성 
				UserInfo info = login.makeUserInfo(
						dsIn.getColumnAsString(0, "SYSID"),
						dsIn.getColumnAsString(0, "MANDT"), 
                        dsIn.getColumnAsString(0, "USER_ID"), 
                        dsIn.getColumnAsString(0, "PWD"), 
                        dsIn.getColumnAsString(0, "CLIENT_IP"), 
                        dsIn.getColumnAsString(0, "LANG"));

				session.makeNewUserInfo(request, info, sessionKey);
				
				// 제우스의 중복 로그인 체크를 위해서 추가 : 16.02.19 
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
				// 다시 읽기 
				ir = all.keySet().iterator();
				ds.appendRow();
				while( ir.hasNext() ) 
				{
					tempKey = (String)ir.next();
					ds.setColumn(0, tempKey, info.get(tempKey));
				}
				// 세션ID
				ds.setColumn(0, "SESSION_ID", request.getSession().getId());

				ctx.addOutputDataset(ds);
				
				// message 읽기 
				if( !info.get(HelcoUserInfo.LANG).equals("ko")) 
				{
					Dataset dsMsg = login.makeMessage(info.get(HelcoUserInfo.LANG));
					ctx.addOutputDataset("gds_message", dsMsg);
				}
				
				// 권한 정보 가져오기 
				Dataset dsAuth = login.makeAuthData(info.get("SYSID"),info.get(HelcoUserInfo.USER_ID),info.get(HelcoUserInfo.LANG),dsIn.getColumnAsString(0, "MANDT"));
				if ( dsAuth != null ) 
				{
					ctx.addOutputDataset("gds_auth", dsAuth);
				}
				// 메뉴 정보 읽기
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
			// Exception 처리 
//			e.printStackTrace();
			if ( ctx == null ) 
			{
				ctx = new MiplatformBusinessContext(response);
			}
			// Error 정보 생성 
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
