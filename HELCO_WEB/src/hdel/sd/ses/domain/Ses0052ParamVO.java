package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0052ParamVO extends ParameterVO {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String MANDT;
	private String QTNUM;
	private String QTSER;
	
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getQTNUM() {
		return QTNUM;
	}
	public void setQTNUM(String qTNUM) {
		QTNUM = qTNUM;
	}
	public String getQTSER() {
		return QTSER;
	}
	public void setQTSER(String qTSER) {
		QTSER = qTSER;
	}
	
}
