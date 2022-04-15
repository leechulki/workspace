package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0183D;
import hdel.sd.ses.domain.Ses0183;
import hdel.sd.ses.domain.Ses0183ParamVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.service.sapjco3.RfcException;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0183S extends SrmService {

	private Ses0183D ses0183D;

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void createDao(SqlSession sqlSession) {
		ses0183D = sqlSession.getMapper(Ses0183D.class);
	}
	
	public List<Ses0183> selectZSDT1055(Ses0183ParamVO param) {
		return ses0183D.selectZSDT1055(param);
	}

	public List<Ses0183> selectPrint(Map<String, Object> paramMap) {
		return ses0183D.selectPrint(paramMap);
	}
}
