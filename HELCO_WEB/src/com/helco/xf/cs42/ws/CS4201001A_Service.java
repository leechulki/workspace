package com.helco.xf.cs42.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS4201001A_Service {

	public void create(BusinessContext ctx, Dataset ds_list) throws Exception;
	public void read(BusinessContext ctx, Dataset ds_list) throws Exception;
	public void getTaxinfo(BusinessContext ctx, Dataset ds_cond, Dataset ds_list) throws Exception;
	public void insertHeader(BusinessContext ctx, Dataset ds_list) throws Exception;

}
