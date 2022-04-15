package hdel.sd.sbi.dao;

import hdel.sd.sbi.domain.Sbi0030;
import hdel.sd.sbi.domain.Sbi0030ParamVO;

import java.util.List;

public interface Sbi0030D {

	public List<Sbi0030> selectListHeader(Sbi0030ParamVO param);
	public List<Sbi0030> selectMaxInfNo(Sbi0030 param);
	public List<Sbi0030> selectListFile(Sbi0030ParamVO param);
	
	
	public void insertHeader(Sbi0030 param);
	public void updateHeader(Sbi0030 param);
	
	public void deleteFile(Sbi0030 param);
	public void insertFile(Sbi0030 param);
	public void updateFile(Sbi0030 param);

}
