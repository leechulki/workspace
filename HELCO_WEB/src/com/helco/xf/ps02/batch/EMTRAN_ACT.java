package com.helco.xf.ps02.batch;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;

public class EMTRAN_ACT extends AbstractAction {

	/**
	 * 착공/준공 SMS 발송 BATCH 수동 실행 
	 * @param ctx
	 * @throws Exception
	 */
	
	static Logger logger = ServiceManagerFactory.getLogger();
	public void query(BusinessContext ctx) throws Exception {
		System.out.println("==============================================================================");
        System.out.println("착공/준공예정일 SMS발송 HDCS.EM_TRAN QUERY ");
        System.out.println("==============================================================================\n");

        ProcEMTRAN EMTRAN = new ProcEMTRAN();
		EMTRAN.query(ctx);
	}
	public void save(BusinessContext ctx) throws Exception {
		System.out.println("==============================================================================");
        System.out.println("착공/준공예정일 SMS발송 BATCH WEB 수동 실행");
        System.out.println("==============================================================================\n");

        ProcEMTRAN EMTRAN = new ProcEMTRAN();
		EMTRAN.run();
	}
}
