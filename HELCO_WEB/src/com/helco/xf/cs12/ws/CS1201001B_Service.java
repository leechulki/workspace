package com.helco.xf.cs12.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1201001B_Service {

	public Dataset searchComboCst(Dataset ds) throws Exception ;
	
	public Dataset searchNamSpt(Dataset ds) throws Exception ;
	
	public void save(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_lists) throws Exception;
	
	public void delete(BusinessContext ctx, Dataset ds_list) throws Exception;

	public void save3_bak(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_cost) throws Exception;
	
	public void cancel(BusinessContext ctx, Dataset ds_list, Dataset ds_zcst630) throws Exception;
	
	public void cancelJ(BusinessContext ctx, Dataset ds_list, Dataset ds_zcst630) throws Exception;

	public void seungin_cancel(BusinessContext ctx, Dataset ds_list) throws Exception;

	public void save3(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_lists) throws Exception;

	public void saveCost(BusinessContext ctx, Dataset ds_list, Dataset ds_cost) throws Exception;

}
