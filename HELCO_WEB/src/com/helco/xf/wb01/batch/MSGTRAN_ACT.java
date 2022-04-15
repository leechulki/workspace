package com.helco.xf.wb01.batch;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;

public class MSGTRAN_ACT extends AbstractAction {

	/**
	 * 착공/준공 SMS 발송 BATCH 수동 실행 
	 * @param ctx
	 * @throws Exception
	 */
	
	static Logger logger = ServiceManagerFactory.getLogger();
	public void query(BusinessContext ctx) throws Exception {
		System.out.println("==============================================================================");
        System.out.println("기간 만료 쪽지 삭제 조회 QUERY ");
        System.out.println("==============================================================================\n");

        ProcMSGTRAN MSGTRAN = new ProcMSGTRAN();
		MSGTRAN.run();
	}
}
