package com.helco.xf.mm05.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface MM0501003B_Service {

	public void save(BusinessContext ctx, Dataset ds_list) throws Exception;
	
	public void save2(BusinessContext ctx, Dataset ds_list) throws Exception;
}
