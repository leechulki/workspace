package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0442 extends CommonDomain {
 
    private String mandt;
    private String pt_num;      //������Ʈ
    private String gl_num;        //����
    private String pt_name;     //����
    private String zspec;      //�Է���
    private String zsize;      //�μ�
    private String amount;     //�μ���
    private String waers;      //��
    private String zgubun;
    private String zuse;
    private float inrate;
    private String gubun;
   	
    public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getPt_num() {
		return pt_num;
	}
	public void setPt_num(String pt_num) {
		this.pt_num = pt_num;
	}	
	public String getGl_num() {
		return gl_num;
	}
	public void setGl_num(String gl_num) {
		this.gl_num = gl_num;
	}
	public String getPt_name() {
		return pt_name;
	}
	public void setPt_name(String pt_name) {
		this.pt_name = pt_name;
	}
	public String getZspec() {
		return zspec;
	}
	public void setZspec(String zspec) {
		this.zspec = zspec;
	}
	public String getZsize() {
		return zsize;
	}
	public void setZsize(String zsize) {
		this.zsize = zsize;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getZgubun() {
		return zgubun;
	}
	public void setZgubun(String zgubun) {
		this.zgubun = zgubun;
	}
	public String getZuse() {
		return zuse;
	}
	public void setZuse(String zuse) {
		this.zuse = zuse;
	}
	public float getInrate() {
		return inrate;
	}
	public void setInrate(float inrate) {
		this.inrate = inrate;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
}
