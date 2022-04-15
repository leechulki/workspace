package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0290ParamVO extends ParameterVO {

	private String gtype;
	private String mandt;
	private String blockno;
	private String lang;


	public String getGtype() {
		return gtype;
	}
	public void setGtype(String gtype) {
		this.gtype = gtype;
	}
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getBlockno() {
		return blockno;
	}
	public void setBlockno(String blockno) {
		this.blockno = blockno;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
}
