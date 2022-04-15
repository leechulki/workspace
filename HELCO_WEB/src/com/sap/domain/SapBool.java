package com.sap.domain;

public class SapBool {
	private Boolean value = false;

	public SapBool() {
	}
	public SapBool(Boolean value) {
		setValue(value);
	}
	public SapBool(String value) {
		setValue(value);
	}

	public boolean isInitial() {
		return this.value.equals(false);
	}
	public Boolean getValue() {
		return value;
	}
	public String toString() {
		return parseMPString(this.value);
	}
	public String toDBString() {
		return parseDBString(value);
	}
	public void setValue(Boolean value) {
		this.value = value;
	}
	public void setValue(String value) {
		setValue(parseBoolean(value));
	}
	public static Boolean parseBoolean(String value) {
		return "X".equals(value) ? true : false;
	}
	public static String parseDBString(Boolean value) {	//Boolean to SAP DB
		return true == value ? "X" : "";
	}
	public static String parseMPString(Boolean value) {	//Boolean to MiPlatform
		return true == value ? "1" : "0";
	}
}