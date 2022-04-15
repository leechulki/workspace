package com.helco.xf.cs18.ws;

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

public class CS1801001A_ACT extends AbstractAction {

	/**
	 * ½ÂÀÎ
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		CS1801001A_Service service = (CS1801001A_Service)
			ServiceFactoryManager.getService("CS1801001A");
		Dataset ds_temp3 = ctx.getInputDataset("ds_temp3");
		service.save(ctx, ds_temp3);
	}

	/**
	 * ¹Ý¼Û
	 * @param ctx
	 * @throws Exception
	 */
	public void sendback(BusinessContext ctx) throws Exception {
		CS1801001A_Service service = (CS1801001A_Service)
			ServiceFactoryManager.getService("CS1801001A");
		Dataset ds_temp3 = ctx.getInputDataset("ds_temp3");
		service.sendback(ctx, ds_temp3);
	}
}
