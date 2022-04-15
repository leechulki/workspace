package com.helco.xf.qm07.ws;

import com.helco.xf.qm07.ws.QM0701001A_Service;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;

public class QM0701001A_ACT extends AbstractAction {

    /*******************��������*******************/      
	/**
	 * ����
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset ds_list = ctx.getInputDataset("ds_list");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.save(ctx, ds_list);
	}

	/**
	 * ����/���� ����ó��
	 * @param ctx
	 * @throws Exception
	 */
	public void matnrProc(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.matnrProc(ctx, ds_cond);
	}

	/**
	 * ����/���� ����ó�� (������ �ݿ�)
	 * @param ctx
	 * @throws Exception
	 */
	public void matnrProcCondAdd(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.matnrProcCondAdd(ctx, ds_cond);
	}
//TEST
	public void matnrProcCondAdd3(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.matnrProcCondAdd3(ctx, ds_cond);
	}
	/**
	 * ����/���� ����ó�� ���
	 * @param ctx
	 * @throws Exception
	 */
	public void cancelProcess(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.cancelProcess(ctx, ds_cond);
	}

	/**
	 * �������� ����ó��
	 * @param ctx
	 * @throws Exception
	 */
	public void workareaProcessAdd(BusinessContext ctx) throws Exception {
		Dataset ds_list = ctx.getInputDataset("ds_list");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.workareaProcessAdd(ctx, ds_list);
	}
	
    /*******************�������*******************/      
	/**
	 * ����
	 * @param ctx
	 * @throws Exception
	 */
	public void save2(BusinessContext ctx) throws Exception {
		Dataset ds_list = ctx.getInputDataset("ds_list");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.save2(ctx, ds_list);
	}

	/**
	 * ����/���� ����ó��
	 * @param ctx
	 * @throws Exception
	 */
	public void matnrProc2(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.matnrProc2(ctx, ds_cond);
	}

	/**
	 * ����/���� ����ó�� (������ �ݿ�)
	 * @param ctx
	 * @throws Exception
	 */
	public void matnrProcCondAdd2(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.matnrProcCondAdd2(ctx, ds_cond);
	}

	/**
	 * ����/���� ����ó�� ���
	 * @param ctx
	 * @throws Exception
	 */
	public void cancelProcess2(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		QM0701001A_Service service = (QM0701001A_Service)
		ServiceFactoryManager.getService("QM0701001A");
		service.cancelProcess2(ctx, ds_cond);
	}	

}	
	

