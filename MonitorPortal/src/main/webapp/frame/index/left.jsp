<%
String ProjectID=request.getParameter("ProjectID");
if(ProjectID==null){
	ProjectID="";
}
%>
<html>
<head>
<title></title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
	line-height: 130%;
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
<script language="javascript">
<!--
function showMenu( index )
{
	for(i=1;i<7;i++)
	{
		//alert(i);
		if(index==i)
		{
			document.all['gnlb'+i].style.display = '';
		}
		else
		{
			document.all['gnlb'+i].style.display = 'none';
		}
		alert(i);
	}
}

//alert(screen.width);
//alert(screen.height);
//-->
</script>   


</head>
<body bgcolor="#F2FAFD">
<table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#F2FAFD" background="../img/jxc_12.jpg">
      <tr>
        <td width="20" height="24"><img src="../img/comm.gif" width="19" height="17"></td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;LQA Framework</td>
        <td width="18"></td>
      </tr>
      <tr>
        <td height="1" colspan=3 bgcolor="#055880"></td>
      </tr>
    </table>
<table cellspacing=0 cellpadding=0 border=0 height="94%" width="100%" align="center">
  <tr> 
    <td valign="top"> 
	<iframe src="left_kjj.jsp?ProjectID=<%=ProjectID%>" name="nr" width="100%" height=100% align=middle valign-top scrolling=auto frameborder=0 border=0 >

    </td>
  </tr>
</table>

</body>
</html>

 
</BODY>
