package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0280;
import hdel.sd.ses.domain.Ses0280ParamVO;

import java.util.List;

public interface Ses0280D {  
	
	public List<Ses0280> selectListHeader(Ses0280ParamVO param);

	public List<Ses0280> selectListDetail(Ses0280ParamVO param);

	// �����Ƿڿ�û ����ó��
	public void receiptZSDT1054(Ses0280 param);

	// �����Ƿڿ�û ���� ���ó��
	public void receiptcancelZSDT1054(Ses0280 param);

}
