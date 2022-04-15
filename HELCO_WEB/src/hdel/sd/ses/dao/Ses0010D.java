package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0010;
import hdel.sd.ses.domain.Ses0010ParamVO;

import java.util.List;
import java.util.Map;

public interface Ses0010D {  
	
	public List<Map> selectListAbrand(Ses0010ParamVO param);

	public List<Ses0010> selectListSpec(Ses0010ParamVO param);
	
	
}
