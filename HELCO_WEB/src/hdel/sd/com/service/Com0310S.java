package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0310D;
import hdel.sd.com.domain.Com0310ParamVO;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0310S extends SrmService
{

	private Com0310D com0310d;
	
	
    public void createDao(SqlSession sqlSession)
    {
        com0310d = (Com0310D)sqlSession.getMapper(Com0310D.class);
    }

    public List find(Com0310ParamVO param)
    {
        return com0310d.find(param);
    }

    
}
