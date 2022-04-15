package com.helco.xf.ps13.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface PS1301005A_Service {

	public void approve(BusinessContext ctx, Dataset ds_list) throws Exception;
	public void sendback(BusinessContext ctx, Dataset ds_list) throws Exception;
}
