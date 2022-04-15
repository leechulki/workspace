package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0442ParamVO extends ParameterVO {

	private String mandt;
	private String pt_num;
	private String gl_num;
	private String zgubun;
	private String zuse;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getPt_num() {
		return pt_num;
	}
	public void setPt_num(String pt_num) {
		this.pt_num = pt_num;
	}
	public String getGl_num() {
		return gl_num;
	}
	public void setGl_num(String gl_num) {
		this.gl_num = gl_num;
	}
	public String getZgubun() {
		return zgubun;
	}
	public void setZgubun(String zgubun) {
		this.zgubun = zgubun;
	}
	public String getZuse() {
		return zuse;
	}
	public void setZuse(String zuse) {
		this.zuse = zuse;
	}		
}
