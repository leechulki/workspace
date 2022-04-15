<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<% 
	//request.setCharacterEncoding("UTF-8");  //한글깨지면 주석제거
	String inputYn = request.getParameter("inputYn"); 
	String roadFullAddr = request.getParameter("roadFullAddr"); 
	String roadAddrPart1 = request.getParameter("roadAddrPart1"); 
	String roadAddrPart2 = request.getParameter("roadAddrPart2"); 
	String engAddr = request.getParameter("engAddr"); 
	String jibunAddr = request.getParameter("jibunAddr"); 
	String zipNo = request.getParameter("zipNo"); 
	String addrDetail = request.getParameter("addrDetail"); 
	String admCd    = request.getParameter("admCd");
	String rnMgtSn = request.getParameter("rnMgtSn");
	String bdMgtSn  = request.getParameter("bdMgtSn");
	
	//if(roadFullAddr != null) roadFullAddr = new String(roadFullAddr.getBytes("8859_1"),"UTF-8"); 
	//if(roadAddrPart1 != null) roadAddrPart1 = new String(roadAddrPart1.getBytes("8859_1"),"UTF-8"); 
	//if(roadAddrPart2 != null) roadAddrPart2 = new String(roadAddrPart2.getBytes("8859_1"),"UTF-8"); 
	//if(jibunAddr != null) jibunAddr = new String(jibunAddr.getBytes("8859_1"),"UTF-8"); 
	//if(addrDetail != null) addrDetail = new String(addrDetail.getBytes("8859_1"),"UTF-8"); 
%>
</head>
<script language="javascript">
//opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. ("주소입력화면 소스"도 동일하게 적용시켜야 합니다.)
document.domain = "srm.hdel.co.kr";

function init(){
	var url = location.href;
	var confmKey = "bnVsbDIwMTQxMjE4MTY1NDQ2"; //운영 bnVsbDIwMTQxMjE4MTY1NDQ2	//개발 bnVsbDIwMTQxMjE2MTEwMjEy 신청후 1개월 만료
	var inputYn= "<%=inputYn%>";
	if(inputYn != "Y"){
		document.form.confmKey.value = confmKey;
		document.form.returnUrl.value = url;
		document.form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do"; //인터넷망
		//document.form.action="http://10.182.60.22/addrlink/addrLinkUrl.do"; //내부행망
		document.form.submit();
	}else{
		opener.jusoCallBack("<%=roadFullAddr%>","<%=roadAddrPart1%>","<%=addrDetail%>","<%=roadAddrPart2%>","<%=engAddr%>","<%=jibunAddr%>","<%=zipNo%>", "<%=admCd%>", "<%=rnMgtSn%>", "<%=bdMgtSn%>");
		window.close();
	}
}
</script>
<body onload="init();">
	<form id="form" name="form" method="post">
		<input type="hidden" id="confmKey" name="confmKey" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value=""/>
	</form>
</body>
</html>