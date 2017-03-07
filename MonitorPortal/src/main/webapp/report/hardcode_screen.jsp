<%@ page language="java" import="java.sql.*"%>
<%@ page language="java" import="java.util.*" %>
<jsp:useBean id="workM" scope="page" class="record.ManageURL" />

<html>
<head>
<LINK href="./img/style.css" rel=stylesheet type=text/css>
<LINK href="./img/css.css" rel=stylesheet title=list type=text/css>
<title></title>
</script>
</head>
<body bgcolor="#F8FDFF">
<%
String ProjectID="00000000";
String getProjectID=request.getParameter("ProjectID");
if(getProjectID!=null){
	ProjectID=getProjectID;
}
String UniqueStringListID=request.getParameter("UniqueStringListID");
if(UniqueStringListID==null){
	UniqueStringListID="0";
}
String NodePath=request.getParameter("NodePath");
if(NodePath==null){
	NodePath="000";
}
workM.getConnection();

String DebugServer="http://localhost";
String sql_debug="select * from ProjectParameter where ProjectID='"+ProjectID+"'";
ResultSet rs_debug=workM.getSQL(sql_debug);
if(rs_debug.next()){
	DebugServer=rs_debug.getString("DebugServer");
}
rs_debug.close();
String Hardcode="";
String sql1="select * from UniqueStringList where ProjectID='"+ProjectID+"' and UniqueStringListID="+UniqueStringListID;
ResultSet rs1=workM.getSQL(sql1);
if(rs1.next()){
	Hardcode=rs1.getString("Hardcode");
}
rs1.close();

DebugServer="localhost:8082";


%>
<table width="100%" border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" height=200 valign=top>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">



<tr bgcolor="#C3DBE8"> 
<td height="25" align=left><div align="left">
Screen list for hard code string <font color=red><%=Hardcode%></font>
</div></td>
</tr>

<tr bgcolor="#F8FDFF"> 
<td><div align=left>




<img border=0 src='../screen/00000265/hardcode/001.png'><BR>
<img border=0 src='../screen/00000265/hardcode/002.png'><BR>
<%
String sql="";
int i=0;
sql="select * from HardcodeList where ProjectID='"+ProjectID+"' and UniqueStringListID="+UniqueStringListID+" order by NodePath asc";
//out.print(sql);
ResultSet rs=workM.getSQL(sql);
while(rs.next()){
	
	UniqueStringListID=rs.getString("UniqueStringListID");
	NodePath=rs.getString("NodePath");
	i++;
	%>	
	<img border=0 src='../screen/<%=ProjectID%>/hardcode/<%=NodePath%>.png'><%=NodePath%>.png
	<BR>
	<%	
}
workM.close();
%>
</div></td></tr>
</table>

</td>
</tr>
</table>
</body>
</html>