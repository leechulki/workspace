package hdel.sd.ses.dao;

import java.util.List;

import hdel.sd.ses.domain.Ses0610;
import hdel.sd.ses.domain.Ses0610ParamVO;

public interface Ses0610D {
	// ���𵨸� �������� ��ȸ
	public List<Ses0610> find(Ses0610ParamVO param);
	
	// ���𵨸� �������� merge
	public void merge(List<Ses0610> param);
	
	// ���ܼ����� ��ȸ
	public List<Ses0610> findzsdt0222(Ses0610ParamVO param);
}
