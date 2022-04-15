package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0150;
import hdel.sd.ses.domain.Ses0151;
import hdel.sd.ses.domain.Ses0150ParamVO;

import java.util.List;

public interface Ses0151D {  
	
	// 기술검토요청 결과등록
	public void saveZSDT1058(Ses0151 param);
	
	// 기술검토요청 첨부 조회 
	public List<Ses0151> selectListDetail(Ses0150ParamVO param);
	
	// 기술검토요청 첨부 
	public void saveZSDT1059(Ses0151 param);	
	
	public List<Ses0151> selectListSeq(Ses0150ParamVO param);

}
