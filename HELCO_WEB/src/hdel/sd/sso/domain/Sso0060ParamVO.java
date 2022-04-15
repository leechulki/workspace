package hdel.sd.sso.domain;

import hdel.lib.domain.ParameterVO;


/**
 * ���ֻ���(Sso0060ParamVO) ParameterVO
 * @Comment 
 *     	- Sso0060C(���ֻ���) ���� ���
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */ 


public class Sso0060ParamVO extends ParameterVO {

	private String MANDT;	// CLIENT
	private String QTNUM;	// ������ȣ
	private String QTSER;	// ��������
	private String VBELN;	// ������Ʈ��ȣ
	private String CMD;		// ó������ (��ȸ:DISP, ����:SAVE, ����:UPDT)
	private String CODE;	// ��Ʈ���ڵ� 

	private String VKBUR;	// �μ��ڵ�
	private String VKGRP;	// ���ڵ�
	
	private String AUART;	// ��������
	
	private String SDFIELD;	// ����, ���𵨸�, �ű� ����
	private String ZKUNNR;	// ����� �ڵ�

	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String value) {
		this.MANDT = value;
	} 
	public String getQTNUM() {
		return QTNUM;
	}
	public void setQTNUM(String value) {
		this.QTNUM = value;
	}
	public String getQTSER() {
		return QTSER;
	}
	public void setQTSER(String value) {
		this.QTSER = value;
	}
	public String getVBELN() {
		return VBELN;
	}
	public void setVBELN(String value) {
		this.VBELN = value;
	}
	public String getCMD() {
		return CMD;
	}
	public void setCMD(String value) {
		this.CMD = value;
	}
	public String geCODE() {
		return CODE;
	}
	public void setCODE(String value) {
		this.CODE = value;
	} 
	public String getVKBUR() {
		return VKBUR;
	}
	public void setVKBUR(String value) {
		this.VKBUR = value;
	} 
	public String getVKGRP() {
		return VKGRP;
	}
	public void setVKGRP(String value) {
		this.VKGRP = value;
	} 
	public String getAUART() {
		return AUART;
	}
	public void setAUART(String value) {
		this.AUART = value;
	}
	public String getSDFIELD() {
		return SDFIELD;
	}
	public void setSDFIELD(String sDFIELD) {
		SDFIELD = sDFIELD;
	}
	public String getZKUNNR() {
		return ZKUNNR;
	}
	public void setZKUNNR(String zKUNNR) {
		ZKUNNR = zKUNNR;
	}
	
}