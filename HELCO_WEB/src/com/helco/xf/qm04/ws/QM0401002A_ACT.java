package com.helco.xf.qm04.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class QM0401002A_ACT extends AbstractAction {
	public static final String ZWEB_QM_GET_INS_RESULT_DETAIL
		= "com.helco.xf.qm04.ws.ZWEB_QM_GET_INS_RESULT_DETAIL";
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 입력 파라메터 처리 
		ZQMS024[] list = new ZQMS024[]{};
		
		 Object obj[] = new Object[]{
				 DatasetUtility.getString(dsCond, "POSID")
				, DatasetUtility.getString(dsCond, "QMNUM")
				, list
		};

		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_QM_GET_INS_RESULT_DETAIL, obj);
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZQMS024.getDataset();
		ds.setId("ds_list");
		// 필요 컬럼 - 추가 
		CallSAP.makeDsCol(ds, "CHK", 1);
		CallSAP.makeDsCol(ds, "FLAG", 1);
		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
		
		for(int i=0; i<ds.getRowCount(); i++)
		{
			ds.setColumn(
					i, 
					"LNTXT",
					RegExp.replaceAll(
							ds.getColumnAsString(i, "LNTXT"),
							"<(B|b)(R|r)(\\/)?>",
							"")
			);
		}

		ctx.addOutputDataset(ds);
	}
}