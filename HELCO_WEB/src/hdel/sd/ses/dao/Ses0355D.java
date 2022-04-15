package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0171;
import hdel.sd.ses.domain.Ses0355;
import hdel.sd.ses.domain.Ses0355ParamVO;

import java.util.List;

public interface Ses0355D {

	public List<Ses0355> selectContItem(Ses0355ParamVO param);
	
	public List<Ses0355> selectContItemStd(Ses0355ParamVO param);
	
	public List<Ses0355> selectListDetail(Ses0355ParamVO param);
	
	public List<Ses0355> selectContInfo(Ses0355ParamVO param);

	// 원가의뢰요청 접수 취소처리
	public void mergeZSDT1050(Ses0355 param);
	
	public void deleteZSDT1050(Ses0355 param);

	// 계약변경원가반영
	public void updateZSDT0091(Ses0355 param);

}
