package hdel.lib.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.dao.ZSDT0161D;
import hdel.lib.domain.ZSDT0161;
import hdel.lib.domain.ZSDT1081;
import hdel.sd.ses.domain.Ses0400ParamVO;


@Service
public class ZSDT0161S extends SrmService {

	private ZSDT0161D dao0161;
	
	public void createDao(SqlSession sqlSession) { 
		dao0161 = sqlSession.getMapper(ZSDT0161D.class);
	}
	
	public void insertCistat(String mandt, String zrqseq, String cistat, String userid) throws Exception {
		if(mandt == null || zrqseq == null)
			return;

		try {
			List<ZSDT0161> ls0161 = new ArrayList<ZSDT0161>();
			ls0161 = select(mandt, zrqseq, cistat);
			if(ls0161.size() > 0) {
				for(ZSDT0161 zsdt0161 : ls0161) {
					zsdt0161.getTstmp().setTimeStamp(userid);
					update(zsdt0161);
				}
			}
			else {
				ZSDT0161 zsdt0161 = new ZSDT0161();
				zsdt0161.setMandt(mandt);
				zsdt0161.setZrqseq(zrqseq);
				zsdt0161.setCistat(cistat);
				zsdt0161.getTstmp().setTimeStamp(userid);
	
				ls0161.add(zsdt0161);
				insert(ls0161);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	public List<ZSDT0161> select(String mandt, String zrqseq, String cistat) {
		return dao0161.select(mandt, zrqseq, cistat);
	}
	public void insert(List<ZSDT0161> ls0161) throws Exception {
		if(ls0161.isEmpty())
			return;

		try {
			dao0161.insert(ls0161);
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(ZSDT0161 zsdt0161) throws Exception {
		if(zsdt0161 == null)
			return;

		try {
			dao0161.update(zsdt0161);
		} catch (Exception e) {
			throw e;
		}
	}

//	public void delete(List<ZSDT0161> ls0161) throws Exception {
//		if(ls0161.isEmpty())
//			return;
//
//		try {
//			dao0161.delete(ls0161);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
}