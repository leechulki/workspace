package com.sap.domain;

import com.sap.conversion.ConvertorFactory;

public class Spras extends StringType {
	public Spras() {
		super();
	}
	public Spras(String value) {
		super(value);
	}

	@Override
	protected void initializeConversionID() {
		this.conversionID = ConvertorFactory.ISOLA;
	}
	@Override
	protected void initializeLength() {
		this.length = 2;
	}
}