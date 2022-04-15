package com.helco.xf.cs96.ws;

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

public class CS9614001E_ACT extends AbstractAction {

	public void selectList(BusinessContext ctx) throws Exception {	
		CS9614001E_Service service = (CS9614001E_Service)
		ServiceFactoryManager.getService("CS9614001E");

		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset rtn = service.selectList(ctx, ds_cond);
		rtn.setId("ds_list");
		ctx.addOutputDataset(rtn);
	}
}
