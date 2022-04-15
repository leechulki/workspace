package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0170D;
import hdel.sd.ses.domain.Ses0170;
import hdel.sd.ses.domain.Ses0170ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0170S extends SrmService {

	private Ses0170D Ses0170Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0170Dao = sqlSession.getMapper(Ses0170D.class);
	}
	
	public List<Ses0170> searchHeader(Ses0170ParamVO param) {
		return Ses0170Dao.selectListHeader(param);
	}
}
