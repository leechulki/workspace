package com.helco.xf.pcc03.ws;

import com.helco.xf.comm.Utillity;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class PCC0301001A_ACT extends AbstractAction {

	static Logger logger;
	String mandt = "183";

	/**
	 * Á¶È¸ 
	 * @param ctx
	 * @throws Exception
	 */
	public void execute(BusinessContext ctx) throws Exception {	
		PCC0301001A_Service service = (PCC0301001A_Service)
		ServiceFactoryManager.getService("PCC0301001A");

		Dataset ds = ctx.getInputDataset("ds_cond");
		Dataset rtn = service.query(ctx, ds);
		rtn.setId("ds_list");
		ctx.addOutputDataset(rtn);
	}
}
