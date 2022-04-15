package com.helco.xf.bs05.ws;

import java.math.BigDecimal;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.bs05.ws.ZCABS004;
import com.helco.xf.bs05.ws.ZCABS006;
import com.helco.xf.bs05.ws.ZCABS005;
import com.helco.xf.bs05.ws.ZCABT002;
import com.helco.xf.bs05.ws.ZCABS007;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class BS0501002A_ACT extends AbstractAction {
	public static final String ZWEB_CO3_ESTIMATED_COST
		= "com.helco.xf.bs05.ws.ZWEB_CO3_ESTIMATED_COST";
	/**
	 * 원가 계산 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsIn = ctx.getInputDataset("ds_in8");
		Dataset dsList = ctx.getInputDataset("ds_list8");
		SapFunction stub = null;

		// 입력 파라메터 처리 
		ZCABS004[] list2 = new ZCABS004[]{};
		ZCABS006[] list3 = new ZCABS006[]{};
		ZCABS005[] list4 = new ZCABS005[]{};
		ZCABT002[] list5 = new ZCABT002[]{};
		ZCABT002[] list6 = new ZCABT002[]{};
		ZCABS007[] list7 = new ZCABS007[]{};
		Dataset dsError = null;

		list2 = (ZCABS004[])CallSAP.moveDs2Obj(dsList, ZCABS004.class, "");

		Object obj[] = new Object[]{
				list3
				, list4
				, list5
				, list6
				, DatasetUtility.getString(dsIn, "DEP")
				, DatasetUtility.getString(dsIn, "GBN")
				, DatasetUtility.getString(dsIn, "ISR")
				, DatasetUtility.getString(dsIn, "RDT")
				, DatasetUtility.getString(dsIn, "SEQ")
				, DatasetUtility.getString(dsIn, "SIR")
				, list2
				, list7
		};

		// 저장 처리 
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CO3_ESTIMATED_COST, obj);
		
		ctx.addOutputDataset(dsError);
	}
}
