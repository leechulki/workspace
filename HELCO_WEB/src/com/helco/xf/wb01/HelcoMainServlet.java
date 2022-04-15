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
 *  HttpServlet�� ��ӹ޾� ����� ��û ������ �߾� �����ϴ� ���� Ŭ���� <p>
 *  �ش� ������ �⺻������ ������� ��û/���� ������ Miplatform�� �����ͷν� ó���Ѵ�. 
 *  
 *  ���� �Ϲ� Web �������� ��û�� ó���ϰ� �� ��쿡�� �ش� MainServlet�� ��ӹ��� 
 *  ������ ������ �����Ͽ��� �Ѵ�. 
 *  
 *  # init-param���� ���� ���� �Ӽ�
 *  1) debug (�ɼ� �Ӽ�) : ��/��� ������ System.out���� ����� ó�� 
 *     true / false �� ���� 
 *  2) sessionManager (�ɼ� �Ӽ�) : ���������� Session ó���� �� ��� SessionManager ���� ���� 
 *  
 *  # service-def.xml ���� ���� ����
 *  1) INOUT_DEBUG 
 *     debug init-param �� �����ϸ�, ���� ���ǵ� ��� init-param�� �Ӽ��� ���õȴ�. 
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
	 * ���� �ʱ�ȭ ó��
	 * @param config ���� config ���� 
	 * @throws ServletException ó�� ���н� 
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
	 * ����� ȣ�� ó��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// ó���� class name 
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
				// session ���� Ȯ��  : helco.conf�� ����
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
							// �α��� �ʿ� ���� 
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
					
					// ���� ���� ó�� 
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
					// ó�� 
					action.doAction(ctx);
				} // else session 	
			}	
		} 
		catch(Throwable e)
		{
			// Exception ó�� 
			e.printStackTrace();
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
				// ���� ���� ó�� 
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
