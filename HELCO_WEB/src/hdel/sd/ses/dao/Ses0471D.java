package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0401;
import hdel.sd.ses.domain.Ses0471;
import hdel.sd.ses.domain.Ses0471ParamVO;

import java.util.List;

public interface Ses0471D {

	public List<Ses0471> selectListHeader(Ses0471ParamVO param);
	public List<Ses0471> selectMaxZRqSeq(Ses0471 param);
	public List<Ses0471> selectListFile(Ses0471ParamVO param);
	
	public List<Ses0471> selectQtnumPrint(Ses0471ParamVO param);
	public List<Ses0471> selectProjectPrint(Ses0471ParamVO param);

	public List<Ses0471> selectQtnumTemplate(Ses0471ParamVO param);
	public List<Ses0471> selectZkunnr_Z3(Ses0471 param);
	
	public List<Ses0471> selectQtseq(Ses0471 param);

	public void insertHeader(Ses0471 param);
	public void updateHeader(Ses0471 param);
	public void updateZrqstat(Ses0471 param);
	public void updateApproval(Ses0471 param);
	public void updateOutdate(Ses0471 param);
	public void updateOutfinish(Ses0471 param);
	public void updateOutcandt(Ses0471 param);
	
	public void deleteFile(Ses0471 param);
	public void insertFile(Ses0471 param);
	public void updateFile(Ses0471 param);
	
	public void deleteHeader(Ses0471 param);
	public void deleteFile2(Ses0471 param);

}
