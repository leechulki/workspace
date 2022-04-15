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

public class MM0502001B_ServiceImpl extends AbstractBusinessService implements MM0502001B_Service {

	public void save(BusinessContext ctx, Dataset ds_list, Dataset ds_list2) throws Exception {
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
						
		String zknum2 = "";
		String sql = "";
		
		String db_con = Utillity.getSapJndi(mandt);
		
		SqlRequest sqlRequest = null;
		SqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = null;

		//출하예정상세화면에서 호차를 선택했을 경우 경유지번호를 채번한다.
		if (TitUtility.isNotNull(zknum)) {
			sql  = " SELECT SAPHEE.FILLINZERO(ZSEQN2,2) AS ZKNUM2 FROM TABLE(SAPHEE.GET_HOCHA_NO('"+mandt+"','"+zknum+"','"+ernam+"')) AS T ";  
			
			// SqlRequest 생성
			sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
	 
			// 조회 처리 
//			executor = new DatasetSqlExecutor(getConnection(db_con));
			result = executor.query(sqlRequest);
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
//			executor = new DatasetSqlExecutor(getConnection(db_con));
			result = executor.query(sqlRequest);

			// 조회 결과 객체 찾아오기 
			Dataset returnDs = (Dataset)result.getResultObject();
			zknum = returnDs.getColumnAsString(0, "ZKNUM");
		}

		//ZLET002테이블에 1건 INSERT한다.
//		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		DatasetSqlRequest zlet002i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0502001B_I1");
		zlet002i.addParamObject("ds_list2", ds_list2);
		
		zlet002i.addParamObject("G_MANDT", mandt);
		zlet002i.addParamObject("G_USER_ID", ernam);
		
		for(int i=0; i<ds_list2.getRowCount(); i++) {
			ds_list2.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
			ds_list2.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
			zlet002i.setRowIndex(i);
			
			executor.execute(zlet002i);
		}
	
		//ZLET017테이블에 N건 INSERT한다.
		DatasetSqlRequest zlet017i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0502001B_I2");
		zlet017i.addParamObject("ds_list", ds_list);

		zlet017i.addParamObject("G_MANDT", mandt);
		zlet017i.addParamObject("G_USER_ID", ernam);

		for(int i=0; i<ds_list.getRowCount(); i++) {
			ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
			ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
			zlet017i.setRowIndex(i);
			
			executor.execute(zlet017i);
		}

		//ZLET003테이블에 N건 INSERT한다.
		DatasetSqlRequest zlet003i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0502001B_I3");
		zlet003i.addParamObject("ds_list", ds_list);

		zlet003i.addParamObject("G_MANDT", mandt);
		zlet003i.addParamObject("G_USER_ID", ernam);
		
		for(int i=0; i<1; i++) {
			ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
			ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
			zlet003i.setRowIndex(i);
			
			executor.execute(zlet003i);
		}

		//ZLET004테이블에 N건 INSERT한다.
		DatasetSqlRequest zlet004i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0502001B_I4");
		zlet004i.addParamObject("ds_list", ds_list);

		zlet004i.addParamObject("G_MANDT", mandt);
		zlet004i.addParamObject("G_USER_ID", ernam);

		for(int i=0; i<1; i++) {
			ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
			ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
			zlet004i.setRowIndex(i);
			
			executor.execute(zlet004i);
		}
		
		//ZLET041테이블에 N건 INSERT한다.
		DatasetSqlRequest zlet005i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0502001B_I5");
		//zlet005i.addParamObject("ds_list2", ds_list2);

		zlet005i.addParamObject("G_MANDT", mandt);
		zlet005i.addParamObject("G_USER_ID", ernam);
		
	
				
		String tplstdt1 = ds_list2.getColumnAsString(0, "TPLSTDT1");
		String tplstdtqty1 = ds_list2.getColumnAsString(0, "TPLSTDTQTY1");
		
		
		
		
		if(tplstdt1 != "" && tplstdt1.trim().length() != 0)
		{		
			for(int i = 0; i < tplstdtqty1.split("\\,").length; i++)
			{
				String ztonGubun = "";
				if( i == 0)  ztonGubun = "A";
				if( i == 1)  ztonGubun = "B";
				if( i == 2)  ztonGubun = "C";
				if( i == 3)  ztonGubun = "D";
				if( i == 4)  ztonGubun = "E";
				if( i == 5)  ztonGubun = "F";
				if( i == 6)  ztonGubun = "G";
				if( i == 7)  ztonGubun = "H";
				if( i == 8)  ztonGubun = "I";
				if( i == 9)  ztonGubun = "J";
				if( i == 10)  ztonGubun = "K";
				if( i == 11)  ztonGubun = "L";
				if( i == 12)  ztonGubun = "M";
				if( i == 13)  ztonGubun = "N";
				if( i == 14)  ztonGubun = "O";
				
				if(  Integer.parseInt(tplstdtqty1.split("\\,")[i])  > 0)
				{		
					for(int j=0; j<Integer.parseInt(tplstdtqty1.split("\\,")[i]); j++)
					{
						zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
						zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
						zlet005i.addParamObject("ZTON", ztonGubun);
						zlet005i.addParamObject("TPLSTDT", tplstdt1);
						
						zlet005i.setRowIndex(j);					
						executor.execute(zlet005i);
					}
				}
				
			}
		}
		
		
				
		String tplstdt2 = "";
		String tplstdtqty2 = "";
		if(ds_list2.getColumnAsString(0, "TPLSTDT2") != null)
		{
			tplstdt2 = ds_list2.getColumnAsString(0, "TPLSTDT2");
			tplstdtqty2 = ds_list2.getColumnAsString(0, "TPLSTDTQTY2");
		}
		
		if(tplstdt2 != "" && tplstdt2.trim().length() != 0)
		{			
			for(int i = 0; i < tplstdtqty2.split("\\,").length; i++)
			{
				String ztonGubun = "";
				if( i == 0)  ztonGubun = "A";
				if( i == 1)  ztonGubun = "B";
				if( i == 2)  ztonGubun = "C";
				if( i == 3)  ztonGubun = "D";
				if( i == 4)  ztonGubun = "E";
				if( i == 5)  ztonGubun = "F";
				if( i == 6)  ztonGubun = "G";
				if( i == 7)  ztonGubun = "H";
				if( i == 8)  ztonGubun = "I";
				if( i == 9)  ztonGubun = "J";
				if( i == 10)  ztonGubun = "K";
				if( i == 11)  ztonGubun = "L";
				if( i == 12)  ztonGubun = "M";
				if( i == 13)  ztonGubun = "N";
				if( i == 14)  ztonGubun = "O";				
				
				if(  Integer.parseInt(tplstdtqty2.split("\\,")[i])  > 0)
				{		
					for(int j=0; j<Integer.parseInt(tplstdtqty2.split("\\,")[i]); j++)
					{
						zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
						zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
						zlet005i.addParamObject("ZTON", ztonGubun);
						zlet005i.addParamObject("TPLSTDT", tplstdt2);
						
						zlet005i.setRowIndex(j);					
						executor.execute(zlet005i);
					}
				}
				
			}
		}
		

		
		String tplstdt3 = "";
		String tplstdtqty3 = "";
		if(ds_list2.getColumnAsString(0, "TPLSTDT3") != null)
		{
			tplstdt3 = ds_list2.getColumnAsString(0, "TPLSTDT3");
			tplstdtqty3 = ds_list2.getColumnAsString(0, "TPLSTDTQTY3");
		}
		
		if(tplstdt3 != "" && tplstdt3.trim().length() != 0)
		{			
			for(int i = 0; i < tplstdtqty3.split("\\,").length; i++)
			{
				String ztonGubun = "";
				if( i == 0)  ztonGubun = "A";
				if( i == 1)  ztonGubun = "B";
				if( i == 2)  ztonGubun = "C";
				if( i == 3)  ztonGubun = "D";
				if( i == 4)  ztonGubun = "E";
				if( i == 5)  ztonGubun = "F";
				if( i == 6)  ztonGubun = "G";
				if( i == 7)  ztonGubun = "H";
				if( i == 8)  ztonGubun = "I";
				if( i == 9)  ztonGubun = "J";
				if( i == 10)  ztonGubun = "K";
				if( i == 11)  ztonGubun = "L";
				if( i == 12)  ztonGubun = "M";
				if( i == 13)  ztonGubun = "N";
				if( i == 14)  ztonGubun = "O";
				
				if(  Integer.parseInt(tplstdtqty3.split("\\,")[i])  > 0)
				{		
					for(int j=0; j<Integer.parseInt(tplstdtqty3.split("\\,")[i]); j++)
					{
						zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
						zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
						zlet005i.addParamObject("ZTON", ztonGubun);
						zlet005i.addParamObject("TPLSTDT", tplstdt3);
						
						zlet005i.setRowIndex(j);					
						executor.execute(zlet005i);
					}
				}
				
			}
		}
		
		
		
		String tplstdt4 = "";
		String tplstdtqty4 = "";
		if(ds_list2.getColumnAsString(0, "TPLSTDT4") != null)
		{
			tplstdt4 = ds_list2.getColumnAsString(0, "TPLSTDT4");
			tplstdtqty4 = ds_list2.getColumnAsString(0, "TPLSTDTQTY4");
		}
		
		if(tplstdt4 != "" && tplstdt4.trim().length() != 0)
		{			
			for(int i = 0; i < tplstdtqty4.split("\\,").length; i++)
			{
				String ztonGubun = "";
				if( i == 0)  ztonGubun = "A";
				if( i == 1)  ztonGubun = "B";
				if( i == 2)  ztonGubun = "C";
				if( i == 3)  ztonGubun = "D";
				if( i == 4)  ztonGubun = "E";
				if( i == 5)  ztonGubun = "F";
				if( i == 6)  ztonGubun = "G";
				if( i == 7)  ztonGubun = "H";
				if( i == 8)  ztonGubun = "I";
				if( i == 9)  ztonGubun = "J";
				if( i == 10)  ztonGubun = "K";
				if( i == 11)  ztonGubun = "L";
				if( i == 12)  ztonGubun = "M";
				if( i == 13)  ztonGubun = "N";
				if( i == 14)  ztonGubun = "O";
				
				if(  Integer.parseInt(tplstdtqty4.split("\\,")[i])  > 0)
				{		
					for(int j=0; j<Integer.parseInt(tplstdtqty4.split("\\,")[i]); j++)
					{
						zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
						zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
						zlet005i.addParamObject("ZTON", ztonGubun);
						zlet005i.addParamObject("TPLSTDT", tplstdt4);
						
						zlet005i.setRowIndex(j);					
						executor.execute(zlet005i);
					}
				}
				
			}
		}
		
		
		String tplstdt5 = "";
		String tplstdtqty5 = "";
		if(ds_list2.getColumnAsString(0, "TPLSTDT5") != null)
		{
			tplstdt5 = ds_list2.getColumnAsString(0, "TPLSTDT5");
			tplstdtqty5 = ds_list2.getColumnAsString(0, "TPLSTDTQTY5");
		}		
		
		
		if(tplstdt5 != "" && tplstdt5.trim().length() != 0)
		{			
			for(int i = 0; i < tplstdtqty5.split("\\,").length; i++)
			{
				String ztonGubun = "";
				if( i == 0)  ztonGubun = "A";
				if( i == 1)  ztonGubun = "B";
				if( i == 2)  ztonGubun = "C";
				if( i == 3)  ztonGubun = "D";
				if( i == 4)  ztonGubun = "E";
				if( i == 5)  ztonGubun = "F";
				if( i == 6)  ztonGubun = "G";
				if( i == 7)  ztonGubun = "H";
				if( i == 8)  ztonGubun = "I";
				if( i == 9)  ztonGubun = "J";
				if( i == 10)  ztonGubun = "K";
				if( i == 11)  ztonGubun = "L";
				if( i == 12)  ztonGubun = "M";
				if( i == 13)  ztonGubun = "N";
				if( i == 14)  ztonGubun = "O";
				
				if(  Integer.parseInt(tplstdtqty5.split("\\,")[i])  > 0)
				{		
					for(int j=0; j<Integer.parseInt(tplstdtqty5.split("\\,")[i]); j++)
					{
						zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
						zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
						zlet005i.addParamObject("ZTON", ztonGubun);
						zlet005i.addParamObject("TPLSTDT", tplstdt5);
						
						zlet005i.setRowIndex(j);					
						executor.execute(zlet005i);
					}
				}
				
			}
		}
		
		

		
		String tplstdt6 = "";
		String tplstdtqty6 = "";
		if(ds_list2.getColumnAsString(0, "TPLSTDT6") != null)
		{
			tplstdt6 = ds_list2.getColumnAsString(0, "TPLSTDT6");
			tplstdtqty6 = ds_list2.getColumnAsString(0, "TPLSTDTQTY6");
		}	
		
		if(tplstdt6 != "" && tplstdt6.trim().length() != 0)
		{			
			for(int i = 0; i < tplstdtqty6.split("\\,").length; i++)
			{
				String ztonGubun = "";
				if( i == 0)  ztonGubun = "A";
				if( i == 1)  ztonGubun = "B";
				if( i == 2)  ztonGubun = "C";
				if( i == 3)  ztonGubun = "D";
				if( i == 4)  ztonGubun = "E";
				if( i == 5)  ztonGubun = "F";
				if( i == 6)  ztonGubun = "G";
				if( i == 7)  ztonGubun = "H";
				if( i == 8)  ztonGubun = "I";
				if( i == 9)  ztonGubun = "J";
				if( i == 10)  ztonGubun = "K";
				if( i == 11)  ztonGubun = "L";
				if( i == 12)  ztonGubun = "M";
				if( i == 13)  ztonGubun = "N";
				if( i == 14)  ztonGubun = "O";
				
				if(  Integer.parseInt(tplstdtqty6.split("\\,")[i])  > 0)
				{		
					for(int j=0; j<Integer.parseInt(tplstdtqty6.split("\\,")[i]); j++)
					{
						zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
						zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
						zlet005i.addParamObject("ZTON", ztonGubun);
						zlet005i.addParamObject("TPLSTDT", tplstdt6);
						
						zlet005i.setRowIndex(j);					
						executor.execute(zlet005i);
					}
				}
				
			}
		}
		
		

		
		String tplstdt7 = "";
		String tplstdtqty7 = "";
		if(ds_list2.getColumnAsString(0, "TPLSTDT7") != null)
		{
			tplstdt7 = ds_list2.getColumnAsString(0, "TPLSTDT7");
			tplstdtqty7 = ds_list2.getColumnAsString(0, "TPLSTDTQTY7");
		}	
		
		if(tplstdt7 != "" && tplstdt7.trim().length() != 0)
		{			
			for(int i = 0; i < tplstdtqty7.split("\\,").length; i++)
			{
				String ztonGubun = "";
				if( i == 0)  ztonGubun = "A";
				if( i == 1)  ztonGubun = "B";
				if( i == 2)  ztonGubun = "C";
				if( i == 3)  ztonGubun = "D";
				if( i == 4)  ztonGubun = "E";
				if( i == 5)  ztonGubun = "F";
				if( i == 6)  ztonGubun = "G";
				if( i == 7)  ztonGubun = "H";
				if( i == 8)  ztonGubun = "I";
				if( i == 9)  ztonGubun = "J";
				if( i == 10)  ztonGubun = "K";
				if( i == 11)  ztonGubun = "L";
				if( i == 12)  ztonGubun = "M";
				if( i == 13)  ztonGubun = "N";
				if( i == 14)  ztonGubun = "O";
				
				if(  Integer.parseInt(tplstdtqty7.split("\\,")[i])  > 0)
				{		
					for(int j=0; j<Integer.parseInt(tplstdtqty7.split("\\,")[i]); j++)
					{
						zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
						zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
						zlet005i.addParamObject("ZTON", ztonGubun);
						zlet005i.addParamObject("TPLSTDT", tplstdt7);
						
						zlet005i.setRowIndex(j);					
						executor.execute(zlet005i);
					}
				}
				
			}
		}
		
		/*
		if(zton1> 0)
		{		
			for(int j=0; j<zton1; j++)
			{
				zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
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
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
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
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
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
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
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
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
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
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
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
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
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
				zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet005i.addParamObject("ZTON", ton8);
				
				zlet005i.setRowIndex(j);					
				executor.execute(zlet005i);
			}
		}
		*/
		
	}
}
