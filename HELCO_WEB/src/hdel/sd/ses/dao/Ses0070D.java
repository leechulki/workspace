package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0070;
import hdel.sd.ses.domain.Ses0070ParamVO;
import hdel.sd.ses.domain.Ses0110;

import java.util.List;

public interface Ses0070D {  
	
	public List<Ses0070> selectListHeader(Ses0070ParamVO param);

	public List<Ses0070> selectListDetail(Ses0070ParamVO param);

	// 斑利铰牢夸没 立荐 秒家贸府
	public void updateZSDT1057(Ses0070 param);

	public void updateFlagZSDT1046(Ses0070 param);
}
