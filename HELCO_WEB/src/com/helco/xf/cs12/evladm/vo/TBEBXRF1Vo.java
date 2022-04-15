/**
 * 파 일 명 : TBEBXRF1Vo.java
 * 설    명 : 기성정보
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-04-28 오후 1:11:30
 * 변경내역 :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.cs12.evladm.vo;

public class TBEBXRF1Vo extends FrwValueObject
{
   private String dataId;
   private String jobGubun;
   
	private String cebxrpjt;
	private String cebxrhgb;
	private String cebxrhno;
	private String cebxrseq;
	private String cebxrgnd;
	private String cebxrjym;
	private String cebxrisr;
	private String cebxrmno;
	private String cebxrupn;
	private String cebxrcst;
	private String cebxrhty;
	private String cebxrbsu;
	private String cebxrgbu;
	private String cebxrcon;
	private String cebxragb;
	private String cebxrara;
	private String cebxrjuj;
	private String cebxrabg;
	private String cebxrpst;
	private String cebxrfdt;
	private String cebxrtod;
	private String cebxrjdq;
	private String cebxrrgb;
	private String cebxrsag;
	private String cebxrjdj;
	private String cebxrbda;
	private String cebxrhda;
	private String cebxrdda;
	private String cebxrbpd;
	private String cebxrcpr;
	private String cebxrjsd;
	private String cebxrjyq;
	private String cebxrjyc;
	private String cebxrbya;
	private String cebxrhya;
	private String cebxrdya;
	private String cebxris1;
	private String cebxris2;
	private String cebxris3;
	private String cebxris4;
	private String cebxrjyd;
	private String cebxrysy;
	private String cebxrjyj;
	private String cebxrrdt;
	private String cebxrjsy;
	private String cebxrreq;
	private String cebxridq;
	private String cebxridj;
	private String cebxrbam;
	private String cebxrham;
	private String cebxrdam;
	private String cebxrss1;
	private String cebxrapp;
	private String cebxrggb;
	private String cebxrgym;
	private String cebxrmgb;
	private String cebxriy1;
	private String cebxriy2;
	private String cebxriy3;
	private String cebxriy4;
	private String cebxrret;
	private String cebxrrmk;
	private String cebxrsdt;
	private String cebxrmsa;
	private String cebxrmdt;
	private String cebxrgy1;
   
   private String cebxrjrq;
   private String cebxrjar;
   private String cebxrjrv;
   
   //승인취소 처리용 변수 
   private String cebxrjymfrom;
   private String cebxrjymto;  
   private String mandt;
   private  String		cebxriis;
   private  String		cebxrplt;
   private  String		cebxrecb;
   private  String		cebxrydt;
   private  String		cebxrypp;
   private  String		gno;
   
   //분담이행방식 변수 추가
   private	String	cebxrbdgbn;
   
   //===========================공기청정기 추가 20200515 Han.JH==================================
   private String cebxracda;
   private String cebxracya;
   private String cebxracam;
   //======================================================================================
   
   
	/**
	 * 작성자: jhlee
	 * 작성일: 2008-05-02 오후 4:09:25
	 * 설  명: void
	 * 기  타:
	 */
	public String getMandt()
	{
		return mandt;
	}
	
	public String getGno()
	{
		return gno;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:46
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrabg()
	{
		return cebxrabg; 
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxragb()
	{
		return cebxragb;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrapp()
	{
		return cebxrapp;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrara()
	{
		return cebxrara;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrbam()
	{
		return cebxrbam;
	}
	
	public String getCebxrham()
	{
		return cebxrham;
	}
	
	public String getCebxrdam()
	{
		return cebxrdam;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrbda()
	{
		return cebxrbda;
	}

	public String getCebxrhda()
	{
		return cebxrhda;
	}

	public String getCebxrdda()
	{
		return cebxrdda;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrbpd()
	{
		return cebxrbpd;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrbsu()
	{
		return cebxrbsu;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrbya()
	{
		return cebxrbya;
	}

	public String getCebxrhya()
	{
		return cebxrhya;
	}
	
	public String getCebxrdya()
	{
		return cebxrdya;
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrcon()
	{
		return cebxrcon;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrcpr()
	{
		return cebxrcpr;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrcst()
	{
		return cebxrcst;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrfdt()
	{
		return cebxrfdt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrgbu()
	{
		return cebxrgbu;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrggb()
	{
		return cebxrggb;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrgnd()
	{
		return cebxrgnd;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrgym()
	{
		return cebxrgym;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrgy1()
	{
		return cebxrgy1;
	}	

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrhgb()
	{
		return cebxrhgb;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrhno()
	{
		return cebxrhno;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrhty()
	{
		return cebxrhty;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxridj()
	{
		return cebxridj;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxridq()
	{
		return cebxridq;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxris1()
	{
		return cebxris1;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxris2()
	{
		return cebxris2;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxris3()
	{
		return cebxris3;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxris4()
	{
		return cebxris4;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrisr()
	{
		return cebxrisr;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxriy1()
	{
		return cebxriy1;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxriy2()
	{
		return cebxriy2;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxriy3()
	{
		return cebxriy3;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxriy4()
	{
		return cebxriy4;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrret()
	{
		return cebxrret;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrrmk()
	{
		return cebxrrmk;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjdj()
	{
		return cebxrjdj;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjdq()
	{
		return cebxrjdq;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjsd()
	{
		return cebxrjsd;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:47
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjsy()
	{
		return cebxrjsy;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjuj()
	{
		return cebxrjuj;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjyc()
	{
		return cebxrjyc;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjyd()
	{
		return cebxrjyd;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjyj()
	{
		return cebxrjyj;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjym()
	{
		return cebxrjym;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjyq()
	{
		return cebxrjyq;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrmdt()
	{
		return cebxrmdt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrmgb()
	{
		return cebxrmgb;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrmno()
	{
		return cebxrmno;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrmsa()
	{
		return cebxrmsa;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrpjt()
	{
		return cebxrpjt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrpst()
	{
		return cebxrpst;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrrdt()
	{
		return cebxrrdt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrreq()
	{
		return cebxrreq;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrrgb()
	{
		return cebxrrgb;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrsag()
	{
		return cebxrsag;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrsdt()
	{
		return cebxrsdt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrseq()
	{
		return cebxrseq;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrss1()
	{
		return cebxrss1;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrtod()
	{
		return cebxrtod;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrupn()
	{
		return cebxrupn;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrysy()
	{
		return cebxrysy;
	}

	public String getCebxriis()
	{
		return cebxriis;
	}
	
	public String getCebxrplt()
	{
		return cebxrplt;
	}

	public String getCebxrecb()
	{
		return cebxrecb;
	}
	
	public String getCebxrydt()
	{
		return cebxrydt;
	}
	
	public String getCebxrypp()
	{
		return cebxrypp;
	}
	/*
	 * 분담이행방서 변수 정보
	 */
	public String getCebxrbdgbn()
	{
		return cebxrbdgbn;
	}

	public void setCebxriis(String string)
	{
		cebxriis = string;
	}
	
	public void setCebxrplt(String string)
	{
		cebxrplt = string;
	}
	
	public void setCebxrecb(String string)
	{
		cebxrecb = string;
	}
	
	public void setCebxrydt(String string)
	{
		cebxrydt = string;
	}
	
	public void setCebxrypp(String string)
	{
		cebxrypp = string;
	}
	
	/**
	 * 작성자: jhlee
	 * 작성일: 2008-05-02 오후 4:09:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setMandt(String string)
	{
		mandt = string;
	}
	
	public void setGno(String string)
	{
		gno = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrabg(String string)
	{
		cebxrabg = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxragb(String string)
	{
		cebxragb = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrapp(String string)
	{
		cebxrapp = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrara(String string)
	{
		cebxrara = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrbam(String string)
	{
		cebxrbam = string;
	}
	public void setCebxrham(String string)
	{
		cebxrham = string;
	}
	public void setCebxrdam(String string)
	{
		cebxrdam = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrbda(String string)
	{
		cebxrbda = string;
	}

	public void setCebxrhda(String string)
	{
		cebxrhda = string;
	}

	public void setCebxrdda(String string)
	{
		cebxrdda = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrbpd(String string)
	{
		cebxrbpd = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrbsu(String string)
	{
		cebxrbsu = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrbya(String string)
	{
		cebxrbya = string;
	}
	public void setCebxrhya(String string)
	{
		cebxrhya = string;
	}
	public void setCebxrdya(String string)
	{
		cebxrdya = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrcon(String string)
	{
		cebxrcon = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrcpr(String string)
	{
		cebxrcpr = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrcst(String string)
	{
		cebxrcst = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrfdt(String string)
	{
		cebxrfdt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrgbu(String string)
	{
		cebxrgbu = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrggb(String string)
	{
		cebxrggb = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrgnd(String string)
	{
		cebxrgnd = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrgym(String string)
	{
		cebxrgym = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrgy1(String string)
	{
		cebxrgy1 = string;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrhgb(String string)
	{
		cebxrhgb = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrhno(String string)
	{
		cebxrhno = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrhty(String string)
	{
		cebxrhty = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxridj(String string)
	{
		cebxridj = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxridq(String string)
	{
		cebxridq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxris1(String string)
	{
		cebxris1 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxris2(String string)
	{
		cebxris2 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxris3(String string)
	{
		cebxris3 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxris4(String string)
	{
		cebxris4 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrisr(String string)
	{
		cebxrisr = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxriy1(String string)
	{
		cebxriy1 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxriy2(String string)
	{
		cebxriy2 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxriy3(String string)
	{
		cebxriy3 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxriy4(String string)
	{
		cebxriy4 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrret(String string)
	{
		cebxrret = string;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:48
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrrmk(String string)
	{
		cebxrrmk = string;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjdj(String string)
	{
		cebxrjdj = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjdq(String string)
	{
		cebxrjdq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjsd(String string)
	{
		cebxrjsd = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjsy(String string)
	{
		cebxrjsy = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjuj(String string)
	{
		cebxrjuj = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjyc(String string)
	{
		cebxrjyc = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjyd(String string)
	{
		cebxrjyd = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjyj(String string)
	{
		cebxrjyj = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjym(String string)
	{
		cebxrjym = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjyq(String string)
	{
		cebxrjyq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrmdt(String string)
	{
		cebxrmdt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrmgb(String string)
	{
		cebxrmgb = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrmno(String string)
	{
		cebxrmno = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrmsa(String string)
	{
		cebxrmsa = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrpjt(String string)
	{
		cebxrpjt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrpst(String string)
	{
		cebxrpst = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrrdt(String string)
	{
		cebxrrdt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrreq(String string)
	{
		cebxrreq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrrgb(String string)
	{
		cebxrrgb = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrsag(String string)
	{
		cebxrsag = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrsdt(String string)
	{
		cebxrsdt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrseq(String string)
	{
		cebxrseq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrss1(String string)
	{
		cebxrss1 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrtod(String string)
	{
		cebxrtod = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrupn(String string)
	{
		cebxrupn = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:34:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrysy(String string)
	{
		cebxrysy = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:36:40
	 * 설  명: String
	 * 기  타:
	 */
	public String getDataId()
	{
		return dataId;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:36:40
	 * 설  명: String
	 * 기  타:
	 */
	public String getJobGubun()
	{
		return jobGubun;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:36:40
	 * 설  명: void
	 * 기  타:
	 */
	public void setDataId(String string)
	{
		dataId = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:36:40
	 * 설  명: void
	 * 기  타:
	 */
	public void setJobGubun(String string)
	{
		jobGubun = string;
	}



	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-21 오후 9:33:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjar()
	{
		return cebxrjar;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-21 오후 9:33:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjrq()
	{
		return cebxrjrq;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-21 오후 9:33:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebxrjrv()
	{
		return cebxrjrv;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-21 오후 9:33:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjar(String string)
	{
		cebxrjar = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-21 오후 9:33:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjrq(String string)
	{
		cebxrjrq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-21 오후 9:33:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebxrjrv(String string)
	{
		cebxrjrv = string;
	}

	/**
	 * @return
	 */
	public String getCebxrjymfrom() {
		return cebxrjymfrom;
	}
	
	/**
	 * @return
	 */
	public String getCebxrjymto() {
		return cebxrjymto;
	}
	
	/**
	 * @param string
	 */
	public void setCebxrjymfrom(String string) {
		cebxrjymfrom = string;
	}
	
	/**
	 * @param string
	 */
	public void setCebxrjymto(String string) {
		cebxrjymto = string;
	}

	/**
	 * 분담이행방식 변서 설정
	 */
	public void setCebxrbdgbn(String string) {
		cebxrbdgbn = string;
	}

	/**
	 * 공기청정기 VO Getter/Setter
	 */
	public String getCebxracda() {
		return cebxracda;
	}

	public void setCebxracda(String cebxracda) {
		this.cebxracda = cebxracda;
	}

	public String getCebxracya() {
		return cebxracya;
	}

	public void setCebxracya(String cebxracya) {
		this.cebxracya = cebxracya;
	}

	public String getCebxracam() {
		return cebxracam;
	}

	public void setCebxracam(String cebxracam) {
		this.cebxracam = cebxracam;
	}
	
}
