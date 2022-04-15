package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0470;
import hdel.sd.ses.domain.Ses0470ParamVO;

import java.util.List;

public interface Ses0470D {  
	
	public List<Ses0470> selectListHeaderProject(Ses0470ParamVO param);
	
	public List<Ses0470> selectListHeaderQtnum(Ses0470ParamVO param);

	public List<Ses0470> selectListDetail(Ses0470ParamVO param);

	public List<Ses0470> selectRequestList(Ses0470ParamVO param);
}
