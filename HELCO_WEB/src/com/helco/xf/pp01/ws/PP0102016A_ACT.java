package com.helco.xf.pp01.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.pp01.ws.PP0103001A_ACT.MyDatasetHelper;
import com.helco.xf.pp01.ws.PP0103001A_ACT.MyDs2ObjHelper;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class PP0102016A_ACT extends AbstractAction {
	public static final String ZWEB_PP_GET_ZPPR142 
	= "com.helco.xf.pp01.ws.ZWEB_PP_GET_ZPPR142";
	
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		
		// 입력 파라메터 처리 
		ZPPS142[] list = new ZPPS142[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "I_AGUBUN")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
				, DatasetUtility.getString(dsCond, "I_GUBUN")
				, DatasetUtility.getString(dsCond, "I_POSID")
				, DatasetUtility.getString(dsCond, "I_PSPID")
				, DatasetUtility.getString(dsCond, "I_RBT")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
				, list
		};		

		// 호출 
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_ZPPR142, obj);

		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS142.getDataset();
		ds.setId("ds_list");


		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"), new MyDatasetHelper());
		ctx.addOutputDataset(ds);
	}


	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,
				Object orgObj) {

			if ( colValue == null ) {
				return null;
			}
			
			if (     dsColName.equals("VDATU") || dsColName.equals("ERDAT") || dsColName.equals("CONTR_DA") || dsColName.equals("ERDAT") 
					|| dsColName.equals("RECAD_DA") || dsColName.equals("EDATE") || dsColName.equals("LAST_LAYOUT") || dsColName.equals("BOM_DAY")
					|| dsColName.equals("MAN_DAY") || dsColName.equals("CUL_DAY") || dsColName.equals("ZZSHIP1")
					 || dsColName.equals("SHIP_DAY") || dsColName.equals("ZZBEFOR_DAY")) 
			{
				return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
			} 

			return colValue;
		}
	}

}
