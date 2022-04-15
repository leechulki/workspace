package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0550;
import hdel.sd.ses.domain.Ses0550ParamVO;

import java.util.List;

public interface Ses0550D {  
	public List<Ses0550> selectTemplate(Ses0550ParamVO param);
	public int insertZSDT1073(Ses0550 param);
	public int insertZSDT1072(Ses0550 param);
}
