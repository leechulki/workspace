package com.sap.domain;

import com.sap.conversion.ConvertorFactory;

public class Vbeln extends StringType {
	public Vbeln() {
		super();
	}
	public Vbeln(String value) {
		super(value);
	}

	@Override
	protected void initializeConversionID() {
		this.conversionID = ConvertorFactory.ALPHA;
	}
	@Override
	protected void initializeLength() {
		this.length = 10;
	}
}