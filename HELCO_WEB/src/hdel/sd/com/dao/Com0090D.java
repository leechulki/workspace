package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0090;
import hdel.sd.com.domain.Com0090ParamVO;

import java.util.List;

public interface Com0090D {  
	
	// 거래처목록 조회
	public List<Com0090> selectList(Com0090ParamVO param);
	
}

