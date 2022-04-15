package com.helco.xf.comm;

import javax.servlet.http.HttpSessionEvent;

import tit.base.ServiceManagerFactory;
import tit.biz.session.SessionManager;

import com.ibm.websphere.servlet.session.IBMSessionListener;

public class HelcoSessionListener implements IBMSessionListener 
{

	public void sessionRemovedFromCache(String arg0) 
	{
		// TODO Auto-generated method stub
	}

	public void sessionCreated(HttpSessionEvent event) 
	{
        System.out.println((new StringBuilder("sessionCreated... session id[")).append(event.getSession().getId()).append("]").toString());
	}

	public void sessionDestroyed(HttpSessionEvent event) 
	{
        System.out.println((new StringBuilder("sessionDestroyed... session id[")).append(event.getSession().getId()).append("]").toString());

        SessionManager sessionmanager = (SessionManager)ServiceManagerFactory.getServiceObject("SessionManager");
        if(sessionmanager != null)
        {
            sessionmanager.removeUserInfo(event.getSession());
        }
	}

}
