package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0230;
import hdel.sd.com.domain.Com0230ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface Com0230D {  
	
	// �ŷ�ó��� ��ȸ
	public List<Com0230> selectListSman(Com0230ParamVO param);
	
}
