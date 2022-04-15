package com.sap.conversion;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class AlphaConvertor implements Convertor {

	@Override
	public String toInternal(String value, Integer length) {
		String str = toExternal(value);
		if(NumberUtils.isNumber(str))
			str = StringUtils.leftPad(str, length, "0");
		return str;
	}

	@Override
	public String toExternal(String value) {
		String str = value==null ? "" : value;
		if(NumberUtils.isNumber(str)) {
			str = str.replaceAll("^[0]+", "");
			if (str.equals(""))
	            str = "0";
		}

		return str;
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