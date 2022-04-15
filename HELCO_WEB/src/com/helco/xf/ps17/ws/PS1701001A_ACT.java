package com.helco.xf.ps17.ws;

import java.sql.ResultSet;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.ps02.ws.ZPSS007;
import com.helco.xf.ps02.ws.ZPSS008;
import com.helco.xf.ps02.ws.ZPSS009;
import com.helco.xf.ps11.ws.BAPI_WBS_LIST_TAB;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS1701001A_ACT extends AbstractAction 
{
	private static final long serialVersionUID = 5661911687794518235L;

	/**
	 * query : 특이사항 가져오기
	 * @param ctx
	 * @throws Exception
	 */
	public void run(BusinessContext ctx) throws Exception 
	{
		hdel.ps.batch.ProcPS17 prc17 = new hdel.ps.batch.ProcPS17();
		
		prc17.run();
		
	}

	
}
