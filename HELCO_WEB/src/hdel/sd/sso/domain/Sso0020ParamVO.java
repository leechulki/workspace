package hdel.sd.sso.domain;

import hdel.sd.smp.domain.SmpComParameterVO;

/**
 * ���ֺ���
 * 
 * �ۼ�  ����   : 2015.07
 * HISTORY  : �ű԰���
 */
@SuppressWarnings("serial")
public class Sso0020ParamVO extends SmpComParameterVO {

	// �빮�ڷ� ǥ���Ұ�.
	private String MANDT;			// Ŭ���̾�Ʈ
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
