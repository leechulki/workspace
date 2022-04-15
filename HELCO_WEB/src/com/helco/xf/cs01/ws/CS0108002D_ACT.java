package com.helco.xf.cs01.ws;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.Utillity;
import com.tobesoft.platform.data.Dataset;

public class CS0108002D_ACT extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger;

	/**
	 * 최초 데이터 저장시 템플릿 마스터 디테일 정보 저장
	 * @param ctx
	 * @throws Exception
	 */
	public void saveTemplet(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		CS0108002D_Service service = (CS0108002D_Service)ServiceFactoryManager.getService("CS0108002D");
		try {
			service.saveTemplet(ctx);
		} catch(Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002D", e.getLocalizedMessage(), "Y", "Y");
		}
		ctx.addOutputDataset(ds_error);
    }
}
