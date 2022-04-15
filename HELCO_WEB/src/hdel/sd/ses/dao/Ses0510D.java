package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0510;
import hdel.sd.ses.domain.Ses0510ParamVO;

import java.util.List;

public interface Ses0510D {  
	public List<Ses0510> selectPartCd(Ses0510ParamVO param);
	public List<Ses0510> selectDetailCd(Ses0510ParamVO param);
	public List<Ses0510> selectCheckId(Ses0510ParamVO param);
	public List<Ses0510> selectDeviationList(Ses0510ParamVO param);
}
