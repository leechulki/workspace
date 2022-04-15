package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0440;
import hdel.sd.ses.domain.Ses0440ParamVO;

import java.util.List;

public interface Ses0440D {  
	
	public List<Ses0440> selectListHeaderProject(Ses0440ParamVO param);

	public List<Ses0440> selectListDetail(Ses0440ParamVO param);

}
