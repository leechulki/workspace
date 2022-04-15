package hdel.qm.sag.service;

import hdel.lib.service.SrmService;
import hdel.qm.sag.dao.QM0901001D;
import hdel.qm.sag.domain.QM0901001;
import hdel.qm.sag.domain.QM0901001ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class QM0901001S extends SrmService {

	private QM0901001D QM0901001Dao;
	
	public void createDao(SqlSession sqlSession) {
		QM0901001Dao = sqlSession.getMapper(QM0901001D.class);
	}
	
	public List<QM0901001> find(QM0901001ParamVO param) {
		return QM0901001Dao.selectListSman(param);
	}  
	
}
