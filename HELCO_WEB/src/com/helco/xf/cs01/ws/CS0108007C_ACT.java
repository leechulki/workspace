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
	 * 데이터 체크 정합성 데이터 저장
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
	 * 정합성 연산식안 정의된 사양특성코드 저장
	 * @param ctx
	 * @throws Exception
	 */
	public void calSpecInsert(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		CS0108007C_Service service = (CS0108007C_Service)ServiceFactoryManager.getService("CS0108007C");

		try {
			// 정합성 연산수식에서 사용되는 특성코드를 파싱해서 저장한다.
		    service.calSpecInsert(ctx);
		} catch(Exception e) {
			// 프로그램 로직상에서 발생되는 에러 메세지를 공통 에러 데이터셑에 담당 에러 메세지를 전달한다.
			//dsError = CallSAP.makeErrorInfo( msgList );
			logger.debug("calSpecInsert 에러 getMessage:"+e.getMessage());
		}
	}
	
}
