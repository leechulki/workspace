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

		// NULL�� �ƴ��� ���� Ȯ�� 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sql += " AND MANDT = #MANDT#";
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sql += " AND CS121_UPN = #UPN#";
		}
		
		// SqlRequest ����
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
		
		// �Ķ���� �����ϱ� 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sqlRequest.addParamObject("MANDT", DatasetUtility.getString(ds, "MANDT"));
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sqlRequest.addParamObject("UPN", DatasetUtility.getString(ds, "UPN"));
		}

		// ��ȸ ó�� 
		SqlExecutor db = new DatasetSqlExecutor(getConnection());
		SqlResult result = db.query(sqlRequest);
		
		// ��ȸ ��� ��ü ã�ƿ��� 
		Dataset returnDs = (Dataset)result.getResultObject();
		
		// ����ڿ��� �� �ѱ�� 
		return returnDs;
	}
	
	public void delete(String empno) throws Exception {

	}

	public void save(Dataset ds) throws Exception {

	}

	public void update(Dataset ds) throws Exception {

	}
}
