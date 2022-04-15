package com.helco.xf.ps02.batch;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;

public class EMTRAN_ACT extends AbstractAction {

	/**
	 * ����/�ذ� SMS �߼� BATCH ���� ���� 
	 * @param ctx
	 * @throws Exception
	 */
	
	static Logger logger = ServiceManagerFactory.getLogger();
	public void query(BusinessContext ctx) throws Exception {
		System.out.println("==============================================================================");
        System.out.println("����/�ذ������� SMS�߼� HDCS.EM_TRAN QUERY ");
        System.out.println("==============================================================================\n");

        ProcEMTRAN EMTRAN = new ProcEMTRAN();
		EMTRAN.query(ctx);
	}
	public void save(BusinessContext ctx) throws Exception {
		System.out.println("==============================================================================");
        System.out.println("����/�ذ������� SMS�߼� BATCH WEB ���� ����");
        System.out.println("==============================================================================\n");

        ProcEMTRAN EMTRAN = new ProcEMTRAN();
		EMTRAN.run();
	}
}
