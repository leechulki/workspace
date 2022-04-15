package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0540;
import hdel.sd.ses.domain.Ses0540ParamVO;

import java.util.List;

public interface Ses0540D {  
	public List<Ses0540> selectPartCd(Ses0540ParamVO param);
	public List<Ses0540> selectDetailCd(Ses0540ParamVO param);
	public List<Ses0540> selectCheckId(Ses0540ParamVO param);
	public List<Ses0540> selectDeviationListHeader(Ses0540ParamVO param);
	public List<Ses0540> selectDeviationListDetail(Ses0540ParamVO param);
	public List<Ses0540> selectMaxZrqno(Ses0540ParamVO Param);
	public void insertDeviationHeader(Ses0540ParamVO param);
	public void insertDeviationDetail(Ses0540ParamVO param);
	public void insertDeviationState(Ses0540ParamVO param);
	public void updateDeviationDetail(Ses0540ParamVO param);
	public void insertDeviationReceipt(Ses0540ParamVO param);
	public void updateDeviationDestat(Ses0540ParamVO param);
	public void updateDeviationReviewDetail(Ses0540ParamVO param);
	public void updateDeviationReviewHeader(Ses0540ParamVO param);
	public void updateDeviationHeader(Ses0540ParamVO param);
	
}
