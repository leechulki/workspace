<%@ page contentType="text/html;charset=euc-kr" pageEncoding="euc-kr" import="java.sql.*, java.util.* " %>
<%@ page import="com.helco.xf.cs12.evladm.dbutil.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script language="javascript">

function fn_view(mdt, upn, seq, dat) {
    var frm = document.frm;
	frm.mdt.value = mdt;
	frm.upn.value = upn;
	frm.seq.value = seq;
	frm.dat.value = dat;
	frm.target = "_blank";
	frm.action = "view.jsp";
	frm.submit();
}

</script>
</head>

<%
Connection conn = null;
PreparedStatement pstmt1 = null;
ResultSet rs1 = null;
PreparedStatement pstmt2 = null;
ResultSet rs2 = null;
int cnt = 0;

try {
	conn = DBUtil.getConnection("java:comp/env/jdbc/hep");

	String sql1 = "SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
					"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FLG = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
					"FETCH FIRST 5 ROWS ONLY";

	String sql2 = "SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||'-'||B.CS181_DAT VLIST FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
					"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FLG = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
					"ORDER BY B.CS181_DAT DESC, B.CS181_UPN FETCH FIRST 5 ROWS ONLY";

	pstmt1 = conn.prepareStatement(sql1);
	rs1 = pstmt1.executeQuery();

	pstmt2 = conn.prepareStatement(sql2);
	rs2 = pstmt2.executeQuery();

	if(rs1.next()) {
		cnt = rs1.getInt("CNT");
	}
%>

<body bgcolor="#FFFFFF">
<form name="frm" method="post">
<table width="660" border="1" cellspacing="0" cellpadding="0">

<input type="hidden" name="mdt">
<input type="hidden" name="upn">
<input type="hidden" name="seq">
<input type="hidden" name="dat">

<tr>
	<td width="500"><%= cnt%></td>
</tr>

<%
	while(rs2.next()) {
		String t_mdt = rs2.getString("MANDT");
		String t_upn = rs2.getString("CS181_UPN");
		String t_seq = rs2.getString("CS181_SEQ");
		String t_dat = rs2.getString("CS181_DAT");
		String vlist = rs2.getString("VLIST");
%>

<tr>
	<td width="500"><a href="javascript:fn_view('<%=t_mdt%>','<%=t_upn%>','<%=t_seq%>','<%=t_dat%>');"><%= vlist%></a></td>
</tr>

<%
	}
} catch (Exception e) {
	e.printStackTrace();
	throw e;
} finally {
	try {
		rs1.close();
		pstmt1.close();
		rs2.close();
		pstmt2.close();
		conn.close();
	} catch (Exception te) {
		throw te;
	}
}
%>

</table>
</form>
</body>
</html>
