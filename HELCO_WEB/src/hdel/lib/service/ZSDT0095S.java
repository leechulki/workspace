package hdel.lib.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.dao.ZSDT0095D;
import hdel.lib.domain.ZSDT0095;

@Service
public class ZSDT0095S extends SrmService {

	private ZSDT0095D dao0095;

	public void createDao(SqlSession sqlSession) {
		dao0095 = sqlSession.getMapper(ZSDT0095D.class);
	}

	public ZSDT0095 select(String mandt, String vbeln) {
		ZSDT0095 zsdt0095 = dao0095.select(new ZSDT0095(mandt, vbeln, false));
		return zsdt0095 == null ? new ZSDT0095() : zsdt0095;
	}

	public void merge(ZSDT0095 zsdt0095) throws Exception {
		try {
			dao0095.merge(zsdt0095);
		} catch(Exception e) {
			throw e;
		}
	}

	public void lock(String mandt, String vbeln, Boolean lock, String userid) throws Exception {
		ZSDT0095 zsdt0095 = new ZSDT0095(mandt, vbeln, lock);
		zsdt0095.getTstmp().setTimeStamp(userid);

		merge(zsdt0095);
	}
}