package com.helco.xf.cs13.ws;

import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractBusinessService;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.TitUtility;

public class CS1304001B_ServiceImpl extends AbstractBusinessService implements
		CS1301001B_Service {

	public Dataset search(String empno) throws Exception {
		SqlRequest sqlRequest 
			= SqlMapManagerUtility.makeSqlRequest("test3:EMP_SELECT");
		
		sqlRequest.addParamObject("EMPNO", empno);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection());
		return (Dataset)executor.query(sqlRequest).getResultObject();
	}
	
	public Dataset searchComboCst(Dataset ds) throws Exception {
		String sql = "SELECT CS121_CST CST FROM SAPHEE.ZCST121 WHERE 1 = 1";

		// NULL이 아닌지 여부 확인 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sql += " AND MANDT = #MANDT#";
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sql += " AND CS121_UPN = #UPN#";
		}
		
		// SqlRequest 생성
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
		
		// 파라메터 설정하기 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sqlRequest.addParamObject("MANDT", DatasetUtility.getString(ds, "MANDT"));
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sqlRequest.addParamObject("UPN", DatasetUtility.getString(ds, "UPN"));
		}

		// 조회 처리 
		SqlExecutor db = new DatasetSqlExecutor(getConnection());
		SqlResult result = db.query(sqlRequest);
		
		// 조회 결과 객체 찾아오기 
		Dataset returnDs = (Dataset)result.getResultObject();
		
		// 사용자에게 값 넘기기 
		return returnDs;
	}
	
	public void delete(String empno) throws Exception {

	}

	public void save(Dataset ds) throws Exception {

	}

	public void update(Dataset ds) throws Exception {

	}
}
