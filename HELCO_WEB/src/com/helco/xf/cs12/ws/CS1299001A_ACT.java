package com.helco.xf.cs12.ws;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.helco.xf.cs12.ws.CS1201001B_Service;
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

public class CS1299001A_ACT extends AbstractAction {

	/**
	 * txt 파일 생성
	 * @param ctx
	 * @throws Exception
	 */
	public void getStdPrice(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		service.getStdPrice(ctx, ds_list, ds_cond );
	}
	public void getStdPrice2(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.getStdPrice2(ctx, ds_list);
	}
	public void getSujuRat(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		service.getSujuRat(ctx, ds_list, ds_cond);
	}

	public void getListSeg(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.getListSeg(ctx, ds_list);
	}

	public void queryCS4101(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		service.queryCS4101(ctx, ds_cond);
	}
	public void queryCS1209(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		service.queryCS1209(ctx, ds_cond);
	}
	public void queryCS1238(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset ds_list2 = ctx.getInputDataset("ds_list2");
		Dataset ds_list3 = ctx.getInputDataset("ds_list3");
		service.queryCS1238(ctx, ds_cond, ds_list, ds_list2, ds_list3);
	}
	public void getListSTO(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.getListSTO(ctx, ds_list);
	}
	public void getListSTO2(BusinessContext ctx) throws Exception {
		CS1299001A_Service service = (CS1299001A_Service)
		ServiceFactoryManager.getService("CS1299001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.getListSTO2(ctx, ds_list);
	}
}
