package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0200;
import hdel.sd.ses.domain.Ses0200ParamVO;

import java.util.List;

public interface Ses0200D {  

	public List<Ses0200> selectListDetail(Ses0200ParamVO param);
}
