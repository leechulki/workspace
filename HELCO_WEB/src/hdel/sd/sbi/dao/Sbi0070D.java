package hdel.sd.sbi.dao;

import hdel.sd.sbi.domain.Sbi0070;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface Sbi0070D {

	public List<Sbi0070> find(Sbi0070 param);
	public void save(Sbi0070 sbi0070) throws DataAccessException;
	
}
