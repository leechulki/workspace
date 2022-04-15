package com.helco.xf.wb01;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tit.biz.session.UserInfo;

/**
 * G_EMAIL
G_EXPR_DATE
G_LANG
G_ORG_VEND_CODE
G_SERVER_URL
G_USER_GROUP
G_USER_ID
G_USER_NAME
G_VEND_CODE
G_VEND_NAME
 */
public class HelcoUserInfo implements UserInfo, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6163895691035677821L;

	private Map mData = new HashMap();
	
	public static final String VEND_NAME = "VEND_NAME";
	public static final String LANG = "LANG";
	public static final String ORG_VEND_CODE = "ORG_VEND_CODE";
	public static final String ORG_VEND_CODE_B = "ORG_VEND_CODE_B";
	public static final String ORG_VEND_CODE_E = "ORG_VEND_CODE_E";
	public static final String ORG_VEND_CODE_M = "ORG_VEND_CODE_M";
	public static final String ORG_VEND_CODE_R = "ORG_VEND_CODE_R";
	public static final String USER_GROUP = "USER_GROUP";
	public static final String USER_GROUP_R = "USER_GROUP_R";		// ���� 
	public static final String USER_GROUP_E = "USER_GROUP_E";		// ��ġ 
	public static final String USER_GROUP_M = "USER_GROUP_M";		// ���� 
	public static final String USER_GROUP_B = "USER_GROUP_B";		// ���� 
	public final static String USER_NAME = "USER_NAME";
	public final static String USER_ID = "USER_ID";
	public final static String VEND_CODE = "VEND_CODE";
	public final static String VEND_CODE_R = "VEND_CODE_R";
	public final static String VEND_CODE_B = "VEND_CODE_B";
	public final static String VEND_CODE_M = "VEND_CODE_M";
	public final static String VEND_CODE_E = "VEND_CODE_E";
	public final static String EMAIL = "EMAIL";
	public final static String AREA_CODE = "AREA_CODE";
	public final static String AREA_NAME = "AREA_NAME";
	public final static String PWD_EXPR_DATE = "PWD_EXPR_DATE";		// ��й�ȣ ���� ���� 
	public final static String AREA_CODE_E = "AREA_CODE_E";       	// ��ġ�����ڵ�
	public final static String AREA_CODE_R = "AREA_CODE_R";       	// ���������ڵ�
	public final static String AREA_CODE_B = "AREA_CODE_B";       	// ���������ڵ�
	public final static String DPT1 = "DPT1";       				// ���뿤�������� �μ��ڵ�
	
	
	// 2012.09.03 : �߰�
	public final static String VKBUR 	= "VKBUR";       			// SAP �μ��ڵ�
	public final static String VKBUR_NM = "VKBUR_NM";    			// SAP �μ���
	public final static String VKGRP 	= "VKGRP";       			// SAP ���ڵ�
	public final static String VKGRP_NM = "VKGRP_NM";    			// SAP ����   
	
	public final static String WGBN = "WGBN";						// ��ü����
	public final static String BIZ_PM = "BIZ_PM";					// PM(����)
	public final static String BIZ_PMNM = "BIZ_PMNM";				// PM��(����)
	public final static String BSU_PM = "BSU_PM";					// PM(����)
	public final static String BSU_PMNM = "BSU_PMNM";				// PM��(����)
	
	public final static String USERTELE = "USERTELE";				// 2013.06.03: ��ȭ��ȣ,HP �߰�
	public final static String USERMBPN = "USERMBPN";				
	public final static String CURR_DATE = "CURR_DATE";             // �α��� ���� CHECK ��			
	




	public String get(String key) {
		return (String)mData.get(key);
	}

	public Map getAllInfo() {
		return this.mData;
	}

	public String getOrgVendCode() {
		return (String)mData.get(ORG_VEND_CODE);
	}
	
	public String getOrgVendCode_b() {
		return (String)mData.get(ORG_VEND_CODE_B);
	}
	
	public String getOrgVendCode_e() {
		return (String)mData.get(ORG_VEND_CODE_E);
	}
	
	public String getOrgVendCode_m() {
		return (String)mData.get(ORG_VEND_CODE_M);
	}
	
	public String getOrgVendCode_r() {
		return (String)mData.get(ORG_VEND_CODE_R);
	}
	
	public String getVendCode() {
		return (String)mData.get(VEND_CODE);
	}

	public String getVendCode_r() {
		return (String)mData.get(VEND_CODE_R);
	}

	public String getVendCode_b() {
		return (String)mData.get(VEND_CODE_B);
	}

	public String getVendCode_m() {
		return (String)mData.get(VEND_CODE_M);
	}

	public String getVendCode_e() {
		return (String)mData.get(VEND_CODE_E);
	}

	public String getDeptCode() {
		return getVendCode();
	}

	public String getVendName() {
		return (String)mData.get(VEND_NAME);
	}

	public String getDpt1() {
		return (String)mData.get(DPT1);
	}
	
	public String getDeptName() {
		return getVendName();
	}

	public String getUserName() {
		return (String)mData.get(USER_NAME);
	}
	
	public String getEngUserName() {
		return getUserName();
	}

	public String getUserId() {
		return (String)mData.get(USER_ID);
	}
	
	public String getLang() {
		return (String)mData.get(USER_GROUP);
	}
	
	public String getUserGroup() {
		return (String)mData.get(USER_GROUP);
	}
	public String getEmail() {
		return (String)mData.get(EMAIL);
	}

	// 2012.09.03 : �߰� : SAP�μ��ڵ�
	public String getVkbur() {
		return (String)mData.get(VKBUR);
	}
	// 2012.09.03 : �߰� : SAP�μ���
	public String getVkburNm() {
		return (String)mData.get(VKBUR_NM);
	}
	// 2012.09.03 : �߰� : SAP���ڵ�
	public String getVkgrp() {
		return (String)mData.get(VKGRP);
	}
	// 2012.09.03 : �߰� : SAP����
	public String getVkgrpNm() {
		return (String)mData.get(VKGRP_NM);
	}
	// 2012.09.03 : �߰� : ��ü����
	public String getVendWgbn() {
		return (String)mData.get(WGBN);
	}
	// 2012.09.03 : �߰� : PM(����)
	public String getBizPm() {
		return (String)mData.get(BIZ_PM);
	}
	// 2012.09.03 : �߰� : PM��(����)
	public String getBizPmNm() {
		return (String)mData.get(BIZ_PMNM);
	}
	// 2012.09.03 : �߰� : PM(����)
	public String getBsuPm() {
		return (String)mData.get(BSU_PM);
	}
	// 2012.09.03 : �߰� : PM��(����)
	public String getBsuPmNm() {
		return (String)mData.get(BSU_PMNM);
	}

	//2013.06.03 : �߰� : ��ȭ��ȣ
	public String getUsertele() {
		return (String)mData.get(USERTELE);		
	}
	//2013.06.03 : �߰� : H.P
	public String getUsermbpn() {
		return (String)mData.get(USERMBPN);			
	}
	public static String getCurrDate() {
		return CURR_DATE;
	}
	
	public void setUserGroup(String newUserGroup) {
		mData.put(USER_GROUP, newUserGroup);
	}
	public void setVendCode(String newArg) {
		mData.put(VEND_CODE, newArg);
	}
	public void setVendCode_r(String newArg) {
		mData.put(VEND_CODE_R, newArg);
	}
	public void setVendCode_b(String newArg) {
		mData.put(VEND_CODE_B, newArg);
	}
	public void setVendCode_m(String newArg) {
		mData.put(VEND_CODE_M, newArg);
	}
	public void setVendCode_e(String newArg) {
		mData.put(VEND_CODE_E, newArg);
	}
	public void setOrgVendCode(String newArg) {
		mData.put(ORG_VEND_CODE, newArg);
	}
	public void setOrgVendCode_b(String newArg) {
		mData.put(ORG_VEND_CODE_B, newArg);
	}
	public void setOrgVendCode_e(String newArg) {
		mData.put(ORG_VEND_CODE_E, newArg);
	}
	public void setOrgVendCode_m(String newArg) {
		mData.put(ORG_VEND_CODE_M, newArg);
	}
	public void setOrgVendCode_r(String newArg) {
		mData.put(ORG_VEND_CODE_R, newArg);
	}
	public void setVendName(String newArg) {
		mData.put(VEND_NAME, newArg);
	}

	public void setDpt1(String newArg) {
		mData.put(DPT1, newArg);
	}
	public void setUserId(String userId) {
		mData.put(USER_ID, userId);
	}

	public void setUserName(String userName) {
		mData.put(USER_NAME, userName);
	}
	public void set(String key, String value) {
		mData.put(key, value);
	}
	public void setEmail(String eMail) {
		mData.put(EMAIL, eMail);
	}

	public static String getAREA_CODE_E() {
		return AREA_CODE_E;
	}

	public static String getAREA_CODE_B() {
		return AREA_CODE_B;
	}



	// 2012.09.03 : �߰� : SAP�μ��ڵ� 
	public void setVkbur(String value) {
		mData.put(VKBUR, value);
	} 
	// 2012.09.03 : �߰� : SAP�μ���
	public void setVkburNm(String value) {
		mData.put(VKBUR_NM, value);
	}  
	// 2012.09.03 : �߰� : SAP���ڵ� 
	public void setVkgrp(String value) {
		mData.put(VKGRP, value);
	} 
	// 2012.09.03 : �߰� : SAP����
	public void setVkgrpNm(String value) {
		mData.put(VKGRP_NM, value);
	} 
	// 2012.09.03 : �߰� : ��ü����
	public void setVendWgbn(String value) {
		mData.put(WGBN, value);
	}
	// 2012.09.03 : �߰� : PM(����)
	public void setBizPm(String value) {
		mData.put(BIZ_PM, value);
	}
	// 2012.09.03 : �߰� : PM��(����)
	public void setBizPmNm(String value) {
		mData.put(BIZ_PMNM, value);
	}
	// 2012.09.03 : �߰� : PM(����)
	public void setBsuPm(String value) {
		mData.put(BSU_PM, value);
	}
	// 2012.09.03 : �߰� : PM��(����)
	public void setBsuPmNm(String value) {
		mData.put(BSU_PMNM, value);
	}
	//2013.06.03 : �߰� : ��ȭ��ȣ
	public void setUsertele(String value) {
	mData.put(USERTELE, value);
	}	
	//2013.06.03 : �߰� : H.P	
	public void setUsermbpn(String value) {
	mData.put(USERMBPN, value);
	}			
	//2013.08.30 : �߰� : ������	
	public void setCurrDate(String value) {
	mData.put(CURR_DATE, value);
	}
}
