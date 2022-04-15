package hdel.lib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.OrderNo;

import hdel.lib.domain.ZSDTDUTYEXCPT;

public interface ZSDTDUTYEXCPTD {
	public List<ZSDTDUTYEXCPT> select(@Param("mandt") String mandt, @Param("ordno") OrderNo ordno, @Param("ordseq") Integer ordseq);
	public void merge(ZSDTDUTYEXCPT dutyExcpt);
	public void delete(@Param("mandt") String mandt, @Param("ordno") OrderNo ordno, @Param("ordseq") Integer ordseq);
}