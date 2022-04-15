/**
 * 파 일 명 : TBEBXLF1Vo.java
 * 설    명 : 매출계획
 * 작 성 자 : shhwang
 * 작 성 일 : 2006-04-20 오후 6:43:09
 * 변경내역 :
   0424 : CGB 빠짐, ISR,FDT,TDT,JDQ추가  
 *
 */ 
package com.helco.xf.cs12.evladm.vo;

public class TBEBXLF1Vo extends FrwValueObject
{
	private  String cebxlupn;
	private  String cebxlcst;
	private  String cebxlpjt;
	private  String cebxlhgb;
	private  String cebxlhno;
	private  String cebxlseq;
	private  String cebxlgnd;
	private  String cebxlmym;
	private  String cebxlmno;
	private  String cebxltyp;
	private  String cebxlara;
	private  String cebxlbsu;
	private  String cebxlgbu;
	private  String cebxljuj;
	private  String cebxlgbn;
	private  String cebxlagb;
	private  String cebxlabg;
	private  String cebxlgjp;
	private  String cebxlydt;
	private  String cebxlbgb;
	private  String cebxlbhb;
	private  String cebxlsju;
	private  String cebxlzer;	
	private  String cebxlham;	
	private  String cebxlfdt;//0424추가 
	private  String cebxltdt;//0424추가 
	private  String cebxljdq;//0424추가 	
    private  String cebxlisr;//0425추가(mhcho)
	private  String cebxlamd;
	private  String cebxlvad;
	private  String cebxltod;
	private  String cebxlamt;
	private  String cebxlvat;
	private  String cebxltot;	
	private  String cebxlmyb;
	private  String cebxlmsa;
	private  String cebxlmdt;
	private  String cebxlbgd;
	private  String cebxljgb;
	private  String cebxlup1;
	private  String cebxlcs1;
	private  String cebxlyym;
	private  String cebxltis;
	private  String cebxltno;
	private  String cebxlndt;
	private  String cebxlntm;
	private  String cebxlcsj;
	private  String cebxlyy1;
	private  String cebxliis;
	private  String mandt;
	private  String   userid;
	private  String   gno;
	private  String cebxlhmd;
	private  String cebxldmd;
	private  String cebxlhmt;
	private  String cebxldmt;
	
	// 공기청정기 VO
	private  String cebxlacmd;
	private  String cebxlacmt;
	
	//승인취소용 변수 
	private  String cebxlmymfrom;
	private  String cebxlmymto;
	
	//분담이행방식 변수 추가
	private	 String cebxlbdgbn;
	
	public String getMandt()
	{
		return mandt;
	}
	
	public String getUserId()
  	{
  		return userid;
  	}
	
	public String getGno()
  	{
  		return gno;
  	}
	
	/**
	 * @return
	 */
	public String getCebxlabg()
	{
		return cebxlabg;
	}

	/**
	 * @return
	 */
	public String getCebxlagb()
	{
		return cebxlagb; 
	}

	/**
	 * @return
	 */
	public String getCebxlamd()
	{
		return cebxlamd;
	}
	
	public String getCebxlhmd()
	{
		return cebxlhmd;
	}
	
	public String getCebxldmd()
	{
		return cebxldmd;
	}

	/**
	 * @return
	 */
	public String getCebxlamt()
	{
		return cebxlamt;
	}
	
	public String getCebxlhmt()
	{
		return cebxlhmt;
	}

	public String getCebxldmt()
	{
		return cebxldmt;
	}


	/**
	 * @return
	 */
	public String getCebxlara()
	{
		return cebxlara;
	}

	/**
	 * @return
	 */
	public String getCebxlbgb()
	{
		return cebxlbgb;
	}

	/**
	 * @return
	 */
	public String getCebxlbgd()
	{
		return cebxlbgd;
	}

	/**
	 * @return
	 */
	public String getCebxlbhb()
	{
		return cebxlbhb;
	}

	/**
	 * @return
	 */
	public String getCebxlbsu()
	{
		return cebxlbsu;
	}
	
	/**
	 * @return
	 */
	public String getCebxlcs1()
	{
		return cebxlcs1;
	}

	/**
	 * @return
	 */
	public String getCebxlcsj()
	{
		return cebxlcsj;
	}

	/**
	 * @return
	 */
	public String getCebxlcst()
	{
		return cebxlcst;
	}

	/**
	 * @return
	 */
	public String getCebxlgbn()
	{
		return cebxlgbn;
	}

	/**
	 * @return
	 */
	public String getCebxlgbu()
	{
		return cebxlgbu;
	}

	/**
	 * @return
	 */
	public String getCebxlgjp()
	{
		return cebxlgjp;
	}

	/**
	 * @return
	 */
	public String getCebxlgnd()
	{
		return cebxlgnd;
	}

	/**
	 * @return
	 */
	public String getCebxlhgb()
	{
		return cebxlhgb;
	}

	/**
	 * @return
	 */
	public String getCebxlhno()
	{
		return cebxlhno;
	}

	/**
	 * @return
	 */
	public String getCebxljgb()
	{
		return cebxljgb;
	}

	/**
	 * @return
	 */
	public String getCebxljuj()
	{
		return cebxljuj;
	}

	/**
	 * @return
	 */
	public String getCebxlmdt()
	{
		return cebxlmdt;
	}

	/**
	 * @return
	 */
	public String getCebxlmno()
	{
		return cebxlmno;
	}

	/**
	 * @return
	 */
	public String getCebxlmsa()
	{
		return cebxlmsa;
	}

	/**
	 * @return
	 */
	public String getCebxlmyb()
	{
		return cebxlmyb;
	}

	/**
	 * @return
	 */
	public String getCebxlmym()
	{
		return cebxlmym;
	}

	/**
	 * @return
	 */
	public String getCebxlndt()
	{
		return cebxlndt;
	}

	/**
	 * @return
	 */
	public String getCebxlntm()
	{
		return cebxlntm;
	}

	/**
	 * @return
	 */
	public String getCebxlpjt()
	{
		return cebxlpjt;
	}

	/**
	 * @return
	 */
	public String getCebxlseq()
	{
		return cebxlseq;
	}

	/**
	 * @return
	 */
	public String getCebxlsju()
	{
		return cebxlsju;
	}

	/**
	 * @return
	 */
	public String getCebxltis()
	{
		return cebxltis;
	}

	/**
	 * @return
	 */
	public String getCebxltno()
	{
		return cebxltno;
	}

	/**
	 * @return
	 */
	public String getCebxltod()
	{
		return cebxltod;
	}

	/**
	 * @return
	 */
	public String getCebxltot()
	{
		return cebxltot;
	}

	/**
	 * @return
	 */
	public String getCebxltyp()
	{
		return cebxltyp;
	}

	/**
	 * @return
	 */
	public String getCebxlup1()
	{
		return cebxlup1;
	}

	/**
	 * @return
	 */
	public String getCebxlupn()
	{
		return cebxlupn;
	}

	/**
	 * @return
	 */
	public String getCebxlvad()
	{
		return cebxlvad;
	}

	/**
	 * @return
	 */
	public String getCebxlvat()
	{
		return cebxlvat;
	}

	/**
	 * @return
	 */
	public String getCebxlydt()
	{
		return cebxlydt;
	}

	/**
	 * @return
	 */
	public String getCebxlyym()
	{
		return cebxlyym;
	}

	/**
	 * @return
	 */
	public String getCebxlzer()
	{
		return cebxlzer;
	}

	/**
	 * @return
	 */
	public String getCebxlham()
	{
		return cebxlham;
	}

	/**
	 * @return
	 */
	public String getCebxlyy1()
	{
		return cebxlyy1;
	}
	
	/**
	 * @return
	 */
	public String getCebxliis()
	{
		return cebxliis;
	}
	/*
	 * 분담이행방식 변수 가져오기
	 */
	public String getCebxlbdgbn()
	{
		return cebxlbdgbn;
	}

	public void setMandt(String string)
	{
		mandt = string;
	}
	
	public void setUserId(String string)
	{
		userid= string;
	}
	
	public void setGno(String string)
	{
		gno= string;
	}
	
	/**
	 * @param string
	 */
	public void setCebxlabg(String string)
	{
		cebxlabg = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlagb(String string)
	{
		cebxlagb = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlamd(String string)
	{
		cebxlamd = string;
	}
	public void setCebxlhmd(String string)
	{
		cebxlhmd = string;
	}
	public void setCebxldmd(String string)
	{
		cebxldmd = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlamt(String string)
	{
		cebxlamt = string;
	}
	
	public void setCebxlhmt(String string)
	{
		cebxlhmt = string;
	}
	
	public void setCebxldmt(String string)
	{
		cebxldmt = string;
	}
	/**
	 * @param string
	 */
	public void setCebxlara(String string)
	{
		cebxlara = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlbgb(String string)
	{
		cebxlbgb = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlbgd(String string)
	{
		cebxlbgd = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlbhb(String string)
	{
		cebxlbhb = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlbsu(String string)
	{
		cebxlbsu = string;
	}
	
	/**
	 * @param string
	 */
	public void setCebxlcs1(String string)
	{
		cebxlcs1 = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlcsj(String string)
	{
		cebxlcsj = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlcst(String string)
	{
		cebxlcst = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlgbn(String string)
	{
		cebxlgbn = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlgbu(String string)
	{
		cebxlgbu = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlgjp(String string)
	{
		cebxlgjp = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlgnd(String string)
	{
		cebxlgnd = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlhgb(String string)
	{
		cebxlhgb = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlhno(String string)
	{
		cebxlhno = string;
	}

	/**
	 * @param string
	 */
	public void setCebxljgb(String string)
	{
		cebxljgb = string;
	}

	/**
	 * @param string
	 */
	public void setCebxljuj(String string)
	{
		cebxljuj = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlmdt(String string)
	{
		cebxlmdt = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlmno(String string)
	{
		cebxlmno = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlmsa(String string)
	{
		cebxlmsa = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlmyb(String string)
	{
		cebxlmyb = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlmym(String string)
	{
		cebxlmym = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlndt(String string)
	{
		cebxlndt = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlntm(String string)
	{
		cebxlntm = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlpjt(String string)
	{
		cebxlpjt = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlseq(String string)
	{
		cebxlseq = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlsju(String string)
	{
		cebxlsju = string;
	}

	/**
	 * @param string
	 */
	public void setCebxltis(String string)
	{
		cebxltis = string;
	}

	/**
	 * @param string
	 */
	public void setCebxltno(String string)
	{
		cebxltno = string;
	}

	/**
	 * @param string
	 */
	public void setCebxltod(String string)
	{
		cebxltod = string;
	}

	/**
	 * @param string
	 */
	public void setCebxltot(String string)
	{
		cebxltot = string;
	}

	/**
	 * @param string
	 */
	public void setCebxltyp(String string)
	{
		cebxltyp = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlup1(String string)
	{
		cebxlup1 = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlupn(String string)
	{
		cebxlupn = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlvad(String string)
	{
		cebxlvad = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlvat(String string)
	{
		cebxlvat = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlydt(String string)
	{
		cebxlydt = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlyym(String string)
	{
		cebxlyym = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlzer(String string)
	{
		cebxlzer = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlham(String string)
	{
		cebxlham = string;
	}
	
	/**
	 * @param string
	 */
	public void setCebxlyy1(String string)
	{
		cebxlyy1 = string;
	}	
	
	/**
	 * @param string
	 */
	public void setCebxliis(String string)
	{
		cebxliis = string;
	}	

	/**
	 * @return
	 */
	public String getCebxlfdt()
	{
		return cebxlfdt;
	}

	/**
	 * @return
	 */
	public String getCebxljdq()
	{
		return cebxljdq;
	}

	/**
	 * @return
	 */
	public String getCebxltdt()
	{
		return cebxltdt;
	}

	/**
	 * @param string
	 */
	public void setCebxlfdt(String string)
	{
		cebxlfdt = string;
	}

	/**
	 * @param string
	 */
	public void setCebxljdq(String string)
	{
		cebxljdq = string;
	}

	/**
	 * @param string
	 */
	public void setCebxltdt(String string)
	{
		cebxltdt = string;
	}

	/**
	 * 작 성 자 : moono
	 * 작 성 일 : 2006-04-25
	 * 설    명 : 
	 * 기    타 : 
	 */
	public String getCebxlisr() {
		return cebxlisr;
	}

	/**
	 * 작 성 자 : moono
	 * 작 성 일 : 2006-04-25
	 * 설    명 : 
	 * 기    타 : 
	 */
	public void setCebxlisr(String string) {
		cebxlisr = string;
	}

	/**
	 * @return
	 */
	public String getCebxlmymfrom() {
		return cebxlmymfrom;
	}

	/**
	 * @return
	 */
	public String getCebxlmymto() {
		return cebxlmymto;
	}

	/**
	 * @param string
	 */
	public void setCebxlmymfrom(String string) {
		cebxlmymfrom = string;
	}

	/**
	 * @param string
	 */
	public void setCebxlmymto(String string) {
		cebxlmymto = string;
	}

	/*
	 * 분담이행방식 변수 setting
	 */
	public void setCebxlbdgbn(String string) {
		cebxlbdgbn = string;
	}

	/*
	 * 공기청정기 VO Getter/Setter
	 */		
	public String getCebxlacmd() {
		return cebxlacmd;
	}

	public void setCebxlacmd(String cebxlacmd) {
		this.cebxlacmd = cebxlacmd;
	}

	public String getCebxlacmt() {
		return cebxlacmt;
	}

	public void setCebxlacmt(String cebxlacmt) {
		this.cebxlacmt = cebxlacmt;
	}
	
}
