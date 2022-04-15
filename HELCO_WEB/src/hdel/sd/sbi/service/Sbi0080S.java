package hdel.sd.sbi.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import tit.util.DatasetUtility;

import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.Spras;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.ZBCS_TIMESTAMP;
import hdel.lib.service.SrmService;
import hdel.sd.sbi.dao.Sbi0080D;
import hdel.sd.sbi.domain.Sbi0070;
import hdel.sd.sbi.domain.Sbi0080;
import hdel.sd.ses.domain.Ses0031ParamVO;

@Service
public class Sbi0080S extends SrmService {

	private Sbi0080D sbi0080Dao;

	public void createDao(SqlSession sqlSession) {
		sbi0080Dao = sqlSession.getMapper(Sbi0080D.class);
	}

	public Dataset find(MipParameterVO paramVO) {
		Sbi0080 param = new Sbi0080();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMANDT(paramVO.getVariable("G_MANDT"));
		param.setLANG(new Spras(paramVO.getVariable("G_LANG")).getValue());
		
		List<Sbi0080> agentList = sbi0080Dao.find(param);
		Dataset dsList = paramVO.getDataset("ds_list");
		DatasetUtil.moveListToDs(agentList, dsList);
		return dsList;
	}
	
	public void save(MipParameterVO paramVO, SqlSession session)  throws Exception {

		Dataset dsList = paramVO.getDataset("ds_list");

		String flag    = "";

		try {
			createDao(session);

			for(int i=0; i < dsList.getRowCount(); i++) {
				flag = DatasetUtility.getString(dsList, i, "FLAG");

				Sbi0080 param = new Sbi0080();
				if ( flag != null) {
					if(flag.equals("I") || flag.equals("U")) {
						ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
						tstmp.setErnam(paramVO.getVariable("G_USER_ID"));
						tstmp.setAenam(paramVO.getVariable("G_USER_ID"));
						param.setTstmp(tstmp);

						DatasetUtil.moveDsRowToObj(dsList, i, param);
						sbi0080Dao.save(param);
					}
					else if(flag.equals("D")) {
						DatasetUtil.moveDsRowToObj(dsList, i, param);
						sbi0080Dao.delete(param);
					}
				}		
			}   //end for		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
	}

}