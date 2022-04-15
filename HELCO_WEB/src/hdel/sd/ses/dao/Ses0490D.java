package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0490;
import hdel.sd.ses.domain.Ses0490ParamVO;

import java.util.List;

public interface Ses0490D {  
	public List<Ses0490> selectListTemplate(Ses0490ParamVO param);
	public int mergeZSDT1090(Ses0490 param);
}
