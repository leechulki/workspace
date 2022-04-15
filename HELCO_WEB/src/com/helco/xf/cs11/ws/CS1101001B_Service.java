package com.helco.xf.cs11.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1101001B_Service {
	
	/** 저장 */
	public void save(BusinessContext ctx, Dataset ds_list) throws Exception;
	
	/** 기안 */
	public void draft(BusinessContext ctx, Dataset ds_list) throws Exception;
	
	/** 결재 */
	public void saveDecide(BusinessContext ctx, Dataset ds_list, Dataset ds_decide) throws Exception;
	
	/** 기안과 결재 동시처리 */
	public void saveDraftAndDecide(BusinessContext ctx, Dataset ds_list, Dataset ds_decide) throws Exception;

	/** 발주삭제 */
	public void balDelete(BusinessContext ctx, Dataset ds_list) throws Exception;
}
