package com.helco.xf.comm;

import tit.service.business.config.ConfigUtility;

import tit.biz.session.SessionManager;
import tit.biz.session.UserInfo;
import tit.base.ServiceManagerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Utillity {
	
	/**
	 * SAP용 JNDI 이름 가져오기 
	 * @return
	 * @throws Exception
	 */
	public static String getSapJndi() throws Exception 
	{
		return getSapJndi(ConfigUtility.getString("MANDT"));
	}
	
	/**
	 * SAP용 JNDI 이름 가져오기 
	 * @param mandt
	 * @return
	 * @throws Exception
	 */
	
	
/*	sysid를 이용하여 기존 getSapJndi 사용하기 위한 주석처리후 아래 로직 사용
	public static String getSapJndi(String mandt) throws Exception 
	{
		String result = null;	 
		// 시스템 판단 
		if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
		{
			result = "jdbc/hep";	// 운영
		} 
		else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
		{
			result = "jdbc/heq";	// 품질
		}
		else
		{
			result = "jdbc/hed";	// 개발
		}
		
		return result;
	}
*/
	

	
	
	public static String getSapJndi(String mandt) throws Exception 
	{
		String result = null;
		
		// 세션객체 선언
		SessionManager session = (SessionManager)ServiceManagerFactory.getServiceObject("SessionManager");
		
		HttpServletRequest request = null;
		UserInfo info = null;
		
		try {
			// request 선언
			request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			
			// 유저정보 회득
			info = (UserInfo) session.findUserInfo(request);
			
			if(info.get("SYSID") != "") {
				result = getSapJndiBySysid(info.get("SYSID"));
			} else {
				// 시스템 판단 
				if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
				{
					result = "jdbc/hep";	// 운영
				} 
				else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
				{
					result = "jdbc/heq";	// 품질
				}
				else
				{
					result = "jdbc/hed";	// 개발
				}
			}
			
			
		} catch (Exception e) {
			// 시스템 판단 
			if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
			{
				result = "jdbc/hep";	// 운영
			} 
			else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
			{
				result = "jdbc/heq";	// 품질
			}
			else
			{
				result = "jdbc/hed";	// 개발
			}
		}
		/* batch 실행시 request null 에러로 인한 주석처리. 2019.07.03 j.h
		// request 선언
		HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		// 유저정보 회득
		UserInfo info = (UserInfo) session.findUserInfo(request);

		
		if(info.get("SYSID") != "") {
			result = getSapJndiBySysid(info.get("SYSID"));
		} else {
			// 시스템 판단 
			if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
			{
				result = "jdbc/hep";	// 운영
			} 
			else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
			{
				result = "jdbc/heq";	// 품질
			}
			else
			{
				result = "jdbc/hed";	// 개발
			}
		}
		*/
		
		
		return result;
	}	
	
	
	

	public static String getSapJndiBySysid(String sysid) throws Exception 
	{
		String result = null;	 
		// 시스템 판단 
		if ( ConfigUtility.getString("SYSID_HEP").indexOf(sysid) != -1 ) 
		{
			result = "jdbc/hep";	// 운영
		} 
		else if ( ConfigUtility.getString("SYSID_HEQ").indexOf(sysid) != -1 ) 
		{
			result = "jdbc/heq";	// 품질
		}
		else
		{
			result = "jdbc/hed";	// 개발
		}
		
		return result;
	}



}
