package webservice.stub.eai;

import webservice.stub.WSStub;

public class EAIStub extends WSStub {

	public EAIStub(String dtpid, String mandt, String langu) {
		super(dtpid, mandt, langu);
	}

	@Override
	protected void initIndexClient() {
		setIdxMandt(4);
	}

	@Override
	protected String getHost(String dtpid) {
		if("HED".equals(dtpid)) {
			return "203.242.37.91";
		} else if("HEQ".equals(dtpid)) {
			return "203.242.37.91";
		} else if("HEP".equals(dtpid)) {
			return "203.242.40.208";
		}
		return "203.242.37.91";
	}

	/**
	 * EAI가 제공하는 WSDL URL중 마지막 부분은 URL parameter로 pass해야 하지만
	 * 최종 protocol은 "URL 마지막 부분 구성으로 sysid 마다 한개씩 고정된 SAP client로 한다"임.
	 * @param dtpid
	 * @return
	 */
	@Override
	protected String getClientInfo() {
		String dtpid = getDtpid();

		if("HED".equals(dtpid)) {
			return "910";
		} else if("HEQ".equals(dtpid)) {
			return "183";
		} else if("HEP".equals(dtpid)) {
			return "183";
		}
		return "183";
	}

	@Override
	protected String getURLParameter() {
		return "";
	}
}
