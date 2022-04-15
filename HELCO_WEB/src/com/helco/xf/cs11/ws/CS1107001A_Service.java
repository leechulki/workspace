package com.helco.xf.cs11.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1107001A_Service {
	
	public void save(BusinessContext ctx) throws Exception;
	public void approvalProcess(BusinessContext ctx) throws Exception;
	public void deleteData(BusinessContext ctx) throws Exception;

}
