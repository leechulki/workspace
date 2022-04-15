package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0280;
import hdel.sd.ses.domain.Ses0280ParamVO;

import java.util.List;

public interface Ses0280D {  
	
	public List<Ses0280> selectListHeader(Ses0280ParamVO param);

	public List<Ses0280> selectListDetail(Ses0280ParamVO param);

	// 원가의뢰요청 접수처리
	public void receiptZSDT1054(Ses0280 param);

	// 원가의뢰요청 접수 취소처리
	public void receiptcancelZSDT1054(Ses0280 param);

}
