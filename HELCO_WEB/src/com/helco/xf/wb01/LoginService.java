package com.helco.xf.wb01;

import com.tobesoft.platform.data.Dataset;
import tit.biz.session.UserInfo;

/**
 * �α��� ��� ���� 
 */
public interface LoginService {

	/**
	 * ����� ���̵�, ��й�ȣ�� �Է� �޾� �α��� ó�� 
	 * @param userId
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public UserInfo makeUserInfo(String userId, String pwd) throws Exception;
	
	/**
	 * ����� ���̵�, ��й�ȣ�� �Է� �޾� �α��� ó�� 
	 * @param userId
	 * @param pwd
	 * @param clientIp
	 * @return
	 * @throws Exception
	 */
//	public UserInfo makeUserInfo(String mandt, String userId, String pwd, String clientIp, String lang) throws Exception;
	public UserInfo makeUserInfo(String sysid, String mandt, String userId, String pwd, String clientIp, String lang) throws Exception;
	/**
	 * ����� language�� �´� �޽��� �о� ���̱� 
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	public Dataset makeMessage(String lang) throws Exception;
	
	/**
	 * ����ڿ� �ش��ϴ� ���� ���� �о� ���̱� 
	 * @param userId
	 * @param lang	��� 
	 * @return
	 * @throws Exception
	 */
//	public Dataset makeAuthData(String userId, String lang, String mandt) throws Exception;
	public Dataset makeAuthData(String sysid, String userId, String lang, String mandt) throws Exception;
	
	/**
	 * �޴� ���� ����� 
	 * @param lang
	 * @return
	 * @throws Exception
	 */
//	public Dataset makeMenuData(String userId, String lang, String mandt) throws Exception;
	public Dataset makeMenuData(String sysid, String userId, String lang, String mandt) throws Exception;

	/**
	 * ����� ���� ��ȸ 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public Dataset getUserInfoData(String mandt, String lang, String userId) throws Exception;

	/**
	 * ����� MACA���� ��ȸ 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public Dataset getUserMacaData(String mandt, String lang, String userId) throws Exception;

	/**
	 * ����� MACA���� ��ȸ 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int setUserMacaData(String userId, String mandt, String ipaddr, String macaddr, String confno, String confdt, String bigo, String flag) throws Exception;

	/**
	 * I/F�� ����� ���� ���
	 * @param sysid
	 * @param mandt
	 * @param lang
	 * @param userId
	 * @return I/F�� ����� ����
	 * @throws Exception ���� ��
	 */
	public Dataset getUserInfoDs(String sysid, String mandt, String lang, String userId) throws Exception;
}
