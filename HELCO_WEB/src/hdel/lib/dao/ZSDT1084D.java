package hdel.lib.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import hdel.lib.domain.ZSDT1084;

public interface ZSDT1084D {
	public ZSDT1084 select(@Param("mandt") String mandt, @Param("zrqseq") String zrqseq);
	public void merge(ZSDT1084 zsdt1084) throws DataAccessException;
	public void delete(@Param("mandt") String mandt, @Param("zrqseq") String zrqseq);
}
