package com.ksign;

import com.ksign.spin.apilib.v3.SpinApiLib;
import com.ksign.spin.apilib.v3.SpinApiData;

import java.net.InetAddress;
import java.util.Properties;

public class KsignSPinCrypto {
	
	private static Properties props = null;
	
	private static final String strSEED_Jumin     = "P001"; // 주민번호 암호화(8번째 이후 부분 암호화)
	private static final String strSEED_Acc       = "P002"; // 계좌번호 암호화
	private static final String strSEED_Card      = "P003"; // 카드번호 암호화
	private static final String strSHA256_Pwd     = "P004"; // 비밀번호
	
	private static final int nSEED_Jumin     = 0;
	private static final int nSEED_Acc       = 1;
	private static final int nSEED_Card      = 2;
	private static final int nSHA256_Pwd     = 3;
	
	// SecurePIN 서벼 연결 정보 리스트   IP:PORT;IP:PORT
	private static String strConnInfValue  = "";
	// 이중화 일 경우 연결 방식 
	// 1 : Round-Robin
	// 2 : Active Standby
	private static int    nConnInfoSchedule = 1;
	// 암복호화를 요청하는 서버의 이름
	private static String strSenderName     = "";
	// Sender IP
	private static String strSenderIp       = null;

	
	private SpinApiLib crypto = null;

	public KsignSPinCrypto() {
		if(crypto == null) {
			if(props == null) {
				/*
				 #KSign Setting
				 #Primary KeyServer IP
				 KS_PIP=
				 #Primary KeyServer Port
				 KS_PPORT=
				 #Secondary KeyServer IP
				 KS_SIP=
				 #Secondary KeyServer Port
				 KS_SPORT=
				 #Connection Info Schedule
				 KS_CIS=
				 #Sender Name
				 KS_SNAME=
				 */
				try {
					// 프로퍼트 파일 로드
					props = new java.util.Properties();
					props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("KsignAPI_HDEL.properties"));
					// Sender IP 
					InetAddress local = InetAddress.getLocalHost();
					strSenderIp = local.getHostAddress();
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				String strTmp = "";
				strTmp = props.getProperty("KS_PIP");
				strTmp = strTmp + ":" + props.getProperty("KS_PPORT") + ";";
				
				strConnInfValue = strTmp;
				strTmp = "";
				strTmp = props.getProperty("KS_SIP");
				if(strTmp != null && !strTmp.equals("")) strTmp = strTmp + ":" + props.getProperty("KS_SPORT") + ";";
				
				strConnInfValue += strTmp;
				
				strTmp = props.getProperty("KS_CIS");
				
				if(strTmp != null && !strTmp.equals("")) nConnInfoSchedule = Integer.parseInt(strTmp);
				
				strSenderName = props.getProperty("KS_SNAME");
				
				
//				System.out.println("=============== ksign Debug ===============");
//				System.out.println("ConnInfValue : " + strConnInfValue);
//				System.out.println("ConnInfoSchedule : " + nConnInfoSchedule);
//				System.out.println("SenderName : " + strSenderName);
//				System.out.println("SenderIP : " + strSenderIp);
//				System.out.println("===========================================");
			}
			try {
				crypto = new SpinApiLib();
				
				crypto.initializeRemote(strConnInfValue, nConnInfoSchedule, strSenderName);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	public KsignSPinCrypto(String sysid) {
		if(crypto == null) {
			if(props == null) {

				try {
					// 프로퍼트 파일 로드
					props = new java.util.Properties();
					props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("KsignAPI_HDEL.properties"));
					// Sender IP 
					InetAddress local = InetAddress.getLocalHost();
					strSenderIp = local.getHostAddress();
				} catch(Exception e) {
					e.printStackTrace();
				}
						
				String strTmp = "";
				strTmp = props.getProperty("KS_PIP");
				strTmp = strTmp + ":" + props.getProperty("KS_PPORT"+"_"+sysid) + ";";
				
				strConnInfValue = strTmp;
				strTmp = "";
				strTmp = props.getProperty("KS_SIP");
				if(strTmp != null && !strTmp.equals("")) strTmp = strTmp + ":" + props.getProperty("KS_SPORT"+"_"+sysid) + ";";
				
				strConnInfValue += strTmp;
				
				strTmp = props.getProperty("KS_CIS");
				
				if(strTmp != null && !strTmp.equals("")) nConnInfoSchedule = Integer.parseInt(strTmp);
				
				strSenderName = props.getProperty("KS_SNAME");
				
			
			}
			try {
				crypto = new SpinApiLib();
				
				crypto.initializeRemote(strConnInfValue, nConnInfoSchedule, strSenderName);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 공통 암호화 함수
	private String encSEED(String plainText, int nType) {
		SpinApiData[] data = new SpinApiData[1];
		data[0] = new SpinApiData(plainText);
		String strRet = plainText;
		
		if(crypto != null) {
			try {
				switch(nType) {
				case nSEED_Jumin   : data[0].setPolicyId(strSEED_Jumin); crypto.encryptEx(data, strSenderName, strSenderIp); strRet = data[0].getOutput(); break;
				case nSEED_Acc     : data[0].setPolicyId(strSEED_Acc  ); crypto.encryptEx(data, strSenderName, strSenderIp); strRet = data[0].getOutput(); break;
				case nSEED_Card    : data[0].setPolicyId(strSEED_Card ); crypto.encryptEx(data, strSenderName, strSenderIp); strRet = data[0].getOutput(); break;
				case nSHA256_Pwd   : data[0].setPolicyId(strSHA256_Pwd); crypto.encryptEx(data, strSenderName, strSenderIp); strRet = data[0].getOutput(); break;
				default            : break;
				}
			} catch(Exception e) {
				switch(nType) {
				case nSEED_Jumin   : System.out.println(" Ksign Encrypt Message : [Jumin    ]"); break;
				case nSEED_Acc     : System.out.println(" Ksign Encrypt Message : [Acc      ]"); break;
				case nSEED_Card    : System.out.println(" Ksign Encrypt Message : [Card     ]"); break;
				case nSHA256_Pwd   : System.out.println(" Ksign Encrypt Message : [Pwd      ]"); break;
				default            : System.out.println(" Ksign Encrypt Message : [default  ]"); break;
				}
				strRet = plainText;
				e.printStackTrace();
			}
		}
		return strRet;
	}
	
	// 공통 복호화 함수
	private String decSEED(String cipherText, int nType) {
		SpinApiData[] data = new SpinApiData[1];
		data[0] = new SpinApiData(cipherText);
		String strRet = cipherText;
		if(crypto != null) {
			try {
				switch(nType) {
				case nSEED_Jumin   : data[0].setPolicyId(strSEED_Jumin); crypto.decryptEx(data, strSenderName, strSenderIp); strRet = data[0].getOutput(); break;
				case nSEED_Acc     : data[0].setPolicyId(strSEED_Acc  ); crypto.decryptEx(data, strSenderName, strSenderIp); strRet = data[0].getOutput(); break;
				case nSEED_Card    : data[0].setPolicyId(strSEED_Card ); crypto.decryptEx(data, strSenderName, strSenderIp); strRet = data[0].getOutput(); break;
				default            : break;
				}
			} catch(Exception e) {
				switch(nType) {
				case nSEED_Jumin     : System.out.println(" Ksign Decrypt Message : [Jumin    ]"); break;
				case nSEED_Acc       : System.out.println(" Ksign Decrypt Message : [Acc      ]"); break;
				case nSEED_Card      : System.out.println(" Ksign Decrypt Message : [Card     ]"); break;
				default              : System.out.println(" Ksign Decrypt Message : [default  ]"); break;
				}
				strRet = cipherText;
				e.printStackTrace();
			}
		}
//		System.out.println(strRet);
		return strRet;
	}
	
	// Column별 암호화 함수
	public String encJumin    (String plainText) { return encSEED(plainText, nSEED_Jumin    ); }
	public String encAcc      (String plainText) { return encSEED(plainText, nSEED_Acc      ); }
	public String encCard     (String plainText) { return encSEED(plainText, nSEED_Card     ); }
	public String encPwd      (String plainText) { return encSEED(plainText, nSHA256_Pwd    ); }

	// Column별 복호화 함수
	public String decJumin    (String cipherText) { return decSEED(cipherText, nSEED_Jumin    ); }
	public String decAcc      (String cipherText) { return decSEED(cipherText, nSEED_Acc      ); }
	public String decCard     (String cipherText) { return decSEED(cipherText, nSEED_Card     ); }
	
//	// 문자열 배열 공통암호화 함수
	public String[] encStringArray(String[] strArray, int nType) {
		if(crypto != null) {
			for(int i = 0; i < strArray.length; i++) {
				strArray[i] = encSEED(strArray[i], nType);
			}
		}
		return strArray;
	}

	// 문자열 배열 공통복호화 함수
	public String[] decStringArray(String[] strArray, int nType) {
		if(crypto != null) {
			for(int i = 0; i < strArray.length; i++) {
				strArray[i] = decSEED(strArray[i], nType);
			}
		}
		return strArray;
	}
	
	// 배열 Column별 복호화 함수
	public String[] encJumin    (String[] plainText) { return encStringArray(plainText, nSEED_Jumin    ); }
	public String[] encAcc      (String[] plainText) { return encStringArray(plainText, nSEED_Acc      ); }
	public String[] encCard     (String[] plainText) { return encStringArray(plainText, nSEED_Card     ); }
	public String[] encPwd      (String[] plainText) { return encStringArray(plainText, nSHA256_Pwd    ); }

	// 배열 Column별 복호화 함수
	public String[] decJumin    (String[] cipherText) { return decStringArray(cipherText, nSEED_Jumin  ); }
	public String[] decAcc      (String[] cipherText) { return decStringArray(cipherText, nSEED_Acc    ); }
	public String[] decCard     (String[] cipherText) { return decStringArray(cipherText, nSEED_Card   ); }
	
	public static void main(String[] args) {

		KsignSPinCrypto crypto = new KsignSPinCrypto();
		String strJumin = "123456-1234567";
		String strAcc   = "123412";
		String strCard  = "123412";
		String strPwd   = "1234123412341234";
		
		String strEncJumin = "";
		String strEncAcc   = "";
		String strEncCard  = "";
		String strEncPwd   = "";

		String strDecJumin = "";
		String strDecAcc   = "";
		String strDecCard  = "";

		strEncJumin = crypto.encJumin(strJumin);
		strEncAcc   = crypto.encAcc  (strAcc  );
		strEncCard  = crypto.encCard (strCard );
		strEncPwd   = crypto.encPwd  (strPwd  );
		
		strDecJumin = crypto.decJumin(strEncJumin);
		strDecAcc   = crypto.decAcc  (strEncAcc);
		strDecCard  = crypto.decCard (strEncCard);
		
		System.out.println("orgJuminData : " + strJumin + " encJuminData : " + strEncJumin + " decJuminData : " + strDecJumin);
		System.out.println("orgAccData : " + strAcc + " encAccData : " + strEncAcc + " decAccData : " + strDecAcc);
		System.out.println("orgCardData : " + strCard + " encCardData : " + strEncCard + " decCardData : " + strDecCard);
		System.out.println("orgPwdData : " + strPwd + " encPwdData : " + strEncPwd);


	}
}