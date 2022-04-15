package hdel.lib.domain;

import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;
import com.sap.domain.Spras;

public class ZSDTDUTYEXCPTD {
	private static final long serialVersionUID = 1L;

	private String mandt;
	private OrderNo ordno;
	private Integer ordseq;
	private OrderItem orditem;
	private String orditemnm;
	private String blockno;
	private String blocknm;
	private Spras spras = new Spras();
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
	public OrderItem getOrditem() {
		return orditem;
	}
	public void setOrditem(OrderItem orditem) {
		this.orditem = orditem;
	}
	public String getOrditemnm() {
		return orditemnm;
	}
	public void setOrditemnm(String orditemnm) {
		this.orditemnm = orditemnm;
	}
	public String getBlockno() {
		return blockno;
	}
	public void setBlockno(String blockno) {
		this.blockno = blockno;
	}
	public String getBlocknm() {
		return blocknm;
	}
	public void setBlocknm(String blocknm) {
		this.blocknm = blocknm;
	}
	public Spras getSpras() {
		return spras;
	}
	public void setSpras(Spras spras) {
		this.spras = spras;
	}
	public ZBCS_TIMESTAMP getTstmp() {
		return tstmp;
	}
	public void setTstmp(ZBCS_TIMESTAMP tstmp) {
		this.tstmp = tstmp;
	}
}