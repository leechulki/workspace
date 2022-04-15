package hdel.sd.sbi.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.ZBCS_TIMESTAMP;
import hdel.lib.service.SrmService;
import hdel.sd.sbi.dao.Sbi0070D;
import hdel.sd.sbi.domain.Sbi0070;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

@Service
public class Sbi0070S extends SrmService {

	private Sbi0070D sbi0070Dao;

	public void createDao(SqlSession sqlSession) {
		sbi0070Dao = sqlSession.getMapper(Sbi0070D.class);
	}

	public Dataset find(MipParameterVO paramVO) {
		Sbi0070 param = new Sbi0070();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMANDT(paramVO.getVariable("G_MANDT"));

		List<Sbi0070> agentList = sbi0070Dao.find(param);
		Dataset dsList = paramVO.getDataset("ds_list");

		DatasetUtil.moveListToDs(agentList, dsList);

		return dsList;
	}

	public void save(MipParameterVO paramVO) throws Exception {

		Sbi0070 param = new Sbi0070();
		ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
		tstmp.setErnam(paramVO.getVariable("G_USER_ID"));
		tstmp.setAenam(paramVO.getVariable("G_USER_ID"));
		param.setTstmp(tstmp);
		
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsSave  = paramVO.getDataset("ds_save");	
		
		dsSave.deleteAll(); 
		for(int iRow=0; iRow < dsList.getRowCount(); iRow++) {

			if(!dsList.getColumnAsString(iRow, "MANAGER1").equals("")) {
				dsSave.appendRow();   
				dsSave.setColumn(iRow, "MANDT"		, dsList.getColumn(iRow, "MANDT")); 		// 클라이언트
				dsSave.setColumn(iRow, "DEALER"		, dsList.getColumn(iRow, "DEALER")); 		// 딜러
				dsSave.setColumn(iRow, "SDFIELD"	, "NI"); 									// "NI" 관리구분
				dsSave.setColumn(iRow, "MANAGER"	, dsList.getColumn(iRow, "MANAGER1")); 		// 관리자

//				param = paramSet(param, "C", paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_USER_ID"), iRow, dsSave);

				DatasetUtil.moveDsRowToObj(dsSave, iRow, param);
				sbi0070Dao.save(param);
			}
			if(!dsList.getColumnAsString(iRow, "MANAGER2").equals("")) {
				dsSave.appendRow();   
				dsSave.setColumn(iRow, "MANDT"		, dsList.getColumn(iRow, "MANDT")); 		// 클라이언트
				dsSave.setColumn(iRow, "DEALER"		, dsList.getColumn(iRow, "DEALER")); 		// 딜러
				dsSave.setColumn(iRow, "SDFIELD"	, "REM"); 		                        // "REM(리모델링)" 관리구분 
				dsSave.setColumn(iRow, "MANAGER"	, dsList.getColumn(iRow, "MANAGER2")); 		// 관리자

				DatasetUtil.moveDsRowToObj(dsSave, iRow, param);
				sbi0070Dao.save(param);
			}
			if(!dsList.getColumnAsString(iRow, "MANAGER3").equals("")) {
				dsSave.appendRow();   
				dsSave.setColumn(iRow, "MANDT"		, dsList.getColumn(iRow, "MANDT")); 		// 클라이언트
				dsSave.setColumn(iRow, "DEALER"		, dsList.getColumn(iRow, "DEALER")); 		// 딜러
				dsSave.setColumn(iRow, "SDFIELD"	, "PRK"); 									// "PRK(주차)" 관리구분	
				dsSave.setColumn(iRow, "MANAGER"	, dsList.getColumn(iRow, "MANAGER3")); 		// 관리자

				DatasetUtil.moveDsRowToObj(dsSave, iRow, param);
				sbi0070Dao.save(param);
			}
		}

	}
}