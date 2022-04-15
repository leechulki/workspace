/**
 * 파 일 명 : ComMethodVo.java
 * 설    명 : 
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-04-28 오후 1:32:46
 * 변경내역 :
 */ 
package com.helco.xf.cs12.evladm.vo;

public class ComMethodVo extends FrwValueObject
{
   private  String   dataId;     // 1.자료ID
   private  String   jobGubun;   // 2.작업구분
   private  String   upn;        // 3.통합프로젝트번호
   private  String   cst;        // 4.거래처
   private  String   pjt;        // 5.원프로젝트번호
   private  String   hgb;        // 6.호기구분
   private  String   hno;        // 7.호기번호
   private  String   seq;        // 8.순번
   private  String   gbn;        // 9.업무구분-일시중지/복구 구분
   private  String   wym;        // 10.작업년월-일시중지/복구승인
   private  String   isr;        // 11.순번 - 일시중지/복구승
   private  String   igm;        // 12.이관예정년
   private  String   stopDate;   // 13.중지일자 - 일시중지복구에서만 사용.
   private  String   mandt;      // 14.클라이언트
   private  String   rto;
   private  String   userid;
   private  String   gno;
   private  String   jhd;
   private  String   yym;
   private  String   maxseq;
   private  String   sla;
   private  String   gnd;
   
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getCst()
	{
		return cst;
	}
	
	public String getUserId()
	{
		return userid;
	}
	
	public String getGno()
	{
		return gno;
	}
	
	public String getJhd()
	{
		return jhd;
	}
	
	public String getYym()
	{
		return yym;
	}
	
	public String getRto()
	{
		return rto;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getDataId()
	{
		return dataId;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getHgb()
	{
		return hgb;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getHno()
	{
		return hno;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getJobGubun()
	{
		return jobGubun;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getPjt()
	{
		return pjt;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getSeq()
	{
		return seq;
	}
	
	public String getMaxSeq()
	{
		return maxseq;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: String
	 * 기  타:
	 */
	public String getUpn()
	{
		return upn;
	}

	public String getSla()
	{
		return sla;
	}
	
	public String getGnd()
	{
		return gnd;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setCst(String string)
	{
		cst = string;
	}
	
	public void setUserId(String string)
	{
		userid = string;
	}
	
	public void setGno(String string)
	{
		gno = string;
	}
	
	public void setJhd(String string)
	{
		jhd = string;
	}
	
	public void setYym(String string)
	{
		yym = string;
	}
	
	public void setRto(String string)
	{
		rto = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setDataId(String string)
	{
		dataId = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setHgb(String string)
	{
		hgb = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setHno(String string)
	{
		hno = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setJobGubun(String string)
	{
		jobGubun = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setPjt(String string)
	{
		pjt = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setSeq(String string)
	{
		seq = string;
	}
	
	public void setMaxSeq(String string)
	{
		maxseq = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:54:25
	 * 설  명: void
	 * 기  타:
	 */
	public void setUpn(String string)
	{
		upn = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 4:34:19
	 * 설  명: String
	 * 기  타:
	 */
	public String getGbn()
	{
		return gbn;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 4:34:20
	 * 설  명: String
	 * 기  타:
	 */
	public String getWym()
	{
		return wym;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 4:34:20
	 * 설  명: void
	 * 기  타:
	 */
	public void setGbn(String string)
	{
		gbn = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 4:34:20
	 * 설  명: void
	 * 기  타:
	 */
	public void setWym(String string)
	{
		wym = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 4:37:01
	 * 설  명: String
	 * 기  타:
	 */
	public String getIsr()
	{
		return isr;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 4:37:01
	 * 설  명: void
	 * 기  타:
	 */
	public void setIsr(String string)
	{
		isr = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 5:11:04
	 * 설  명: String
	 * 기  타:
	 */
	public String getIgm()
	{
		return igm;
	}

	public void setSla(String string)
	{
		sla = string;
	}
	
	public void setGnd(String string)
	{
		gnd = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 5:11:04
	 * 설  명: void
	 * 기  타:
	 */
	public void setIgm(String string)
	{
		igm = string;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 6:18:49
	 * 설  명: String
	 * 기  타:
	 */
	public String getStopDate()
	{
		return stopDate;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-12 오후 6:18:49
	 * 설  명: void
	 * 기  타:
	 */
	public void setStopDate(String string)
	{
		stopDate = string;
	}

	/**
	 * 작성자: jhlee
	 * 작성일: 2008-05-02 오전 9:48:49
	 * 설  명: String
	 * 기  타:
	 */
	public String getMandt()
	{
		return mandt;
	}

	/**
	 * 작성자: jhlee
	 * 작성일: 2008-05-02 오전 9:48:49
	 * 설  명: String
	 * 기  타:
	 */
	public void setMandt(String string)
	{
		mandt = string;
	}
}
