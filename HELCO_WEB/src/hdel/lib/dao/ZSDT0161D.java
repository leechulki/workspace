package hdel.lib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import hdel.lib.domain.ZSDT0161;

public interface ZSDT0161D {
	public List<ZSDT0161> select(@Param("mandt") String mandt, @Param("zrqseq") String zrqseq, @Param("cistat") String cistat);
	public void insert(List<ZSDT0161> ls0161) throws DataAccessException;
	public void update(ZSDT0161 zsdt0161) throws DataAccessException;
}
