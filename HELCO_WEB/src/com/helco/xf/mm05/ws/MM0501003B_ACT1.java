package com.helco.xf.mm05.ws;

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

public class MM0501003B_ACT1 extends AbstractAction {

	/**
	 * ���� 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		MM0501003B_Service service = (MM0501003B_Service)
		ServiceFactoryManager.getService("MM0501003B");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");


		service.save(ctx, ds_list);
	}
	
	public void save2(BusinessContext ctx) throws Exception {
		MM0501003B_Service service = (MM0501003B_Service)
		ServiceFactoryManager.getService("MM0501003B");
		
		Dataset ds_list = ctx.getInputDataset("ds_list");


		service.save2(ctx, ds_list);
	}
}
