package com.helco.xf.wb01;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class HttpLoginManager implements HttpSessionBindingListener {

	private Hashtable mLoginUser = new Hashtable();
	
	/**
	 * ������ ����� �� ȣ�� ��. ( session.setAttribute("login", this) )
	 * Hashtable�� ���ǰ� ������ ���̵� �����Ѵ�.  
	 */
	public void valueBound(HttpSessionBindingEvent arg0) {
		mLoginUser.put(arg0.getSession(), arg0.getName());
	}

	/**
	 * ������ ������ ��� ȣ�� ( invalidate )
	 * Hashtable�� ����� �α��� ���� ���� 
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
				// ������ invalidate �� �� HttpSessionBindingLisiner��
				// valueUnbound() ȣ�� ��
				session.invalidate();
			}
		}
	}
	
	public void setSession(HttpSession session, String userId) {
		session.setAttribute(userId, this);
	}
}
