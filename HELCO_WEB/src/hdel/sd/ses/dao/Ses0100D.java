package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0070;
import hdel.sd.ses.domain.Ses0100;
import hdel.sd.ses.domain.Ses0100ParamVO;

import java.util.List;

public interface Ses0100D {  
	
	public List<Ses0100> selectListHeader(Ses0100ParamVO param);

	public List<Ses0100> selectListDetail(Ses0100ParamVO param);

	// 견적승인
	public void approvalZSDT1057(Ses0100 param);

	// 견적반려
	public void rejectZSDT1057(Ses0100 param);
	
	public void updateFlagConfirmZSDT1046(Ses0100 param);
	
	public void updateFlagCancelZSDT1046(Ses0100 param);

}
