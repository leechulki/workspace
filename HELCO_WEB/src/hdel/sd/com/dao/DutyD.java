package hdel.sd.com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import hdel.sd.com.domain.Duty;
import hdel.sd.com.domain.DutyDet;

public interface DutyD {

	public Duty selectDutyUnit(@Param("mandt") String mandt, @Param("blockno") String blockno, @Param("lang") String lang);
	public Duty selectDutyUnit(@Param("mandt") String mandt, @Param("blockno") String blockno);
	
	public List<Duty> selectDuty(@Param("mandt") String mandt, @Param("gtype") String gtype, @Param("blockgbn") String blockgbn, @Param("lang") String lang);
	public List<Duty> selectDuty(@Param("mandt") String mandt, @Param("gtype") String gtype, @Param("blockgbn") String blockgbn);
	
	public List<DutyDet> selectListDutyD(String mandt, String blockno);
	
	public Map selectError(@Param("mandt") String mandt, @Param("class") String cls, @Param("atnam") String atnam, @Param("lang") String lang);
}
