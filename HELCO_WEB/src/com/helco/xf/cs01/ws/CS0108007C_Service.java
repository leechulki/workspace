package com.helco.xf.cs01.ws;

import tit.biz.BusinessContext;

import com.tobesoft.platform.data.Dataset;

public interface CS0108007C_Service {

	
	/** ������ üũ ���ռ� ������ ���� */
	public void  saveMatchData( BusinessContext ctx) throws Exception;
	
	
	/** ���ռ� ������� ���Ư�� ���� */
	public void  calSpecInsert(
			                     BusinessContext ctx
			                    ) throws Exception;
	
}
