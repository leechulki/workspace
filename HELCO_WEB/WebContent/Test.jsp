
<HTML><HEAD><TITLE>게시물 리스트</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="./images/style.css" type=text/css rel=stylesheet>
<SCRIPT language=JavaScript>

function ItemRead_onclick(mdt, upn, seq, dat, num)
{
	var pheigth = window.screen.availHeight;
	var pwidth = window.screen.availWidth;
	var rHeight = 657;
	var rWidth = 720;
		
	pheigth = parseInt(pheigth - rHeight) / 2;
	pwidth = parseInt(pwidth - rWidth) / 2;
	
	window.open("./BoardItemView.jsp?mdt="+mdt+"&upn="+upn+"&seq="+seq+"&dat="+dat+"&num="+num, "", "height=657,width=720px, status = no, toolbar=no, menubar=no, location=no, resizable=1, top=" + pheigth + ",left = " + pwidth,"");
}

function refresh_onclick()
{
	window.location.reload(false);
}

</SCRIPT>
</HEAD>

<body leftMargin="0" topMargin="0" marginheight="0" marginwidth="0">

<table  width="94%" border="0" cellpadding="0" cellspacing="0">	
<tr><td valign="top">

 
	<table id = table1 border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td id=pColor  height="1" colspan="2" bgcolor="#d5d5d5"></td>
	</tr>
	<tr>
	
	<td height="22" id=pColor1 style="CURSOR: hand;" bgcolor="#E9E9E9">
	<span id="BoardNm"  style="font-size:9pt;color:#11228C;font-weight:bold;padding-left:12px">&nbsp;중점현장 일일보수업무 보고서</span>
	
		<font color="A3A3A3">&nbsp;|</font> <font color="CD6600"></font> 건</td>
		
			<td id=pColor2 width="35">
			</td>
		
	</tr>
	</table>


			<table id=table2 border="0" cellpadding="0" cellspacing="0" style="TABLE-LAYOUT: fixed;margin-top:7px" width="100%">




		</table>
	
	</td>
	</tr>
</table>
</body>
</HTML>