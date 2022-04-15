package hdel.sd.ses.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0088D;
import hdel.sd.ses.domain.Ses0088;
import hdel.sd.ses.domain.Ses0088ParamVO;


@Service
public class Ses0088S extends SrmService {

	private Ses0088D dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Ses0088D.class);
	}
	
	public List<Ses0088> selectListFile(Ses0088ParamVO paramVO) {
		return dao.selectListFile(paramVO);
	}
	
	public void save( List<Ses0088> listFile) throws Exception {
		
		try {
			
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0088 paramFile = listFile.get(i);
				
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