package hdel.lib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.Datum;

import hdel.lib.domain.ZSDT1087;
import hdel.sd.set.domain.Set0010;

public interface ZSDT1087D {
	public List<ZSDT1087> select(@Param("mandt") String mandt, @Param("bsdat") Datum bsdat);
}