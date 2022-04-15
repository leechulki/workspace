package hdel.lib.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.dao.ZSDT0167D;
import hdel.lib.domain.ZSDT0167;

@Service
public class ZSDT0167S extends SrmService {

	private ZSDT0167D dao0167;

	public void createDao(SqlSession sqlSession) {
		dao0167 = sqlSession.getMapper(ZSDT0167D.class);
	}

	public List<ZSDT0167> select(String mandt, String qtnum) {
		return dao0167.select(mandt, qtnum);
	}

	public void merge(ZSDT0167 zsdt0167) throws Exception {
		try {
			dao0167.merge(zsdt0167);
		} catch(Exception e) {
			throw e;
		}
	}

	public void merge(String mandt, String qtnum, String tquot, String userid) throws Exception {
		ZSDT0167 zsdt0167 = new ZSDT0167();
		zsdt0167.setMandt(mandt);
		zsdt0167.setQtnum(qtnum);
		zsdt0167.setTquot(tquot);
		zsdt0167.getTstmp().setTimeStamp(userid);
		
		merge(zsdt0167);
	}

//	public void insertFirstTime(String mandt, String qtnum, String tquot, String userid) throws Exception {
//		insert(mandt, qtnum, tquot, userid);
//	}
//
	public void insert(ZSDT0167 zsdt0167) throws Exception {
		if (zsdt0167 == null)
			return;

		try {
			dao0167.insert(zsdt0167);
		} catch (Exception e) {
			throw e;
		}
	}
	public void insert(String mandt, String qtnum, String tquot, String userid) throws Exception {
		ZSDT0167 zsdt0167 = new ZSDT0167();
		zsdt0167.setMandt(mandt);
		zsdt0167.setQtnum(qtnum);
		zsdt0167.setTquot(tquot);
		zsdt0167.getTstmp().setTimeStamp(userid);

		insert(zsdt0167);
	}

	public void update(ZSDT0167 zsdt0167) throws Exception {
		if (zsdt0167 == null)
			return;

		try {
			dao0167.update(zsdt0167);
		} catch (Exception e) {
			throw e;
		}
	}

	public void delete(String mandt, String qtnum) throws Exception {
		try {
			dao0167.delete(mandt, qtnum);
		} catch (Exception e) {
			throw e;
		}
	}
}