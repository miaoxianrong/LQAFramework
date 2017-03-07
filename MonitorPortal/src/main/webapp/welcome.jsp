<html>
<head>
<%@ page contentType="text/html;charset=GB2312" %>
<title></title>
<link rel="stylesheet" href="css/font_mac.css">
<script language="JavaScript">
<!--
function MyClose() {
  if(window.confirm("Do you really want to exit?"))
		window.parent.close();
}

function MySource() {
	window.location = "view-source:"+window.location.href;
}

function MyReShow() {
	document.location.reload();
}

function MyFavorite() {
	window.external.addFavorite("http://www.Google.com", "Google");
}

function MySetBgColor(inColor) {
	var expDays = 30;
	var exp = new Date();
	exp.setTime(exp.getTime()+(expDays*24*60*60*1000));

	document.bgColor = inColor;
	document.cookie = "bgColor="+inColor+";"+exp+";path=";
	MM_showHideLayers('Layer13','','hide');
}

function MyGetBgColor() {
	var strCookie = document.cookie;
	var strBgColor = strCookie.indexOf("bgColor=");
	if(strBgColor==-1)	return;
	var nEnd = strCookie.indexOf(";", strBgColor);
	if(nEnd==-1) nEnd = strCookie.length+1;
	strBgColor = strCookie.substring(strBgColor+8, nEnd);
	document.bgColor = strBgColor;
}

function MyMenuOvr(src) {
	if(!src.contains(event.fromElement)) {
		src.style.cursor = "hand";
		src.bgColor = "#FFFFFF";
	}
}

function MyMenuOut(src) {
	if(!src.contains(event.toElement)) {
		src.style.cursor = "default";
		src.bgColor = "#DFDFDF";
	}
}

function MyMenuClk(src) {
	if(event.srcElement.tagName=="TD"){
		src.children.tags("A")[0].click();
	}
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_dragLayer(objName,x,hL,hT,hW,hH,toFront,dropBack,cU,cD,cL,cR,targL,targT,tol,dropJS,et,dragJS) { //v3.0
  //Copyright 1998 Macromedia, Inc. All rights reserved.
  var i,j,aLayer,retVal,curDrag=null,NS=(navigator.appName=='Netscape'), curLeft, curTop;
  if (!document.all && !document.layers) return false;
  retVal = true; if(!NS && event) event.returnValue = true;
  if (MM_dragLayer.arguments.length > 1) {
    curDrag = MM_findObj(objName); if (!curDrag) return false;
    if (!document.allLayers) { document.allLayers = new Array();
      with (document) if (NS) { for (i=0; i<layers.length; i++) allLayers[i]=layers[i];
        for (i=0; i<allLayers.length; i++) if (allLayers[i].document && allLayers[i].document.layers)
          with (allLayers[i].document) for (j=0; j<layers.length; j++) allLayers[allLayers.length]=layers[j];
      } else for (i=0;i<all.length;i++) if (all[i].style&&all[i].style.position) allLayers[allLayers.length]=all[i];}
    curDrag.MM_dragOk=true; curDrag.MM_targL=targL; curDrag.MM_targT=targT;
    curDrag.MM_tol=Math.pow(tol,2); curDrag.MM_hLeft=hL; curDrag.MM_hTop=hT;
    curDrag.MM_hWidth=hW; curDrag.MM_hHeight=hH; curDrag.MM_toFront=toFront;
    curDrag.MM_dropBack=dropBack; curDrag.MM_dropJS=dropJS;
    curDrag.MM_everyTime=et; curDrag.MM_dragJS=dragJS;
    curDrag.MM_oldZ = (NS)?curDrag.zIndex:curDrag.style.zIndex;
    curLeft= (NS)?curDrag.left:curDrag.style.pixelLeft; curDrag.MM_startL = curLeft;
    curTop = (NS)?curDrag.top:curDrag.style.pixelTop; curDrag.MM_startT = curTop;
    curDrag.MM_bL=(cL<0)?null:curLeft-cL; curDrag.MM_bT=(cU<0)?null:curTop -cU;
    curDrag.MM_bR=(cR<0)?null:curLeft+cR; curDrag.MM_bB=(cD<0)?null:curTop +cD;
    curDrag.MM_LEFTRIGHT=0; curDrag.MM_UPDOWN=0; curDrag.MM_SNAPPED=false; //use in your JS!
    document.onmousedown = MM_dragLayer; document.onmouseup = MM_dragLayer;
    if (NS) document.captureEvents(Event.MOUSEDOWN|Event.MOUSEUP);
  } else {
    var theEvent = ((NS)?objName.type:event.type);
    if (theEvent == 'mousedown') {
      var mouseX = (NS)?objName.pageX : event.clientX + document.body.scrollLeft;
      var mouseY = (NS)?objName.pageY : event.clientY + document.body.scrollTop;
      var maxDragZ=null; document.MM_maxZ = 0;
      for (i=0; i<document.allLayers.length; i++) { aLayer = document.allLayers[i];
        var aLayerZ = (NS)?aLayer.zIndex:aLayer.style.zIndex;
        if (aLayerZ > document.MM_maxZ) document.MM_maxZ = aLayerZ;
        var isVisible = (((NS)?aLayer.visibility:aLayer.style.visibility).indexOf('hid') == -1);
        if (aLayer.MM_dragOk != null && isVisible) with (aLayer) {
          var parentL=0; var parentT=0;
          if (!NS) { parentLayer = aLayer.parentElement;
            while (parentLayer != null && parentLayer.style.position) {
              parentL += parentLayer.offsetLeft; parentT += parentLayer.offsetTop;
              parentLayer = parentLayer.parentElement; } }
          var tmpX=mouseX-(((NS)?pageX:style.pixelLeft+parentL)+MM_hLeft);
          var tmpY=mouseY-(((NS)?pageY:style.pixelTop +parentT)+MM_hTop);
          var tmpW = MM_hWidth;  if (tmpW <= 0) tmpW += ((NS)?clip.width :offsetWidth);
          var tmpH = MM_hHeight; if (tmpH <= 0) tmpH += ((NS)?clip.height:offsetHeight);
          if ((0 <= tmpX && tmpX < tmpW && 0 <= tmpY && tmpY < tmpH) && (maxDragZ == null
              || maxDragZ <= aLayerZ)) { curDrag = aLayer; maxDragZ = aLayerZ; } } }
      if (curDrag) {
        document.onmousemove = MM_dragLayer; if (NS) document.captureEvents(Event.MOUSEMOVE);
        curLeft = (NS)?curDrag.left:curDrag.style.pixelLeft;
        curTop = (NS)?curDrag.top:curDrag.style.pixelTop;
        MM_oldX = mouseX - curLeft; MM_oldY = mouseY - curTop;
        document.MM_curDrag = curDrag;  curDrag.MM_SNAPPED=false;
        if(curDrag.MM_toFront) {
          eval('curDrag.'+((NS)?'':'style.')+'zIndex=document.MM_maxZ+1');
          if (!curDrag.MM_dropBack) document.MM_maxZ++; }
        retVal = false; if(!NS) event.returnValue = false;
    } } else if (theEvent == 'mousemove') {
      if (document.MM_curDrag) with (document.MM_curDrag) {
        var mouseX = (NS)?objName.pageX : event.clientX + document.body.scrollLeft;
        var mouseY = (NS)?objName.pageY : event.clientY + document.body.scrollTop;
        newLeft = mouseX-MM_oldX; newTop  = mouseY-MM_oldY;
        if (MM_bL!=null) newLeft = Math.max(newLeft,MM_bL);
        if (MM_bR!=null) newLeft = Math.min(newLeft,MM_bR);
        if (MM_bT!=null) newTop  = Math.max(newTop ,MM_bT);
        if (MM_bB!=null) newTop  = Math.min(newTop ,MM_bB);
        MM_LEFTRIGHT = newLeft-MM_startL; MM_UPDOWN = newTop-MM_startT;
        if (NS) {left = newLeft; top = newTop;}
        else {style.pixelLeft = newLeft; style.pixelTop = newTop;}
        if (MM_dragJS) eval(MM_dragJS);
        retVal = false; if(!NS) event.returnValue = false;
    } } else if (theEvent == 'mouseup') {
      document.onmousemove = null;
      if (NS) document.releaseEvents(Event.MOUSEMOVE);
      if (NS) document.captureEvents(Event.MOUSEDOWN); //for mac NS
      if (document.MM_curDrag) with (document.MM_curDrag) {
        if (typeof MM_targL =='number' && typeof MM_targT == 'number' &&
            (Math.pow(MM_targL-((NS)?left:style.pixelLeft),2)+
             Math.pow(MM_targT-((NS)?top:style.pixelTop),2))<=MM_tol) {
          if (NS) {left = MM_targL; top = MM_targT;}
          else {style.pixelLeft = MM_targL; style.pixelTop = MM_targT;}
          MM_SNAPPED = true; MM_LEFTRIGHT = MM_startL-MM_targL; MM_UPDOWN = MM_startT-MM_targT; }
        if (MM_everyTime || MM_SNAPPED) eval(MM_dropJS);
        if(MM_dropBack) {if (NS) zIndex = MM_oldZ; else style.zIndex = MM_oldZ;}
        retVal = false; if(!NS) event.returnValue = false; }
      document.MM_curDrag = null;
    }
    if (NS) document.routeEvent(objName);
  } return retVal;
}

function MM_showHideLayers() { //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
}
function SetFrameTitle(Title) { 
iframetitle.innerHTML="<nobr>"+Title+"</nobr>";
showframe();
}

function  hiddenframe(){
iframeLayer.style.visibility="hidden";
}

function  showframe(){
iframeLayer.style.visibility="visible";
}

function  setframesize(Width,Height){
iframeLayer.style.height=Height;
iframeLayer.style.width=Width;
document.all.myiframe.height=Height;
showframe();
}

function printit(){  
if(myiframe.frames.length>0)
{
 if (confirm("所要打印的内容是由"+myiframe.frames.length+"个frame嵌套组成的，打印效果可能会有影响，您希望继续打印吗？"))
          {
          myiframe.focus();
          myiframe.print();
          }
}
else
{
myiframe.focus();
myiframe.print();
}
} 
  
function  minframe( ){

if (MinAndRestore.alt=="Min")
{
iframeLayer.style.height=20;
iframeLayer.style.width=300;
iframeLayer.style.left=30;
iframeLayer.style.top =window.screen.availHeight-120;
document.all.myiframe.height=0;
MinAndRestore.alt="Restore";
}
else
{
restoreframe();
//myframewidth;
MinAndRestore.alt="Max";
}
showframe();
}       


function onlinehelp() {

showModalDialog("help.html","","dialogWidth:18;dialogHeight:16;dialogTop:50;dialogLeft:300;status:no;");
	
}


function  maxframe( ){
if (MaxAndRestore.alt=="Max")
{
iframeLayer.style.height=window.screen.availHeight-20;
iframeLayer.style.width=window.screen.availWidth-20;
iframeLayer.style.left=1;
iframeLayer.style.top =20;
document.all.myiframe.height=window.screen.availHeight;
MaxAndRestore.alt="Restore";
}else
{
restoreframe();
//myframewidth;
MaxAndRestore.alt="Max";
}
showframe();
}


function restoreframe()
{
var Width,Height;
Width=myiframe.winWidth;
Height=myiframe.winHeight;
if (!Width) Width=600;
if (!Height) Height=450;
iframeLayer.style.height=Height;
iframeLayer.style.width=Width;
iframeLayer.style.left=(window.screen.availWidth-Width)/2;
iframeLayer.style.top =100;
document.all.myiframe.height=Height;

}

function MySetBackground(pic) {
	document.body.background = pic;
	MM_showHideLayers('MyLayer13','','hide');
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<script language="javascript">
	MyGetBgColor();
</script>
</head>
<body background="images/bg1.jpg" bgcolor="#59599c" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_dragLayer('Layer1','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer18','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer2','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer3','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer4','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer5','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer6','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer7','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer8','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer9','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('iframeLayer','',0,0,550,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer10','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'');MM_dragLayer('Layer11','',0,0,35,25,true,false,-1,-1,-1,-1,false,false,0,'',false,'')" link="#333399" vlink="#333399" alink="#333399">
<div id="Layer1" style="HEIGHT: 40px; LEFT: 30px; POSITION: absolute; TOP: 200px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=26 src="images/ico/about.gif" width=31></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="./finance/xiaoshou/xiaosckd_open.jsp" onclick='setframesize(1248,640);SetFrameTitle("Admin")'  target="myiframe"><font color="#ffffff">Admin</font></a></td>
    </tr>
  </table>
</div>
 <% if(false){ %>
<div id="Layer2" style="HEIGHT: 40px; LEFT: 30px; POSITION: absolute; TOP: 260px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/email.gif" width=26></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="intro.html" onclick='setframesize(1248,640);SetFrameTitle("HR_MG")'  target="myiframe"><font color="#ffffff">Selenium</font></a></td>
    </tr>
  </table>
</div>
<%}%>
<div id="Layer11" style="HEIGHT: 40px; LEFT: 80px; POSITION: absolute; TOP: 200px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/skin.gif" width=26></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="./finance/caigou/caigrkd_open.jsp" onclick='setframesize(1248,640);SetFrameTitle("Integration")'  target="myiframe"><font color="#ffffff">Integration</font></a></td>
    </tr>
  </table>
</div>

<% if(false){ %>
<div id="Layer10" style="HEIGHT: 40px; LEFT: 80px; POSITION: absolute; TOP: 260px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/skin.gif" width=26></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="intro.html" onclick='setframesize(1248,640);SetFrameTitle("TRS")'  target="myiframe"><font color="#ffffff">Developer</font></a></td>
    </tr>
  </table>
</div>

<div id="Layer9" style="HEIGHT: 40px; LEFT: 80px; POSITION: absolute; TOP: 320px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/skin.gif" width=26></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="intro.html" onclick='setframesize(1248,640);SetFrameTitle("TRS")'  target="myiframe"><font color="#ffffff">Linguist</font></a></td>
    </tr>
  </table>
</div>
<%}%>
<div id="Layer3" style="HEIGHT: 40px; LEFT: 80px; POSITION: absolute; TOP: 140px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/skin.gif" width=26></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="./finance/caigou/caigrkd_open.jsp?Caozuo=wode" onclick='setframesize(1248,640);SetFrameTitle("Report")'  target="myiframe"><font color="#ffffff">Report</font></a></td>
    </tr>
  </table>
</div>

<div id="Layer4" style="HEIGHT: 40px; LEFT: 80px; POSITION: absolute; TOP: 80px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/wallpaper.gif" width=25></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="./xiaoshou/kehu_wh.jsp?Youxiao=1&Leibie=2" onclick='setframesize(1248,640);SetFrameTitle("UIContext")'  target="myiframe"><font color="#ffffff">UIContext</a></td>
    </tr>
  </table>
</div>
<% if(false){ %>
<div id="Layer5" style="HEIGHT: 40px; LEFT: 30px; POSITION: absolute; TOP: 320px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/guest.gif" width=27></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="intro.html" onclick='setframesize(1248,640);SetFrameTitle("PJ_MG")'  target="myiframe"><font color="#ffffff">Admin</font></a></td>
    </tr>
  </table>
</div>

<div id="Layer6" style="HEIGHT: 40px; LEFT: 30px; POSITION: absolute; TOP: 380px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/link.gif" width=27></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="intro.html" onclick='setframesize(1248,640);SetFrameTitle("FL_MG")'  target="myiframe"><font color="#ffffff"><nobr>Express</nobr></font></a></td>
    </tr>
  </table>
</div>
<%}%>
<div id="Layer7" style="HEIGHT: 40px; LEFT: 30px; POSITION: absolute; TOP: 140px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/icon.gif" width=25></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="./finance/xiaoshou/xiaosckd_open.jsp?Caozuo=wode" onclick='setframesize(1248,640);SetFrameTitle("I18N")'  target="myiframe"><font color="#ffffff">I18N
    </tr>
  </table>
</div>
<div id="Layer8" style="HEIGHT: 40px; LEFT: 30px; POSITION: absolute; TOP: 80px; WIDTH: 35px; Z-INDEX: 10"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="middle"> 
      <td><IMG height=25 src="images/ico/subject.gif" width=26></td>
    </tr>
    <tr align="middle"> 
      <td class="mycfont9"><A href="./xiaoshou/kehu_wh.jsp?Youxiao=1&Leibie=1" onclick='setframesize(1248,640);SetFrameTitle("Workbench")'  target="myiframe"><font color="#ffffff">Workbench</font></a></td>
    </tr>
  </table>
</div>



<div id="Layer17" style="HEIGHT: 55px; LEFT: 20px; POSITION: absolute; TOP: 20px; VISIBILITY: hidden; WIDTH: 150px; Z-INDEX: 400"> 
  <table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="#000000">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="3" cellpadding="0" class="mycfont9" bgcolor="#dfdfdf">

          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);" >　<A  href="./jsp/agent.jsp" onclick='setframesize(1248,640);SetFrameTitle("Workbench")' target="myiframe"><font color="#000000">Connect Hub</font></a></td>
          </tr>

          </table>
      </td>
    </tr>
  </table>
</div>



<div id="Layer12" style="HEIGHT: 55px; LEFT: 80px; POSITION: absolute; TOP: 20px; VISIBILITY: hidden; WIDTH: 120px; Z-INDEX: 200"> 
  <table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="#000000">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="3" cellpadding="0" class="mycfont9" bgcolor="#dfdfdf">
          
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MyReShow();"><font color="#000000">Refresh(R)</font></a></td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MyFavorite();"><font color="#000000">Favorite(F)</font></a></td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MyClose();"><font color="#000000">Close(X)</font></a></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<div id="Layer13" style="HEIGHT: 31px; LEFT: 100px; POSITION: absolute; TOP: 20px; VISIBILITY: hidden; WIDTH: 160px; Z-INDEX: 200"> 
  <table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="#000000">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="3" cellpadding="0" class="mycfont9" bgcolor="#dfdfdf">
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MySetBgColor('#000000');MySetBackground('images/bg6.jpg')"><font color="#000000">Background1</font></a></td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MySetBgColor('#003366');MySetBackground('images/bg2.jpg')"><font color="#000000">Background2</font></a></td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MySetBgColor('#999999');MySetBackground('images/bg3.jpg')"><font color="#000000">Background3</font></a></td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MySetBgColor('#999999');MySetBackground('images/bg4.jpg')"><font color="#000000">Background4</font></a></td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MySetBgColor('#999999');MySetBackground('images/bg5.jpg')"><font color="#000000">Background5</font></a></td>
          </tr>
       
          <tr> 
            <td> 
              <hr>
            </td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="javascript:MySetBgColor('#59599C');MySetBackground('images/bg1.jpg')"><font color="#000000">Background-Reset</font></a></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<div id="Layer14" style="HEIGHT: 45px; LEFT: 280px; POSITION: absolute; TOP: 20px; VISIBILITY: hidden; WIDTH: 75px; Z-INDEX: 200"> 
  <table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="#000000">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="3" cellpadding="0" class="mycfont9" bgcolor="#dfdfdf">
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="mailto:xianrong.miao@ca.com"><font color="#000000">Email(E)</font></a></td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="intro.html" target=_blank><font color="#000000">About(A)</font></a></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<div id="Layer15" style="HEIGHT: 45px; LEFT: 160px; POSITION: absolute; TOP: 20px; VISIBILITY: hidden; WIDTH: 180px; Z-INDEX: 201"> 
  <table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="#000000">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="3" cellpadding="0" class="mycfont9" bgcolor="#dfdfdf">
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<a href="http://www.Google.com" target="_blank"><font color="#000000">Google</font></a></td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<a href="http://www.zol.com.cn" target="_blank"><font color="#000000">CA</font></a></td>
          </tr>
          <tr> 
            <td> 
              <hr>
            </td>
          </tr>
          <tr> 
            <td onClick="MyMenuClk(this);" onMouseOut="MyMenuOut(this);" onMouseOver="MyMenuOvr(this);">　<A href="http://www.google.com" target="_blank"><font color="#000000">google</font></a></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<!-- #BeginEditable "MyLayer" --> 
<div id="iframeLayer" style="HEIGHT: 640px; LEFT: 160px; POSITION: absolute; TOP: 80px; WIDTH: 1024px; Z-INDEX: 100;visibility:visible"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#dfdfdf">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="28"><A href="null.html" target=myiframe><IMG alt=Close border=0 height=24 onclick=hiddenframe() src="images/macwin/macwin_r1_c01.gif" width=28></A></td>
            <td background="images/macwin/macwin_r1_c04.gif" width="*">&nbsp;</td>
            <td background="images/macwin/macwin_r1_c04.gif" width="67">&nbsp;</td>
            <td width="12"><IMG height=24 src="images/macwin/macwin_r1_c06.gif" width=12></td>
            <td background="images/macwin/macwin_r1_c07.gif" align="middle" class="mycfont9" width="60"><font id="iframetitle" style="CURSOR: move">Workbench</font></td>
            <td width="12"><IMG height=24 src="images/macwin/macwin_r1_c09.gif" width=12></td>
            <td background="images/macwin/macwin_r1_c04.gif" width="*">&nbsp;</td>
            <td background="images/macwin/macwin_r1_c04.gif" width="85" align="left"><IMG alt=Print height=24 onclick=printit() src="images/macwin/printer.gif" style="CURSOR: hand" width=24 ><IMG alt=Help height=24 onclick=onlinehelp()  src="images/macwin/help.gif" style ="CURSOR:  help" width=20 ><IMG id=MinAndRestore alt="最小化" height=24 onclick=minframe()  src="images/macwin/min.gif" style ="CURSOR: hand" width=19 ><IMG alt=最大化 height=24 id=MaxAndRestore onclick=maxframe()  src="images/macwin/max.gif" style="CURSOR: hand" width=22 ></td>
       
          </tr>
        </table>
      </td>
    </tr>
    <tr> 
      <td onMouseOut="MM_showHideLayers('Layer12','','hide'); MM_showHideLayers('Layer13','','hide'); MM_showHideLayers('Layer14','','hide'); MM_showHideLayers('Layer15','','hide');"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="8" background="images/macwin/macwin_r2_c01.gif"><IMG height=15 src="images/macwin/macwin_r2_c01.gif" width=8></td>
            <td> 
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td><hr>
                  </td>
                </tr>
                <tr> 
                  
                  <td><nolayer><iframe name="myiframe" src="./jsp/agent.jsp" frameborder=0 width="100%" height="640"></iframe></NOLAYER></td>
                </tr>
                 <tr> 
                  <td><hr>
                  </td>
                </tr>
              </table>
            </td>
            <td width="10" background="images/macwin/macwin_r2_c12.gif"><IMG height=15 src="images/macwin/macwin_r2_c12.gif" width=10></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr> 
      <td> 
      
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <TR> 
            <TD width=8 background="images/macwin/macwin_r2_c01.gif" rowspan="3"></TD>
            <TD bgcolor="#003399" rowspan="2" valign="bottom"> 
              <table width="10" border="0" cellspacing="0" cellpadding="0" height="5">
                <tr> 
                  <td></td>
                </tr>
              </table>
                <font size =2 color=red></font>
            </TD>
            <TD bgcolor="#003399" rowspan="2" valign="bottom"  align='right'>
              <font color="#FFFFFF" style="font-size:12px">&nbsp;&nbsp;</font>
            </TD>
            <TD width=10 background="images/macwin/macwin_r2_c12.gif" rowspan="3"> </TD>
          </TR>
          <TR> </TR>
            <TR> 
            <TD bgcolor="#003399" height="3" valign="bottom" colspan="2"></TD>
          </TR>
          <tr> 
            <td width="8"><IMG height=10 src="images/macwin/macwin_r4_c01.gif" width=8></td>
            <td colspan='2' background="images/macwin/macwin_r4_c02.gif"><IMG height=10 src="images/macwin/macwin_r4_c02.gif" width=11></td>
            <td width="10"><IMG height=10 src="images/macwin/macwin_r4_c12.gif" width=10></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<!-- #EndEditable --> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="34" bgcolor="#cfcfcf"><IMG border=0 height=23 src="images/topbar/left_bar.gif" useMap=#Map width=34><map name="Map"><area shape="RECT" coords="14,5,26,16" href="javascript:MyClose();" alt="Close" title="Close"></map></td>
          <td background="images/topbar/center_bar.gif" bgcolor="#cfcfcf" width="60" align="middle" class="mycfont9"><A href="#" onmouseover="MM_showHideLayers('Layer17','','show'); MM_showHideLayers('Layer12','','hide');MM_showHideLayers('Layer13','','hide'); MM_showHideLayers('Layer14','','hide'); MM_showHideLayers('Layer15','','hide');"><font color="#000000">Menu(C)</font></a></td>
          <td background="images/topbar/center_bar.gif" bgcolor="#cfcfcf" width="60" align="middle" class="mycfont9"><A href="#" onmouseover="MM_showHideLayers('Layer12','','show'); MM_showHideLayers('Layer17','','hide');MM_showHideLayers('Layer13','','hide'); MM_showHideLayers('Layer14','','hide'); MM_showHideLayers('Layer15','','hide');"><font color="#000000">Func(F)</font></a></td>
          <td background="images/topbar/center_bar.gif" bgcolor="#cfcfcf" width="60" align="middle" class="mycfont9"><A href="#" onmouseover="MM_showHideLayers('Layer13','','show'); MM_showHideLayers('Layer17','','hide');MM_showHideLayers('Layer12','','hide'); MM_showHideLayers('Layer14','','hide'); MM_showHideLayers('Layer15','','hide');"><font color="#000000">Conf(O)</font></a></td>
          <td background="images/topbar/center_bar.gif" bgcolor="#cfcfcf" width="60" align="middle" class="mycfont9"><A href="#" onmouseover="MM_showHideLayers('Layer15','','show'); MM_showHideLayers('Layer17','','hide');MM_showHideLayers('Layer13','','hide'); MM_showHideLayers('Layer12','','hide'); MM_showHideLayers('Layer14','','hide');"><font color="#000000">Link(L)</font></a></td>
          <td background="images/topbar/center_bar.gif" bgcolor="#cfcfcf" width="60" align="middle" class="mycfont9"><A href="#" onmouseover="MM_showHideLayers('Layer14','','show'); MM_showHideLayers('Layer17','','hide');MM_showHideLayers('Layer13','','hide'); MM_showHideLayers('Layer12','','hide'); MM_showHideLayers('Layer15','','hide');"><font color="#000000">Help(H)</font></a></td>
          <td background="images/topbar/center_bar.gif" bgcolor="#cfcfcf">&nbsp;</td>
          <td background="images/topbar/center_bar.gif" bgcolor="#cfcfcf" width="200" class="mycfont9" align="middle">User:<font color=navy><%=(String)session.getValue("XingMing")%></font></td>
          <td align="right" width="13" bgcolor="#cfcfcf"><IMG height=23 src="images/topbar/right_bar.gif" width=13></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr onMouseOut="MM_showHideLayers('Layer17','','hide');MM_showHideLayers('Layer12','','hide'); MM_showHideLayers('Layer13','','hide'); MM_showHideLayers('Layer14','','hide'); MM_showHideLayers('Layer15','','hide');"> 
    <td> 
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
    </td>
  </tr>
</table>

<p>&nbsp;</p>
</body>
<!-- #EndTemplate -->
</html>
