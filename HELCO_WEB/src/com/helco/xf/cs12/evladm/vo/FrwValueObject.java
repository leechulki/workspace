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
	
    //목록 조회시 사용되는 항목들
    private String currPageNo ;     //현재페이지    
	 private String startPageNo ;     //시작페이지  
	 private String EndPageNo ;     //마지막페이지  
	 
	 private String regDate; 			// 등록일자
	 private String modDate; 			// 수정일자
	 
	 private String firstUserId; 			// 최초사용자
	 private String lastUserId; 			// 최종사용자
	 
    private int totalcount = 0;     		//전체 row 갯수 
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
