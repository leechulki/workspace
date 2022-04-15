package com.helco.xf.wb01;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class HttpLoginManager implements HttpSessionBindingListener {

	private Hashtable mLoginUser = new Hashtable();
	
	/**
	 * 세션이 연결될 때 호출 됨. ( session.setAttribute("login", this) )
	 * Hashtable에 세션과 접속자 아이디를 저장한다.  
	 */
	public void valueBound(HttpSessionBindingEvent arg0) {
		mLoginUser.put(arg0.getSession(), arg0.getName());
	}

	/**
	 * 세션이 끊겼을 경우 호출 ( invalidate )
	 * Hashtable에 저장된 로그인 정보 삭제 
	 */
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		mLoginUser.remove(arg0.getSession());
	}

	public void removeSession(String userId) {
		Enumeration e = mLoginUser.keys();
		HttpSession session = null;
		while( e.hasMoreElements()) {
			session = (HttpSession) e.nextElement();
			if( mLoginUser.get(session).equals(userId)) {
				// 세션이 invalidate 될 때 HttpSessionBindingLisiner의
				// valueUnbound() 호출 됨
				session.invalidate();
			}
		}
	}
	
	public void setSession(HttpSession session, String userId) {
		session.setAttribute(userId, this);
	}
}
