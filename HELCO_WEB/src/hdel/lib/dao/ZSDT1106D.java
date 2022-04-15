package hdel.lib.dao;

import org.apache.ibatis.annotations.Param;

import hdel.lib.domain.ZSDT1106;

public interface ZSDT1106D {
	public ZSDT1106 select(@Param("mandt") String mandt, @Param("bsyym") String bsyym, @Param("sprno") String sprno);
}