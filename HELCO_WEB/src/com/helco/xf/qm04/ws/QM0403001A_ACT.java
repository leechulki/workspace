package com.helco.xf.qm04.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.qm04.ws.ZQMS019R;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class QM0403001A_ACT extends AbstractAction {
	public static final String ZWEB_QM_GET_TOG_RESULT_LIST 
			= "com.helco.xf.qm04.ws.ZWEB_QM_GET_TOG_RESULT_LIST";
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		
		// 입력 파라메터 처리 
		ZQMS019R[] list = new ZQMS019R[]{};
		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "MANAGE")
				, DatasetUtility.getString(dsCond, "OTEIL")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PARQDTF"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "PARQDTT"))
				, DatasetUtility.getString(dsCond, "PSPID")
				, DatasetUtility.getString(dsCond, "STATUS1")
				, DatasetUtility.getString(dsCond, "STATUS2")
				, DatasetUtility.getString(dsCond, "STATUS3")
				, DatasetUtility.getString(dsCond, "STATUS4")
				, DatasetUtility.getString(dsCond, "STATUS5")
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "ZBOSUINF"))
				, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "ZBOSUINT"))
				, DatasetUtility.getString(dsCond, "ZZBSU")
				, list
		};
		// 호출 
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_QM_GET_TOG_RESULT_LIST, obj);
		// 출력 Dataset 생성 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZQMS019R.getDataset();
		ds.setId("ds_list");
		// 필요 컬럼 - 추가 
		CallSAP.makeDsCol(ds, "CHK", 1);
		CallSAP.makeDsCol(ds, "FLAG", 1);
		// 목록 정보 옮기기 
		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
		ctx.addOutputDataset(ds);
	}
	
	/**
	 * 저장 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
/*
		Dataset dsList = ctx.getInputDataset("ds_list");
		
		// 입력 파라메터 처리 
		ZQMS019[] list = new ZQMS019[Integer.parseInt(ctx.getInputVariable("REQ_CNT"))];
		String flag = null;
		ZQMS019 tmp = null;
		
		int cnt = 0;
		for( int i = 0; i < dsList.getRowCount(); i++ ) {
			flag = DatasetUtility.getString(dsList, i, "FLAG", "");
			if ( flag.equals("I") || flag.equals("U") || flag.equals("D")) {
				tmp = new ZQMS019();
				tmp.setTMODE(flag);
				tmp.setSTATUS(DatasetUtility.getString(dsList, i, "STATUS", ""));
				tmp.setWERKS(DatasetUtility.getString(dsList, i, "WERKS", ""));
				tmp.setEBELN(DatasetUtility.getString(dsList, i, "EBELN", ""));
				tmp.setEBELP(DatasetUtility.getString(dsList, i, "EBELP", ""));
				tmp.setINVNR(DatasetUtility.getString(dsList, i, "INVNR", ""));
				tmp.setINVITEM(DatasetUtility.getString(dsList, i, "INVITEM", ""));
				tmp.setPARQDT(CallSAP.getFormatDate(DatasetUtility.getString(dsList, i, "PARQDT")));
				tmp.setLIFNR(DatasetUtility.getString(dsList, i, "LIFNR", ""));
				tmp.setMATNR(DatasetUtility.getString(dsList, i, "MATNR", ""));
				tmp.setPSPID(DatasetUtility.getString(dsList, i, "PSPID", ""));
				tmp.setRPHOGI(DatasetUtility.getString(dsList, i, "RPHOGI", ""));
				tmp.setATYPE(DatasetUtility.getString(dsList, i, "ATYPE", ""));
				
				tmp.setSPEC(DatasetUtility.getString(dsList, i, "SPEC", ""));
				tmp.setZEINR(DatasetUtility.getString(dsList, i, "ZEINR", ""));
				tmp.setLOSMENGE(new BigDecimal(DatasetUtility.getDbl(dsList, i, "LOSMENGE", 0)));
				tmp.setMENGENEINH(DatasetUtility.getString(dsList, i, "MENGENEINH", ""));
				tmp.setEINDT(DatasetUtility.getString(dsList, i, "EINDT", ""));
				tmp.setPRUEFLOS(DatasetUtility.getString(dsList, i, "PRUEFLOS", ""));
				list[cnt++] = tmp;
			}
		}
		
		ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
		CommStub stub = null;
		try {
			// 저장 처리 
			stub = CallSAP.callSap(ZWEB_QM_GET_INS_RESULT_SAVE.class, new Object[]{msgList, list});
			
			list = (ZQMS019[])stub.getOutput("O_TAB");
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			
			Dataset dsError = CallSAP.makeErrorInfo( msgList );
			if ( dsError.getRowCount() == 0 ) {
				// 조회 호출 
				query(ctx);
			}
			
			ctx.addOutputDataset(dsError);
		} catch( CommRfcException e) {
			throw new BusinessException(e.getFaultString());
		}
*/
	}
	
	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,
				Object orgObj) {

			if ( colValue == null ) {
				return null;
			}
			/*
			if ( dsColName.equals("EXPORT") ) {
				return ((String) colValue).equals("0") ? "1" : "0";
			} else if ( dsColName.equals("INSGB")) {
				return ((String) colValue).equals("X") ? "1" : "0";
			}
			*/
			return colValue;
		}
	}
}
