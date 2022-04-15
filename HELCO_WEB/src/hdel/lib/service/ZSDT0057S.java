package hdel.lib.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.sap.domain.SapBool;
import com.sap.domain.Vbeln;

import hdel.lib.dao.ZSDT0057D;
import hdel.lib.domain.ZSDT0057;

@Service
public class ZSDT0057S extends SrmService {

	private ZSDT0057D dao;

	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(ZSDT0057D.class);
	}

	public ZSDT0057 select(String mandt, Vbeln vbeln) {
		return select(mandt, vbeln, null);
	}
	public ZSDT0057 select(String mandt, Vbeln vbeln, SapBool canfg) {
		ZSDT0057 zsdt0057 = dao.select(mandt, vbeln, canfg);
		return zsdt0057 == null ? new ZSDT0057() : zsdt0057;
	}
	public void update(ZSDT0057 zsdt0057) {
		dao.update(zsdt0057);
	}
}