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

public class MM0501003B_ServiceImpl extends AbstractBusinessService implements MM0501003B_Service {

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
		String ton9 = "I";
		String ton10 = "J";
		String ton11 = "K";
		String ton12 = "L";
		String ton13 = "M";
		String ton14 = "N";
		String ton15 = "O";
		
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
		int zton9 = ds_list.getColumnAsInteger(0, "ZTON9");
		int zton10 = ds_list.getColumnAsInteger(0, "ZTON10");
		int zton11 = ds_list.getColumnAsInteger(0, "ZTON11");
		int zton12 = ds_list.getColumnAsInteger(0, "ZTON12");
		int zton13 = ds_list.getColumnAsInteger(0, "ZTON13");
		int zton14 = ds_list.getColumnAsInteger(0, "ZTON14");
		int zton15 = ds_list.getColumnAsInteger(0, "ZTON15");
		
		String strZGubun = ds_list.getColumnAsString(0, "ZTYPE");
	
		String sql = "";		
		String temp = "";
		
		String db_con = Utillity.getSapJndi(mandt);
		
		SqlRequest sqlRequest = null;
		SqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = null;
		
		if (TitUtility.isNotNull(zknum)) {
			sql  = " SELECT * FROM SAPHEE.ZLET041 WHERE MANDT = '"+mandt+"' AND ZKNUM = '"+zknum+"' AND ZKNUM2 = '"+zknum2+"'";  
			
			// SqlRequest 생성
			sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
		
			
			result = executor.query(sqlRequest);
			Dataset returnDs = (Dataset)result.getResultObject();
			
			
			temp = returnDs.getColumnAsString(0, "ZKNUM");	
		
			
			DatasetSqlRequest zlet002i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U1");
			zlet002i.addParamObject("ds_list", ds_list);
			
			zlet002i.addParamObject("G_MANDT", mandt);
			zlet002i.addParamObject("G_USER_ID", ernam);
			
			for(int i=0; i<ds_list.getRowCount(); i++) {
				ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
				ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet002i.setRowIndex(i);
				
				executor.execute(zlet002i);
			}
			
			DatasetSqlRequest zlet003i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U3");
			zlet003i.addParamObject("ds_list", ds_list);
			
			zlet003i.addParamObject("G_MANDT", mandt);
			zlet003i.addParamObject("G_USER_ID", ernam);
			
			for(int i=0; i<ds_list.getRowCount(); i++) {
				ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
				ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet003i.setRowIndex(i);
				
				executor.execute(zlet003i);
			}
					
			
			DatasetSqlRequest zlet006i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U2");
			zlet006i.addParamObject("ds_list", ds_list);
			
			zlet006i.addParamObject("G_MANDT", mandt);
			zlet006i.addParamObject("G_USER_ID", ernam);
			
			for(int i=0; i<ds_list.getRowCount(); i++) {
				ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
				ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet006i.setRowIndex(i);
				
				executor.execute(zlet006i);
			}
			
			DatasetSqlRequest zlet007i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U4");
			zlet007i.addParamObject("ds_list", ds_list);
			
			zlet007i.addParamObject("G_MANDT", mandt);
			zlet007i.addParamObject("G_USER_ID", ernam);
			
			for(int i=0; i<ds_list.getRowCount(); i++) {
				ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
				ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
				zlet007i.setRowIndex(i);
				
				executor.execute(zlet007i);
			}
			
			if(temp != null)
			{				
				
				DatasetSqlRequest zlet041i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_D1");
				zlet041i.addParamObject("ds_list", ds_list);
				
				zlet041i.addParamObject("G_MANDT", mandt);
				zlet041i.addParamObject("G_USER_ID", ernam);
				
				for(int i=0; i<ds_list.getRowCount(); i++) {
					ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));		
					ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
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
					
		
		if(strZGubun.equals("11")) // ZLET002
		{
	
			String tplstdt1 = ds_list.getColumnAsString(0, "TPLSTDT1");
			String tplstdtqty1 = ds_list.getColumnAsString(0, "TPLSTDTQTY1");
	
			
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
			if(ds_list.getColumnAsString(0, "TPLSTDT2") != null)
			{
				tplstdt2 = ds_list.getColumnAsString(0, "TPLSTDT2");
				tplstdtqty2 = ds_list.getColumnAsString(0, "TPLSTDTQTY2");
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
			if(ds_list.getColumnAsString(0, "TPLSTDT3") != null)
			{
				tplstdt3 = ds_list.getColumnAsString(0, "TPLSTDT3");
				tplstdtqty3 = ds_list.getColumnAsString(0, "TPLSTDTQTY3");
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
			if(ds_list.getColumnAsString(0, "TPLSTDT4") != null)
			{
				tplstdt4 = ds_list.getColumnAsString(0, "TPLSTDT4");
				tplstdtqty4 = ds_list.getColumnAsString(0, "TPLSTDTQTY4");
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
			if(ds_list.getColumnAsString(0, "TPLSTDT5") != null)
			{
				tplstdt5 = ds_list.getColumnAsString(0, "TPLSTDT5");
				tplstdtqty5 = ds_list.getColumnAsString(0, "TPLSTDTQTY5");
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
			if(ds_list.getColumnAsString(0, "TPLSTDT6") != null)
			{
				tplstdt6 = ds_list.getColumnAsString(0, "TPLSTDT6");
				tplstdtqty6 = ds_list.getColumnAsString(0, "TPLSTDTQTY6");
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
			if(ds_list.getColumnAsString(0, "TPLSTDT7") != null)
			{
				tplstdt7 = ds_list.getColumnAsString(0, "TPLSTDT7");
				tplstdtqty7 = ds_list.getColumnAsString(0, "TPLSTDTQTY7");
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
			
		}
		else	// ZLET006
		{ 
			if(zton1> 0)
			{		
				for(int j=0; j<zton1; j++)
				{
					zlet005i.addParamObject("ZKNUM", CommonUtil.nullToBlank(zknum));
					zlet005i.addParamObject("ZKNUM2", CommonUtil.nullToBlank(zknum2));
					zlet005i.addParamObject("ZTON", ton1);
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));	
							
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
						
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
								
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
								
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
						
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
								
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
						
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
							
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
							
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
							
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
							
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
							
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
							
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
							
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
					zlet005i.addParamObject("TPLSTDT", ds_list.getColumnAsString(0, "BEZEI"));
							
					zlet005i.setRowIndex(j);					
					executor.execute(zlet005i);
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
	
	
	
	
	public void save2(BusinessContext ctx, Dataset ds_list) throws Exception {
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
		String ton9 = "I";
		String ton10 = "J";
		String ton11 = "K";
		String ton12 = "L";
		String ton13 = "M";
		String ton14 = "N";
		String ton15 = "O";
		
		String erdat = ds_list.getColumnAsString(0, "ERDAT");
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
		int zton9 = ds_list.getColumnAsInteger(0, "ZTON9");
		int zton10 = ds_list.getColumnAsInteger(0, "ZTON10");
		int zton11 = ds_list.getColumnAsInteger(0, "ZTON11");
		int zton12 = ds_list.getColumnAsInteger(0, "ZTON12");
		int zton13 = ds_list.getColumnAsInteger(0, "ZTON13");
		int zton14 = ds_list.getColumnAsInteger(0, "ZTON14");
		int zton15 = ds_list.getColumnAsInteger(0, "ZTON15");
		
		String strZGubun = ds_list.getColumnAsString(0, "ZTYPE");
	
		String sql = "";		
		String temp = "";
		
		String db_con = Utillity.getSapJndi(mandt);
		
		SqlRequest sqlRequest = null;
		SqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = null;
		
		
		
		
		// 호차번호 신규 채번
		sql  = " SELECT ZKNUM FROM TABLE(SAPHEE.GET_HOCHA_NO('"+mandt+"','"+erdat+"','"+ernam+"')) AS T ";

		// SqlRequest 생성
		sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);


		result = executor.query(sqlRequest);

		// 조회 결과 객체 찾아오기 
		Dataset returnDs = (Dataset)result.getResultObject();
		String zknumNew = returnDs.getColumnAsString(0, "ZKNUM");
		
		System.out.println("zknumNew값은 "+ zknumNew);
		
		
		if (TitUtility.isNotNull(zknumNew)) {
	
			if(strZGubun.equals("11")) // ZLET002
			{
				// ZLET002
				DatasetSqlRequest zlet002i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U6");
				zlet002i.addParamObject("ds_list", ds_list);
				
				zlet002i.addParamObject("G_MANDT", mandt);
				zlet002i.addParamObject("G_USER_ID", ernam);
				
				for(int i=0; i<ds_list.getRowCount(); i++) {
					ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
					ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
					ds_list.setColumn(i, "NEWZKNUM", CommonUtil.nullToBlank(zknumNew));
					ds_list.setColumn(i, "ERDAT", CommonUtil.nullToBlank(erdat));
					zlet002i.setRowIndex(i);
					
					executor.execute(zlet002i);
				}
				
				// ZLET003
				DatasetSqlRequest zlet003i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U7");
				zlet003i.addParamObject("ds_list", ds_list);
				
				zlet003i.addParamObject("G_MANDT", mandt);
				zlet003i.addParamObject("G_USER_ID", ernam);
				
				for(int i=0; i<ds_list.getRowCount(); i++) {
					ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
					ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
					ds_list.setColumn(i, "NEWZKNUM", CommonUtil.nullToBlank(zknumNew));
					zlet003i.setRowIndex(i);
					
					executor.execute(zlet003i);
				}
				
				
				// ZLET017
				DatasetSqlRequest zlet017i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U11");
				zlet017i.addParamObject("ds_list", ds_list);
				
				zlet017i.addParamObject("G_MANDT", mandt);
				zlet017i.addParamObject("G_USER_ID", ernam);
				
				for(int i=0; i<ds_list.getRowCount(); i++) {
					ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
					ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
					ds_list.setColumn(i, "NEWZKNUM", CommonUtil.nullToBlank(zknumNew));
					zlet017i.setRowIndex(i);
					
					executor.execute(zlet017i);
				}
					
			}
				
			
			else	// ZLET006
			{ 
				
				// ZLET006
				DatasetSqlRequest zlet006i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U8");
				zlet006i.addParamObject("ds_list", ds_list);
				
				zlet006i.addParamObject("G_MANDT", mandt);
				zlet006i.addParamObject("G_USER_ID", ernam);
				
				for(int i=0; i<ds_list.getRowCount(); i++) {
					ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
					ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
					ds_list.setColumn(i, "NEWZKNUM", CommonUtil.nullToBlank(zknumNew));
					zlet006i.setRowIndex(i);
					
					executor.execute(zlet006i);
				}
				
				//	ZLET007
				DatasetSqlRequest zlet007i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U9");
				zlet007i.addParamObject("ds_list", ds_list);
				
				zlet007i.addParamObject("G_MANDT", mandt);
				zlet007i.addParamObject("G_USER_ID", ernam);
				
				for(int i=0; i<ds_list.getRowCount(); i++) {
					ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));
					ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
					ds_list.setColumn(i, "NEWZKNUM", CommonUtil.nullToBlank(zknumNew));
					zlet007i.setRowIndex(i);
					
					executor.execute(zlet007i);
				}
			}	
			
			
			
			// ZLET041
			DatasetSqlRequest zlet041i = SqlMapManagerUtility.makeSqlRequestForDataset("mm05:MM0501003B_U10");
			zlet041i.addParamObject("ds_list", ds_list);
			
			zlet041i.addParamObject("G_MANDT", mandt);
			zlet041i.addParamObject("G_USER_ID", ernam);
			
			for(int i=0; i<ds_list.getRowCount(); i++) {
				ds_list.setColumn(i, "ZKNUM", CommonUtil.nullToBlank(zknum));		
				ds_list.setColumn(i, "ZKNUM2", CommonUtil.nullToBlank(zknum2));
				ds_list.setColumn(i, "NEWZKNUM", CommonUtil.nullToBlank(zknumNew));
				zlet041i.setRowIndex(i);
				
				executor.execute(zlet041i);
			}
			
			
		}					
						
	
					
		
		
		
	}
}
