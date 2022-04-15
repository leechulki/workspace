package com.helco.xf.cs42.ws;

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

public class CS4201001A_ACT extends AbstractAction {

	/**
	 * txt 파일 생성
	 * @param ctx
	 * @throws Exception
	 */
	public void create(BusinessContext ctx) throws Exception {
		CS4201001A_Service service = (CS4201001A_Service)
		ServiceFactoryManager.getService("CS4201001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.create(ctx, ds_list);
	}

	public void read(BusinessContext ctx) throws Exception {
		CS4201001A_Service service = (CS4201001A_Service)
		ServiceFactoryManager.getService("CS4201001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.read(ctx, ds_list);
	}

	public void getTaxinfo(BusinessContext ctx) throws Exception {
		CS4201001A_Service service = (CS4201001A_Service)
		ServiceFactoryManager.getService("CS4201001A");
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getInputDataset("ds_list");

		service.getTaxinfo(ctx, ds_cond, ds_list);
	}
	public void insertHeader(BusinessContext ctx) throws Exception {
		CS4201001A_Service service = (CS4201001A_Service)
		ServiceFactoryManager.getService("CS4201001A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");

		service.insertHeader(ctx, ds_list);
	}
}
