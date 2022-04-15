package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0340;
import hdel.sd.ses.domain.Ses0340ParamVO;

import java.util.List;

public interface Ses0340D {  
	
	public List<Ses0340> selectList(Ses0340ParamVO param);

	public List<Ses0340> selectSpec(Ses0340ParamVO param);
	
	public List<Ses0340> selectCheck(Ses0340ParamVO param);

	public void saveZSDT1068(Ses0340 param); 

}
