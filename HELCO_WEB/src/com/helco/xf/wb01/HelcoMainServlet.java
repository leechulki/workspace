package com.helco.xf.wb01;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tit.base.ServiceManagerFactory;
import tit.base.ServiceName;
import tit.beans.ServiceNameEditor;
import tit.biz.WebAction;
import tit.biz.session.SessionManager;
import tit.biz.session.UserInfo;
import tit.service.business.ServiceFactoryManager;
import tit.service.business.config.ConfigUtility;
import tit.service.miplatform.MiplatformBusinessContext;
import tit.service.miplatform.servlet.MainServlet;

/**
 * <pre>
 *  HttpServlet을 상속받아 사용자 요청 정보를 중앙 관리하는 서블릿 클래스 <p>
 *  해당 서블릿은 기본적으로 사용자의 요청/응답 정보를 Miplatform용 데이터로써 처리한다. 
 *  
 *  만약 일반 Web 페이지의 요청을 처리하고 할 경우에는 해당 MainServlet을 상속받은 
 *  별도의 서블릿을 구현하여야 한다. 
 *  
 *  # init-param으로 정의 가능 속성
 *  1) debug (옵션 속성) : 입/출력 정보를 System.out으로 디버깅 처리 
 *     true / false 로 설정 
 *  2) sessionManager (옵션 속성) : 서블릿내에서 Session 처리를 할 경우 SessionManager 서비스 정의 
 *  
 *  # service-def.xml 파일 정의 내용
 *  1) INOUT_DEBUG 
 *     debug init-param 과 동일하며, 같이 정의될 경우 init-param의 속성은 무시된다. 
 *  2) SESSION_CHECK
 *     sessionManager init-param
 * </pre>
 * @author TrueInfoTec
 * @version 1.0 
 * @since 2007/09/11
 */
public class HelcoMainServlet extends MainServlet
{
	protected boolean isDebug = false;
	protected ServiceNameEditor sessionServiceName = null;
	protected ServiceNameEditor messageServiceName = null;
	protected String noSessionCode = "-200";
	protected String noSessionMsg = "not logined.";
	
	private final static String NOT_FOUND_ACTION_NAME = "Request argument[ actionName ] is not found.";
	
	// debug 
	private final static String INOUT_DEBUG_PROP_NAME = "INOUT_DEBUG";
	private final static String SESSION_CHECK_PROP_NAME = "SESSION_CHECK";
	private final static String SESSION_SERVICE_PROP_NAME = "SESSION_SERVICE";
	private final static String SESSION_NO_CODE_PROP_NAME = "SESSION_NO_CODE";
	private final static String SESSION_NO_MSG_PROP_NAME = "SESSION_NO_MSG";
	
	/**
	 * 서블릿 초기화 처리
	 * @param config 서블릿 config 정보 
	 * @throws ServletException 처리 실패시 
	 */
	public void init(ServletConfig config) throws ServletException {
		isDebug = false;
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		sessionServiceName = new ServiceNameEditor();
		sessionServiceName.setAsText("SessionManager");
	}

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
		// 처리할 class name 
		WebAction action = null;
		MiplatformBusinessContext ctx = null;

		try 
		{
			ctx = new MiplatformBusinessContext(request, response);
			
			String actionNm = ctx.getInputVariable("actionName");
			if ( actionNm == null || actionNm.trim().equals("") ) 
			{
				ctx.makeErrorResult(NOT_FOUND_ACTION_NAME);
			} 
			else 
			{
				boolean isOk = true;
				// session 정보 확인  : helco.conf에 정의
				if( ConfigUtility.getString("NO_SESSION_ACT").indexOf(actionNm) != -1 ) 
				{
				}
				else if ( sessionServiceName != null ) 
				{
					SessionManager session = (SessionManager)ServiceManagerFactory.getServiceObject((ServiceName)sessionServiceName.getValue());
					
					if ( session != null ) 
					{
						if ( session.isNeedLoggin(actionNm) ) 
						{
							// 로그인 필요 서비스 
							UserInfo info = (UserInfo)session.findUserInfo(request);
							
							if ( info == null ) 
							{
								ctx.makeErrorResult(Integer.parseInt(noSessionCode), noSessionMsg);
								isOk = false;
							} 
							else 
							{
								ctx.setUserInfo(info);
							}	
						}
					}
					else
					{
						ctx.makeErrorResult(Integer.parseInt(noSessionCode), noSessionMsg);
						isOk = false;
					}
				}
				else
				{
					ctx.makeErrorResult(Integer.parseInt(noSessionCode), noSessionMsg);
					isOk = false;
				}
				
				if ( isOk ) 
				{
					if ( ServiceFactoryManager.contains(actionNm) ) 
					{
						action = (WebAction)ServiceFactoryManager.getService(actionNm);
					} 
					else 
					{
						ServiceNameEditor ed = new ServiceNameEditor();
						ed.setAsText(actionNm);
						action = (WebAction)ServiceManagerFactory.getServiceObject((ServiceName)ed.getValue());
					}
					
					// 전송 정보 처리 
					if ( isDebug ) 
					{
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						pw.write("======================= INDATA ========================\n");
						pw.write("[Datasets]\n");
						ctx.getInputDatasetList().writeTo(pw, ctx.getCharset());
						pw.write("\n[Variables]\n");
						ctx.getInputVariableList().writeTo(pw);
						System.out.println( sw.toString() );
						pw.close();
						sw.close();
					}	
					// 처리 
					action.doAction(ctx);
				} // else session 	
			}	
		} 
		catch(Throwable e)
		{
			// Exception 처리 
			e.printStackTrace();
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
				// 전송 정보 처리 
				if ( isDebug ) 
				{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					pw.write("======================= OUTDATA ========================\n");
					pw.write("[Datasets]\n");
					ctx.getOutputDatasetList().writeTo(pw, ctx.getCharset());
					pw.write("\n[Variables]\n");
					ctx.getOutputVariableList().writeTo(pw);
					System.out.println( sw.toString() );
					pw.close();
					sw.close();
				}
				
				ctx.sendData();
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}	
		}
	}
}
