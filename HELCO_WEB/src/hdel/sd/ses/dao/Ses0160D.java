package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0160;
import hdel.sd.ses.domain.Ses0160ParamVO;

import java.util.List;

public interface Ses0160D {  
	
	public List<Ses0160> selectListHeader(Ses0160ParamVO param);

	public List<Ses0160> selectListDetail(Ses0160ParamVO param);

	// ��������û ����ó��
	public void receiptZSDT1058(Ses0160 param);

	// ��������û ���� ���ó��
	public void receiptcancelZSDT1058(Ses0160 param);

}
