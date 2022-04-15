package hdel.sd.ses.dao;

import java.util.List;
import hdel.sd.ses.domain.Ses0611;
import hdel.sd.ses.domain.Ses0611ParamVO;

public interface Ses0611D {
	public List<Ses0611> selectListFile(Ses0611ParamVO param);
	public void insertFile(Ses0611 param);
	public void updateFile(Ses0611 param);
	public void deleteFile(Ses0611 param);
}