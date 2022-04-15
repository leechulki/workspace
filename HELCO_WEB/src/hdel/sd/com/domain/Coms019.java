package hdel.sd.com.domain;

import com.sap.domain.Spras;

import hdel.lib.domain.CommonDomain;

public class Coms019 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt;
	private String code;
	private String codeName;
	private Spras spras;

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public Spras getSpras() {
		return spras;
	}
	public void setSpras(Spras spras) {
		this.spras = spras;
	}
}