package com.helco.xf.fs03.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface FS0301003A_Service {

	
	/** ���� SAP ���� �� �̷� ������ ���� */
	public void  psPidSapCreate( BusinessContext ctx) throws Exception;

	/** ���� ���� ������ SAP */
	public void  bilSapSave( BusinessContext ctx) throws Exception;

}
