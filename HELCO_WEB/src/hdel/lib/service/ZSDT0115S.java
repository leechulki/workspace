package hdel.lib.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.sap.domain.Lifnr;

import hdel.lib.dao.ZSDT0115D;
import hdel.lib.domain.ZSDT0115;


@Service
public class ZSDT0115S extends SrmService {

	private ZSDT0115D dao0115;
	
	public void createDao(SqlSession sqlSession) { 
		dao0115 = sqlSession.getMapper(ZSDT0115D.class);
	}
	
	public ZSDT0115 select(String mandt, Lifnr lifnr) {
		return dao0115.select(mandt, lifnr);
	}
	public List<ZSDT0115> getAll(String mandt, String delfg) {
		return dao0115.getAll(mandt, delfg);
	}
}