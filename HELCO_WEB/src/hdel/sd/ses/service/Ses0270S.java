package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0270D;
import hdel.sd.ses.domain.Ses0110;
import hdel.sd.ses.domain.Ses0270;
import hdel.sd.ses.domain.Ses0270ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0270S extends SrmService {

	private Ses0270D Ses0270Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0270Dao = sqlSession.getMapper(Ses0270D.class);
	}

	public List<Ses0270> searchList(Ses0270ParamVO param) {
		return Ses0270Dao.selectList(param);
	}
	
	public List<Ses0270> searchListDetail(Ses0270ParamVO param) {
		System.out.print("\n@@@ Ses0270FindDtlView searchListDetail strat");
		return Ses0270Dao.selectListDetail(param);
	}
}
