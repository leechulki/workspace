package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0110;
import hdel.sd.ses.domain.Ses0110ParamVO;

import java.util.List;

public interface Ses0110D {  
	
	public List<Ses0110> selectListHeader(Ses0110ParamVO param);

	public List<Ses0110> selectListDetail(Ses0110ParamVO param);

	// �����Ƿڿ�û ����/���ó��
	public void updateZSDT1057(Ses0110 param);

	public void updateFlagZSDT1046(Ses0110 param);
}
