package hdel.sd.ses.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0400D;
import hdel.sd.ses.domain.Ses0400;
import hdel.sd.ses.domain.Ses0400ParamVO;
import hdel.sd.ses.domain.Ses0404;
import hdel.sd.ses.domain.Ses0404ParamVO;
import hdel.sd.ses.domain.ZSDT1048;
import tit.util.DatasetUtility;

@Service
public class Ses0400S extends SrmService {

	private Ses0400D Ses0400Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0400Dao = sqlSession.getMapper(Ses0400D.class);
	}
	
	public List<Ses0400> getListHeader(Ses0400ParamVO param) {
		// 
		/*if (param.getQtso_no() != null && !"".equals(param.getQtso_no()))	{
			param.setQuery_yn("Y");
		}*/

		if (param.getFrzrqdat() != null && !"".equals(param.getFrzrqdat()))	{
			param.setQuery_yn("Y");
		}

		if (param.getZrqstat() != null && !"".equals(param.getZrqstat()))	{
			param.setQuery_yn("Y");
		}

		if ("P".equals(param.getGubun()) )	{	// 
			return Ses0400Dao.selectListHeaderProject(param);
		}else	{								// 
			return Ses0400Dao.selectListHeaderQtnum(param);
		}
	}

	public List<Ses0400> getListDetail(Ses0400ParamVO param) {
		return Ses0400Dao.selectListDetail(param);
	}

	public List<Ses0400> getRequestList(Ses0400ParamVO param) {
		return Ses0400Dao.selectRequestList(param);
	}
	
	public List<Ses0404> queryOutsourcingBlueprint(Ses0404ParamVO param) {
		return Ses0400Dao.queryOutsourcingBlueprint(param);
	}
	
	public void updateDelayDays(MipParameterVO mipParam) throws Exception {
		Dataset updDelayDaysList = mipParam.getDataset("ds_upd_delaydays");
		try {
			for ( int i = 0 ; i < updDelayDaysList.getRowCount() ; i++ ) {
				Ses0404 ses0404SRec = new Ses0404();
				ses0404SRec.setMandt(mipParam.getVariable("G_MANDT"));
				ses0404SRec.setZrqseq(DatasetUtility.getString(updDelayDaysList, i, "ZRQSEQ", ""));
				ses0404SRec.setDelaydays(DatasetUtility.getString(updDelayDaysList, i, "DELAYDAYS", "000"));
				ses0404SRec.setUdate(DateTime.getDateString());
				ses0404SRec.setUtime(DateTime.getShortTimeString());
				ses0404SRec.setUuser(mipParam.getVariable("G_USER_ID"));

				Ses0400Dao.updateDelayDays(ses0404SRec);
			}
		} catch (Exception e) {
			throw e;
		}

		// return param.getZRQSEQ();
	}	
	public List<ZSDT1048> getAtnam(Ses0400ParamVO param) {
		return Ses0400Dao.getAtnam(param);	
	}
}



