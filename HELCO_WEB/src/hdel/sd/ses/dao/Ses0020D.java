package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0020;
import hdel.sd.ses.domain.Ses0020ParamVO;

import java.util.List;

public interface Ses0020D {  
	
	public List<Ses0020> selectMinTemplate(Ses0020ParamVO param);
	public List<Ses0020> selectListTemp(Ses0020ParamVO param);
	public List<Ses0020> selectList(Ses0020ParamVO param);
	public List<Ses0020> findHeaderSeq(Ses0020ParamVO param);
	
	public int mergeHeader(Ses0020ParamVO param);
	public int mergeDetail(Ses0020ParamVO param);
	public int deleteHeader(Ses0020ParamVO param);
	public int deleteDetail(Ses0020ParamVO param);
}
