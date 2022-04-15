package com.sap.domain;

import com.sap.conversion.ConvertorFactory;

public class Datum extends DatsType {
	public Datum() {	// throws ParseException {
		super();
	}
	public Datum(String value) {	// throws ParseException {
		super(value);
	}

	@Override
	protected void initializeConversionID() {
		this.conversionID = ConvertorFactory.DATUM;
	}
}