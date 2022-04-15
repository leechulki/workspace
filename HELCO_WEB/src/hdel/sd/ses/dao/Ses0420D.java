package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0420;
import hdel.sd.ses.domain.Ses0420ParamVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface Ses0420D {  
		
	public List<Ses0420> selectList(Ses0420ParamVO param);
	public int saveAdditionalData(Ses0420 param);
}
