package hdel.sd.ses.domain;

import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;
import com.sap.domain.Spras;

import hdel.lib.domain.CommonDomain;

public class Ses0251 extends CommonDomain {
	private String mandt="";
	private OrderNo ordno = new OrderNo();
	private Integer ordseq = new Integer(0);
	private OrderItem orditem = new OrderItem();
	private Spras spras = new Spras();
	private String atnam="";
	private String atbez="";
	private String atwrt="";

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public OrderNo getOrdno() {
		return ordno;
	}
	public void setOrdno(OrderNo ordno) {
		this.ordno = ordno;
	}
	public Integer getOrdseq() {
		return ordseq;
	}
	public void setOrdseq(Integer ordseq) {
		this.ordseq = ordseq;
	}
	public OrderItem getOrditem() {
		return orditem;
	}
	public void setOrditem(OrderItem orditem) {
		this.orditem = orditem;
	}
	public Spras getSpras() {
		return spras;
	}
	public void setSpras(Spras spras) {
		this.spras = spras;
	}
	public String getAtnam() {
		return atnam;
	}
	public void setAtnam(String atnam) {
		this.atnam = atnam;
	}
	public String getAtbez() {
		return atbez;
	}
	public void setAtbez(String atbez) {
		this.atbez = atbez;
	}
	public String getAtwrt() {
		return atwrt;
	}
	public void setAtwrt(String atwrt) {
		this.atwrt = atwrt;
	}
}