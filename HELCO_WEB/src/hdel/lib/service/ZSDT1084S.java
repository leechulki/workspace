package hdel.lib.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.sap.domain.Vbeln;

import hdel.lib.dao.ZSDT1084D;
import hdel.lib.domain.ZSDT1084;

@Service
public class ZSDT1084S extends SrmService {

	private ZSDT1084D dao1084;

	public void createDao(SqlSession sqlSession) {
		dao1084 = sqlSession.getMapper(ZSDT1084D.class);
	}

	public ZSDT1084 select(String mandt, String zrqseq) {
		return dao1084.select(mandt, zrqseq);
	}

	public void merge(String mandt, String zrqseq, Vbeln vbeln, Integer seq, String userid) throws Exception {
		ZSDT1084 zsdt1084 = new ZSDT1084();
		zsdt1084.setMandt(mandt);
		zsdt1084.setZrqseq(zrqseq);
		zsdt1084.setVbeln(vbeln);
		zsdt1084.setSeq(seq);
		zsdt1084.getTstmp().setTimeStamp(userid);
		
		merge(zsdt1084);
	}

	public void merge(ZSDT1084 zsdt1084) throws Exception {
		try {
			dao1084.merge(zsdt1084);
		} catch(Exception e) {
			throw e;
		}
	}

	public void delete(String mandt, String zrqseq) throws Exception {
		try {
			dao1084.delete(mandt, zrqseq);
		} catch (Exception e) {
			throw e;
		}
	}
}