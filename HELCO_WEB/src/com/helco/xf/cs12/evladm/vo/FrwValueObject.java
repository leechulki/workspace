/*
 * Created on 2004. 10. 27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.helco.xf.cs12.evladm.vo;

import java.io.Serializable;

/**
 * @author artsci
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FrwValueObject implements Serializable {
	
    //��� ��ȸ�� ���Ǵ� �׸��
    private String currPageNo ;     //����������    
	 private String startPageNo ;     //����������  
	 private String EndPageNo ;     //������������  
	 
	 private String regDate; 			// �������
	 private String modDate; 			// ��������
	 
	 private String firstUserId; 			// ���ʻ����
	 private String lastUserId; 			// ���������
	 
    private int totalcount = 0;     		//��ü row ���� 
    private int jobType;
    
    private String gubun;
    

	/**
	 * @return
	 */
	public String getCurrPageNo()
	{
		return currPageNo;
	}

	/**
	 * @return
	 */
	public String getEndPageNo()
	{
		return EndPageNo;
	}

	/**
	 * @return
	 */
	public String getFirstUserId()
	{
		return firstUserId;
	}

	/**
	 * @return
	 */
	public int getJobType()
	{
		return jobType;
	}

	/**
	 * @return
	 */
	public String getLastUserId()
	{
		return lastUserId;
	}

	/**
	 * @return
	 */
	public String getModDate()
	{
		return modDate;
	}

	/**
	 * @return
	 */
	public String getRegDate()
	{
		return regDate;
	}

	/**
	 * @return
	 */
	public String getStartPageNo()
	{
		return startPageNo;
	}

	/**
	 * @return
	 */
	public int getTotalcount()
	{
		return totalcount;
	}

	/**
	 * @param string
	 */
	public void setCurrPageNo(String string)
	{
		currPageNo = string;
	}

	/**
	 * @param string
	 */
	public void setEndPageNo(String string)
	{
		EndPageNo = string;
	}

	/**
	 * @param string
	 */
	public void setFirstUserId(String string)
	{
		firstUserId = string;
	}

	/**
	 * @param i
	 */
	public void setJobType(int i)
	{
		jobType = i;
	}

	/**
	 * @param string
	 */
	public void setLastUserId(String string)
	{
		lastUserId = string;
	}

	/**
	 * @param string
	 */
	public void setModDate(String string)
	{
		modDate = string;
	}

	/**
	 * @param string
	 */
	public void setRegDate(String string)
	{
		regDate = string;
	}

	/**
	 * @param string
	 */
	public void setStartPageNo(String string)
	{
		startPageNo = string;
	}

	/**
	 * @param i
	 */
	public void setTotalcount(int i)
	{
		totalcount = i;
	}

   /**
    * @param i
    */
	public String getGubun()
	{
		return gubun;
	}

   /**
    * @param i
    */
	public void setGubun(String string)
	{
		gubun = string;
	}

}
