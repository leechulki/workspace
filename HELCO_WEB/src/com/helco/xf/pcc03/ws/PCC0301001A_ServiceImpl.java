package com.helco.xf.pcc03.ws;

import com.helco.xf.comm.Utillity;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.TitUtility;

public class PCC0301001A_ServiceImpl extends AbstractBusinessService implements
		PCC0301001A_Service {

	public Dataset query(BusinessContext ctx, Dataset ds) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		String db_con = Utillity.getSapJndi(dsCond.getColumnAsString(0, "MANDT"));

		// 미수채권 select(zsdt0040)
		DatasetSqlRequest zsdt0040s = SqlMapManagerUtility.makeSqlRequestForDataset("pcc03:PCC0301001A_S1");
		zsdt0040s.addParamObject("ds_cond", dsCond);

		zsdt0040s.addParamObject("GV_MANDT", ctx.getInputVariable("GV_MANDT"));
		zsdt0040s.addParamObject("GV_USER_ID", ctx.getInputVariable("GV_USER_ID"));
		zsdt0040s.addParamObject("GV_LGORT", ctx.getInputVariable("GV_LGORT"));
		zsdt0040s.addParamObject("GV_AREA_CODE", ctx.getInputVariable("GV_AREA_CODE"));

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

//		zsdt0040s.setRowIndex(0);
		Dataset dsRtn0040 = (Dataset)executor.query(zsdt0040s).getResultObject();

		return dsRtn0040;
	}
}
