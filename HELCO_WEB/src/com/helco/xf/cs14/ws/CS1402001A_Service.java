package com.helco.xf.cs14.ws;

import tit.biz.BusinessContext;
import com.tobesoft.platform.data.Dataset;

public interface CS1402001A_Service {

	public Dataset query(BusinessContext ctx, Dataset ds_cond) throws Exception;
	
	public void save(BusinessContext ctx, Dataset ds_list) throws Exception;
}
