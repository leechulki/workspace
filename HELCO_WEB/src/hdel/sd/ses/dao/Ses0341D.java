package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0341;
import hdel.sd.ses.domain.Ses0341ParamVO;

import java.util.List;

public interface Ses0341D {  
	
	public List<Ses0341> selectList(Ses0341ParamVO param);

	public List<Ses0341> selectListFile(Ses0341ParamVO param);

}
