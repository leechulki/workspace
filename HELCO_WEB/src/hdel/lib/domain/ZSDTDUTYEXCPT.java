package hdel.lib.domain;

import com.sap.domain.OrderNo;

public class ZSDTDUTYEXCPT {
	private static final long serialVersionUID = 1L;

	private String mandt;
	private OrderNo ordno;
	private Integer ordseq;
	private ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();

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
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}