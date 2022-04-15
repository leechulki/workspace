package com.helco.xf.cs12.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1299001A_Service {

	public void getStdPrice(BusinessContext ctx, Dataset ds_list, Dataset ds_cond) throws Exception;
	public void getStdPrice2(BusinessContext ctx, Dataset ds_list) throws Exception;
	public void getSujuRat(BusinessContext ctx, Dataset ds_list, Dataset ds_cond) throws Exception;
	public void getListSeg(BusinessContext ctx, Dataset ds_list) throws Exception;
	public void queryCS4101(BusinessContext ctx, Dataset ds_cond) throws Exception;
	public void queryCS1209(BusinessContext ctx, Dataset ds_cond) throws Exception;
	public void queryCS1238(BusinessContext ctx, Dataset ds_cond, Dataset ds_list, Dataset ds_list2, Dataset ds_list3) throws Exception;
	public void getListSTO(BusinessContext ctx, Dataset ds_list) throws Exception;
	public void getListSTO2(BusinessContext ctx, Dataset ds_list) throws Exception;
}
