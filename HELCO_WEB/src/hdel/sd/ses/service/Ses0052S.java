package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0052D;
import hdel.sd.ses.domain.Ses0052;
import hdel.sd.ses.domain.Ses0052ParamVO;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


@Service
public class Ses0052S extends SrmService {

	private Ses0052D dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Ses0052D.class);
	}
	
	public List<Ses0052> selectListHeader(Ses0052ParamVO paramVO) {
		return dao.selectListHeader(paramVO);
	}
		
	public void saveHeader(Ses0052 param) throws Exception {
		
		try {						
				dao.saveHeader(param);
			
		} catch (Exception e) {
			throw e;
		}
	}

	public int selectQtn(Ses0052ParamVO param) throws Exception {
		
		/*try {   
				dao.selectQtn(param);
		} catch (Exception e) {
			throw e;
		}*/
		
		return  dao.selectQtn(param);
	}
/*	
	public void saveOutdate(Ses0052 param) throws Exception {
		
		try {
			dao.updateOutdate(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutfinish(Ses0052 param) throws Exception {
		
		try {
			dao.updateOutfinish(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    public void deleteHeader(Ses0052 param) throws Exception {
		
		try {
			dao.deleteHeader(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}*/
}
