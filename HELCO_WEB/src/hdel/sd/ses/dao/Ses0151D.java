package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0150;
import hdel.sd.ses.domain.Ses0151;
import hdel.sd.ses.domain.Ses0150ParamVO;

import java.util.List;

public interface Ses0151D {  
	
	// ��������û ������
	public void saveZSDT1058(Ses0151 param);
	
	// ��������û ÷�� ��ȸ 
	public List<Ses0151> selectListDetail(Ses0150ParamVO param);
	
	// ��������û ÷�� 
	public void saveZSDT1059(Ses0151 param);	
	
	public List<Ses0151> selectListSeq(Ses0150ParamVO param);

}
