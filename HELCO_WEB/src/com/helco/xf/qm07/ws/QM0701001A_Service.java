package com.helco.xf.qm07.ws;

import tit.biz.BusinessContext;
import com.tobesoft.platform.data.Dataset;

public interface QM0701001A_Service {

	/** �������� **/
	public void save(BusinessContext ctx, Dataset ds_list) throws Exception;
	
	public void matnrProc(BusinessContext ctx, Dataset ds_cond) throws Exception;
	
	public void matnrProcCondAdd(BusinessContext ctx, Dataset ds_cond) throws Exception;

	public void matnrProcCondAdd3(BusinessContext ctx, Dataset ds_cond) throws Exception;

	public void cancelProcess(BusinessContext ctx, Dataset ds_cond) throws Exception;
	
	public void workareaProcessAdd(BusinessContext ctx, Dataset ds_list) throws Exception;
	
	/** ������� **/
	public void save2(BusinessContext ctx, Dataset ds_list) throws Exception;
	
	public void matnrProc2(BusinessContext ctx, Dataset ds_cond) throws Exception;
	
	public void matnrProcCondAdd2(BusinessContext ctx, Dataset ds_cond) throws Exception;

	public void cancelProcess2(BusinessContext ctx, Dataset ds_cond) throws Exception;
	
	
	
}
