package com.helco.xf.mm05.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.ws.CS1201001B_Service;
import com.helco.xf.mm05.ws.ZLET017;
import com.helco.xf.mm05.ws.ZLES006;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class MM0502005A_ACT extends AbstractAction {

	public static final String ZWEB_LE_GET_DN002
		= "com.helco.xf.mm05.ws.ZWEB_LE_GET_DN002";
	
	public static boolean flag = false;
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
			
		// 입력 파라메터 처리 
		ZLES006[] list1 = new ZLES006[]{};
		ZLET017[] list2 = new ZLET017[]{};
		
		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "EXTWG2")
				, DatasetUtility.getString(dsCond, "MATNR")
				, DatasetUtility.getString(dsCond, "POSID")
				, DatasetUtility.getString(dsCond, "PSPID")
				, DatasetUtility.getString(dsCond, "VSTEL")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "ZZPROF"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "ZZPROT"))
				, list1
				, list2
		};
		// 호출 
		//SapFunction stub 
		//		= CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_LE_GET_DN002, obj);
		
		// eai 도입으로 인해 변경
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_LE_GET_DN002, obj, false);

		// 출력 Dataset 생성 
		Dataset ds1 = CallSAP.isJCO() ? new Dataset() : ZLES006.getDataset();
		ds1.setId("ds_list1");
		CallSAP.makeDsCol(ds1, "CHK", 1);
		CallSAP.makeDsCol(ds1, "FLAG", 1);

		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds1, stub.getOutput("O_TAB"));

		for(int i=0; i<ds1.getRowCount(); i++){
			ds1.setColumn(i, "FLAG","");
			ds1.setColumn(i, "CHK","0");
		}
		
		ctx.addOutputDataset(ds1);

		// 출력 Dataset 생성 
		Dataset ds2 = CallSAP.isJCO() ? new Dataset() : ZLET017.getDataset();
		ds2.setId("ds_list2");
		CallSAP.makeDsCol(ds2, "CHK", 1);
		CallSAP.makeDsCol(ds2, "FLAG", 1);
		// 목록 정보 옮기기 

		CallSAP.moveObj2Ds(ds2, stub.getOutput("O_TABL"));

		for(int i=0; i<ds2.getRowCount(); i++){
			if( ds2.getColumnAsString(i,"ZSTAT4").equals("1") || 
				ds2.getColumnAsString(i,"ZSTAT4").equals("2") || 
				ds2.getColumnAsString(i,"ZSTAT4").equals("3") || 
				ds2.getColumnAsString(i,"ZSTAT4").equals("4") || 
				ds2.getColumnAsString(i,"ZSTAT4").equals("5") || 
				ds2.getColumnAsString(i,"ZSTAT4").equals("7") || 
				ds2.getColumnAsString(i,"ZSTAT4").equals("8") || 
				ds2.getColumnAsString(i,"ZSTAT4").equals("9") ) 	
			{
				ds2.setColumn(i, "FLAG","");
				ds2.setColumn(i, "CHK","0");
			}
			else
			{
				ds2.setColumn(i, "FLAG","U");
				ds2.setColumn(i, "CHK","1");
			}
		}
		
		ctx.addOutputDataset(ds2);
	}
}
