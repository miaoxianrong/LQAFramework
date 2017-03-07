<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>
<%
String Infomation=request.getParameter("Infomation");
%>
<title>Infomation</title>
</head>
<base target="_self">
<body bgcolor="#F8FDFF">

<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=1000 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
    <tbody>
      <tr bgcolor="#C3DBE8"> 
        <td height=25 colspan=2 align=center><span class="zi"><font size=4>Invalid operation!</font></span></td>
      </tr>      
      <tr bgcolor="#E8FDFF"> 
        <td height="25" align=right>Information: </td>
        <td><font color=red><%=Infomation%></font></td>
      </tr>
     
</table>
</td>
</tr>
</table>
</body>
</html>
