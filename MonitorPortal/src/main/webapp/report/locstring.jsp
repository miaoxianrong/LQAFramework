<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<%
String ProjectID=request.getParameter("ProjectID");
%>
<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %> 
<title>Local Strings</title>

</head>
<body bgcolor="#F8FDFF">

<table width=100% border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=right class="zi" height=200 valign=top>

<table width=800 border=0 align="center" cellpadding=5 cellspacing=1 bgcolor="#666666" class="tb1" style="FONT-SIZE: 9pt">
      <form method="POST" action="parameter_edit_do.jsp" name="kh" onsubmit="return validata();">
      <input type=hidden name="ProjectID" value="<%=ProjectID%>">
    <tbody>         
     
      <tr bgcolor="#C3DBE8"> 
 	<td height="25" width='50%' align=center>Local String </td>
        <td height="25" width='20%' align=center>Language </td>
        <td height="25" width='10%' align=center>Type </td>
	<td height="25" width='20%' align=center>Validated </td>
      </tr>
<%
workM.getConnection();
String LocalString="";
String Language="";
String StringType="";
String sql="select * from LocalStringList";// where ProjectID='"+ProjectID+"'";
ResultSet rs1=workM.getSQL(sql);
while(rs1.next()){
	LocalString=rs1.getString("LocalString");
	Language=rs1.getString("Language");
	StringType=rs1.getString("StringType");	

%>
      <tr bgcolor="#E8FDFF"> 
	<td height="25" width='50%' align=center><font color=blue><%=LocalString%></font></td>
        <td height="25" width='20%' align=center><font color=purple><%=Language%></font></td>
        <td height="25" width='10%' align=center><font color=blue><%=StringType%></font></td>
	<td height="25" width='20%' align=center><font color=red>validating...</font></td>
      </tr>
<%

}
rs1.close();
workM.close();
%>
      </form>
</table>
</td>
</tr>
</table>
</body>
</html>
