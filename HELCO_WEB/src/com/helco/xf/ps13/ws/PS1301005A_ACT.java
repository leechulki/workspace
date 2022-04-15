package com.helco.xf.ps13.ws;

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
import tit.util.DatasetUtility;

public class PS1301005A_ACT extends AbstractAction {

	/**
	 * ½ÂÀÎ 
	 * @param ctx
	 * @throws Exception
	 */
	public void appr(BusinessContext ctx) throws Exception {
		PS1301005A_Service service = (PS1301005A_Service)
		ServiceFactoryManager.getService("PS1301005A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");

		service.approve(ctx, ds_list);
	}
	/**
	 * ¹Ý¼Û 
	 * @param ctx
	 * @throws Exception
	 */
	public void back(BusinessContext ctx) throws Exception {
		PS1301005A_Service service = (PS1301005A_Service)
		ServiceFactoryManager.getService("PS1301005A");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");

		service.sendback(ctx, ds_list);
	}
	
}
