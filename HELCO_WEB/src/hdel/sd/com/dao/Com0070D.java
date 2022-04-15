package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0070;
import hdel.sd.com.domain.Com0070ParamVO;

import java.util.List;

public interface Com0070D {  
	
	// 거래처목록 조회
	public List<Com0070> selectList(Com0070ParamVO param);
	
}

