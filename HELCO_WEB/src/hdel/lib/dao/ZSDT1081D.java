package hdel.lib.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import hdel.lib.domain.ZSDT1081;

public interface ZSDT1081D {
	public void insert(List<ZSDT1081> ls1081) throws DataAccessException;
	public void update(ZSDT1081 zsdt1081) throws DataAccessException;
	public void delete(List<ZSDT1081> ls1081) throws DataAccessException;
}
