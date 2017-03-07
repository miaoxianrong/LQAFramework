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
	      <TD width="20"><a href="../../report/monitor.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../report/monitor.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Running monitor</a></TD>
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
	      <TD width="20"><a href="../../report/log.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../report/log.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Running log</a></TD>
	    </TR>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
	      <TD width="20"><a href="../../report/analysis_pre.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../report/navigation.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Project information</a></TD>
	    </TR> 	      
	</TABLE>

    </td>
  </tr>
</table>



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
        <!-- start -->
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valig ="top" onclick="showMenu1( 1,'ZMEMU_CG',MEMU_CG_01,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id="MEMU_CG_01"></TD>
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
	</TABLE> 
	<!--  end -->
	
	<!--  start -->
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valign="top" onclick="showMenu1( 2,'ZMEMU_CG',MEMU_CG_02,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_CG_02></TD>
	     <TD height="25"><a href="javascript:showMenu1( 2,'ZMEMU_CG',MEMU_CG_02,0 )">UniqueObject</a></TD>
	    </TR>
	</TABLE>
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG2" style="DISPLAY:none">
	    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/unique_add.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/unique_add.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Create UniqueObject</a></TD>
	    </TR>
	    <TR>
	      <TD></TD>
	      <TD background="../img/line2.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/unique.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/unique.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Manage UniqueObject</a></TD>
	    </TR>
	</TABLE>
	<!--  end --> 
	
	<!--  start -->
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valign="top" onclick="showMenu1( 3,'ZMEMU_CG',MEMU_CG_03,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_CG_03></TD>
	     <TD height="25"><a href="javascript:showMenu1( 3,'ZMEMU_CG',MEMU_CG_03,0 )">Excluder</a></TD>
	    </TR>
	</TABLE>
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG3" style="DISPLAY:none">
	    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/excluder_add.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/excluder_add.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Create Excluder</a></TD>
	    </TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line2.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/excluder.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/excluder.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Manage Excluder</a></TD>
	    </TR>
	</TABLE>
	<!--  end -->
	
	
	<!-- start -->
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valign="top" onclick="showMenu1( 4,'ZMEMU_CG',MEMU_CG_04,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CG_04></TD>
	     <TD height="25"><a href="javascript:showMenu1( 4,'ZMEMU_CG',MEMU_CG_04,0 )">Includer</a></TD>
	    </TR>
	</TABLE>
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG4" style="DISPLAY:none">
	    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>	    
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/includer_add.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/includer_add.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Create Includer</a></TD>
	    </TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/includer.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/includer.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Manage Includer</a></TD>
	    </TR>
	</TABLE>
	<!--  end -->
	
	<!-- start -->
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valign="top" onclick="showMenu1( 5,'ZMEMU_CG',MEMU_CG_05,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CG_05></TD>
	     <TD height="25"><a href="javascript:showMenu1( 5,'ZMEMU_CG',MEMU_CG_05,0 )">DOM Type</a></TD>
	    </TR>
	</TABLE>
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG5" style="DISPLAY:none">
	    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/domtype_add.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/domtype_add.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Create DOM type</a></TD>
	    </TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/domtype.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/domtype.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Manage DOM type</a></TD>
	    </TR>
	</TABLE>
	<!--  end -->
	
	<!-- start -->
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR>
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valign="top" onclick="showMenu1( 6,'ZMEMU_CG',MEMU_CG_06,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CG_06></TD>
	     <TD height="25"><a href="javascript:showMenu1( 6,'ZMEMU_CG',MEMU_CG_06,0 )">Input value(Baseline)</a></TD>
	    </TR>
	</TABLE>
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CG6" style="DISPLAY:none">
	    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank2.gif"></TD>
	      <TD><a href="../../develop/inputnode_list.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/inputnode_list.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Manage input value</a></TD>
	    </TR>
	</TABLE>
	<!--  end -->
	
      	</TD>
    </TR>
</TABLE>




      	
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"></TD>
     <TD height="6"></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD height="6"><a href="javascript:showMenuAll( 'xiaoshou' )">Execution</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="xiaoshou" style="DISPLAY:none">
    <TR> 
      <TD>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 1,'ZMEMU',MEMU_01,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id="MEMU_01"></TD>
     <TD><a href="javascript:showMenu1( 1,'ZMEMU',MEMU_01,0 )">Run</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU1" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../management/robot_record.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../management/robot_record.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Record and capture</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank2.gif"></TD>
      <TD><a href="../../management/robot_play.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../management/robot_play.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Play and mapping</a></TD>
    </TR>  
</TABLE>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 2,'ZMEMU',MEMU_02,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_02></TD>
     <TD height="25"><a href="javascript:showMenu1( 2,'ZMEMU',MEMU_02,0 )">Debug</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU2" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>	
	<TR> 
	  <TD></TD>
	  <TD background="../img/line.gif"></TD>
	  <TD><img src="../img/tree_blank.gif"></TD>
	  <TD><a href="../../report/dom_special.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	  <TD><a href="../../report/dom_special.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Valid DOM</a></TD>
	</TR>
	<TR> 
	  <TD></TD>
	  <TD background="../img/line.gif"></TD>
	  <TD><img src="../img/tree_blank.gif"></TD>
	  <TD><a href="../../report/dom.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	  <TD><a href="../../report/dom.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">DOM analysis</a></TD>
	</TR>
</TABLE>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 3,'ZMEMU',MEMU_03,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_03></TD>
     <TD height="25"><a href="javascript:showMenu1( 3,'ZMEMU',MEMU_03,0 )">Monitor</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU3" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
	<TR> 
	  <TD></TD>
	  <TD background="../img/line.gif"></TD>
	  <TD><img src="../img/tree_blank.gif"></TD>
	  <TD><a href="../../report/filter.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	  <TD><a href="../../report/filter.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Record progress</a></TD>
	</TR>
	<TR> 
	  <TD></TD>
	  <TD background="../img/line.gif"></TD>
	  <TD><img src="../img/tree_blank.gif"></TD>
	  <TD><a href="../../report/progress.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	  <TD><a href="../../report/progress.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Play progress</a></TD>
	</TR>	
</TABLE>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 4,'ZMEMU',MEMU_04,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_04></TD>
     <TD height="25"><a href="javascript:showMenu1( 4,'ZMEMU',MEMU_04,0 )">Mapping analysis</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU4" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>   
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../develop/import_file.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../develop/import_file.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Import files</a></TD>
    </TR> 
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../develop/reason.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../develop/reason.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Filter setting</a></TD>
    </TR> 
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../report/analysis_pre.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/analysis_pre.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Pre-analysis</a></TD>
    </TR> 
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../report/analysis_post.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/analysis_post.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Post-analysis</a></TD>
    </TR>
    <% if(false){ %>
    <TR> 
      <TD></TD>
      <TD background="../img/line1.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../report/analysis_report.jsp" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/analysis_report.jsp" target="mainFrame">Total analysis</a></TD>
    </TR>
    <% }%>
   
</TABLE>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 5,'ZMEMU',MEMU_05,1 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_05></TD>
     <TD height="25"><a href="javascript:showMenu1( 5,'ZMEMU',MEMU_05,1 )">Information</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU5" style="DISPLAY:none">
    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../report/log.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/log.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Log</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank.gif"></TD>
      <TD><a href="../../report/alert.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/alert.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Alert</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank2.gif"></TD>
      <TD><a href="../../report/debug.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/debug.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Debug</a></TD>
    </TR>
   
</TABLE>

      	</TD>
    </TR>
</TABLE>

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
      <TD width="20"><a href="../../management/robot_truncation.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../management/robot_truncation.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Detect truncation</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" valign="top"><IMG src="../img/tree_blank_2.gif" width="16"></div></TD>
      <TD width="20"><a href="../../management/robot_iformat.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../management/robot_iformat.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Detect iFormat</a></TD>
    </TR>
</TABLE>

    </td>
  </tr>
</table>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"></TD>
     <TD height="6"></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD height="6"><a href="javascript:showMenuAll( 'cunhuo' )">Report</a></TD>
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
     <TD height="25"><a href="javascript:showMenu1( 2,'ZMEMU_CUNHUO',MEMU_CUNHUO_02,0 )">Mapping report</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CUNHUO2" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../../report/analysis_report.jsp" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/analysis_post.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Analysis report</a></TD>
    </TR>
    
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16" valign="top" onclick="showMenu1( 3,'ZMEMU_CUNHUO',MEMU_CUNHUO_03,1 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CUNHUO_03></TD>
     <TD height="25"><a href="javascript:showMenu1( 3,'ZMEMU_CUNHUO',MEMU_CUNHUO_03,1 )">I18N testing report</a></TD>
    </TR>
</TABLE>
<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CUNHUO3" style="DISPLAY:none">
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../../report/hardcode.jsp?ProjectID=00000265" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/hardcode.jsp?ProjectID=00000265" target="mainFrame">Hard code report</a></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="18" background="../img/line.gif"></TD>
      <TD width="16"><img src="../img/tree_blank.gif"></TD>
      <TD width="20"><a href="../../report/truncation.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/truncation.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Truncation report</a></TD>
    </TR>
    <TR> 
      <TD></TD>
      <TD background="../img/line.gif"></TD>
      <TD><img src="../img/tree_blank2.gif"></TD>
      <TD><a href="../../report/iformat.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
      <TD><a href="../../report/iformat.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">iFormat report</a></TD>
    </TR>
    
    
    
</TABLE>

      	</TD>
    </TR>
</TABLE>


<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"></TD>
     <TD height="6"></TD>
    </TR>
    <TR> 
      <TD width="10"></TD>
      <TD width="16" background="../img/line.gif"><img src="../img/001.jpg"></TD>
     <TD height="6"><a href="javascript:showMenuAll( 'caiwu' )"><font color='grey'>Admin(Underconstruction)</a></a></TD>
    </TR>
</TABLE>

<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="caiwu" style="DISPLAY:none">
    <TR> 
      <TD>
      
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valign="top" onclick="showMenu1( 1,'ZMEMU_CAIWU',MEMU_CAIWU_01,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus.gif" id=MEMU_CAIWU_01></TD>
	     <TD height="25"><a href="javascript:showMenu1( 1,'ZMEMU_CAIWU',MEMU_CAIWU_01,0 )">	Project Management</a></TD>
	    </TR>
	</TABLE>
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CAIWU1" style="DISPLAY:none">
	    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
	    
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Parameter</a></TD>
	    </TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../develop/reason.jsp?ProjectID=00000000" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../develop/reason.jsp?ProjectID=00000000" target="mainFrame">Filter template</a></TD>
	    </TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Project ID</a></TD>
	    </TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Drop No.</a></TD>
	    </TR>
	
	      	</TD>
	    </TR>
	</TABLE>

	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valign="top" onclick="showMenu1( 2,'ZMEMU_CAIWU',MEMU_CAIWU_02,0 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CAIWU_02></TD>
	     <TD height="25"><a href="javascript:showMenu1( 2,'ZMEMU_CAIWU',MEMU_CAIWU_02,0 )">	User Management</a></TD>
	    </TR>
	</TABLE>
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CAIWU2" style="DISPLAY:none">
	    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
	    
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">User Role</a></TD>
	    </TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">User Group</a></TD>
	    </TR>
	
	      	</TD>
	    </TR>
	</TABLE>
	
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0>
	    <TR> 
	      <TD width="10"></TD>
	      <TD width="18" background="../img/line.gif"></TD>
	      <TD width="16" valign="top" onclick="showMenu1( 3,'ZMEMU_CAIWU',MEMU_CAIWU_03,1 )" style="CURSOR: hand"><IMG src="../img/tree_plus2.gif" id=MEMU_CAIWU_03></TD>
	     <TD height="25"><a href="javascript:showMenu1( 3,'ZMEMU_CAIWU',MEMU_CAIWU_03,1 )">Summary Report</a></TD>
	    </TR>
	</TABLE>
	<TABLE width="100%" border=0 cellPadding=0 cellSpacing=0 id="ZMEMU_CAIWU3" style="DISPLAY:none">
	    <TR><TD width="10"></TD><TD width="18"></TD><TD width="16"></TD><TD width="20"></TD><TD></TD></TR>
	    
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank.gif"></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">UI coverage report</a></TD>
	    </TR>
	    <TR> 
	      <TD></TD>
	      <TD background="../img/line.gif"></TD>
	      <TD><img src="../img/tree_blank2.gif"></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="_blank"><img src="../img/xtb.gif" border=0></a></TD>
	      <TD><a href="../../management/blank.jsp?ProjectID=<%=ProjectID%>" target="mainFrame">Mapping report</a></TD>
	    </TR>
	
	      	</TD>
	    </TR>
	</TABLE>
	
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          