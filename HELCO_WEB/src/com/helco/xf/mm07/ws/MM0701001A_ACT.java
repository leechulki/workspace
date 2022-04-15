package com.helco.xf.mm07.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.mm07.ws.MM0701001A_ACT.MyDatasetHelper;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class MM0701001A_ACT extends AbstractAction {
	public static final String ZWEB_MM_GET_ZMMR023
	= "com.helco.xf.mm07.ws.ZWEB_MM_GET_ZMMR023";
	
	public void query(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		Dataset dsError = null;

		ZMMS023[] list = new ZMMS023[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "I_CODE")
				  , DatasetUtility.getString(dsCond, "I_EKGRP")
				  , DatasetUtility.getString(dsCond, "I_LIFNR","")
				  , DatasetUtility.getString(dsCond, "I_MATNR","")
				  , DatasetUtility.getString(dsCond, "I_PSPNR","")
				  , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_UDATE"))
				  , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_VDATE"))
				  , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_ZSHIP"))
				  , listMsg
				  , list
		};

		// SAP Call
		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_ZMMR023 , obj);
		
		// eai 도입으로 인해 변경
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_MM_GET_ZMMR023, obj, false);
		
		//수정
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZMMS023.getDataset();
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
