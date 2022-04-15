package com.sap.conversion;

public class DefaultConvertor implements Convertor {

	@Override
	public String toInternal(String value, Integer length) {
		return toExternal(value);
	}

	@Override
	public String toExternal(String value) {
		return value==null ? "" : value;
	}

	@Override
	public String toInternal(ConvertedTarget convTarget) {
		return toInternal(convTarget.getValue(), convTarget.getLength());
	}

	@Override
	public String toExternal(ConvertedTarget convTarget) {
		return toExternal(convTarget.getValue());
	}
}