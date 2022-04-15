package hdel.lib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import hdel.lib.domain.ZSDT0198;

public interface ZSDT0198D {
	public List<ZSDT0198> select(@Param("mandt") String mandt, @Param("qtnum") String qtnum);
	public void insertWhenNotExists(ZSDT0198 zsdt0198) throws DataAccessException;
}