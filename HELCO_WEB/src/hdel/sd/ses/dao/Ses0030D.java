package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0030;
import hdel.sd.ses.domain.Ses0030ParamVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface Ses0030D {  
	
	public List<Ses0030> selectListHeader(Ses0030ParamVO param);

	public List<Ses0030> selectMaxQtnumInfo(@Param("mandt") String sMandt, @Param("qtnum") String sQtnum, @Param("qtser") String sQtser);
	
	public List<Ses0030> selectListDetail(Ses0030ParamVO param);
	
	public List<Ses0030> selectSameVbeln(Ses0030ParamVO param);

	public String isAutolpOnMaxSeq(@Param("mandt") String mandt, @Param("qtnum") String qtnum);
}