package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0085;
import hdel.sd.ses.domain.Ses0085ParamVO;

import java.util.List;

public interface Ses0085D {  

	public List<Ses0085> selectListDetail(Ses0085ParamVO param);
	
	public void updateZSDT1057(Ses0085 param);
	
	public void updateZSDT1046(Ses0085 param);
}
