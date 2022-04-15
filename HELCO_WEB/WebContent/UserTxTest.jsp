<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="javax.transaction.UserTransaction"%>
<%@page import="tit.base.ServiceManagerFactory"%>
<%@page import="tit.service.jndi.JndiFinder"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	JndiFinder jndi = (JndiFinder)
			ServiceManagerFactory.getServiceObject("DatasourceJndiFinder");
	
	
	UserTransaction tx = (UserTransaction) 
		jndi.lookup(ServiceManagerFactory.getProperty("G_USER_TX"));
	tx.begin();
	tx.commit();
%>
success!
</body>
</html>





