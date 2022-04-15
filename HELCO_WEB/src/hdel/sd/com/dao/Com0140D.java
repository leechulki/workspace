package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0140;
import hdel.sd.com.domain.Com0140ParamVO;

import java.util.List;

public interface Com0140D {  
	
	// 거래처목록 조회
	public List<Com0140> selectList(Com0140ParamVO param);
	
}

