package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0250;
import hdel.sd.com.domain.Com0250ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface Com0250D {  
	
	// ������ȣ��� ��ȸ
	public List<Com0250> selectListQtnum(Com0250ParamVO param);
	public List<Com0250> selectListQtnumD(Com0250ParamVO param);
	
	
}
