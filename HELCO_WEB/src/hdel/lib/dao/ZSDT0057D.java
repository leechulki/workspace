package hdel.lib.dao;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.SapBool;
import com.sap.domain.Vbeln;

import hdel.lib.domain.ZSDT0057;

public interface ZSDT0057D {
	public ZSDT0057 select(@Param("mandt") String mandt, @Param("vbeln") Vbeln vbeln);
	public ZSDT0057 select(@Param("mandt") String mandt, @Param("vbeln") Vbeln vbeln, @Param("canfg") SapBool canfg);
	public void update(ZSDT0057 zsdt0057);
}