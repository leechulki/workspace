package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0110;
import hdel.sd.ses.domain.Ses0130;
import hdel.sd.ses.domain.Ses0130ParamVO;

import java.util.List;

public interface Ses0130D {  
	
	public List<Ses0130> selectListHeader(Ses0130ParamVO param);

	public List<Ses0130> selectListDetail(Ses0130ParamVO param);

	// ¿ø°¡ÀÇ·Ú½ÂÀÎ/¹Ý·Á
	public void updateZSDT1057(Ses0130 param);

	public void updateFlagZSDT1046(Ses0130 param);
}
