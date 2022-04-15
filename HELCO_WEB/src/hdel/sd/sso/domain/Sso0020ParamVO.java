package hdel.sd.sso.domain;

import hdel.sd.smp.domain.SmpComParameterVO;

/**
 * 수주변경
 * 
 * 작성  일자   : 2015.07
 * HISTORY  : 신규개발
 */
@SuppressWarnings("serial")
public class Sso0020ParamVO extends SmpComParameterVO {

	// 대문자로 표시할것.
	private String MANDT;			// 클라이언트
	private String VBELN;

	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}	
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}

}
