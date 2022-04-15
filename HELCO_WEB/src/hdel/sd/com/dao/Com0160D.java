package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0160;
import hdel.sd.com.domain.Com0160ParamVO;

import java.util.List;

public interface Com0160D {  
	
	// 거래처목록 조회
	public List<Com0160> selectList(Com0160ParamVO param);
	public List<Com0160> selectList_l(Com0160ParamVO param);
	
}

