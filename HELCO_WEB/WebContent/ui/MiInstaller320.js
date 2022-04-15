function checkOS()
{
	var strOS = 'XP';

	if( navigator.appVersion.indexOf("Windows NT 6.3") != -1)  //Windows 8.1
        strOS = "WIN8.1";
    else if( navigator.appVersion.indexOf("Windows NT 6.2") != -1)
        strOS = "WIN8";
    else if( navigator.appVersion.indexOf("Windows NT 6.1") != -1)
        strOS = "WIN7";
    else if( navigator.appVersion.indexOf('Windows NT 6.0') != -1)
		strOS = 'VISTA';
	else if( navigator.appVersion.indexOf('Windows 98') != -1)
		strOS = '98';
	else if( navigator.appVersion.indexOf('Windows NT 5.0') != -1)
		strOS = '2000';
	else if( navigator.appVersion.indexOf('Windows NT 5.2') != -1)
		strOS = 'XP';
  	else if( navigator.appVersion.indexOf('Windows NT 5.1') != -1)
  		strOS = 'XP';
  	return strOS;
}

function Is98()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows 98') != -1)
  	bChk = true;
  return bChk;
}

function Is2000()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows NT 5.0') != -1)
  	bChk = true;
  return bChk;
}

function Is2003()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows NT 5.2') != -1)
  	bChk = true;
  return bChk;
}

function IsXp()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows NT 5.1') != -1)
  	bChk = true;
  return bChk;
}

function IsAfterVista()
{
  var bChk = false;
  if( navigator.appVersion.indexOf('Windows NT 6.0') != -1 || navigator.appVersion.indexOf("Windows NT 6.1") != -1 
      || navigator.appVersion.indexOf("Windows NT 6.2") != -1 || navigator.appVersion.indexOf("Windows NT 6.3") != -1)
  	bChk = true;
  if( navigator.userAgent.indexOf("Trident") != -1) bChk = true;
  return bChk;
}
function IsWin8()
{
  var bChk = false;
  if( navigator.appVersion.indexOf("Windows NT 6.2") != -1 || navigator.appVersion.indexOf("Windows NT 6.3") != -1)
  	bChk = true;
  return bChk;
}

function CreateMiInstlr(strID, Device,Version,Key)
{
	document.write('<object id="'+strID+'" classid="clsid:1A000B1F-B285-4fbf-B3CD-B50845003EBB" width="0" height="0" CodeBase="./ui/320U/MiPlatform_Updater321_20130222_1609.cab#VERSION=2012,12,28,1" SHOWASTEXT>' +
		'<PARAM NAME="DeviceType" VALUE="'+Device+'" >' +
		'<PARAM NAME="Version" VALUE="'+Version+'" >' +
		'<PARAM NAME="key" VALUE="'+Key+'" >' +
		'</object>' );
}

function ObjectMiInstlr(strID, Device,Version,Key)
{
	document.write('<object id="'+strID+'" classid="clsid:1A000B1F-B285-4fbf-B3CD-B50845003EBB" width="0" height="0" SHOWASTEXT>' +
		'<PARAM NAME="DeviceType" VALUE="'+Device+'" >' +
		'<PARAM NAME="Version" VALUE="'+Version+'" >' +
		'<PARAM NAME="key" VALUE="'+Key+'" >' +
		'</object>' );
}
