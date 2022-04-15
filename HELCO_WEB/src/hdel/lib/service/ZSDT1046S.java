package hdel.lib.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.dao.ZSDT1046D;
import hdel.lib.domain.ZSDT1046;

@Service
public class ZSDT1046S extends SrmService {

	private ZSDT1046D dao1046;

	public void createDao(SqlSession sqlSession) {
		dao1046 = sqlSession.getMapper(ZSDT1046D.class);
	}

	public ZSDT1046 select(String mandt, String qtnum, Integer qtser) {
		return dao1046.select(mandt, qtnum, qtser);
	}
}