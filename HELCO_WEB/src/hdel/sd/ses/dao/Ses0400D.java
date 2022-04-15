package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0400;
import hdel.sd.ses.domain.Ses0400ParamVO;
import hdel.sd.ses.domain.Ses0404;
import hdel.sd.ses.domain.Ses0404ParamVO;
import hdel.sd.ses.domain.ZSDT1048;

import java.util.List;

public interface Ses0400D {  
	
	public List<Ses0400> selectListHeaderProject(Ses0400ParamVO param);
	public List<Ses0400> selectListHeaderQtnum(Ses0400ParamVO param);
	public List<Ses0400> selectListDetail(Ses0400ParamVO param);
	public List<Ses0400> selectRequestList(Ses0400ParamVO param);
	public List<Ses0404> queryOutsourcingBlueprint(Ses0404ParamVO param);
	public List<Ses0404> updateDelayDays(Ses0404 singleRecord);
	public List<ZSDT1048> getAtnam(Ses0400ParamVO param);
	
}