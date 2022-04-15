<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="java.sql.*, java.util.* " %>
<%@ page import="com.helco.xf.cs12.evladm.dbutil.*" %>

<%
String t_sort = request.getParameter("sortby");
String t_chkflg = request.getParameter("chkflg");
String chkimg = "0";
int cpage = Integer.parseInt(request.getParameter("cpage"));
int pnum = 0;
int nnum = 0;
String t_str = "";
String t_str2 = "";
String t_listall = request.getParameter("listall");
String t_userid = request.getParameter("userid");

if(cpage == 0) {
	cpage = 1;
}

pnum = ((cpage - 1) * 20) + 1;
nnum = cpage * 20;

Connection conn = null;
PreparedStatement pstmt1 = null;
ResultSet rs1 = null;
PreparedStatement pstmt2 = null;
ResultSet rs2 = null;
int tpage = 0;
String sql1 = "";
String sql2 = "";
%>

<html>
  <head>
    <title>일일업무 보고서</title>
	<META http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel="stylesheet" href="./images/style.css" type="text/css">
    <script language="javascript">

		function prevPage_onclick(tpage, cpage)
		{
			newPage = parseInt(cpage) - 1;
			if(newPage > 0) {
				window.location.href = "Reportlist.jsp?cpage=" + newPage + "&sortby=<%=t_sort%>&chkflg=<%=t_chkflg%>&listall=<%=t_listall%>&userid=<%=t_userid%>";
			}
		}

		function nextPage_onclick(tpage, cpage)
		{
			newPage = parseInt(cpage) + 1;
			if(newPage <= parseInt(tpage)) {
				window.location.href = "Reportlist.jsp?cpage=" + newPage + "&sortby=<%=t_sort%>&chkflg=<%=t_chkflg%>&listall=<%=t_listall%>&userid=<%=t_userid%>";
			}
		}

		function moveToPage(tpage)
		{
			if(window.event.keyCode == 13)
			{
				var newPage = txt_PageInputNum.value;

				if(parseInt(newPage) > 0 && parseInt(newPage) <= parseInt(tpage)) 
				{
					window.location.href = "Reportlist.jsp?cpage=" + newPage + "&sortby=<%=t_sort%>&chkflg=<%=t_chkflg%>&listall=<%=t_listall%>&userid=<%=t_userid%>";
				}
			}
		}
		
		function refresh_onclick()
		{
			window.location.reload(false);
			//parent.frames("left").document.Script.getCountMyList_total();
		}
		
			
		function Read_onclick(mdt, upn, seq, dat, num)
		{
			var pheigth = window.screen.availHeight;
			var pwidth = window.screen.availWidth;
			var rHeight = 657;
			var rWidth = 720;
			
			pheigth = parseInt(pheigth - rHeight) / 2;
			pwidth = parseInt(pwidth - rWidth) / 2;
			
			window.open("./BoardItemView.jsp?mdt="+mdt+"&upn="+upn+"&seq="+seq+"&dat="+dat+"&num="+num+"&pag=Y&userid=<%=t_userid%>", "", "height=657,width=720px, status = no, toolbar=no, menubar=no, location=no, resizable=1, top=" + pheigth + ",left = " + pwidth,"");
		}
		
		// 제안 수정
		function PopUp_Proposal(pProSN)
		{
			var pheight = window.screen.availHeight;
			var pwidth = window.screen.availWidth;
			var pTop = (pheight - 720) / 2;
			var pLeft = (pwidth - 765) / 2;
			
			window.open("proposal_NewProposal.aspx?Mode=modify&"+"ProSN=" + escape(pProSN), "", "toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=1,height=720,width=765,top=" + pTop + ",left=" + pLeft, "");	
			
			try {
						if ( typeof(window.opener.btn_Refresh_onclick()) !="undefined")
							window.opener.btn_Refresh_onclick();
					} catch(e) {
					}
					
			window.close();
		}
		
		// 엑셀로 내보내기 1일경우 전체문서, 0일경우 현재페이지만
		function btnExcel_onclick(AllFG)
		{
			strSearch = "ProcessState=101&UserID=" +pEmployeeNo + "&EmployeeNo=";
			
			url = "/myoffice/ezProposal/excelExportOut2.aspx?"+ escape(strSearch) +  "&Flag=prolist&CompanyCD=" + pCompanyCD + "&FactoryID=" + pFactoryID; 
			saveExcel.location.href = url;
		}
		
		function SortPage(SortBy, chkflg)
		{
			if(chkflg == "A") {
				chkflg = "B";
			} else if(chkflg == "B") {
				chkflg = "A";
			}

			if(chkflg == "") {
				chkflg = "A";
			}

			window.location.href = "Reportlist.jsp?cpage=1&sortby=" + escape(SortBy) + "&chkflg=" + chkflg + "&listall=<%=t_listall%>&userid=<%=t_userid%>";
		}

		function js_listAll()
		{
			if(listall.checked == true) {
				window.location.href = "Reportlist.jsp?cpage=1&sortby=&chkflg=&listall=Y&userid=<%=t_userid%>";
			} else {
				window.location.href = "Reportlist.jsp?cpage=1&sortby=&chkflg=&listall=N&userid=<%=t_userid%>";
			}
		}

	</script>
  </head>

<%
try {
	conn = DBUtil.getConnection("jdbc/hep");

	if(t_sort.equals("NAM")) {
		if(t_chkflg.equals("A")) {
			t_str = "ORDER BY A.CS180_NAM, B.CS181_DAT DESC";
			chkimg = "1";
		} else if(t_chkflg.equals("B")) {
			t_str = "ORDER BY A.CS180_NAM DESC, B.CS181_DAT DESC";
			chkimg = "2";
		}
	} else if(t_sort.equals("MUR")) {
		if(t_chkflg.equals("A")) {
			t_str = "ORDER BY B.CS181_MUR, B.CS181_DAT DESC";
			chkimg = "3";
		} else if(t_chkflg.equals("B")) {
			t_str = "ORDER BY B.CS181_MUR DESC, B.CS181_DAT DESC";
			chkimg = "4";
		}
	} else if(t_sort.equals("MDT")) {
		if(t_chkflg.equals("A")) {
			t_str = "ORDER BY B.CS181_DAT, A.CS180_NAM";
			chkimg = "5";
		} else if(t_chkflg.equals("B")) {
			t_str = "ORDER BY B.CS181_DAT DESC, A.CS180_NAM DESC";
			chkimg = "6";
		}
	} else {
		t_str = "ORDER BY B.CS181_DAT DESC, B.CS181_UPN";
		chkimg = "0";
	}

	if(t_listall.equals("Y")) { //전체 리스트 보기 선택시
		t_str2 = "";
	} else {
		if(t_userid.substring(5).equals("1001094")) { //사장님
			t_str2 = " AND B.CS181_FLG = 'N'";
		} else if(t_userid.substring(5).equals("1001086")) { //부사장님
			t_str2 = " AND B.CS181_FL2 = 'N'";
		} else if(t_userid.substring(5).equals("1001248")) { //상무님
			t_str2 = " AND B.CS181_FL3 = 'N'";
		} else { //그외
			t_str2 = " AND B.CS181_FL3 = 'N'";
		}
	}

	if(t_userid.substring(5).equals("1001094")) { //사장님
		sql1 = "SELECT CHAR(INT((X.CNT - 1)/20) + 1) TPAGE FROM (SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y'" + t_str2 + " AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ) X ";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, A.CS180_NAM, TRIM(A.CS180_NAM)||' - '||B.CS181_DAT VLIST, B.CS181_FLG FLG, " + 
				"C.USERNAME MUR, ROW_NUMBER() OVER (" + t_str + ", B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B, SAPHEE.ZUSERF C " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y'" + t_str2 + " AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"AND B.MANDT = C.MANDT AND B.CS181_MUR = C.USERNUMB " + t_str + ") X WHERE X.NUM BETWEEN " + pnum + " AND " + nnum;
	} else if(t_userid.substring(5).equals("1001086")) { //부사장님
		sql1 = "SELECT CHAR(INT((X.CNT - 1)/20) + 1) TPAGE FROM (SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y'" + t_str2 + " AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ) X ";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, A.CS180_NAM, TRIM(A.CS180_NAM)||' - '||B.CS181_DAT VLIST, B.CS181_FL2 FLG, " + 
				"C.USERNAME MUR, ROW_NUMBER() OVER (" + t_str + ", B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B, SAPHEE.ZUSERF C " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y'" + t_str2 + " AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"AND B.MANDT = C.MANDT AND B.CS181_MUR = C.USERNUMB " + t_str + ") X WHERE X.NUM BETWEEN " + pnum + " AND " + nnum;
	} else if(t_userid.substring(5).equals("1001248")) { //상무님
		sql1 = "SELECT CHAR(INT((X.CNT - 1)/20) + 1) TPAGE FROM (SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y'" + t_str2 + " AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ) X ";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, A.CS180_NAM, TRIM(A.CS180_NAM)||' - '||B.CS181_DAT VLIST, B.CS181_FL3 FLG, " + 
				"C.USERNAME MUR, ROW_NUMBER() OVER (" + t_str + ", B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B, SAPHEE.ZUSERF C " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y'" + t_str2 + " AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"AND B.MANDT = C.MANDT AND B.CS181_MUR = C.USERNUMB " + t_str + ") X WHERE X.NUM BETWEEN " + pnum + " AND " + nnum;
	} else { //그외
		sql1 = "SELECT CHAR(INT((X.CNT - 1)/20) + 1) TPAGE FROM (SELECT COUNT(*) CNT FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y'" + t_str2 + " AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ) X ";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, A.CS180_NAM, TRIM(A.CS180_NAM)||' - '||B.CS181_DAT VLIST, B.CS181_FL3 FLG, " + 
				"C.USERNAME MUR, ROW_NUMBER() OVER (" + t_str + ", B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B, SAPHEE.ZUSERF C " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y'" + t_str2 + " AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"AND B.MANDT = C.MANDT AND B.CS181_MUR = C.USERNUMB " + t_str + ") X WHERE X.NUM BETWEEN " + pnum + " AND " + nnum;
	}

	pstmt1 = conn.prepareStatement(sql1);
	rs1 = pstmt1.executeQuery();

	pstmt2 = conn.prepareStatement(sql2);
	rs2 = pstmt2.executeQuery();

	if(rs1.next()) {
		tpage = rs1.getInt("TPAGE");
	}
%>

 <body class="mbody">
	<table border="0" cellpadding="0" cellspacing="0" width=100%> 
		<tr> 
			<td class="mtitle"><img src="./images/title.gif" height="20" align="absmiddle"> 중점현장 일일업무 보고서 </td>
			<!-- <td align="right" valign=bottom> -->
			<td align="right">
				<table cellpadding="0" cellspacing="0" border="0" style="margin-top:10">
					<tr>
						<td width="8" class="pg" align="right" id="td_Previous" style="CURSOR:hand" color="#242424" onClick="prevPage_onclick('<%=tpage%>','<%=cpage%>')"><img src="./images/page_previous.gif" width="15" height="15" align="absmiddle" hspace="2"></td>
						<td width="70" class="pg" align="center">페이지 :</td>
						<td width="15" class="pg" align="right" id="td_pTotalCount">&nbsp;<%=tpage%>&nbsp;</td>
						<td width="15" class="pg">의</td>
						<td width="12" class="pg" align="right"><input id="txt_PageInputNum" class="pg" onkeydown="moveToPage('<%=tpage%>')" type="text" size="4" style="WIDTH:30px" value="<%=cpage%>" name="text2" onselectstart="event.cancelBubble=true;event.returnValue=true"></td>
						<td width="13" class="pg" align="left" id="td_Next" style="CURSOR:hand" color="#242424" onClick="nextPage_onclick('<%=tpage%>','<%=cpage%>')"><img src="./images/page_next.gif" width="15" height="15" align="absmiddle" hspace="2"></td>
					</tr>
						
				</table>
			</td>
		</tr>     
	</table>
	
	<table border=0 cellpadding=0 cellspacing=0 class="micon">
		<tr>
			<td width="780" align="right">
			<% //if(t_userid.substring(5).equals("1001094")) { %>
				<input type="checkbox" id="listall" name="listall" onClick="js_listAll();" <% if(t_listall.equals("Y")) { %>checked<% } %>>&nbsp;전체 리스트 보기
			<% //} %>
			</td>
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" class="mwidth">
		<tr> 
			<td class="line"></td>
		</tr>
		<tr> 
			<td height="1" bgcolor="#FFFFFF"></td>
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" class="ltitle">
		<tr align="center"> 
			<td width="60%" align="left" style="cursor:hand;padding-top:2px;height:23px;" align="left" onclick="SortPage('NAM','<%=t_chkflg%>')">일일보고서 제목&nbsp;<% if(chkimg.equals("1")) { %><img src="./images/arrowup.gif"><% } else if(chkimg.equals("2")) { %><img src="./images/arrowdown.gif"><% } %></td>
			<td width="20%" style="cursor:hand;padding-top:2px;height:23px;" onclick="SortPage('MUR','<%=t_chkflg%>')">작성자&nbsp;<% if(chkimg.equals("3")) { %><img src="./images/arrowup.gif"><% } else if(chkimg.equals("4")) { %><img src="./images/arrowdown.gif"><% } %></td>
			<td width="20%" style="cursor:hand;padding-top:2px;height:23px;" onclick="SortPage('MDT','<%=t_chkflg%>')">작성일자&nbsp;<% if(chkimg.equals("5")) { %><img src="./images/arrowup.gif"><% } else if(chkimg.equals("6")) { %><img src="./images/arrowdown.gif"><% } %></td>
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" class="mwidth">
		<tr>
			<td height="1" bgcolor="#FFFFFF"></td>
		</tr>
		<tr>
			<td class="line"></td>
		</tr>
	</table>
	<table style="TABLE-LAYOUT: fixed" border="0" cellspacing="0" cellpadding="0" class="list">

<%
	while(rs2.next()) {
		String t_mdt = rs2.getString("MANDT");
		String t_upn = rs2.getString("CS181_UPN");
		String t_seq = rs2.getString("CS181_SEQ");
		String t_dat = rs2.getString("CS181_DAT");
		String t_flg = rs2.getString("FLG");
		String t_mur = rs2.getString("MUR");
		String vlist = rs2.getString("VLIST");
		int t_num = rs2.getInt("NUM");
%>

		<tr align="center">
			<td title='<%=vlist%>' width="60%" align="left" height="22" style="cursor:hand;overflow:hidden;text-overflow:ellipsis" onclick="Read_onclick('<%=t_mdt%>','<%=t_upn%>','<%=t_seq%>','<%=t_dat%>','<%=t_num%>')">
			<nobr><span id="MyList__ctl0_Title">
				<% if(t_flg.equals("N")) { %><B><% } %><%=vlist%><% if(t_flg.equals("N")) { %></B><% } %>
			</span></nobr></td>
			<td width="20%"><%=t_mur%></td>
			<td width="20%"><%=t_dat.substring(0,4)%>-<%=t_dat.substring(4,6)%>-<%=t_dat.substring(6,8)%></td>
		</tr>
		<tr><td colspan=3 style="background:url('./images/bg_line.gif')"></td></tr>

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
	<iframe id="saveExcel" name="saveExcel" style="display:none;">
</iframe>
</body>

</html>
