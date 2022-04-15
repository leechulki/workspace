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

public class CS1201001D_ACT extends AbstractAction {

	/**
	 * ¿˙¿Â
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		CS1201001D_Service service = (CS1201001D_Service)
			ServiceFactoryManager.getService("CS1201001D");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.save(ctx, ds_list);
	}

	public void save2(BusinessContext ctx) throws Exception {
		CS1201001D_Service service = (CS1201001D_Service)
			ServiceFactoryManager.getService("CS1201001D");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.save2(ctx, ds_list);
	}
}
