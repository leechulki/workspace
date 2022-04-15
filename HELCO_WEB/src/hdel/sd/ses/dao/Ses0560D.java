package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0560;
import hdel.sd.ses.domain.Ses0560ParamVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface Ses0560D {  
	
	public List<Ses0560> selectListHeader(Ses0560ParamVO param);
	public int updateZSDT1046(Map<String, Object> map);

}
