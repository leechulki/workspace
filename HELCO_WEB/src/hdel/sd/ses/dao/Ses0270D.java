package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0270;
import hdel.sd.ses.domain.Ses0270ParamVO;

import java.util.List;

public interface Ses0270D {  

	public List<Ses0270> selectList(Ses0270ParamVO param);
	
	public List<Ses0270> selectListDetail(Ses0270ParamVO param);

}
