package com.sap.domain;

import com.sap.conversion.ConvertorFactory;

public class Posnr extends StringType {
	public Posnr() {
		super();
	}
	public Posnr(String value) {
		super(value);
	}

	@Override
	protected void initializeConversionID() {
		this.conversionID = ConvertorFactory.ALPHA;
	}
	@Override
	protected void initializeLength() {
		this.length = 06;
	}
}