package hdel.lib.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.sap.domain.Vbeln;

import hdel.lib.domain.ZSDT1074;

public interface ZSDT1074D {
	public String getLH(@Param("mandt") String mandt, @Param("vbeln") Vbeln vbeln) throws DataAccessException;
	public ZSDT1074 select(@Param("mandt") String mandt, @Param("vbeln") Vbeln vbeln) throws DataAccessException;
	public void insert(ZSDT1074 zsdt1074) throws DataAccessException;
	public void merge(ZSDT1074 zsdt1074) throws DataAccessException;
	public void update(ZSDT1074 zsdt1074) throws DataAccessException;
	public void updateLH(ZSDT1074 zsdt1074) throws DataAccessException;
}