package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0183;
import hdel.sd.ses.domain.Ses0183ParamVO;

import java.util.List;
import java.util.Map;

public interface Ses0183D {

	List<Ses0183> selectZSDT1055(Ses0183ParamVO param);
	List<Ses0183> selectPrint(Map<String, Object> parmMap);	
}
