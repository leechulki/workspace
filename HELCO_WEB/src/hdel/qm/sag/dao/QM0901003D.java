package hdel.qm.sag.dao;

import hdel.qm.sag.domain.QM0901003;
import hdel.qm.sag.domain.QM0901003ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface QM0901003D {  
	
	// 거래처목록 조회
	public List<QM0901003> selectListSman(QM0901003ParamVO param);
		
}
