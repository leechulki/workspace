package com.helco.xf.fs03.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

public class FS0301003A_ACT extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 수주 SAP 연동 후 이력 데이터 생성
	 * @param ctx
	 * @throws Exception
	 */
	public void psPidSapCreate(BusinessContext ctx) throws Exception {
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		FS0301003A_Service service = (FS0301003A_Service)ServiceFactoryManager.getService("FS0301003A");
		try {
			service.psPidSapCreate(ctx);
		} catch(Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "FS0301003A", e.getLocalizedMessage(), "Y", "Y");
			System.out.println("err ds_error:"+ds_error);
		}

		ctx.addOutputDataset(ds_error);
	}

	/**
	 * 수주 빌링 데이터 연동
	 * @param ctx
	 * @throws Exception
	 */
	public void bilSapSave(BusinessContext ctx) throws Exception {
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		FS0301003A_Service service = (FS0301003A_Service)ServiceFactoryManager.getService("FS0301003A");
		try {
			service.psPidSapCreate(ctx);
		} catch(Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "FS0301003A", e.getLocalizedMessage(), "Y", "Y");
			System.out.println("err ds_error:"+ds_error);
		}

		ctx.addOutputDataset(ds_error);
	}

}
