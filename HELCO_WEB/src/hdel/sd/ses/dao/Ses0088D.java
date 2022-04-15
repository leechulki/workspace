package hdel.sd.ses.dao;

import java.util.List;
import hdel.sd.ses.domain.Ses0088;
import hdel.sd.ses.domain.Ses0088ParamVO;

public interface Ses0088D {
	public List<Ses0088> selectListFile(Ses0088ParamVO param);
	public void insertFile(Ses0088 param);
	public void updateFile(Ses0088 param);
	public void deleteFile(Ses0088 param);
}