package hdel.lib.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import hdel.lib.domain.ZSDT0167;

public interface ZSDT0167D {
	public List<ZSDT0167> select(@Param("mandt") String mandt, @Param("qtnum") String qtnum);
	public void merge(ZSDT0167 zsdt0167) throws DataAccessException;
	public void insert(ZSDT0167 zsdt0167) throws DataAccessException;
	public void update(ZSDT0167 zsdt0167) throws DataAccessException;
	public void delete(@Param("mandt") String mandt, @Param("qtnum") String qtnum) throws DataAccessException;
}
