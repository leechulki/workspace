package com.sap.domain;

import com.sap.conversion.ConvertorFactory;

public class Kunnr extends StringType {
	public Kunnr() {
		super();
	}
	public Kunnr(String value) {
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