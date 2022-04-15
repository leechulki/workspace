package com.helco.xf.fs02.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

public class FS0201002A_ACT extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 견적원가 마스터 생성
	 * @param ctx
	 * @throws Exception
	 */
	public void escsMasterSave(BusinessContext ctx) throws Exception {
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		FS0201002A_Service service = (FS0201002A_Service)ServiceFactoryManager.getService("FS0201002A");
		try {
			service.escsMasterSave(ctx);
		} catch(Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "FS0201002A", e.getLocalizedMessage(), "Y", "Y");
			System.out.println("err ds_error:"+ds_error);
		}

		ctx.addOutputDataset(ds_error);
	}
}
