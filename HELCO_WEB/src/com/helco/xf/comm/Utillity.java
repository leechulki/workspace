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
	 * SAP�� JNDI �̸� �������� 
	 * @return
	 * @throws Exception
	 */
	public static String getSapJndi() throws Exception 
	{
		return getSapJndi(ConfigUtility.getString("MANDT"));
	}
	
	/**
	 * SAP�� JNDI �̸� �������� 
	 * @param mandt
	 * @return
	 * @throws Exception
	 */
	
	
/*	sysid�� �̿��Ͽ� ���� getSapJndi ����ϱ� ���� �ּ�ó���� �Ʒ� ���� ���
	public static String getSapJndi(String mandt) throws Exception 
	{
		String result = null;	 
		// �ý��� �Ǵ� 
		if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
		{
			result = "jdbc/hep";	// �
		} 
		else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
		{
			result = "jdbc/heq";	// ǰ��
		}
		else
		{
			result = "jdbc/hed";	// ����
		}
		
		return result;
	}
*/
	

	
	
	public static String getSapJndi(String mandt) throws Exception 
	{
		String result = null;
		
		// ���ǰ�ü ����
		SessionManager session = (SessionManager)ServiceManagerFactory.getServiceObject("SessionManager");
		
		HttpServletRequest request = null;
		UserInfo info = null;
		
		try {
			// request ����
			request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			
			// �������� ȸ��
			info = (UserInfo) session.findUserInfo(request);
			
			if(info.get("SYSID") != "") {
				result = getSapJndiBySysid(info.get("SYSID"));
			} else {
				// �ý��� �Ǵ� 
				if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
				{
					result = "jdbc/hep";	// �
				} 
				else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
				{
					result = "jdbc/heq";	// ǰ��
				}
				else
				{
					result = "jdbc/hed";	// ����
				}
			}
			
			
		} catch (Exception e) {
			// �ý��� �Ǵ� 
			if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
			{
				result = "jdbc/hep";	// �
			} 
			else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
			{
				result = "jdbc/heq";	// ǰ��
			}
			else
			{
				result = "jdbc/hed";	// ����
			}
		}
		/* batch ����� request null ������ ���� �ּ�ó��. 2019.07.03 j.h
		// request ����
		HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		// �������� ȸ��
		UserInfo info = (UserInfo) session.findUserInfo(request);

		
		if(info.get("SYSID") != "") {
			result = getSapJndiBySysid(info.get("SYSID"));
		} else {
			// �ý��� �Ǵ� 
			if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
			{
				result = "jdbc/hep";	// �
			} 
			else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
			{
				result = "jdbc/heq";	// ǰ��
			}
			else
			{
				result = "jdbc/hed";	// ����
			}
		}
		*/
		
		
		return result;
	}	
	
	
	

	public static String getSapJndiBySysid(String sysid) throws Exception 
	{
		String result = null;	 
		// �ý��� �Ǵ� 
		if ( ConfigUtility.getString("SYSID_HEP").indexOf(sysid) != -1 ) 
		{
			result = "jdbc/hep";	// �
		} 
		else if ( ConfigUtility.getString("SYSID_HEQ").indexOf(sysid) != -1 ) 
		{
			result = "jdbc/heq";	// ǰ��
		}
		else
		{
			result = "jdbc/hed";	// ����
		}
		
		return result;
	}



}
