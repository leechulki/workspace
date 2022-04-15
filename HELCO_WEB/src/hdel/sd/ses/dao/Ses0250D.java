package hdel.sd.ses.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.ses.domain.Ses0250;
import hdel.sd.ses.domain.Ses0251;

public interface Ses0250D {  
	public List<Ses0250> searchOrder(Ses0250 param);
	public List<Ses0251> searchCharacteristics(Map<String, Object> map);
}