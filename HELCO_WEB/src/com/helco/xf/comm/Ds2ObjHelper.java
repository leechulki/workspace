package com.helco.xf.comm;

import com.tobesoft.platform.data.Dataset;

public interface Ds2ObjHelper {

	/**
	 * Data Move ���� 
	 * @param ds
	 * @param row
	 * @param obj
	 */
	public void endMoveRow(Dataset ds, int row, Object obj);
}
