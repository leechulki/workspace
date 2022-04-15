package hdel.sd.sso.domain;

import com.tobesoft.platform.data.Dataset;

public class ZSDT1108 implements java.io.Serializable{
	
	public ZSDT1108() {
	}
	
	private String mandt;
	private String vbeln;
	private String hogi;
	private String jqprnum;
	private String jqprno;
	private String remarkseq;
	private String remarkhogi;
	private String remarkrow;
	private String erdat;
	private String erzet;
	private String ernam;
	private String aedat;
	private String aezet;
	private String aenam;
	private String waers;
	private String iwbtrorg;
	private String seq;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public String getHogi() {
		return hogi;
	}
	public void setHogi(String hogi) {
		this.hogi = hogi;
	}
	public String getJqprnum() {
		return jqprnum;
	}
	public void setJqprnum(String jqprnum) {
		this.jqprnum = jqprnum;
	}
	public String getJqprno() {
		return jqprno;
	}
	public void setJqprno(String jqprno) {
		this.jqprno = jqprno;
	}
	public String getRemarkseq() {
		return remarkseq;
	}
	public void setRemarkseq(String remarkseq) {
		this.remarkseq = remarkseq;
	}
	public String getRemarkhogi() {
		return remarkhogi;
	}
	public void setRemarkhogi(String remarkhogi) {
		this.remarkhogi = remarkhogi;
	}
	public String getRemarkrow() {
		return remarkrow;
	}
	public void setRemarkrow(String remarkrow) {
		this.remarkrow = remarkrow;
	}
	public String getErdat() {
		return erdat;
	}
	public void setErdat(String erdat) {
		this.erdat = erdat;
	}
	public String getErzet() {
		return erzet;
	}
	public void setErzet(String erzet) {
		this.erzet = erzet;
	}
	public String getErnam() {
		return ernam;
	}
	public void setErnam(String ernam) {
		this.ernam = ernam;
	}
	public String getAedat() {
		return aedat;
	}
	public void setAedat(String aedat) {
		this.aedat = aedat;
	}
	public String getAezet() {
		return aezet;
	}
	public void setAezet(String aezet) {
		this.aezet = aezet;
	}
	public String getAenam() {
		return aenam;
	}
	public void setAenam(String aenam) {
		this.aenam = aenam;
	}
	
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getIwbtrorg() {
		return iwbtrorg;
	}
	public void setIwbtrorg(String iwbtrorg) {
		this.iwbtrorg = iwbtrorg;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public static Dataset getDataset() {
		Dataset ds = new Dataset();
		ds.addColumn("MANDT", (short)1, 3);
		ds.addColumn("VBELN", (short)1, 10);
		ds.addColumn("HOGI",(short)1,24);
		ds.addColumn("JQPRNUM",(short)1,10);
		ds.addColumn("JQPRNO",(short)1,20);
		ds.addColumn("REMARKHOGI",(short)1,24);
		ds.addColumn("REMARKROW",(short)1,2);
		ds.addColumn("ERDAT",(short)1,8);
		ds.addColumn("ERZET",(short)1,6);
		ds.addColumn("ERNAM",(short)1,12);
		ds.addColumn("AEDAT",(short)1,8);
		ds.addColumn("AEZET",(short)1,6);
		ds.addColumn("AENAM",(short)1,12);
		ds.addColumn("IWBTRORG",(short)1,13);
		ds.addColumn("WAERS",(short)1,15);
		ds.addColumn("FLAG",(short)1,2);
		ds.addColumn("SEQ",(short)1,3);
		return ds;
	}
}
