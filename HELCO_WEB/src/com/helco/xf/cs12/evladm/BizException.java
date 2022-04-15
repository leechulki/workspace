/**
 * 파 일 명 : BizException.java
 * 설    명 : 
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-04-10 오후 6:01:35
 * 변경내역 :
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
    * 작성자: jkkoo
    * 작성일: 2006-04-25
    * 설  명: 
    * 기  타: 
    */
	public BizException()
	{
		super();
	}
   
   /**
    * 작성자: jkkoo
    * 작성일: 2006-04-25
    * 설  명: 
    * 기  타:   
    */
	public BizException(String msg)
	{
		super(msg);
	}
   
   /**
    * 작성자: jkkoo
    * 작성일: 2006-04-25
    * 설  명: 
    * 기  타:   
    */
	public BizException(String msg, int se_Num)
	{
		super(msg);
		this.se_Num = se_Num;
	}
}
