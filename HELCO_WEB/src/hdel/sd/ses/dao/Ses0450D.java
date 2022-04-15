package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0450;
import hdel.sd.ses.domain.Ses0450ParamVO;

import java.util.List;

public interface Ses0450D {  
	
	public List<Ses0450> selectListHeaderProject(Ses0450ParamVO param);
	
	public List<Ses0450> selectListHeaderQtnum(Ses0450ParamVO param);

	public List<Ses0450> selectListDetail(Ses0450ParamVO param);

	public List<Ses0450> selectRequestList(Ses0450ParamVO param);
}
