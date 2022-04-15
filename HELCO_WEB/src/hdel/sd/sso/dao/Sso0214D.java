package hdel.sd.sso.dao;

import java.util.List;

import hdel.sd.sso.domain.Sso0214;
import hdel.sd.sso.domain.Sso0214ParamVO;

public interface Sso0214D {
	// 대리점 딜러 영업미수현황 조회
	public List<Sso0214> find(Sso0214ParamVO param);
	
	 //대리점 딜러 영업미수현황 merge
	public void merge(List<Sso0214> param);
}
