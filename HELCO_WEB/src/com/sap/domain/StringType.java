package com.sap.domain;

import com.sap.conversion.Convertor;
import com.sap.conversion.ConvertorFactory;
import com.sap.conversion.ConvertedTarget;

public abstract class StringType implements ConvertedTarget {
	private final String initValue = "";
	private String value = "";
	private Convertor convertor;

	protected Integer conversionID=0;
	protected Integer length=0;

	protected StringType() {
		initialize();
		setValue(initValue);
	}
	protected StringType(String value) {
		initialize();
		setValue(value);
	}

	protected abstract void initializeConversionID();
	protected abstract void initializeLength();

	protected void initialize() {
		initializeConversionID();
		initializeLength();
		this.convertor = ConvertorFactory.getConvertor(this.conversionID);
	}

	public Integer getConversionID() {
		return conversionID;
	}
	public Integer getLength() {
		return length;
	}

	public boolean isInitial() {
		return this.getValue().equals(convertor.toInternal(this.initValue, this.length));
	}
	public String getValue() {	//guarantee internal
		return value;
	}
	public void setValue(String value) {
		this.value = convertor.toInternal(value, this.length);
	}
	public String toString() {	//guarantee external
		return convertor.toExternal(this.value);
	}
}