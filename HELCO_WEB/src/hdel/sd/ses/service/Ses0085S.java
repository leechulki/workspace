package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0085D;
import hdel.sd.ses.domain.Ses0085;
import hdel.sd.ses.domain.Ses0085ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0085S extends SrmService {

	private final String ZPRGFLG_23 = "23";
	private final String ZPRGFLG_24 = "24";
	private final String ZPRGFLG_25 = "25";
	private final String ZPRGFLG_26 = "26";

	private final String ZRQFLG_1   = "1";		// 국내 견적승인요청
	
	private Ses0085D Ses0085Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0085Dao = sqlSession.getMapper(Ses0085D.class);
	}

	public List<Ses0085> searchDetail(Ses0085ParamVO param) {
		return Ses0085Dao.selectListDetail(param);
	} 
}
