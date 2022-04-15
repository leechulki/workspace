package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0441;
import hdel.sd.ses.domain.Ses0441ParamVO;

import java.util.List;

public interface Ses0441D {

	public List<Ses0441> selectList(Ses0441ParamVO param);
	public List<Ses0441> selectListMax(Ses0441ParamVO param);
	public List<Ses0441> selectListFile(Ses0441ParamVO param);
	public List<Ses0441> selectListMaxFile(Ses0441ParamVO param);
	public List<Ses0441> selectMaxRqser(Ses0441 param);
	
	public void insertHeader(Ses0441 param);
	public void updateHeader(Ses0441 param);
	public void updateRequest(Ses0441 param);
	public void updateApproval(Ses0441 param);
	public void updateOutdate(Ses0441 param);
	public void updateOutfinish(Ses0441 param);
	
	public void deleteFile(Ses0441 param);
	public void insertFile(Ses0441 param);
	public void updateFile(Ses0441 param);
	
	public void deleteHeader(Ses0441 param);
	public void deleteFile2(Ses0441 param);

}
