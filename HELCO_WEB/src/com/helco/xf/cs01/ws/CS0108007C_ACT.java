package com.helco.xf.cs01.ws;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.core.logger.Logger;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

public class CS0108007C_ACT extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger;

	/**
	 * ������ üũ ���ռ� ������ ����
	 * @param ctx
	 * @throws Exception
	 */
	public void saveMatchData(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		CS0108007C_Service service = (CS0108007C_Service)ServiceFactoryManager.getService("CS0108007C");
		try {
			service.saveMatchData(ctx);
		} catch(Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108007C", e.getLocalizedMessage(), "Y", "Y");
		}
		ctx.addOutputDataset(ds_error);
    }
	
	/**
	 * ���ռ� ����ľ� ���ǵ� ���Ư���ڵ� ����
	 * @param ctx
	 * @throws Exception
	 */
	public void calSpecInsert(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		CS0108007C_Service service = (CS0108007C_Service)ServiceFactoryManager.getService("CS0108007C");

		try {
			// ���ռ� ������Ŀ��� ���Ǵ� Ư���ڵ带 �Ľ��ؼ� �����Ѵ�.
		    service.calSpecInsert(ctx);
		} catch(Exception e) {
			// ���α׷� �����󿡼� �߻��Ǵ� ���� �޼����� ���� ���� �����͙V�� ��� ���� �޼����� �����Ѵ�.
			//dsError = CallSAP.makeErrorInfo( msgList );
			logger.debug("calSpecInsert ���� getMessage:"+e.getMessage());
		}
	}
	
}
