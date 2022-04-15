package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0080;
import hdel.sd.ses.domain.Ses0080ParamVO;
import hdel.sd.ses.domain.Ses0110;

import java.util.List;

public interface Ses0080D {  
	
	public List<Ses0080> selectListHeader(Ses0080ParamVO param);

	public List<Ses0080> selectListDetail(Ses0080ParamVO param);

	// �������ο�û ����ó��
	public void receiptZSDT1057(Ses0080 param);

	// �������ο�û ���� ���ó��
	public void receiptcancelZSDT1057(Ses0080 param);

	public void updateFlagConfirmZSDT1046(Ses0080 param);
	
	public void updateFlagCancelZSDT1046(Ses0080 param);
}
