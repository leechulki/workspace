package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0350;
import hdel.sd.ses.domain.Ses0350ParamVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface Ses0350D {  
	
	public List<Ses0350> selectListHeader(Ses0350ParamVO param);
	public Ses0350 selectZuam(@Param("mandt") String sMandt, @Param("qtnum") String sQtnum, @Param("qtser") String sQtser);
	public Ses0350 selectWavwr(@Param("mandt") String sMandt, @Param("pspid") String sQtnum, @Param("seq") String sQtser);
}
