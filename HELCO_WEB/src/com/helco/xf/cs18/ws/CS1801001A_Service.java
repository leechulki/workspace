package com.helco.xf.cs18.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1801001A_Service {

	public void save(BusinessContext ctx, Dataset ds_temp3) throws Exception;

	public void sendback(BusinessContext ctx, Dataset ds_temp3) throws Exception;
}
