package com.helco.xf.wb01;

import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.ksign.KsignSPinCrypto;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessException;
import tit.biz.session.UserInfo;
import tit.service.business.config.ConfigUtility;
import tit.service.resource.TransactionManager;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.util.miplatform.ResourceUtility;
import tit.util.TitUtility;

public class LoginServiceImpl extends AbstractBusinessService implements LoginService {

	protected Log log = LogFactory.getLog(LoginServiceImpl.class);

	/**
	 * 
	 * @param str
	 * @return
	 */
	private static String trim(String str) 
	{
		if ( str == null ) 
		{
			return "";
		}
		
		return str.trim();
	}
	/**
	 * 
	 * 
	 */
	public UserInfo makeUserInfo(String userId, String pwd) throws Exception 
	{
		return makeUserInfo(ConfigUtility.getString("SYSID"), ConfigUtility.getString("MANDT"), userId, pwd, "", "ko");
	}
	
		public Dataset getUserInfoDs(String sysid, String mandt, String lang, String userId)
			throws Exception {

		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("egis:LOGIN_S1");
		sqlRequest.addParamObject("MANDT", mandt);
		sqlRequest.addParamObject("USER_ID", userId);
		sqlRequest.addParamObject("LANG", lang);
		sqlRequest.addParamObject("G_SYSID", sysid);

		String db_con = Utillity.getSapJndiBySysid(sysid);
		DatasetSqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		this.log.debug("로그인SQL===========>"+sqlRequest.getSql()+"<============");
		this.log.debug("로그인getParamSize===========>"+sqlRequest.getParamSize()+"<============");
		SqlResult result = db.query(sqlRequest);
		
		Dataset ds=(Dataset) result.getResultObject();
		ds.setId("ds_login");
		
		return ds;
		
	}
	public UserInfo makeUserInfo(String sysid, String mandt, String userId, String pwd, String clientIp, String lang) throws Exception 
	{
		HelcoUserInfo userInfo = new HelcoUserInfo();
		
		// 사용자 정보 조회 
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:LOGIN_S1");
		sqlRequest.addParamObject("MANDT", mandt);
		sqlRequest.addParamObject("USER_ID", userId);
		sqlRequest.addParamObject("LANG", lang);
		
		String db_con = Utillity.getSapJndiBySysid(sysid);

		SimpleSqlExecutor db = new SimpleSqlExecutor(getConnection(db_con));
		
		SqlResult result = db.query(sqlRequest);
		ResultSet rs = (ResultSet)result.getResultObject();

		/////db 암호화 적용 2016.09.26 hss///
		KsignSPinCrypto crypto = new KsignSPinCrypto(sysid);
		/////////////////////////////////////////////
		
		if ( rs.next() ) 
		{
			
			//System.out.println(crypto.encPwd(pwd) + ":"+rs.getString("USER_PWD"));
		
			if ( rs.getInt("USERFAIL") >= 5 ) 
			{
				// 3번 이상 틀린 경우 관리자에게 문의 
				throw new BusinessException("ZW00005");
			}
			else if( pwd.equals("__") ) // db 암호화 적용 2016.09.26 hss                    
			{
       
			}
			else if( !rs.getString("USER_PWD").equals(pwd) ) // db 암호화 적용 2016.09.26 hss
			//else if( !rs.getString("USER_PWD").equals(crypto.encPwd(pwd)))
			{
				// password가 맞지 않음. 
				// =비밀번호가 올바르지 않습니다.\n확인하여 주십시오.	
				SqlRequest sqlRequestUpdate	= SqlMapManagerUtility.makeSqlRequest("wb01:LOGIN_U2");
				sqlRequestUpdate.addParamObject("MANDT", mandt);
				sqlRequestUpdate.addParamObject("USER_ID", userId);

				int cnt = rs.getInt("USERFAIL") + 1;
				if ( db.execute(sqlRequestUpdate) != 1 ) 
				{
					TransactionManager.rollback();
					throw new BusinessException("ZW00002", cnt+"");
				} 
				else 
				{
					// 강제로 commit
					TransactionManager.commit();
				}
				throw new BusinessException("ZW00002", cnt+"");
			}
			
			// 사용자 정보
			userInfo.set(HelcoUserInfo.USER_ID, 		trim(rs.getString("USER_ID")));
			userInfo.set(HelcoUserInfo.USER_NAME, 		trim(rs.getString("USER_NAME")));
			userInfo.set(HelcoUserInfo.VEND_CODE, 		trim(rs.getString("VEND_CODE")));
			userInfo.set(HelcoUserInfo.VEND_CODE_R, 	trim(rs.getString("VEND_CODE_R")));
			userInfo.set(HelcoUserInfo.VEND_CODE_B, 	trim(rs.getString("VEND_CODE_B")));
			userInfo.set(HelcoUserInfo.VEND_CODE_M, 	trim(rs.getString("VEND_CODE_M")));
			userInfo.set(HelcoUserInfo.VEND_CODE_E, 	trim(rs.getString("VEND_CODE_E")));
			userInfo.set(HelcoUserInfo.ORG_VEND_CODE, 	trim(rs.getString("VEND_CODE")));
			userInfo.set(HelcoUserInfo.ORG_VEND_CODE_B, trim(rs.getString("VEND_CODE_B")));
			userInfo.set(HelcoUserInfo.ORG_VEND_CODE_E, trim(rs.getString("VEND_CODE_E")));
			userInfo.set(HelcoUserInfo.ORG_VEND_CODE_M, trim(rs.getString("VEND_CODE_M")));
			userInfo.set(HelcoUserInfo.ORG_VEND_CODE_R, trim(rs.getString("VEND_CODE_R")));
			userInfo.set(HelcoUserInfo.VEND_NAME, 		trim(rs.getString("VEND_NAME")));
			userInfo.set(HelcoUserInfo.EMAIL, 			trim(rs.getString("EMAIL")));
			userInfo.set(HelcoUserInfo.USER_GROUP, 		trim(rs.getString("USER_GROUP")));
			userInfo.set(HelcoUserInfo.USER_GROUP_R, 	trim(rs.getString("USER_GROUP_R")));
			userInfo.set(HelcoUserInfo.USER_GROUP_E, 	trim(rs.getString("USER_GROUP_E")));
			userInfo.set(HelcoUserInfo.USER_GROUP_M, 	trim(rs.getString("USER_GROUP_M")));
			userInfo.set(HelcoUserInfo.USER_GROUP_B, 	trim(rs.getString("USER_GROUP_B")));
			userInfo.set(HelcoUserInfo.AREA_CODE, 		trim(rs.getString("AREA_CODE")));
			userInfo.set(HelcoUserInfo.AREA_NAME, 		trim(rs.getString("AREA_NAME")));
			userInfo.set(HelcoUserInfo.DPT1, 		    trim(rs.getString("DPT1")));
			userInfo.set(HelcoUserInfo.LANG, 			lang);
			//userInfo.set("USER_PWD", 					trim(rs.getString("USER_PWD")));	// 사용자 비밀번호
			userInfo.set("SYSID", 						sysid);
			userInfo.set("MANDT", 						mandt);
			userInfo.set("CLIENT_IP", 					clientIp);
			userInfo.set("LGORT", 					    trim(rs.getString("LGORT")));		// 저장위치

			userInfo.set(HelcoUserInfo.AREA_CODE_B, 	trim(rs.getString("AREA_CODE_B")));
			userInfo.set(HelcoUserInfo.AREA_CODE_E, 	trim(rs.getString("AREA_CODE_E")));
			userInfo.set(HelcoUserInfo.AREA_CODE_R, 	trim(rs.getString("AREA_CODE_R")));
			
			
			//2013.06.03 세션에 전화번호,HP 추가
			userInfo.set("USERTELE", 					trim(rs.getString("USERTELE")));	// 전화번호
			userInfo.set("USERMBPN", 					trim(rs.getString("USERMBPN")));	// H.P			
					
		//	userInfo.set(HelcoUserInfo.LANG, trim(rs.getString("LANG")).toLowerCase());
		//	userInfo.set("MANDT", ConfigUtility.getString("MANDT"));	// 클라이언트 번호  
			
			// 지역코드 존재 시 LGORT 를 업체 코드로 설정 
			if ( !trim(rs.getString("AREA_CODE_R")).equals("") && !trim(rs.getString("USER_GROUP_R")).equals("")) 
			{
				userInfo.set(HelcoUserInfo.VEND_CODE, trim(rs.getString("LGORT")));
				userInfo.set(HelcoUserInfo.VEND_CODE_R, trim(rs.getString("LGORT")));
//				userInfo.set(HelcoUserInfo.ORG_VEND_CODE, trim(rs.getString("LGORT")));
//				userInfo.set(HelcoUserInfo.AREA_CODE_R, trim(rs.getString("AREA_CODE_R")));
			}
//			if ( !trim(rs.getString("AREA_CODE")).equals("") && !trim(rs.getString("USER_GROUP_E")).equals("")) 
//			{
//				userInfo.set(HelcoUserInfo.AREA_CODE_E, trim(rs.getString("AREA_CODE")));
//			}
//			if ( !trim(rs.getString("AREA_CODE")).equals("") && !trim(rs.getString("USER_GROUP_B")).equals("")) 
//			{
//				userInfo.set(HelcoUserInfo.VEND_CODE, trim(rs.getString("LGORT")));
//				userInfo.set(HelcoUserInfo.ORG_VEND_CODE, trim(rs.getString("LGORT")));
//				userInfo.set(HelcoUserInfo.AREA_CODE_R, trim(rs.getString("AREA_CODE_R")));
//			}

			// 비밀번호 만료 일자 
			userInfo.set(HelcoUserInfo.PWD_EXPR_DATE, trim(rs.getString("PWD_EXPR_DATE")));
			userInfo.set("LOGIN_TIME", TitUtility.getDateTimeString("yyyy.MM.dd HH:mm:ss"));			
			userInfo.set("SYSTEM", getSystem(sysid));

			// 2012.09.03 : 추가 
			userInfo.set(HelcoUserInfo.VKBUR	, trim(rs.getString("VKBUR"		)));  // SAP 부서코드
			userInfo.set(HelcoUserInfo.VKBUR_NM	, trim(rs.getString("VKBUR_NM"	)));  // SAP 부서명
			userInfo.set(HelcoUserInfo.VKGRP	, trim(rs.getString("VKGRP"		)));  // SAP 팀코드
			userInfo.set(HelcoUserInfo.VKGRP_NM	, trim(rs.getString("VKGRP_NM"	)));  // SAP 팀명

			userInfo.set(HelcoUserInfo.WGBN		, trim(rs.getString("WGBN"		)));  // 업체구분
			userInfo.set(HelcoUserInfo.BIZ_PM	, trim(rs.getString("BIZ_PM"	)));  // PM(영업)
			userInfo.set(HelcoUserInfo.BIZ_PMNM	, trim(rs.getString("BIZ_PMNM"	)));  // PM명(영업)
			userInfo.set(HelcoUserInfo.BSU_PM	, trim(rs.getString("BSU_PM"	)));  // PM(보수)
			userInfo.set(HelcoUserInfo.BSU_PMNM	, trim(rs.getString("BSU_PMNM"	)));  // PM명(보수)
			
			userInfo.set(HelcoUserInfo.CURR_DATE, (DateTime.getDateString("yyyyMMdd")));  // 현재일 로그인시 지정
			
			
		} 
		else
		{
			// user id가 없음.
			// 해당 사용자 정보를 찾을 수 없습니다.\n아이디를 확인해 주십시오.
			throw new BusinessException("ZW00002");
		}

		// 처리 성공 - Update 처리  
		SqlRequest sqlRequestUpdate	= SqlMapManagerUtility.makeSqlRequest("wb01:LOGIN_U1");
		sqlRequestUpdate.addParamObject("MANDT", mandt);
		sqlRequestUpdate.addParamObject("USER_ID", userId);
		sqlRequestUpdate.addParamObject("CLIENT_IP", clientIp);

		if ( db.execute(sqlRequestUpdate) != 1 ) 
		{
			TransactionManager.rollback();
			throw new BusinessException("CE00001");
		}
		
		TransactionManager.commit();

		return userInfo;
	}

	/**
	 * 프로그램 사용권한
	 */
	public Dataset makeAuthData(String sysid, String userId, String lang, String mandt) throws Exception 
	{
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:MENU_S2");
		sqlRequest.addParamObject("USER_ID", userId);
		sqlRequest.addParamObject("LANG", lang.toLowerCase());
		sqlRequest.addParamObject("MANDT", mandt);
		
		DatasetSqlExecutor db = null;
		String db_con = Utillity.getSapJndiBySysid(sysid);
		db = new DatasetSqlExecutor(getConnection(db_con));
		
		return (Dataset)db.query(sqlRequest).getResultObject();
	}

	/**
	 * 메시지 정보 생성하기 
	 */
	public Dataset makeMessage(String lang) throws Exception 
	{
		Dataset ds = ResourceUtility.readMsg("com/helco/xf/wb01/res/msg", lang);
		ds.setId("gds_message");
		return ds;
	}

	/**
	 * 메뉴제어
	 */
	public Dataset makeMenuData(String sysid, String userId, String lang, String mandt) throws Exception 
	{
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:MENU_S1");
		sqlRequest.addParamObject("USER_ID", userId);
		sqlRequest.addParamObject("LANG", lang.toLowerCase());
		sqlRequest.addParamObject("MANDT", mandt);
		
		DatasetSqlExecutor db = null;
		String db_con = Utillity.getSapJndiBySysid(sysid);
		db = new DatasetSqlExecutor(getConnection(db_con));

		return (Dataset)db.query(sqlRequest).getResultObject();
	}

	/**
	 * 사용자 정보 
	 */
	public Dataset getUserInfoData(String mandt, String lang, String userId) throws Exception 
	{
		// 사용자 정보 조회 
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:LOGIN_S3");
		sqlRequest.addParamObject("MANDT", mandt);
		sqlRequest.addParamObject("USER_ID", userId);
		sqlRequest.addParamObject("LANG", lang);

		String db_con = Utillity.getSapJndi(mandt);
		DatasetSqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		
		return (Dataset)db.query(sqlRequest).getResultObject();
	}

	/**
	 * 사용자 MACA 정보 
	 */
	public Dataset getUserMacaData(String mandt, String lang, String userId) throws Exception 
	{
		// 사용자 정보 조회 
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:USERMACA_S1");
		sqlRequest.addParamObject("MANDT", mandt);
		sqlRequest.addParamObject("USER_ID", userId);
		sqlRequest.addParamObject("LANG", lang);
		
		String db_con = Utillity.getSapJndi(mandt);
		DatasetSqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		
		return (Dataset)db.query(sqlRequest).getResultObject();
	}

	public int setUserMacaData(String userId, String mandt, String ipaddr, String macaddr, String confno, String confdt, String bigo, String flag) throws Exception 
	{
		int iResult = 0;

		if("I".equals(flag))	{
			// 사용자 MACA정보 등록 
			SqlRequest sqlRequestUpdate = SqlMapManagerUtility.makeSqlRequest("wb01:USERMACA_I1");
			sqlRequestUpdate.addParamObject("MANDT",    mandt);
			sqlRequestUpdate.addParamObject("USERNUMB", userId);
			sqlRequestUpdate.addParamObject("IPADDR",   ipaddr);
			sqlRequestUpdate.addParamObject("MACADDR",  macaddr);
			sqlRequestUpdate.addParamObject("CONFNO",   confno);
			sqlRequestUpdate.addParamObject("CONFDT",   confdt);
			sqlRequestUpdate.addParamObject("BIGO",     bigo);
	
			String db_con = Utillity.getSapJndi(mandt);
	
			SimpleSqlExecutor db = new SimpleSqlExecutor(getConnection(db_con));
	
			if ( db.execute(sqlRequestUpdate) != 1 ) 
			{
				TransactionManager.rollback();
				iResult = -999;
			} 
			else 
			{
				// 강제로 commit
				TransactionManager.commit();
				iResult = 0;
			}
		}else if ("U".equals(flag))	{
			// 사용자 MACA정보 update
			SqlRequest sqlRequestUpdate = SqlMapManagerUtility.makeSqlRequest("wb01:USERMACA_U1");
			sqlRequestUpdate.addParamObject("MANDT",    mandt);
			sqlRequestUpdate.addParamObject("USERNUMB", userId);
			sqlRequestUpdate.addParamObject("IPADDR",   ipaddr);
			sqlRequestUpdate.addParamObject("MACADDR",  macaddr);
			sqlRequestUpdate.addParamObject("CONFNO",   confno);
			sqlRequestUpdate.addParamObject("CONFDT",   confdt);
			sqlRequestUpdate.addParamObject("BIGO",     bigo);
	
			String db_con = Utillity.getSapJndi(mandt);
	
			SimpleSqlExecutor db = new SimpleSqlExecutor(getConnection(db_con));
	
			if ( db.execute(sqlRequestUpdate) != 1 ) 
			{
				TransactionManager.rollback();
				iResult = -999;
			} 
			else 
			{
				// 강제로 commit
				TransactionManager.commit();
				iResult = 0;
			}
		}

		return iResult;
	}

	/**
	 * 시스템 
	 */
	private static String getSystem(String sysid) 
	{
		String system = "D";	// 개발 
		// 시스템 판단 
		if ( ConfigUtility.getString("SYSID_HEP").equals(sysid)) 
		{
			system = "P";
		} 
		else if ( ConfigUtility.getString("SYSID_HEQ").equals(sysid)) 
		{
			system = "Q";	// 품질
		}
		return system;
	}	
}
