<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%
String t_userid = request.getParameter("userid");
%>

<HTML>
	<HEAD>
		<title></title>
		<META http-equiv=Content-Type content="text/html; charset=utf-8">
		<link rel="stylesheet" href="./images/style.css" type="text/css">
	</HEAD>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	
		<table width="175" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="55"><img src="./images/left_title.gif" width="175" height="55"></td>
			</tr>
			<tr>
				<td valign="top" bgcolor="#f0f0f0" style="BORDER-RIGHT:#dadada 1px solid; BORDER-LEFT:#dadada 5px solid">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="1" colspan="2" class="leftline1"></td>
						</tr>
						<tr>
							<td width="27" align="right"><img src="./images/left_dot01.gif" width="9" height="9" hspace="5"></td>
							<td class="leftmenu"><a href="Reportlist.jsp?cpage=1&sortby=&chkflg=&listall=N&userid=<%=t_userid%>" class="l" target="right" style="text-decoration:none"><span id="menu00">일일업무 보고서</span></a></td> 
						</tr>
					</table>
				</TD>
			</TR>
		</TABLE>
		
	</body>
</HTML>