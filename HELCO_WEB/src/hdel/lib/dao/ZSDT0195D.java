package hdel.lib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import hdel.lib.domain.ZSDT0195;

public interface ZSDT0195D {
	public List<ZSDT0195> select(@Param("mandt") String mandt, @Param("atinn") String atinn, @Param("atnam") String atnam, @Param("delfg") String delfg);
	
}
