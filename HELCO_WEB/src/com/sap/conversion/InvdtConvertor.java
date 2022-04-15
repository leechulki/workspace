package com.sap.conversion;

public class InvdtConvertor implements Convertor {

	@Override
	public String toInternal(String value, Integer length) {
		return String.valueOf(String.format("%0"+length+"d", 99999999 - Integer.parseInt(new DatumConvertor().toInternal(value, length))));
	}

	@Override
	public String toExternal(String value) {
		return String.valueOf(String.format("%0"+value.length()+"d", 99999999 - Integer.parseInt(value)));
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