package hdel.sd.sbi.domain;
import hdel.lib.domain.CommonDomain;

public class Sbi0010 extends CommonDomain {
	
	private String code;
	private String code_name;
	
	public String getCode_name() {
		return code_name;
	}
	
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

}
