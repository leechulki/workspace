/**
 * �� �� �� : BizException.java
 * ��    �� : 
 * �� �� �� : jkkoo
 * �� �� �� : 2006-04-10 ���� 6:01:35
 * ���泻�� :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.cs12.evladm;

public class BizException extends Exception
{
	private int se_Num;
   
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-04-25
    * ��  ��: 
    * ��  Ÿ: 
    */
	public BizException()
	{
		super();
	}
   
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-04-25
    * ��  ��: 
    * ��  Ÿ:   
    */
	public BizException(String msg)
	{
		super(msg);
	}
   
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-04-25
    * ��  ��: 
    * ��  Ÿ:   
    */
	public BizException(String msg, int se_Num)
	{
		super(msg);
		this.se_Num = se_Num;
	}
}
