package com.helco.xf.cs11.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1104001A_Service {

	public void save(BusinessContext ctx, Dataset ds_list) throws Exception;

	public void sendback(BusinessContext ctx, Dataset ds_list) throws Exception;
}
