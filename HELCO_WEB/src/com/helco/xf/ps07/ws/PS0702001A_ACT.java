package com.helco.xf.ps07.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.ps02.ws.ZPSS004;
import com.helco.xf.ps02.ws.ZPSS008;
import com.tobesoft.platform.data.Dataset;

public class PS0702001A_ACT extends AbstractAction {
	
	/**
	 * 조회 
	 * @param ctx
	 * @throws Exception
	 */
	public void search(BusinessContext ctx) throws Exception {	
		
	}

	public void searchComboCst(BusinessContext ctx) throws Exception {	
		
	}
	
	/**
	 * 저장
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );

		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.ps07.ws.ZWEB_PS_SET_0004", 
					new Object[]{ds_list});
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리
			ZPSS018[] list = (ZPSS018[])
					CallSAP.moveDs2Obj(ds_list, ZPSS018.class, "CHECK");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};

			// 저장 처리
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.ps07.ws.ZWEB_PS_SET_0004", 
					new Object[]{msgList, list});

			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
		}

		if ( dsError.getRowCount() == 0 ) {
			// 조회 호출
			System.out.println("ok");
			// query(ctx);
		}

		ctx.addOutputDataset(dsError);
	}
	
	/**
	 * 저장
	 * @param ctx
	 * @throws Exception
	 */
	public void save2(BusinessContext ctx) throws Exception {
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );
		
		
		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.ps07.ws.ZWEB_PS_SET_0011", 
					new Object[]{ds_list},false);
			//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps07.ws.ZWEB_PS_SET_0011", new Object[]{ds_list}, false);
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리
			ZPSS031[] list = (ZPSS031[])
					CallSAP.moveDs2Obj(ds_list, ZPSS031.class, "CHECK");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};

			// 저장 처리
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.ps07.ws.ZWEB_PS_SET_0011", 
					new Object[]{msgList, list},false);

			//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps07.ws.ZWEB_PS_SET_0011", new Object[]{msgList, list}, false);
			
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
		}

		if ( dsError.getRowCount() == 0 ) {
			// 조회 호출
			System.out.println("ok");
			// query(ctx);
		}

		ctx.addOutputDataset(dsError);
	}
	
	/**
	 * 수정 
	 * @param ctx
	 * @throws Exception
	 */
	public void update(BusinessContext ctx) throws Exception {
		SapFunction stub = null;
		Dataset dsMaker = null;
		Dataset ds_list = ctx.getInputDataset("ds_list");
		ZPSS018[] list = new ZPSS018[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		
		for(int i=0; i<ds_list.getRowCount(); i++){
			if(ds_list.getColumn(i, "CHECK").equals("1")){
				Object obj[] = new Object[]{
						DatasetUtility.getString(ds_list, i, "PSPID","")
						, DatasetUtility.getString(ds_list, i, "POSID","")
						, DatasetUtility.getString(ds_list, i, "ZZJUNGD","")
						, DatasetUtility.getString(ds_list, i, "MODE1","")
						, listMsg
						, list
				};
				
				//  SAP Call
				stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps07.ws.ZWEB_PS_SET_0001", obj);
			}
		}
		
		// Dataset make
		dsMaker = CallSAP.isJCO() ? new Dataset() : ZPSS018.getDataset();
		// 필요 컬럼 - 추가
		CallSAP.makeDsCol(dsMaker, "FLAG", 1);
		CallSAP.makeDsCol(dsMaker, "CHK", 1);
			
	}

	/**
	 * 삭제 
	 * @param ctx
	 * @throws Exception
	 */
	public void delete(BusinessContext ctx) throws Exception {
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );

		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.ps07.ws.ZWEB_PS_SET_0004", 
					new Object[]{ds_list});
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리
			ZPSS018[] list = (ZPSS018[])
					CallSAP.moveDs2Obj(ds_list, ZPSS018.class, "CHECK");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// 저장 처리
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.ps07.ws.ZWEB_PS_SET_0004", 
					new Object[]{msgList, list});

			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
		}

		if ( dsError.getRowCount() == 0 ) {
			System.out.println("ok"); 
		}

		ctx.addOutputDataset(dsError);
	}
	
	/**
	 * 삭제 
	 * @param ctx
	 * @throws Exception
	 */
	public void delete2(BusinessContext ctx) throws Exception {
		Dataset ds_list = ctx.getInputDataset("ds_list");
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );

		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.ps07.ws.ZWEB_PS_SET_0011", 
					new Object[]{ds_list},false);
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리
			ZPSS031[] list = (ZPSS031[])
					CallSAP.moveDs2Obj(ds_list, ZPSS031.class, "CHECK");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// 저장 처리
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),
					"com.helco.xf.ps07.ws.ZWEB_PS_SET_0011", 
					new Object[]{msgList, list},false);

			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
		}

		if ( dsError.getRowCount() == 0 ) {
			System.out.println("ok"); 
		}

		ctx.addOutputDataset(dsError);
	}
}
