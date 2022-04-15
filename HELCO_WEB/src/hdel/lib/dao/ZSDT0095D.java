package hdel.lib.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import hdel.lib.domain.ZSDT0095;

public interface ZSDT0095D {
	public ZSDT0095 select(ZSDT0095 zsdt0095);
	public void merge(ZSDT0095 zsdt0095) throws DataAccessException;
}
