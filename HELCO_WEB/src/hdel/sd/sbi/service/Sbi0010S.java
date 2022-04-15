package hdel.sd.sbi.service;

import hdel.sd.sbi.dao.Sbi0010D;
import hdel.sd.sbi.domain.Sbi0010;
import hdel.sd.sbi.domain.Sbi0010ParamVO;

import org.springframework.stereotype.Service;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

@Service
public class Sbi0010S {
	private Sbi0010D dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Sbi0010D.class);
	}
	
	public List<Sbi0010> selectRegio(Sbi0010ParamVO param) {
		return dao.selectRegio(param);
	}
}
