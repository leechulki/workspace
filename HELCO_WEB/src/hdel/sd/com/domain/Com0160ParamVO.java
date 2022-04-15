package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0160ParamVO extends ParameterVO {

	private String mandt;		// 
	private String builduse;
	private String stext;
	private String where;
	private String builduse_l;

	public String getBuilduse_l() {
		return builduse_l;
	}

	public void setBuilduse_l(String builduse_l) {
		this.builduse_l = builduse_l;
	}

	public String getMandt() {
		return mandt;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
	}

	public String getBuilduse() {
		return builduse;
	}

	public void setBuilduse(String builduse) {
		this.builduse = builduse;
	}
	
	public String getStext() {
		return stext;
	}

	public void setStext(String stext) {
		this.stext = stext;
	}
	
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
}
