package com.helco.xf.ps04.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface PS0402001A_Service {

	public void sendMsg(BusinessContext ctx, Dataset ds_list) throws Exception;
}
