package com.helco.xf.comm;

public interface DatasetHelper {
	/**
	 * Object���� Dataset���� ��ȯ�� �� �� �÷��� ���� ��ȯ�� �ʿ��� ��� ó�� 
	 * 
	 * @param dsColName
	 * @param colValue
	 * @param orgObj
	 * @return
	 */
	public Object getDsValue(String dsColName, Object colValue, Object orgObj );
}
