package com.sap.domain;

import com.sap.conversion.ConvertorFactory;

public class Invdt extends StringType {
	public Invdt() {
		super();
	}
	public Invdt(String value) {
		super(value);
	}

	@Override
	protected void initializeConversionID() {
		this.conversionID = ConvertorFactory.INVDT;
	}
	@Override
	protected void initializeLength() {
		this.length = 8;		
	}
}