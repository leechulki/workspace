package com.helco.xf.cs11.ws;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs11.ws.ZPSS000;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs14.ws.CS1402001A_Service;
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
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class CS1102001A_ACT extends AbstractAction {

	public static final String ZWEB_PS_SET_0000
		= "com.helco.xf.cs11.ws.ZWEB_PS_SET_0000";

	public static boolean flag = false;

	/**
	 * 저장
	 * @param ctx
	 * @throws Exception
	 */
	public void save2(BusinessContext ctx) throws Exception {
		CS1102001A_Service service = (CS1102001A_Service)
		ServiceFactoryManager.getService("CS1102001A");
		Dataset ds_list = ctx.getInputDataset("ds_list2");
		service.save2(ctx, ds_list);
	}

	/**
	 * 승인
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsList = ctx.getInputDataset("ds_list4");
		Dataset dsError = null;
		SapFunction stub = null;

		// 입력 파라메터 처리 
		if ( CallSAP.isJCO() ) {
			dsError = new Dataset("ds_error");
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PS_SET_0000, new Object[]{dsList});
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_PS_SET_0000, new Object[]{dsList}, false);		 // 기존  SAP 연결
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_PS_SET_0000, new Object[]{dsList}, false);  // EAI 연결

			

			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			ZPSS000[] list = (ZPSS000[])
					CallSAP.moveDs2Obj(dsList, ZPSS000.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// 저장 처리 
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PS_SET_0000, new Object[]{msgList, list});
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_PS_SET_0000, new Object[]{msgList, list}, false);		 // 기존  SAP 연결
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_PS_SET_0000, new Object[]{msgList, list}, false);  // EAI 연결
			
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}

		CS1102001A_Service service = (CS1102001A_Service)
		ServiceFactoryManager.getService("CS1102001A");
		Dataset ds_list = ctx.getInputDataset("ds_list3");

		if(dsError.getRowCount() == 0) {
			service.save(ctx, ds_list);
		}

		ctx.addOutputDataset(dsError);
	}

	/**
	 * 반송
	 * @param ctx
	 * @throws Exception
	 */
	public void sendback(BusinessContext ctx) throws Exception {
		CS1102001A_Service service = (CS1102001A_Service)
			ServiceFactoryManager.getService("CS1102001A");
		Dataset ds_list = ctx.getInputDataset("ds_list3");
		service.sendback(ctx, ds_list);
	}
}
