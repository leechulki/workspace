package com.helco.xf.cs34.ws;

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

public class CS3403001A_ServiceImpl extends AbstractBusinessService implements
		CS3403001A_Service {

	public Dataset query(Dataset ds) throws Exception {
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

	public void save(Dataset ds) throws Exception {

		DatasetSqlRequest sqlRequest 
			= SqlMapManagerUtility.makeSqlRequestForDataset("test3:EMP_INSERT");
		sqlRequest.addParamObject("ds_master", ds);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection());
		executor.execute(sqlRequest);
	}

	public void setTestAttribute(String name ) {
		
	}

}
