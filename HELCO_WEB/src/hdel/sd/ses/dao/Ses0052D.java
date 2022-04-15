package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0052;
import hdel.sd.ses.domain.Ses0052ParamVO;

import java.util.List;

public interface Ses0052D {

	public List<Ses0052> selectListHeader(Ses0052ParamVO param);
	
	public void saveHeader(Ses0052 param);
	public void updateHeader(Ses0052 param);
	public int selectQtn(Ses0052ParamVO param);
	public void deleteHeader(Ses0052 param);

}
