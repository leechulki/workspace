package hdel.lib.dao;

import org.springframework.dao.DataAccessException;

import hdel.lib.domain.ZSDT0168;

public interface ZSDT0168D {
//	public List<ZSDT0161> select(@Param("mandt") String mandt, @Param("zrqseq") String zrqseq, @Param("cistat") String cistat);
//	public void insert(List<ZSDT0161> ls0161) throws DataAccessException;
	public void merge(ZSDT0168 zsdt0168) throws DataAccessException;
}
