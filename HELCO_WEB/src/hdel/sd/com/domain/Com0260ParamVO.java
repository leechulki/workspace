package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0260ParamVO extends ParameterVO {

	private String mandt;		//클라이언트
	private String rqdat_f;		// 견적번호
	private String rqdat_t; 		// 견적차수
	private String sman; 		// 견적일자
	private String gsnam; 		// 공사명
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getRqdat_f() {
		return rqdat_f;
	}
	public void setRqdat_f(String rqdat_f) {
		this.rqdat_f = rqdat_f;
	}
	public String getRqdat_t() {
		return rqdat_t;
	}
	public void setRqdat_t(String rqdat_t) {
		this.rqdat_t = rqdat_t;
	}
	public String getSman() {
		return sman;
	}
	public void setSman(String sman) {
		this.sman = sman;
	}
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	

}
