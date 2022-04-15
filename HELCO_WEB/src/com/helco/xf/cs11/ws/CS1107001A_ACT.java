package com.helco.xf.cs11.ws;

import com.helco.xf.cs12.evladm.BizException;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;

public class CS1107001A_ACT extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public void save(BusinessContext ctx) throws Exception {	
		CS1107001A_Service service = (CS1107001A_Service) ServiceFactoryManager.getService("CS1107001A");
		try {
			service.save(ctx);
		} catch(Exception e) {
			e.printStackTrace();
			throw new BizException(e + " 저장 처리 시 오류 발생 하였습니다.");
		}
	}
	
	public void approvalProcess(BusinessContext ctx) throws Exception {	
		CS1107001A_Service service = (CS1107001A_Service) ServiceFactoryManager.getService("CS1107001A");
		try {
			service.approvalProcess(ctx);
		} catch(Exception e) {
			e.printStackTrace();
			throw new BizException(e + " 승인 처리 시 오류 발생 하였습니다.");
		}
	}
	public void deleteData(BusinessContext ctx) throws Exception {	
		CS1107001A_Service service = (CS1107001A_Service) ServiceFactoryManager.getService("CS1107001A");
		try {
			service.deleteData(ctx);
		} catch(Exception e) {
			e.printStackTrace();
			throw new BizException(e + " 삭제 처리 시 오류 발생 하였습니다.");
		}
	}
}