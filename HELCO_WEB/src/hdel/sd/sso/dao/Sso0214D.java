package hdel.sd.sso.dao;

import java.util.List;

import hdel.sd.sso.domain.Sso0214;
import hdel.sd.sso.domain.Sso0214ParamVO;

public interface Sso0214D {
	// �븮�� ���� �����̼���Ȳ ��ȸ
	public List<Sso0214> find(Sso0214ParamVO param);
	
	 //�븮�� ���� �����̼���Ȳ merge
	public void merge(List<Sso0214> param);
}
