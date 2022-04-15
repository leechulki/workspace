package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class AutoNumberParamVO extends ParameterVO {
	
	private String deptFlag;
	private String soQtFlag;
	private String mandt;
	
	public String getDeptFlag() {
		return deptFlag;
	}

	public void setDeptFlag(String deptFlag) {
		this.deptFlag = deptFlag;
	}
	
	public String getSoQtFlag() {
		return soQtFlag;
	}

	public void setSoQtFlag(String soQtFlag) {
		this.soQtFlag = soQtFlag;
	}
	
	public String getMandt() {
		return mandt;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
}