package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0430;
import hdel.sd.ses.domain.Ses0430ParamVO;

import java.util.List;

public interface Ses0430D {  
		
	public List<Ses0430> selectList(Ses0430ParamVO param);
}
