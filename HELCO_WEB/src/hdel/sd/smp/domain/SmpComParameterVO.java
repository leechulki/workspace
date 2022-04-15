package hdel.sd.smp.domain;

import hdel.lib.domain.ParameterVO;

public class SmpComParameterVO extends ParameterVO {

	private String MANDT;
	private String USER_ID;
	
	private String CODE;
	private String CODE_NAME;
	private String FILTER1;	//
	private String FILTER2;	//
	private String FILTER3;	//
	private String FILTER4;	//

	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getCODE_NAME() {
		return CODE_NAME;
	}
	public void setCODE_NAME(String cODE_NAME) {
		CODE_NAME = cODE_NAME;
	}
	public String getFILTER1() {
		return FILTER1;
	}
	public void setFILTER1(String fILTER1) {
		FILTER1 = fILTER1;
	}
	public String getFILTER2() {
		return FILTER2;
	}
	public void setFILTER2(String fILTER2) {
		FILTER2 = fILTER2;
	}
	public String getFILTER3() {
		return FILTER3;
	}
	public void setFILTER3(String fILTER3) {
		FILTER3 = fILTER3;
	}
	public String getFILTER4() {
		return FILTER4;
	}
	public void setFILTER4(String fILTER4) {
		FILTER4 = fILTER4;
	}
	
	
}
