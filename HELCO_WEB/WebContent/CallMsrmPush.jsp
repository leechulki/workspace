<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>before</title>
<% 
	String strUserId = request.getParameter("USER_ID");
	String strSapId = request.getParameter("SAP_ID");
	String strSeq = request.getParameter("SEQ");
	String strRecvId = request.getParameter("RECV");
%>
</head>
<script language="javascript">
function init(){
	//##운영서버 반영 시 action url 확인할 것
	//document.form.action = "http://10.51.5.88:8081/MobileServer/Order.msrm";//mSRM 개발자(이기복S) PC 로컬서버
	//document.form.action = "http://203.242.40.209:8090/MobileServer/Order.msrm";//mSRM 개발서버
	document.form.action = "http://203.242.40.61:55002/MobileServer/Order.msrm";//mSRM 운영서버
	document.form.USER_ID.value = "<%=strUserId%>";
	document.form.SAP_ID.value = "<%=strSapId%>";
	document.form.SEQ.value = "<%=strSeq%>";
	document.form.RECV.value = "<%=strRecvId%>";
	document.form.submit();
}
</script>
<body onload="init();">
	<form id="form" name="form" method="post">
		<input type="hidden" id="serviceFlag" name="serviceFlag" value="PUSH_INTERFACE"/>
		<input type="hidden" id="USER_ID" name="USER_ID" value=""/>
		<input type="hidden" id="SAP_ID" name="SAP_ID" value=""/>
		<input type="hidden" id="SEQ" name="SEQ" value=""/>
		<input type="hidden" id="RECV" name="RECV" value=""/>
	</form>
</body>
</html>