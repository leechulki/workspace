package hdel.sd.com.domain;

import hdel.lib.domain.ParameterVO;

public class Com0250ParamVO extends ParameterVO {

	private String mandt;		//Ŭ���̾�Ʈ
	private String qtnum;		// ������ȣ
	private String qtser; 		// ��������
	private String kunnr; 		// ����ȣ
	private String gsnam; 		// �����
	private String fr_qtdat; 		// ��������
	private String to_qtdat; 		// ��������
	
	private String gubun;
		
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getQtnum() {
		return qtnum;
	}
	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}
	public String getQtser() {
		return qtser;
	}
	public void setQtser(String qtser) {
		this.qtser = qtser;
	}	
	public String getKunnr() {
		return kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	public String getGsnam() {
		return gsnam;
	}
	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getFr_qtdat() {
		return fr_qtdat;
	}
	public void setFr_qtdat(String fr_qtdat) {
		this.fr_qtdat = fr_qtdat;
	}
	public String getTo_qtdat() {
		return to_qtdat;
	}
	public void setTo_qtdat(String to_qtdat) {
		this.to_qtdat = to_qtdat;
	}
}
