package com.helco.xf.pp01.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class PP0102009A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR034 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR034";
	
	public void query(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		Dataset dsError = null;

		ZPPS034[] list = new ZPPS034[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "I_ARBPL")
				  , DatasetUtility.getString(dsCond, "I_AUFNR")
				  , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DATE1"))
				  , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DATE2"))
				  , DatasetUtility.getString(dsCond, "I_ITEMNO","")
				  , DatasetUtility.getString(dsCond, "I_POSID","")
				  , DatasetUtility.getString(dsCond, "I_PSPID","")
				  , listMsg
				  , list
		};

		// SAP Call
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR034, obj);
		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS034.getDataset();
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
			
/*			if (     dsColName.equals("CRDAT") || dsColName.equals("INPDAT1")  
				  || dsColName.equals("INPDAT2") || dsColName.equals("INPDAT3")
				  || dsColName.equals("INPDAT4") || dsColName.equals("INPDAT5")
				  || dsColName.equals("BUDAT") || dsColName.equals("EPDAT")
				  || dsColName.equals("ILDAT")) 
			{
				return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
			} */
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
