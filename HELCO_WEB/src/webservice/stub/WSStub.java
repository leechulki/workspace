package webservice.stub;

import org.apache.axis2.client.Stub;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties.Authenticator;

import tit.service.business.config.ConfigUtility;

public abstract class WSStub {
	protected Stub stub = null;

	private String dtpid;	//Development, Test, Production
	private String mandt;
	private String langu;
	private int idxMandt;

	public WSStub(String dtpid, String mandt, String langu) {
		this.dtpid = dtpid;
		this.mandt = mandt;
		this.langu = langu;
	}

	public Stub create(Class cls) {
		initIndexClient();

		try {
			stub = (Stub)cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		setAuthentication();
		adjustStubAddress();
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(2000000);

	    return stub;
	}

	private void setAuthentication() {
		Authenticator authenticator = new Authenticator();
		authenticator.setUsername(ConfigUtility.getString("WS_USER_ID"));
		authenticator.setPassword(ConfigUtility.getString("WS_USER_PWD"));
	    authenticator.setPreemptiveAuthentication(true);
	    stub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, authenticator);
	}

	private void adjustStubAddress() {
		String addr = stub._getServiceClient().getOptions().getTo().getAddress();
		String addrSplit[] = addr.split("/");
		String ipportSplit[] = addrSplit[2].split(":");

		addrSplit[2] = getHost(dtpid) + ":" + ipportSplit[1];
		addrSplit[idxMandt] = getClientInfo();

		addr = "";
		for (String str : addrSplit) {
			if (addr.equals("") == false)
				addr += "/";
			addr += str;
		}
		addr += getURLParameter();

		stub._getServiceClient().getOptions().getTo().setAddress(addr);
	}

	protected abstract void initIndexClient();
	protected abstract String getHost(String dtpid);
	protected abstract String getClientInfo();
	protected abstract String getURLParameter();

	public String getDtpid() {
		return dtpid;
	}

	public int getIdxMandt() {
		return idxMandt;
	}
	public void setIdxMandt(int idxMandt) {
		this.idxMandt = idxMandt;
	}
	public String getLangu() {
		return langu;
	}
	public void setLangu(String langu) {
		this.langu = langu;
	}
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
}
