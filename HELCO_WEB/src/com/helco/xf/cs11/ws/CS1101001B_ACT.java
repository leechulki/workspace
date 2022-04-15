package com.helco.xf.cs11.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;

import com.tobesoft.platform.data.Dataset;

public class CS1101001B_ACT extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public static final String ZWEB_PS_SET_0000 = "com.helco.xf.cs11.ws.ZWEB_PS_SET_0000";

	public static boolean flag = false;
	
	/**
	 * 저장
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		CS1101001B_Service service = (CS1101001B_Service)ServiceFactoryManager.getService("CS1101001B");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.save(ctx, ds_list);
	}
	/**
	 * 기안전처리
	 * @param ctx
	 * @throws Exception
	 */
	public void draft(BusinessContext ctx) throws Exception {
		CS1101001B_Service service = (CS1101001B_Service)ServiceFactoryManager.getService("CS1101001B");
		Dataset ds_list = ctx.getInputDataset("ds_draft");
		service.draft(ctx, ds_list);
	}
	
	/**
	 * 결재
	 * @param ctx
	 * @throws Exception
	 */
	public void saveDecide(BusinessContext ctx) throws Exception {
		CS1101001B_Service service = (CS1101001B_Service)ServiceFactoryManager.getService("CS1101001B");
		Dataset ds_list   = ctx.getInputDataset("ds_list");
		Dataset ds_decide = ctx.getInputDataset("ds_decide");
		service.saveDecide(ctx, ds_list, ds_decide);
	}
	/**
	 * 기안과 결재 동시처리
	 * @param ctx
	 * @throws Exception
	 */
	public void saveDraftAndDecide(BusinessContext ctx) throws Exception {
		CS1101001B_Service service = (CS1101001B_Service)ServiceFactoryManager.getService("CS1101001B");
		Dataset ds_list   = ctx.getInputDataset("ds_list");
		Dataset ds_decide = ctx.getInputDataset("ds_decide");
		service.saveDraftAndDecide(ctx, ds_list, ds_decide);
	}
	
	/**
	 * 발주 삭제
	 * @param ctx
	 * @throws Exception
	 */
	public void balDelete(BusinessContext ctx) throws Exception {
		CS1101001B_Service service = (CS1101001B_Service)ServiceFactoryManager.getService("CS1101001B");
		Dataset ds_list = ctx.getInputDataset("ds_list");
		service.balDelete(ctx, ds_list);
	}
	

}
