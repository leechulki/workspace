package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0081;
import hdel.sd.ses.domain.Ses0081ParamVO;

import java.util.List;

public interface Ses0081D {  

	public List<Ses0081> selectListDetail(Ses0081ParamVO param);
}
