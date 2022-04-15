package com.helco.xf.comm;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tit.base.ServiceManagerFactory;
import tit.base.ServiceName;
import tit.beans.ServiceNameEditor;
import tit.biz.WebAction;
import tit.biz.session.SessionManager;
import tit.biz.session.UserInfo;
import tit.service.business.ServiceFactoryManager;
import tit.service.miplatform.servlet.MainServlet;

import com.helco.comm.SessionObject;
import com.helco.xf.wb01.WebBusinessContext;

public class WebMainServlet extends MainServlet {

	/**
	 * Main 처리 
	 */
	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		// 처리할 class name 
		WebAction action = null;
		WebBusinessContext ctx = null;
		
		try {
			ctx = new WebBusinessContext(request, response, getServletContext());
			
			String actionNm = ctx.getActionName();
			if ( actionNm == null || actionNm.trim().equals("")) {
				ctx.makeErrorResult("Action Class is not defined.");
			//	ctx.makeErrorResult(findMessage(MSG_00001));
			} else {
				System.out.println("==============================  "+actionNm);
				System.out.println("==============================  "+request.getSession().getId());
				
				boolean isOk = true;
				// session 정보 확인  
				if ( sessionServiceName != null ) {
					SessionManager session = (SessionManager) 
						ServiceManagerFactory.getServiceObject((ServiceName)sessionServiceName.getValue());
					
					if ( session != null ) {
						if ( session.isNeedLoggin(actionNm)) {
							// 로그인 필요 서비스 
							// UserInfo info = ctx.getUserInfo();
							UserInfo info = (UserInfo) session.findUserInfo(request);
							
							if ( info == null ) {
								ctx.makeErrorResult("NOT_LOGIN", "로그인 되어있지 않습니다.");
								// output page 설정
								ctx.setOutput("notlogin.jsp");
								isOk = false;
							} else {
								ctx.setUserInfo(info);
								
								
							}	
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
						
						Enumeration e = request.getParameterNames();
						String key = null;
						String[] values = null;
						while(e.hasMoreElements()) {
							key = (String)e.nextElement();
							values = request.getParameterValues(key);
							if ( values.length > 1 ) {
								for ( int i = 0; i < values.length; i++) {
									sw.write(key + "[" + i + "]:" + values[i] + "\n");
								}
							} else {
								sw.write(key + ":" + values[0] + "\n");
							}	
						}
						
						System.out.println( sw.toString() );
						pw.close();
						sw.close();
					}	
					// 처리 
					action.doAction(ctx);
				}	
			}	
		} catch( Throwable e){
			// Exception 처리 
			e.printStackTrace();
			if ( ctx == null ) {
				ctx = new WebBusinessContext(request, response, getServletContext());
			}
			ctx.makeErrorResult(e);
		} finally {
			try {
				ctx.sendData();
			} catch( Exception e){
				e.printStackTrace();
			}	
		}
	}
}
