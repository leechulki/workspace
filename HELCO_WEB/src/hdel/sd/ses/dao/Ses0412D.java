package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0412;
import hdel.sd.ses.domain.Ses0412ParamVO;

import java.util.List;

public interface Ses0412D {

	public List<Ses0412> selectListHeader(Ses0412ParamVO param);
	public List<Ses0412> selectMaxZRqSeq(Ses0412 param);
	public List<Ses0412> selectListFile(Ses0412ParamVO param);
	
	public List<Ses0412> selectQtnumPrint(Ses0412ParamVO param);
	public List<Ses0412> selectProjectPrint(Ses0412ParamVO param);

	public List<Ses0412> selectQtnumTemplate(Ses0412ParamVO param);
	public List<Ses0412> selectZkunnr_Z3(Ses0412 param);

	public void insertHeader(Ses0412 param);
	public void insertHeaderPs(Ses0412 param);
	public void updateHeader(Ses0412 param);
	public void updateZrqstat(Ses0412 param);
	public void updateApproval(Ses0412 param);
	public void updateOutdate(Ses0412 param);
	public void updateOutfinish(Ses0412 param);
	
	public void deleteFile(Ses0412 param);
	public void insertFile(Ses0412 param);
	public void updateFile(Ses0412 param);
	
	public void deleteHeader(Ses0412 param);
	public void deleteFile2(Ses0412 param);

}
