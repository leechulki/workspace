package com.helco.xf.comm;

public interface DatasetHelper {
	/**
	 * Object에서 Dataset으로 변환될 때 각 컬럼의 값이 변환이 필요한 경우 처리 
	 * 
	 * @param dsColName
	 * @param colValue
	 * @param orgObj
	 * @return
	 */
	public Object getDsValue(String dsColName, Object colValue, Object orgObj );
}
