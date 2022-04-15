package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0032;
import hdel.sd.sso.domain.Sso0032ParamVO;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface Sso0032D {
	
	public List<Sso0032> find(Sso0032ParamVO param);
	public void saveZsdt0219(Sso0032 sso0032) throws DataAccessException;	
	public void deleteZsdt0219(Sso0032 sso0032) throws DataAccessException;	

}
