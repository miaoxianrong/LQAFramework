<%
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="";
}
%>
<head>
<title>LQA Framework</title>
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
-->
</style>
<link href="../img/web.css" rel="stylesheet" type="text/css">
<script>

function printMF()
{
	top.frames["mainFrame"].focus();   
  top.frames["mainFrame"].print();
}
</script> 
</head>
<body bgcolor="#FFFFFF">
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="../img/top2_bg.jpg">
  <tr>
    <td width="190"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center"><img src="../img/ca.png" alt="Framework" width="64" height="57"></td>
      </tr>
      <tr>
        <td align="center"></td>
      </tr>
      <tr>
        <td height="25" align="center">
<div id="time"></div>
<script language="javascript" type="text/javascript">
 window.onload=function (){
  setInterval("var nowTime = new Date();document.getElementById('time').innerHTML=nowTime.getHours()+':'+nowTime.getMinutes()+':'+nowTime.getSeconds();",1000);
 }
</script>
        </td>
      </tr>
      
    </table>
    </td>
    <td valign="bottom"><table width="100%" height="94" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="15"><img src="../img/jxc2_03.jpg" width="15" height="94" alt=""></td>
        <td background="../img/dh2_bg.jpg">
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr align="center">
                <td width="2%"></td>
               
		
                <td width="12%" class="z13"><a href="../../develop/xpath.jsp?ProjectID=<%=ProjectID%>" target="mainFrame"><img src="../img/dh_07.gif" width="46" height="49" border="0"><br>XPATH</a></td>
              
                <td width="12%" class="z13"><a href="../../develop/unique.jsp?ProjectID=<%=ProjectID%>" target="mainFrame"><img src="../img/dh_09.gif" width="43" height="49" border="0"><br>UniqueObject</a></td>
                
                <td width="12%" class="z13"><a href="../../develop/excluder.jsp?ProjectID=<%=ProjectID%>" target="mainFrame"><img src="../img/dh_11.gif" width="43" height="49" border="0"><br>Excluder</a></td>
                
                <td width="12%" class="z13"><a href="../../develop/includer.jsp?ProjectID=<%=ProjectID%>" target="mainFrame"><img src="../img/czd.jpg" width="47" height="49" border="0"><br>Includer</a></td>
                
                <td width="12%" class="z13"><a href="../../develop/domtype.jsp?ProjectID=<%=ProjectID%>" target="mainFrame"><img src="../img/leaf.jpg" width="43" height="49" border="0"><br>DOM Type</a></td>
                               
                <td width="12%" class="z13"><a href="../../develop/inputnode_list.jsp?ProjectID=<%=ProjectID%>" target="mainFrame"><img src="../img/gxsz.jpg" width="47" height="49" border="0"><br>Input value</a></td>
              
                <td width="12%" class="z13"><a href="../jsp/exit.jsp" target="_parent"><img src="../img/dh_15.gif" width="38" height="49" border="0"><br>Exit</a></td>
               
                <td width="2%"></td>
              </tr>
            </table>	
        	</td>
        <td width="13"><img src="../img/jxc2_05.jpg" width="13" height="94" alt=""></td>
      </tr>
    </table></td>
    <td width="20">&nbsp;</td>
  </tr>
</table>
<table width="100%" height="10"  border="0" cellpadding="0" cellspacing="0" background="../img/jxc2_11.jpg">
<form name="zdx">
<input type="hidden" name="tcbz" value="0">
  <tr>
    <td></td>
  </tr>
</form>  
  <tr>
    <td height=1 bgcolor="#045a82"></td>
  </tr>
</table>
</body>
</html>