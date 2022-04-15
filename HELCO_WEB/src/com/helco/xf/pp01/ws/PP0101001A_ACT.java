package com.helco.xf.pp01.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

public class PP0101001A_ACT extends AbstractAction {
	
	public static final String ZWEB_PP_GET_HIGHGLOSSY 
		= "com.helco.xf.pp01.ws.ZWEB_PP_GET_HIGHGLOSSY";

	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		
		// 입력 파라메터 처리 
		ZPPS010[] list = new ZPPS010[]{};
		Object obj[] = new Object[]{
				  CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_FDATE"))
				, DatasetUtility.getString(dsCond, "I_LIFNR")
				, DatasetUtility.getString(dsCond, "I_PSPID")
				, DatasetUtility.getString(dsCond, "I_STATUS")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_TDATE"))
				, list
		};		

		// 호출 
		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_HIGHGLOSSY, obj);
		
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_PP_GET_HIGHGLOSSY, obj, false);		 // 기존  SAP 연결
		
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZPPS010.getDataset();
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
			
			if (     dsColName.equals("ILDATD") || dsColName.equals("ILDATE")  
				  || dsColName.equals("HU_ODATE") || dsColName.equals("HU_IDATE")
				  || dsColName.equals("HP_ODATE") || dsColName.equals("HP_IDATE")
				  || dsColName.equals("CD_ODATE") || dsColName.equals("CD_IDATE")
				  || dsColName.equals("CW_ODATE") || dsColName.equals("CW_IDATE")) 
			{
				return ((String) colValue).equals("0000-00-00") ? "" : (String) colValue;
			} 
			if (     dsColName.equals("HU_JMENGE") || dsColName.equals("HU_OMENGE") || dsColName.equals("HU_IMENGE")  
					  || dsColName.equals("HP_JMENGE") || dsColName.equals("HP_OMENGE") || dsColName.equals("HP_IMENGE")
					  || dsColName.equals("CD_JMENGE") || dsColName.equals("CD_OMENGE") || dsColName.equals("CD_IMENGE")
					  || dsColName.equals("CW_JMENGE") || dsColName.equals("CW_OMENGE") || dsColName.equals("CW_IMENGE")) 
			{
				return Double.parseDouble((String)colValue) <= 0 ? "" : (String) colValue;
			} 
			return colValue;
		}
	}

}
