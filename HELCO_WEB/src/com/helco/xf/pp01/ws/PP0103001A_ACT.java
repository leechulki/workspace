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
 * ��ȸ ó�� 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	
	// �Է� �Ķ���� ó�� 
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

	// ȣ�� 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR103, obj);

	// ��� Dataset ���� 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS103.getDataset();
	ds.setId("ds_list");
	// �ʿ� �÷� - �߰� 
	CallSAP.makeDsCol(ds, "CHECK", 1);

	// ��� ���� �ű�� 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
	ctx.addOutputDataset(ds);
}

/**
 * ���� ó�� 
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
		// �Է� �Ķ���� ó�� 
		ZPPS103[] list = (ZPPS103[])
				CallSAP.moveDs2Obj(dsList, ZPPS103.class, "", new MyDs2ObjHelper());
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		// ���� ó�� 
		stub = CallSAP.callSap(
					ctx.getInputVariable("G_MANDT")
					, ZWEB_PP_SET_ZPPR103
					, new Object[]{DatasetUtility.getString(dsCond, "I_RBT"), list});
		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo( msgList );
	}
	ctx.addOutputDataset(dsError);
	if ( dsError.getRowCount() == 0 ) {
		// ��ȸ ȣ�� 
		try {
			query(ctx);
		} catch( CommRfcException e) {}	
	}
}

/**
 * ���� ó�� 
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
		// �Է� �Ķ���� ó�� 
		ZPPS103[] list = (ZPPS103[])
				CallSAP.moveDs2Obj(dsList, ZPPS103.class, null);
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		// ���� ó�� 
		stub = CallSAP.callSap(
					ctx.getInputVariable("G_MANDT")
					, ZWEB_PP_DLT_ZPPR103
					, new Object[]{DatasetUtility.getString(dsCond, "I_RBT"), list});
		msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo( msgList );
	}
	ctx.addOutputDataset(dsError);
	if ( dsError.getRowCount() == 0 ) {
		// ��ȸ ȣ�� 
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
		// ���� ���� ��Ŵ. 
		ZPPS103 tmpObj = ((ZPPS103)obj);
		tmpObj.setDGDATE(CallSAP.getFormatDate(tmpObj.getDGDATE()));	// ������
		tmpObj.setIGDATE(CallSAP.getFormatDate(tmpObj.getIGDATE()));	// �԰�䱸��
		tmpObj.setSSCDAT(CallSAP.getFormatDate(tmpObj.getSSCDAT()));	// ���Ͽ���
		tmpObj.setIBGO1(CallSAP.getFormatDate(tmpObj.getIBGO1()));	// 1�� �԰� ������
		tmpObj.setIBGO1(CallSAP.getFormatDate(tmpObj.getIBGO2()));	// 2�� �԰� ������
		tmpObj.setINPDAT4(CallSAP.getFormatDate(tmpObj.getINPDAT4()));	// �����԰���
		tmpObj.setCRDAT(CallSAP.getFormatDate(tmpObj.getCRDAT()));	// ������
	}
}
}
