package com.sap.domain;

import com.sap.conversion.ConvertorFactory;

public class Lifnr extends StringType {
	public Lifnr() {
		super();
	}
	public Lifnr(String value) {
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