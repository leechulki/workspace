package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0260;
import hdel.sd.com.domain.Com0260ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface Com0260D {  
	
	// ������ȣ��� ��ȸ
	public List<Com0260> selectListQtnum(Com0260ParamVO param);
	
}
