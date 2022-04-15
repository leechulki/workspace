package hdel.sd.set.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.Datum;

import hdel.sd.set.domain.Set0010;

public interface Set0010D {
	public List<Set0010> getValidCrtsc(@Param("mandt") String mandt, @Param("bsdat") Datum bsdat);
	public List<Set0010> searchMatrixMap(@Param("mandt") String mandt, @Param("bsdat") Datum bsdat, @Param("sprno") String sprno);
}