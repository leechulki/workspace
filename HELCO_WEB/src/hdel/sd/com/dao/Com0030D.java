package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0020;
import hdel.sd.com.domain.Com0020ParamVO;
import hdel.sd.com.domain.Com0030;
import hdel.sd.com.domain.Com0030ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface Com0030D {  
	
	// 거래처목록 조회
	public List<Com0030> selectListSman(Com0030ParamVO param);
	
	public List<Com0030> selectLangLogin(Com0030ParamVO param);
	
}
