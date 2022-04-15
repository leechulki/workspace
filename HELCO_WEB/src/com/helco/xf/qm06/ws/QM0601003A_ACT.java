package com.helco.xf.qm06.ws;

import javax.faces.validator.LengthValidator;

import com.helco.xf.qm06.ws.QM0601003A_ACT.MyDatasetHelper;
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class QM0601003A_ACT extends AbstractAction {
	public static final String ZWEB_MM_GET_RETURNMATNR 
	= "com.helco.xf.qm06.ws.ZWEB_MM_GET_RETURNMATNR";

/**
 * 조회 처리 
 * @param ctx
 * @throws Exception
 */
public void query(BusinessContext ctx) throws Exception {
	Dataset dsCond = ctx.getInputDataset("ds_cond");
	//Dataset dsError = null;

	// 입력 파라메터 처리 
	ZMMS014[] list = new ZMMS014[]{};
	//ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	
	Object obj[] = new Object[]{
			//, listMsg
			  DatasetUtility.getString(dsCond, "I_CGROUP")
			, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_CRDAT1"))
		    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_CRDAT2"))
			, DatasetUtility.getString(dsCond, "I_NAMET")
			, DatasetUtility.getString(dsCond, "I_POSID")
			, list

	};		

	// 호출 
	SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_RETURNMATNR, obj);
	
	// 출력 Dataset 생성 
	Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS014.getDataset();
	ds.setId("ds_list");

	// 목록 정보 옮기기 
	CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());

	for(int i=0; i<ds.getRowCount(); i++)
	{
		ds.setColumn(i, "CRDAT",StringOperator.replaceString(ds.getColumnAsString(i, "CRDAT"), "-", ""));
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

		if ( dsColName.equals("CRDAT") ) 
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
