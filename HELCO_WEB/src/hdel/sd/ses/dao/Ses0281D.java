package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0281;
import hdel.sd.ses.domain.Ses0281ParamVO;

import java.util.List;

public interface Ses0281D {  
	
	public List<Ses0281> selectFormHeader(Ses0281ParamVO param);

	public List<Ses0281> selectList(Ses0281ParamVO param);
	
	public List<Ses0281> selectListDetail(Ses0281ParamVO param);

	// 원가의뢰요청 접수처리
	public void updateZSDT1054(Ses0281 param);

	// 원가의뢰요청 접수 취소처리
	public void insertZSDT1049(Ses0281 param);
	
	public void updateFlagConfirmZSDT1046(Ses0281 param);
	
	public void updateFlagCancelZSDT1046(Ses0281 param);

}
