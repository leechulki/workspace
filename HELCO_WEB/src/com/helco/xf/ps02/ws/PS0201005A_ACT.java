package com.helco.xf.ps02.ws;

import javax.faces.validator.LengthValidator;

import com.helco.xf.ps02.ws.PS0201005A_ACT.MyDatasetHelper;
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS0201005A_ACT extends AbstractAction {
	public static final String ZWEB_MM_BOXPROG 
	= "com.helco.xf.ps02.ws.ZWEB_MM_BOXPROG";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	//Dataset dsError = null;

	// 입력 파라메터 처리 
	ZMMS016[] list = new ZMMS016[]{};
	//ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	
	Object obj[] = new Object[]{
			//, listMsg
			 DatasetUtility.getString(dsCond, "I_ATT")
			, DatasetUtility.getString(dsCond, "I_BOX") 
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DAT1"))
		    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DAT2"))
			, DatasetUtility.getString(dsCond, "I_GUBN")
		    , DatasetUtility.getString(dsCond, "I_POSID")
			, list

	};		

	// 호출 
	//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_BOXPROG, obj);
		
	// eai 도입으로 인해 변경
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_MM_BOXPROG, obj, false);
		
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS016.getDataset();
	ds.setId("ds_list");
	CallSAP.makeDsCol(ds, "CHECK", 0);

/*	for(int i=0; i<ds.getRowCount(); i++)
	{
		ds.setColumn(i, "CHKCK", 0);
	}*/
//	ctx.addOutputDataset(ds);

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());

	for(int i=0; i<ds.getRowCount(); i++)
	{
		ds.setColumn(i, "SHIPDAT1",StringOperator.replaceString(ds.getColumnAsString(i, "SHIPDAT1"), "-", ""));
		ds.setColumn(i, "SHIPDAT2",StringOperator.replaceString(ds.getColumnAsString(i, "SHIPDAT2"), "-", ""));
		ds.setColumn(i, "SHIPDAT3",StringOperator.replaceString(ds.getColumnAsString(i, "SHIPDAT3"), "-", ""));
		ds.setColumn(i, "SHIPDAT4",StringOperator.replaceString(ds.getColumnAsString(i, "SHIPDAT4"), "-", ""));
		ds.setColumn(i, "SHIPDAT5",StringOperator.replaceString(ds.getColumnAsString(i, "SHIPDAT5"), "-", ""));
		ds.setColumn(i, "SHIPDAT6",StringOperator.replaceString(ds.getColumnAsString(i, "SHIPDAT6"), "-", ""));
		ds.setColumn(i, "CHNDAT",StringOperator.replaceString(ds.getColumnAsString(i, "CHNDAT"), "-", ""));

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
		
/*		if ( dsColName.equals("BADAT") || dsColName.equals("BEDAT")
			 || dsColName.equals("CKDATE") || dsColName.equals("BUDAT")
			 || dsColName.equals("SBUDAT1") || dsColName.equals("BUDAT2")
			 || dsColName.equals("SBUDAT2") || dsColName.equals("BUDAT3")) 
		{
			return ((String) colValue).equals("00000000") ? "" : (String) colValue;
		}*/ 

		if ( dsColName.equals("SHIPDAT1") || dsColName.equals("SHIPDAT2")
				 || dsColName.equals("SHIPDAT3") || dsColName.equals("SHIPDAT4")
				 || dsColName.equals("SHIPDAT5") || dsColName.equals("SHIPDAT6")
				 || dsColName.equals("CHNDAT")) 
			{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue; 
			}
		
		return colValue;
	}
}

}
