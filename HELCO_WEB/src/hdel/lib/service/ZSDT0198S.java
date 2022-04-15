package hdel.lib.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.sap.domain.Lifnr;

import hdel.lib.dao.ZSDT0198D;
import hdel.lib.domain.ZSDT0198;


@Service
public class ZSDT0198S extends SrmService {

	private ZSDT0198D dao0198;
	
	public void createDao(SqlSession sqlSession) { 
		dao0198 = sqlSession.getMapper(ZSDT0198D.class);
	}
	
	public List<ZSDT0198> select(String mandt, String qtnum) {
		return dao0198.select(mandt, qtnum);
	}
	public void insertWhenNotExists(String mandt, String qtnum, Lifnr lifnr, String userid) throws Exception {
		ZSDT0198 zsdt0198 = new ZSDT0198();
		try {
			zsdt0198.setMandt(mandt);
			zsdt0198.setQtnum(qtnum);
			zsdt0198.setLifnr(lifnr);
			zsdt0198.getTstmp().setTimeStamp(userid);
			dao0198.insertWhenNotExists(zsdt0198);
		} catch (Exception e) {
			throw e;
		}
	}
}