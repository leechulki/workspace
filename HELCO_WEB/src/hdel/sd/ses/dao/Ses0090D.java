package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0090;
import hdel.sd.ses.domain.Ses0090ParamVO;

import java.util.List;

public interface Ses0090D {  
	
	public List<Ses0090> selectListHeader(Ses0090ParamVO param);


	// 견적승인/반려
	public void updateZSDT1057(Ses0090 param);

	public void updateFlagZSDT1046(Ses0090 param);

	public void updateFlagZSDT1001(Ses0090 param);

	// 해외대리점 정보 처리
	public List<Ses0090> selectListAgentHeader(Ses0090ParamVO param);

	// Detail 정보 조회 공통 (국내, 해외대리점)
	public List<Ses0090> selectListDetail(Ses0090ParamVO param);
	
	public List<Ses0090> selectListAgentDetail(Ses0090ParamVO param);

	// 해외대리점 정보 조회
	public List<Ses0090> selectKunnr(Ses0090ParamVO param);

	// SPEC 정보 조회
	public List<Ses0090> searchSpecList(Ses0090ParamVO param);
}
