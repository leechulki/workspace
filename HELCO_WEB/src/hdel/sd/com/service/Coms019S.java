package hdel.sd.com.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Coms019D;
import hdel.sd.com.domain.Coms019;

@Service
public class Coms019S extends SrmService {
	private Coms019D Coms019Dao;

	public void createDao(SqlSession sqlSession) {
		Coms019Dao = sqlSession.getMapper(Coms019D.class);
	}

	public List<Coms019> search(Coms019 param) {
		return Coms019Dao.searchMatnrEA(param);
	}
}