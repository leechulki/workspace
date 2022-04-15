package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0451;
import hdel.sd.ses.domain.Ses0451ParamVO;

import java.util.List;

public interface Ses0451D {  
	
	public List<Ses0451> selectListHeader(Ses0451ParamVO param);
	public List<Ses0451> selectListTemplateQtnum(Ses0451ParamVO param);
	public List<Ses0451> selectListTemplateSd120(Ses0451ParamVO param);
	public List<Ses0451> selectListFile(Ses0451ParamVO param);	
	public List<Ses0451> selectMaxZRqSeq(Ses0451 param);
	public List<Ses0451> selectListEmail(Ses0451ParamVO param);
	
	public void insertHeader(Ses0451 param);
	public void updateHeader(Ses0451 param);
	public void updateZrqstat(Ses0451 param);
	public void updateApproval(Ses0451 param);
	
	public void deleteFile(Ses0451 param);
	public void insertFile(Ses0451 param);
	public void updateFile(Ses0451 param);

	
}
