package hdel.sd.ssa.domain;

import hdel.sd.smp.domain.SmpComParameterVO;

/**
 * On-Hand �����ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public class Ssa0050ParamVO extends SmpComParameterVO {

	// �빮�ڷ� ǥ���Ұ�.
	private String MANDT;		// Ŭ���̾�Ʈ
	private String ZPYM;		// ��ȹ���f
	private String ZPYM_TO;		// ��ȹ���t
	private String WHERE;		// ��ȸ����
	 
	private String VKBUR_F;		// ����� from
	private String VKGRP_F;		// �����׷� from
	private String VKBUR_T;		// ����� to
	private String VKGRP_T;		// �����׷� to
	private String ZKUNNR;		// �������
	
	
	
	public String getZPYM() {
		return ZPYM;
	}
	public void setZPYM(String zPYM) {
		ZPYM = zPYM;
	}
	public String getZPYM_TO() {
		return ZPYM_TO;
	}
	public void setZPYM_TO(String zPYM_TO) {
		ZPYM_TO = zPYM_TO;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getWHERE() {
		return WHERE;
	}
	public void setWHERE(String wHERE) {
		WHERE = wHERE;
	}
	public String getVKBUR_F() {
		return VKBUR_F;
	}
	public void setVKBUR_F(String vKBUR_F) {
		VKBUR_F = vKBUR_F;
	}
	public String getVKGRP_F() {
		return VKGRP_F;
	}
	public void setVKGRP_F(String vKGRP_F) {
		VKGRP_F = vKGRP_F;
	}
	public String getVKBUR_T() {
		return VKBUR_T;
	}
	public void setVKBUR_T(String vKBUR_T) {
		VKBUR_T = vKBUR_T;
	}
	public String getVKGRP_T() {
		return VKGRP_T;
	}
	public void setVKGRP_T(String vKGRP_T) {
		VKGRP_T = vKGRP_T;
	}
	public String getZKUNNR() {
		return ZKUNNR;
	}
	public void setZKUNNR(String zKUNNR) {
		ZKUNNR = zKUNNR;
	}
	
}
