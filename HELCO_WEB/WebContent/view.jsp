<%@ page contentType="text/html;charset=euc-kr" pageEncoding="euc-kr" import="java.sql.*, java.util.* " %>
<%@ page import="com.helco.xf.cs12.evladm.dbutil.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script language="javascript">

function fn_update() {
    var frm = document.frm;
	frm.chk.value = "Y";
	frm.action = "update.jsp";
	frm.submit();
}

</script>
</head>

<%
String t_mdt = request.getParameter("mdt");
String t_upn = request.getParameter("upn");
String t_seq = request.getParameter("seq");
String t_dat = request.getParameter("dat");

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
	conn = DBUtil.getConnection("java:comp/env/jdbc/hep");

	String sql = "SELECT A.CS180_UPN, A.CS180_SEQ, A.CS180_CEL, A.CS180_CES, A.CS180_NAM, A.CS180_RMK, B.CS181_DAT, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM1) PM1, " + 
					"SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM2) PM2, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ1) SJ1, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ2) SJ2, " + 
					"B.CS181_TXA, B.CS181_TXB, B.CS181_TXC, B.CS181_TXD, B.CS181_TXE, B.CS181_TXF, B.CS181_RMK FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
					"WHERE A.MANDT = '" + t_mdt + "' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
					"AND B.CS181_UPN = '" + t_upn + "' AND B.CS181_SEQ = '" + t_seq + "' AND B.CS181_DAT = '" + t_dat + "'";

	pstmt = conn.prepareStatement(sql);
	rs = pstmt.executeQuery();
%>

<body bgcolor="#FFFFFF" onunload="javascript:fn_update();">
<form name="frm" method="post" target="MsgFrm" action="update.jsp">
<table width="660" border="1" cellspacing="0" cellpadding="0">

<%
	while(rs.next()) {
		String upn = rs.getString("CS180_UPN");
		String seq = rs.getString("CS180_SEQ");
		String nam = rs.getString("CS180_NAM");
		int cel = rs.getInt("CS180_CEL");
		int ces = rs.getInt("CS180_CES");
		int tot = cel + ces;
		String txt = rs.getString("CS180_RMK");
		String pm1 = rs.getString("PM1");
		String pm2 = rs.getString("PM2");
		String sj1 = rs.getString("SJ1");
		String sj2 = rs.getString("SJ2");
		String txa = rs.getString("CS181_TXA");
		String txb = rs.getString("CS181_TXB");
		String txc = rs.getString("CS181_TXC");
		String txd = rs.getString("CS181_TXD");
		String txe = rs.getString("CS181_TXE");
		String txf = rs.getString("CS181_TXF");
		String rmk = rs.getString("CS181_RMK");
%>

<input type="hidden" name="chk" value="N">
<input type="hidden" name="mdt" value="<%= t_mdt%>">
<input type="hidden" name="upn" value="<%= t_upn%>">
<input type="hidden" name="seq" value="<%= t_seq%>">
<input type="hidden" name="dat" value="<%= t_dat%>">

<tr>
	<td width="220">TXA  : <input type="text" name="txa" value="<%= txa%>" readonly></td>
	<td width="220">TXB  : <input type="text" name="txb" value="<%= txb%>" readonly></td>
	<td width="220">TXC  : <input type="text" name="txc" value="<%= txc%>" readonly></td>
</tr>
<tr>
	<td width="220">TXD  : <input type="text" name="txd" value="<%= txd%>" readonly></td>
	<td width="220">TXE  : <input type="text" name="txe" value="<%= txe%>" readonly></td>
	<td width="220">TXF  : <input type="text" name="txf" value="<%= txf%>" readonly></td>
</tr>
<tr>
	<td colspan="3">의견 : <input type="text" name="rmk" value="<%= rmk%>"></td>
</tr>
<tr>
	<td colspan="3" align="left"><input type="submit" name="btn_submit" value="저 장"></td>
</tr>

<%
	}
} catch (Exception e) {
	e.printStackTrace();
	throw e;
} finally {
	try {
		rs.close();
		pstmt.close();
		conn.close();
	} catch (Exception te) {
		throw te;
	}
}
%>

</table>
</form>
<iframe name="MsgFrm" frameborder="no" scrolling="no" marginwidth="0" marginheight="0"></iframe>
</body>
</html>
