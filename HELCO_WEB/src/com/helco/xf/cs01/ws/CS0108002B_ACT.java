package com.helco.xf.cs01.ws;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.core.logger.Logger;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

public class CS0108002B_ACT extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger;

	/**
	 * 서비스 BOM 정합성 체크 수행
	 * @param ctx
	 * @throws Exception
	 */
	public void specBomMatchCheck(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list_match = ctx.getInputDataset("ds_list_match");
		Dataset ds_match_rslt = ctx.getInputDataset("ds_match_rslt");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		CS0108002B_Service service = (CS0108002B_Service)ServiceFactoryManager.getService("CS0108002B");

		try {
		    service.specMatchCheck(ctx, ds_cond, ds_list_match, ds_match_rslt);
		} catch(SpecDutyException se) {
			// 사양특성 스펙에 대한 에러 발생 시 공통 에러 데이터셑에 담아 에러메세지를 전달한다.
			//dsError = CallSAP.makeErrorInfo( se.getLocalizedMessage() );
			logger.debug("specBomMatchCheck 에러 getMessage:"+se.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", se.getLocalizedMessage(), "Y", "Y");
		} catch(Exception e) {
			// 프로그램 로직상에서 발생되는 에러 메세지를 공통 에러 데이터셑에 담당 에러 메세지를 전달한다.
			//dsError = CallSAP.makeErrorInfo( msgList );
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", e.getLocalizedMessage(), "Y", "Y");
		}

		ctx.addOutputDataset(ds_error);
		ctx.addOutputDataset(ds_match_rslt);
		ctx.addOutputDataset(ds_cond);
	}

	/**
	 * 서비스 BOM Duty 수행
	 * @param ctx
	 * @throws Exception
	 */
	public void specBomDutyCheck(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list_match = ctx.getInputDataset("ds_list_match");
		Dataset ds_duty_rslt = ctx.getInputDataset("ds_duty_rslt");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		CS0108002B_Service service = (CS0108002B_Service)ServiceFactoryManager.getService("CS0108002B");

		try {
		    service.specDutyCheck(ctx, ds_cond, ds_list_match, ds_duty_rslt);
		} catch(SpecDutyException se) {
			// 사양특성 스펙에 대한 에러 발생 시 공통 에러 데이터셑에 담아 에러메세지를 전달한다.
			//dsError = CallSAP.makeErrorInfo( se.getLocalizedMessage() );
			logger.debug("specBomDutyCheck 에러 getMessage:"+se.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", se.getLocalizedMessage(), "Y", "Y");
		} catch(Exception e) {
			// 프로그램 로직상에서 발생되는 에러 메세지를 공통 에러 데이터셑에 담당 에러 메세지를 전달한다.
			//dsError = CallSAP.makeErrorInfo( msgList );
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", e.getLocalizedMessage(), "Y", "Y");
		}

		ctx.addOutputDataset(ds_error);
		ctx.addOutputDataset(ds_duty_rslt);
	}
	
	/**
	 * 정합성 연산식 검증
	 * @param ctx
	 * @throws Exception
	 */
	public void specBomMatchVerify(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		CS0108002B_Service service = (CS0108002B_Service)ServiceFactoryManager.getService("CS0108002B");

		try {
		    service.specBomMatchVerify(ctx, ds_cond);
		    
		    // 정합성 연삭 식 검증 오류가 없는 경우 정합성 연삭식 검증 결과 처리
			ds_cond.setColumn(0, "VERIFY", "Y");
		} catch(SpecDutyException se) {
			// 사양특성 스펙에 대한 에러 발생 시 공통 에러 데이터셑에 담아 에러메세지를 전달한다.
			//dsError = CallSAP.makeErrorInfo( se.getLocalizedMessage() );
			logger.debug("specBomMatchCheck 에러 getMessage:"+se.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", se.getLocalizedMessage(), "Y", "Y");
		} catch(Exception e) {
			// 프로그램 로직상에서 발생되는 에러 메세지를 공통 에러 데이터셑에 담당 에러 메세지를 전달한다.
			//dsError = CallSAP.makeErrorInfo( msgList );
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", e.getLocalizedMessage(), "Y", "Y");
		}

		ctx.addOutputDataset(ds_error);
		ctx.addOutputDataset(ds_cond);
	}

}
