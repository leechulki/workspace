package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0320;
import hdel.sd.ses.domain.Ses0320ParamVO;

import java.util.List;

public interface Ses0320D {  
	
	public List<Ses0320> selectMinTemplate(Ses0320ParamVO param);
	public List<Ses0320> selectListTemp(Ses0320ParamVO param);
	public List<Ses0320> selectListQt(Ses0320ParamVO param);
	public List<Ses0320> selectList(Ses0320ParamVO param);
	public List<Ses0320> findHeaderSeq(Ses0320ParamVO param);
    // 2020 ºê·£µå
	public List<Ses0320> selectListPt(Ses0320ParamVO param);
	
	public int mergeHeader(Ses0320ParamVO param);
	public int mergeDetail(Ses0320ParamVO param);
	public int deleteHeader(Ses0320ParamVO param);
	public int deleteDetail(Ses0320ParamVO param);
}
