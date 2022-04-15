package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0240;
import hdel.sd.com.domain.Com0240ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface Com0240D {  
	
	// 거래처목록 조회
	public List<Com0240> selectListSman(Com0240ParamVO param);
	
}
