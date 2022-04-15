package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0151;
import hdel.sd.ses.domain.Ses0161;

import java.util.List;

public interface Ses0161D {  
	
	// 기술검토요청 결과등록
	public void saveZSDT1058(Ses0161 param);
	
	// 기술검토요청 첨부 
	public void saveZSDT1059(Ses0161 param);		
}
