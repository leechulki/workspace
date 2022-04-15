<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="java.sql.*, java.util.* " %>
<%@ page import="com.helco.xf.cs12.evladm.dbutil.*" %>

<%
String t_userid = request.getParameter("userid");
%>
<HTML><HEAD><TITLE>게시물 리스트</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="./images/style.css" type=text/css rel=stylesheet>
<SCRIPT language=JavaScript>

function MiniGotoList()
{
//alert(top.frames[1].location.href);
//	parent.top.frames("main").location.href = "http://srm.hdel.co.kr/HELCO_WEB/main.jsp";
	parent.top.frames[1].location.href = "./main.jsp?userid=<%=t_userid%>";
//	window.open("./main.jsp", "main");
//	parent.top.frames("main").location.href = "./main.jsp";
}

function ItemRead_onclick(mdt, upn, seq, dat, num)
{
	var pheigth = window.screen.availHeight;
	var pwidth = window.screen.availWidth;
	var rHeight = 657;
	var rWidth = 720;
		
	pheigth = parseInt(pheigth - rHeight) / 2;
	pwidth = parseInt(pwidth - rWidth) / 2;
	
	window.open("./BoardItemView.jsp?mdt="+mdt+"&upn="+upn+"&seq="+seq+"&dat="+dat+"&num="+num+"&pag=N&userid=<%=t_userid%>", "", "height=657,width=720px, status = no, toolbar=no, menubar=no, location=no, resizable=1, top=" + pheigth + ",left = " + pwidth,"");
}

function refresh_onclick()
{
	window.location.reload(false);
}

</SCRIPT>
</HEAD>

<%
Connection conn = null;
PreparedStatement pstmt1 = null;
ResultSet rs1 = null;
PreparedStatement pstmt2 = null;
ResultSet rs2 = null;
int cnt = 0;
String sql1 = "";
String sql2 = "";

try {
	conn = DBUtil.getConnection("jdbc/hep");

	if(t_userid.substring(5).equals("1001094")) { //사장님
		sql1 = "SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FLG = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||' - '||B.CS181_DAT VLIST, " + 
				"ROW_NUMBER() OVER (ORDER BY B.CS181_DAT DESC, B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FLG = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"ORDER BY B.CS181_DAT DESC, B.CS181_UPN FETCH FIRST 5 ROWS ONLY) X";
	} else if(t_userid.substring(5).equals("1001086")) { //부사장님
		sql1 = "SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FL2 = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||' - '||B.CS181_DAT VLIST, " + 
				"ROW_NUMBER() OVER (ORDER BY B.CS181_DAT DESC, B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FL2 = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"ORDER BY B.CS181_DAT DESC, B.CS181_UPN FETCH FIRST 5 ROWS ONLY) X";
	} else if(t_userid.substring(5).equals("1001248")) { //상무님
		sql1 = "SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FL3 = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||' - '||B.CS181_DAT VLIST, " + 
				"ROW_NUMBER() OVER (ORDER BY B.CS181_DAT DESC, B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FL3 = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"ORDER BY B.CS181_DAT DESC, B.CS181_UPN FETCH FIRST 5 ROWS ONLY) X";
	} else { //그외
		sql1 = "SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FL3 = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||' - '||B.CS181_DAT VLIST, " + 
				"ROW_NUMBER() OVER (ORDER BY B.CS181_DAT DESC, B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND B.CS181_FL3 = 'N' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"ORDER BY B.CS181_DAT DESC, B.CS181_UPN FETCH FIRST 5 ROWS ONLY) X";
	}

	pstmt1 = conn.prepareStatement(sql1);
	rs1 = pstmt1.executeQuery();

	pstmt2 = conn.prepareStatement(sql2);
	rs2 = pstmt2.executeQuery();

	if(rs1.next()) {
		cnt = rs1.getInt("CNT");
	}
%>

<body leftMargin="0" topMargin="0" marginheight="0" marginwidth="0">

<table  width="94%" border="0" cellpadding="0" cellspacing="0">	
<tr><td valign="top">
 
	<table id=table1 border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td id=pColor height="1" colspan="3" bgcolor="#d5d5d5"></td>
	</tr>
	<tr>
	<td height="22" id=pColor1 bgcolor="#E9E9E9" style="CURSOR: hand;" onclick="MiniGotoList()">
		<span id="BoardNm"  style="font-size:9pt;color:#11228C;font-weight:bold;padding-left:12px">중점현장 일일업무 보고서</span>
		<font color="A3A3A3">&nbsp;|</font> <font color="CD6600"><%= cnt%></font> 건
	</td>
	<td id=pColor2 width="35" bgcolor="#E9E9E9" style="CURSOR: hand;" onclick="MiniGotoList()">
		<span class="plist">
			<img src="./images/btn_more02.gif" width="31" height="13" border="0" align="absmiddle">
		</span>
	</td>
	</table>

	<table id=table2 border="0" cellpadding="0" cellspacing="0" style="TABLE-LAYOUT: fixed;margin-top:7px" width="100%">

<%
	while(rs2.next()) {
		String t_mdt = rs2.getString("MANDT");
		String t_upn = rs2.getString("CS181_UPN");
		String t_seq = rs2.getString("CS181_SEQ");
		String t_dat = rs2.getString("CS181_DAT");
		String vlist = rs2.getString("VLIST");
		int t_num = rs2.getInt("NUM");
%>

		<TR onclick="ItemRead_onclick('<%=t_mdt%>','<%=t_upn%>','<%=t_seq%>','<%=t_dat%>','<%=t_num%>');">
			<td align="center" width="15"><img src="./images/dot_03.gif" width="3" height="3" hspace="5" align="absmiddle"></td>	
			<td height="20" style="overflow:hidden;text-overflow:ellipsis">
			<nobr><SPAN onmouseover="this.style.color='#006BB6'" title="<%= vlist%>" style="CURSOR: hand"
				onmouseout="this.style.color='#393939'">
					<%= vlist%>
			</SPAN>&nbsp;</nobr></TD>
		</TR>

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
	
	</td>
	</tr>
</table>
</body>
</HTML>