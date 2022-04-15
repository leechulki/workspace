package hdel.lib.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.dao.ZSDT0168D;
import hdel.lib.domain.ZSDT0168;


@Service
public class ZSDT0168S extends SrmService {

	private ZSDT0168D dao0168;
	
	public void createDao(SqlSession sqlSession) { 
		dao0168 = sqlSession.getMapper(ZSDT0168D.class);
	}
	
	public void update(ZSDT0168 zsdt0168) throws Exception {
		if(zsdt0168 == null)
			return;

		try {
			dao0168.merge(zsdt0168);
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