package com.helco.xf.mm05.ws;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.cs12.evladm.dbutil.*;
import com.helco.xf.comm.Utillity;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.business.config.ConfigUtility;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;

public class MM0502003A_ServiceImpl extends AbstractBusinessService implements MM0502003A_Service {
	public void save(BusinessContext ctx, Dataset ds_list, Dataset ds_list2) throws Exception {
		String mandt = ctx.getInputVariable("G_MANDT");
		String ernam = ctx.getInputVariable("G_USER_ID");
		String ton1 = "A";
		String ton2 = "B";
		String ton3 = "C";
		String ton4 = "D";
		String ton5 = "E";
		String ton6 = "F";
		String ton7 = "G";
		String ton8 = "H";
		String ton9 = "I";
		String ton10 = "J";
		String ton11 = "K";
		String ton12 = "L";
		String ton13 = "M";
		String ton14 = "N";
		String ton15 = "O";
		
		String erdat = ds_list2.getColumnAsString(0, "ERDAT");
		String zknum = ds_list2.getColumnAsString(0, "ZKNUM");
		int zton1 = ds_list2.getColumnAsInteger(0, "ZTON1");
		int zton2 = ds_list2.getColumnAsInteger(0, "ZTON2");
		int zton3 = ds_list2.getColumnAsInteger(0, "ZTON3");
		int zton4 = ds_list2.getColumnAsInteger(0, "ZTON4");
		int zton5 = ds_list2.getColumnAsInteger(0, "ZTON5");
		int zton6 = ds_list2.getColumnAsInteger(0, "ZTON6");
		int zton7 = ds_list2.getColumnAsInteger(0, "ZTON7");
		int zton8 = ds_list2.getColumnAsInteger(0, "ZTON8");
		int zton9 = ds_list2.getColumnAsInteger(0, "ZTON9");
		int zton10 = ds_list2.getColumnAsInteger(0, "ZTON10");
		int zton11 = ds_list2.getColumnAsInteger(0, "ZTON11");
		int zton12 = ds_list2.getColumnAsInteger(0, "ZTON12");
		int zton13 = ds_list2.getColumnAsInteger(0, "ZTON13");
		int zton14 = ds_list2.getColumnAsInteger(0, "ZTON14");
		int zton15 = ds_list2.getColumnAsInteger(0, "ZTON15");

		String zknum2 = "";
		String sql = "";
		
		String db_con = Utillity.getSapJndi(mandt);
		
		SqlRequest sqlRequest = null;
		SqlExecutor db = null;
		SqlResult result = null;

		//출하예정상세화면에서 호차를 선택했을 경우 경유지번호를 채번한다.
		if (TitUtility.isNotNull(zknum)) {
			sql  = " SELECT SAPHEE.FILLINZERO(ZSEQN2,2) AS ZKNUM2 FROM TABLE(SAPHEE.GET_HOCHA_NO('"+mandt+"','"+zknum+"','"+ernam+"')) AS T ";  
			
			// SqlRequest 생성
			sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
	 
			// 조회 처리 
			db = new DatasetSqlExecutor(getConnection(db_con));
			result = db.query(sqlRequest);
			// 조회 결과 객체 찾아오기 
			Dataset returnDs = (Dataset)result.getResultObject();
			zknum2 = returnDs.getColumnAsString(0, "ZKNUM2");
		}
		else
		//출하예정상세화면에서 호차를 선택하지 않았을 경우 호차번호를 채번한다.	
		{
			sql  = " SELECT ZKNUM FROM TABLE(SAPHEE.GET_HOCHA_NO('"+mandt+"','"+erdat+"','"+ernam+"')) AS T ";

			// SqlRequest 생성
			sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);

			// 조회 처리 
			db = new DatasetSqlExecutor(getConnection(db_con));
			result = db.query(sqlRequest);

			// 조회 결과 객체 찾아오기 
			Dataset returnDs = (Dataset)result.getResultObject();
			zknum = returnDs.getColumnAsString(0, "ZKNUM");
		}

		//ZLET006테이블에 1건 INSERT한다.
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		DatasetSqlRequest zlet006i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0502003A_I1");
		zlet006i.addParamObject("ds_list2", ds_list2);
		
		zlet006i.addParamObject("G_MANDT", mandt);
		zlet006i.addParamObject("G_USER_ID", ernam);
		
		for(int i=0; i<ds_list2.getRowCount(); i++) {
			ds_list2.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
			ds_list2.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
			zlet006i.setRowIndex(i);
			
			executor.execute(zlet006i);
		}
	
		//ZLET007테이블에 N건 INSERT한다.
		DatasetSqlRequest zlet007i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0502003A_I2");
		zlet007i.addParamObject("ds_list", ds_list);

		zlet007i.addParamObject("G_MANDT", mandt);
		zlet007i.addParamObject("G_USER_ID", ernam);

		for(int i=0; i<ds_list.getRowCount(); i++) {
			ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
			ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
			zlet007i.setRowIndex(i);
			
			executor.execute(zlet007i);
		}
		
		//ZLET041테이블에 N건 INSERT한다.
		DatasetSqlRequest zlet005i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0502001B_I5");
		//zlet005i.addParamObject("ds_list2", ds_list2);

		zlet005i.addParamObject("G_MANDT", mandt);
		zlet005i.addParamObject("G_USER_ID", ernam);
								
			
		if(zton1> 0)
		{		
			for(int j=0; j<zton1; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton1);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));	
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton2> 0)
		{
			for(int j=0; j<zton2; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton2);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
					
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton3> 0)
		{
			for(int j=0; j<zton3; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton3);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
							
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton4> 0)
		{				
			for(int j=0; j<zton4; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton4);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
							
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton5> 0)
		{
			for(int j=0; j<zton5; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton5);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
					
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton6> 0)
		{
			for(int j=0; j<zton6; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton6);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
							
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton7> 0)
		{
			for(int j=0; j<zton7; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton7);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
					
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton8> 0)
		{
			for(int j=0; j<zton8; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton8);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton9> 0)
		{
			for(int j=0; j<zton9; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton9);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton10> 0)
		{
			for(int j=0; j<zton10; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton10);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton11> 0)
		{
			for(int j=0; j<zton11; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton11);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton12> 0)
		{
			for(int j=0; j<zton12; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton12);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton13> 0)
		{
			for(int j=0; j<zton13; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton13);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton14> 0)
		{
			for(int j=0; j<zton14; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton14);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		if(zton15> 0)
		{
			for(int j=0; j<zton15; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton15);
				zlet005i.addParamObject("TPLSTDT", ds_list2.getColumnAsString(0, "BEZEI"));
						
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
	}
}
