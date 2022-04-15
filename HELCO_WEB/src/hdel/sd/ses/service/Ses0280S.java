package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0280D;
import hdel.sd.ses.domain.Ses0280;
import hdel.sd.ses.domain.Ses0280ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0280S extends SrmService {

	private Ses0280D Ses0280Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0280Dao = sqlSession.getMapper(Ses0280D.class);
	}
	
	public List<Ses0280> searchHeader(Ses0280ParamVO param) {
		return Ses0280Dao.selectListHeader(param);
	}
}
