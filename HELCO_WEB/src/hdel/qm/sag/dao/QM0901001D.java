package hdel.qm.sag.dao;

import hdel.qm.sag.domain.QM0901001;
import hdel.qm.sag.domain.QM0901001ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface QM0901001D {  
	
	// �ŷ�ó��� ��ȸ
	public List<QM0901001> selectListSman(QM0901001ParamVO param);
		
}
