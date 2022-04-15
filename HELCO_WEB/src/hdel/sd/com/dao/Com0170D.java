package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0170;
import hdel.sd.com.domain.Com0170ParamVO;

import java.util.List;

public interface Com0170D {  
	
	// 거래처목록 조회
	public List<Com0170> selectList(Com0170ParamVO param);
	
}

