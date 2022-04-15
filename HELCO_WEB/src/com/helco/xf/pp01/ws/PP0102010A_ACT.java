package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102010A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR037 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR037";
	
	public void query(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		Dataset dsError = null;

		ZPPS037[] list = new ZPPS037[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DATA1"))
				  , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DATA2"))
				  , DatasetUtility.getString(dsCond, "I_GUBUN","")
				  , DatasetUtility.getString(dsCond, "I_GUBUN2","")
				  , DatasetUtility.getString(dsCond, "I_GUBUN3","")
				  , DatasetUtility.getString(dsCond, "I_GUBUN4","")
				  , DatasetUtility.getString(dsCond, "I_GUBUN5","")
				  , DatasetUtility.getString(dsCond, "I_GUBUN6","")
				  , DatasetUtility.getString(dsCond, "I_GUBUN7","")
				  , DatasetUtility.getString(dsCond, "I_POSID","")
				  , listMsg
				  , list
		};

		// SAP Call
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR037, obj);
		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS037.getDataset();
		ds.setId("ds_list");

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);
		
		if(dsError.getRowCount() == 0) {
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
		
	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,
				Object orgObj) {

			if ( colValue == null ) {
				return null;
			}
			
			if (     dsColName.equals("DATE1") || dsColName.equals("DATE2")  
				  || dsColName.equals("DATE3") || dsColName.equals("DATE4")) 
			{
				return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
			}
//			if (     dsColName.equals("AUFNR") || dsColName.equals("HOGI") || dsColName.equals("CONSNAM")  
//					  || dsColName.equals("ITEM_NO") || dsColName.equals("ITEM_NAM") || dsColName.equals("PSMNG")
//					  || dsColName.equals("AMEIN") || dsColName.equals("CONSNO") || dsColName.equals("VORNR")
//					  || dsColName.equals("LTXA1") || dsColName.equals("ARBPL") || dsColName.equals("KTEXT")
//					  || dsColName.equals("STATUS")) 
//			{
//				return Double.parseDouble((String)colValue) <= 0 ? "" : (String) colValue;
//			} 
			return colValue;
		}
	}
}
