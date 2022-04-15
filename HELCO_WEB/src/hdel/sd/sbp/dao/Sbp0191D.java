package hdel.sd.sbp.dao;
 
import hdel.sd.sbp.domain.Sbp0191;
import hdel.sd.sbp.domain.Sbp0191ParamVO;

import java.util.List;

public interface Sbp0191D {  
	
	List<Sbp0191> selectListDetail(Sbp0191ParamVO param);
	
	int insertZSDT1006(Sbp0191 param);
	
	int deleteZSDT1006(Sbp0191 param);
	
	int insertZSDT1007(Sbp0191 param);
	
	int deleteZSDT1007(Sbp0191 param);
	
	int insertZSDT1008(Sbp0191 param);
	
	int deleteZSDT1008(Sbp0191 param);
}
