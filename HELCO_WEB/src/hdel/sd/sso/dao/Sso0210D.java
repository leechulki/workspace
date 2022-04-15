package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0210;
import hdel.sd.sso.domain.Sso0210ParamVO;

import java.util.List;

public interface Sso0210D {  
		
	public List<Sso0210> selectList(Sso0210ParamVO param);
	public List<Sso0210> selectDetail(Sso0210ParamVO param);
}
