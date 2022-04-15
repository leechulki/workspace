package com.helco.xf.cs18.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1801002A_Service {

	public void save(BusinessContext ctx, Dataset ds_list) throws Exception;

	public void sendback(BusinessContext ctx, Dataset ds_list) throws Exception;
}
