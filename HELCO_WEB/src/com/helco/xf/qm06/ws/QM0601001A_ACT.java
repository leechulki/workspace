package com.helco.xf.qm06.ws;

import javax.faces.validator.LengthValidator;

import com.helco.xf.qm06.ws.QM0601001A_ACT.MyDatasetHelper;
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class QM0601001A_ACT extends AbstractAction {
	public static final String ZWEB_MM_GET_JQPRPROCESS 
	= "com.helco.xf.qm06.ws.ZWEB_MM_GET_JQPRPROCESS";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	//Dataset dsError = null;

	// 입력 파라메터 처리 
	ZMMS012[] list = new ZMMS012[]{};
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
			, DatasetUtility.getString(dsCond, "I_LIFNR")
			, DatasetUtility.getString(dsCond, "I_NAMET")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, list

	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_JQPRPROCESS, obj);
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS012.getDataset();
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
		ds.setColumn(i, "CHKCK", 0);
		ds.setColumn(i, "APPDT03",StringOperator.replaceString(ds.getColumnAsString(i, "APPDT03"), "-", ""));
		ds.setColumn(i, "BADAT",StringOperator.replaceString(ds.getColumnAsString(i, "BADAT"), "-", ""));
		ds.setColumn(i, "BEDAT",StringOperator.replaceString(ds.getColumnAsString(i, "BEDAT"), "-", ""));
		ds.setColumn(i, "CKDATE",StringOperator.replaceString(ds.getColumnAsString(i, "CKDATE"), "-", ""));
		ds.setColumn(i, "SBUDAT1",StringOperator.replaceString(ds.getColumnAsString(i, "SBUDAT1"), "-", ""));
		ds.setColumn(i, "BUDAT2",StringOperator.replaceString(ds.getColumnAsString(i, "BUDAT2"), "-", ""));
		ds.setColumn(i, "SBUDAT2",StringOperator.replaceString(ds.getColumnAsString(i, "SBUDAT2"), "-", ""));
		ds.setColumn(i, "BUDAT3",StringOperator.replaceString(ds.getColumnAsString(i, "BUDAT3"), "-", ""));
		ds.setColumn(i, "BUDAT",StringOperator.replaceString(ds.getColumnAsString(i, "BUDAT"), "-", ""));

		ds.setColumn(i, "RTNDT",StringOperator.replaceString(ds.getColumnAsString(i, "RTNDT"), "-", ""));
		ds.setColumn(i, "MAILDT",StringOperator.replaceString(ds.getColumnAsString(i, "MAILDT"), "-", ""));
		ds.setColumn(i, "CRDAT",StringOperator.replaceString(ds.getColumnAsString(i, "CRDAT"), "-", ""));
		ds.setColumn(i, "BUDAT4",StringOperator.replaceString(ds.getColumnAsString(i, "BUDAT4"), "-", ""));

		if ( DatasetUtility.getString(dsCond, "GBN").equals("10"))
		{
			ds.setColumn(i,"IWBTR",0);
			ds.setColumn(i,"OWBTR",0);
			ds.setColumn(i,"TWBTR",0);
		} else {
			if(ds.getColumnAsDouble(i,"IWBTR") == null || ds.getColumnAsDouble(i,"IWBTR").equals("")) ds.setColumn(i,"IWBTR",0); 
			else ds.setColumn(i,"IWBTR",ds.getColumnAsDouble(i,"IWBTR"));

			if(ds.getColumnAsDouble(i,"OWBTR") == null || ds.getColumnAsDouble(i,"OWBTR").equals("")) ds.setColumn(i,"OWBTR",0); 
			else ds.setColumn(i,"OWBTR",ds.getColumnAsDouble(i,"OWBTR"));

			if(ds.getColumnAsDouble(i,"TWBTR") == null || ds.getColumnAsDouble(i,"TWBTR").equals("")) ds.setColumn(i,"TWBTR",0); 
			else ds.setColumn(i,"TWBTR",ds.getColumnAsDouble(i,"TWBTR"));			
		}
		ds.setColumn(i, "CLSDT",StringOperator.replaceString(ds.getColumnAsString(i, "CLSDT"), "-", ""));
		ds.setColumn(i, "BUDAT_8000",StringOperator.replaceString(ds.getColumnAsString(i, "BUDAT_8000"), "-", ""));
		ds.setColumn(i, "IPGO_1000",StringOperator.replaceString(ds.getColumnAsString(i, "IPGO_1000"), "-", ""));
		ds.setColumn(i, "IPGO_8000",StringOperator.replaceString(ds.getColumnAsString(i, "IPGO_8000"), "-", ""));
		ds.setColumn(i, "CHULJANG",StringOperator.replaceString(ds.getColumnAsString(i, "CHULJANG"), "-", ""));

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

		if ( dsColName.equals("BADAT") || dsColName.equals("BEDAT")
				 || dsColName.equals("CKDATE") || dsColName.equals("BUDAT")
				 || dsColName.equals("SBUDAT1") || dsColName.equals("BUDAT2")
				 || dsColName.equals("SBUDAT2") || dsColName.equals("BUDAT3")
				 || dsColName.equals("APPDT03") || dsColName.equals("BUDAT4")
				 || dsColName.equals("CLSDT") || dsColName.equals("BUDAT_8000")
				 || dsColName.equals("IPGO_1000") || dsColName.equals("IPGO_8000")
				 || dsColName.equals("CHULJANG")) 
			{
			return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue; 
			}
		
		return colValue;
	}
}

}
