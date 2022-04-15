package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0200;
import hdel.sd.com.domain.Com0200ParamVO;

import java.util.List;

public interface Com0200D {  
	
	// 거래처목록 조회
	public List<Com0200> selectList(Com0200ParamVO param);
	
}

