package com.helco.xf.cs14.ws;

import com.tobesoft.platform.data.Dataset;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;

public class CS1403001A_ACT extends AbstractAction {

	/**
	 * ½ÂÀÎ
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		CS1403001A_Service service = (CS1403001A_Service)
			ServiceFactoryManager.getService("CS1403001A");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.save(ctx, ds_list);
	}
}
