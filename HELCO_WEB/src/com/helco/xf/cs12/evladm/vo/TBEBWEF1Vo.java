/**
 * �� �� �� : TBEBWEF1Vo.java
 * ��    �� : ���󺸼� �������� vo 
 * �� �� �� : shhwang
 * �� �� �� : 2006-03-22 ����  9:43:09
 * ���泻�� :
 * 0404 Į�� 4�� ���� 
 */ 
package com.helco.xf.cs12.evladm.vo;

public class TBEBWEF1Vo extends FrwValueObject
{
   // TBEBWEF1 �÷�����
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
	private  String   	cebweseq	;//�߰� 0401
	private  String   	cebwegnd	;//�߰� 0401
	private  String   	cebwebhd1	;//���� �ܿ��Ⱓ �μ�Ʈ�� ��������    
	private  String   	gkd	;//��౸��
   private  String		prePst; // ���� ����
   private  String		mandt; // Ŭ���̾�Ʈ 
   private  String   	gno	;
   private  String   	sla	;
   
   // �д��������� ���� ����
   private	String		cebwebdgbn;
   
   //��ȸ�� ���� ���� �߰�
   private  String   	cevwevkgrp	 ;   //�����ڵ� 
   private  String   	cevwevkgrpco ;   //co �����ڵ�
   
   private  String   	cevwezmusaocnt ;   //������� ������
   private  String   	cevwezmusaocnt2 ;  //�����Ϲ� ������
   private  String   	cevwezmusaucnt ;   //������� ��밳����
   private  String   	cevwezmusaucnt2 ;  //�����Ϲ� �Ϲݰ�����
   private  String   	cevwezmusosum ;    //�Ѱ����� 
   private  String   	cevwezmusogap ;    //�Ѱ����� - �� ��밳����
   
   
	/**
	 * �ۼ���: jhlee
	 * �ۼ���: 2008-05-02 ���� 4:09:25
	 * ��  ��: void
	 * ��  Ÿ:
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
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebweabg()
	{
		return cebweabg;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebweagb()
	{
		return cebweagb;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebweapp()
	{
		return cebweapp;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebweara()
	{
		return cebweara;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebcc()
	{
		return cebwebcc;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebct()
	{
		return cebwebct;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebgt()
	{
		return cebwebgt;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebhd()
	{
		return cebwebhd;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebjj()
	{
		return cebwebjj;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebjt()
	{
		return cebwebjt;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebmt()
	{
		return cebwebmt;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebpm()
	{
		return cebwebpm;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:24
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebpm_f()
	{
		return cebwebpm_f;
	}
	
	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebst()
	{
		return cebwebst;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebsu()
	{
		return cebwebsu;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebyt()
	{
		return cebwebyt;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwecbs()
	{
		return cebwecbs;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwefdt()
	{
		return cebwefdt;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwegbn()
	{
		return cebwegbn;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwegnd()
	{
		return cebwegnd;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwehgb()
	{
		return cebwehgb;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwehno()
	{
		return cebwehno;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwehty()
	{
		return cebwehty;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwehyn()
	{
		return cebwehyn;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwejuc()
	{
		return cebwejuc;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwejuj()
	{
		return cebwejuj;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwembg()
	{
		return cebwembg;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwemg1()
	{
		return cebwemg1;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwemno()
	{
		return cebwemno;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwemut()
	{
		return cebwemut;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwepjt()
	{
		return cebwepjt;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwepst()
	{
		return cebwepst;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwermk()
	{
		return cebwermk;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebweseq()
	{
		return cebweseq;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwetyp()
	{
		return cebwetyp;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: String
	 * ��  Ÿ:
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
	 * �ۼ���: jhlee
	 * �ۼ���: 2008-05-02 ���� 4:09:25
	 * ��  ��: void
	 * ��  Ÿ:
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
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebweabg(String string)
	{
		cebweabg = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebweagb(String string)
	{
		cebweagb = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebweapp(String string)
	{
		cebweapp = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebweara(String string)
	{
		cebweara = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebcc(String string)
	{
		cebwebcc = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebct(String string)
	{
		cebwebct = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebgt(String string)
	{
		cebwebgt = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebhd(String string)
	{
		cebwebhd = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebjj(String string)
	{
		cebwebjj = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebjt(String string)
	{
		cebwebjt = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebmt(String string)
	{
		cebwebmt = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebpm(String string)
	{
		cebwebpm = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebpm_f(String string)
	{
		cebwebpm_f = string;
	}
	
	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebst(String string)
	{
		cebwebst = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebsu(String string)
	{
		cebwebsu = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebyt(String string)
	{
		cebwebyt = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwecbs(String string)
	{
		cebwecbs = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwefdt(String string)
	{
		cebwefdt = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwegbn(String string)
	{
		cebwegbn = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwegnd(String string)
	{
		cebwegnd = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwehgb(String string)
	{
		cebwehgb = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwehno(String string)
	{
		cebwehno = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwehty(String string)
	{
		cebwehty = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwehyn(String string)
	{
		cebwehyn = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwejuc(String string)
	{
		cebwejuc = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwejuj(String string)
	{
		cebwejuj = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwembg(String string)
	{
		cebwembg = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwemg1(String string)
	{
		cebwemg1 = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwemno(String string)
	{
		cebwemno = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwemut(String string)
	{
		cebwemut = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwepjt(String string)
	{
		cebwepjt = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwepst(String string)
	{
		cebwepst = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwermk(String string)
	{
		cebwermk = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebweseq(String string)
	{
		cebweseq = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwetyp(String string)
	{
		cebwetyp = string;
	}

	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-04-28 ���� 5:05:25
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebweycj(String string)
	{
		cebweycj = string;
	}

	/**
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-06-19 ���� 5:49:00
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getCebwebhd1()
	{
		return cebwebhd1;
	}

	/**
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-06-19 ���� 5:49:00
	 * ��  ��: void
	 * ��  Ÿ:
	 */
	public void setCebwebhd1(String string)
	{
		cebwebhd1 = string;
	}

	/**
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-06-20 ���� 1:22:14
	 * ��  ��: String
	 * ��  Ÿ:
	 */
	public String getGkd()
	{
		return gkd;
	}

	/**
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-06-20 ���� 1:22:14
	 * ��  ��: void
	 * ��  Ÿ:
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
