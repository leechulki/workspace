package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0290;
import hdel.sd.ses.domain.Ses0290ParamVO;

import java.util.List;

public interface Ses0290D {  
	
	public List<Ses0290> selectListHeader(Ses0290ParamVO param);

	public List<Ses0290> selectListDetail(Ses0290ParamVO param);
	
	public void saveZSDTDUTY(Ses0290 param);
	
	public void saveZSDTDUTYD(Ses0290 param);
}
