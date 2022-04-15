package hdel.qm.sag.service;

import hdel.lib.service.SrmService;
import hdel.qm.sag.dao.QM0901003D;
import hdel.qm.sag.domain.QM0901003;
import hdel.qm.sag.domain.QM0901003ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class QM0901003S extends SrmService {

	private QM0901003D QM0901003Dao;
	
	public void createDao(SqlSession sqlSession) {
		QM0901003Dao = sqlSession.getMapper(QM0901003D.class);
	}
	
	public List<QM0901003> find(QM0901003ParamVO param) {
		return QM0901003Dao.selectListSman(param);
	}  
	
}
