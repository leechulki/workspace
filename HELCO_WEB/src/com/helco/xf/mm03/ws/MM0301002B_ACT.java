package com.helco.xf.mm03.ws;

import java.sql.ResultSet;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;



import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;

public class MM0301002B_ACT extends AbstractAction 
{
	//private static final long serialVersionUID = 5661911687794518235L;

	
	// 거래명세서 생성
	public void query(BusinessContext ctx) throws Exception{
		
		Dataset ds_mts = ctx.getInputDataset("ds_mts");
		//String sInvnr = DatasetUtility.getString(ds_mts, "INVNR");
		
		
		String db_con = "jdbc/mts";
		
		SqlRequest sqlRequest = null;
		SqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
			
		SqlResult result = null;
			
		DatasetSqlRequest zlet017i = SqlMapManagerUtility.makeSqlRequestForDataset("mm11:MM1102001A_I3");
		zlet017i.addParamObject("ds_mts", ds_mts);
			
		//sqlRequest.addParamObject("INVNR", sInvnr);			
				
		for(int i=0; i<ds_mts.getRowCount(); i++)
		{						
			System.out.println( ds_mts.getColumnAsString(i, "INVITEM"));
			zlet017i.setRowIndex(i);
			executor.execute(zlet017i);
		}	

		//ctx.addOutputDataset("ds_mtsresult",ds_mtsresult);
	}
	
	
	// 거래명세서 취소
	public void query2(BusinessContext ctx) throws Exception{
		
		Dataset ds_condmts2 = ctx.getInputDataset("ds_condmts2");
		//String sInvnr = DatasetUtility.getString(ds_mts, "INVNR");
		
		
		String db_con = "jdbc/mts";
		
		SqlRequest sqlRequest = null;
		SqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
			
		SqlResult result = null;
			
		DatasetSqlRequest zlet017u = SqlMapManagerUtility.makeSqlRequestForDataset("mm11:MM1102001A_U4");
		zlet017u.addParamObject("ds_condmts2", ds_condmts2);
			
		//sqlRequest.addParamObject("INVNR", sInvnr);			
				
		for(int i=0; i<ds_condmts2.getRowCount(); i++)
		{
			zlet017u.setRowIndex(i);
			executor.execute(zlet017u);
		}	

		//ctx.addOutputDataset("ds_mtsresult",ds_mtsresult);
	}
	
	
	// 거래명세서 전송
	public void query3(BusinessContext ctx) throws Exception{
		
		Dataset ds_condmts= ctx.getInputDataset("ds_condmts");
		//String sInvnr = DatasetUtility.getString(ds_mts, "INVNR");
		
		
		String db_con = "jdbc/mts";
		
		SqlRequest sqlRequest = null;
		SqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
			
		SqlResult result = null;
			
		DatasetSqlRequest zlet017u2 = SqlMapManagerUtility.makeSqlRequestForDataset("mm11:MM1102002A_U5");
		zlet017u2.addParamObject("ds_condmts", ds_condmts);
			
		//sqlRequest.addParamObject("INVNR", sInvnr);			
				
		for(int i=0; i<ds_condmts.getRowCount(); i++)
		{
			zlet017u2.setRowIndex(i);
			executor.execute(zlet017u2);
		}	

		//ctx.addOutputDataset("ds_mtsresult",ds_mtsresult);
	}
	
	
	// 거래명세서 전송 취소
	public void query4(BusinessContext ctx) throws Exception{
			
		Dataset ds_condmts= ctx.getInputDataset("ds_condmts");
		//String sInvnr = DatasetUtility.getString(ds_mts, "INVNR");
		
		
		String db_con = "jdbc/mts";
		
		SqlRequest sqlRequest = null;
		SqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
			
		SqlResult result = null;
			
		DatasetSqlRequest zlet017u3 = SqlMapManagerUtility.makeSqlRequestForDataset("mm11:MM1102002A_U6");
		zlet017u3.addParamObject("ds_condmts", ds_condmts);
			
		//sqlRequest.addParamObject("INVNR", sInvnr);			
				
		for(int i=0; i<ds_condmts.getRowCount(); i++)
		{
			zlet017u3.setRowIndex(i);
			executor.execute(zlet017u3);
		}	

		//ctx.addOutputDataset("ds_mtsresult",ds_mtsresult);
	}
	
	
	
	class MyDatasetHelper implements DatasetHelper 
	{
		public Object getDsValue(String dsColName, Object colValue,	Object orgObj) 
		{
			if ( colValue == null ) 
			{
				return null;
			}

			if ( dsColName.equals("EXPORT") ) 
			{
				return ((String) colValue).equals("0") ? "1" : "0";
			} 
			else if ( dsColName.equals("INSGB")) 
			{
				return ((String) colValue).equals("X") ? "1" : "0";
			}

			return colValue;
		}
	}
}

