<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="java.sql.*, java.util.* " %>
<%@ page import="com.helco.xf.cs12.evladm.dbutil.*" %>

<%
String t_pag = request.getParameter("pag");
String t_chk = request.getParameter("chk");
String t_flg = request.getParameter("flg");
String t_mdt = request.getParameter("mdt");
String t_upn = request.getParameter("upn");
String t_seq = request.getParameter("seq");
String t_dat = request.getParameter("dat");
String t_num = request.getParameter("num");
String t_userid = request.getParameter("userid");
String t_str1 = request.getParameter("str1");
String t_str2 = request.getParameter("str2");
//String t_rmk = new String(request.getParameter("rmk").getBytes("8859_1"),"utf-8");
String t_rmk = request.getParameter("rmk");
String sql = "";
int cnt = 0;
String url = "";
String t_id = "";
t_id = t_userid.substring(5);

Connection conn = null;
PreparedStatement pstmt = null;

try {
	conn = DBUtil.getConnection("jdbc/hep");

	if(t_chk.equals("Y")) { //지시사항 입력시
		sql = "UPDATE SAPHEE.ZCST181 SET CS181_FLG = 'Y', CS181_RMK = '" + t_rmk + "', " + 
				"CS181_RDT = HEX(CURRENT DATE), CS181_RTM = HEX(CURRENT TIME), CS181_RUR = '" + t_id + "' " + 
				"WHERE MANDT = '" + t_mdt + "' AND CS181_UPN = '" + t_upn + "' AND CS181_SEQ = '" + t_seq + "' AND CS181_DAT = '" + t_dat + "'";
	} else { //지시사항 미입력시
		if(t_userid.substring(5).equals("1001094")) { //사장님
			sql = "UPDATE SAPHEE.ZCST181 SET CS181_FLG = 'Y', " + 
					"CS181_RDT = HEX(CURRENT DATE), CS181_RTM = HEX(CURRENT TIME), CS181_RUR = '" + t_id + "' " + 
					"WHERE MANDT = '" + t_mdt + "' AND CS181_UPN = '" + t_upn + "' AND CS181_SEQ = '" + t_seq + "' AND CS181_DAT = '" + t_dat + "'";
		} else if(t_userid.substring(5).equals("1001086")) { //부사장님
			sql = "UPDATE SAPHEE.ZCST181 SET CS181_FL2 = 'Y', " + 
					"CS181_RD2 = HEX(CURRENT DATE), CS181_RT2 = HEX(CURRENT TIME), CS181_RU2 = '" + t_id + "' " + 
					"WHERE MANDT = '" + t_mdt + "' AND CS181_UPN = '" + t_upn + "' AND CS181_SEQ = '" + t_seq + "' AND CS181_DAT = '" + t_dat + "'";
		} else if(t_userid.substring(5).equals("1001248")) { //상무님
			sql = "UPDATE SAPHEE.ZCST181 SET CS181_FL3 = 'Y', " + 
					"CS181_RD3 = HEX(CURRENT DATE), CS181_RT3 = HEX(CURRENT TIME), CS181_RU3 = '" + t_id + "' " + 
					"WHERE MANDT = '" + t_mdt + "' AND CS181_UPN = '" + t_upn + "' AND CS181_SEQ = '" + t_seq + "' AND CS181_DAT = '" + t_dat + "'";
		} else { //그외
			sql = "UPDATE SAPHEE.ZCST181 SET CS181_FLG = CS181_FLG " + 
					"WHERE MANDT = '" + t_mdt + "' AND CS181_UPN = '" + t_upn + "' AND CS181_SEQ = '" + t_seq + "' AND CS181_DAT = '" + t_dat + "'";
		}
	}
//System.out.println("sql ==> [" + sql + "]");

	pstmt = conn.prepareStatement(sql);
	cnt = pstmt.executeUpdate();
//System.out.println("t_str1 ==> [" + t_str1 + "]");
//System.out.println("t_str2 ==> [" + t_str2 + "]");
	if(t_flg.equals("Y")) { //이전,다음 클릭시
		if(!t_str1.equals("undefined") && t_str1.length() > 0 && !t_str1.equals("")) {
			t_mdt = t_str1.substring(0,3);
			t_upn = t_str1.substring(3,9);
			t_seq = t_str1.substring(9,11);
			t_dat = t_str1.substring(11,19);
			t_num = t_str1.substring(19);
		}

		if(!t_str2.equals("undefined") && t_str2.length() > 0 && !t_str2.equals("")) {
			t_mdt = t_str2.substring(0,3);
			t_upn = t_str2.substring(3,9);
			t_seq = t_str2.substring(9,11);
			t_dat = t_str2.substring(11,19);
			t_num = t_str2.substring(19);
		}
	}

	url = "./BoardItemView.jsp?mdt="+t_mdt+"&upn="+t_upn+"&seq="+t_seq+"&dat="+t_dat+"&num="+t_num+"&userid="+t_userid;

	if(t_flg.equals("N")) {
		if(t_chk.equals("Y")) { //등록 버튼 클릭시
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
		} else { //상세창을 닫을때
%>
			<script>
				//opener.parent.window.refresh_onclick();
				parent.window.close();
			</script>
<%
		}
	} else { //이전,다음 클릭시
%>
		<script>
			parent.location.href = '<%= url%>';
		</script>
<%
	}

	if(t_pag.equals("Y")) {
%>
		<script>
			//parent.right.parent.location.reload();
		</script>
<%
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
