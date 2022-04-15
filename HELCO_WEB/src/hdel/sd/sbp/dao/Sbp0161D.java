package hdel.sd.sbp.dao;
 
import hdel.sd.sbp.domain.Sbp0161;
import hdel.sd.sbp.domain.Sbp0161ParamVO;

import java.util.List;

public interface Sbp0161D {  
	
	List<Sbp0161> selectListDetail(Sbp0161ParamVO param);
	
	int insertZSDT1002(Sbp0161 param);
	
	int deleteZSDT1002(Sbp0161 param);
	
	int insertZSDT1003(Sbp0161 param);
	
	int deleteZSDT1003(Sbp0161 param);
	
	int insertZSDT1004(Sbp0161 param);
	
	int deleteZSDT1004(Sbp0161 param);
}
