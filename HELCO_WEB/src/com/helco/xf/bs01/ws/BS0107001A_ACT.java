package com.helco.xf.bs01.ws;

import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.Spras;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import webservice.stub.eai.EAIStub;
import webservice.stub.eai.ZWEB_SD_AGENT_SUJUStub;
import webservice.stub.eai.ZWEB_SD_AGENT_SUJUStub.TABLE_OF_ZPSSEMSG;
import webservice.stub.eai.ZWEB_SD_AGENT_SUJUStub.TABLE_OF_ZSDS0022;

public class BS0107001A_ACT extends AbstractAction {
	public static final String ZWEB_SD_AGENT_SUJU
		= "com.helco.xf.bs01.ws.ZWEB_SD_AGENT_SUJU";
	/**
	 * 조회 처리 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
//		Dataset dsError = null;

		ZWEB_SD_AGENT_SUJUStub stub;
		ZWEB_SD_AGENT_SUJUStub.ZWEB_SD_AGENT_SUJU param;
		ZWEB_SD_AGENT_SUJUStub.ZWEB_SD_AGENT_SUJUResponse response;

		try {
			stub = (ZWEB_SD_AGENT_SUJUStub) new EAIStub(
						ctx.getInputVariable("G_SYSID"),
						ctx.getInputVariable("G_MANDT"), 
						new Spras(ctx.getInputVariable("G_LANG")).toString()
					).create(ZWEB_SD_AGENT_SUJUStub.class);
    		param = new ZWEB_SD_AGENT_SUJUStub.ZWEB_SD_AGENT_SUJU();
			param.setI_EMON(ZWEB_SD_AGENT_SUJUStub.Char6.Factory.fromString(dsCond.getColumnAsString(0, "EMON"),""));
			param.setI_LIFNR(ZWEB_SD_AGENT_SUJUStub.Char10.Factory.fromString(dsCond.getColumnAsString(0, "LIFNR"),""));
			param.setI_PROJ(ZWEB_SD_AGENT_SUJUStub.Char20.Factory.fromString(dsCond.getColumnAsString(0, "PROJ"),""));
			param.setI_SMON(ZWEB_SD_AGENT_SUJUStub.Char6.Factory.fromString(dsCond.getColumnAsString(0, "SMON"),""));
			param.setI_SUM(ZWEB_SD_AGENT_SUJUStub.Char1.Factory.fromString(dsCond.getColumnAsString(0, "SUM"),""));
			param.setI_VKBUR(ZWEB_SD_AGENT_SUJUStub.Char4.Factory.fromString(dsCond.getColumnAsString(0, "VKBUR"),""));
			param.setI_ZMAN(ZWEB_SD_AGENT_SUJUStub.Char8.Factory.fromString(dsCond.getColumnAsString(0, "ZMAN"),""));

    		TABLE_OF_ZPSSEMSG table_of_zpssmesg = new TABLE_OF_ZPSSEMSG();
    		TABLE_OF_ZSDS0022 table_of_zsds0022 = new TABLE_OF_ZSDS0022();
    		param.setT_ITAB(table_of_zsds0022);
    		param.setO_ETAB(table_of_zpssmesg);

			response = stub.zWEB_SD_AGENT_SUJU(param);
			ZWEB_SD_AGENT_SUJUStub.TABLE_OF_ZPSSEMSG errMesg = response.getO_ETAB();
			ZWEB_SD_AGENT_SUJUStub.TABLE_OF_ZSDS0022 list = response.getT_ITAB();
			
			Dataset dsList = ctx.getInputDataset("ds_list");
			ZWEB_SD_AGENT_SUJUStub.ZSDS0022 arrZsds0022[] = list.getItem();
			if(arrZsds0022 != null) {
				for(ZWEB_SD_AGENT_SUJUStub.ZSDS0022 zsds0022 : arrZsds0022) {
					dsList.appendRow();
					DatasetUtil.moveObjToDsRow(zsds0022, dsList, dsList.getRowCount()-1);
				}
			}
			ctx.addOutputDataset(dsList);

//			dsError = CallSAP.makeErrorInfo(listMsg);
//			ctx.addOutputDataset(dsError);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
