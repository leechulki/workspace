<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%
String t_userid = request.getParameter("userid");
%>

<frameset cols="175,*" border="0" framespacing="0" frameborder="NO">
  <frame src="./left.jsp?userid=<%=t_userid%>" marginwidth="0" marginheight="0" scrolling="NO" frameborder="NO" name="left">
  <frame src="./Reportlist.jsp?cpage=1&sortby=&chkflg=&listall=N&userid=<%=t_userid%>"  marginwidth="0" marginheight="0" scrolling="AUTO" frameborder="NO" name="right">
</frameset>
