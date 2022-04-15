package hdel.lib.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.sap.domain.Vbeln;

import hdel.lib.dao.ZSDT1074D;
import hdel.lib.domain.ZSDT1074;

@Service
public class ZSDT1074S extends SrmService {

	private ZSDT1074D dao1074;

	public void createDao(SqlSession sqlSession) {
		dao1074 = sqlSession.getMapper(ZSDT1074D.class);
	}

	public String getLH(String mandt, Vbeln vbeln) {
		return dao1074.getLH(mandt, vbeln);
	}

	public ZSDT1074 select(String mandt, Vbeln vbeln) {
		return dao1074.select(mandt, vbeln);
	}

	public void insert(ZSDT1074 zsdt1074) {
		dao1074.insert(zsdt1074);
	}

	public void merge(ZSDT1074 zsdt1074) {
		dao1074.merge(zsdt1074);
	}

	public void update(ZSDT1074 zsdt1074) {
		dao1074.update(zsdt1074);
	}

	public void updateLH(ZSDT1074 zsdt1074) {
		dao1074.updateLH(zsdt1074);
	}
}