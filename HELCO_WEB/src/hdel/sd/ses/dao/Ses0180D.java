package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0180;
import hdel.sd.ses.domain.Ses0180ParamVO;

import java.util.List;

public interface Ses0180D {

	List<Ses0180> selectZSDT1055(Ses0180ParamVO param);
	
	List<Ses0180> selectZSDT0181(Ses0180ParamVO param);
	
	void mergeZSDT1055(Ses0180 param);
	
	void deleteZSDT1055(Ses0180 param);
	
	void updateZSDT1001(Ses0180 param);
	
	void updateZSDT1046(Ses0180 param);
	
	List<Ses0180> selectZSDT1046(Ses0180 param);
}
