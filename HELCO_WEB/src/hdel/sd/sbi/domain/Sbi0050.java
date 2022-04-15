package hdel.sd.sbi.domain;

import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;

import hdel.lib.domain.CommonDomain;

public class Sbi0050 extends CommonDomain {
	private static final long serialVersionUID = 1L;

	private String mandt;
	private OrderNo orderno;
	private Integer orderseq;
	private OrderItem orderitem;
	private String orderitemnm;
	private String blockno;
	private String blocknm;

	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public OrderNo getOrderno() {
		return orderno;
	}
	public void setOrderno(OrderNo orderno) {
		this.orderno = orderno;
	}
	public Integer getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(Integer orderseq) {
		this.orderseq = orderseq;
	}
	public OrderItem getOrderitem() {
		return orderitem;
	}
	public void setOrderitemnm(String orderitemnm) {
		this.orderitemnm = orderitemnm;
	}
	public String getOrderitemnm() {
		return orderitemnm;
	}
	public void setOrderitem(OrderItem orderitem) {
		this.orderitem = orderitem;
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
}