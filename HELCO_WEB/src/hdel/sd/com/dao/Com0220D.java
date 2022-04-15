package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0220;
import hdel.sd.com.domain.Com0220ParamVO;

import java.util.List;

public interface Com0220D {  
	
	// 거래처목록 조회
	public List<Com0220> selectList(Com0220ParamVO param);
	
}

