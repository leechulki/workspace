package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0150;
import hdel.sd.ses.domain.Ses0150ParamVO;

import java.util.List;

public interface Ses0150D {  
	
	public List<Ses0150> selectListHeader(Ses0150ParamVO param);

	public List<Ses0150> selectListDetail(Ses0150ParamVO param);

	// ��������û ����ó��
	public void receiptZSDT1058(Ses0150 param);

	// ��������û ���� ���ó��
	public void receiptcancelZSDT1058(Ses0150 param);

}
