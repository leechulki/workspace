package com.sap.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sap.conversion.ConvertedTarget;
import com.sap.conversion.Convertor;
import com.sap.conversion.ConvertorFactory;

public abstract class DatsType implements ConvertedTarget {
	private final String initValue = "00000000";
	private String value = "";
	private Convertor convertor;

	protected Integer conversionID=0;
	protected Integer length=8;

	protected DatsType() {
		initialize();
		setValue(initValue);
	}
	protected DatsType(String value) {
		initialize();
		setValue(value);
	}

	protected abstract void initializeConversionID();

	protected void initialize() {
		initializeConversionID();
		this.convertor = ConvertorFactory.getConvertor(this.conversionID);
	}

	public Integer getConversionID() {
		return conversionID;
	}
	public Integer getLength() {
		return length;
	}

	public boolean isInitial() {
		return isInitial(this.getValue());
	}
	private boolean isInitial(String value) {
		return value.equals(convertor.toInternal(this.initValue, this.length));
	}
	public String getValue() {	//guarantee internal
		return value;
	}
	public void setValue(String value) {
		this.value = convertor.toInternal(value, this.length);
	}
	public String toString() {	//guarantee external
		String value = convertor.toExternal(this.value);
		return isInitial(value) ? "" : value;
	}
}