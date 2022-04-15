package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0310;
import hdel.sd.ses.domain.Ses0310ParamVO;

import java.util.List;

public interface Ses0310D {  
	
	public List<Ses0310> selectListSpec(Ses0310ParamVO param);
	
}
