package com.helco.xf.cs14.ws;

import com.helco.xf.cs14.ws.CS1402001A_Service;
import com.tobesoft.platform.data.Dataset;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;

public class CS1402001A_ACT extends AbstractAction {

	/**
	 * 조회
	 * @param ctx
	 * @throws Exception
	 */
	public void execute(BusinessContext ctx) throws Exception {
		CS1402001A_Service service = (CS1402001A_Service)
		ServiceFactoryManager.getService("CS1402001A");
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_rtn = service.query(ctx, ds_cond);
		ds_rtn.setId("ds_list");
		ctx.addOutputDataset(ds_rtn);
	}

	/**
	 * 저장
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		CS1402001A_Service service = (CS1402001A_Service)
		ServiceFactoryManager.getService("CS1402001A");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.save(ctx, ds_list);
	}
}
