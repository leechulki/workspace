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
	 * 서블릿 초기화 처리
	 * @param config 서블릿 config 정보 
	 * @throws ServletException 처리 실패시 
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		if ( config == null ) {
			return;
		}
		
		// debug 설정 
		if ( config.getInitParameter("debug") != null){
			if ( config.getInitParameter("debug").equalsIgnoreCase("true") ) {
				isDebug = true;
			}
		} 
		
		// message 등 처리 
		String msgStr = config.getInitParameter("messageManager");
		
		if ( msgStr != null && !msgStr.trim().equals("")) {
			// Session 정보 확인
			messageServiceName = new ServiceNameEditor();
			messageServiceName.setAsText(msgStr);
		}
		
		// session 등 처리 
		String sessionStr = config.getInitParameter("sessionManager");
		
		if ( sessionStr != null && !sessionStr.trim().equals("")) {
			// Session 정보 확인
			sessionServiceName = new ServiceNameEditor();
			sessionServiceName.setAsText(sessionStr);
		}
		
		// session 등 처리 
		String SqlMap = config.getInitParameter("loginSqlMap");
		
		if ( SqlMap != null && !SqlMap.trim().equals("")) {
			// Session 정보 확인
			loginSqlMap = SqlMap;
		}		
		
		// session 등 처리 
		String userCheck = config.getInitParameter("isUserCheck");
		
		if ( userCheck != null && !userCheck.trim().equals("")) {
			// Session 정보 확인
			isUserCheck = userCheck.equals("true") ? true : false;
		}
		
		// 서비스 정의 파일에서 확인
		// 존재할 경우 web.xml의 속성 정보를 무시한다. 
		String tmp = ServiceManagerFactory.getProperty(INOUT_DEBUG_PROP_NAME);
		if ( tmp != null && !tmp.equals("")) {
			isDebug = Boolean.valueOf(tmp).booleanValue();
		}
		
		// sesssion 체크 여부 확인하기 
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
	 * 사용자 호출 처리
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		// 처리할 class name 
		WebAction action = null;
		MiplatformBusinessContext ctx = null;
		try {
			ctx = new MiplatformBusinessContext(request, response);
			
			String actionNm = ctx.getInputVariable("actionName");
			if ( actionNm == null || actionNm.trim().equals("")) {
				ctx.makeErrorResult(NOT_FOUND_ACTION_NAME);
			} else {
				boolean isOk = true;
							
				// session 정보 확인 
				if ( isUserCheck ) {
					// 로그인 필요 서비스 
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
					
					// 전송 정보 처리 
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
					// 처리 
					action.doAction(ctx);
				} // else session 	
			}	
		} catch( Throwable e){
			// Exception 처리 
			e.printStackTrace();
			if ( ctx == null ) {
				ctx = new MiplatformBusinessContext(response);
			}
			// Error 정보 생성 
			ctx.makeErrorResult(e);
		} finally {
			try {
				// 전송 정보 처리 
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
	 * 서블릿에서 출력할 메시지 코드 찾아오기 
	 * @param msgCode 메시지 코드 
	 * @return 메시지 코드에 해당하는 메시지 문자열 
	 
	protected String findMessage(String msgCode) {
		return findMessage(msgCode, null);
	}*/
	/**
	 * 출력할 메시지 코드 찾아오기 
	 * @param msgCode 메시지 코드
	 * @param emb 메시지에서 대체할 문자열 
	 * @return 메시지 코드에 해당하는 메시지 문자열 
	 
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
