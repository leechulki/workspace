package com.sap.conversion;

public class ConvertorFactory {
	private static int exitID = 0; 

	public final static int ALPHA = 1;
	public final static int DATUM = 2;
	public final static int INVDT = 3;
	public final static int ISOLA = 4;

	private static Convertor convertor = new DefaultConvertor();

	public static Convertor getConvertor(Integer conversionID) {
		if(exitID != conversionID)
			switch(conversionID) {
			case ALPHA:
				convertor = new AlphaConvertor();
				break;

			case DATUM:
				convertor = new DatumConvertor();
				break;

			case INVDT:
				convertor = new InvdtConvertor();
				break;

			case ISOLA:
				convertor = new IsolaConvertor();
				break;

			default:
			}

		return convertor;
	}
}