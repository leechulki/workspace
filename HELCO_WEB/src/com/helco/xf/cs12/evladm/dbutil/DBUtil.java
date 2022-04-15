/**
 * �� �� �� : DBUtil.java
 * ��    �� : WAS�� ��� �Ǿ� �ִ� DataSource�� ���� ��ƿ
 * �� �� �� : ���ڰ�
 * �� �� �� : 2006-02-14
 * ���泻�� :
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

//�߰�
import javax.naming.NameNotFoundException;

public class DBUtil
{
   /**
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.02.14
    * ��  �� : WAS�� ��ϵǾ� �ִ� udbDS�� ���ݹ������ �����Ͽ� Ŀ�ؼ��� ��ȯ
    * ��  Ÿ :
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
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.02.14
    * ��  �� : WAS�� ��ϵǾ� �ִ� Datasource�� ���޹��� DataSource�鿡 ���� Ŀ�ؼ��� ��ȯ
    * ��  Ÿ :
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
    * �ۼ��� : ���ڰ�
    * �ۼ��� : 2006.02.14
    * ��  �� : DAO ���� PreparedStatement���� ������ resource�� ���� 
    * ��  Ÿ :
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
