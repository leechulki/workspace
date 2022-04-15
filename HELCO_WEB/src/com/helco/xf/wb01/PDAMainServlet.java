package com.helco.xf.wb01;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tobesoft.platform.util.Base64Converter;

import tit.base.ServiceManagerFactory;
import tit.base.ServiceName;
import tit.beans.ServiceNameEditor;
import tit.biz.WebAction;
import tit.service.business.ServiceFactoryManager;
import tit.service.miplatform.MiplatformBusinessContext;

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
public class PDAMainServlet extends HttpServlet {
	protected boolean isDebug = false;
	protected ServiceNameEditor sessionServiceName = null;
	protected ServiceNameEditor messageServiceName = null;
	protected String noSessionCode = "-200";
	protected String noSessionMsg = "not logined.";
	protected String loginSqlMap = "PCC04:LOGIN_S01";
	protected boolean isUserCheck = false;
	
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
		super.init(config);
		
		if ( config == null ) {
			return;
		}
		
		// debug ���� 
		if ( config.getInitParameter("debug") != null){
			if ( config.getInitParameter("debug").equalsIgnoreCase("true") ) {
				isDebug = true;
			}
		} 
		
		// message �� ó�� 
		String msgStr = config.getInitParameter("messageManager");
		
		if ( msgStr != null && !msgStr.trim().equals("")) {
			// Session ���� Ȯ��
			messageServiceName = new ServiceNameEditor();
			messageServiceName.setAsText(msgStr);
		}
		
		// session �� ó�� 
		String sessionStr = config.getInitParameter("sessionManager");
		
		if ( sessionStr != null && !sessionStr.trim().equals("")) {
			// Session ���� Ȯ��
			sessionServiceName = new ServiceNameEditor();
			sessionServiceName.setAsText(sessionStr);
		}
		
		// session �� ó�� 
		String SqlMap = config.getInitParameter("loginSqlMap");
		
		if ( SqlMap != null && !SqlMap.trim().equals("")) {
			// Session ���� Ȯ��
			loginSqlMap = SqlMap;
		}		
		
		// session �� ó�� 
		String userCheck = config.getInitParameter("isUserCheck");
		
		if ( userCheck != null && !userCheck.trim().equals("")) {
			// Session ���� Ȯ��
			isUserCheck = userCheck.equals("true") ? true : false;
		}
		
		// ���� ���� ���Ͽ��� Ȯ��
		// ������ ��� web.xml�� �Ӽ� ������ �����Ѵ�. 
		String tmp = ServiceManagerFactory.getProperty(INOUT_DEBUG_PROP_NAME);
		if ( tmp != null && !tmp.equals("")) {
			isDebug = Boolean.valueOf(tmp).booleanValue();
		}
		
		// sesssion üũ ���� Ȯ���ϱ� 
		tmp = ServiceManagerFactory.getProperty(SESSION_CHECK_PROP_NAME);
		boolean isSession = false;
		if ( tmp != null && !tmp.equals("")) {
			isSession = Boolean.valueOf(tmp).booleanValue();
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	/**
	 * ����� ȣ�� ó��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		// ó���� class name 
		WebAction action = null;
		MiplatformBusinessContext ctx = null;
		try {
			ctx = new MiplatformBusinessContext(request, response);
			
			String actionNm = ctx.getInputVariable("actionName");
			if ( actionNm == null || actionNm.trim().equals("")) {
				ctx.makeErrorResult(NOT_FOUND_ACTION_NAME);
			} else {
				boolean isOk = true;
							
				// session ���� Ȯ�� 
				if ( isUserCheck ) {
					// �α��� �ʿ� ���� 
					String GV_USER_ID = ctx.getInputVariable("GV_USER_ID");
					String GV_LOGINKEY = ctx.getInputVariable("GV_LOGINKEY");

					if(GV_USER_ID ==null || GV_USER_ID.equals("") || GV_LOGINKEY ==null || GV_LOGINKEY.equals("")){
						isOk =false;
						if(actionNm.equals("PDALoginAction")){
							isOk = true;
						}
					}else{
						MessageDigest md = MessageDigest.getInstance("SHA-1");
						md.update(GV_USER_ID.getBytes());
						byte[] digest = md.digest();
						
						if ( GV_LOGINKEY == null || GV_LOGINKEY.equals("") || !GV_LOGINKEY.equals(Base64Converter.encode(digest))) {
							ctx.makeErrorResult(Integer.parseInt(noSessionCode), noSessionMsg);
							isOk = false;
						} 
					}
				}
				
				if ( isOk ) {
					if ( ServiceFactoryManager.contains(actionNm)) {
						action = (WebAction) ServiceFactoryManager.getService(actionNm);
					} else {
						ServiceNameEditor ed = new ServiceNameEditor();
						ed.setAsText(actionNm);
						action = (WebAction) ServiceManagerFactory.getServiceObject((ServiceName)ed.getValue());
					}
					
					// ���� ���� ó�� 
					if ( isDebug ) {
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
		} catch( Throwable e){
			// Exception ó�� 
			e.printStackTrace();
			if ( ctx == null ) {
				ctx = new MiplatformBusinessContext(response);
			}
			// Error ���� ���� 
			ctx.makeErrorResult(e);
		} finally {
			try {
				// ���� ���� ó�� 
				if ( isDebug ) {
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
			} catch( Exception e){
				e.printStackTrace();
			}	
		}
	}
	/**
	 * �������� ����� �޽��� �ڵ� ã�ƿ��� 
	 * @param msgCode �޽��� �ڵ� 
	 * @return �޽��� �ڵ忡 �ش��ϴ� �޽��� ���ڿ� 
	 
	protected String findMessage(String msgCode) {
		return findMessage(msgCode, null);
	}*/
	/**
	 * ����� �޽��� �ڵ� ã�ƿ��� 
	 * @param msgCode �޽��� �ڵ�
	 * @param emb �޽������� ��ü�� ���ڿ� 
	 * @return �޽��� �ڵ忡 �ش��ϴ� �޽��� ���ڿ� 
	 
	protected String findMessage(String msgCode, String emb) {
		MessageRecordFactory msgFactory = ServiceManagerFactory.getMessageRecordFactory();
		if ( messageServiceName != null ) {
			msgFactory = (MessageRecordFactory) ServiceManagerFactory.getServiceObject(
					(ServiceName) messageServiceName.getValue() );
		}
		
		if ( emb == null ) {
			return msgFactory.findMessage(msgCode);
		} else {
			return msgFactory.findEmbedMessage(msgCode, emb);
		}	
	}*/
}
