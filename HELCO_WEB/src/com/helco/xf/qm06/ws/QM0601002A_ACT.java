package com.helco.xf.qm06.ws;

import javax.faces.validator.LengthValidator;

import com.helco.xf.qm06.ws.QM0601002A_ACT.MyDatasetHelper;
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class QM0601002A_ACT extends AbstractAction {
	public static final String ZWEB_MM_GET_JQPRRMATNR 
	= "com.helco.xf.qm06.ws.ZWEB_MM_GET_JQPRRMATNR";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	//Dataset dsError = null;

	// 입력 파라메터 처리 
	ZMMS013[] list = new ZMMS013[]{};
	//ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	
	Object obj[] = new Object[]{
			//, listMsg
			 CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_APPDT1"))
		    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_APPDT2"))
		    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_CREDT1"))
		    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_CREDT2"))
			, DatasetUtility.getString(dsCond, "I_CRENM")
			, DatasetUtility.getString(dsCond, "I_JQPRNO")
			, DatasetUtility.getString(dsCond, "I_JQPRNUM")
			, DatasetUtility.getString(dsCond, "I_NAMET")
			, DatasetUtility.getString(dsCond, "I_POSID")
		    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_SDATE1"))
		    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_SDATE2"))
			, list

	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_JQPRRMATNR, obj);
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS013.getDataset();
	ds.setId("ds_list");
	CallSAP.makeDsCol(ds, "CHECK", 0);	
	CallSAP.makeDsCol(ds, "CHECK2", 0);
	CallSAP.makeDsCol(ds, "CELLP", 0);
	CallSAP.makeDsCol(ds, "CELLP_1", 0);
	CallSAP.makeDsCol(ds, "NAME1", 0);
	CallSAP.makeDsCol(ds, "POSID", 0);
	CallSAP.makeDsCol(ds, "PSPID", 0);
	CallSAP.makeDsCol(ds, "POSID_1", 0);
	
/*	for(int i=0; i<ds.getRowCount(); i++)
	{
		ds.setColumn(i, "CHKCK", 0);
	}*/
//	ctx.addOutputDataset(ds);

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());

	for(int i=0; i<ds.getRowCount(); i++)
	{
		ds.setColumn(i, "CHKCK", 0);
		ds.setColumn(i, "APPDT03",StringOperator.replaceString(ds.getColumnAsString(i, "APPDT03"), "-", ""));
		ds.setColumn(i, "OCCDT",StringOperator.replaceString(ds.getColumnAsString(i, "OCCDT"), "-", ""));
		ds.setColumn(i, "ZCDATE",StringOperator.replaceString(ds.getColumnAsString(i, "ZCDATE"), "-", ""));
		ds.setColumn(i, "ZCDATE2",StringOperator.replaceString(ds.getColumnAsString(i, "ZCDATE2"), "-", ""));
		ds.setColumn(i, "CKDATE",StringOperator.replaceString(ds.getColumnAsString(i, "CKDATE"), "-", ""));
		ds.setColumn(i, "ZSDATE",StringOperator.replaceString(ds.getColumnAsString(i, "ZSDATE"), "-", ""));
	}    
	
	ctx.addOutputDataset(ds);

	

	//listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
	//dsError = CallSAP.makeErrorInfo(listMsg);
}

class MyDatasetHelper implements DatasetHelper {
	public Object getDsValue(String dsColName, Object colValue,
			Object orgObj) {

		if ( colValue == null ) {
			return null;
		}
		
/*		if ( dsColName.equals("ZSDATE") || dsColName.equals("ZCDATE")
			 || dsColName.equals("ZCDATE2")) 
		{
			return ((String) colValue).equals("00000000") ? "" : (String) colValue;
		} */

		if ( dsColName.equals("ZSDATE") || dsColName.equals("ZCDATE")
				 || dsColName.equals("ZCDATE2")) 
			{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue; 
			}
		
/*		if ( dsColName.equals("BADAT") || dsColName.equals("BEDAT")
				 || dsColName.equals("CKDATE") || dsColName.equals("BUDAT")
				 || dsColName.equals("SBUDAT1") || dsColName.equals("BUDAT2")
				 || dsColName.equals("SBUDAT2") || dsColName.equals("BUDAT3")
				 || dsColName.equals("APPDT03")) 
			{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue; 
			}*/
		
		return colValue;
	}
}

}
