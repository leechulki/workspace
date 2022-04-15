package com.sap.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

public class DatumConvertor implements Convertor {

	@Override
	public String toInternal(String value, Integer length) {
		String initDats = StringUtils.leftPad("", length, "0");
		String str = value==null ? initDats : value;
		if(str.equals(initDats) || value.equals(""))
			return initDats;

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		str = (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());

		return str;
	}

	@Override
	public String toExternal(String value) {
		return toInternal(value, 8);
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