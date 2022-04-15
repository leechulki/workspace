package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0530;
import hdel.sd.ses.domain.Ses0530ParamVO;

import java.util.List;

public interface Ses0530D {  
	public List<Ses0530> selectPartCd(Ses0530ParamVO param);
	public List<Ses0530> selectDetailCd(Ses0530ParamVO param);
	public List<Ses0530> selectCheckId(Ses0530ParamVO param);
	public List<Ses0530> selectDeviationList(Ses0530ParamVO param);
}
