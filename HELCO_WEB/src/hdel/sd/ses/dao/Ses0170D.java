package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0170;
import hdel.sd.ses.domain.Ses0170ParamVO;

import java.util.List;

public interface Ses0170D {  
	
	public List<Ses0170> selectListHeader(Ses0170ParamVO param);

	public List<Ses0170> selectListDetail(Ses0170ParamVO param);

	// 원가의뢰요청 접수처리
	public void receiptZSDT1054(Ses0170 param);

	// 원가의뢰요청 접수 취소처리
	public void receiptcancelZSDT1054(Ses0170 param);

}
