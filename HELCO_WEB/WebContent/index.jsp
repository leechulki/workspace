<%@ page contentType="text/html; charset=euc-kr" session="true" %>
<%
	String SvrUrl      = request.getRequestURL().toString();
    String keyValue    = "SRM";   // "HELCO_WEB"; 2015.10.05 ���ÿ���� ��û�� ���� 	// ������ HED_WEB  
    
    //SvrUrl      = SvrUrl.substring(0, SvrUrl.lastIndexOf("/"));
   // SvrUrl      = SvrUrl.substring(0, SvrUrl.indexOf("/HELCO_WEB/") + 10);
   SvrUrl      = SvrUrl.substring(0, SvrUrl.length()-10);
    String webAppUrl   = SvrUrl + "/ui/HELCO_WEB/HELCO_WEB_ci_main_Win32.xml"; 
%>
<html>
<head>
<meta http-equiv=Content-type content="text/html; charset=euc-kr">
<meta http-equiv="X-UA -Compatible" content="requiresActiveX=true">
<SCRIPT LANGUAGE="JavaScript" src="./ui/MiInstaller320.js"></SCRIPT>
<style>
	.f2 { font-size:12 }
	.hide { display:none }
	.show { display:block }	
</style>


<script language="JavaScript">
// vbscript�� �������� �ʾ��ڹٽ�ũ��Ʈ�� check 2014.10.06 hss
function Check_Module()
{
	try
	{ 
		if(MiInstaller.Version == undefined) return false;
		
		return true;
	}
	catch(ex)
	{
		return false;
	}
}

</script>

<script language="javascript">    
	var progressColor = 'blue';	// set to progress bar color
	var pg_cell_At = 0,pg1_cell_At = 0; 
	var IsError = false;
	var proMsg, procMsg;;
	
	function page_link() {
		// snisweb
		// ���� ������ �����
		// UBKImage : 250 * 300
		
		//BYTE�� 256�� ������ �ȵ�..
		// 256�� ���� ��� MiInstaller�� property�� �̿��Ұ�

		/*var strCommand  = '-V 3.2 -D Win32U -R FALSE -K <%=keyValue%> -L TRUE -LE TRUE -X ' + MiInstaller.StartXML + ' -U ' + MiInstaller.UpdateURL;
		//update image �ٲٱ� 
		//strCommand = strCommand + ' -G "%Component%BK1.jpg" -BI "%Component%BK2.jpg"'; */
		
	    var strCommand  = "";
	    
	    if(IsWin8())
	    {
	     // strCommand = '-V 3.2 -D Win32U -R FALSE -K <%=keyValue%> -L TRUE -LE TRUE -X ' + MiInstaller.StartXML + ' -U ' + MiInstaller.UpdateURL;
	    	strCommand = '-V 3.2 -D Win32U -R FALSE -K <%=keyValue%> -L TRUE -LE TRUE';
	    	//strCommand = strCommand + ' -G "%Component%BK1.jpg" -BI "%component%BK2.jpg"';
	    	strCommand = strCommand + ' -BI "%Component%BK2.jpg"';
			MiInstaller.MakeShortCut("%system%MiUpdater321.exe",strCommand,"<%=keyValue%>","%APPDATA%icon_tsm.ico","");  // win8 ���� ����
		//	MiInstaller.MakeShortCut("%system%MiUpdater321.exe",strCommand,"<%=keyValue%>","%MIPLATFORM%icon_tsm.ico","");  // win8 ���� ����  // after version 2013.1.28
		 } else {
	    	strCommand = '-V 3.2 -D Win32U -R FALSE -K <%=keyValue%> -L TRUE -LE TRUE';
	    	strCommand = strCommand + ' -BI "%Component%BK2.jpg"';
			MiInstaller.MakeShortCut("%system%MiUpdater321.exe",strCommand,"<%=keyValue%>","%Component%icon_tsm.ico","");  // xp�� ���
		}
		
		//	MiInstaller.MakeShortCut() ���� ����------------------------
		// MakeShortCut �ٷΰ��⸦ ����� �Լ�
		// strExeName: �ٷΰ��⸦ ���� ���� ���� �̸�
		// strCommand: �ٷΰ��⸦ ���� �� �ʿ��� Command ����
		// strShortcutName: �ٷΰ��� ���� �̸�
		// strIConPath: �����Ϸ��� Icon ��θ� %alias%���·� �Է��� �� �ֽ��ϴ�.
		// strPos: Startmenu-���� / Desktop-����ȭ��(Default)
	
		// Alias ���� ------------------------
		// %MiPlatform%
		// %Component%
		// %system%
		// %Window%
		// %ProgramFiles%
	
	} 
	
	function fn_run() {
		page_link();

		// ���� ������ ����
		//MiInstaller.Run();
		
		//self.opener=self;
		//window.close();
		
		if(navigator.appVersion.indexOf("MSIE 7.") >= 0 ||
			navigator.appVersion.indexOf("MSIE 8.") >= 0 ||
			navigator.appVersion.indexOf("MSIE 9.") >= 0 ||
			navigator.appVersion.indexOf("MSIE 10.") >= 0 ||
			navigator.appVersion.indexOf("Trident") >= 0)
		{
			//window.open('about:blank','_self').close();
			window.opener = self;
			self.close();
		}
		else
		{
			window.opener = self;
			self.close();
		}
		
		// ���� ������ ����
		MiInstaller.Run();
	}	
	
	function pg_cell_init(icell_width,obj,cell_id_nm,tot_len) {
		var inum = 0;
		var ins_cell_str = "";	
	
		while ( inum <= tot_len ) {
			//inum += icell_width;
			//ins_cell_str += '<span id="' + cell_id_nm + '" >&nbsp;</span>';
			inum += icell_width*2 ; 
			ins_cell_str += '<span id="' + cell_id_nm + '" >&nbsp;</span><span>&nbsp;</span>';
		} 
		//ins_cell_str += '<span id="' + cell_id_nm + '" >&nbsp;</span>';
		obj.innerHTML = ins_cell_str;
	}
	


	function progress_clear(obj) {
		for (var i = 0; i < obj.length; i++) obj[i].style.backgroundColor = 'transparent';
	}
	
	function progress_update(obj,cur_cnt) {

		if ( cur_cnt >= obj.length ) cur_cnt = obj.length - 1;

		obj[cur_cnt].style.backgroundColor = progressColor;
	}
		
</script>

 
<SCRIPT LANGUAGE=javascript FOR=MiInstaller EVENT=OnStartDownLoad(VersionFileName,DownFileName,Type,TotalCnt,CurIndex)>
	//alert("OnStartDownLoad : "+DownFileName+" "+Type);
		
	if ( Type == 1 ) //EVENTCONFIG
	{
		progress_clear(progress);
	} else if ( Type == 2 ) //EVENTGETVERSIONCNT
	{
		TotalItemCnt = TotalCnt;
		progress_clear(progress1);
		if ( CurIndex > 1 ) {
			var before_At = pg_cell_At;
			pg_cell_At += parseInt( ( ( (CurIndex - 1)/TotalVersionFileCnt ) * progress.length ) - before_At );
			for ( var i = before_At ; i < pg_cell_At ; i++ ) progress_update(progress,i);
		}	
		pg1_cell_At = 0;
		var tpos = DownFileName.lastIndexOf("/") + 1;
		var Fname = DownFileName.substr(tpos,DownFileName.length - tpos);
		item_nm.innerHTML = "&nbsp;" + Fname;
	} else if ( Type == 3 ) //EVENTDOWNLOAD
	{
		var before_At = pg1_cell_At;
		pg1_cell_At += parseInt( ( ( (CurIndex - 1)/TotalItemCnt ) * progress.length ) - before_At );
		for ( var i = before_At ; i < pg1_cell_At ; i++ ) progress_update(progress1,i);
		if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;���� �ٿ�ε� ��....";
	} else if ( Type == 4 ) //EVENTDISTRIBUTE
	{
		if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;���� ��� ���� ��....";
	}	
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller event=OnEndDownLoad(VersionFileName,DownFileName,Type,TotalCnt,CurIndex)>
{
		//alert("OnEndDownLoad : "+DownFileName+" "+Type);
		
		if ( Type == 1 ) //EVENTCONFIG
		{
			TotalVersionFileCnt = TotalCnt;
			for ( var i = pg_cell_At ; i < progress.length ; i++ ) progress_update(progress,i);
			for ( var j = pg1_cell_At ; j < progress1.length ; j++ ) progress_update(progress1,j);
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;��ġ �Ϸ�";

			fn_run();
			
			//window.location = "./start.html";
			//alert("Install Completed !! ");
		} else if ( Type == 2 )//EVENTGETVERSIONCNT
		{
			if ( TotalCnt == CurIndex )
				for ( var i = pg1_cell_At ; i < progress1.length ; i++ ) progress_update(progress1,i);
			else {	
				if ( CurIndex > 1 ) {
				    var before_At = pg_cell_At;
					pg_cell_At += parseInt( ( ( (CurIndex - 1)/TotalVersionFileCnt ) * progress.length ) - before_At );
		
					for ( var i = before_At ; i < pg_cell_At ; i++ ) progress_update(progress,i);
				}	
				pg1_cell_At = 0;
		//		tot_item.innerHTML = "&nbsp;" + CurIndex + "/" + TotalCnt;
				
				var tpos = DownFileName.lastIndexOf("/") + 1;
				var Fname = DownFileName.substr(tpos,DownFileName.length - tpos);
				
				item_nm.innerHTML = "&nbsp;" + Fname;
				//item_size.innerHTML = "&nbsp;" + VersionFileName;
			}

		} else if ( Type == 3 )//EVENTDOWNLOAD
		{
		    var before_At = pg1_cell_At;
			pg1_cell_At += parseInt( ( ( (CurIndex - 1)/TotalItemCnt ) * progress.length ) - before_At );

			for ( var i = before_At ; i < pg1_cell_At ; i++ ) progress_update(progress1,i);
		
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;���� �ٿ�ε� ��....";
		} else if ( Type == 4 )//EVENTDISTRIBUTE
		{
			for ( var i = pg_cell_At ; i < progress.length ; i++ ) progress_update(progress,i);
			if ( IsError ) {
					t_err.className = "show";
					reinstall.className = "show";
			}		
			
			if ( prc_msg != "" && prc_msg != null && prc_msg != "undefined" ) prc_msg.innerHTML = "&nbsp;���� ��� ���� �Ϸ�";
			
			
			//window.location = "./start.html";
			//alert("Install Completed !! ");
		}
} 
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller event=OnProgress(DownFileName,progress,progressMax,statusText)>
{
	//prog1.innerHTML = "&nbsp;" + progress;
	//prog2.innerHTML = "&nbsp;" + progressMax;
	//prog3.innerHTML = "&nbsp;" + statusText;
} 
</SCRIPT>

<SCRIPT language=JavaScript for=MiInstaller event=OnError(ItemName,ErrorCode,ErrorMsg)>
		IsError = true;
		alert(ErrorMsg);
		err_msg.innerHTML += '<font class="f2" color="red">' + ItemName + '&nbsp;�ٿ�&nbsp;����&nbsp; -- ErrorCode:' + ErrorCode + ' ' + ErrorMsg + "<br>";
		t_err.className = "show";
		reinstall.className = "show";
</SCRIPT>

</head>
<BODY>
<div align="center"><!-- img src="./img/loading_2.jpg" --> 
  <table width="100" height="5" border="0" cellpadding="0" cellspacing="0">
     <tr> 
       <td></td>
     </tr>
     <tr> 
       <td></td>
     </tr>
  </table>
</div>
<table width="700" height="180" border="0" align="center" cellpadding="0" cellspacing="0" style="border:1px solid #dadada" >
  <tr> 
    <td style="border:5px solid #F0F0F0"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="300" align="center"><img src="./img/loading_2.jpg" ></td>
          <td>
          	<table border=0>
              <tr> 
                <td class="f2"> ��ü ���ϼ�ġ ���� ��Ȳ</td>
              </tr>
              <tr> 
                <td width="250"> <div id=pg style="font-size:8pt;padding:1px;border:1px GROOVE silver;height:10;overflow:hidden" > 
                    <span id="progress" >&nbsp;</span> </div></td>
              </tr>
              <tr> 
                <td colspan=2 class="f2" ><br>
                  ���� ���ϼ�ġ ���� ��Ȳ</td>
              </tr>
              <tr> 
                <td> <div id=pg1 style="font-size:8pt;padding:1px;border:1px GROOVE silver;height:10;overflow:hidden"> 
                    <span id="progress1" >&nbsp;</span> </div></td>
              </tr>
              <tr> 
                <td> <table width="100" height="5" border="0" cellpadding="0" cellspacing="0">
                    <tr> 
                      <td></td>
                    </tr>
                  </table>
                  <table cellpadding="1" cellspacing="1">
                    <tr> 
                      <td class="f2">������� : </td>
                      <td id="item_nm" class="f2" align=left NOWRAP>&nbsp;
                      <td> </tr>
                  </table>
              </td>
              </tr>
              <tr> 
                <td id=prc_msg class="f2" >&nbsp;
                <td> </tr>
            </table>
            <table width="255" class="hide" id=t_err>
              <tr> 
                <td class="f2">��ġ�� ������ �߻��� �׸�</td>
              </tr>
              <tr> 
                <td id=err_msg class="f2">&nbsp;</td>
              </tr>
            </table>
            <table class="hide" id=reinstall>
              <tr> 
                <td><a class="f2" href="javascript:window.location.reload();">�ڵ��缳ġ</a></td>
              </tr>
              <tr> 
                <td><a class="f2" href="./ui/320U/MiPlatform_SetupUpdater321_20130222_1609.exe">������ġ</a></td>
              </tr>
            </table>
            <table class="show" id=ins_reg>
			     <!-- tr> 
			       <td><a class="f2" href="./ui/320U/UAC_release.reg">WINDOWS7 �̻� UAC����</a></td>
			     </tr -->
			     <tr> 
			       <td><a class="f2" href="./ui/320U/SRM_Explorer_Setting.reg">Internet explorer ����</a></td>
			     </tr>
			     <tr> 
			       <td><a class="f2" href="./ui/320U/Uninstall_manual.zip">���� ���α׷� �ٿ�ε�</a></td>
			     </tr>
			     <tr> 
			       <td><a class="f2" href="./ui/320U/uninstall_manual.pdf">Uninstall_manual</a></td>
			     </tr>
			     <tr> 
			       <td><a class="f2" href="./ui/320U/install_manual.pdf">install_manual</a></td>
			     </tr>
            </table>
            </td>
        </tr>
        <tr>
        	<!--td id=msg class="f2" >
        * ��ġ��  ���� �߻��� 1588-7895(�������Ʈ ����������)�� �����ϼ���.<BR>
        	&nbsp;&nbsp;[����:���뿤��������]
        	</td-->
        </tr>
      </table></td>
  </tr>
</table>

<script language="javascript">
	CreateMiInstlr("MiInstaller","Win32U","3.2","<%=keyValue%>");
</script>				
<script language="javascript">

	var TotalVersionFileCnt;
	var TotalItemCnt;

	if ( Check_Module() == true ) { 
		var tmp_len = progress.offsetWidth; 
		var tmp_tot_len = pg.offsetWidth; 
		pg_cell_init(tmp_len,pg,"progress",tmp_tot_len);
		pg_cell_init(tmp_len,pg1,"progress1",tmp_tot_len);

		MiInstaller.DeviceType      = "Win32U";
		MiInstaller.Key             = "<%=keyValue%>";
		MiInstaller.Launch          = true;
		MiInstaller.Width           = 1024;
		MiInstaller.Height	        = 727;	// 745
		MiInstaller.Retry           = 1;
		MiInstaller.Timeout         = 1800;
		MiInstaller.GlobalVal       = "";
		MiInstaller.OnlyOne         = false;  //true;
		MiInstaller.StartXml        = "<%=webAppUrl%>";
		MiInstaller.ComponentPath	= "%UserApp%TobeSoft\\"+"<%=keyValue%>"+"\\component";
	 	MiInstaller.StartImage      = "";		// MiPlatform Load �̹���
	 //	MiInstaller.UBKImage        = "%component%BK1.jpg";  // ������Ʈ ����̹��� (������ 250 * 300)

		/***** Vista OS UAC Check *****/
	//  var IsUACEnabled = MiInstaller.IsUACEnabled();

     // win8 ���� ��ġ ���� HSS
	/*
	  	if(IsAfterVista()) {
			if(IsUACEnabled==true) {
				if(MiInstaller.IsElevatedProcess() == true)
					IsUACEnabled = false;
					
				MiInstaller.UpdateUrl = "<%=SvrUrl%>/ui/320U/update_info_VISTA.xml";
			} else {
				MiInstaller.UpdateUrl = "<%=SvrUrl%>/ui/320U/update_info_VISTA_UAC.xml";
			}  
		} else {
			MiInstaller.UpdateUrl = "<%=SvrUrl%>/ui/320U/update_info.xml";
		}
	*/

		if(IsAfterVista()) {
			if (IsWin8()) {
				MiInstaller.UpdateUrl = "<%=SvrUrl%>/ui/320U/update_info_WIN8_UAC.xml";
			}
			else {
				MiInstaller.UpdateUrl = "<%=SvrUrl%>/ui/320U/update_info_VISTA_UAC.xml";
			}
		} else {
			MiInstaller.UpdateUrl = "<%=SvrUrl%>/ui/320U/update_info.xml";
		}

		
		var Bcnt = MiInstaller.ExistVersionUpCnt(); 
		if ( Bcnt ) {
			MiInstaller.StartDownload();
		} else {
			fn_run();
		}
	} else {
		reinstall.className = "show";
		window.location.href = "./ui/320U/MiPlatform_SetupUpdater321_20130222_1609.exe";
	}
</script>
</body>
</html>
