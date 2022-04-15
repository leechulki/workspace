package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0181D;
import hdel.sd.ses.domain.Ses0181;
import hdel.sd.ses.domain.Ses0181ParamVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
public class Ses0181S extends SrmService {

	private Ses0181D ses0181D;

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void createDao(SqlSession sqlSession) {
		ses0181D = sqlSession.getMapper(Ses0181D.class);
	}
	
	public List<Ses0181> selectZSDT1046(Ses0181ParamVO param) {
		return ses0181D.selectZSDT1046(param);
	}
}
