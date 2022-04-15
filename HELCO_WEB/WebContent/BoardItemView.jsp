<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="java.sql.*, java.util.* " %>
<%@ page import="com.helco.xf.cs12.evladm.dbutil.*" %>

<%
String t_userid = request.getParameter("userid");
%>

<HTML><HEAD><TITLE>중점현장 일일 보고서</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8"><LINK 
href="./images/style.css" type=text/css rel=stylesheet>
<STYLE title=ezform_style_1>P {
	MARGIN-TOP: 0mm; MARGIN-BOTTOM: 0mm
}
</STYLE>
<SCRIPT language=javascript>
<!--
window.offscreenBuffering = true;
var curFontSize = 1;
var page_chk = "Y"

function ExtractBetweenPattern( orgStr, firstPattern, lastPattern )
{
	var sIndex, eIndex;
	var copyStr = new String( orgStr );
	var retStr = "", subStr;
	
	var regFExp = new RegExp( firstPattern, "i" );
	var regEExp = new RegExp( lastPattern, "i" );
	
	var loop = 0;

	sIndex = copyStr.search( regFExp );
	if ( sIndex == -1 ) {
		return orgStr;
	}
	
	copyStr = copyStr.substr( sIndex + firstPattern.length );

	eIndex = copyStr.search( regEExp );
	if ( eIndex == -1 ) {
		return copyStr;
	}
	
	retStr = copyStr.substr( 0, eIndex );
	
	return retStr;
}

function MhtConvert()
{
	var fullPath = document.location.protocol + "//" + document.location.hostname + strContentLocation;
	objMHT.sync = true;

	var strMht = objMHT.DownloadURL(fullPath);
	
	objMHT.mhtData = strMht;
	objMHT.filterIn();

	var ret = objMHT.htmlData;	
	
	ret = ReplaceText(ret, "<a", "<a target='_blank'");
	return ret;
}

function CheckIfHasReplies()
{
	var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.open("POST", "interASP/CheckIfHasReply.aspx?ItemList=" + pItemID + ",;", false);
	xmlhttp.send();	
	if(xmlhttp.responseText == "FALSE") {
		xmlhttp = null;	
		return false;
	}
	xmlhttp = null;
	return true;
}

function btn_Delete_Onclick()
{
	if (CheckIfHasReplies())
	{
		alert("답변이 있는 게시물은 삭제할 수 없습니다.");
		return;
	}

	if(Delete_FG != "true") {
		alert("삭제 권한이 없습니다.");
		return;
	}

	if(BoardAdmin_FG != "true" && BoardGroupAdmin_FG != "OK" && strWriterID.toLowerCase() != SSUserID.toLowerCase()) 
	{
		if (gubun == "2")
		{
			var ret = window.showModalDialog("interASP/CheckPassWord.aspx?ItemID=" + pItemID, "", "status:no;dialogWidth:300px;dialogHeight:177px;help:no;scroll:no");
			if (typeof(ret) == "undefined") 		
			{	
				alert("삭제 권한이 없습니다.");
				return;
			}

			if (ret != "OK")
			{
				alert("삭제 권한이 없습니다.");
				return;
			}
		}
		else
		{
			alert("삭제 권한이 없습니다.");
		}
		return;
	}

	if(!confirm("삭제하시겠습니까?")) return;

	var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.open("POST", "interASP/DeleteItem.aspx?ItemList=" + pItemID + ";", false);
	xmlhttp.send();
	xmlhttp = null;
	try {
		window.opener.refresh_onclick();
	} catch(e) {
	}
	window.close();
}

function btnClose_onclick()
{
	var frm = document.frm;

	frm.chk.value = "N";
	frm.flg.value = "N";
	frm.action = "BoardItemUpdate.jsp";
	frm.submit();
}

function getSText(pFileName)
{
	for(i=0; i<pFileName.length; i++)
	{
		pFileName = pFileName.replace("'","")
		pFileName = pFileName.replace("+","-")
	}
	return pFileName;
}

function encodeBase64(p_str)
{
	var objEzUtil = new ActiveXObject("EzUtil.MiscFunc");
	ret = objEzUtil.EncodeBase64(p_str);
	objEzUtil = null;
	return ret;
}

function MemberInfo_onclick(pUserID)
{
	window.open("/myoffice/main/common/get_userinfo.aspx?id=" + pUserID, "", "height=290px,width=420px, status = no, toolbar=no, menubar=no,location=no, resizable=1");
}

function btn_SaveToPC_Onclick()
{
	var fPath;
	var objSave =  new ActiveXObject("EzUtil.MiscFunc");
	var strFilter;
	strFilter = objSave.OpenSaveDlg("MHT files (*.mht)\0*.mht\0HTM files (*.htm)\0*.htm");
	
	if ( strFilter != "")
	{
		var arryFileNM = strFilter.split(".");
	
		var cnt = arryFileNM.length;
		
		var FileExtensionNM = arryFileNM[cnt-1].toLowerCase( );

		if( FileExtensionNM == "mht")
		{
			objSave.SaveTextToFile(strFilter, objMHT.mhtData );
		}
		else
		{
			objSave.SaveTextToFile(strFilter, objMHT.htmlData );
		}
		alert("저장되었습니다.");
	}
	
}

function Bigger()
{
	if(curFontSize < 4) {
		curFontSize += 1;
	}
	txtContent.style.fontSize = fontSize[curFontSize];
}

function Smaller()
{
	if(curFontSize > 0) {
		curFontSize -= 1;
	}
	txtContent.style.fontSize = fontSize[curFontSize];
}

function document.onselectstart()
{
	//event.cancelBubble = true;
	//event.returnValue = false;
}

function mail_boarditem()
{
	var pEmail = getEmail();
	
	var MsgTo = "\"" + strWriterName + "\" <" + pEmail + ">";
	
	var szUrl = "/myoffice/ezEmail/mail_write.aspx?boardid=" + pBoardID + "&itemid=" + pItemID + "&cmd=board" + "&msgto=" + escape(MsgTo);

	window.open(szUrl, "", "height = 656px, width = 690px, status = no, toolbar=no, menubar=no,location=no,resizable=1");
	window.close();
}

function mail_boarditem_bigfile()
{
	var pEmail = getEmail();
	
	var MsgTo = "\"" + strWriterName + "\" <" + pEmail + ">";
	
	var szUrl = "/myoffice/ezEmail/mail_write_bigmail.aspx?boardid=" + pBoardID + "&itemid=" + pItemID + "&cmd=board" + "&msgto=" + escape(MsgTo);

	window.open(szUrl, "", "height = 656px, width = 737px, status = no, toolbar=no, menubar=no,location=no,resizable=1");
	window.close();
}		

function fax_boarditem()
{
	var szUrl = "/myoffice/ezEmail/mail_fax_write.aspx?boardid=" + pBoardID + "&itemid=" + pItemID + "&cmd=board";

	window.open(szUrl, "", "height = 656px, width = 690px, status = no, toolbar=no, menubar=no,location=no,resizable=1");
	window.close();
}

function ReaderList()
{
	var szHref = "Item_ReadList.aspx?BoardID=" + pBoardID + "&ItemID=" + pItemID;			
	var strFeature	= "status:no;dialogHeight: 400px;dialogWidth: 520px;help: no;resizable:yes"
	
	window.open(szHref,"", "width=520, height=400, resizable=yes, scrollbars=yes");
}

function btn_Print_Onclick()
{
	//window.print();
	
	var url = window.location.href;
	
	url = url.replace(".aspx", "_Print.aspx");
	
	if(url.indexOf("BoardID={") > -1)
	{
		window.open(url, "", "top=0, left=0, height=700px, width=765px, location=0, menubar=0, toolbar=1, resizable=1, scrollbars=1");
	}
	else	
	{
		var boardStrLength = url.indexOf("BoardID=");
		url = url.substring(0, boardStrLength);
		var rUrl = url +"BoardID="+ pBoardID
		window.open(rUrl, "", "top=0, left=0, height=700px, width=765px, location=0, menubar=0, toolbar=1, resizable=1, scrollbars=1");
	}	
	
}

function OneLineReply_onkeydown()
{
	if (event.keyCode == 13) Save_OneLineReply();
}

function Save_OneLineReply()
{
	// 주석문:답변권한이 없어도 한줄답변은 작성 가능토록 변경 : 현대
	//if (Reply_FG != "true") 
	//{
	//	alert("답변 권한이 없습니다.");
	//	return;
	//}
	
	event.returnValue = false;
	event.cancelBubble = true;

	if (document.all.onelinereply.value == "") 
	{
		alert("한줄 답변 내용을 입력해 주세요.");
		return;
	}

    var frm = document.frm;
	frm.chk.value = "Y";
	frm.action = "BoardItemUpdate.jsp";
	frm.submit();
/*
	var pReplyID = "";
	var ezUtil = new ActiveXObject("ezUtil.MiscFunc");
	pReplyID = ezUtil.GetGUID();
	ezUtil = null;
	
	var strXML = "";
	
	strXML += "<DATA>";
	strXML += "<BOARDID>" + pBoardID + "</BOARDID>";
	strXML += "<ITEMID>" + pItemID + "</ITEMID>";
	strXML += "<REPLYID>" + pReplyID + "</REPLYID>";
	strXML += "<CONTENT>" + MakeXMLString(document.all.onelinereply.value) + "</CONTENT>";
	strXML += "</DATA>";
	
	var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.open("POST", "interASP/SaveOneLineReply.aspx", false);
	xmlhttp.send(strXML);
	
	if (xmlhttp.status == 200) 
	{
		xmlhttp = null;
		alert("등록되었습니다.");
		document.all.onelinereply.value = "";
		getOneLineReply();
	}
	
	xmlhttp = null;
*/
}

function delete_onelinereply(pReplyID)
{
	var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.open("POST", "interASP/CheckOneLineOwner.aspx?ReplyID=" + pReplyID, false);
	xmlhttp.send();
	
	if (xmlhttp.responseText.substr(0,2) != "OK")
	{
		alert("본인이 등록한 한줄 답변만 삭제할 수 있습니다.");
		return;
	}
	
	if (!confirm("선택한 한줄 답변을 삭제하시겠습니까?")) return;
	
	xmlhttp.open("POST", "interASP/DeleteOneLineReply.aspx?ReplyID=" + pReplyID, false);
	xmlhttp.send();
	getOneLineReply();			
	xmlhttp = null;
}

function getOneLineReply()
{
	var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.open("POST", "interASP/ReadOneLineReply.aspx?BoardID=" + pBoardID + "&ItemID=" + pItemID, false);
	xmlhttp.send();
	
	var xmldom = new ActiveXObject("Microsoft.XMLDOM");
	xmldom.loadXML(xmlhttp.responseText);
	xmlhttp = null;
	
	strHTML = "";
	var DbTime = "";
	var temp = xmldom.getElementsByTagName("REPLYID").length + 1;
	for (var i=0; i<xmldom.getElementsByTagName("REPLYID").length; i++)
	{
		temp = temp - 1;	
		if (gubun != "2")
		{
			DbTime = GetLocalTime(xmldom.getElementsByTagName("WRITEDATE").item(i).text);
			strHTML += "<font color=blue>" + temp.toString() + ". " + "<span style='cursor:hand' onclick='OpenUserInfo(\"" + xmldom.getElementsByTagName("USERID").item(i).text + "\")'>" + xmldom.getElementsByTagName("USERNAME").item(i).text + "</span>(" + DbTime + ")" + " : </font>" + xmldom.getElementsByTagName("CONTENT").item(i).text + " <img src='/images/oneline_delete.gif' alt = '메모삭제' style='cursor:hand' onclick='delete_onelinereply(\"" + xmldom.getElementsByTagName("REPLYID").item(i).text + "\")'><p>";
		}else{
			DbTime = xmldom.getElementsByTagName("WRITEDATE").item(i).text;
			strHTML += "<font color=blue>" + temp.toString() + ". " + "<span style='cursor:hand' onclick=''>" + xmldom.getElementsByTagName("USERNAME").item(i).text + "</span>(" + DbTime + ")" + " : </font>" + xmldom.getElementsByTagName("CONTENT").item(i).text + " <img src='/images/oneline_delete.gif' alt = '메모삭제' style='cursor:hand' onclick='delete_onelinereply(\"" + xmldom.getElementsByTagName("REPLYID").item(i).text + "\")'><p>";
		}
	}

	if (i==0)
		strHTML = "한줄 답변 목록이 없습니다.";
	document.all.onelinereplylist.innerHTML = strHTML;
}

function addzero(arg) {
	if(arg < 10) {
		arg = "0" + arg;
	}
	return arg;
}
		
function ReplaceText( orgStr, findStr, replaceStr )
{
	var re = new RegExp( findStr, "gi" );
	return ( orgStr.replace( re, replaceStr ) );
}

function MakeXMLString(p_str)
{
	p_str = ReplaceText(p_str, "&", "&amp;");
	p_str = ReplaceText(p_str, "<", "&lt;");
	p_str = ReplaceText(p_str, ">", "&gt;");
	
	return p_str;
}

function OpenItem(str1, str2)
{
	var frm = document.frm;

	page_chk = "N";
	frm.chk.value = "N";
	frm.flg.value = "Y";
	frm.str1.value = str1;
	frm.str2.value = str2;
	frm.action = "BoardItemUpdate.jsp";
	frm.submit();
//	if (strItemID != "") window.location.href = window.location.href.replace(pItemID, strItemID);
}

// 주석문:게시 보기(새거)
function Item_View_New(pBoardID, pItemID)
{
	var pheigth = window.screen.availHeight;
	var pwidth = window.screen.availWidth;
	pheigth = parseInt(pheigth) / 2;
	pwidth = parseInt(pwidth) / 2;
	pheigth = pheigth - 284;
	pwidth = pwidth - 359;
			
	window.open("/myoffice/ezBoardSTD/BoardItemView.aspx?ItemID=" + pItemID + "&BoardID=" + pBoardID, "", "height=700,width=1000, status = no, toolbar=no, menubar=no, location=no, resizable=1, top=0, left=0", "");	
}


function window_onbeforeunload()
{
	if(page_chk == "Y") {
		var frm = document.frm;

		frm.chk.value = "N";
		frm.flg.value = "N";
		frm.action = "BoardItemUpdate.jsp";
		frm.submit();
	}

	try{
		window.opener.refresh_onclick();
	}catch(e){}		
}


var g_ValuePoint = "";
function fnClick(param)
{
	g_ValuePoint = param;
}
function goValue()
{
	if(strWriterID == SSUserID)
	{
		alert("본인평가는 할 수 없습니다.");
		return;
	}
	if(g_ValuePoint == "")
	{
		alert("평가점수를 선택하세요.");
		return;
	}
	var xmlpara = new ActiveXObject("Microsoft.XMLDOM");
	var objRoot = xmlpara.createNode(1,"DATA","");
	xmlpara.appendChild(objRoot);

	var objNode = xmlpara.createNode(1, "WRITERID", "");
	objNode.text = strWriterID;
	xmlpara.documentElement.appendChild(objNode);
	
	var objNode = xmlpara.createNode(1, "WRITERNAME", "");
	objNode.text = strWriterName;
	xmlpara.documentElement.appendChild(objNode);
	
	var objNode = xmlpara.createNode(1, "WRITERDEPTNAME", "");
	objNode.text = strWriterDeptName;
	xmlpara.documentElement.appendChild(objNode);
	
	var objNode = xmlpara.createNode(1, "WRITERTITLENAME", "");
	objNode.text = "４급사원";
	xmlpara.documentElement.appendChild(objNode);
		
	var objNode = xmlpara.createNode(1, "ITEMID", "");
	objNode.text = pItemID;
	xmlpara.documentElement.appendChild(objNode);
	
	var objNode = xmlpara.createNode(1, "POINT", "");
	objNode.text = g_ValuePoint;
	xmlpara.documentElement.appendChild(objNode);
	
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	xmlhttp.open("POST","interASP/ValueItem.aspx", false);
	xmlhttp.send(xmlpara);

	var rtnValue = xmlhttp.responseText;
	
	xmlhttp = null;
	xmlpara = null;
	if (rtnValue == "2") 
	{
		//finished
		alert("이미 평가하였습니다.");
	}
	else if(rtnValue == "1")
	{
		//OK
		alert("평가완료");
		document.location.reload();
	}
	else
	{
		//err
		alert(rtnValue);
	}
}
//-->
</SCRIPT>

<%
String t_pag = request.getParameter("pag");
String t_mdt = request.getParameter("mdt");
String t_upn = request.getParameter("upn");
String t_seq = request.getParameter("seq");
String t_dat = request.getParameter("dat");
int t_num = Integer.parseInt(request.getParameter("num"));
int p_num = 0;
int n_num = 0;

if(t_num > 1) {
	p_num = t_num - 1;
}

n_num = t_num + 1;

Connection conn = null;
PreparedStatement pstmt1 = null;
ResultSet rs1 = null;
PreparedStatement pstmt2 = null;
ResultSet rs2 = null;
String sql1 = "";
String sql2 = "";

try {
	conn = DBUtil.getConnection("jdbc/hep");

	if(t_userid.substring(5).equals("1001094")) { //사장님
		sql1 = "SELECT A.CS180_UPN, A.CS180_SEQ, A.CS180_CEL, A.CS180_CES, A.CS180_NAM, A.CS180_RMK, B.CS181_DAT, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM1) PM1, " + 
				"SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM2) PM2, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ1) SJ1, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ2) SJ2, " + 
				"B.CS181_TXA, B.CS181_TXB, B.CS181_TXC, B.CS181_TXD, B.CS181_TXE, B.CS181_TXF, B.CS181_RMK FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '" + t_mdt + "' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"AND B.CS181_UPN = '" + t_upn + "' AND B.CS181_SEQ = '" + t_seq + "' AND B.CS181_DAT = '" + t_dat + "'";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||'-'||B.CS181_DAT VLIST, " + 
				"ROW_NUMBER() OVER (PARTITION BY A.MANDT ORDER BY B.CS181_DAT DESC, B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"ORDER BY B.CS181_DAT DESC, B.CS181_UPN) X WHERE NUM = " + p_num + " OR NUM = " + n_num + " ORDER BY NUM";
	} else if(t_userid.substring(5).equals("1001086")) { //부사장님
		sql1 = "SELECT A.CS180_UPN, A.CS180_SEQ, A.CS180_CEL, A.CS180_CES, A.CS180_NAM, A.CS180_RMK, B.CS181_DAT, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM1) PM1, " + 
				"SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM2) PM2, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ1) SJ1, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ2) SJ2, " + 
				"B.CS181_TXA, B.CS181_TXB, B.CS181_TXC, B.CS181_TXD, B.CS181_TXE, B.CS181_TXF, B.CS181_RMK FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '" + t_mdt + "' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"AND B.CS181_UPN = '" + t_upn + "' AND B.CS181_SEQ = '" + t_seq + "' AND B.CS181_DAT = '" + t_dat + "'";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||'-'||B.CS181_DAT VLIST, " + 
				"ROW_NUMBER() OVER (PARTITION BY A.MANDT ORDER BY B.CS181_DAT DESC, B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"ORDER BY B.CS181_DAT DESC, B.CS181_UPN) X WHERE NUM = " + p_num + " OR NUM = " + n_num + " ORDER BY NUM";
	} else if(t_userid.substring(5).equals("1001248")) { //상무님
		sql1 = "SELECT A.CS180_UPN, A.CS180_SEQ, A.CS180_CEL, A.CS180_CES, A.CS180_NAM, A.CS180_RMK, B.CS181_DAT, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM1) PM1, " + 
				"SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM2) PM2, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ1) SJ1, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ2) SJ2, " + 
				"B.CS181_TXA, B.CS181_TXB, B.CS181_TXC, B.CS181_TXD, B.CS181_TXE, B.CS181_TXF, B.CS181_RMK FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '" + t_mdt + "' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"AND B.CS181_UPN = '" + t_upn + "' AND B.CS181_SEQ = '" + t_seq + "' AND B.CS181_DAT = '" + t_dat + "'";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||'-'||B.CS181_DAT VLIST, " + 
				"ROW_NUMBER() OVER (PARTITION BY A.MANDT ORDER BY B.CS181_DAT DESC, B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"ORDER BY B.CS181_DAT DESC, B.CS181_UPN) X WHERE NUM = " + p_num + " OR NUM = " + n_num + " ORDER BY NUM";
	} else { //그외
		sql1 = "SELECT A.CS180_UPN, A.CS180_SEQ, A.CS180_CEL, A.CS180_CES, A.CS180_NAM, A.CS180_RMK, B.CS181_DAT, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM1) PM1, " + 
				"SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_PM2) PM2, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ1) SJ1, SAPHEE.GET_BOSU_INWON(A.MANDT,A.CS180_SJ2) SJ2, " + 
				"B.CS181_TXA, B.CS181_TXB, B.CS181_TXC, B.CS181_TXD, B.CS181_TXE, B.CS181_TXF, B.CS181_RMK FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '" + t_mdt + "' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"AND B.CS181_UPN = '" + t_upn + "' AND B.CS181_SEQ = '" + t_seq + "' AND B.CS181_DAT = '" + t_dat + "'";

		sql2 = "SELECT * FROM (SELECT A.MANDT, B.CS181_UPN, B.CS181_SEQ, B.CS181_DAT, TRIM(A.CS180_NAM)||'-'||B.CS181_DAT VLIST, " + 
				"ROW_NUMBER() OVER (PARTITION BY A.MANDT ORDER BY B.CS181_DAT DESC, B.CS181_UPN) NUM FROM SAPHEE.ZCST180 A, SAPHEE.ZCST181 B " + 
				"WHERE A.MANDT = '183' AND A.CS180_USE = 'Y' AND A.MANDT = B.MANDT AND A.CS180_UPN = B.CS181_UPN AND A.CS180_SEQ = B.CS181_SEQ " + 
				"ORDER BY B.CS181_DAT DESC, B.CS181_UPN) X WHERE NUM = " + p_num + " OR NUM = " + n_num + " ORDER BY NUM";
	}

	pstmt1 = conn.prepareStatement(sql1);
	rs1 = pstmt1.executeQuery();

	pstmt2 = conn.prepareStatement(sql2, rs2.TYPE_SCROLL_INSENSITIVE, rs2.CONCUR_READ_ONLY);
	rs2 = pstmt2.executeQuery();
%>

<body class="pbody" onbeforeunload="return window_onbeforeunload()">
<form name="frm" method="post" target="MsgFrm" action="BoardItemUpdate.jsp">

<%
	while(rs1.next()) {
		String upn = rs1.getString("CS180_UPN");
		String seq = rs1.getString("CS180_SEQ");
		String nam = rs1.getString("CS180_NAM");
		int cel = rs1.getInt("CS180_CEL");
		int ces = rs1.getInt("CS180_CES");
		int tot = cel + ces;
		String txt = rs1.getString("CS180_RMK");
		String dat = rs1.getString("CS181_DAT");
		String pm1 = rs1.getString("PM1");
		String pm2 = rs1.getString("PM2");
		String sj1 = rs1.getString("SJ1");
		String sj2 = rs1.getString("SJ2");
		String txa = rs1.getString("CS181_TXA");
		String txb = rs1.getString("CS181_TXB");
		String txc = rs1.getString("CS181_TXC");
		String txd = rs1.getString("CS181_TXD");
		String txe = rs1.getString("CS181_TXE");
		String txf = rs1.getString("CS181_TXF");
		String rmk = rs1.getString("CS181_RMK");

		String mdt1 = "";
		String mdt2 = "";
		String upn1 = "";
		String upn2 = "";
		String seq1 = "";
		String seq2 = "";
		String dat1 = "";
		String dat2 = "";
		String str1 = "";
		String str2 = "";
		String vlist1 = "";
		String vlist2 = "";
		int num1 = 0;
		int num2 = 0;
		int cnt = 0;
		rs2.last();
		cnt = rs2.getRow();
		rs2.first();

		for(int i=0; i<cnt; i++) {
			if(cnt == 1) {
				if(p_num > 0) {
					mdt1 = rs2.getString("MANDT");
					upn1 = rs2.getString("CS181_UPN");
					seq1 = rs2.getString("CS181_SEQ");
					dat1 = rs2.getString("CS181_DAT");
					vlist1 = rs2.getString("VLIST");
					num1 = rs2.getInt("NUM");
				} else {
					mdt2 = rs2.getString("MANDT");
					upn2 = rs2.getString("CS181_UPN");
					seq2 = rs2.getString("CS181_SEQ");
					dat2 = rs2.getString("CS181_DAT");
					vlist2 = rs2.getString("VLIST");
					num2 = rs2.getInt("NUM");
				}
			}

			if(cnt == 2) {
				if(i == 0) {
					mdt1 = rs2.getString("MANDT");
					upn1 = rs2.getString("CS181_UPN");
					seq1 = rs2.getString("CS181_SEQ");
					dat1 = rs2.getString("CS181_DAT");
					vlist1 = rs2.getString("VLIST");
					num1 = rs2.getInt("NUM");
					rs2.next();
				} else {
					mdt2 = rs2.getString("MANDT");
					upn2 = rs2.getString("CS181_UPN");
					seq2 = rs2.getString("CS181_SEQ");
					dat2 = rs2.getString("CS181_DAT");
					vlist2 = rs2.getString("VLIST");
					num2 = rs2.getInt("NUM");
				}
			}
		}

		str1 = mdt1 + upn1 + seq1 + dat1 + String.valueOf(num1);
		str2 = mdt2 + upn2 + seq2 + dat2 + String.valueOf(num2);
%>

<input type="hidden" name="pag" value=<%= t_pag%>>
<input type="hidden" name="chk" value="N">
<input type="hidden" name="flg" value="N">
<input type="hidden" name="mdt" value="<%= t_mdt%>">
<input type="hidden" name="upn" value="<%= t_upn%>">
<input type="hidden" name="seq" value="<%= t_seq%>">
<input type="hidden" name="dat" value="<%= t_dat%>">
<input type="hidden" name="num" value="<%= t_num%>">
<input type="hidden" name="userid" value="<%= t_userid%>">
<input type="hidden" name="str1">
<input type="hidden" name="str2">

<table width="100%" cellspacing="0" cellpadding="0" class="iconbg" height="29" border="0">
	<tr> 
		<td width="26" align="center"><img src="./images/p_board.jpg"></td>
		<td width="490"> 
		</td>
		<td>&nbsp;</td>
		<td align=right  onclick="btnClose_onclick()" style="CURSOR: hand;PADDING-RIGHT:20px" onmouseover="this.style.color='#D58713'" onmouseout="this.style.color='#393939'">닫기</td>
	</tr>
</table>
<table height=10px><tr><td></td></tr></table>


<table border=0 width=100%>
	<tr height=80px>
		<td valign=top style="padding-left:5px;padding-right:5px">

<table height=10px><tr><td>1. 관리현황</td></tr></table>

<TABLE border="1"  style="TABLE-LAYOUT: fixed;BORDER-BOTTOM: medium none;Design_Time_Lock: true " cellSpacing=0 width="100%"  borderColorDark="white" borderColorLight="B6B6B6">
<TBODY>
<TR>
<TD width=75 height=23>
<P align=center>고객명</P></TD>
<TD class="white" width=359 height=23>&nbsp;<%= upn%> <%= nam%></TD>
<TD width=65 height=59 rowSpan=3>
<P align=center>대수</P></TD>
<TD width=66 height=23>
<P align=center>E/L</P></TD>
<TD class="white" width=108 height=23>&nbsp;<input type="text" name="cel" style="width:25%;height:100%;border:solid 0;text-align:right;overflow:hidden;font-size:9pt;color:#393939" readonly value="<%= cel%>"></TD></TR>
<TR>
<TD width=75 height=23>
<P align=center>책임관리자</P></TD>
<TD class="white" width=359 height=23>&nbsp;<%= pm1%> <%= pm2%></TD>
<TD width=66 height=23>
<P align=center>E/S</P></TD>
<TD class="white" width=108 height=23>&nbsp;<input type="text" name="ces" style="width:25%;height:100%;border:solid 0;text-align:right;overflow:hidden;font-size:9pt;color:#393939" readonly value="<%= ces%>"></TD></TR>
<TR>
<TD width=75 height=23>
<P align=center>상주자</P></TD>
<TD class="white" width=359 height=23>&nbsp;<%= sj1%> <%= sj2%></TD>
<TD width=66 height=23>
<P align=center>계</P></TD>
<TD class="white" width=108 height=23>&nbsp;<input type=text name="tot" style="width:25%;height:100%;border:solid 0;text-align:right;overflow:hidden;font-size:9pt;color:#393939" readonly value="<%= tot%>"></TD></TR>
<TR>
<TD width=75 height=69>
<P align=center>비고</P></TD>
<TD class="white" width=601 colSpan=4 height=69>&nbsp;<textarea name="txt" style="width:100%;height:100%;border:solid 0;overflow:hidden;font-size:9pt;color:#393939" readonly><%= txt%></textarea></TD></TR></TBODY></TABLE>

<table height=10px><tr><td></td></tr></table>

<table height=10px width="100%"><tr><td width="50%">2. 문제점 처리현황</td><td width="50%" align="right">작성일자 : <%= dat.substring(0,4)%>년 <%= dat.substring(4,6)%>월 <%= dat.substring(6,8)%>일</td></tr></table>
<TABLE border="1" style="TABLE-LAYOUT: fixed;BORDER-BOTTOM: medium none;Design_Time_Lock: true " cellSpacing=0 width="100%"  borderColorDark="white" borderColorLight="B6B6B6">
<TBODY>
<TR>
<TD width=75 height=23>
<P align=center>구분</P></TD>
<TD width=240 height=23>
<P align=center>고장/점검사항</P></TD>
<TD width=240 height=23>
<P align=center>조치/쟁점사항</P></TD>
<TD width=121 height=23>
<P align=center>비고</P></TD></TR>
<TR>
<TD height=140>
<P align=center>&nbsp;</P>
<P align=center>&nbsp;</P>
<P align=center>&nbsp;</P>
<P align=center>문제점</P>
<P align=center>처리현황</P>
<P align=center>&nbsp;</P>
<P align=center>&nbsp;</P>
<P align=center>&nbsp;</P>
</TD>
<TD height=140 bgcolor="FFFFFF"><textarea name="txa" style="width:100%;height:100%;border:solid 0;overflow:auto;font-size:9pt;line-height:130%;vertical-align:middle;font-family:돋움;color:#393939" readonly><%=txa%></textarea></TD>
<TD height=140 bgcolor="FFFFFF"><textarea name="txb" style="width:100%;height:100%;border:solid 0;overflow:auto;font-size:9pt;line-height:130%;vertical-align:middle;font-family:돋움;color:#393939" readonly><%=txb%></textarea></TD>
<TD height=140 bgcolor="FFFFFF"><textarea name="txc" style="width:100%;height:100%;border:solid 0;overflow:auto;font-size:9pt;line-height:130%;vertical-align:middle;font-family:돋움;color:#393939" readonly><%=txc%></textarea></TD></TR>
<TR>
<TD height=140>
<P align=center>&nbsp;</P>
<P align=center>&nbsp;</P>
<P align=center>&nbsp;</P>
<P align=center>고객</P>
<P align=center>요구사항</P>
<P align=center>&nbsp;</P>
<P align=center>&nbsp;</P>
<P align=center>&nbsp;</P></TD>
<TD height=140 bgcolor="FFFFFF"><textarea name="txd" style="width:100%;height:100%;border:solid 0;overflow:auto;font-size:9pt;line-height:130%;vertical-align:middle;font-family:돋움;color:#393939" readonly><%=txd%></textarea></TD>
<TD height=140 bgcolor="FFFFFF"><textarea name="txe" style="width:100%;height:100%;border:solid 0;overflow:auto;font-size:9pt;line-height:130%;vertical-align:middle;font-family:돋움;color:#393939" readonly><%=txe%></textarea></TD>
<TD height=140 bgcolor="FFFFFF"><textarea name="txf" style="width:100%;height:100%;border:solid 0;overflow:auto;font-size:9pt;line-height:130%;vertical-align:middle;font-family:돋움;color:#393939" readonly><%=txf%></textarea></TD></TR>
</TBODY>
</TABLE>

</td>
</tr>
<tr height=20px>
	<td valign=top style="padding-left:5px;padding-right:5px">
		<table width=100% height=20px cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td height="20px" valign=top>
					<table border="0" cellspacing="1" cellpadding="1" width="100%" class="tbbg">
						<tr>
							<td style="PADDING-LEFT: 10px; WIDTH: 80px; HEIGHT: 23px; BACKGROUND-COLOR: #f5f5f5">이전글</td>
							<td class="white" width="258px" height="23px" style="cursor:hand">				
<% if(!vlist1.equals("")) { %>
								<div align="left" style="margin-top:0px;padding-top:0px;HEIGHT: 15px;WIDTH:270px; background-color:white" onclick="OpenItem('<%=str1%>')"><%= vlist1%></div>
<% } %>
							</td>
							<td style="PADDING-LEFT: 10px; WIDTH: 80px; HEIGHT: 23px; BACKGROUND-COLOR: #f5f5f5">다음글</td>
							<td class="white" width="258px" height="23px" style="cursor:hand">				
<% if(!vlist2.equals("")) { %>
								<div align="left" style="margin-top:0px;padding-top:0px;HEIGHT: 15px; background-color:white" onclick="OpenItem('<%=str2%>')"><%= vlist2%></div>
<% } %>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr height=25px>
	<td valign=top style="padding-left:5px;padding-right:5px">
		<table width=100% height=25px cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td height="23" valign=top>
					<table border="0" cellspacing="1" cellpadding="2" width="100%" class="tbbg">
						<tr>
							<td class="white" height="23">	
								<div align="left" id="onelinereplylist" style="margin-top:0px;padding-top:0px;OVERFLOW: auto; HEIGHT: 23px; background-color:white"></div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>	
<tr>
	<td valign=top style="padding-left:5px;padding-right:5px" height=85px>
		<table width=100% height=20px cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td height="20" valign=top>
					<table border="0" cellspacing="1" cellpadding="2" width="100%" class="tbbg">
						<tr>
							<td class="normaltd" style="BACKGROUND-COLOR: #f5f5f5" width="72px"><p align=center>지시사항</p></td>
							<td class="white"><input id="onelinereply" name="rmk" style="WIDTH: 100%" type="text" maxLength="100" onkeydown="OneLineReply_onkeydown()" value="<%=rmk%>" <% if(!t_userid.substring(5).equals("1001094")) { %>readonly<% } %>></td>
							<td class="white" width="70" align="center">
								<% if(t_userid.substring(5).equals("1001094")) { %>
								<img src="./images/btn_register.gif" style="cursor:hand" onclick="Save_OneLineReply()">
								<% } %>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>

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

</form>
<iframe name="MsgFrm" frameborder="no" scrolling="no" marginwidth="0" marginheight="0"></iframe>
</body>
</html>