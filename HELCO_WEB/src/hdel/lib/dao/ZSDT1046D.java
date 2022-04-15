package hdel.lib.dao;

import org.apache.ibatis.annotations.Param;

import hdel.lib.domain.ZSDT1046;

public interface ZSDT1046D {
	public ZSDT1046 select(@Param("mandt") String mandt, @Param("qtnum") String qtnum, @Param("qtser") Integer qtser);
}
