package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0330;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.domain.Com0330ParamVO;

import java.util.List;

public interface Com0330D {  
	
	public List<Com0330> selectList(Com0330ParamVO param);
	public List<ComboVO> searchAra(Com0330ParamVO param);
	public List<ComboVO> searchDmstat(Com0330ParamVO param);
}
