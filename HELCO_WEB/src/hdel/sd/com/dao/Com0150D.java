package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0150;
import hdel.sd.com.domain.Com0150ParamVO;

import java.util.List;

public interface Com0150D {  
	
	// 거래처목록 조회
	public List<Com0150> selectList(Com0150ParamVO param);
	
}

