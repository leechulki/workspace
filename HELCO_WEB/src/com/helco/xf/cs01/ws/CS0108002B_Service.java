package com.helco.xf.cs01.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS0108002B_Service {
	
	/** ���� BOM ���ռ� üũ ���� */
	public void  specMatchCheck(
			                     BusinessContext ctx 
			                    ,Dataset ds_cond
			                    ,Dataset ds_list2
			                    ,Dataset ds_mach_rslt
			                    ) throws Exception;

	/** ���� BOM Duty ���� */
	public void  specDutyCheck(
			                     BusinessContext ctx 
			                    ,Dataset ds_cond
			                    ,Dataset ds_list2
			                    ,Dataset ds_duty_rslt
			                    ) throws Exception;
	
	/** ���ռ� ����� ���� */
	public void  specBomMatchVerify(
			                     BusinessContext ctx 
			                    ,Dataset ds_cond
			                    ) throws Exception;
	
}
