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
	 * EAI�� �����ϴ� WSDL URL�� ������ �κ��� URL parameter�� pass�ؾ� ������
	 * ���� protocol�� "URL ������ �κ� �������� sysid ���� �Ѱ��� ������ SAP client�� �Ѵ�"��.
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
