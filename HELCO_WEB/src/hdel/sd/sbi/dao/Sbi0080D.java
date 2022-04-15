package hdel.sd.sbi.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import hdel.sd.sbi.domain.Sbi0080;

public interface Sbi0080D {

	public List<Sbi0080> find(Sbi0080 param);

	public int save(Sbi0080 param) throws DataAccessException;
	public int delete(Sbi0080 param) throws DataAccessException;

}
