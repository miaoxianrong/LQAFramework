<%
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="";
}
%>
<% 
boolean bTest=false;
String MyDepart="2";
%>
<html>
<head>

<title></title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
	line-height: 180%;
	color: #2E252A;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.np
	{
	font-family: Webdings;
	font-size:15px;
	color:black;
	cursor:hand;
	}
-->
</style>
<link href="../img/web.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript>
function switchSysBar(){
	if (switchPoint.innerText==3){
		switchPoint.innerText=4
		document.all("mnuList").style.display="none"
		top.content.cols="16,*"
	}
	else{
		switchPoint.innerText=3
		document.all("mnuList").style.display=""				
		top.content.cols="175,*"		
	}
}
</SCRIPT>
</head>

<body bgcolor="#F2FAFD">
<%
String XingMing="My";
%>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
     <TD height="6" colspan=3></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD><a href="javascript:showMenuAll( 'kuaijie' )"><%=XingMing%> desktop</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="kuaijie" style="DISPLAY:none">
    <TR> 
      <TD>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/filter.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/filter.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Xpath run result</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/dom_special.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/dom_special.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Valid DOM</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/dom_translate.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/dom_translate.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Translate</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/dom.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/dom.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">DOM analysis</a></TD>
    </TR>    
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/guimap.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/guimap.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">GUIMap</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/progress.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/progress.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Mapping Progress</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../develop/reason.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../develop/reason.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Project Filter</a></TD>
    </TR> 
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/newfeature_string_confirm.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/newfeature_string_confirm.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Confirm(before)</a></TD>
    </TR> 
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/notfound_newfeature_string.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/notfound_newfeature_string.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Confirm(after)</a></TD>
    </TR> 
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/mapping_analysis.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/mapping_analysis.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Analysis overview</a></TD>
    </TR>    
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/mapping_report.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/mapping_report.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Template</a>(<font color=green>Draft</font>)</TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/analysis_pre.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/analysis_pre.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">pre-analysis</a></TD>
    </TR> 
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/analysis_post.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/analysis_post.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">post-analysis</a></TD>
    </TR> 
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/log.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/log.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">log</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/alert.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/alert.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Alert</a></TD>
    </TR>   
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/debug.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/debug.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Debug</a></TD>
    </TR>   
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/navigation.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/navigation.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Report</a></TD>
    </TR> 
    
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/parameter.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/parameter.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">parameter</a>(<font color=red>no</font>)</TD>
    </TR> 
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../develop/reason.jsp?ProjectID=00000000" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../develop/reason.jsp?ProjectID=00000000" target="mainFrame">Common Filter</a></TD>
    </TR> 
</TABLE>

    </td>
  </tr>
</table>

<%
//I18N Testing
%>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
     <TD height="6" colspan=3></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD><a href="javascript:showMenuAll( 'i18n' )">I18N Testing</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="i18n" style="DISPLAY:none">
    <TR> 
      <TD>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/hardcode.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/hardcode.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Detect hard code</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/truncation.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/truncation.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Detect truncation</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../report/iformat.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/iformat.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Detect iFormat</a></TD>
    </TR>
</TABLE>

    </td>
  </tr>
</table>

<% if(MyDepart.compareTo("2")==0||MyDepart.compareTo("4")==0||MyDepart.compareTo("1")==0){ %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
     <TD height="5"></TD>
     <TD background="../img/line.gif"></TD>
     <TD></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD height="6"><a href="javascript:showMenuAll( 'caigou' )">Workbench</a></TD>
    </TR>
</TABLE>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="caigou" style="DISPLAY:none">
    <TR> 
      <TD>
<% if(MyDepart.compareTo("2")==0||MyDepart.compareTo("4")==0){ %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 1,'ZMEMU_CG',MEMU_CG_01,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id="MEMU_CG_01"></TD>
     <TD height="25"><a href="javascript:showMenu1( 1,'ZMEMU_CG',MEMU_CG_01,0 )">XPATH</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG1" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../develop/xpath_add.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../develop/xpath_add.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Create XPATH</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../develop/xpath.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../develop/xpath.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Manage XPATH</a></TD>
    </TR>

    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD><a href="#" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="#" target="mainFrame">Import XPATH</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD><a href="#" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="#" target="mainFrame">Export XPATH</a></TD>
    </TR>
</TABLE>
<%} %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 2,'ZMEMU_CG',MEMU_CG_02,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_CG_02></TD>
     <TD height="25"><a href="javascript:showMenu1( 2,'ZMEMU_CG',MEMU_CG_02,0 )">Excluder Filter</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG2" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <% if(MyDepart.compareTo("2")==0||MyDepart.compareTo("4")==0){ %>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=wode" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=wode" target="mainFrame">Create Excluder</a></TD>
    </TR>
    <%} %>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Manage Excluder</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigrkd_open_inactive.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open_inactive.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Run Excluder</a></TD>
    </TR>
     <% if(bTest){ %>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigrkd_cx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_cx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Import Excluder</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigthd_cx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigthd_cx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Export Excluder</a></TD>
    </TR>
    <% } %>
</TABLE>
<% if(MyDepart.compareTo("2")==0||MyDepart.compareTo("4")==0){ %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 3,'ZMEMU_CG',MEMU_CG_03,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_CG_03></TD>
     <TD height="25"><a href="javascript:showMenu1( 3,'ZMEMU_CG',MEMU_CG_03,0 )">Unique Object</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG3" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=aaa" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD title='aa'><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=aaa" target="mainFrame">Object 1</a></TD>
	    </TR>
	     <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=aaa" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD title='aa'><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=aaa" target="mainFrame">Object 2</a></TD>
	    </TR>
	     <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=aaa" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD title='aa'><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=aaa" target="mainFrame">Object 3</a></TD>
	    </TR>
	    
</TABLE>
<%}%>
<%
if(MyDepart.compareTo("2")==0||MyDepart.compareTo("4")==0){
%>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 4,'ZMEMU_CG',MEMU_CG_04,1 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CG_04></TD>
     <TD height="25"><a href="javascript:showMenu1( 4,'ZMEMU_CG',MEMU_CG_04,1 )">Input value</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG4" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../caigou/xiaoshou_fenpei.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../caigou/xiaoshou_fenpei.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Import csv file</a></TD>
    </TR>
<%}%>
<% if(bTest){ %>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/cg_dwtj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/cg_dwtj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/cg_cktj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/cg_cktj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/cg_jbrtj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/cg_jbrtj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/cg_spth.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/cg_spth.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/cg_dwth.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD noWrap><a href="../finance/tongji/cg_dwth.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/cg_ckth.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/cg_ckth.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/cg_yfx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/cg_yfx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/cg_kptj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/cg_kptj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaaa</a></TD>
    </TR>
<% } //bTest%>    
</TABLE>

      	</TD>
    </TR>
</TABLE>


<%}%>
<% if(MyDepart.compareTo("4")==0||MyDepart.compareTo("2")==0||MyDepart.compareTo("1")==0){ %>
      	
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"></TD>
     <TD height="6"></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD height="6"><a href="javascript:showMenuAll( 'xiaoshou' )">Framework Management</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="xiaoshou" style="DISPLAY:none">
    <TR> 
      <TD>
<% if(MyDepart.compareTo("4")==0||MyDepart.compareTo("2")==0){ %>      	
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 1,'ZMEMU',MEMU_01,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id="MEMU_01"></TD>
     <TD><a href="javascript:showMenu1( 1,'ZMEMU',MEMU_01,0 )">Execution</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU1" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../xiaoshou/product_search.jsp?ProjectID=<%=ProjectID%>?ChuRu=Chu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../xiaoshou/product_search.jsp?ProjectID=<%=ProjectID%>?ChuRu=Chu" target="mainFrame">Record</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/product_wh.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/product_wh.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Play</a></TD>
    </TR>  
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../xiaoshou/kehu_wh.jsp?ProjectID=<%=ProjectID%>?Youxiao=1&Leibie=1" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../xiaoshou/kehu_wh.jsp?ProjectID=<%=ProjectID%>?Youxiao=1&Leibie=1" target="mainFrame">Debug</a></TD>
    </TR>    
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../xiaoshou/kehu_wh.jsp?ProjectID=<%=ProjectID%>?Youxiao=1&Leibie=1" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../xiaoshou/kehu_wh.jsp?ProjectID=<%=ProjectID%>?Youxiao=1&Leibie=1" target="mainFrame">Adjust/Recall</a></TD>
    </TR>    
</TABLE>
<% } %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 2,'ZMEMU',MEMU_02,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_02></TD>
     <TD height="25"><a href="javascript:showMenu1( 2,'ZMEMU',MEMU_02,0 )">UIContext Mapping</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU2" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <% if(MyDepart.compareTo("4")==0||MyDepart.compareTo("2")==0){ %> 
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=wode" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=wode" target="mainFrame">PLOC strings</a></TD>
    </TR>
    <% } %>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">New/Fuzzy</a></TD>
    </TR>
    
    <% if(bTest){ %>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_cx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_cx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xxxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosthd_cx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosthd_cx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xxx</a></TD>
    </TR>
    <% } %>
</TABLE>
<% if(MyDepart.compareTo("4")==0||MyDepart.compareTo("2")==0){ %>   
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 3,'ZMEMU',MEMU_03,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_03></TD>
     <TD height="25"><a href="javascript:showMenu1( 3,'ZMEMU',MEMU_03,0 )">Agile builds</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU3" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=a" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD title='aa'><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=a" target="mainFrame">build 01</a></TD>
	    </TR>
	    
	     <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=a" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD title='aa'><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=a" target="mainFrame">build 02</a></TD>
	    </TR>
	    
	     <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=a" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD title='aa'><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=a" target="mainFrame">build 03</a></TD>
	    </TR>
	    
	     <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=a" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD title='aa'><a href="../jsp/aaa.jsp?ProjectID=<%=ProjectID%>?DanjuBH=a" target="mainFrame">build 04</a></TD>
	    </TR>
    
</TABLE>
 <% } %>
<%
if(MyDepart.compareTo("4")==0||MyDepart.compareTo("2")==0){
%>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 4,'ZMEMU',MEMU_04,1 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_04></TD>
     <TD height="25"><a href="javascript:showMenu1( 4,'ZMEMU',MEMU_04,1 )">Baseline Management</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU4" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../xiaoshou/caigou_fenpei.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../xiaoshou/caigou_fenpei.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Create baseline</a></TD>
    </TR>
<% }%>
<% if(bTest){ %>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_dwtj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_dwtj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Com</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_cktj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_cktj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">sss</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_jbrtj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_jbrtj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">sss</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_dqtj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_dqtj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">sss</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_pptj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_pptj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_khfltj.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_khfltj.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xx</a></TD>
    </TR>

   
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD height="5" background="../img/sx.gif"></TD>
      <TD colspan=2></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_spph.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_spph.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xxx</a></TD>
    </TR>
    
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_ckph.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_ckph.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD height="5" background="../img/sx.gif"></TD>
      <TD colspan=2></TD>
    </TR>
     
    <% } //bTest %>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tongji/xs_yfx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tongji/xs_yfx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Compare</a></TD>
    </TR>
   
</TABLE>

      	</TD>
    </TR>
</TABLE>

<%}%>

<% if(MyDepart.compareTo("5")==0||MyDepart.compareTo("1")==0){ %>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"></TD>
     <TD height="6"></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD height="6"><a href="javascript:showMenuAll( 'cunhuo' )">xxx</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="cunhuo" style="DISPLAY:none">
    <TR> 
      <TD>


<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 2,'ZMEMU_CUNHUO',MEMU_CUNHUO_02,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_CUNHUO_02></TD>
     <TD height="25"><a href="javascript:showMenu1( 2,'ZMEMU_CUNHUO',MEMU_CUNHUO_02,0 )">xxx</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CUNHUO2" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=houqin" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=houqin" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=houqin" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=houqin" target="mainFrame">xx</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/caigou/caigrkd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=houqin" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=houqin" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=houqin" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=houqin" target="mainFrame">xxx</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 3,'ZMEMU_CUNHUO',MEMU_CUNHUO_03,1 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CUNHUO_03></TD>
     <TD height="25"><a href="javascript:showMenu1( 3,'ZMEMU_CUNHUO',MEMU_CUNHUO_03,1 )">xxx</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CUNHUO3" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../xiaoshou/kucun.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../xiaoshou/kucun.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/saoma_tool_product.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/saoma_tool_product.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xxx</a></TD>
    </TR>
    
    
    
</TABLE>

      	</TD>
    </TR>
</TABLE>
<% } %>

<% if(MyDepart.compareTo("3")==0||MyDepart.compareTo("1")==0){ %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"></TD>
     <TD height="6"></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD height="6"><a href="javascript:showMenuAll( 'caiwu' )">xxx</a></TD>
    </TR>
</TABLE>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="caiwu" style="DISPLAY:none">
    <TR> 
      <TD>


<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 2,'ZMEMU_CAIWU',MEMU_CAIWU_02,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_CAIWU_02></TD>
     <TD height="25"><a href="javascript:showMenu1( 2,'ZMEMU_CAIWU',MEMU_CAIWU_02,0 )">xxx</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CAIWU2" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>?Yishenhe=yufu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=yufu" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=yufu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=yufu" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=yifu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=yifu" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=yifu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open.jsp?ProjectID=<%=ProjectID%>?Caozuo=yifu" target="mainFrame">xxx</a></TD>
    </TR>
    
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigrkd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Yishenhe=yufu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=yufu" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=yufu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=yufu" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/caigou/caigrkd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=yifu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/caigou/caigrkd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=yifu" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=yifu" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/xiaoshou/xiaosckd_open_inactive.jsp?ProjectID=<%=ProjectID%>?Caozuo=yifu" target="mainFrame">xxx</a></TD>
    </TR>
</TABLE>

<% if(bTest){ %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 4,'ZMEMU_CAIWU',MEMU_CAIWU_04,1 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CAIWU_04></TD>
     <TD height="25"><a href="javascript:showMenu1( 4,'ZMEMU_CAIWU',MEMU_CAIWU_04,1 )">xxx</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CAIWU4" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/wlcx/wldz_cx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/wlcx/wldz_cx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/jingying/ysk.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/jingying/ysk.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/jingying/yfk.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/jingying/yfk.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/jingying/ribb.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/jingying/ribb.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xxx</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank2.gif"></TD>
      <TD><a href="../finance/jingying/yuebb.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/jingying/yuebb.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">xxx</a></TD>
    </TR>
</TABLE>
<% } %>
      	</TD>
    </TR>
</TABLE>
<% } %>




<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"></TD>
     <TD height="6"></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD height="6"><a href="javascript:showMenuAll( 'xitong' )"><% if(MyDepart.compareTo("1")==0){ %>System Management<%}else{%>System Management<%}%></a></TD>
    </TR>
</TABLE>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="xitong" style="DISPLAY:none">
    <TR> 
      <TD>
<% if(MyDepart.compareTo("1")==0){ %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 1,'ZMEMU_XITONG',MEMU_XITONG_01,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id="MEMU_XITONG_01"></TD>
     <TD height="25"><a href="javascript:showMenu1( 1,'ZMEMU_XITONG',MEMU_XITONG_01,0 )">User account</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG1" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/huow_ck_htm.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/huow_ck_htm.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/cpfl/main.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/cpfl/main.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/xitong.jsp?ProjectID=<%=ProjectID%>?url=huow_cp_wh.jsp?ProjectID=<%=ProjectID%>&url2=huow_cp_dh.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/xitong.jsp?ProjectID=<%=ProjectID%>?url=huow_cp_wh.jsp?ProjectID=<%=ProjectID%>&url2=huow_cp_dh.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/xitong.jsp?ProjectID=<%=ProjectID%>?url=kehu_wh.jsp?ProjectID=<%=ProjectID%>&url2=kehu_dh.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/xitong.jsp?ProjectID=<%=ProjectID%>?url=kehu_wh.jsp?ProjectID=<%=ProjectID%>&url2=kehu_dh.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/xitong.jsp?ProjectID=<%=ProjectID%>?url=zhiyuan_wh.jsp?ProjectID=<%=ProjectID%>&url2=zhiyuan_dh.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/xitong.jsp?ProjectID=<%=ProjectID%>?url=zhiyuan_wh.jsp?ProjectID=<%=ProjectID%>&url2=zhiyuan_dh.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/chukuleixing.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/chukuleixing.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/rukuleixing.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/rukuleixing.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/qitsrlx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/qitsrlx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/feiylx.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/feiylx.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/jiesfs.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/jiesfs.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/huow_cp_sxxsz.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/huow_cp_sxxsz.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/wldwfl/main.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/wldwfl/main.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/diqu/main.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/diqu/main.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/pinp.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/pinp.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>

</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 3,'ZMEMU_XITONG',MEMU_XITONG_03,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_XITONG_03></TD>
     <TD height="25"><a href="javascript:showMenu1( 3,'ZMEMU_XITONG',MEMU_XITONG_03,0 )">aaa</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG3" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/glry/glry_htm.jsp?ProjectID=<%=ProjectID%>?lmbh=204" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/glry/glry_htm.jsp?ProjectID=<%=ProjectID%>?lmbh=204" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/glry/glry_wh.jsp?ProjectID=<%=ProjectID%>?lmbh=204" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/glry/glry_wh.jsp?ProjectID=<%=ProjectID%>?lmbh=204" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/glry/glry_qxlb.jsp?ProjectID=<%=ProjectID%>?lmbh=205" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/glry/glry_qxlb.jsp?ProjectID=<%=ProjectID%>?lmbh=205" target="mainFrame">aaa</a></TD>
    </TR>
    
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 4,'ZMEMU_XITONG',MEMU_XITONG_04,0)" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_XITONG_04></TD>
     <TD height="25"><a href="javascript:showMenu1( 4,'ZMEMU_XITONG',MEMU_XITONG_04,0 )">aaaa</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG4" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/config/xmsz.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/xmsz.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/xitong.jsp?ProjectID=<%=ProjectID%>?url=bdsz.jsp?ProjectID=<%=ProjectID%>&url2=huow_bdsz_dh.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/xitong.jsp?ProjectID=<%=ProjectID%>?url=bdsz.jsp?ProjectID=<%=ProjectID%>&url2=huow_bdsz_dh.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/bdqzsz.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/bdqzsz.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>

</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 9,'ZMEMU_XITONG',MEMU_XITONG_09,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_XITONG_09></TD>
     <TD height="25"><a href="javascript:showMenu1( 9,'ZMEMU_XITONG',MEMU_XITONG_09,0 )">aaa</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG9" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/config/qic_cp_lb.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/qic_cp_lb.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/qic_wl_lb.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/qic_wl_lb.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aa</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/config/qic_xjyh_lb.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/config/qic_xjyh_lb.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">aaa</a></TD>
    </TR>
</TABLE>
<%}%>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 5,'ZMEMU_XITONG',MEMU_XITONG_05,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_XITONG_05></TD>
     <TD height="25"><a href="javascript:showMenu1( 5,'ZMEMU_XITONG',MEMU_XITONG_05,0 )">Administrator</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG5" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../../report/hardcode.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/hardcode.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">User password</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../../report/progress_truncation.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/progress_truncation.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">User role</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../../report/progress_iformat.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/progress_iformat.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">User group</a></TD>
    </TR>
</TABLE>
<% if(MyDepart.compareTo("1")==0){ %>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 6,'ZMEMU_XITONG',MEMU_XITONG_06,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_XITONG_06></TD>
     <TD height="25"><a href="javascript:showMenu1( 6,'ZMEMU_XITONG',MEMU_XITONG_06,0 )">bbb</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG6" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../databak/sjbf.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../databak/sjbf.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">zz</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../databak/file_list.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../databak/file_list.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../databak/table_fileup.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../databak/table_fileup.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../databak/cpmldc.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../databak/cpmldc.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../databak/cpmlmb.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../databak/cpmlmb.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    
    
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 7,'ZMEMU_XITONG',MEMU_XITONG_07,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_XITONG_07></TD>
     <TD height="25"><a href="javascript:showMenu1( 7,'ZMEMU_XITONG',MEMU_XITONG_07,0 )">ddd</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG7" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/tools/empty_cp.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tools/empty_cp.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/tools/empty_huo.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tools/empty_huo.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tools/empty_ckflcp.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tools/empty_ckflcp.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tools/zhuanjie.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tools/zhuanjie.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">dd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tools/empty_riz.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tools/empty_riz.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tools/empty_system.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tools/empty_system.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 8,'ZMEMU_XITONG',MEMU_XITONG_08,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_XITONG_08></TD>
     <TD height="25"><a href="javascript:showMenu1( 8,'ZMEMU_XITONG',MEMU_XITONG_08,0 )">ddd</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG8" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/zt/zt_wh.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/zt/zt_wh.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" valign=top><img src="../finance/image/tree/joinbottom.gif" id=MEMU_qita></TD>
      <TD width="16" valign="top" onclick="showMenu1( 10,'ZMEMU_XITONG',MEMU_XITONG_10,1 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_XITONG_10></TD>
     <TD height="25"><a href="javascript:showMenu1( 10,'ZMEMU_XITONG',MEMU_XITONG_10,1 )">ddd</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_XITONG10" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../finance/tj/zaixyh.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tj/zaixyh.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">dddd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../finance/tj/xit_rz.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../finance/tj/xit_rz.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD valign=top><img src="../finance/image/tree/joinbottom.gif"></TD>
      <TD><img src="../img/tree_blank2.gif"></TD>
      <TD><a href="../reg/register.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../reg/register.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">ddd</a></TD>
    </TR>
</TABLE>
<% } %>
      	</TD>
    </TR>
</TABLE>


</body>
</html>
<script language="javascript">
<!--
function showMenu1( index,nm,tt,jw )
{
	if(document.all[nm+index].style.display=='none')
	{
		document.all[nm+index].style.display = '';
		tt.src="../img/jh.gif";
		if(index==10)
		  MEMU_qita.src="../img/line.gif"
	}
	else
	{
		document.all[nm+index].style.display = 'none';
		if(jw==1)
			tt.src="../img/tree_plus2.gif";
		else
			tt.src="../img/tree_plus.gif";
			
		if(index==10)
		  MEMU_qita.src="../finance/image/tree/joinbottom.gif"
	}
}

function showMenuAll( nm )
{
	if(document.all[nm].style.display=='none')
	{
		document.all[nm].style.display = '';
	}
	else
	{
		document.all[nm].style.display = 'none';
	}
}
//-->
</script>   
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          