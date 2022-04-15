package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0200ParamVO extends ParameterVO {

	private String mandt;		// 
	private String matnr; 		// 
	private String maktx; 		// 
	private String where;

	public String getMandt() {
		return mandt;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	
	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
}
