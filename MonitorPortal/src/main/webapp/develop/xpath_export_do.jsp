<%
String ProjectID=request.getParameter("ProjectID");
String ProjectName=request.getParameter("ProjectName");
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>
<title>Export</title>
</head>
<body bgcolor="#F8FDFF">
<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>



<table width=500 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <tr bgcolor="#C3DBE8"> 
      <td height=25 colspan=2 align=center><span class="zi"><b>Export result</b></span></td>
      </tr>      
      <tr bgcolor="#F8FDFF"> 
        <td height="25" align=right>Exported file:</td><td> 
          	c:\framework\xpath\<%=ProjectID%>_<%=ProjectName%>.csv
          </td>
      </tr>
</table>
</td>
</tr>
</table>
</body>
</html>
				
		