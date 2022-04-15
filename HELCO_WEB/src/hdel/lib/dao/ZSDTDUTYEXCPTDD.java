package hdel.lib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;
import com.sap.domain.Spras;

import hdel.lib.domain.ZSDTDUTYEXCPTD;

public interface ZSDTDUTYEXCPTDD {
	public List<ZSDTDUTYEXCPTD> select(@Param("mandt") String mandt, @Param("ordno") OrderNo ordno, @Param("ordseq") Integer ordseq, @Param("orditem") OrderItem orditem, @Param("spras") Spras spras);
	public List<ZSDTDUTYEXCPTD> selectAll(@Param("mandt") String mandt, @Param("ordno") OrderNo ordno, @Param("ordseq") Integer ordseq, @Param("orditem") OrderItem orditem, @Param("spras") Spras spras);
	public void merge(ZSDTDUTYEXCPTD dutyExcptD);
	public void delete(ZSDTDUTYEXCPTD dutyExcptD);
	public List<String> getExceptionalBlockNoList(@Param("mandt") String mandt, @Param("ordno") OrderNo ordno, @Param("ordseq") Integer ordseq, @Param("orditem") OrderItem orditem);
}