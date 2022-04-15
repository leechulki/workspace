package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0080;
import hdel.sd.com.domain.Com0080ParamVO;

import java.util.List;

public interface Com0080D {  
	
	// 거래처목록 조회
	public List<Com0080> selectList(Com0080ParamVO param);
	
}

