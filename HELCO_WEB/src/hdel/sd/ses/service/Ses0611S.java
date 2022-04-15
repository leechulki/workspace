package hdel.sd.ses.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0611D;
import hdel.sd.ses.domain.Ses0611;
import hdel.sd.ses.domain.Ses0611ParamVO;


@Service
public class Ses0611S extends SrmService {

	private Ses0611D dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Ses0611D.class);
	}
	
	public List<Ses0611> selectListFile(Ses0611ParamVO paramVO) {
		return dao.selectListFile(paramVO);
	}
	
	public void save( List<Ses0611> listFile) throws Exception {
		
		try {
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0611 paramFile = listFile.get(i);
				
				if ("I".equals(paramFile.getFLAG())) {
					dao.insertFile(paramFile);
				}
				if ("U".equals(paramFile.getFLAG())) {
					dao.updateFile(paramFile);
				}
				if ("D".equals(paramFile.getFLAG())) {
					dao.deleteFile(paramFile);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}