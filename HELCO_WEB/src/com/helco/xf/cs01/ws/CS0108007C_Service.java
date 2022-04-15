package com.helco.xf.cs01.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS0108007C_Service {

	
	/** 데이터 체크 정합성 데이터 저장 */
	public void  saveMatchData( BusinessContext ctx) throws Exception;
	
	
	/** 정합성 연산수식 사양특성 저장 */
	public void  calSpecInsert(
			                     BusinessContext ctx
			                    ) throws Exception;
	
}
