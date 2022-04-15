package com.helco.xf.pp01.ws;

import java.math.BigDecimal;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class PP0103001A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR103 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR103";
	public static final String ZWEB_PP_SET_ZPPR103 
	= "com.helco.xf.pp01.ws.ZWEB_PP_SET_ZPPR103";
	public static final String ZWEB_PP_DLT_ZPPR103 
	= "com.helco.xf.pp01.ws.ZWEB_PP_DLT_ZPPR103";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	
	// 입력 파라메터 처리 
	ZPPS103[] list = new ZPPS103[]{};
	Object obj[] = new Object[]{
			CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
			, DatasetUtility.getString(dsCond, "I_GUBUN")
			, DatasetUtility.getString(dsCond, "I_ITEMNO")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, DatasetUtility.getString(dsCond, "I_PSPID")
			, DatasetUtility.getString(dsCond, "I_RBT")
			, DatasetUtility.getString(dsCond, "I_STATUS")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
			, DatasetUtility.getString(dsCond, "I_TYPE")
			, list
	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR103, obj);

	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS103.getDataset();
	ds.setId("ds_list");
	// 필요 컬럼 - 추가 
	CallSAP.makeDsCol(ds, "CHECK", 1);

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
	ctx.addOutputDataset(ds);
}

/**
 * 저장 처리 
 * @param ctx
 * @throws Exception
 */
public void save(BusinessContext ctx) throws Exception 
{
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	Dataset dsList = ctx.getInputDataset("ds_list_0");
	Dataset dsError = null;
	SapFunction stub = null;
	
	if ( CallSAP.isJCO() ) 		
	{
		dsError = new Dataset("ds_error");
		stub = CallSAP.callSap(ZWEB_PP_SET_ZPPR103
				, new Object[]{dsList});

		CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
	} else {
		// 입력 파라메터 처리 
		ZPPS103[] list = (ZPPS103[])
				CallSAP.moveDs2Obj(dsList, ZPPS103.class, "", new MyDs2ObjHelper());
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		// 저장 처리 
		stub = CallSAP.callSap(
					ctx.getInputVariable("G_MANDT")
					, ZWEB_PP_SET_ZPPR103
					, new Object[]{DatasetUtility.getString(dsCond, "I_RBT"), list});
		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo( msgList );
	}
	ctx.addOutputDataset(dsError);
	if ( dsError.getRowCount() == 0 ) {
		// 조회 호출 
		try {
			query(ctx);
		} catch( CommRfcException e) {}	
	}
}

/**
 * 삭제 처리 
 * @param ctx
 * @throws Exception
 */
public void delete(BusinessContext ctx) throws Exception 
{
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	Dataset dsList = ctx.getInputDataset("ds_list_0");
	Dataset dsError = null;
	SapFunction stub = null;
	
	if ( CallSAP.isJCO() ) 		
	{
		dsError = new Dataset("ds_error");
		stub = CallSAP.callSap(ZWEB_PP_DLT_ZPPR103
				, new Object[]{dsList});

		CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
	} else {
		// 입력 파라메터 처리 
		ZPPS103[] list = (ZPPS103[])
				CallSAP.moveDs2Obj(dsList, ZPPS103.class, null);
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		// 저장 처리 
		stub = CallSAP.callSap(
					ctx.getInputVariable("G_MANDT")
					, ZWEB_PP_DLT_ZPPR103
					, new Object[]{DatasetUtility.getString(dsCond, "I_RBT"), list});
		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo( msgList );
	}
	ctx.addOutputDataset(dsError);
	if ( dsError.getRowCount() == 0 ) {
		// 조회 호출 
		try {
			query(ctx);
		} catch( CommRfcException e) {}	
	}
}

class MyDatasetHelper implements DatasetHelper {
	public Object getDsValue(String dsColName, Object colValue,
			Object orgObj) {

		if ( colValue == null ) {
			return null;
		}
		
		if (     dsColName.equals("DGDATE") || dsColName.equals("IGDATE")
				 || dsColName.equals("SSCDAT") || dsColName.equals("IBGO1")  || dsColName.equals("IBGO2")
				 || dsColName.equals("INPDAT4") || dsColName.equals("CRDAT")) 
		{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
		} 

		return colValue;
	}
}

class MyDs2ObjHelper implements Ds2ObjHelper {
	public void endMoveRow(Dataset ds, int row, Object obj) {
		// 일자 변경 시킴. 
		ZPPS103 tmpObj = ((ZPPS103)obj);
		tmpObj.setDGDATE(CallSAP.getFormatDate(tmpObj.getDGDATE()));	// 생성일
		tmpObj.setIGDATE(CallSAP.getFormatDate(tmpObj.getIGDATE()));	// 입고요구일
		tmpObj.setSSCDAT(CallSAP.getFormatDate(tmpObj.getSSCDAT()));	// 출하예정
		tmpObj.setIBGO1(CallSAP.getFormatDate(tmpObj.getIBGO1()));	// 1차 입고 예정일
		tmpObj.setIBGO1(CallSAP.getFormatDate(tmpObj.getIBGO2()));	// 2차 입고 예정일
		tmpObj.setINPDAT4(CallSAP.getFormatDate(tmpObj.getINPDAT4()));	// 자재입고일
		tmpObj.setCRDAT(CallSAP.getFormatDate(tmpObj.getCRDAT()));	// 생성일
	}
}
}
