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


String Feature="";
String getFeature=request.getParameter("Feature");
if(getFeature!=null){
	Feature=getFeature;
}
String screenName="00_main_page.png";
String getscreenName=request.getParameter("screenName");
if(getscreenName!=null){
	screenName=getscreenName;
}else{
	screenName="000.png";
}
if(NodePath.compareTo("000")==0){
	screenName="All";	
}
%>
<table width="100%" border=0 align="center" cellpadding=5 cellspacing=0>
<tr><td height=10 align=right></td></tr>
<tr>
<td align=center class="zi" height=200 valign=top>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">

<tr bgcolor="#F8FDFF"> 
<td  align=left vAlign=top>
<%
if(NodePath.compareTo("000")!=0){
%>
<img border=0 src='<%=DebugServer%>/screen/<%=ProjectID%>/hardcode/<%=screenName%>'>
<%
}else{
	out.print("ProjectID: "+ProjectID);
}
%>
</td></tr>

<tr bgcolor="#C3DBE8"> 
<td height="25" align=left><div align="left">
<%=screenName%>: Hardcode string list
</div></td>
</tr>

<tr bgcolor="#F8FDFF"> 
<td><div align=left>
<%
String UniqueStringListID="";
String Hardcode="";
String sql="";
int i=0;
if(NodePath.compareTo("000")==0){
	sql="select * from UniqueStringList where ProjectID='"+ProjectID+"' order by Hardcode asc";
}else{
	sql="select * from HardcodeList where ProjectID='"+ProjectID+"' and NodePath='"+NodePath+"' order by Hardcode asc";
}
ResultSet rs=workM.getSQL(sql);
while(rs.next()){
	
	UniqueStringListID=rs.getString("UniqueStringListID");
	Hardcode=rs.getString("Hardcode");
	if(Hardcode.indexOf("#")==-1&&Hardcode.indexOf("/")==-1){
	i++;
	%>
	<%=i%>:
	<a target=_blank href="hardcode_link.jsp?ProjectID=<%=ProjectID%>&UniqueStringListID=<%=UniqueStringListID%>">
	<font color=blue>
	<%=Hardcode%>
	</font>
	</a>	
	<BR>
	<%
	}
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