package com.helco.xf.ps02.ws;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS0201001M_ACT extends AbstractAction {
	
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;
		if(DatasetUtility.getString(dsCond, "TAB_GBN", "").equals("1")){
			ZPSS007[] list = new ZPSS007[]{};
			ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
			Object obj[] = new Object[]{
					CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DATEF",""))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DATET",""))
					, DatasetUtility.getString(dsCond, "MODE","")
					, DatasetUtility.getString(dsCond, "PSPID","")
					, DatasetUtility.getString(dsCond, "ZZACTSS","")
					, DatasetUtility.getString(dsCond, "ZZLIFNR","")
					, DatasetUtility.getString(dsCond, "ZZPMNUM","")
					, listMsg
					, list
			};

			// SAP Call
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0003", obj);
			// Dataset make
			dsList = CallSAP.isJCO() ? new Dataset() : ZPSS007.getDataset();
		} else if(DatasetUtility.getString(dsCond, "TAB_GBN", "").equals("2")){
			ZPSS028[] list = new ZPSS028[]{};
			ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
			Object obj[] = new Object[]{
					 DatasetUtility.getString(dsCond, "PSPID","")
					, listMsg
					, list
			};

			// SAP Call
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZMBL_PS_GET_001", obj);
			// Dataset make
			dsList = CallSAP.isJCO() ? new Dataset() : ZPSS028.getDataset();
			// 필요 컬럼 - 추가
			CallSAP.makeDsCol(dsList, "FLAG", 1);
			CallSAP.makeDsCol(dsList, "CHK", 1);
			CallSAP.makeDsCol(dsList, "SDATE", 1);
		} else if(DatasetUtility.getString(dsCond, "TAB_GBN", "").equals("3")){
			ZPSS029[] list = new ZPSS029[]{};
			ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
			Object obj[] = new Object[]{
					DatasetUtility.getString(dsCond, "PSPID","")
					, listMsg
					, list
			};

			// SAP Call
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZMBL_PS_GET_002", obj);
			// Dataset make
			dsList = CallSAP.isJCO() ? new Dataset() : ZPSS029.getDataset();
			// 필요 컬럼 - 추가
			CallSAP.makeDsCol(dsList, "FLAG", 1);
			CallSAP.makeDsCol(dsList, "CHK", 1);
			CallSAP.makeDsCol(dsList, "GUBUN", 1);
			CallSAP.makeDsCol(dsList, "CLOSI", 1);
			CallSAP.makeDsCol(dsList, "BLOCK", 1);
			CallSAP.makeDsCol(dsList, "CHASU", 1);
			CallSAP.makeDsCol(dsList, "QUART", 1);
			CallSAP.makeDsCol(dsList, "REMOV", 1);
			CallSAP.makeDsCol(dsList, "CHANG", 1);
			CallSAP.makeDsCol(dsList, "REASO", 1);
			CallSAP.makeDsCol(dsList, "TXT01", 1);
			CallSAP.makeDsCol(dsList, "REAQU", 1);
			CallSAP.makeDsCol(dsList, "CNAME", 1);
//			CallSAP.makeDsCol(dsList, "CDATE", 1);
			CallSAP.makeDsCol(dsList, "CHASU1", 1);
			CallSAP.makeDsCol(dsList, "REMOV1", 1);
			CallSAP.makeDsCol(dsList, "CHANG1", 1);
			CallSAP.makeDsCol(dsList, "REASO1", 1);
			CallSAP.makeDsCol(dsList, "TXT011", 1);
			CallSAP.makeDsCol(dsList, "REAQU1", 1);
			CallSAP.makeDsCol(dsList, "CNAME1", 1);
			CallSAP.makeDsCol(dsList, "CDATE1", 1);
			CallSAP.makeDsCol(dsList, "SDATE", 1);
		}
		dsList.setId("ds_list");
		// 필요 컬럼 - 추가
		//CallSAP.makeDsCol(dsList, "FLAG", 1);
		// ZPSS007[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));
		
		for(int i=0; i<dsList.getRowCount(); i++){
			dsList.setColumn(i, "SDATE",dsList.getColumnAsString(i, "CDATE"));
			dsList.setColumn(i, "CDATE","");
			
			if(DatasetUtility.getString(dsCond, "TAB_GBN", "").equals("3")){
				if(dsList.getColumnAsString(i, "ZZFKEYD").equals("X")){
					dsList.setColumn(i, "ZZFKEYD",StringOperator.replaceString(
							dsList.getColumnAsString(i, "ZZFKEYD"), "X", "1"));
				}
				if(dsList.getColumnAsString(i, "ZZSTAN1").equals("X")){
					dsList.setColumn(i, "ZZSTAN1",StringOperator.replaceString(
							dsList.getColumnAsString(i, "ZZSTAN1"), "X", "1"));
				}
			}
		}

		ctx.addOutputDataset(dsList);
	}

	/**
	 * 저장 처리
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsList = ctx.getInputDataset("ds_list");
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		
		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZMBL_PS_SET_001", new Object[]{dsList});
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리
			ZPSS030[] list = (ZPSS030[])
					CallSAP.moveDs2Obj(dsList, ZPSS030.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};

			// 저장 처리
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZMBL_PS_SET_001", new Object[]{ctx.getInputVariable("GUBUN"),msgList, list});

			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
		}

		if ( dsError.getRowCount() == 0 ) {
			// 조회 호출
			query(ctx);
		}

		ctx.addOutputDataset(dsError);
	}

	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,
				Object orgObj) {

			if ( colValue == null ) {
				return null;
			}

			if ( dsColName.equals("EXPORT") ) {
				return ((String) colValue).equals("0") ? "1" : "0";
			} else if ( dsColName.equals("INSGB")) {
				return ((String) colValue).equals("X") ? "1" : "0";
			}
			return colValue;
		}
	}
}
