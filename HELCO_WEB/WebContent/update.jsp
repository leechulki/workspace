<%@ page contentType="text/html;charset=euc-kr" pageEncoding="euc-kr" import="java.sql.*, java.util.* " %>
<%@ page import="com.helco.xf.cs12.evladm.dbutil.*" %>

<%
String t_chk = request.getParameter("chk");
String t_mdt = request.getParameter("mdt");
String t_upn = request.getParameter("upn");
String t_seq = request.getParameter("seq");
String t_dat = request.getParameter("dat");
String t_rmk = new String(request.getParameter("rmk").getBytes("8859_1"),"euc-kr");
String sql = "";
int cnt = 0;

Connection conn = null;
PreparedStatement pstmt = null;

try {
	conn = DBUtil.getConnection("java:comp/env/jdbc/hep");

	if(t_chk.equals("N")) {
		sql = "UPDATE SAPHEE.ZCST181 SET CS181_FLG = 'Y', CS181_RMK = '" + t_rmk + "', CS181_RDT = HEX(CURRENT DATE), CS181_RTM = HEX(CURRENT TIME) " + 
				"WHERE MANDT = '" + t_mdt + "' AND CS181_UPN = '" + t_upn + "' AND CS181_SEQ = '" + t_seq + "' AND CS181_DAT = '" + t_dat + "'";
	} else {
		sql = "UPDATE SAPHEE.ZCST181 SET CS181_FLG = 'Y', CS181_RDT = HEX(CURRENT DATE), CS181_RTM = HEX(CURRENT TIME) " + 
				"WHERE MANDT = '" + t_mdt + "' AND CS181_UPN = '" + t_upn + "' AND CS181_SEQ = '" + t_seq + "' AND CS181_DAT = '" + t_dat + "'";
	}

	pstmt = conn.prepareStatement(sql);
	cnt = pstmt.executeUpdate();

	if(t_chk.equals("N")) {
		if(cnt > 0) {
%>
			<script> 
				alert("저장에 성공하였습니다.");
			</script>
<%
		} else {
%>
			<script> 
				alert("저장에 실패하였습니다.");
			</script>
<%
		}
	}
} catch (Exception e) {
	e.printStackTrace();
	throw e;
} finally {
	try {
		pstmt.close();
		conn.close();
	} catch (Exception te) {
		throw te;
	}
}
%>
