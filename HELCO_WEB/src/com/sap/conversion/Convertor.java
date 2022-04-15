package com.sap.conversion;

public interface Convertor {
	public String toInternal(String value, Integer length);
	public String toExternal(String value);
	public String toInternal(ConvertedTarget convTarget);
	public String toExternal(ConvertedTarget convTarget);
}