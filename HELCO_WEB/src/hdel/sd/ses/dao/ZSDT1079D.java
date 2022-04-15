package hdel.sd.ses.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import hdel.sd.ses.domain.ZSDT1079;

public interface ZSDT1079D {

	public List<ZSDT1079> select(@Param("mandt") String mandt, @Param("zrqseq") String zrqseq);

	public void insert(List<ZSDT1079> param) throws DataAccessException;
	public void delete(@Param("mandt") String mandt, @Param("zrqseq") String zrqseq) throws DataAccessException;
}
