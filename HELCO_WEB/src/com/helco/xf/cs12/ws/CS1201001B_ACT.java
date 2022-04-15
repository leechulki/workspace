package com.helco.xf.cs12.ws;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;

public class CS1201001B_ACT extends AbstractAction {

	public void searchComboCst(BusinessContext ctx) throws Exception {	
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
	
		Dataset ds = ctx.getInputDataset("ds_cond");
		Dataset rtn = service.searchComboCst(ds);
		rtn.setId("ds_list");
		ctx.addOutputDataset(rtn);
	}
	
	public void searchNamSpt(BusinessContext ctx) throws Exception {	
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
	
		Dataset ds = ctx.getInputDataset("ds_cond");
		Dataset rtn = service.searchNamSpt(ds);
		rtn.setId("ds_list");
		ctx.addOutputDataset(rtn);
	}

	/**
	 * 저장 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
		
		Dataset ds_gbn = ctx.getInputDataset("ds_gbn");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_lists = ctx.getInputDataset("ds_lists");
		service.save(ctx, ds_gbn, ds_list, ds_lists);
	}

	/**
	 * 삭제 
	 * @param ctx
	 * @throws Exception
	 */
	public void delete(BusinessContext ctx) throws Exception {
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.delete(ctx, ds_list);
	}
	
	/**
	 * 승인 
	 * @param ctx
	 * @throws Exception
	 */
	public void save3_bak(BusinessContext ctx) throws Exception {
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
		
		Dataset ds_gbn = ctx.getInputDataset("ds_gbn");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_cost = ctx.getInputDataset("ds_cost");
		service.save3_bak(ctx, ds_gbn, ds_list, ds_cost);
	}
	
	/**
	 * 중도해지 
	 * @param ctx
	 * @throws Exception
	 */
	public void cancel(BusinessContext ctx) throws Exception {
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_zcst630 = ctx.getInputDataset("ds_zcst630");
		service.cancel(ctx, ds_list,ds_zcst630);
	}
	public void cancelJ(BusinessContext ctx) throws Exception {
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_zcst630 = ctx.getInputDataset("ds_zcst630");
		service.cancelJ(ctx, ds_list,ds_zcst630);
	}

	/**
	 * 승인취소 
	 * @param ctx
	 * @throws Exception
	 */
	public void seungin_cancel(BusinessContext ctx) throws Exception {
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.seungin_cancel(ctx, ds_list);
	}

	/**
	 * 승인 New
	 * @param ctx
	 * @throws Exception
	 */
	public void save3(BusinessContext ctx) throws Exception {
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
		
		Dataset ds_gbn = ctx.getInputDataset("ds_gbn");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_lists = ctx.getInputDataset("ds_lists");
		service.save3(ctx, ds_gbn, ds_list, ds_lists);
	}
	
	/**
	 * 시행율저장 
	 * @param ctx
	 * @throws Exception
	 */
	public void saveCost(BusinessContext ctx) throws Exception {
		CS1201001B_Service service = (CS1201001B_Service)
		ServiceFactoryManager.getService("CS1201001B");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_cost = ctx.getInputDataset("ds_cost");
		service.saveCost(ctx, ds_list, ds_cost);
	}	
}
