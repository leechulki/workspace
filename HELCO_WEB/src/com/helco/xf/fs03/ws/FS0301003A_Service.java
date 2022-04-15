package com.helco.xf.fs03.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface FS0301003A_Service {

	
	/** 수주 SAP 연동 후 이력 데이터 생성 */
	public void  psPidSapCreate( BusinessContext ctx) throws Exception;

	/** 수주 빌링 데이터 SAP */
	public void  bilSapSave( BusinessContext ctx) throws Exception;

}
