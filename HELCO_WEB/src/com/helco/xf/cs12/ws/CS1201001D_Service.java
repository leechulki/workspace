package com.helco.xf.cs12.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1201001D_Service {
	public void save(BusinessContext ctx, Dataset ds_list) throws Exception;

	public void save2(BusinessContext ctx, Dataset ds_list) throws Exception;
}
