package hdel.egis.user.service;

import hdel.egis.user.dao.UserMgrD;
import hdel.egis.user.domain.UserParamVO;
import hdel.egis.user.domain.UserVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0010D;
import hdel.sd.ses.domain.Ses0010;
import hdel.sd.ses.domain.Ses0010ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class UserMgrS extends SrmService {

	private UserMgrD userMgrD;
	
	public void createDao(SqlSession sqlSession) {
		userMgrD = sqlSession.getMapper(UserMgrD.class);
	}
	
	public List<UserVO> selectUser(UserParamVO param) {
		return userMgrD.selectUser(param);
	}  
}
