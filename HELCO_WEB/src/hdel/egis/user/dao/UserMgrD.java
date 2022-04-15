package hdel.egis.user.dao;

import java.util.List;

import hdel.egis.user.domain.UserParamVO;
import hdel.egis.user.domain.UserVO;

public interface UserMgrD {  
	
	public List<UserVO> selectUser(UserParamVO param);
	
}
