package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0520;
import hdel.sd.ses.domain.Ses0520ParamVO;

import java.util.List;

public interface Ses0520D {  
	public List<Ses0520> selectPartCd(Ses0520ParamVO param);
	public List<Ses0520> selectDetailCd(Ses0520ParamVO param);
	public List<Ses0520> selectCheckId(Ses0520ParamVO param);
	public List<Ses0520> selectDeviationListHeader(Ses0520ParamVO param);
	public List<Ses0520> selectDeviationListDetail(Ses0520ParamVO param);
	public List<Ses0520> selectMaxZrqno(Ses0520ParamVO Param);
	public void insertDeviationHeader(Ses0520ParamVO param);
	public void insertDeviationDetail(Ses0520ParamVO param);
	public void insertDeviationState(Ses0520ParamVO param);
	public void updateDeviationDetail(Ses0520ParamVO param);
}
