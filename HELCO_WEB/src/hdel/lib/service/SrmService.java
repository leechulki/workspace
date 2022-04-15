package hdel.lib.service;

import org.apache.ibatis.session.SqlSession;

public abstract class SrmService {

	public abstract void createDao(SqlSession sqlSession);
	
}
