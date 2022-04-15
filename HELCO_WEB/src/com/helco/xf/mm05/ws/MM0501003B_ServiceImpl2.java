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

public class MM0501003B_ServiceImpl2 extends AbstractBusinessService implements MM0501003B_Service2 {

	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mandt = ctx.getInputVariable("G_MANDT");
		String ernam = ctx.getInputVariable("G_ORG_USER_INFO").split("\\^")[0]; //ctx.getInputVariable("G_USER_ID"); // 사용자 변경시 원 사용자 ID 저장 2013.06.20 HSS
		String ton1 = "A";
		String ton2 = "B";
		String ton3 = "C";
		String ton4 = "D";
		String ton5 = "E";
		String ton6 = "F";
		String ton7 = "G";
		String ton8 = "H";
		
		String zknum = ds_list.getColumnAsString(0, "ZKNUM");
		String zknum2 = ds_list.getColumnAsString(0, "ZKNUM2");
		int zton1 = ds_list.getColumnAsInteger(0, "ZTON1");
		int zton2 = ds_list.getColumnAsInteger(0, "ZTON2");
		int zton3 = ds_list.getColumnAsInteger(0, "ZTON3");
		int zton4 = ds_list.getColumnAsInteger(0, "ZTON4");
		int zton5 = ds_list.getColumnAsInteger(0, "ZTON5");
		int zton6 = ds_list.getColumnAsInteger(0, "ZTON6");
		int zton7 = ds_list.getColumnAsInteger(0, "ZTON7");
		int zton8 = ds_list.getColumnAsInteger(0, "ZTON8");
		
		String sql = "";		
		String temp = "";
		
		String db_con = Utillity.getSapJndi(mandt);
		
		SqlRequest sqlRequest = null;
		SqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = null;
		
		if (TitUtility.isNotNull(zknum)) {
			sql  = " SELECT * FROM SAPHEE.ZLET041 WHERE MANDT = '"+mandt+"' AND ZKNUM = '"+zknum+"' ";  
			
			// SqlRequest 생성
			sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
		
			
			result = executor.query(sqlRequest);
			Dataset returnDs = (Dataset)result.getResultObject();
			
			
			temp = returnDs.getColumnAsString(0, "ZKNUM");	
		
			
			DatasetSqlRequest zlet002i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U2");
			zlet002i.addParamObject("ds_list", ds_list);
			
			zlet002i.addParamObject("G_MANDT", mandt);
			zlet002i.addParamObject("G_USER_ID", ernam);
			
			for(int i=0; i<ds_list.getRowCount(); i++) {
				ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
				ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet002i.setRowIndex(i);
				
				executor.execute(zlet002i);
			}
			
			DatasetSqlRequest zlet003i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U4");
			zlet003i.addParamObject("ds_list", ds_list);
			
			zlet003i.addParamObject("G_MANDT", mandt);
			zlet003i.addParamObject("G_USER_ID", ernam);
			
			for(int i=0; i<ds_list.getRowCount(); i++) {
				ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
				ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet003i.setRowIndex(i);
				
				executor.execute(zlet003i);
			}
					
			
			if(temp != null)
			{				
				
				DatasetSqlRequest zlet041i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_D1");
				zlet041i.addParamObject("ds_list", ds_list);
				
				zlet041i.addParamObject("G_MANDT", mandt);
				zlet041i.addParamObject("G_USER_ID", ernam);
				
				for(int i=0; i<ds_list.getRowCount(); i++) {
					ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));			
					zlet041i.setRowIndex(i);
					
					executor.execute(zlet041i);
				}
			}	
			
		}					
						
		//ZLET041테이블에 N건 INSERT한다.
		DatasetSqlRequest zlet005i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_I1");
		//zlet005i.addParamObject("ds_list2", ds_list2);

		zlet005i.addParamObject("G_MANDT", mandt);
		zlet005i.addParamObject("G_USER_ID", ernam);
						
	
			if(zton1> 0)
			{		
				for(int j=0; j<zton1; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZTON", ton1);
					
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
				}
			}
			if(zton2> 0)
			{
				for(int j=0; j<zton2; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZTON", ton2);
					
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
				}
			}
			if(zton3> 0)
			{
				for(int j=0; j<zton3; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZTON", ton3);
					
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
				}
			}
			if(zton4> 0)
			{				
				for(int j=0; j<zton4; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZTON", ton4);
					
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
				}
			}
			if(zton5> 0)
			{
				for(int j=0; j<zton5; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZTON", ton5);
					
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
				}
			}
			if(zton6> 0)
			{
				for(int j=0; j<zton6; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZTON", ton6);
					
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
				}
			}
			if(zton7> 0)
			{
				for(int j=0; j<zton7; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZTON", ton7);
					
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
				}
			}
			if(zton8> 0)
			{
				for(int j=0; j<zton8; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZTON", ton8);
					
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
				}
			}			
		
	}
}
