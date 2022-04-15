package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0442;
import hdel.sd.ses.domain.Ses0442ParamVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface Ses0442D {  
	
	public List<Ses0442> selectListHeaderProject(Ses0442ParamVO param);

	public float selectinexrate(@Param("mandt") String sMandt, @Param("zgubun") String sZgubun, @Param("zuse") String sZuse);

}
