package hdel.sd.sso.dao;

import java.util.List;

import hdel.sd.sso.domain.Sso0212;

public interface Sso0212D {

	public List<Sso0212> searchAgentCommission(Sso0212 param);
	public void deleteAgentCommission(Sso0212 param);
	public void merge(List<Sso0212> param);
}
