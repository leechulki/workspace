package hdel.lib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.Lifnr;

import hdel.lib.domain.ZSDT0115;

public interface ZSDT0115D {
	public ZSDT0115 select(@Param("mandt") String mandt, @Param("lifnr") Lifnr lifnr);
	public List<ZSDT0115> getAll(@Param("mandt") String mandt, @Param("delfg") String delfg);
}