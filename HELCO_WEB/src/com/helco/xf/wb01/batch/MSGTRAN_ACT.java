package com.helco.xf.wb01.batch;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;

public class MSGTRAN_ACT extends AbstractAction {

	/**
	 * ����/�ذ� SMS �߼� BATCH ���� ���� 
	 * @param ctx
	 * @throws Exception
	 */
	
	static Logger logger = ServiceManagerFactory.getLogger();
	public void query(BusinessContext ctx) throws Exception {
		System.out.println("==============================================================================");
        System.out.println("�Ⱓ ���� ���� ���� ��ȸ QUERY ");
        System.out.println("==============================================================================\n");

        ProcMSGTRAN MSGTRAN = new ProcMSGTRAN();
		MSGTRAN.run();
	}
}
