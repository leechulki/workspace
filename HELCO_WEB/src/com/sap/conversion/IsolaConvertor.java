package com.sap.conversion;

public class IsolaConvertor implements Convertor {
	private String[][] t002 = new String[][]{
		{"0","SR"},
		{"1","ZH"},
		{"2","TH"},
		{"3","KO"},
		{"4","RO"},
		{"5","SL"},
		{"6","HR"},
		{"7","MS"},
		{"8","UK"},
		{"9","ET"},
		{"A","AR"},
		{"B","HE"},
		{"C","CS"},
		{"D","DE"},
		{"E","EN"},
		{"F","FR"},
		{"G","EL"},
		{"H","HU"},
		{"I","IT"},
		{"J","JA"},
		{"K","DA"},
		{"L","PL"},
		{"M","ZF"},
		{"N","NL"},
		{"O","NO"},
		{"P","PT"},
		{"Q","SK"},
		{"R","RU"},
		{"S","ES"},
		{"T","TR"},
		{"U","FI"},
		{"V","SV"},
		{"W","BG"},
		{"X","LT"},
		{"Y","LV"},
		{"Z","Z1"},
		{"a","AF"},
		{"b","IS"},
		{"c","CA"},
		{"d","SH"},
		{"i","ID"}
	};

	@Override
	public String toInternal(String value, Integer length) {
		if(value == null) return "";

		for(int i=0; i < t002.length; i++) {
			if(t002[i][1].equals(value.toUpperCase()))
				return t002[i][0];
		}
		return "";
	}

	@Override
	public String toExternal(String value) {
		for(int i=0; i < t002.length; i++) {
			if(t002[i][0].equals(value))
				return t002[i][1];
		}
		return "";
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