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
	 * ���� BOM ���ռ� üũ ����
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
			// ���Ư�� ���忡 ���� ���� �߻� �� ���� ���� �����͙V�� ��� �����޼����� �����Ѵ�.
			//dsError = CallSAP.makeErrorInfo( se.getLocalizedMessage() );
			logger.debug("specBomMatchCheck ���� getMessage:"+se.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", se.getLocalizedMessage(), "Y", "Y");
		} catch(Exception e) {
			// ���α׷� �����󿡼� �߻��Ǵ� ���� �޼����� ���� ���� �����͙V�� ��� ���� �޼����� �����Ѵ�.
			//dsError = CallSAP.makeErrorInfo( msgList );
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", e.getLocalizedMessage(), "Y", "Y");
		}

		ctx.addOutputDataset(ds_error);
		ctx.addOutputDataset(ds_match_rslt);
		ctx.addOutputDataset(ds_cond);
	}

	/**
	 * ���� BOM Duty ����
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
			// ���Ư�� ���忡 ���� ���� �߻� �� ���� ���� �����͙V�� ��� �����޼����� �����Ѵ�.
			//dsError = CallSAP.makeErrorInfo( se.getLocalizedMessage() );
			logger.debug("specBomDutyCheck ���� getMessage:"+se.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", se.getLocalizedMessage(), "Y", "Y");
		} catch(Exception e) {
			// ���α׷� �����󿡼� �߻��Ǵ� ���� �޼����� ���� ���� �����͙V�� ��� ���� �޼����� �����Ѵ�.
			//dsError = CallSAP.makeErrorInfo( msgList );
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", e.getLocalizedMessage(), "Y", "Y");
		}

		ctx.addOutputDataset(ds_error);
		ctx.addOutputDataset(ds_duty_rslt);
	}
	
	/**
	 * ���ռ� ����� ����
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
		    
		    // ���ռ� ���� �� ���� ������ ���� ��� ���ռ� ����� ���� ��� ó��
			ds_cond.setColumn(0, "VERIFY", "Y");
		} catch(SpecDutyException se) {
			// ���Ư�� ���忡 ���� ���� �߻� �� ���� ���� �����͙V�� ��� �����޼����� �����Ѵ�.
			//dsError = CallSAP.makeErrorInfo( se.getLocalizedMessage() );
			logger.debug("specBomMatchCheck ���� getMessage:"+se.getMessage());
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", se.getLocalizedMessage(), "Y", "Y");
		} catch(Exception e) {
			// ���α׷� �����󿡼� �߻��Ǵ� ���� �޼����� ���� ���� �����͙V�� ��� ���� �޼����� �����Ѵ�.
			//dsError = CallSAP.makeErrorInfo( msgList );
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CS0108002B", e.getLocalizedMessage(), "Y", "Y");
		}

		ctx.addOutputDataset(ds_error);
		ctx.addOutputDataset(ds_cond);
	}

}
