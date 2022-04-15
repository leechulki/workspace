package com.helco.xf.cs11.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS1101001B_Service {
	
	/** ���� */
	public void save(BusinessContext ctx, Dataset ds_list) throws Exception;
	
	/** ��� */
	public void draft(BusinessContext ctx, Dataset ds_list) throws Exception;
	
	/** ���� */
	public void saveDecide(BusinessContext ctx, Dataset ds_list, Dataset ds_decide) throws Exception;
	
	/** ��Ȱ� ���� ����ó�� */
	public void saveDraftAndDecide(BusinessContext ctx, Dataset ds_list, Dataset ds_decide) throws Exception;

	/** ���ֻ��� */
	public void balDelete(BusinessContext ctx, Dataset ds_list) throws Exception;
}
