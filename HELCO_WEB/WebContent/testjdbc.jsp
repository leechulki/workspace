
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.ResultSetMetaData"%>

<%@page import="javax.naming.InitialContext"%><%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	Connection c = null;
	Statement s = null;
	try{
		InitialContext ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("jdbc/cs_uc");
		
		 c = ds.getConnection();
		
		c.setAutoCommit(false);
		
		 s = c.createStatement();
		
		
		
		ResultSet r = s.executeQuery("SELECT 	 NAME AS NAME	 FROM SYSIBM.SYSTABLES fetch first 100 rows only");
		
		ResultSetMetaData rm = r.getMetaData();
		
		rm.getColumnName(1);
		rm.getColumnDisplaySize(1);
		rm.getColumnLabel(1);
		rm.getColumnType(1);
		rm.getColumnTypeName(1);
		
		r.setFetchSize(10000);

		
		
		while(r.next()){
			out.println("NAME==>" + r.getString("NAME") +"<P>" );
		}
		
		
		s.close();
		c.close();
	}catch(Exception e){
		e.printStackTrace();
	}

%>