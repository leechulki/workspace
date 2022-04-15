package com.helco.xf.wb01;

import com.tobesoft.platform.data.Dataset;
import tit.biz.session.UserInfo;

/**
 * 로그인 기능 제공 
 */
public interface LoginService {

	/**
	 * 사용자 아이디, 비밀번호를 입력 받아 로그인 처리 
	 * @param userId
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public UserInfo makeUserInfo(String userId, String pwd) throws Exception;
	
	/**
	 * 사용자 아이디, 비밀번호를 입력 받아 로그인 처리 
	 * @param userId
	 * @param pwd
	 * @param clientIp
	 * @return
	 * @throws Exception
	 */
//	public UserInfo makeUserInfo(String mandt, String userId, String pwd, String clientIp, String lang) throws Exception;
	public UserInfo makeUserInfo(String sysid, String mandt, String userId, String pwd, String clientIp, String lang) throws Exception;
	/**
	 * 사용자 language에 맞는 메시지 읽어 들이기 
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	public Dataset makeMessage(String lang) throws Exception;
	
	/**
	 * 사용자에 해당하는 권한 정보 읽어 들이기 
	 * @param userId
	 * @param lang	언어 
	 * @return
	 * @throws Exception
	 */
//	public Dataset makeAuthData(String userId, String lang, String mandt) throws Exception;
	public Dataset makeAuthData(String sysid, String userId, String lang, String mandt) throws Exception;
	
	/**
	 * 메뉴 정보 만들기 
	 * @param lang
	 * @return
	 * @throws Exception
	 */
//	public Dataset makeMenuData(String userId, String lang, String mandt) throws Exception;
	public Dataset makeMenuData(String sysid, String userId, String lang, String mandt) throws Exception;

	/**
	 * 사용자 정보 조회 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public Dataset getUserInfoData(String mandt, String lang, String userId) throws Exception;

	/**
	 * 사용자 MACA정보 조회 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public Dataset getUserMacaData(String mandt, String lang, String userId) throws Exception;

	/**
	 * 사용자 MACA정보 조회 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int setUserMacaData(String userId, String mandt, String ipaddr, String macaddr, String confno, String confdt, String bigo, String flag) throws Exception;

	/**
	 * I/F용 사용자 정보 취득
	 * @param sysid
	 * @param mandt
	 * @param lang
	 * @param userId
	 * @return I/F용 사용자 정보
	 * @throws Exception 예외 시
	 */
	public Dataset getUserInfoDs(String sysid, String mandt, String lang, String userId) throws Exception;
}
