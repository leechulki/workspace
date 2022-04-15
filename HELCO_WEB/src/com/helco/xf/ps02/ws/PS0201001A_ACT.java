package com.helco.xf.ps02.ws;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


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

public class PS0201001A_ACT extends AbstractAction {
	
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
			
			System.out.println("TAB_GBN = 1 "+obj);
			
			// SAP Call
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0003", obj,false);
			
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0003", obj, false);
			
			// Dataset make
			dsList = CallSAP.isJCO() ? new Dataset() : ZPSS007.getDataset();
		} else if(DatasetUtility.getString(dsCond, "TAB_GBN", "").equals("2")){
			ZPSS008[] list = new ZPSS008[]{};
			ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
			
			
			Object obj[] = new Object[]{
					CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DATEF",""))
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DATET",""))
					, DatasetUtility.getString(dsCond, "PSPID","")
					, DatasetUtility.getString(dsCond, "ZZACTSS","")
					, DatasetUtility.getString(dsCond, "ZZLIFNR","")
					, DatasetUtility.getString(dsCond, "ZZPMNUM","")
					, listMsg
					, list
			};
			
			System.out.println("TAB_GBN = 2 "+obj);
			
			// SAP Call
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0004", obj,false);
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0004", obj, false);
			
			
			// Dataset make
			dsList = CallSAP.isJCO() ? new Dataset() : ZPSS008.getDataset();
			// 필요 컬럼 - 추가
			CallSAP.makeDsCol(dsList, "FLAG", 1);
			CallSAP.makeDsCol(dsList, "CHK", 1);
			CallSAP.makeDsCol(dsList, "SDATE", 1);
			CallSAP.makeDsCol(dsList, "CNAME1", 1);
		} else if(DatasetUtility.getString(dsCond, "TAB_GBN", "").equals("3")){
			ZPSS009[] list = new ZPSS009[]{};
			ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
			Object obj[] = new Object[]{
					DatasetUtility.getString(dsCond, "PSPID","")
					, DatasetUtility.getString(dsCond, "ZZACTSS","")
					, DatasetUtility.getString(dsCond, "ZZLIFNR","")
					, listMsg
					, list
			};
			
			System.out.println("TAB_GBN = 3 "+obj);
			
			// SAP Call
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0005", obj,false);
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0005", obj, false);
			
			// Dataset make
			dsList = CallSAP.isJCO() ? new Dataset() : ZPSS009.getDataset();
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
			dsList.setColumn(i, "CNAME1", ctx.getInputVariable("G_USER_ID"));
			if(DatasetUtility.getString(dsCond, "TAB_GBN", "").equals("3")){
				if(dsList.getColumnAsString(i, "ZZFKEYD").equals("X")){
					dsList.setColumn(i, "ZZFKEYD",StringOperator.replaceString(
							dsList.getColumnAsString(i, "ZZFKEYD"), "X", "1"));
				}
				if(dsList.getColumnAsString(i, "ZZSTAN1").equals("X")){
					dsList.setColumn(i, "ZZSTAN1",StringOperator.replaceString(
							dsList.getColumnAsString(i, "ZZSTAN1"), "X", "1"));
				}
				if(dsList.getColumnAsString(i, "ZTEAM").equals("X")){
					dsList.setColumn(i, "ZTEAM",StringOperator.replaceString(
							dsList.getColumnAsString(i, "ZTEAM"), "X", "1"));
				}
			}
		}
		
		for(int i=0; i<dsList.getRowCount(); i++)
		{
			dsList.setColumn(i,"BEST_DT1",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT1"), "-", ""));
			dsList.setColumn(i,"BEST_DT2",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT2"), "-", ""));
			dsList.setColumn(i,"BEST_DT3",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT3"), "-", ""));
			dsList.setColumn(i,"BEST_DT4",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT4"), "-", ""));
			dsList.setColumn(i,"BEST_DT5",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT5"), "-", ""));

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
		
//		if(ctx.getInputVariable("GUBUN").equals("U")) //착준공계획 수정화면에서 저장시
//		{	
//			for(int i=0; i<dsList.getRowCount(); i++){
//                if(dsList.getColumnAsString(i, "CHK"   ) == null || dsList.getColumnAsString(i, "CHK"   ).equals("null")) { dsList.setColumn(i, "CHK",   "");}
//				if(dsList.getColumnAsString(i, "GUBUN" ) == null || dsList.getColumnAsString(i, "GUBUN" ).equals("null")) { dsList.setColumn(i, "GUBUN", "");}
//				if(dsList.getColumnAsString(i, "CLOSI" ) == null || dsList.getColumnAsString(i, "CLOSI" ).equals("null")) { dsList.setColumn(i, "CLOSI", "");}
//				if(dsList.getColumnAsString(i, "BLOCK" ) == null || dsList.getColumnAsString(i, "BLOCK" ).equals("null")) { dsList.setColumn(i, "BLOCK", "");}
//				if(dsList.getColumnAsString(i, "CHASU" ) == null || dsList.getColumnAsString(i, "CHASU" ).equals("null")) { dsList.setColumn(i, "CHASU", "");}
//				if(dsList.getColumnAsString(i, "QUART" ) == null || dsList.getColumnAsString(i, "QUART" ).equals("null")) { dsList.setColumn(i, "QUART", "");}
//				if(dsList.getColumnAsString(i, "REMOV" ) == null || dsList.getColumnAsString(i, "REMOV" ).equals("null")) { dsList.setColumn(i, "REMOV", "");}
//				if(dsList.getColumnAsString(i, "CHANG" ) == null || dsList.getColumnAsString(i, "CHANG" ).equals("null")) { dsList.setColumn(i, "CHANG", "");}
//				if(dsList.getColumnAsString(i, "REASO" ) == null || dsList.getColumnAsString(i, "REASO" ).equals("null")) { dsList.setColumn(i, "REASO", "");}
//				if(dsList.getColumnAsString(i, "TXT01" ) == null || dsList.getColumnAsString(i, "TXT01" ).equals("null")) { dsList.setColumn(i, "TXT01", "");}
//				if(dsList.getColumnAsString(i, "REAQU" ) == null || dsList.getColumnAsString(i, "REAQU" ).equals("null")) { dsList.setColumn(i, "REAQU", "");}
//				if(dsList.getColumnAsString(i, "CNAME" ) == null || dsList.getColumnAsString(i, "CNAME" ).equals("null")) { dsList.setColumn(i, "CNAME", "");}
//				if(dsList.getColumnAsString(i, "CHASU1") == null || dsList.getColumnAsString(i, "CHASU1").equals("null")) { dsList.setColumn(i, "CHASU1","");}
//				if(dsList.getColumnAsString(i, "REMOV1") == null || dsList.getColumnAsString(i, "REMOV1").equals("null")) { dsList.setColumn(i, "REMOV1","");}
//				if(dsList.getColumnAsString(i, "CHANG1") == null || dsList.getColumnAsString(i, "CHANG1").equals("null")) { dsList.setColumn(i, "CHANG1","");}
//				if(dsList.getColumnAsString(i, "REASO1") == null || dsList.getColumnAsString(i, "REASO1").equals("null")) { dsList.setColumn(i, "REASO1","");}
//				if(dsList.getColumnAsString(i, "TXT011") == null || dsList.getColumnAsString(i, "TXT011").equals("null")) { dsList.setColumn(i, "TXT011","");}
//				if(dsList.getColumnAsString(i, "REAQU1") == null || dsList.getColumnAsString(i, "REAQU1").equals("null")) { dsList.setColumn(i, "REAQU1","");}
//				if(dsList.getColumnAsString(i, "CNAME1") == null || dsList.getColumnAsString(i, "CNAME1").equals("null")) { dsList.setColumn(i, "CNAME1","");}
//				if(dsList.getColumnAsString(i, "CDATE1") == null || dsList.getColumnAsString(i, "CDATE1").equals("null")) { dsList.setColumn(i, "CDATE1","");}
//				if(dsList.getColumnAsString(i, "SDATE" ) == null || dsList.getColumnAsString(i, "SDATE" ).equals("null")) { dsList.setColumn(i, "SDATE", "");}
//
//				if(dsList.getColumnAsString(i, "FLAG").equals("I")) //변경된 데이타만..
//				{	//GUBUN : 출하요청일1 값 변경시 팝업창을 띄워서 변경사유입력후 저장 했을경우 : Y,
//					//출하요청일1 값 변경시 팝업창을 띄워서 변경사유 입력 안하고 닫기  했을경우 : ''
//					System.out.println("<======================== 착준공계획대상 수정 START ========================>");
//					System.out.println("G_USER_ID ==> [" + ctx.getInputVariable("G_USER_ID") + "] ,CURRENT DATE ==> [" + format.format(new Date()) + "]");
//					System.out.println("GUBUN ==> [" + dsList.getColumnAsString(i, "GUBUN") + "] ,PSPID ==> [" + dsList.getColumnAsString(i, "PSPID") + "] ,POSID ==> [" + dsList.getColumnAsString(i, "POSID") + "] ,ZZSHIP1 ==> [" + dsList.getColumnAsString(i, "ZZSHIP1") + "] ,ZZFMAGA ==> [" + dsList.getColumnAsString(i, "ZZFMAGA") + "]");
//					System.out.println("CLOSI ==> [" + dsList.getColumnAsString(i, "CLOSI") + "] ,BLOCK ==> [" + dsList.getColumnAsString(i, "BLOCK") + "] ,CHASU ==> [" + dsList.getColumnAsString(i, "CHASU") + "] ,QUART ==> [" + dsList.getColumnAsString(i, "QUART") + "] ,REMOV ==> [" + dsList.getColumnAsString(i, "REMOV") + "] ,CHANG ==> [" + dsList.getColumnAsString(i, "CHANG") + "]");
//					System.out.println("REASO ==> [" + dsList.getColumnAsString(i, "REASO") + "] ,TXT01 ==> [" + dsList.getColumnAsString(i, "TXT01") + "] ,REAQU ==> [" + dsList.getColumnAsString(i, "REAQU") + "] ,CNAME ==> [" + dsList.getColumnAsString(i, "CNAME") + "] ,CDATE ==> [" + dsList.getColumnAsString(i, "CDATE") + "]");
//					System.out.println("<======================== 착준공계획대상 수정 END ========================>");
//				}
//			}
//		}	
		
		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0001", new Object[]{dsList},false);
			//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0001", new Object[]{dsList}, false);
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리
			ZPSS004[] list = (ZPSS004[])
					CallSAP.moveDs2Obj(dsList, ZPSS004.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};

			// 저장 처리
			//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0001", new Object[]{ctx.getInputVariable("GUBUN"),msgList, list}, false);
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0001", new Object[]{ctx.getInputVariable("GUBUN"),msgList, list},false);

			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
		}

		if ( dsError.getRowCount() == 0 ) {
			// 조회 호출
			query(ctx);
		}

		ctx.addOutputDataset(dsError);
	}
	
	/**
	 * PM, 협력업체, 양중업체 변경 처리
	 * @param ctx
	 * @throws Exception
	 */
	public void change(BusinessContext ctx) throws Exception {
		Dataset dsList = ctx.getInputDataset("ds_list");
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		
		
		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0016", new Object[]{dsList},false);
			//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0001", new Object[]{dsList}, false);
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리
			ZPSS036[] list = (ZPSS036[])
					CallSAP.moveDs2Obj(dsList, ZPSS036.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};

			// 저장 처리
			//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0001", new Object[]{ctx.getInputVariable("GUBUN"),msgList, list}, false);
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0016", new Object[]{ctx.getInputVariable("GUBUN"),msgList, list},false);

			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
		}

		if ( dsError.getRowCount() == 0 ) {
			// 조회 호출
			query(ctx);
		}

		ctx.addOutputDataset(dsError);
	}
	
	/**
	 * 현장답사록 저장 처리
	 * @param ctx
	 * @throws Exception
	 */
	public void save_g(BusinessContext ctx) throws Exception {
		Dataset dsList = ctx.getInputDataset("ds_save");
		Dataset dsError = null; //CallSAP.makeErrorInfo( msgList );
		
		if (CallSAP.isJCO()) {
			dsError = new Dataset("ds_error");
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0015", new Object[]{dsList},false);
			//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0015", new Object[]{dsList}, false);
			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			// 입력 파라메터 처리
			ZPSS032[] list = (ZPSS032[])
					CallSAP.moveDs2Obj(dsList, ZPSS032.class,"");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};

			// 저장 처리
			//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0015", new Object[]{ctx.getInputVariable("GUBUN"),msgList, list}, false);
			SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_SET_0015", new Object[]{msgList, list},false);

			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(msgList);
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
