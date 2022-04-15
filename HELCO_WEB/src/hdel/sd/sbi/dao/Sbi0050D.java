package hdel.sd.sbi.dao;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.OrderItem;
import com.sap.domain.Spras;

import hdel.sd.sbi.domain.Sbi0050;

public interface Sbi0050D {
	public OrderItem searchQuotOrderItem(Sbi0050 param);
	public OrderItem searchProjOrderItem(Sbi0050 param);
	public String searchBlockName(@Param("mandt") String mandt, @Param("blockno") String blockno, @Param("spras") Spras spras);
	public String searchBlockName(@Param("mandt") String mandt, @Param("blockno") String blockno);
}