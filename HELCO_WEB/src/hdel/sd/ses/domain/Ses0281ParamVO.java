package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0281ParamVO extends ParameterVO {

	private String mandt;
	private String qtnum;
	private int qtser;
	private int zrqseq;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public int getZrqseq() {
		return zrqseq;
	}
	public void setZrqseq(int zrqseq) {
		this.zrqseq = zrqseq;
	}
	public String getQtnum() {
		return qtnum;
	}
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	public int getQtser() {
		return qtser;
	}
	public void setQtser(int qtser) {
		this.qtser = qtser;
	}
}
