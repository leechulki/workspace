package hdel.lib.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.dao.ZSDT1081D;
import hdel.lib.domain.ZSDT1081;


@Service
public class ZSDT1081S extends SrmService {

	private ZSDT1081D dao1081;
	
	public void createDao(SqlSession sqlSession) { 
		dao1081 = sqlSession.getMapper(ZSDT1081D.class);
	}
	
	public void insert(List<ZSDT1081> ls1081) throws Exception {
		if(ls1081.isEmpty())
			return;

		try {
			dao1081.insert(ls1081);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(ZSDT1081 zsdt1081) throws Exception {
		if(zsdt1081 == null)
			return;

		try {
			dao1081.update(zsdt1081);
		} catch (Exception e) {
			throw e;
		}
	}

	public void delete(List<ZSDT1081> ls1081) throws Exception {
		if(ls1081.isEmpty())
			return;

		try {
			dao1081.delete(ls1081);
		} catch (Exception e) {
			throw e;
		}
	}
}