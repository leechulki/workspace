/**
 * 파 일 명 : TBEBWMF1Vo.java
 * 설    명 : 유상보수 정보 vo 
 * 작 성 자 : shhwang
 * 작 성 일 : 2006-03-27 오전   8:43:09
 * 변경내역 :
 *
 */ 
package com.helco.xf.cs12.evladm.vo;

public class TBEBWMF1Vo extends FrwValueObject
{
	  // TBEBWMF1 컬럼정보
	  private  String   cebwmupn;               
	  private  String   cebwmcst;
	  private  String   cebwmpjt;
	  private  String   cebwmhgb;
	  private  String   cebwmhno;
	  private  String   cebwmseq;//0406추가 
	  private  String   cebwmmno;
	  private  String   cebwmpst;
	  private  String   cebwmtyp;
	  private  String   cebwmhty;
	  private  String   cebwmara;
	  private  String   cebwmbpm;//0330추가
	  private  String   cebwmbpm_f;//0330추가  
	  private  String   cebwmbsu;//0330추가 
	  private  String   cebwmjuj;
	  private  String   cebwmbuj;
	  private  String   cebwmgnd;//계약종류 0331추가 
	  private  String   cebwmgkd;
	  private  String   cebwmgyb;
	  private  String   cebwmrgb;
	  private  String   cebwmcgb;
	  private  String   cebwmusd;
	  private  String   cebwmugs;
	  private  String   cebwmums;
	  private  String   cebwmmmn;
	  private  String   cebwmumr;
	  private  String   cebwmuhj;	  
	  private  String   cebwmagb;
	  private  String   cebwmabg;
	  private  String   cebwmbgb;
	  private  String   cebwmgbn;
	  private  String   cebwmbjg;
	  private  String   cebwmtis;
	  private  String   cebwmjkh;
	  private  String   cebwmyyb;
	  private  String   cebwmrmk;
	  private  String   cebwmzer;
	  private  String   cebwmwyb;
	  private  String   cebwmamt;
	  private  String   cebwmvat;
	  private  String   cebwmtot;
	  private  String   cebwmknd;
	  private  String   cebwmrto;
	  private  String   cebwmiyn;
	  private  String   cebwmgbm;
	  private  String   cebwmbdt;
	  private  String   cebwmbsj;
	  private  String   cebwmrdt;
	  private  String   cebwmreq;
	  private  String   cebwmsdt;
	  private  String   cebwmapp;
	  private  String   cebwmcha;
	  private  String   cebwmcdt;
	  private  String   cebwmwil;
	  private  String   cebwmpro;
	  private  String   cebwmtgb;
	  private  String   cebwmplt;
	  private  String   cebwmcsy;
	  private  String   cebwmddt;
	  private  String   cebwmdpp;
	  private  String   cebwmgno;
	  private  String   cebwmadt;
	  private  String   cebwmaeq;
	  private  String   cebwmmlt;
	  private  String   userid;
	  private  String   gno;
	  private  String   vbeln;
	  private  String   lifnr;
	  private  String   sla;
	  private  String   cebwmhmt;
	  private  String   cebwmdmt;
	  
	  // 공기청정기 금액 변수
	  private  String   cebwmacmt;
	  
	  // 분담이행방식 변
	  private  String   cebwmambt;
	  private  String   cebwmvabt;
	  private  String   cebwmtobt;
	  private  String   cebwmbdgbn;

     

	  //화면에서 넘기는 파라미터  변수 
	  private  String   fmyn;//fm계약여부 
	  private  String   bsu;//협력사코드 
	  private  String   gubun;//신규입력시 계약구분 
	  private  String   flag;//저장구분 
	  private  String   extseq;//화면에서 넘기는 자동연장요 seq
	  private  String   cebwmuhj1;//해지일 업데이트용 변수 
	  private  String   cebwmseq1;//해지일 처리용 maxseq
     
     //자동이체여부
     private  String   cebwmsju;
     private  String   mandt;
		
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
     
     public String getVbeln()
   	{
   		return vbeln;
   	}
     
     public String getLifnr()
   	{
   		return lifnr;
   	}
     
     public String getUserId()
   	{
   		return userid;
   	}
     
 	public String getCebwmpro()
 	{
 		return cebwmpro;
 	}
 	public String getCebwmtgb()
 	{
 		return cebwmtgb;
 	}
 	public String getCebwmplt()
 	{
 		return cebwmplt;
 	}
 	public String getCebwmcsy()
 	{
 		return cebwmcsy;
 	}
 	public String getCebwmddt()
 	{
 		return cebwmddt;
 	}
 	public String getCebwmdpp()
 	{
 		return cebwmdpp;
 	}
 	public String getCebwmgno()
 	{
 		return cebwmgno;
 	}
 	public String getCebwmadt()
 	{
 		return cebwmadt;
 	}
 	public String getCebwmaeq()
 	{
 		return cebwmaeq;
 	}
 	
     /**
	 * @return
	 */
	public String getCebwmabg()
	{
		return cebwmabg;
	}

	/**
	 * @return
	 */
	public String getCebwmagb()
	{
		return cebwmagb;
	}

	/**
	 * @return
	 */
	public String getCebwmamt()
	{
		return cebwmamt;
	}

	/**
	 * @return
	 */
	public String getCebwmapp()
	{
		return cebwmapp;
	}

	/**
	 * @return
	 */
	public String getCebwmara()
	{
		return cebwmara;
	}

	/**
	 * @return
	 */
	public String getCebwmbdt()
	{
		return cebwmbdt;
	}

	/**
	 * @return
	 */
	public String getCebwmbgb()
	{
		return cebwmbgb;
	}

	/**
	 * @return
	 */
	public String getCebwmbjg()
	{
		return cebwmbjg;
	}

	/**
	 * @return
	 */
	public String getCebwmbsj()
	{
		return cebwmbsj;
	}

	/**
	 * @return
	 */
	public String getCebwmbuj()
	{
		return cebwmbuj;
	}

	/**
	 * @return
	 */
	public String getCebwmcdt()
	{
		return cebwmcdt;
	}

	/**
	 * @return
	 */
	public String getCebwmcgb()
	{
		return cebwmcgb;
	}

	/**
	 * @return
	 */
	public String getCebwmcha()
	{
		return cebwmcha;
	}

	/**
	 * @return
	 */
	public String getCebwmcst()
	{
		return cebwmcst;
	}

	/**
	 * @return
	 */
	public String getCebwmgbm()
	{
		return cebwmgbm;
	}

	/**
	 * @return
	 */
	public String getCebwmgbn()
	{
		return cebwmgbn;
	}

	/**
	 * @return
	 */
	public String getCebwmgyb()
	{
		return cebwmgyb;
	}

	/**
	 * @return
	 */
	public String getCebwmhgb()
	{
		return cebwmhgb;
	}

	/**
	 * @return
	 */
	public String getCebwmhno()
	{
		return cebwmhno;
	}

	/**
	 * @return
	 */
	public String getCebwmhty()
	{
		return cebwmhty;
	}

	/**
	 * @return
	 */
	public String getCebwmiyn()
	{
		return cebwmiyn;
	}

	/**
	 * @return
	 */
	public String getCebwmjkh()
	{
		return cebwmjkh;
	}

	/**
	 * @return
	 */
	public String getCebwmjuj()
	{
		return cebwmjuj;
	}

	/**
	 * @return
	 */
	public String getCebwmknd()
	{
		return cebwmknd;
	}

	/**
	 * @return
	 */
	public String getCebwmmmn()
	{
		return cebwmmmn;
	}

	/**
	 * @return
	 */
	public String getCebwmmno()
	{
		return cebwmmno;
	}

	/**
	 * @return
	 */
	public String getCebwmpjt()
	{
		return cebwmpjt;
	}

	/**
	 * @return
	 */
	public String getCebwmpst()
	{
		return cebwmpst;
	}

	/**
	 * @return
	 */
	public String getCebwmrdt()
	{
		return cebwmrdt;
	}

	/**
	 * @return
	 */
	public String getCebwmreq()
	{
		return cebwmreq;
	}

	/**
	 * @return
	 */
	public String getCebwmrgb()
	{
		return cebwmrgb;
	}

	/**
	 * @return
	 */
	public String getCebwmrmk()
	{
		return cebwmrmk;
	}

	/**
	 * @return
	 */
	public String getCebwmsdt()
	{
		return cebwmsdt;
	}

	/**
	 * @return
	 */
	public String getCebwmtis()
	{
		return cebwmtis;
	}

	/**
	 * @return
	 */
	public String getCebwmtot()
	{
		return cebwmtot;
	}

	/**
	 * @return
	 */
	public String getCebwmtyp()
	{
		return cebwmtyp;
	}

	/**
	 * @return
	 */
	public String getCebwmugs()
	{
		return cebwmugs;
	}

	/**
	 * @return
	 */
	public String getCebwmuhj()
	{
		return cebwmuhj;
	}

	/**
	 * @return
	 */
	public String getCebwmumr()
	{
		return cebwmumr;
	}

	/**
	 * @return
	 */
	public String getCebwmums()
	{
		return cebwmums;
	}

	/**
	 * @return
	 */
	public String getCebwmupn()
	{
		return cebwmupn;
	}

	/**
	 * @return
	 */
	public String getCebwmusd()
	{
		return cebwmusd;
	}

	/**
	 * @return
	 */
	public String getCebwmvat()
	{
		return cebwmvat;
	}

	/**
	 * @return
	 */
	public String getCebwmwil()
	{
		return cebwmwil;
	}

	/**
	 * @return
	 */
	public String getCebwmwyb()
	{
		return cebwmwyb;
	}

	/**
	 * @return
	 */
	public String getCebwmyyb()
	{
		return cebwmyyb;
	}

	/**
	 * @return
	 */
	public String getCebwmzer()
	{
		return cebwmzer;
	}

	/**
	 * @return
	 */
	public String getCebwmmlt()
	{
		return cebwmmlt;
	}

	/**
	 * @return
	 */
	public String getCebwmambt()
	{
		return cebwmambt;
	}
	
	/**
	 * @return
	 */
	public String getCebwmvabt()
	{
		return cebwmvabt;
	}
	
	/**
	 * @return
	 */
	public String getCebwmtobt()
	{
		return cebwmtobt;
	}

	/**
	 * @return
	 */
	public String getCebwmbdgbn()
	{
		return cebwmbdgbn;
	}

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
		gno= string;
	}
	
	public void setVbeln(String string)
	{
		vbeln= string;
	}
	
	public void setLifnr(String string)
	{
		lifnr= string;
	}
	
	public void setUserId(String string)
	{
		userid= string;
	}
	public void setCebwmpro(String string)
	{
		cebwmpro = string;
	}
	public void setCebwmtgb(String string)
	{
		cebwmtgb = string;
	}
	public void setCebwmplt(String string)
	{
		cebwmplt = string;
	}
	public void setCebwmcsy(String string)
	{
		cebwmcsy = string;
	}
	public void setCebwmddt(String string)
	{
		cebwmddt = string;
	}
	public void setCebwmdpp(String string)
	{
		cebwmdpp = string;
	}
	public void setCebwmgno(String string)
	{
		cebwmgno = string;
	}
	public void setCebwmadt(String string)
	{
		cebwmadt = string;
	}
	public void setCebwmaeq(String string)
	{
		cebwmaeq = string;
	}
	/**
	 * @param string
	 */
	public void setCebwmabg(String string)
	{
		cebwmabg = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmagb(String string)
	{
		cebwmagb = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmamt(String string)
	{
		cebwmamt = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmapp(String string)
	{
		cebwmapp = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmara(String string)
	{
		cebwmara = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmbdt(String string)
	{
		cebwmbdt = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmbgb(String string)
	{
		cebwmbgb = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmbjg(String string)
	{
		cebwmbjg = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmbsj(String string)
	{
		cebwmbsj = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmbuj(String string)
	{
		cebwmbuj = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmcdt(String string)
	{
		cebwmcdt = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmcgb(String string)
	{
		cebwmcgb = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmcha(String string)
	{
		cebwmcha = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmcst(String string)
	{
		cebwmcst = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmgbm(String string)
	{
		cebwmgbm = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmgbn(String string)
	{
		cebwmgbn = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmgyb(String string)
	{
		cebwmgyb = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmhgb(String string)
	{
		cebwmhgb = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmhno(String string)
	{
		cebwmhno = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmhty(String string)
	{
		cebwmhty = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmiyn(String string)
	{
		cebwmiyn = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmjkh(String string)
	{
		cebwmjkh = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmjuj(String string)
	{
		cebwmjuj = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmknd(String string)
	{
		cebwmknd = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmmmn(String string)
	{
		cebwmmmn = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmmno(String string)
	{
		cebwmmno = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmpjt(String string)
	{
		cebwmpjt = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmpst(String string)
	{
		cebwmpst = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmrdt(String string)
	{
		cebwmrdt = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmreq(String string)
	{
		cebwmreq = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmrgb(String string)
	{
		cebwmrgb = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmrmk(String string)
	{
		cebwmrmk = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmsdt(String string)
	{
		cebwmsdt = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmtis(String string)
	{
		cebwmtis = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmtot(String string)
	{
		cebwmtot = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmtyp(String string)
	{
		cebwmtyp = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmugs(String string)
	{
		cebwmugs = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmuhj(String string)
	{
		cebwmuhj = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmumr(String string)
	{
		cebwmumr = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmums(String string)
	{
		cebwmums = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmupn(String string)
	{
		cebwmupn = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmusd(String string)
	{
		cebwmusd = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmvat(String string)
	{
		cebwmvat = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmwil(String string)
	{
		cebwmwil = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmwyb(String string)
	{
		cebwmwyb = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmyyb(String string)
	{
		cebwmyyb = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmzer(String string)
	{
		cebwmzer = string;
	}

		/**
		 * @return
		 */
		public String getCebwmgkd()
		{
			return cebwmgkd;
		}

		/**
		 * @param string
		 */
		public void setCebwmgkd(String string)
		{
			cebwmgkd = string;
		}

	/**
	 * @return
	 */
	public String getCebwmrto()
	{
		return cebwmrto;
	}

	/**
	 * @param string
	 */
	public void setCebwmrto(String string)
	{
		cebwmrto = string;
	}

	/**
	 * @return
	 */
	public String getFmyn()
	{
		return fmyn;
	}

	/**
	 * @param string
	 */
	public void setFmyn(String string)
	{
		fmyn = string;
	}

	/**
	 * @return
	 */
	public String getBsu()
	{
		return bsu;
	}

	/**
	 * @param string
	 */
	public void setBsu(String string)
	{
		bsu = string;
	}

	/**
	 * @return
	 */
	public String getFlag()
	{
		return flag;
	}

	/**
	 * @param string
	 */
	public void setFlag(String string)
	{
		flag = string;
	}

	/**
	 * @return
	 */
	public String getGubun()
	{
		return gubun;
	}

	/**
	 * @param string
	 */
	public void setGubun(String string)
	{
		gubun = string;
	}

	/**
	 * @return
	 */
	public String getCebwmbpm()
	{
		return cebwmbpm;
	}

	/**
	 * @return
	 */
	public String getCebwmbpm_f()
	{
		return cebwmbpm_f;
	}
	
	/**
	 * @return
	 */
	public String getCebwmbsu()
	{
		return cebwmbsu;
	}

	/**
	 * @param string
	 */
	public void setCebwmbpm(String string)
	{
		cebwmbpm = string;
	}

	/**
	 * @param string
	 */
	public void setCebwmbpm_f(String string)
	{
		cebwmbpm_f = string;
	}
	
	/**
	 * @param string
	 */
	public void setCebwmbsu(String string)
	{
		cebwmbsu = string;
	}

	/**
	 * @return
	 */
	public String getCebwmgnd()
	{
		return cebwmgnd;
	}

	/**
	 * @param string
	 */
	public void setCebwmgnd(String string)
	{
		cebwmgnd = string;
	}

	/**
	 * @return
	 */
	public String getCebwmseq()
	{
		return cebwmseq;
	}
	
	public String getCebwmhmt()
	{
		return cebwmhmt;
	}	
	
	public String getCebwmdmt()
	{
		return cebwmdmt;
	}		
	/**
	 * @param string
	 */
	public void setCebwmseq(String string)
	{
		cebwmseq = string;
	}

	/**
	 * 작성자: owner
	 * 작성일: 2006-05-02 오후 2:39:59
	 * 설  명: String
	 * 기  타:
	 */
	public String getExtseq()
	{
		return extseq;
	}

	/**
	 * 작성자: owner
	 * 작성일: 2006-05-02 오후 2:39:59
	 * 설  명: void
	 * 기  타:
	 */
	public void setExtseq(String string)
	{
		extseq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-08 오전 8:41:02
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwmsju()
	{
		return cebwmsju;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-08 오전 8:41:03
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwmsju(String string)
	{
		cebwmsju = string;
	}

	/**
	 * 작성자: owner
	 * 작성일: 2006-06-15 오후 1:18:33
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwmuhj1()
	{
		return cebwmuhj1;
	}

	/**
	 * 작성자: owner
	 * 작성일: 2006-06-15 오후 1:18:34
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwmuhj1(String string)
	{
		cebwmuhj1 = string;
	}

	/**
	 * 작성자: owner
	 * 작성일: 2006-06-15 오후 1:58:22
	 * 설  명: String
	 * 기  타:
	 */
	public String getCebwmseq1()
	{
		return cebwmseq1;
	}

	/**
	 * 작성자: owner
	 * 작성일: 2006-06-15 오후 1:58:22
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwmseq1(String string)
	{
		cebwmseq1 = string;
	}
	
	/**
	 * 작성자: owner
	 * 작성일: 2006-06-15 오후 1:58:22
	 * 설  명: void
	 * 기  타:
	 */
	public void setCebwmmlt(String string)
	{
		cebwmmlt = string;
	}
	
	public void setCebwmhmt(String string)
	{
		cebwmhmt = string;
	}
	
	public void setCebwmdmt(String string)
	{
		cebwmdmt = string;
	}

	public void setCebwmambt(String string)
	{
		cebwmambt = string; 
	}

	public void setCebwmvabt(String string)
	{
		cebwmvabt = string;
	}

	public void setCebwmtobt(String string)
	{
		cebwmtobt = string;
	}

	public void setCebwmbdgbn(String string)
	{
		cebwmbdgbn = string;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCebwmacmt() {
		return cebwmacmt;
	}

	public void setCebwmacmt(String cebwmacmt) {
		this.cebwmacmt = cebwmacmt;
	}

}
