package webservice.stub.sap;

import tit.service.business.config.ConfigUtility;
import webservice.stub.WSStub;

public class SAPStub extends WSStub {
	public SAPStub(String dtpid, String mandt, String langu) {
		super(dtpid, mandt, langu);
	}

	@Override
	protected void initIndexClient() {
		setIdxMandt(9);
	}

	@Override
	protected String getHost(String dtpid) {
		return ConfigUtility.getString("WS_" + dtpid);
	}

	@Override
	protected String getClientInfo() {
		return getMandt();
	}

	@Override
	protected String getURLParameter() {
		String langu = getLangu();
		return (langu.isEmpty() ? "" : "?sap-language="+langu);
	}

}
