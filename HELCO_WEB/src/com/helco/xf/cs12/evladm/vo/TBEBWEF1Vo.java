/**
 * 파 일 명 : TBEBWEF1Vo.java
 * 설    명 : 무상보수 발주정보 vo 
 * 작 성 자 : shhwang
 * 작 성 일 : 2006-03-22 오전  9:43:09
 * 변경내역 :
 * 0404 칼럼 4개 빠짐 
 */ 
package com.helco.xf.cs12.evladm.vo;

public class TBEBWEF1Vo extends FrwValueObject
{
   // TBEBWEF1 컬럼정보
   private  String     cebwepjt;	
	private  String     cebwehgb;	
	private  String     cebwehno;	
	private  String     cebwemno;	
	private  String   	cebwetyp	;
	private  String   	cebwehty	;
	private  String   	cebweara	;
	private  String   	cebwejuj	;
	private  String   	cebwebpm;
	private  String   	cebwebpm_f;		
	private  String   	cebwebsu	;
	private  String   	cebwefdt	;
	private  String   	cebwecbs;	
	private  String   	cebwepst	;
	private  String   	cebwejuc	;
	private  String   	cebwebgt	;
	private  String   	cebwemg1	;
	private  String   	cebwebmt	;
	private  String   	cebwegbn	;	
	private  String   	cebwebhd	;
	private  String   	cebweagb	;
    private  String     cebweabg;
	private  String   	cebwemut	;
	private  String   	cebwembg;	
	private  String   	cebwebyt	;
	private  String   	cebweycj	;
	private  String   	cebwebjt	;
	private  String   	cebwebjj	;
	private  String   	cebwehyn;	
	private  String   	cebwebst	;
	private  String   	cebweapp	;
	private  String   	cebwebcc	;
	private  String   	cebwebct	;
	private  String   	cebwermk	;	
	private  String   	cebweplt	;
	private  String   	cebwemlt	;	
	private  String   	cebwetgb	;	
	private  String   	cebwegno	;	
	private  String   	cebweseq	;//추가 0401
	private  String   	cebwegnd	;//추가 0401
	private  String   	cebwebhd1	;//무상 잔여기간 인서트용 해지일자    
	private  String   	gkd	;//계약구분
   private  String		prePst; // 이전 상태
   private  String		mandt; // 클라이언트 
   private  String   	gno	;
   private  String   	sla	;
   
   // 분담이행방식을 위한 변수
   private	String		cebwebdgbn;
   
   //자회사 무상 관련 추가
   private  String   	cevwevkgrp	 ;   //조직코드 
   private  String   	cevwevkgrpco ;   //co 조직코드
   
   private  String   	cevwezmusaocnt ;   //무상공사 개월수
   private  String   	cevwezmusaocnt2 ;  //무상일반 개월수
   private  String   	cevwezmusaucnt ;   //무상공사 사용개월수
   private  String   	cevwezmusaucnt2 ;  //무상일반 일반개월수
   private  String   	cevwezmusosum ;    //총개월수 
   private  String   	cevwezmusogap ;    //총개월수 - 총 사용개월수
   
   
	/**
	 * 작성자: jhlee
	 * 작성일: 2008-05-02 오후 4:09:25
	 * 설  명: void
	 * 기  타:
	 */
   public String getSla()
	{
		return sla;
	}
   
   public String getMandt()
	{
		return mandt;
	}
	
	public String getGno()
	{
		return gno;
	}

	public String getCebweplt()
	{
		return cebweplt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebweabg()
	{
		return cebweabg;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebweagb()
	{
		return cebweagb;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebweapp()
	{
		return cebweapp;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebweara()
	{
		return cebweara;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebcc()
	{
		return cebwebcc;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebct()
	{
		return cebwebct;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebgt()
	{
		return cebwebgt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebhd()
	{
		return cebwebhd;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebjj()
	{
		return cebwebjj;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebjt()
	{
		return cebwebjt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebmt()
	{
		return cebwebmt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebpm()
	{
		return cebwebpm;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:24
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebpm_f()
	{
		return cebwebpm_f;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebst()
	{
		return cebwebst;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebsu()
	{
		return cebwebsu;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebyt()
	{
		return cebwebyt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwecbs()
	{
		return cebwecbs;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwefdt()
	{
		return cebwefdt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwegbn()
	{
		return cebwegbn;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwegnd()
	{
		return cebwegnd;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwehgb()
	{
		return cebwehgb;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwehno()
	{
		return cebwehno;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwehty()
	{
		return cebwehty;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwehyn()
	{
		return cebwehyn;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwejuc()
	{
		return cebwejuc;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwejuj()
	{
		return cebwejuj;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwembg()
	{
		return cebwembg;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwemg1()
	{
		return cebwemg1;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwemno()
	{
		return cebwemno;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwemut()
	{
		return cebwemut;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwepjt()
	{
		return cebwepjt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwepst()
	{
		return cebwepst;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwermk()
	{
		return cebwermk;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebweseq()
	{
		return cebweseq;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwetyp()
	{
		return cebwetyp;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebweycj()
	{
		return cebweycj;
	}
	
	public String getCebwemlt()
	{
		return cebwemlt;
	}
	
	public String getCebwetgb()
	{
		return cebwetgb;
	}
	
	public String getCebwegno()
	{
		return cebwegno;
	}


	public String getCebwebdgbn()
	{
		return cebwebdgbn;
	}
/**
	 * 작성자: jhlee
	 * 작성일: 2008-05-02 오후 4:09:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setSla(String string)
	{
		sla = string;
	}

	public void setMandt(String string)
	{
		mandt = string;
	}
	
	public void setGno(String string)
	{
		gno = string;
	}

	public void setCebweplt(String string)
	{
		cebweplt = string;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebweabg(String string)
	{
		cebweabg = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebweagb(String string)
	{
		cebweagb = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebweapp(String string)
	{
		cebweapp = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebweara(String string)
	{
		cebweara = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebcc(String string)
	{
		cebwebcc = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebct(String string)
	{
		cebwebct = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebgt(String string)
	{
		cebwebgt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebhd(String string)
	{
		cebwebhd = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebjj(String string)
	{
		cebwebjj = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebjt(String string)
	{
		cebwebjt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebmt(String string)
	{
		cebwebmt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebpm(String string)
	{
		cebwebpm = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebpm_f(String string)
	{
		cebwebpm_f = string;
	}
	
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebst(String string)
	{
		cebwebst = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebsu(String string)
	{
		cebwebsu = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebyt(String string)
	{
		cebwebyt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwecbs(String string)
	{
		cebwecbs = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwefdt(String string)
	{
		cebwefdt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwegbn(String string)
	{
		cebwegbn = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwegnd(String string)
	{
		cebwegnd = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwehgb(String string)
	{
		cebwehgb = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwehno(String string)
	{
		cebwehno = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwehty(String string)
	{
		cebwehty = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwehyn(String string)
	{
		cebwehyn = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwejuc(String string)
	{
		cebwejuc = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwejuj(String string)
	{
		cebwejuj = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwembg(String string)
	{
		cebwembg = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwemg1(String string)
	{
		cebwemg1 = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwemno(String string)
	{
		cebwemno = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwemut(String string)
	{
		cebwemut = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwepjt(String string)
	{
		cebwepjt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwepst(String string)
	{
		cebwepst = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwermk(String string)
	{
		cebwermk = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebweseq(String string)
	{
		cebweseq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwetyp(String string)
	{
		cebwetyp = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 5:05:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebweycj(String string)
	{
		cebweycj = string;
	}

	/**
	 * 작성자: shhwang
	 * 작성일: 2006-06-19 오후 5:49:00
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwebhd1()
	{
		return cebwebhd1;
	}

	/**
	 * 작성자: shhwang
	 * 작성일: 2006-06-19 오후 5:49:00
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwebhd1(String string)
	{
		cebwebhd1 = string;
	}

	/**
	 * 작성자: shhwang
	 * 작성일: 2006-06-20 오후 1:22:14
	 * 설  명: String
	 * 기  타:
	 */
	public String getGkd()
	{
		return gkd;
	}

	/**
	 * 작성자: shhwang
	 * 작성일: 2006-06-20 오후 1:22:14
	 * 설  명: void
	 * 기  타:
	 */
	public void setGkd(String string)
	{
		gkd = string;
	}

/**
 * @return
 */
public String getPrePst()
{
	return prePst;
}

/**
 * @param string
 */
public void setPrePst(String string)
{
	prePst = string;
}

public void setCebwemlt(String string)
{
	cebwemlt = string;
}

public void setCebwetgb(String string)
{
	cebwetgb = string;
}

public void setCebwegno(String string)
{
	cebwegno = string;
}

public void setCebwebdgbn(String string)
{
	cebwebdgbn = string;
}

/**
 * @return the cevwevkgrp
 */
public String getCevwevkgrp() {
	return cevwevkgrp;
}

/**
 * @param cevwevkgrp the cevwevkgrp to set
 */
public void setCevwevkgrp(String cevwevkgrp) {
	this.cevwevkgrp = cevwevkgrp;
}

/**
 * @return the cevwevkgrpco
 */
public String getCevwevkgrpco() {
	return cevwevkgrpco;
}

/**
 * @param cevwevkgrpco the cevwevkgrpco to set
 */
public void setCevwevkgrpco(String cevwevkgrpco) {
	this.cevwevkgrpco = cevwevkgrpco;
}

/**
 * @return the cevwezmusaocnt
 */
public String getCevwezmusaocnt() {
	return cevwezmusaocnt;
}

/**
 * @param cevwezmusaocnt the cevwezmusaocnt to set
 */
public void setCevwezmusaocnt(String cevwezmusaocnt) {
	this.cevwezmusaocnt = cevwezmusaocnt;
}

/**
 * @return the cevwezmusaocnt2
 */
public String getCevwezmusaocnt2() {
	return cevwezmusaocnt2;
}

/**
 * @param cevwezmusaocnt2 the cevwezmusaocnt2 to set
 */
public void setCevwezmusaocnt2(String cevwezmusaocnt2) {
	this.cevwezmusaocnt2 = cevwezmusaocnt2;
}

/**
 * @return the cevwezmusaucnt
 */
public String getCevwezmusaucnt() {
	return cevwezmusaucnt;
}

/**
 * @param cevwezmusaucnt the cevwezmusaucnt to set
 */
public void setCevwezmusaucnt(String cevwezmusaucnt) {
	this.cevwezmusaucnt = cevwezmusaucnt;
}

/**
 * @return the cevwezmusaucnt2
 */
public String getCevwezmusaucnt2() {
	return cevwezmusaucnt2;
}

/**
 * @param cevwezmusaucnt2 the cevwezmusaucnt2 to set
 */
public void setCevwezmusaucnt2(String cevwezmusaucnt2) {
	this.cevwezmusaucnt2 = cevwezmusaucnt2;
}

/**
 * @return the cevwezmusosum
 */
public String getCevwezmusosum() {
	return cevwezmusosum;
}

/**
 * @param cevwezmusosum the cevwezmusosum to set
 */
public void setCevwezmusosum(String cevwezmusosum) {
	this.cevwezmusosum = cevwezmusosum;
}

/**
 * @return the cevwezmusogap
 */
public String getCevwezmusogap() {
	return cevwezmusogap;
}

/**
 * @param cevwezmusogap the cevwezmusogap to set
 */
public void setCevwezmusogap(String cevwezmusogap) {
	this.cevwezmusogap = cevwezmusogap;
}


}
