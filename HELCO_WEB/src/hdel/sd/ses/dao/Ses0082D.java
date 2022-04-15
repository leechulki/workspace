package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0082;
import hdel.sd.ses.domain.Ses0082ParamVO;

import java.util.List;

public interface Ses0082D {  

	public List<Ses0082> selectListDetail(Ses0082ParamVO param);
	
	public void updateZSDT1057(Ses0082 param);
	
	public void updateZSDT1046(Ses0082 param);
}
