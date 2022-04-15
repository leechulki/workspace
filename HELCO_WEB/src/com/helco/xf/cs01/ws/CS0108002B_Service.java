package com.helco.xf.cs01.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS0108002B_Service {
	
	/** 서비스 BOM 정합성 체크 수행 */
	public void  specMatchCheck(
			                     BusinessContext ctx 
			                    ,Dataset ds_cond
			                    ,Dataset ds_list2
			                    ,Dataset ds_mach_rslt
			                    ) throws Exception;

	/** 서비스 BOM Duty 수행 */
	public void  specDutyCheck(
			                     BusinessContext ctx 
			                    ,Dataset ds_cond
			                    ,Dataset ds_list2
			                    ,Dataset ds_duty_rslt
			                    ) throws Exception;
	
	/** 정합성 연산식 검증 */
	public void  specBomMatchVerify(
			                     BusinessContext ctx 
			                    ,Dataset ds_cond
			                    ) throws Exception;
	
}
