package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0020;
import hdel.sd.com.domain.Com0020ParamVO;

import java.util.List;

public interface Com0020D {  
	
	// 거래처목록 조회
	public List<Com0020> selectListLifnr(Com0020ParamVO param);

}
