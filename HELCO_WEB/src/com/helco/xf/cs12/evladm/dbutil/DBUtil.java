/**
 * 파 일 명 : DBUtil.java
 * 설    명 : WAS에 등록 되어 있는 DataSource에 대한 유틸
 * 작 성 자 : 구자경
 * 작 성 일 : 2006-02-14
 * 변경내역 :
 *
 * struts-Config : 
 * 
 * 
 */ 
package com.helco.xf.cs12.evladm.dbutil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

//추가
import javax.naming.NameNotFoundException;

public class DBUtil
{
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.02.14
    * 설  명 : WAS에 등록되어 있는 udbDS를 로콜방식으로 생성하여 커넥션을 반환
    * 기  타 :
    */  

	public static Connection getConnection() throws SQLException
	{
		Connection conn = null;
		try
		{
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/udbDS");
			conn = ds.getConnection();
		}
		catch (SQLException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}

   /**
    * 작성자 : 구자경
    * 작성일 : 2006.02.14
    * 설  명 : WAS에 등록되어 있는 Datasource중 전달받은 DataSource면에 대한 커넥션을 반환
    * 기  타 :
    */     
	public static Connection getConnection(String dsName) throws SQLException
	{
		Connection conn = null;
		
		try
		{
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dsName);
			conn = ds.getConnection();
		}
		catch (SQLException e)
		{
			throw e;
		}
		
		catch (NameNotFoundException e) 
		{
			try
			{
				InitialContext ctx = new InitialContext();
				dsName = "java:comp/env/" + dsName;
				DataSource ds = (DataSource) ctx.lookup(dsName);
				conn = ds.getConnection();
			}
			catch (SQLException x)
			{
				throw x;
			}
			
			catch (Exception x)
			{
				x.printStackTrace();
			}
		}		
		
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		

		
		
		
		
		return conn;
	}
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.02.14
    * 설  명 : DAO 에서 PreparedStatement등을 생성후 resource를 해제 
    * 기  타 :
    */     
	public static void close(ResultSet rs, Statement stmt, Connection conn)
	{
		if (rs != null)
			try
			{
				rs.close();
			}
			catch (Exception e)
			{
			}
		if (stmt != null)
			try
			{
				stmt.close();
			}
			catch (Exception e)
			{
			}
		if (conn != null)
			try
			{
				conn.close();
			}
			catch (Exception e)
			{
			}
	}
}
